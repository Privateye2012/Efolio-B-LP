import org.jpl7.Term;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ClientManager {
    public static void handleClientMenu(PrologManager prologManager, Scanner scanner) {
        boolean is_running = true;
        while (is_running) {
            System.out.println("\nGestão de Clientes:");
            System.out.println("1. Ver Clientes");
            System.out.println("2. Ver Clientes por Distrito");
            System.out.println("3. Ver Clientes com Lealdade Acima de um Valor");
            System.out.println("4. Adicionar Cliente");
            System.out.println("5. Modificar Cliente");
            System.out.println("6. Remover Cliente");
            System.out.println("0. Voltar ao Menu Principal\n");

            System.out.print(">> ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    List<Term[]> clients = prologManager.getClients();
                    printClients(clients);
                    break;
                case 2:
                    System.out.print("Distrito: ");
                    String district = scanner.nextLine();
                    List<Term[]> clientsByDistrict = prologManager.getClientsByDistrict(district);
                    printClients(clientsByDistrict);
                    break;
                case 3:
                    System.out.print("Valor de Lealdade: ");
                    int loyaltyValue = scanner.nextInt();
                    List<Term[]> clientsByLoyalty = prologManager.getClientsWithLoyaltyAbove(loyaltyValue);
                    printClients(clientsByLoyalty);
                    break;
                case 4:
                    addClient(prologManager, scanner);
                    break;
                case 5:
                    updateClient(prologManager, scanner);
                    break;
                case 6:
                    removeClient(prologManager, scanner);
                    break;
                case 0:
                    is_running = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void printClients(List<Term[]> clients) {
        for (Term[] client : clients) {
            System.out.println("ID: " + client[0]);
            System.out.println("Nome: " + client[1]);
            System.out.println("Distrito: " + client[2]);
            System.out.println("Lealdade: " + client[3]);
            System.out.println("----------------------------");
        }
    }

    private static void addClient(PrologManager prologManager, Scanner scanner) {
        System.out.print("ID do Cliente: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nome do Cliente: ");
        String name = scanner.nextLine();

        System.out.print("Distrito do Cliente: ");
        String district = scanner.nextLine();

        System.out.print("Valor de Lealdade do Cliente: ");
        int loyaltyValue = scanner.nextInt();

        prologManager.addClient(id, name, district, loyaltyValue);
        System.out.println("Cliente adicionado com sucesso!");
    }

    private static void updateClient(PrologManager prologManager, Scanner scanner) {
        System.out.print("ID do Cliente: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nome do Cliente: ");
        String name = scanner.nextLine();

        System.out.print("Distrito do Cliente: ");
        String district = scanner.nextLine();

        System.out.print("Valor de Lealdade do Cliente: ");
        int loyaltyValue = scanner.nextInt();

        prologManager.updateClient(id, name, district, loyaltyValue);
        System.out.println("Cliente modificado com sucesso!");
    }

    private static void removeClient(PrologManager prologManager, Scanner scanner) {
        System.out.print("ID do Cliente: ");
        int id = scanner.nextInt();

        prologManager.removeClient(id);
        System.out.println("Cliente removido com sucesso!");
    }
}
