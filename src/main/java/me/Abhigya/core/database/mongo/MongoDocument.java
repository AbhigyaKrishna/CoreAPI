package me.Abhigya.core.database.mongo;

import com.mongodb.*;
import org.apache.commons.lang.Validate;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class for interacting with a Mongo database collection {@link DBObject docuemnts}.
 */
public class MongoDocument {

    private final MongoClient client;
    private final DBCollection collection;

    /**
     * Constructs MongoDocument.
     * <p>
     *
     * @param client     MongoClient object
     * @param collection Database collection object
     */
    public MongoDocument(MongoClient client, DBCollection collection) {
        Validate.notNull(collection, "Collection cannot be null!");

        this.client = client;
        this.collection = collection;
    }

    /**
     * Constructs MongoDocument.
     * <p>
     *
     * @param client     MongoClient object
     * @param database   Database object
     * @param collection Database collection name
     */
    public MongoDocument(MongoClient client, DB database, String collection) {
        this(client, database.getCollection(collection));
    }

    /**
     * Constructs MongoDocument.
     * <p>
     *
     * @param host       Database Host Address
     * @param port       Database Host port
     * @param collection Database collection object
     * @deprecated This initialization may conflict while storing and retrieving data.
     */
    @Deprecated
    public MongoDocument(String host, int port, DBCollection collection) {
        this(new MongoClient(host, port), collection);
    }

    /**
     * Constructs MongoDocument.
     * <p>
     *
     * @param host       Database Host Address
     * @param port       Database Host port
     * @param database   Database object
     * @param collection Database collection name
     * @deprecated This initialization may conflict while storing and retrieving data.
     */
    @Deprecated
    public MongoDocument(String host, int port, DB database, String collection) {
        this(new MongoClient(host, port), database.getCollection(collection));
    }

    /**
     * Constructs MongoDocument.
     * <p>
     *
     * @param collection Database collection object
     */
    public MongoDocument(DBCollection collection) {
        this(null, collection);
    }

    /**
     * Constructs MongoDocument.
     * <p>
     *
     * @param database   Database Object
     * @param collection Database collection name
     */
    public MongoDocument(DB database, String collection) {
        this(null, database.getCollection(collection));
    }

    /**
     * Insert new document with the given id and data.
     * <p>
     *
     * @param id    Document id
     * @param key   Key with which the specified value is to be associated
     * @param value Value to be associated with the specified key
     */
    public void insert(Object id, String key, Object value) {
        BasicDBObject document = new BasicDBObject();
        document.put("_id", id);
        document.put(key, value);
        this.collection.insert(document);
    }

    /**
     * Insert new document with the given data.
     * <p>
     *
     * @param key   Key with which the specified value is to be associated
     * @param value Value to be associated with the specified key
     */
    public void insert(String key, Object value) {
        BasicDBObject document = new BasicDBObject();
        document.put(key, value);
        this.collection.insert(document);
    }

    /**
     * Insert new document with the given id and data.
     * <p>
     *
     * @param id     Document id
     * @param values Map of keys and values
     * @see #insert(Object, String, Object)
     */
    public void insert(Object id, Map<String, Object> values) {
        BasicDBObject document = new BasicDBObject();
        document.put("_id", id);
        for (String key : values.keySet()) {
            document.put(key, values.get(key));
        }
        this.collection.insert(document);
    }

    /**
     * Insert new document with the given data.
     * <p>
     *
     * @param values Map of keys and values
     * @see #insert(String, Object)
     * @see #insert(Object, Map)
     */
    public void insert(Map<String, Object> values) {
        BasicDBObject document = new BasicDBObject();
        for (String key : values.keySet()) {
            document.put(key, values.get(key));
        }
        this.collection.insert(document);
    }

    /**
     * Find documents with the given query set and update/change the values
     * <p>
     *
     * @param queryKey   Query key to search with
     * @param queryValue Query value to search with
     * @param key        Key with which the specified value is to be associated
     * @param value      Value to be associated with the specified key
     */
    public void update(String queryKey, Object queryValue, String key, Object value) {
        BasicDBObject query = new BasicDBObject();
        query.put(queryKey, queryValue);

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put(key, value);

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", newDocument);

        collection.update(query, updateObject);
    }

    /**
     * Find documents with the given query set and update/change the values
     * <p>
     *
     * @param queryKey   Query key to search with
     * @param queryValue Query value to search with
     * @param values     Map of keys and values
     * @see #update(String, Object, String, Object)
     */
    public void update(String queryKey, Object queryValue, Map<String, Object> values) {
        BasicDBObject query = new BasicDBObject();
        query.put(queryKey, queryValue);

        BasicDBObject newDocument = new BasicDBObject();
        for (String key : values.keySet()) {
            newDocument.put(key, values.get(key));
        }

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", newDocument);

        collection.update(query, updateObject);
    }

    /**
     * Find documents with the given query set and update/change the values
     * <p>
     *
     * @param id    Query document id
     * @param key   Key with which the specified value is to be associated
     * @param value Value to be associated with the specified key
     */
    public void update(Object id, String key, Object value) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", id);

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put(key, value);

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", newDocument);

        collection.update(query, updateObject);
    }

    /**
     * Find documents with the given query set and update/change the values
     * <p>
     *
     * @param id     Query document id
     * @param values Map of keys and values
     * @see #update(Object, String, Object)
     * @see #update(String, Object, Map)
     */
    public void update(Object id, Map<String, Object> values) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", id);

        BasicDBObject newDocument = new BasicDBObject();
        for (String key : values.keySet()) {
            newDocument.put(key, values.get(key));
        }

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", newDocument);

        collection.update(query, updateObject);
    }

    /**
     * Get the set of documents with the given search query.
     * <p>
     *
     * @param queryKey   Query key to search with
     * @param queryValue Query value to search with
     * @return Set of {@link DBObject documents}
     */
    public Set<DBObject> getDocument(String queryKey, Object queryValue) {
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put(queryKey, queryValue);
        DBCursor cursor = collection.find(searchQuery);
        Set<DBObject> doc = new HashSet<>();
        if (!cursor.hasNext())
            doc.add(cursor.one());
        while (cursor.hasNext()) {
            doc.add(cursor.next());
        }
        return doc;
    }

    /**
     * Get the set of documents with the given search query.
     * <p>
     *
     * @param query Map of query keys and values
     * @return Set of {@link DBObject documents}
     * @see #getDocument(String, Object)
     */
    public Set<DBObject> getDocument(Map<String, Object> query) {
        BasicDBObject searchQuery = new BasicDBObject();
        for (String key : query.keySet()) {
            searchQuery.put(key, query.get(key));
        }
        DBCursor cursor = collection.find(searchQuery);
        Set<DBObject> doc = new HashSet<>();
        if (!cursor.hasNext())
            doc.add(cursor.one());
        while (cursor.hasNext()) {
            doc.add(cursor.next());
        }
        return doc;
    }

    /**
     * Get the document with the given document id.
     * <p>
     *
     * @param id Document id to search with
     * @return {@link DBObject Document}
     */
    public DBObject getDocument(Object id) {
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("_id", id);
        DBCursor cursor = collection.find(searchQuery);
        return cursor.one();
    }

    /**
     * Get the field with the given document id and key.
     * <p>
     *
     * @param id  Document id
     * @param key Field key to get value from
     * @return Value of the given field if found else null
     */
    public Object getField(Object id, String key) {
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("_id", id);
        DBCursor cursor = collection.find(searchQuery);
        try {
            return cursor.one().get(key);
        } catch (NullPointerException ex) {
            throw new NullPointerException("No document can be found with the id!");
        }
    }

    /**
     * Get the field with the given document and key.
     * <p>
     *
     * @param document {@link DBObject Document}
     * @param key      Field key to get value from
     * @return Value of the given field if found else null
     */
    public Object getField(DBObject document, String key) {
        return document.get(key);
    }

    /**
     * Deletes a document with the given id.
     * <p>
     *
     * @param id Document id
     */
    public void delete(Object id) {
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("_id", id);

        collection.remove(searchQuery);
    }

    /**
     * Deletes a document with the given search parameter.
     * <p>
     *
     * @param key   Key to find a document with
     * @param value Value to find a document with
     */
    public void delete(String key, Object value) {
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put(key, value);

        collection.remove(searchQuery);
    }

    /**
     * Deletes a document with the given search parameter.
     * <p>
     *
     * @param queries Map of query keys and values
     */
    public void delete(Map<String, Object> queries) {
        BasicDBObject searchQuery = new BasicDBObject();
        for (String s : queries.keySet())
            searchQuery.put(s, queries.get(s));

        collection.remove(searchQuery);
    }

}
