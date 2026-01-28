package com.tienda.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.tienda.dao.ClienteDAO;
import com.tienda.dao.ClienteDAOImpl;
import com.tienda.dao.PedidoDAO;
import com.tienda.dao.PedidoDAOImpl;
import com.tienda.dao.ProductoDAO;
import com.tienda.dao.ProductoDAOImpl;
import com.tienda.dto.PedidoResumenDTO;
import com.tienda.model.Cliente;
import com.tienda.model.EstadoPedido;
import com.tienda.model.LineaPedido;
import com.tienda.model.Pedido;
import com.tienda.model.Producto;
import com.tienda.util.JPAUtil;

import jakarta.persistence.EntityManager;

public class PedidoServicio {

    private final EntityManager em = JPAUtil.getEntityManager();
    private final PedidoDAO pedidoDAO = new PedidoDAOImpl(em);
    private final ClienteDAO clienteDAO = new ClienteDAOImpl(em);
    private final ProductoDAO productoDAO = new ProductoDAOImpl(em);

    // -------------------- PEDIDOS --------------------
    public Pedido crearPedido(String emailCliente) {
        Cliente cliente = clienteDAO.buscarPorEmail(emailCliente);
        if (cliente == null)
            throw new IllegalArgumentException("Cliente no existe");

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setNumeroPedido(generarNumeroPedido());

        pedidoDAO.guardar(pedido);
        return pedido;
    }

    public Pedido buscarPedidoPorId(Long pedidoId) {
        return pedidoDAO.buscarPorId(pedidoId);
    }

    public void añadirProductoAPedido(Long pedidoId, Long productoId, int cantidad) {
        Pedido pedido = pedidoDAO.buscarPorId(pedidoId);
        Producto producto = productoDAO.buscarPorId(productoId);

        if (pedido == null)
            throw new IllegalArgumentException("Pedido no existe");
        if (producto == null)
            throw new IllegalArgumentException("Producto no existe");
        if (cantidad <= 0)
            throw new IllegalArgumentException("Cantidad debe ser mayor que 0");
        if (producto.getStock() < cantidad)
            throw new IllegalArgumentException("Stock insuficiente");

        LineaPedido linea = new LineaPedido();
        linea.setCantidad(cantidad);
        linea.setPrecioUnidad(producto.getPrecio());
        linea.setProducto(producto);

        pedido.addLinea(linea);
        producto.setStock(producto.getStock() - cantidad);

        pedidoDAO.actualizar(pedido);
        productoDAO.actualizar(producto);
    }

    public void finalizarPedido(Long pedidoId) {
        Pedido pedido = pedidoDAO.buscarPorId(pedidoId);
        if (pedido == null)
            throw new IllegalArgumentException("Pedido no existe");
        pedido.setEstado(EstadoPedido.ENVIADO);
        pedidoDAO.actualizar(pedido);
    }

    public void cambiarEstadoPedido(Long pedidoId, EstadoPedido estado) {
        Pedido pedido = pedidoDAO.buscarPorId(pedidoId);
        if (pedido == null)
            throw new IllegalArgumentException("Pedido no existe");
        pedido.setEstado(estado);
        pedidoDAO.actualizar(pedido);
    }

    public PedidoResumenDTO verDetallesPedido(Long pedidoId) {
        Pedido pedido = pedidoDAO.buscarPorId(pedidoId);
        if (pedido == null)
            throw new IllegalArgumentException("Pedido no existe");

        return new PedidoResumenDTO(
                pedido.getId(),
                pedido.getNumeroPedido(),
                pedido.getFechaPedido(),
                pedido.getTotal(),
                pedido.getEstado().name(),
                pedido.getCliente().getNombre());
    }

    public void cancelarPedido(Long pedidoId) {
        Pedido pedido = pedidoDAO.buscarPorId(pedidoId);
        if (pedido == null)
            throw new IllegalArgumentException("Pedido no existe");
        pedido.setEstado(EstadoPedido.CANCELADO);
        pedidoDAO.actualizar(pedido);
    }

    // -------------------- REPORTES --------------------
    public List<PedidoResumenDTO> listarPedidosPorCliente(String emailCliente) {
        // Buscamos el cliente por email
        Cliente cliente = clienteDAO.buscarPorEmail(emailCliente);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no existe");
        }

        // Usamos el ID del cliente para la consulta
        return pedidoDAO.listarPorCliente(cliente.getId()).stream()
                .map(p -> new PedidoResumenDTO(
                        p.getId(),
                        p.getNumeroPedido(),
                        p.getFechaPedido(),
                        p.getTotal(),
                        p.getEstado().name(),
                        cliente.getNombre())) // ya tenemos el nombre del cliente
                .collect(Collectors.toList());
    }

    // Generación de número de pedido
    private String generarNumeroPedido() {
        LocalDateTime now = LocalDateTime.now();
        String fecha = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "PED-" + fecha;
    }

}
