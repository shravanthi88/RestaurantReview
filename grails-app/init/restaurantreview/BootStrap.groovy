package restaurantreview

import cscie56.fp.Customer
import cscie56.fp.Restaurant
import cscie56.fp.Survey

class BootStrap {

    def init = { servletContext ->

        Restaurant r = new Restaurant (restaurantName: "Chipotle",location:"Reston",cuisineType: "Mexican")
        r.save (flush:true)

        Restaurant r1 = new Restaurant (restaurantName: "Olive Garden",location:"Reston",cuisineType: "Italian")
        r.save (flush:true)

        Customer c = new Customer (username: "John",emailID: "john@test.com", age: 29)
        c.save (flush:true)

        Customer c1 = new Customer (username: "Sam",emailID: "sam@test.com", age: 31)
        c1.save (flush:true)

        Survey s = new Survey (foodTaste: 3, foodService: 2,hygiene: 3,ratings: 4,ambiance: 3,review: "Good Place")
        s.save (flush:true)

        Survey s1 = new Survey (foodTaste: 2, foodService: 3,hygiene: 4,ratings: 3,ambiance: 3,review: "Test")
        s1.save (flush:true)

        r.addToSurveys(s)
        r.addToSurveys(s1)
        r.save(flush:true)

        c.addToSurveys(s)
        c1.addToSurveys(s1)
    }
    def destroy = {
    }



}
