package com.thoughtworks.todolistexample.ui.home;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thoughtworks.todolistexample.R;
import com.thoughtworks.todolistexample.repository.task.entity.Task;

import java.util.ArrayList;

import static com.thoughtworks.todolistexample.repository.utils.DateUtil.toDate;

public class HomeAdapter extends RecyclerView.Adapter {
    private ArrayList<Task> tasks;

    public HomeAdapter() {
        tasks = new ArrayList<>();
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
        this.notifyDataSetChanged();
    }

    private static class HomeViewHolder extends RecyclerView.ViewHolder {
        public CheckBox selectDoneCheckBox;
        public TextView titleView;
        public TextView deadlineView;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            selectDoneCheckBox = itemView.findViewById(R.id.select_done_checkbox);
            titleView = itemView.findViewById(R.id.task_title);
            deadlineView = itemView.findViewById(R.id.task_deadline);
        }
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_item, parent, false);

        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Task task = tasks.get(position);
        ((HomeViewHolder)holder).selectDoneCheckBox.setChecked(task.isDone());
        ((HomeViewHolder)holder).titleView.setText(task.getTitle());
        ((HomeViewHolder)holder).deadlineView.setText(toDate(task.getDeadline()));
        if (task.isDone()) {
            ((HomeViewHolder)holder).titleView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
