package com.example.doancuoiky.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.doancuoiky.R;
import com.example.doancuoiky.databinding.ActivityCheckInOutBinding;
import com.example.doancuoiky.model.InvoiceModel;
import com.example.doancuoiky.model.ReservationModel;
import com.example.doancuoiky.model.RoomModel;
import com.example.doancuoiky.model.UserModel;
import com.example.doancuoiky.retrofit.RetrofitClient;

import java.io.StringReader;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckInOutActivity extends AppCompatActivity {
    private ActivityCheckInOutBinding binding;
    private ReservationModel reservationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_in_out);
        binding.btnCheckNumberPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient.getApiService().getUserIdByNumberPhone(String.valueOf(binding.numberphoneEditText.getText())).enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        UserModel userModel = response.body();
                        binding.userIDEditText.setText(userModel.getKey());
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Log.d("aaa", t.toString());
                        Toast.makeText(CheckInOutActivity.this, "Failure API: " + t.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        binding.checkUserIDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient.getApiService().getReservationById(String.valueOf(binding.userIDEditText.getText())).enqueue(new Callback<ReservationModel>() {
                    @Override
                    public void onResponse(Call<ReservationModel> call, Response<ReservationModel> response) {
                        if (response.isSuccessful()) {
                            reservationModel = response.body();
                            binding.checkInTimeEditText.setText(reservationModel.getCheckIn());
                            binding.checkOutTimeEditText.setText(reservationModel.getCheckOut());
                            binding.moreRequiredEditText.setText(reservationModel.getMoreRequire());
                            binding.typeEditText.setText(reservationModel.getRoomType());
                            binding.numberEditText.setText(reservationModel.getRoomNumber());
                            binding.precisionPriceTextView.setText(reservationModel.getTotalPrice());
                        } else {
                            Toast.makeText(CheckInOutActivity.this, "Reservation is not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ReservationModel> call, Throwable t) {
                    }
                });
            }
        });
        binding.getCheckInTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                binding.checkInTimeEditText.setText(String.valueOf(Instant.now()));
            }
        });
        binding.getCheckOutTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                binding.checkOutTimeEditText.setText(String.valueOf(Instant.now()));
            }
        });
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient.getApiService().addReservation(
                        String.valueOf(binding.userIDEditText.getText()),
                        String.valueOf(binding.typeEditText.getText()),
                        String.valueOf(binding.checkInTimeEditText.getText()),
                        String.valueOf(binding.checkOutTimeEditText.getText()),
                        0,
                        String.valueOf(binding.moreRequiredEditText.getText()),
                        "0",
                        String.valueOf(binding.numberEditText.getText())).enqueue(new Callback<ReservationModel>() {
                    @Override
                    public void onResponse(Call<ReservationModel> call, Response<ReservationModel> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(CheckInOutActivity.this, "Add is success", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(CheckInOutActivity.this, "Add is fail", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ReservationModel> call, Throwable t) {
                        Toast.makeText(CheckInOutActivity.this, "Failure API: " + t.toString(), Toast.LENGTH_LONG).show();
                        Log.d("aa", t.toString());
                    }
                });
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient.getApiService().deleteReservationById(String.valueOf(reservationModel.getKey())).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(CheckInOutActivity.this, "Delete is success", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(CheckInOutActivity.this, "Delete is fail", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(CheckInOutActivity.this, "Failure API: " + t.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        binding.applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient.getApiService().updateReservation(String.valueOf(reservationModel.getKey()),
                        String.valueOf(binding.userIDEditText.getText()),
                        String.valueOf(binding.typeEditText.getText()),
                        String.valueOf(binding.checkInTimeEditText.getText()),
                        String.valueOf(binding.checkOutTimeEditText.getText()),
                        0,
                        String.valueOf(binding.moreRequiredEditText.getText()),
                        "0",
                        String.valueOf(binding.numberEditText.getText())).enqueue(new Callback<ReservationModel>() {
                    @Override
                    public void onResponse(Call<ReservationModel> call, Response<ReservationModel> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(CheckInOutActivity.this, "Update is success", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(CheckInOutActivity.this, "Update is fail", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ReservationModel> call, Throwable t) {
                        Toast.makeText(CheckInOutActivity.this, "Update is success", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        binding.precisionPriceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.checkInTimeEditText.getText() != null && binding.checkOutTimeEditText.getText() != null&&binding.numberEditText.getText()!=null) {
                    Instant start = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        start = Instant.parse(binding.checkInTimeEditText.getText());
                    }
                    Instant end = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        end = Instant.parse(binding.checkOutTimeEditText.getText());
                    }
                    double exactDaysBetween = 0;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        exactDaysBetween = (double) ChronoUnit.MILLIS.between(start, end) / (24 * 60 * 60 * 1000);
                    }
                    long roundedDaysBetweenUp = (long) Math.ceil(exactDaysBetween);
                    RetrofitClient.getApiService().getListRoom().enqueue(new Callback<List<RoomModel>>() {
                        @Override
                        public void onResponse(Call<List<RoomModel>> call, Response<List<RoomModel>> response) {
                            if(response.isSuccessful()){
                                List<RoomModel> modelList=response.body();
                                for(RoomModel roomModel:modelList){
                                    if (roomModel.getNumber().equals(String.valueOf(binding.numberEditText.getText()))){
                                        binding.precisionPriceTextView.setText(String.valueOf(Integer.parseInt(roomModel.getCost())*roundedDaysBetweenUp));
                                    }
                                }
                            }
                            else{
                                Toast.makeText(CheckInOutActivity.this, "Wrong information", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<RoomModel>> call, Throwable t) {

                        }
                    });
                }
            }
        });
        binding.checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient.getApiService().checkIn(String.valueOf(reservationModel.getKey())).enqueue(new Callback<ReservationModel>() {
                    @Override
                    public void onResponse(Call<ReservationModel> call, Response<ReservationModel> response) {
                        if (response.isSuccessful()) {
                            ReservationModel reservationModel1 = response.body();
                            binding.numberEditText.setText(String.valueOf(reservationModel1.getRoomNumber()));
                            Toast.makeText(CheckInOutActivity.this, "Check-in is success", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(CheckInOutActivity.this, "Check-in is fail", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ReservationModel> call, Throwable t) {
                        Toast.makeText(CheckInOutActivity.this, "Failure API: " + t.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        binding.checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient.getApiService().checkOut(String.valueOf(reservationModel.getKey()), String.valueOf(binding.reviewTextMultiLine.getText())).enqueue(new Callback<InvoiceModel>() {
                    @Override
                    public void onResponse(Call<InvoiceModel> call, Response<InvoiceModel> response) {
                        if (response.isSuccessful()) {
                            InvoiceModel invoiceModel = response.body();
                            Intent intent = new Intent(CheckInOutActivity.this, InvoiceProfileActivity.class);
                            intent.putExtra("key", invoiceModel.getKey());
                            startActivity(intent);
                            Toast.makeText(CheckInOutActivity.this, "Check-out is success", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(CheckInOutActivity.this, "Check-out is success", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<InvoiceModel> call, Throwable t) {

                    }
                });
            }
        });
    }
}