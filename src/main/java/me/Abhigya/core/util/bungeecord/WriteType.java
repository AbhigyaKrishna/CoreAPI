package me.Abhigya.core.util.bungeecord;

/** Enumeration of all write types. */
public enum WriteType {
    BOOLEAN(boolean.class, Boolean.class),
    BYTE(byte.class, Byte.class),
    CHAR(char.class, Character.class),
    DOUBLE(double.class, Double.class),
    FLOAT(float.class, Float.class),
    INT(int.class, Integer.class),
    LONG(long.class, Long.class),
    SHORT(short.class, Short.class),
    BYTES(String.class),
    CHARS(String.class),
    UTF(String.class);

    private final Class<?>[] primitive_classes;

    WriteType(Class<?>... primitive_classes) {
        this.primitive_classes = primitive_classes;
    }

    /**
     * Returns the write type of the given data.
     *
     * <p>
     *
     * @param to_write Object to get write type of
     * @return WriteType of the object
     */
    public static WriteType of(Object to_write) {
        if (to_write == null) {
            return null;
        }

        if (to_write instanceof String) {
            throw new UnsupportedOperationException(
                    "The class of the object to write is not compatible with this operation!");
        }

        for (WriteType type : values()) {
            for (Class<?> type_pr_classes : type.getPrimitiveClasses()) {
                if (type_pr_classes.equals(to_write.getClass())) {
                    return type;
                }
            }
        }
        return null;
    }

    /**
     * Returns the primitive class.
     *
     * <p>
     *
     * @return Primitive classes
     */
    public Class<?>[] getPrimitiveClasses() {
        return primitive_classes;
    }
}
