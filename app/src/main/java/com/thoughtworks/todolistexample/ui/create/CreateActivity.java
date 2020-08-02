package com.thoughtworks.todolistexample.ui.create;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.thoughtworks.todolistexample.MainApplication;
import com.thoughtworks.todolistexample.R;
import com.thoughtworks.todolistexample.repository.task.entity.Task;
import com.thoughtworks.todolistexample.ui.home.HomeActivity;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Objects;

import static com.thoughtworks.todolistexample.constant.Constants.IS_HAVE_TASK;
import static com.thoughtworks.todolistexample.repository.utils.DateUtil.toDate;
import static com.thoughtworks.todolistexample.repository.utils.DateUtil.toDay;
import static com.thoughtworks.todolistexample.repository.utils.DateUtil.toMonth;
import static com.thoughtworks.todolistexample.repository.utils.DateUtil.toStringTimestamp;
import static com.thoughtworks.todolistexample.repository.utils.DateUtil.toYear;

public class CreateActivity extends AppCompatActivity {
    private TextView dateView;
    private EditText titleET;
    private EditText descriptionET;
    private CheckBox isDoneCheckbox;
    private Switch isRemindSwitch;
    private ImageView deleteImgView;
    private CreateViewModel createViewModel;
    private Task existTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Intent intent = getIntent();
        String existTaskJson = intent.getStringExtra(IS_HAVE_TASK.getName());
        existTask = new Gson().fromJson(existTaskJson, Task.class);

        dateView = findViewById(R.id.select_date);
        dateView.setOnClickListener(view -> showDateDialog());

        titleET = findViewById(R.id.title);
        descriptionET = findViewById(R.id.description);
        isDoneCheckbox = findViewById(R.id.is_done_checkbox);
        isRemindSwitch = findViewById(R.id.is_remind_switch);
        deleteImgView = findViewById(R.id.delete);
        ImageView confirmImgView = findViewById(R.id.confirm);
        confirmImgView.setOnClickListener(view -> saveTask());
        deleteImgView.setOnClickListener(view -> delete());

        if (Objects.nonNull(existTask)) {
            init(existTask);
        }

        createViewModel = obtainViewModel();
        final Observer<Boolean> observer = aBoolean -> {
            Toast.makeText(getApplicationContext(), aBoolean ? getString(R.string.create_success) : getString(R.string.create_failed), Toast.LENGTH_SHORT)
                    .show();
            openHomeActivity();
        };
        createViewModel.getCreateResult().observe(this, observer);

        final Observer<Boolean> updateObserver = aBoolean -> {
            Toast.makeText(getApplicationContext(), aBoolean ? getString(R.string.update_success) : getString(R.string.update_fail), Toast.LENGTH_SHORT)
                    .show();
            openHomeActivity();
        };
        createViewModel.getUpdateResult().observe(this, updateObserver);

        final Observer<Boolean> deleteObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Toast.makeText(getApplicationContext(), aBoolean ? getString(R.string.delete_success) : getString(R.string.delete_fail), Toast.LENGTH_SHORT)
                        .show();
                openHomeActivity();
            }
        };
        createViewModel.getDeleteResult().observe(this, deleteObserver);

    }

    private void delete() {
        createViewModel.delete(existTask);
    }

    private void init(Task task) {
        titleET.setText(task.getTitle());
        descriptionET.setText(task.getDescription());
        isDoneCheckbox.setChecked(task.isDone());
        isRemindSwitch.setChecked(task.isRemind());
        dateView.setText(toDate(task.getDeadline()));
        deleteImgView.setVisibility(View.VISIBLE);
    }

    private void openHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private CreateViewModel obtainViewModel() {
        TaskRepository taskRepository = ((MainApplication) getApplicationContext()).getTaskRepository();
        CreateViewModel createViewModel = new ViewModelProvider(this).get(CreateViewModel.class);
        createViewModel.setTaskRepository(taskRepository);
        return createViewModel;
    }

    private void saveTask() {
        if (cannotCreateTask()) {
            return;
        }

        if (Objects.nonNull(existTask)) {
            createViewModel.update(buildTask(true));
            return;
        }

        createViewModel.save(buildTask(false));
    }

    private boolean cannotCreateTask() {
        if (titleET.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), getString(R.string.must_edit_title), Toast.LENGTH_SHORT).show();
            return true;
        }
        if (dateView.getText().toString().equals(getResources().getString(R.string.date))) {
            Toast.makeText(getApplicationContext(), getString(R.string.must_select_date), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private Task buildTask(boolean isHaveTask) {
        Task task = new Task();
        try {
            if (isHaveTask) {
                existTask.setTitle(titleET.getText().toString());
                existTask.setDescription(descriptionET.getText().toString());
                existTask.setDeadline(toStringTimestamp(dateView.getText().toString()));
                existTask.setDone(isDoneCheckbox.isChecked());
                existTask.setRemind(isRemindSwitch.isChecked());
                return existTask;
            }
            task.setTitle(titleET.getText().toString());
            task.setDescription(descriptionET.getText().toString());
            task.setDeadline(toStringTimestamp(dateView.getText().toString()));
            task.setDone(isDoneCheckbox.isChecked());
            task.setRemind(isRemindSwitch.isChecked());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return task;
    }

    private void showDateDialog() {
        int year, month, day;

        String dateViewText = dateView.getText().toString();
        if (!dateViewText.equals(getString(R.string.date))) {
            year = toYear(dateViewText);
            month = toMonth(dateViewText);
            day = toDay(dateViewText);
        } else {
            Calendar now = Calendar.getInstance();
            year = now.get(Calendar.YEAR);
            month = now.get(Calendar.MONTH);
            day = now.get(Calendar.DAY_OF_MONTH);
        }
        new DatePickerDialog(this, (datePicker, i, i1, i2) -> {
            String date = i + getString(R.string.year) +
                    i1 + getString(R.string.month) +
                    i2 + getString(R.string.day);
            dateView.setText(date);
        }, year, month, day).show();
    }
}
