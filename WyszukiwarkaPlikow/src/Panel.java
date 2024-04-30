import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;

public class Panel extends JPanel {

    Szukaj lookFor = new Szukaj(this);
    boolean searchStop = true;
    ImageIcon lupa = new ImageIcon("src/images/lupa.png");
    ImageIcon szukaj = new ImageIcon("src/images/771.gif");
    JButton search = new JButton(lupa);


    JTextField WHATDiskTextField = new JTextField(10);
    String fileD = "";

    JTextArea textArea = new JTextArea(10, 1);
    ScrollPane scrollPane = new ScrollPane();

    String myExtends;

    Thread searchThread = new Thread();
    JProgressBar progressBar = new JProgressBar(0, 100);
    JLabel aktualnieSzukany = new JLabel("Aktualnie szukany:");

    JPanel mainPanel = new JPanel(new BorderLayout());
    JPanel NORTHPANNEL = new JPanel(new FlowLayout());

    JPanel orientacja = new JPanel();
    JLabel ktoryDysk = new JLabel("Który Dysk, lub ścieżka?: ");
    JLabel domyslnie = new JLabel("(domyślnie D:\\)");

    JPanel coSzukane = new JPanel();
    JLabel szukajLabel = new JLabel("Szukaj:");

    JPanel prawyPasek = new JPanel();
    JPanel prawyPasek1 = new JPanel();
    JPanel prawyPasek2 = new JPanel();
    JPanel prawyPasek3 = new JPanel();

    JPanel centerPanel = new JPanel();

    JLabel przeskanowanePliki = new JLabel("Przeskanowane pliki: " + lookFor.liczbaPlikow);
    JLabel przeskanowaneFoldery = new JLabel("Przeskanowane foldery: " + lookFor.liczbaFolderow);
    JLabel jakiePliki = new JLabel("Szukam Plików: " + Arrays.toString(lookFor.szukamRozszerzen));
    JLabel zgodnePliki = new JLabel("Zgodne pliki: " + lookFor.zgodnePliki);
    JTextField rozszerzenie = new JTextField(10);

    JPanel panel;
    Runnable SearchFile;
    long numberOfFIles;

    Panel() {
        panel = this;
        this.add(mainPanel);

        File file = new File("D:\\");
        numberOfFIles = countFiles(file);

        System.out.println(numberOfFIles);


        System.out.println(getPreferredSize());
        mainPanel.setSize(new Dimension(getPreferredSize()));

        mainPanel.add(NORTHPANNEL, BorderLayout.NORTH);
        NORTHPANNEL.add(orientacja);
        NORTHPANNEL.add(coSzukane);
        NORTHPANNEL.add(search);
        NORTHPANNEL.setLayout(new GridLayout());
        NORTHPANNEL.setPreferredSize(new Dimension(getPreferredSize().width - 30, 100));

        Border borderNorth = new TitledBorder("Search Panel");
        NORTHPANNEL.setBorder(borderNorth);


        orientacja.setLayout(new GridLayout(3, 1));
        coSzukane.setLayout(new GridLayout(2, 1));
        orientacja.setAlignmentY(TOP_ALIGNMENT);
        orientacja.setAlignmentX(LEFT_ALIGNMENT);
        coSzukane.setAlignmentY(TOP_ALIGNMENT);
        coSzukane.setAlignmentX(LEFT_ALIGNMENT);
        search.setAlignmentX(RIGHT_ALIGNMENT);

        orientacja.add(ktoryDysk);
        orientacja.add(domyslnie);
        orientacja.add(WHATDiskTextField);

        coSzukane.add(szukajLabel);
        coSzukane.add(lookFor.comboBox);
        lookFor.comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == lookFor.comboBox) {  ///wypisz na nowo rozszerzenie
                    lookFor.indexOfComboBox = lookFor.comboBox.getSelectedIndex();
                    lookFor.setIndexOfComboBox(lookFor.comboBox.getSelectedIndex());
                    lookFor.szukamRozszerzen = lookFor.wybor[lookFor.indexOfComboBox];
                    jakiePliki.setText("Szukam Plików + " + Arrays.toString(lookFor.szukamRozszerzen));
                }
            }
        });
        lookFor.comboBox.setVisible(true);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        Border centerPanelBorder = new TitledBorder("results");
        centerPanel.setBorder(centerPanelBorder);

        centerPanel.add(scrollPane);
        scrollPane.setPreferredSize(new Dimension(getPreferredSize().width - 270, getPreferredSize().height - 180));
        scrollPane.add(textArea);

        mainPanel.add(prawyPasek, BorderLayout.EAST);
        prawyPasek.setLayout(new BoxLayout(prawyPasek, BoxLayout.Y_AXIS));

        Border prawypasekBorder = new TitledBorder("Control Panel");
        prawyPasek.setBorder(prawypasekBorder);
        prawyPasek.setPreferredSize(new Dimension(230, 300));


        Border pasekBorder1 = new TitledBorder("Files");
        Border pasekBorder2 = new TitledBorder("Extends");
        Border pasekBorder3 = new TitledBorder("progress");
        prawyPasek.add(prawyPasek1);
        prawyPasek1.setBorder(pasekBorder1);
        prawyPasek.add(prawyPasek2);
        prawyPasek2.setLayout(new FlowLayout());
        prawyPasek2.setBorder(pasekBorder2);
        prawyPasek.add(prawyPasek3);
        prawyPasek3.setBorder(pasekBorder3);

        prawyPasek1.add(przeskanowanePliki);
        prawyPasek1.add(przeskanowaneFoldery);
        prawyPasek1.add(jakiePliki);
        prawyPasek2.add(lookFor.checkBox);
        prawyPasek2.add(rozszerzenie);


        prawyPasek3.setLayout(new BoxLayout(prawyPasek3, BoxLayout.Y_AXIS));
        progressBar.setPreferredSize(new Dimension(200, 80));
        prawyPasek3.setAlignmentX(CENTER_ALIGNMENT);
        prawyPasek3.add(progressBar);
        prawyPasek3.add(aktualnieSzukany);
        prawyPasek3.add(lookFor.przeszukiwany);
        progressBar.setStringPainted(true);
        prawyPasek3.add(zgodnePliki);

        lookFor.checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rozszerzenie.setVisible(lookFor.checkBox.isSelected());
            }
        });

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(searchStop);
                KtoryDysk();
                kasujTablice();
                search.setIcon(szukaj);

                SearchFile = () -> {
                    lookFor.setZgodnePliki(0);
                    if (lookFor.checkBox.isSelected()) {
                        System.out.println(lookFor.zgodnePliki);
                        myExtends = rozszerzenie.getText();
                        lookFor.SzukajWlasne(myExtends, fileD);
                        System.out.println("szukam moje");
                        repaint();
                    } else {
                        lookFor.zgodnePliki = 0;
                        lookFor.Szukaj(fileD);
                        System.out.println("szukam ogól");
                        repaint();
                    }
                };

                progressBar.setStringPainted(true);
                progressBar.setMaximum((int) numberOfFIles);
                startProgressBar();

                searchThread = new Thread(SearchFile);
                if (searchStop) {
                    searchThread.start();
                    searchStop = !searchStop;
                    System.out.println("czy watek zyje? ( nie ma )" + searchThread.isAlive());
                } else {
                    searchThread.interrupt();
                    System.out.println("czy watek zyje? ( nie ma )" + searchThread.isAlive());
                    search.setIcon(lupa);
                    searchStop = !searchStop;
                }


            }
        });


    }

    public static long countFiles(File directory) {
        long count = 0;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    count += countFiles(file); // Rekurencyjnie przeszukaj podkatalogi
                } else {
                    count++; // Zwiększ liczbę plików
                    if (count % 1000 == 0) {
                        System.out.println("1000!");
                    }
                }
            }
        }
        return count;
    }

    private void startProgressBar() {
        // Tworzenie wątku, który będzie aktualizował postęp paska co 100 ms
        Thread thread = new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                final long progress = lookFor.liczbaPlikow;
                SwingUtilities.invokeLater(() -> progressBar.setValue((int) progress)); // Aktualizuj pasek postępu w wątku interfejsu użytkownika
                try {
                    Thread.sleep(100); // Poczekaj 100 ms
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        thread.start(); // Uruchom wątek
    }

    public void Metoda() {

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

//        for (int i = 0; i < lookFor.znalezionyPlik.size(); i++) {
//            String clean = lookFor.znalezionyPlik.get(i).replaceAll("\s", ""); // spacje
//
//            textArea.append(clean + "\n");
//        }

        try {
            searchThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (!searchThread.isAlive()) {
            search.setIcon(lupa);
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
            File file = new File(fileD);
            // tutaj prosgress bar ale nie wiadomo ile elementów
            // a oblicza długo
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(650, 500);
    }
}
