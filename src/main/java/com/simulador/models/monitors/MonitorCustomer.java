package com.simulador.models.monitors;

import com.simulador.entities.Customer;

import java.util.Arrays;

public class MonitorCustomer {
    private final int CAPACIDAD = 10;
    private final CustomerRequest[] buffer;
    private int lleno;
    private int indiceInsercion;
    private int indiceExtraccion;

    public synchronized boolean hasWaitingCustomers() {
        return lleno > 0;
    }


    public static class CustomerRequest {
        public final Customer customer;
        public final int tableNumber;
        public final long arrivalTime;

        public CustomerRequest(Customer customer, int tableNumber) {
            this.customer = customer;
            this.tableNumber = tableNumber;
            this.arrivalTime = System.currentTimeMillis();
        }

        @Override
        public String toString() {
            return "CustomerRequest{" +
                    "customer=" + customer +
                    ", tableNumber=" + tableNumber +
                    ", arrivalTime=" + arrivalTime +
                    '}';
        }
    }

    public MonitorCustomer() {
        buffer = new CustomerRequest[CAPACIDAD];
        lleno = 0;
        indiceInsercion = 0;
        indiceExtraccion = 0;
    }

    @Override
    public String toString() {
        return "ComensalesMonitor{" +
                "buffer=" + Arrays.toString(buffer) +
                '}';
    }

    public synchronized void addCustomer(Customer customer, int tableNumber) {
        while (lleno == CAPACIDAD) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        buffer[indiceInsercion] = new CustomerRequest(customer, tableNumber);
        indiceInsercion = (indiceInsercion + 1) % CAPACIDAD;
        lleno++;
        System.out.println(Thread.currentThread().getName() + " - Añadido: " + this.toString());

        this.notify();
    }

    public synchronized CustomerRequest getNextCustomer() {
        while (lleno == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        CustomerRequest request = buffer[indiceExtraccion];
        buffer[indiceExtraccion] = null;
        indiceExtraccion = (indiceExtraccion + 1) % CAPACIDAD;
        lleno--;
        System.out.println(Thread.currentThread().getName() + " - Extraído: " + this.toString());

        this.notify();
        return request;
    }
}
