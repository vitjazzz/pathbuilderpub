var goToSpecificVenue = function (access_token, venueId, backHistory) {
    $.ajax({
        url: "singleVenue",
        headers: {'Authorization': 'Bearer ' + access_token}
    }).done(function (data) {
        $('#main_content').html(data);
        fillSingleVenue(access_token, venueId);
        if(!backHistory){
            history.pushState({fName:'goToSpecificVenue', attributes:[access_token, venueId, true]}, null, null);
        }

    })
        .fail(function () {
            goToLoginForm();
        });
}

var fillSingleVenue = function (access_token, venueId) {
    $.ajax({
        url: '/degree/venue/' + venueId,
        headers: {'Authorization': 'Bearer ' + access_token}
    }).done(function (venue) {
        $('#venue_title').text(venue.name);
        if(venue.photoURL){
            $('#single-venue-image').attr('src', venue.photoURL)
        }
        createStarRatingDiv($('#venueRating'), venue.rating, venue.name, venue.id);

        fillVenueEvents(access_token, 1, 3, venueId);

        fillVenueReviews(access_token, venueId);
    }).fail(function () {});
}

var fillVenueEvents = function (access_token, page, size, venueId) {
    $.ajax({
        url: '/degree/event?page='+page+'&size='+size+'&venueId='+venueId,
        headers: {'Authorization': 'Bearer ' + access_token}
    }).done(function (eventPage) {
        $('#venueEvents').empty();
        eventPage.content.forEach(function (item) {
            $('#venueEvents').append(convertEventToHtml(item));
        });
        if(eventPage.totalPages > 0){
            configureVenueEventsPagination(eventPage.totalPages, venueId);
        } else {
            $('#singleVenueContainer').addClass('no-events-value');
            $('#pagination-block').addClass('hidden');
        }

    })
}

var configureVenueEventsPagination = function (totalPages, venueId) {
    var visiblePages = totalPages < 3 ? totalPages : 3;
    $('#pagination-demo').twbsPagination({
        totalPages: totalPages,
        visiblePages: visiblePages,
        next: 'Next',
        prev: 'Prev',
        onPageClick: function (event, page) {
            fillVenueEvents(getAccessToken(), page, 9, venueId);
        }
    });
}

var fillVenueReviews = function (access_token, venueId) {
    $.ajax({
        url: '/degree/review/venue?venueId='+venueId,
        headers: {'Authorization': 'Bearer ' + access_token}
    }).done(function (reviews) {
        $('#venueReviews').empty();
        $('#venueReviews').append($('<h3/>')
            .text('Reviews:'));
        reviews.forEach(function (review) {
            $('#venueReviews').append(convertReviewToHtml(review));
        });
    })
}

var convertReviewToHtml = function (review) {
    var review_block = $('<div/>')
        .addClass('review-block');
    var div_cont = $('<div/>')
        .appendTo(review_block);
    var author_title = $('<h4/>')
        .addClass('author-name')
        .appendTo(div_cont);
    var review_text = $('<p/>')
        .appendTo(div_cont);

    author_title.append(review.username);
    review_text.append(review.review);

    return review_block;
}