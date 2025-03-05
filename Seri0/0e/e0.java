import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class e0 {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int log_count = input.nextInt();
        input.nextLine();

        Log[] logs = new Log[log_count];
        Log.GetLogs(logs, input);
        // Log.ShowAllLogs(logs);

        while (input.hasNextLine()) {
            String[] cmdParts = input.nextLine().split(" ");
            String command = cmdParts[0];

            switch (command) {
                case "LEVEL": {
                    Log.LogsLevel(logs, Level.valueOf(cmdParts[1]));
                }
                    break;
                case "COUNT_LEVEL": {
                    System.out.println(Log.CountLevel(logs, Level.valueOf(cmdParts[1])));
                }
                    break;
                case "DATE_RANGE": {
                    String[] date1 = cmdParts[1].split("-");
                    String[] date2 = cmdParts[2].split("-");
                    Log.DateRange(logs, date1, date2);
                }
                    break;

                case "ERROR_TIMESTAMPS": {
                    if (cmdParts.length == 1) {
                        Log.ErrorTimeStamps(logs, false);
                    } else if (Arrays.asList(cmdParts).contains("--reverse")) {
                        Log.ErrorTimeStamps(logs, true);
                    }
                }
                    break;
                case "CONTAINS": {
                    Log.LogContains(logs, cmdParts[1]);
                }
                    break;
                case "FREQUENCY_ANALYSIS": {
                    if (cmdParts.length == 1) {
                        Log.FrequencyAnalysis(logs, 5);
                    } else if (cmdParts.length == 3 && Arrays.asList(cmdParts).contains("--top")) {
                        try {
                            int topCount = Integer.parseInt(cmdParts[2]);
                            Log.FrequencyAnalysis(logs, topCount);
                        } catch (Exception e) {
                            System.out.println("can not int in freq analysis");
                        }
                    }
                }
                    break;
                case "TOP_K_LEVEL": {
                    if (cmdParts.length == 3) {
                        int count = Integer.parseInt(cmdParts[1]);
                        Level level = Level.valueOf(cmdParts[2]);
                        Log.TopKLevel(logs, level, count, false);
                    } else if (Arrays.asList(cmdParts).contains("--reverse")) {
                        int count = Integer.parseInt(cmdParts[1]);
                        Level level = Level.valueOf(cmdParts[2]);
                        Log.TopKLevel(logs, level, count, true);
                    }
                }
                    break;
                default:
                    System.out.println("Unexpected Command....");
                    break;
            }

            System.out.println();
        }

        input.close();
    }
}

enum Level {
    WARNING, ERROR, DEBUG, INFO
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
        // System.out.println("Log created sus fully ");
    }

    static void GetLogs(Log[] logs, Scanner input) {
        String logRegex = "\\[(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})\\] \\[(\\w+)\\] (.*)";
        // [YYYY-MM-DD HH:MM:SS] [LOG_LEVEL] Message
        Pattern logPattern = Pattern.compile(logRegex);

        for (int i = 0; i < logs.length; i++) {

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
                    // System.out.print(i + ":");
                    // logs[i].PrintLog();
                } catch (Exception e) {
                    System.out.println("can not int in get log.GetLogs");
                }

            } else {
                System.out.println("invalid regex");
            }
        }
    }

    @Override
    public String toString() {// for developer to check
        return (this.dateTime + "  Level:  " + this.level + "   Message: " + this.message);
    }

    public void PrintLog() {

        System.out.printf("[%4d-%02d-%02d %02d:%02d:%02d] [%s] %s\n", dateTime.getYear(),
                dateTime.getMonth().getValue(), dateTime.getDayOfMonth(), dateTime.getHour(), dateTime.getMinute(),
                dateTime.getSecond(), level, message);
    }

    public void PrintDateTime() {
        System.out.printf("%4d-%02d-%02d %02d:%02d:%02d\n", dateTime.getYear(),
                dateTime.getMonth().getValue(), dateTime.getDayOfMonth(), dateTime.getHour(), dateTime.getMinute(),
                dateTime.getSecond());
    }

    public static void ShowAllLogs(Log[] logs) {
        for (Log log : logs) {
            log.PrintLog();
        }
    }

    public static void LogsLevel(Log[] logs, Level filteringLevel) {
        for (Log log : logs) {
            if (log.level == filteringLevel) {
                log.PrintLog();
            }
        }
    }

    public static int CountLevel(Log[] logs, Level filteringLevel) {
        int count = 0;
        for (Log log : logs) {
            if (log.level == filteringLevel)
                count++;
        }
        return count;
    }

    public static void LogContains(Log[] logs, String Keyword) {
        for (Log log : logs) {
            if (log.message.contains(Keyword)) {
                log.PrintLog();
            }
        }
    }

    public static void ErrorTimeStamps(Log[] logs, boolean reverse) {
        if (reverse) {
            for (int i = logs.length - 1; i >= 0; i--) {
                if (logs[i].level == Level.ERROR) {
                    logs[i].PrintDateTime();
                }
            }
        } else {
            for (Log log : logs) {
                if (log.level == Level.ERROR) {
                    log.PrintDateTime();
                }
            }
        }
    }

    public static void DateRange(Log[] logs, String[] date1Str, String[] date2Str) {
        int year1, month1, day1, year2, month2, day2;
        try {
            year1 = Integer.parseInt(date1Str[0]);
            month1 = Integer.parseInt(date1Str[1]);
            day1 = Integer.parseInt(date1Str[2]);
            year2 = Integer.parseInt(date2Str[0]);
            month2 = Integer.parseInt(date2Str[1]);
            day2 = Integer.parseInt(date2Str[2]);

            LocalDateTime dateTime1 = LocalDateTime.of(year1, month1, day1, 0, 0, 0);
            LocalDateTime dateTime2 = LocalDateTime.of(year2, month2, day2, 23,59,59);
            for (Log log : logs) {
                if (log.dateTime.isAfter(dateTime1) && log.dateTime.isBefore(dateTime2) ||
                        (year1 == log.dateTime.getYear() && month1 == log.dateTime.getMonthValue()
                                && day1 == log.dateTime.getDayOfMonth())
                        ||
                        (year2 == log.dateTime.getYear() && month2 == log.dateTime.getMonthValue()
                                && day2 == log.dateTime.getDayOfMonth())) {
                    log.PrintLog();
                }
            }
        } catch (Exception e) {
            System.out.println("Can not int date in log.date range");
        }

    }

    public static void FrequencyAnalysis(Log[] logs, int topCount) {

        Map<String, Integer> wordCount = new HashMap<>();

        for (Log log : logs) {
            String[] words = log.message.toLowerCase().split(" ");// to lower case
            for (String word : words) {
                if (!word.isEmpty()) {
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                }
            }
        }

        List<Map.Entry<String, Integer>> sortedWords = new ArrayList<>(wordCount.entrySet());

        // sortedWords.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        sortedWords.sort((a, b) -> {
            int cmp = b.getValue().compareTo(a.getValue());
            return (cmp != 0) ? cmp : a.getKey().compareTo(b.getKey());// length
        });

        for (int i = 0; i < Math.min(topCount, sortedWords.size()); i++) {
            System.out.println(sortedWords.get(i).getKey() + ": " + sortedWords.get(i).getValue());
        }
    }

    public static void TopKLevel(Log[] logs, Level level, int count, boolean reverse) {
        List<Log> filteredLogs = new ArrayList<>(count);
        for (Log log : logs) {
            if (log.level == level) {
                filteredLogs.add(log);
            }
        }

        filteredLogs.sort((a, b) -> b.dateTime.compareTo(a.dateTime));

        if (reverse) {
            Collections.reverse(filteredLogs);
        }

        for (int i = 0; i < count; i++) {
            Log plog = filteredLogs.get(i);
            plog.PrintLog();
        }
    }
}