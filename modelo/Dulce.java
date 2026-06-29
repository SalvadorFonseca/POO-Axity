package modelo;

public class Dulce {
    private String codigo;
    private String nombre;
    private double precio;
    private int stock;

    public Dulce(String codigo, String nombre, double precio, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public boolean hayStock() {
        return stock > 0;
    }

    public void reducirStock() {
        if (hayStock()) {
            stock--;
        }
    }


    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - $%.2f (Stock: %d)",
                codigo, nombre, precio, stock);
    }
}