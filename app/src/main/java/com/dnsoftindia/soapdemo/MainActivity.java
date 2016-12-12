package com.dnsoftindia.soapdemo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dnsoftindia.soapdemo.api.BrailleTextApiImpl;
import com.dnsoftindia.soapdemo.api.models.request.BrailleTextRequestBody;
import com.dnsoftindia.soapdemo.api.models.request.BrailleTextRequestData;
import com.dnsoftindia.soapdemo.api.models.request.BrailleTextRequestEnvelope;
import com.dnsoftindia.soapdemo.api.models.response.BrailleTextResponseData;
import com.dnsoftindia.soapdemo.api.models.response.BrailleTextResponseEnvelope;
import com.dnsoftindia.soapdemo.utils.NetworkUtils;
import com.dnsoftindia.soapdemo.utils.StringUtils;
import com.dnsoftindia.soapdemo.utils.UIUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String tag = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private EditText etConvert;
    private ImageView ivBraille;
    private Button btnConvert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etConvert = (EditText) findViewById(R.id.etConvert);
        ivBraille = (ImageView) findViewById(R.id.ivBraille);
        btnConvert = (Button) findViewById(R.id.btnConvert);
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etConvert.getText()!=null && etConvert.getText().toString().length()>3) {
                    String toConvert = etConvert.getText().toString();

                    if (NetworkUtils.isConnectedToInternet(MainActivity.this)) {
                        // Using KSoap2 library
//                      new AsyncTaskBraille().execute(toConvert);

                        // Using Retrofit library
                        convertTextToBraille(toConvert);
                    }
                    else {
                        UIUtils.showSnackBar(MainActivity.this, "Please check your internet connection.");
                    }
                }
                else {
                    UIUtils.showSnackBar(MainActivity.this, "Please add more than 3 characters to convert.");
                }
            }
        });

        UIUtils.showSnackBar(this, "Welcome, convert your texts to Braille messages using this app.");
    }



    private void convertTextToBraille(String textToConvert) {
        pDialog = ProgressDialog.show(MainActivity.this,
                "SOAP Demo",
                "Converting message to Braille",
                true);

        BrailleTextRequestEnvelope envelope = new BrailleTextRequestEnvelope();
        BrailleTextRequestBody body = new BrailleTextRequestBody();
        BrailleTextRequestData data = new BrailleTextRequestData();
        data.setInText(textToConvert);
        data.setTextFontSize("60.0");

        body.setBrailleTextRequestData(data);

        envelope.setBody(body);

        Call<BrailleTextResponseEnvelope> responseEnvelopeCall
                = BrailleTextApiImpl.getApi().convertTextToBraille(envelope);
        responseEnvelopeCall.enqueue(new Callback<BrailleTextResponseEnvelope>() {
            @Override
            public void onResponse(Call<BrailleTextResponseEnvelope> call, Response<BrailleTextResponseEnvelope> response) {
                if (response!=null) {
                    Log.i(tag, "API call completed");
                    hideProgressDialog();

                    Log.i(tag, response.toString()+" ---- "+response.body().toString());

                    BrailleTextResponseData data = ((BrailleTextResponseEnvelope)response.body())
                            .getBody().getBrailleTextResponseData();
                    if (StringUtils.hasValue(data.getBrailleTextResult())) {
                        Log.i(tag, "Converting image...");
                        Glide.with(MainActivity.this)
                                .load(Base64.decode(data.getBrailleTextResult(), Base64.DEFAULT))
                                .asBitmap()
                                .placeholder(android.R.drawable.ic_menu_camera)
                                .into(ivBraille);
                    }
                }
            }

            @Override
            public void onFailure(Call<BrailleTextResponseEnvelope> call, Throwable t) {
                hideProgressDialog();
                t.printStackTrace();
            }
        });
    }

    protected void hideProgressDialog() {
        if (pDialog!=null) {
            pDialog.dismiss();
        }
    }

//    private class AsyncTaskBraille extends AsyncTask<String, Void, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = ProgressDialog.show(MainActivity.this,
//                    "SOAP Demo",
//                    "Converting message to Braille",
//                    true);
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            Log.i(tag, "API call completed");
//            hideProgressDialog();
//
//            if (StringUtils.hasValue(s)) {
//                Log.i(tag, "Converting image...");
//                Glide.with(MainActivity.this)
//                        .load(Base64.decode(s, Base64.DEFAULT))
//                        .asBitmap()
//                        .placeholder(android.R.drawable.ic_menu_camera)
//                        .into(ivBraille);
//            }
//
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//            SoapObject request = new SoapObject(Constants.NAMESPACE, Constants.METHOD_NAME);
//            request.addProperty("InText", strings[0]); // Message to be converted
//            request.addProperty("TextFontSize", "60.0"); // Font of the converted Braille Text
//
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//            envelope.implicitTypes = true;
//            envelope.setOutputSoapObject(request);
//            envelope.dotNet = true;
//
//            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.API_BASE_URL);
//            httpTransportSE.debug = true;
//
//            try {
//                httpTransportSE.call(Constants.SOAP_ACTION, envelope);
//            } catch (IOException | XmlPullParserException e) {
//                e.printStackTrace();
//            }
//
//            Object result = null;
//            try {
//                result = envelope.getResponse();
//                Log.i(tag, String.valueOf(result));
//            } catch (SoapFault soapFault) {
//                soapFault.printStackTrace();
//            }
//
//            return result != null ? String.valueOf(result) : "";
//        }
//    }
}
