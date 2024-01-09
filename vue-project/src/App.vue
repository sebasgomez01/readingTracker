<script setup>
import NavBar from './components/NavBar.vue'
import AddItem from './components/AddItem.vue'
import Modal from './components/Modal.vue'
import BookItem from './components/BookItem.vue'

import { ref } from 'vue'

// La variable showModal va a controlar si se muestran o no los componentes AddItem y Modal
// cuando uno se muestra el otro no y viceversa
const showModal = ref(false)

// Esta variable guarda los items de la colección, es una lista de objetos con los datos de cada libro
const collectionItems = ref([])

function modifyShowModal() {
  showModal.value = !showModal.value
}

function saveData(data) {
  let bookData = data
  console.log("te saludo desde el escuchador del evento", bookData)
  console.log(collectionItems.value)
  collectionItems.value = [...collectionItems.value, bookData]
  console.log(collectionItems.value)
  modifyShowModal()
}

function deleteItem(title) {
  // Filtra la lista y crea una nueva lista sin el objeto con el título proporcionado
  const updatedCollectionItems = collectionItems.value.filter(item => item.title !== title);

  // Verifica si se eliminó algún elemento
  if (updatedCollectionItems.length !== collectionItems.length) {
    console.log(`Objeto con título "${title}" eliminado.`);
  } else {
    console.log(`No se encontró un objeto con título "${title}".`);
  }

  // Devuelve la nueva lista sin modificar la original
  //return updatedCollectionItems;  
  collectionItems.value = updatedCollectionItems
} 

</script>

<template>
  <NavBar />
  <AddItem v-if="!showModal" @add-book="modifyShowModal" />
  <Modal v-if="showModal" @save-data="saveData" @cancel="modifyShowModal" />
  <div>
    <h1>Your colletion:</h1>
  </div>
  <div id="itemsContainer">  
      <BookItem v-for="item in collectionItems"  v-bind="item" @delete-item="deleteItem" />
  </div>
</template>

<style scoped>
  #itemsContainer {
    display: grid;
    grid-template-columns: 23% 23% 23% 23%;
    gap: 20px;
  }



</style>
