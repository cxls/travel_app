package com.hjq.demo.sqlite.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.hjq.demo.sqlite.model.User;

import org.litepal.LitePal;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author flight
 * @date 2024/6/2
 */
public class UserRepository {
    public void insert(User user) {
        user.save();
    }

    public void update(User user) {
        user.save();
    }

    public void delete(User user) {
        LitePal.delete(User.class, user.getUserId());
    }

    public User getUserById(int id) {
        return LitePal.find(User.class, id);
    }

    /**
     * 获取所有用户信息的方法。
     *
     * 通过调用LitePal的findAll方法，查询数据库中User类的所有记录，并返回一个包含所有用户对象的列表。
     * 这个方法使得我们可以一次性获取所有用户的信息，而不需要手动执行SQL查询。
     *
     * @return 返回一个List类型的用户对象列表，列表中包含了数据库中所有User类的实例。
     */
    public List<User> getAllUsers() {
        // 调用LitePal的findAll方法查询并返回所有User类的实例
        return LitePal.findAll(User.class);
    }

    // 批量插入
    public Boolean insertAll(List<User> users) {
        return LitePal.saveAll(users);
    }
}
