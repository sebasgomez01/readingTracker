<script setup>
  import { ref, defineEmits } from 'vue'

  // Defino los eventos que va a emitir a el componente a su padre
  const emit = defineEmits(['showLogIn', 'showRegister'])

  function showLogInEvent() {
    emit('showLogIn')
  }

  function showRegisterEvent() {
    emit('showRegister')
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
      
      <button @click="showLogInEvent"> Log Out   </button>
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
