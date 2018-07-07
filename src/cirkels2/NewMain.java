/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cirkels2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Piet
 */
public class NewMain {
    private static BufferedImage cirkels = new BufferedImage(400, 300, BufferedImage.TYPE_INT_ARGB);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       JFrame frame = new JFrame();
       JPanel panel = new JPanel() {
         @Override
          public void paintComponent(Graphics g) {
             super.paintComponent(g);
             g.drawImage(cirkels, 0, 0, null);
          }
       };

       panel.setPreferredSize(new Dimension(400,300));
       panel.setBackground(Color.WHITE);
       Graphics g = cirkels.createGraphics();
       g.setColor(new Color(0,0,0,0));
       g.fillRect(0,0,400,300);
       g.setColor(Color.RED);
       for (int i = 500; i >= 0; i--) {
          if ((i / 8) % 2 == 0) g.setColor(Color.BLACK);
          else g.setColor(Color.RED);
          g.fillRect(200 - i,150 - i, 2 * i,2*i);
       }
       g.dispose();
       frame.setContentPane(panel);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.pack();
       frame.setLocation(300, 300);
       frame.setVisible(true);
    }
   
}
