package org.example.nosqldemo;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDbConnection {
    /*public static void main(String[] args) {
        String uri = "mongodb+srv://valeriot:nDxqMlvTdN8q94rL@testcluster.yqtxiqf.mongodb.net/?retryWrites=true&w=majority&appName=Testcluster";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("NoSQLDemo");
            MongoCollection<Document> collection = database.getCollection("people");
            System.out.println("Connected to the database");
        } catch (Exception e) {
            System.out.println("An error occurred.");
        }
    } */

    public static MongoClient getConnection(String connectionString)  {
        return MongoClients.create(connectionString);
    }

    public static MongoDatabase getDatabase(MongoClient client, String databaseName) {
        return client.getDatabase("NoSQLDemo");
    }

    public static MongoCollection<Document> getCollection(MongoDatabase database, String collectionName) {
        return database.getCollection(collectionName);
    }

    public static boolean insertDocument(MongoCollection<Document> collection, Document document)  {
        try {
            collection.insertOne(document);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean updateDocument(MongoCollection<Document> collection, String id, Document document) {
        try {
            UpdateResult result = collection.updateOne(eq("id", id), new Document("$set", document));
            return result.getModifiedCount() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static void deleteDocument(MongoCollection<Document> collection, String id) {
        collection.deleteOne(eq("id", id));
    }

    public static Document readDocument(MongoCollection<Document> collection, String id) {
        Document document = collection.find(eq("id", id)).first();
        System.out.println(document.toJson());
        return document;
    }

    public static void closeConnection(MongoClient client) {
        client.close();
    }


}
