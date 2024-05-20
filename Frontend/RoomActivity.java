package com.example.doancuoiky.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.RoomAdapter;
import com.example.doancuoiky.adapter.UserAdapter;
import com.example.doancuoiky.databinding.ActivityRoomBinding;
import com.example.doancuoiky.model.RoomModel;
import com.example.doancuoiky.model.UserModel;
import com.example.doancuoiky.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomActivity extends AppCompatActivity {
    private ActivityRoomBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_room);
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
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RoomActivity.this, RoomProfileActivity.class));
            }
        });
    }
    private void callApi(){
        RetrofitClient.getApiService().getListRoom().enqueue(new Callback<List<RoomModel>>() {
            @Override
            public void onResponse(Call<List<RoomModel>> call, Response<List<RoomModel>> response) {
                if(response.isSuccessful()){
                    List<RoomModel> mListRoom=response.body();
                    display(mListRoom);
                }
                else{
                }
            }

            @Override
            public void onFailure(Call<List<RoomModel>> call, Throwable t) {
            }
        });
    }
    private void display(List<RoomModel> mListRoom){
        RoomAdapter roomAdapter=new RoomAdapter(RoomActivity.this, mListRoom);
        binding.recycleView.setAdapter(roomAdapter);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
    }
}