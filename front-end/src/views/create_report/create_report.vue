<template>
  <div class="report_container">
      生成report
      <p></p>
      <button @click="getReport">获取report</button>
      <div id="dom" style="padding: 1600px 50px 50px 50px;overflow: visible;width: 1000px;" >
      需要生成的内容
      <img src="../../assets/bg-image.png" width="500px">
      <br>
      <img src="../../assets/bg-image2.png" width="500px">
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

      downLoadAll() {
        this.getPDF();
        console.log(this.pdfSave)
        
        const data = [this.pdfSave]; //todo 
        const zip = new JSZip();


        const cache = {};
        const promises = [];

        data.forEach(item => {
        const promise = data => {
          // 下载文件, 并存成ArrayBuffer对象
          const file_name = item.name // 获取文件名
          zip.file(file_name, data.data, { binary: true }) // 逐个添加文件
          cache[file_name] = data.data
        }
        promises.push(promise)
      })



      //   data.forEach(item => {
      //   const promise = getFile(item).then(data => {
      //     // 下载文件, 并存成ArrayBuffer对象
      //     const arr_name = item.split('-')
      //     const file_name = arr_name[arr_name.length - 1] // 获取文件名
      //     zip.file(file_name, data.data, { binary: true }) // 逐个添加文件
      //     cache[file_name] = data.data
      //   })
      //   promises.push(promise)
      // })

        Promise.all(promises).then(() => {
          zip.generateAsync({ type: "blob" }).then(content => {
            // 生成二进制流
            FileSaver.saveAs(content, "测试.zip"); // 利用file-saver保存文件  自定义文件名
          });
        });
      },
  },
  
    
}
</script>


<style lang="scss" scoped>
.report_container{
  min-height: 100%;
  width: 100%;
  background-image:url('../../assets/bg-image2.png');
  background-size:100%;
  position: fixed;
}


</style>