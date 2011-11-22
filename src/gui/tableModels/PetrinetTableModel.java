/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.tableModels;

import gui.PetrinetTreeModel.*;

/**
 *
 * @author steffen
 */
public class PetrinetTableModel extends PropertiesTable.AbstractModel{

    public static final int PROPERTY_NAME = 0;
    
    private String[] names = {
        "Name"
    };
    private PetrinetNode petrinet;

    public PetrinetTableModel(PetrinetNode net){
       petrinet = net;
    }

    @Override
    protected int getPropertyCount() {
        return names.length;
    }

    @Override
    protected String getPropertyName(int rowIndex) {
        return names[rowIndex];
    }

    @Override
    protected Object getPropertyValue(int rowIndex) {
        if(rowIndex == PROPERTY_NAME){
            return ((Named<?>)petrinet.getUserObject()).getName();
        }else{
            return null;
        }
    }

    @Override
    protected void setPropertyValue(int rowIndex, Object value) {
        if(rowIndex == PROPERTY_NAME){
            ((Named<?>)petrinet.getUserObject()).setName((String)value);
        }
    }

    @Override
    protected Class<?> getPropertyClass(int rowIndex) {
        return String.class;
    }

    @Override
    protected boolean isWritable(int rowIndex) {
        return true;
    }

}
