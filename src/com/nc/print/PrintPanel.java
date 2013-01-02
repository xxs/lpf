package com.nc.print;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable; 
import java.awt.print.PrinterException;

import javax.swing.JPanel;

public class PrintPanel extends JPanel implements Printable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6127969904494747969L;

	public   void   paintComponent(Graphics   g)   
    {     super.paintComponent(g);   
          Graphics2D   g2   =   (Graphics2D)g;   
          drawPage(g2);   
    }   

    public   int   print(Graphics   g,   PageFormat   pf,   int   page)   
          throws   PrinterException   
    {     if   (page   >=   1)   return   Printable.NO_SUCH_PAGE;   
          Graphics2D   g2   =   (Graphics2D)g;   
          g2.setPaint(Color.black);   
          g2.translate(pf.getImageableX(),   pf.getImageableY());   
          g2.draw(new   Rectangle2D.Double(0,   0,   
                pf.getImageableWidth(),   pf.getImageableHeight()));   

          drawPage(g2);   
          return   Printable.PAGE_EXISTS;   
    }   

    public   void   drawPage(Graphics2D   g2)   
    {     FontRenderContext   context   =   g2.getFontRenderContext();   
          Font   f   =   new   Font("Serif",   Font.PLAIN,   72);   
          GeneralPath   clipShape   =   new   GeneralPath();   

          TextLayout   layout   =   new   TextLayout("2426打印指南",   f,   context);   
          AffineTransform   transform   
                =   AffineTransform.getTranslateInstance(0,   72);   
          Shape   outline   =   layout.getOutline(transform);   
          clipShape.append(outline,   false);   

          layout   =   new   TextLayout("thank you",   f,   context);   
          transform   
                =   AffineTransform.getTranslateInstance(0,   144);   
          outline   =   layout.getOutline(transform);   
          clipShape.append(outline,   false);   

          g2.draw(clipShape);   
          g2.clip(clipShape);   

          final   int   NLINES   =50;   
          Point2D   p   =   new   Point2D.Double(0,   0);   
          for   (int   i   =   0;   i   <   NLINES;   i++)   
          {     double   x   =   (2   *   getWidth()   *   i)   /   NLINES;   
                double   y   =   (2   *   getHeight()   *   (NLINES   -   1   -   i))   
                      /   NLINES;   
                Point2D   q   =   new   Point2D.Double(x,   y);   
                g2.draw(new   Line2D.Double(p,   q));   
          }   
    }   
}
