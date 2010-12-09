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
        currentLocale = new Locale("en", "US");

        messages = ResourceBundle.getBundle("gui.resource.MessageBundle",currentLocale);
        System.out.println(messages.getString("apply"));
    }

}
