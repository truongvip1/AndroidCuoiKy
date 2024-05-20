package com.example.doancuoiky.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.UserAdapter;
import com.example.doancuoiky.databinding.ActivityEmployeeBinding;
import com.example.doancuoiky.model.UserModel;
import com.example.doancuoiky.retrofit.APIService;
import com.example.doancuoiky.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeActivity extends AppCompatActivity {
    private ActivityEmployeeBinding binding;
    UserAdapter userAdapter;
    List<UserModel> mListUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_employee);
        callApi();
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApi();
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeeActivity.this, EditUserActivity.class));
            }
        });
    }
    private void callApi(){
        RetrofitClient.getApiService().getListUser().enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if(response.isSuccessful()){
                    mListUser=response.body();
                    display();
                }
                else{
                    Toast.makeText(EmployeeActivity.this, "Not found", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Toast.makeText(EmployeeActivity.this, "Failure API"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void display(){
        userAdapter=new UserAdapter(EmployeeActivity.this, mListUser);
        binding.recycleView.setAdapter(userAdapter);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
    }
}