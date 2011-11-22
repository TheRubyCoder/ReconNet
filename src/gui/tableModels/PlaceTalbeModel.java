/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.tableModels;

import petrinet.Place;


/**
 *
 * @author steffen
 */
public class PlaceTalbeModel extends PropertiesTable.AbstractModel {

    public static final int PROPERTY_ID = 0;

    public static final int PROPERTY_NAME = 1;

    public static final int PROPERTY_MARK = 2;

    private final String[] propertyNames = {
        "ID",
        "Name",
        "Markierung"
    };

    private Place place;

    public PlaceTalbeModel(Place place) {
        this.place = place;
    }

    @Override
    protected int getPropertyCount() {
        return propertyNames.length;
    }

    @Override
    protected String getPropertyName(int rowIndex) {
        return propertyNames[rowIndex];
    }

    @Override
    protected Object getPropertyValue(int rowIndex) {
        switch (rowIndex) {
            case PROPERTY_ID:
                return place.getId();
            case PROPERTY_NAME:
                return place.getName();
            case PROPERTY_MARK:
                return place.getMark();

            default: return null;
        }
    }

    @Override
    protected void setPropertyValue(int rowIndex, Object value) {
        switch (rowIndex) {
            case PROPERTY_NAME:
                place.setName((String)value);
                break;
            case PROPERTY_MARK:
                place.setMark(Integer.parseInt((String) value));
                break;
        }
    }

    @Override
    protected Class<?> getPropertyClass(int rowIndex) {
        switch (rowIndex) {
            case PROPERTY_NAME: return String.class;
            default: return Integer.class;
        }
    }

    @Override
    protected boolean isWritable(int rowIndex) {
        return rowIndex > 0;
    }

}
