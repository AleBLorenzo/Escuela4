package com.tienda.dto;

import java.time.LocalDateTime;

public class PedidoResumenDTO {

      private Long id;
    private String numeroPedido;
    private LocalDateTime fechaPedido;
    private Double total;
    private String estado;
    private String clienteNombre;

    @Override
    public String toString() {
        return "PedidoResumenDTO [id=" + id + ", numeroPedido=" + numeroPedido + ", fechaPedido=" + fechaPedido
                + ", total=" + total + ", estado=" + estado + ", clienteNombre=" + clienteNombre + "]";
    }

    public PedidoResumenDTO() {
    }

    public PedidoResumenDTO(Long id, String numeroPedido, LocalDateTime fechaPedido, Double total, String estado, String clienteNombre) {
        this.id = id;
        this.numeroPedido = numeroPedido;
        this.fechaPedido = fechaPedido;
        this.total = total;
        this.estado = estado;
        this.clienteNombre = clienteNombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }


}
