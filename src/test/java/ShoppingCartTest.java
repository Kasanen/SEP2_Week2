import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class ShoppingCartTest {

    @Test
    void mainCalculatesEnglishTotalForMultipleItems() {
        String input = String.join("\n",
                "1",  // En
                "2",  // item amount
                "10", // item 1 price
                "2",  // item 1 quantity
                "5",  // item 2 price
                "3"   // item 2 quantity
        ) + "\n";

        String output = runShoppingCartWithInput(input);

        assertTrue(output.contains("Enter the number of items to purchase:"));
        assertTrue(output.contains("Total cost: 35"));
    }

    @Test
    void mainCalculatesSingleItemCostCorrectly() {
        String input = String.join("\n",
                "1",  // En
                "1",  // item amount
                "15", // item price
                "4"   // item quantity
        ) + "\n";

        String output = runShoppingCartWithInput(input);

        // Individual item cost: 15 × 4 = 60
        assertTrue(output.contains("Total cost: 60"));
    }

    @Test
    void mainCalculatesTotalCostWithDifferentPrices() {
        String input = String.join("\n",
                "1",  // En
                "3",  // item amount
                "20", // item 1 price
                "1",  // item 1 quantity
                "30", // item 2 price
                "2",  // item 2 quantity
                "10", // item 3 price
                "5"   // item 3 quantity
        ) + "\n";

        String output = runShoppingCartWithInput(input);

        
        assertTrue(output.contains("Total cost: 130"));
    }

    @Test
    void mainCalculatesCartCostWithZeroQuantityItem() {
        String input = String.join("\n",
                "1",  // En
                "2",  // item amount
                "25", // item 1 price
                "0",  // item 1 quantity
                "50", // item 2 price
                "2"   // item 2 quantity
        ) + "\n";

        String output = runShoppingCartWithInput(input);

        assertTrue(output.contains("Total cost: 100"));
    }

    @Test
    void mainCalculatesCartCostWithHighValues() {
        String input = String.join("\n",
                "1",  // En
                "2",  // item amount
                "999", // item 1 price 
                "10",  // item 1 quantity
                "500", // item 2 price
                "20"   // item 2 quantity
        ) + "\n";

        String output = runShoppingCartWithInput(input);

        assertTrue(output.contains("Total cost: 19990"));
    }

    private String runShoppingCartWithInput(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream testOutBytes = new ByteArrayOutputStream();

        try (PrintStream testOut = new PrintStream(testOutBytes, true, StandardCharsets.UTF_8)) {
            System.setIn(testIn);
            System.setOut(testOut);
            ShoppingCart.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        return testOutBytes.toString(StandardCharsets.UTF_8);
    }
}
