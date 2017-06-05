var geocoder;
var city, country;

function codeLatLng(lat, lng, callback) {
    var latlng = new google.maps.LatLng(lat, lng);
    var result = "";
    geocoder.geocode({'latLng': latlng}, function(results, status) {
        if(status === google.maps.GeocoderStatus.OK) {
            if(results[1]) {
                // Find the city name
                for(var i = 0; i < results[0].address_components.length; i++) {
                  for(var b = 0; b < results[0].address_components[i].types.length; b++) {
                    if(results[0].address_components[i].types[b] === "administrative_area_level_2") {
                        city = results[0].address_components[i];
                        break;
                    }
                  }
                }

                // Find the state name
                for(var i = 0; i < results[0].address_components.length; i++) {
                  for(var b = 0; b < results[0].address_components[i].types.length; b++) {
                    if(results[0].address_components[i].types[b] === "country") {
                        country = results[0].address_components[i];
                        break;
                    }
                  }
                }

                callback(city.long_name + ", " + country.long_name);
            }
        }
    });
}

function handlePostSubmited(area) {
    $('.post-area-new textarea').val('');
}

function handleReplySubmited(area) {
    $('.post-area-reply textarea').val('');
}

$(function () {    
    $('.post-location, .post-info-time').hide();

    $('.userMenu-config').click(function(e) {
        e.stopPropagation();
        $('.dialog-modal.config-menu').slideDown();
    });
    $(document).click(function () {
        $('.dialog-modal.config-menu').slideUp();
    });

    /* Abre/Cierra el área para escribir un nuevo mensaje */
    $('.post-area-new textarea').on("focus", function (e) {
        $('.post-area-new').addClass('open');
    });
    $('.post-area-new textarea').on("blur", function (e) {
        if(!$(this).val())
            $('.post-area-new').removeClass('open');
    });
    /* Cuenta carácteres restantes */
    $('.post-area-new textarea').keyup(function() {
        $('.post-area-new .post-area-remaining').text(256 - $(this).val().length);
    });
    $('.post-area-reply textarea').keyup(function() {
        $('.post-area-reply .post-area-remaining').text(256 - $(this).val().length);
    });
    /* Abre/Cierra el área para escribir un nuevo mensaje */
    $('.post-area-reply textarea').on("focus", function (e) {
        $('.post-area-reply').addClass('open');
    });
    $('.post-area-reply textarea').on("blur", function (e) {
        $('.post-area-reply').removeClass('open');
    });

    /* Abre/Cierra el área para escribir una respuesta y ver estadísticas de chirper */
    $('.post-data').on('click', '.post-expand', function () {
        var state = $(this).data('state');

        switch (state) {
            case 1:
            case undefined :
                $(this).siblings('.expanded-content').slideDown();
                $(this).text('Collapse');
                $(this).data('state', 2);
                break;
            case 2:
                $(this).siblings('.expanded-content').slideUp();
                $(this).text('Expand');
                $(this).data('state', 1);
                break;
        }
    });
    
    $('.post-upload .ui-c').text('Upload Image');
    
    tagsAndMentionsToLinks();
    
    // Elementos futuros? (No funciona)
    //  $('.post-text').on('DOMNodeInserted', function() {
    //  if(!$(this).find("a.tag").length )
    //      $(this).html($(this).html().replace(tagPattern, "<a class='tag' href='/Chirper/tag/$1'>#$1</a>"));
    //  });  
    
    $('button.view-more').on('click', function() {futureTagsAndMentions();});
    
    navigator.geolocation.getCurrentPosition(
        function (position) {
            $(PrimeFaces.escapeClientId('form-new-post:new-post-lat')).val(position.coords.latitude);
            $(PrimeFaces.escapeClientId('form-new-post:new-post-lng')).val(position.coords.longitude);

            geocoder = new google.maps.Geocoder();
            // User Location
            $elem = $('#user-location');
            codeLatLng(position.coords.latitude, position.coords.longitude, function(loc) { 
                $elem.text(loc);
            });

            // Posts locations
            $('.post-location').each(function() {
                var $elem = $(this);
                var lat = $elem.data('lat');
                var lng = $elem.data('lng');
                codeLatLng(lat, lng, function(loc) { 
                    $elem.text(' · ' + loc);                                
                });                            
            });
            $('.post-location, .post-info-time').fadeIn();

        },
        function(error) {
            // no hacer nada
        },
        {enableHighAccuracy: true}
    );
});

function tagsAndMentionsToLinks() {
    var tagPattern = /\B#([a-zA-Z0-9_-]{4,81})/gi;
    var mentionPattern = /\B@([a-zA-Z0-9_-]{4,21})/gi;
    $.each($('a.tt'), function() {
        if(!$(this).find("a.tag").length )
            $(this).html($(this).html().replace(tagPattern, "<a class='tag' href='/Chirper/tag/$1'>#$1</a>"));
    });
    $.each($('.post-text'), function() {
        /* Don't add link if it already has been added */
        if(!$(this).find("a.tag").length )
            $(this).html($(this).html().replace(tagPattern, "<a class='tag' href='/Chirper/tag/$1'>#$1</a>"));
    });
    $.each($('.post-text'), function() {
        if(!$(this).find("a.mention").length )
            $(this).html($(this).html().replace(mentionPattern, "<a class='mention' href='/Chirper/user/$1'>@$1</a>"));
    });    
}

function futureTagsAndMentions() {
    setTimeout(function(){        
        tagsAndMentionsToLinks();
    }, 800);    
}


  