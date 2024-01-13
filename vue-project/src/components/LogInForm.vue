<script setup>
	const userData = {
		username: '',
		password: ''
	}

	 import { ref, defineEmits } from 'vue'

	// Defino los eventos que va a emitir a el componente a su padre
	const emit = defineEmits(['getBackHome', 'goToRegister'])

	function getBackHomeEvent() {
	  emit('getBackHome')
	}

	function goToRegisterEvent() {
	  emit('goToRegister')
	}


</script>

<template>

	<div>
		<h1> <a href="#" @click="getBackHomeEvent"> Reading Tracker </a></h1>
	    <form>
	      <h1>Log In</h1>
	      <label>
	        <input type="text" placeholder="Username" v-model="userData.username" >
	      </label>
	      <label>
	        <input type="password" placeholder="Password" v-model="userData.password"> 
	      </label>
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
	
</style>