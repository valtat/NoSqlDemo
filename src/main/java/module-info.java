module org.example.nosqldemo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.mongodb.bson;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires java.desktop;

    opens org.example.nosqldemo to javafx.fxml;
    exports org.example.nosqldemo;
}