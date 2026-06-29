package servicio;

import interfaz.GestorVentas;
import modelo.Dulce;
import modelo.Venta;
import java.util.*;

public class SistemaVentas implements GestorVentas {
    private List<Venta> historialVentas;
    private Map<String, Integer> stockInicial;

    public SistemaVentas() {
        this.historialVentas = new ArrayList<>();
        this.stockInicial = new HashMap<>();
    }

    @Override
    public void registrarStockInicial(String nombreDulce, int cantidad) {
        if (!stockInicial.containsKey(nombreDulce)) {
            stockInicial.put(nombreDulce, cantidad);
        }
    }

    @Override
    public void registrarVenta(Venta venta) {
        historialVentas.add(venta);
    }

    @Override
    public void mostrarReporteVentas(Map<String, Dulce> inventario) {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("           REPORTE DE VENTAS (ADMIN)                ");
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("Ventas totales: $" + String.format("%.2f", calcularVentasTotales()));
        System.out.println("Cantidad de ventas: " + calcularCantidadVentas());
        System.out.println("\nDetalle de ventas por producto:");
        
        Map<String, Integer> ventasPorProducto = obtenerVentasPorProducto();
        
        if (ventasPorProducto.isEmpty()) {
            System.out.println("   No hay ventas registradas.");
        } else {
            for (Dulce dulce : inventario.values()) {
                String nombre = dulce.getNombre();
                int vendidos = ventasPorProducto.getOrDefault(nombre, 0);
                int stockActual = dulce.getStock();
                int stockInicialProducto = stockInicial.getOrDefault(nombre, stockActual + vendidos);
                
                System.out.printf("   • %s: %d/%d vendidos (Quedan: %d)%s\n", 
                    nombre, 
                    vendidos, 
                    stockInicialProducto,
                    stockActual,
                    stockActual == 0 ? " AGOTADO" : "");
            }
        }
        
        System.out.println("═══════════════════════════════════════════════════════\n");
    }

    private double calcularVentasTotales() {
        return historialVentas.stream()
                .mapToDouble(v -> v.getDulce().getPrecio())
                .sum();
    }

    private int calcularCantidadVentas() {
        return historialVentas.size();
    }

    private Map<String, Integer> obtenerVentasPorProducto() {
        Map<String, Integer> ventasPorProducto = new HashMap<>();
        
        for (Venta venta : historialVentas) {
            String nombreDulce = venta.getDulce().getNombre();
            ventasPorProducto.put(nombreDulce, 
                ventasPorProducto.getOrDefault(nombreDulce, 0) + 1);
        }
        
        return ventasPorProducto;
    }
}