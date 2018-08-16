$(function () {
  
  let labels = [];

  switch (new Date().getDay()) {
    case 0:
      labels = buildWeek(moment().add(8, 'days').format('ll'))
      break;
    case 1:
      labels = buildWeek(moment().add(7, 'days').format('ll'))
      break;
    case 2:
      labels = buildWeek(moment().add(6, 'days').format('ll'))
      break;
    case 3:
      labels = buildWeek(moment().add(5, 'days').format('ll'))
      break;
    case 4:
      labels = buildWeek(moment().add(4, 'days').format('ll'))
      break;
    case 5:
      labels = buildWeek(moment().add(3, 'days').format('ll'))
      break;
    case 6:
      labels = buildWeek(moment().add(2, 'days').format('ll'))
      break;
  }

  function buildWeek(weekStart) {
    for (let i = 0; i < 5; i++) {
      labels.push(moment(weekStart).add(i, 'days').format('ll'));
    }
    return labels;
  }


  var firstShiftCount = [],
      secondShiftCount = [],
      etoCount = [],
      notFilledCount = [];

  var shiftDate = new Date(labels[0]);
  var weekDates = '{"fromDate": "' + shiftDate.getDate() + '-' + (shiftDate.getMonth()+1) + '-' + shiftDate.getFullYear() + '","toDate": "' + shiftDate.getDate() + '-' + (shiftDate.getMonth()+1) + '-' + shiftDate.getFullYear() + '"}';
    
  $.ajax({
    contentType: 'application/json',
    data: weekDates,
    dataType: 'json',
    async: false,
    success: function(data){
      console.log(data);
      if(data.success) {
        etoCount = data.etoCount;
        firstShiftCount = data.firstShiftCount;
        secondShiftCount = data.secondShiftCount;
        notFilledCount = data.notFilledCount;
      }
    },
    error: function(){
      console.log('shift count failed');
    },
    type: 'POST',
    url: 'http://w0gl9912:8081/mcrcdashboard/food/getWeeklyShiftCount'
  });

  console.log(`ETO : ${etoCount}, First : ${firstShiftCount}, Second : ${secondShiftCount}`);

  var eventDomTarget = document.getElementById('eventChart').getContext('2d');
  var eventConfig = {
    "type": "line",
    "data": {
      "labels": labels,
      "datasets": [{
        "label": "First Shift",
        // "data": [65, 59, 80, 81, 56],
        "data": firstShiftCount,
        "fill": false,
        "borderColor": "rgb(32, 150, 186)",
        backgroundColor: "rgb(32, 150, 186)",
        "lineTension": 0,
        pointRadius: 10,
        pointHoverRadius: 15
      }, {
        "label": "Second Shift",
        // "data": [80, 40, 90, 151, 58],
        "data": secondShiftCount,
        "fill": false,
        "borderColor": "rgb(223, 110, 33)",
        backgroundColor: "rgb(223, 110, 33)",
        "lineTension": 0,
        pointRadius: 10,
        pointHoverRadius: 15
      }, {
        "label": "ETO",
        // "data": [4, 6, 10, 2, 6],
        "data": etoCount,
        "fill": false,
        "borderColor": "rgb(197, 145, 157)",
        backgroundColor: "rgb(197, 145, 157)",
        "lineTension": 0,
        borderDash: [5, 5],
        pointRadius: 10,
        pointHoverRadius: 15
      }]
    },
    "options": {
      maintainAspectRatio: true
    }
  };

  var statusDomTarget = document.getElementById('statusChart').getContext('2d');
  var statusConfig = {
    "type": "bar",
    "data": {
      "labels": labels,
      "datasets": [{
        "label": "Status",
        // "data": [65, 59, 80, 81, 56],
        "data": notFilledCount,
        "fill": false,
        "backgroundColor": ["rgba(255, 99, 132, 0.2)", "rgba(255, 159, 64, 0.2)", "rgba(255, 205, 86, 0.2)", "rgba(75, 192, 192, 0.2)", "rgba(54, 162, 235, 0.2)", "rgba(153, 102, 255, 0.2)", "rgba(201, 203, 207, 0.2)"], "borderColor": ["rgb(255, 99, 132)", "rgb(255, 159, 64)", "rgb(255, 205, 86)", "rgb(75, 192, 192)", "rgb(54, 162, 235)", "rgb(153, 102, 255)", "rgb(201, 203, 207)"],
        "borderWidth": 1
      }]
    },
    "options": {
      "scales": {
        "yAxes": [{
          "ticks": {
            "beginAtZero": true
          }
        }],
        xAxes: [{
          gridLines: {
            display: false
          }
        }]
      }
    }
  }

  var chart = new Chart(eventDomTarget, eventConfig);
  var chart = new Chart(statusDomTarget, statusConfig);


  var currentYear = new Date().getFullYear();
  var minDateVal = new Date(currentYear, 5, 1);
  var maxDateVal = new Date(currentYear, 11, 31);

  var calendarDataSrc = [];
  var userEmail, userPassword;

  
  checkUserAuth();
  
  // initialises & configures calender app
  function calendarInit() {
	  calendarDataSrc = [];
	// uncomment when connected to server
	  $.ajax({
			url: 'http://w0gl9912:8081/mcrcdashboard/food/getShiftDetailsforEmp/220',
			method: 'GET',
			async: false,
			success: function(data){
			  var i;
			  for (i=0; i<data.result.length; i++){ 
				  var obj = data.result[i]
				  var mydate = obj.startDate;
				  var splt = mydate.split("-");
				  data.result[i].startDate = new Date(splt[2],splt[1]-1,splt[0]);
				  data.result[i].endDate = new Date(splt[2],splt[1]-1,splt[0]);
				  calendarDataSrc.push(data.result[i]);
			  }
			}, 
			error: function(data){
			  console.dir(data);
			} 
    });
	  
    $('#calendar').calendar({
      displayWeekNumber: true,
      minDate: new Date(currentYear, 0, 01),
      maxDate: maxDateVal,
      enableRangeSelection: false,
      allowOverlap: false,
      enableContextMenu: false,
      mouseOnDay: function(e) {
        if(e.events.length > 0 && e.events[0].status != 'deleted') {
          var content = '';
          
          for(var i in e.events) {
              content += '<div class="event-tooltip-content">'
                      + '<div class="event-name" style="color:' + e.events[i].color + '">' + e.events[i].name + '</div>'                            
                      + '</div>'; 
          }
          
          $(e.element).popover({ 
              trigger: 'manual',
              container: 'body',
              html:true,
              content: content
          });
          
          $(e.element).popover('show');
        }
      },
      mouseOutDay: function(e) {
          if(e.events.length > 0) {
              $(e.element).popover('hide');
          }
      },
      dataSource: calendarDataSrc,
      clickDay: function (e) {

        console.log('events count : ', e.events.length);

        if(e.events.length > 0) {
          editEvent(e.events);
        } else {
          var clickedDate = new Date(e.date);
          var selectedDate = ("0" + clickedDate.getDate()).slice(-2);
          var selectedMonth = ("0" + (clickedDate.getMonth() + 1)).slice(-2);
          var selectedYear = clickedDate.getFullYear();
          clickedDate = selectedYear + '-' + selectedMonth + '-' + selectedDate;
    
          $('#event-modal input[name="event-date"]').val(clickedDate);
    
          $('#event-modal').modal('show');
        }
      },
      disabledDays: disableWeekends(2018)
    });

    $('#mealError').removeClass('d-block').addClass('d-none');
  }
  
  // triggers save - add ajax to send data to backend
  $('#save-event').click(function() {
    saveEvent();
  });

  // triggers delete - add ajax to update data to backend
  $('#delete-event').click(function() {
    deleteEvent();
  });


  function editEvent(event) {
    
    for(var e in event) {
      var selectCheckbox = event[e] ? event[e].name : '';  
      $('#event-modal input[name="event-index"]').val(event[e] ? event[e].id : '');
      
      // console.log(selectCheckbox);
      $('#event-modal input[name="shiftOption"]#option' + selectCheckbox).prop('checked', true);

      var eventDate = new Date(event[e].startDate);
      var selectedDate = ("0" + eventDate.getDate()).slice(-2);
      var selectedMonth = ("0" + (eventDate.getMonth() + 1)).slice(-2);
      var selectedYear = eventDate.getFullYear();
      eventDate = selectedYear + '-' + selectedMonth + '-' + selectedDate;
      $('#event-modal input[name="event-date"]').val(eventDate);
    }    

    $('#event-modal').modal();
  }

  function saveEvent() {

    var dataSource = $('#calendar').data('calendar').getDataSource();

    // console.log('before filter dataSource : ', dataSource);

    var eventDate = new Date($('#event-modal input[name="event-date"]').val());
    var compDate = new Date(eventDate.getFullYear(), eventDate.getMonth(), eventDate.getDate())
    
    /* dataSource = dataSource.filter(function(el) {      
      return JSON.stringify(el.startDate) !== JSON.stringify(compDate);
    }); */

    // console.log('after filter dataSource : ', dataSource);

  console.log('before each ', dataSource);

    $('input[name="shiftOption"]').each(function () {
      
      var eventName = $(this).val();

      if($(this).prop('checked')) {

        console.log('inside checked');
        
        console.log(`eventDate : ${eventDate}, 
        eventname : ${eventName}`);

        

        let recordPresent = false;
        for(var i in dataSource) { 
          if(JSON.stringify(dataSource[i].startDate) == JSON.stringify(compDate) && eventName == dataSource[i].name) {
            // dataSource[i].status = 'updated';
            // dataSource[i].name = eventName;

            console.log('in if ', dataSource[i].startDate);

            recordPresent = true;
          }
        }
      
        if(!recordPresent) {
          var newId = 0;
          for (var i in dataSource) {
            if (dataSource[i].id > newId) {
              newId = dataSource[i].id;
            }
          }

          newId++;
          var event = {
        	        id: newId,
        	        recordId: 0,
        	        name: eventName,
        	        color: findColor(eventName),
        	        startDate: eventDate.getDate() + "-" + (eventDate.getMonth()+1)  + "-" +  eventDate.getFullYear(),
        	        endDate: eventDate.getDate() + "-" + (eventDate.getMonth()+1)  + "-" +  eventDate.getFullYear(),
        	        status: 'new'
        	      }

          dataSource.push(event);
        }        

        console.log('saved dataSource : ', dataSource);
        
      } else {
        for(var i in dataSource) { 
          if(JSON.stringify(dataSource[i].startDate) == JSON.stringify(compDate) && eventName == dataSource[i].name) {
	    	  
        	  var dateObj = dataSource[i].startDate;
        	  dataSource[i].startDate = dateObj.getDate() + "-" + (dateObj.getMonth()+1)  + "-" +  dateObj.getFullYear();
        	  dataSource[i].endDate = dateObj.getDate() + "-" + (dateObj.getMonth()+1)  + "-" +  dateObj.getFullYear();
              
	          dataSource[i].status = 'deleted';
          }
        }
      }

    });

    $('#calendar').data('calendar').setDataSource(dataSource);

    $('#event-modal').modal('hide');
    
    var myJson = $('#calendar').data('calendar').getDataSource();
    
    for(var i in myJson) { 
        if(myJson[i].status == 'updated') {
          var dateObj = myJson[i].startDate;
          myJson[i].startDate = dateObj.getDate() + "-" + (dateObj.getMonth()+1)  + "-" +  dateObj.getFullYear();
          myJson[i].endDate = dateObj.getDate() + "-" + (dateObj.getMonth()+1)  + "-" +  dateObj.getFullYear();
        }
      }

    // uncomment when connected to server
    $.ajax({
      url: 'http://w0gl9912:8081/mcrcdashboard/food/saveDetails',
      method: 'POST',
      dataType: 'json',
      data: JSON.stringify(myJson),
      async: false,
      contentType: "application/json; charset=utf-8",
      success: function(data){
        console.log('succes: '+ data);
      }, 
      error: function(data){
        console.dir(data);
      } 
    });

    calendarInit();

  }
  
  function findColor(eventName) {
    var color;
    switch(eventName) {
      case 'First':
          color = '#2096BA';
          break;
      case 'Second':
          color = '#DF6E21';
          break;
      case 'ETO':
          color = '#C5919D';
          break;
    }

    return color;
  }

  function disableWeekends(year) { 
    var offdays=new Array();
    i=0;
    for(month=0;month<12;month++) { 
      tdays=new Date(year, month, 0).getDate(); 
      for(date=1;date<=tdays;date++)     {
        smonth=(month<10)?"0"+(month):(month);
        sdate=(date<10)?"0"+date:date;
        dd=year+"-"+smonth+"-"+sdate;
        var day=new Date(year,month,date);
        if(day.getDay() == 0 || day.getDay() == 6) {
          offdays.push(new Date(year,smonth,sdate));
        }
      }
    }
    // console.log(offdays);
    return offdays;
  }

  $( "#loginForm" ).submit(function( event ) {
    event.preventDefault();
  });

  $('#loginBtn').click(function() {
    console.log('inside login click');
    

    userEmail = $('#loginForm #userEmail').val();
    userPassword = $('#loginForm #userPassword').val();

    handleLogin();
  })

  function handleLogin() {
    console.log('handleLogin called')
    let userDetails = '{"email":"' + userEmail + '","password":"' + userPassword + '"}';

    $.ajax({
      contentType: 'application/json',
      data: userDetails,
      dataType: 'json',
      success: function(data){
          console.log("Login success", data.response);
          if(data.response != 'failure') {
            console.log('in if');

            if(!checkCookie('authStatus')) {
              setCookie('authStatus', true, 15);
            }

            calendarInit();
            
            $('.navbar-nav > .li-login').toggleClass('d-block d-none');
            $('.navbar-nav > .li-logout').toggleClass('d-block d-none');

            $('#userName').text(data.employee.name);

            $('#loginModal').modal('hide');
          }
      },
      error: function(){
          app.log("Login failed");
      },
      type: 'POST',
      url: 'http://w0gl9912:8081/mcrcdashboard/mcrc/login'
    });

    return true;
  }  

  $('#logoutBtn').click(function(e) {

    e.preventDefault();

    console.log('inside logout click');
    
    if(checkCookie('authStatus') && getCookie('authStatus')) {
      console.log('in remove cookie');
      setCookie('authStatus', false, 0);
      location.reload();
    }
  })

  // clear checbox & date picker when modal is closed
  $('#event-modal').on('hidden.bs.modal', function () {
    $('#event-modal input[name="shiftOption"]').removeAttr('disabled');
    $('#event-modal input[name="shiftOption"]').prop('checked', false);
    $('#event-modal input[name="event-date"]').val('');    
  })

  // toggles checbox activity for ETO/Shifts
  $('#event-modal .checkbox input[name="shiftOption"]').change(function(){
    console.log($(this).val());
    console.log($(this).prop('checked'));

    if($(this).prop('checked') && $(this).val() === 'ETO') {
      $('#event-modal .checkbox input[name="shiftOption"]').prop('checked', false);
      $('#event-modal .checkbox input[name="shiftOption"]').attr('disabled', true);
      $(this).removeAttr('disabled');
      $(this).prop('checked', true);
    } else if ($(this).prop('checked') && $(this).val() === 'First' || $(this).val() === 'Second') {
      $('#event-modal .checkbox input[name="shiftOption"]#optionETO').prop('checked', false);
      $('#event-modal .checkbox input[name="shiftOption"]#optionETO').attr('disabled', true);
    } else {
      $('#event-modal .checkbox input[name="shiftOption"]').removeAttr('disabled');
    }
  });

  $('.page-route .nav-link').click(function(){
    $('.page-route .nav-link').removeClass('selected');
    $(this).addClass('selected');

    $('main > div[class*="-container"]').removeClass('d-block').addClass('d-none');
    let containerTarget = $(this).data('target');
    $('main > div.' + containerTarget).removeClass('d-none').addClass('d-block');
  })


  function checkUserAuth() {
    if(checkCookie('authStatus') && getCookie('authStatus')) {
      console.log('logged in');

      calendarInit();

      $('.navbar-nav > .li-login').removeClass('d-block').addClass('d-none');
      $('.navbar-nav > .li-logout').removeClass('d-none').addClass('d-block');
    } else {
      console.log('logged out');

      $('.navbar-nav > .li-login').removeClass('d-none').addClass('d-block');
      $('.navbar-nav > .li-logout').removeClass('d-block ').addClass('d-none');
    }
  }



  function setCookie(cName, cValue, exMins) {
    var d = new Date();
    d.setTime(d.getTime() + (exMins * 60 * 1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cName + "=" + cValue + ";" + expires + ";path=/";
  }

  function getCookie(cname) {
      var name = cname + "=";
      var ca = document.cookie.split(';');
      for(var i = 0; i < ca.length; i++) {
          var c = ca[i];
          while (c.charAt(0) == ' ') {
              c = c.substring(1);
          }
          if (c.indexOf(name) == 0) {
              return c.substring(name.length, c.length);
          }
      }
      return "";
  }

  function checkCookie(cName) {
      let cookieExists = false;
      var cValue = getCookie(cName);
      if (cValue != "") {
        console.log("Cookie present" + cValue);
        cookieExists = true
      }

      return cookieExists;
  }
  
});