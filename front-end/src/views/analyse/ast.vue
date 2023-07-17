<template>
    <div class = "astGraph">
      <div class="choose_file" >
              选择文件:
          <el-select v-model="selectFile.id" @change="getAst(selectFile.id)" placeholder="请选择">
            <el-option
            v-for="item in fileData"
                      :key="item.id" 
                      :label="item.fileName"
                      :value="item.id">
            </el-option>
          </el-select>
      </div>
      <!-- 一键展开,放大,缩小,下载按钮 -->
      <el-button @click="expandall">全部展开</el-button>
      <el-button @click="enlarge" >放大</el-button>
      <el-button @click="narrow">缩小</el-button>
      <el-button @click="download_img">下载图片</el-button>
        <!-- 生成ast树 -->
        <div class="ast" id="ast">
            <orgtree :data="testData" :horizontal="true" name="test" :label-class-name="labelClassName"    
            collapsable    @on-expand="onExpand" @on-node-mouseover="onMouseover" @on-node-mouseout="onMouseout"/> 
            <!-- 创建浮窗盒子 -->
            <div v-show="BasicSwich" class="floating">    
              <p>ID:{{BasicInfo.id}}</p>    <p>Name:{{BasicInfo.label}}</p>
            </div>
        </div>
        
  </div>
  </template>
  
  
  
  <script>
  import orgtree from "../../components/orgtree";
  import {getFile} from "@/api/file";
  import {getAST} from "@/api/graph";
  import html2canvas from 'html2canvas';
  import FileSaver from'file-saver';
  import {AstPNG} from '@/api/create_report'
    export default {
      components:{
        orgtree
      },
      data () {
        return {
          fileData:[],
          selectFile:{
              id:''
          },
          BasicSwich:false,	
          BasicInfo:{id:null,label:null},
          testData:{},
          scal:1,
        }
      },
      created(){
          this.getFileMethod()
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
          }}
        )},
        getAst(val){
          getAST(val).then(res => {
            if(res.success){
              this.testData = JSON.parse(res.data.result);
              console.log(this.testData);
             // this.$forceUpdate()
            }
            else{
              this.$message({
              type:'warning',
              message: res.msg
            })}
          })
        },
  
        collapse(list) {   
          var _this = this;    
          list.forEach(
            function(child) {        
              if (child.expand) {          
                child.expand = false;        
              }        
              child.children && _this.collapse(child.children);	
            });},
        onExpand(e,data) {    
          if ("expand" in data) {       
            data.expand = !data.expand;    	
            if (!data.expand && data.children) {       		
              this.collapse(data.children);    	
            }    
          } 
          else {        
            this.$set(data, "expand", true);    
          }},
        renderLabelClass (node) {
          return 'label-class-blue'
        },
        renderCurrentClass (node) {
          return 'label-bg-blue'
        },
        onMouseout(e, data) {    
          this.BasicSwich = false
        },
        onMouseover(e, data) {    
          this.BasicInfo = data;    
          this.BasicSwich = true;    
          var floating = document.getElementsByClassName('floating')[0];    
          floating.style.left = e.clientX +'px';    
          floating.style.top = e.clientY+'px';
        },
        toggleExpand(data, val) {//一键展开
          var _this = this;
          if (Array.isArray(data)) {
              data.forEach(function(item) {
                _this.$set(item, "expand", val);
                if (item.children) {
                  _this.toggleExpand(item.children, val);
                }
              });
          } else {
              this.$set(data, "expand", val);
              if (data.children) {
                _this.toggleExpand(data.children, val);
              }
          }
        },
        expandall(){
          this.toggleExpand(this.testData,true)
        },

        narrow() {
        this.$nextTick(() => {
            // imageWrapper 获取元素
            let imageWrapper = document.getElementById('ast');
            this.scal = (parseFloat(this.scal) - 0.10).toFixed(2);
            imageWrapper.style.transform = "scale(" + this.scal + ")";
            imageWrapper.style.transformOrigin = '0 0';
          })
        },
        enlarge() {
          this.$nextTick(() => {
            // imageWrapper 获取元素
            let imageWrapper = document.getElementById('ast');
            this.scal = (parseFloat(this.scal) + 0.10).toFixed(2);
            imageWrapper.style.transform = "scale(" + this.scal + ")";
            imageWrapper.style.transformOrigin = '0 0';
          })
       },
       download_img(){  //下载图片
        
        AstPNG(this.selectFile.id).then(res => {
            let astbinaryData = [];
            astbinaryData.push(res);
            let url = window.URL.createObjectURL(new Blob(astbinaryData));		// 获取对象url
            // this.ast = url	// 给变量赋值

            FileSaver(url,"ast.png")
        })
        
       },



      }
    }
  </script>
  <style>
  .label-class-blue{
    color: #1989fa;
  }
  .label-bg-blue{
    background: #1989fa;
    color: #fff;
  }
  /* 盒子css */
  .floating{    
    background: rgba(0, 0, 0, 0.7);    
    width: 160px;    
    height: 100px;    
    position: absolute;    
    color: #fff;    
    padding-top: 15px;    
    border-radius: 15px;    
    padding-left: 15px;    
    box-sizing: border-box;    
    left:0;    
    top: 0;    
    transition: all 0.3s;    
    z-index: 999;    
    text-align: left;    
    font-size: 12px;
  }
 .ast{
  overflow: scroll;
 }
  .astGraph{
  
  }
  </style>
  
  
  