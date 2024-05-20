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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.doancuoiky.R;
import com.example.doancuoiky.activity.EditUserActivity;
import com.example.doancuoiky.activity.UserProfileActivity;
import com.example.doancuoiky.model.UserModel;
import com.example.doancuoiky.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<UserModel> mUsers;
    private Context mContext;
    public UserAdapter(Context context, List<UserModel> datas){
        mContext=context;
        mUsers=datas;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_employee, null);
        return new UserViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position){
        UserModel userModel=mUsers.get(position);
        holder.name.setText(userModel.getName());
        switch (userModel.getRole()) {
            case 0:
                holder.role.setText("Administrator");
                break;
            case 1:
                holder.role.setText("Employee");
                break;
            case 2:
                holder.role.setText("Customer");
                break;
            default:
                holder.role.setText("Anonymous");
        }
        Glide.with(mContext).load(userModel.getImg()).centerCrop().transform(new CircleCrop()).placeholder(R.drawable.noimage).error(R.drawable.imageerrror).into(holder.avt);
    }
    @Override
    public int getItemCount(){
        return mUsers.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        private ImageView avt;
        private TextView name, role;
        private int position;
        public UserViewHolder(@NonNull View itemView){
            super(itemView);
            avt=(ImageView) itemView.findViewById(R.id.avatarImageView);
            name=(TextView) itemView.findViewById(R.id.nameTextView);
            role=(TextView) itemView.findViewById(R.id.roleTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserModel userModel = mUsers.get(getAdapterPosition());
                    Intent intent=new Intent(mContext, EditUserActivity.class);
                    intent.putExtra("user", userModel);
                    mContext.startActivity(intent);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position=getAdapterPosition();
                    UserModel userModel = mUsers.get(position);
                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    alert.setTitle("Notification");
                    alert.setMessage("Do you want to delete the user "+userModel.getName()+" ?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            RetrofitClient.getApiService().deleteUserById(userModel.getKey()).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if(response.isSuccessful()){
                                        Toast.makeText(mContext,"Delete is success",Toast.LENGTH_LONG).show();
                                        mUsers.remove(position);
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