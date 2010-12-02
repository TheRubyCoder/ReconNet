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
    private static String path = "C:/Users/Niklas/Documents/My Dropbox/HAW-Share/WPPetrinetze/trunk/i18n";


    /**
     * Diese Methode setzt die Sprache und das Land.
     * @param Sprache
     * @param Land
     * @return nichts
     */
    public static void setLocation(String language){
        resource = new I18n(language,path);
    }

    public static String getPath(){
        return path;
    }

    public static void setPath(String newPath){
        path = newPath;
    }

    /**
     * Dient zur uebersetzung von Feldern.
     * @param Feld, das uebersetzt werden soll.
     * @return Die passende Übersetzung
     */
    public static String get(String str){
        if(resource == null){
            resource = new I18n(path);
        }
        return resource.getTranslation(str);
    }

    private String apath;
    private String language;
    private Map<String,String> translation;


    private I18n(String path){
        this.apath = path;
        language = "en";
        loadFile();
    }


    private I18n(String language,String path){
        apath = path;
        this.language = language;
        loadFile();
    }

    private String getLanguage(){
        return language;
    }

    private String bulidPath(){
        return apath + "/I18n_" + language + ".txt";
    }

    private void loadFile(){
        BufferedReader reader;
        Map<String,String> map = new HashMap<String,String>();
        String zeile;
        try {
            reader = new BufferedReader(new FileReader(bulidPath()));
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
