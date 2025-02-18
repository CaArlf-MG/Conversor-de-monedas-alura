import java.util.Map;
import java.util.Scanner;

public class CurrencyConverter {
    private static final String[] currencies = {"USD", "MXN", "ARS", "CLP", "COP", "BRL"};
    private static ExchangeRateService exchangeRateService = new CurrencyConverterAPI();

    public static void main(String[] args) {
        runMenu();
    }
   //--------Menu en loop---------
    public static void runMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Sistema de Conversión de Monedas ===");
            System.out.println("Seleccione una opción:");
            System.out.println("1. USD a Peso Mexicano (MXN)");
            System.out.println("2. Peso Mexicano (MXN) a USD");
            System.out.println("3. USD a Peso Argentino (ARS)");
            System.out.println("4. Peso Argentino (ARS) a USD");
            System.out.println("5. USD a Peso Chileno (CLP)");
            System.out.println("6. Peso Chileno (CLP) a USD");
            System.out.println("7. USD a Peso Colombiano (COP)");
            System.out.println("8. Peso Colombiano (COP) a USD");
            System.out.println("9. USD a Real Brasileño (BRL)");
            System.out.println("10. Real Brasileño (BRL) a USD");
            System.out.println("11. Salir");
            System.out.print("Ingrese su opción: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número entre 1 y 11.");
                continue;
            }

            if (choice == 11) {
                System.out.println("Saliendo del sistema...");
                break;
            }

            if (choice < 1 || choice > 11) {
                System.out.println("Opción inválida. Por favor, elija una opción entre 1 y 11.");
                continue;
            }

            System.out.print("Ingrese la cantidad a convertir: ");
            double amount;
            try {
                amount = Double.parseDouble(scanner.nextLine());
                if (amount < 0) {
                    System.out.println("La cantidad debe ser un número positivo.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Cantidad inválida. Por favor, ingrese un número válido.");
                continue;
            }

            // ----para establcer las monedas de origen y destino --------
            String from = "";
            String to = "";
            switch (choice) {
                case 1:
                    from = "USD";
                    to = "MXN";
                    break;
                case 2:
                    from = "MXN";
                    to = "USD";
                    break;
                case 3:
                    from = "USD";
                    to = "ARS";
                    break;
                case 4:
                    from = "ARS";
                    to = "USD";
                    break;
                case 5:
                    from = "USD";
                    to = "CLP";
                    break;
                case 6:
                    from = "CLP";
                    to = "USD";
                    break;
                case 7:
                    from = "USD";
                    to = "COP";
                    break;
                case 8:
                    from = "COP";
                    to = "USD";
                    break;
                case 9:
                    from = "USD";
                    to = "BRL";
                    break;
                case 10:
                    from = "BRL";
                    to = "USD";
                    break;
                default:
                    System.out.println("Opción no implementada.");
                    continue;
            }

            // -----Lineas para realizar la conversion-----
            try {
                convertCurrency(from, to, amount);
            } catch (Exception e) {
                System.out.println("Error al realizar la conversión: " + e.getMessage());
            }
        }
        scanner.close();
    }

    public static void convertCurrency(String from, String to, double amount) throws Exception {
        // ----Para obtener las tasas de cambio de origen --------
        Map<String, Double> exchangeRates = exchangeRateService.getExchangeRates(from);

        if (!exchangeRates.containsKey(to)) {
            throw new RuntimeException("La moneda de destino no está soportada.");
        }

        double rate = exchangeRates.get(to);
        double convertedAmount = amount * rate;

        System.out.printf("Monto convertido: %.2f %s\n", convertedAmount, to);
    }
}
