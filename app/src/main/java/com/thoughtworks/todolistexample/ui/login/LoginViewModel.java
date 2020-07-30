package com.thoughtworks.todolistexample.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.thoughtworks.todolistexample.repository.user.entity.User;

import java.io.IOException;
import java.util.Objects;

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

import static com.thoughtworks.todolistexample.repository.utils.Encryptor.md5;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<Boolean> loginResult;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private UserRepository userRepository;

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
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
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
        }).subscribeOn(Schedulers.io())
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

                    }
                });
    }

    private Boolean isLoginSuccess(Response response, String username, String password) throws IOException {
        ResponseBody body = response.body();
        if (body == null) {
            return false;
        }
        String dbUserJson = body.string();
        User dbUser = new Gson().fromJson(dbUserJson, User.class);
        return !Objects.isNull(dbUser)
                && dbUser.getName().equals(username)
                && dbUser.getPassword().equals(md5(password));
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
