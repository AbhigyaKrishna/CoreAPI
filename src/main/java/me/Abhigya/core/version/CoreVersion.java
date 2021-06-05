package me.Abhigya.core.version;

import me.Abhigya.core.main.Main;

public enum CoreVersion {

    v1_0_0(100);

    private final int id;

    CoreVersion(int id) {
        this.id = id;
    }

    public static CoreVersion getCoreVersion() {
        return valueOf(format(Main.getInstance().getDescription().getVersion()));
    }

    private static String format(String suppose_version) {
        return "v" + suppose_version.trim().toLowerCase().replace(".", "_").replace("v", "");
    }

    public int getID() {
        return this.id;
    }

    public boolean isOlder(CoreVersion other) {
        return (getID() < other.getID());
    }

    public boolean isOlderEquals(CoreVersion other) {
        return (getID() <= other.getID());
    }

    public boolean isNewer(CoreVersion other) {
        return (getID() > other.getID());
    }

    public boolean isNewerEquals(CoreVersion other) {
        return (getID() >= other.getID());
    }

}
