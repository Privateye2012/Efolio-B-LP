import org.jpl7.Term;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SalesHistoryManager {
    public static void handleSalesHistoryMenu(PrologManager prologManager, Scanner scanner) {
        boolean is_running = true;
        while (is_running) {
            System.out.println("\nHistórico de Vendas:");
            System.out.println("1. Ver Vendas por Data");
            System.out.println("2. Ver Vendas por Cliente");
            System.out.println("3. Ver Vendas por Distrito");
            System.out.println("4. Ver Totais de Vendas por Distrito");
            System.out.println("5. Ver Totais de Vendas por Data");
            System.out.println("6. Ver Distrito com Maior Desconto");
            System.out.println("0. Voltar ao Menu Principal\n");

            System.out.print(">> ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Data (DD/MM/YYYY): ");
                    String date = scanner.nextLine();
                    List<Term[]> salesByDate = prologManager.getSalesByDate(date);
                    printSales(salesByDate);
                    break;
                case 2:
                    System.out.print("ID do Cliente: ");
                    int clientId = scanner.nextInt();
                    List<Term[]> salesByClient = prologManager.getSalesByClient(clientId);
                    printSales(salesByClient);
                    break;
                case 3:
                    System.out.print("Distrito: ");
                    String district = scanner.nextLine();
                    List<Term[]> salesByDistrict = prologManager.getSalesByDistrict(district);
                    printSales(salesByDistrict);
                    break;
                case 4:
                    System.out.print("Distrito: ");
                    String districtTotal = scanner.nextLine();
                    Map<String, Term> totalSalesByDistrict = prologManager.getTotalSalesByDistrict(districtTotal);
                    printTotalSales(totalSalesByDistrict);
                    break;
                case 5:
                    System.out.print("Data (DD/MM/YYYY): ");
                    String dateTotal = scanner.nextLine();
                    Map<String, Term> totalSalesByDate = prologManager.getTotalSalesByDate(dateTotal);
                    printTotalSales(totalSalesByDate);
                    break;
                case 6:
                    Map<String, Term> districtWithHighestDiscount = prologManager.getDistrictWithHighestDiscount();
                    System.out.println("Distrito com maior desconto: " + districtWithHighestDiscount.get("Distrito").toString());
                    break;
                case 0:
                    is_running = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void printSales(List<Term[]> sales) {
        for (Term[] sale : sales) {
            System.out.println("Cliente ID: " + sale[0]);
            System.out.println("Data: " + sale[1]);
            System.out.println("Valor: " + sale[2]);
            System.out.println("Desconto de Categoria: " + sale[3]);
            System.out.println("Desconto de Lealdade: " + sale[4]);
            System.out.println("Custo de Envio: " + sale[5]);
            System.out.println("Total: " + sale[6]);
            System.out.println("----------------------------");
        }
    }

    private static void printTotalSales(Map<String, Term> totalSales) {
        System.out.println("Valor Total: " + totalSales.get("ValorTotal"));
        System.out.println("Desconto de Categoria Total: " + totalSales.get("DescontoCategoriaTotal"));
        System.out.println("Desconto de Lealdade Total: " + totalSales.get("DescontoLealdadeTotal"));
        System.out.println("Custo de Envio Total: " + totalSales.get("CustoEnvioTotal"));
        System.out.println("Total Geral: " + totalSales.get("TotalTotal"));
    }
}
