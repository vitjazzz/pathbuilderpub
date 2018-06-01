var goToVenues = function (access_token, backHistory) {
    $.ajax({
        url: "venues",
        headers: {'Authorization': 'Bearer ' + access_token}
    }).done(function (data) {
        $('#main_content').html(data);
        fillVenues(access_token, 1, 9);
        $('#list').click(function (event) {
            event.preventDefault();
            activateList();
        });
        $('#grid').click(function (event) {
            event.preventDefault();
            activateGrid();
        });
        if (!backHistory) {
            history.pushState({fName: 'goToVenues', attributes: [access_token, true]}, null, null);
        }
    })
        .fail(function () {
            goToLoginForm();
        });
}

var fillVenues = function (access_token, page, size) {
    $.ajax({
        url: '/degree/venue?page=' + page + '&size=' + size,
        headers: {'Authorization': 'Bearer ' + access_token}
    }).done(function (data) {
        $('#products').empty();
        data.content.forEach(function (item, index, array) {
            $('#products').append(convertVenueToHtml(item));
        });
        configureVenuesPagination(data.totalPages);
        configureVenueCreation(access_token);
        if ($('#products').hasClass('list-group')) {
            activateList();
        }
    })
        .fail(function () {
        });
}
var configureVenueCreation = function (access_token) {
    $.ajax({
        url: '/degree/user',
        headers: {'Authorization': 'Bearer ' + access_token}
    }).done(function (user) {
        console.log(user);
        if(user.role == 'MANAGER' || user.role == 'ADMIN'){
            $('#addVenue').removeClass('hidden');
            $('#addVenue').click(function (event) {
                event.preventDefault();
                goToAddVenue(access_token);
            });
        }
    })
        .fail(function () {
        });
}
var configureVenuesPagination = function (totalPages) {
    var visiblePages = totalPages < 6 ? totalPages : 6;
    $('#pagination-demo').twbsPagination({
        totalPages: totalPages,
        visiblePages: visiblePages,
        next: 'Next',
        prev: 'Prev',
        onPageClick: function (event, page) {
            fillVenues(getAccessToken(), page, 9);
            //fetch content and render here
        }
    });
}
var convertVenueToHtml = function (venue) {
    var div_item = $('<div/>')
        .addClass('item  col-xs-4 col-lg-4');
    var card = $('<div/>')
        .addClass('card list-item-hover')
        .appendTo(div_item);

    var img = $('<img/>')
        .addClass('group list-group-image')
        .attr('src', venue.photoURL ? venue.photoURL : 'http://placehold.it/400x250/000/fff')
        .appendTo(card);
    var caption = $('<div/>')
        .addClass('caption')
        .appendTo(card);

    var title = $('<h4/>')
        .addClass('group inner list-group-item-heading')
        .appendTo(caption);
    var description = $('<p/>')
        .addClass('group inner list-group-item-text')
        .text('Product description... Lorem ipsum dolor sit amet, consectetuer adipiscing elit,' +
            'sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.')
        .appendTo(caption);
    var row = $('<div/>')
        .addClass('row')
        .appendTo(caption);

    var ratingBlock = $('<div/>')
        .addClass('col-xs-12 col-md-6')
        .appendTo(row);
    var viewVenueBlock = $('<div/>')
        .addClass('col-xs-12 col-md-6 view-venue')
        .appendTo(row);

    var ratingTop = $('<div/>')
        .addClass('lead')
        .text('Rating')
        .appendTo(ratingBlock);
    createStarRatingDiv(ratingTop, venue.rating, venue.name, venue.id);

    var viewVenueButton = $('<a/>')
        .addClass('btn btn-success')
        .attr('venueId', venue.id)
        .text('View venue')
        .appendTo(viewVenueBlock);
    viewVenueButton.click(function (event) {
        event.preventDefault();
        var venueId = event.target.attributes[1];
        goToSpecificVenue(getAccessToken(), venueId.value);
    });


    title.append(venue.name);

    return div_item;
}
var createStarRatingDiv = function (parentEl, rating, name, id) {
    var ratingDiv = $('<div/>')
        .addClass('star-rating')
        .appendTo(parentEl);
    $('<span/>')
        .addClass('fa fa-star-o')
        .attr('data-rating', '1')
        .appendTo(ratingDiv);
    $('<span/>')
        .addClass('fa fa-star-o')
        .attr('data-rating', '2')
        .appendTo(ratingDiv);
    $('<span/>')
        .addClass('fa fa-star-o')
        .attr('data-rating', '3')
        .appendTo(ratingDiv);
    $('<span/>')
        .addClass('fa fa-star-o')
        .attr('data-rating', '4')
        .appendTo(ratingDiv);
    $('<span/>')
        .addClass('fa fa-star-o')
        .attr('data-rating', '5')
        .appendTo(ratingDiv);
    rating = rating < 0 ?
        0 : rating;
    $('<input/>')
        .addClass('rating-value')
        .attr('name', name)
        .attr('value', Math.round(rating))
        .attr('type','hidden')
        .appendTo(ratingDiv);

    var $star_rating = ratingDiv.find( ".fa" )
    var SetRatingStar = function() {
        return $star_rating.each(function() {
            if (parseInt($star_rating.siblings('input.rating-value').val()) >= parseInt($(this).data('rating'))) {
                return $(this).removeClass('fa-star-o').addClass('fa-star');
            } else {
                return $(this).removeClass('fa-star').addClass('fa-star-o');
            }
        });
    };

    $star_rating.on('click', function() {
        $star_rating.siblings('input.rating-value').val($(this).data('rating'));
        rateVenue($(this).data('rating'), id)
            .done(function () {}).fail(function () {});
        return SetRatingStar();
    });
    SetRatingStar();
}

var rateVenue = function ( rating, venueId) {
    return $.ajax({
        url: '/degree/rating',
        type: "POST",
        data: JSON.stringify({
            venueId:venueId,
            rating:rating
        }),
        dataType: 'json',
        contentType: 'application/json',
        headers: {'Authorization': 'Bearer ' + getAccessToken()}
    });
}

var activateList = function () {
    $('#products .item, #products .item .card').addClass('list-group-item');
    $('#products').addClass('list-group');
}

var activateGrid = function () {
    $('#products .item, #products .item .card').removeClass('list-group-item');
    $('#products .item').addClass('grid-group-item');
    $('#products').removeClass('list-group');
}