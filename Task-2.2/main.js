var app_key = 'ojtyzbpz7peoc2v';
var app_secret = 'm8kdx1fyrghmj5d';
var access_token = null;

$(document).ready(function() {
if (!getUrlParameter('code')) {
  $('#data').text('Ready to login.');
} else {
  $('#data').text('Ready to authorize.');
}
});

function list() {
  var request = $.ajax({
      type: 'POST',
      url: 'https://api.dropboxapi.com/2/files/list_folder',
      contentType: 'application/json',
      data: JSON.stringify({
        "path": "",
        "recursive": false,
        "include_media_info": false,
        "include_deleted": false,
        "include_has_explicit_shared_members": false
      }),
      success: function(data, status) {
          var files = 'Total of ' + data.entries.length + ' files. ';
          for (var i = 0; i< data.entries.length; i++) {
            files += data.entries[i].name + ', ';
          }

          $('#data').text(files);
          console.log(access_token);
          console.log(data, status);
      },
      error: function(data, status) {
          $('#data').text(data.responseText); 
          console.error(data.responseText);
      }
    });
}

function upload() {
  var now = new Date().getTime().toString();
  var filename = 'newfile'+ now+'.txt';
  var request = $.ajax({
      type: 'POST',
      url: 'https://content.dropboxapi.com/1/files_put/auto/' + filename + '?param=val',
      success: function(data, status) {
          $('#data').text(filename+' uploaded!');
          console.log(access_token);
          console.log(data, status);
      },
      error: function(data, status) {
          $('#data').text(data.responseText); 
          console.error(data.responseText);
      }
    });
}

function info() {
  var req = $.ajax({
    url: 'https://api.dropboxapi.com/2/users/get_current_account',
    type: 'POST',
    success: function(data, status) {
        console.log(data, status);
        $('#data').text(JSON.stringify(data));
    },
    error: function(data, status) {
        $('#data').text(data.responseText); 
        console.error(data.responseText);
    }
  });
}

function login() {
  window.location.replace('https://www.dropbox.com/1/oauth2/authorize?client_id=ojtyzbpz7peoc2v&response_type=code&redirect_uri=http://localhost:8000');
}

function auth() {
  var auth_code = getUrlParameter('code');
  if (!auth_code) return;
  var request = $.ajax({
      type: 'POST',
      url: 'https://api.dropbox.com/1/oauth2/token/',
      data: {
        code: auth_code,
        grant_type: 'authorization_code',
        redirect_uri: 'http://localhost:8000',
        client_id: app_key,
        client_secret: app_secret
      },
      success: function(data, status) {
          var json = JSON.parse(data);
          access_token = json.access_token;

          $('#data').text('Access token retrieved. Ready to upload or get info.');
          console.log(access_token);
          console.log(data, status);
      },
      error: function(data, status) {
          $('#data').text(data.responseText); 
          console.error(data.responseText);
      }
    });
}

function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};


$.ajaxSetup({
    beforeSend: function(xhr, settings) {
        if (access_token) {    
          xhr.setRequestHeader ("Authorization", "Bearer " + access_token);
        }
    }
});