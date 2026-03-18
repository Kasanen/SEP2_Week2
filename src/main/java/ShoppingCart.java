import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ShoppingCart {
    public static void main(String[] args) {
        //HashMap <Integer, Integer> shoppingCart = new HashMap<Integer, Integer>();

        // Language selection
        System.out.println("Select a language:");
        System.out.println("1. English");
        System.out.println("2. Finnish");
        System.out.println("3. Swedish");
        System.out.println("4. Japanese");

        Scanner scanner = new Scanner(System.in);
        int choice = Integer.parseInt(scanner.nextLine().trim());

        Locale locale;
        switch (choice) {
            case 1:
                locale = new Locale("en", "US");
                break;
            case 2:
                locale = new Locale("fi", "FI");
                break;
            case 3:
                locale = new Locale("sv", "SE");
                break;
            case 4:
                locale = new Locale("ja", "JP");
                break;
            default:
                System.out.println("Invalid choice. Defaulting to English.");
                locale = new Locale("en", "US");
                break;
        }

        // Resource Bundle
        ResourceBundle rb;
        try {
            rb = ResourceBundle.getBundle("messagesa", locale);
        } catch (Exception e) {
            System.out.println("Error loading language pack. Defaulting to English.");
            rb = ResourceBundle.getBundle("messagesa", new Locale("en", "US"));
        }

        // Item amount
        System.out.println(rb.getString("askItemAmount"));
        int itemAmount = Integer.parseInt(scanner.nextLine().trim());

        // Asking user for price + quantity
        int totalCost = 0;

        for(int i = 0; i < itemAmount ; i++ ){
            System.out.println(rb.getString("askPrice"));
            int itemPrice = Integer.parseInt(scanner.nextLine().trim());

            System.out.println(rb.getString("askQuantity"));
            int itemQuantity = Integer.parseInt(scanner.nextLine().trim());

            totalCost += (itemPrice * itemQuantity);
        }

        // Output total cost
        System.out.println(rb.getString("total") + " " + totalCost);

        scanner.close();
    }
}
