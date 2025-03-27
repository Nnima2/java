package controllers;
/*
Explanation:
- This is a controller class for the sign-up menu Controller.
- This class will be used to implement functions that do sign up menu operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */

import models.App;
import models.Result;
import models.User;
import models.enums.Menu;
import models.enums.SignUpMenuCommands;

public class SignUpMenuController {

    public static String invalidUsernameError=("username format is invalid!"),
            invalidPasswordError=("password format is invalid!"),
            invalidEmailError=("email format is invalid!"),
            invalidNameError=("name format is invalid!"),
            userExistsError=("this username is already taken!"),
            registerSuccess=("user registered successfully.you are now in login menu!"),
            loginMenuMassage=("you are now in login menu!");

    private boolean isUsernameTaken(String username) {
        for(User user: App.allUsers){
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public Result SignUp(String username, String password, String email, String name)
    {
        //TODO DEBUG
        //System.out.println("Testing {"+password+"} on  {"+SignUpMenuCommands.passwordRegex.GetPattern() + "}");
        if(SignUpMenuCommands.usernameRegex.GetMatcher(username) == null) {
            return new Result(false, invalidUsernameError);
        } else if(isUsernameTaken(username)) {
            return new Result(false, userExistsError);
        } else if(SignUpMenuCommands.passwordRegex.GetMatcher(password)==null){
            return new Result(false, invalidPasswordError);
        } else if(SignUpMenuCommands.emailRegex.GetMatcher(email) == null) {
            return new Result(false, invalidEmailError);
        } else if(SignUpMenuCommands.nameRegex.GetMatcher(name) == null) {
            return new Result(false, invalidNameError);
        }
        else {
            User newUser = new User(username, password, email, name);
            App.allUsers.add(newUser);
            App.setCurrentMenu(Menu.LoginMenu);
            return new Result(true, registerSuccess);
        }
    }

    public Result GoToLoginMenu() {
        App.setCurrentMenu(Menu.LoginMenu);
        return new Result(true, loginMenuMassage);
    }

    public void Exit(){ App.setCurrentMenu(Menu.ExitMenu); }
}
