<template>
  <button v-on:click="start" id="startButton">Start Game</button>
    <div class="board">
      <WordComponent class="row" v-for="(item, index) in words" :key="index"  :word="item"  v-on:word-sended="wordSended"/>
    </div>
</template>
<script>


import WordComponent from "@/components/Word";
import {API} from '../api';

export default {
  name: 'GameView',
  data : function () {
     return {
      words: this.initWords(),
      results:[]
    };
  },
  components: {WordComponent},
  methods: {
    initWords: function () {
      let words = [];
      for(let i=0; i< 6; i++) {
        words.push(String.fromCharCode(160).repeat(5));
      }
      return words;
    },
    start: function () {
      this.words = this.initWords();
      this.results = [];
      API.nextWord([], response => {
        this.words[0] = response.data.word.toUpperCase();
      })
    },
    wordSended: function (data) {
      this.results.push(data);
      API.nextWord(this.results, response => {
        this.words[this.results.length] = response.data.word.toUpperCase();
      })
    }
  }
}
</script>
<style>

#startButton {
  margin-bottom: 40px;
  width: 322px;
  height: 50px;
  margin-right: 69px;
  border-radius: 5px;
  border-width: 1px;
  font-weight: bold;
  color: blue;
  font-size: 17px;
}

.row {
  margin-bottom: 10px;
  overflow: hidden;
}
.board {
  /*width: 100%;*/
  /*height: 100%;*/
  width: 400px;
  margin: auto;
  /*overflow: hidden;*/
}

</style>