<template>
<div id="app">
    <router-view/>
</div>
</template>

<script>


export default {
    name: 'App'
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Prompt:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');

html{
  height: 100%;
  margin:0;
}
body{
  height:100%;
  margin:0;
  background: rgb(252 254 255)!important;
}
#app {
    font-family: 'Prompt', sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
   
    height: 100%;
}

.main-div {
  padding: 10px;
    background: #fcfeff;
    border-radius: 32px 32px 0px 0px;
    margin-top: -64px;
    box-shadow: 0px -4px 3px 0px #2a3e4c;
    height: calc(100% - 78px);
    text-align: left;
}

.page-title {
    font-weight: 600;
    font-size: 20px;
    padding-left: 20px;
    padding-bottom: 20px;
    padding-top: 8px;
}
select {
    word-wrap: normal;
    background: #ffffff;
    height: 40px;
    margin-bottom: 10px;
    border-radius: 4px;
    border: 1px solid #7a80864a;
    color: #3f5a6b;
    font-size: 14px;
}

select:focus-visible {
  border:1px solid #7a80865d;
  box-shadow: none;
}

.title-w-button {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    padding-right: 20px;
}



</style>
