import org.jpl7.Term;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ShoppingCartManager {
    private Scanner scanner;
    private PrologManager prologManager;
    private int selectedClientId;
    private Map<Integer, Integer> cartItems;

    public ShoppingCartManager(PrologManager prologManager, Scanner scanner) {
        this.prologManager = prologManager;
        this.scanner = scanner;
        this.cartItems = new HashMap<>();
    }

    public void handleShoppingCartMenu() {
        System.out.println("Fazer Compra:");
        selectClient();
        selectItems();
        finalizePurchase();
    }

    private void selectClient() {
        System.out.println("Selecione o cliente:");
        List<Term[]> clients = prologManager.getClients();
        for (Term[] client : clients) {
            System.out.println("ID: " + client[0] + ", Nome: " + client[1]);
        }

        System.out.print("Digite o ID do cliente: ");
        selectedClientId = scanner.nextInt();
        scanner.nextLine();
    }

    private void selectItems() {
        while (true) {
            System.out.println("Selecione os itens para adicionar ao carrinho (ou digite 0 para finalizar):");
            List<Term[]> items = prologManager.getInventoryItems();
            for (Term[] item : items) {
                System.out.println("ID: " + item[0] + ", Nome: " + item[1] + ", Preço: " + item[3]);
            }

            System.out.print("Digite o ID do item: ");
            int itemId = scanner.nextInt();
            if (itemId == 0) break;

            System.out.print("Digite a quantidade: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            cartItems.put(itemId, cartItems.getOrDefault(itemId, 0) + quantity);
        }
    }

    private void finalizePurchase() {
        double totalValue = 0.0;
        double totalCategoryDiscount = 0.0;
        double totalLoyaltyDiscount = 0.0;
        double loyaltyDiscount = 0.0;
        double shippingCost = 0.0;

        for (Map.Entry<Integer, Integer> entry : cartItems.entrySet()) {
            int itemId = entry.getKey();
            int quantity = entry.getValue();

            List<Term[]> itemData = prologManager.getInventoryItems();
            for (Term[] item : itemData) {
                if (item[0].intValue() == itemId) {
                    double price = item[3].doubleValue();
                    totalValue += price * quantity;

                    String category = item[2].toString();
                    double categoryDiscount = getCategoryDiscount(category);
                    totalCategoryDiscount += (price * quantity) * (categoryDiscount / 100);

                    int newInventory = item[4].intValue() - quantity;
                    prologManager.updateItem(itemId, item[1].toString().replace("'", ""), category.replace("'", ""), price, newInventory);
                }
            }
        }

        List<Term[]> clientData = prologManager.getClients();
        for (Term[] client : clientData) {
            if (client[0].intValue() == selectedClientId) {
                int loyaltyYears = client[3].intValue();
                loyaltyDiscount = getLoyaltyDiscount(loyaltyYears);
                totalLoyaltyDiscount = totalValue * (loyaltyDiscount / 100);

                String district = client[2].toString();
                shippingCost = getShippingCost(district);
            }
        }

        double total = totalValue - totalCategoryDiscount - totalLoyaltyDiscount + shippingCost;

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        prologManager.addPurchase(selectedClientId, formatter.format(date).toString(), totalValue, totalCategoryDiscount, loyaltyDiscount, shippingCost, total);

        System.out.println("Compra realizada com sucesso!");
    }

    private double getCategoryDiscount(String category) {
        List<Term[]> discounts = prologManager.getCategoryDiscounts();
        for (Term[] discount : discounts) {
            if (discount[0].toString().equals(category)) {
                return discount[1].doubleValue();
            }
        }
        return 0.0;
    }

    private double getLoyaltyDiscount(int loyalty) {
        List<Term[]> discounts = prologManager.getLoyaltyDiscounts();
        for (Term[] discount : discounts) {
            if (discount[0].intValue() == loyalty) {
                return discount[1].doubleValue();
            }
        }
        return 0.0;
    }

    private double getShippingCost(String district) {
        List<Term[]> shippingCosts = prologManager.getShippingCosts();
        for (Term[] cost : shippingCosts) {
            if (cost[0].toString().equals(district)) {
                return cost[1].doubleValue();
            }
        }
        return 0.0;
    }
}
