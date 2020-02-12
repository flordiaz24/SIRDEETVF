/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function mapa(){
    map = L.map('map',{
        zoomControl: false,
        maxZoom: 17,
        minZoom: 3,
        zoomDelta: 0.25,
        zoomSnap: 0
        }).setView([13.716542759922987, -88.86316643487693], 13);

        var openStreet = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        }).addTo(map);
        
        /*Para agregar caja home*/
        var zoomHome = L.Control.zoomHome({
            position: 'topleft',
            zoomHomeTitle: 'Inicio'
          });
          zoomHome.addTo(map);
        
       
        /**
            * Boton para limpiar el contenido del mapa
            */
           L.easyButton({
               position:'topright',
               states:[{
                 icon:'icono-mapa icon-eraser',
                 title:'Limpiar Mapa',
                 onClick: function(btn, map) {
                   limpiarMapa();
                 }
               }]      
             }
           ).addTo( map );
       
       var loadingControl = L.Control.loading({
        separate: true,
        position: 'topright'
      });
      map.addControl(loadingControl);
      
      
       /* Esri Layer  */
//        var EsriLayer = L.esri.basemapLayer('Imagery');
//        var EsriLayerDetalle = L.esri.basemapLayer('ImageryLabels');

        /* Topografy Layer */
          topoLayer = L.tileLayer('https://{s}.tile.opentopomap.org/{z}/{x}/{y}.png', {
            maxZoom: 15,
            attribution: '',
            attributionControl: false
          });
          
          var OpenStreetMap_DE = L.tileLayer('https://{s}.tile.openstreetmap.de/tiles/osmde/{z}/{x}/{y}.png', {
            maxZoom: 18,
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            });
          
          /**
        * Agrega el incono de seleccion de capas
        */
         var baseMaps = {
           'Capa Base': openStreet,
           'OpenStreetmap':OpenStreetMap_DE,
            'Terreno': topoLayer
         };
           L.control
            .groupedLayers(baseMaps, null, { position: 'bottomright' })
            .addTo(map);
    
            /**
            * Boton que implementa la funcionalidad del FullScreen, se descarto los plugin de 
            * leaflet para fullscreen debido a que los select2 no se mostraban y no se ha logrado
            * encontrar el motivo del error.
            */
           var stateChangingButton = L.easyButton({
             position: 'topright',
             states: [{
                     stateName: 'enterfullscreen',        // name the state
                     icon:      'icono-mapa icon-external-link',               // and define its properties
                     title:     'Enter FullScreen',      // like its title
                     onClick: function(btn, map) {       // and its callback
                       btn.state('exitefullscreen');
                       fullScream(document.documentElement);
                     }
                 }, {
                     stateName: 'exitefullscreen',
                     icon:      'icono-mapa icon-minus',
                     title:     'Exit FullScreen',
                     onClick: function(btn, map) {
                       btn.state('enterfullscreen');
                       cancelFullScreen();
                     }
             }]
           });
           stateChangingButton.addTo( map );
           
           // create the sidebar instance and add it to the map
            sidebar = L.control
              .sidebar({
                container: 'sidebar',
                closeButton: true
              })
              .addTo(map)
              .open('home');
    
}

        /**
         * Funciones que me permiten entrar y salir de full screen
         */
        function fullScream(element){
          if(element.requestFullScreen) {
          element.requestFullScreen();
          } else if(element.mozRequestFullScreen) {
            element.mozRequestFullScreen();
          } else if(element.webkitRequestFullScreen) {
            element.webkitRequestFullScreen();
          }
        }
        function cancelFullScreen() {
           if(document.cancelFullScreen) {
               document.cancelFullScreen();
           } else if(document.mozCancelFullScreen) {
               document.mozCancelFullScreen();
           } else if(document.webkitCancelFullScreen) {
               document.webkitCancelFullScreen();
           }
        }
        
        /**
        * Elimina los markers y layer agregados en el mapa,
        * si se agrega un nuevo layer se debe de agregar a esta funcion para poder ser limpiado del
        * mapa
        */
       function limpiarMapa(){
         //Elimina los marker
         for (var i = 0; i < marker.length; i++) {
           map.removeLayer(marker[i]);
         }
         marker = [];
       }
       
       function crearMarquerTextCriadero (
            latitud,
            longitud,
            id,
            indice,
            nombre,
            icono
          ) {
            var marcador = L.icon({
              iconUrl: assetsBaseDir+'leaflet/img/'+icono,
              shadowUrl: assetsBaseDir+'leaflet/img/marker-shadow.png',
              iconSize:     [25, 41], 
              iconAnchor:   [12, 41], 
              popupAnchor:  [-1, -38], 
              shadowSize: [41, 41]
            });
            //  new L.NumberedDivIcon({ number: '', iconUrl: icono })
            return L.marker([latitud, longitud], {
              id: id,
              indice: indice,
              nombre: nombre,
              icon: marcador,
              draggable: false
            });
          }


