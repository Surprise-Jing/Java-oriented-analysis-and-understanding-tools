<template>
    <div class="box">
        <div class="graph">
        <svg class="canvas">
            <g></g>
        </svg>
        </div>
    </div>
</template>

<script>
    import dagreD3 from "dagre-d3";
    import * as d3 from "d3";
    export default {
        data() {
            return {
                list: {
                    nodeInfos: [
                        {
                            id: "node1",
                            label: "节点1",
                        },
                        {
                            id: "node2",
                            label: "节点2",
                        },
                        {
                            id: "node3",
                            label: "节点3",
                        },
                        {
                            id: "node4",
                            label: "节点4",
                        },
                    ],
                    edges: [
                        {
                            source: "node1",
                            target: "node2",
                        },
                        {
                            source: "node2",
                            target: "node3",
                        },
                        {
                            source: "node4",
                            target: "node2",
                        },
                        {
                            source: "node3",
                            target: "node4",
                        }
                    ]
                }
            };
        },
        mounted() {
            this.initGraph();

        },
        methods: {
            initGraph() {
                var g = new dagreD3.graphlib.Graph().setGraph({rankdir: 'LR'});
                // 添加节点
                let that = this;
                that.list.nodeInfos.forEach(item => {
                    g.setNode(item.id, {
                    //节点标签
                    label: item.label,
                    //节点形状
                    shape: "diamond",
                    //节点样式
                    style: "fill:#fff;stroke:#000",

                    labelStyle: "fill:#000;font-weight:bold"
                    })
                });
                this.list.edges.forEach(item => {
                    g.setEdge(item.source, item.target, {
                    //边标签
                    label: item.label,
                    //边样式
                    style: "fill:#fff;stroke:#333;stroke-width:1.5px"
                    })
                })
                //绘制图形
                var svg = d3.select(".box").select(".graph").select("svg")
                    .attr("preserveAspectRatio", "xMidYMid meet")
                    .attr("viewBox", "0 0 1000 500");
                var inner = svg.select("g");
                //缩放
                var zoom = d3.zoom().on("zoom", function () {
                    inner.attr("transform", d3.zoomTransform(svg.node()));
                });
                svg.call(zoom);

                var render = new dagreD3.render();
                render(inner, g);


                
        }
    }
}
</script>

<style lang="less">
    svg {
        font-size: 14px;
    }

    .node rect {
        stroke: #606266;
        fill: #fff;
    }

    .edgePath path {
        stroke: #606266;
        fill: #333;
        stroke-width: 1.5px;
    }
    .box {
        width:1200px;
        height:550px;
        background-color:rgb(255, 255, 255);
        position: relative;
}
    .graph {
        width: 1000px;
        height: 500px;
        border: solid;
        background-color: rgb(255, 255, 255);
        position: absolute;
        left:0px;
        right: 0px;
        top:0px;
        bottom: 0px;
        margin: auto;
    }
    .canvas {
        width: 1000px;
        height: 500px;
    }
</style>