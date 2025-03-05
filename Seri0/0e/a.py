import re
from collections import Counter

def parse_logs(log_lines):
    logs = []
    log_pattern = re.compile(r"\[(.*?)\] \[(.*?)\] (.*)")
    for line in log_lines:
        match = log_pattern.match(line)
        if match:
            timestamp, level, message = match.groups()
            logs.append((timestamp, level, message))
    return logs

def count_level(logs, level):
    return sum(1 for log in logs if log[1] == level)

def filter_by_level(logs, level):
    return [log for log in logs if log[1] == level]

def filter_by_keyword(logs, keyword):
    return [log for log in logs if keyword in log[2]]

def error_timestamps(logs, reverse=False):
    errors = [log[0] for log in logs if log[1] == "ERROR"]
    return errors[::-1] if reverse else errors

def frequency_analysis(logs, top_n=5):
    words = []
    for log in logs:
        words.extend(re.findall(r"\b\w+\b", log[2].lower()))
    counter = Counter(words)
    return counter.most_common(top_n)

# Sample Logs
log_entries = [
    "[2025-03-01 10:15:30] [INFO] System startup completed successfully",
    "[2025-03-01 11:20:45] [WARNING] High CPU usage detected: 92% utilization",
    "[2025-03-01 12:05:10] [ERROR] Database connection failed: timeout after 30 seconds",
    "[2025-03-01 13:45:00] [DEBUG] Cache cleared successfully",
    "[2025-03-01 15:30:55] [WARNING] Low disk space on /var: 5GB remaining",
    "[2025-03-01 16:00:00] [ERROR] Failed to send email notification: SMTP server unreachable",
    "[2025-03-02 07:40:15] [ERROR] Disk write error encountered: /var/log/syslog",
    "[2025-03-02 12:50:50] [ERROR] Payment processing failed: Insufficient funds",
    "[2025-03-02 14:10:30] [INFO] User 'admin' logged in successfully",
    "[2025-03-02 15:20:45] [WARNING] System temperature high: 80°C detected",
    "[2025-03-02 16:30:50] [DEBUG] Network latency measured at 150ms",
    "[2025-03-02 17:45:15] [ERROR] Application crashed due to memory overflow",
    "[2025-03-02 18:55:40] [INFO] Scheduled backup completed successfully",
    "[2025-03-02 20:05:55] [WARNING] Firewall detected multiple unauthorized access attempts",
    "[2025-03-02 21:15:10] [DEBUG] Debugging session started by user 'developer'",
    "[2025-03-02 22:25:25] [ERROR] Hard drive failure detected: Read errors increasing",
]

parsed_logs = parse_logs(log_entries)

# Generating Test Cases
print("COUNT_LEVEL INFO:", count_level(parsed_logs, "INFO"))
print("COUNT_LEVEL ERROR:", count_level(parsed_logs, "ERROR"))
print("COUNT_LEVEL WARNING:", count_level(parsed_logs, "WARNING"))
print("LEVEL WARNING:", filter_by_level(parsed_logs, "WARNING"))
print("LEVEL INFO:", filter_by_level(parsed_logs, "INFO"))
print("CONTAINS 'error':", filter_by_keyword(parsed_logs, "error"))
print("CONTAINS 'backup':", filter_by_keyword(parsed_logs, "backup"))
print("ERROR_TIMESTAMPS --reverse:", error_timestamps(parsed_logs, reverse=True))
print("ERROR_TIMESTAMPS:", error_timestamps(parsed_logs))
print("FREQUENCY_ANALYSIS --top 3:", frequency_analysis(parsed_logs, top_n=3))
print("FREQUENCY_ANALYSIS --top 5:", frequency_analysis(parsed_logs, top_n=5))
