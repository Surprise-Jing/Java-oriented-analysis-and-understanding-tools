<template>
  <div  class="file_selector">
      <!--只需要双向绑定代码块即可-->
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
  </div>
</template>

<script>
import {getFile, getFileContext} from "@/api/file"
import CodeEdit from "@/components/CodeEdit";
export default {
  components: {CodeEdit},
  data() {
    return {
      content: '', // 代码块
      fileData:[],
      selectFile:{
        id:''
      },
    }
  },
  methods: {
    // 获取代码
    getCode() {
      console.log(this.content)
    },
    getfilecontext(val){
      getFileContext(val).then(res => {
          if(res.success){
            this.content = res.data.content;
            console.log(this.code);
            this.$forceUpdate()
          }
          else{
            this.code = '程序加载有误，请重新选择文件'
          }});
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
