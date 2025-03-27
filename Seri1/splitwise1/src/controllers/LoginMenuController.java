package controllers;
/*
Explanation:
- This is a controller class for the login menu Controller.
- This class will be used to implement functions that do log in menu operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */

import models.App;
import models.Result;
import models.User;
import models.enums.Menu;

public class LoginMenuController {
    public static String notExistingUsernameError = ("username doesn't exist!"),
            incorrectPasswordError = ("password is incorrect!"),
            loginSuccessMessage =("user logged in successfully.you are now in dashboard!"),
            misMatchEmailError = ("email doesn't match!"),
            signUpMenuMassage = ("you are now in signup menu!");


    public Result Login(String username, String password) {
        User user = App.GetUserByUsername(username);
        if(user == null) {
            return new Result(false,notExistingUsernameError);
        } else if(!user.getPassword().equals(password)) {
            return new Result(false,incorrectPasswordError);
        } else{
            App.setCurrentMenu(Menu.Dashboard);
            App.setLoggedInUser(user);
            return new Result(true,loginSuccessMessage);
        }
    }

    public Result GoToSignUpMenu() {
        App.setCurrentMenu(Menu.SignUpMenu);
        return new Result(true, signUpMenuMassage);
    }

    public void Exit(){ App.setCurrentMenu(Menu.ExitMenu); }

    public Result ForgotPassword(String username, String email) {
        User user = App.GetUserByUsername(username);
        if(user == null) {
            return new Result(false,notExistingUsernameError);
        } else if(!user.getEmail().equals(email)) {
            return new Result(false,misMatchEmailError);
        } else {
            return new Result(true,"password : " + user.getPassword());
        }
    }
}
