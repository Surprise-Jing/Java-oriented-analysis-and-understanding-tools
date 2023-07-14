
<template>
    <div :id=id>
        <div>
              <select
              v-model="selectFile.id"
              @change="getfileid(selectFile.id)" style="width: 150px;">
        <option
          class="choose_file"
              v-for="item in fileData"
              :key="item.fileId"
              :label="item.fileName"
              :value="item.fileId"
              >
          </option>
              </select>
      </div>
    </div>

</template>

<script type="text/javascript" src="../../main.js"></script>

<script >
  import * as d3 from 'd3';
  export default {
      props: {
          astData: Object,
          nodeWidth:
              {
                  type: Number,
                  default: 160
              },
          nodeHeight:
              {
                  type: Number,
                  default: 40
              },
          active:
              {
                  type: String,
                  default: ''
              }
      },
      data() {
          return {
            fileData:[],//要选择的文件
            selectFile:{
                id:''
            },
              id: 'ASTtree' + randomString(4),
              data: {},
              deep: 0,
              treeData: null,
              show: true,
          }
      },
      mounted() {
          this.$nextTick(
              () => {
                  this.drawMap()
              }
          )
      },
      methods: {
        getfileid(val){
        console.log(val)
        },
          drawMap() {
              let that = this
              // 源数据
              let data = {}
              // 判断data是否为空
              //url待填写
              if (this.data && JSON.stringify(this.data) !== "{}") {
                  data = this.data
              } else {
                  this.$axios({
        methods:'get',
        url:'http://172.27.130.2:8080/graph/ast',
      })
          .then((res) => {
              if(res.code==200) {
              console.log(res.data);
              this.data = res.data;}})
          .catch((err) => {
              console.log(err)
          })
              }
              if (!this.treeData) {
                  this.treeData = data
              } else {
                  // 清空画布
                  d3.select('#' + this.id).selectAll("svg").remove();
              }
              let leafList = []
              getTreeLeaf(data, leafList)
              let leafNum = leafList.length
              let TreeDeep = getDepth(data)
              // 左右内边距
              let mapPaddingLR = 10
              // 上下内边距
              let mapPaddingTB = 0
              let mapWidth = this.nodeWidth * TreeDeep + mapPaddingLR * 2;
              let mapHeight = (this.nodeHeight - 4) * leafNum + mapPaddingTB * 2;
              // 定义画布—— 外边距 10px
              let svgMap = d3.select('#' + this.id).append('svg').attr("width", mapWidth).attr("height", mapHeight).style("margin", "0px")
              // 定义树状图画布
              let treeMap = svgMap.append("g").attr("transform", "translate(" + mapPaddingLR + "," + (mapHeight / 2 - mapPaddingTB) + ")");
              // 将源数据转换为可以生成树状图的数据(有节点 nodes 和连线 links )
              let treeData = d3.tree()
              // 设置每个节点的尺寸
                  .nodeSize(
                      // 节点包含后方的连接线 [节点高度，节点宽度]
                      [this.nodeHeight, this.nodeWidth]
                  )
                  // 设置树状图节点之间的垂直间隔
                  .separation(function (a, b) {
                      // 根据节点子节点的数量，动态调整节点间的间距
                      let rate = (a.parent == b.parent ? (b.children ? b.children.length / 2 : 1) : 2) / a.depth
                      // 间距比例不能小于0.7，避免重叠
                      if (rate < 0.7) {
                          rate = 0.7
                      }
                      return rate;
                  })(
                      // 创建层级布局，对源数据进行数据转换
                      d3.hierarchy(data).sum(function (node) {
                          return node.value;
                      })
                  )
              // 贝塞尔曲线生成器
              let Bézier_curve_generator = d3.linkHorizontal()
                  .x(function (d) {
                      return d.y;
                  })
                  .y(function (d) {
                      return d.x;
                  });
              //绘制边
              treeMap.selectAll("path")
              // 节点的关系
                  .data(treeData.links())
                  .enter()
                  .append("path")
                  .attr("d", function (d) {
                      // 根据name值的长度调整连线的起点
                      var start = {
                          x: d.source.x,
                          // 连线起点的x坐标
                          // 设置与圆圈的间距，link内文字与边框的间距，标签文字与连线起点的间距
                          y: d.source.y + 10 + (d.source.data.link ? (getPXwidth(d.source.data.link) + 10) : 0) + getPXwidth(d.source.data.label) + 10
                      };
                      var end = {x: d.target.x, y: d.target.y};
                      return Bézier_curve_generator({source: start, target: end});
                  })
                  .attr("fill", "none")
                  .attr("stroke", "#c3c3c3")
                  // 虚线
                  // .attr("stroke-dasharray", "8")
                  .attr("stroke-width", 1);
              // 创建分组——节点+文字
              let groups = treeMap.selectAll("g")
              // 节点 nodes
                  .data(treeData.descendants()
                  )
                  .enter()
                  .append("g")
                  .attr("transform", function (d) {
                      var cx = d.x;
                      var cy = d.y;
                      return "translate(" + cy + "," + cx + ")";
                  });
              //绘制节点(节点前的圆圈)
              groups.append("circle")
              // 树的展开折叠
                  .on("click", function (event, node) {
                      let data = node.data
                      if (data.children) {
                          data.childrenTemp = data.children
                          data.children = null
                      } else {
                          data.children = data.childrenTemp
                          data.childrenTemp = null
                      }
                      that.drawMap()
                  })
                  .attr("cursor", 'pointer')
                  .attr("r", 4)
                  .attr("fill", function (d) {
                      if (d.data.childrenTemp) {
                          return 'blue'
                      } else {
                          return 'white'
                      }
                  })
                  .attr("stroke", "black")
                  .attr("stroke-width", 1);
              //绘制标注(节点前的矩形)
              //groups.append("rect")
              //    .attr("x", 8)
              //    .attr("y", -10)
              //    .attr("width",
              //        function (d) {
              //            return d.data.link ? (getPXwidth(d.data.link) + 10) : 0
              //        }
              //    )
              //    .attr("height", 22)
              //    .attr("fill", "grey")
                  // 添加圆角
              //    .attr("rx", 4)
              //绘制链接方式
              groups.append("text")
                  .attr("x", 12)
                  .attr("y", -5)
                  .attr("dy", 10)
                  .attr("fill", 'white')
                  .attr("font-size", 12)
                  .text(function (d) {
                      return d.data.link;
                  })
              //绘制文字
              groups.append("text")
                  .on("click", function (event, node) {
                      let data = node.data
                      // 被禁用的节点，点击无效
                      if (data.disabled) {
                          return
                      }
                      // 有外链的节点，打开新窗口后恢复到思维导图页面
                      if (data.url) {
                          window.open(data.url)
                          that.$emit('activeChange', 'map')
                          return
                      }
                      // 标准节点—— 传出 prop
                      if (data.dicType) {
                          that.$emit('dicTypeChange', data.dicType)
                      }
                      // 标准节点—— 传出 prop
                      if (data.prop) {
                          that.$emit('activeChange', data.prop)
                      }
                  })
                  .attr("x", function (d) {
                      return 12 + (d.data.link ? (getPXwidth(d.data.link) + 10) : 0)
                  })
                  .attr("fill",
                      function (d) {
                          if (d.data.prop === that.active) {
                              return '#409EFF'
                          }
                      }
                  )
                  .attr("font-weight",
                      function (d) {
                          if (d.data.prop === that.active) {
                              return 'bold'
                          }
                      })
                  .attr("font-size", 14)
                  .attr("cursor",
                      function (d) {
                          if (d.data.disabled) {
                              return 'not-allowed'
                          } else {
                              return 'pointer'
                          }
                      })
                  .attr("y", -5)
                  .attr("dy", 10)
                  .attr("slot", function (d) {
                      return d.data.prop;
                  })
                  .text(function (d) {
                      return d.data.label;
                  })
          },
      },
  }

  // 获取树的深度
  function getDepth(json) {
      var arr = [];
      arr.push(json);
      var depth = 0;
      while (arr.length > 0) {
          var temp = [];
          for (var i = 0; i < arr.length; i++) {
              temp.push(arr[i]);
          }
          arr = [];
          for (var i = 0; i < temp.length; i++) {
              if (temp[i].children && temp[i].children.length > 0) {
                  for (var j = 0; j < temp[i].children.length; j++) {
                      arr.push(temp[i].children[j]);
                  }
              }
          }
          if (arr.length >= 0) {
              depth++;
          }
      }
      return depth;
  }

  // 提取树的子节点，最终所有树的子节点都会存入传入的leafList数组中
  function getTreeLeaf(treeData, leafList) {
      // 判断是否为数组
      if (Array.isArray(treeData)) {
          treeData.forEach(item => {
              if (item.children && item.children.length > 0) {
                  getTreeLeaf(item.children, leafList)
              } else {
                  leafList.push(item)
              }
          })
      } else {
          if (treeData.children && treeData.children.length > 0) {
              getTreeLeaf(treeData.children, leafList)
          } else {
              leafList.push(treeData)
          }
      }
  }

  // 获取包含汉字的字符串的长度
  function getStringSizeLength(string) {
      return string.replace(/[\u0391-\uFFE5]/g, "aa").length;
  }

  // 生成随机的字符串
  function randomString(strLength) {
      strLength = strLength || 32;
      let strLib = "ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz"
      let n = "";
      for (let i = 0; i < strLength; i++) {
          n += strLib.charAt(Math.floor(Math.random() * strLib.length));
      }
      return n
  }

  // 获取字符串的像素宽度
  function getPXwidth(str, fontSize = "12px", fontFamily = "Microsoft YaHei") {
      var span = document.createElement("span");
      var result = {};
      result.width = span.offsetWidth;
      result.height = span.offsetHeight;
      span.style.visibility = "hidden";
      span.style.fontSize = fontSize;
      span.style.fontFamily = fontFamily;
      span.style.display = "inline-block";
      document.body.appendChild(span);
      if (typeof span.textContent != "undefined") {
          span.textContent = str;
      } else {
          span.innerText = str;
      }
      result.width = parseFloat(window.getComputedStyle(span).width) - result.width;
      // 字符串的显示高度
      // result.height = parseFloat(window.getComputedStyle(span).height) - result.height;
      return result.width;
  }
</script>
