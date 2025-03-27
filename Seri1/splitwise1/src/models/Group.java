package models;
/*
Explanation:
- In our app, we have groups that have some information.
- Group is something that we need to make it an object because it looks like an object:
- put those information here and use them in your code.
 */

import models.enums.GroupType;

import java.util.ArrayList;

public class Group {
    private final int id;
    private String name;
    private ArrayList<User> users= new ArrayList<>();
    private final User creator;
    private GroupType groupType;

    public Group(User creator, ArrayList<User> users, String name, GroupType groupType, int id) {
        this.creator = creator;
        this.users = users;
        this.name = name;
        this.groupType = groupType;
        this.id = id;
    }

    public String getName() { return name; }

    public GroupType getGroupType() { return groupType; }

    public int getId() {
        return id;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public User getCreator() {
        return creator;
    }

    public void AddUser(User user) { users.add(user); }

    public boolean HasUser(User user) {
        for(User u : users) {
            if(u.equals(user)) {
                return true;
            }
        }
        return false;
    }
}