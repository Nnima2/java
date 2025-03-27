package views;
/*
Explanation:
- This is a view class for the dashboard.
- This class should use to check inputs and print outputs for the dashboard.
- notice that : this class should not have any logic and just use it to get inputs and handle it to use correct methods in controller.
 */

import controllers.DashboardController;
import models.App;
import models.enums.DashboardCommands;
import models.enums.ProfileMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class Dashboard implements AppMenu {

    public static String invalidCommandError=("invalid command!");

    public DashboardController controller = new DashboardController();
    @Override
    public void check(Scanner scanner) {

        String input = scanner.nextLine();
        Matcher matcher;

        if(DashboardCommands.showGroupsRegex.GetMatcher(input) != null) {
            controller.ShowUsersGroups(App.getLoggedInUser());
        } else if((matcher = DashboardCommands.createGroupRegex.GetMatcher(input)) != null) {
            String message = controller.CreateNewGroup(App.getLoggedInUser(),
                                      matcher.group("name"),matcher.group("type")).message();
            System.out.println(message);
        } else if((matcher = DashboardCommands.addUserRegex.GetMatcher(input)) != null) {
            String message = controller.AddUserToGroup(App.getLoggedInUser(),matcher.group("username"),
                                      matcher.group("email"),matcher.group("groupId")).message();
            System.out.println(message);
        } else if((matcher = DashboardCommands.addExpense.GetMatcher(input)) != null) {
            String message = controller.AddExpense(scanner,App.getLoggedInUser(),matcher.group("groupId"),
                    matcher.group("equality"), matcher.group("totalExpense"),
                    matcher.group("numberOfUsers")).message();
            System.out.println(message);
        } else if((matcher = DashboardCommands.showBalanceRegex.GetMatcher(input)) != null) {
            String message = controller.ShowUserBalance(App.getLoggedInUser(),
                    matcher.group("username")).message();
            System.out.println(message);
        } else if((matcher = DashboardCommands.settleUpRegex.GetMatcher(input)) != null) {
            String message = controller.SettleUpWithUser(App.getLoggedInUser(),matcher.group("username"),
                    matcher.group("amount")).message();
            System.out.println(message);
        } else if( DashboardCommands.goToProfileRegex.GetMatcher(input) != null) {
            String message = controller.GoToProfile().message();
            System.out.println(message);
        } else if(DashboardCommands.logoutRegex.GetMatcher(input) != null) {
            String message = controller.Logout().message();
            System.out.println(message);
        } else if(DashboardCommands.exitRegex.GetMatcher(input) != null) {
            controller.Exit();
        } else {
            System.out.println(invalidCommandError);
        }
    }
}