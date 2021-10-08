package me.Abhigya.core.version;

import me.Abhigya.core.main.CoreAPI;

public enum CoreVersion {

    v1_0_0( 100 ),
    v1_0_1( 101 ),
    v1_1_0( 110 ),
    v1_1_1( 111 ),
    v1_1_2( 112 ),
    v1_2_0( 120 ),
    v1_2_1( 121 ),
    ;

    private final int id;

    CoreVersion( int id ) {
        this.id = id;
    }

    /**
     * Gets the current {@link CoreAPI} version in use.
     * <p>
     *
     * @return Version of CoreAPI
     */
    public static CoreVersion getCoreVersion( ) {
        return valueOf( format( CoreAPI.getInstance( ).getDescription( ).getVersion( ) ) );
    }

    /**
     * Get the formatted string for the version.
     * <p>
     *
     * @param suppose_version Version to format to string
     * @return Formatted version string
     */
    private static String format( String suppose_version ) {
        return "v" + suppose_version.trim( ).toLowerCase( ).replace( ".", "_" ).replace( "v", "" );
    }

    /**
     * Gets the version's id.
     * <p>
     *
     * @return Version's id
     */
    public int getID( ) {
        return this.id;
    }

    /**
     * Checks whether this version is older than the provided version.
     * <p>
     *
     * @param other Other version
     * @return true if older
     */
    public boolean isOlder( CoreVersion other ) {
        return ( getID( ) < other.getID( ) );
    }

    /**
     * Checks whether this version is older than or equals to the provided version.
     * <p>
     *
     * @param other Other version
     * @return true if older or equals
     */
    public boolean isOlderEquals( CoreVersion other ) {
        return ( getID( ) <= other.getID( ) );
    }

    /**
     * Checks whether this version is newer than the provided version.
     * <p>
     *
     * @param other Other version
     * @return true if newer
     */
    public boolean isNewer( CoreVersion other ) {
        return ( getID( ) > other.getID( ) );
    }

    /**
     * Checks whether this version is newer than or equals to the provided version.
     * <p>
     *
     * @param other Other version
     * @return true if newer or equals
     */
    public boolean isNewerEquals( CoreVersion other ) {
        return ( getID( ) >= other.getID( ) );
    }

}
