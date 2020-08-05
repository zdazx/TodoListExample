package com.thoughtworks.todolistexample.ui.home;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.thoughtworks.todolistexample.repository.task.entity.Task;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Objects;

import static com.thoughtworks.todolistexample.constant.Constants.TASK_KEY;
import static com.thoughtworks.todolistexample.constant.Constants.TASK_NOTIFICATION;
import static com.thoughtworks.todolistexample.repository.utils.DateUtil.toDateTime;

public class AlarmService {

    public static void triggerTaskAlarm(Context context, Task task) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, TaskReceiver.class);
        if (Objects.nonNull(task)) {
            String taskJson = new Gson().toJson(task);
            intent.putExtra(TASK_KEY.getName(), taskJson);
        }
        intent.setAction(TASK_NOTIFICATION.getName());

        PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, intent, 0);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(toDateTime(task.getDeadline()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long triggerAtMillis = cal.getTimeInMillis() + 6 * 60 * 60 * 1000;
//        long thirtySecondsFromNow = SystemClock.currentThreadTimeMillis() + 3 * 1000;
        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, broadcast);
    }
}
