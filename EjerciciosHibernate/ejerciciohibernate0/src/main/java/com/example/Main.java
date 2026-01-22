package com.example;

import java.util.List;

import com.example.model.Producto;
import com.example.util.JPAUtil;

import jakarta.persistence.EntityManager;

public class Main {
    
    public static void main(String[] args) {
        
        System.out.println("=== DEMOSTRACIÓN HIBERNATE ===\n");
        
        // 1. CREAR (INSERT)
        crearProductos();
        
        // 2. LEER (SELECT)
        listarProductos();
        
        // 3. BUSCAR POR ID
        buscarProducto(1L);
        
        // 4. ACTUALIZAR (UPDATE)
        actualizarProducto(1L);
        
        // 5. ELIMINAR (DELETE)
        eliminarProducto(2L);
        
        // Listar productos después de eliminar
        listarProductos();
        
        // Cerrar EntityManagerFactory
        JPAUtil.close();
        
        System.out.println("\n=== FIN DEMOSTRACIÓN ===");
    }
    
    // CREATE - Guardar nuevos productos
    private static void crearProductos() {
        System.out.println("--- Creando productos ---");
        EntityManager em = JPAUtil.getEntityManager();
        
        try {
            em.getTransaction().begin();
            
            Producto p1 = new Producto("Portátil Lenovo", "ThinkPad X1 Carbon", 1299.99, 10);
            Producto p2 = new Producto("Ratón Logitech", "MX Master 3", 89.99, 25);
            Producto p3 = new Producto("Teclado Mecánico", "Keychron K2", 79.99, 15);
            
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            
            em.getTransaction().commit();
            
            System.out.println("✓ Productos creados correctamente\n");
            
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    
    // READ - Listar todos los productos
    private static void listarProductos() {
        System.out.println("--- Listado de productos ---");
        EntityManager em = JPAUtil.getEntityManager();
        
        try {
            List<Producto> productos = em.createQuery(
                "SELECT p FROM Producto p ORDER BY p.id", 
                Producto.class
            ).getResultList();
            
            if (productos.isEmpty()) {
                System.out.println("No hay productos en la base de datos");
            } else {
                productos.forEach(System.out::println);
            }
            System.out.println();
            
        } finally {
            em.close();
        }
    }
    
    // READ - Buscar por ID
    private static void buscarProducto(Long id) {
        System.out.println("--- Buscar producto ID: " + id + " ---");
        EntityManager em = JPAUtil.getEntityManager();
        
        try {
            Producto producto = em.find(Producto.class, id);
            
            if (producto != null) {
                System.out.println("Encontrado: " + producto);
            } else {
                System.out.println("Producto no encontrado");
            }
            System.out.println();
            
        } finally {
            em.close();
        }
    }
    
    // UPDATE - Actualizar producto
    private static void actualizarProducto(Long id) {
        System.out.println("--- Actualizar producto ID: " + id + " ---");
        EntityManager em = JPAUtil.getEntityManager();
        
        try {
            em.getTransaction().begin();
            
            Producto producto = em.find(Producto.class, id);
            
            if (producto != null) {
                System.out.println("Antes: " + producto);
                
                // Modificar atributos
                producto.setPrecio(1199.99);
                producto.setStock(8);
                
                // No es necesario llamar a merge() porque la entidad está en estado MANAGED
                
                em.getTransaction().commit();
                
                System.out.println("Después: " + producto);
                System.out.println("✓ Producto actualizado\n");
            } else {
                System.out.println("Producto no encontrado\n");
            }
            
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    
    // DELETE - Eliminar producto
    private static void eliminarProducto(Long id) {
        System.out.println("--- Eliminar producto ID: " + id + " ---");
        EntityManager em = JPAUtil.getEntityManager();
        
        try {
            em.getTransaction().begin();
            
            Producto producto = em.find(Producto.class, id);
            
            if (producto != null) {
                em.remove(producto);
                em.getTransaction().commit();
                System.out.println("✓ Producto eliminado: " + producto.getNombre() + "\n");
            } else {
                System.out.println("Producto no encontrado\n");
            }
            
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}