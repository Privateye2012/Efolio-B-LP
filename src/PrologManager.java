import org.jpl7.*;
import java.util.*;

public class PrologManager {
    public PrologManager(String prologFilePath) {
        String consultQuery = "consult('" + prologFilePath + "')";
        Query query = new Query(consultQuery);
        if (!query.hasSolution()) {
            throw new RuntimeException("Failed to consult Prolog file.");
        }
    }

    public void addPurchase(int clientId, String date, double value, double categoryDiscount, double loyaltyDiscount, double shippingCost, double total) {
        String queryStr = String.format(
            "adicionar_compra(%d, '%s', %.2f, %.2f, %.2f, %.2f, %.2f)",
            clientId, date, value, categoryDiscount, loyaltyDiscount, shippingCost, total
        );
        Query query = new Query(queryStr);
        query.hasSolution();
    }

    public List<Term[]> getSalesByDate(String date) {
        String queryStr = String.format("vendas_por_data('%s', Vendas)", date);
        return convertListToArray(new Query(queryStr).oneSolution().get("Vendas"));
    }

    public List<Term[]> getSalesByClient(int clientId) {
        String queryStr = String.format("vendas_por_cliente(%d, Vendas)", clientId);
        return convertListToArray(new Query(queryStr).oneSolution().get("Vendas"));
    }

    public List<Term[]> getSalesByDistrict(String district) {
        String queryStr = String.format("vendas_por_distrito('%s', Vendas)", district);
        return convertListToArray(new Query(queryStr).oneSolution().get("Vendas"));
    }

    public Map<String, Term> getTotalSalesByDistrict(String district) {
        String queryStr = String.format("totais_vendas_por_distrito('%s', ValorTotal, DescontoCategoriaTotal, DescontoLealdadeTotal, CustoEnvioTotal, TotalTotal)", district);
        return new Query(queryStr).oneSolution();
    }

    public Map<String, Term> getTotalSalesByDate(String date) {
        String queryStr = String.format("totais_vendas_por_data('%s', ValorTotal, DescontoCategoriaTotal, DescontoLealdadeTotal, CustoEnvioTotal, TotalTotal)", date);
        return new Query(queryStr).oneSolution();
    }

    public Map<String, Term> getDistrictWithHighestDiscount() {
        String queryStr = "distrito_com_maior_desconto_total(Distrito)";
        return new Query(queryStr).oneSolution();
    }

    public List<Term[]> getInventoryItems() {
        String queryStr = "itens_inventario(ItensLista)";
        return convertListToArray(new Query(queryStr).oneSolution().get("ItensLista"));
    }

    public List<Term[]> getInventoryItemsByCategory(String category) {
        String queryStr = String.format("itens_inventario_por_categoria('%s', ItensLista)", category);
        return convertListToArray(new Query(queryStr).oneSolution().get("ItensLista"));
    }

    public List<Term[]> getCategories() {
        String queryStr = "categorias(CategoriaLista)";
        return convertListToArray(new Query(queryStr).oneSolution().get("CategoriaLista"));
    }

    public void addCategory(String category) {
        String queryStr = String.format("adicionar_categoria('%s')", category);
        Query query = new Query(queryStr);
        query.hasSolution();
    }

    public void updateCategory(String oldCategory, String newCategory) {
        String queryStr = String.format("modificar_categoria('%s', '%s')", oldCategory, newCategory);
        Query query = new Query(queryStr);
        query.hasSolution();
    }

    public void removeCategory(String category) {
        String queryStr = String.format("remover_categoria('%s')", category);
        Query query = new Query(queryStr);
        query.hasSolution();
    }

    public void addItem(int id, String name, String category, double cost, int inventory) {
        String queryStr = String.format(
            "adicionar_item(%d, '%s', '%s', %.2f, %d)", id, name, category, cost, inventory
        );
        Query query = new Query(queryStr);
        query.hasSolution();
    }

    public void updateItem(int id, String name, String category, double cost, int inventory) {
        String queryStr = String.format(
            "modificar_item(%d, '%s', '%s', %.2f, %d)", id, name, category, cost, inventory
        );
        Query query = new Query(queryStr);
        query.hasSolution();
    }

    public void removeItem(int id) {
        String queryStr = String.format("remover_item(%d)", id);
        Query query = new Query(queryStr);
        query.hasSolution();
    }

    public List<Term[]> getShippingCosts() {
        String queryStr = "custos_envio(CustoEnvioLista)";
        return convertListToArray(new Query(queryStr).oneSolution().get("CustoEnvioLista"));
    }

    public List<Term[]> getCategoryDiscounts() {
        String queryStr = "descontos_categoria(DescontoCategoriaLista)";
        return convertListToArray(new Query(queryStr).oneSolution().get("DescontoCategoriaLista"));
    }

    public List<Term[]> getLoyaltyDiscounts() {
        String queryStr = "descontos_lealdade(DescontoLealdadeLista)";
        return convertListToArray(new Query(queryStr).oneSolution().get("DescontoLealdadeLista"));
    }

    public void addShippingCost(String district, double cost) {
        String queryStr = String.format("adicionar_custo_envio('%s', %.2f)", district, cost);
        Query query = new Query(queryStr);
        query.hasSolution();
    }

    public void updateShippingCost(String district, double newCost) {
        String queryStr = String.format("modificar_custo_envio('%s', %.2f)", district, newCost);
        Query query = new Query(queryStr);
        query.hasSolution();
    }

    public void removeShippingCost(String district) {
        String queryStr = String.format("remover_custo_envio('%s')", district);
        Query query = new Query(queryStr);
        query.hasSolution();
    }

    public void addCategoryDiscount(String category, double discount) {
        String queryStr = String.format("adicionar_desconto_categoria('%s', %.2f)", category, discount);
        Query query = new Query(queryStr);
        query.hasSolution();
    }

    public void updateCategoryDiscount(String category, double newDiscount) {
        String queryStr = String.format("modificar_desconto_categoria('%s', %.2f)", category, newDiscount);
        Query query = new Query(queryStr);
        query.hasSolution();
    }

    public void removeCategoryDiscount(String category) {
        String queryStr = String.format("remover_desconto_categoria('%s')", category);
        Query query = new Query(queryStr);
        query.hasSolution();
    }

    public void addLoyaltyDiscount(int loyalty, double discount) {
        String queryStr = String.format("adicionar_desconto_lealdade(%d, %.2f)", loyalty, discount);
        Query query = new Query(queryStr);
        query.hasSolution();
    }

    public void updateLoyaltyDiscount(int loyalty, double newDiscount) {
        String queryStr = String.format("modificar_desconto_lealdade(%d, %.2f)", loyalty, newDiscount);
        Query query = new Query(queryStr);
        query.hasSolution();
    }

    public void removeLoyaltyDiscount(int loyalty) {
        String queryStr = String.format("remover_desconto_lealdade(%d)", loyalty);
        Query query = new Query(queryStr);
        query.hasSolution();
    }

    public List<Term[]> getClients() {
        String queryStr = "clientes(ClienteLista)";
        return convertListToArray(new Query(queryStr).oneSolution().get("ClienteLista"));
    }

    public List<Term[]> getClientsByDistrict(String district) {
        String queryStr = String.format("clientes_por_distrito('%s', ClienteLista)", district);
        return convertListToArray(new Query(queryStr).oneSolution().get("ClienteLista"));
    }

    public List<Term[]> getClientsWithLoyaltyAbove(int loyaltyThreshold) {
        String queryStr = String.format("clientes_com_lealdade_superior(%d, ClienteLista)", loyaltyThreshold);
        return convertListToArray(new Query(queryStr).oneSolution().get("ClienteLista"));
    }

    public void addClient(int id, String name, String district, int loyalty) {
        String queryStr = String.format("adicionar_cliente(%d, '%s', '%s', %d)", id, name, district, loyalty);
        Query query = new Query(queryStr);
        query.hasSolution();
    }

    public void updateClient(int id, String name, String district, int loyalty) {
        String queryStr = String.format("modificar_cliente(%d, '%s', '%s', %d)", id, name, district, loyalty);
        Query query = new Query(queryStr);
        query.hasSolution();
    }

    public void removeClient(int id) {
        String queryStr = String.format("remover_cliente(%d)", id);
        Query query = new Query(queryStr);
        query.hasSolution();
    }

    private List<Term[]> convertListToArray(Term list) {
        Term[] terms = list.listToTermArray();
        List<Term[]> result = new ArrayList<>();
        for (Term term : terms) {
            result.add(term.listToTermArray());
        }
        return result;
    }
}
