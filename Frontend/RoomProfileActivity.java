package com.example.doancuoiky.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.doancuoiky.R;
import com.example.doancuoiky.databinding.ActivityRoomProfileBinding;
import com.example.doancuoiky.model.RoomModel;
import com.example.doancuoiky.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomProfileActivity extends AppCompatActivity {
    private ActivityRoomProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_room_profile);
        Intent intent = getIntent();
        if (intent.hasExtra("room")) {
            RoomModel roomModel = (RoomModel) intent.getSerializableExtra("room");
            binding.numberEditText.setText(roomModel.getNumber());
            binding.nameEditText.setText(roomModel.getName());
            binding.typeEditText.setText(roomModel.getType());
            binding.costEditText.setText(roomModel.getCost());
            binding.descriptionEditText.setText(roomModel.getDescription());
            binding.reserveIDEditText.setText(roomModel.getReserveID());
            Glide.with(this).load(roomModel.getImage()).placeholder(R.drawable.noimage).error(R.drawable.imageerrror).transform(new CircleCrop()).into(binding.roomImageView);
            SharedPreferences sharedPreferences = RoomProfileActivity.this.getSharedPreferences("loginSessionInformation", MODE_PRIVATE);
            if (sharedPreferences.getInt("role", -1) != 0) {
                binding.btnConfirm.setVisibility(View.GONE);
            }
            binding.statusEditText.setText(String.valueOf(roomModel.getStatus()));
            binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RetrofitClient.getApiService().updateRoom(roomModel.getKey(),
                            String.valueOf(binding.reserveIDEditText.getText()),
                            String.valueOf(binding.numberEditText.getText()),
                            String.valueOf(binding.typeEditText.getText()),
                            String.valueOf(binding.nameEditText.getText()),
                            Integer.parseInt(String.valueOf(binding.statusEditText.getText())),
                            String.valueOf(binding.descriptionEditText.getText()),
                            String.valueOf(binding.costEditText.getText()),
                            String.valueOf(roomModel.getImage())
                    ).enqueue(new Callback<RoomModel>() {
                        @Override
                        public void onResponse(Call<RoomModel> call, Response<RoomModel> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(RoomProfileActivity.this, "Update is success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RoomProfileActivity.this, "Update is fail", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RoomModel> call, Throwable t) {
                            Toast.makeText(RoomProfileActivity.this, "Update is success", Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                }
            });
        } else {
            binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RetrofitClient.getApiService().addRoom(String.valueOf(binding.reserveIDEditText.getText()),
                            String.valueOf(binding.numberEditText.getText()),
                            String.valueOf(binding.typeEditText.getText()),
                            String.valueOf(binding.nameEditText.getText()),
                            Integer.parseInt(String.valueOf(binding.statusEditText.getText())),
                            String.valueOf(binding.descriptionEditText.getText()),
                            String.valueOf(binding.costEditText.getText()),
                            "").enqueue(new Callback<RoomModel>() {
                        @Override
                        public void onResponse(Call<RoomModel> call, Response<RoomModel> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(RoomProfileActivity.this, "Add is success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RoomProfileActivity.this, "Add is fail", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RoomModel> call, Throwable t) {
                            Toast.makeText(RoomProfileActivity.this, "Add is success", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}