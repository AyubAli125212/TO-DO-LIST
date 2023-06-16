package com.todo;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class NoteServer {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            NoteService noteService = new NoteServiceImpl(); // Implement NoteService interface
            registry.rebind("NoteService", noteService);
            System.out.println("Server is running...");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
