public class Konstruktory {

  public static void main(String[] args) throws InterruptedException {
    Szkola szkolaPodstawowa = new Szkola("5 w Rybniku", 20);
    Szkola liceum = new Szkola("Liceum nr 1 w Rybniku", 20);
    Szkola liceumBezUczniow = new Szkola("Liceum nr 1 w Raciborzu");

    System.out.println("nazwa szkolaPodstawowej" + szkolaPodstawowa.nazwa);
    System.out.println("nazwa liceum" + liceum.nazwa);

    liceum.dodajUcziow(10);

  }
}

class Szkola {
  int iloscUczniow;
  String nazwa;

  public Szkola(String nazwa, int iloscUczniow) {
    this.iloscUczniow = iloscUczniow;
    this.nazwa = nazwa;
  }

  public Szkola(String nazwa) {
    this.iloscUczniow = 0;
    this.nazwa = nazwa;
  }

  public void dodajUcziow(int liczbaUczniow) {
    this.iloscUczniow += iloscUczniow;
  }

  public void dodajUcziow(int liczbaUczniow, int liczbaChlopcow) {
    this.iloscUczniow += iloscUczniow;
  }
}
