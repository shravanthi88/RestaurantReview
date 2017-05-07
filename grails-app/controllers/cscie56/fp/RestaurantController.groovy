package cscie56.fp


import grails.transaction.Transactional
import grails.converters.JSON

import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
@Secured ([Role.ROLE_CUSTOMER])
class RestaurantController {
    def restaurantService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured ([Role.ROLE_CUSTOMER,Role.ROLE_REP,Role.ROLE_ANONYMOUS])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Restaurant.list(params), model:[restaurantCount: Restaurant.count()]
    }

    @Secured ([Role.ROLE_CUSTOMER,Role.ROLE_REP,Role.ROLE_ANONYMOUS])
    def show(Restaurant restaurant) {
        respond restaurant
    }

    @Secured ([Role.ROLE_REP])
    def create() {
        respond new Restaurant(params)
    }

    @Secured ([Role.ROLE_CUSTOMER,Role.ROLE_REP,Role.ROLE_ANONYMOUS])
    def getInput (Restaurant restaurant){
        def data = [:]
        def cols = [
                [label: "Variables", type:"string"],
                [label: "Values", type:"number"]
        ]
        def rows = []
        def data1 = [cols: cols, rows: rows]
        def addRow = { name, value ->
            rows << [c: [[v: name], [v: value]]]
        }
        if (restaurant.surveys.size() > 1) {
            data = restaurantService.calculateAvg (restaurant)

            addRow("Service", data.get('Service'))
            addRow("Hygiene", data.get('Hygiene'))
            addRow("Ambiance", data.get('Ambiance'))
            addRow("Taste", data.get('Taste'))
            addRow("Ratings", data.get('Ratings'))
        }
        else {
            //Handle when there is only one survey for a restaurant

            restaurant.surveys.each { s ->
                addRow("Service", s.foodService)
                addRow("Hygiene", s.hygiene)
                addRow("Ambiance", s.ambiance)
                addRow("Taste", s.foodTaste)
                addRow("Ratings", s.ratings)
            }
        }
        render data1 as JSON
    }

    @Secured ([Role.ROLE_CUSTOMER,Role.ROLE_REP,Role.ROLE_ANONYMOUS])
    def search () {
        render (view:'search')
    }


    @Secured ([Role.ROLE_CUSTOMER,Role.ROLE_REP,Role.ROLE_ANONYMOUS])
    def searchResults () {

        def list
        String resName = params.restaurantName
        String cusineType = params.cuisineType

        if (resName){
            list = Restaurant.findAllByRestaurantNameIlike("%${resName}%")
        } else if (cusineType) {
            list = Restaurant.findAllByCuisineTypeIlike("%${cusineType}%")
        } else if (resName!=null & cusineType!=null) {
            list = Restaurant.findAllByRestaurantNameIlikeAndCuisineTypeIlike ("%${resName}%","%${cusineType}%")
        }  else {
            list = Restaurant.list()
        }

        render(template:'searchResults', model:[searchresults:list])

    }

    @Transactional
    @Secured ([Role.ROLE_REP])
    def save(Restaurant restaurant) {
        if (restaurant == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (restaurant.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond restaurant.errors, view:'create'
            return
        }

        restaurant.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'restaurant.label', default: 'Restaurant'), restaurant.id])
                redirect restaurant
            }
            '*' { respond restaurant, [status: CREATED] }
        }
    }

    def edit(Restaurant restaurant) {
        respond restaurant
    }

    @Transactional
    @Secured ([Role.ROLE_REP])
    def update(Restaurant restaurant) {
        if (restaurant == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (restaurant.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond restaurant.errors, view:'edit'
            return
        }

        restaurant.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'restaurant.label', default: 'Restaurant'), restaurant.id])
                redirect restaurant
            }
            '*'{ respond restaurant, [status: OK] }
        }
    }

    @Transactional
    @Secured ([Role.ROLE_REP])
    def delete(Restaurant restaurant) {

        if (restaurant == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        restaurant.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'restaurant.label', default: 'Restaurant'), restaurant.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }
}
