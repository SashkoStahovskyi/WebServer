package com.stahovsky.webserver.entity;

public enum HttpMethod {

    GET("GET"),
    POST("POST"),
    HEAD("HEAD"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE");

    private final String methodName;

    HttpMethod(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public static HttpMethod getMethod(String methodName) {
        for (HttpMethod values : values()) {
            if (values.getMethodName().equals(methodName)) {
                return values;
            }
        }
        throw new IllegalArgumentException(" Not exist http method for method name: " + methodName);
    }
}
