package productores;

public class Productores {

    public static void main(String[] args) {

        Producto[] array;
        //arreglo de objetos

        array = new Producto[3];
        array[0] = new Producto("maiz", 15);
        array[1] = new Producto("frijol", 10);
        array[2] = new Producto("trigo", 12);

        Almacen almacen = new Almacen(array);

        /*pruebas uno = new pruebas(almacen);
        
        
        listaC1 = new Producto[1];                          //se crea un nuevo arreglo de productos para el cliente
        productosc1[0] = new Producto("maiz", 15);          //se agregan productos al arreglo
        
        Cliente c1 = new Cliente ("Pablo",listaC1, almacen);        //se pasa el arreglo (como al alamacen) y el almacen para que utilize sus datos
       
       uno.mostrar();*/
        Productor p1 = new Productor("Juan", "maiz", 5, "frijol", 4, almacen);
        Productor p2 = new Productor("Kath", "frijol", 4, "trigo", 3, almacen);
        Productor p3 = new Productor("Valdo", "trigo", 3, "maiz", 5, almacen);
        
        
        
        
        
        p1.start();
        p2.start();
        p3.start();
    }
}
