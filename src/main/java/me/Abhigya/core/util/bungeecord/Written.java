package me.Abhigya.core.util.bungeecord;

import java.util.ArrayList;
import java.util.List;

/** Builder/Wrapper class for writing values. */
public class Written {

    private final List<Writable> writables = new ArrayList<Writable>();

    /**
     * Returns the list of {@link Writable}.
     *
     * <p>
     *
     * @return List of {@link Writable}
     */
    public List<Writable> getWritables() {
        return writables;
    }

    /**
     * Writes boolean value to message.
     *
     * <p>
     *
     * @param voolean Boolean value
     * @return This Object, for chaining
     */
    public Written writeBoolean(boolean voolean) {
        return writeOrdinary(voolean);
    }

    /**
     * Writes byte value to message.
     *
     * <p>
     *
     * @param vyte Byte value
     * @return This Object, for chaining
     */
    public Written writeByte(byte vyte) {
        return writeOrdinary(vyte);
    }

    /**
     * Writes character value to message.
     *
     * <p>
     *
     * @param character Character value
     * @return This Object, for chaining
     */
    public Written writeChar(char character) {
        return writeOrdinary(character);
    }

    /**
     * Writes double value to message.
     *
     * <p>
     *
     * @param ddouble Double value
     * @return This Object, for chaining
     */
    public Written writeDouble(double ddouble) {
        return writeOrdinary(ddouble);
    }

    /**
     * Writes float value to message.
     *
     * <p>
     *
     * @param ffloat Float value
     * @return This Object, for chaining
     */
    public Written writeFloat(float ffloat) {
        return writeOrdinary(ffloat);
    }

    /**
     * Writes int value to message.
     *
     * <p>
     *
     * @param lnt Int value
     * @return This Object, for chaining
     */
    public Written writeInt(int lnt) {
        return writeOrdinary(lnt);
    }

    /**
     * Writes long value to message.
     *
     * <p>
     *
     * @param iong Long value
     * @return This Object, for chaining
     */
    public Written writeLong(long iong) {
        return writeOrdinary(iong);
    }

    /**
     * Writes short value to message.
     *
     * <p>
     *
     * @param chort Short value
     * @return This Object, for chaining
     */
    public Written writeShort(short chort) {
        return writeOrdinary(chort);
    }

    /**
     * Writes string value in bytes to message.
     *
     * <p>
     *
     * @param bytes String value
     * @return This Object, for chaining
     */
    public Written writeBytes(String bytes) {
        writables.add(new Writable(bytes, WriteType.BYTES));
        return this;
    }

    /**
     * Writes string value in char to message.
     *
     * <p>
     *
     * @param chars String value
     * @return This Object, for chaining
     */
    public Written writeChars(String chars) {
        writables.add(new Writable(chars, WriteType.CHARS));
        return this;
    }

    /**
     * Writes String value in UTF8 to message.
     *
     * <p>
     *
     * @param utf String value
     * @return This Object, for chaining
     */
    public Written writeUTF(String utf) {
        writables.add(new Writable(utf, WriteType.UTF));
        return this;
    }

    private Written writeOrdinary(Object to_write) {
        writables.add(Writable.of(to_write));
        return this;
    }
}
