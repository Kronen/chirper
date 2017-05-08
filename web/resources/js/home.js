$(function () {

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
});