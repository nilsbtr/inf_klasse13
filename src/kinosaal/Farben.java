package kinosaal;

import java.awt.Color;

public class Farben {
    public Farben() {

    }

    public char toCharCode(Color c) {
        if (c == Color.BLACK) {
            return 'G';
        } else if (c == Color.GREEN) {
            return 'F';
        } else if (c == Color.YELLOW) {
            return 'R';
        }  else if (c == Color.RED) {
            return 'B';
        } else if (c == Color.LIGHT_GRAY) {
            return 'D';
        } else {
            return 'Z';
        }   
    }

    public Color toColorCode(char c) {
        if (c == 'G') {
            return Color.BLACK;
        } else if (c == 'F') {
            return Color.GREEN;
        } else if (c == 'R') {
            return Color.YELLOW;
        } else if (c == 'B') {
            return Color.RED;
        } else if (c == 'D') {
            return Color.LIGHT_GRAY;
        } else {
            return Color.WHITE;
        }
    }

    public Color getNextColor(Color c) {
        if (c == Color.WHITE) {
            return Color.BLACK;
        } else if (c == Color.BLACK) {
            return Color.GREEN;
        } else if (c == Color.GREEN) {
            return Color.YELLOW;
        } else if (c == Color.YELLOW) {
            return Color.RED;
        }  else if (c == Color.RED) {
            return Color.LIGHT_GRAY;
        } else if (c == Color.LIGHT_GRAY) {
            return Color.WHITE;
        } else {
            return Color.WHITE;
        }
    }
}