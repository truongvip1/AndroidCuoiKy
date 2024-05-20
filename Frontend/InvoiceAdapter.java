package com.example.doancuoiky.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.activity.InvoiceProfileActivity;
import com.example.doancuoiky.model.InvoiceModel;
import com.example.doancuoiky.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {
    private List<InvoiceModel> mInvoices;
    private Context mContext;
    public InvoiceAdapter(Context context, List<InvoiceModel> datas){
        mContext=context;
        mInvoices=datas;
    }
    @NonNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_invoice, null);
        return new InvoiceViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull InvoiceViewHolder holder, int position){
        InvoiceModel InvoiceModel=mInvoices.get(position);
        holder.userId.setText(InvoiceModel.getUserID());
        holder.roomNumber.setText(InvoiceModel.getRoomNumber());
        holder.checkInTime.setText(InvoiceModel.getCheckIn());
        holder.checkOutTime.setText(InvoiceModel.getCheckOut());
        holder.totalPrice.setText(InvoiceModel.getTotal());
        holder.constraintLayout.setBackgroundResource(R.drawable.rounded_backround);
    }
    @Override
    public int getItemCount(){
        return mInvoices.size();
    }
    class InvoiceViewHolder extends RecyclerView.ViewHolder{
        private TextView userId, roomNumber, checkInTime, checkOutTime,totalPrice;
        private ConstraintLayout constraintLayout;
        public InvoiceViewHolder(@NonNull View itemView){
            super(itemView);
            userId=(TextView) itemView.findViewById(R.id.userIDTextView);
            roomNumber=(TextView) itemView.findViewById(R.id.roomNumberTextView);
            checkInTime=(TextView) itemView.findViewById(R.id.checkInTimeTextView);
            checkOutTime=(TextView) itemView.findViewById(R.id.checkOutTimeTextView);
            totalPrice=(TextView) itemView.findViewById(R.id.totalPriceTextView);
            constraintLayout=(ConstraintLayout) itemView.findViewById(R.id.ctLayout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, InvoiceProfileActivity.class);
                    InvoiceModel invoiceModel=mInvoices.get(getAdapterPosition());
                    intent.putExtra("invoice", invoiceModel);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}