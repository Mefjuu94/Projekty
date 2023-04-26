public class klasy {
    public static void main(String[] args) {
        //Wielokat w = new Wielokat();
        Prostokat p = new Prostokat(2,3);
        p.wypiszPole();

        Kwadrat k = new Kwadrat(5);
        k.wypiszPole();
    }

    //
}


class Wielokat {
    public Wielokat() {
        System.out.println("Konstruktor - wielokat");
    }

    public Wielokat(int p) {
        System.out.println("inny konstruktor");
    }

    public void publicznaMetoda() {
        System.out.println("Publiczna metoda - wielokat");
    }

    public void publicznaMetoda(int p) {

    }

    protected void chronionaMetoda() {
        System.out.println("Chroniona metoda - wielokat");
    }

    private void prywatnaMetoda() {
        System.out.println("Prywatna metoda - wielokat");
    } // musi wywołać się tylko w obrębie tej klasy!
}

class Prostokat extends Wielokat {
    protected int a;
    protected int b;
    public Prostokat(int a, int b) {
        this.a = a;
        this.b = b;
        System.out.println("Konstruktor - prostokat");
    }

    public void wypiszPole() {
        System.out.println(a*b);
    }

    public void publicznaMetoda() {
        System.out.println("Publiczna metoda - prostokat");
    }
}

class Kwadrat extends Prostokat {
    public Kwadrat(int a) {
        super(a, a);
    }
}