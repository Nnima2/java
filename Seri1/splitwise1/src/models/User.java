package models;

/*
Explanation:
- User is definitely an object in our app.
- put the information that you need to store about the user here.
- you can put some functions here to manage the user data too.
 */

import models.enums.Currency;
import models.enums.GroupType;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String email;

    private final String name;
    //TODO check these
    //private double balance;
    private Currency currency;
    private ArrayList<Group> groups;
    private ArrayList<Expense> credits;


    public User(String username, String password, String email, String name) {//Also set all defaults
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.currency = Currency.GTC;
        this.groups = new ArrayList<>();
        this.credits = new ArrayList<>();
    }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public String getEmail() { return email; }

    public String getName() { return name; }

    public Currency getCurrency() { return currency; }

    public ArrayList<Group> getGroups() { return groups; }

    public ArrayList<Expense> getCredits() { return credits; }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }

    public void setEmail(String email) { this.email = email; }

    public void setCurrency(Currency currency) { this.currency = currency; }
}