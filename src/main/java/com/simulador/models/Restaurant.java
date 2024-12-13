package com.simulador.models;

import com.simulador.Observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    public static final int TOTAL_TABLES = 20;
    private final boolean[] tables;
    private final Object lock; // Objeto para sincronización
    private final List<Observer> observers;

    public Restaurant() {
        tables = new boolean[TOTAL_TABLES];
        lock = new Object();
        observers = new ArrayList<>();
    }

    public void addObserver(Observer observer) {
        synchronized (lock) {
            observers.add(observer);
        }
    }

    public void removeObserver(Observer observer) {
        synchronized (lock) {
            observers.remove(observer);
        }
    }

    private void notifyObservers() {
        synchronized (lock) {
            for (Observer observer : observers) {
                observer.onTableAvailable();
            }
        }
    }

    public int findAvailableTable() {
        synchronized (lock) {
            for (int i = 0; i < tables.length; i++) {
                if (!tables[i]) {
                    return i;
                }
            }
            return -1;
        }
    }

    public void occupyTable(int tableNumber) {
        synchronized (lock) {
            tables[tableNumber] = true;
        }
    }

    public void releaseTable(int tableNumber) {
        synchronized (lock) {
            tables[tableNumber] = false;
            notifyObservers(); // Notifica cuando una mesa se libera
            lock.notifyAll();  // Notifica a los hilos en espera
        }
    }

    public void waitForAvailableTable() throws InterruptedException {
        synchronized (lock) {
            while (findAvailableTable() == -1) {
                lock.wait(); // Espera hasta que una mesa esté disponible
            }
        }
    }
}