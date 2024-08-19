<script setup>
  import { ref, defineEmits } from 'vue'

  /* 
    Declaro el evento addBook que se va a emitir cada vez que se toque el botón add book
    el componente padre va a escuchar este evento y va a cambiar el valor de showModal
  ´ para esconder el componente AddItem y mostrar el componente Modal
  */
  const emit = defineEmits(['addBook'])

  function buttonClick() {
  emit('addBook')
}  

</script>

<template>
  <div>
    <button @click="buttonClick" > Add book + </button>
  </div>
</template>

<style scoped> 
  div {
    display: flex;
    justify-content: center;
    align-content: center;
  }

  button {
    background-color: seagreen;
    font-size: 20px;
    border-radius: 10px;
    border-style: none;
    padding: 15px;
  }
</style>
