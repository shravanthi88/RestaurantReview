<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Grails"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <asset:stylesheet src="application.css"/>

    <g:layoutHead/>
</head>
<body>

    <div class="navbar navbar-default navbar-static-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/#">
                    <i class="fa grails-icon">
                        <asset:image src="grails-cupsonly-logo-white.svg"/>
                    </i> Restaurant Review
                </a>
            </div>
            <div class="navbar-collapse collapse" aria-expanded="false" style="height: 0.8px;">
                <ul class="nav navbar-nav">
                    <li><a href="../../restaurant/index">Restaurants</a></li>
                    <li class="active"><a href="../../restaurant/search">Search <span class="sr-only">(current)</span></a></li>
                    <li><a href="../../survey/create">Create Survey </a></li>
                    <li><a href="../../customer/index"> Customers </a></li> <!--only admin can login and see-->
                    <sec:ifAllGranted roles='ROLE_ANONYMOUS'>
                      <li><a href="../../customer/create"> New Customer </a></li>
                    </sec:ifAllGranted>
                    <sec:ifAllGranted roles='ROLE_CUSTOMER'>
                        <li><a href="../../customer/myProfile">My Profile </a></li>
                    </sec:ifAllGranted>
                </ul>
            </div>
        </div>
    </div>

    <g:layoutBody/>

    <div class="footer" role="contentinfo"></div>

    <div id="spinner" class="spinner" style="display:none;">
        <g:message code="spinner.alt" default="Loading&hellip;"/>
    </div>

    <asset:javascript src="application.js"/>

</body>
</html>
