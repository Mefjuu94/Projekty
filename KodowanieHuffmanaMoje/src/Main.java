import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        String wyraz = "Banan";
        System.out.println(wyraz);


        Map<char[], Integer> czestotliwosc = new HashMap<>();

           char c[] =  wyraz.toCharArray();

           char c1[] = new char[c.length];
           int licznikFreq= 0;

        for (int i = 0; i < c.length; i++) {
            char litera = c[i];
            for (int j = 0; j < c.length; j++) {
                if (litera == c[j]){
                    licznikFreq++;
                }
            }
            c1[i] = litera;
            licznikFreq = 0;
        }


        czestotliwosc.put(c1,czestotliwosc.getOrDefault(c,0) + 1);
        System.out.println("----------------");

        System.out.println(czestotliwosc.values());




    }
}
