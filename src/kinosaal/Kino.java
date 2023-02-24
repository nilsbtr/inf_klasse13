package kinosaal;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Kino implements ActionListener {
    private JFrame window = new JFrame();
    private JPanel panel1;
    private JPanel panel2;
    private JButton[][] fields;
    private boolean editing = true;
    private char[][] saal;
    Farben f = new Farben();

    public Kino() {
        try (BufferedReader reader = new BufferedReader(new FileReader("assets/Export.txt"))) {
            String[] size = reader.readLine().split(" ");
            createWindow(Integer.parseInt(size[0]), Integer.parseInt(size[1]));
            bildImportieren(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Kino(int x, int y) {
        createWindow(x, y);
    }

    private void createWindow(int x, int y) {
        fields = new JButton[y][x];
        saal = new char[y][x]; 

        window.setSize(1600, 800);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new GridLayout(1, 0));

        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(fields.length, fields[0].length));
        window.add(panel1);

        panel2 = new JPanel();
        panel2.setLayout(new GridLayout(2, 2));
        window.add(panel2);

        int z = 1;

        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                fields[i][j] = new JButton("");
                panel1.add(fields[i][j]);
                fields[i][j].setBackground(Color.WHITE);
                fields[i][j].setText(Integer.toString(z));
                fields[i][j].addActionListener(this);
                z++;
            }
        }

        createControls();
        window.setVisible(true);
    }
    
    public boolean istFrei(int x, int y, int a) {
        syncToChar();
        
        int max = saal[0].length;
        int anzahl = 0;
        int pos = x;
        
        while (pos >= 0) {
            if(saal[y][pos] == 'F') {
                anzahl++;
                pos--;
            } else {
                pos--;
            }
        }
        
        pos = x + 1;
        
        while (pos < max) {
            if (saal[y][pos] == 'F') {
                anzahl++;
                pos++;
            } else {
                pos = max;
            }
        }
        
        return anzahl >= a;
    }
    
    public void syncToChar() {
        for (int y = 0; y < fields.length; y++) {
            for (int x = 0; x < fields[y].length; x++) {
                saal[y][x] = f.toCharCode(fields[y][x].getBackground());
            }
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        for (int y = 0; y < fields.length; y++) {
            for (int x = 0; x < fields[y].length; x++) {
                if (e.getSource() == fields[y][x]) {
                    if (editing) {
                        fields[y][x].setBackground(f.getNextColor(fields[y][x].getBackground()));
                    } else {
                        
                    }
                }
            }
        }
    }
    
    private void bildExportieren() throws IOException {
        String s = "";
        
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                s += f.toCharCode(fields[i][j].getBackground());
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("assets/Export.txt"))) {
            writer.write(fields[0].length + " " + fields.length);
            writer.write("\n" + s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    private void bildImportieren(String s) {
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                char c = s.charAt(Integer.parseInt(fields[i][j].getText()) - 1);
                fields[i][j].setBackground(f.toColorCode(c));
            }
        }
    }

    private void createControls() {
        
    }

    public static void main(String[] args) {
        Kino k = new Kino();
    }
}
