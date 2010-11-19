/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.i18n;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasse für die Internationalisierung.
 * I18n = Internationalzation
 *         |18 Buchstaben  |
 * @author Steffen Brauer
 *
 */
public class I18n {

    private static I18n resource = null;


    /**
     * Diese Methode setzt die Sprache und das Land.
     * @param Sprache
     * @param Land
     * @return nichts
     */
    public static void setLocation(String language){
        resource = new I18n(language);
    }

    /**
     * Dient zur uebersetzung von Feldern.
     * @param Feld, das uebersetzt werden soll.
     * @return Die passende Übersetzung
     */
    public static String get(String str){
        if(resource == null){
            resource = new I18n();
        }
        return resource.getTranslation(str);
    }

    private String path;
    private String language;
    private Map<String,String> translation;


    private I18n(){
        path = "./";
        language = "en";
        loadFile();
    }

    private I18n(String language){
        path = "./";
        this.language = language;
        loadFile();
    }

    private String getLanguage(){
        return language;
    }

    private String bulidPath(){
        return "I18n_" + language + ".txt";
    }

    private void loadFile(){
        BufferedReader reader;
        Map<String,String> map = new HashMap<String,String>();
        String zeile;
        try {
            reader = new BufferedReader(new FileReader(path + "/" + bulidPath()));
            while ((zeile = reader.readLine()) != null) {
                String[]values = zeile.split(" = ");
                map.put(values[0], values[1]);
            }
        } catch (IOException e) {
            System.err.println(e.toString());
        }
        translation = map;
    }

    private String getTranslation(String str){
        String result = translation.get(str);
        return result == null ? str : result;
    }





}
