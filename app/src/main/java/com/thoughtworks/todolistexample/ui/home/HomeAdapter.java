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

import static com.thoughtworks.todolistexample.repository.utils.DateUtil.toDateOfMonthAndDay;

public class HomeAdapter extends RecyclerView.Adapter {
    private ArrayList<Task> tasks;
    private HomeViewModel homeViewModel;

    public HomeAdapter(HomeViewModel homeViewModel) {
        tasks = new ArrayList<>();
        this.homeViewModel = homeViewModel;
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
        ((HomeViewHolder) holder).selectDoneCheckBox.setChecked(task.isDone());
        ((HomeViewHolder) holder).titleView.setText(task.getTitle());
        ((HomeViewHolder) holder).deadlineView.setText(toDateOfMonthAndDay(task.getDeadline()));
        paintStrikeLine(((HomeViewHolder) holder).titleView, task.isDone());

        ((HomeViewHolder) holder).selectDoneCheckBox.setOnCheckedChangeListener((compoundButton, b) -> {
            task.setDone(b);
            homeViewModel.receiveUpdateTask(task);
            paintStrikeLine(((HomeViewHolder) holder).titleView, b);
        });

        holder.itemView.setOnClickListener(view -> homeViewModel.receiveGoToDetailTask(task));
    }

    private void paintStrikeLine(TextView textView, boolean isDone) {
        if (isDone) {
            textView.setPaintFlags(textView.getPaintFlags() | (Paint.STRIKE_THRU_TEXT_FLAG));
        } else {
            textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
