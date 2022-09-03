package ictgradschool.Adapter;


import ictgradschool.user.UserData;
import ictgradschool.user.UserDataSet;


import javax.swing.table.AbstractTableModel;

/**
 * This class is used to create a table of user information...
 */

public class UsersInfoTableAdapter extends AbstractTableModel{
    public static final String[] COLMUN_NAMES = {"ID", "First Name", "Last Name", "Bio", "Username", "Date of Birth", "Authored articles"};

    private UserData adaptee;

    public UsersInfoTableAdapter(UserData userdata){
        adaptee = userdata;
    }


    @Override
    public int getRowCount() {
        return adaptee.getNumberOfUsers();
    }

    @Override
    public int getColumnCount() {
        return COLMUN_NAMES.length;
    }

    @Override
    public String getColumnName(int index) {
        return COLMUN_NAMES[index];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        UserDataSet users = adaptee.getUserAt(rowIndex);
        Object result = null;

        switch(columnIndex) {
            case 0:
                result = users.getId();
                break;
            case 1:
                result = users.getFname();
                break;
            case 2:
                result = users.getLname();
                break;
            case 3:
                result = users.getBio();
                break;
            case 4:
                result = users.getUsername();
                break;
            case 5:
                result = users.getDob();
                break;
            case 6:
                result = users.getAuthored();
                break;
        }
        return result;
    }

}
