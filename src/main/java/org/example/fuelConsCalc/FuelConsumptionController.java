package org.example.fuelConsCalc;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

import org.example.fuelConsCalc.service.LocalizationService;

public class FuelConsumptionController {

    @FXML private VBox rootVBox;
    @FXML private Label lblTitle;
    @FXML private Label lblDistance;
    @FXML private Label lblFuel;
    @FXML private TextField txtDistance;
    @FXML private TextField txtFuel;
    @FXML private Button btnCalculate;
    @FXML private Label lblResult;

    private Locale currentLocale = new Locale("en", "UK");
    private Map<String, String> localizedStrings;
    /**
     * Initialize the controller - called automatically after FXML loading
     */
    @FXML
    public void initialize() {
        // Set initial language
        setLanguage(currentLocale);

        // Add listeners to clear result when input changes
        txtDistance.textProperty().addListener((obs, oldVal, newVal) -> lblResult.setText(""));
        txtFuel.textProperty().addListener((obs, oldVal, newVal) -> lblResult.setText(""));
    }

    /**
     * Language button handlers
     */
    @FXML
    public void onENClick(ActionEvent e) { setLanguage(new Locale("en", "UK")); }

    @FXML
    public void onFRClick(ActionEvent e) { setLanguage(new Locale("fr", "FR")); }

    @FXML
    public void onJPClick(ActionEvent e) { setLanguage(new Locale("ja", "JP")); }

    @FXML
    public void onFAClick(ActionEvent e) { setLanguage(new Locale("fa", "IR")); }

    /**
     * Calculate BMI button handler
     */
    @FXML
    public void onCalculateClick(ActionEvent e) {
        try {
            double distance = Double.parseDouble(txtDistance.getText());
            double fuel = Double.parseDouble(txtFuel.getText());

            if (distance <= 0 || fuel <= 0) {
                lblResult.setText(localizedStrings.getOrDefault("error_invalid_input", "Please enter valid numbers"));
                return;
            }

            double calculation = (fuel / distance) * 100;


                String result = String.format(
                    currentLocale,
                    localizedStrings.getOrDefault("calc_result", "fuel consumption: %.2f L / 100 km"),
                    calculation
                );
            lblResult.setText(result);

        } catch (NumberFormatException ex) {
            lblResult.setText(localizedStrings.getOrDefault("error_invalid_input", "Please enter valid numbers"));
        }
    }

    /**
     * Set the application language
     */
    private void setLanguage(Locale locale) {
        currentLocale = locale;
        lblResult.setText(""); // Clear previous result

        // Load localized strings
        localizedStrings = LocalizationService.getLocalizedStrings(locale);

        // Update all UI text
        lblTitle.setText(localizedStrings.getOrDefault("title", "Consumption Calculator"));
        lblDistance.setText(localizedStrings.getOrDefault("lblDistance", "Distance (km):"));
        lblFuel.setText(localizedStrings.getOrDefault("lblFuel", "Fuel (L)):"));
        txtDistance.setPromptText(localizedStrings.getOrDefault("distance", "Enter distance"));
        txtFuel.setPromptText(localizedStrings.getOrDefault("fuel", "Enter fuel"));
        btnCalculate.setText(localizedStrings.getOrDefault("calculate", "Calculate"));

        // Apply text direction based on language
        applyTextDirection(locale);
    }

    /**
     * Apply LTR or RTL layout direction
     */
    private void applyTextDirection(Locale locale) {
        // Step 1: Detect if the language is RTL
        String lang = locale.getLanguage();
        boolean isRTL = lang.equals("fa")   // Persian
                || lang.equals("ur")   // Urdu
                || lang.equals("ar")   // Arabic
                || lang.equals("he");  // Hebrew

        // Step 2: Wrap UI changes in Platform.runLater() for thread safety
        Platform.runLater(() -> {
            // Step 3: Set NodeOrientation on the root VBox
            if (rootVBox != null) {
                rootVBox.setNodeOrientation(
                        isRTL ? NodeOrientation.RIGHT_TO_LEFT
                                : NodeOrientation.LEFT_TO_RIGHT
                );
            }

            // Step 4: Align text inside TextFields
            String alignment = isRTL ? "-fx-text-alignment: right; -fx-alignment: center-right;"
                    : "-fx-text-alignment: left; -fx-alignment: center-left;";
            txtDistance.setStyle(alignment);
            txtFuel.setStyle(alignment);
        });
    }
}