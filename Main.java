import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

// Class representing a log entry
class LogEntry {
    long timestamp; // Timestamp of the log entry
    String logType; // Type of the log entry
    double severity; // Severity level of the log entry

    // Constructor to initialize a log entry
    public LogEntry(long timestamp, String logType, double severity) {
        this.timestamp = timestamp;
        this.logType = logType;
        this.severity = severity;
    }
}

// Class representing a log monitor
class LogMonitor {
    private List<LogEntry> logs = new ArrayList<>(); // List to store all log entrie
    private Map<String, List<LogEntry>> logTypeMap = new HashMap<>(); // Map to group log entries by type

    // Method to submit a new log entry
    public void submitLog(long timestamp, String logType, double severity) {
        // Method to submit a new log entry
        LogEntry entry = new LogEntry(timestamp, logType, severity);
        // Add the log entry to the list of all logs
        logs.add(entry);
        // Add the log entry to the map, grouping by log type
        logTypeMap.computeIfAbsent(logType, k -> new ArrayList<>()).add(entry);
    }

    // Method to compute and write log type statistics to a writer
    public void computeLogTypeStats(String logType, BufferedWriter writer) throws IOException {
        // Get the list of log entries for the specified log type
        List<LogEntry> entries = logTypeMap.get(logType);
        if (entries == null) {
            // If no entries found for the log type, write a message to the writer
            writer.write("No entries for log type: " + logType + "\n");
            return;
        }

        // Calculate statistics: min severity, max severity, mean severity
        double minSeverity = Double.MAX_VALUE;
        double maxSeverity = Double.MIN_VALUE;
        double sumSeverity = 0;

        for (LogEntry entry : entries) {
            minSeverity = Math.min(minSeverity, entry.severity);
            maxSeverity = Math.max(maxSeverity, entry.severity);
            sumSeverity += entry.severity;
        }

        double meanSeverity = sumSeverity / entries.size();
        // Format the statistics and write to the writer
        DecimalFormat df = new DecimalFormat("#.######");
        writer.write("Min: " + df.format(minSeverity) + ", Max: " + df.format(maxSeverity) + ", Mean: "
                + df.format(meanSeverity) + "\n");
    }

    // Method to compute and write timestamp statistics to a writer
    public void computeTimestampStats(String mode, long timestamp, BufferedWriter writer) throws IOException {
        // Calculate statistics for log entries based on timestamp and mode
        double minSeverity = Double.MAX_VALUE;
        double maxSeverity = Double.MIN_VALUE;
        double sumSeverity = 0;
        int count = 0;

        for (LogEntry entry : logs) {
            if ((mode.equals("BEFORE") && entry.timestamp < timestamp) ||
                    (mode.equals("AFTER") && entry.timestamp > timestamp)) {
                minSeverity = Math.min(minSeverity, entry.severity);
                maxSeverity = Math.max(maxSeverity, entry.severity);
                sumSeverity += entry.severity;
                count++;
            }
        }

        // If no matching entries found, write a message to the writer
        if (count == 0) {
            writer.write("Min: 0.0, Max: 0.0, Mean: 0.0\n");
            return;
        }

        // Calculate mean severity
        double meanSeverity = sumSeverity / count;
        // Format the statistics and write to the writer
        DecimalFormat df = new DecimalFormat("#.######");
        writer.write("Min: " + df.format(minSeverity) + ", Max: " + df.format(maxSeverity) + ", Mean: "
                + df.format(meanSeverity) + "\n");
    }

    // Method to compute and write log type timestamp statistics to a writer
    public void computeLogTypeTimestampStats(String logType, String mode, long timestamp, BufferedWriter writer)
            throws IOException {
        // Get the list of log entries for the specified log type
        List<LogEntry> entries = logTypeMap.get(logType);
        if (entries == null) {
            // If no entries found for the log type, write a message to the writer
            writer.write("No entries for log type: " + logType + "\n");
            return;
        }

        // Calculate statistics for log entries based on timestamp and mode
        double minSeverity = Double.MAX_VALUE;
        double maxSeverity = Double.MIN_VALUE;
        double sumSeverity = 0;
        int count = 0;

        for (LogEntry entry : entries) {
            if ((mode.equals("BEFORE") && entry.timestamp < timestamp) ||
                    (mode.equals("AFTER") && entry.timestamp > timestamp)) {
                minSeverity = Math.min(minSeverity, entry.severity);
                maxSeverity = Math.max(maxSeverity, entry.severity);
                sumSeverity += entry.severity;
                count++;
            }
        }

        // If no matching entries found, write a message to the writer
        if (count == 0) {
            writer.write("Min: 0.0, Max: 0.0, Mean: 0.0\n");
            return;
        }

        // Calculate mean severity
        double meanSeverity = sumSeverity / count;
        // Format the statistics and write to the writer
        DecimalFormat df = new DecimalFormat("#.######");
        writer.write("Min: " + df.format(minSeverity) + ", Max: " + df.format(maxSeverity) + ", Mean: "
                + df.format(meanSeverity) + "\n");
    }
}

public class Main {
    public static void main(String[] args) {
        LogMonitor monitor = new LogMonitor();
        String inputFilePath = "testcases/test1/input.txt";
        String outputFilePath = "testcases/test1/output.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            String command;
            while ((command = reader.readLine()) != null) {
                try (Scanner ss = new Scanner(command)) {
                    int operation = ss.nextInt();

                    // Process each operation based on the input
                    if (operation == 1) {
                        String rest = ss.nextLine().trim();
                        String[] parts = rest.split(";");
                        long timestamp = Long.parseLong(parts[0]);
                        String logType = parts[1];
                        double severity = Double.parseDouble(parts[2]);
                        monitor.submitLog(timestamp, logType, severity);
                        writer.write("No output\n");
                    } else if (operation == 2) {
                        String logType = ss.next();
                        monitor.computeLogTypeStats(logType, writer);
                    } else if (operation == 3) {
                        String mode = ss.next();
                        long timestamp = ss.nextLong();
                        monitor.computeTimestampStats(mode, timestamp, writer);
                    } else if (operation == 4) {
                        String mode = ss.next();
                        String logType = ss.next();
                        long timestamp = ss.nextLong();
                        monitor.computeLogTypeTimestampStats(logType, mode, timestamp, writer);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File reading unsuccessful");
        }
    }
}

/*
 * 
 * Time Complexity Analysis -
 * 
 * Here's a breakdown of the time complexity for each operation in the provided
 * code:
 * 
 * Submitting a log entry (Operation 1):
 * 
 *    Time Complexity: O(1)
 *      Adding a log entry to the list and map takes constant time.
 * 
 * 
 * Computing log type statistics (Operation 2):
 * 
 *    Time Complexity: O(n)
 *      Here, 'n' represents the number of log entries of the specified log type. We
 *      iterate over all log entries of that type to find the minimum, maximum, and
 *      mean severity values.
 * 
 * 
 * Computing timestamp statistics (Operation 3):
 * 
 *    Time Complexity: O(n)
 *      Similar to operation 2, we iterate over all log entries to find the minimum,
 *      maximum, and mean severity values based on the given timestamp and mode.
 * 
 * 
 * Computing log type timestamp statistics (Operation 4):
 * 
 *    Time Complexity: O(n)
 *      Again, similar to operations 2 and 3, we iterate over all log entries of the
 *      specified log type to find the minimum, maximum, and mean severity values
 *      based on the given timestamp and mode.
 * 
 * 
 *      Overall, the time complexity of the code is primarily dominated by the
 *      iterations over the log entries, resulting in linear time complexity O(n) for
 *      each of the three main operations (2, 3, and 4), where 'n' is the number of
 *      log entries involved in the computation.
 * 
 */
