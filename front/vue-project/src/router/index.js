import { createRouter, createWebHistory } from 'vue-router'

import LogInForm from '../components/LogInForm.vue';
import RegisterForm from '../components/RegisterForm.vue';
import Home from '../components/Home.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'login',
      component: LogInForm,
    },
    {
      path: '/register',
      name: 'register',
      component: RegisterForm,
    },
    {
        path: '/home',
        name: 'home',
        component: Home,
        beforeEnter(to, from, next) {
          let isAuthenticated = null; /* Your authentication check code here */
          const token = localStorage.getItem('jwt_token');
          if(token.startsWith("Bearer")) {
            isAuthenticated = true;
          } else {
            isAuthenticated = false;
          }
          if (isAuthenticated) {
            next()
          } else {
            next('/')
          }
        }
    }
  ],
})

export default router;
