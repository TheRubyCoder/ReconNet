/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.awt.Component;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JOptionPane;

/**
 *
 * @author steffen
 */
public class Error {
    
    public static Logger logger;

static {
    try {
      boolean append = true;
      FileHandler fh = new FileHandler("Error.log", append);
      //fh.setFormatter(new XMLFormatter());
      fh.setFormatter(new SimpleFormatter());
      logger = Logger.getLogger("Error");
      logger.addHandler(fh);
     
    }
    catch (IOException e) {
      e.printStackTrace();
    }
}


    private static void print(Exception ex){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        ex.printStackTrace(pw);
        pw.flush();
        sw.flush();
        System.out.println(sw.toString());
        logger.warning(sw.toString());
    }


    public static void create(Exception ex){
        JOptionPane.showMessageDialog(null, ex.toString(), I18n.translate("error"), JOptionPane.ERROR_MESSAGE);
        print(ex);
    }

    public static void create(String ex){
        JOptionPane.showMessageDialog(null,ex,I18n.translate("error"),JOptionPane.ERROR_MESSAGE);
    }

    public static void create(Component parentComponent,Exception ex){
        JOptionPane.showMessageDialog(parentComponent, ex.toString(), I18n.translate("error"), JOptionPane.ERROR_MESSAGE);
        print(ex);
    }

    public static void create(Component parentComponent,String ex){
        JOptionPane.showMessageDialog(parentComponent,ex,I18n.translate("error"),JOptionPane.ERROR_MESSAGE);
    }

    

}
