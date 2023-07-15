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
          <div style="margin-left:30px;">
        <el-row :gutter="20">
          <el-col :span="5">
            <el-switch v-model="horizontal" :width="50" active-text="横排" inactive-text="竖排" style="margin-top:8px;"/>
          </el-col>
          <el-col :span="5">
            <el-switch v-model="expandAll" :width="50" active-text="全部展开"
                       inactive-text="全部折叠" style="margin:8px;" @change="expandChange"/>
          </el-col>
          </el-row>
          </div>
      <div class = "ast" style="border: none; padding: 20px; width: 600px; height: 600px">
        <orgtree :data="testData" :horizontal="true" name="test" :label-class-name="labelClassName"    
        collapsable    @on-expand="onExpand" @on-node-mouseover="onMouseover" @on-node-mouseout="onMouseout"/> 
        <!-- 创建浮窗盒子 --><div v-show="BasicSwich" class="floating">    
          <p>ID:{{BasicInfo.id}}</p>    <p>Name:{{BasicInfo.label}}</p></div>
      </div>
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
          "treeData": {
            labelClassName: "bg-color-orange",
            basicInfo: { id: null, label: "---null" },
            basicSwitch: false,
            data:{},
        horizontal: true, //横版 竖版
        collapsable: false,
        expandAll: true, //是否全部展开
        labelClassName: '白色', // 默认颜色
        scrollTreeStyle: 'width:100%;',
        },
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
        renderContent(h, data) {
        return data.label;
        },
        onExpand(data) {
        if ("expand" in data) {
            data.expand = !data.expand;
            if (!data.expand && data.children) {
            this.collapse(data.children);
            }
        } else {
            this.$set(data, "expand", true);
        }
        },
        onNodeClick(e, data) {
        alert(data.label);
        },
        collapse(list) {
        var _this = this;
        list.forEach(function(child) {
            if (child.expand) {
            child.expand = false;
            }
            child.children && _this.collapse(child.children);
        });
        },
        expandChange() {
        this.toggleExpand(this.data, this.expandAll);
        },
        toggleExpand(data, val) {
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
  
  
  