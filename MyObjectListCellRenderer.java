import javax.swing.*;

public class MyObjectListCellRenderer extends DefaultListCellRenderer {
    public MyObjectListCellRenderer getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        if (value instanceof Car) {
            value = ((Car)value).getTitle();
        }
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        return this;
    }

}
