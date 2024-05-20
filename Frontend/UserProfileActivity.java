package com.example.doancuoiky.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.doancuoiky.R;
import com.example.doancuoiky.SharedPrefManager;
import com.example.doancuoiky.databinding.ActivityProfileBinding;
import com.example.doancuoiky.model.UserModel;

public class UserProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_profile);
        SharedPrefManager sharedPrefManager=new SharedPrefManager(getApplicationContext());
        userModel=sharedPrefManager.getData();
        binding.nameTextView.setText(userModel.getName());
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
        binding.emailTextView.setText(userModel.getEmail());
        binding.numberPhoneTextview.setText(userModel.getNumberphone());
        binding.usernameTextView.setText(userModel.getUsername());
        binding.addressTextView.setText(userModel.getAddress());
        binding.salaryTextView.setText(String.valueOf(userModel.getSalary()));
        binding.birthDayTextView.setText(userModel.getBirthday());
        binding.hireDateTextView.setText(userModel.getHiredate());
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Glide.with(this).load(userModel.getImg()).centerCrop().placeholder(R.drawable.ic_launcher_background).error(R.color.black).transform(new CircleCrop()).into(binding.avatarImage);
    }
}