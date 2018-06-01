function formatDate(date) {
    var monthNames = [
        "January", "February", "March",
        "April", "May", "June", "July",
        "August", "September", "October",
        "November", "December"
    ];

    var hour = date.getHours();
    var day = date.getDate();
    var monthIndex = date.getMonth();
    var year = date.getFullYear();

    return hour + ':00 ' + day + ' ' + monthNames[monthIndex] + ' ' + year;
}