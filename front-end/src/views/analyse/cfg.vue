<template>
  <div class = "cfgGraph">
  <div id = "image">
  </div>
  </div>
</template>
   
<script>


import * as vis from 'vis-network'
  
export default {
  name: 'cfgGraph',
  data() {
    return {
      showHover: false,
      dot : `
      digraph G {
  0 [ label="ENTER main" ];
  1 [ label="EXIT main" ];
  2 [ label="int n = 0;" ];
  3 [ label="int i = 1;" ];
  4 [ label="int sum = 0;" ];
  5 [ label="int product = 1;" ];
  6 [ shape="diamond" label="i < n" ];
  7 [ label="sum = sum + i;" title="test"];
  8 [ label="product = product * i;" ];
  9 [ label="i = i + 1;" ];
  10 [ label="System.out.println(sum);" ];
  11 [ label="System.out.println(product);" ];
  0 -> 2;
  2 -> 3;
  3 -> 4;
  4 -> 5;
  5 -> 6;
  6 -> 7;
  7 -> 8;
  8 -> 9;
  9 -> 6;
  6 -> 10;
  10 -> 11;
  11 -> 1;
}
`,

    };
  },
  mounted () {
    this.draw();

  },
  methods : {
    draw(){

      let that = this;
      let parsedData = vis.parseDOTNetwork(that.dot);
      let data = {
        nodes: parsedData.nodes,
        edges: parsedData.edges
      }

console.log(data.nodes.label);
console.log(data.edges);

let options = parsedData.options;


options.nodes = {
  color: {
      border: '#000',
      background: '#fff',
    },
  fixed: {
    x: false,
    y: false
  }
};
options.edges = {
  color: "black"
};
options.layout = {
    hierarchical: {
      direction: 'UD',        
      sortMethod: 'directed',
      levelSeparation: 75,
      nodeSpacing: 200
    }
  };

  options.interaction = {
    tooltipDelay: 300
  }

  options.physics = { 

enabled: false,}


let container = document.getElementById("image");
let network = new vis.Network(container, data, options);

  }}
  
}
</script>
   
<style type="text/css">
.cfgGraph {
	width: 1200px;
	height: 500px;
	border: 1px solid #000000;
	position: relative;
}
      #image {
        width: 1000px;
	height: 500px;
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	margin: auto;
	background: #ffffff;
      }
    </style>