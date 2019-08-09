package com.gmail.mozhgru.config;

import com.gmail.mozhgru.annotations.Page;
import com.gmail.mozhgru.annotations.Pages;

import com.gmail.mozhgru.exceptions.NotImplementedException;
import com.gmail.mozhgru.exceptions.PageNotFoundException;
import com.gmail.mozhgru.page.AbstractPage;
import com.gmail.mozhgru.utils.DriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PageHandler {

    private static ThreadLocal<Map<Class<? extends AbstractPage>, Object>> pageStorage;
    private static ThreadLocal<AbstractPage> activePage;
    private static ThreadLocal<AbstractPage> previousPage;
    private static HashMap<String, Class<? extends AbstractPage>> pageNames = new HashMap<>();
    private static String basicPackage = AbstractPage.class.getPackageName();
    private static final String basicPath = "src/main/java/";

    static {
        pageStorage = new ThreadLocal<>();
        pageStorage.set(new HashMap<>());

        activePage = new ThreadLocal<>();
        previousPage = new ThreadLocal<>();
        File basicPagesDir = new File(basicPath + basicPackage.replace(".", "/"));
        try {
            populatePages(basicPagesDir);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    static {
        pageStorage = new ThreadLocal<>();
        pageStorage.set(new HashMap<>());

        activePage = new ThreadLocal<>();
        previousPage = new ThreadLocal<>();
        File basicPagesDir = new File(basicPath + basicPackage.replace(".", "/"));
        try {
            populatePages(basicPagesDir);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private PageHandler() {
    }

    @SuppressWarnings("unchecked")
    private static void populatePages(File dir) throws ClassNotFoundException {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                populatePages(file);
            }
            String fileName = file.getName();
            if (!fileName.endsWith(".class") && !fileName.endsWith(".java")) {
                continue;
            }
            String className = file.getPath().replace("\\", "/")
                    .replace(basicPath, "")
                    .replaceAll("(\\.java$)|(\\.class$)", "")
                    .replaceAll("/", ".");

            Class<? extends AbstractPage> klass = (Class<? extends AbstractPage>) Class.forName(className);
            if ((!klass.isAnnotationPresent(Page.class)) && (!klass.isAnnotationPresent(Pages.class))) {
                continue;
            }
            if (klass.isAnnotationPresent(Pages.class)) {
                Arrays.stream(klass.getDeclaredAnnotation(Pages.class).value())
                        .forEach(page -> pageNames.put(page.value(), klass));
                continue;
            }
            String pageName = klass.getDeclaredAnnotation(Page.class).value();
            pageNames.put(pageName, klass);
        }
    }

    private static <P extends AbstractPage> P createNewInstance(Class<P> pageClass) {
        try {
            Constructor<P> constructor = pageClass.getConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <P extends AbstractPage> P getPage(boolean mustCreate, Class<P> pageClass) {
        if (activePage != null && activePage.get() != null && !Objects.equals(activePage.get().getClass(), pageClass)) {
            activePage.get().afterClose();
            previousPage.set(activePage.get());
        }
        Optional<P> optionalPage = Optional.ofNullable((P) pageStorage.get().get(pageClass));
        P page = mustCreate || optionalPage.isEmpty() ? createNewInstance(Objects.requireNonNull(pageClass)) : optionalPage.get();
        activePage.set(page);
        return page;
    }

    @SuppressWarnings("unchecked")
    public static <P extends AbstractPage> P getPage(Class<P> pageClass) {
        return getPage(false, pageClass);
    }

    @SuppressWarnings("unchecked")
    public static <P extends AbstractPage> P getPage(String name) {
        Class<P> pageClass = (Class<P>) pageNames.get(name);
        return getPage(pageClass);
    }

    @SuppressWarnings("unchecked")
    public static <P extends AbstractPage> P createPage(Class<P> pageClass) {
        return getPage(true, pageClass);
    }

    @SuppressWarnings("unchecked")
    public static <P extends AbstractPage> P createPage(String name) {
        Class<P> pageClass = (Class<P>) Optional.ofNullable(pageNames.get(name))
                .orElseThrow(() -> new PageNotFoundException(name));
        return createPage(pageClass);
    }

    @SuppressWarnings("unchecked")
    public static <P extends AbstractPage> P loadPreviousPage() {
        if (previousPage.get() != null) {
            activePage.set(previousPage.get());
        }
        return (P) activePage.get();
    }

    public static void putPage(AbstractPage page) {
        pageStorage.get().put(page.getClass(), page);
        activePage.set(page);
    }

    @SuppressWarnings("unchecked")
    public static <P extends AbstractPage> P getActivePage() {
        return (P) activePage.get();
    }

    public static Function waitForLoadFinished(boolean isStrict, String... states) {
        if (states.length == 0) {
            throw new NotImplementedException("State страницы не зарегистрирован");
        }
        StringBuilder state = new StringBuilder();
        for (int i = 0; i < states.length; i++) {
            state.append(String.format("@state='%s'", states[i]));
            if (i != states.length - 1) {
                state.append(" or ");
            }
        }
        String ionView = String.format("//ion-view[@nav-view!='cached'][%s]", state.toString());
        String spinnerView = ionView + "//ion-spinner";
        boolean isFast = (!isStrict) && Config.getWaitStrategy();
        Predicate<Stream<WebElement>> matcher = isFast ? o -> o.count() < 2 : o -> o.noneMatch(WebElement::isDisplayed);
        return o -> {
            try {
                DriverUtils.waitFor(ExpectedConditions
                        .visibilityOfAllElementsLocatedBy(By.xpath(ionView)));
                List<WebElement> spinners = DriverManager.get()
                        .findElements(By.xpath(spinnerView));
                return matcher.test(spinners.stream());
            } catch (WebDriverException e) {
                return false;
            }
        };
    }
}
