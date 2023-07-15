<template>
  <div class = "pdgGraph">
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
    <div class = "ast" style="border: none; padding: 20px; width: 600px; height: 600px">
      <orgtree :data="testData" :horizontal="true" name="test" :label-class-name="labelClassName"    
      collapsable    @on-expand="onExpand" @on-node-mouseover="onMouseover" @on-node-mouseout="onMouseout"/> 
      <!-- 创建浮窗盒子 --><div v-show="BasicSwich" class="floating">    
        <p>ID:{{BasicInfo.id}}</p>    <p>Name:{{BasicInfo.label}}</p></div>
    </div>
</div>
</template>



<script>
import orgtree from "../../components/orgtree";
import {getFile} from "@/api/file";
import {getAST} from "@/api/graph";

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
        testData:{}
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
            this.testData = res.data.result;
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
      }
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
</style>


