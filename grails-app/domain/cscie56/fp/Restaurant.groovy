package cscie56.fp

class Restaurant {

    String restaurantName
    String location
    String cuisineType

    static hasMany = [surveys: Survey]
    static constraints = {
        restaurantName nullable:false
        location nullable:false
        cuisineType inList:['Italian' , 'Mexican' , 'Mediterranean','Chinese']
    }
}
