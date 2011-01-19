/*a
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.TableModels;

import gui.TableModels.PropertiesTable;
import petrinetze.IArc;

/**
 *
 * @author steffen
 */
public class ArcTableModel extends PropertiesTable.AbstractModel{

    private String[] names;
    private Object[] values;
    private IArc arc;

    public ArcTableModel(IArc arc){
        this.arc = arc;
        names = new String[2];
        names[0] = "Name";
        names[1] = "Mark";
        values = new Object[2];
        values[0] =  arc.getName();
        values[1] =  arc.getMark();
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
        if(rowIndex == 0){
            arc.setName((String)value);
        }else if(rowIndex == 1){
            arc.setMark((Integer)value);
        }
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
