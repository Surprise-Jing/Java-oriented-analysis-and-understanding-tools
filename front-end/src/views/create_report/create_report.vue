<template>
  <div class="report_container">
      生成report
      <p></p>
      <button @click="getPDF">获取pdf</button>
      <button @click="downLoadAll">获取zip</button>
      <div id="dom" style="padding: 0 50px 50px 50px;overflow: visible;width: 1000px;">
      需要生成的内容
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
              const _this = this;
              //未生成pdf的html页面高度
              var leftHeight = canvas.height;
              var a4Width = 595.28
              var a4Height = 841.89
              //一页pdf显示html页面生成的canvas高度;
              var a4HeightRef = Math.floor(canvas.width / a4Width * a4Height);
              //pdf页面偏移
              var position = 0;
              var pageData = canvas.toDataURL('image/jpeg', 1.0);
              var pdf = new JsPDF('x', 'pt', 'a4');
              var index = 1,
                canvas1 = document.createElement('canvas'),
                height;
              pdf.setDisplayMode('fullwidth', 'continuous', 'FullScreen');
              function createImpl(canvas) {
                if (leftHeight > 0) {
                  index++;
                  var checkCount = 0;
                  if (leftHeight > a4HeightRef) {
                    var i = position + a4HeightRef;
                    for (i = position + a4HeightRef; i >= position; i--) {
                      var isWrite = true
                      for (var j = 0; j < canvas.width; j++) {
                        var c = canvas.getContext('2d').getImageData(j, i, 1, 1).data
                        if (c[0] != 0xff || c[1] != 0xff || c[2] != 0xff) {
                          isWrite = false
                          break
                        }
                      }
                      if (isWrite) {
                        checkCount++
                        if (checkCount >= 10) {
                          break
                        }
                      } else {
                        checkCount = 0
                      }
                    }
                    height = Math.round(i - position) || Math.min(leftHeight, a4HeightRef);
                    if(height<=0){
                      height = a4HeightRef;
                    }
                  } else {
                    height = leftHeight;
                  }
 
                  canvas1.width = canvas.width;
                  canvas1.height = height;
 
                  var ctx = canvas1.getContext('2d');
                  ctx.drawImage(canvas, 0, position, canvas.width, height, 0, 0, canvas.width, height);
                  var pageHeight = Math.round(a4Width / canvas.width * height);
                  if(position != 0){
                    pdf.addPage();
                  }
                  pdf.addImage(canvas1.toDataURL('image/jpeg', 1.0), 'JPEG', 0, 20, a4Width, a4Width / canvas1.width * height);
 
                  leftHeight -= height;
                  position += height;
                  if (leftHeight > 0) {
                    //添加全屏水印
                    // const base64 = ''
                    // for(let i=0;i<6;i++){
                    //   for(let j=0;j<5;j++){
                    //     const  left = (j*120)+20;
                    //     pdf.addImage(base64,'JPEG', left, i*150, 20, 30);
                    //   }
                    // }
                    setTimeout(createImpl, 500, canvas);
                  } else {
                    pdf.name='report.pdf'
                    this.pdfSave= pdf
                    //this.pdfSave = pdf.save(pdfFileName + '.pdf');
                    //pdf.name=pdfFileName + '.pdf'
                    //return pdf
                  }
                }
              }
              //当内容未超过pdf一页显示的范围，无需分页
              if (leftHeight < a4HeightRef) {
                pdf.addImage(pageData, 'JPEG', 0, 0, a4Width, a4Width / canvas.width * leftHeight);
                this.dialogVisible = true;
                pdf.name='report.pdf'
                //pdf.name=pdfFileName + '.pdf'
                //FileSaver.saveAs('./report',pdf)
               this.pdfSave= pdf
                //this.pdfSave = pdf.save(pdfFileName + '.pdf')
               
              } else {
                try {
                  pdf.deletePage(0);
                  setTimeout(createImpl, 500, canvas);
                 
                } catch (err) {
                  console.log(err);
                }
              }

              const zip = new JSZip();
             
             // this.saveAs=pdf.output('dataurlstring')
              zip.file("report.pdf",pdf.output('arraybuffer'));
              zip.generateAsync({ type: "blob" }).then(content => {
                // 生成二进制流
                  FileSaver.saveAs(content, "测试.zip"); // 利用file-saver保存文件  自定义文件名
                });
             


            })
           
    },

    getPDF(){
      let dom = document.getElementById('dom')  
       this.getPdfFromHtml(dom)
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