package com.gmail.mozhgru.utils;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;

/**
 * Специальный класс с дебаговыми методами.
 */
public final class Debug {

    private static Logger logger = Logger.getLogger(Debug.class);

    private Debug() {
    }

    /**
     * сон в милисекундах.
     *
     * @param time сколько спать
     */
    @Deprecated
    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.info("Thread has been interrupted");
        }
    }

    /**
     * вывод сообщения в консоль.
     */
    @Deprecated
    public static void info(Object o) {
        System.out.println(o);
    }

    /**
     * вывод сообщения в консоль c заданным форматом.
     */
    @Deprecated
    public static void info(String format, Object... o) {
        System.out.println(String.format(format, o));
    }

    public static Map<String, Object> getStaticFields(Class clazz) {
        Map result = new TreeMap();
        for (Field field : clazz.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                result.put(field.getName(), field.get(null));
            } catch (Exception ignored) {

            }
        }
        return result;
    }

    public Map<String, Object> getFields(Object o) {
        Map result = new TreeMap();
        Class clazz = o.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                result.put(field.getName(), field.get(o));
            } catch (Exception ignored) {

            }
        }
        return result;
    }
}
