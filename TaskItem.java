package com.example.todo_list;

public class TaskItem {
    private String name;
    private long durationMillis;

    public TaskItem(String name, long durationMillis) {
        this.name = name;
        this.durationMillis = durationMillis;
    }

    public String getName() {
        return name;
    }

    public long getDurationMillis() {
        return durationMillis;
    }

    @Override
    public String toString() {
        // Concatenate the name and time as a string
        return name + " (Time: " + formatDuration(durationMillis) + ")";
    }

    private String formatDuration(long millis) {
        // Convert milliseconds to hours and minutes
        long hours = millis / 3600000;
        long minutes = (millis % 3600000) / 60000;

        // Format as HH:MM
        return String.format("%02d:%02d", hours, minutes);
    }
}



