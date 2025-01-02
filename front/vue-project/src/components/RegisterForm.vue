<script setup>
import axios from 'axios'
const API_URL = import.meta.env.VITE_API_URL;
import apiClient from '@/axiosConfig';

	const userData = {
		username: '',
		password: ''
	}

  import { ref, defineEmits } from 'vue'

  // Defino los eventos que va a emitir a el componente a su padre
  const emit = defineEmits(['getBackHome', 'goToLogIn'])

  function getBackHomeEvent() {
    emit('getBackHome')
  }

  function goToLogInEvent() {
    emit('goToLogIn')
  }

  // Manejar el envío del formulario
async function handleSubmit(event) {
  event.preventDefault() // Evita la recarga de la página
  try {
    console.log(userData)
    const response = await apiClient.post('/login/register', userData);
    console.log('Response:', response.data);
    goToLogInEvent();
  } catch (error) {
    console.error('Error:', error)
  }
}

</script>

<template>
	<div>
      <h1> <a href="#" @click="getBackHomeEvent" > Reading Tracker </a></h1>
	    <form @submit="handleSubmit">
	      <h1>Join Us</h1>
	      <label>
	        <input type="text" placeholder="Username" v-model="userData.username" >
	      </label>
	      <label>
	        <input type="password" placeholder="Password" v-model="userData.password"> 
	      </label>
	      <button type="submit"> Register </button>
	      <p>Already have an account? 
	      	<a href="#" @click="goToLogInEvent">	Sign In </a>
 	      </p>
	    </form>
  	</div>
</template>

<style scoped>
	div {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-content: center;
    align-items: center;
    row-gap: 15px;
    
  }

  form {
    display: flex;
    flex-direction: column;
    row-gap: 25px;
    width: 30%;
    border-style: solid;
    border-width: 1px;
    border-radius: 10px;
    padding: 25px;
  }

  input {
    font-size: 20px;
    padding: 5px;
    width: 100%;
  }

  button {
    background-color: seagreen;
    font-size: 20px;
    border-radius: 5px;
    border-style: none;
    padding: 10px;
  }
	
</style>