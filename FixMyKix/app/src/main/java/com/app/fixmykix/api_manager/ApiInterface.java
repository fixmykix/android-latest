package com.app.fixmykix.api_manager;

import com.app.fixmykix.utils.Constants;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("v1/users/sign_up")
    Call<ResponseBody> signup(@Field("user[email]") String email,
                              @Field("user[password]") String password,
                              @Field("user[password_confirmation]") String passwrod_confirm,
                              @Field("user[role]") int usertype);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("u_signinotp")
    Call<ResponseBody> verifyOtp(@Field("user_phone") String phone,
                                 @Field("otp") String otp);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("Reotpgen")
    Call<ResponseBody> resendOtp(@Field("phone") String phone);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("v1/users/sign_in")
    Call<ResponseBody> login(@Field("user[email]") String userName,
                             @Field("user[password]") String password);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("v1/users/forgot_password")
    Call<ResponseBody> forgetPassword(@Field("user[email]") String phone);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("updatePassword")
    Call<ResponseBody> updatePassword(@Field("user_phone") String phone,
                                      @Field("password") String password);

    @Headers({"Accept: application/json"})
    @GET("v1/messages")
    Call<ResponseBody> getChatMessages(@Header(Constants.KEY_X_USER_TOKEN) String token,
                                       @Field("receiver_id") String recieverid);

    @Headers({"Accept: application/json"})
    @GET("v1/addresses")
    Call<ResponseBody> getAddresses(@Header(Constants.KEY_X_USER_TOKEN) String token);

    @Headers({"Accept: application/json"})
    @GET("v1/cart_items/add_to_cart")
    Call<ResponseBody> addToCart(@Header(Constants.KEY_X_USER_TOKEN) String token,
                                 @Query("service_ids[]") Object[] serviceIds);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("v1/addresses")
    Call<ResponseBody> saveAddresses(@Header(Constants.KEY_X_USER_TOKEN) String token, @Field("street") String street,
                                     @Field("city") String city,
                                     @Field("state") String state,
                                     @Field("zip") String zip);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @PUT("v1/addresses/{id}")
    Call<ResponseBody> editAddresses(@Path("id") int id, @Header(Constants.KEY_X_USER_TOKEN) String token, @Field("street") String street,
                                     @Field("city") String city,
                                     @Field("state") String state,
                                     @Field("zip") String zip);

    @Headers({"Accept: application/json"})
    @DELETE("v1/addresses/{id}")
    Call<ResponseBody> deleteAddresses(@Path("id") int id, @Header(Constants.KEY_X_USER_TOKEN) String token);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("services/parcel/api/v1/united_parcel_services/validate")
    Call<ResponseBody> validateAddress(@Header(Constants.KEY_X_USER_TOKEN) String token,
                                       @Field("city") String city,
                                       @Field("postal_code") String postalcode,
                                       @Field("state_province_code") String provincecode);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("v1/artist_details")
    Call<ResponseBody> addArtistDetail(@Header(Constants.KEY_X_USER_TOKEN) String token,
                                       @Field("artist_detail[business_name]") String busiName,
                                       @Field("artist_detail[service_description]") String desc,
                                       @Field("artist_detail[twitter_link]") String twitterLink,
                                       @Field("artist_detail[youtube_link]") String youTUbeLink,
                                       @Field("artist_detail[facebook_link]") String fbLink,
                                       @Field("artist_detail[snapchat_link]") String snapchatLink,
                                       @Field("artist_detail[instagram_linkL]") String instagramLink,
                                       @Field("artist_detail[proof_of_work]") String image);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @PUT()
    Call<ResponseBody> addUserDetail(@Header(Constants.KEY_X_USER_TOKEN) String token,
                                     @Url String url,
                                     @Field("user[email]") String email,
                                     @Field("user[mobile_number]") String mobile,
                                     @Field("user[phone_number]") String phone,
                                     @Field("user[first_name]") String firstName);

    @Headers({"Accept: application/json"})
    @GET("v1/services")
    Call<ResponseBody> getServices();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("v1/artist_services")
    Call<ResponseBody> addService(@Header(Constants.KEY_X_USER_TOKEN) String token,
                                  @Field("artist_service[service_id]") String serviceId,
                                  @Field("artist_service[description]") String description,
                                  @Field("artist_service[artist_id]") String artistId,
                                  @Field("artist_service[price]") String price,
                                  @Field("artist_service[number_of_days]") String noOfDays,
                                  @Field("artist_service[proof_of_work]") String image);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @PUT()
    Call<ResponseBody> updateService(@Header(Constants.KEY_X_USER_TOKEN) String token,
                                     @Url String url,
                                     @Field("artist_service[service_id]") String serviceId,
                                     @Field("artist_service[description]") String description,
                                     @Field("artist_service[artist_id]") String artistId,
                                     @Field("artist_service[price]") String price,
                                     @Field("artist_service[number_of_days]") String noOfDays,
                                     @Field("artist_service[proof_of_work]") String image);

    @Headers({"Accept: application/json"})
    @DELETE()
    Call<ResponseBody> deleteService(@Header(Constants.KEY_X_USER_TOKEN) String token,
                                     @Url String url);

    @Headers({"Accept: application/json"})
    @GET("v1/artist_service_requests")
    Call<ResponseBody> getArtistServices(@Header(Constants.KEY_X_USER_TOKEN) String token); /*"v1/artist_services"*/


    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("v1/service_requests")
    Call<ResponseBody> bookService(@Header(Constants.KEY_X_USER_TOKEN) String token,
                                   @Field("service_request[description]") String desc,
                                   @Field("service_request[price]") String price,
                                   @Field("service_request[artist_id]") String artistId,
                                   @Field("service_request[artist_service_ids]") ArrayList<String> serviceIds,
                                   @Field("service_request[image]") String image);

    @Headers({"Accept: application/json"})
    @GET("v1/service_requests")
    Call<ResponseBody> getServiceRequests(@Header(Constants.KEY_X_USER_TOKEN) String token);


    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @PUT()
    Call<ResponseBody> editBookedServiceRequest(@Header(Constants.KEY_X_USER_TOKEN) String token,
                                                @Url String url,
                                                @Field("service_request[description]") String desc,
                                                @Field("service_request[price]") String price,
                                                @Field("service_request[artist_id]") String artistId,
                                                @Field("service_request[artist_service_ids]") ArrayList<String> serviceIds,
                                                @Field("service_request[image]") String image);

    @Headers({"Accept: application/json"})
    @PUT()
    Call<ResponseBody> changeRequestStatus(@Header(Constants.KEY_X_USER_TOKEN) String token,
                                           @Url String url);

    @Headers({"Accept: application/json"})
    @GET("v1/categories")
    Call<ResponseBody> getServiceWithCategory(@Header(Constants.KEY_X_USER_TOKEN) String token);

    @Headers({"Accept: application/json"})
    @GET("v1/categories")
    Call<ResponseBody> getServiceByCategoryIds(@Header(Constants.KEY_X_USER_TOKEN) String token,
                                               @Field("category_ids[]") ArrayList<String> serviceIds);


    @Headers({"Accept: application/json"})
    @PUT()
    Call<ResponseBody> acceptRequest(@Header(Constants.KEY_X_USER_TOKEN) String token,
                                     @Url String url,
                                     @Query("service_request[artist_description]") String desc,
                                     @Query("service_request[price]") String price,
                                     @Query("service_request[turn_around_time]") String tat);

    @Headers({"Accept: application/json"})
    @GET("v1/artists")
    Call<ResponseBody> getArtistByService(@Header(Constants.KEY_X_USER_TOKEN) String token,
                                          @Query("service_ids[]") Object[] serviceIds);

    @Headers({"Accept: application/json"})
    @GET("v1/cart_items")
    Call<ResponseBody> getCartItems(@Header(Constants.KEY_X_USER_TOKEN) String token);
}
