package com.example.todo_list;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TaskItem> taskList;
    private ArrayAdapter<TaskItem> taskAdapter;
    private EditText taskEditText;
    private ListView taskListView;
    private TimePicker timePicker;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the ArrayList to store task items
        taskList = new ArrayList<>();

        // Initialize the ArrayAdapter to display task items
        taskAdapter = new ArrayAdapter<>(this, R.layout.task_item, taskList);

        // Get references to UI elements
        taskEditText = findViewById(R.id.taskEditText);
        taskListView = findViewById(R.id.taskListView);
        timePicker = findViewById(R.id.timePicker);
        addButton = findViewById(R.id.addButton);

        // Set the adapter for the ListView
        taskListView.setAdapter(taskAdapter);

        // Handle the "Add" button click event
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskItem = taskEditText.getText().toString().trim();
                if (!taskItem.isEmpty()) {
                    int hours = timePicker.getCurrentHour();
                    int minutes = timePicker.getCurrentMinute();
                    long durationMillis = hours * 3600000 + minutes * 60000; // Convert to milliseconds

                    TaskItem newTask = new TaskItem(taskItem, durationMillis);
                    taskList.add(newTask);

                    // Clear the EditText
                    taskEditText.getText().clear();
                    // Notify the adapter that the data has changed
                    taskAdapter.notifyDataSetChanged();

                    // Set the alarm for the new task
                    setAlarm(newTask);
                }
            }
        });

        // Handle item long-click to delete a task
        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteTask(position);
                return true; // Indicates that the long-click event is consumed
            }
        });
    }

    private void setAlarm(TaskItem taskItem) {
        // Create an Intent to trigger the alarm
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        alarmIntent.setAction(AlarmReceiver.ACTION_ALARM);
        alarmIntent.putExtra("task_name", taskItem.getName());

        // Create a PendingIntent to be triggered when the alarm time arrives
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(), alarmIntent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        // Calculate the alarm time by adding the duration to the current time
        long alarmTimeMillis = SystemClock.elapsedRealtime() + taskItem.getDurationMillis();

        // Get the AlarmManager service
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Set the alarm to trigger at the specified time
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, alarmTimeMillis, pendingIntent);

        // Display a confirmation toast
        Toast.makeText(this, "Alarm set for: " + taskItem.getName(), Toast.LENGTH_SHORT).show();
    }

    private void deleteTask(int position) {
        // Remove the task from the list
        taskList.remove(position);
        // Notify the adapter that the data has changed
        taskAdapter.notifyDataSetChanged();
    }
}
