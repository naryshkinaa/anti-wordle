<template>

  <div class="infoRoot">
    <h3> Правила <button id="closeButton" src="@/images/info.png" v-on:click="close"> X </button></h3>
    <div> Загадайте слово (например РЕЗКА)</div>
    <br/>
    <div> Нажимайте на квадраты в соответствии с правилами WORDLE</div>
    <div> для предложенного слова</div>
    <img id="word" src="@/images/word.png">
    <div>Обращайте внимание если в слове несколько одинаковых букв!</div>
    <br/>
    <div>Сможете придумать слово которое не отгадает программа?</div>

  </div>

</template>
<script>

export default {
  name: 'InfoComponent',
  methods: {
    close: function () {
      this.$emit("close-info");
    }
  }
}
</script>
<style>
.infoRoot {
  position: absolute;
  left: 30%;
  top:70px;
  width: 40%;
  height: calc(90% - 70px);
  background-color: lightblue;
}
#closeButton {
  float: right;
  margin-right: 10px;
}
#word {
  margin-top: 20px;
  margin-bottom: 20px;
  width: 340px;
  height: 95px;
}
</style>