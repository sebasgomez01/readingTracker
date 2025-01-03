<script setup>
import NavBar from './components/NavBar.vue'
import AddItem from './components/AddItem.vue'
import Modal from './components/Modal.vue'
import BookItem from './components/BookItem.vue'
import LogInForm from './components/LogInForm.vue'
import RegisterForm from './components/RegisterForm.vue'

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

function deleteItem(id) {
  // Filtra la lista y crea una nueva lista sin el objeto con el título proporcionado
  const updatedCollectionItems = collectionItems.value.filter(item => item.id !== id);

  // Verifica si se eliminó algún elemento
  if (updatedCollectionItems.length !== collectionItems.length) {
    console.log(`Objeto con el id "${id}" eliminado.`);
  } else {
    console.log(`No se encontró un objeto con el id "${id}".`);
  }

  // Devuelve la nueva lista sin modificar la original
  //return updatedCollectionItems;  
  collectionItems.value = updatedCollectionItems
} 

const showLogInForm = ref(true);
const showRegisterForm = ref(false);

function changeShowLogInValue() {
  showLogInForm.value = !showLogInForm.value; 
}

function updateItem(updateBook) {
  for(let i = 0; i < collectionItems.value.length; i++) {
    if(collectionItems.value[i].id == updateBook.id) {
      console.log("libro actual:", collectionItems.value[i]);
      collectionItems.value[i] = updateBook;
      console.log("libro actualizado:", collectionItems.value[i]);
    }
  }
  //collectionItems.value = updatedCollectionItems
} 


function changeShowRegisterValue() {
  showRegisterForm.value = !showRegisterForm.value; 
}

function changeBothFormValues() {
  showLogInForm.value = !showLogInForm.value; 
  showRegisterForm.value = !showRegisterForm.value; 
}

</script>

<template>
  <NavBar v-if="!showLogInForm && !showRegisterForm" 
    @show-log-in="changeShowLogInValue"
    @show-register="changeShowRegisterValue"
  />
  <AddItem v-if="!showModal && !showLogInForm && !showRegisterForm" @add-book="modifyShowModal" />
  <Modal v-if="showModal" @save-data="saveData" @cancel="modifyShowModal" />
  <LogInForm v-if="showLogInForm" @get-back-home="changeShowLogInValue" 
    @go-to-register="changeBothFormValues"
  />
  <RegisterForm v-if="showRegisterForm" @get-back-home="changeShowRegisterValue"
    @go-to-log-in="changeBothFormValues"
  />
  <div v-if="!showLogInForm && !showRegisterForm">
    <h1>Your collection:</h1>

  </div>
  <div id="itemsContainer">  
      <BookItem v-for="item in collectionItems"  v-bind="item" @delete-item="deleteItem" @update-item="updateItem" />
  </div>
</template>

<style scoped>
  #itemsContainer {
    display: grid;
    grid-template-columns: 23% 23% 23% 23%;
    gap: 20px;
  }



</style>
