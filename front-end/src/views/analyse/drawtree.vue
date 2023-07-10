<template>
    <AST v-if="showMindMap" :active='active' :data="mapData.webMap" @activeChange="activeChange"/>
</template>
<script>

  // 导入思维导图组件
  import ast from 'ast.vue'

  export default {
      components:{ast},
      data() {
          return {
              active: '',
              mapData: null,
              showMindMap: false
          }
      },
      mounted() {
          // 获取到数据后，再加载思维导图
          if (this.data && JSON.stringify(this.data) !== "{}") {
                    data = this.data
                } else {
                    this.$axios({
			    methods:'get',
			    url:'https://xxxxxxxxxxxxxxxxxx',
		    })
            .then((res) => {
                if(res.code==200) {
                console.log(res.data);
                this.data = res.data;}})
            .catch((err) => {
                console.log(err)
            })
                }
          this.showMindMap = true
      },
      methods: {
          // 点击思维导图节点后，触发变量更新
          activeChange(newLabel) {
              this.active = newLabel
              this.reloadMindMap()
          },
          // 重载思维导图
          reloadMindMap() {
              this.showMindMap = false
              this.$nextTick(
                  () => {
                      this.showMindMap = true
                  }
              )
          },
      }
  }
</script>