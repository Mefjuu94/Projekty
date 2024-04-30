package UserSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LoginPanel extends JFrame implements MouseListener {
    public String LocalHost = "";
    public String User = "";

    private JRadioButton radioButton = new JRadioButton("zapisać nowego użytkownika?");

    private List<String> hostSuggestions = new ArrayList<>();
    private List<String> usernameSuggestions = new ArrayList<>();

    private JTextField hostTextField;
    private JTextField usernameTextField;
    private JComboBox<String> hostComboBox;
    private JComboBox<String> usersComboBox;

    JButton zaloguj = new JButton("Zaloguj do czatu->");
    public boolean login = false;

    public LoginPanel() throws IOException {
        super("Login Panel");
        setResizable(false);

        add(new JLabel("Podaj Ip hosta:"));
        radioButton.setVisible(true);
        radioButton.setSelected(false);
        radioButton.addMouseListener(this);

        showCookies();

        setLayout(new FlowLayout());
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/3,Toolkit.getDefaultToolkit().getScreenSize().height/3 );

        hostTextField = new JTextField(20);
        hostTextField.setText("localhost");
        hostComboBox = new JComboBox<>(hostSuggestions.toArray(new String[0]));
        hostComboBox.setEditable(true);

        hostTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String input = hostTextField.getText();
                updateHostSuggestions(input);
            }
        });

        add(hostTextField);
        add(hostComboBox);
        hostComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) hostComboBox.getSelectedItem();
                // Ustawienie tej wartości w JTextField
                hostTextField.setText(selectedOption);
            }
        });


        add(new JLabel("Podaj nazwę użytkownika:"));

        usernameTextField = new JTextField(20);
        if (usernameSuggestions != null ){
            usernameTextField.setText(usernameSuggestions.get(0));
        }
        usersComboBox = new JComboBox<>(usernameSuggestions.toArray(new String[0]));
        usersComboBox.setEditable(true);

        usernameTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String input = usernameTextField.getText();
                updateUsersSuggestions(input);
            }
        });

        add(usernameTextField);
        add(usersComboBox);
        usersComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) usersComboBox.getSelectedItem();
                // Ustawienie tej wartości w JTextField
                usernameTextField.setText(selectedOption);
            }
        });

        add(radioButton);

        zaloguj.setVisible(true);
        zaloguj.addMouseListener(this);
        add(zaloguj);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 270);
        setVisible(true);
    }

    private void updateHostSuggestions(String input) {
        List<String> filteredSuggestions = new ArrayList<>();
        for (String suggestion : hostSuggestions) {
            if (suggestion.toLowerCase().startsWith(input.toLowerCase())) {
                filteredSuggestions.add(suggestion);
            }
        }
        hostComboBox.setModel(new DefaultComboBoxModel<>(filteredSuggestions.toArray(new String[0])));
        hostComboBox.setSelectedItem(input);
        hostComboBox.setPopupVisible(true);
    }

    private void updateUsersSuggestions(String input) {
        List<String> filteredSuggestions = new ArrayList<>();
        for (String suggestion : usernameSuggestions) {
            if (suggestion.toLowerCase().startsWith(input.toLowerCase())) {
                filteredSuggestions.add(suggestion);
            }
        }
        usersComboBox.setModel(new DefaultComboBoxModel<>(filteredSuggestions.toArray(new String[0])));
        usersComboBox.setSelectedItem(input);
        usersComboBox.setPopupVisible(true);
    }

    private void showCookies() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("Cookies.txt"));
        String line = "";
        while ((line = bufferedReader.readLine()) != null){
            if (line.startsWith("user")){
                String adduser = line.substring(5);
                System.out.println(adduser);
                usernameSuggestions.add(adduser);
            }
            if (line.startsWith("host")){
                String addhost = line.substring(5);
                System.out.println(addhost);
                hostSuggestions.add(addhost);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (e.getSource() == zaloguj) {
            if (radioButton.isSelected()) {
                // jak jest zaznaczony to:
                User = usernameTextField.getText();
                LocalHost = hostTextField.getText();
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("Cookies.txt"));

                    for (int i = 0; i < usernameSuggestions.size(); i++) {
                    writer.write("user=" + usernameSuggestions.get(i));
                    writer.newLine();
                    }
                    writer.write("user=" +usernameTextField.getText());
                    writer.newLine();
                    for (int i = 0; i < hostSuggestions.size(); i++) {
                        writer.write("host=" + hostSuggestions.get(i));
                        writer.newLine();
                    }
                    writer.close();

            } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                // jak NIE JEST ZAZANZCONY TO:
                User = usernameTextField.getText();
                LocalHost = hostTextField.getText();
            }
            login = true;
            this.dispose();
            System.out.println("loguje!");
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
