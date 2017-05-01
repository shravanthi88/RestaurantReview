package cscie56.fp

import grails.test.mixin.*
import spock.lang.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(SurveyController)
@Mock(Survey)
class SurveyControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        params << [foodTaste: 3, foodService: 2,hygiene: 3,ratings: 4,ambiance: 3,review: "Good Place"]
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
        survey = new Survey(params)

        controller.save(survey)

        then:"A redirect is issued to the show action"
        response.redirectedUrl == '/survey/show/1'
        controller.flash.message != null
        survey.count() == 1
    }

}
