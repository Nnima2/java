package models.enums;
import java.util.regex.Matcher;

public interface Command {
    public Matcher GetMatcher(String command);
    public String GetPattern();
}