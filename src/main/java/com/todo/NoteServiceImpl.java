package com.todo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NoteServiceImpl extends UnicastRemoteObject implements NoteService {
    private Connection connection;

    public NoteServiceImpl() throws RemoteException {
        try {
            // Establish database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo_app", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
public int createNote(String note) throws RemoteException {
    int newTaskId = -1; // Default value in case of an error
    try {
        // Insert note into the database
        PreparedStatement statement = connection.prepareStatement("INSERT INTO notes (note) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, note);
        statement.executeUpdate();
        
        // Retrieve the generated task ID
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            newTaskId = generatedKeys.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return newTaskId;
}


    @Override
    public List<Note> getAllTasks() throws RemoteException {
        List<Note> noteList = new ArrayList<>();
        try {
            // Retrieve all notes from the database
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM notes");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String note = resultSet.getString("note");
                Boolean isCompleted = resultSet.getBoolean("isCompleted");
                Note noteObject = new Note(note, id, isCompleted);
                noteList.add(noteObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return noteList;
    }

    @Override
    public void updateNoteById(int id, String updatedNote, Boolean isCompleted) throws RemoteException {
        try { 
            // Update note in the database
            PreparedStatement statement = connection.prepareStatement("UPDATE notes SET note = ?, isCompleted = ? WHERE id = ?");
            statement.setString(1, updatedNote);
            statement.setBoolean(2, isCompleted);
            statement.setInt(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteNoteById(int id) throws RemoteException {
        try {
            // Delete note from the database
            PreparedStatement statement = connection.prepareStatement("DELETE FROM notes WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
