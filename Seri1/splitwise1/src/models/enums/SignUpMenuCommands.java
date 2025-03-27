package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
Explanation:
- we have commands in our sign-up menu and this commands need regexes to be checked.
- put those regexes here and use them in your code.
- this regexes need some functions, put those functions in here.
 */
public enum SignUpMenuCommands implements Command {
    //regexes
    addUserRegex("\\s*register\\s+-u\\s+(?<username>[\\S]+)\\s+-p\\s+(?<password>[\\S]+)"+
            "\\s+-e\\s+(?<email>[\\S]+)\\s+-n\\s+(?<name>[\\S]+)\\s*"),
    goToLoginRegex("\\s*go\\s+to\\s+login\\s+menu\\s*"),
    usernameRegex("^[a-zA-Z][a-zA-Z0-9_.-]{3,9}$"),
    passwordRegex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,12}$"),
    emailRegex("^(?<emailName>[a-zA-Z][a-zA-Z0-9_.+-]+)@(?<domain>(?=.{3,7})(?!.*[.-].*[-.].*[.])"+
            "(?!.*[-].*[-])(?)[a-z]+(?:[-.][a-z]+)*)\\.(?<Tld>org|com|net|edu)$"),
    //TODO . add length condition to domain
    nameRegex("^[a-zA-Z][a-zA-Z-]*[a-zA_Z]$"),
    ExitRegex("\\s*exit\\s*");

    private final String pattern;

    SignUpMenuCommands(String pattern) {
        this.pattern = pattern;
    }

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