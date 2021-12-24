import { createRouter, createWebHistory } from 'vue-router'
import Login from './components/Login.vue'
import Register from './components/Register.vue'
import ActiveTodos from './components/ActiveTodos.vue'
import CompletedTodos from './components/CompletedToDos.vue'
import Groups from './components/Groups.vue'

const routes = [
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/active', component: ActiveTodos, meta: { requiresAuth: true } },
    { path: '/completed', component: CompletedTodos, meta: { requiresAuth: true } },
    { path: '/groups', component: Groups, meta: { requiresAuth: true } },
    {
        path: "/:catchAll(.*)", // Unrecognized path automatically matches 404
        redirect: '/login',
    }
]


const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    const requiresAuth = to.matched.some(record => record.meta.requiresAuth);
    const isAuthenticated = localStorage.getItem('user');

    if (requiresAuth && !isAuthenticated) {
        next("/login");
    } else {
        next();
    }
})

export default router