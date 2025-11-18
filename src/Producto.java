public class Producto{
    private double precio;
    private String nombre;
    private String id;
    private String descripcion;
    private int inventario;
    private int tiempoEntrega;
    private String fechaReab;

    public Producto(double precio, String nombre, String id, String descripcion, int inventario, int tiempoEntrega, String fechaReab) {
        this.precio = precio;
        this.nombre = nombre;
        this.id = id;
        this.descripcion = descripcion;
        this.inventario = inventario;
        this.tiempoEntrega = tiempoEntrega;
        this.fechaReab = fechaReab;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "precio=" + precio +
                ", nombre='" + nombre + '\'' +
                ", id='" + id + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", inventario=" + inventario +
                ", tiempoEntrega=" + tiempoEntrega +
                ", fechaReab='" + fechaReab + '\'' +
                '}';
    }

    /**Metodos de java*/

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getInventario() {
        return inventario;
    }

    public void setInventario(int inventario) {
        this.inventario = inventario;
    }

    public int getTiempoEntrega() {
        return tiempoEntrega;
    }

    public void setTiempoEntrega(int tiempoEntrega) {
        this.tiempoEntrega = tiempoEntrega;
    }

    public String getFechaReab() {
        return fechaReab;
    }

    public void setFechaReab(String fechaReab) {
        this.fechaReab = fechaReab;
    }
}