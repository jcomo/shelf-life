package me.jcomo.stilltasty.client;

import java.io.IOException;

public class StillTastyClientException extends IOException {
    public StillTastyClientException(String message) {
        super(message);
    }

    public StillTastyClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public StillTastyClientException(Throwable cause) {
        super(cause);
    }
}
