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

    public Cliente(String nombre, Producto[] listaproductos, Almacen almacen) {
        this.nombre = nombre;
        this.listaproductos = listaproductos;
        this.almacen = almacen;
        productosfaltantes = listaproductos.length;
    }

    
    public void limiteLista(){
    
        productosfaltantes = listaproductos.length;

        for (int x = 0; x < listaproductos.length; x++) {
             
            if (listaproductos[x].actual == listaproductos[x].espacio) {
                productosfaltantes -= 1;
            }
        }
        //System.out.println("{{"+ nombre + "}}  productos faltantes " + productosfaltantes + " --");
    }
    
    public void comprar(int idprod, int idlista) {

        faltante = listaproductos[idlista].espacio - listaproductos[idlista].actual;

        //System.out.println(" DENTRO DEL METODO comprar() -- iteracion No. " + idlista + " --");
        
        if (almacen.productoAlmacen[idprod].actual > 0) {       //mientras exista producto en el amacen
            
            System.out.println(" Almacen de "+ almacen.productoAlmacen[idprod].producto +" cantidad [" + almacen.productoAlmacen[idprod].actual +"/" + almacen.productoAlmacen[idprod].espacio + "]");
            System.out.println(" Compras de " + listaproductos[idlista].producto + " cantidad [" + listaproductos[idlista].actual +"/" + listaproductos[idlista].espacio + "]" + " Faltante = " + faltante);
            
            if ((faltante) < almacen.productoAlmacen[idprod].actual) {  //si lo que me falta por llenar de mi lista es menor a la cantidad del almacen
                listaproductos[idlista].actual += faltante;  // lo tomo y se lo sumo a mi cantidad 
                almacen.productoAlmacen[idprod].actual -= faltante;  //se lo resto a la cantidad del almacen

                System.out.println(nombre + " compro " + faltante + " toneladas de " + listaproductos[idlista].producto);

            } else if (faltante >= almacen.productoAlmacen[idprod].actual) {      //si me falta mas de lo que hay en el almacen

                
                System.out.println(nombre + " compro " + almacen.productoAlmacen[idprod].actual + " toneladas de " + listaproductos[idlista].producto);
                
                listaproductos[idlista].actual += almacen.productoAlmacen[idprod].actual;       //tomo lo que pueda 
                almacen.productoAlmacen[idprod].actual -= almacen.productoAlmacen[idprod].actual;    //se queda en 0 la cantidad del almacen;

                System.out.println(" Almacen de " + almacen.productoAlmacen[idprod].producto + " cantidad [" + almacen.productoAlmacen[idprod].actual + "/" + almacen.productoAlmacen[idprod].espacio + "]");
                System.out.println(" Compras de " + listaproductos[idlista].producto + " cantidad [" + listaproductos[idlista].actual + "/" + listaproductos[idlista].espacio + "]" );

                System.out.println(" ```````` YA NO QUEDA MAS " + almacen.productoAlmacen[idprod].producto + " EN EL ALMACEN`````````` ");

            
                Almacen.estado = true;
                Almacen.vacio = true;
            }
            
           
            Almacen.limite();

                
        } else {
            System.out.println(" DENTRO DEL IF PARA COMPROBAR CONVERTIR estado = true");
            Almacen.estado = true;
        }
    }

    public boolean getlleno(int id) {
        if (listaproductos[id].actual == listaproductos[id].espacio) { //si la acntidad de roducto que tengo es igual a lo que queria 
            listacompleta = true;
        } else if (listaproductos[id].actual < listaproductos[id].espacio) {    //si aun me faltan productos
            listacompleta = false;
        }
        return listacompleta;
    }
   

    @Override
    public void run() {

        while (productosfaltantes > 0) {           //mientras mi lista no este completa
           
            try {
          
            sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            limiteLista();
            
            //System.out.println(" estado " + almacen.estado);
            if (!almacen.estado) {
                    //System.out.println(" estado:  " + almacen.estado);
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
                }


                try {

                    for (int i = 0; i < almacen.productoAlmacen.length; i++) {   //por cada producto del almacen 

                        //System.out.println(" dentro del for grande  ");
                        synchronized (almacen) {

                            
                            if (almacen.ocupado) {  //si el recurso esta ocupado manda al hilo a la espera
                                try {
                                    //System.out.println(" Antes del wait ");
                                    almacen.wait();
                                    //System.out.println(" despues del wait ");
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                            almacen.ocupado = true;
                                    
                            //System.out.println(" despues del ocupado  ");
                             
                             
                            for (int j = 0; j < listaproductos.length; j++) {      //por cada producto de mi lista

                                if (Almacen.estado == true ){
                                    break;
                                }
                                if (getlleno(j) == false && almacen.productoAlmacen[i].actual > 0) {    // si la lista no Esta terminada 

                                    if (almacen.productoAlmacen[i].producto.equals(listaproductos[j].producto)) { //si el nombre del produco coincide con el del array 
                                        System.out.print("[ " + nombre + " ] ");
                                        comprar(i, j);  //se llama el metodo de compra
                                         //System.out.println(" Al final de compras ");
                                    }

                                    if (getlleno(j)) {                //si al terminar de comprar esta lleno
                                       // productosfaltantes -= 1;
                                    }
                                    
                                    //System.out.println(" Al final del if para saber si algo esta lleno ");
                                }
                                
                                //System.out.println(" Al final del for ");
                            }
                            
                            

                            //System.out.println(" Arriba del notify ");
                            almacen.ocupado = false;
                            almacen.notify();
                            
                            if (Almacen.estado == true) {
                                break;
                            }
                        }

                    }
                    sleep(500);

                } catch (InterruptedException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
        
        System.out.println("*******************************" + nombre +" murio --***************************************");
    }
}
