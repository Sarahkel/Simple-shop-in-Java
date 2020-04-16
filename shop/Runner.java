package shop;

import java.util.ArrayList;
import java.util.Scanner;

// Asks user in which mode they want to operate
// Creates new customer
// Calls process Order Method
public class Runner {

	public static void main(String[] args) {
		// create and stock the shop
		Shop shop = new Shop("src/shop/stock.csv");

		// initialize variables
		String choice;
		ArrayList<ProductStock> shoppingList;

		// create the scanner to take in user input
		// once order is processed, start from top until user presses 3 (exit)
		while (true) {
			Scanner menuScan = new Scanner(System.in);

			// keep asking until valid choice was made
			while (true) {

				// Display Menu
				System.out.println("---------------");
				System.out.println(
						"Welcome! Please press 1 or 2 or 3 \n 1 - Read order from csv \n 2 - order now \n 3 - exit Shop\n -------------");
				choice = menuScan.nextLine();

				// keep asking until valid choice was made
				if (choice.contentEquals("1") || choice.contentEquals("2") || choice.contentEquals("3")) {
					break;
				} else {
					System.out.println("Sorry, this is not a valid choice. Try again.");
				}

			}

			// Print the information
			System.out.println("You chose " + choice + "\n\n");

			// Add if statement to process according to choice
			// 2 = Live Mode
			if (choice.contentEquals("2")) {
				shoppingList = new ArrayList<>();

				// ask the user for their name
				System.out.println("What is your name?");
				String name = menuScan.nextLine();

				// find out how much money they have
				System.out.println("How much money do you have?");
				double budget = menuScan.nextDouble();
				String consumeNewline = menuScan.nextLine(); // consume new line character so it does not interfere with
																// input

				while (true) {

					// ask the user for what they want to buy and save as string
					System.out.println("What product do you want to buy?");
					String productName = menuScan.nextLine();

					// Ask how many they want and save as a integer
					System.out.println("How many " + productName + " do you want?");
					int amount = menuScan.nextInt();
					consumeNewline = menuScan.nextLine();

					// Add to their shoppinglist
					int quantity = amount;
					Product p = new Product(productName, 0);
					ProductStock s = new ProductStock(p, quantity);
					shoppingList.add(s);

					// Ask if they want to add more
					System.out.println("Do you want to buy anything else? Type y or n ?");
					String yesno = menuScan.nextLine();

					if (yesno.contentEquals("y")) {
						continue;
					} else {
						break;
					}
				}

				System.out.println("Thank you, let me fetch your items");
				// Use constructor to create new Customer with the above values
				Customer custLive = new Customer(name, budget, shoppingList);
				System.out.println(custLive);
				shop.processOrder(custLive, shop);

				// 1 = read from csv
			} else if (choice.contentEquals("1")) {
				// create new customer by feeding in csv file
				Customer Cust = new Customer("src/shop/customer.csv");
				System.out.println(Cust);
				shop.processOrder(Cust, shop);

				// 3 = exit menu
			} else if (choice.contentEquals("3")) {
				System.out.println("Thank you for your custom. See you next time");
				break;
			}
			// will start again from the top
			System.out.println("\n\n ------- \n Thank you. Anything else we can do for you?");

		}

	}

}
