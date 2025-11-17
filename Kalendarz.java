import java.util.Map;
import java.time.*;
import javax.swing.*;
import java.awt.*;


class Kalendarz extends JDialog {
    private YearMonth miesiac;
    private Map<LocalDate, Integer> historia;
    private int oczekiwana;

    public Kalendarz(JFrame rodzic, int oczekiwana) {
        super(rodzic, "Kalendarz miesięczny", false);
        miesiac = YearMonth.now();
        historia = Zapis.wczytaj_();
        this.oczekiwana=oczekiwana;
        

        setSize(500, 400);
        setLocationRelativeTo(rodzic);
        setLayout(new BorderLayout());

        JPanel kalendarzPanel = new JPanel(new GridLayout(0, 7));
        dodajDni(kalendarzPanel);
        kalendarzPanel.setBackground(new Color(173, 187, 148));
        add(kalendarzPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    


    private void dodajDni(JPanel panel) {
        panel.removeAll();
            String[] dniTygodnia = {"Pn", "Wt", "Śr", "Cz", "Pt", "So", "Nd"};
    for (String dzien : dniTygodnia) {
        JLabel naglowek = new JLabel(dzien, SwingConstants.CENTER); 
        naglowek.setFont(naglowek.getFont().deriveFont(Font.BOLD));
        panel.add(naglowek);
    }
        LocalDate pierwszy = miesiac.atDay(1);
        int przesuniecie = (pierwszy.getDayOfWeek().getValue() + 6) % 7;


        int dni = miesiac.lengthOfMonth();

        for (int i = 0; i < przesuniecie; i++) {
            panel.add(new JLabel(""));
        }

        for (int d = 1; d <= dni; d++) {
            LocalDate data = miesiac.atDay(d);
            int ilosc = historia.getOrDefault(data, 0);
            int procent=(ilosc/oczekiwana)*100;

            JButton dzien = new JButton("<html><center>" + d + "<br>" + ilosc + " ml</center></html>");
         if (procent >= 100) {
            dzien.setBackground(new Color(136, 194, 254));
        } else if (procent >= 75) {
            dzien.setBackground(new Color(170, 209, 255)); 
        } else if (procent >= 50) {
            dzien.setBackground(new Color(200, 224, 255));         
        } else if (procent > 25) {
           dzien.setBackground(new Color(228, 239, 255));
        } else if (ilosc > 0) {
           dzien.setBackground(Color.WHITE);
        } else {
            dzien.setBackground(Color.PINK);
        }
            dzien.addActionListener(e -> JOptionPane.showMessageDialog(this,
                    "Data: " + data + "\nWypito: " + ilosc + " ml wody"));

            panel.add(dzien);
        }
    }
}

