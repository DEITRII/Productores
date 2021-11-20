package productores;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Almacen {

    Producto[] productoAlmacen;
    int disponibles;

    boolean lleno = false; //valor para indicar cuando este lleno un almacen, esto se resta de [almacenes] en la clase productor 
    boolean ocupado = false;    //lo us aun hilo para avisar que esta usando el recurso
    boolean estado = true; //este valor permite saber si lo va a ocupar un producor(true) o un cliente(false)

    public Almacen(Producto[] productoAlmacen) {
        this.productoAlmacen = productoAlmacen;
        disponibles = productoAlmacen.length;    //nos dice cuantos almacenes aun no esatn llenos (igual a la cantidad de elementos del arreglo)
    }

    public boolean getEstado() {
        if (disponibles == 0) {
            estado = false;
        } else {
            estado = true;
        }

        return estado;

    }

    
    public boolean getlleno(int id) {
        if (productoAlmacen[id].actual ==  productoAlmacen[id].espacio) {
            lleno = true;
        } else if (productoAlmacen[id].actual <  productoAlmacen[id].espacio){
            lleno = false;
        } else {
             System.out.println("! ! ! ! ! ! ERROR EN Almacen.getlleno() ! ! ! ! ! ! " );
        }
        return lleno;
    }


    //Llenado del almacen 
    public void llenarAlmacen(int id, int deposito)     { //con el id sabemos a que elemeto del array se llama, y asi podemos restar el valor que recibimos del productor
        
        
        if (productoAlmacen[id].actual < productoAlmacen[id].espacio) {   //mientras haya espacio libre

            if ((productoAlmacen[id].espacio - productoAlmacen[id].actual) > deposito) {          //si el deposito es menor al espacio disponible 
                productoAlmacen[id].actual += deposito;
                System.out.println("Ha depositado " + deposito + " toneladas de " + productoAlmacen[id].producto);
                
                try {
                    Thread. sleep(1000) ;
                } catch (InterruptedException ex) {
                    Logger.getLogger(Almacen.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } else if (productoAlmacen[id].actual + deposito >= productoAlmacen[id].espacio) {            //cuando el deposito sea mayor o igual 

                productoAlmacen[id].actual += deposito;
                
                System.out.println("Ha depositado " + deposito + " toneladas de " + productoAlmacen[id].producto);
                System.out.println("> > > El almacen de " + productoAlmacen[id].producto + " se lleno < < <");  //mensaje de lleno

                //si el espacio mas la cantidad depositada es mayor al tope se devuelve el excedente y se convierte [actual] en [tope]
                if (productoAlmacen[id].actual > productoAlmacen[id].espacio) {
                    System.out.println("Se regresaron " + (productoAlmacen[id].actual - productoAlmacen[id].espacio) + " Toneladas de  " + productoAlmacen[id].producto);
                }
                
                disponibles -= 1;       //quitamos un almacen de los siponibles para ser llenados 
                productoAlmacen[id].actual = productoAlmacen[id].espacio;  //ponemos el valor [actual] igual al maximo [tope]
                
                  try {
                    Thread. sleep(1000) ;
                } catch (InterruptedException ex) {
                    Logger.getLogger(Almacen.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        } else {
            System.out.println(">>El almacen de [" + productoAlmacen[id].producto + "] esta lleno ");  //mensaje de lleno
        }
    }

}
