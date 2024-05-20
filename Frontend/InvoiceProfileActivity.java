package com.example.doancuoiky.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.doancuoiky.R;
import com.example.doancuoiky.databinding.ActivityInvoiceProfileBinding;
import com.example.doancuoiky.model.InvoiceModel;
import com.example.doancuoiky.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceProfileActivity extends AppCompatActivity {
    private ActivityInvoiceProfileBinding binding;
    InvoiceModel invoiceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_invoice_profile);
        Intent intent=getIntent();
        if(intent.hasExtra("invoice")){
            invoiceModel=(InvoiceModel) intent.getSerializableExtra("invoice");
            binding.userIDTextView.setText(invoiceModel.getUserID());
            binding.roomNumberTextView.setText(invoiceModel.getRoomNumber());
            binding.checkInTimeTextView.setText(invoiceModel.getCheckIn());
            binding.checkOutTimeTextView.setText(invoiceModel.getCheckOut());
            binding.dateTextView.setText(invoiceModel.getDate());
            binding.totalPriceTextView.setText(invoiceModel.getTotal());
            binding.reviewTextView.setText(invoiceModel.getReview());
        } else if (intent.hasExtra("key")) {
            String key=intent.getStringExtra("key");
            Log.d("aa", key);
            RetrofitClient.getApiService().getListInvoice().enqueue(new Callback<List<InvoiceModel>>() {
                @Override
                public void onResponse(Call<List<InvoiceModel>> call, Response<List<InvoiceModel>> response) {
                    if(response.isSuccessful()){
                        List<InvoiceModel> invoiceModels=response.body();
                        for(InvoiceModel invoiceModel1:invoiceModels){
                            Log.d("aaa", invoiceModel1.getKey());
                            if (String.valueOf(invoiceModel1.getKey()).equals(key)){
                                invoiceModel=invoiceModel1;
                                break;
                            }
                        }
                        binding.userIDTextView.setText(invoiceModel.getUserID());
                        binding.roomNumberTextView.setText(invoiceModel.getRoomNumber());
                        binding.checkInTimeTextView.setText(invoiceModel.getCheckIn());
                        binding.checkOutTimeTextView.setText(invoiceModel.getCheckOut());
                        binding.dateTextView.setText(invoiceModel.getDate());
                        binding.totalPriceTextView.setText(invoiceModel.getTotal());
                        binding.reviewTextView.setText(invoiceModel.getReview());
                    }else{
                        Log.d("aaaa", "ko response");
                        Toast.makeText(InvoiceProfileActivity.this, "Get Invoice Fail", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<InvoiceModel>> call, Throwable t) {
                    Log.d("aaaaa", t.toString());
                    Toast.makeText(InvoiceProfileActivity.this, "Failure API"+t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            invoiceModel=new InvoiceModel("","","","","","","","");
        }
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}