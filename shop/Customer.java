package shop;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Creates new Customer per csv
// Includes constructor which is used to create Customer in Live Mode
public class Customer {

	// initialize variables
	private String name;
	private double budget;
	private ArrayList<ProductStock> shoppingList;

	public Customer(String fileName) {
		shoppingList = new ArrayList<>();
		List<String> lines = Collections.emptyList();
		try {
			lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
			// delimiter is comma
			// first line includes name and budget and is different to the rest
			String[] firstLine = lines.get(0).split(",");
			name = firstLine[0];
			budget = Double.parseDouble(firstLine[1]);
			// removing at index 0 as it is the only one treated differently
			lines.remove(0);
			// go through lines and assign to shoppinglist
			for (String line : lines) {
				String[] arr = line.split(",");
				String name = arr[0];
				int quantity = Integer.parseInt(arr[1].trim());
				Product p = new Product(name, 0);
				ProductStock s = new ProductStock(p, quantity);
				shoppingList.add(s);
			}

		}

		catch (IOException e) {

			e.printStackTrace();
		}
	}

	// setters and getters
	public String getName() {
		return name;
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public ArrayList<ProductStock> getShoppingList() {
		return shoppingList;
	}

	// toString
	@Override
	public String toString() {
		String ret = "Customer [name=" + name + ", budget=" + budget + ", shoppingList=\n";
		for (ProductStock productStock : shoppingList) {
			ret += productStock.getProduct().getName() + " Quantity: " + productStock.getQuantity() + "\n";
		}
		return ret + "]";
	}

	public static void main(String[] args) {
		Customer Tara = new Customer("src/shop/customer.csv");
		System.out.println(Tara);
	}

	// Constructor (used by LiveMode)
	public Customer(String name, double budget, ArrayList<ProductStock> shoppingList) {
		super();
		this.name = name;
		this.budget = budget;
		this.shoppingList = shoppingList;
	}

}
