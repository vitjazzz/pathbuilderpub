'use strict'
$(".login-logo,.form-container").removeClass("off-canvas");

$(document).ready(function () {
    history.pushState("", null, null);
    window.onpopstate = function (event) {
        console.log(event.state);
        var f = window[event.state.fName];
        console.log(f);
        f.apply(this, event.state.attributes);
        // Handle the back (or forward) buttons here
        // Will NOT handle refresh, use onbeforeunload for this.
    };
    if (getAccessToken()) {
        goToHomePage(getAccessToken());
    } else {
        goToLoginForm();
    }

});

var getAccessToken = function () {
    return sessionStorage.getItem("access_token");
}