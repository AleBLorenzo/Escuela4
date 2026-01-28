package com.tienda.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "lineas_pedido")
public class LineaPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer cantidad;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Double precioUnidad;

    /**
     * Subtotal calculado (cantidad * precioUnidad)
     * No se persiste en BD
     */
    @Transient
    private Double subtotal;

    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    public LineaPedido() {
    }

    public LineaPedido(Integer cantidad, Long id, Pedido pedido, Double precioUnidad, Producto producto, Double subtotal) {
        this.cantidad = cantidad;
        this.id = id;
        this.pedido = pedido;
        this.precioUnidad = precioUnidad;
        this.producto = producto;
        this.subtotal = subtotal;
    }

    /*
     * =====================
     * Cálculo automático
     * =====================
     */

    @PrePersist
    @PreUpdate
    private void calcularSubtotal() {
        if (cantidad != null && precioUnidad != null) {
            this.subtotal = cantidad * precioUnidad;
        }
    }

    /*
     * =====================
     * Getter calculado
     * =====================
     */

    public Double getSubtotal() {
        if (cantidad != null && precioUnidad != null) {
            return cantidad * precioUnidad;
        }
        return 0.0;
    }

    // Establecer precio del producto actual al crear la línea
    public void setPrecioDesdeProducto(Producto producto) {
        this.precioUnidad = producto.getPrecio();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(Double precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

}
