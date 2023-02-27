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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Kino implements ActionListener {
    private JFrame window = new JFrame();
    private JPanel mPanel;
    private JPanel cPanel;

    private JButton[][] fields;
    private char[][] saal;
    Farben f = new Farben();

    private boolean editing = false;
    private JLabel displaySeat;
    int[] currSeat = new int[2];

    public Kino() {
        bildImportieren(true);
    }
    
    public Kino(int x, int y) {
        createWindow(x, y);
    }

    private void createWindow(int x, int y) {
        fields = new JButton[y][x];
        saal = new char[y][x]; 

        window.setSize(1300, 800);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);

        mPanel = new JPanel();
        mPanel.setBounds(0, 0, 800, 760);
        mPanel.setLayout(new GridLayout(fields.length, fields[0].length));
        window.add(mPanel);

        cPanel = new JPanel();
        cPanel.setBounds(800, 0, 500, 760);
        cPanel.setLayout(null);
        window.add(cPanel);


        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                fields[i][j] = new JButton("");
                mPanel.add(fields[i][j]);
                fields[i][j].setBackground(Color.WHITE);
                fields[i][j].addActionListener(this);
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

    public String getBezeichnung(int x, int y) {
        int r = 1;
        for (int i = 0; i < fields.length; i++) {
            int c = 1;
            for (int j = 0; j < fields[y].length; j++) {
                if (fields[y][j].getBackground() != Color.BLACK) {
                    if (i == y && j == x) {
                        return ("Reihe " + r + " Sitz " + c);
                    }
                    c++;
                }
            }
            if (c > 1) r++;
        }
        return null;
    }

    public void setBezeichnung() {
        int r = 1;
        for (int y = 0; y < fields.length; y++) {
            int c = 1;
            for (int x = 0; x < fields[y].length; x++) {
                if (fields[y][x].getBackground() != Color.BLACK) {
                    fields[y][x].setText("R" + r + "\nS" + c);
                    c++;
                }
            }
            if (c > 1) r++;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int y = 0; y < fields.length; y++) {
            for (int x = 0; x < fields[y].length; x++) {
                if (e.getSource() == fields[y][x]) {
                    if (editing) {
                        fields[y][x].setBackground(f.getNextColor(fields[y][x].getBackground()));
                    } else {
                        currSeat[0] = x;
                        currSeat[1] = y;
                        displaySeat.setText(getBezeichnung(x, y));
                    }
                }
            }
        }
    }

    private String bildImportieren(boolean init) {
        String pic = "";
        try (BufferedReader reader = new BufferedReader(new FileReader("assets/Export.txt"))) {
            String[] size = reader.readLine().split(" ");
            if (init) {
                createWindow(Integer.parseInt(size[0]), Integer.parseInt(size[1]));
            } else if (Integer.parseInt(size[1]) != fields.length || Integer.parseInt(size[0]) != fields[0].length) {
                return "Datei hat die falsche Größe!";
            }
            pic = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int z = 0;
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                fields[i][j].setBackground(f.toColorCode(pic.charAt(z)));
                z++;
            }
        }

        return "Import war erfolgreich!";
    }

    private String bildExportieren() {
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

        return "Export war erfolgreich!";
    }

    private void createControls() {
        JButton buttonText = new JButton("Bezeichn.");
        buttonText.setBounds(10, 10, 100, 30);
        buttonText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBezeichnung();
            }
        });
        cPanel.add(buttonText);

        JLabel displayMode = new JLabel("Aktueller Modus: Auswählen");
        displayMode.setBounds(230, 10, 260, 30);
        displayMode.setVerticalAlignment(JLabel.CENTER);
        cPanel.add(displayMode);

        JButton buttonMode = new JButton("Modus");
        buttonMode.setBounds(120, 10, 100, 30);
        buttonMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (displayMode.getText().equals("Aktueller Modus: Auswählen")) {
                    editing = true;
                    displayMode.setText("Aktueller Modus: Editieren");
                } else {
                    editing = false;
                    displayMode.setText("Aktueller Modus: Auswählen");
                }
            }
        });
        cPanel.add(buttonMode);

        JLabel displayLogging = new JLabel();
        displayLogging.setBounds(230, 50, 260, 30);
        displayLogging.setVerticalAlignment(JLabel.CENTER);
        cPanel.add(displayLogging);

        JButton buttonImport = new JButton("Import");
        buttonImport.setBounds(10, 50, 100, 30);
        buttonImport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayLogging.setText(bildImportieren(false));
            }
        });
        cPanel.add(buttonImport);

        JButton buttonExport = new JButton("Export");
        buttonExport.setBounds(120, 50, 100, 30);
        buttonExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayLogging.setText(bildExportieren());
            }
        });
        cPanel.add(buttonExport);

        displaySeat = new JLabel("Kein aktuell ausgewählter Sitz");
        displaySeat.setBounds(230, 90, 260, 30);
        displaySeat.setVerticalAlignment(JLabel.CENTER);
        cPanel.add(displaySeat);

        JLabel displayStatus = new JLabel();
        displayStatus.setBounds(10, 130, 210, 30);
        displayStatus.setVerticalAlignment(JLabel.CENTER);
        cPanel.add(displayStatus);

        JTextField fieldCount = new JTextField();
        fieldCount.setBounds(10, 90, 100, 30);
        cPanel.add(fieldCount);

        JButton buttonStart = new JButton("Buchen");
        buttonStart.setBounds(120, 90, 100, 30);
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (istFrei(currSeat[0], currSeat[1], Integer.parseInt(fieldCount.getText()))) {
                    displayStatus.setText("Die Plätzte sind frei!");
                } else {
                    displayStatus.setText("Die Plätzte sind belegt!");
                }
                
            }
        });
        cPanel.add(buttonStart);
    }

    public static void main(String[] args) {
        Kino k = new Kino();
    }
}
