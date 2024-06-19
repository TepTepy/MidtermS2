import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LockerApplication extends JFrame {

    private JTextField passcodeField;
    private JButton clearButton;
    private JButton[] numberButtons;
    private JButton enterButton;
    private JLabel statusLabel;
    private String password;
    private boolean passwordSet = false;
    private StringBuilder passcodeBuilder = new StringBuilder();

    public LockerApplication() {
        super("Locker Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 350);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(4, 3, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        numberButtons = new JButton[9];
        for (int i = 0; i < 9; i++) {
            final int number = i + 1;
            numberButtons[i] = new JButton(String.valueOf(number));
            numberButtons[i].setFont(new Font("Arial", Font.PLAIN, 20));
            numberButtons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    passcodeBuilder.append(number);
                    passcodeField.setText(passcodeBuilder.toString());
                    updateStatusLabel("Enter passcode: " + passcodeBuilder.toString());
                }
            });
            buttonPanel.add(numberButtons[i]);
        }

        // Clear button
        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.PLAIN, 16));
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                passcodeBuilder.setLength(0);
                passcodeField.setText("");
                updateStatusLabel("Enter passcode:");
            }
        });
        buttonPanel.add(clearButton);

        // Empty cell
        buttonPanel.add(new JPanel()); // Empty cell

        // Enter button
        enterButton = new JButton("Enter");
        enterButton.setFont(new Font("Arial", Font.PLAIN, 16));
        enterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = passcodeField.getText().trim();

                if (!passwordSet) {
                    // Set password for the first time
                    password = input;
                    passwordSet = true;
                    updateStatusLabel("Password set successfully.");
                } else {
                    // Verify password
                    if (input.equals(password)) {
                        updateStatusLabel("Correct Password");
                    } else {
                        updateStatusLabel("Incorrect Password");
                    }
                }

                // Clear the passcode builder after each operation
                passcodeBuilder.setLength(0);
                passcodeField.setText("");
            }
        });
        buttonPanel.add(enterButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Input field and status label panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        passcodeField = new JTextField(10);
        passcodeField.setEditable(false);
        passcodeField.setFont(new Font("Arial", Font.PLAIN, 20));
        passcodeField.setHorizontalAlignment(JTextField.CENTER);
        bottomPanel.add(passcodeField, BorderLayout.NORTH);

        statusLabel = new JLabel("Enter passcode:");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(statusLabel, BorderLayout.CENTER);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void updateStatusLabel(String text) {
        statusLabel.setText(text);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LockerApplication locker = new LockerApplication();
                locker.setVisible(true);
            }
        });
    }
}