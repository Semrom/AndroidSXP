package com.sxp.core.network.impl.messages;

import com.sxp.core.network.api.annotation.MessageElement;
import com.sxp.core.network.impl.MessagesImpl;

public class RequestItemMessage extends MessagesImpl{
    @MessageElement("source")
    private String sourceUri;

    @MessageElement("title")
    private String title;

    @MessageElement("type")
    private String type = "request";

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setSource(String source) {
        this.sourceUri = source;
    }

    public String getSource() {
        return sourceUri;
    }

}