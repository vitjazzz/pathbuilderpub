var readyNavbar = function (homeNavLinkId, venuesNavLinkId, eventsNavLinkId, userNavLinkId, logoutNavLinkId) {
    var access_token = sessionStorage.getItem("access_token");

    $('#'+homeNavLinkId).click(function() {
        goToHomePage(access_token);
    });

    $('#'+venuesNavLinkId).click(function() {
        goToVenues(access_token);
    });

    $('#'+eventsNavLinkId).click(function() {
        goToEvents(access_token);
    });

    $('#'+userNavLinkId).click(function() {
        goToUserAccount(access_token);
    });

    $('#'+logoutNavLinkId).click(function() {
        sessionStorage.setItem("access_token", "");
        goToLoginForm();
    });
}

