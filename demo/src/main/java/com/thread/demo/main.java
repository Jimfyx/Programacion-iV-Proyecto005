package com.thread.demo;

import java.io.IOException;

public class main {
    public static void main(String[] args) {
        BufferCompartido buffer = new BufferCompartido(10);

        String rutaArchivoSalida = "E:/Proyectos/webdev/Programacion-iV/Proyecto005/demo/src/main/resources/salida.txt";

        Thread productor1 = new Thread(new Productor(buffer, "input1.txt", "productor1"));
        Thread productor2 = new Thread(new Productor(buffer, "input2.txt", "productor2"));
        Thread productor3 = new Thread(new Productor(buffer, "input3.txt", "productor3"));

        productor1.start();
        productor2.start();
        productor3.start();

        Thread consumidor1 = null;
        Thread consumidor2 = null;
        Thread consumidor3 = null;

        try {
            consumidor1 = new Thread(new Consumidor(buffer,rutaArchivoSalida, "consumidor 1"));
            consumidor2 = new Thread(new Consumidor(buffer,rutaArchivoSalida, "consumidor 2"));
            consumidor3 = new Thread(new Consumidor(buffer,rutaArchivoSalida, "consumidor 3"));

        }catch (IOException e){
            e.printStackTrace();
        }

        if (consumidor1 != null) {
            consumidor1.start();
        }
        if (consumidor2 != null) {
            consumidor2.start();
        }
        if (consumidor3 != null) {
            consumidor3.start();
        }

        try {
            productor1.join();
            productor2.join();
            productor3.join();

            if (consumidor1 != null) {
                consumidor1.join();
            }
            if (consumidor2 != null) {
                consumidor2.join();
            }
            if (consumidor3 != null) {
                consumidor3.join();
            }

        }catch (InterruptedException ex){
            Thread.currentThread().interrupt();
        }

    }
}
