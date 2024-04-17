package org.example.nosqldemo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.bson.Document;

public class NoSqlController {
    private static final String DATABASE_NAME = "NoSQLDemo";
    private static final String COLLECTION_NAME = "people";
    private static final String CONNECTION_STRING = "mongodb+srv://valeriot:nDxqMlvTdN8q94rL@testcluster.yqtxiqf.mongodb.net/?retryWrites=true&w=majority&appName=Testcluster";

    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField cityField;
    @FXML
    private Button addButton;
    @FXML
    private Button readButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Label feedbackLabel;

    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    private PauseTransition pause;

    public void initialize() {
        client = MongoDbConnection.getConnection(CONNECTION_STRING);
        database = MongoDbConnection.getDatabase(client, DATABASE_NAME);
        collection = MongoDbConnection.getCollection(database, COLLECTION_NAME);
        pause = new PauseTransition(javafx.util.Duration.seconds(5));
        pause.setOnFinished(e -> feedbackLabel.setText(""));
    }

    public void closeConnection() {
        MongoDbConnection.closeConnection(client);
    }

    public void showFeedback(String message) {
        pause.stop();
        feedbackLabel.setText(message);
        pause.playFromStart();

    }

    public void clearFields() {
        idField.clear();
        nameField.clear();
        ageField.clear();
        cityField.clear();
    }

    @FXML
    protected void onAddButtonClick() {
        String id = idField.getText();
        String name = nameField.getText();
        String age = ageField.getText();
        String city = cityField.getText();

        if (id.isEmpty() || name.isEmpty() || age.isEmpty() || city.isEmpty()) {
            showFeedback("All fields must be filled");
            return;
        }

        try {
            Document document = new Document("id", id)
                    .append("name", name)
                    .append("age", Integer.parseInt(age))
                    .append("city", city);
            boolean isInserted = MongoDbConnection.insertDocument(collection, document);
            if (isInserted) {
                showFeedback("Document added successfully");
                clearFields();
            } else {
                showFeedback("Failed to add document");
            }
        } catch (NumberFormatException e) {
            showFeedback("Age must be a number");
        }
    }

    @FXML
    protected void onReadButtonClick() {
        String id = idField.getText();

        if (id.isEmpty()) {
            showFeedback("ID field must be filled");
            return;
        }
        try {
            Document document = MongoDbConnection.readDocument(collection, id);
            feedbackLabel.setText(document.toJson());
        } catch (Exception e) {
            showFeedback("Document not found");
        }
    }

    @FXML
    protected void onUpdateButtonClick() {
        String id = idField.getText();
        String name = nameField.getText();
        String age = ageField.getText();
        String city = cityField.getText();

        if (id.isEmpty() || name.isEmpty() || age.isEmpty() || city.isEmpty()) {
            feedbackLabel.setText("All fields must be filled");
            return;
        }

        try {
            Document document = new Document("name", name)
                    .append("age", Integer.parseInt(age))
                    .append("city", city);
            boolean isUpdated = MongoDbConnection.updateDocument(collection, id, document);
            if (isUpdated) {
                showFeedback("Document updated successfully");
                clearFields();
            } else {
                showFeedback("Document not found");
            }
        } catch (NumberFormatException e) {
            showFeedback("Age must be a number");
        }
    }

    @FXML
    protected void onDeleteButtonClick() {
        String id = idField.getText();

        if (id.isEmpty()) {
            feedbackLabel.setText("ID field must be filled");
            return;
        }

        try {
            MongoDbConnection.deleteDocument(collection, id);
            showFeedback("Document deleted successfully");
        } catch (Exception e) {
            feedbackLabel.setText("Document not found");
        }
    }
}