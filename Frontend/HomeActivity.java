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
import com.example.doancuoiky.databinding.ActivityHomeBinding;
import com.example.doancuoiky.model.UserModel;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_home);
        SharedPrefManager sharedPrefManager=new SharedPrefManager(getApplicationContext());
        UserModel userModel=sharedPrefManager.getData();
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
        Glide.with(this).load(userModel.getImg()).centerCrop().placeholder(R.drawable.noimage).error(R.drawable.imageerrror).transform(new CircleCrop()).into(binding.avatarImage);
        binding.avatarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, UserProfileActivity.class));
            }
        });
        binding.checkInAndOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, CheckInOutActivity.class));
            }
        });
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });
        binding.employeeManageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, EmployeeActivity.class));
            }
        });
        binding.roomManageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, RoomActivity.class));
            }
        });
        binding.reservationManageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ReservationActivity.class));
            }
        });
        binding.invoiceManageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, InvoiceActivity.class));
            }
        });
    }
}