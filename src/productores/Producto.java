package productores;

public class Producto {

    String producto;
    int espacio;
    int actual = 0 ;
    

    static int disponibles = 3;

    public Producto(String producto, int espacio) {
        this.producto = producto;
        this.espacio = espacio;
    }

    //Metodos getters
    public String getProducto() {
        return producto;
    }

    public int getCantidad() {
        return espacio;
    }
    
    public int canActual() {
        return espacio;
    }
}
