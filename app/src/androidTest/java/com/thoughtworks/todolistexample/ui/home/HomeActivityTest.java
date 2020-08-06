package com.thoughtworks.todolistexample.ui.home;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.thoughtworks.todolistexample.MainApplication;
import com.thoughtworks.todolistexample.R;
import com.thoughtworks.todolistexample.repository.task.entity.Task;
import com.thoughtworks.todolistexample.ui.create.TaskRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.internal.operators.maybe.MaybeCreate;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> activityTestRule =
            new ActivityTestRule<>(HomeActivity.class, false, false);

    @Before
    public void setUp() throws Exception {
        Task task1 = new Task(1, "title1", "description", "1589899000000", false, false);
        Task task2 = new Task(2, "title2", "description", "1589899000000", false, false);
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        MainApplication application = (MainApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        TaskRepository taskRepository = application.getTaskRepository();
        when(taskRepository.getAllTasks()).thenReturn(new MaybeCreate<>(emitter -> emitter.onSuccess(tasks)));
        activityTestRule.launchActivity(null);
    }

    @Test
    public void should_show_in_layout_with_some_view() {
        onView(withId(R.id.today)).check(matches(isDisplayed()));
        onView(withId(R.id.month)).check(matches(isDisplayed()));
        onView(withId(R.id.go_create)).check(matches(isDisplayed()));
    }

    @Test
    public void should_show_task_title_in_layout() {
        onView(withText("title1")).check(matches(isDisplayed()));
        onView(withText("title2")).check(matches(isDisplayed()));
    }
}