import javax.swing.*;

public class ObjectToJson{

    String tasma1;
    String tasma2;
    String tasma3;

    //ImageIcon[][] wylosowane;

    boolean[] jakieWygrane;

    int stawka;
    int pieniadze;

    ObjectToJson(String tasma1, String tasma2, String tamsa3,
                 boolean[] jakieWygrane, int stawka, int pieniadze){
        this.tasma1 = tasma1;
        this.tasma2 = tasma2;
        this.tasma3 = tamsa3;

        //this.wylosowane = wylosowane;

        this.jakieWygrane = jakieWygrane;

        this.stawka = stawka;
        this.pieniadze = pieniadze;
    }
}
