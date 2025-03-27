package models;

import models.enums.Menu;

import java.util.ArrayList;

/*
Explanation:
- In out app, we need somewhere to keep our general data like list of users and list of groups and logged-in user etc.
- This class is the place for that.
- Put your general data here and use them in your code.
- you should put some functions here to manage your data too.
 */
public class App {
    public static ArrayList<User> allUsers = new ArrayList<>();
    public static ArrayList<Group> allGroups = new ArrayList<>();
    private static User loggedInUser = null;
    private static Menu currentMenu = Menu.SignUpMenu;

    public static Menu getCurrentMenu() { return currentMenu; }
    public static void setCurrentMenu(Menu currentMenu) {
        App.currentMenu = currentMenu;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        App.loggedInUser = loggedInUser;
    }

    public static User GetUserByUsername(String username) {
        for(User user : allUsers) {
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
