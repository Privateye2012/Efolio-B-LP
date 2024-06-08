import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        PrologManager prologManager = new PrologManager("store.pl");
        Scanner scanner = new Scanner(System.in);
        System.out.println("--------------- Enchanted Emporium ---------------");

        boolean is_running = true;
        while (is_running) {
            System.out.println("\nMenu Principal:");
            System.out.println("1. Fazer Compra");
            System.out.println("2. Histórico de Vendas");
            System.out.println("3. Gestão de Inventário");
            System.out.println("4. Gestão de Custos e Descontos");
            System.out.println("5. Gestão de Clientes");
            System.out.println("0. Sair\n");

            System.out.print(">> ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    ShoppingCartManager shoppingCartManager = new ShoppingCartManager(prologManager, scanner);
                    shoppingCartManager.handleShoppingCartMenu();
                    break;
                case 2:
                    SalesHistoryManager.handleSalesHistoryMenu(prologManager, scanner);
                    break;
                case 3:
                    InventoryManager.handleInventoryMenu(prologManager, scanner);
                    break;
                case 4:
                    CostDiscountManager.handleCostDiscountMenu(prologManager, scanner);
                    break;
                case 5:
                    ClientManager.handleClientMenu(prologManager, scanner);
                    break;
                case 0:
                    is_running = false;
                    System.out.println("Saindo do programa...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
        scanner.close();
    }
}