import modelo.Dulce;
import modelo.Venta;
import servicio.MaquinaVendedora;
import servicio.SistemaVentas;
import excepcion.FondosInsuficientesException;
import excepcion.StockInsuficienteException;
import java.util.Scanner;

public class Main {
    
    private static final String CODIGO_ADMIN = "A1A1B2C1";
    
    public static void main(String[] args) {
        MaquinaVendedora maquina = inicializarMaquina(new SistemaVentas());
        Scanner scanner = new Scanner(System.in);

        System.out.println("¡Bienvenido a la Máquina Vendedora de Dulces!\n");

        while (true) {
            maquina.mostrarInventario();
            
            System.out.print("Ingrese el código del dulce (o 'salir' para terminar): ");
            String codigo = scanner.nextLine().trim().toUpperCase();

            if (codigo.equals("SALIR")) {
                System.out.println("\n¡Gracias por usar nuestra máquina! Hasta pronto.\n");
                break;
            }

            if (codigo.equals(CODIGO_ADMIN)) {
                maquina.getGestorVentas().mostrarReporteVentas(maquina.getInventario());
                continue;
            }

            Dulce dulce = maquina.obtenerDulce(codigo);
            
            if (dulce == null) {
                System.out.println("Código inválido.\n");
                continue;
            }

            try {
                maquina.verificarDisponibilidad(codigo);
                
                System.out.printf("Precio: $%.2f\n", dulce.getPrecio());
                double montoPagado = leerDouble(scanner);

                Venta venta = maquina.comprarDulce(codigo, montoPagado);
                
                System.out.println("\n¡Compra exitosa!");
                System.out.println("Producto: " + venta.getDulce().getNombre());
                System.out.printf("Su cambio: $%.2f\n\n", venta.getCambio());
                
            } catch (StockInsuficienteException | FondosInsuficientesException e) {
                System.out.println("\n" + e.getMessage() + "\n");
            }
        }
        
        scanner.close();
    }

    private static MaquinaVendedora inicializarMaquina(SistemaVentas sistemaVentas) {
        MaquinaVendedora maquina = new MaquinaVendedora(sistemaVentas);
        
        maquina.agregarDulce(new Dulce("A1", "Snickers", 15.50, 10));
        maquina.agregarDulce(new Dulce("A2", "KitKat", 14.00, 8));
        maquina.agregarDulce(new Dulce("B1", "Skittles", 12.00, 15));
        maquina.agregarDulce(new Dulce("B2", "Panditas", 10.50, 20));
        maquina.agregarDulce(new Dulce("C1", "Halls", 8.00, 25));
        maquina.agregarDulce(new Dulce("C2", "Vero Mango", 5.00, 0));
        
        return maquina;
    }

    private static double leerDouble(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Ingrese el monto a pagar: $");
                double valor = Double.parseDouble(scanner.nextLine().trim());
                if (valor >= 0) return valor;
                System.out.println("El monto no puede ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número válido.");
            }
        }
    }
}