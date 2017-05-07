<div id="searchresults">
    <h3>Search Results</h3>
    <g:if test="${searchresults}">
        <table>
            <thead>
             <tr>
                 <g:sortableColumn property="restaurantName" title ="Restaurant Name" />
                 <g:sortableColumn property="cuisineType" title ="Cuisine Type" />
                 <th><g:message code="restaurant.location.label" default="Location" /></th>
             </tr>
            </thead>
            <tbody>
              <g:each in="${searchresults}" status="i" var="restaurant">
                  <tr>
                      <td>
                          <g:link   controller="restaurant" action="show" id="${restaurant.id}" resource="${restaurant}">
                              ${restaurant.restaurantName}
                      </g:link>
                      </td>
                      <td>${restaurant.cuisineType}</td>
                      <td>${restaurant.location}</td>
                  </tr>
              </g:each>
            </tbody>
        </table>
    </g:if>
    <g:else>
        <p>No Results Found</p>
    </g:else>
</div>