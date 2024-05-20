package com.example.doancuoiky.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.doancuoiky.R;
import com.example.doancuoiky.SharedPrefManager;
import com.example.doancuoiky.databinding.ActivityLoginBinding;
import com.example.doancuoiky.model.UserModel;
import com.example.doancuoiky.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private UserModel user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callapilogin(String.valueOf(binding.etUsername.getText()), String.valueOf(binding.etPassword.getText()));
            }
        });
    }
    private void callapilogin(String username, String password){
        RetrofitClient.getApiService().login(username, password).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    user=response.body();
                    SharedPrefManager sharedPrefManager=new SharedPrefManager(getApplicationContext());
                    sharedPrefManager.setData(user);
                    finish();
                    Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Sai username hoặc password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}