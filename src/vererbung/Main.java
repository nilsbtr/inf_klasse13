package vererbung;
 
public class Main {
    public static void main(String[] args) {
       Tier tier = new Tier();
       Hund hund = new Hund();

       // Subtyping
       Tier thund = new Hund();
       thund.sprechen();

       // Typecast
       Hund hund2 = (Hund) thund;
       hund2.fangen();

       // Polymorphie
       tier.sprechen();
       hund.sprechen();
    }
}