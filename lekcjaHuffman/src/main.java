//biore jakiesw slowo i wrzucam do tablicy char
//twortzer hasshmape znakow ktore qwyspeuja i zliczam ich wystepowanie
//sortowanie od najmniej do najwiecej wystepowan
//z pososrtowanych znakow utowrzyc drzewo binarne
//lewa strona to jest 0 a prawa to 1

import java.util.HashMap;

public class main {
    public static void main(String[] args) {

        String jakiesSlowo = "kiedysprzeczytamwszystkieksiazkiswiata";


        char[] slowoChar = jakiesSlowo.toCharArray();

        // wejsciowe dane i rodzaj klucza
        HashMap<Character, Integer> pojedynczeZnaki = new HashMap<>();

        for (int i = 0; i < slowoChar.length; i++) {
            char znak = slowoChar[i];
            int frequency = 0;
            for (int j = 0; j < slowoChar.length; j++) {
                if (znak == slowoChar[j]){
                    frequency++;
                }
                pojedynczeZnaki.put(znak,frequency);
            }
            //System.out.println("znak: " + znak + " ilolsc wystapien = " + frequency);
        }

        System.out.println(pojedynczeZnaki.size());
        System.out.println(pojedynczeZnaki.entrySet());


        String tab = pojedynczeZnaki.toString();
        System.out.println("-------- sortowanie----------");

        System.out.println(jakiesSlowo);

        char[] tablica = tab.toCharArray();

        char[] tablicaLiter = new char[tab.length()/5];
        char[] tablicaWystapien = new char[tab.length()/5];

        char[] tablicaLiterPosort = new char[tab.length()/5];
        char[] tablicaWystapienPosort = new char[tab.length()/5];

        int litera = 1;
        int lczba = 3;
        for (int i = 0; i < tablica.length/5; i++) {
            tablicaLiter[i] = tablica[litera];
            tablicaWystapien[i] = tablica[lczba];

            lczba += 5;
            litera += 5;
        }

        int zmienna = 1;
        for (int j = 0; j < tablicaWystapien.length; j++) {



            for (int i = tablicaWystapien.length - 1; i > -1; i--) {
                //System.out.println(tablicaLiter[i] + " " + tablicaWystapien[i]);

                String zmiennaChar = String.valueOf(zmienna);
                String tablicawystapienChar = String.valueOf(tablicaWystapien[i]);
                if (tablicawystapienChar.equals(zmiennaChar)) {

                    System.out.println("znak: " + tablicaLiter[i] + " ilosc wystepowan = "  + zmiennaChar);
                    tablicaLiterPosort[i] = tablicaLiter[i];
                    tablicaWystapienPosort[i] = tablicaWystapien[i];
                }
            }
            zmienna++;
        }

        //dodanie do mampy z kluczem
        System.out.println("-----");

//////////???????????????????????



    }
}
