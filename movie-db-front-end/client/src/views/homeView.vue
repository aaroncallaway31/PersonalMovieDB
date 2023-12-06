<template>
  <div>
    <div v-for="movie in movies" v-bind:key="movie.movie_id">
      <div>{{movie.title}}</div>
      <img v-bind:src="movie.poster">
    </div>
  </div>
</template>

<script>
import movieService from "../services/movieService";

export default {
  data() {
    return { movies: [] };
  },
  created() {
    movieService.getMovies().then((response) => {
      this.movies = response.data;
    });
  },
};
</script>

<style scoped></style>
