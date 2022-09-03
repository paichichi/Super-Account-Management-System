package ictgradschool.ui;

import ictgradschool.Adapter.UsersInfoTableAdapter;
import ictgradschool.user.UserData;
import ictgradschool.user.UserDetailCreate;
import ictgradschool.web.API;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * This class will be finishing and running all the basics functions, including Swing and SwingWorker...
 */
public class UserPanel extends JPanel {

    public static final String[] COLMUN_NAMES = {"ID", "First Name", "Last Name", "Bio", "Username", "Date of Birth", "Authored articles"};

        private JLabel userName;
        private JTextField userNameInputField;

        private JLabel password;
        private JPasswordField passwordInputField;

        private JButton login;
        private JButton logout;

        private JLabel about;
        private JTable userDetailsDisplay;
        private JTextArea executionDisplayField;

        private JButton delete;

        private int statusCodeOfLogin;

        private SwingWorkerUserInformationCheck sw;

        private Object message;

        private int userId;

    /**
     * JFrame creation....
     */
    public UserPanel(){

            JFrame.setDefaultLookAndFeelDecorated(true);
            JFrame frame = new JFrame("User Information Management");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            userName = new JLabel("Username:");
            userNameInputField = new JTextField(15);

            password = new JLabel("Password:");
            passwordInputField = new JPasswordField(15);

            login = new JButton("Login");
            logout = new JButton("Logout");

            about = new JLabel("User Info:");
            executionDisplayField = new JTextArea();

            delete = new JButton("Delete");

            userDetailsDisplay = new JTable();

            userDetailsDisplay.setRowHeight(20);
            executionDisplayField.setEditable(false);
            executionDisplayField.setLineWrap(true);
            executionDisplayField.setRows(5);

            JScrollPane jScrollPane1 = new JScrollPane();
            jScrollPane1.setViewportView(executionDisplayField);
            JScrollPane jScrollPane2 = new JScrollPane();
            jScrollPane2.setViewportView(userDetailsDisplay);
            logout.setEnabled(false);
            delete.setEnabled(false);
/**
 * JTable initialises, just displaying the name of each column in the table,
 * and the row data will be updated when JSON is sent back from the API interface.
 */
            userDetailsDisplay.setModel(new AbstractTableModel() {
                @Override
                public int getRowCount() {
                    return 0;
                }

                @Override
                public int getColumnCount() {
                    return COLMUN_NAMES.length;
                }
                @Override
                public String getColumnName(int index){
                    return COLMUN_NAMES[index];
                }
                @Override
                public Object getValueAt(int rowIndex, int columnIndex) {
                    return null;
                }
            });
/**
 * Frame building...
 */
            JPanel panel = new JPanel();
            GroupLayout layout = new GroupLayout(panel);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);
            panel.setLayout(layout);

            layout.setHorizontalGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(userName)
                            .addComponent(about))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(userNameInputField)
                                            .addComponent(login))
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(password)
                                            .addComponent(logout))
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(passwordInputField)))
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane1)
                            .addComponent(delete)));

            layout.setVerticalGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(userName)
                            .addComponent(userNameInputField)
                            .addComponent(password)
                            .addComponent(passwordInputField))
                    .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(login)
                                    .addComponent(logout)))
                    .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(about)
                                    .addComponent(jScrollPane2)))
                    .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(jScrollPane1)))
                    .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(delete))));
            frame.add(panel);
            frame.pack();
            frame.setVisible(true);

        /**
         * Login button...
         */
        login.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (userNameInputField.getText().isEmpty()){

                        executionDisplayField.append("Please input your username" + "\n");

                        login.setEnabled(true);
                        logout.setEnabled(false);
                        delete.setEnabled(false);

                    }else if (passwordInputField.getPassword().length == 0){

                        executionDisplayField.append("Please input your password" + "\n");

                        login.setEnabled(true);
                        logout.setEnabled(false);
                        delete.setEnabled(false);
                    }
                    else{
                        String userAccount = userNameInputField.getText();
                        String userPassword = String.valueOf(passwordInputField.getPassword());
                        try {
                            statusCodeOfLogin = API.getInstance().loginRequestPost(userAccount, userPassword);
                            if (statusCodeOfLogin == 401){
                                login.setEnabled(true);
                                logout.setEnabled(false);
                                delete.setEnabled(false);

                                userNameInputField.setText("");
                                passwordInputField.setText("");

                                executionDisplayField.append("Authentication failed, please check your account and password" + "\n");

                            }else if (statusCodeOfLogin == 204){
                                sw = new SwingWorkerUserInformationCheck();
                                sw.execute();
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    }

            });
/**
 * logout button...
 */
            logout.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int statusCodeOfLogout = API.getInstance().logoutRequestGet();
                        if(statusCodeOfLogout == 204){

                            userNameInputField.setText("");
                            passwordInputField.setText("");
                            logout.setEnabled(false);
                            login.setEnabled(true);
                            delete.setEnabled(false);

                            /**
                             * JTable update when user click logout button...
                             */
                            userDetailsDisplay.setModel(new AbstractTableModel() {
                                @Override
                                public int getRowCount() {
                                    return 0;
                                }

                                @Override
                                public int getColumnCount() {
                                    return COLMUN_NAMES.length;
                                }
                                @Override
                                public String getColumnName(int index){
                                    return COLMUN_NAMES[index];
                                }
                                @Override
                                public Object getValueAt(int rowIndex, int columnIndex) {
                                    return null;
                                }
                            });

                            executionDisplayField.append("Logout successfully, Good-bye" + "\n");
                            delete.setEnabled(false);

                        }else {

                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            });


        }

    /**
     * this function will be invoked when admin login to the system and display all user's information.
     * @throws IOException
     * @throws InterruptedException
     */
    public void getRowValue() throws IOException, InterruptedException {
       userDetailsDisplay.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
           @Override
           public void valueChanged(ListSelectionEvent e) {
               if(e.getValueIsAdjusting()){
                   /**
                    * get a user id when user select a row and delete button will be available...
                    */
                   userId = userDetailsDisplay.getSelectedRow() + 1;
                   delete.setEnabled(true);
               }
           }
       });
    }

    /**
     * this function will be invoked when admin login to the system and the user selecte user information with the mouseclick...
     * @param cookies
     */
    public void deleteAccount(String cookies){
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (userId == 1){
                        executionDisplayField.append("You can't delete yourself, please select other users" + "\n");
                    }else{
                        String deleteMessage = API.getInstance().deleteUserById(userId, cookies);

                        if (deleteMessage.equals("Delete failed because you are not admin!")){

                            executionDisplayField.append("Deletion failed because you do not have permission" + "\n");

                        }else if (deleteMessage.equals("Delete failed because user doesn't exist!")){

                            executionDisplayField.append("Deletion failed because the user you are trying to delete does not exist" + "\n");
                        }else if (userId == 0){
                            delete.setEnabled(false);
                        }else{

                            executionDisplayField.append("Delete successful, the user you select is delete. User id: " + userId + " is deleted"+ "\n");

                            /**
                             * Double-check the database information and update JTable...
                             */
                            List<UserDetailCreate> data = (List<UserDetailCreate>) API.getInstance().getAllUsersDetail(cookies);
                            tableUpdate(data);
                            delete.setEnabled(false);
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * refresh JTable...when this function called...
     * @param data
     */
    public void tableUpdate(List<UserDetailCreate> data){
        UserData userData = new UserData(data);
        UsersInfoTableAdapter table = new UsersInfoTableAdapter(userData);
        userDetailsDisplay.setModel(table);
        userDetailsDisplay.repaint();
    }

    /**
     * SwingWorker...
     */
    private class SwingWorkerUserInformationCheck extends SwingWorker<Object, String> {

            @Override
            protected Object doInBackground() throws Exception {

                try{

                    String cookies = API.getInstance().getAuthToken();

                    message = API.getInstance().getAllUsersDetail(cookies);

                    if (message.equals("Authentication failed because you are not admin!")){

                        String [] messagePopup = {"You have successfully logged on", "User authentication checking", "Authentication failed because you are not admin"};
                        for (int i = 0; i < messagePopup.length; i++) {
                            publish(messagePopup[i] + "\n");
                        }

                        return "Please login in as a admin!";
                    }else if(message.equals("Authentication failed because user doesn't exist!")){

                        String [] messagePopup = {"You have successfully logged on", "User authentication checking", "Authentication failed because user doesn't exist"};
                        for (int i = 0; i < messagePopup.length; i++) {
                            publish(messagePopup[i] + "\n");
                        }

                        return "Please check this user, is doesn't exist!";
                    }else{

                        String [] messagePopup = {"You have successfully logged on", "User authentication checking", "Authentication successful, you can check all users information right now"};

                        for (int i = 0; i < messagePopup.length; i++) {
                            publish(messagePopup[i] + "\n");
                        }

                        return message;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }



            @Override
            public void process(List<String> messages){
                for (String message : messages){
                    executionDisplayField.append(message);
                }
            }

            @Override
            public void done(){
                    try {

                        if (get().equals("Please login in as a admin!")){
                            executionDisplayField.append("You can still use the system, but only with system limited privileges" + "\n");

                            logout.setEnabled(true);
                            login.setEnabled(false);
                            delete.setEnabled(false);

                        }else if(get().equals("Please check this user, is doesn't exist!")){

                            API.getInstance().logoutRequestGet();
                            executionDisplayField.append("Please check your username and password, the user is doesn't exist! You've logged out of the system" + "\n");

                            login.setEnabled(true);
                            logout.setEnabled(false);
                            delete.setEnabled(false);

                        }else{
                            List<UserDetailCreate> ud = (List<UserDetailCreate>) get();
                            System.out.println(ud);
                            new UserData(ud);

                            UserData userData = new UserData(ud);
                            UsersInfoTableAdapter table = new UsersInfoTableAdapter(userData);
                            userDetailsDisplay.setModel(table);

                            login.setEnabled(false);
                            logout.setEnabled(true);
                            delete.setEnabled(false);

                            String cookies = API.getInstance().getAuthToken();
                            getRowValue();
                            deleteAccount(cookies);
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch(ClassCastException e){
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }
        }
}
