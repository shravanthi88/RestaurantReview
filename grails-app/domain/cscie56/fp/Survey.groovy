package cscie56.fp

class Survey {

    Integer foodTaste
    Integer foodService
    Integer hygiene
    Integer ratings
    Integer ambiance
    String review

    static belongsTo = [customer:Customer,restaurant:Restaurant]

    static constraints = {
        review maxSize:150, blank:false
        ratings nullable: false
    }
}
