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
            <CodeEdit v-model="content" />
          <el-button @click="getCode">获取代码</el-button>
        <el-select v-model="selectFile.id" @change="getfilecontext(selectFile.id)" placeholder="请选择">
          <el-option
          v-for="item in fileData"
                    :key="item.id" 
                    :label="item.fileName"
                    :value="item.id">
          </el-option>
        </el-select>
      <!-- <div  class="file_selector">
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
        <p></p>
        选择输入行数:<input class="java_input" type="number" v-model="lineNumber"  min="1" style="width: 100px;">
        <button @click="input_res">确定</button>
      
      </div>
      <div class="displayArea"  >
        <div  v-highlight class="displayCode" ><pre ><code > {{code}}</code></pre></div>
        <div v-highlight class="displaySlice"><pre><code> {{code}}</code></pre></div>
      </div> -->
</div>
</template>

<script>
import {getFile, getFileContext} from "@/api/file"

export default {
  data(){
    return {
      codeShow:true,
      input: '',
      code:'int main(){\nreturn 0;\n}',//这里放要展示的代码
      //code:'',
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
    code:{
      handler(val){
        if(val && val != this.code){
          this.code = val;
          this.$nextTick(this.refresh);
        };
      }
    },
    immediate: true
  },
  methods:{
    updataCode(){
      this.codeShow=false;
      this.$nextTick(function(){
			// 加载
			this.codeShow= true
		})
    },
    getfilecontext(val){
      getFileContext(val).then(res => {
          if(res.success){
            this.code = res.data.content;
            //this.$set(data, 'code', res.data.content);
            console.log(this.code);
           // this.$forceUpdate()
           this.updataCode()
          }
          else{
            this.code = '程序加载有误，请重新选择文件'
          }});
    },
   
    input_res(){
      console.log('var',this.variable)
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
  background-image:url('../../assets/bg-image.png');
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
  overflow-y: scroll;
}
.displaySlice{
  left:65%;
  top:20%;
  width: 400px;
  height: 500px;
  position:fixed;
  background-color:rgb(40, 44, 52);
  overflow-y: scroll;
}
.method.selector{
  position: absolute;
  left:0%;
  top:10%;
}

</style>