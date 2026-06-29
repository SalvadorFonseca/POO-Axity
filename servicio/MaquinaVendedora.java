package servicio;

import interfaz.GestorVentas;
import modelo.Dulce;
import modelo.Venta;
import excepcion.FondosInsuficientesException;
import excepcion.StockInsuficienteException;
import java.util.HashMap;
import java.util.Map;

public class MaquinaVendedora {
    private Map<String, Dulce> inventario;
    private GestorVentas gestorVentas;

    public MaquinaVendedora(GestorVentas gestorVentas) {
        this.inventario = new HashMap<>();
        this.gestorVentas = gestorVentas;
    }

    public void agregarDulce(Dulce dulce) {
        inventario.put(dulce.getCodigo(), dulce);
        gestorVentas.registrarStockInicial(dulce.getNombre(), dulce.getStock());
    }

    public void mostrarInventario() {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("              INVENTARIO DE DULCES                  ");
        System.out.println("═══════════════════════════════════════════════════════");
        
        if (inventario.isEmpty()) {
            System.out.println("No hay dulces disponibles en la máquina.");
        } else {
            inventario.values().forEach(System.out::println);
        }
        System.out.println("═══════════════════════════════════════════════════════\n");
    }

    public void verificarDisponibilidad(String codigo) throws StockInsuficienteException {
        Dulce dulce = inventario.get(codigo);

        if (dulce == null) {
            throw new IllegalArgumentException("Código de dulce no válido: " + codigo);
        }

        if (!dulce.hayStock()) {
            throw new StockInsuficienteException(
                "Lo sentimos, el dulce '" + dulce.getNombre() + "' está agotado.");
        }
    }

    public Venta comprarDulce(String codigo, double montoPagado) 
            throws StockInsuficienteException, FondosInsuficientesException {
        
        verificarDisponibilidad(codigo);
        
        Dulce dulce = inventario.get(codigo);

        if (montoPagado < dulce.getPrecio()) {
            throw new FondosInsuficientesException(
                String.format("Fondos insuficientes. Se requiere $%.2f pero pagó $%.2f",
                    dulce.getPrecio(), montoPagado));
        }

        double cambio = montoPagado - dulce.getPrecio();
        dulce.reducirStock();
        
        Venta venta = new Venta(dulce, montoPagado, cambio);
        gestorVentas.registrarVenta(venta);

        return venta;
    }

    public GestorVentas getGestorVentas() {
        return gestorVentas;
    }

    public Dulce obtenerDulce(String codigo) {
        return inventario.get(codigo);
    }

    public Map<String, Dulce> getInventario() {
        return inventario;
    }
}