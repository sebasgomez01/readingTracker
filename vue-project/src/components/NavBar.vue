<script setup>

</script>

<template>
  <div id="root">
    <div id="titleDiv">
      <h1>Reading Tracker</h1>
    </div>
    <div id="buttonsDiv">
      
      <button> Register </button>
      <button> Log In   </button>
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
