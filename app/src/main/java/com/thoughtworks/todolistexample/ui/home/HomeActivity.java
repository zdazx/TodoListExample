package com.thoughtworks.todolistexample.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.thoughtworks.todolistexample.MainApplication;
import com.thoughtworks.todolistexample.R;
import com.thoughtworks.todolistexample.repository.task.entity.Task;
import com.thoughtworks.todolistexample.ui.create.CreateActivity;
import com.thoughtworks.todolistexample.ui.create.TaskRepository;

import java.util.ArrayList;
import java.util.Objects;

import static com.thoughtworks.todolistexample.constant.Constants.IS_HAVE_TASK;
import static com.thoughtworks.todolistexample.repository.utils.DateUtil.toCurrentDayAndWeek;
import static com.thoughtworks.todolistexample.repository.utils.DateUtil.toCurrentMonth;

public class HomeActivity extends AppCompatActivity {
    private HomeViewModel homeViewModel;
    private TextView todayTV;
    private TextView monthTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        todayTV = findViewById(R.id.today);
        monthTV = findViewById(R.id.month);
        TextView taskCountTV = findViewById(R.id.task_count);
        setCurrentDate();

        ImageView goCreateBtn = findViewById(R.id.go_create);
        goCreateBtn.setOnClickListener(view -> openCreateActivity(null));

        homeViewModel = obtainViewModel();
        RecyclerView taskContainer = findViewById(R.id.task_container);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        HomeAdapter homeAdapter = new HomeAdapter(homeViewModel);

        taskContainer.setLayoutManager(layoutManager);
        taskContainer.setAdapter(homeAdapter);

        final Observer<ArrayList<Task>> observer = tasks -> {
            homeAdapter.setTasks(tasks);
            String count = String.valueOf(tasks.size()).concat(getString(R.string.task_count));
            taskCountTV.setText(count);
        };
        homeViewModel.getTaskResult().observe(this, observer);

        final Observer<Task> updateNotificationObserver = this::updateTask;
        homeViewModel.getUpdateNotification().observe(this, updateNotificationObserver);

        final Observer<Task> detailNotificationObserver = this::openCreateActivity;
        homeViewModel.getDetailNotification().observe(this, detailNotificationObserver);

        getAllTasks();
    }

    private void updateTask(Task task) {
        homeViewModel.updateTask(task);
    }

    private void setCurrentDate() {
        todayTV.setText(toCurrentDayAndWeek());
        monthTV.setText(toCurrentMonth());
    }

    private void openCreateActivity(Task task) {
        Intent intent = new Intent(this, CreateActivity.class);
        if (Objects.nonNull(task)) {
            String taskJson = new Gson().toJson(task);
            intent.putExtra(IS_HAVE_TASK.getName(), taskJson);
        }
        startActivity(intent);
    }

    private void getAllTasks() {
        homeViewModel.getAllTasks();
    }

    private HomeViewModel obtainViewModel() {
        TaskRepository taskRepository = ((MainApplication) getApplicationContext()).getTaskRepository();
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.setTaskRepository(taskRepository);
        return homeViewModel;
    }
}
