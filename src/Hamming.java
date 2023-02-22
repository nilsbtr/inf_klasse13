import dstrukturen.DynArray;

public class Hamming {
    private char xOR(char a, char b) {
        return a == b ? '0' : '1';
    }

    public String hammingCode(String s) {
        return "";
    }

    public static void main(String[] args) {
        Hamming h = new Hamming();
        h.hammingPruefen("0110011");
    }

    public String hammingPruefen(String s) {
        
        char p0 = xOR(xOR(s.charAt(2), s.charAt(4)), s.charAt(6));
        char p1 = xOR(xOR(s.charAt(2), s.charAt(5)), s.charAt(6));
        char p2 = xOR(xOR(s.charAt(4), s.charAt(5)), s.charAt(6));

        p0 = xOR(p0, s.charAt(0));
        p1 = xOR(p1, s.charAt(1));
        p2 = xOR(p2, s.charAt(3));

        String pp = "" + p2 + p1 + p0;
        int pos = Integer.parseInt(pp, 2) - 1;
        if (pos == -1) return s;
        System.out.println("Fehler an der Stelle: " + pos);

        String r = "";
        for (int i = 0; i < s.length(); i++) {
            if (pos != i) {
                r += s.charAt(i);
            } else {
                r += Character.getNumericValue(s.charAt(i)) + 1 % 2;
            }
        }

        return r;
    }

    public String eanErstellen(String s) {
        int a = 0;
        int b = 0;

        for (int i = 0, j = 1; j < 12; i += 2, j += 2) {
            a += Character.getNumericValue(s.charAt(i));
            b += Character.getNumericValue(s.charAt(j));
        }

        return s + (10 - ((a + (3 * b)) % 10));
    }

    public boolean eanPruefen(String s) {
        return eanErstellen(s.substring(0, 12)).equals(s);
    }

    public String eanErstellen2(String s) {
        DynArray<Integer> darr = new DynArray<>();

        for (int i = 0; i < s.length(); i++) {
            darr.append(Character.getNumericValue(s.charAt(i)));
        }

        // darr@0 = ungreade, darr@1 = gerade

        while (darr.getLength() > 2) {
            darr.setItem(0, darr.getItem(0) + darr.getItem(2));
            darr.delete(2);
            darr.setItem(1, darr.getItem(1) + darr.getItem(2));
            darr.delete(2);
        }

        return s + (10 - ((darr.getItem(0) + (3 * darr.getItem(1))) % 10));
    }

    public boolean eanPruefen2(String s) {
        return eanErstellen2(s.substring(0, 12)).equals(s);
    }
}