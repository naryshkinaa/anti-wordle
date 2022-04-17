<template>
  <div class="header">
    <h2>ANTI WORDLE (RU)</h2>

<!--    <br>-->
  </div>
  <GameView ref="childComponent"/>
</template>
<script>
import GameView from './components/Game.vue';


export default {
  name: 'app',
  components: {
    GameView
  }
}
</script>
<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  width: calc(100% - 16px);
  height: calc(100% - 16px);
}

.header {
  margin-right: 60px;
}
</style>