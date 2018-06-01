var goToAddVenue = function (access_token, backHistory) {
    $.ajax({
        url: "addVenue",
        headers: {'Authorization': 'Bearer ' + access_token}
    }).done(function (data) {
        $('#main_content').html(data);
        var myLatlng = {lat: 50.447580, lng: 30.493993};

        var map = new google.maps.Map(document.getElementById('addVenueMap'), {
            zoom: 11,
            center: myLatlng
        });

        var marker;

        map.addListener('click', function(event) {
            if(!(marker == undefined)){
                marker.setMap(null);
            }
            marker = new google.maps.Marker({
                position: event.latLng,
                map: map,
                title: 'Your venue'
            })
        });

        $('#form_addVenue').on('submit', function (e) {
            e.preventDefault();
            e.stopPropagation();
            if(marker == undefined || $('#venue_name').val() == undefined){
                throw "Fill all values";
            }
            var venueLocation = marker.getPosition().toJSON();

            var ajaxRequest = $.ajax({
                url: '/degree/venue',
                type: "POST",
                data: JSON.stringify({
                    name:$('#venue_name').val(),
                    lat:venueLocation.lat,
                    lon:venueLocation.lng
                }),
                dataType: 'json',
                contentType: 'application/json',
                headers: {'Authorization': 'Bearer ' + access_token}
            }).done(function (response) {
                goToSpecificVenue(access_token, response.id);
            }).fail(function (data, status, errorThrown) {
                // error
            });

        });

        if(!backHistory){
            history.pushState({fName:'goToAddVenue', attributes:[access_token, true]}, null, null);
        }
    })
        .fail(function () {
            goToLoginForm();
        });
}