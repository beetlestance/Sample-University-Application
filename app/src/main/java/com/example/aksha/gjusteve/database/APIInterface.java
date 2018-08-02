package com.example.aksha.gjusteve.database;

import com.example.aksha.gjusteve.POJO.AnnouncementsJson;
import com.example.aksha.gjusteve.POJO.EventJson;
import com.example.aksha.gjusteve.POJO.Events;
import com.example.aksha.gjusteve.POJO.FacultiesList;
import com.example.aksha.gjusteve.POJO.FeedbackJson;
import com.example.aksha.gjusteve.POJO.FeedbackList;
import com.example.aksha.gjusteve.POJO.LoginJson;
import com.example.aksha.gjusteve.POJO.NewFeedback;
import com.example.aksha.gjusteve.POJO.ProfileJson;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface APIInterface {

    @GET()
    @Streaming
    Call<ResponseBody> downloadFile(@Url String url);

    @GET("android/feedbacks.php")
    Call<FeedbackList> getFeedbackarray();

    @POST("android/feedbacks.php")
    @FormUrlEncoded
    Call<FeedbackList> getSelectedFeedbackarray(@Field("uid") String uid);

    @POST("android/getfeedbacks.php")
    @FormUrlEncoded
    Call<NewFeedback> setFeedback(@Field("userprofile") String userprofile, @Field("username") String username, @Field("date") String date, @Field("status") String status, @Field("category") String category, @Field("suggestion") String suggestion);

    @POST("android/Faculty.php")
    @FormUrlEncoded
    Call<FacultiesList> getFaculties(@Field("department") String department);

    @POST("android/login.php")
    @FormUrlEncoded
    Call<LoginJson> getLogin(@Field("roll") String roll, @Field("password") String password);

    @POST("android/register.php")
    @FormUrlEncoded
    Call<LoginJson> register(@Field("roll") String roll, @Field("email") String email, @Field("password") String password,@Field("father") String father,@Field("phone") String phone);

    @POST("android/resetpassword.php")
    @FormUrlEncoded
    Call<LoginJson> getResetPassword(@Field("roll") String roll,@Field("password")String Password,@Field("email")String email,@Field("phone")String phone,@Field("newpass")String newpass);


    @Multipart
    @POST("android/postimage.php")
    Call<ProfileJson> postImage(@Part MultipartBody.Part file, @Part("file") RequestBody name, @Query("roll") String roll);

    @POST("android/singleevent.php")
    @FormUrlEncoded
    Call<Events> getSingleEvent(@Field("uid") String uid);

    @POST("android/count.php")
    @FormUrlEncoded
    Call<Events> getCountUpdate(@Field("uid") String uid, @Field("count") String count);

    @POST("android/like.php")
    @FormUrlEncoded
    Call<Events> getLikeUpdate(@Field("uid") String uid, @Field("likes") String count);

    @GET("android/events.php")
    Call<EventJson> getEventList();

    @GET("android/announcements.php")
    Call<AnnouncementsJson> getAnnouncement();

    @GET("android/getupvote.php")
    Call<FeedbackJson> getUpvoteUpdate(@Query("uid") String uid, @Query("count") String count);
}

