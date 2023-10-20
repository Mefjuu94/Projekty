import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {




        Scanner scaner = new Scanner(System.in);
        System.out.println("Podaj lcizbę:");

        int liczba = scaner.nextInt();
        List<Integer> lista = new ArrayList<>();
        int fibonacci = 0;


        for (int i = 0; i < liczba + 1 ; i++) {

            if (i == 0){
                fibonacci = 0;
                lista.add(i);
            } else if (i == 1) {
                fibonacci = 1;
                lista.add(i);
            }else if (i == 2) {
                    fibonacci = 1;
                    lista.add(i);
            }else {
                fibonacci = lista.get(0) + lista.get(1);
                lista.add(fibonacci);
            }

            System.out.println( " nr przejscia = " + i + " == " + fibonacci);
            System.out.println("----------------");
            if (lista.size() > 2) {
                lista.remove(0);
            }

            System.out.println(lista + " lista");
        }

        System.out.println("jeśli twoja liczba to " + liczba + " to wynosi ona " + fibonacci);



    }
}


