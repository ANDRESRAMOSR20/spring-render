console.log("Sirvio el hijuepita js");


function onYouTubeIframeAPIReady() {
        // Crea un reproductor para cada video de YouTube
        var player1 = new YT.Player('video1', {
            height: '290',
            width: '400',
            videoId: 'wMjT3CQayKo' // Reemplaza 'ID_DEL_VIDEO_1' con el ID del primer video de YouTube
        });

        var player2 = new YT.Player('video2', {
            height: '290',
            width: '400',
            videoId: 'Opa3z6rYymo' // Reemplaza 'ID_DEL_VIDEO_2' con el ID del segundo video de YouTube
        });

        var player3 = new YT.Player('video3', {
            height: '290',
            width: '400',
            videoId: '7gk9-D0mJdg' // Reemplaza 'ID_DEL_VIDEO_3' con el ID del tercer video de YouTube
        });
    }
    
    var tiempoInactividad = 600; // Tiempo en segundos (10 minutos)
var tiempoCierreSesion;



    

    
    
    document.addEventListener("DOMContentLoaded", function() {
    const estadoElements = document.querySelectorAll(".disponi");
    

    estadoElements.forEach(function(estadoElement) {
        if (estadoElement.textContent.trim() === "Disponible") {
            estadoElement.classList.add("verde");
        }else if(estadoElement.textContent.trim() === "No Disponible"){
			 estadoElement.classList.add("rojo");
		}
    });
});
    
    
    
    
   document.addEventListener("DOMContentLoaded", function() {
    const estadoElements = document.querySelectorAll(".estado");
    

    estadoElements.forEach(function(estadoElement) {
        if (estadoElement.textContent.trim() === "Reservada") {
            estadoElement.classList.add("verde");
        }else if(estadoElement.textContent.trim() === "Pendiente"){
			 estadoElement.classList.add("rojo");
		}
    });
});


document.addEventListener('DOMContentLoaded', function () {
        document.querySelector('form').addEventListener('click', function () {
            var errorMessages = document.querySelectorAll('.error-message');
            errorMessages.forEach(function (element) {
                element.style.display = 'none';
            });
        });
    });


    // Asegúrate de que el valor de userEmail se haya establecido correctamente en tu HTML
var userEmail; /* Obtén el correo del usuario desde tu modelo o cualquier otra fuente */;

// Asigna el valor al campo de correo oculto
/*document.getElementById('email').value = userEmail;*/


