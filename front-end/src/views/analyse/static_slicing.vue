<template>
  <div class="slice_container">
    <div  class="file_selector">
        <!--只需要双向绑定代码块即可-->
          <span style="position: fixed;left:15%;top:16%;font-size: large;color: gray;">源代码:</span>
        <span style="position: fixed;left:55%;top:16%;font-size: large;color: gray;">切片结果:</span>
      <div style="position: fixed;">
        <CodeEdit v-model="content1" class="show_code"/>
        <CodeEdit2 v-model="content2" class="show_slicecode"/>
      </div>





      <el-button type="text" @click="dialogVisible = true">点击打开使用说明</el-button>
      <el-dialog
        title="静态切片使用说明"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose"
        append-to-body>
        静态切片支持包含顺序Sequence、条件if-else、循环while/for/foreach、分支switch、跳转break/continue <br><br>
        输入参数： 行号n，变量名v <br><br>
        注意：<br>
        1、该软件系统只能对没有错误的Java文件进行静态切片。<br>
        2、切片时请输入正确的代码行数和变量，且变量只能有一个。<br>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
        </span>
      </el-dialog>

      <div class="input_x">
        请输入行数:<el-input  type="number" min="1" class="getrow" v-model="rowNumber"></el-input>
        <br>
        请输入变量:<el-input class="getvar" v-model="variable"></el-input>
        <el-button @click="input_ok">确定</el-button>
      </div>
      
      <div class="choose_file" >
            选择文件:
        <el-select v-model="selectFile.id" @change="getfilecontext(selectFile.id)" placeholder="请选择">
          <el-option
          v-for="item in fileData"
                    :key="item.id"
                    :label="item.fileName"
                    :value="item.id">
          </el-option>
        </el-select>
        <br>
        选择方法:
        <el-select v-model="selectMethod.id" @change="getMethod(selectMethod.id)" placeholder="请选择">
          <el-option
          v-for="item in selectMethod"
                    :key="item.id"
                    :label="item.methodName"
                    :value="item.id">
          </el-option>
        </el-select>
      </div>


    </div>
  </div>
</template>

<script>
import {getFile, getFileContext} from "@/api/file"
import {DataFlowSlicer, PDGSlicer} from "@/api/slicer"
import CodeEdit from "@/components/CodeEdit";
import CodeEdit2 from "@/components/CodeEdit2";
export default {
  components: {CodeEdit,CodeEdit2},
  data() {
    return {
    dialogVisible: false,
     content1:'',
     content2:'',
      variable:'',
      rowNumber:'',
     // content: '', // 代码块
      fileData:[],
      selectFile:{
        id:''
      },
      selectMethod:[{
        id:'1', methodName : "基于数据流方程的切片"
      },
    {
      id:'2', methodName: '基于程序依赖图的切片'
    }]
    }
  },
  methods: {
    // 获取代码
    input_ok() {

      if(this.getMethod.id == 1){
        DataFlowSlicer(this.selectFile.id, this.rowNumber, this.variable).then(res => {
          if(res.success){
            this.content2 = res.data.result;
            //console.log(this.code);
           // this.$forceUpdate()
          }
          else{
            this.$message({
            type:'warning',
            message: res.msg
          });
          }})
        }
      else{
        PDGSlicer(this.selectFile.id, this.rowNumber, this.variable).then(res => {
          if(res.success){
            this.content2 = res.data.result;
            //console.log(this.code);
           // this.$forceUpdate()
          }
          else{
            this.$message({
            type:'warning',
            message: res.msg
          });
          }})
        }
      },
      //console.log(this.content)
    getfilecontext(val){
      getFileContext(val).then(res => {
          if(res.success){
            this.content1 = res.data.content;
            this.content2='';
            //console.log(this.code);
           // this.$forceUpdate()
          }
          else{
            this.content1 = '程序加载有误，请重新选择文件'
          }});
    },
    getMethod(val){
      //val=2;
    },

    handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
      
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
.slice_container{
  min-height: 100%;
  width: 100%;
  //background-image:url('../../assets/bg-image.png');
  background-size:100%;
  position: fixed;
}
.choose_file{
  position: fixed;
  left:25%;
  top:7%;
  color:black;
  opacity: 0.9;
}
.input_x{
  position: fixed;
  left:65%;
  top:7%;
  color:black;
  opacity: 0.9;
}
.getvar{
  width:150px;
}
.getrow{
  width:150px;
}

  

</style>

