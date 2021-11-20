package productores;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;
import java.util.Currency;

public class Productor extends Thread {

    String nombre, producto1, producto2;
    int cantidad1, cantidad2;

    Almacen almacen;
    int almacenes = 2;   // no funciona por que no es sincrono 

    public Productor(String nombre, String producto1, int cantidad1, String producto2, int cantidad2, Almacen almacen) {

        //Almacen con el que estara sincronizado
        this.almacen = almacen;

        //datos del productor 
        this.nombre = nombre;

        this.producto1 = producto1;
        this.cantidad1 = cantidad1;

        this.producto2 = producto2;
        this.cantidad2 = cantidad2;

    }


    @Override
    public void run() {

        if (almacen.getEstado()) {
            while (almacen.disponibles > 0) {
                for (int i = 0; i < almacen.productoAlmacen.length; i++) {
                    //  inicio de la sincronizacion
                    synchronized (almacen) {
                        
                        
                        if (almacen.ocupado) {  //si el recurso esta ocupado manda al hilo a la espera
                            try {
                                almacen.wait();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        almacen.ocupado = true;
                        
                        
                        if (!almacen.getlleno(i)) {                                            // si no esta lleno 
                            if (almacen.productoAlmacen[i].producto.equals(producto1)) {      //si el nombre del produco coincide con el del array 
                                System.out.print("[ "+ nombre +" ] " );
                                almacen.llenarAlmacen(i, cantidad1);                          //se llama el metodo de llenado 
                                System.out.println("[ "+ nombre +" ]  termino de despachar" );
                            } else if (almacen.productoAlmacen[i].producto.equals(producto2)) {
                                 System.out.println("[ "+ nombre +" ]" );
                                almacen.llenarAlmacen(i, cantidad2);
                                 System.out.print("[ "+ nombre +" ] termino de despachar");
                            }
                        }
                        almacen.ocupado = false;
                        almacen.notify();
                    }  //   Fin de la sincronizacion
                }

            }
        }

        System.out.println("---------------Todos los almacenes estan llenos---------------");

    }
}
