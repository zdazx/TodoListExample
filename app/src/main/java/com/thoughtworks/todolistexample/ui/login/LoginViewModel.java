package com.thoughtworks.todolistexample.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thoughtworks.todolistexample.R;
import com.thoughtworks.todolistexample.repository.user.entity.User;

import java.util.Objects;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.thoughtworks.todolistexample.repository.utils.Encryptor.md5;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginResult> loginResult;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private UserRepository userRepository;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<LoginResult> getLoginResult() {
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
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(User user) {
                        if (!user.getName().equals(username)) {
                            loginResult.postValue(new LoginResult(R.string.login_failed_username));
                            return;
                        }
                        if (!user.getPassword().equals(md5(password))) {
                            loginResult.postValue(new LoginResult(R.string.login_failed_password));
                            return;
                        }
                        loginResult.postValue(new LoginResult(new LoggedInUserView(username)));
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
