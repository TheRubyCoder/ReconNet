/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author steffen
 */
public class Test1 {

    public static void main(String[] args){
        Locale currentLocale;
        ResourceBundle messages;
        currentLocale = new Locale("de", "DE");

        messages = ResourceBundle.getBundle("gui.resource.MessageBundle",currentLocale);
        System.out.println(messages.getString("step"));

         
        I18n.setLocale("de", "DE");
        System.out.println(I18n.translate("step"));
        Error.create("Test");
    }

}
