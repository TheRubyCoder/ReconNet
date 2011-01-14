/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;


import javax.swing.UIManager;

/**
 *
 * @author steffen
 */
public class Main {

    public static void main(String[] args){
        try {
            String cn = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(cn);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainGUI().setVisible(true);

            }
        });
        
    }

   


}
