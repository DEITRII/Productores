package productores;

public class pruebas {

    Almacen prueba;

    public pruebas(Almacen prueba) {
        this.prueba = prueba;
    }

    public void mostrar() {
        for (int i = 0; i < prueba.productoAlmacen.length; i++) {
            System.out.println("Revision de almacenes desde pruebas [" + prueba.productoAlmacen[i].producto + "] espacio: [" + prueba.productoAlmacen[i].espacio + "]");
        }

    }
}
