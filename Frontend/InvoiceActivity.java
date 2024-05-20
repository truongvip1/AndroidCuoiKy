package com.example.doancuoiky.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.InvoiceAdapter;
import com.example.doancuoiky.databinding.ActivityInvoiceBinding;
import com.example.doancuoiky.model.InvoiceModel;
import com.example.doancuoiky.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceActivity extends AppCompatActivity {
    private ActivityInvoiceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_invoice);
        callApi();
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void callApi(){
        RetrofitClient.getApiService().getListInvoice().enqueue(new Callback<List<InvoiceModel>>() {
            @Override
            public void onResponse(Call<List<InvoiceModel>> call, Response<List<InvoiceModel>> response) {
                if(response.isSuccessful()){
                    List<InvoiceModel> mListInvoice=response.body();
                    display(mListInvoice);
                }
            }

            @Override
            public void onFailure(Call<List<InvoiceModel>> call, Throwable t) {

            }
        });
    }
    private void display(List<InvoiceModel> mListInvoice){
        InvoiceAdapter InvoiceAdapter=new InvoiceAdapter(InvoiceActivity.this, mListInvoice);
        binding.recycleView.setAdapter(InvoiceAdapter);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(InvoiceActivity.this, LinearLayoutManager.VERTICAL, false));
        long total=0;
        for(InvoiceModel invoiceModel:mListInvoice){
            total=total+Integer.parseInt(invoiceModel.getTotal());
        }
        binding.totalRevenueTextView.setText(String.valueOf(total));
    }
}