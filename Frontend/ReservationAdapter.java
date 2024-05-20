package com.example.doancuoiky.adapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.model.ReservationModel;
import com.example.doancuoiky.model.UserModel;
import com.example.doancuoiky.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {
    private List<ReservationModel> mReservations;
    private Context mContext;
    public ReservationAdapter(Context context, List<ReservationModel> datas){
        mContext=context;
        mReservations=datas;
    }
    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_reservation, null);
        return new ReservationViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position){
        ReservationModel reservationModel=mReservations.get(position);
        holder.userId.setText(reservationModel.getUserID());
        holder.roomNumber.setText(reservationModel.getRoomNumber());
        holder.checkInTime.setText(reservationModel.getCheckIn());
        holder.checkOutTime.setText(reservationModel.getCheckOut());
        holder.moreRequire.setText(reservationModel.getMoreRequire());
        holder.totalPrice.setText(reservationModel.getTotalPrice());
        if(reservationModel.getStatus()==0){
            holder.constraintLayout.setBackgroundResource(R.drawable.round_status0);
        }
        else {
            holder.constraintLayout.setBackgroundResource(R.drawable.round_status1);
        }
    }
    @Override
    public int getItemCount(){
        return mReservations.size();
    }
    class ReservationViewHolder extends RecyclerView.ViewHolder{
        private TextView userId, roomNumber, checkInTime, checkOutTime, moreRequire, totalPrice;
        private ConstraintLayout constraintLayout;
        public ReservationViewHolder(@NonNull View itemView){
            super(itemView);
            userId=(TextView) itemView.findViewById(R.id.userIDTextView);
            roomNumber=(TextView) itemView.findViewById(R.id.roomNumberTextView);
            checkInTime=(TextView) itemView.findViewById(R.id.checkInTimeTextView);
            checkOutTime=(TextView) itemView.findViewById(R.id.checkOutTimeTextView);
            moreRequire=(TextView) itemView.findViewById(R.id.moreRequiredTextView);
            totalPrice=(TextView) itemView.findViewById(R.id.totalPriceTextView);
            constraintLayout=(ConstraintLayout) itemView.findViewById(R.id.ctLayout);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position=getAdapterPosition();
                    ReservationModel reservationModel = mReservations.get(position);
                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    alert.setTitle("Notification");
                    alert.setMessage("Do you want to delete the reservation "+reservationModel.getKey()+" ?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            RetrofitClient.getApiService().deleteReservationById(reservationModel.getKey()).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if(response.isSuccessful()){
                                        Toast.makeText(mContext,"Delete is success",Toast.LENGTH_LONG).show();
                                        mReservations.remove(position);
                                        notifyItemRemoved(position);
                                    }else{
                                        Toast.makeText(mContext,"Delete is fail",Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(mContext,"Failure API: "+t.toString(), Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    });
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(mContext,"Cancel",Toast.LENGTH_LONG).show();
                        }
                    });
                    alert.show();
                    return false;
                }
            });
        }
    }
}