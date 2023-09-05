package com.example.todo_list;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String ACTION_ALARM = "com.example.todo.ACTION_ALARM";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(ACTION_ALARM)) {
            String taskName = intent.getStringExtra("task_name");
            // Handle the alarm action (e.g., display a notification or alert)
            Toast.makeText(context, "Task '" + taskName + "' is due!", Toast.LENGTH_LONG).show();
        }
    }
}
