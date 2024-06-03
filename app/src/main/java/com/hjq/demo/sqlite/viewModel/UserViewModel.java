package com.hjq.demo.sqlite.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hjq.demo.sqlite.model.User;
import com.hjq.demo.sqlite.repository.UserRepository;

import java.util.List;

/**
 * @author flight
 * @date 2024/6/2
 */
public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;
    private List<User> allUsers;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository();
        allUsers = repository.getAllUsers();
    }

    public void insert(User user) {
        repository.insert(user);
    }

    public void update(User user) {
        repository.update(user);
    }

    public void delete(User user) {
        repository.delete(user);
    }

    public List<User> getAllUsers() {
        return allUsers;
    }

    // 批量插入
    public Boolean insertAll(List<User> users) {
        return repository.insertAll(users);
    }
}
