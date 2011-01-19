/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import petrinetze.IArc;

/**
 *
 * @author steffen
 */
public class ArcTableModel extends PropertiesTable.AbstractModel{

    private ;

    public ArcTableModel(IArc arc){
        this.arc = arc;
    }

    @Override
    protected int getPropertyCount() {
        return 1;
    }

    @Override
    protected String getPropertyName(int rowIndex) {
        return "Mark";
    }

    @Override
    protected Object getPropertyValue(int rowIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void setPropertyValue(int rowIndex, Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Class<?> getPropertyClass(int rowIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean isWritable(int rowIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
