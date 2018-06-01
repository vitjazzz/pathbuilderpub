var goToUserAccount = function (access_token, backHistory) {
    $.ajax({
        url: "userAccount",
        headers: {'Authorization': 'Bearer ' + access_token}
    }).done(function (data) {
        $('#main_content').html(data);
        fillUserInfo(access_token);
        if(!backHistory){
            history.pushState({fName:'goToUserAccount', attributes:[access_token, true]}, null, null);
        }
    })
        .fail(function () {
            goToLoginForm();
        });
}

var fillUserInfo = function (access_token) {
    $.ajax({
        url: "/degree/user",
        headers: {'Authorization': 'Bearer ' + access_token}
    }).done(function (user) {
        $('#user-acc-username-label').append(user.firstName+' '+user.lastName);
        $('#user-acc-email-label').append(user.email);
        var roleFormatted = user.role.charAt(0) + user.role.slice(1).toLowerCase();
        $('#user-acc-role-label').append(roleFormatted);
    })
        .fail(function () {
            //goToLoginForm();
        });
}

