package restaurantreview

import cscie56.fp.Customer
import cscie56.fp.Restaurant
import cscie56.fp.Role
import cscie56.fp.Survey
import cscie56.fp.User
import cscie56.fp.UserRole

class BootStrap {

    def init = { servletContext ->

        User customer1 = new User(username: 'John',password: 'secret')
        saveObject(customer1)
        User customer2 = new User(username: 'Sam',password: 'supersecret')
        saveObject(customer2)
        User customer3 = new User(username: 'Test',password: 'Test')
        saveObject(customer3)
        User rep = new User(username: 'admin', password: 'admin')
        saveObject(rep)

        Role customerRole = new Role(authority: Role.ROLE_CUSTOMER)
        saveObject(customerRole)
        Role repRole = new Role(authority: Role.ROLE_REP)
        saveObject(repRole)

        UserRole.create(customer1,customerRole)
        UserRole.create(rep,repRole)
        UserRole.create(customer2,customerRole)
        UserRole.create(customer3,customerRole)

        Restaurant r = new Restaurant (restaurantName: "Chipotle",location:"Reston",cuisineType: "Mexican")
        r.save (flush:true)

        Restaurant r1 = new Restaurant (restaurantName: "Olive Garden",location:"Reston",cuisineType: "Italian")
        r1.save (flush:true)

        Customer c = new Customer (username: "John",emailID: "john@test.com", age: 29)
        c.save (flush:true)

        Customer c1 = new Customer (username: "Sam",emailID: "sam@test.com", age: 31)
        c1.save (flush:true)

        Survey s = new Survey (foodTaste: 3, foodService: 2,hygiene: 3,ratings: 4,ambiance: 3,review: "Good Place")
        s.save (flush:true)

        Survey s1 = new Survey (foodTaste: 2, foodService: 3,hygiene: 4,ratings: 3,ambiance: 3,review: "Test")
        s1.save (flush:true)

        Survey s2 = new Survey (foodTaste: 2, foodService: 3,hygiene: 4,ratings: 3,ambiance: 3,review: "Nice")
        s2.save (flush:true)

        r.addToSurveys(s)
        r.addToSurveys(s1)
        r.save(flush:true)

        c.addToSurveys(s)
        c1.addToSurveys(s1)

        r1.addToSurveys(s2)
        r1.save(flush:true)
        c1.addToSurveys(s2)

    }
    def destroy = {
    }

    def saveObject(object) {
        if (!object.save(flush:true)) {
            object.errors.allErrors.each { println it }
        }
    }


}
