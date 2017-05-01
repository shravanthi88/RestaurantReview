package cscie56.fp

import grails.test.mixin.*
import spock.lang.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(RestaurantService)
@Mock(Restaurant)
class RestaurantServiceSpec extends Specification {

    def restaurantService

    void "Test that average values are calculated for all the properties in a survey"() {
        when:

        Restaurant restaurant = new Restaurant (restaurantName: "Olive Gardens",location: "Fairfax", cuisineType: "Italian")
        restaurant.save(flush:true)

        Customer c = new Customer (username: "John",emailID: "john@test.com", age: 29)
        c.save(flush:true)
        Customer c1 = new Customer (username: "Sam",emailID: "sam@test.com", age: 31)
        c1.save(flush:true)
        Customer c2 = new Customer (username: "Drew",emailID: "drew@test.com", age: 37)
        c2.save(flush:true)

        Survey s = new Survey (foodTaste: 3, foodService: 2,hygiene: 3,ratings: 4,ambiance: 3,review: "Good Place")
        s.save()
        Survey s1 = new Survey (foodTaste: 2, foodService: 3,hygiene: 4,ratings: 3,ambiance: 3,review: "Test")
        s1.save()
        Survey s2 = new Survey (foodTaste: 4, foodService: 5,hygiene: 3,ratings: 5,ambiance: 4,review: "Loved the food")
        s2.save()

        c.addToSurveys(s)
        c1.addToSurveys(s1)
        c2.addToSurveys(s2)

        restaurant.addToSurveys(s)
        restaurant.addToSurveys(s1)
        restaurant.addToSurveys(s2)

        def map =[:]
        map = restaurantService.calculateAvg (restaurant)

        then:
        map !=null
        map.containsKey("Ratings")

    }

}
