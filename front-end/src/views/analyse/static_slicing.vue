<template>
  <div class="static_slicing_container">
      <div  class="file_selector">
      选择文件:
              <select
              v-model="selectFile.id" 
              @change="getfileid(selectFile.id)" style="width: 200px;">
        <option 
          class="choose_file" 
              v-for="item in fileData"
              :key="item.fileId" 
              :label="item.fileName"
              :value="item.fileId"
              >
          </option>
              </select>
      </div>
      <div class="input_x">
        选择输入变量:<input class="java_input" type="text" v-model="Variable" style="width: 100px;">
        <button @click="input_var">确定</button>
        <p></p>
        选择输入行数:<input class="java_input" type="number" v-model="RowNumber"  min="1" style="width: 100px;">
        <button @click="input_row">确定</button>
      </div>
      <div class="displayArea" >
        <pre v-highlightjs  class="displayCode"><code>{{code}}</code></pre>
        <pre v-highlightjs  class="displaySlice"><code>{{slice_code}}</code></pre>
      </div>
  </div>
</template>

<script>
export default {
  data(){
    return {
      code:'import { createApp } from "vue"; \nimport App from "./App.vue";',//这里放要展示的代码
      slice_code:'import { createApp } from "vue"; \nimport App from "\n./App.vue";',//切片后的代码
      Variable:'',
      RowNumber:'',
      fileData:[
        {
          fileId:'111',
          fileName:'test1',
          file_time:'2022.0.1'
        },
        {
          fileId:'222',
          fileName:'test2',
          file_time:'2022.0.1'
        },
        {
          fileId:'333',
          fileName:'test3',
          file_time:'2022.0.1'
        },
      ],
      selectFile:{
        id:''
      }
    }
  },
  methods:{
    getfileid(val){
      console.log(val)
    },
    input_var(){
      console.log('variable',this.Variable)
    },
    input_row(){
      console.log('rowNumber',this.RowNumber)
    }
  }

}
</script>


<style lang="scss" scoped>
.static_slicing_container{
  min-height: 100%;
  width: 100%;
  background-image:url('../../assets/bg-image2.png');
  background-size:100%;
  position: fixed;
}
</style>

<style>
.file_selector{
  position: absolute;
  left:0%;
  top:10%;
}
.displayCode{
  left:15%;
  top:20%;
  width: 400px;
  height: 500px;
  position:fixed;
  background-color:rgb(40, 44, 52);
}
.displaySlice{
  left:65%;
  top:20%;
  width: 400px;
  height: 500px;
  position:fixed;
  background-color:rgb(40, 44, 52);
}
</style>