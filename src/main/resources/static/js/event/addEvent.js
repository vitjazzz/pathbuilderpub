var goToAddEvent = function (access_token, backHistory) {
    $.ajax({
        url: "addEvent",
        headers: {'Authorization': 'Bearer ' + access_token}
    }).done(function (data) {
        $('#main_content').html(data);

        $('#form_addEvent').on('submit', function (e) {
            e.preventDefault();
            e.stopPropagation();
            var event_name = $('#event_name');
            var venue_name = $('#event_venue');
            var event_start_date = $('#event_start_date');
            var event_end_date = $('#event_end_date');
            console.log(event_name.val()+' '+venue_name.val()+' '+event_start_date.val()+' '+event_end_date.val());
            if( !(event_name.val() && venue_name.val()
                && event_start_date.val() && event_end_date.val()) ){
                throw "Fill all values";
            }

            var startDate = moment(event_start_date.val()).toDate();
            var endDate = moment(event_end_date.val()).toDate();
            $.ajax({
                url: '/degree/event',
                type: "POST",
                data: JSON.stringify({
                    name:event_name.val(),
                    description:"",
                    venueName:venue_name.val(),
                    startDate:startDate,
                    endDate:endDate,
                }),
                dataType: 'json',
                contentType: 'application/json',
                headers: {'Authorization': 'Bearer ' + access_token}
            }).done(function (event) {
                goToEvents(access_token);
            }).fail(function () {
                // error
            });

        });

        if(!backHistory){
            history.pushState({fName:'goToAddEvent', attributes:[access_token, true]}, null, null);
        }
    })
        .fail(function () {
            goToLoginForm();
        });
}