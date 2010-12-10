/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author steffen
 */
public class Error {

    public static void create(Exception ex){
        JOptionPane.showMessageDialog(null, ex.toString(), I18n.translate("error"), JOptionPane.ERROR_MESSAGE);
    }

    public static void create(String ex){
        JOptionPane.showMessageDialog(null,ex,I18n.translate("error"),JOptionPane.ERROR_MESSAGE);
    }

    public static void create(Component parentComponent,Exception ex){
        J
    }

    public static void create(Component parentComponent,String ex){

    }

    

}
