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
    <center><div id="image" style="border: solid; padding: 20px; width: 1170px; height: 400px"></div></center>
  </div>
</div>
</template>
<script>
import Vis from "vis";
export default {
  data() {
    return {
      dialogVisible: false,
      nodes: [],
      edges: [],
      container: null,
      nodesArray: [
        {
          id: 0,
          label: "大前端",
          shape: "rect",
        },
        {
          id: 1,
          label: "HTML",
          shape: "circle",
        },
        {
          id: 2,
          label: "JavaScript",
          shape: "circle",
        },
        {
          id: 3,
          label: "CSS",
          shape: "circle",
        },
        {
          id: 4,
          label: "三大主流框架",
          shape: "circle",
        },
        {
          id: 5,
          label: "vue.js",
          shape: "circle",
        },
        {
          id: 6,
          label: "react.js",
          shape: "diamond",
        },
        {
          id: 7,
          label: "angular.js",
          shape: "diamond",
        }
      ],
      edgesArray: [
        { from: 0, to: 1 },
        { from: 1, to: 0 },
        { from: 0, to: 2 },
        { from: 0, to: 3 },
        { from: 0, to: 4 },
        { from: 4, to: 5 },
        { from: 4, to: 6 },
        { from: 4, to: 7 }
      ],
      options: {},
      data: {}
    };
  },
  methods: {
    init() {
      let _this = this;

      _this.nodes = new Vis.DataSet(_this.nodesArray);
      _this.edges = new Vis.DataSet(_this.edgesArray);

      _this.container = document.getElementById("image");
      _this.data = {
        nodes: _this.nodes,
        edges: _this.edges
      };

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
      _this.data = {
        nodes: _this.nodes,
        edges: _this.edges
      };
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
    this.init();
  }
};
</script>
<style lang="less">
</style>
