/*a
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.tableModels;

import petrinet.Arc;
import gui.tableModels.PropertiesTable;

/**
 *
 * @author steffen
 */
public class ArcTableModel extends PropertiesTable.AbstractModel{
    
    public static final int PROPERTY_NAME = 0;

    public static final int PROPERTY_MARK = 1;

    private final String[] names = {
        "Name",
        "Mark"
    };
    private Arc arc;

    public ArcTableModel(Arc arc){
        this.arc = arc;
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
        switch (rowIndex) {
            case PROPERTY_NAME:
                return arc.getName();
            case PROPERTY_MARK:
                return arc.getMark();
            default:
                return null;
        }
    }

    @Override
    protected void setPropertyValue(int rowIndex, Object value) {
        switch (rowIndex){
            case PROPERTY_NAME:
                arc.setName((String) value);
                break;
            case PROPERTY_MARK:
                arc.setMark(Integer.parseInt((String)value));
                break;
        }
    }

    @Override
    protected Class<?> getPropertyClass(int rowIndex) {
        if(rowIndex == PROPERTY_MARK){
            return Integer.class;
        }else{
            return String.class;
        }
    }

    @Override
    protected boolean isWritable(int rowIndex) {
        return rowIndex >= 0;
    }


}
