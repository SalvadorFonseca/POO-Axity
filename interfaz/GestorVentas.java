package interfaz;

import modelo.Dulce;
import modelo.Venta;
import java.util.Map;

public interface GestorVentas {
    void registrarVenta(Venta venta);
    void registrarStockInicial(String nombreDulce, int cantidad);
    void mostrarReporteVentas(Map<String, Dulce> inventario);
}