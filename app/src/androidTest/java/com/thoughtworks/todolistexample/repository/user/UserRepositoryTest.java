package com.thoughtworks.todolistexample.repository.user;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import com.thoughtworks.todolistexample.repository.AppDatabase;
import com.thoughtworks.todolistexample.repository.user.entity.User;
import com.thoughtworks.todolistexample.ui.login.UserRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Objects;

import io.reactivex.schedulers.Schedulers;

import static com.thoughtworks.todolistexample.repository.utils.Encryptor.md5;

public class UserRepositoryTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase appDatabase;

    private UserRepository userRepository;

    @Before
    public void setUp() {
        appDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                AppDatabase.class).build();
        userRepository = new UserRepositoryImpl(appDatabase.dbUserDataSource());
        User user = new User("android", md5("123456"));
        userRepository.save(user).subscribeOn(Schedulers.io()).subscribe();
    }

    @After
    public void tearDown() {
        appDatabase.close();
    }

    @Test
    public void should_find_user_when_given_correct_username_and_password() {
        userRepository.findByName("android").test()
                .assertValue(Objects::nonNull)
                .assertValue(user -> user.getName().equals("android"))
                .assertValue(user -> user.getPassword().equals("e10adc3949ba59abbe56e057f20f883e"));
    }

    @Test
    public void should_not_find_user_when_given_mistake_username() {
        userRepository.findByName("android123").test()
                .assertNoValues();
    }
}