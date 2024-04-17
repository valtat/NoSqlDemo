package org.example.nosqldemo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import javax.swing.text.Document;

import static com.mongodb.client.model.Filters.eq;

public class CRUDOperations {
    private static final String DATABASE_NAME = "NoSQLDemo";
    private static final String COLLECTION_NAME = "people";

    public static void insertDocument(MongoClient client, Document document) {
        MongoDatabase database = MongoDbConnection.getDatabase(client, DATABASE_NAME);
        MongoCollection<org.bson.Document> collection = MongoDbConnection.getCollection(database, COLLECTION_NAME);
        MongoDbConnection.insertDocument(collection, (org.bson.Document) document);
    }

    public static void updateDocument(MongoClient client, String id, Document document) {
        MongoDatabase database = MongoDbConnection.getDatabase(client, DATABASE_NAME);
        MongoCollection<org.bson.Document> collection = MongoDbConnection.getCollection(database, COLLECTION_NAME);
        MongoDbConnection.updateDocument(collection, id, (org.bson.Document) document);
    }

    public static void deleteDocument(MongoClient client, String id) {
        MongoDatabase database = MongoDbConnection.getDatabase(client, DATABASE_NAME);
        MongoCollection<org.bson.Document> collection = MongoDbConnection.getCollection(database, COLLECTION_NAME);
        MongoDbConnection.deleteDocument(collection, id);
    }

    public static void readDocument(MongoClient client, String id) {
        MongoDatabase database = MongoDbConnection.getDatabase(client, DATABASE_NAME);
        MongoCollection<org.bson.Document> collection = MongoDbConnection.getCollection(database, COLLECTION_NAME);
        MongoDbConnection.readDocument(collection, id);
    }

    public static void closeConnection(MongoClient client) {
        MongoDbConnection.closeConnection(client);
    }


}
