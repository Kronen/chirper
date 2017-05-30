// Notifica al usuario cuando ha enviado un chirp (mensaje)
function handlePostSubmited(area) {
    $('.post-area-new textarea').val('');
    if (area === 'new-post')
        $('.post-area-new').removeClass('open');
    $.notify("¡Tu Chirp ha sido enviado!",
            {
                clickToHide: true,
                autoHide: true,
                autoHideDelay: 5000,
                position: 'top center',
                className: 'success',
                showAnimation: 'slideDown',
                showDuration: 400,
                hideAnimation: 'slideUp',
                hideDuration: 200
            }
    );
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
    
    
    $.each($('a.tt'), function() {
        $(this).attr("href", 'tag/' + $(this).text().substring(1));
    });
    
    
    var tagPattern = /\B#([a-zA-Z0-9_-]{4,81})/gi;
    
    /* add link to Trenting topic tags */
    
    $.each($('a.tt'), function() {
        $(this).attr("href", 'tag/' + $(this).text().substring(1));
    });
    /* add link to post tags */
    tagsAndMentionsToLinks();
    
    // Elementos futuros? (No funciona)
    //  $('.post-text').on('DOMNodeInserted', function() {
    //  if(!$(this).find("a.tag").length )
    //      $(this).html($(this).html().replace(tagPattern, "<a class='tag' href='/Chirper/tag/$1'>#$1</a>"));
    //  });  
    
    $('button.view-more').on('click', function() {futureTagsAndMentions();});
});

function tagsAndMentionsToLinks() {
    var tagPattern = /\B#([a-zA-Z0-9_-]{4,81})/gi;
    var mentionPattern = /\B@([a-zA-Z0-9_-]{4,21})/gi;
    $.each($('.post-text'), function() {
        /* Don't add link if it already has */
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

  