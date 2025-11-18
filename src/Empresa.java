import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Empresa {
    private Double presupuesto;
    private int espacioAlmacenamiento;
    private ArrayList<Producto> producto;
    Scanner sc = new Scanner(System.in);

    public Empresa() {
        producto = new ArrayList<>();
        espacioAlmacenamiento = 500;
        Producto pr = new Producto(23,"Cama","23232323","colchon",21,21,"23/02/2002");
        Producto pr2 = new Producto(23,"Cama","24242424","colchon",20,21,"23/02/2002");
        Producto pr3 = new Producto(23,"Gata","33233233","colchon",19,21,"23/02/2002");
        validacionEspacio(pr.getInventario());
        validacionEspacio(pr2.getInventario());
        validacionEspacio(pr3.getInventario());
        producto.add(pr);
        producto.add(pr2);
        producto.add(pr3);
    }


    /**Metodos propios*/
    /**Metodo de ingreso de productos*/
    public void creacionProducto(){
        System.out.println("\n\n========Ingreso del producto========");
        double precio;
        while (true) {
            System.out.print("Ingrese un precio: ");
            if (sc.hasNextDouble()) {
                precio = sc.nextDouble();
                if (precio > 0 ) {
                    break;
                }else{
                    System.out.println("El numero debe ser positivo");
                }
            } else {
                System.out.println("Error: ingrese un número válido.");
                sc.next();
            }
        }
        sc.nextLine();

        System.out.println("Ingrese el nombre");
        String nombre = sc.nextLine();

        String id;
        while (true) {
            System.out.print("Ingrese el ID (solo números, 4–10 caracteres): ");
            id = sc.nextLine();

            // Validar longitud mínima
            if (id.length() < 4) {
                System.out.println("Error: el ID debe tener al menos 4 dígitos.");
                continue;
            }

            // Validar longitud máxima
            if (id.length() > 10) {
                System.out.println("Error: el ID no puede tener más de 10 dígitos.");
                continue;
            }

            // Validar que sea positivo
            if (Long.parseLong(id) <= 0) {
                System.out.println("Error: el ID debe ser un número positivo.");
                continue;
            }
            break; // ID válido
        }
        
        System.out.println("Ingrese la descripcion");
        String descripcion = sc.nextLine();
        int inventario;
        while (true) {
            System.out.println("Ingrese la cantida de producto");
            if (sc.hasNextInt()) {
                inventario = sc.nextInt();
                if (inventario > 0) {
                    break; // válido
                } else {
                    System.out.println("Error: el número debe ser positivo.");
                }
            } else {
                System.out.println("Error: debe ingresar un número entero.");
                sc.next();
            }
        }
        while (validacionEspacio(inventario) != 1){
            System.out.println("Ingrese la cantida de producto");
            inventario = sc.nextInt();
            validacionEspacio(inventario);
        };

        System.out.println("Ingrese el tiempo de entrega(En dias)");
        int tiempoEntrega;
        while (true) {
            System.out.println("Ingrese la cantida de producto");
            if (sc.hasNextInt()) {
                tiempoEntrega = sc.nextInt();
                if (tiempoEntrega > 0) {
                    break; // válido
                } else {
                    System.out.println("Error: el número debe ser positivo.");
                }
            } else {
                System.out.println("Error: debe ingresar un número entero.");
                sc.next();
            }
        }
        sc.nextLine();

        System.out.println("Ingrese la fecha de reabastecimiento del producto");
        String fechaReab = ingresoFecha();

        producto.add(new Producto(precio,nombre,id,descripcion,inventario,tiempoEntrega,fechaReab));
    }

    /**Metodo de mustra del producto*/
    public void showProducto(int a){
        if (a == -2 ){
            return;
        }
        if (a == -3){
            for (int i = 0; i<producto.size();i++){
                System.out.println(producto.get(i));
            }
            return;
        }
        try {
            System.out.println(producto.get(a));
        }catch (IndexOutOfBoundsException e){
            System.out.println("Error: No existe un producto con esa descripcion.\n");
        }
    }

    /**Metodo de busqueda del producto por su nombre*/
    public int busqueda(String a){
        int total = 0;
        int i;
        int indx = -1;
        ArrayList <Producto> repetidos;
        repetidos = new ArrayList<>();
        for (i = 0 ; i < producto.size();i++){
            String nombre = producto.get(i).getNombre();
            if(nombre.equalsIgnoreCase(a)){
                repetidos.add(producto.get(i));
                indx = i;
            }
        }
        if (repetidos.size() >= 2){
            System.out.println("Se encontro mas de un producto con ese nombre");
            for (int j = 0 ;j < repetidos.size();j++){
                showProducto(j);
            }
        }else{
            return indx;
        }
        return -2;
    }

    /**Metodo de busqueda del producto por su id*/
    public int busquedaId(String id){
        for (int i = 0 ; i < producto.size();i++){
            String nombre = producto.get(i).getId();
            if(nombre.equalsIgnoreCase(id)){
                return i;
            }
        }
        return -1;
    }
    /**Metodo de eliminacion del producto por su id*/
    public void eliminarProducto(int a){
        System.out.println("Usted quire eliminar el objero: ");
        showProducto(a);
        System.out.println("Esta seguro?");
        System.out.println("1.Si   |  2.No");
        if (sc.nextInt()== 1){
            System.out.println("Producto removido con exito");
            producto.remove(a);
        }
    }
    /**Almacen*/
    public void almacen(){
        String nombre;
        String id;
        int espacio;
        for (int i = 0;i < producto.size();i++){
           nombre = producto.get(i).getNombre();
           id = producto.get(i).getId();
           espacio = producto.get(i).getInventario();
            System.out.println("Nombre: "+nombre +", Id: "+ id +", Cantidad: "+ espacio);
        }
        System.out.println("Espacio disponible en el almacen: "+ espacioAlmacenamiento);


    }




    /**Metodos de validacion*/
    /**Validacion de fechas*/

    public String ingresoFecha() {
        String fecha = "";
        LocalDate fechaValida = null;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        boolean esCorrecta = false;
        while (!esCorrecta) {
            System.out.print("Ingresa la fecha (dd/MM/yyyy): ");
            fecha = sc.nextLine();
            if (Integer.parseInt(fecha) == 0){
                return fecha;
            }else{
                try {
                    fechaValida = LocalDate.parse(fecha,formato);
                    esCorrecta = true;
                }catch (DateTimeParseException e){
                    System.out.println("Formato inválido. Ejemplo correcto: 23/02/2025)");
                }
            }
        }
        return fecha;
    }

    public int validacionEspacio(int a){
        int temp = espacioAlmacenamiento;
        espacioAlmacenamiento = espacioAlmacenamiento - a;
        if (espacioAlmacenamiento > 0){
            System.out.println(a);
            System.out.println(espacioAlmacenamiento);
            return 1;
        }else{System.out.println(a);

            System.out.println("Menos"+espacioAlmacenamiento);
            espacioAlmacenamiento = temp;
            System.out.println(espacioAlmacenamiento);
            return 0;
        }
    }

    /**Metodos de java*/
    public Double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public int getEspacioAlmacenamiento() {
        return espacioAlmacenamiento;
    }

    public void setEspacioAlmacenamiento(int espacioAlmacenamiento) {
        this.espacioAlmacenamiento = espacioAlmacenamiento;
    }

    public ArrayList<Producto> getProducto() {
        return producto;
    }

    public void setProducto(ArrayList<Producto> producto) {
        this.producto = producto;
    }

}
