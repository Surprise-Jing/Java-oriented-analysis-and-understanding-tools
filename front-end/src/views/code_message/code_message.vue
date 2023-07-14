<template>
    <div class="message_container">
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
      </div>

      <div class="choose_func" >
            选择函数:
        <el-select v-model="selectFunc.id" @change="getfunc(selectFunc.id)" placeholder="请选择">
          <el-option
          v-for="item in funcData"
                    :key="item.id" 
                    :label="item.funcName"
                    :value="item.id">
          </el-option>
        </el-select>
        <el-button @click="btn_ok" class="file_btn">确定</el-button>
      </div>


      <div class="display_area">

       
            <div id="rowChart" style="width:600px;height:278px;float:left;" ></div>
            <div style="height: 400px;"></div>
            <div id="callChart" style=" width: 600px; height: 400px;" ></div>
        
       


      </div>
    </div>
</template>


<script>
import { methods } from 'vue2-ace-editor'
export default{
    data(){
      return{

            fileData:[],
            funcData:[],
            selectFile:{
                id:''
            },
            selectFunc:{
                id:''
            },

        rowAnalyse: '',
        rowData: [
            { value: '', name: '源代码行数', itemStyle: '#1FC48D' },
            { value: '', name: '注释行数', itemStyle: '#6DC8EC' },
            { value: '', name: '空行数', itemStyle: '#3F8FFF' }
        ],
        callData:[7,3,5,4,6],//调用，被调用，圈复杂度，代码深度，参数个数




      }
    },


    methods:{
        getfilecontext(val){
          getFileContext(val).then(res => {
          if(res.success){
            this.content1 = res.data.content;
          }
          else{
            this.content1 = '程序加载有误，请重新选择文件'
          }});
        },



       
        drawLine () {
            this.rowAnalyse = this.$echarts.init(document.getElementById('rowChart'))
            this.rowAnalyse.setOption({
            title: {
            text: '行数统计', // 主标题
            subtext: '', // 副标题
            x: 'left' // x轴方向对齐方式
            },
            grid: { containLabel: true },
            tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} :{c}<br/> 占比:{d}%'
            },
            // color: ['#1FC48D', '#F5A60A', '#6DC8EC', '#3F8FFF'],
            color: [ '#1FC48D', '#6DC8EC', '#3F8FFF'],
            // backgroundColor: '#ffffff',
            legend: {
            orient: 'vertical',
            icon: 'circle',
            align: 'left',
            x: 'right',
            y: 'bottom',
            data: ['源代码行数', '注释行数', '空行数']
            },
            series: [
            {
                name: '行数统计',
                type: 'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                center: ['40%', '50%'],
                itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                },
                color: function (params) {
                    // 自定义颜色
                    var colorList = [ '#1FC48D', '#6DC8EC', '#3F8FFF']
                    return colorList[params.dataIndex]
                }
                },
                data: this.rowData
            }
            ]
            })

            let max_num=5
            console.log(max_num)
            let callChart = this.$echarts.init(document.getElementById('callChart'))
            let dataMax = [
                { name: '调用函数次数', max: max_num },
                { name: '被调用函数次数', max: max_num },
                { name: '圈复杂度', max: max_num },
                { name: '代码深度', max: max_num },
                { name: '参数个数', max: max_num },
            ]
            let option = {
                //配置维度的最大值
                        title: {
                    text: '调用分析', // 主标题
                    subtext: '', // 副标题
                    x: 'left' // x轴方向对齐方式
                    },
                radar: {
                name: {
                    show: true,
                    color: 'black',
                    fontSize:14,
                },
                //   雷达图的指示器，用来指定雷达图中的多个变量（维度）
                indicator: dataMax,
                shape: 'circle', //对雷达图形设置成一个圆形,可选 circle:圆形,polygon:多角形(默认)
                },
                series: [
                {
                    type: 'radar',
                    label: {
                    show: true,
                    fontSize:14,
                    
                     //显示数值
                    },
                    areaStyle: {}, //每个雷达图形成一个阴影的面积
                    data:[{
                        // value:[1,2,3,4,5]
                        value:this.callData
                    }]
                },
                ],
            }
            // 绘制图表
            callChart.setOption(option)





        },
        btn_ok(){
            //开始分析
            this.rowData[0].value=3;//'源代码行数'
            this.rowData[1].value=5;//'注释行数'
            this.rowData[2].value=7;//'空行数'
            //this.callData


            this.drawLine()
        }
        
    }
}
</script>



<style>

</style>

<style lang="scss" scoped>
.message_container{
  min-height: 100%;
  width: 100%;
  background-image:url('../../assets/bg-image.png');
  background-size:100%;
  position: fixed;
}
.choose_file{
position: fixed;
left:15%;
top:15%;
color:white;
opacity: 0.75;
}
.choose_func{
position: fixed;
left:15%;
top:22%;
color:white;
opacity: 0.75;
}
.display_area{
    width:700px;
    height: 500px;
    position: fixed;
    left:40%;
    top:15%;
    background-color:white;
    opacity: 0.9;
    overflow-y: scroll;
}
.file_btn{

}
</style>