package com.merrycoders.target

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(ProductsService)
class ProductsServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "search products by keywords"() {
        when:
        def results = service.searchByKeywords("milk")

        then:
        results.size() == 10

    }

    void "get product categories"() {
        when:
        def results = service.getProductCategories()

        then:
        results.size() == 26

    }

}
