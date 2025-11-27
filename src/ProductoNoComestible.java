public class ProductoNoComestible extends Producto {

    private String material;
    private boolean almacenamientoEspecial;

    public ProductoNoComestible(double precio, String nombre, String id, String descripcion, int inventario, int tiempoEntrega, String fechaReab, String material, boolean almacenamientoEspecial) {
        super(precio, nombre, id, descripcion, inventario, tiempoEntrega, fechaReab);
        this.material = material;
        this.almacenamientoEspecial = almacenamientoEspecial;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public boolean isAlmacenamientoEspecial() {
        return almacenamientoEspecial;
    }

    public void setAlmacenamientoEspecial(boolean almacenamientoEspecial) {
        this.almacenamientoEspecial = almacenamientoEspecial;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\n\nProducto No comestible "
                + "\nMaterial: " + material + " | Almacenamiento Especial: " + (almacenamientoEspecial ? "Sí" : "No");
    }
}
