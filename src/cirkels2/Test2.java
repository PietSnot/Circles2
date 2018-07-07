package cirkels2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Piet
 */
public class Test2 extends JFrame {
   JPanel content;
   BufferedImage buff;
   Color kleur1, kleur2, kleur3, kleur4, kleur5, achtergrond;
   int breedte, hoogte;

   // constructor
   public Test2(int width, int height) {
      breedte = width;
      hoogte = height;
      kleur1 = Color.GREEN;
      kleur2 = Color.RED;
      kleur3 = Color.BLACK;
      kleur4 = Color.BLUE;
      kleur5 = Color.ORANGE;
      achtergrond = Color.YELLOW;

      content = new JPanel() {
         @Override
         public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(buff,0,0,null);
         }
         @Override
         public Dimension getPreferredSize() {
            return new Dimension(breedte, hoogte);
         }
      };
      content.setBackground(Color.LIGHT_GRAY);
      buff = new BufferedImage(breedte, hoogte, BufferedImage.TYPE_INT_ARGB );
      Graphics g = buff.getGraphics();
      g.setColor(achtergrond);
      g.fillRect(0,0,width,height);
      int Mx1, My1, Mx2, My2, Mx3, My3, Mx4, My4, Mx5, My5;
      Mx1 = breedte / 6; My1 = hoogte / 4;
      Mx2 = breedte * 5 / 6; My2 = My1;
      Mx3 = breedte / 2; My3 = hoogte / 2;
      Mx4 = Mx1; My4 = hoogte * 3 / 4;
      Mx5 = Mx2; My5 = My4;
      for (int i = Math.max(breedte,hoogte); i >= 0; i--) {
         g.setColor(kleur1);
         g.fillOval(Mx1 - i, My1 - i, 2 * i, 2 * i);
         g.setColor(kleur2);
         g.fillOval(Mx2 - i, My1 - i, 2 * i, 2 * i);
         g.setColor(kleur3);
         g.fillOval(Mx3 - i, My3 - i, 2 * i, 2 * i);
         g.setColor(kleur4);
         g.fillOval(Mx4 - i, My4 - i, 2 * i, 2 * i);
         g.setColor(kleur5);
         g.fillOval(Mx5 - i, My5 - i, 2 * i, 2 * i);
      }

      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setContentPane(content);
      this.pack();
//      this.setResizable(false);
      this.setVisible(true);
      this.setLocation(300,300);
   }

   // einde class
}
