package com.merrycoders.target

import com.target.api.Target
import org.apache.commons.lang.WordUtils
import wslite.rest.ContentType
import wslite.rest.RESTClient

class ProductsService {

    /**
     * The Product Search method returns products that satisfy the search criteria.  Product search operation will return 10 results at a time.
     * It can be overridden by passing a variable 'Count'. The parameter 'PageNumber' returns a specified page of results. An error is returned if you try to
     * access higher numbered pages.
     * @param keywords
     * @return JSON
     */
    def searchByKeywords(String keywords) {
        def restClient = Target.restClient
        def results = restClient.get(path: "/v2/items?Keywords=${keywords}&key=${Target.consumerKey}&ResponseGroups=Images,ItemAttributes,Reviews,Offers,MerchantItemAttributes,VariationSummary,Collections", accept: ContentType.JSON)

        def items = []
        def names = ["Title", "Brand", "Asin", "BestSeller", "TCIN", "ParentTCIN"]

        results.json.ItemSearchResponse.Items.Item.ItemAttributes.Attribute.each { attribute ->

            def item = [:]

            names.each { name ->

                def key = name
                def value = attribute.find { it."@name" == name }."@value"

                item[key] = value

            }

            items << item

            // def title = attribute.find { it."@name" == "Title" }."@value"

        }

        return items

    }

    /**
     * Returns a list of Category names
     * @return
     */
    def List getProductCategories() {

        def restClient = Target.restClient
        def result = restClient.get(path: "/v2/catalogs/5?key=${Target.consumerKey}", accept: ContentType.JSON)
        def categoryNames = result.json.BrowseNodeLookupResponse.BrowseNodes.BrowseNode.Children.BrowseNode.Name
        def capitalizedCategoryNames = categoryNames.collect{WordUtils.capitalize(it)}

        return capitalizedCategoryNames

    }

}
