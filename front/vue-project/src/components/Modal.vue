<script setup>
  import { defineEmits, ref } from 'vue'
  import apiClient from '@/axiosConfig';
  /* 
    Declaro los dos eventos que va a emitir el componente, saveData que se va a emitir cuando se haga click en el botón save del modal, y cancel que se va a emitir cuando se haga click en el botón 
    cancel del modal
  */
  const emit = defineEmits(['saveData', 'cancel', 'sessionExpired'])

  const pagesMustBeANumberError = ref(false);

  // función que emite el evento cancel
  function cancelEvent() {
    emit('cancel')
  }

  // variable para guardar los datos del libro
  let bookData = {
    title: '',
    author: '',
    pages: null,
    read: false,
    id: null,
    savedDate: null,
  }

  function isNumeric(str) {
    return /^\d+$/.test(str);
  }

  // función que emite el evento saveData
  function saveDataEvent() {
    emit('saveData', bookData)
    //console.log("hola desde el emitidor del evento", bookData)
    bookData = {
    title: '',
    author: '',
    pages: null,
    read: false,
    id: null,
    savedDate: null,
    }
  }

  function getDate() {
    const today = new Date();
    const day = String(today.getDate()).padStart(2, '0'); // Día con dos dígitos
    const month = String(today.getMonth() + 1).padStart(2, '0'); // Mes con dos dígitos (0 indexado)
    const year = today.getFullYear(); // Año completo
    const formattedDate = `${day}/${month}/${year}`;
    //console.log(formattedDate); 
    return formattedDate;

  }

  // Código para hacer la petición POST al enviar el formulario 
    
    const submitForm = async () => {
      if(!isNumeric(bookData.pages)) {
        pagesMustBeANumberError.value = true;
        return;
      }

      try {
        
        bookData.savedDate = getDate();
        // Realizo la petición POST
        const response = await apiClient.post('/books', bookData);
      
        // Manejo la respuesta 
        //console.log(response.data);
        // Me guardo en bookData el id que le asignó la base de datos
        //id = response.data._links.self // por alguna razón no puedo realizar esta asignación, la variable aparece como undefined
        //console.log(id)
        //bookData.id = .split('/')[4]
        //console.log("id:", bookData.id) 
        bookData.id = response.data.id;
        // Ejecuto la función saveDataEvent que emite el evento saveData para que lo escuche el componente App.vue, y restablece los valores iniciales de bookData 
        saveDataEvent();
      } catch (error) {
        // Maneja el error según tus necesidades
        //console.error(error);
        if(error.response.status == 403 || error.response.status == 401) {
          emit('sessionExpired');       
        }
      }
    };

  /* 
    Comentario acerca de la lógica del componente: Me surge la duda de si es necesario hacer que bookData sea un ref, 
    en mi criterio esto no es necesario y justamente busqué evitarlo para poder manejar todos los refs desde App.vue, 
    pues no creo que sea necesario volver a renderizar cada vez que se cambien los valores del formulario, 
    entonces lo que hago es que al enviar los datos en el evento al pulsar save establezco bookData en los valores 
    iniciales y entonces cuando se vuelve a renderizar el formulario los campos están en blanco. 
    ¡OJO! ESTO SOLO ES POSIBLE PORQUE EL FORMULARIO SE ESCONDE LUEGO DE ENVIAR LA INFO PUES ES UN MODAL, 
    EN CASO DE QUE NO SEA ASÍ QUIZÁS SI SEA NECESARIO QUE bookData SEA UN REF O BUSCAR OTRA FORMA DE LIMPIAR LOS DATOS 
    DEL FORM
  */
</script>

<template>
  <div>
    
    <form @submit.prevent="submitForm">
      <h1>Add book</h1>
      <label>
        <input type="text" placeholder="Title" v-model="bookData.title" required>
      </label>
      <label>
        <input type="text" placeholder="Author" v-model="bookData.author" required> 
      </label>
      <label>
        <input type="text" placeholder="Pages" v-model="bookData.pages" required>
      </label>
      <label id="checkbox">
        <input type="checkbox" v-model="bookData.read" id="checkboxInput"> Read?
      </label>
      <p v-if="pagesMustBeANumberError">Pages must be an integer number</p>
      <button type="submit"> Save </button>
      <button @click="cancelEvent"> Cancel </button>
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
    row-gap: 15px;
    width: 50%;
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

  #checkbox {
    font-size: 20px;
  }

  #checkboxInput {
    width: 10%;
  }

  p {
    color: red;
  }


  @media screen and (max-width: 480px) {
    form {
      display: flex;
      flex-direction: column;
      row-gap: 15px;
      width: 100%;
      border-style: solid none;
      border-width: 1px;
      border-radius: 0px;
      padding: 25px 15px;
    }  

    input {
      font-size: 20px;
      padding: 5px;
      width: 100%;
    }
  }

  @media screen and (max-width: 768px) and (min-width: 481px) {
    form {
      display: flex;
      flex-direction: column;
      row-gap: 15px;
      width: 60%;
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
      
  }

</style>
