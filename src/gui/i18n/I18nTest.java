/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.i18n;

import java.io.File;

/**
 *
 * @author steffen
 */
public class I18nTest {

    public static void main(String[] args){
        System.out.println("Tests für I18n");
        System.out.println("Deutsch:");
        I18n.setLocation("de");
        System.out.println("greetings = " + I18n.get("greetings"));
        System.out.println("inquiry = " + I18n.get("inquiry"));
        System.out.println("farewell = " +I18n.get("farewell"));
        System.out.println("Englisch:");
        I18n.setLocation("en");
        System.out.println("greetings = " + I18n.get("greetings"));
        System.out.println("inquiry = " + I18n.get("inquiry"));
        System.out.println("farewell = " +I18n.get("farewell"));

    }

}
