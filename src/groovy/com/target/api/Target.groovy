package com.target.api

import wslite.rest.RESTClient

class Target {

    public static final String API_BASE = "https://api.target.com"
    public static final String consumerKey = "yourConsumeKey"
    public static final String secretKey = "yourSecretKey"
    public static final String userId = "yourUserId"

    public static RESTClient getRestClient() {

        return new RESTClient(Target.API_BASE)

    }

}
