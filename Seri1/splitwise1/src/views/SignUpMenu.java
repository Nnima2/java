package views;
import java.util.Scanner;
import java.util.regex.Matcher;

import controllers.SignUpMenuController;
import models.enums.SignUpMenuCommands;
/*
Explanation:
- This is a view class for the SignUpMenu.
- This class should use to check inputs and print outputs for the SignUpMenu.
- notice that : this class should not have any logic and just use it to get inputs and handle it to use correct methods in controller.
 */

public class SignUpMenu  implements AppMenu {

    public static String invalidCommandError=("invalid command!");

    private final SignUpMenuController controller = new SignUpMenuController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        //System.out.println("testing {{" + input +  "}} on pattern \\n {{" + SignUpMenuCommands.addUserRegex );
        if((matcher = SignUpMenuCommands.addUserRegex.GetMatcher(input)) != null) {
            String message = controller.SignUp(matcher.group("username"),matcher.group("password"),
                    matcher.group("email"),matcher.group("name")).getMessage();
            System.out.println(message);
        } else if(SignUpMenuCommands.goToLoginRegex.GetMatcher(input) != null) {
            String message = controller.GoToLoginMenu().getMessage();
            System.out.println(message);
        } else if(SignUpMenuCommands.ExitRegex.GetMatcher(input) != null) {
            controller.Exit();
        } else {
            System.out.println(invalidCommandError);
        }
    }
}