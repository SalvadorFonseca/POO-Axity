package modelo;

public class Venta {
    private Dulce dulce;
    private double montoPagado;
    private double cambio;

    public Venta(Dulce dulce, double montoPagado, double cambio) {
        this.dulce = dulce;
        this.montoPagado = montoPagado;
        this.cambio = cambio;
    }

    public Dulce getDulce() {
        return dulce;
    }

    public double getMontoPagado() {
        return montoPagado;
    }

    public double getCambio() {
        return cambio;
    }

    @Override
    public String toString() {
        return String.format("Venta: %s | Precio: $%.2f | Pagado: $%.2f | Cambio: $%.2f",
                dulce.getNombre(), dulce.getPrecio(), montoPagado, cambio);
    }
}