<script setup>
	import axios from 'axios'
	const API_URL = import.meta.env.VITE_API_URL;
	import apiClient from '@/axiosConfig';
  import { ref, defineEmits, onMounted } from 'vue'
  //import router from '../router/index.js'; ESTE IMPORT ESTÁ MAL Y NO FUNCIONA, HAY QUE USAR $router SIN IMPORTAR NADA

  import { useRouter, useRoute } from 'vue-router'

  const router = useRouter()
  const route = useRoute()


	const userData = {
		username: '',
		password: ''
	}

  const showLoginError = ref(false);
  const showSuccessMessage = ref(false);
  const showSessionExpiredMessage = ref(false);
	// Defino los eventos que va a emitir a el componente a su padre
	const emit = defineEmits(['getBackHome', 'goToRegister'])


	function getBackHomeEvent() {
	  emit('getBackHome')
	}

	function goToRegisterEvent() {
	  emit('goToRegister');
    router.replace('register');
    //console.log("evento go to register emitido")
	}

	
  // Manejar el envío del formulario
async function handleSubmit(event) {
  event.preventDefault() // Evita la recarga de la página
  // limpio espaces al inicio y al final del username
  userData.username = userData.username.trim();
  try {
    //console.log(userData)
    const response = await apiClient.post('/login/authenticate', userData);
    //console.log('Response:', response);
	const token = response.headers['authorization']; // O  si está en el header
    localStorage.setItem('jwt_token', token);
    //console.log('Token saved:', token);
    getBackHomeEvent();
    router.replace('/home')
  } catch (error) {
    showLoginError.value = true;
    //console.error('Error:', error)
  }
}

onMounted(() => {
  showSessionExpiredMessage.value = false;

  if (route.query.success) {
    showSuccessMessage.value = true;
  }

  if (route.query.sessionExpired) {
    showSessionExpiredMessage.value = true;
  }

});

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
        <p v-if="showSuccessMessage" >¡User successfully created! Now you can Log in.</p>
        <p class="errorLoginMsg" v-if="showSessionExpiredMessage" >Your session has expired. Please log in again.</p>
        
	      <button type="submit"> Sign In </button>
	      <p>Don't have an account yet? 
	      	<a class="registerLink" href="#" @click="goToRegisterEvent">	Register now </a>
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

  @media screen and (max-width: 480px) {
    h1 {
      font-size: 40px;
    }

    div {
      height: 90%;
      width: 95%;
    }

    form {
    display: flex;
    flex-direction: column;
    row-gap: 30px;
    width: 100%;
    border-style: none;
    border-width: 1px;
    border-radius: 10px;
    padding: 0rem 1rem;
    } 

    input {
      height: 3.5rem;
      font-size: 28px;
    }

    input::placeholder {
      font-size: 28px;
      
    }

    button {
      font-size: 30px;
    }

    p {
      font-size: 20px;
    }

  }


  @media screen and (max-width: 768px) and (min-width: 481px)  {
    div {
      height: 100%;
    }

    form {
    display: flex;
    flex-direction: column;
    row-gap: 25px;
    width: 70%;
    border-style: solid;
    border-width: 1px;
    border-radius: 10px;
    padding: 25px;
  } 

  input {
      height: 3rem;
    }

    input::placeholder {
      font-size: 25px;
      
    }
    button {
      font-size: 25px;
    }

    p {
      font-size: 20px;
    }
  }
  
</style>