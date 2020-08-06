package com.thoughtworks.todolistexample.repository.task;

import android.os.SystemClock;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.thoughtworks.todolistexample.repository.AppDatabase;
import com.thoughtworks.todolistexample.repository.task.entity.Task;
import com.thoughtworks.todolistexample.ui.create.TaskRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.schedulers.Schedulers;

@RunWith(AndroidJUnit4.class)
public class TaskRepositoryTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase appDatabase;
    private TaskRepository taskRepository;

    @Before
    public void setUp() {
        appDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                AppDatabase.class).build();
        taskRepository = new TaskRepositoryImpl(appDatabase.dbTaskDataSource());
        Task task1 = new Task(1, "title1", "description1", "1589899000000", false, false);
        Task task2 = new Task(2, "title2", "description2", "1589899000000", false, false);
        appDatabase.dbTaskDataSource().save(task1).subscribeOn(Schedulers.io()).subscribe();
        appDatabase.dbTaskDataSource().save(task2).subscribeOn(Schedulers.io()).subscribe();
    }

    @After
    public void tearDown() {
        appDatabase.close();
    }

    @Test
    public void should_find_tasks_saved() {
        taskRepository.getAllTasks().test()
                .assertValue(tasks -> tasks.size() == 2)
                .assertValue(tasks -> tasks.get(0).getTitle().equals("title1"))
                .assertValue(tasks -> tasks.get(0).getDescription().equals("description1"))
                .assertValue(tasks -> tasks.get(1).getTitle().equals("title2"))
                .assertValue(tasks -> tasks.get(1).getDescription().equals("description2"));
    }

    @Test
    public void should_show_task_updated() {
        Task task = new Task(1, "title123", "description123", "1589899000000", false, false);
        taskRepository.update(task).subscribeOn(Schedulers.io()).subscribe();
        SystemClock.sleep(2000);
        taskRepository.getAllTasks().test()
                .assertValue(tasks -> tasks.size() == 2)
                .assertValue(tasks -> tasks.get(0).getTitle().equals("title123"))
                .assertValue(tasks -> tasks.get(0).getDescription().equals("description123"));
    }

    @Test
    public void should_show_one_tasks_after_deleted_a_task() {
        Task task = new Task(1, "title1", "description", "1589899000000", false, false);
        taskRepository.delete(task).subscribeOn(Schedulers.io()).subscribe();
        SystemClock.sleep(2000);
        taskRepository.getAllTasks().test()
                .assertValue(tasks -> tasks.size() == 1)
                .assertValue(tasks -> tasks.get(0).getTitle().equals("title2"));
    }
}