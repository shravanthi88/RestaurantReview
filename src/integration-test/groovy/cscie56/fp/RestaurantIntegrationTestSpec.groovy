package cscie56.fp


import grails.test.mixin.integration.Integration
import grails.transaction.*
import spock.lang.*

@Integration
@Rollback
class RestaurantIntegrationTestSpec extends Specification {

    void "test that restaurant is valid and has at least one survey"() {
        when:""
        Restaurant restaurant = new Restaurant ()
        Survey survey = new Survey()
        Customer customer = new Customer()
        then:
        restaurant.addToSurveys(survey)
        customer.addToSurveys(survey)
        !restaurant.validate()
    }

    void "test when search is made a restaurant is returned"() {
        when:"create restaurant object"
        Restaurant restaurant = new Restaurant (restaurantName: "Olive Gardens",location: "Fairfax", cuisineType: "Italian")
        Restaurant restaurant1 = new Restaurant (restaurantName: "Qdaba",location: "Reston", cuisineType: "Mexican")
        Survey survey = new Survey(foodTaste: 3, foodService: 2,hygiene: 3,ratings: 4,ambiance: 3,review: "Good Place")
        Customer customer = new Customer(username: "Sam",emailID: "sam@test.com", age: 31)

        def list = Restaurant.findAllByRestaurantNameIlikeAndCuisineTypeIlike("Olive Gardens","Italian")

        then:
        list.size() == 1

    }


}
