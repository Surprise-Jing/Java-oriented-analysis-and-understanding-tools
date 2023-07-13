<template>
  <!-- <el-form ref="form" :model="form" label-width="80px" class="frame">
    <el-form-item label="选择文件"></el-form-item>
  <div class="static_slicing_container">
    选择文件：
    <el-select v-model="selectFile.id" filterable placeholder="请选择">
      <el-option
        v-for="item in fileData"
        :key="item.fileId"
        :label="item.fileName"
        :value="item.fileId">
      </el-option>
    </el-select>
    <div class="input_x">
      输入切片行号：
    <el-input v-model="lineNumber" placeholder="请输入切片行号" size="medium"></el-input>
    <div class="input_x">
      输入切片变量：
    <el-input v-model="variable" placeholder="请输入切片变量"></el-input> -->
    <div class="static_slicing_container">
      <div  class="file_selector">
      选择文件:
              <select
              v-model="selectFile.id" 
              @change="getfilecontext(selectFile.id)" style="width: 200px;">
        <option 
          class="choose_file" 
              v-for="item in fileData"
              :key="item.id" 
              :label="item.fileName"
              :value="item.id"
              >
          </option>
              </select>
      </div>
      <div  class="method_selector">
      选择切片方法:
              <select
              v-model="selectMethod.id" 
              @change="selectMethod.id" style="width: 200px;">
        <option 
          class="choose_file" 
              v-for="item in methodData"
              :key="item.methodId" 
              :label="item.methodName"
              :value="item.methodId"
              >
          </option>
              </select>
      </div>
      <div class="input_x">
        选择输入变量:<input class="java_input" type="text" v-model="variable" style="width: 100px;">
        <button @click="input_var">确定</button>
        <p></p>
        选择输入行数:<input class="java_input" type="number" v-model="lineNumber"  min="1" style="width: 100px;">
        <button @click="input_row">确定</button>
      </div>
      <div class="displayArea" >
        <pre v-highlightjs  class="displayCode"><code>{{code}}</code></pre>
        <pre v-highlightjs  class="displaySlice"><code>{{slice_code}}</code></pre>
      </div>
</div>
</template>

<script>
import {getFile, getFileContext} from "@/api/file"

export default {
  data(){
    return {
      input: '',
      code:'',//这里放要展示的代码
      slice_code:'',//切片后的代码
      variable:'',
      lineNumber:'',
      fileData:[],
      selectFile:{
        id:''
      },
      methodData:[
        {
          methodId:'1',
          methodName:'基于数据流方程的切片',
        },
        {
          methodId:'2',
          methodName:'基于控制依赖图的切片'
        },
      ],
      selectMethod:{
        id:''
      }
    }
  },
  watch:{
    code(newValue){
      this.code = newValue;
    },
    slice_code(newValue){
      this.slice_code = newValue;
    },
    immediate: true
  },
  methods:{
    getfilecontext(val){
      getFileContext(val).then(res => {
          if(res.success){
            this.code = res.data.content
          }
          else{
            this.code = '程序加载有误，请重新选择文件'
          }});
      console.log(val)
    },
    input_var(){
      console.log('variable',this.variable)
    },
    input_row(){
      console.log('rowNumber',this.lineNumber)
    }
  },
  mounted() {
      getFile(localStorage.getItem("uid")).then(res => {
        if(res.success){
          this.fileData = res.data
          console.log(this.fileData)
        }
        else{
          this.$message({
            type:'warning',
            message: res.msg
          });
        }
      })
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
.method.selector{
  position: absolute;
  left:0%;
  top:10%;
}

</style>