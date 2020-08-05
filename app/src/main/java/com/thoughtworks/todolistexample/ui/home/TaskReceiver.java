package com.thoughtworks.todolistexample.ui.home;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.thoughtworks.todolistexample.R;
import com.thoughtworks.todolistexample.repository.task.entity.Task;

import static com.thoughtworks.todolistexample.constant.Constants.TASK_KEY;
import static com.thoughtworks.todolistexample.constant.Constants.TASK_NOTIFICATION;

public class TaskReceiver extends BroadcastReceiver {
    private static final String TODO_LIST = "TodoList";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TASK_NOTIFICATION.getName().equals(intent.getAction())) {
            String taskJson = intent.getStringExtra(TASK_KEY.getName());
            Task task = new Gson().fromJson(taskJson, Task.class);
            showNotification(context, task);
        }
    }

    private void showNotification(Context context, Task task) {
        Intent intent = new Intent(context, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String id = String.valueOf(task.getId());
        NotificationChannel notificationChannel = notificationManager.getNotificationChannel(id);
        if (notificationChannel == null) {
            int importance = NotificationManager.IMPORTANCE_LOW;
            notificationChannel = new NotificationChannel(id, task.getTitle(), importance);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Notification notification = new NotificationCompat
                .Builder(context, id)
                .setContentTitle(task.getTitle())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(task.getDescription())
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setTicker(TODO_LIST)
                .setWhen(System.currentTimeMillis())
                .build();

        int intValue = Long.valueOf(task.getDeadline()).intValue();
        notificationManager.notify(intValue, notification);
    }
}
