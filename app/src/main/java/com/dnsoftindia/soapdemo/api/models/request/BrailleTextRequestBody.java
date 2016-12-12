package com.dnsoftindia.soapdemo.api.models.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Ganesha on 12/12/2016.
 */

@Root(name = "soap12:Body", strict = false)
public class BrailleTextRequestBody {

    @Element(name = "BrailleText",required = false)
    private BrailleTextRequestData brailleTextRequestData;

    public BrailleTextRequestData getBrailleTextRequestData() {
        return brailleTextRequestData;
    }

    public void setBrailleTextRequestData(BrailleTextRequestData brailleTextRequestData) {
        this.brailleTextRequestData = brailleTextRequestData;
    }
}
