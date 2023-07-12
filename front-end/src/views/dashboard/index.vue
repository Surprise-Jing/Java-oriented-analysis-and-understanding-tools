<template>
  <div class="dashboard_container">

    <el-table :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"  class="data_table">
      <el-table-column prop="file_id" label="文件id" width="50" v-if="false">
      </el-table-column>

    <el-table-column prop="file_name" label="文件名称" width="180">
    </el-table-column>
    <el-table-column prop="upload_time" label="上传时间" width="280">
    </el-table-column>
    <el-table-column prop="operation" label="操作">
      <template slot-scope="scope">
        <el-button  type="info"  @click="download_file(scope.row)">下载</el-button>
        <el-button type="info" @click="delete_file(scope.row)">删除</el-button>
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

export default {
  data(){
    return {
       tableData:[
        {
          file_id:'1111',
          file_name:'test1',
          upload_time:'2022-1-1'
        },
        {
          file_id:'1111',
          file_name:'test2',
          upload_time:'2022-1-1'
        },
        {
          file_id:'1111',
          file_name:'test3',
          upload_time:'2022-1-1'
        },
        {
          file_id:'1111',
          file_name:'test4',
          upload_time:'2022-1-1'
        },
        {
          file_id:'1111',
          file_name:'test5',
          upload_time:'2022-1-1'
        },
        {
          file_id:'1111',
          file_name:'test6',
          upload_time:'2022-1-1'
        },
       ],
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

      },
      delete_file(val){
        
      }

  },
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