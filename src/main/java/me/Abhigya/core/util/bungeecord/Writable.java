package me.Abhigya.core.util.bungeecord;

import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Class for writing plugin messages.
 */
public class Writable {

    public static Writable of(Object to_write) {
        if (to_write instanceof String) {
            throw new UnsupportedOperationException("The class of the object to write is not compatible with this operation!");
        }

        WriteType type = WriteType.of(to_write);
        if (type != null) {
            return new Writable(to_write, type);
        }
        return null;
    }

    private final Object to_write;
    private final WriteType type;

    public Writable(Object to_write, WriteType type) {
        this.to_write = to_write;
        this.type = type;
    }

    public Object getObjectToWrite() {
        return to_write;
    }

    public WriteType getWriteType() {
        return type;
    }

    public void writeTo(DataOutput data) throws IOException, IllegalAccessException, IllegalArgumentException,
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
                throw new IllegalArgumentException("The WriteType does not match with the class of the object to write!");
            }
        }

        switch (type) {
            case UTF:
                data.writeUTF((String) to_write);
                break;
            default:
                for (Class<?> type_pr_classes : type.getPrimitiveClasses()) {
                    try {
                        final String write_method_name = "write"
                                + (type.name().substring(0, 1) + type.name().toLowerCase().substring(1));
                        final Method write = data.getClass().getMethod(write_method_name, type_pr_classes);
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
