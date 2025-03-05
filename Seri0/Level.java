import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Level {
    WARNING, ERROR, DEBUG, INFO
}
public class e0 {

    public static void main(String[] args) {
        // [YYYY-MM-DD HH:MM:SS] [LOG_LEVEL] Message
        Scanner input = new Scanner(System.in);
        String logRegex = "\\[(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})\\] \\[(\\w+)\\] (.*)";
        Pattern logPattern = Pattern.compile(logRegex);

        int log_count = input.nextInt();
        input.nextLine();

        Log[] logs = new Log[log_count];
        for (int i = 0; i < log_count; i++) {

            Matcher matcher = logPattern.matcher(input.nextLine());
            if (matcher.matches()) {
                try {
                    int year = Integer.parseInt(matcher.group(1));
                    int month = Integer.parseInt(matcher.group(2));
                    int day = Integer.parseInt(matcher.group(3));
                    int hour = Integer.parseInt(matcher.group(4));
                    int minute = Integer.parseInt(matcher.group(5));
                    int second = Integer.parseInt(matcher.group(6));
                    String level = matcher.group(7);
                    String message = matcher.group(8);
                    Level logLevel = Level.valueOf(level);
                    LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute, second);
                    logs[i] = new Log(dateTime, logLevel, message);
                    System.out.print(i + ":");
                    System.out.println(logs[i].toString());
                } catch (Exception e) {
                    System.out.println(e);
                }

            } else {
                System.out.println("Invalid log format.");
            }
        }

        input.close();
    }
}

class Log {
    public Level level;
    public String message;
    public LocalDateTime dateTime;
    public static int logsCount = 0;

    public Log(LocalDateTime dateTime, Level level, String message) {
        this.dateTime = dateTime;
        this.message = message;
        this.level = level;
        logsCount++;
        System.out.println("Log created sus fully ");
    };

    @Override
    public String toString() {
        return (this.dateTime + "  Level:  " + this.level + "Message: " + this.message);
    }
}