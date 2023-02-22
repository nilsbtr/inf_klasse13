import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Gui implements ActionListener {
    private JFrame window = new JFrame();
    private JButton button;
    private JTextField field;
    private JLabel text;
    public boolean bpressed = false;

    public Gui() {
        window.setSize(500, 300);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);

        button = new JButton("Start");
        button.setBounds(10, 60, 100, 25);
        button.addActionListener(this);
        window.add(button);

        field = new JTextField();
        field.setBounds(10, 10, 470, 40);
        window.add(field);

        text = new JLabel("");
        text.setBounds(10, 95, 470, 500);
        text.setVerticalAlignment(JLabel.TOP);
        window.add(text);

        window.setVisible(true);
        button.requestFocus();
    }

    public String getText() {
        bpressed = false;
        return field.getText();
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public void actionPerformed(ActionEvent e) {
        bpressed = true;
    }

    public static void main(String[] args) {
        Gui g = new Gui();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
