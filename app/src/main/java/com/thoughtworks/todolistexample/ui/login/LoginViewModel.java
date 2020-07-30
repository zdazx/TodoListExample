package com.thoughtworks.todolistexample.ui.login;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.thoughtworks.todolistexample.repository.user.entity.User;

import java.io.IOException;
import java.util.Objects;

import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.thoughtworks.todolistexample.constant.Constants.LOG_TAG;
import static com.thoughtworks.todolistexample.repository.utils.Encryptor.md5;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<Boolean> loginResult;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private UserRepository userRepository;
    private User localUser;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<Boolean> getLoginResult() {
        if (Objects.isNull(loginResult)) {
            loginResult = new MutableLiveData<>();
        }
        return loginResult;
    }

    public void login(String username, String password) {
        userRepository.findByName(username)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new MaybeObserver<User>() {
                    private User dbUser;
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(User user) {
                        dbUser = user;
                        localUser = user;
                        loginResult.postValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if (Objects.nonNull(localUser) && Objects.nonNull(dbUser)) {
                            Log.d(LOG_TAG.name(), "success find user from db");
                            return;
                        }
                        Log.d(LOG_TAG.name(), "failed find user from db");
                        Log.d(LOG_TAG.name(), "start get remote user");
                        getRemoteUser(username, password);
                    }
                });
    }

    private void getRemoteUser(String username, String password) {
        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> LoginViewModel.this.getRemoteUser(emitter, username, password)).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        loginResult.postValue(aBoolean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if (Objects.isNull(localUser)) {
                            Log.d(LOG_TAG.name(), "get remote user failed");
                            return;
                        }
                        Log.d(LOG_TAG.name(), "start insert user to db");
                        saveRemoteUserToLocal();
                    }
                });

    }

    private void getRemoteUser(ObservableEmitter<Boolean> emitter, String username, String password) throws IOException {
        final String url = "https://twc-android-bootcamp.github.io/fake-data/data/user.json";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            if (!emitter.isDisposed()) {
                emitter.onNext(isLoginSuccess(response, username, password));
            }
            emitter.onComplete();
        } else if (!response.isSuccessful() && !emitter.isDisposed()) {
            emitter.onError(new Exception("error"));
        }
    }

    private void saveRemoteUserToLocal() {
        userRepository.save(localUser)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(LOG_TAG.name(), "end insert user to db");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private Boolean isLoginSuccess(Response response, String username, String password) throws IOException {
        ResponseBody body = response.body();
        if (body == null) {
            return false;
        }
        User remoteUser = new Gson().fromJson(body.string(), User.class);
        this.localUser = remoteUser;
        return !Objects.isNull(remoteUser)
                && remoteUser.getName().equals(username)
                && remoteUser.getPassword().equals(md5(password));
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
