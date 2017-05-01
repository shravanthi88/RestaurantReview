package cscie56.fp

import grails.test.mixin.*
import spock.lang.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(RestaurantController)
@Mock(Restaurant)
class RestaurantControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        params << [restaurantName:'firstName',location:'lastName',cuisineType:'Chinese']
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
        controller.show(null)

        then:"A 404 error is returned"
        response.status == 404

        when:"A domain instance is passed to the show action"
        populateValidParams(params)
        Restaurant restaurant = new Restaurant(params)
        controller.show(restaurant)

        then:"A model is populated containing the domain instance"
        model.restaurant == restaurant
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
        controller.create()

        then:"The model is correctly created"
        model.restaurant!= null
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
        controller.edit(null)

        then:"A 404 error is returned"
        response.status == 404

        when:"A domain instance is passed to the edit action"
        populateValidParams(params)
        def r = new Restaurant(params)
        controller.edit(r)

        then:"A model is populated containing the domain instance"
        model.restaurant == r
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        def restaurant = new Restaurant()
        restaurant.validate()
        controller.save(restaurant)

        then:"The create view is rendered again with the correct model"
        model.restaurant!= null
        view == 'create'

        when:"The save action is executed with a valid instance"
        response.reset()
        populateValidParams(params)
        restaurant = new Restaurant(params)

        controller.save(restaurant)

        then:"A redirect is issued to the show action"
        response.redirectedUrl == '/restaurant/show/1'
        controller.flash.message != null
        restaurant.count() == 1
    }

}
