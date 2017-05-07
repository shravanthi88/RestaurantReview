package cscie56.fp

import grails.test.mixin.*
import spock.lang.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(SurveyController)
@Mock([Restaurant,Survey,Customer])
class SurveyControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        Restaurant r = new Restaurant (restaurantName: "Chipotle",location:"Reston",cuisineType: "Mexican")
        Customer c = new Customer (username: "John",emailID: "john@test.com", age: 29)
        params << [foodTaste: 3, foodService: 2,hygiene: 3,ratings: 4,ambiance: 3,review: "Good Place",restaurant: r,customer:c]
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
        controller.index()

        then:"The model is correct"
        !model.surveyList
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
        controller.create()

        then:"The model is correctly created"
        model.survey!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        def survey = new Survey()
        survey.validate()
        controller.save(survey)

        then:"The create view is rendered again with the correct model"
        model.survey!= null
        view == 'create'

        when:"The save action is executed with a valid instance"
        response.reset()
        populateValidParams(params)

        def s = new Survey (params)
        s.save (flush:true)
        controller.save(s)

        then:"A redirect is issued to the show action"
        response.redirectedUrl == '/survey/show/1'
        controller.flash.message != null
        s.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
        controller.show(null)

        then:"A 404 error is returned"
        response.status == 404

        when:"A domain instance is passed to the show action"
        populateValidParams(params)
        def survey = new Survey(params)
        controller.show(survey)

        then:"A model is populated containing the domain instance"
        model.survey == survey
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
        controller.edit(null)

        then:"A 404 error is returned"
        response.status == 404

        when:"A domain instance is passed to the edit action"
        populateValidParams(params)
        def survey = new Survey(params)
        controller.edit(survey)

        then:"A model is populated containing the domain instance"
        model.survey == survey
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'PUT'
        controller.update(null)

        then:"A 404 error is returned"
        response.redirectedUrl == '/survey/index'
        flash.message != null

        when:"An invalid domain instance is passed to the update action"
        response.reset()
        def survey = new Survey()
        survey.validate()
        controller.update(survey)

        then:"The edit view is rendered again with the invalid instance"
        view == 'edit'
        model.survey == survey

        when:"A valid domain instance is passed to the update action"
        response.reset()
        populateValidParams(params)
        survey = new Survey(params).save(flush: true)
        controller.update(survey)

        then:"A redirect is issued to the show action"
        survey != null
        response.redirectedUrl == "/survey/show/$survey.id"
        flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'DELETE'
        controller.delete(null)

        then:"A 404 is returned"
        response.redirectedUrl == '/survey/index'
        flash.message != null

        when:"A domain instance is created"
        response.reset()
        populateValidParams(params)
        def survey = new Survey(params).save(flush: true)

        then:"It exists"
        Survey.count() == 1

        when:"The domain instance is passed to the delete action"
        controller.delete(survey)

        then:"The instance is deleted"
        Survey.count() == 0
        response.redirectedUrl == '/survey/index'
        flash.message != null
    }

}
