<script setup>
    import { ref, defineEmits } from 'vue';
    import apiClient from '@/axiosConfig';
    const router = useRouter();
    import { useRouter, useRoute } from 'vue-router';


  
    // Defino los eventos que va a emitir a el componente a su padre
    const emit = defineEmits(['logOut'])

    function logOutEvent() {
        emit('logOut')
    }

  const handleLogOut = async() => {
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
    <div id="sideMenu">
        <ul>
            <li @click="handleLogOut()" class="custom-li">Log Out</li>
        </ul>
    </div>
</template>

<style scoped>
    @media screen and (min-width: 481px) {
        

      div {
          display: none
      }

    }

    @media screen and (max-width: 480px) {

      ul {
         /* Cambia el puntito por un cuadrado */
          border-style: solid none;
          border-width: 1px;
          padding-top: 10px;
          padding-bottom: 10px;
      }
      
      .custom-li {
        color: red; /* Cambia a cualquier color que desees */
        cursor: pointer; /* Opcional: hace que el cursor cambie al pasar sobre el elemento */
        text-align: start;
        font-size: 20px;
      }
    } 

</style>