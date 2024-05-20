package com.example.doancuoiky;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.example.doancuoiky.model.UserModel;

import java.util.Set;

public class SharedPrefManager {
    private final String SHARED_PREF_NAME = "loginSessionInformation";
    private final String key = "key", name = "name", email = "email", numberphone = "numberphone", username = "username", password = "password", img = "img", role = "role", birthday = "birthday", hiredate = "hiredate", address = "address", salary = "salary";
    private Context mContext;

    public SharedPrefManager(Context context) {
        mContext = context;
    }

    public void setData(UserModel user) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, user.getKey());
        editor.putString(name, user.getName());
        editor.putString(email, user.getEmail());
        editor.putString(numberphone, user.getNumberphone());
        editor.putString(username, user.getUsername());
        editor.putString(password, user.getPassword());
        editor.putString(img, user.getImg());
        editor.putString(birthday, user.getBirthday());
        editor.putString(address, user.getAddress());
        editor.putString(hiredate, user.getHiredate());
        editor.putInt(role, user.getRole());
        editor.putInt(salary, user.getSalary());
        editor.apply();
    }

    public UserModel getData() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new UserModel(sharedPreferences.getString(key, ""),
                sharedPreferences.getString(name, ""),
                sharedPreferences.getString(email, ""),
                sharedPreferences.getString(numberphone, ""),
                sharedPreferences.getString(username, ""),
                sharedPreferences.getString(password, ""),
                sharedPreferences.getString(img, ""),
                sharedPreferences.getString(birthday, ""),
                sharedPreferences.getString(address, ""),
                sharedPreferences.getString(hiredate, ""),
                sharedPreferences.getInt(role, -1),
                sharedPreferences.getInt(salary, -1));
    }
}
