var goToHomePage = function (access_token, backHistory) {
    $.ajax({
        url: "home",
        headers: {'Authorization': 'Bearer ' + access_token}
    }).done(function (data) {
        $('#main_content').html(data);


        $('#navbar_list').empty();
        $('#navbar_list').append("<li class='nav-item active'><button id='homeNavLink' type='button' class='link'>Home</button></li>");
        $('#navbar_list').append("<li class='nav-item'><button id='venuesNavLink' type='button' class='link'>Venues</button></li>");
        $('#navbar_list').append("<li class='nav-item'><button id='eventsNavLink' type='button' class='link'>Events</button></li>");
        $('#navbar_list').append("<li class='nav-item'><button id='userNavLink' type='button' class='link'>My Account</button></li>");
        $('#navbar_list').append("<li class='nav-item'><button id='logoutNavLink' type='button' class='link'>Logout</button></li>");
        readyNavbar("homeNavLink", "venuesNavLink", "eventsNavLink", "userNavLink", 'logoutNavLink');

        var directionsService = new google.maps.DirectionsService;
        var directionsDisplay = new google.maps.DirectionsRenderer;

        var myLatlng = {lat: 50.447580, lng: 30.493993};
        var map = new google.maps.Map(document.getElementById('homeMap'), {
            zoom: 11,
            center: myLatlng
        });
        directionsDisplay.setMap(map);
        var mapCircle = configureHomeMapCircle(map, myLatlng);
        var markers = [];
        buildPathForm(access_token, directionsService, directionsDisplay, mapCircle, map, markers);

        if (!backHistory) {
            history.pushState({fName: 'goToHomePage', attributes: [access_token, true]}, null, null);
        }
    })
        .fail(function () {
            goToLoginForm();
        });
}
function addMarker(location, map, markers) {
    var marker = new google.maps.Marker({
        position: location,
        map: map
    });
    markers.push(marker);
}
function setMapOnAll(map, markers) {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
    }
}
function deleteMarkers(markers) {

}
var getMapCircle = function (map, center, radius) {
    return new google.maps.Circle({
        strokeColor: '#bfc4cc',
        strokeOpacity: 0.8,
        strokeWeight: 2,
        fillColor: '#bfc4cc',
        fillOpacity: 0.35,
        map: map,
        center: center,
        radius: radius * 1000
    });
}

var configureHomeMapCircle = function (map, myLatlng) {
    var mapCircle = getMapCircle(map, myLatlng, $('#radius').val());
    mapCircle.addListener('click', function (event) {
        mapCircle.setRadius($('#radius').val() * 1000);
        mapCircle.setCenter(event.latLng);
    });
    map.addListener('click', function (event) {
        mapCircle.setRadius($('#radius').val() * 1000);
        mapCircle.setCenter(event.latLng);
    });
    return mapCircle;
}

var buildPathForm = function (access_token, directionsService, directionsDisplay, mapCircle, map, markers) {
    $('#form_buildPath').on('submit', function (e) {
        e.preventDefault();
        e.stopPropagation();

        var venues_amount = $('#venues_amount');
        var mapCircleCenter = mapCircle.getCenter().toJSON();
        if (!(venues_amount.val())) {
            throw "Fill all values";
        }
        $.ajax({
            url: '/degree/venue/path',
            type: 'POST',
            data: JSON.stringify(
                {
                    radius: mapCircle.getRadius() / 1000.0,
                    lat: mapCircleCenter.lat,
                    lon: mapCircleCenter.lng,
                    venuesAmount: venues_amount.val()
                }
            ),
            dataType: 'json',
            contentType: 'application/json',
            headers: {'Authorization': 'Bearer ' + access_token}
        }).done(function (venues) {
            var waypts = [];
            var startLocation = {lat: venues[0].lat, lng: venues[0].lon};
            var lastElIndex = venues.length - 1;
            var endLocation = {lat: venues[lastElIndex].lat, lng: venues[lastElIndex].lon};
            for(var i = 1; i < lastElIndex; i++) {
                var venueLatLng = {lat: venues[i].lat, lng: venues[i].lon};
                waypts.push({
                    location: venueLatLng,
                    stopover: true
                });
            }
            directionsService.route({
                origin: startLocation,
                destination: endLocation,
                waypoints: waypts,
                travelMode: 'WALKING'
            }, function(response, status) {
                if (status === 'OK') {
                    directionsDisplay.setDirections(response);
                    var route = response.routes[0];
                } else {
                    window.alert('Directions request failed due to ' + status);
                }
            });
            $('#result_venues').empty();
            venues.forEach(function (item) {
                $('#result_venues').append(convertVenueToHtml(item));
                activateHomeVenueList();
            });

        }).fail(function (data, status, errorThrown) {
            // error
        });
    });
}

var activateHomeVenueList = function () {
    $('#result_venues .item, #result_venues .item .card').addClass('list-group-item');
    $('#result_venues').addClass('list-group');
}

var loadRestaurantsInCircle = function (mapCircle, map) {
    var mapCircleCenter = mapCircle.getCenter().toJSON();
    var service = new google.maps.places.PlacesService(map);
    service.nearbySearch({
        location: {lat: mapCircleCenter.lat, lng: mapCircleCenter.lng},
        radius: 500,
        type: ['restaurant']
    }, callback);

    function callback(results, status) {
        if (status === google.maps.places.PlacesServiceStatus.OK) {
            for (var i = 0; i < results.length; i++) {
                var photoUrl = '';
                if(typeof results[i].photos !== 'undefined' && results[i].photos.length > 0) {
                    photoUrl = results[i].photos[0].getUrl({'maxWidth': 400, 'maxHeight': 250});

                }
                $.ajax({
                    url: '/degree/venue',
                    type: "POST",
                    data: JSON.stringify({
                        name:results[i].name,
                        lat:results[i].geometry.location.lat(),
                        lon:results[i].geometry.location.lng(),
                        photoURL:photoUrl
                    }),
                    dataType: 'json',
                    contentType: 'application/json',
                    headers: {'Authorization': 'Bearer ' + access_token}
                }).done(function (response) {
                }).fail(function (data, status, errorThrown) {
                });
            }
        }
    }
}