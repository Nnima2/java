package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Explanation:
- we have commands in our dashboard and this commands need regexes to be checked.
- put those regexes here and use them in your code.
- this regexes need some functions, put those functions in here.
 */
public enum DashboardCommands implements Command {
    //regexes
    showGroupsRegex("\\s*show\\s+my\\s+groups\\s*"),
    createGroupRegex("\\s*create-group\\s+-n\\s+(?<name>[\\S\\s]+)\\s+-t\\s+(?<type>[\\S]+)\\s*"),
    //TODO  check edge case spaces
    addUserRegex("\\s*add-user\\s+-u\\s+(?<username>[\\S]+)\\s+-e\\s+"+
                 "(?<email>[\\S]+)\\s+-g\\s+(?<groupId>-?[0-9]+)\\s*"),
    addExpense("\\s*add-expense\\s+-g\\s+(?<groupId>-?[0-9]+)\\s+-s\\s+(?<equality>equally|unequally)"+
                "\\s+-t\\s+(?<totalExpense>[\\S]+)\\s+-n\\s+(?<numberOfUsers>-?[0-9]+)\\s*"),
    getUsernameRegex("\\s*(?<username>[\\S]+)\\s*"),
    getUsernameExpenseRegex("\\s*(?<username>[\\S]+)\\s+(?<amount>[\\S]+)\\s*"),
    showBalanceRegex("\\s*show\\s+balance\\s+-u\\s+(?<username>[\\S]+)\\s*"),
    settleUpRegex("\\s*settle-up\\s+-u\\s+(?<username>[\\S]+)\\s+-m\\s+(?<amount>-?[0-9]+)\\s*"),
    goToProfileRegex("\\s*go\\s+to\\s+profile\\s+menu\\s*"),
    logoutRegex("logout"),
    exitRegex("\\s*exit\\s*"),
    expenseRegex("^-?[0-9]+$"),
    groupNameRegex("^[a-zA-Z0-9!@#$%^&* ]{4,30}$"),
    groupTypeRegex("^Home|Trip|Zan-o-Bache|Other$");

    private final String pattern;

    DashboardCommands(String pattern) { this.pattern = pattern; }

    @Override
    public Matcher GetMatcher(String input)
    {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);
        if(matcher.matches())
        {
            return matcher;
        }
        return null;
    }

    @Override
    public String GetPattern() {
        return this.pattern;
    }
}
