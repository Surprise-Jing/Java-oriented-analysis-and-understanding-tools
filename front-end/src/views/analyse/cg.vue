<template>
  <div class="box">
      <div class="choose_file" >
          选择文件:
      <el-select v-model="selectFile.id" @change="getmethod(selectFile.id)" placeholder="请选择">
        <el-option
        v-for="item in fileData"
                  :key="item.id" 
                  :label="item.fileName"
                  :value="item.id">
        </el-option>
      </el-select>
    </div>
      <el-button @click="btn_ok" class="file_btn">确定</el-button>
  <div class="graph">
      <svg class="canvas">
          <g></g>
      </svg>
  </div>
  </div>
</template>

<script>
import {getFile} from "@/api/file"
import {getCG} from "@/api/graph";
import dagreD3 from "dagre-d3";
import * as d3 from "d3";

  export default {
      data() {
          return {
            //可选文件列表
              fileData:[],
              //用户选择的文件id
              selectFile:{
                  id:''
              },
              //图的数据
              list: {}
          };
      },
      created(){
          this.getFileMethod();
      },
      mounted() {
          this.initGraph();

      },
      methods: {
        //获取文件列表
      getFileMethod(){
          getFile(localStorage.getItem("uid")).then(res => {
              if(res.success){
                  this.fileData = res.data
                  //console.log(this.fileData)
              }
              else{
                  this.$message({
                  type:'warning',
                  message: res.msg
                  });
              }
              })},
              //获取函数列表（本页面未调用）
          getmethod(val){
              getMethod(val).then(res => {
                  if(res.success){
                      this.funcData = res.data
                      this.selectFunc.name = ''
                  }
                  else{
                      this.$message({
                      type:'warning',
                      message: res.msg
                  });
                  }
              })
          },
          //初始化图
          initGraph() {
              var g = new dagreD3.graphlib.Graph().setGraph({rankdir: 'UD'});
              // 添加节点
              let that = this;
              that.list.nodes.forEach(item => {
                //设置节点参数
                  g.setNode(item.id, {
                  //节点标签
                  label: item.label,
                  //节点形状
                  shape: "ellipse",
                  //节点样式
                  style: "fill:#fff;stroke:#000",
                  //标签样式
                  labelStyle: "fill:#000;font-weight:bold"
                  })
              });
              this.list.edges.forEach(item => {
                  g.setEdge(item.source, item.target, {
                  //边标签
                  label: item.label,
                  //边样式，注意fill
                  style: "fill:#fff;stroke:#333;stroke-width:1.5px"
                  })
              })
              //选择容器
              var svg = d3.select(".box").select(".graph").select("svg");
              var inner = svg.select("g");
              //缩放
              var zoom = d3.zoom().on("zoom", function () {
                  inner.attr("transform", d3.zoomTransform(svg.node()));
              });
              svg.call(zoom);
              //渲染
              var render = new dagreD3.render();
              render(inner, g);      
          },
    //点击按钮，开始获取数据，进行图的渲染
      btn_ok(){
          getCG(this.selectFile.id).then(res =>{
              if(res.success){
                  this.list = JSON.parse(res.data.result);
                  console.log(this.list)
              }
              else{
              this.$message({
                  type:'warning',
                  message: res.msg
              });
          }})

          this.initGraph()
      }
  }
}
</script>

<style lang="less">
  svg {
      width: 1250px;
      height: 650px;
      font-size: 14px;
  }

  .node rect {
      stroke: #606266;
      fill: #fff;
  }

  .edgePath path {
      stroke: #606266;
      fill: #333;
      stroke-width: 1.5px;
  }
  .box {
      width:1350px;
      height:800px;
      background-color:rgb(255, 255, 255);
      position: relative;
}
  .graph {
      width: 1250px;
      height: 650px;
      border: solid;
      background-color: rgb(255, 255, 255);
      position: absolute;
      left:0px;
      right: 0px;
      top:0px;
      bottom: 0px;
      margin: auto;
  }

</style>