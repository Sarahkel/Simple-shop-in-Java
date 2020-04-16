package shop;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Creates shop
//includes method to process orders
public class Shop {

	private double cash;
	private ArrayList<ProductStock> stock;

	// shop is created by feeding in csv
	public Shop(String fileName) {
		stock = new ArrayList<>();
		List<String> lines = Collections.emptyList();
		try {
			// read cash from first line, different to other lines
			lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
			System.out.println(lines.get(0));
			// stocking shop with cash
			cash = Double.parseDouble(lines.get(0));
			// removing at index 0 as it is the only one treated differently
			lines.remove(0);
			// assign stock by looping through the rest of the file
			for (String line : lines) {
				String[] arr = line.split(",");
				String name = arr[0];
				double price = Double.parseDouble(arr[1]);
				int quantity = Integer.parseInt(arr[2].trim());
				Product p = new Product(name, price);
				ProductStock s = new ProductStock(p, quantity);
				stock.add(s); // adds at end higher level than c, stores it internally
			}

		}

		catch (IOException e) {

			e.printStackTrace();
		}

	}

	// setters and getters
	public double getCash() {
		return cash;
	}

	public ArrayList<ProductStock> getStock() {
		return stock;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public void setStock(ArrayList<ProductStock> stock) {
		this.stock = stock;
	}

	// toString
	@Override
	public String toString() {
		return "Shop [cash=" + cash + ", stock=" + stock + "]";
	}

	public static void main(String[] args) {
		Shop shop = new Shop("src/shop/stock.csv");
	}

	// method to find price
	private double findPrice(String name) {

		for (ProductStock productStock : stock) {
			Product p = productStock.getProduct();
			if (p.getName().equals(name)) {
				return p.getPrice();
			}
		}

		return -1;
	}

	// method to process an order
	public void processOrder(Customer c, Shop s) {
		System.out.println("-------- \nProcessing Order\n");
		// loop through customers shoppinglist
		for (ProductStock productStock : c.getShoppingList()) {
			// System.out.println(" Starting stock is " + s.getStock());
			Product p = productStock.getProduct();
			int quantity = productStock.getQuantity();
			double price = findPrice(p.getName());
			System.out.println("You want " + productStock.getQuantity() + " of " + p.getName());

			// verify product exists in shopstock
			if (price == -1) {
				System.out.println("We are very sorry, we do not carry " + p.getName());
				continue;
			}

			// verify customer can afford product and quantity
			double costToCustomer = price * quantity;

			double customerNewBudget = c.getBudget() - costToCustomer;
			if (customerNewBudget > 0) {
				// if customer has enough money, deduce from their budget and add to shop cash
				c.setBudget(customerNewBudget);
				s.setCash(s.getCash() + costToCustomer);

				int indexCounter = 0; // creating a counter to update/locate product

				// loop until shop and shoppinglist match
				// deduce bought quantity from shopstock
				for (ProductStock shopStock : s.getStock()) {
					Product product = shopStock.getProduct();

					if ((product.getName().contentEquals(p.getName()))) {
						int quant = shopStock.getQuantity();
						int newquant = quant - quantity; // subtracting Customer request from stock
						ProductStock updatedStock = new ProductStock(product, newquant);
						s.stock.set(indexCounter, updatedStock);

					}
					// increase counter
					indexCounter++;

				}
				//summarize
				//java math can mess things up: 
				//https://www.dummies.com/programming/java/weird-things-java-math/
				System.out.println(" New shop balance is " + s.getCash());
				System.out.println(" Customer's budget has reduced to %.1f" + customerNewBudget);
			} else {
				System.out.println(
						" We are sorry, you have insufficient budget to purchase " + quantity + " " + p.getName());
				// tell customer how much they can afford
				double amount = Math.floor(c.getBudget() / price);
				System.out.println(" The maximum amount you can purchase is " + (String.format("%.0f", amount)));
			}

		}

	}

}
