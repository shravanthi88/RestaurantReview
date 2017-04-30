<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'restaurant.label', default: 'Restaurant')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-restaurant" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-restaurant" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <div class="container">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <td>Restaurant Name</td>
                        <td>Location</td>
                        <td>Cusine Type</td>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each var="r" in="${restaurantList}">
                        <tr>
                            <td>
                                <g:link  class="restaurant" action="show" id="${r.id}" resource="${restaurant}">
                                    ${r.restaurantName}
                                </g:link>
                            </td>
                            <td>${r.location}</td>
                            <td>${r.cuisineType}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="pagination">
                <g:paginate total="${restaurantCount ?: 0}" />
            </div>
        </div>
    </body>
</html>