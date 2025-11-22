import java.util.Scanner;

/**Descripción de la actividad: Los estudiantes deberán analizar un escenario de una
 pequeña empresa que enfrenta problemas en la gestión de su inventario. Deberán
 identificar las variables clave (productos, cantidades, fechas de reabastecimiento,
 costos) y las restricciones (presupuesto, espacio de almacenamiento, tiempo de
 entrega) que afectan la gestión eficiente del inventario.*/


public class Main {
    public static void main(String[] args) {
        Empresa em = new Empresa();
        Scanner sc = new Scanner(System.in);
        int opcion;
        int nr;
        do {
            String producto = "";
            System.out.println(
                    "===== MENÚ PRINCIPAL =====\n" +
                            "1. Agregar Producto\n" +
                            "2. Buscar producto\n" +
                            "3. Editar producto\n" +
                            "4. Eliminar producto\n" +
                            "5. Capacidad del alamcen\n" +
                            "6. Compra de producto\n" +
                            "7. venta de producto\n" +
                            "8. Salir\n"
            );
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion){
                case 1:
                    em.creacionProducto();
                    break;
                case 2:
                    System.out.println("1.Buscar por nombre |  2.Buscar por id |  3.Mostrar toda la lista");
                    boolean b = true;
                    do {
                        System.out.print("Ingrese el metodo de busqueda: ");
                        int elc = sc.nextInt();
                        sc.nextLine();
                        switch (elc){
                            case 1:
                                System.out.print("Ingrese el nombre: ");
                                producto = sc.nextLine();
                                em.showProducto(em.busqueda(producto));
                                b = false;
                                break;
                            case 2:
                                System.out.print("Ingrese el id: ");
                                producto = sc.nextLine();
                                em.showProducto(em.busquedaId(producto));
                                b = false;
                                break;
                            case 3:
                                if (producto.isEmpty()){
                                    System.out.println("No existe el productos");
                                }else{ em.showProducto(-3);}
                                b = false;
                                break;
                            default:
                                System.out.println();
                                System.out.println("Ingrese un numero entre 1-3");
                                break;
                        }
                    }while (b);
                    break;
                case 3:

                    break;
                case 4:
                    System.out.print("Ingrese el id del objeto que quiere eliminar: ");
                    producto = sc.nextLine();
                    System.out.println(em.busquedaId(producto));
                    em.eliminarProducto(em.busquedaId(producto));
                    break;
                case 5:
                    em.almacen();
                    break;
                case 6:
                    em.comprarParaInventario();
                    break;
                case 7:
                    em.ventaProducto();
                    break;
            }
        }while (opcion != 8);
        System.out.println("Saliendo...");
    }
}
