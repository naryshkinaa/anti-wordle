<template>
  <div class="header">
    <h2><img id="info" src="@/images/info.png" :onclick="showInfo"/> ANTI WORDLE (RU)</h2>
    <InfoComponent v-if="isShow" v-on:close-info="closeInfo" ref="childComponent"/>
<!--    <br>-->
  </div>
  <GameView ref="childComponent"/>
</template>
<script>
import GameView from './components/Game.vue';
import InfoComponent from "@/components/Info";


export default {
  name: 'app',
  data: function (){
    return {
      isShow: false
    }
  },
  components: {
    InfoComponent,
    GameView
  },
  methods: {
    showInfo: function (){
      this.isShow = true
    },
    closeInfo: function (){
      this.isShow = false
    }
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

#info{
  vertical-align: middle;
  width: 28px;
  height: 28px;
}
.header {
  margin-right: 60px;
}
</style>