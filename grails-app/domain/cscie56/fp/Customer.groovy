package cscie56.fp

class Customer {

    String username
    String emailID
    Integer age

    static hasMany = [surveys:Survey]
    static constraints = {
        username (blank: false, unique: true)
        emailID nullable: false
        age   nullable: true, range: 12..100
    }
}
