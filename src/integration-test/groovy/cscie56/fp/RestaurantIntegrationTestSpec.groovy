package cscie56.fp


import grails.test.mixin.integration.Integration
import grails.transaction.*
import spock.lang.*

@Integration
@Rollback
class RestaurantIntegrationTestSpec extends Specification {

    void "test that Restaurant is valid"() {
        when:""
        Restaurant restaurant = new Restaurant()
        then:
        !restaurant.validate()
    }

    void "test that there are 2 surveys in the db" () {
        expect:
        Survey.count() == 2
    }


}
