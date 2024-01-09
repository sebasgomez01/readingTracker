<script setup>
	import { ref, defineEmits } from 'vue'
	// Declaro los props del componente
	const props = defineProps(['title', 'author', 'pages', 'read'])

	// DEfino los eventos que va a emitir a el componente a su padre
	const emit = defineEmits(['deleteItem'])

	// me creo un ref utilizando el valor inicial del prop read, pues necesito modificarlo y los props son inmutables. Esto va a controlar los estilos y el contenido del botón read/unread 
	const readValue = ref(props.read)

	// por alguna razón si le paso props.title como segundo parámetro a emit me tira el error "title is not defined" así que probé declarando esta variable y pasandosela de parámetro y ahí si funciona, rarísima :P
	const bookTitle = props.title

	// función que emite el evento para borrar un item y manda el title del ítem a borrar como dato
	function deleteEvent() {
		emit('deleteItem', bookTitle)
	}

	function changeReadValue() {
		readValue.value = !readValue.value 
	}


</script>


<template>
	<div>
		<p> {{ title }} </p>
		<p> {{ author }}  </p>
		<p> {{pages}} </p>
		<p> </p>
		<button @click="changeReadValue" 
		:class="{ 'read-style': readValue, 'unread-style': !readValue } "> 
			{{ readValue ? 'Read' : 'Unread' }}       
		</button>
		<button id="deleteButton" @click="deleteEvent" > Delete </button>
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