var goToEvents = function (access_token, backHistory) {
    $.ajax({
        url: "events",
        headers: {'Authorization': 'Bearer ' + access_token}
    }).done(function (data) {
        $('#main_content').html(data);
        configureEventCreation(access_token);
        fillEvents(access_token, 1, 9);
        $('#list').click(function (event) {
            event.preventDefault();
            activateList();
        });
        $('#grid').click(function (event) {
            event.preventDefault();
            activateGrid();
        });
        if(!backHistory){
            history.pushState({fName:'goToEvents', attributes:[access_token, true]}, null, null);
        }
    })
        .fail(function () {
            goToLoginForm();
        });
}

var fillEvents = function (access_token, page, size) {
    $.ajax({
        url: '/degree/event?page='+page+'&size='+size+'&venueId=',
        headers: {'Authorization': 'Bearer ' + access_token}
    }).done(function (data) {
        $('#products').empty();
        data.content.forEach(function(item, index, array) {
            $('#products').append( convertEventToHtml(item));
        });
        configureEventsPagination(data.totalPages);

        if($('#products').hasClass('list-group')){
            activateList();
        }
    })
        .fail(function () {
        });
}
var configureEventsPagination = function (totalPages) {
    var visiblePages = totalPages < 6 ? totalPages : 6;
    $('#pagination-demo').twbsPagination({
        totalPages: totalPages,
        visiblePages: visiblePages,
        next: 'Next',
        prev: 'Prev',
        onPageClick: function (event, page) {
            fillEvents(getAccessToken(), page, 9);
            //fetch content and render here
        }
    });
}
var convertEventToHtml = function (event) {
    var div_item = $('<div/>')
        .addClass('item  col-xs-4 col-lg-4');
    var card = $('<div/>')
        .addClass('card')
        .appendTo(div_item);

    var img = $('<img/>')
        .addClass('group list-group-image')
        .attr('src','http://placehold.it/400x250/000/fff')
        .appendTo(card);
    var caption = $('<div/>')
        .addClass('caption')
        .appendTo(card);

    var title = $('<h4/>')
        .addClass('group inner list-group-item-heading')
        .appendTo(caption);
    var venue = $('<h6/>')
        .addClass('group inner list-group-item-heading')
        .text('Venue: ')
        .appendTo(caption);
    var description = $('<p/>')
        .addClass('group inner list-group-item-text')
        .text('Product description... Lorem ipsum dolor sit amet, consectetuer adipiscing elit,'+
    'sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.')
        .appendTo(caption);
    var row = $('<div/>')
        .addClass('row')
        .appendTo(caption);

    var startDateBlock = $('<div/>')
        .addClass('lead date')
        .text('Starts: ')
        .appendTo(row);

    var endDateBlock = $('<div/>')
        .addClass('lead date')
        .text('Ends: ')
        .appendTo(row);

    var start = formatDate(new Date(event.startDate));
    var end = formatDate(new Date(event.endDate));

    title.append(event.name);
    venue.append(event.venue.name);
    startDateBlock.append(start);
    endDateBlock.append(end);

    return div_item;
}
var configureEventCreation = function (access_token) {
    $.ajax({
        url: '/degree/user',
        headers: {'Authorization': 'Bearer ' + access_token}
    }).done(function (user) {
        console.log(user);
        if(user.role == 'MANAGER' || user.role == 'ADMIN'){
            $('#addEvent').removeClass('hidden');
            $('#addEvent').click(function (event) {
                event.preventDefault();
                goToAddEvent(access_token);
            });
        }
    })
        .fail(function () {
        });
}