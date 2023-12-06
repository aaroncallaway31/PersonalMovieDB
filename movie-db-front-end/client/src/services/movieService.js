import axios from "axios";

export default {
    getMovies() {
        return axios.get("/movies");
    }
}