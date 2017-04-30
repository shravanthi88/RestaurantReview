package cscie56.fp

import grails.transaction.Transactional

@Transactional
class RestaurantService {

    def surveyService

    def calculateAvg (Restaurant r) {
        def map = [:]
        def surveyList
        surveyList = r.surveys as Set<Survey>

        def avgServiceval   = calAvg (surveyList.foodService)
        def avgHygieneval   = calAvg (surveyList.hygiene)
        def avgAmbianceval  = calAvg (surveyList.ambiance)
        def avgTasteval     = calAvg (surveyList.foodTaste)
        def avgRatingsval   = calAvg (surveyList.ratings)

        map.put ("Service",avgServiceval)
        map.put ("Hygiene",avgHygieneval)
        map.put ("Ambiance",avgAmbianceval)
        map.put ("Taste",avgTasteval)
        map.put ("Ratings",avgRatingsval)

        return map
    }

    int calAvg (def list) {
        int total = 0;
        int avg = 0;
        for (int i=0; i < list.size() ; i++) {
            total = total +list[i]
            avg = total/list.size()
        }
        return avg
    }
}
