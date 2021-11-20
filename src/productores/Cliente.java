
package productores;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;
import java.util.Currency;

public class Cliente extends Thread {
    
    String nombre;
    
    Producto[] listaproductos;
    Almacen almacen;
    
    
    boolean listacompleta = false;
    int productosfaltantes;
    int faltante;
    
    public Cliente(String nombre, Producto[] listaproductos, Almacen almacen){
        this.nombre = nombre;
        this.listaproductos = listaproductos;
        this.almacen = almacen;
        productosfaltantes = listaproductos.length;
    }
    
    
    public void comprar(int idprod, int idlista){
        
        faltante =  listaproductos[idlista].espacio - listaproductos[idlista].actual;
        
            if(almacen.productoAlmacen[idprod].actual > 0){                 //mientras exista producto en el amacen
                if((faltante) < almacen.productoAlmacen[idprod].actual){  //si lo que me falta por llenar de mi lista es menor a la cantidad del almacen
                    listaproductos[idlista].actual += faltante;  // lo tomo y se lo sumo a mi cantidad 
                    almacen.productoAlmacen[idprod].actual -= faltante;  //se lo resto a la cantidad del almacen
                } else if (faltante > almacen.productoAlmacen[idprod].actual){      //si me falta mas de lo que hay en el almacen
                    
                    listaproductos[idlista].actual += almacen.productoAlmacen[idprod].actual;       //tomo lo que pueda 
                    almacen.productoAlmacen[idprod].actual -= almacen.productoAlmacen[idprod].actual;    //se queda en 0 la cantidad del almacen;
                    
                }
            }
    }
    
    @Override
    public void run(){
    
          if (!almacen.getEstado()) {
            while (!listacompleta) {
                for (int i = 0; i < almacen.productoAlmacen.length; i++) {
                    //  inicio de la sincronizacion
                    synchronized (almacen) { // <-----[considerar cambiarlo dentro del for anidado]
                        
                        
                        if (almacen.ocupado) {  //si el recurso esta ocupado manda al hilo a la espera
                            try {
                                almacen.wait();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        almacen.ocupado = true;
                 
                        for (int j = 0; j < listaproductos.length; j++) {
                            if (!listacompleta) {                                            // si la lista no sta terminada 
                                if (almacen.productoAlmacen[i].producto.equals(listaproductos[j].producto)) {      //si el nombre del produco coincide con el del array 
                                    
                                    comprar(i,j);
                                    System.out.print("[ " + nombre + " ] ");
                                    almacen.llenarAlmacen(i, cantidad1);                          //se llama el metodo de llenado 
                                    System.out.println("[ " + nombre + " ]  termino de despachar");
                                } else if (almacen.productoAlmacen[i].producto.equals(producto2)) {
                                    System.out.println("[ " + nombre + " ]");
                                    almacen.llenarAlmacen(i, cantidad2);
                                    System.out.print("[ " + nombre + " ] termino de despachar");
                                }
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
