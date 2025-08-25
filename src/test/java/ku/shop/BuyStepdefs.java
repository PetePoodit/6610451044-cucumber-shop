package ku.shop;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BuyStepdefs {

    private ProductCatalog catalog;
    private Order order;
    private Exception caughtException;

    @Given("the store is ready to service customers")
    public void the_store_is_ready_to_service_customers() {
        catalog = new ProductCatalog();
        order = new Order();
        caughtException = null;
    }

    @Given("a product {string} with price {float} and stock of {int} exists")
    public void a_product_exists(String name, double price, int stock) {
        catalog.addProduct(name, price, stock);
    }

    @When("I buy {string} with quantity {int}")
    public void i_buy_with_quantity(String name, int quantity) {
        Product prod = catalog.getProduct(name);
        order.addItem(prod, quantity);
    }

    @Then("total should be {float}")
    public void total_should_be(double total) {
        assertEquals(total, order.getTotal());
    }

    @When("I try to buy {string} with quantity {int}")
    public void i_try_to_buy_with_quantity(String name, int quantity) {
        try {
            Product prod = catalog.getProduct(name);
            order.addItem(prod, quantity);
            caughtException = null;
        } catch (Exception e) {
            caughtException = e;
        }
    }

    @Then("I should get an insufficient stock error for {string}")
    public void i_should_get_an_insufficient_stock_error_for(String productName) {
        assertTrue(caughtException instanceof InsufficientStockException);
        assertTrue(caughtException.getMessage().contains(productName));
    }
}
