<template>
  <div class="dashboard_container">
    
    <el-table :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"  class="data_table">
      <el-table-column prop="id" label="文件id" width="280" v-if="false">
    </el-table-column>
    <el-table-column prop="fileName" label="文件名称" width="280">

    </el-table-column>
    <el-table-column prop="uploadTime" label="上传时间" width="280">
    </el-table-column>
    <el-table-column prop="operation" label="操作">
      <template slot-scope="scope">
        <el-button  type="info"  @click="download_file(scope.row.id)" >下载</el-button>

        <el-button type="info" @click="delete_file(scope.row.id)">删除</el-button>
        <el-button type="info" @click="see_file(scope.row.id)">预览</el-button>
        <!-- <el-button :type="scope.row.status?'danger':'primary'" @click="changeStatus(scope.$index)" </el-button> -->

      </template>
    </el-table-column>
  </el-table>
  <div class="block" >
            <el-pagination align='center' @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page="currentPage"

            :page-sizes="[1,5]"

            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="tableData.length">
            </el-pagination>
  </div>



  <el-dialog
  title="代码:"
  :visible.sync="codeVisible"
  width="30%"
  
  append-to-body>
  <span style="white-space: pre-wrap;"> {{code_msg}}</span>
  <span slot="footer" class="dialog-footer">
   
  </span>
</el-dialog>



  </div>
</template>

<script>
import {getFile, getFileContext, deleteFile} from "@/api/file"
import FileSaver from "file-saver"

export default {
  data(){
    return {
      tableData : [],
        currentPage: 1, // 当前页码
        total: 20, // 总条数
        pageSize: 5 ,// 每页的数据条数
        code_msg:'',
        codeVisible:false
      }
  },
  methods: {
    handleSizeChange(val) {
          console.log(`每页 ${val} 条`);
          this.currentPage = 1;
          this.pageSize = val;
      },
                //当前页改变时触发 跳转其他页
    handleCurrentChange(val) {
          console.log(`当前页: ${val}`);
          this.currentPage = val;
      },


      download_file(val){
        getFileContext(val).then(res => {
          if(res.success){
            let blob = new Blob([res.data.content], {
              type: "text/plain;charset=utf-8"
            });
            FileSaver.saveAs(blob, res.data.fileName)
          }
          else{
              this.$message({
              type:'warning',
              message: res.msg
            });
          }
        })
      },
      delete_file(val){
        deleteFile(val, localStorage.getItem("uid")).then(res => {
          if(res.success){
            if(res.data){
              this.$message.success('删除成功');
            }
            else{
              this.$message.error('删除失败');
            }
          }
          else{
              this.$message({
              type:'warning',
              message: res.msg
            });
          }
        })
      },
      see_file(val){
        getFileContext(val).then(res => {
          if(res.success){
            this.code_msg=res.data.content
           // this.code_msg=this.preText(this.code_msg)
           
            //FileSaver.saveAs(blob, res.data.fileName)
          }
          else{
              this.$message({
              type:'warning',
              message: res.msg
            });
          }
        })
        this.codeVisible=true
      },
      close_code(){
        this.able_see=false
      },
      preText (pretext) {
        return pretext.replace(/\r\n/g, '<br/>').replace(/\n/g, '<br/>').replace(/\s/g, '&nbsp;')
      },

      

  },
  mounted() {
      getFile(localStorage.getItem("uid")).then(res => {
        if(res.success){
          this.tableData = res.data
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
.dashboard_container{
  min-height: 100%;
  width: 100%;
  //background-image:url('../../assets/bg-image.png');
  background-size:100%;
  position: fixed;
}

.data_table{

  //height: 300px;
  width: 900px;
  border: solid 1px gray;
  margin-bottom: 10px;
  color: #777;
  position: absolute;
  left:45%;
  top:40%;
  transform: translate(-50%,-50%);
  background-color:gray;
  font-size: large;
}
.block{
  position: absolute;
  left:45%;
  top:80%;
  transform: translate(-50%,-50%);
  //background-color:gray;
}
.code_area{
  height: 500px;
  width:500px;
  white-space: pre-wrap;
  position: relative;
  left: 30%;
  background-color:darkgrey;
  //border-radius: 5%;
  font-size: large;
  overflow:scroll;

}
</style>
