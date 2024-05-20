package com.example.doancuoiky.retrofit;

import com.example.doancuoiky.model.InvoiceModel;
import com.example.doancuoiky.model.ReservationModel;
import com.example.doancuoiky.model.RoomModel;
import com.example.doancuoiky.model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {
    @FormUrlEncoded
    @POST("user/login")
    Call<UserModel> login(@Field("username") String username,
                          @Field("password") String password);

    @GET("room")
    Call<List<RoomModel>> getListRoom();

    @GET("reservation")
    Call<List<ReservationModel>> getListReservation();

    @GET("user")
    Call<List<UserModel>> getListUser();

    @GET("reservation/{id}")
    Call<ReservationModel> getReservationById(@Path("id") String id);

    @FormUrlEncoded
    @PUT("user/update/{id}")
    Call<UserModel> updateUser(@Path("id") String id,
                               @Field("name") String name,
                               @Field("numberphone") String numberphone,
                               @Field("email") String email,
                               @Field("username") String username,
                               @Field("password") String password,
                               @Field("role") int role,
                               @Field("img") String img,
                               @Field("address") String address,
                               @Field("salary") int salary,
                               @Field("birthday") String birthday,
                               @Field("hiredate") String hiredate);

    @FormUrlEncoded
    @POST("user/add")
    Call<UserModel> addUser(@Field("name") String name,
                            @Field("numberphone") String numberphone,
                            @Field("email") String email,
                            @Field("username") String username,
                            @Field("password") String password,
                            @Field("role") int role,
                            @Field("img") String img,
                            @Field("address") String address,
                            @Field("salary") int salary,
                            @Field("birthday") String birthday,
                            @Field("hiredate") String hiredate);

    @FormUrlEncoded
    @PUT("room/update/{id}")
    Call<RoomModel> updateRoom(@Path("id") String id,
                               @Field("reserveID") String reserveID,
                               @Field("number") String number,
                               @Field("type") String type,
                               @Field("name") String name,
                               @Field("status") int status,
                               @Field("description") String description,
                               @Field("cost") String cost,
                               @Field("image") String image);

    @FormUrlEncoded
    @POST("room/add")
    Call<RoomModel> addRoom(@Field("reserveID") String reserveID,
                            @Field("number") String number,
                            @Field("type") String type,
                            @Field("name") String name,
                            @Field("status") int status,
                            @Field("description") String description,
                            @Field("cost") String cost,
                            @Field("image") String image);

    @FormUrlEncoded
    @POST("reservation/add")
    Call<ReservationModel> addReservation(@Field("userID") String userID,
                                          @Field("roomType") String roomType,
                                          @Field("checkIn") String checkIn,
                                          @Field("checkOut") String checkOut,
                                          @Field("status") int status,
                                          @Field("moreRequire") String moreRequire,
                                          @Field("totalPrice") String totalPrice,
                                          @Field("roomNumber") String roomNumber);

    @DELETE("user/delete/{id}")
    Call<Void> deleteUserById(@Path("id") String Key);

    @DELETE("reservation/delete/{id}")
    Call<Void> deleteReservationById(@Path("id") String Key);

    @GET("user/findnumberphone/{id}")
    Call<UserModel> getUserIdByNumberPhone(@Path("id") String id);

    @FormUrlEncoded
    @PUT("reservation/update/{id}")
    Call<ReservationModel> updateReservation(@Path("id") String id,
                                             @Field("userID") String userID,
                                             @Field("roomType") String roomType,
                                             @Field("checkIn") String checkIn,
                                             @Field("checkOut") String checkOut,
                                             @Field("status") int status,
                                             @Field("moreRequire") String moreRequire,
                                             @Field("totalPrice") String totalPrice,
                                             @Field("roomNumber") String roomNumber);
    @FormUrlEncoded
    @POST("checkin")
    Call<ReservationModel> checkIn(@Field("reserveID") String reserveID);
    @FormUrlEncoded
    @POST("checkout")
    Call<InvoiceModel> checkOut(@Field("reserveID") String reserveID,
                                @Field("review") String review);
    @GET("invoice")
    Call<List<InvoiceModel>> getListInvoice();
}
