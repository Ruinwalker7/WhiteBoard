package server.model.entity;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class OnlineUserTableModel extends AbstractTableModel {
    private static final long serialVersionUID = -444245379288364831L;
    /** 列名标题 */
    private String[] title = {"账号", "昵称", "性别"};
    /** 数据行 */
    private List<String[]> rows = new ArrayList<String[]>();

    public int getRowCount() {
        return rows.size();
    }

    public int getColumnCount() {
        return title.length;
    }

    public String getColumnName(int column) {
        return title[column];
    }

    public String getValueAt(int row, int column) {
        return (rows.get(row))[column];
    }

    public void add(String[] value) {
        int row = rows.size();
        rows.add(value);
        fireTableRowsInserted(row, row);
    }

    public void remove(long id) {
        int row = -1;
        for (int i = 0; i <= rows.size(); i++) {
            if (String.valueOf(id).equals(getValueAt(i , 0))) {
                row = i;
                break;
            }
        }
        rows.remove(row);
        fireTableRowsDeleted(2, 3);
    }
}
