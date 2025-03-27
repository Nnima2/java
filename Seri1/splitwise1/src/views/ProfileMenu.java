package views;
import controllers.ProfileMenuController;
import models.App;
import models.enums.ProfileMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

/*
Explanation: 
- This is a view class for profile menu.
- This class should use to check inputs and print outputs for profile menu.
- notice that : this class should not have any logic and just use it to get inputs and handle it to use correct methods in controller.
 */
public class ProfileMenu  implements AppMenu {

    public static String invalidCommandError=("invalid command!");
    private final ProfileMenuController controller = new ProfileMenuController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if((matcher = ProfileMenuCommands.showUserInfoRegex.GetMatcher(input)) != null) {
            String message = controller.ShowCurrentUserInfo(App.getLoggedInUser()).getMessage();
            System.out.println(message);
        } else if((matcher = ProfileMenuCommands.changeUsernameRegex.GetMatcher(input)) != null) {
            String message = controller.ChangeUsername(App.getLoggedInUser(),
                    matcher.group("newUsername")).message();
            System.out.println(message);
        } else if((matcher = ProfileMenuCommands.changePasswordRegex.GetMatcher(input)) != null) {
            String message = controller.ChangePassword(App.getLoggedInUser(),
                    matcher.group("oldPassword"),matcher.group("newPassword")).message();
            System.out.println(message);
        } else if((matcher = ProfileMenuCommands.changeCurrencyRegex.GetMatcher(input)) != null) {
            String message = controller.ChangeCurrancy(App.getLoggedInUser(),
                    matcher.group("currency")).message();
            System.out.println(message);
        } else if(ProfileMenuCommands.backToDashboardRegex.GetMatcher(input) != null) {
            String message = controller.BackToDashboard().message();
            System.out.println(message);
        } else if(ProfileMenuCommands.logoutRegex.GetMatcher(input) != null) {
            String message = controller.Logout().message();
            System.out.println(message);
        } else if(ProfileMenuCommands.exitRegex.GetMatcher(input) != null) {
            controller.Exit();
        } else {
            System.out.println(invalidCommandError);
        }
    }
}