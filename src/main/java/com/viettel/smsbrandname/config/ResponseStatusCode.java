package com.viettel.smsbrandname.config;

import com.viettel.smsbrandname.commons.Translator;
import org.springframework.http.HttpStatus;

public enum ResponseStatusCode {

    SUCCESS(HttpStatus.OK, "OK");

    private final String message;

    private final HttpStatus httpCode;

    ResponseStatusCode(HttpStatus httpCode, String message) {
        this.httpCode = httpCode;
        String msgDb = Translator.toLocale(message);
        if (msgDb != null && !msgDb.isEmpty()) {
            this.message = msgDb;
        } else {
            this.message = httpCode.getReasonPhrase();
        }
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpCode() {
        return httpCode;
    }

    @Override
    public String toString() {
        return "ResponseStatus{" +
            "code='" + httpCode + '\'' +
            "message='" + Translator.toLocale(message) + '\'' +
            '}';
    }
}
