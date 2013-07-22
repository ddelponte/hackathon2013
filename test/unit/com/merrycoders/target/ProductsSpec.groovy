package com.merrycoders.target

import com.target.api.Target
import spock.lang.Shared
import spock.lang.Specification
import wslite.rest.ContentType
import wslite.rest.RESTClient

class ProductsSpec extends Specification {
    @Shared
    def restClient = new RESTClient(Target.API_BASE)

    void setup() {
        restClient.httpClient.sslTrustAllCerts = true
    }

    void "get by product id"() {
        when:
        def response = restClient.get(path: "/v2/items/13970297?key=${Target.consumerKey}", accept: ContentType.JSON)

        then:
        assert 200 == response.statusCode
        assert "Superman Men's Caped Socks" == response.json."ItemLookupResponse"."Items"."Item"."ItemAttributes"."Title"
    }

    void "search products by keyword"() {
        when:
        def response = restClient.get(path: "/v2/items?Keywords=socks&ResponseGroups=ItemAttributes&key=${Target.consumerKey}", accept: ContentType.JSON)

        then:
        assert 200 == response.statusCode
        assert 1110 == response.json."ItemSearchResponse"."Items"."TotalResults"
    }

}
