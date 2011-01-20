/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.TableModels;

import petrinetze.IRenew;
import petrinetze.ITransition;

/**
 *
 * @author steffen
 */
public class TransitionTableModel extends PropertiesTable.AbstractModel {

    public static final int PROPERTY_ID = 0;

    public static final int PROPERTY_NAME = 1;

    public static final int PROPERTY_LABEL = 2;

    public static final int PROPERTY_RENEW = 3;

    private final String[] names = {
        "ID",
        "Name",
        "Label",
        "Renew"
    };

    private ITransition transition;

    public TransitionTableModel(ITransition transition) {
        this.transition = transition;
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
            case PROPERTY_ID: return transition.getId();
            case PROPERTY_NAME:
                return transition.getName();
            case PROPERTY_LABEL:
                return transition.getTlb();
            case PROPERTY_RENEW:
                return transition.getRnw();
            default:
                return null;
        }
    }

    @Override
    protected void setPropertyValue(int rowIndex, Object value) {
        switch (rowIndex) {
            case PROPERTY_NAME:
                transition.setName((String)value);
                break;

            case PROPERTY_LABEL:
                transition.setTlb((String)value);
                break;

            case PROPERTY_RENEW:
                transition.setRnw((IRenew)value);
                break;
        }
    }

    @Override
    protected Class<?> getPropertyClass(int rowIndex) {
        switch (rowIndex) {
            case PROPERTY_ID: return Integer.class;
            case PROPERTY_RENEW: return IRenew.class;
            default: return String.class;
        }
    }

    @Override
    protected boolean isWritable(int rowIndex) {
        return rowIndex > 0;
    }
}
