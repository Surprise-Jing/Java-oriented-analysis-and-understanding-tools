<template>
  <div class="box">
        <!-- 选择函数、选择文件以及下载图片 -->
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

    <!-- <div class="choose_func" >
        
    </div> -->
  <div class="graph">
      <svg class="canvas">
          <g></g>
      </svg>
  </div>
  </div>
</template>

<script>
import {getFile} from "@/api/file"
import {getMethod} from "@/api/graph"
import {getCFG} from "@/api/graph";
import dagreD3 from "dagre-d3";
import * as d3 from "d3";
import {CfgPNG} from '@/api/create_report'
import html2canvas from 'html2canvas';
import FileSaver from'file-saver';

  export default {
      data() {
          return {
              //用户可选的文件列表
              fileData:[],
              //用户可选的函数列表
              funcData:[],
              //用户选择的文件id
              selectFile:{
                  id:''
              },
              //用户选择的函数名
              selectFunc:{
                  name:''
              },
              //图的数据（包括nodes和edges）
              list: {},
              //是否绘制图片
              draw: false
          };
      },
      created(){
          this.getFileMethod();
      },
      mounted() {
          this.initGraph();

      },
      methods: {
        //根据选择的文件获取文件信息
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
          //获取函数列表
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
        // 图的展示
          //绘制图
          initGraph() {
              var g = new dagreD3.graphlib.Graph().setGraph({rankdir: 'UD'});
              // 添加节点
              let that = this;
              that.list.nodes.forEach(item => {
                  //设置节点格式
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
                  //边样式
                  /**
                   * 特别注意{@code fill}属性
                   * 该属性设置以生成的连线为边的三角形的填充颜色，对视觉效果有很大影响
                   * 此处将其设置为背景颜色，若背景色发生改动，此处也需要同步修改
                   */
                  style: "fill:	#fff;stroke:#333;stroke-width:1.5px"       
                  })
              })
              //选择容器
              var svg = d3.select(".box").select(".graph").select("svg");
              var inner = svg.select("g");
              this.draw = true;

              //设置缩放
              var zoom = d3.zoom().on("zoom", function () {
                  inner.attr("transform", d3.zoomTransform(svg.node()));
              });
              svg.call(zoom);
              //渲染图形
              var render = new dagreD3.render();
              render(inner, g);      
          },
          downloadCfg() {
            if(!this.draw)
                console.log("error");
            else {
                let el = document.getElementsByTagName("svg")[0];
	            saveSvgAsPng.saveSvgAsPng(el, "cfg.png");
            }
    },
    //确认按钮
      btn_ok(){
          const funcName = {id:1, methodName: this.selectFunc.name}
          getCFG(this.selectFile.id, funcName).then(res =>{
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
            setTimeout(() => {
                this.initGraph()
            }, 1000);
          
      },
      //下载图片
      download_img(){  
        CfgPNG(this.selectFile.id).then(res => {
            let cfgbinaryData = [];
            cfgbinaryData.push(res);
            let url = window.URL.createObjectURL(new Blob(cfgbinaryData));		// 获取对象url
            FileSaver(url,"cfg.png")
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

  .node ellipse {
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
      position: relative;
}
  .graph {
      width: 1250px;
      height: 650px;
      border: solid;
      position: absolute;
      left:0px;
      right: 0px;
      top:0px;
      bottom: 0px;
      margin: auto;
  }

</style>