<template>
  <div class="report_container">
    <div style="height: 30px;"></div>
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
        <el-button @click="getReport" class="get_button">获取report</el-button>
      </div>


      <div style="height: 100px;"></div>
    <el-progress type="circle" :width="400" :percentage="percentage" :color="colors" :stroke-width="20"
     class="show_board"></el-progress>
      <div class="per_board">
      </div>



      <p></p>
      
      <div id="dom" style="padding: 1600px 50px 50px 50px;overflow: visible;width: 1000px;" >
        <div style="height: 300px;"></div>
        <div style="position: relative;">
          <img src="../../assets/report.png" style="width: 700px;position:absolute; top:50%; left:50%; transform:translate(-50%,-50%);">
        </div>
        <div style="height: 600px;"></div>
        <h1 style="text-align: center;">Java分析理解报告</h1>
        <p style="text-align: center;">用户名:{{ userName }}</p>
        <p style="text-align: center;">文件名:{{ fileName }}</p>
        <p style="text-align: center;">报告生成日期:{{ createTime }}</p>
        <p style="text-align: right;">--have a good time!</p>
        <div style="height: 200px;"></div>

        <!-- <h2>一.抽象语法树(AST,Abstract Syntax Tree)</h2>
        <div style="height: 400px;"></div>
        <div style="position: relative;">
          <img v-bind:src="ast" style="height: 700px; position:absolute; top:50%; left:50%; transform:translate(-50%,-50%);">
        </div>
        <div style="height: 1000px;"></div> -->

        <h2>一.控制流图(CFG,Control Flow Graph)</h2>
        <div style="height: 400px;"></div>
        <div style="position: relative;">
          <img v-bind:src="cfg" style="height: 700px;position:absolute; top:50%; left:50%; transform:translate(-50%,-50%);">
        </div>
        
        <div style="height: 900px;"></div>
        <h2>二.程序依赖图(PDG,Program Dependency Graph)</h2>
        <div style="height: 400px;"></div>
        <div style="position: relative;">
          <img v-bind:src="pdg" style="height: 700px;position:absolute; top:50%; left:50%; transform:translate(-50%,-50%);">
        </div>
        <div style="height: 800px;"></div>
        <h2>三.调用关系图(CG,Call Graph)</h2>
        <div style="height: 400px;"></div>
        <div style="position: relative;">
          <img v-bind:src="cg" style="width: 700px;position:absolute; top:50%; left:50%; transform:translate(-50%,-50%);">
        </div>
        <div style="height: 400px;"></div>
        <h2>四.代码度量(Code Metrics)</h2>
          <div>
            <h3>源代码行数统计</h3>
              <el-table :data="rowData" style="width: 600px" v-if="refresh">
                <el-table-column prop="totalrow" label="TotalRow" width="150" />
                <el-table-column prop="coderow" label="CodeRow" width="150" />
                <el-table-column prop="annotation" label="Annotation" width="150"/>
                <el-table-column prop="blankrow" label="BlankRow" />
              </el-table>
            <div style="height: 100px;"></div>
            <h3>调用分析</h3>
            <el-table :data="funcData"  style="width: 900px" v-if="refresh">
                <el-table-column prop="funcName" label="TotalRow" width="150" />
                <el-table-column prop="param" label="CodeRow" width="150" />
                <el-table-column prop="maxdepth" label="Annotation" width="150"/>
                <el-table-column prop="cyclomatic" label="CircleComplexity" width="150"  />
                <el-table-column prop="calling" label="CallTimes" width="150" />
                <el-table-column prop="called" label="CalledTimes" />
            </el-table>
          </div>

      </div>

     
  </div>

</template>

<script>
import html2Canvas from 'html2canvas'
import JsPDF from 'jspdf'
import JSZip from'jszip'
import FileSaver from'file-saver'
import { saveAs } from 'file-saver';
import { getFile, getFileInfo } from '@/api/file'
import {getInfoId} from '@/api/user'
import {getLines, getCodeMetrics} from '@/api/metrics'
import {AstPNG, CfgPNG, CgPNG, PdgPNG} from '@/api/create_report'
import jsPDF from 'jspdf'
import { blob } from 'd3'

export default {
  data(){
    return {
      ast:require('@/assets/report.png'),
      cfg:require('@/assets/report.png'),
      pdg:require('@/assets/report.png'),
      cg:require('@/assets/report.png'),

      userName:'',
      fileName:'',
      createTime:'',
      fileData:[],      
      selectFile:{
          id:'',
      },

      rowData:[
        {
          // totalrow:'1',
          // coderow:'2',
          // annotation:'3',
          // blankrow:'4'
        }
      ],
      funcData:[
        // {
        //   funcname:'',
        //   paranumber:'',
        //   codedepth:'',
        //   circlecomplexity:'',
        //   calltimes:'',
        //   calledtimes:''
        // }
      ],
      refresh:true,
       pdfSave: '',
       percentage: 0,
        colors: [
          {color: '#f56c6c', percentage: 20},
          {color: '#e6a23c', percentage: 40},
          {color: '#5cb87a', percentage: 60},
          {color: '#1989fa', percentage: 80},
          {color: '#6f7ad3', percentage: 100}
        ]
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
        }}),
        getInfoId(localStorage.getItem("uid")).then(res => {
          if(res.success){
          this.userName = res.data.username
         
          //console.log(this.fileData)
        }
        else{
          this.$message({
            type:'warning',
            message: res.msg
          });
        }})
    },
    getfilecontext(val){
      this.percentage=0;
      getFileInfo(val).then(res =>{
        if(res.success){
          this.fileName = res.data.name;
          //console.log(this.fileData)
        }
        else{
          this.$message({
            type:'warning',
            message: res.msg
          });
      }})
        },
    getPdfFromHtml(ele) {
            html2Canvas(ele,{
              dpi: window.devicePixelRatio * 4,
              scale: 4,
              useCORS: true,//允许canvas画布内可以跨域请求外部链接图片, 允许跨域请求。
              allowTaint: false,
              height: ele.offsetHeight,
              width: ele.offsetWidth,
              windowWidth: document.body.scrollWidth,
              windowHeight: document.body.scrollHeight,
            }).then(canvas=>{
              let contentWidth = canvas.width
        let contentHeight = canvas.height
        let pageHeight = contentWidth / 592.28 * 841.89
        let leftHeight = contentHeight
        let position = 0
        let imgWidth = 595.28
        let imgHeight = 592.28 / contentWidth * contentHeight
        let pageData = canvas.toDataURL('image/jpeg', 1.0)
        let PDF = new JsPDF('', 'pt', 'a4')
        if (leftHeight < pageHeight) {
          PDF.addImage(pageData, 'JPEG', 0, 0, imgWidth, imgHeight)
        } else {
          while (leftHeight > 0) {
            PDF.addImage(pageData, 'JPEG', 0, position, imgWidth, imgHeight)
            leftHeight -= pageHeight
            position -= 841.89
            if (leftHeight > 0) {
              PDF.addPage()
            }
          }
        }
            PDF.deletePage(1);
              const zip = new JSZip();
              zip.file('report.pdf',PDF.output('arraybuffer'))
              zip.generateAsync({type:"blob"}).then(function(content) {
                FileSaver(content, "report.zip");
              });

            })
           
    },

    getReport(){
      setTimeout(() => {
        this.increase();
      }, 200);

      this.getData();
      this.refresh_table();
      this.getCurrentTime();

      setTimeout(() => {
        this.increase();
      }, 400);
      setTimeout(() => {
        this.increase();
      }, 600);
      setTimeout(() => {
        this.increase();
      }, 800);
      setTimeout(() => {
        this.increase();
      }, 1000);
      setTimeout(() => {
        this.increase();
      }, 1200);
      setTimeout(() => {
        this.increase();
      },1400);
      setTimeout(() => {
        let dom = document.getElementById('dom')  
       this.getPdfFromHtml(dom,'test')
       this.increase();
       this.increase();
       this.increase();
      }, 2000);
       


      
    },

    getFile (url) {
      return new Promise((resolve, reject) => {
        getFile(url).then(data => {
          resolve(data.data)
        }).catch(error => {
          reject(error.toString())
        })
      })
    },
    increase() {
        this.percentage += 10;
        if (this.percentage > 100) {
          this.percentage = 100;
        }
      },
      decrease() {
        this.percentage -= 10;
        if (this.percentage < 0) {
          this.percentage = 0;
        }
      },

      getData(){
        AstPNG(this.selectFile.id).then(res => {
            // this.$forceUpdate()
            let astbinaryData = [];
            astbinaryData.push(res);
            let url = window.URL.createObjectURL(new Blob(astbinaryData));		// 获取对象url
            this.ast = url	// 给变量赋值
        }),

        CfgPNG(this.selectFile.id).then(res => {
          let cfgbinaryData = [];
            cfgbinaryData.push(res);
            let url = window.URL.createObjectURL(new Blob(cfgbinaryData));		// 获取对象url
            this.cfg = url	// 给变量赋值
        }),

        CgPNG(this.selectFile.id).then(res => {
          let cgbinaryData = [];
            cgbinaryData.push(res);
            let url = window.URL.createObjectURL(new Blob(cgbinaryData));		// 获取对象url
            this.cg = url	// 给变量赋值
        }),

        PdgPNG(this.selectFile.id).then(res => {
          let pdgbinaryData = [];
            pdgbinaryData.push(res);
            let url = window.URL.createObjectURL(new Blob(pdgbinaryData));		// 获取对象url
            this.pdg = url	// 给变量赋值
        }),

        getLines(this.selectFile.id).then(res => {
          if(res.success){
            this.rowData.push({
              totalraw:res.data.linesOfAll,
              coderow:res.data.linesOfCode,
              annotation:res.data.linesOfComment,
              blankrow:res.data.linesOfBlanks
            })
             
            }
            else{
              this.$message({
              type:'warning',
              message: res.msg
            })}
        }),

        getCodeMetrics(this.selectFile.id).then(res => {
          if(res.success){
            this.funcData = res.data
           
            }
            else{
              this.$message({
              type:'warning',
              message: res.msg
            })}
        })


       
      },
      refresh_table(){
        this.refresh = false
            // 在组件移除后，重新渲染组件
            // this.$nextTick可实现在DOM 状态更新后，执行传入的方法。
            this.$nextTick(() => {
                this.refresh = true
            })
      },
      getCurrentTime() {
        //获取当前时间并打印
        var _this = this;
        let yy = new Date().getFullYear();
        let mm = new Date().getMonth()+1;
        let dd = new Date().getDate();
        _this.gettime = yy+'.'+mm+'.'+dd+' ';
       this.createTime=_this.gettime ;
    }
  },
  
    
}
</script>


<style lang="scss" scoped>
.report_container{
  min-height: 100%;
  width: 100%;
  //background-image:url('../../assets/bg-image.png');
  background-size:100%;
  position: fixed;
}
.per_board{
  padding-left:39%;

}
.show_board{
  left:30%;
  top:20%;
  stroke-width:200px;
  
}
.choose_file{
  position: relative;
  left:30%;
color:black;
font-size: large;
}
</style>