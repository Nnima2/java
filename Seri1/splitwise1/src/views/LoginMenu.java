package views;
import java.util.Scanner;
import java.util.regex.Matcher;

import controllers.LoginMenuController;
import models.enums.LoginMenuCommands;
/*
Explanation:
- This is a view class for the login menu.
- This class should use to check inputs and print outputs for the login menu.
- notice that : this class should not have any logic and just use it to get inputs and handle it
    to use correct methods in controller.
 */

public class LoginMenu  implements AppMenu {

    public static String invalidCommandError = ("invalid command!");

    private final LoginMenuController controller = new LoginMenuController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if((matcher = LoginMenuCommands.loginRegex.GetMatcher(input)) != null) {
            String message = controller.Login(
                                matcher.group("username"),matcher.group("password")).getMessage();
            System.out.println(message);
        } else if((matcher = LoginMenuCommands.forgotPasswordRegex.GetMatcher(input)) != null){
            String message = controller.ForgotPassword(
                                matcher.group("username"),matcher.group("email")).message();
            System.out.println(message);
        } else if(LoginMenuCommands.goToSignUpRegex.GetMatcher(input) != null) {
            String message = controller.GoToSignUpMenu().getMessage();
            System.out.println(message);
        } else if(LoginMenuCommands.exitRegex.GetMatcher(input) != null) {
            controller.Exit();
        } else {
            System.out.println(invalidCommandError);
        }
    }

}
