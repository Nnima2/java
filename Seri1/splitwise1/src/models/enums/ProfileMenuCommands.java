package models.enums;
/*
Explanation:
- we have commands in our profile menu and this commands need regexes to be checked.
- put those regexes here and use them in your code.
- this regexes need some functions, put those functions in here.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands implements Command {
    //regexes
    showUserInfoRegex("\\s*show\\s+user\\s+info\\s*"),
    changeCurrencyRegex("\\s*change-currency\\s+-n\\s+(?<currency>[\\S]+)\\s*"),
    changeUsernameRegex("\\s*change-username\\s+-n\\s+(?<newUsername>[\\S]+)\\s*"),
    changePasswordRegex("\\s*change-password\\s+-o\\s+(?<oldPassword>[\\S]+)\\s+-n\\s+(?<newPassword>[\\S]+)\\s*"),
    backToDashboardRegex("\\s*back\\s*"),
    currencyRegex("^GTC|SUD|QTR$"),
    usernameRegex("^[a-zA-Z][a-zA-Z0-9_.-]{3,9}$"),
    logoutRegex("logout"),
    exitRegex("\\s*exit\\s*");
    //messages

    private final String pattern;
    ProfileMenuCommands(String pattern) { this.pattern = pattern; }

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