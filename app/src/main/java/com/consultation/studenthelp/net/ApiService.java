package com.consultation.studenthelp.net;

import com.consultation.studenthelp.net.vo.UserInfo;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("/web/identity/userIdentityStatus")
    Observable<UserInfo> userIdentityStatus(@FieldMap HashMap<String, String> map);
}
