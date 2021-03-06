package com.thoughtworks.todolistexample.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thoughtworks.todolistexample.repository.task.entity.Task;
import com.thoughtworks.todolistexample.ui.create.TaskRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.thoughtworks.todolistexample.repository.utils.CommonUtil.booleanToInt;

public class HomeViewModel extends ViewModel {
    private final int beginIdxOfDate = 0;
    private final int endIdxOfDate = 7;
    private MutableLiveData<ArrayList<Task>> taskResult;
    private MutableLiveData<Task> updateTaskIsDoneNotification;
    private MutableLiveData<Task> goToDetailNotification;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private TaskRepository taskRepository;
    public static final String HOME_VIEW_MODEL = "HomeViewModel";

    public LiveData<ArrayList<Task>> getTaskResult() {
        if (Objects.isNull(taskResult)) {
            taskResult = new MutableLiveData<>();
        }
        return taskResult;
    }

    public LiveData<Task> getUpdateTaskIsDoneNotification() {
        if (Objects.isNull(updateTaskIsDoneNotification)) {
            updateTaskIsDoneNotification = new MutableLiveData<>();
        }
        return updateTaskIsDoneNotification;
    }

    public LiveData<Task> getGoToDetailNotification() {
        if (Objects.isNull(goToDetailNotification)) {
            goToDetailNotification = new MutableLiveData<>();
        }
        return goToDetailNotification;
    }

    public void receiveGoToDetailTask(Task task) {
        goToDetailNotification.postValue(task);
    }

    public void receiveUpdateTaskIsDone(Task task, boolean isDone) {
        task.setDone(isDone);
        ArrayList<Task> tasks = taskResult.getValue();
        if (tasks == null) {
            return;
        }
        taskResult.postValue(sortByDeadline(tasks));
        updateTaskIsDoneNotification.postValue(task);
    }

    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void getAllTasks() {
        taskRepository.getAllTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new MaybeObserver<List<Task>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Task> tasks) {
                        taskResult.postValue(sortByDeadline(tasks));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private ArrayList<Task> sortByDeadline(List<Task> tasks) {
        return (ArrayList<Task>) tasks.stream()
                .sorted(Comparator.comparing((Task task) -> booleanToInt(task.isDone()))
                        .thenComparing(task -> Integer.parseInt(task.getDeadline().substring(beginIdxOfDate, endIdxOfDate))))
                .collect(Collectors.toList());
    }

    public void updateTask(Task task) {
        taskRepository.update(task)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new MaybeObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        Log.d(HOME_VIEW_MODEL, "update success");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
