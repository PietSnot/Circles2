/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cirkels2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Piet
 */
public class TestFrame extends JFrame{
   JPanel panel_content, panel_afdruk, panel_button, panel_trans;
   int rood_kleur1, rood_kleur2;
   int groen_kleur1, groen_kleur2;
   int blauw_kleur1, blauw_kleur2;
   int trans_kleur1, trans_kleur2;
   Color kleur1, kleur2, achtergrond;
   Timer timer;
   int breedte, hoogte, bandbreedte, band, framenummer, timer_interval;
   int verander_kleur;
   Cirkels_Base plaatjes;
   BufferedImage buf_panel, foto;
   ActionListener al;
   Random random;
   JButton button_SavePicture, button_LoadPicture, button_kleur1, button_kleur2;

   // constructor
   public TestFrame(int width, int height) {
      breedte = width;
      hoogte = height;
      bandbreedte = 16;
      band = 8;
      maakKleur(1, 0, 0, 128, 255);
      maakKleur(2, 0, 0, 0, 0);
      achtergrond = new Color(0, 255, 0);
      framenummer = 0;
      timer_interval = 1000 / 60;
      random = new Random();

      plaatjes = new Cirkels_Base(bandbreedte, band, breedte, hoogte, kleur1, kleur2);
      buf_panel = new BufferedImage(breedte, hoogte, BufferedImage.TYPE_INT_ARGB);
      foto = new BufferedImage(breedte, hoogte, BufferedImage.TYPE_INT_ARGB);

      Graphics g1 = foto.getGraphics();
      g1.setColor(achtergrond);
      g1.fillRect(0, 0, foto.getWidth(), foto.getHeight());
      g1.dispose();

      panel_afdruk = new JPanel() {
         @Override
         public void paintComponent(Graphics g) {
            framenummer = (framenummer + 1) % bandbreedte;
            Graphics g1 = buf_panel.getGraphics();
            g1.drawImage(foto, 0, 0, null);
            g1.drawImage(plaatjes.getImage(framenummer), 0, 0, null);
            g.drawImage(buf_panel, 0, 0, null);
            g1.dispose();
         }

         @Override
         public Dimension getPreferredSize(){
            return new Dimension(breedte, hoogte);
         }
         // einde panel_afdruk
      };

      button_LoadPicture = new JButton("Load Picture");
      button_SavePicture = new JButton("Save Picture");
      button_kleur1 = new JButton("Choose Color 1");
      button_kleur1.setBackground(maakKleurZonderTransparancy(kleur1));
      button_kleur1.setForeground(inverteerKleur(kleur1, false));
      button_kleur2 = new JButton("Choose Color 2");
      button_kleur2.setBackground(maakKleurZonderTransparancy(kleur2));
      button_kleur2.setForeground(inverteerKleur(kleur2, false));

      panel_button = new JPanel();
      panel_button.setLayout(new BoxLayout(panel_button, BoxLayout.Y_AXIS));
      panel_button.add(button_LoadPicture);
      panel_button.add(button_SavePicture);
      panel_button.add(new JLabel("           "));
      panel_button.add(button_kleur1);
      panel_button.add(button_kleur2);

      panel_content = new JPanel();
      panel_content.setLayout(new BorderLayout(5, 5));
      panel_content.add(panel_afdruk, BorderLayout.CENTER);
      panel_content.add(panel_button, BorderLayout.EAST);
      panel_content.setBackground(achtergrond);

      al = new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
 
//            verander_kleur++;
//            if (verander_kleur == 3 * bandbreedte) {
//               int r, g, b, a;
//               r = random.nextInt(256);
//               g = random.nextInt(256);
//               b = random.nextInt(256);
//               a = random.nextInt(256);
//               kleur1 = new Color(r, g, b, a);
//               plaatjes.setColor1(kleur1);
//               verander_kleur = 0;
//            }
            panel_afdruk.repaint();
         }
      };

      this.setContentPane(panel_content);
      timer = new Timer(timer_interval, al);

      this.pack();
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setVisible(true);
      this.setLocation(new Point(300,300));
      this.setResizable(false);
      this.setTitle("Cirkels...  groeten van Piet");
      timer.start();
   }

   private void maakKleur(int nr, int rood, int groen, int blauw, int trans) {
      if (nr == 1) {
         rood_kleur1 = rood;
         groen_kleur1 = groen;
         blauw_kleur1 = blauw;
         trans_kleur1 = trans;
         kleur1 = new Color(rood, groen, blauw, trans);
      }
      else {
         rood_kleur2 = rood;
         groen_kleur2 = groen;
         blauw_kleur2 = blauw;
         trans_kleur2 = trans;
         kleur2 = new Color(rood, groen, blauw, trans);

      }
   }

   private Color inverteerKleur(Color col, boolean metTrans) {
      Color c;
      int r = 255 - col.getRed();
      int g = 255 - col.getGreen();
      int b = 255 - col.getBlue();
      if (metTrans) {
         int t = 255 - col.getAlpha();
         c = new Color(r, g, b, t);
      }
      else c = new Color(r, g, b);
      return c;
   }

   private Color maakKleurZonderTransparancy(Color col) {
      Color c;
      int r = col.getRed();
      int g = col.getGreen();
      int b = col.getBlue();
      return new Color(r, g, b);
   }
}
