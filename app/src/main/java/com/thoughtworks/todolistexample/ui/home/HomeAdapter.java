package com.thoughtworks.todolistexample.ui.home;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        ((HomeViewHolder)holder).getSelectDoneCheckBox().setChecked(task.isDone());
        ((HomeViewHolder)holder).getTitleView().setText(task.getTitle());
        ((HomeViewHolder)holder).getDeadlineView().setText(toDate(task.getDeadline()));
        if (task.isDone()) {
            ((HomeViewHolder)holder).getTitleView().getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
