<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'restaurant.label', default: 'Restaurant')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <asset:javascript src="application.js"></asset:javascript>
    </head>
    <body>
    <div id="wrap">
        <g:form action="search">
            <table>
                <tbody>
                <tr class="prop">
                    <td valign="top" class="name">Restaurant Name:</td>
                    <td valign="top" class="value"><input name="restaurantName" type="text" value="${restaurantName}"/></td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">Cusine Type:</td>
                    <td valign="top" class="value"><input name="cuisineType" type="text" value="${cuisineType}"/></td>
                </tr>
                </tbody>
            </table>
            <g:submitToRemote update="searchresults" value="Search" url="[controller:'restaurant', action:'searchResults']">
            </g:submitToRemote>
        </g:form>
        <g:render template="searchresults"/>
    </div>
    </body>
</html>