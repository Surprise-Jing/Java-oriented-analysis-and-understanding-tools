
<template>
  <div class="upload_container">
    <div id="app">
    <div class="content">
      <div class="drag-area" @dragover="fileDragover" @drop="fileDrop">
        <div v-if="fileName" class="file-name">{{ fileName }}</div>
        <div v-else class="uploader-tips">
          <span style="font-size: large;">将文件拖拽至此，或</span>
          <label for="fileInput" style="color: #11A8FF; cursor: pointer;font-size: large;">点此上传</label>
        </div>
      </div>
    </div>
 
    <div class="footer">
      <input type="file" id="fileInput" @change="chooseUploadFile" style="display: none;">
     
      <el-button class="uploadbtn" @click="uploadOk">提交</el-button>
      <el-button class="cleanbtn" @click="cleanFile">清空</el-button>
    </div>
  </div>
  </div>
  
</template>


<script type="text/javascript" src="../../main.js"></script>
<script >
import { uploadFile } from '@/api/file'

export default {
  data () {
    return {  
      fileName: '',
      batchFile: '',
      MAX_FILE_SIZE: 10 * 1000 * 1000
    }
  },
  
  methods: {
    // 点击上传
    chooseUploadFile (e) {
      const file = e.target.files.item(0)

       if (!file) return
       console.log(file.type)
      if(file.type!=""){
        return alert('文件格式不符合要求')
      }
      this.batchFile = file
      this.fileName = file.name

      // 清空，防止上传后再上传没有反应
      e.target.value = ''
    },
    // 拖拽上传
    fileDragover (e) {
      e.preventDefault()
    },
    fileDrop (e) {
      e.preventDefault()
      const file = e.dataTransfer.files[0] // 获取到第一个上传的文件对象
      console.log(file)
      console.log('拖拽释放鼠标时')

      if (!file) return
      if(file.type!=""){
        return alert('文件格式不符合要求')
      }
      this.batchFile = file
      this.fileName = file.name
    },
    // 提交
    uploadOk () {
      if (this.batchFile === '') {
        return alert('请选择要上传的文件')
      }
      let data = new FormData()
      data.append('uid', localStorage.getItem("uid"))
      data.append('file', this.batchFile)
      uploadFile(data).then(res =>{
            console.log(res)
            if(res.success){
              localStorage.setItem("id", res.data.id)
              this.$message.success("文件上传成功")
              this.$router.push("/")
            }
            else{
              this.$message({
                type:'warning',
                message:res.msg
              });
            }
          })
    },
    cleanFile(){
      this.fileName='';
      this.batchFile='';
    }
   
  }

}
</script>

<style lang="less" scoped>
.upload_container{
  min-height: 100%;
  width: 100%;
 // background-image:url('../../assets/bg-image.png');
  background-size:100%;
  position: fixed;
}
    * {
      font-size: 14px;
    }
    
    .drag-area {
      height: 300px;
      width: 700px;
      border: solid 1px darkslategrey;
      margin-bottom: 10px;
      color: #777;
      position: absolute;
      left:45%;
      top:30%;
      transform: translate(-50%,-50%);
      //background-color:darkslategrey;
     //background-color: darkgray;
      opacity: 0.75;
      border-radius: 5%;
    }
    .uploader-tips {
      text-align: center;
      height: 200px;
      line-height: 200px;
      //color:white;
     // color:darkgray;
      color:black;
    }
    .file-name {
      text-align: center;
      height: 200px;
      line-height: 200px;
    }
    .uploadbtn{
      position: absolute;
      left:40%;
      top:60%;
      height: 50px;
      width:70px;
      transform: translate(-50%,-50%);
      color:darkgray;
      background-color: darkslategray;
      border-color: black;
      font-size: large;
    }
    .cleanbtn{
      position: absolute;
      left:50%;
      top:60%;
      height: 50px;
      width:70px;
      transform: translate(-50%,-50%);
      color:darkgray;
      background-color: darkslategray;

      border-color: black;
      font-size: large;
    }

</style>
