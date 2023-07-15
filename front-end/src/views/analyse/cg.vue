<template>
    <div class = "cgGraph" style="border: none; padding: 20px; width: 600px; height: 600px">
    <svg class="graph" width="1200" height="1200">
      <g class="container"></g>
    </svg>
    </div>

</template>

<script>
import Vis from "vis";
import vis from "vis-network";
export default {
  data() {
    return {
      dialogVisible: false,
      nodes: [],
      edges: [],
      container: null,
      inputData : 'digraph G { 0 [ label="ENTER main" ]; 1 [ label="EXIT main" ];2 [ label="int n = 0;" ];3 [ label="int i = 1;" ];4 [ label="int sum = 0;" ];5 [ label="int product = 1;" ];6 [ shape="diamond" label="i < n" ];7 [ label="sum = sum + i;" ];8 [ label="product = product * i;" ];9 [ label="i = i + 1;" ];10 [ label="System.out.println(sum);" ];11 [ label="System.out.println(product);" ];0 -> 2;2 -> 3;3 -> 4;4 -> 5;5 -> 6;6 -> 7;7 -> 8;8 -> 9;9 -> 6;6 -> 10;10 -> 11;11 -> 1}',
      nodesArray: [],
      edgesArray: [],
      options: {},
      data: {}
    };
  },
  methods: {
    getfileid(val){
        console.log(val)
        },
    init() {
      let _this = this;
      //var parserdData = vis.network.convertDot(_this.inputData);
      var parserdData = vis.parseDOTNetwork(_this.inputData);

      _this.data = parserdData;
      // _this.nodes = new Vis.DataSet(parserdData.nodes);
      // _this.edges = new Vis.DataSet(parserdData.edges);

      // console.log(_this.nodes);
      // console.log(_this.edges);

      _this.container = document.getElementById("image");
      // _this.data = vis.parseDOTNetwork(_this.inputData);
      // console.log(_this.data);

      _this.options = {
        autoResize: true, //网络将自动检测其容器的大小调整，并相应地重绘自身

        // 设置节点样式
        nodes: {
          shape: "circle",
          size: 50,
          font: {
            //字体配置
            size: 30
          },
          color: {
            border: "#000",
            background: "#fff",
            highlight: {
              //节点选中时状态颜色
              border: "#2B7CE9",
              background: "#D2E5FF"
            },
            hover: {
              //节点鼠标滑过时状态颜色
              border: "#2B7CE9",
              background: "#D2E5FF"
            }
          },
          borderWidth: 1, //节点边框宽度，单位为px
          borderWidthSelected: 2 //节点被选中时边框的宽度，单位为px
        },
        // 边线配置
        edges: {
          width: 1,
          length: 50,
          color: {
            color: "#848484",
            highlight: "#848484",
            hover: "#848484",
            inherit: "from",
            opacity: 1.0
          },
          shadow: false,
          smooth: {
            //设置两个节点之前的连线的状态
            enabled: false //默认是true，设置为false之后，两个节点之前的连线始终为直线，不会出现贝塞尔曲线
          },
          arrows: { to: true } //箭头指向to
        },
        layout: {
          hierarchical: {
      levelSeparation: 150,
      nodeSpacing: 100,
      direction: 'UD',        // UD, DU, LR, RL
      sortMethod: 'directed',  // hubsize, directed
      shakeTowards: 'leaves'  // roots, leaves
    }
        },
        //计算节点之前斥力，进行自动排列的属性
        physics: {
          enabled: false,
        },
        //用于所有用户与网络的交互。处理鼠标和触摸事件以及导航按钮和弹出窗口
        interaction: {
          hover: true,
          dragNodes: true, //是否能拖动节点
          dragView: true, //是否能拖动画布
          hover: true, //鼠标移过后加粗该节点和连接线
          multiselect: true, //按 ctrl 多选
          selectable: true, //是否可以点击选择
          selectConnectedEdges: true, //选择节点后是否显示连接线
          hoverConnectedEdges: true, //鼠标滑动节点后是否显示连接线
          zoomView: true //是否能缩放画布
        },
        //操作模块:包括 添加、删除、获取选中点、设置选中点、拖拽系列、点击等等
        manipulation: {
          enabled: false, //该属性表示可以编辑，出现编辑操作按钮
          addNode: true,
          addEdge: true,
          // editNode: undefined,
          editEdge: true,
          deleteNode: true,
          deleteEdge: true
        }
      };

      _this.network = new Vis.Network(
        _this.container,
        _this.data,
        _this.options
      );
    },

    resetAllNodes() {
      let _this = this;
      _this.nodes.clear();
      _this.edges.clear();
      _this.nodes.add(_this.nodesArray);
      _this.edges.add(_this.edgesArray);
      // _this.data = {
      //   nodes: _this.nodes,
      //   edges: _this.edges
      // };
      //   network是一种用于将包含点和线的网络和网络之间的可视化展示
      _this.network = new Vis.Network(
        _this.container,
        _this.data,
        _this.options
      );
    },
    resetAllNodesStabilize() {
      let _this = this;
      _this.resetAllNodes();
      _this.network.stabilize();
    }
  },

  mounted() {
    this.init()
  }
};
</script>

<style>

</style>
