package gui;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class PropertiesTable extends JTable {


    static abstract class AbstractModel extends AbstractTableModel {
        @Override
        public int getRowCount() {
            return getPropertyCount();
        }

        @Override
        public final int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0) return getPropertyName(rowIndex);
            if (columnIndex == 1) return getPropertyValue(rowIndex);
            return null;
        }

        @Override
        public String getColumnName(int column) {
            if (column == 0) return "Name";
            if (column == 1) return "Value";
            return null;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 0) return String.class;
            return Object.class;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 1 && isWritable(rowIndex);
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (isCellEditable(rowIndex, columnIndex)) {
                setPropertyValue(rowIndex, aValue);
            }
        }

        protected abstract int getPropertyCount();

        protected abstract String getPropertyName(int rowIndex);

        protected abstract Object getPropertyValue(int rowIndex);

        protected abstract void setPropertyValue(int rowIndex, Object value);

        protected abstract Class<?> getPropertyClass(int rowIndex);

        protected abstract boolean isWritable(int rowIndex);
    }

    @Override
    public TableCellEditor getCellEditor(int row, int column) {
        final TableCellEditor e = getDefaultEditor(getModel().getPropertyClass(row));
        return e == null ? super.getCellEditor(row, column) : e;
    }

    @Override
    public AbstractModel getModel() {
        return (AbstractModel) super.getModel();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void setModel(TableModel dataModel) {
        if (dataModel == null) throw new IllegalArgumentException("Model must not be null");
        if (!(dataModel instanceof AbstractModel)) throw new IllegalArgumentException("Unsupported model");
        super.setModel(dataModel);
    }
}
