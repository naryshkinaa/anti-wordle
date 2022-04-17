<template>
  <div class="wordRoot">


    <div class="wordWrapper">
      <div v-for="(item, index) in word" v-on:click="click(index)" :key="index" class="char"
           :class="'state' + result[index]">
        <div class="charInline "> {{ item }}</div>
      </div>
    </div>
    <div class="buttonWrapper">
      <button v-if="!isEmpty && !isSend" v-on:click='send' class="sendResult"><b>send</b></button>
    </div>
  </div>

</template>
<script>

export default {
  name: 'WordComponent',
  props: ["word"],
  data: function () {
    return {
      isEmpty: true,
      isSend: false,
      result: [0, 0, 0, 0, 0]
    }
  },
  computed: {},
  methods: {
    send: function () {
      this.isSend = true;
      let chars = [];
      var finish = true;
      for (let i = 0; i < 5; i++) {
        if (this.result[i] != 2) finish = false;
        chars.push({
          char: this.word[i],
          result: this.result[i]
        })
      }

      if (finish) alert("Ваше слово отгадано! Попробуйте новое слово")
      else this.$emit("word-sended", {chars: chars});
    },
    click: function (index) {
      if(!this.isEmpty && !this.isSend) this.result[index] = (this.result[index] + 1) % 3
    }
  },
  watch: {
    word: function (newValue) {
      this.isEmpty = newValue === String.fromCharCode(160).repeat(5);
      if (this.isEmpty) {
        this.result = [0, 0, 0, 0, 0];
      }
      this.isSend = false;
    }
  }
}
</script>
<style>

.wordRoot {
  width: 400px;
}

.char {
  height: 70px;
  width: 60px;
  background-color: grey;
  display: inline-block;
  margin-left: 5px;
  font-size: 40px;
  font-weight: bold;
  color: white;
  -webkit-touch-callout: none; /* iOS Safari */
  -webkit-user-select: none; /* Safari */
  -khtml-user-select: none; /* Konqueror HTML */
  -moz-user-select: none; /* Old versions of Firefox */
  -ms-user-select: none; /* Internet Explorer/Edge */
  user-select: none;
  /* Non-prefixed version, currently
                                   supported by Chrome, Edge, Opera and Firefox */
}

.state0 {
  background-color: grey;
}

.state1 {
  background-color: #BDA747;
}

.state2 {
  background-color: #599D5B;
}
.wordWrapper {
  float: left
}

.buttonWrapper {
  display: inline-block;
  margin-left: 10px;
  float: left;
  margin-top: 18px;
  /*width: 200px;*/
  /*height: 40px;*/
}

.sendResult {
  width: 50px;
  height: 30px;
  color: blue;
  font-size: 15px;
  border-radius: 5px;
  border-width: 1px;
}

@media only screen and (max-width: 600px) {
  .sendResult {
    width: 50px;
    height: 40px;
    font-size: 11px;
  }
}

.charInline {
  margin-top: 10px;
}
</style>