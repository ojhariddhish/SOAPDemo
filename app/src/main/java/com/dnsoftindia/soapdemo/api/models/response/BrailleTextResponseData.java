package com.dnsoftindia.soapdemo.api.models.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * Created by Ganesha on 12/12/2016.
 */

@Root(name = "BrailleTextResponse", strict = false)
@Namespace(reference = "http://www.webserviceX.NET")
public class BrailleTextResponseData {

    @Element(name = "BrailleTextResult", required = false)
    private String brailleTextResult;

    public String getBrailleTextResult() {
        return brailleTextResult;
    }

    public void setBrailleTextResult(String brailleTextResult) {
        this.brailleTextResult = brailleTextResult;
    }

    @Override
    public String toString() {
        return "BrailleTextResponseData{" +
                "brailleTextResult=" + brailleTextResult +
                '}';
    }
}
