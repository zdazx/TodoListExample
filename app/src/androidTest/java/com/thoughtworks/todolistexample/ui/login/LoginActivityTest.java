package com.thoughtworks.todolistexample.ui.login;

import android.os.SystemClock;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.thoughtworks.todolistexample.MainApplication;
import com.thoughtworks.todolistexample.R;
import com.thoughtworks.todolistexample.repository.user.entity.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.internal.operators.maybe.MaybeCreate;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception {
        MainApplication application = (MainApplication) InstrumentationRegistry
                .getInstrumentation().getTargetContext().getApplicationContext();
        UserRepository userRepository = application.getUserRepository();

        User user = new User(1, "android", "123456");
        when(userRepository.findByName("android")).thenReturn(new MaybeCreate(emitter -> emitter.onSuccess(user)));
    }

    @Test
    public void should_success_when_name_and_password_are_correct() {

        onView(withId(R.id.username)).perform(typeText("android"));
        onView(withId(R.id.password)).perform(typeText("123456")).perform(closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());

        SystemClock.sleep(1000);
        onView(withText("Welcome!"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void should_failed_when_name_is_not_correct() {

        onView(withId(R.id.username)).perform(typeText("androidddd"));
        onView(withId(R.id.password)).perform(typeText("123456")).perform(closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());

        SystemClock.sleep(1000);
        onView(withText("用户不存在"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void should_failed_when_password_is_correct() {

        onView(withId(R.id.username)).perform(typeText("android"));
        onView(withId(R.id.password)).perform(typeText("123456777")).perform(closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());

        SystemClock.sleep(1000);
        onView(withText("用户不存在"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }
}