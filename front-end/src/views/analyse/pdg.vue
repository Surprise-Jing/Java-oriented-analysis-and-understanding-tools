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

      选择函数:
      <el-select v-model="selectFunc.name" placeholder="请选择">
        <el-option
        v-for="item in funcData"
                  :key="item.id" 
                  :label="item.methodName"
                  :value="item.methodName">
        </el-option>
      </el-select>
      <el-button @click="btn_ok" class="file_btn">确定</el-button>
      <el-button @click="download_img">下载图片</el-button>
    </div>

  
      <!-- <svg class="canvas">
          <g></g>
      </svg> -->
      <el-image 
           class="graph"
            :src="pdgurl" 
            :preview-src-list="srcList">
  </el-image>
  
  </div>
</template>

<script>
import {getFile} from "@/api/file"
import {getMethod} from "@/api/graph"
import {getPDG} from "@/api/graph";
import dagreD3 from "dagre-d3";
import * as d3 from "d3";
import FileSaver from'file-saver';
import {PdgPNG} from '@/api/create_report'
  export default {
      data() {
          return {
              fileData:[],
              funcData:[],
              selectFile:{
                  id:''
              },
              selectFunc:{
                  name:''
              },
              list: {},
             pdgurl:require('../../assets/white.png'),
             srcList:[
             require('../../assets/white.png'),
             ]
          };
      },
      created(){
          this.getFileMethod();
      },
      mounted() {
          this.initGraph();

      },
      methods: {
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
          initGraph() {
              var g = new dagreD3.graphlib.Graph().setGraph({rankdir: 'UD'});
              // 添加节点
              let that = this;
              that.list.nodes.forEach(item => {
                  g.setNode(item.id, {
                  //节点标签
                  label: item.label,
                  //节点形状
                  shape: "ellipse", //function(item) {return item.shape? item.shape :"ellipse"},
                  //节点样式
                  style: "fill:#fff;stroke:#000",

                  labelStyle: "fill:#000;font-weight:bold"
                  })
              });
              this.list.edges.forEach(item => {
                  g.setEdge(item.source, item.target, {
                  //边标签
                  label: item.label,//function(item) {return item.label? item.label : ""},
                  //边样式
                  style: "fill:#fff;stroke:#333;stroke-width:1.5px",
                  })
              })
              //绘制图形
              var svg = d3.select(".box").select(".graph").select("svg");
              var inner = svg.select("g");
              //缩放
              var zoom = d3.zoom().on("zoom", function () {
                  inner.attr("transform", d3.zoomTransform(svg.node()));
              });
              svg.call(zoom);

              var render = new dagreD3.render();
              render(inner, g);      
          },

      btn_ok(){
          const funcName = {id:1, methodName: this.selectFunc.name}
          getPDG(this.selectFile.id, funcName).then(res =>{
              if(res.success){
                  this.list = JSON.parse(res.data.result);
                  console.log(this.list)
              }
              else{
              this.$message({
                  type:'warning',
                  message: res.msg
              });
          }}),

          PdgPNG(this.selectFile.id).then(res => {
          let pdgbinaryData = [];
            pdgbinaryData.push(res);
            let url = window.URL.createObjectURL(new Blob(pdgbinaryData));		// 获取对象url
            this.pdgurl = url	// 给变量赋值
           this.vbs(url)
        }),

          setTimeout(() => {
            this.initGraph()
          }, 1000);
          
        },
        vbs(val){
            this.srcList=[]
            this.srcList.push(val)
        },
        download_img(){  //下载图片
            PdgPNG(this.selectFile.id).then(res => {
                let pdgbinaryData = [];
                pdgbinaryData.push(res);
                let url = window.URL.createObjectURL(new Blob(pdgbinaryData));		// 获取对象url
                FileSaver(url,"pdg.png")
            })
       },
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
    
    height: 600px;
      background-color: rgb(255, 255, 255);
      position: relative;
      left:100px;
      top: 20px;
      margin: auto;
      
  }

</style>