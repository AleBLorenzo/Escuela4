package com.tienda;

import java.util.List;
import java.util.Scanner;

import com.tienda.dto.ProductoDTO;
import com.tienda.model.Cliente;
import com.tienda.model.EstadoPedido;
import com.tienda.model.Pedido;
import com.tienda.service.PedidoServicio;
import com.tienda.service.TiendaServicio;
import com.tienda.util.JPAUtil;

public class Main {

    private static final TiendaServicio tiendaServicio = new TiendaServicio();
    private static final PedidoServicio pedidoServicio = new PedidoServicio();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;

        do {
            mostrarMenu();
            System.out.print("Seleccione opción: ");
            opcion = Integer.parseInt(sc.nextLine());

            try {
                switch (opcion) {
                    // -------------------- GESTIÓN DE CATÁLOGO --------------------
                    case 1 -> crearCategoria();
                    case 2 -> crearProducto();
                    case 3 -> listarProductosPorCategoria();
                    case 4 -> actualizarStockProducto();
                    case 5 -> buscarProductosPorNombre();
                    case 6 -> productosConStockBajo();

                    // -------------------- GESTIÓN DE CLIENTES --------------------
                    case 7 -> registrarCliente();
                    case 8 -> añadirDireccionCliente();
                    case 9 -> establecerDireccionPrincipal();
                    case 10 -> listarClientesConPedidos();
                    case 11 -> buscarClientePorEmail();

                    // -------------------- GESTIÓN DE PEDIDOS --------------------
                    case 12 -> crearPedido();
                    case 13 -> añadirProductoAPedido();
                    case 14 -> finalizarPedido();
                    case 15 -> cambiarEstadoPedido();
                    case 16 -> verDetallesPedido();
                    case 17 -> cancelarPedido();

                    // -------------------- REPORTES --------------------
                    case 18 -> productosMasVendidos();
                    case 19 -> clientesMasPedidos();
                    case 20 -> ingresosTotalesPorCategoria();
                    case 21 -> pedidosPorEstado();
                    case 22 -> productosSinVentas();
                    case 23 -> valorTotalInventario();
                    case 24 -> clientesSinPedidos();

                    case 0 -> System.out.println("Saliendo...");
                    default -> System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println();

        } while (opcion != 0);

        // Cerrar EntityManager
        JPAUtil.close();
        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("=== TIENDA ONLINE - SHOPEASE ===\n");
        System.out.println("GESTIÓN DE CATÁLOGO");
        System.out.println("1. Crear categoría");
        System.out.println("2. Crear producto");
        System.out.println("3. Listar productos por categoría");
        System.out.println("4. Actualizar stock de producto");
        System.out.println("5. Buscar productos por nombre");
        System.out.println("6. Productos con stock bajo (<10 unidades)\n");

        System.out.println("GESTIÓN DE CLIENTES");
        System.out.println("7. Registrar nuevo cliente");
        System.out.println("8. Añadir dirección a cliente");
        System.out.println("9. Establecer dirección principal");
        System.out.println("10. Listar clientes con pedidos");
        System.out.println("11. Buscar cliente por email\n");

        System.out.println("GESTIÓN DE PEDIDOS");
        System.out.println("12. Crear nuevo pedido");
        System.out.println("13. Añadir producto a pedido");
        System.out.println("14. Finalizar pedido");
        System.out.println("15. Cambiar estado de pedido");
        System.out.println("16. Ver detalles de pedido");
        System.out.println("17. Cancelar pedido\n");

        System.out.println("CONSULTAS Y REPORTES");
        System.out.println("18. Productos más vendidos (top 10)");
        System.out.println("19. Clientes con más pedidos (top 5)");
        System.out.println("20. Ingresos totales por categoría");
        System.out.println("21. Pedidos por estado");
        System.out.println("22. Productos sin ventas");
        System.out.println("23. Valor total de inventario");
        System.out.println("24. Clientes sin pedidos\n");

        System.out.println("0. Salir");
    }

    // -------------------- MÉTODOS DE CATÁLOGO --------------------
    private static void crearCategoria() {
        System.out.print("Nombre categoría: ");
        String nombre = sc.nextLine();
        System.out.print("Descripción: ");
        String descripcion = sc.nextLine();
        tiendaServicio.crearCategoria(nombre, descripcion);
        System.out.println("Categoría creada correctamente.");
    }

    private static void crearProducto() {
        System.out.print("Nombre producto: ");
        String nombre = sc.nextLine();
        System.out.print("Descripción: ");
        String descripcion = sc.nextLine();
        System.out.print("Precio: ");
        double precio = Double.parseDouble(sc.nextLine());
        System.out.print("Stock: ");
        int stock = Integer.parseInt(sc.nextLine());
        System.out.print("ID categoría: ");
        long catId = Long.parseLong(sc.nextLine());
        tiendaServicio.crearProducto(nombre, descripcion, precio, stock, catId);
        System.out.println("Producto creado correctamente.");
    }

    private static void listarProductosPorCategoria() {
        System.out.print("ID categoría: ");
        long catId = Long.parseLong(sc.nextLine());
        List<ProductoDTO> productos = tiendaServicio.listarProductosPorCategoria(catId);
        productos.forEach(p -> System.out.println(p.getId() + " - " + p.getNombre() + " - Stock: " + p.getStock()));
    }

    private static void actualizarStockProducto() {
        System.out.print("ID producto: ");
        long prodId = Long.parseLong(sc.nextLine());
        System.out.print("Nuevo stock: ");
        int stock = Integer.parseInt(sc.nextLine());
        tiendaServicio.actualizarStockProducto(prodId, stock);
        System.out.println("Stock actualizado.");
    }

    private static void buscarProductosPorNombre() {
        System.out.print("Nombre producto: ");
        String nombre = sc.nextLine();
        List<ProductoDTO> productos = tiendaServicio.buscarProductoPorNombre(nombre);
        productos.forEach(p -> System.out.println(p.getId() + " - " + p.getNombre() + " - Stock: " + p.getStock()));
    }

    private static void productosConStockBajo() {
        List<ProductoDTO> productos = tiendaServicio.listarProductosConStockBajo(10);
        productos.forEach(p -> System.out.println(p.getId() + " - " + p.getNombre() + " - Stock: " + p.getStock()));
    }

    // -------------------- MÉTODOS DE CLIENTES --------------------
    private static void registrarCliente() {
        System.out.print("Nombre cliente: ");
        String nombre = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Teléfono: ");
        String telefono = sc.nextLine();
        tiendaServicio.registrarCliente(nombre, email, telefono);
        System.out.println("Cliente registrado.");
    }

    private static void añadirDireccionCliente() {
        System.out.print("Email cliente: ");
        String email = sc.nextLine();
        System.out.print("Calle: ");
        String calle = sc.nextLine();
        System.out.print("Ciudad: ");
        String ciudad = sc.nextLine();
        System.out.print("Provincia: ");
        String prov = sc.nextLine();
        System.out.print("Código postal: ");
        String cp = sc.nextLine();
        System.out.print("País (Enter = España): ");
        String pais = sc.nextLine();
        tiendaServicio.añadirDireccionACliente(email, calle, ciudad, prov, cp, pais);
        System.out.println("Dirección añadida.");
    }

    private static void establecerDireccionPrincipal() {
        System.out.print("Email cliente: ");
        String email = sc.nextLine();
        System.out.print("ID dirección principal: ");
        long dirId = Long.parseLong(sc.nextLine());
        tiendaServicio.establecerDireccionPrincipal(email, dirId);
        System.out.println("Dirección principal establecida.");
    }

    private static void listarClientesConPedidos() {
        List<Cliente> clientes = tiendaServicio.listarClientesConPedidos();
        clientes.forEach(
                c -> System.out.println(c.getId() + " - " + c.getNombre() + " - Pedidos: " + c.getPedidos().size()));
    }

    private static void buscarClientePorEmail() {
        System.out.print("Email cliente: ");
        String email = sc.nextLine();
        Cliente c = tiendaServicio.buscarClientePorEmail(email);
        if (c != null)
            System.out.println(c.getId() + " - " + c.getNombre() + " - Direcciones: " + c.getDirecciones().size());
        else
            System.out.println("Cliente no encontrado.");
    }

    // -------------------- MÉTODOS DE PEDIDOS --------------------
    private static void crearPedido() {
        System.out.print("Email cliente: ");
        String email = sc.nextLine();
        pedidoServicio.crearPedido(email);
        System.out.println("Pedido creado.");
    }

    private static void añadirProductoAPedido() {
        System.out.print("ID pedido: ");
        long pedidoId = Long.parseLong(sc.nextLine());
        System.out.print("ID producto: ");
        long prodId = Long.parseLong(sc.nextLine());
        System.out.print("Cantidad: ");
        int cantidad = Integer.parseInt(sc.nextLine());
        pedidoServicio.añadirProductoAPedido(pedidoId, prodId, cantidad);
        System.out.println("Producto añadido al pedido.");
    }

    private static void finalizarPedido() {
        System.out.print("ID pedido: ");
        long pedidoId = Long.parseLong(sc.nextLine());
        pedidoServicio.finalizarPedido(pedidoId);
        System.out.println("Pedido finalizado.");
    }

    private static void cambiarEstadoPedido() {
        System.out.print("ID pedido: ");
        long pedidoId = Long.parseLong(sc.nextLine());
        System.out.print("Nuevo estado (PENDIENTE, PROCESANDO, ENVIADO, ENTREGADO, CANCELADO): ");

        String estadoStr = sc.nextLine().toUpperCase();
        EstadoPedido estado = EstadoPedido.valueOf(estadoStr);

        pedidoServicio.cambiarEstadoPedido(pedidoId, estado);
        System.out.println("Estado actualizado.");
    }

    private static void verDetallesPedido() {
        System.out.print("ID pedido: ");
        long pedidoId = Long.parseLong(sc.nextLine());
        Pedido p = pedidoServicio.buscarPedidoPorId(pedidoId);
        if (p != null) {
            System.out.println("Pedido: " + p.getNumeroPedido() + " - Cliente: " + p.getCliente().getNombre()
                    + " - Total: " + p.getTotal() + " - Estado: " + p.getEstado());
            p.getLineas().forEach(l -> System.out.println("  Producto: " + l.getProducto().getNombre() + " - Cantidad: "
                    + l.getCantidad() + " - Subtotal: " + l.getSubtotal()));
        } else
            System.out.println("Pedido no encontrado.");
    }

    private static void cancelarPedido() {
        System.out.print("ID pedido: ");
        long pedidoId = Long.parseLong(sc.nextLine());
        pedidoServicio.cancelarPedido(pedidoId);
        System.out.println("Pedido cancelado.");
    }

    // -------------------- MÉTODOS DE REPORTES --------------------
    private static void productosMasVendidos() {
        tiendaServicio.productosMasVendidos().forEach(dto -> System.out.println(dto.getNombre()
                + " - Vendido: " + dto.getCantidad()));
    }

    private static void clientesMasPedidos() {
        tiendaServicio.clientesMasPedidos().forEach(c -> System.out.println(c.getId()
                + " - " + c.getNombre()
                + " - Pedidos: " + c.getPedidos().size()));
    }

    private static void ingresosTotalesPorCategoria() {
        tiendaServicio.ingresosTotalesPorCategoria().forEach(dto -> System.out.println(dto.getNombre()
                + " - Ingresos: " + dto.getTotal()));
    }

    private static void pedidosPorEstado() {
        tiendaServicio.pedidosPorEstado().forEach(dto -> System.out.println(dto.getNombre()
                + " - Total pedidos: " + dto.getCantidad()));
    }

    private static void productosSinVentas() {
        tiendaServicio.productosSinVentas().forEach(p -> System.out.println(p.getId() + " - " + p.getNombre()));
    }

    private static void valorTotalInventario() {
        System.out.println("Valor total inventario: "
                + tiendaServicio.valorTotalInventario());
    }

    private static void clientesSinPedidos() {
        tiendaServicio.clientesSinPedidos().forEach(c -> System.out.println(c.getId() + " - " + c.getNombre()));
    }
}
