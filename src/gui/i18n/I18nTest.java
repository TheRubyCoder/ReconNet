/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.i18n;

/**
 *
 * @author steffen
 */
public class I18nTest {

    public static void main(String[] args){
        System.out.println("Tests für I18n");
        I18n.setPath("./I18n");
        System.out.println("Sprachen:" + I18n.getLanguages());
        System.out.println("Standard:");
        System.out.println("Apply:" + I18n.get("apply"));
        System.out.println("Deutsch:");
        I18n.setLanguage("de");
        System.out.println("Apply:" + I18n.get("apply"));
        System.out.println("Englisch:");
        I18n.setLanguage("en");
        System.out.println("Apply:" + I18n.get("apply"));

    }

}
