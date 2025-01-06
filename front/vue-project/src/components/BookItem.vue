<script setup>
	import { ref, defineEmits } from 'vue'
	const API_URL = import.meta.env.VITE_API_URL;
	import apiClient from '@/axiosConfig';

	// Declaro los props del componente
	const props = defineProps(['title', 'author', 'pages', 'read', 'id'])

	// DEfino los eventos que va a emitir a el componente a su padre
	const emit = defineEmits(['deleteItem', 'updateItem', 'sessionExpired'])

	// me creo un ref utilizando el valor inicial del prop read, pues necesito modificarlo y los props son inmutables. Esto va a controlar los estilos y el contenido del botón read/unread 
	const readValue = ref(props.read)

	// por alguna razón si le paso props.id como segundo parámetro a emit me tira el error "id is not defined" así que probé declarando esta variable y pasandosela de parámetro y ahí si funciona, rarísimo :P
	const bookID = props.id

	// función que emite el evento para borrar un item y manda el item del ítem a borrar como dato
	function deleteEvent() {
		emit('deleteItem', bookID)
	}

	function changeReadValue(updateBook) {
		readValue.value = !readValue.value 
		emit('updateItem', updateBook)
	}

	// Función para realizar la petición HTTP con el verbo DELETE para eliminar el elemento de la base de datos
	const deleteElem = async () => {
		// Realizo la petición DELETE
		apiClient.delete(`/books/${bookID}`)
		  .then(response => {
		    console.log('Libro eliminado correctamente', response.data);
			deleteEvent();
		  })
		  .catch(error => {
		    console.error('Error al eliminar el libro', error);
			emit('sessionExpired');
		  });

  		
        // Ejecuto la función deleteEvent que emite el evento deleteItem para que lo escuche el componente App.vue
       
       
      
    };

    // Función para realizar una petición Http Patch para modificar el estado de read
    const updateReadState = async () => {
		let updateBook = {
			title: props.title,
			author: props.author,
			pages: props.pages,
			read: !props.read,
			id: bookID
		}
		
		console.log(updateBook)

    	// Primero actualizo los estilos del botón:
    	changeReadValue(updateBook);

		// Realizo la petición Patch
		apiClient.patch(`books/${bookID}`, updateBook)
		  .then(response => {
		    console.log('Libro actualizado correctamente', response.data);
		    
		  })
		  .catch(error => {
		    console.error('Error al actualizar el libro', error);
			emit('sessionExpired');
	
		  });
    	
    }
    


</script>


<template>
	<div>
		<p> {{ title }} </p>
		<p> {{ author }}  </p>
		<p> Pages: {{pages}} </p>
		<p> </p>
		<button @click="updateReadState" 
		:class="{ 'read-style': readValue, 'unread-style': !readValue } "> 
			{{ readValue ? 'Read' : 'Unread' }}       
		</button>
		<button id="deleteButton" @click="deleteElem" > Delete </button>
	</div>
</template>

<style scoped> 	
	div {
		display: flex;
		flex-direction: column;
		justify-content: space-between;
		align-items: center;
		justify-items: center;
		padding: 20px;
		row-gap: 15px;
		border-style: solid;
		border-width: 1px;
		border-radius: 5px;
	}

	p {
    	font-size: 25px;
  	}

  	#deleteButton {
	    background-color: orangered;
	    font-size: 20px;
	    border-radius: 5px;
	    border-style: none;
	    padding: 10px;
	    width: 90%;
  	}

  	/* Estilos para el botón cuando readValue es true */
	.read-style {
	  background-color: seagreen;
	  font-size: 20px;
	    border-radius: 5px;
	    border-style: none;
	    padding: 10px;
	    width: 90%;
	}

	/* Estilos para el botón cuando readValue es false */
	.unread-style {
	  background-color: orangered;
	  font-size: 20px;
	    border-radius: 5px;
	    border-style: none;
	    padding: 10px;
	    width: 90%;
	}

</style>