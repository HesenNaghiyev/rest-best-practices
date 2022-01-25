package com.restfulservices.Restful.WebServices.helloworld;

public class HelloWorldBean {

    private String message;

    public HelloWorldBean(String mes) {
        this.message=mes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "HelloWorldBean{" +
                "message='" + message + '\'' +
                '}';
    }
}
