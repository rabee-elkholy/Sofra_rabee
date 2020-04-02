package com.androdu.sofra.data.retrofitApi;


import com.androdu.sofra.data.models.GeneralResponse;
import com.androdu.sofra.data.models.city.CitiesGeneralResponse;
import com.androdu.sofra.data.models.city.CitiesGeneralResponse2;
import com.androdu.sofra.data.models.client.ClientLoginGeneralResponse;
import com.androdu.sofra.data.models.region.RegionsGeneralResponse;
import com.androdu.sofra.data.models.restaurant.RestaurantDetailsGeneralResponse;
import com.androdu.sofra.data.models.restaurant.RestaurantLoginGeneralRequest;
import com.androdu.sofra.data.models.restaurant.RestaurantsGeneralRequest;
import com.androdu.sofra.data.models.restaurantCategory.RestaurantCategoriesGeneralResponse;
import com.androdu.sofra.data.models.retaurantItem.RestaurantItemsGeneralResponse;
import com.androdu.sofra.data.models.review.RestaurantReviewsGeneralRequest;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface IRetrofitApi {

    @POST("restaurant/login")
    @FormUrlEncoded
    Call<RestaurantLoginGeneralRequest> restaurantLogin(@Field("email") String email,
                                                        @Field("password") String password);

    @POST("client/login")
    @FormUrlEncoded
    Call<ClientLoginGeneralResponse> clientLogin(@Field("email") String email,
                                                 @Field("password") String password);

    @GET("restaurant")
    Call<RestaurantDetailsGeneralResponse> getRestaurantsDetails(@Query("restaurant_id") int restaurantId);

    @GET("restaurant/reviews")
    Call<RestaurantReviewsGeneralRequest> getRestaurantsReviews(@Query("restaurant_id") int restaurantId,
                                                                @Query("page") int pageNumber);

    @GET("cities-not-paginated")
    Call<CitiesGeneralResponse2> getCities();

    @GET("restaurants")
    Call<RestaurantsGeneralRequest> getRestaurantsByPage(@Query("page") int pageNum);

    @GET("categories")
    Call<RestaurantCategoriesGeneralResponse> getRestaurantCategories(@Query("restaurant_id") int restaurantId);

    @GET("items")
    Call<RestaurantItemsGeneralResponse> getRestaurantItems(@Query("restaurant_id") int restaurantId,
                                                            @Query("category_id") int categoryId);

    @GET("restaurants")
    Call<RestaurantsGeneralRequest> getRestaurantsByPageAndFilter(@Query("page") int pageNum,
                                                                  @Query("keyword") String keyWord,
                                                                  @Query("city_id") int cityId);

    @GET("restaurants")
    Call<RestaurantsGeneralRequest> getRestaurantsByPageAndFilter(@Query("page") int pageNum,
                                                                  @Query("keyword") String keyWord);

    @GET("cities")
    Call<CitiesGeneralResponse> getCities(@Query("page") int pageNum);

    @GET("regions")
    Call<RegionsGeneralResponse> getRegions(@Query("city_id") int cityId);


    @POST("restaurant/sign-up")
    @Multipart
    Call<RestaurantLoginGeneralRequest> restaurantRegister(@Part("name") RequestBody name,
                                                           @Part("email") RequestBody email,
                                                           @Part("password") RequestBody password,
                                                           @Part("password_confirmation") RequestBody passwordConfirmation,
                                                           @Part("phone") RequestBody phone,
                                                           @Part("whatsapp") RequestBody whatsapp,
                                                           @Part("region_id") RequestBody regionId,
                                                           @Part("delivery_cost") RequestBody delivery_cost,
                                                           @Part("minimum_charger") RequestBody minimumCharger,
                                                           @Part MultipartBody.Part profileImage,
                                                           @Part("delivery_time") RequestBody deliveryTime);


    @POST("client/sign-up")
    @Multipart
    Call<ClientLoginGeneralResponse> clientRegister(@Part("name") RequestBody name,
                                                    @Part("email") RequestBody email,
                                                    @Part("phone") RequestBody shone,
                                                    @Part("region_id") RequestBody regionId,
                                                    @Part("password") RequestBody password,
                                                    @Part("password_confirmation") RequestBody passwordConfirmation,
                                                    @Part MultipartBody.Part photo);


    @POST("client/restaurant/review")
    @FormUrlEncoded
    Call<GeneralResponse> addReview(@Field("rate") int rate,
                                    @Field("comment") String comment,
                                    @Field("restaurant_id") int restaurantId,
                                    @Field("api_token") String apiToken);

}
