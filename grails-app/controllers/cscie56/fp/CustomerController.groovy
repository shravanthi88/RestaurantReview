package cscie56.fp

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional


import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

@Secured ([Role.ROLE_CUSTOMER])
class CustomerController {

    boolean isNewCustomer = false;
    def springSecurityService

    @Secured ([Role.ROLE_REP])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Customer.list(params), model:[customerCount: Customer.count()]
    }

    @Secured ([Role.ROLE_REP,Role.ROLE_CUSTOMER,Role.ROLE_ANONYMOUS])
    def show(Customer customer) {
        assignRoles (customer)
        respond customer
    }

    @Secured ([Role.ROLE_ANONYMOUS])
    def create() {
        isNewCustomer = true;
        respond new Customer (params)
    }

    @Secured ([Role.ROLE_REP,Role.ROLE_ANONYMOUS])
    def assignRoles (Customer customer) {
        if (isNewCustomer){
            User user1 = new User(username: customer.username ,password: 'secret').save(flush:true)
            Role customerRole = Role.findByAuthority('ROLE_CUSTOMER')
            UserRole.create(user1,customerRole).save(flush:true)
        }
    }

    @Secured ([Role.ROLE_REP,Role.ROLE_CUSTOMER,Role.ROLE_ANONYMOUS])
    @Transactional
    def save(Customer customer) {

        if (customer == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (customer.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond customer.errors, view:'create'
            return
        }

        customer.save flush:true


        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'customer.label', default: 'Customer'), customer.id])
                redirect customer
            }
            '*' { respond customer, [status: CREATED] }
        }

    }

    def myProfile (Customer customer){
        if (isLoggedIn()) {

            def user = springSecurityService.currentUser.username
            def list = Customer.findByUsername(user)
            render (view:'myProfile',model:[list:list])
        }
    }

    @Secured ([Role.ROLE_CUSTOMER])
    def edit(Customer customer) {
        respond customer
    }

    @Secured ([Role.ROLE_CUSTOMER])
    @Transactional
    def update(Customer customer) {
        if (customer == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (customer.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond customer.errors, view:'edit'
            return
        }

        customer.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'customer.label', default: 'Customer'), customer.id])
                redirect customer
            }
            '*'{ respond customer, [status: OK] }
        }
    }

    @Secured ([Role.ROLE_CUSTOMER])
    @Transactional
    def delete(Customer customer) {

        if (customer == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        customer.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'customer.label', default: 'Customer'), customer.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    @Secured ([Role.ROLE_CUSTOMER])
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
