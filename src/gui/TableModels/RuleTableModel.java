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

    public static final int PROPERTY_NAME = 0;
    
    private String[] names = {
        "Name"
    };

    private RuleWrapper rule;

    public RuleTableModel(RuleWrapper rule){
        this.rule = rule;
        names = new String[1];
        names[0] = "Name";
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
            case PROPERTY_NAME: return rule.getName();
            default: return null;
        }
    }

    @Override
    protected void setPropertyValue(int rowIndex, Object value) {
        switch (rowIndex) {
            case PROPERTY_NAME:
                rule.setName((String) value);
                break;
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
