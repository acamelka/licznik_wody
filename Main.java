import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.Map;

public class Main {

    

     public static void jaki_kwiat(int procent, JLabel kwiat){
        if (procent >= 100) {
            kwiat.setIcon(new ImageIcon("zdjecia/kwiatek100.png"));
        } else if (procent >= 75) {
            kwiat.setIcon(new ImageIcon("zdjecia/kwiatek75.png"));
        } else if (procent >= 50) {
            kwiat.setIcon(new ImageIcon("zdjecia/kwiatek50.png"));
        } else if (procent >= 25) {
            kwiat.setIcon(new ImageIcon("zdjecia/kwiatek25.png"));
        } else {
            kwiat.setIcon(new ImageIcon("zdjecia/kwiatek0.png"));
        }
     }




     public static void aktualizacja(JSpinner spinner, JLabel label, Suma suma,JProgressBar zbiornik, JLabel kwiat){
       spinner.setValue(0);
        zbiornik.setValue(suma.get_procent_());
        label.setText(suma.get_procent_() + "%");
        
        jaki_kwiat(suma.get_procent_(),kwiat);

     }

    public static void main(String[] args) {
        int oczekiwana = 1750;

        Suma suma=new Suma();

        LocalDate dzisiaj = LocalDate.now();
        Zapis zapis=new Zapis();
        Map<LocalDate, Integer> historia = Zapis.wczytaj_();
        



        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("✿✿");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 500);
            frame.getContentPane().setBackground(new Color(173, 187, 148));
            frame.setLayout(null);

            
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 4000, 10));
            JComponent editor = spinner.getEditor();
            if (editor instanceof JSpinner.DefaultEditor) {
                JFormattedTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
                textField.setBackground(Color.PINK);
                textField.setForeground(Color.WHITE);
                textField.setFont(new Font("Arial", Font.BOLD, 16));
            }
            spinner.setBounds(350, 150, 75, 30);
            frame.add(spinner);

            
            JButton ok = new JButton("OK");
            ok.setBackground(Color.PINK);
            ok.setForeground(Color.WHITE);
            ok.setBounds(350, 180, 75, 30);
            frame.add(ok);

            ImageIcon kalendarzIcon = new ImageIcon("zdjecia/kalendarz.png");
            JButton kalendarz = new JButton(kalendarzIcon);
           
            kalendarz.setBounds(350, 50, 90, 90);
            frame.add(kalendarz);
            
            
             kalendarz.addActionListener(e -> new Kalendarz(frame, oczekiwana));

            
            JProgressBar zbiornik = new JProgressBar(SwingConstants.VERTICAL, 0, 100);
            zbiornik.setStringPainted(false);
            zbiornik.setForeground(new Color(50, 164, 168));
            zbiornik.setBounds(10, 100, 50, 200);
            frame.add(zbiornik);

            
            JLabel label = new JLabel("0%");
            label.setFont(new Font("Arial", Font.BOLD, 20));
            label.setBounds(20, 30, 250, 100);
            label.setForeground(Color.BLACK);
            frame.add(label);

            
            ImageIcon konewkaIcon = new ImageIcon("zdjecia/konewka.png");
            JLabel konewka = new JLabel(konewkaIcon);
            konewka.setBounds(280, 300, 100, 121);
            

            
            ImageIcon kwiat0Icon = new ImageIcon("zdjecia/kwiatek0.png");
            JLabel kwiat = new JLabel(kwiat0Icon);
            kwiat.setBounds(100, 100, 161, 300);
            frame.add(kwiat);


            if(historia.containsKey(dzisiaj)){
                suma.set_suma_((int)historia.get(dzisiaj),oczekiwana);
                aktualizacja(spinner,  label,  suma, zbiornik,  kwiat);
        }




            
            Animacja panel = new Animacja();
            panel.setBounds(0, 0, 500, 500);
            panel.setKonewka(konewka);
            frame.add(panel);

            
            ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int ile =(int)spinner.getValue();
                    suma.dodaj_(ile, oczekiwana);
                    zapis.zapiszdzisiaj_(dzisiaj, suma.get_suma_());
                    
                    panel.startAnimacja();
                   Timer opoznienie = new Timer(1000, new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
        
         aktualizacja(spinner,  label,  suma, zbiornik,  kwiat);

        
    }
});
opoznienie.setRepeats(false);
opoznienie.start();
                   
                }
            });

            frame.setVisible(true);
        });
    }
}
