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
      console.log(response.status);
      localStorage.setItem('jwt_token', null);
      router.replace('/')
    } catch(error) {
      console.error(error);
    }
  }
</script>

<template>
    <div id="sideMenu">
        <ul>
            <li @click="handleLogOut()"><a href="#">Logout</a></li>
        </ul>
    </div>
</template>

<style scoped>
    @media screen and (min-width: 480px) {
        

    div {
        display: none
    }
    }

</style>