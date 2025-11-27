import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Empresa {
    private Double presupuesto;
    private int espacioAlmacenamiento;
    private ArrayList<Producto> producto;
    Scanner sc = new Scanner(System.in);

    public Empresa() {
        producto = new ArrayList<>();
        espacioAlmacenamiento = 600;
        presupuesto = 200000.0;
//        ProductoComestible pc = new ProductoComestible(23,"Helado","2233","es un helado",23,0,"20/10/2001","10/20/2030",true);
//        validacionEspacio(pc.getInventario());
//        producto.add(pc);
//        ProductoNoComestible pcn = new ProductoNoComestible(23,"Cama","242424","colchon",20,0,"23/02/2002","plumas",false);
//        validacionEspacio(pcn.getInventario());
//        producto.add(pcn);

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
                sc.next();
            }
        }

        sc.nextLine();
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
            nuevoProducto = new ProductoComestible(precio, nombre, id, descripcion, inventario, 0, fechaReab, fechaCaducidad, requiereRefrigeracion);

        } else if (tipo == 2) {
            // Datos específicos de no comestible
            System.out.print("Ingrese el material del producto: ");
            String material = sc.nextLine();

            System.out.println("¿Requiere almacenamiento especial?");
            System.out.println("1. Sí   |  2. No");
            boolean almacenamientoEspecial = false;
            int opAlm = 0; //Opcion de almacenamiento
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
            if (producto.isEmpty()){
                System.out.println("No hay productos almacenados.");
            }else{ for (int i = 0; i<producto.size();i++){
                System.out.println(producto.get(i).toString());
            }
                return;}
        }
        try {
            System.out.println(producto.get(a).toString());
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
                Producto p = producto.get(a);
                int inv = p.getInventario();
                espacioAlmacenamiento = espacioAlmacenamiento + inv;
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
        //validacion del espacio diponible
        if (validacionEspacio(cantidad) == 0) {
            System.out.println("No hay suficiente espacio disponible para agregar el producto.");
            return;
        }
        if (!validarPresupuesto(cantidad,p.getPrecio())){
            return;
        }

        // Actualizar inventario
        p.setInventario(p.getInventario() + cantidad);

        // pedir la fecha de reabastecimiento
        System.out.println("Ingrese la fecha de reabastecimiento de esta compra:");
        String nuevaFechaReab = ingresoFecha();
        p.setFechaReab(nuevaFechaReab);
        //valída que la fecha de ingreso no sea antes de la fecha actual
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate FechaReab = LocalDate.parse(nuevaFechaReab,f);
        LocalDate fechaHoy = LocalDate.now();
        //se usa la variable en long, este metodo solo funciona con long
        long dias = ChronoUnit.DAYS.between(fechaHoy, FechaReab);
        p.setTiempoEntrega((int)dias);
        System.out.println("Compra registrada correctamente.");
        System.out.println("Inventario actual: " + p.getInventario());
        System.out.println("Fecha de reabastecimiento: " + p.getFechaReab());
        System.out.println("Tiempo entrega: " + p.getTiempoEntrega()+" dias");
    }

    /**Venta de producto*/
    public void ventaProducto() {
        System.out.print("Ingrese el ID del producto que desea vender: ");
        String id = sc.nextLine();

        int idx = busquedaId(id);
        if (idx == -1) {
            System.out.println("No existe un producto con ese ID.");
            return;
        }

        Producto p = producto.get(idx);
        System.out.println("Producto encontrado:");
        System.out.println(p);

        // Pedir cantidad a vender con validación
        int cantidad = 0;
        while (true) {
            System.out.print("Ingrese la cantidad a vender: ");
            if (sc.hasNextInt()) {
                cantidad = sc.nextInt();
                sc.nextLine();
                if (cantidad > 0) {
                    break;
                } else {
                    System.out.println("La cantidad debe ser mayor que 0.");
                }
            } else {
                System.out.println("Error: ingrese un número entero válido.");
                sc.next();
            }
        }

        // Verificar stock
        if (cantidad > p.getInventario()) {
            System.out.println("No hay suficiente stock para realizar la venta.");
            System.out.println("Stock disponible: " + p.getInventario());
            return;
        }

        // Actualizar inventario
        p.setInventario(p.getInventario() - cantidad);

        // Liberar espacio en el almacén
        espacioAlmacenamiento += cantidad;

        // Actualizar presupuesto con la venta
        if (presupuesto == null) {
            presupuesto = 0.0;
        }
        double ingreso = cantidad * p.getPrecio();
        presupuesto += ingreso;

        System.out.println("Venta realizada con éxito.");
        System.out.println("Unidades vendidas: " + cantidad);
        System.out.println("Inventario restante: " + p.getInventario());
        System.out.println("Ingreso generado: " + ingreso);
        System.out.println("Presupuesto actual: " + presupuesto);
        System.out.println("Espacio disponible en el almacén: " + espacioAlmacenamiento);
    }

    public void editarProductoPorId() {
        if (producto.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }

        System.out.print("Ingrese el ID del producto que desea editar: ");
        String idBuscado = sc.nextLine().trim();

        int idx = busquedaId(idBuscado);
        if (idx == -1) {
            System.out.println("No se encontró ningún producto con el ID: " + idBuscado);
            return;
        }

        Producto p = producto.get(idx);

        System.out.println("Producto encontrado:");
        showProducto(idx);

        boolean salir = false;
        while (!salir) {
            System.out.println("\n¿Qué desea editar?");
            System.out.println("1. Nombre");
            System.out.println("2. Precio");
            System.out.println("3. Descripción");
            System.out.println("4. Inventario");
            System.out.println("5. Fecha de reabastecimiento");
            System.out.println("6. Tiempo de entrega");

            // Opciones adicionales según el tipo de producto
            if (p instanceof ProductoComestible) {
                System.out.println("7. Fecha de caducidad");
                System.out.println("8. Requiere refrigeración");
            } else if (p instanceof ProductoNoComestible) {
                System.out.println("7. Material");
                System.out.println("8. Almacenamiento especial");
            }

            System.out.println("0. Terminar edición");
            System.out.print("Opción: ");

            int opcion;
            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
                sc.nextLine(); // limpiar ENTER
            } else {
                System.out.println("Opción inválida. Intente de nuevo.");
                sc.next(); // limpiar basura
                continue;
            }

            switch (opcion) {
                case 1: // Nombre
                    System.out.println("Nombre actual: " + p.getNombre());
                    System.out.print("Nuevo nombre: ");
                    String nuevoNombre = sc.nextLine();
                    p.setNombre(nuevoNombre);
                    System.out.println("Nombre actualizado.");
                    break;

                case 2: // Precio
                    System.out.println("Precio actual: " + p.getPrecio());
                    double nuevoPrecio;
                    while (true) {
                        System.out.print("Nuevo precio: ");
                        if (sc.hasNextDouble()) {
                            nuevoPrecio = sc.nextDouble();
                            sc.nextLine();
                            if (nuevoPrecio > 0) {
                                break;
                            } else {
                                System.out.println("El precio debe ser mayor que 0.");
                            }
                        } else {
                            System.out.println("Ingrese un número válido.");
                            sc.next();
                        }
                    }
                    p.setPrecio(nuevoPrecio);
                    System.out.println("Precio actualizado.");
                    break;

                case 3: // Descripción
                    System.out.println("Descripción actual: " + p.getDescripcion());
                    System.out.print("Nueva descripción: ");
                    String nuevaDesc = sc.nextLine();
                    p.setDescripcion(nuevaDesc);
                    System.out.println("Descripción actualizada.");
                    break;

                case 4: { // Inventario
                    System.out.println("Inventario actual: " + p.getInventario());
                    int nuevoInv;
                    while (true) {
                        System.out.print("Nuevo inventario (>= 0): ");
                        if (sc.hasNextInt()) {
                            nuevoInv = sc.nextInt();
                            sc.nextLine();
                            if (nuevoInv >= 0) {
                                break;
                            } else {
                                System.out.println("El inventario no puede ser negativo.");
                            }
                        } else {
                            System.out.println("Ingrese un número entero válido.");
                            sc.next();
                        }
                    }

                    int invActual = p.getInventario();
                    if (nuevoInv == invActual) {
                        System.out.println("El inventario no ha cambiado.");
                        break;
                    }

                    if (nuevoInv > invActual) {
                        // Necesita más espacio en el almacén
                        int diferencia = nuevoInv - invActual;
                        if (validacionEspacio(diferencia) == 0) { // reutilizamos validacionEspacio
                            System.out.println("No se pudo actualizar el inventario por falta de espacio.");
                        } else {
                            p.setInventario(nuevoInv);
                            System.out.println("Inventario actualizado.");
                        }
                    } else {
                        // Libera espacio en el almacén
                        int liberar = invActual - nuevoInv;
                        p.setInventario(nuevoInv);
                        espacioAlmacenamiento += liberar; // estamos en la misma clase, podemos usar la variable
                        System.out.println("Inventario actualizado. Espacio liberado: " + liberar);
                        System.out.println("Espacio disponible en el almacén: " + espacioAlmacenamiento);
                    }
                    break;
                }

                case 5: // Fecha de reabastecimiento
                    System.out.println("Fecha de reabastecimiento actual: " + p.getFechaReab());
                    System.out.println("Ingrese la nueva fecha (dd/MM/yyyy) o 0 si no aplica:");
                    String nuevaFechaReab = ingresoFecha();
                    p.setFechaReab(nuevaFechaReab);
                    System.out.println("Fecha de reabastecimiento actualizada.");
                    break;

                case 6: { // Tiempo de entrega
                    System.out.println("Tiempo de entrega actual (días): " + p.getTiempoEntrega());
                    int nuevoTiempo;
                    while (true) {
                        System.out.print("Nuevo tiempo de entrega (en días, >= 0): ");
                        if (sc.hasNextInt()) {
                            nuevoTiempo = sc.nextInt();
                            sc.nextLine();
                            if (nuevoTiempo >= 0) {
                                break;
                            } else {
                                System.out.println("El tiempo no puede ser negativo.");
                            }
                        } else {
                            System.out.println("Ingrese un número entero válido.");
                            sc.next();
                        }
                    }
                    p.setTiempoEntrega(nuevoTiempo);
                    System.out.println("Tiempo de entrega actualizado.");
                    break;
                }

                case 7:
                    if (p instanceof ProductoComestible) {
                        ProductoComestible pc = (ProductoComestible) p;
                        System.out.println("Fecha de caducidad actual: " + pc.getFechaCaducidad());
                        System.out.println("Ingrese la nueva fecha de caducidad (dd/MM/yyyy):");
                        String nuevaCad = ingresoFecha();
                        pc.setFechaCaducidad(nuevaCad);
                        System.out.println("Fecha de caducidad actualizada.");
                    } else if (p instanceof ProductoNoComestible) {
                        ProductoNoComestible pnc = (ProductoNoComestible) p;
                        System.out.println("Material actual: " + pnc.getMaterial());
                        System.out.print("Nuevo material: ");
                        String nuevoMat = sc.nextLine();
                        pnc.setMaterial(nuevoMat);
                        System.out.println("Material actualizado.");
                    } else {
                        System.out.println("Opción no válida para este tipo de producto.");
                    }
                    break;

                case 8:
                    if (p instanceof ProductoComestible) {
                        ProductoComestible pc = (ProductoComestible) p;
                        System.out.println("Requiere refrigeración actual: " +
                                (pc.isRequiereRefrigeracion() ? "Sí" : "No"));
                        System.out.print("¿Requiere refrigeración? (1. Sí / 2. No): ");
                        int opRef = sc.nextInt();
                        sc.nextLine();
                        pc.setRequiereRefrigeracion(opRef == 1);
                        System.out.println("Valor actualizado.");
                    } else if (p instanceof ProductoNoComestible) {
                        ProductoNoComestible pnc = (ProductoNoComestible) p;
                        System.out.println("Almacenamiento especial actual: " +
                                (pnc.isAlmacenamientoEspecial() ? "Sí" : "No"));
                        System.out.print("¿Requiere almacenamiento especial? (1. Sí / 2. No): ");
                        int opEsp = sc.nextInt();
                        sc.nextLine();
                        pnc.setAlmacenamientoEspecial(opEsp == 1);
                        System.out.println("Valor actualizado.");
                    } else {
                        System.out.println("Opción no válida para este tipo de producto.");
                    }
                    break;

                case 0:
                    salir = true;
                    break;

                default:
                    System.out.println("Opción inválida, intente de nuevo.");
            }

            System.out.println("\nEstado actual del producto:");
            showProducto(idx);
        }

        System.out.println("Edición finalizada.");
    }



    /**Metodos de validacion*/
    /**Validacion de fechas*/
    public String ingresoFecha() {
        String fecha = "";
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        boolean esCorrecta = false;
        LocalDate fechaHoy = LocalDate.now();
        LocalDate fecha1;

        while (!esCorrecta) {
            System.out.print("Ingresa la fecha (dd/MM/yyyy) o 0 si no aplica: ");
            fecha = sc.nextLine().trim();

            // Si el usuario no quiere ingresar fecha
            if (fecha.equals("0")) {
                return fecha;
            }
            try {
                fecha1 = LocalDate.parse(fecha, formato);
                if(!fechaHoy.isBefore(fecha1)) {
                    System.out.println("Fecha incorrecta");
                    System.out.println("No pude ingresar un fecha anterior a " +  fechaHoy);
                }else {esCorrecta = true;}
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

    public boolean  validarPresupuesto(int cantidad,double precioUnitario) {
        if (presupuesto < cantidad * precioUnitario) {
            System.out.println("No hay presupuesto disponible para agregar el producto.");
            System.out.println("Costo total: " + cantidad * precioUnitario);
            System.out.println("Presupuesto disponible: " + presupuesto);
            return false;
        }
        System.out.println("Compra válida. Costo total: " + cantidad * precioUnitario);
        return true;
    }

}
