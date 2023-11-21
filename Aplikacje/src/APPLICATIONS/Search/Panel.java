package APPLICATIONS.Search;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Panel extends JPanel {

    Szukaj lookFor = new Szukaj();

    ImageIcon lupa = new ImageIcon("src/APPLICATIONS/Search/lupa.png");
    JButton search = new JButton(lupa);

    JTextField textField = new JTextField();

    JTextField WHATDiskTextField = new JTextField();
    String fileD = "";

    JTextArea textArea = new JTextArea(10, 1);
    ScrollPane scrollPane = new ScrollPane();

    String myExtends;
    boolean WLASNEroz = false;

    public Panel() {
        this.setLayout(null);

        add(lookFor.checkBox);
        lookFor.checkBox.setText("Własne rozszerzenia");
        lookFor.checkBox.setBounds(400, 250, 150, 40);

//        add(lookFor.wlasnaNazwa);
//        lookFor.wlasnaNazwa.setText("Własna nazwa Pliku");
//        lookFor.wlasnaNazwa.setBounds(400,280,150,40);
//        lookFor.wlasnaNazwa.setVisible(true);
//        lookFor.wlasnaNazwa.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (lookFor.wlasnaNazwa.isSelected()){
//
//                }else {
//
//                }
//
//            }
//        });

        add(WHATDiskTextField);
        WHATDiskTextField.setBounds(30, 60, 200, 40);

        add(lookFor.comboBox);
        lookFor.comboBox.setBounds(300, 20, 150, 50);
        lookFor.comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == lookFor.comboBox) {  ///wypisz na nowo rozszerzenie
                    repaint();
                }
            }
        });
        lookFor.comboBox.setVisible(true);

        lookFor.checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KtoryDysk();
                kasujTablice();
                if (lookFor.checkBox.isSelected()) {
                    System.out.println("Radiobutton ON");
                    WLASNEroz = true;

                    add(lookFor.comboBox);
                    lookFor.comboBox.setBounds(300, 20, 150, 50);
                    lookFor.comboBox.setVisible(true);
                    repaint();
                } else {
                    add(textField);
                    System.out.println("Radiobutton OFF");
                    WLASNEroz = false;

                    textField.setBounds(300, 20, 150, 50);
                    textField.setVisible(true);
                    repaint();
                }
            }
        });
        lookFor.checkBox.setVisible(true);


        this.add(search);
        search.setBounds(500, 20, 100, 100);
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KtoryDysk();
                kasujTablice();
                if (lookFor.checkBox.isSelected()) {
                    myExtends = textField.getText();
                    lookFor.SzukajWlasne(myExtends, fileD);
                    repaint();
                } else {
                    lookFor.Szukaj(fileD);
                    repaint();
                }
            }
        });

        search.setVisible(true);
        add(scrollPane);
        scrollPane.setBounds(30, 140, 350, 300);
        scrollPane.add(textArea);
        scrollPane.setVisible(true);

    }

    public void Metoda() {

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        if (!WLASNEroz) {
            g2d.drawString("Szukaj:", 250, 50);
        } else {
            g2d.drawString("Wpisz własne -->", 200, 50);
        }
        g2d.drawString("Który Dysk, lub ścieżka?: ", 30, 25);
        g2d.drawString("(domyślnie D:\\)", 30, 40);

        for (int i = 0; i < lookFor.znalezionyPlik.size(); i++) {
            String clean = lookFor.znalezionyPlik.get(i).replaceAll("\s", ""); // spacje

            textArea.append(clean + "\n");
        }

        g2d.drawString("Przeskanowanie Pliki: " + lookFor.liczbaPlikow, 400, 150);
        g2d.drawString("Przeskanowanie foldery: " + lookFor.liczbaFolderow, 400, 170);
        if (lookFor.checkBox.isSelected()) {
            g2d.drawString("ROZSZERZENIE WŁASNE", 400, 220);
        } else {
            g2d.drawString("szukam plików z:" + Arrays.toString(lookFor.szukamRozszerzen), 400, 220);
        }
    }

    public void kasujTablice() {
        lookFor.znalezionyPlik.clear();
        lookFor.znalezionyKatalog.clear();
        textArea.removeAll();
        textArea.setText("");

        lookFor.liczbaPlikow = 0;
        lookFor.liczbaFolderow = 0;
    }


    public void KtoryDysk() {

        String zawiera = WHATDiskTextField.getText();

        if (WHATDiskTextField.getText().equals("")) {
            fileD = "D:\\";
        } else if (zawiera.contains(":\\")) {
            fileD = zawiera;
        } else {
            fileD = WHATDiskTextField.getText() + ":\\";
        }
    }

}


