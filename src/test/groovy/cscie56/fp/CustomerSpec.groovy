package cscie56.fp

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Customer)
class CustomerSpec extends Specification {
    Customer c

    def setup() {
         c = new Customer (username: "foodCritic", emailAddress: "abc@gmail.com",age: 30)
        c.save (flush:true)
    }

    def cleanup() {
    }

    def "test 'blank' constraint"() {

        when:
        def instance = new Customer( username: " ", emailID: "name@test.com", age:40 )
        def result = instance.validate()
        then:
        result == false
    }

    void "Test that userName should be unique"() {
        when: "The first customer is saved"
        String firstName = 'Unique Name'
        String firstEmail  = 'test@customer.com'
        Customer firstCustomer = new Customer(username: firstName, emailID: firstEmail)
        firstCustomer.save(flush:true)

        String secondEmail = 'second@customer.com'
        Customer customerWithSameCustomerName =
                new Customer(username: firstName, emailID: secondEmail)

        then: "Another customer with same customerName is invalid"
        Customer.count() == 1
        !customerWithSameCustomerName.validate()
    }


}
