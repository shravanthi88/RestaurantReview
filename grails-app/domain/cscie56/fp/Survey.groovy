package cscie56.fp

class Survey {

    Integer foodTaste
    Integer foodService
    Integer hygiene
    Integer ratings
    Integer ambiance
    String review
    Date dateCreated

    static belongsTo = [customer:Customer,restaurant:Restaurant]

    static constraints = {
        review maxSize:150, blank:false
        foodTaste     nullable: true, range: 1..10
        foodService   nullable: true, range: 1..10
        ratings       nullable: false, range: 1..10
        ambiance      nullable: true, range: 1..10
        hygiene       nullable: true, range: 1..10
    }
}
