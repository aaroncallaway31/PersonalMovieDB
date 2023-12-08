<template>
  <div>
    <div v-for="movie in moviesProp" v-bind:key="movie.movie_id">
      <div>{{ movie.title }}</div>
      <img v-bind:src="movie.poster" />
    </div>
  </div>
</template>

<script>
export default {
  props: ["moviesProp"],
};
</script>

<style scoped></style>
