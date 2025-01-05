<script setup>
</script>


<template>  
    <div class="animation">

    </div>
</template>

<style scoped> 	
    .animation {
        margin: 50px auto;
        border: 8px solid #fefefe;
        border-top: solid 8px hsla(160, 100%, 37%, 1);
        width: 55px;
        height: 55px;
        border-radius: 50%;
        animation: loading 1s infinite linear
    }

    @keyframes loading {
        0% {
            transform: rotate(0deg);
        }

        100% {
            transform: rotate(360deg);
        }
    }
</style>