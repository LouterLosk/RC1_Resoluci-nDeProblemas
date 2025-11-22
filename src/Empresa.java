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
        espacioAlmacenamiento = 600;
        Producto pr = new Producto(23,"Cama","23232323","colchon",21,21,"23/02/2002");
        Producto pr2 = new Producto(23,"Cama","24242424","colchon",20,21,"23/02/2002");
        Producto pr3 = new Producto(23,"Gata","33233233","colchon",19,21,"23/02/2002");
        ProductoComestible pnc = new ProductoComestible(23,"Helado","2233","es un helado",23,23,"20/10/2001","10/20/2030",true);
        validacionEspacio(pr.getInventario());
        validacionEspacio(pr2.getInventario());
        validacionEspacio(pr3.getInventario());
        validacionEspacio(pnc.getInventario());
        producto.add(pr);
        producto.add(pr2);
        producto.add(pr3);
        producto.add(pnc);
    }


    /**Metodos propios*/
    /**Metodo de ingreso de productos*/
    public void creacionProducto(){
        System.out.println("\n\n--------Ingreso del producto--------");
        System.out.print("Ingrese el nombre: ");
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

            // Validar que solo contenga dígitos
            boolean soloDigitos = true;
            for (int i = 0; i < id.length(); i++) {
                if (!Character.isDigit(id.charAt(i))) {
                    soloDigitos = false;
                    break;
                }
            }
            if (!soloDigitos) {
                System.out.println("Error: el ID solo debe contener números.");
                continue;
            }

            // Validar que sea positivo
            if (Long.parseLong(id) <= 0) {
                System.out.println("Error: el ID debe ser un número positivo.");
                continue;
            }

            // Validar que no exista otro producto con el mismo ID
            if (busquedaId(id) != -1) {
                System.out.println("Error: ya existe un producto con ese ID.");
                continue;
            }

            break; // ID válido
        }

        double precio;
        while (true) {
            System.out.print("Ingrese un precio: ");
            if (sc.hasNextDouble()) {
                precio = sc.nextDouble();
                sc.nextLine();
                if (precio > 0 ) {
                    break;
                } else {
                    System.out.println("El número debe ser positivo");
                }
            } else {
                System.out.println("Error: ingrese un número válido.");
                sc.next();
            }
        }
        System.out.print("Ingrese la descripción: ");
        String descripcion = sc.nextLine();

        int inventario;
        while (true) {
            System.out.print("Ingrese la cantidad de producto(disponible): ");
            if (sc.hasNextInt()) {
                inventario = sc.nextInt();
                sc.nextLine();
                if (inventario > 0) {
                    break; // válido
                } else {
                    System.out.println("Error: el número debe ser positivo.");
                }
            } else {
                System.out.println("Error: ingrese un número entero válido.");
                sc.next(); // limpiar buffer
            }
        }

        // Validar espacio de almacenamiento
        boolean hayEspacio = (validacionEspacio(inventario) == 1);

        if (!hayEspacio) {
            System.out.println("No hay suficiente espacio para almacenar esta cantidad.");
            System.out.println("El producto NO ha sido registrado. Volviendo al menú...");
            return;
        }
        String fechaReab = "";

        // Elegir tipo de producto
        System.out.println("Seleccione el tipo de producto:");
        System.out.println("1. Comestible");
        System.out.println("2. No comestible");
        int tipo = 0;
        while (tipo != 1 && tipo != 2) {
            System.out.print("Opción: ");
            if (sc.hasNextInt()) {
                tipo = sc.nextInt();
            } else {
                sc.next(); // limpiar
            }
        }

        sc.nextLine(); // limpiar salto
        Producto nuevoProducto;

        if (tipo == 1) {
            // Datos específicos de comestible
            System.out.println("Ingrese la fecha de caducidad del producto comestible:");
            String fechaCaducidad = ingresoFecha();

            System.out.println("¿Requiere refrigeración?");
            System.out.println("1. Sí   |  2. No");
            boolean requiereRefrigeracion = false;
            int opRef = 0;
            while (opRef != 1 && opRef != 2) {
                System.out.print("Opción: ");
                if (sc.hasNextInt()) {
                    opRef = sc.nextInt();
                    sc.nextLine();
                } else {
                    sc.next();
                }
            }
            requiereRefrigeracion = (opRef == 1);

            nuevoProducto = new ProductoComestible(
                    precio, nombre, id, descripcion,
                    inventario, 0, fechaReab,
                    fechaCaducidad, requiereRefrigeracion
            );

        } else if (tipo == 2) {
            // Datos específicos de no comestible
            System.out.print("Ingrese el material del producto: ");
            String material = sc.nextLine();

            System.out.println("¿Requiere almacenamiento especial?");
            System.out.println("1. Sí   |  2. No");
            boolean almacenamientoEspecial = false;
            int opAlm = 0;
            while (opAlm != 1 && opAlm != 2) {
                System.out.print("Opción: ");
                if (sc.hasNextInt()) {
                    opAlm = sc.nextInt();
                    sc.nextLine();
                } else {
                    sc.next();
                }
            }
            almacenamientoEspecial = (opAlm == 1);

            nuevoProducto = new ProductoNoComestible(
                    precio, nombre, id, descripcion,
                    inventario, 0, fechaReab,
                    material, almacenamientoEspecial
            );
            producto.add(nuevoProducto);
        }
        System.out.println("Producto registrado con éxito.");
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
        if (a != -1 ){
            System.out.println("Usted quire eliminar el objeto: ");
            showProducto(a);
            System.out.println("Esta seguro?");
            System.out.println("1.Si   |  2.No");
            if (sc.nextInt()== 1){
                System.out.println("Producto removido con exito");
                producto.remove(a);
            }
        }else{
            System.out.println("No existe ese id");
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

    /**Compra de inventario*/
    public void comprarParaInventario() {
        System.out.print("Ingrese el ID del producto a reabastecer: ");
        String idBuscado = sc.nextLine();

        int pos = busquedaId(idBuscado);
        if (pos < 0) {
            System.out.println("No se encontró un producto con ese ID.");
            return;
        }

        Producto p = producto.get(pos);

        System.out.print("¿Cuántas unidades va a agregar al inventario?: ");
        int cantidad = sc.nextInt();
        sc.nextLine();
        if (validacionEspacio(cantidad) == 0){
            System.out.println("No hay suficiente espacio disponoble para agregar el producto.");
            return;
        }
        // Actualizar inventario
        p.setInventario(p.getInventario() + cantidad);

        // AHORA SÍ pedir la fecha de reabastecimiento
        System.out.println("Ingrese la fecha de reabastecimiento de esta compra:");
        String nuevaFechaReab = ingresoFecha();  // ya tienes este método
        p.setFechaReab(nuevaFechaReab);

        System.out.println("Compra registrada correctamente.");
        System.out.println("Inventario actual: " + p.getInventario());
        System.out.println("Fecha de reabastecimiento: " + p.getFechaReab());
    }


    /**Metodos de validacion*/
    /**Validacion de fechas*/
    public String ingresoFecha() {
        String fecha = "";
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        boolean esCorrecta = false;

        while (!esCorrecta) {
            System.out.print("Ingresa la fecha (dd/MM/yyyy) o 0 si no aplica: ");
            fecha = sc.nextLine().trim();

            // Si el usuario no quiere ingresar fecha
            if (fecha.equals("0")) {
                return fecha;   // o return ""; si prefieres dejarla vacía
            }

            try {
                // Solo validamos el formato si no es "0"
                LocalDate.parse(fecha, formato);
                esCorrecta = true;          // formato correcto
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido. Ejemplo correcto: 23/02/2025");
            }
        }
        return fecha;
    }

    public int validacionEspacio(int a){
        int temp = espacioAlmacenamiento;
        espacioAlmacenamiento = espacioAlmacenamiento - a;
        if (espacioAlmacenamiento >= 0){
            return 1;
        }else{
            espacioAlmacenamiento = temp;
            System.out.println("El espacio disponible es de " + espacioAlmacenamiento);
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
