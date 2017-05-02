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
        $('.post-area-new').removeClass('open');
    });
    /* Abre/Cierra el área para escribir un nuevo mensaje */
    $('.post-area-reply textarea').on("focus", function (e) {
        $('.post-area-reply').addClass('open');
    });
    $('.post-area-reply textarea').on("blur", function (e) {
        $('.post-area-reply').removeClass('open');
    });

    /* Abre cierra el área para escribir una respuesta y ver estadísticas de chirper */
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
});