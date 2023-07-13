<template>
    <div class = "cfgGraph" style="border: none; padding: 20px; width: 600px; height: 600px">
      <el-select v-model="value" placeholder="请选择">
        <el-option
          v-for="item in fileData"
          :key="item.id"
          :label="item.fileName"
          :value="item.id">
        </el-option>
      </el-select>

    <div class="col-8">
      <center><div class="image" v-html="image"></div></center>
    </div>
</div>
</template>

<script src="viz.js"></script>
<script src="full.render.js"></script>

<script>

  // var viz = new Viz();
  // viz.renderSVGElement('digraph { a -> b }')
  // .then(function(element) {
  //   document.body.appendChild(element);
  //   image = element;
  // })
  // .catch(error => {
  //   // Create a new Viz instance (@see Caveats page for more info)
  //   viz = new Viz();
  //   // Possibly display the error
  //   console.error(error);
  // });

export default {
  name: 'cfgGraph',
  data() {
    return {
      value: '',
      fileData:[
        {
          id:'111',
          fileName:'test1',
          file_time:'2022.0.1'
        },
        {
          id:'222',
          fileName:'test2',
          file_time:'2022.0.1'
        },
        {
          id:'333',
          fileName:'test3',
          file_time:'2022.0.1'
        },
      ],
      selectFile:{
        id:''
      },
      dots:'digraph "x"{\n  1->2\n  5->2;\n  1->3;\n  3->4;\n  4->1\n}',
      //nodes: [],
      //edges: []
      //测试用数据
    nodes: [
      { id: 0, label: "流动人员", shape: "rect" },
      { id: 1, label: "安全筛查", shape: "rect" },
      { id: 2, label: "热像仪人体测温筛查", shape: "diamond" },
      { id: 3, label: "人工复测", shape: "diamond" },
      { id: 4, label: "快速通过", shape: "rect" },
      { id: 5, label: "紧急处理", shape: "rect" }
    ],
    edges: [
      { source: 0, target: 1, label: "" },
      { source: 1, target: 2, label: "" },
      { source: 2, target: 4, label: "正常" },
      { source: 2, target: 3, label: "不正常" },
      { source: 3, target: 5, label: "不正常" },
      { source: 3, target: 4, label: "正常" }
    ]
    };
  },
  computed:{
    image: function(){
      var viz = new Viz({workURL});
      var image = Viz(this.dots, {format: 'png-image-element'});
      document.body.appendChild(image);
      return image;
    }
  },
  mounted() {
    var that = this;
    that.draw();
  },
  methods: {
    getfileid(val){
      console.log(val)
    },
  //绘图
  draw() {
    let g = new dagreD3.graphlib.Graph();
  //设置图
  g.setGraph({
    rankdir: 'LR'
  });

  console.log(this.nodes);
  console.log(this.edges);

  this.nodes.forEach(item => {
    g.setNode(item.id, {
      //节点标签
      label: item.label,
      //节点形状
      shape: item.shape,
      //节点样式
      style: "fill:#F0F8FF;stroke:#000"
    })
  })
  this.edges.forEach(item => {
    g.setEdge(item.source, item.target, {
      //边标签
      label: item.label,
      //边样式
      style: "fill:#4682B4;stroke:#000;stroke-width:2px"
    })
  })
  // 创建渲染器
  let render = new dagreD3.render();
  // 选择 svg 并添加一个g元素作为绘图容器.
  let svgGroup = d3.select('svg').append('g');
  // 在绘图容器上运行渲染器生成流程图.
  render(svgGroup, g);

  },

},
}
</script>

<style>

.image {
    position: fixed;
    top: 80px;
    left: 50%;
}

</style>
