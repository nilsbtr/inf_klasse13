public class Hamming {
    private char xOR(char a, char b) {
        return a == b ? '0' : '1';
    }

    public String hammingCode(String s) {
        char p0 = xOR(xOR(s.charAt(2), s.charAt(4)), s.charAt(6));
        char p1 = xOR(xOR(s.charAt(2), s.charAt(5)), s.charAt(6));
        char p2 = xOR(xOR(s.charAt(4), s.charAt(5)), s.charAt(6));

        p0 = xOR(p0, s.charAt(0));
        p1 = xOR(p1, s.charAt(1));
        p2 = xOR(p2, s.charAt(3));

        String p = "" + p2 + p1 + p0;
        int x = Integer.parseInt(p, 2);
        System.out.println("Fehler an der Stelle: " + x);

        String r = "";
        for (int i = 0; i < s.length(); i++) {
            if (x - 1 != i) {
                r += s.charAt(i);
            } else {
                r += Integer.parseInt(String.valueOf(s.charAt(i))) + 1 % 2;
            }
        }

        return r;
    }

    public void articleNumber() {

    }
}
