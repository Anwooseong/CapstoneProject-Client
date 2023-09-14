package com.uou.capstoneproject.data.match;

import com.uou.capstoneproject.data.match.response.GetAllMatchCountResponse;
import com.uou.capstoneproject.data.match.response.GetAllOfflineMatchCountResponse;
import com.uou.capstoneproject.data.match.response.GetAllOnlineMatchCountResponse;
import com.uou.capstoneproject.data.match.response.GetMatchCityResponse;
import com.uou.capstoneproject.data.match.response.GetMatchRoomDetailResponse;
import com.uou.capstoneproject.data.match.response.GetMatchRoomResponse;
import com.uou.capstoneproject.data.match.request.PostMatchRoom;
import com.uou.capstoneproject.data.match.response.matchroom.PostMatchRoomResponse;
import com.uou.capstoneproject.data.match.response.plan.GetDetailMatchResponse;
import com.uou.capstoneproject.data.match.response.plan.GetRemainMatchRoomResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MatchRetrofitInterface {

    /**
     * 매칭방 개설 retrofit url
     * @param jwt
     * @param postMatchRoom
     * @return
     */
    @POST("/app/matches/rooms")
    Call<PostMatchRoomResponse> createMatchRoom(@Header("X-ACCESS-TOKEN") String jwt,
                                                @Body PostMatchRoom postMatchRoom);

    /**
     * 참여가능한 매치 총 갯수 retrofit url
     * @return
     */
    @GET("/app/matches/rooms/counts")
    Call<GetAllMatchCountResponse> getAllMatchCount();

    /**
     * 참여가능한 온라인 매치 총 갯수 retrofit url
     * @return
     */
    @GET("/app/matches/rooms/counts/online")
    Call<GetAllOnlineMatchCountResponse> getAllOnlineMatchCount();

    /**
     * 참여가능한 내 지역 오프라인 총 갯수 retrofit url
     * @param local
     * @param city
     * @return
     */
    @GET("/app/matches/rooms/counts/locations")
    Call<GetAllOfflineMatchCountResponse> getAllOfflineMatchCount(@Query("localName") String local, @Query("cityName") String city);

    /**
     * 예정 매치 목록 조회 retrofit url
     * @param jwt
     * @return
     */
    @GET("/app/matches/rooms/plans")
    Call<GetRemainMatchRoomResponse> getRemainingMatchRoom(@Header("X-ACCESS-TOKEN") String jwt);

    /**
     * 예정 매치 상세 retrofit url
     * @param jwt
     * @param matchIdx
     * @return
     */
    @GET("/app/matches/rooms/plans/{matchIdx}")
    Call<GetDetailMatchResponse> getDetailMatch(@Header("X-ACCESS-TOKEN") String jwt, @Path("matchIdx") int matchIdx);

    /**
     * 매칭방 목록 조회 retrofit url(온/오프라인 매칭방 목록)
     * @param network
     * @param local
     * @param city
     * @return
     */
    @GET("/app/matches/rooms")
    Call<GetMatchRoomResponse> getMatchRoom(@Query("network") String network, @Query("localName") String local, @Query("cityName") String city);

    /**
     * 매칭방 상세 내용 retrofit url
     * @param matchIdx
     * @return
     */
    @GET("/app/matches/rooms/{matchIdx}")
    Call<GetMatchRoomDetailResponse> getMatchRoomDetail(@Path("matchIdx") int matchIdx);

    /**
     * 해당 행정 구역 목록 조회 retrofit url
     * @param local
     * @return
     */
    @GET("/app/matches/local")
    Call<GetMatchCityResponse> getMatchCity(@Query("localName") String local);
}
