package cscie56.fp

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Survey)
class SurveySpec extends Specification {

    def setup() {

    }

    def cleanup() {
    }
    void "test if values are in Range"() {
        Survey s1  = new Survey(foodService: 3,hygiene: 5,ambiance: 3, review: "Test very good")
        s1.save( flush:true)
        s1.foodTaste = 0
        assertFalse s1.validate()
        assertEquals '0 is below range for foodTaste', 'range', s1.errors['foodTaste']

        s1.foodService = 12
        assertFalse s1.validate()
        assertEquals '12 is above range for foodService.', 'range', s1.errors['foodService']

        s1.ratings = 8
        assertTrue s1.validate()
    }

    void "test if ratings can be nullable"() {
        Survey s1  = new Survey(foodTaste: 4,service: 3,hygiene: 5,ambiance: 3, review: "Test very good")
        s1.save( flush:true)
        assertFalse s1.validate()
        assertEquals 'ratings is null.', 'nullable', s1.errors['ratings']
        assertNull s1.errors['ratings']
    }

    void "test review can have a maximum of 150 characters"() {
        when: 'for a string of 151 characters'
        Survey s = new Survey (foodTaste: 4,service: 3,hygiene: 5,ratings: 4,ambiance: 3)
        String str = 'a'*151
        s.review = str
        s.save(flush:"true")

        then: 'review validation fails'
        def result = s.validate()
        s.errors['review'].code == 'maxSize.exceeded'

        when: 'for a string of 150 characters'
        str = 'a'*150
        s.review = str

        then: 'review validation passes'
        s.validate(['review'])
    }
}
