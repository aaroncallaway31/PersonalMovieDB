import { createRouter as createRouter, createWebHistory } from 'vue-router';
import { useStore } from 'vuex';

// Import components
import homeView from '../views/homeView.vue';


/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */
const routes = [
  {
    path: "/",
    name: "home",
    component: homeView,
  },
];


// Create the router
const router = createRouter({
  history: createWebHistory(),
  routes: routes
});

router.beforeEach(() => {

  // Get the Vuex store
  const store = useStore();

  // Clear error notifications from previous view
  store.commit("CLEAR_MESSAGE");
 
});

export default router;
