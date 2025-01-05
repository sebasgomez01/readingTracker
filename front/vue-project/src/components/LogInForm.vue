<script setup>
	import axios from 'axios'
	const API_URL = import.meta.env.VITE_API_URL;
	import apiClient from '@/axiosConfig';
  import { ref, defineEmits } from 'vue'
  //import router from '../router/index.js'; ESTE IMPORT ESTÁ MAL Y NO FUNCIONA, HAY QUE USAR $router SIN IMPORTAR NADA

  import { useRouter, useRoute } from 'vue-router'

  const router = useRouter()
  const route = useRoute()


	const userData = {
		username: '',
		password: ''
	}

  const showLoginError = ref(false);

	// Defino los eventos que va a emitir a el componente a su padre
	const emit = defineEmits(['getBackHome', 'goToRegister'])


	function getBackHomeEvent() {
	  emit('getBackHome')
	}

	function goToRegisterEvent() {
	  emit('goToRegister')
    router.replace('register')  
	}

	
  // Manejar el envío del formulario
async function handleSubmit(event) {
  event.preventDefault() // Evita la recarga de la página
  try {
    console.log(userData)
    const response = await apiClient.post('/login/authenticate', userData);
    console.log('Response:', response);
	const token = response.headers['authorization']; // O  si está en el header
    localStorage.setItem('jwt_token', token);
    console.log('Token saved:', token);
    getBackHomeEvent();
  } catch (error) {
    showLoginError.value = true;
    console.error('Error:', error)
  }
}

</script>

<template>

	<div>
		<h1> <a href="#" > Reading Tracker </a></h1>
	    <form @submit="handleSubmit">
	      <h1>Log In</h1>
	      <label>
	        <input type="text" placeholder="Username" v-model="userData.username" required>
	      </label>
	      <label>
	        <input type="password" placeholder="Password" v-model="userData.password" required> 
	      </label>
        <p class="errorLoginMsg" v-if="showLoginError">Username or password incorrect.Try again please.</p>
	      <button type="submit"> Sign In </button>
	      <p>Don't have an account yet? 
	      	<a a href="#" @click="goToRegisterEvent">	Register now </a>
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
	
  .errorLoginMsg {
    color:red;
  }
  
</style>