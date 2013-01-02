package com.nc.print;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PrintTestFrame   extends   JFrame   
implements   ActionListener      {
	private   JButton   printButton;   
    private   JButton   pageSetupButton;   

    private   PrintPanel   canvas;   
    private   PageFormat   pageFormat;   

    public   PrintTestFrame()   
    {     setTitle("PrintTest");   
          setSize(300,   300);   
          addWindowListener(new   WindowAdapter()   
                {     public   void   windowClosing(WindowEvent   e)   
                      {     System.exit(0);   
                      }   
                }   );   

          Container   contentPane   =   getContentPane();   
          canvas   =   new   PrintPanel();   
          contentPane.add(canvas,   "Center");   

          JPanel   buttonPanel   =   new   JPanel();   
          printButton   =   new   JButton("Print");   
          buttonPanel.add(printButton);   
          printButton.addActionListener(this);   

          pageSetupButton   =   new   JButton("Page   setup");   
          buttonPanel.add(pageSetupButton);   
          pageSetupButton.addActionListener(this);   

          contentPane.add(buttonPanel,   "North");   
    }   

    public   void   actionPerformed(ActionEvent   event)   
    {     Object   source   =   event.getSource();   
          if   (source   ==   printButton)   
          {     PrinterJob   printJob   =   PrinterJob.getPrinterJob();   
                if   (pageFormat   ==   null)   
                      pageFormat   =   printJob.defaultPage();   
                printJob.setPrintable(canvas,   pageFormat);   
                if   (printJob.printDialog())   
                {     try   
                      {     printJob.print();   
                      }   
                      catch   (PrinterException   exception)   
                      {     JOptionPane.showMessageDialog(this,   exception);   
                      }   
                }   
          }   
          else   if   (source   ==   pageSetupButton)   
          {     PrinterJob   printJob   =   PrinterJob.getPrinterJob();   
                if   (pageFormat   ==   null)   
                      pageFormat   =   printJob.defaultPage();   
                pageFormat   =   printJob.pageDialog(pageFormat);   
          }   
    }   


}
