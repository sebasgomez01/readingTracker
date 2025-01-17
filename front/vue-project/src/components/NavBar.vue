<script setup>
  import { ref, defineEmits } from 'vue'
  import apiClient from '@/axiosConfig';
  import { useRouter, useRoute } from 'vue-router'

  const router = useRouter()
  const route = useRoute()

  // Defino los eventos que va a emitir a el componente a su padre
  const emit = defineEmits(['showLogIn', 'showRegister', 'logOut', 'showLogoutMenu'])

  function showLogInEvent() {
    emit('showLogIn')
  }

  function showRegisterEvent() {
    emit('showRegister')
  }

  function showLogoutMenuEvent() {
    emit('showLogoutMenu')
  }

  function logOutEvent() {
    emit('logOut')
  }

  const handleLogOut = async() => {
    showLogInEvent();
    logOutEvent();
    try {
      const response = await apiClient.get('/logout');
      //console.log(response.status);
      localStorage.setItem('jwt_token', null);
      router.replace('/')
    } catch(error) {
      //console.error(error);
    }
  }

</script>

<template>
  <div id="root">
    <div id="titleDiv">
      <h1>Reading Tracker</h1>
    </div>
    <div id="buttonsDiv">
      
      <button id="logoutButton" @click="handleLogOut"> Log Out   </button>
      <button id="menuButton" @click="showLogoutMenuEvent">☰</button>
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

  #logoutButton {
    background-color: seagreen;
    font-size: 15px;
    border-radius: 5px;
    border-style: none;
    height: 90%;
    padding: 10px;
  }

  #menuButton {
    background-color: seagreen;
    font-size: 15px;
    border-radius: 5px;
    border-style: none;
    height: 50%;
    padding: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    display: none;
  }

  @media screen and (max-width: 480px) {
    #root {
      padding: 0rem 0.5rem;
      height: 60px;
      display: grid;
      grid-template-columns: 7% 80% 20%;
    }

    #titleDiv {
      display: flex;
      align-items: center;
      justify-content: center;
      width: 100%;
      grid-column: 2;
    }

    h1 {
      width: 100%;
      font-size: 30px;
      text-align: center;
    }

    #buttonsDiv {
      display: flex;
      align-items: center;
      grid-column: 3;
      
    }

    #logoutButton {
      height: 70%;
      font-size: 15px;
      display:none;
    }

    #menuButton {
    background-color: seagreen;
    font-size: 15px;
    border-radius: 5px;
    border-style: none;
    height: 50%;
    padding: 10px;
    display: flex;
    justify-content: center;
    align-items: center;

  }
  }
</style>
