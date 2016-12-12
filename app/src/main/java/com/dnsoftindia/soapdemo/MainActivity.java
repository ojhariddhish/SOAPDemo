package com.dnsoftindia.soapdemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dnsoftindia.soapdemo.globals.Constants;
import com.dnsoftindia.soapdemo.utils.StringUtils;
import com.dnsoftindia.soapdemo.utils.UIUtils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

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
                    new AsyncTaskBraille().execute(toConvert);
                }
                else {
                    UIUtils.showSnackBar(MainActivity.this, "Please add more than 3 characters to convert");
                }
            }
        });

        UIUtils.showSnackBar(this, "Welcome, convert your texts to Braille messages using this app.");
    }

    private class AsyncTaskBraille extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = ProgressDialog.show(MainActivity.this,
                    "SOAP Demo",
                    "Converting message to Braille",
                    true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i(tag, "API call completed");
            if (pDialog!=null) {
                pDialog.dismiss();
            }

            if (StringUtils.hasValue(s)) {
                Log.i(tag, "Converting image...");
                Glide.with(MainActivity.this)
                        .load(Base64.decode(s, Base64.DEFAULT))
                        .asBitmap()
                        .placeholder(android.R.drawable.ic_menu_camera)
                        .into(ivBraille);
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(Constants.NAMESPACE, Constants.METHOD_NAME);
            request.addProperty("InText", strings[0]); // Message to be converted
            request.addProperty("TextFontSize", "60.0"); // Font of the converted Braille Text

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);
            envelope.dotNet = true;

            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.API_BASE_URL);
            httpTransportSE.debug = true;

            try {
                httpTransportSE.call(Constants.SOAP_ACTION, envelope);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }

            Object result = null;
            try {
                result = envelope.getResponse();
                Log.i(tag, String.valueOf(result));
            } catch (SoapFault soapFault) {
                soapFault.printStackTrace();
            }

            return result != null ? String.valueOf(result) : "";
        }
    }
}
