<script setup>
import NavBar from './components/NavBar.vue'
import AddItem from './components/AddItem.vue'
import Modal from './components/Modal.vue'
import BookItem from './components/BookItem.vue'
import LogInForm from './components/LogInForm.vue'
import RegisterForm from './components/RegisterForm.vue'
import Home from './components/Home.vue'
import apiClient from '@/axiosConfig';
import { createMemoryHistory, createRouter } from 'vue-router'
import { RouterLink, RouterView } from 'vue-router'


import { ref } from 'vue'


// La variable showModal va a controlar si se muestran o no los componentes AddItem y Modal
// cuando uno se muestra el otro no y viceversa
const showModal = ref(false)
const showLogInForm = ref(true);
const showRegisterForm = ref(false);
const showLoadingAnimation = ref(false);

// Esta variable guarda los items de la colección, es una lista de objetos con los datos de cada libro
const collectionItems = ref([])


function modifyShowModal() {
  showModal.value = !showModal.value
}

function changeShowLogInValue() {
  showLogInForm.value = !showLogInForm.value; 
}

function changeShowRegisterValue() {
  showRegisterForm.value = !showRegisterForm.value; 
}

function changeBothFormValues() {
  showLogInForm.value = !showLogInForm.value; 
  showRegisterForm.value = !showRegisterForm.value;
}


const reloadBookLIst = async() => {
  try {
    const response = await apiClient.get('/books');
    //console.log(response.data);
    collectionItems.value = response.data; 
  } catch(error) {
    //console.error(error);
  }
}

function saveData(data) {
  let bookData = data
  //console.log("te saludo desde el escuchador del evento", bookData)
  //console.log(collectionItems.value)
  collectionItems.value = [...collectionItems.value, bookData]
  //console.log(collectionItems.value)
  modifyShowModal()
}

function deleteItem(id) {
  // Filtra la lista y crea una nueva lista sin el objeto con el título proporcionado
  const updatedCollectionItems = collectionItems.value.filter(item => item.id !== id);

  // Verifica si se eliminó algún elemento
  if (updatedCollectionItems.length !== collectionItems.length) {
    //console.log(`Objeto con el id "${id}" eliminado.`);
  } else {
    //console.log(`No se encontró un objeto con el id "${id}".`);
  }

  //console.log(updatedCollectionItems);

  collectionItems.value = updatedCollectionItems
} 

function updateItem(updateBook) {
  for(let i = 0; i < collectionItems.value.length; i++) {
    if(collectionItems.value[i].id == updateBook.id) {
      //console.log("libro actual:", collectionItems.value[i]);
      collectionItems.value[i] = updateBook;
      //console.log("libro actualizado:", collectionItems.value[i]);
    }
  }
  //collectionItems.value = updatedCollectionItems
} 

function succesfullLogin() {
  reloadBookLIst();
  changeShowLogInValue();
  showLoadingAnimation.value = true;
  setTimeout(() => { showLoadingAnimation.value = false; }, 2000);
}

function logOutHandler() {
  collectionItems.value = [];
}

</script>

<template>
  <RouterView />
</template>

<style scoped>
  #itemsContainer {
    display: grid;
    grid-template-columns: 23% 23% 23% 23%;
    gap: 20px;
  }

  #yourCollectionDiv {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }


  button {
    background-color: seagreen;
    color:white;
    font-size: 15px;
    border-radius: 5px;
    border-style: none;
    height: 90%;
    padding: 10px;
  }

</style>
