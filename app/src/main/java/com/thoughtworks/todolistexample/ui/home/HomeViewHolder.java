package com.thoughtworks.todolistexample.ui.home;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thoughtworks.todolistexample.R;

public class HomeViewHolder extends RecyclerView.ViewHolder {
    private CheckBox selectDoneCheckBox;
    private TextView titleView;
    private TextView deadlineView;

    public HomeViewHolder(@NonNull View itemView) {
        super(itemView);
        selectDoneCheckBox = itemView.findViewById(R.id.select_done_checkbox);
        titleView = itemView.findViewById(R.id.task_title);
        deadlineView = itemView.findViewById(R.id.task_deadline);
    }

    public CheckBox getSelectDoneCheckBox() {
        return selectDoneCheckBox;
    }

    public TextView getTitleView() {
        return titleView;
    }

    public TextView getDeadlineView() {
        return deadlineView;
    }
}
