/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.TableModels;

import gui.PetrinetTreeModel.*;
import petrinetze.impl.Petrinet;

/**
 *
 * @author steffen
 */
public class PetrinetTableModel extends PropertiesTable.AbstractModel{

    private String[] names;
    private Object[] values;
    //private Project project;

    public PetrinetTableModel(Petrinet net,String name){
       // project = pro;
        names = new String[1];
        names[0] = "Name";
        values = new Object[1];
        //values[0] = project.getName();
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
        return values[rowIndex];
    }

    @Override
    protected void setPropertyValue(int rowIndex, Object value) {
        values[rowIndex] = value;
       // project.setName((String)value);
    }

    @Override
    protected Class<?> getPropertyClass(int rowIndex) {
        return values[rowIndex].getClass();
    }

    @Override
    protected boolean isWritable(int rowIndex) {
        return true;
    }

}
