import org.jpl7.Term;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InventoryManager {
    public static void handleInventoryMenu(PrologManager prologManager, Scanner scanner) {
        boolean is_running = true;
        while (is_running) {
            System.out.println("\nGestão de Inventário:");
            System.out.println("1. Ver Itens em Inventário");
            System.out.println("2. Ver Itens em Inventário por Categoria");
            System.out.println("3. Adicionar Item");
            System.out.println("4. Modificar Item");
            System.out.println("5. Remover Item");
            System.out.println("0. Voltar ao Menu Principal\n");

            System.out.print(">> ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    List<Term[]> inventoryItems = prologManager.getInventoryItems();
                    printInventoryItems(inventoryItems);
                    break;
                case 2:
                    System.out.print("Categoria: ");
                    String category = scanner.nextLine();
                    List<Term[]> inventoryItemsByCategory = prologManager.getInventoryItemsByCategory(category);
                    printInventoryItems(inventoryItemsByCategory);
                    break;
                case 3:
                    addItem(prologManager, scanner);
                    break;
                case 4:
                    updateItem(prologManager, scanner);
                    break;
                case 5:
                    removeItem(prologManager, scanner);
                    break;
                case 0:
                    is_running = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void printInventoryItems(List<Term[]> items) {
        for (Term[] item : items) {
            System.out.println("ID: " + item[0]);
            System.out.println("Nome: " + item[1]);
            System.out.println("Categoria: " + item[2]);
            System.out.println("Custo: " + item[3]);
            System.out.println("Quantidade em Inventário: " + item[4]);
            System.out.println("----------------------------");
        }
    }

    private static void addItem(PrologManager prologManager, Scanner scanner) {
        System.out.print("ID do Item: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nome do Item: ");
        String name = scanner.nextLine();

        System.out.print("Categoria do Item: ");
        String category = scanner.nextLine();

        System.out.print("Custo do Item: ");
        double cost = scanner.nextDouble();

        System.out.print("Quantidade em Inventário: ");
        int inventory = scanner.nextInt();

        prologManager.addItem(id, name, category, cost, inventory);
        System.out.println("Item adicionado com sucesso!");
    }

    private static void updateItem(PrologManager prologManager, Scanner scanner) {
        System.out.print("ID do Item: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nome do Item: ");
        String name = scanner.nextLine();

        System.out.print("Categoria do Item: ");
        String category = scanner.nextLine();

        System.out.print("Custo do Item: ");
        double cost = scanner.nextDouble();

        System.out.print("Quantidade em Inventário: ");
        int inventory = scanner.nextInt();

        prologManager.updateItem(id, name, category, cost, inventory);
        System.out.println("Item modificado com sucesso!");
    }

    private static void removeItem(PrologManager prologManager, Scanner scanner) {
        System.out.print("ID do Item: ");
        int id = scanner.nextInt();

        prologManager.removeItem(id);
        System.out.println("Item removido com sucesso!");
    }
}
