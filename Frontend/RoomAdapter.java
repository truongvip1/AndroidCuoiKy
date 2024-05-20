package com.example.doancuoiky.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.doancuoiky.R;
import com.example.doancuoiky.activity.RoomProfileActivity;
import com.example.doancuoiky.activity.UserProfileActivity;
import com.example.doancuoiky.model.RoomModel;
import com.example.doancuoiky.model.UserModel;
import com.example.doancuoiky.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    private List<RoomModel> mRooms;
    private Context mContext;
    public RoomAdapter(Context context, List<RoomModel> datas){
        mContext=context;
        mRooms=datas;
    }
    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_room, null);
        return new RoomViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position){
        RoomModel RoomModel=mRooms.get(position);
        holder.name.setText(RoomModel.getName());
        holder.desc.setText(RoomModel.getDescription());
        holder.number.setText(RoomModel.getNumber());
        holder.cost.setText(RoomModel.getCost());
        Glide.with(mContext).load(RoomModel.getImage()).placeholder(R.drawable.noimage).error(R.drawable.imageerrror).transform(new CircleCrop()).into(holder.roomImg);
        if(RoomModel.getStatus()==0){
            holder.constraintLayout.setBackgroundResource(R.drawable.round_status0);
        }
        else {
            holder.constraintLayout.setBackgroundResource(R.drawable.round_status1);
        }
    }
    @Override
    public int getItemCount(){
        return mRooms.size();
    }
    class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView number, name, cost, desc;
        private ImageView roomImg;
        private ConstraintLayout constraintLayout;
        public RoomViewHolder(@NonNull View itemView){
            super(itemView);
            number=(TextView) itemView.findViewById(R.id.numberTextview);
            name=(TextView) itemView.findViewById(R.id.nameTextView);
            cost=(TextView) itemView.findViewById(R.id.costTextView);
            desc=(TextView) itemView.findViewById(R.id.descriptionTextView);
            roomImg=(ImageView) itemView.findViewById(R.id.roomImageView);
            constraintLayout=(ConstraintLayout) itemView.findViewById(R.id.ctLayout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RoomModel roomModel = mRooms.get(getAdapterPosition());
                    Intent intent=new Intent(mContext, RoomProfileActivity.class);
                    intent.putExtra("room", roomModel);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}