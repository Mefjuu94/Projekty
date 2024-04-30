import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Szukaj implements ActionListener {

    ArrayList<String> znalezionyKatalog = new ArrayList<>();
    ArrayList<String> znalezionyPlik = new ArrayList<>();

    ArrayList<String> SciezkaDoPliku = new ArrayList<>();

    String[] defaultOptcions = {"Filmy", "Muzyka", "Obrazy", "Dokumenty"};
    JComboBox<String> comboBox = new JComboBox<>(defaultOptcions);
    JLabel przeszukiwany = new JLabel("tekst");

    public int getZgodnePliki() {
        return zgodnePliki;
    }

    public void setZgodnePliki(int zgodnePliki) {
        this.zgodnePliki = zgodnePliki;
    }

    int zgodnePliki= 0 ;

    public void setIndexOfComboBox(int indexOfComboBox) {
        this.indexOfComboBox = indexOfComboBox;
    }

    int indexOfComboBox = 0;

    {
        comboBox.addActionListener(this);
    }

    JRadioButton checkBox = new JRadioButton("Własne roszerzenie");
    JRadioButton wlasnaNazwa = new JRadioButton();

    int liczbaPlikow = 0;
    int liczbaFolderow = 0;
    String[] muzyka = {".mp3"};
    String[] video = {".avi", ".mkv", ".mp4"};
    String[] obrazy = {".jpg", ".png"};
    String[] dokumenty = {".pdf", ".doc"};
    String[][] wybor = {video, muzyka, obrazy, dokumenty};

    public String[] getSzukamRozszerzen() {
        return szukamRozszerzen;
    }

    String[] szukamRozszerzen = wybor[indexOfComboBox];
    Panel panel;

    Szukaj(Panel panel) {
        this.panel = panel;
    }

    public void Szukaj(String sciezka) {

        File file = new File(sciezka);
        String[] lista = file.list();
//        System.out.println(sciezka);

        if (lista != null) {

            for (int i = 0; i < Objects.requireNonNull(lista).length; i++) {
                String pojedynczy = lista[i];

                String nowaSciezka = file + "\\" + pojedynczy + "\\";

                File nazwa = new File(nowaSciezka);


                if (nazwa.isDirectory() && !nazwa.isHidden()) {
                    znalezionyKatalog.add(nowaSciezka);
                    liczbaFolderow++;
                    panel.przeskanowaneFoldery.setText("Przeskanowane foldery: " + liczbaFolderow);
                    Szukaj(nowaSciezka);
                } else if (nazwa.isFile()) {
                    String nazwaPliku = nazwa.getName();
//                    System.out.println("nazwaPLiku = " + nazwaPliku);
                    przeszukiwany.setText(nazwaPliku);
                    liczbaPlikow++;
                    panel.przeskanowanePliki.setText("Przeskanowane pliki: " + liczbaPlikow);
                    if (panel.searchStop){
                        break;
                    }
                    for (int j = 0; j < wybor[indexOfComboBox].length; j++) {
                        if (nazwaPliku.endsWith(wybor[indexOfComboBox][j])) {
                            SciezkaDoPliku.add(nowaSciezka);
                            znalezionyPlik.add(nazwaPliku);
                            zgodnePliki++;
                            panel.zgodnePliki.setText("Zgodne pliki: " + zgodnePliki);
                            String clean = nazwaPliku.replaceAll("\s", "");
                            panel.textArea.append(zgodnePliki + ". " +clean + "\n");
                        }
                    }
                }
            }
        }

        panel.zgodnePliki.setText("Zgodne pliki: " + zgodnePliki);
        panel.przeskanowaneFoldery.setText("Przeskanowane foldery: " + liczbaFolderow);
        panel.przeskanowanePliki.setText("Przeskanowane pliki: " + liczbaPlikow);

    }


    public void SzukajWlasne(String rozszerzenie, String sciezka) {

        File file = new File(sciezka);
        String znalezione = Arrays.toString(file.list());
        String[] pliki;

        pliki = znalezione.split(",");

        for (int i = 0; i < Objects.requireNonNull(pliki).length; i++) {
            String pojedynczy = pliki[i];

            String nowaSciezka1 = file + "\\" + pojedynczy + "\\";
            String nowaSciezka = nowaSciezka1.replaceAll("\s", "");

            File nazwa = new File(nowaSciezka);
            String nazwaPliku = nazwa.getName();
            String duze = nazwaPliku.toUpperCase();
            String male = nazwaPliku.toLowerCase();

            if (nazwa.isDirectory() && !nazwa.isHidden()) {
                znalezionyKatalog.add(nowaSciezka);
                liczbaFolderow++;
//                System.out.println("folder: " + nowaSciezka);
                SzukajWlasne(rozszerzenie, nowaSciezka);
                panel.jakiePliki.setText(nowaSciezka);
            } else if (nazwa.isFile()) {
                liczbaPlikow++;
//                System.out.println(nazwa);
//                System.out.println("plik: " + nazwaPliku);
                panel.jakiePliki.setText(nazwaPliku);

                if (duze.endsWith(rozszerzenie) || male.endsWith(rozszerzenie)) {
                    znalezionyPlik.add(nazwaPliku);
                    zgodnePliki++;
                    panel.zgodnePliki.setText("Zgodne pliki: " + zgodnePliki);
                    String clean = nazwaPliku.replaceAll("\s", "");
                    panel.textArea.append(zgodnePliki + ". " +clean + "\n");
                }
            }
        }
        panel.zgodnePliki.setText("Zgodne pliki: " + zgodnePliki);
        panel.przeskanowaneFoldery.setText("Przeskanowane foldery: " + liczbaFolderow);
        panel.przeskanowanePliki.setText("Przeskanowane pliki: " + liczbaPlikow);
    }


    public void SzukajWlasnejNazwy(String wlasne, String sciezka) {

        File file = new File(sciezka);
        String znalezione = Arrays.toString(file.list());
        String[] pliki;

        pliki = znalezione.split(",");

        for (int i = 0; i < Objects.requireNonNull(pliki).length; i++) {
            String pojedynczy = pliki[i];

            String nowaSciezka1 = file + "\\" + pojedynczy + "\\";
            String nowaSciezka = nowaSciezka1.replaceAll("\s", "");

            File nazwa = new File(nowaSciezka);
            String nazwaPliku = nazwa.getName();
            String duze = nazwaPliku.toUpperCase();
            String male = nazwaPliku.toLowerCase();

            if (nazwa.isDirectory() && !nazwa.isHidden()) {
                znalezionyKatalog.add(nowaSciezka);
                liczbaFolderow++;
//                System.out.println("folder: " + nowaSciezka);
                SzukajWlasne(wlasne, nowaSciezka);
            } else if (nazwa.isFile()) {
                liczbaPlikow++;
//                System.out.println(nazwa);
//                System.out.println("plik: " + nazwaPliku);
                if (duze.startsWith(wlasne) || male.startsWith(wlasne)) {
                    znalezionyPlik.add(nazwaPliku);
                }
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == comboBox.getSelectedItem()) {
            indexOfComboBox = comboBox.getSelectedIndex();
            setIndexOfComboBox(comboBox.getSelectedIndex());
            szukamRozszerzen = wybor[indexOfComboBox];

        }

    }
}
