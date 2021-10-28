package me.Abhigya.core.util.bungeecord;

import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/** Class for writing plugin messages. */
public class Writable {

    private final Object to_write;
    private final WriteType type;

    /**
     * Constructs the writable object.
     *
     * <p>
     *
     * @param to_write Object to write
     * @param type Write type
     */
    public Writable(Object to_write, WriteType type) {
        this.to_write = to_write;
        this.type = type;
    }

    /**
     * Writes values to message.
     *
     * <p>
     *
     * @param to_write Object value to write
     * @return {@link Writable} object
     */
    public static Writable of(Object to_write) {
        if (to_write instanceof String) {
            throw new UnsupportedOperationException(
                    "The class of the object to write is not compatible with this operation!");
        }

        WriteType type = WriteType.of(to_write);
        if (type != null) {
            return new Writable(to_write, type);
        }
        return null;
    }

    /**
     * Returns the object which is to write.
     *
     * <p>
     *
     * @return Object to write
     */
    public Object getObjectToWrite() {
        return to_write;
    }

    /**
     * Returns the write type.
     *
     * <p>
     *
     * @return {@link WriteType}
     */
    public WriteType getWriteType() {
        return type;
    }

    /**
     * Writes to data output stream.
     *
     * <p>
     *
     * @param data DataOutput to write to
     * @throws IOException thrown when an issue occur while writing to message stream
     * @throws SecurityException thrown by the security manager to indicate a security violation
     * @throws NoSuchMethodException thrown when a particular method cannot be found
     * @throws InvocationTargetException thrown when a error occur while invoking a method or
     *     constructor
     * @throws IllegalArgumentException thrown when a illegal argument is passed
     * @throws IllegalAccessException thrown when an application tries to reflectively create an
     *     instance (other than an array), set or get a field, or invoke a method, but the currently
     *     executing method does not have access to the definition of the specified class, field,
     *     method or constructor
     */
    public void writeTo(DataOutput data)
            throws IOException, IllegalAccessException, IllegalArgumentException,
                    InvocationTargetException, NoSuchMethodException, SecurityException {
        /* cannot write when the object to write is null or the write type is null */
        if (to_write == null || type == null) {
            throw new IllegalArgumentException("The WriteType or the object to write is null!");
        }

        /* check instance of object to write */
        for (int x = 0; x < type.getPrimitiveClasses().length; x++) {
            Class<?> type_pr_classes = type.getPrimitiveClasses()[x];
            if (type_pr_classes.equals(to_write.getClass())) {
                break;
            } else if ((x + 1) == type.getPrimitiveClasses().length) {
                throw new IllegalArgumentException(
                        "The WriteType does not match with the class of the object to write!");
            }
        }

        switch (type) {
            case UTF:
                data.writeUTF((String) to_write);
                break;
            default:
                for (Class<?> type_pr_classes : type.getPrimitiveClasses()) {
                    try {
                        final String write_method_name =
                                "write"
                                        + (type.name().substring(0, 1)
                                                + type.name().toLowerCase().substring(1));
                        final Method write =
                                data.getClass().getMethod(write_method_name, type_pr_classes);
                        write.invoke(data, to_write);
                        break;
                    } catch (Throwable t) {
                        t.printStackTrace();
                        continue;
                    }
                }
                break;
        }
    }
}
