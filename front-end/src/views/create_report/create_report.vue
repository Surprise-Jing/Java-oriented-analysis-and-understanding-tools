<template>
  <div class="report_container">

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
        <el-button-group>
          <el-button  @click="decrease">减少</el-button>
          <el-button  @click="increase">增加</el-button>
        </el-button-group>
      </div>


      生成report
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

        <h2>一.抽象语法树(AST,Abstract Syntax Tree)</h2>
        <div style="height: 400px;"></div>
        <div style="position: relative;">
          <img src="../../assets/report.png" style="width: 700px;position:absolute; top:50%; left:50%; transform:translate(-50%,-50%);">
        </div>
        <div style="height: 1000px;"></div>

        <h2>二.控制流图(CFG,Control Flow Graph)</h2>
        <div style="height: 400px;"></div>
        <div style="position: relative;">
          <img src="../../assets/report.png" style="width: 700px;position:absolute; top:50%; left:50%; transform:translate(-50%,-50%);">
        </div>
        
        <div style="height: 900px;"></div>
        <h2>三.程序依赖图(PDG,Program Dependency Graph)</h2>
        <div style="height: 400px;"></div>
        <div style="position: relative;">
          <img src="../../assets/report.png" style="width: 700px;position:absolute; top:50%; left:50%; transform:translate(-50%,-50%);">
        </div>
        <div style="height: 800px;"></div>
        <h2>四.调用关系图(CG,Call Graph)</h2>
        <div style="height: 400px;"></div>
        <div style="position: relative;">
          <img src="../../assets/report.png" style="width: 700px;position:absolute; top:50%; left:50%; transform:translate(-50%,-50%);">
        </div>
        <div style="height: 400px;"></div>
        <h2>五.代码度量(Code Metrics)</h2>
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
                <el-table-column prop="funcname" label="TotalRow" width="150" />
                <el-table-column prop="paranumber" label="CodeRow" width="150" />
                <el-table-column prop="codedepth" label="Annotation" width="150"/>
                <el-table-column prop="circlecomplexity" label="CircleComplexity" width="150"  />
                <el-table-column prop="calltimes" label="CallTimes" width="150" />
                <el-table-column prop="calledtimes" label="CalledTimes" />
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
import { getFile } from '@/api/create_report'
import jsPDF from 'jspdf'
import { blob } from 'd3'

export default {
  data(){
    return {
      userName:'',
      fileName:'',
      createTime:'',
      fileData:[],      
      selectFile:{
          id:''
      },

      rowData:[
        {
          totalrow:'1',
          coderow:'2',
          annotation:'3',
          blankrow:'4'
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
  methods: {
    getfilecontext(val){
          getFileContext(val).then(res => {
          if(res.success){
            this.content1 = res.data.content;
          }
          else{
            this.content1 = '程序加载有误，请重新选择文件'
          }});
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
      this.getData();
      this.refresh_table();
      console.log(this.funcData)


      setTimeout(() => {
        let dom = document.getElementById('dom')  
       this.getPdfFromHtml(dom,'test')
	    }, 1000);

      
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
        this.rowData.push({
          totalraw:'20',
          coderow:'12',
          annotation:'5',
          blankrow:'3'
        })
        this.funcData.push({
          "funcname":'getNum',
          "paranumber":'2',
          "codedepth":'3',
          "circlecomplexity":'1',
          "calltimes":'5',
          "calledtimes":'7'
        })
        this.funcData.push({
          funcname:'getRow',
          paranumber:'1',
          codedepth:'2',
          circlecomplexity:'7',
          calltimes:'4',
          calledtimes:'8'
        })
        
      },
      refresh_table(){
        this.refresh = false
            // 在组件移除后，重新渲染组件
            // this.$nextTick可实现在DOM 状态更新后，执行传入的方法。
            this.$nextTick(() => {
                this.refresh = true
            })
      }

  },
  
    
}
</script>


<style lang="scss" scoped>
.report_container{
  min-height: 100%;
  width: 100%;
  background-image:url('../../assets/bg-image.png');
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
  top:10%;
color:white;
opacity: 0.75;
}
</style>