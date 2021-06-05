package me.Abhigya.core.util.bungeecord;

import java.util.ArrayList;
import java.util.List;

public class Written {

    private final List<Writable> writables = new ArrayList<Writable>();

    public List<Writable> getWritables() {
        return writables;
    }

    public Written writeBoolean(boolean voolean) {
        return writeOrdinary(voolean);
    }

    public Written writeByte(byte vyte) {
        return writeOrdinary(vyte);
    }

    public Written writeChar(char character) {
        return writeOrdinary(character);
    }

    public Written writeDouble(double ddouble) {
        return writeOrdinary(ddouble);
    }

    public Written writeFloat(float ffloat) {
        return writeOrdinary(ffloat);
    }

    public Written writeInt(int lnt) {
        return writeOrdinary(lnt);
    }

    public Written writeLong(long iong) {
        return writeOrdinary(iong);
    }

    public Written writeShort(short chort) {
        return writeOrdinary(chort);
    }

    public Written writeBytes(String bytes) {
        writables.add(new Writable(bytes, WriteType.BYTES));
        return this;
    }

    public Written writeChars(String chars) {
        writables.add(new Writable(chars, WriteType.CHARS));
        return this;
    }

    public Written writeUTF(String utf) {
        writables.add(new Writable(utf, WriteType.UTF));
        return this;
    }

    private Written writeOrdinary(Object to_write) {
        writables.add(Writable.of(to_write));
        return this;
    }

}
