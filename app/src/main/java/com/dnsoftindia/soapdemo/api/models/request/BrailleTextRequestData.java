package com.dnsoftindia.soapdemo.api.models.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * Created by Ganesha on 12/12/2016.
 */

@Root(name = "BrailleText", strict = false)
@Namespace(reference = "http://www.webserviceX.NET")
public class BrailleTextRequestData {

    @Element(name = "InText", required = false)
    private String inText;
    @Element(name = "TextFontSize", required = false)
    private String textFontSize;

    public String getInText() {
        return inText;
    }

    public void setInText(String inText) {
        this.inText = inText;
    }

    public String getTextFontSize() {
        return textFontSize;
    }

    public void setTextFontSize(String textFontSize) {
        this.textFontSize = textFontSize;
    }
}
