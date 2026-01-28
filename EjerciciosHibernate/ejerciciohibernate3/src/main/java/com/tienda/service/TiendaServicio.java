package com.tienda.service;

import java.util.List;
import java.util.stream.Collectors;

import com.tienda.dao.CategoriaDAO;
import com.tienda.dao.CategoriaDAOImpl;
import com.tienda.dao.ClienteDAO;
import com.tienda.dao.ClienteDAOImpl;
import com.tienda.dao.PedidoDAO;
import com.tienda.dao.PedidoDAOImpl;
import com.tienda.dao.ProductoDAO;
import com.tienda.dao.ProductoDAOImpl;
import com.tienda.dto.EstadisticasDTO;
import com.tienda.dto.ProductoDTO;
import com.tienda.model.Categoria;
import com.tienda.model.Cliente;
import com.tienda.model.Direccion;
import com.tienda.model.LineaPedido;
import com.tienda.model.Producto;
import com.tienda.util.JPAUtil;

import jakarta.persistence.EntityManager;

public class TiendaServicio {

    private final EntityManager em = JPAUtil.getEntityManager();

    private final CategoriaDAO categoriaDAO = new CategoriaDAOImpl(em);
    private final ProductoDAO productoDAO = new ProductoDAOImpl(em);
    private final ClienteDAO clienteDAO = new ClienteDAOImpl(em);
    private final PedidoDAO pedidoDAO = new PedidoDAOImpl(em);

    // -------------------- CATEGORIAS --------------------
    public void crearCategoria(String nombre, String descripcion) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la categoría es obligatorio");
        }
        Categoria cat = new Categoria();
        cat.setNombre(nombre);
        cat.setDescripcion(descripcion);
        cat.setActiva(true);
        categoriaDAO.guardar(cat);
    }

    public List<Categoria> listarCategorias() {
        return categoriaDAO.listarTodos();
    }

    // -------------------- PRODUCTOS --------------------
    public void crearProducto(String nombre, String descripcion, Double precio, Integer stock, Long categoriaId) {
        if (precio < 0 || stock < 0) {
            throw new IllegalArgumentException("Precio y stock no pueden ser negativos");
        }

        Categoria categoria = categoriaDAO.buscarPorId(categoriaId);
        if (categoria == null) {
            throw new IllegalArgumentException("Categoría no existe");
        }

        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setActivo(true);
        producto.setCategoria(categoria);

        productoDAO.guardar(producto);
    }

    public List<ProductoDTO> listarProductosPorCategoria(Long categoriaId) {
        Categoria categoria = categoriaDAO.buscarPorId(categoriaId);
        if (categoria == null) {
            throw new IllegalArgumentException("Categoría no existe");
        }
        return productoDAO.listarPorCategoria(categoriaId).stream()
                .map(p -> new ProductoDTO(p.getId(), p.getNombre(), p.getPrecio(), p.getStock(),
                        p.getCategoria().getNombre()))
                .collect(Collectors.toList());
    }

    public void actualizarStockProducto(Long productoId, int nuevoStock) {
        Producto p = productoDAO.buscarPorId(productoId);
        if (p == null)
            throw new IllegalArgumentException("Producto no existe");
        if (nuevoStock < 0)
            throw new IllegalArgumentException("Stock no puede ser negativo");
        p.setStock(nuevoStock);
        productoDAO.actualizar(p);
    }

    public List<ProductoDTO> buscarProductoPorNombre(String nombre) {
        return productoDAO.buscarPorNombre(nombre).stream()
                .map(p -> new ProductoDTO(p.getId(), p.getNombre(), p.getPrecio(), p.getStock(),
                        p.getCategoria().getNombre()))
                .collect(Collectors.toList());
    }

    public List<ProductoDTO> listarProductosConStockBajo(int minimo) {
        return productoDAO.productosConStockBajo(minimo).stream()
                .map(p -> new ProductoDTO(p.getId(), p.getNombre(), p.getPrecio(), p.getStock(),
                        p.getCategoria().getNombre()))
                .collect(Collectors.toList());
    }

    // -------------------- CLIENTES --------------------
    public void registrarCliente(String nombre, String email, String telefono) {
        if (clienteDAO.buscarPorEmail(email) != null) {
            throw new IllegalArgumentException("Email ya registrado");
        }
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);
        clienteDAO.guardar(cliente);
    }

    public void añadirDireccionACliente(String emailCliente, Direccion direccion) {
        Cliente cliente = clienteDAO.buscarPorEmail(emailCliente);
        if (cliente == null)
            throw new IllegalArgumentException("Cliente no existe");
        direccion.setCliente(cliente);
        cliente.getDirecciones().add(direccion);
        clienteDAO.actualizar(cliente);
    }

    public void añadirDireccionACliente(String emailCliente, String calle, String ciudad, String provincia, String cp,
            String pais) {
        Direccion direccion = new Direccion();
        direccion.setCalle(calle);
        direccion.setCiudad(ciudad);
        direccion.setProvincia(provincia);
        direccion.setCodigoPostal(cp);
        direccion.setPais(pais.isBlank() ? "España" : pais);
        añadirDireccionACliente(emailCliente, direccion);
    }

    public void establecerDireccionPrincipal(String emailCliente, Long idDireccion) {
        Cliente cliente = clienteDAO.buscarPorEmail(emailCliente);
        if (cliente == null)
            throw new IllegalArgumentException("Cliente no existe");

        Direccion dir = cliente.getDirecciones().stream()
                .filter(d -> d.getId().equals(idDireccion))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Dirección no pertenece al cliente"));

        cliente.setDireccionPrincipal(dir);
        clienteDAO.actualizar(cliente);
    }

    public List<Cliente> listarClientesConPedidos() {
        return clienteDAO.listarClientesConPedidos();
    }

    public Cliente buscarClientePorEmail(String email) {
        return clienteDAO.buscarPorEmail(email);
    }

    // -------------------- MÉTODOS DE REPORTES --------------------

    // 18. Productos más vendidos (top 10)
    public List<EstadisticasDTO> productosMasVendidos() {
        return pedidoDAO.listarTodos().stream()
                .flatMap(p -> p.getLineas().stream())
                .collect(Collectors.groupingBy(
                        lp -> lp.getProducto().getNombre(),
                        Collectors.summingLong(LineaPedido::getCantidad)))
                .entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(10)
                .map(e -> new EstadisticasDTO(
                        e.getKey(), // nombre producto
                        e.getValue(), // cantidad vendida
                        null // total no aplica aquí
                ))
                .collect(Collectors.toList());
    }

    // 19. Clientes con más pedidos (top 5)
    public List<Cliente> clientesMasPedidos() {
        return clienteDAO.listarClientesConPedidos().stream()
                .sorted((c1, c2) -> Integer.compare(c2.getPedidos().size(), c1.getPedidos().size()))
                .limit(5)
                .collect(Collectors.toList());
    }

    // 20. Ingresos totales por categoría
    public List<EstadisticasDTO> ingresosTotalesPorCategoria() {
        return pedidoDAO.listarTodos().stream()
                .flatMap(p -> p.getLineas().stream())
                .collect(Collectors.groupingBy(
                        lp -> lp.getProducto().getCategoria().getNombre(),
                        Collectors.summingDouble(LineaPedido::getSubtotal)))
                .entrySet().stream()
                .map(e -> new EstadisticasDTO(
                        e.getKey(), // nombre categoría
                        null, // cantidad no aplica
                        e.getValue() // ingresos
                ))
                .collect(Collectors.toList());
    }

    // 21. Pedidos por estado
    public List<EstadisticasDTO> pedidosPorEstado() {
        return pedidoDAO.listarTodos().stream()
                .collect(Collectors.groupingBy(
                        p -> p.getEstado().name(),
                        Collectors.counting()))
                .entrySet().stream()
                .map(e -> new EstadisticasDTO(
                        e.getKey(), // estado
                        e.getValue(), // cantidad de pedidos
                        null // total no aplica
                ))
                .collect(Collectors.toList());
    }

    // 22. Productos sin ventas
    public List<Producto> productosSinVentas() {
        List<Long> productosVendidos = pedidoDAO.listarTodos().stream()
                .flatMap(p -> p.getLineas().stream())
                .map(lp -> lp.getProducto().getId())
                .distinct()
                .collect(Collectors.toList());

        return productoDAO.listarTodos().stream()
                .filter(p -> !productosVendidos.contains(p.getId()))
                .collect(Collectors.toList());
    }

    // 23. Valor total de inventario
    public double valorTotalInventario() {
        return productoDAO.listarTodos().stream()
                .mapToDouble(p -> p.getPrecio() * p.getStock())
                .sum();
    }

    // 24. Clientes sin pedidos
    public List<Cliente> clientesSinPedidos() {
        return clienteDAO.listarTodos().stream()
                .filter(c -> c.getPedidos().isEmpty())
                .toList();
    }

}
