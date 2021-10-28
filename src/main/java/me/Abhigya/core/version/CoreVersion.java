package me.Abhigya.core.version;

public enum CoreVersion {
    v1_0_0(100),
    v1_0_1(101),
    v1_1_0(110),
    v1_1_1(111),
    v1_1_2(112),
    v1_2_0(120),
    v1_2_1(121),
    v1_2_2(122),
    ;

    private final int id;

    CoreVersion(int id) {
        this.id = id;
    }

    /**
     * Gets the version's id.
     *
     * <p>
     *
     * @return Version's id
     */
    public int getID() {
        return this.id;
    }

    /**
     * Checks whether this version is older than the provided version.
     *
     * <p>
     *
     * @param other Other version
     * @return true if older
     */
    public boolean isOlder(CoreVersion other) {
        return (getID() < other.getID());
    }

    /**
     * Checks whether this version is older than or equals to the provided version.
     *
     * <p>
     *
     * @param other Other version
     * @return true if older or equals
     */
    public boolean isOlderEquals(CoreVersion other) {
        return (getID() <= other.getID());
    }

    /**
     * Checks whether this version is newer than the provided version.
     *
     * <p>
     *
     * @param other Other version
     * @return true if newer
     */
    public boolean isNewer(CoreVersion other) {
        return (getID() > other.getID());
    }

    /**
     * Checks whether this version is newer than or equals to the provided version.
     *
     * <p>
     *
     * @param other Other version
     * @return true if newer or equals
     */
    public boolean isNewerEquals(CoreVersion other) {
        return (getID() >= other.getID());
    }
}
