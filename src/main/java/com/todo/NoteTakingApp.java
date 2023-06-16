package com.todo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NoteTakingApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("To-Do");
        primaryStage.setResizable(false); // Disable window resizing
        primaryStage.sizeToScene(); // Fit the window size to the content
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
