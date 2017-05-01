package cscie56.fp

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON


@Transactional(readOnly = true)
class RestaurantController {
    def restaurantService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Restaurant.list(params), model:[restaurantCount: Restaurant.count()]
    }

    def show(Restaurant restaurant) {
        respond restaurant
    }

    def create() {
        respond new Restaurant(params)
    }

    def getInput (Restaurant restaurant){
        def data = [:]
        def cols = [
                [label: "Variables", type:"string"],
                [label: "Values", type:"number"]
        ]
        def rows = []
        def data1 = [cols: cols, rows: rows]
        if (restaurant.surveys.size() > 1) {
            data = restaurantService.calculateAvg (restaurant)
            def addRow = { name, value ->
                rows << [c: [[v: name], [v: value]]]
            }
            addRow("Service", data.get('Service'))
            addRow("Hygiene", data.get('Hygiene'))
            addRow("Ambiance", data.get('Ambiance'))
            addRow("Taste", data.get('Taste'))
            addRow("Ratings", data.get('Ratings'))

        }
        render data1 as JSON
    }

    def search () {
        render (view:'search')
    }

    def String wrapSearchParm(value) {
        '%'+value+'%'
    }

    def searchResults () {

        def list
        String resName = params.restaurantName
        String cusineType = params.cuisineType

        if (resName){
            list = Restaurant.findAllByRestaurantNameIlike(wrapSearchParm(resName))
        } else if (cusineType) {
            list = Restaurant.findAllByCuisineTypeIlike(wrapSearchParm(cusineType))
        } else if (resName!=null & cusineType!=null) {
            list = Restaurant.findAllByRestaurantNameIlikeAndCuisineTypeIlike ((wrapSearchParm(resName)),(wrapSearchParm(cusineType)))
        }  else {
            list = Restaurant.list()
        }
        render(template:'searchResults', model:[searchresults:list])

    }

    @Transactional
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
