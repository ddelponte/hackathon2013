package com.merrycoders.target

import com.target.api.Target
import spock.lang.Shared
import spock.lang.Specification
import wslite.rest.ContentType
import wslite.rest.RESTClient

class TargetLocationSpec extends Specification {

    @Shared
    def restClient = new RESTClient(Target.API_BASE)

    void setup() {
        restClient.httpClient.sslTrustAllCerts = true
    }

    void "get nearby stores by zipcode"() {
        when:
        def response = restClient.get(path: "/v2/store?nearby=55402&key=${Target.consumerKey}", accept: ContentType.JSON)

        then:
        assert 200 == response.statusCode
        assert 112 == response.json."Locations"."@count"
    }

    void "get nearby stores by zipcode and range"() {
        when:
        def response = restClient.get(path: "/v2/store?nearby=55402&range=10&key=${Target.consumerKey}", accept: ContentType.JSON)

        then:
        assert 200 == response.statusCode
        assert 18 == response.json."Locations"."@count"
    }

    void "get store by id"() {
        when:
        def response = restClient.get(path: "/v2/store/1375?key=${Target.consumerKey}", accept: ContentType.JSON)

        then:
        assert 200 == response.statusCode
        assert "Mpls Nicollet Mall" == response.json."Location"."Name"
        assert "900 Nicollet Mall, Minneapolis, MN 55403-2530" == response.json."Location"."Address"."FormattedAddress"
    }
}
