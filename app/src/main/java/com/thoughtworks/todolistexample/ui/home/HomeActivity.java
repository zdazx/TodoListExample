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

import com.thoughtworks.todolistexample.MainApplication;
import com.thoughtworks.todolistexample.R;
import com.thoughtworks.todolistexample.repository.task.entity.Task;
import com.thoughtworks.todolistexample.ui.create.CreateActivity;
import com.thoughtworks.todolistexample.ui.create.TaskRepository;

import java.util.ArrayList;

import static com.thoughtworks.todolistexample.repository.utils.DateUtil.toCurrentDay;
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
        goCreateBtn.setOnClickListener(view -> openCreateActivity());

        RecyclerView taskContainer = findViewById(R.id.task_container);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        HomeAdapter homeAdapter = new HomeAdapter();

        taskContainer.setLayoutManager(layoutManager);
        taskContainer.setAdapter(homeAdapter);

        homeViewModel = obtainViewModel();
        final Observer<ArrayList<Task>> observer = tasks -> {
            homeAdapter.setTasks(tasks);
            String count = tasks.size() + "个任务";
            taskCountTV.setText(count);
        };
        homeViewModel.getTaskResult().observe(this, observer);

        getAllTasks();
    }

    private void setCurrentDate() {
        todayTV.setText(toCurrentDay());
        monthTV.setText(toCurrentMonth());
    }

    private void openCreateActivity() {
        Intent intent = new Intent(this, CreateActivity.class);
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
