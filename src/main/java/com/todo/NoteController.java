package com.todo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class NoteController {
    @FXML
    private AnchorPane childVbox;

    @FXML
    private TextField taskField;

    @FXML
    private ListView<HBox> taskList;

    private NoteService noteService;

    public NoteController() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            noteService = (NoteService) registry.lookup("NoteService");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() throws RemoteException {
        // Fetch notes from the NoteService and populate the taskList
        try {
            List<Note> notes = noteService.getAllTasks();
            for (Note note : notes) {
                int taskId = note.getId(); // Get the task ID from the Note object
                String noteText = note.getNote(); // Get the note text from the Note object
                Boolean taskChecked = note.isCompleted(); // Get the note text from the Note object
                HBox taskItem = createTaskItem(noteText, taskId, taskChecked);
                taskList.getItems().add(taskItem);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addTask() {
        String task = taskField.getText();
        if (!task.isEmpty()) {
            try {
                int newTaskId = noteService.createNote(task); // Save task to the database and get the new ID
                HBox taskItem = createTaskItem(task, newTaskId, false); // Pass the new ID to createTaskItem
                taskList.getItems().add(taskItem); // Add task to the list view
                taskField.clear(); // Clear the task field
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void deleteTask() {
        int selectedIndex = taskList.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            HBox taskItem = taskList.getItems().remove(selectedIndex);
            HBox innerHBox = (HBox) taskItem.getChildren().get(0);
            Label ID = (Label) innerHBox.getChildren().get(2); // Get the ID label

            try {
                int taskId = Integer.parseInt(ID.getText()); // Parse the ID as an integer
                noteService.deleteNoteById(taskId); // Pass the ID to the service
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void updateTask() {
        int selectedIndex = taskList.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            
            HBox taskItem = taskList.getItems().get(selectedIndex);
            HBox innerHBox = (HBox) taskItem.getChildren().get(0);
            CheckBox checkBox = (CheckBox) innerHBox.getChildren().get(0);
            Label taskLabel = (Label) innerHBox.getChildren().get(1);
            Label ID = (Label) innerHBox.getChildren().get(2); // Get the ID label

            String updatedTask = taskField.getText();
            try {
                if (updatedTask.isEmpty()) {
                    updatedTask = taskLabel.getText();
                }

                int taskId = Integer.parseInt(ID.getText()); // Parse the ID as an integer
                boolean isCompleted = checkBox.isSelected();
                noteService.updateNoteById(taskId, updatedTask, isCompleted); // Pass the ID, updated task, and
                                                                              // isCompleted to the service
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            checkBox.setSelected(checkBox.isSelected());
            taskLabel.setText(updatedTask);
            taskField.clear();
        }
    }

    private HBox createTaskItem(String task, int id, boolean taskChecked) {
        CheckBox checkBox = new CheckBox();
        Label taskLabel = new Label(task);
        Label ID = new Label(String.valueOf(id)); // Convert the ID to a String
        ID.setVisible(false);
        taskLabel.getStyleClass().add("label");
        Button deleteButton = new Button("Delete");
        Button updateButton = new Button("Update");

        HBox hBox = new HBox(checkBox, taskLabel, ID);
        hBox.getStyleClass().add("vBox");
        hBox.setPrefSize(280, 10);
        hBox.setSpacing(5);

        deleteButton.setOnAction(event -> deleteTask());
        updateButton.setOnAction(event -> updateTask());

        // Rest of the code remains the same
        checkBox.setOnAction(event -> {
            if (checkBox.isSelected()) {
                taskLabel.setStyle("-fx-underline: true;");
            } else {
                taskLabel.setStyle("-fx-underline: false;");
            }
        });

        if (taskChecked) {
            checkBox.setSelected(true);
            taskLabel.setStyle("-fx-underline: true;");
        } else {
            checkBox.setSelected(false);
            taskLabel.setStyle("-fx-underline: false;");
        }

        HBox taskItem = new HBox(hBox, updateButton, deleteButton);
        taskItem.getStyleClass().add("task-item");
        taskItem.setSpacing(5);

        // Apply inline CSS to HBox
        taskItem.setStyle(
                " -fx-padding: 13px; -fx-margin-top: 10px; -fx-margin-bottom: 10px;");

        Image deleteImage = new Image(getClass().getResourceAsStream("delete.png"));
        ImageView deleteIcon = new ImageView(deleteImage);
        deleteIcon.setFitWidth(22);
        deleteIcon.setFitHeight(22);
        deleteButton.setGraphic(deleteIcon);

        Image updateImage = new Image(getClass().getResourceAsStream("update.png"));
        ImageView updateIcon = new ImageView(updateImage);
        updateIcon.setFitWidth(22);
        updateIcon.setFitHeight(22);
        updateButton.setGraphic(updateIcon);

        // Apply inline CSS styles to make the buttons circular
        deleteButton.setStyle(
                "-fx-background-color: transparent; -fx-background-radius: 100%; -fx-padding: 0; -fx-graphic-text-gap: 0;");
        updateButton.setStyle(
                "-fx-background-color: transparent; -fx-background-radius: 100%; -fx-padding: 0; -fx-graphic-text-gap: 0;");

        checkBox.setStyle("-fx-font-size: 16px;");
        checkBox.getStyleClass().add("check-box");

        // Position buttons on the right end of HBox
        HBox.setHgrow(taskLabel, Priority.ALWAYS);

        return taskItem;
    }
}