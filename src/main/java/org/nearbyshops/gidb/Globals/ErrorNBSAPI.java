package org.nearbyshops.gidb.Globals;

/**
 * Created by sumeet on 20/9/16.
 */
public class ErrorNBSAPI {

    public ErrorNBSAPI() {
    }

    public ErrorNBSAPI(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    private Integer statusCode;
    private String message;


    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
