$(document).ready(function () {
    $('#calendar').fullCalendar({
        theme: false,
        editable: false,
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },
        defaultView: 'month',
        firstDay: 1,
        events: {
            url: '/calendar/dates',
            type: 'GET',


            error: function () {
                alert('there was an error while fetching events!');
            },
        }
    });

});

