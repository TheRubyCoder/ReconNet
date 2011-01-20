/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.TableModels;

import gui.RuleWrapper;
import transformation.Rule;



/**
 *
 * @author steffen
 */
public class RuleTableModel extends PropertiesTable.AbstractModel{
    
    private String[] names;
    private Object[] values;
    private RuleWrapper rule;

    public RuleTableModel(RuleWrapper rule){
        this.rule = rule;
        names = new String[1];
        names[0] = "Name";
        values = new Object[1];
        values[0] = rule.getName();
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
        rule.setName((String) value);
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
