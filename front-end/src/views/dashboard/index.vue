<template>
  <div class="dashboard_container">

    <el-table :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"  class="data_table">
      <el-table-column prop="id" label="文件id" width="280" v-if="false">
    </el-table-column>
    <el-table-column prop="fileName" label="文件名称" width="180">

    </el-table-column>
    <el-table-column prop="uploadTime" label="上传时间" width="280">
    </el-table-column>
    <el-table-column prop="operation" label="操作">
      <template slot-scope="scope">
        <el-button  type="info"  @click="download_file(scope.row.id)">下载</el-button>
        <el-button type="info" @click="delete_file(scope.row.id)">删除</el-button>
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


  </div>
</template>

<script>
import axios from "axios";
import store from "@/store/index"
import {getFile, getFileContext, deleteFile} from "@/api/file"
import FileSaver from "file-saver"

export default {
  data(){
    return {
      tableData : [],
        currentPage: 1, // 当前页码
        total: 20, // 总条数
        pageSize: 5 // 每页的数据条数
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
      }

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
  background-image:url('../../assets/bg-image2.png');
  background-size:100%;
  position: fixed;
}

.data_table{

  //height: 300px;
  width: 700px;
  border: dashed 1px gray;
  margin-bottom: 10px;
  color: #777;
  position: absolute;
  left:40%;
  top:30%;
  transform: translate(-50%,-50%);
  background-color:gray;

}
.block{
  position: absolute;
  left:40%;
  top:80%;
  transform: translate(-50%,-50%);
  //background-color:gray;
}

</style>
