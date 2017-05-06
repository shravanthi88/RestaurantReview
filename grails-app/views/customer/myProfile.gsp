<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'customer.label', default: 'Customer')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
    <a href="#show-customer" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="wrap">
        <div class="container">
        <div class="panel panel-primary">
            <div class="panel-heading">Customer Details</div>
                <div class = "panel-body">
                    <p><strong>Name: </strong>${list.username} </p>
                    <p><strong>Email ID: </strong>${list.emailID} </p>
                    <p><strong>Age: </strong>${list.age} </p><br/>
                    <div><strong>Reviews by me:</strong></div><hr/>
                    <div class="box">
                        <g:each in="${list.surveys}" var="survey" >
                         <p>${survey.review} for
                             <g:link   controller="restaurant" action="show" id="${survey.restaurant.id}" resource="${restaurant}">
                                 ${survey.restaurant.restaurantName}
                             </g:link>
                         </p>
                     </g:each>
                    </div>
                </div>
            </div>
         </div>
    </div>
<sec:ifAllGranted roles='ROLE_CUSTOMER'>
    <fieldset class="buttons">
        <g:link class="edit" action="edit" resource="${this.customer}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
        <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
    </fieldset>
</sec:ifAllGranted>

    </body>
</html>
