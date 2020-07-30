package com.thoughtworks.todolistexample.ui.create;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.thoughtworks.todolistexample.R;

import java.util.Calendar;

public class CreateActivity extends AppCompatActivity {
    private TextView dateView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        dateView = findViewById(R.id.select_date);
        dateView.setOnClickListener(view -> showDateDialog());
    }

    private void showDateDialog() {
        Calendar now = Calendar.getInstance();
        new DatePickerDialog(this, (datePicker, i, i1, i2) -> {
            String date = i + "-" + i1 + "-" + i2;
            dateView.setText(date);
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)).show();
    }
}
