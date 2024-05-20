package com.example.doancuoiky.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.ReservationAdapter;
import com.example.doancuoiky.databinding.ActivityReservationBinding;
import com.example.doancuoiky.model.ReservationModel;
import com.example.doancuoiky.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationActivity extends AppCompatActivity {
    private ActivityReservationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_reservation);
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
    }
    private void callApi(){
        RetrofitClient.getApiService().getListReservation().enqueue(new Callback<List<ReservationModel>>() {
            @Override
            public void onResponse(Call<List<ReservationModel>> call, Response<List<ReservationModel>> response) {
                if(response.isSuccessful()){
                    List<ReservationModel> mListReservation=response.body();
                    display(mListReservation);
                }
            }

            @Override
            public void onFailure(Call<List<ReservationModel>> call, Throwable t) {

            }
        });
    }
    private void display(List<ReservationModel> mListReservation){
        ReservationAdapter reservationAdapter=new ReservationAdapter(ReservationActivity.this, mListReservation);
        binding.recycleView.setAdapter(reservationAdapter);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(ReservationActivity.this, LinearLayoutManager.VERTICAL, false));
    }
}