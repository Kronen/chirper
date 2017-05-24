$(function () {
    var $body = $('body');
    var $sidebar = $('#sidebar');

    // Desactiva las transiciones hasta que haya cargado la página
    $body.addClass('cargando');
    setTimeout(function () {
        $body.removeClass('cargando');
    }, 100);

    // Si es pantalla grande y tenemos sidebar
    if($sidebar.length > 0) {
        var $sidebar_links = $sidebar.find('a');

        $sidebar_links
            .addClass('scrolly')
            .each(function () {
                var $this = $(this),
                    id = $this.attr('href'),
                    $section = $(id);

                $section.scrollex({
                    mode: 'middle',
                    top: '-20vh',
                    bottom: '-20vh',
                    initialize: function () {
                        // Desactivamos la sección
                        $section.addClass('inactivo');
                    },
                    enter: function () {
                        // Activa la sección al entrar
                        $section.removeClass('inactivo');
                        // Añadimos la clase activo al enlace correspondiente
                        $sidebar.find('a').removeClass('activo');
                        $('a[href=' + $this.attr('href') + ']').addClass('activo');
                    }
                });
            });
    } 
    
    // Efecto de scroll lento
    $('.scrolly').scrolly({speed: 1000});
    // Pone el focus en el input de username tras 1 segundo de haber hecho click
    // para evitar un pequeño glitch con el scroll.
    $('.scrolly').click(function() {
        setTimeout( function(){
            $('#form-login\\:username').focus();   
        }, 1000 );            
    });
    
    // Pestañas de Login / Sign Up
    $("#demoTab").easyResponsiveTabs({
        type: 'default',                        // Types: default, vertical, accordion.
        width: 'auto',                          // auto or any custom width.
        fit: true,                              // 100% fits in a container.
        closed: false,                          // Close the panels on start, the options 'accordion' and 'tabs'.
                                                // keep them closed in their respective view types.
        activate: function () {},               // Callback function, gets called if tab is switched.
        tabidentify: 'tab_identifier_child',    // The tab groups identifier *This should be a unique name for each.
                                                // tab group and should not be defined in any styling or css file.
        activetab_bg: '#B5AC5F',                // background color for active tabs..
        inactive_bg: '#E0D78C',                 // background color for inactive tabs..
        active_border_color: '#9C905C',         // border color for active tabs heads..
        active_content_border_color: '#9C905C'  // border color for active tabs content so that it matches the tab 
                                                // head border.
    });
});