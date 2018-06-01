var goToSignUpForm = function (backHistory) {
    $.ajax({
        url: "signUp"
    }).done(function (data) {
        $('#main_content').html(data);
        $('#navbar_list').empty();
        readySingUpForm();
        if(!backHistory)
            history.pushState({fName:'goToSignUpForm', attributes:[true]}, null, null);
    })
}
var readySingUpForm = function () {
    var form_signUp = $('#form_signUp');

    var signUp_email = $('#signUp_email');
    var signUp_password = $('#signUp_password');
    var signUp_firstName = $('#signUp_firstName');
    var signUp_lastName = $('#signUp_lastName');

    $('#btn-login').click(function () {
        goToLoginForm();
    })

    form_signUp.on('submit', function (e) {
        e.preventDefault();
        e.stopPropagation();
        $.ajax({
            url: '/degree/user',
            type: "POST",
            data: JSON.stringify({
                email:signUp_email.val(),
                password:signUp_password.val(),
                firstName:signUp_firstName.val(),
                lastName:signUp_lastName.val()
            }),
            dataType: 'json',
            contentType: 'application/json'
        }).done(function (response) {
            logIn(signUp_email.val(), signUp_password.val());
        }).fail(function (data, status, errorThrown) {
        });
    });
}