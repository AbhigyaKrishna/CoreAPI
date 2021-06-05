package me.Abhigya.core.util.reflection.general;

/**
 * Class to reflect Enumerations
 */
public class EnumReflection {

    /**
     * Returns the enum constant of the specified enum type with the specified name.
     * The name must match exactly an identifier used to declare an enum constant in
     * this type. (Extraneous whitespace characters are not permitted.)
     * <p>
     * Note that for a particular enum type {@code T}, the implicitly declared
     * {@code public static T valueOf(String)} method on that enum may be used
     * instead of this method to map from a name to the corresponding enum constant.
     * All the constants of an enum type can be obtained by calling the implicit
     * {@code public static T[] values()} method of that type.
     * <p>
     *
     * @param <T>   The enum type whose constant is to be returned
     * @param clazz {@code Class} object of the enum type from which to return a
     *              constant
     * @param name  Name of the constant to return
     * @return Enum constant of the specified enum type with the specified name,
     * or null if doesn't exist
     */
    public static <T extends Enum<T>> T getEnumConstant(Class<T> clazz, String name) {
        try {
            return Enum.valueOf(clazz, name);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
