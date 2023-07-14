<template>
  <div class="report_container">

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
      <el-button @click="getReport" class="get_button">获取report</el-button>
      <div id="dom" style="padding: 1600px 50px 50px 50px;overflow: visible;width: 1000px;" >
      需要生成的内容
      <img src="../../assets/bg-image.png" width="500px">
      <br>
      <img src="../../assets/bg-image.png" width="500px">
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
       pdfSave: '',
       percentage: 10,
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
      let dom = document.getElementById('dom')  
       this.getPdfFromHtml(dom,'test')
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
  stroke-width:200px;
  
}
.get_button{
  position: relative;
  left:40%;
}
</style>