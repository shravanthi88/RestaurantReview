package cscie56.fp

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK


@Secured ([Role.ROLE_CUSTOMER])
class SurveyController {

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Survey.list(params), model:[surveyCount: Survey.count()]
    }

    def show(Survey survey) {
        respond survey
    }

    def create() {
        respond new Survey(params)
    }

    @Transactional
    def save(Survey survey) {
        if (survey == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (survey.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond survey.errors, view:'create'
            return
        }

        survey.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'survey.label', default: 'Survey'), survey.id])
                redirect survey
            }
            '*' { respond survey, [status: CREATED] }
        }
    }

    def edit(Survey survey) {
        respond survey
    }

    @Transactional
    def update(Survey survey) {
        if (survey == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (survey.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond survey.errors, view:'edit'
            return
        }

        survey.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'survey.label', default: 'Survey'), survey.id])
                redirect survey
            }
            '*'{ respond survey, [status: OK] }
        }
    }

    @Transactional
    def delete(Survey survey) {

        if (survey == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        survey.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'survey.label', default: 'Survey'), survey.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
