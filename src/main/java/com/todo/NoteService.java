package com.todo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface NoteService extends Remote {
    int createNote(String note) throws RemoteException;
    List<Note> getAllTasks() throws RemoteException;
    void updateNoteById(int id, String updatedNote, Boolean isCompleted) throws RemoteException;
    void deleteNoteById(int id) throws RemoteException;
}