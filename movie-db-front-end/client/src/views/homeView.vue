<template>
  <div>
    <movie-list v-bind:moviesProp="movies"></movie-list>
  </div>
</template>

<script>
import movieService from "../services/movieService";
import movieList from "../components/movieList.vue";

export default {
  data() {
    return { movies: [] };
  },
  components: {
    movieList
  },
  created() {
    movieService.getMovies().then((response) => {
      this.movies = response.data;
    });
  },
};
</script>

<style scoped></style>
