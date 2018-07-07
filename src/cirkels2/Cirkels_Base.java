/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cirkels2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;

/**
 *
 * @author piet
 */
class Cirkels_Base {

   private int bandbreedte;                  // bandbreedte totaal
   private int aantalbits;                   // aantal bits
   private int hoofdband;                    // bandbreedte eerste deel
   private int breedte;                      // breedte van de bufferedimage
   private int hoogte;                       // hoogte van de bufferedimage
   private BufferedImage buf;                // om het raster te vullen
   private WritableRaster raster;            // bevat alle cirkels
   private Color kleur1, kleur2;             // de kleuren van de twee banden
   private IndexColorModel[] colmod;         // bevat alle indexcolormodels
   private byte[] rood, groen, blauw, alpha; // de kleurenarrays

   // constructor
   public Cirkels_Base(int bandwidth, int mainband, int numberofcolumns, int numberofrows, Color color1, Color color2) {
      if (bandwidth <= 2) {
         bandbreedte = 2;
         aantalbits = 1;
         hoofdband = 1;
      }
      else if (bandwidth <= 4) {
         bandbreedte = 4;
         aantalbits = 2;
         hoofdband = 2;
      }
      else {
         bandbreedte = 16;
         if (mainband >= bandbreedte) {
            hoofdband = bandbreedte - 1;
         }
         else if (mainband < 1) {
            hoofdband = 1;
         }
         else {
            hoofdband = mainband;
         }
         aantalbits = 4;
      }
      breedte = numberofcolumns;
      hoogte = numberofrows;
      kleur1 = color1;
      kleur2 = color2;
      colmod = new IndexColorModel[bandbreedte];
      rood = new byte[bandbreedte];
      groen = new byte[bandbreedte];
      blauw = new byte[bandbreedte];
      alpha = new byte[bandbreedte];

      // we gaan nu een indexcolormodel bepalen mbv de vier kleur-arrays, en vanuit dit
      // indexcolormodel trekken we een writableraster, dat compatible is met dat color-
      // model. En als we dat hebben, dan gaan we nog de gewenste hoeveelheid colormodels
      // maken, en klaar is kees!

      // we gaan een indexcolormodel maken, waarbij we de kleurenarrays vullen met duidelijk
      // te onderscheiden kleuren. Vervolgens maken we met dit colormodel een writableraster,
      // en met die twee maken we een bufferedimage. Daarna trekken we alle cirkels in die
      // bufferedimage. Gaan we...
      maak_Colmod_Raster_En_BufferedImage();
      maak_indexcolmods();
   }

   /********************************************************************************/
   /*                                                                              */
   /*  de publieke methoden                                                        */
   /*                                                                              */
   /********************************************************************************/

   // om de volgende functie zal het gaan... deze levert de plaatjes

   public BufferedImage getImage(int nr) {
      int h = nr % bandbreedte;
      return new BufferedImage(colmod[h], raster, false, null);
   }

   public Color getColor1() {
      return kleur1;
   }

   public Color getColor2() {
      return kleur2;
   }

   public Color getColor(int colornr) {
      if (colornr == 1) {
         return kleur1;
      }
      else if (colornr == 2) {
         return kleur2;
      }
      else {
         return null;
      }
   }

   public void setColor1(Color col) {
      if (col == null) {
         return;
      }
      kleur1 = col;
      maak_indexcolmods();
   }

   public void setColor2(Color col) {
      if (col == null) {
         return;
      }
      kleur2 = col;
      maak_indexcolmods();
   }

   public void setColors(Color col1, Color col2) {
      if (col1 == null && col2 == null) {
         return;
      }
      if (col1 != null) {
         kleur1 = col1;
      }
      if (col2 != null) {
         kleur2 = col2;
      }
      maak_indexcolmods();
   }

   public int getWidth() {
      return breedte;
   }

   public int getHeight() {
      return hoogte;
   }

   public void setWidth(int new_width) {
      breedte = new_width;
      maak_Colmod_Raster_En_BufferedImage();
   }

   public void setHeight(int new_height) {
      hoogte = new_height;
      maak_Colmod_Raster_En_BufferedImage();
   }

   public void setWidthAndHeight(int width, int height) {
      breedte = width;
      hoogte = height;
      maak_Colmod_Raster_En_BufferedImage();
      maak_indexcolmods();
   }

   public void setMainbandWidth(int radius) {
      if (radius < 1) {
         radius = 1;
      }
      else if (radius >= bandbreedte) {
         radius = bandbreedte - 1;
      }
      hoofdband = radius;
      maak_indexcolmods();
   }

   public BufferedImage giveBufferedImageARGB(int framenr) {
      int i = framenr % bandbreedte;
      return colmod[i].convertToIntDiscrete(raster, true);
   }

   @Override
   public String toString() {
   return "cirkelvormige golven... door Piet Muis, voorjaar 2011";
   }

   /********************************************************************************/
   /*                                                                              */
   /*  de private methoden                                                         */
   /*                                                                              */
   /********************************************************************************/

   private void vulKleurenArrays(int framenr) {
      Color col;
      int i, h;
      for (i = framenr; i < framenr + bandbreedte; i++) {
         h = i % bandbreedte;
         if (i < framenr + hoofdband) {
            col = kleur1;
         }
         else {
            col = kleur2;
         }
         rood[h]  = (byte) col.getRed();
         groen[h] = (byte) col.getGreen();
         blauw[h] = (byte) col.getBlue();
         alpha[h] = (byte) col.getAlpha();
      }
   }

   private void maak_indexcolmods() {
      int i;
      for (i = 0; i < bandbreedte; i++) {
         vulKleurenArrays(i);
         colmod[i] = new IndexColorModel(aantalbits, bandbreedte, rood, groen, blauw, alpha);
      }
   }

   private void maak_Colmod_Raster_En_BufferedImage() {
      for (int i = 0; i < bandbreedte; i++) {
         rood[i]  = (byte) (i * 256 / bandbreedte);
         groen[i] = (byte) (i * 256 / bandbreedte);
         blauw[i] = (byte) (i * 256 / bandbreedte);
         alpha[i] = (byte) (i * 256 / bandbreedte);
      }
      colmod[0] = new IndexColorModel(aantalbits, bandbreedte, rood, groen, blauw, alpha);
      // en het bijbehorende raster
      raster = colmod[0].createCompatibleWritableRaster(breedte, hoogte);
      // en we maken de bufferedimage
      buf = new BufferedImage(colmod[0], raster, false, null);

      // en we gaan de cirkels trekken. Volgens de api (helaas kan ik de betreffende api
      // niet meer vinden) zet de grapichs zèlf de juiste index in de bufferedimage,
      // als we een kleur zetten. Hij kiest dan de index van de kleur die er het dichtste bij zit
      // gaan we...
      int Mx1, My1, Mx2, My2, Mx3, My3, Mx4, My4, Mx5, My5;
      Mx1 = breedte / 6; My1 = hoogte / 4;
      Mx2 = breedte * 5 / 6; My2 = My1;
      Mx3 = breedte / 2; My3 = hoogte / 2;
      Mx4 = Mx1; My4 = hoogte * 3 / 4;
      Mx5 = Mx2; My5 = My4;
      Graphics g = buf.createGraphics();
//      g.setColor(new Color(0,0,0));
//      g.fillRect(0,0,breedte,hoogte);
      Color[] col = new Color[bandbreedte];
      for (int i = 0; i < bandbreedte; i++) {
         col[i] = new Color((int) rood[i] & 0xff, (int) groen[i] & 0xff, (int) blauw[i] & 0xff, (int) alpha[i] & 0xff);
      }
      for (int i = Math.max(hoogte, breedte) / 2; i >= 0; i--) {
         g.setColor(col[i % bandbreedte]);
         g.fillOval(Mx1 - i, My1 - i, 2*i, 2*i);
         g.fillOval(Mx2 - i, My2 - i, 2*i, 2*i);
         g.fillOval(Mx3 - i, My3 - i, 2*i, 2*i);
         g.fillOval(Mx4 - i, My4 - i, 2*i, 2*i);
         g.fillOval(Mx5 - i, My5 - i, 2*i, 2*i);
//////////////////////////////////////////////////////////////////////////
//         g.drawOval(Mx1 - i, My1 - i, 2*i, 2*i);
//         g.drawOval(Mx2 - i, My2 - i, 2*i, 2*i);
//         g.drawOval(Mx3 - i, My3 - i, 2*i, 2*i);
//         g.drawOval(Mx4 - i, My4 - i, 2*i, 2*i);
//         g.drawOval(Mx5 - i, My5 - i, 2*i, 2*i);
//////////////////////////////////////////////////////////////////////////
//         g.drawRect(Mx1 - i, My1 - i, 2*i, 2*i);
//         g.drawRect(Mx2 - i, My2 - i, 2*i, 2*i);
//         g.drawRect(Mx3 - i, My3 - i, 2*i, 2*i);
//         g.drawRect(Mx4 - i, My4 - i, 2*i, 2*i);
//         g.drawRect(Mx5 - i, My5 - i, 2*i, 2*i);
//////////////////////////////////////////////////////////////////////////
//         g.fillRect(Mx1 - i, My1 - i, 2*i, 2*i);
//         g.fillRect(Mx2 - i, My2 - i, 2*i, 2*i);
//         g.fillRect(Mx3 - i, My3 - i, 2*i, 2*i);
//         g.fillRect(Mx4 - i, My4 - i, 2*i, 2*i);
//         g.fillRect(Mx5 - i, My5 - i, 2*i, 2*i);
//////////////////////////////////////////////////////////////////////////
//         g.fillRect(Mx1 - i, My1 - i, 2*i, i);
//         g.fillRect(Mx2 - i, My2 - i, 2*i, i);
//         g.fillRect(Mx3 - i, My3 - i, 2*i, i);
//         g.fillRect(Mx4 - i, My4 - i, 2*i, i);
//         g.fillRect(Mx5 - i, My5 - i, 2*i, i);
//////////////////////////////////////////////////////////////////////////
//         g.fillOval(Mx1 - i, My1 - i, 2*i, 3*i);
//         g.fillOval(Mx2 - i, My2 - i, 2*i, 3*i);
//         g.fillOval(Mx3 - i, My3 - i, 2*i, 3*i);
//         g.fillOval(Mx4 - i, My4 - i, 2*i, 3*i);
//         g.fillOval(Mx5 - i, My5 - i, 2*i, 3*i);

      }
   }

   /*******************************************************************************/
   // einde class
   /*******************************************************************************/

}
