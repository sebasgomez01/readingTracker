<script setup>
  import { ref, defineEmits } from 'vue'
  import apiClient from '@/axiosConfig';
  import { useRouter, useRoute } from 'vue-router'

  const router = useRouter()
  const route = useRoute()

  // Defino los eventos que va a emitir a el componente a su padre
  const emit = defineEmits(['showLogIn', 'showRegister', 'logOut'])

  function showLogInEvent() {
    emit('showLogIn')
  }

  function showRegisterEvent() {
    emit('showRegister')
  }

  function logOutEvent() {
    emit('logOut')
  }

  const handleLogOut = async() => {
    showLogInEvent();
    logOutEvent();
    try {
      const response = await apiClient.get('/logout');
      console.log(response.status);
      localStorage.setItem('jwt_token', null);
      router.replace('/')
    } catch(error) {
      console.error(error);
    }
  }

  /*
  <button @click="showRegisterEvent"> Register </button>
      <button @click="showLogInEvent"> Log In   </button>*/ 

</script>

<template>
  <div id="root">
    <div id="titleDiv">
      <h1>Reading Tracker</h1>
    </div>
    <div id="buttonsDiv">
      
      <button @click="handleLogOut"> Log Out   </button>
    </div>
  </div>
</template>

<style scoped>
  #root {
    display: flex;
  }

  #titleDiv {
    width: 50%;
  }

  #buttonsDiv {
    width: 47%;
    display: flex;
    flex-direction: row-reverse;
    column-gap: 15px;
  }

  button {
    background-color: seagreen;
    font-size: 15px;
    border-radius: 5px;
    border-style: none;
    height: 90%;
    padding: 10px;
  }
</style>
