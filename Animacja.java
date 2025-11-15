import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Animacja extends JPanel {
    private Timer timer;
    private int y = 300;
    private int x = 280;
    private boolean wGore = true;
    private double kat = 0;
    private JLabel konewkaLabel;
    private BufferedImage konewkaImg;

    public Animacja() {
        setLayout(null);
        setOpaque(false);
    }

    public void setKonewka(JLabel konewka) {
        this.konewkaLabel = konewka;

        
        Icon icon = konewka.getIcon();
        if (icon instanceof ImageIcon) {
            Image img = ((ImageIcon) icon).getImage();
            konewkaImg = new BufferedImage(
                img.getWidth(null),
                img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB
            );
            Graphics2D g2d = konewkaImg.createGraphics();
            g2d.drawImage(img, 0, 0, null);
            g2d.dispose();
        }

        
        this.y = konewka.getY();
        this.x = konewka.getX();
    }

    public void startAnimacja() {
        if (konewkaImg == null || konewkaLabel == null) return;

        y = konewkaLabel.getY();
        x = konewkaLabel.getX();
        kat = 0;
        wGore = true;

        timer = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (wGore) {
                    y -= 2;
                    x -= 2;
                    kat -= 0.5; 
                    if (y <= 220) wGore = false;
                } else {
                    y += 2;
                    x += 2;
                    kat += 0.5; 
                    if (y >= 300) {
                        y = 300;
                        kat = 0;
                        timer.stop();
                    }
                }
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (konewkaImg != null) {
            Graphics2D g2d = (Graphics2D) g.create();

            int cx = x + konewkaImg.getWidth() / 2;
            int cy = y + konewkaImg.getHeight() / 2;

            g2d.rotate(Math.toRadians(kat), cx, cy);
            g2d.drawImage(konewkaImg, x, y, null);
            g2d.dispose();
        }
    }
}
