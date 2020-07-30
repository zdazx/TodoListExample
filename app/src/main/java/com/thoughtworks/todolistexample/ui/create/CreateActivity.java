package com.thoughtworks.todolistexample.ui.create;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

import com.thoughtworks.todolistexample.MainApplication;
import com.thoughtworks.todolistexample.R;
import com.thoughtworks.todolistexample.repository.task.entity.Task;
import com.thoughtworks.todolistexample.ui.home.HomeActivity;

import java.text.ParseException;
import java.util.Calendar;

import static com.thoughtworks.todolistexample.constant.Constants.CREATE_FAILED;
import static com.thoughtworks.todolistexample.constant.Constants.CREATE_SUCCESS;
import static com.thoughtworks.todolistexample.constant.Constants.MUST_EDIT_DATE;
import static com.thoughtworks.todolistexample.constant.Constants.MUST_EDIT_TITLE;
import static com.thoughtworks.todolistexample.repository.utils.DateUtil.toStringTimestamp;

public class CreateActivity extends AppCompatActivity {
    private TextView dateView;
    private EditText titleET;
    private EditText descriptionET;
    private CheckBox isDoneCheckbox;
    private Switch isRemindSwitch;
    private CreateViewModel createViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        dateView = findViewById(R.id.select_date);
        dateView.setOnClickListener(view -> showDateDialog());

        titleET = findViewById(R.id.title);
        descriptionET = findViewById(R.id.description);
        isDoneCheckbox = findViewById(R.id.is_done_checkbox);
        isRemindSwitch = findViewById(R.id.is_remind_switch);
        ImageView confirmImgView = findViewById(R.id.confirm);
        confirmImgView.setOnClickListener(view -> saveTask());

        createViewModel = obtainViewModel();
        final Observer<Boolean> observer = aBoolean -> {
            Toast.makeText(getApplicationContext(), aBoolean ? CREATE_SUCCESS.getName() : CREATE_FAILED.getName(), Toast.LENGTH_SHORT)
                    .show();
            openHomeActivity();
        };
        createViewModel.getCreateResult().observe(this, observer);

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
        createViewModel.save(buildTask());
    }

    private boolean cannotCreateTask() {
        if (titleET.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), MUST_EDIT_TITLE.getName(), Toast.LENGTH_SHORT).show();
            return true;
        }
        if (dateView.getText().toString().equals(getResources().getString(R.string.date))) {
            Toast.makeText(getApplicationContext(), MUST_EDIT_DATE.getName(), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private Task buildTask() {
        Task task = new Task();
        try {
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
        Calendar now = Calendar.getInstance();
        new DatePickerDialog(this, (datePicker, i, i1, i2) -> {
            String date = i + "年" + i1 + "月" + i2 + "日";
            dateView.setText(date);
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)).show();
    }
}
