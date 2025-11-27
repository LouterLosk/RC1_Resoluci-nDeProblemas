public class ProductoComestible extends Producto {

    private String fechaCaducidad;
    private boolean requiereRefrigeracion;

    public ProductoComestible(double precio, String nombre, String id, String descripcion, int inventario, int tiempoEntrega, String fechaReab, String fechaCaducidad, boolean requiereRefrigeracion) {
        super(precio, nombre, id, descripcion, inventario, tiempoEntrega, fechaReab);
        this.fechaCaducidad = fechaCaducidad;
        this.requiereRefrigeracion = requiereRefrigeracion;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public boolean isRequiereRefrigeracion() {
        return requiereRefrigeracion;
    }

    public void setRequiereRefrigeracion(boolean requiereRefrigeracion) {
        this.requiereRefrigeracion = requiereRefrigeracion;
    }

    public String toString() {
        return super.toString() +
                "\n\nProducto comestible" +
                "\nCaduca: " + fechaCaducidad + " | Refrigeración: " + (requiereRefrigeracion ? "Sí" : "No");
    }






}
