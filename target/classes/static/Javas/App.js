console.log("hola js");



document.addEventListener("DOMContentLoaded", function() {
    const nivelElements = document.querySelectorAll(".cambiar-color");
    

    nivelElements.forEach(function(nivelElement) {
        if (nivelElement.textContent.trim() === "Nivel 1") {
            nivelElement.classList.add("rojo");
        }else if(nivelElement.textContent.trim() === "Nivel 2"){
			 nivelElement.classList.add("azul");
		}else if(nivelElement.textContent.trim() === "Nivel 3"){
			 nivelElement.classList.add("amarillo");
		}else{
			nivelElement.textContent.trim() === "No definido"
			 nivelElement.classList.add("gris");
		}
    });
});

document.addEventListener("DOMContentLoaded", function() {
    const estadoElements = document.querySelectorAll(".estado");
    

    estadoElements.forEach(function(estadoElement) {
        if (estadoElement.textContent.trim() === "Activo") {
            estadoElement.classList.add("verde");
        }else if(estadoElement.textContent.trim() === "Anulado"){
			 estadoElement.classList.add("gris");
		}
    });
});





document.addEventListener("DOMContentLoaded", function () {
    // Obtenemos una referencia al botón, al contenido y al estado del estudiante
    var botonMostrar = document.getElementById("mostrarContenido");
    var contenido = document.querySelector(".container");
    var estadoEstudiante = document.querySelector(".estado").textContent;
   var divi = document.getElementById("divi");

    if (estadoEstudiante === "Anulado") {
        // Si el estado del estudiante es "Anulado", ocultamos tanto el botón como el contenido
        botonMostrar.style.display = "none";
        contenido.style.display = "none";
        divi.style.display="none";
    } else {
        // Si el estado no es "Anulado," ocultamos el contenido y mostramos el botón
        contenido.style.display = "none";
        botonMostrar.style.display = "block";
    }

    // Agregamos un manejador de evento clic al botón
    botonMostrar.addEventListener("click", function () {
        if (contenido.style.display === "none") {
            // Si el contenido está oculto, lo mostramos
            contenido.style.display = "block";
            botonMostrar.textContent = "Ocultar notas";
        } else {
            // Si el contenido está visible, lo ocultamos
            contenido.style.display = "none";
            botonMostrar.textContent = "Ver notas";
        }
    });
});




document.addEventListener("DOMContentLoaded", function () {
    var estadoEstudiante = document.querySelector(".estado").textContent;
    var promedioPuntajes = document.getElementById("prom");
 	var levelPuntajes = document.getElementById("level");
    // Función para actualizar el campo promedioPuntajes
    function actualizarPromedioPuntajes() {
        if (estadoEstudiante === "Anulado") {
            // Si el estado del estudiante es "Anulado", establecemos el campo promedioPuntajes en 0
            promedioPuntajes.textContent = "0";
            levelPuntajes.classList.add("gris");
            levelPuntajes.textContent = "No definido";
        }
    }

    actualizarPromedioPuntajes(); // Llamar a la función al cargar la página

    // Agregamos un manejador de evento al botón o al elemento que cambia el estado del estudiante
    // Cuando el estado cambia, llamamos a la función actualizarPromedioPuntajes nuevamente.
    // Debes agregar el evento que cambia el estado aquí.

    // Por ejemplo, si tienes un botón que cambia el estado, sería algo como esto:
    var botonCambiaEstado = document.getElementById("botonCambiaEstado");
    botonCambiaEstado.addEventListener("click", function () {
        // Cambiar el estado del estudiante aquí
        estadoEstudiante = "Anulado"; // Cambiar a "Anulado" o "Activo" según corresponda
        actualizarPromedioPuntajes(); // Actualizar el campo promedioPuntajes
    });
});

    const estadoSelect = document.getElementById('estado');
    const puntajeCienciasInput = document.getElementById('puntajeciencias');
    const puntajecompetenciasInput = document.getElementById('puntajecompetencias');
    const puntajecomunicacionInput = document.getElementById('puntajecomunicacion');
    const puntajeinglesInput = document.getElementById('puntajeIngles');
    const puntajedisenoInput = document.getElementById('puntajediseno');
    const puntajelecturaInput = document.getElementById('puntajelectura');
    const puntajeproyectosInput = document.getElementById('puntajeproyectos');
    const puntajerazonamientoInput = document.getElementById('puntajerazonamiento');
    const nivelinglesInput = document.getElementById('nivelingles');
    // Repite esto para los otros campos de puntajes

    estadoSelect.addEventListener('change', function () {
        if (estadoSelect.value === 'Anulado') {
            // Si el estado es "Anulado", establece los campos de puntajes a cero y deshabilítalos
            puntajeCienciasInput.value = '1';
            puntajeCienciasInput.disabled = true;
            puntajecompetenciasInput.value = '1';
            puntajecompetenciasInput.disabled =true;
            puntajecomunicacionInput.value = '1';
            puntajecomunicacionInput.disabled =true;
             puntajeinglesInput.value = '1';
            puntajeinglesInput.disabled =true;
           puntajedisenoInput.value = '1';
           puntajedisenoInput.disabled =true;
             puntajelecturaInput.value = '1';
            puntajelecturaInput.disabled =true;
             puntajeproyectosInput.value = '1';
            puntajeproyectosInput.disabled =true
             puntajerazonamientoInput.value = '1';
            puntajerazonamientoInput.disabled =true;
            nivelinglesInput.value = '1';
            nivelinglesInput.disabled =true;
        }else{
			
			 puntajeCienciasInput.value = '';
            puntajeCienciasInput.disabled = false;
			nivelinglesInput.value = '';
            nivelinglesInput.disabled =false;
            puntajerazonamientoInput.value = '';
            puntajerazonamientoInput.disabled =false;
            puntajeproyectosInput.value = '';
            puntajeproyectosInput.disabled =false;
              puntajelecturaInput.value = '';
            puntajelecturaInput.disabled =false;
              puntajedisenoInput.value = '';
           puntajedisenoInput.disabled =false;
            puntajeinglesInput.value = '';
            puntajeinglesInput.disabled =false;
             puntajecompetenciasInput.value = '';
            puntajecompetenciasInput.disabled =false;
            puntajecomunicacionInput.value = '';
            puntajecomunicacionInput.disabled =false;
		}
        
    });


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
    
