package com.merrycoders.target

import com.target.api.Target
import spock.lang.Shared
import spock.lang.Specification
import wslite.rest.ContentType
import wslite.rest.RESTClient

class GuestSpec extends Specification {

    @Shared
    def restClient = new RESTClient(Target.API_BASE)

    void setup() {
        restClient.httpClient.sslTrustAllCerts = true
    }

    void "guest authentication and update profile"() {
        when:
        def response = restClient.post(path: "/guests/v3/auth?key=${Target.consumerKey}&userid=${Target.userId}", accept: ContentType.JSON) {
            json logonId: "dean.delponte@gmail.com", logonPassword: "aKyaAY2VNO\$FZ*MPj0V@"
        }

        then:
        assert 200 == response.statusCode
        assert "Del Ponte" == response.json.lastName
        assert "Dean" == response.json.firstName
        assert "dean.delponte@gmail.com" == response.json.logonId
        println response.json.accessToken
    }

}
