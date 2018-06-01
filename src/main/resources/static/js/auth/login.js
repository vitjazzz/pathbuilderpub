var goToLoginForm = function (backHistory) {
    $.ajax({
        url: "login"
    }).done(function (data) {
        $('#main_content').html(data);
        $('#navbar_list').empty();
        readyLoginForm();
        if(!backHistory)
            history.pushState({fName:'goToLoginForm', attributes:[true]}, null, null);
    })
}
var readyLoginForm = function () {
    var form_login = $('#form_login');

    var login_email = $('#login_email');
    var login_password = $('#login_password');
    var login_remember = $('#login_remember');

    $('#btn-singUp').click(function () {
        goToSignUpForm();
    })

    form_login.on('submit', function (e) {
        e.preventDefault();
        e.stopPropagation();

        var remember = login_remember.is(':checked') ? 1 : 0;

        logIn(login_email.val(), login_password.val())

    });
}

var logIn = function (login_email, login_password) {
    $.ajax({
        url: '/oauth/token?username=' + login_email + '&password=' + login_password + '&grant_type=password',
        type: "POST",
        data: {},
        headers: {
            "Authorization":"Basic Y2xpZW50OnNlY3JldA==",
            "Accept":"application/json"
        }
    }).done(function (response) {
        sessionStorage.setItem("username", login_email);
        sessionStorage.setItem("access_token", response.access_token);

        goToHomePage(response.access_token);
    }).fail(function (data, status, errorThrown) {
    });

}

