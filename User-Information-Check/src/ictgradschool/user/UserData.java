package ictgradschool.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserData {

    private List<UserDataSet> _userDetail;

    /**
     * Create a userData list containing a collection of users...
     * @param ud
     */

    public UserData(List<UserDetailCreate> ud){
        UserDataSet[] userDataSet = new UserDataSet[ud.size()];
        for (int i = 0; i < ud.size(); i++) {
            userDataSet[i] = new UserDataSet(ud.get(i).getId(), ud.get(i).getFname(), ud.get(i).getLname(), ud.get(i).getBio(), ud.get(i).getUsername(), ud.get(i).getDob(), ud.get(i).getAuthored());
        }
        _userDetail = new ArrayList<UserDataSet>(Arrays.asList(userDataSet));
    }

    public int getNumberOfUsers(){
        return _userDetail.size();
    }

    public UserDataSet getUserAt(int index) {
        return _userDetail.get(index);
    }

    public int getIndexFor(UserData user) {
        return _userDetail.indexOf(user);
    }

}
