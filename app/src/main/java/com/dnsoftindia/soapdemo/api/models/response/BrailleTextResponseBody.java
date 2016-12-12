package com.dnsoftindia.soapdemo.api.models.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Ganesha on 12/12/2016.
 */

@Root(name = "Body", strict = false)
public class BrailleTextResponseBody {

    @Element(name = "BrailleTextResponse",required = false)
    private BrailleTextResponseData brailleTextResponseData;

    public BrailleTextResponseData getBrailleTextResponseData() {
        return brailleTextResponseData;
    }

    public void setBrailleTextResponseData(BrailleTextResponseData brailleTextResponseData) {
        this.brailleTextResponseData = brailleTextResponseData;
    }

    @Override
    public String toString() {
        return "BrailleTextResponseBody{" +
                "brailleTextResponseData=" + brailleTextResponseData +
                '}';
    }
}
