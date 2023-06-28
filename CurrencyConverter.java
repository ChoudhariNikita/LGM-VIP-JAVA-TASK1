import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class CurrencyConverter extends JFrame {
    private JComboBox<String> fromCurrencyComboBox;
    private JComboBox<String> toCurrencyComboBox;
    private JTextField amountTextField;
    private JTextField resultTextField;

    private String[] currencies = { "USD", "EUR", "GBP", "JPY", "INR" };

    private double[][] conversionRatesArr = {
            { 1.0, 0.85, 0.73, 110.12, 74.29 }, // USD to other currencies
            { 1.18, 1.0, 0.86, 130.55, 87.91 }, // EUR to other currencies
            { 1.38, 1.16, 1.0, 151.67, 101.93 }, // GBP to other currencies
            { 0.0091, 0.0077, 0.0066, 1.0, 0.67 }, // JPY to other currencies
            { 0.013, 0.011, 0.009, 1.49, 1.0 } // INR to other currencies
    };

    public CurrencyConverter() {
        super("Currency Converter");

        // JPanel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        // Set margin around the panel
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Create components
        JLabel fromLabel = new JLabel("From:");
        JLabel toLabel = new JLabel("To:");
        JLabel amountLabel = new JLabel("Amount:");
        fromCurrencyComboBox = new JComboBox<>(currencies);
        toCurrencyComboBox = new JComboBox<>(currencies);
        amountTextField = new JTextField(10);
        JButton convertButton = new JButton("Convert");
        resultTextField = new JTextField(10);
        resultTextField.setEditable(false);

        // Set font and size for components
        Font font = new Font("Arial", Font.PLAIN, 20);
        fromLabel.setFont(font);
        toLabel.setFont(font);
        amountLabel.setFont(font);
        fromCurrencyComboBox.setFont(font);
        toCurrencyComboBox.setFont(font);
        amountTextField.setFont(font);
        convertButton.setFont(font);
        resultTextField.setFont(font);

        // Add components to the panel
        panel.add(fromLabel);
        panel.add(fromCurrencyComboBox);
        panel.add(toLabel);
        panel.add(toCurrencyComboBox);
        panel.add(amountLabel);
        panel.add(amountTextField);
        panel.add(convertButton);
        panel.add(resultTextField);

        // Set action listener for the convert button
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertCurrency();
            }
        });

        // Set the panel as the content pane of the frame
        setContentPane(panel);

        // Center the window on the screen
        setLocationRelativeTo(null);
    }

    private void convertCurrency() {
        String fromCurr = (String) fromCurrencyComboBox.getSelectedItem();
        String toCurr = (String) toCurrencyComboBox.getSelectedItem();
        double amount = Double.parseDouble(amountTextField.getText());

        int fromIndex = getIndexForCurrency(fromCurr);
        int toIndex = getIndexForCurrency(toCurr);

        double converRate = conversionRatesArr[fromIndex][toIndex];

        double convertedAmt = amount * converRate;

        DecimalFormat df = new DecimalFormat("#.##");
        String resultval = df.format(convertedAmt);

        String resultWithCurrency = resultval + " " + toCurr;
        resultTextField.setText(resultWithCurrency);
    }

    private int getIndexForCurrency(String currency) {
        for (int i = 0; i < currencies.length; i++) {
            if (currencies[i].equals(currency)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CurrencyConverter app = new CurrencyConverter();

                // Adjust the window size
                app.setSize(550, 450);
                app.setResizable(false);

                // Center the window on the screen
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (screenSize.width - app.getWidth()) / 2;
                int y = (screenSize.height - app.getHeight()) / 2;
                app.setLocation(x, y);

                app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                app.setVisible(true);
            }
        });
    }
}
