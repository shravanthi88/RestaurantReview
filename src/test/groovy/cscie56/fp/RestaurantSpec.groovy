package cscie56.fp

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Restaurant)
class RestaurantSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test validation of nullable objects" () {
        when:
        Restaurant r1 = new Restaurant()
        then:
        !r1.validate()

    }

    void "test cuisineType is from the list" () {
        Restaurant r = new Restaurant(restaurantName:"Qdaba", location: "Reston")
        r.cuisineType = 'American'
        r.save(flush:true)
        assertFalse r.validate()
        assertEquals 'NotValid not in list for type.', 'inList', r.errors['cuisineType']

        r.cuisineType = 'Mexican'
        assertTrue r.validate()
    }
}
