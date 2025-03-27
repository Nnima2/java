package controllers;
/*
Explanation:
- This is a controller class for the profile menu Controller.
- This class will be used to implement functions that do profile menu operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */
import models.App;
import models.Result;
import models.User;
import models.enums.Currency;
import models.enums.Menu;
import models.enums.ProfileMenuCommands;
import models.enums.SignUpMenuCommands;

public class ProfileMenuController {
    public static String invalidCurrencyError=("currency format is invalid!"),
            sameUsernameError=("please enter a new username!"),
            samePasswordError=("please enter a new password!"),
            invalidUsernameError=("new username format is invalid!"),
            invalidPasswordError=("new password format is invalid!"),
            incorrectPasswordError=("password incorrect!"),
            existingUsernameError=("this username is already taken!"),
            changePasswordSuccess=("your password changed successfully!"),
            goToDashboardSuccess=("you are now in dashboard!"),
            logoutSuccess=("user logged out successfully.you are now in login menu!");


    LoginMenuController loginController = new LoginMenuController();

    public Result ShowCurrentUserInfo(User loggedInUser) {
        String info  = "username : "  + loggedInUser.getUsername() +
                "\npassword : " + loggedInUser.getPassword() +
                "\ncurrency : " + loggedInUser.getCurrency() +
                "\nemail : " + loggedInUser.getEmail() +
                "\nname : " + loggedInUser.getName();
        return new Result(true,info);
    }

    public Result ChangeUsername(User loggedInUser, String newUsername) {

        if (loggedInUser.getUsername().equals(newUsername)) {
            return new Result(false,sameUsernameError);
        } else if(App.GetUserByUsername(newUsername)!=null) {
           return new Result(false,existingUsernameError);
        } else if(ProfileMenuCommands.usernameRegex.GetMatcher(newUsername)==null) {
            return new Result(false,invalidUsernameError);
        } else {
            loggedInUser.setUsername(newUsername);
            return new Result(true,"your username changed to "+ newUsername + " successfully!");
        }
    }

    public Result ChangePassword(User loggedInUser, String oldPassword, String newPassword) {

        if(!loggedInUser.getPassword().equals(oldPassword)) {
            return new Result(false,incorrectPasswordError);
        } else if(oldPassword.equals(newPassword)) {
            return new Result(false,samePasswordError);
        } else if (SignUpMenuCommands.passwordRegex.GetMatcher(newPassword)==null) {
            return new Result(false,invalidUsernameError);
        } else {
            loggedInUser.setPassword(newPassword);
            return new Result(true,changePasswordSuccess);
        }

    }

    public Result ChangeCurrancy(User loggedInUser, String currency) {
        if(ProfileMenuCommands.currencyRegex.GetMatcher(currency)==null) {
            return new Result(false,invalidCurrencyError);
        } else {
            loggedInUser.setCurrency(Currency.getCurrency(currency));
            return new Result(true,"your currency changed to " + currency + " successfully!");
        }
    }

    public Result BackToDashboard() {
        App.setCurrentMenu(Menu.Dashboard);
        return new Result(true,goToDashboardSuccess);
    }

    public Result Logout() {
        App.setLoggedInUser(null);
        App.setCurrentMenu(Menu.LoginMenu);
        return new Result(true,logoutSuccess);
    }

    public void Exit() { App.setCurrentMenu(Menu.ExitMenu);}

}
