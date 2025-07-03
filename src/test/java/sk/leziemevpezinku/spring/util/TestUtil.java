package sk.leziemevpezinku.spring.util;

import java.lang.reflect.Field;

/**
 * Utility class for performing common reflection-based operations.
 *
 * This class provides a mechanism to modify private or protected fields of a target object by
 * using reflection. It is primarily designed to help in testing scenarios where field injection
 * or direct modification is necessary.
 */
public class TestUtil {

    /**
     * Sets the value of a specified field of a given target object using reflection.
     *
     * @param target    the object whose field should be modified
     * @param fieldName the name of the field to be modified
     * @param value     the new value to set for the specified field
     * @throws RuntimeException if the field cannot be accessed or modified
     */
    public static void setField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
