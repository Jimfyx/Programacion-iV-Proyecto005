package com.thread.demo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Consumidor implements Runnable{
    private  final  BufferCompartido buffer;
    private final String idConsumidor;
    private PrintWriter impresora;
    private final String rutaArchivo;

    private static final Lock lock = new ReentrantLock();

    public Consumidor(BufferCompartido buffer,String rutaArchivo, String idConsumidor) throws IOException {
        this.buffer = buffer;
        this.idConsumidor = idConsumidor;
        this.rutaArchivo = rutaArchivo;
        this.impresora = new PrintWriter(new FileWriter(rutaArchivo,true));
    }

    @Override
    public void run() {
        try {
            while (true){
                String mensaje = buffer.consumir();

                if(mensaje.startsWith("End")){
                    System.out.println("Finalizado");
                    break;
                }
                lock.lock();
                try {
                    String[] partes = mensaje.split(",",3);
                    if(partes.length == 3){
                        String nombreCompleto = partes[0] + " " + partes[1];
                        String idProductor = partes[2];
                        String salida = nombreCompleto + "," + idProductor + "," + idConsumidor;
                        impresora.write(salida + "\n");
                        impresora.flush();
                        System.out.println("Consumido por: " + idConsumidor + ":" + salida);

                    }
                }finally{
                    lock.unlock();
                }
            }
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }finally {
            impresora.close();
        }
    }
}
