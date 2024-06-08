import org.jpl7.Term;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CostDiscountManager {
    public static void handleCostDiscountMenu(PrologManager prologManager, Scanner scanner) {
        boolean is_running = true;
        while (is_running) {
            System.out.println("\nGestão de Custos e Descontos:");
            System.out.println("1. Ver Custos de Envio");
            System.out.println("2. Adicionar Custo de Envio");
            System.out.println("3. Modificar Custo de Envio");
            System.out.println("4. Remover Custo de Envio");
            System.out.println("5. Ver Descontos de Categoria");
            System.out.println("6. Adicionar Desconto de Categoria");
            System.out.println("7. Modificar Desconto de Categoria");
            System.out.println("8. Remover Desconto de Categoria");
            System.out.println("9. Ver Descontos de Lealdade");
            System.out.println("10. Adicionar Desconto de Lealdade");
            System.out.println("11. Modificar Desconto de Lealdade");
            System.out.println("12. Remover Desconto de Lealdade");
            System.out.println("0. Voltar ao Menu Principal\n");

            System.out.print(">> ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    List<Term[]> shippingCosts = prologManager.getShippingCosts();
                    printShippingCosts(shippingCosts);
                    break;
                case 2:
                    addShippingCost(prologManager, scanner);
                    break;
                case 3:
                    updateShippingCost(prologManager, scanner);
                    break;
                case 4:
                    removeShippingCost(prologManager, scanner);
                    break;
                case 5:
                    List<Term[]> categoryDiscounts = prologManager.getCategoryDiscounts();
                    printCategoryDiscounts(categoryDiscounts);
                    break;
                case 6:
                    addCategoryDiscount(prologManager, scanner);
                    break;
                case 7:
                    updateCategoryDiscount(prologManager, scanner);
                    break;
                case 8:
                    removeCategoryDiscount(prologManager, scanner);
                    break;
                case 9:
                    List<Term[]> loyaltyDiscounts = prologManager.getLoyaltyDiscounts();
                    printLoyaltyDiscounts(loyaltyDiscounts);
                    break;
                case 10:
                    addLoyaltyDiscount(prologManager, scanner);
                    break;
                case 11:
                    updateLoyaltyDiscount(prologManager, scanner);
                    break;
                case 12:
                    removeLoyaltyDiscount(prologManager, scanner);
                    break;
                case 0:
                    is_running = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void printShippingCosts(List<Term[]> costs) {
        for (Term[] cost : costs) {
            System.out.println("Distrito: " + cost[0]);
            System.out.println("Custo: " + cost[1]);
            System.out.println("----------------------------");
        }
    }

    private static void addShippingCost(PrologManager prologManager, Scanner scanner) {
        System.out.print("Distrito: ");
        String district = scanner.nextLine();

        System.out.print("Valor do Custo de Envio: ");
        double value = scanner.nextDouble();

        prologManager.addShippingCost(district, value);
        System.out.println("Custo de envio adicionado com sucesso!");
    }

    private static void updateShippingCost(PrologManager prologManager, Scanner scanner) {
        System.out.print("Distrito: ");
        String district = scanner.nextLine();

        System.out.print("Novo Valor do Custo de Envio: ");
        double value = scanner.nextDouble();

        prologManager.updateShippingCost(district, value);
        System.out.println("Custo de envio modificado com sucesso!");
    }

    private static void removeShippingCost(PrologManager prologManager, Scanner scanner) {
        System.out.print("Distrito: ");
        String district = scanner.nextLine();

        prologManager.removeShippingCost(district);
        System.out.println("Custo de envio removido com sucesso!");
    }

    private static void printCategoryDiscounts(List<Term[]> discounts) {
        for (Term[] discount : discounts) {
            System.out.println("Categoria: " + discount[0]);
            System.out.println("Desconto: " + discount[1] + "%");
            System.out.println("----------------------------");
        }
    }

    private static void addCategoryDiscount(PrologManager prologManager, Scanner scanner) {
        System.out.print("Categoria: ");
        String category = scanner.nextLine();

        System.out.print("Valor do Desconto de Categoria: ");
        double value = scanner.nextDouble();

        prologManager.addCategoryDiscount(category, value);
        System.out.println("Desconto de categoria adicionado com sucesso!");
    }

    private static void updateCategoryDiscount(PrologManager prologManager, Scanner scanner) {
        System.out.print("Categoria: ");
        String category = scanner.nextLine();

        System.out.print("Novo Valor do Desconto de Categoria: ");
        double value = scanner.nextDouble();

        prologManager.updateCategoryDiscount(category, value);
        System.out.println("Desconto de categoria modificado com sucesso!");
    }

    private static void removeCategoryDiscount(PrologManager prologManager, Scanner scanner) {
        System.out.print("Categoria: ");
        String category = scanner.nextLine();

        prologManager.removeCategoryDiscount(category);
        System.out.println("Desconto de categoria removido com sucesso!");
    }

    private static void printLoyaltyDiscounts(List<Term[]> discounts) {
        for (Term[] discount : discounts) {
            System.out.println("Lealdade: " + discount[0]);
            System.out.println("Desconto: " + discount[1] + "%");
            System.out.println("----------------------------");
        }
    }

    private static void addLoyaltyDiscount(PrologManager prologManager, Scanner scanner) {
        System.out.print("Anos de Lealdade: ");
        int loyalty = scanner.nextInt();

        System.out.print("Valor do Desconto de Lealdade: ");
        double value = scanner.nextDouble();

        prologManager.addLoyaltyDiscount(loyalty, value);
        System.out.println("Desconto de lealdade adicionado com sucesso!");
    }

    private static void updateLoyaltyDiscount(PrologManager prologManager, Scanner scanner) {
        System.out.print("Anos de Lealdade: ");
        int loyalty = scanner.nextInt();

        System.out.print("Novo Valor do Desconto de Lealdade: ");
        double value = scanner.nextDouble();

        prologManager.updateLoyaltyDiscount(loyalty, value);
        System.out.println("Desconto de lealdade modificado com sucesso!");
    }

    private static void removeLoyaltyDiscount(PrologManager prologManager, Scanner scanner) {
        System.out.print("Anos de Lealdade: ");
        int loyalty = scanner.nextInt();

        prologManager.removeLoyaltyDiscount(loyalty);
        System.out.println("Desconto de lealdade removido com sucesso!");
    }
}
