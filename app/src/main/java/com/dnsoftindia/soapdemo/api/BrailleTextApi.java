package com.dnsoftindia.soapdemo.api;

import com.dnsoftindia.soapdemo.api.models.request.BrailleTextRequestEnvelope;
import com.dnsoftindia.soapdemo.api.models.response.BrailleTextResponseEnvelope;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Ganesha on 12/12/2016.
 */

public interface BrailleTextApi {

    @Headers({
            "Content-Type: text/xml",
            "Accept-Charset: utf-8"
    })
    @POST("/braille.asmx")
    Call<BrailleTextResponseEnvelope> convertTextToBraille(@Body BrailleTextRequestEnvelope body);

}
