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
public class I18n {

    private static I18n instance = null;

    public static void setLocale(String language, String country){
        instance = new I18n(language,country);
    }

    public static void setDefaultLocale(){
        instance = new I18n();
    }

    public static String translate(String str){
        return instance.trans(str);
    }
    


    private Locale locale;
    private ResourceBundle bundle;

    private I18n(String language,String country){
        locale = new Locale(language,country);
        bundle = ResourceBundle.getBundle("gui.resource.MessageBundle",locale);
    }

    private I18n(){
        bundle = ResourceBundle.getBundle("gui.resource.MessageBundle");
    }

    private Locale getLocale(){
        return locale;
    }

    private String trans(String str){
        return bundle.getString(str);
    }

}
