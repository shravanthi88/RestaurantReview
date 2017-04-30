<div id="wrap">
    <div class="container">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#details" data-toggle="tab">Details</a></li>
            <li><a href="#survey" data-toggle="tab">Survey</a></li>
        </ul>
        <div class="tab-content clearfix">
            <div id="details" class="tab-pane active"><br/>
                <div class="panel-group">

                    <div class="panel panel-info">
                        <div class="panel-heading">Restaurant Details</div>
                        <div class = "panel-body">
                            <p><strong>Name: </strong>${restaurant.restaurantName} </p>
                            <p><strong>Current Company: </strong>${restaurant.location} </p>
                            <p><strong>Department: </strong>${restaurant.cuisineType} </p>
                        </div>
                    </div>

                    <div class="panel panel-info">
                        <div class="panel-heading">Reviews
                        </div>
                        <div class = "panel-body">
                            <div class="blog-comment">
                                <div class="post-comments">
                                    <ul class="comments">
                                        <g:each in="${restaurant.surveys}" var="survey" >
                                            <p class="meta">${survey.dateCreated}
                                            <g:link   controller="customer" action="show" id="${survey.customer.id}" resource="${customer}">
                                                ${survey.customer.username}
                                            </g:link>
                                            says:
                                            </p>
                                            <li class="clearfix">
                                                <div class="post-comments">
                                                    <p>${survey.review}</p>
                                                </div>
                                            </li>
                                        </g:each>
                                    </ul>
                                </div><!--post comments-->
                            </div>
                        </div>
                    </div>
                </div> <!-- panel group-->
            </div>

        <div id="survey" class="tab-pane">
            <div id="chart_div"></div>
        </div>
        </div>
    </div>
</div>

    </div>
</div>