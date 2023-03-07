package vererbung;

public class Hund extends Tier {
    @Override
    public void sprechen() {
       System.out.println("Wuff!");
    }
 
    public void fangen() {
       System.out.println("Der Hund bringt den Ball zurück.");
    }
 }
