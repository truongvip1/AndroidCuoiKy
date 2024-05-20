package com.example.doancuoiky.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.doancuoiky.R;
import com.example.doancuoiky.databinding.ActivityEditUserBinding;
import com.example.doancuoiky.model.UserModel;
import com.example.doancuoiky.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserActivity extends AppCompatActivity {
    private ActivityEditUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_edit_user);
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        if (intent.hasExtra("user")) {
            UserModel userModel = (UserModel) intent.getSerializableExtra("user");
            binding.numberPhoneEditText.setText(userModel.getNumberphone());
            binding.emailEditText.setText(userModel.getEmail());
            binding.usernameEditText.setText(userModel.getUsername());
            binding.passwordEditText.setText(userModel.getPassword());
            binding.addressEditText.setText(userModel.getAddress());
            binding.salaryEditText.setText(String.valueOf(userModel.getSalary()));
            binding.birthDayEditText.setText(userModel.getBirthday());
            binding.hireDateEditText.setText(userModel.getHiredate());
            binding.nameEditText.setText(userModel.getName());
            Glide.with(this).load(userModel.getImg()).placeholder(R.drawable.noimage).error(R.drawable.imageerrror).transform(new CircleCrop()).into(binding.avatarImage);
            SharedPreferences sharedPreferences = EditUserActivity.this.getSharedPreferences("loginSessionInformation", MODE_PRIVATE);
            if (sharedPreferences.getInt("role", -1) != 0) {
                binding.btnConfirm.setVisibility(View.GONE);
            }
            switch (userModel.getRole()){
                case 0:
                    binding.roleTextView.setText("Administrator");
                    break;
                case 1:
                    binding.roleTextView.setText("Employee");
                    break;
                case 2:
                    binding.roleTextView.setText("Customer");
                    break;
                default:
                    binding.roleTextView.setText("Anonymous");
            }
            binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int role=-1;
                    switch (String.valueOf(binding.roleTextView.getText())){
                        case "Administrator":
                            role=0;
                            break;
                        case "Employee":
                            role=1;
                            break;
                        case "Customer":
                            role=2;
                            break;
                        default:
                            role=-1;
                    }
                    RetrofitClient.getApiService().updateUser(userModel.getKey(),
                            String.valueOf(binding.nameEditText.getText()),
                            String.valueOf(binding.numberPhoneEditText.getText()),
                            String.valueOf(binding.emailEditText.getText()),
                            String.valueOf(binding.usernameEditText.getText()),
                            String.valueOf(binding.passwordEditText.getText()),
                            role,
                            String.valueOf(userModel.getImg()),
                            String.valueOf(binding.addressEditText.getText()),
                            Integer.parseInt(String.valueOf(binding.salaryEditText.getText())),
                            String.valueOf(binding.birthDayEditText.getText()),
                            String.valueOf(binding.hireDateEditText.getText())
                    ).enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(EditUserActivity.this, "Update is success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(EditUserActivity.this, "Update is fail", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {
                            Toast.makeText(EditUserActivity.this, "Update is success", Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                }
            });
        } else {
            binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int role=-1;
                    switch (String.valueOf(binding.roleTextView.getText())){
                        case "Administrator":
                            role=0;
                            break;
                        case "Employee":
                            role=1;
                            break;
                        case "Customer":
                            role=2;
                            break;
                        default:
                            role=-1;
                    }
                    RetrofitClient.getApiService().addUser(
                            String.valueOf(binding.nameEditText.getText()),
                            String.valueOf(binding.numberPhoneEditText.getText()),
                            String.valueOf(binding.emailEditText.getText()),
                            String.valueOf(binding.usernameEditText.getText()),
                            String.valueOf(binding.passwordEditText.getText()),
                            role,
                            "",
                            String.valueOf(binding.addressEditText.getText()),
                            Integer.parseInt(String.valueOf(binding.salaryEditText.getText())),
                            String.valueOf(binding.birthDayEditText.getText()),
                            String.valueOf(binding.hireDateEditText.getText())
                    ).enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(EditUserActivity.this, "Add is success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(EditUserActivity.this, "Add is fail", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {
                            Toast.makeText(EditUserActivity.this, "Add is success", Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                }
            });
        }
    }
}