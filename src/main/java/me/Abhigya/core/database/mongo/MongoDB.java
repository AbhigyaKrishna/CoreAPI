package me.Abhigya.core.database.mongo;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import me.Abhigya.core.database.Database;
import me.Abhigya.core.database.DatabaseType;
import me.Abhigya.core.util.StringUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

/**
 * Class for interacting with a Mongo database.
 */
public class MongoDB extends Database {

    private final String ip;
    private final int port;
    private final String dbname;

    private MongoClient client;
    private DB db;
    private Set< DBCollection > collections = new HashSet<>( );

    /**
     * Constructs MongoDB with credentials.
     * <p>
     *
     * @param host   Database host address
     * @param port   Database port
     * @param dbname Database name
     */
    public MongoDB( String host, int port, String dbname ) {
        super( DatabaseType.MongoDB );

        Validate.isTrue( !StringUtils.isBlank( host ), "Host Address cannot be null or empty!" );

        this.ip = host;
        this.port = port;
        this.dbname = dbname;
    }


    /**
     * Gets whether connected to MongoDB.
     * <p>
     *
     * @return true if connected.
     */
    @Override
    public boolean isConnected( ) {
        return this.client != null && this.db != null;
    }

    /**
     * Starts the connection with MongoDB.
     */
    @Override
    public synchronized void connect( ) {
        this.client = new MongoClient( this.ip, this.port );
        this.db = client.getDB( this.dbname );
    }

    /**
     * Closes the connection with MongoDB.
     */
    @Override
    public void disconnect( ) {
        this.client.close( );
        this.client = null;
        this.db = null;
    }

    /**
     * Gets Database Collection with given names and adds to
     * database cache.
     * <p>
     *
     * @param name Names of collection
     * @return Set of database collections
     */
    public Set< DBCollection > getCollections( String... name ) {
        Set< DBCollection > collections = new HashSet<>( );
        for ( String s : name ) {
            collections.add( db.getCollection( s ) );
            this.collections.add( db.getCollection( s ) );
        }
        return collections;
    }

    /**
     * Gets Database Collection with given name and adds to
     * database cache.
     * <p>
     *
     * @param name Name of collection
     * @return Database collections
     */
    public DBCollection getCollection( String name ) {
        this.collections.add( db.getCollection( name ) );
        return db.getCollection( name );
    }

    /**
     * Gets all cached database collection.
     * <p>
     *
     * @return Set of database collection
     */
    public Set< DBCollection > getAllCachedCollection( ) {
        return this.collections;
    }

    /**
     * Gets all raw existing database collections.
     * <p>
     *
     * @return Set of database collection
     */
    public Set< String > getAllRawCollectionsName( ) {
        return db.getCollectionNames( );
    }

    /**
     * Returns the database
     * <p>
     *
     * @return Defined database
     */
    public DB getDb( ) {
        return db;
    }

    /**
     * Returns the MongoClient
     * <p>
     *
     * @return Defined MongoClient
     */
    public MongoClient getClient( ) {
        return client;
    }

}
