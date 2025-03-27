package controllers;
import models.*;
import models.enums.Currency;
import models.enums.DashboardCommands;
import models.enums.GroupType;
import models.enums.Menu;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;


/*
Explanation:
- This is a controller class for the dashboard Controller.
- This class will be used to implement functions that do dashboard operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */
public class DashboardController {
    public static String invalidGroupNameError = ("group name format is invalid!"),
            invalidGroupTypeError=("group type is invalid!"),
            invalidExpenseError=("expense format is invalid!"),
            invalidMoneyError=("input money format is invalid!"),
            notFoundUserError=("user not found!"),
            notFoundGroupError=("group not found!"),
            notAccessError=("only the group creator can add users!"),
            userAlreadyInGroupError=("user already in the group!"),
            mismatchEmailError=("the email provided does not match the username!"),
            groupSuccess=("group created successfully!"),
            addUserSuccess=("user added to the group successfully!"),
            expenseSuccess=("expense added successfully!"),
            goToProfileSuccess=("you are now in profile menu!"),
            logoutSuccess=("user logged out successfully.you are now in login menu!"),
            invalidSumError=("the sum of individual costs does not equal the total cost!");


    public Group GetGroupById(int groupId) {
        for (Group group : App.allGroups) {
            if (group.getId() == groupId) {
                return group;
            }
        }
        return null;
    }

    public Result CreateNewGroup(User loggedInUser, String name, String type) {
        if(DashboardCommands.groupNameRegex.GetMatcher(name) == null) {
            return new Result(false,invalidGroupNameError);
        } else if(DashboardCommands.groupTypeRegex.GetMatcher(type) == null) {
            return new Result(false,invalidGroupTypeError);
        } else{
            ArrayList<User> groupUsers = new ArrayList<>();
            groupUsers.add(loggedInUser);
            Group newGroup = new Group(loggedInUser,groupUsers, name, GroupType.fromDescription(type), App.allGroups.size()+1);
            loggedInUser.getGroups().add(newGroup);
            App.allGroups.add(newGroup);
            return new Result(true,groupSuccess);
        }
    }

    public void ShowUsersGroups(User loggedInUser) {
        if(loggedInUser.getGroups().isEmpty()) {
            System.out.println();
        }
        for(Group group : loggedInUser.getGroups()) {
            System.out.println("group name : " + group.getName());
            System.out.println("id : "+ group.getId());
            System.out.println("type : "+ group.getGroupType().getDescription());
            System.out.println("creator : " + group.getCreator().getName());
            System.out.println("members :");
            for(User user : group.getUsers()) {
                System.out.println(user.getName());
            }
            System.out.println("--------------------");
        }
    }

    public Result AddUserToGroup(User loggedInUser, String username, String email, String groupId) {
        int id=0;
        try {
            id = Integer.parseInt(groupId);
        }catch (Exception e) {
            System.out.println("we cant parse int group id");
        }
        Group group = GetGroupById(id);
        User userToAdd = App.GetUserByUsername(username);
        if(userToAdd == null) {
            return new Result(false,notFoundUserError);
        } else if(group == null){
            return new Result(false,notFoundGroupError);
        } else if(userToAdd.getGroups().contains(GetGroupById(id))) {
            return new Result(false,userAlreadyInGroupError);
        } else if(!userToAdd.getEmail().equals(email)) {
            return new Result(false,mismatchEmailError);
        } else if(!group.getCreator().getUsername().equals(loggedInUser.getUsername())) {
            return new Result(false,notAccessError);
        } else{
            group.AddUser(userToAdd);
            userToAdd.getGroups().add(group);
            return new Result(true,addUserSuccess);
        }
    }

    public Result AddExpense(Scanner scanner, User loggedInUser, String groupId, String equality, String totalExpense, String numberOfUsers) {
        int numOfUsers=1;// i dont sett it 0 to prevent zero division exception
        int totalExp=0;
        Group group = null;
        try{
            numOfUsers = Integer.parseInt(numberOfUsers);
            group = GetGroupById(Integer.parseInt(groupId));
            totalExp = Integer.parseInt(totalExpense);
        }
        catch(Exception e) {
            System.out.println("we cant parse int group id");
        }

        if(group == null)
            return new Result(false, notFoundGroupError);
        else if(equality.equals("equally")) {
            StringBuilder invalidUsernames = new StringBuilder();
            ArrayList<Expense>expensesToAdd = new ArrayList<>();
            boolean notFoundUserError = false;
            for (int i = 0; i < numOfUsers; i++) {
                String input = scanner.nextLine();
                Matcher matcher = DashboardCommands.getUsernameRegex.GetMatcher(input);
                String username;
                if(matcher != null) {
                    username = matcher.group("username");
                }
                else{
                    username = "";
                    System.out.println("username not valid");
                    //System.out.println("couldn't match {" + input + "} on the regex {" + DashboardCommands.getUsernameRegex.GetPattern() +"}" );
                }
                User user = App.GetUserByUsername(username);
                if(user == null|| !group.HasUser(user)) {
                    notFoundUserError = true;
                    invalidUsernames.append(username).append(" not in group!\n");
                }else if(!notFoundUserError){
                    expensesToAdd.add(new Expense(user,0,loggedInUser.getCurrency(),group));
                }
            }
            if(notFoundUserError){
                if (!invalidUsernames.isEmpty() && invalidUsernames.charAt(invalidUsernames.length() - 1) == '\n') {
                    invalidUsernames.deleteCharAt(invalidUsernames.length() - 1);
                }//remove last \n
                return new Result(false, invalidUsernames.toString());
            } else if(DashboardCommands.expenseRegex.GetMatcher(totalExpense) == null) {
                return new Result(false,invalidExpenseError);
            } else {
                int amountForEachUser = totalExp/numOfUsers;
                if(totalExp % numOfUsers != 0) {
                    return new Result(false,invalidSumError);
                }
                for(Expense expenseToAdd : expensesToAdd) {
                    if(!expenseToAdd.getDebtor().equals(loggedInUser)) {
                        expenseToAdd.setValue(amountForEachUser, loggedInUser.getCurrency());
                        loggedInUser.getCredits().add(expenseToAdd);
                    }
                }
            }
        }
        else if(equality.equals("unequally")) {
            ArrayList<Expense>expensesToAdd = new ArrayList<>();
            StringBuilder invalidUsernames = new StringBuilder();
            boolean notFoundUserError = false;
            boolean expenseError = false;
            int sumOfIndividuals= 0;
            for (int i = 0; i < numOfUsers; i++) {
                Matcher matcher = DashboardCommands.getUsernameExpenseRegex.GetMatcher(scanner.nextLine());
                String username;
                int amount = 0;
                if(matcher != null) {
                    username = matcher.group("username");
                    if(DashboardCommands.expenseRegex.GetMatcher(matcher.group("amount")) == null) {
                        expenseError = true;
                    }
                    else{
                        amount = Integer.parseInt(matcher.group("amount"));
                        //System.out.println(amount);
                    }
                }
                else{
                    username = "";
                    System.out.println("username expense not valid");
                }
                User user = App.GetUserByUsername(username);
                if(user == null || !group.HasUser(user)) {
                    notFoundUserError = true;
                    invalidUsernames.append(username).append(" not in group!\n");
                }else if(!notFoundUserError && !expenseError){
                    expensesToAdd.add(new Expense(user,amount,loggedInUser.getCurrency(),group));
                    sumOfIndividuals+= amount;
                }
            }
            if(notFoundUserError){
                if (!invalidUsernames.isEmpty() && invalidUsernames.charAt(invalidUsernames.length() - 1) == '\n') {
                    invalidUsernames.deleteCharAt(invalidUsernames.length() - 1);
                }//remove last \n
                return new Result(false, invalidUsernames.toString());
            } else if(DashboardCommands.expenseRegex.GetMatcher(totalExpense) == null || expenseError) {
                return new Result(false,invalidExpenseError);
            } else if(sumOfIndividuals != totalExp) {
                return new Result(false,invalidSumError);
            } else {
                for(Expense expenseToAdd : expensesToAdd) {
                    if(!expenseToAdd.getDebtor().equals(loggedInUser)) {
                        loggedInUser.getCredits().add(expenseToAdd);
                        // remove these
                    }
                }
            }
        }
        return new Result(true,expenseSuccess);
    }

    public double PriceToPayToUser(User loggedInUser, User counterparty){
        double totalGtcToPay = 0.0;

        for (Expense debt : counterparty.getCredits()) {
            if (debt.getDebtor().equals(loggedInUser)) {
                totalGtcToPay += debt.getAmount() * debt.getCurrency().getValue();
            }
        }

        for (Expense credit : loggedInUser.getCredits()) {
            if (credit.getDebtor().equals(counterparty)) {
                totalGtcToPay -= credit.getAmount() * credit.getCurrency().getValue();
            }
        }
        return totalGtcToPay;
    }

    public Result ShowUserBalance(User loggedInUser, String username) {
        User counterparty = App.GetUserByUsername(username);
        if (counterparty == null) {
            return new Result(false, notFoundUserError);
        }

        double totalGtcToPay = PriceToPayToUser(loggedInUser, counterparty);
        int totalPrice = (int) (totalGtcToPay / loggedInUser.getCurrency().getValue());
        StringBuilder factorMessage = new StringBuilder();

        if (totalPrice == 0) {
            factorMessage.append("you are settled with ").append(username);
            return new Result(true, factorMessage.toString());
        } else {
            if (totalPrice > 0.0) {
                //you owe <username> <price> <Currency> in "<group name1>", "<group name2>", ...!
                factorMessage.append("you owe ").append(username).append(" ");
            } else {
                //<username> owes you <price> <Currency> in "<group name1>", "<group name2>", ...!
                factorMessage.append(username).append(" owes you ");
                totalPrice = -totalPrice;
            }

            factorMessage.append(totalPrice).append(" ").append(loggedInUser.getCurrency()).append(" in ");
            for (Group group : App.allGroups) {
                if (group.HasUser(loggedInUser) && group.HasUser(counterparty)) {
                    factorMessage.append(group.getName()).append(", ");
                }
            }

            factorMessage.setLength(factorMessage.length() - 2);
            factorMessage.append("!");
            return new Result(true, factorMessage.toString());
        }
    }

    public Result SettleUpWithUser(User loggedInUser, String username, String inputMoney) {
        User counterparty = App.GetUserByUsername(username);
        if (counterparty == null) {
            return new Result(false, notFoundUserError);
        } else if(DashboardCommands.expenseRegex.GetMatcher(inputMoney) == null) {
            return new Result(false, invalidMoneyError);
        }

        //TODO if not common group
        for (Group group : App.allGroups) {
            if(group.HasUser(loggedInUser) && group.HasUser(counterparty)) {
                loggedInUser.getCredits().add(new Expense(counterparty,
                        Double.parseDouble(inputMoney),loggedInUser.getCurrency(),group));
                
                break;
            }
        }

        double GtcToPay = PriceToPayToUser(loggedInUser,counterparty);
        int PriceToPay = (int)(GtcToPay/loggedInUser.getCurrency().getValue());
        StringBuilder factorMessage = new StringBuilder();
        if(PriceToPay==0) {
            factorMessage.append("you are settled with ").append(username).append(" now!");
        } else {
            if(PriceToPay < 0) {
                PriceToPay = -PriceToPay;
                factorMessage.append(username).append(" owes you ");
            } else {
                //you owe <username> <price> <Currency> now!
                factorMessage.append("you owe ").append(username);
            }
            factorMessage.append(" ").append(PriceToPay).
                    append(" ").append(loggedInUser.getCurrency()).append(" now!");
        }
        return new Result(true, factorMessage.toString());
    }

    public Result GoToProfile() {
        App.setCurrentMenu(Menu.ProfileMenu);
        return new Result(true,goToProfileSuccess);
    }

    public Result Logout() {
        App.setLoggedInUser(null);
        App.setCurrentMenu(Menu.LoginMenu);
        return new Result(true,logoutSuccess);
    }

    public void Exit() { App.setCurrentMenu(Menu.ExitMenu);}
}