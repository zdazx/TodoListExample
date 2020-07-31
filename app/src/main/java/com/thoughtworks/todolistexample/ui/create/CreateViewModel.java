package com.thoughtworks.todolistexample.ui.create;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thoughtworks.todolistexample.repository.task.entity.Task;

import java.util.Objects;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CreateViewModel extends ViewModel {
    private MutableLiveData<Boolean> createResult;
    private MutableLiveData<Boolean> updateResult;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private TaskRepository taskRepository;

    public LiveData<Boolean> getCreateResult() {
        if (Objects.isNull(createResult)) {
            createResult = new MutableLiveData<>();
        }
        return createResult;
    }

    public LiveData<Boolean> getUpdateResult() {
        if (Objects.isNull(updateResult)) {
            updateResult = new MutableLiveData<>();
        }
        return updateResult;
    }

    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void save(Task task) {
        taskRepository.save(task)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new MaybeObserver<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(Long aLong) {
                        if (Objects.isNull(aLong)) {
                            createResult.postValue(false);
                            return;
                        }
                        createResult.postValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        createResult.postValue(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void update(Task task) {
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
                        updateResult.postValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        updateResult.postValue(false);
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
