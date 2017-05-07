<%@ page import="cscie56.fp.Customer; cscie56.fp.Restaurant" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'survey.label', default: 'Survey')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#create-survey" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="create-survey" class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.survey}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.survey}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form action="save">
                <fieldset class="form">
                <table>
                    <tbody>
                    <tr class="prop">
                        <td valign="top" class="name">Food Service:</td>
                        <td valign="top" class="value">
                            <input type="range" name="foodService" style="width:200px;" value="" min="1" max="10" step="1" id="foodService" oninput="foodServiceOutputId.value = foodService.value"/>
                            <output name="foodServiceName" id="foodServiceOutputId"></output></td>
                    </tr>
                    <tr class="prop">
                        <td valign="top" class="name">Taste:</td>
                        <td valign="top" class="value">
                            <input type="range" name="foodTaste" style="width:200px;" value="" min="1" max="10" step="1" id="foodTaste" oninput="foodTasteOutputId.value = foodTaste.value"/>
                             <output name="foodTasteName" id="foodTasteOutputId"></output></td>
                    </tr>
                    <tr class="prop">
                        <td valign="top" class="name">Hygiene:</td>
                        <td valign="top" class="value">
                            <input type="range" name="hygiene" style="width:200px;" value="" min="1" max="10" step="1" id="hygiene" oninput="hygieneOutputId.value = hygiene.value"/>
                            <output name="hygieneName" id="hygieneOutputId"></output>
                        </td>
                    </tr>
                    <tr class="prop">
                        <td valign="top" class="name">Ambiance:</td>
                        <td valign="top" class="value">
                            <input type="range" name="ambiance" style="width:200px;" value="" min="1" max="10" step="1" id="ambiance" oninput="ambianceOutputId.value = ambiance.value"/>
                            <output name="ambianceName" id="ambianceOutputId"></output></td>
                    </tr>
                    <tr class="prop">
                        <td valign="top" class="name">Ratings:
                            <span class='required-indicator'>*</span>
                        </td>
                        <td valign="top" class="value">
                            <input type="range" required  name="ratings" style="width:200px;" value="" min="1" max="10" step="1" id="ratings" oninput="ratingsOutputId.value = ratings.value"/>
                            <output name="ratingsName" id="ratingsOutputId"></output></td>
                    </tr>
                    <tr class="prop">
                        <td valign="top" class="name">Review:
                            <span class='required-indicator'>*</span>
                        </td>
                        <td valign="top" class="value">
                            <textarea name="review" required id="review" type="text" value="${review}" rows="6" cols="80" maxlength="150">
                            </textarea>
                        </td>
                    </tr>
                    <tr class="prop">
                        <td valign="top" class="name">Restaurant Name: </td>
                        <td valign="top" class="value">
                            <g:select from="${Restaurant.list()}"
                                      optionValue="restaurantName"
                                      optionKey="id"
                                      name="restaurant.id" required=""
                                      value=""
                                      id="restaurant" />
                        </td>
                    </tr>
                    <tr class="prop">
                        <td valign="top" class="name">Customer Name:
                            <span class='required-indicator'>*</span>
                        </td>
                        <td valign="top" class="value">
                            <g:select from="${Customer.findByUsername(sec.loggedInUserInfo(field:"username"))}"
                                      optionValue="username"
                                      name="customer.id"
                                      optionKey="id"
                                      value="username"
                                      id="customer" />
                        </td>
                    </tr>
                    </tbody>
                </table>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
