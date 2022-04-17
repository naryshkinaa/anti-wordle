import axios from "axios";

export const API = {
    nextWord: (words, callback) => axios
        .post('next-word', {"words": words})
        .then(callback),
}

