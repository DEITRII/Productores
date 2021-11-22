package productores;

public class Productores {

    public static void main(String[] args) {

        //arreglo de objetos del almacen
        Producto[] array;
        
        array = new Producto[3];
        array[0] = new Producto("maiz", 15);
        array[1] = new Producto("frijol", 10);
        array[2] = new Producto("trigo", 12);

        Almacen almacen = new Almacen(array);
        
        //arreglo de objetos de clientes
        Producto[] listaC1;
        listaC1 = new Producto[2];                             //se crea un nuevo arreglo de productos para el cliente
        listaC1[0] = new Producto("maiz", 20);                 //se agregan productos al arreglo
        listaC1[1] = new Producto("trigo", 8);
                
        Cliente c1 = new Cliente ("Pablo",listaC1, almacen);   //se pasa el arreglo (como al alamacen) y el almacen para que utilize sus datos
        
        
        Producto[] listaC2;
        listaC2 = new Producto[2];                             
        listaC2[0] = new Producto("maiz", 15);                
        listaC2[1] = new Producto("frijol", 5); 
        
        Cliente c2 = new Cliente ("hiram",listaC2, almacen); 
        
        Producto[] listaC3;
        listaC3 = new Producto[1];                             
        listaC3[0] = new Producto("frijol", 4);   
        
        Cliente c3 = new Cliente ("Azael",listaC3, almacen); 
        
        Producto[] listaC4;
        listaC4 = new Producto[3];                             
        listaC4[0] = new Producto("maiz", 2);                
        listaC4[1] = new Producto("trigo", 7); 
        listaC4[2] = new Producto("frijol", 6);
        
        Cliente c4 = new Cliente ("Erick",listaC4, almacen); 
        
        
        /*pruebas uno = new pruebas(almacen);
       uno.mostrar();*/
        
        
        Productor p1 = new Productor("Juan", "maiz", 5, "frijol", 4, almacen);
        Productor p2 = new Productor("Kath", "frijol", 4, "trigo", 3, almacen);
        Productor p3 = new Productor("Valdo", "trigo", 3, "maiz", 5, almacen);
        
        
        c1.start();
        c2.start();
        c3.start();
        c4.start();
        
        
        p1.start();
        p2.start();
        p3.start();
        
        while (c1.isAlive() || c2.isAlive() || c3.isAlive() || c4.isAlive() ){
            //mientras los hilos esten vivos 
            
        }
        
         System.out.println(" Todos los clientes satisfechos  ");
    }
}
