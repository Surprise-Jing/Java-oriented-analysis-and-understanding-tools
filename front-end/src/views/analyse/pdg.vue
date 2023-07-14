<template>
    <div class = "pdgGraph" style="border: none; padding: 20px; width: 600px; height: 600px">
      <orgtree :data="testData" :horizontal="true" name="test" :label-class-name="labelClassName"    
      collapsable    @on-expand="onExpand" @on-node-mouseover="onMouseover" @on-node-mouseout="onMouseout"/> 
      <!-- 创建浮窗盒子 --><div v-show="BasicSwich" class="floating">    
        <p>ID:{{BasicInfo.id}}</p>    <p>Name:{{BasicInfo.label}}</p></div>
</div>
</template>



<script>
import orgtree from "../../components/orgtree";

  export default {
    components:{
      orgtree
    },
    data () {
      return {
        BasicSwich:false,	
        BasicInfo:{id:null,label:null},
        // testData: {
        //   label: 'xxx科技有有限公司',
        //   children: [{
        //     label: '产品研发部',
        //     children: [{
        //       label: '研发-前端',
        //     }, {
        //       label: '研发-后端',
        //     }, {
        //       label: 'UI 设计',
        //     }]
        //   }, {
        //     label: '销售部',
        //     children: [{
        //         label: '销售一部',
        //       },{
        //         label: '销售二部',
        //       }
        //     ]
        //   },{
        //     label: '财务部'
        //   }]
        // }
      testData:{"children":[{"children":[{"children":[{"children":[{"children":[{"children":[{"children":[{"children":[{"children":[{"children":[{"label":"va"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"children":[{"label":"VariableDeclarator"}],"label":"0"},{"children":[{"children":[{"children":[{"label":"PrimitiveType"}],"label":"0"},{"children":[{"label":"INT"}],"label":"1"}],"label":"type"}],"label":"1"}],"label":"type"},{"children":[{"children":[{"label":"IntegerLiteralExpr"}],"label":"type"},{"children":[{"label":"0"}],"label":"value"}],"label":"initializer"}],"label":"variable"}],"label":"variables"},{"children":[{"label":"FieldDeclaration"}],"label":"type"},{"children":[{"children":[{"children":[{"label":"Modifier"}],"label":"type"},{"children":[{"label":"PUBLIC"}],"label":"keyword"}],"label":"modifier"}],"label":"modifiers"}],"label":"0"},{"children":[{"children":[{"children":[{"children":[{"children":[{"label":"vb"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"children":[{"label":"VariableDeclarator"}],"label":"0"},{"children":[{"children":[{"children":[{"label":"PrimitiveType"}],"label":"0"},{"children":[{"label":"INT"}],"label":"1"}],"label":"type"}],"label":"1"}],"label":"type"},{"children":[{"children":[{"label":"IntegerLiteralExpr"}],"label":"type"},{"children":[{"label":"1"}],"label":"value"}],"label":"initializer"}],"label":"variable"}],"label":"variables"},{"children":[{"label":"FieldDeclaration"}],"label":"type"},{"children":[{"children":[{"children":[{"label":"Modifier"}],"label":"type"},{"children":[{"label":"PUBLIC"}],"label":"keyword"}],"label":"modifier"}],"label":"modifiers"}],"label":"1"},{"children":[{"children":[{"children":[{"label":"main"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"children":[{"label":"MethodDeclaration"}],"label":"0"},{"children":[{"children":[{"label":"VoidType"}],"label":"type"}],"label":"1"}],"label":"type"},{"children":[{"children":[{"children":[{"children":[{"children":[{"children":[{"children":[{"children":[{"children":[{"label":"sum"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"children":[{"label":"VariableDeclarator"}],"label":"0"},{"children":[{"children":[{"children":[{"label":"PrimitiveType"}],"label":"0"},{"children":[{"label":"INT"}],"label":"1"}],"label":"type"}],"label":"1"}],"label":"type"},{"children":[{"children":[{"label":"IntegerLiteralExpr"}],"label":"type"},{"children":[{"label":"0"}],"label":"value"}],"label":"initializer"}],"label":"variable"}],"label":"variables"},{"children":[{"label":"VariableDeclarationExpr"}],"label":"type"}],"label":"expression"},{"children":[{"label":"ExpressionStmt"}],"label":"type"}],"label":"0"},{"children":[{"children":[{"children":[{"children":[{"children":[{"children":[{"label":"prod"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"children":[{"label":"VariableDeclarator"}],"label":"0"},{"children":[{"children":[{"children":[{"label":"PrimitiveType"}],"label":"0"},{"children":[{"label":"INT"}],"label":"1"}],"label":"type"}],"label":"1"}],"label":"type"},{"children":[{"children":[{"label":"IntegerLiteralExpr"}],"label":"type"},{"children":[{"label":"1"}],"label":"value"}],"label":"initializer"}],"label":"variable"}],"label":"variables"},{"children":[{"label":"VariableDeclarationExpr"}],"label":"type"}],"label":"expression"},{"children":[{"label":"ExpressionStmt"}],"label":"type"}],"label":"1"},{"children":[{"children":[{"children":[{"children":[{"children":[{"children":[{"label":"i"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"children":[{"label":"VariableDeclarator"}],"label":"0"},{"children":[{"children":[{"children":[{"label":"PrimitiveType"}],"label":"0"},{"children":[{"label":"INT"}],"label":"1"}],"label":"type"}],"label":"1"}],"label":"type"},{"children":[{"children":[{"label":"IntegerLiteralExpr"}],"label":"type"},{"children":[{"label":"0"}],"label":"value"}],"label":"initializer"}],"label":"variable"}],"label":"variables"},{"children":[{"label":"VariableDeclarationExpr"}],"label":"type"}],"label":"expression"},{"children":[{"label":"ExpressionStmt"}],"label":"type"}],"label":"2"},{"children":[{"children":[{"children":[{"children":[{"children":[{"label":"i"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"NameExpr"}],"label":"type"}],"label":"left"},{"children":[{"children":[{"label":"IntegerLiteralExpr"}],"label":"type"},{"children":[{"label":"10"}],"label":"value"}],"label":"right"},{"children":[{"label":"BinaryExpr"}],"label":"type"},{"children":[{"label":"LESS"}],"label":"operator"}],"label":"condition"},{"children":[{"label":"WhileStmt"}],"label":"type"},{"children":[{"children":[{"children":[{"children":[{"children":[{"children":[{"label":"AssignExpr"}],"label":"type"},{"children":[{"children":[{"children":[{"children":[{"label":"i"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"NameExpr"}],"label":"type"}],"label":"left"},{"children":[{"children":[{"label":"IntegerLiteralExpr"}],"label":"type"},{"children":[{"label":"1"}],"label":"value"}],"label":"right"},{"children":[{"label":"BinaryExpr"}],"label":"type"},{"children":[{"label":"PLUS"}],"label":"operator"}],"label":"value"},{"children":[{"label":"ASSIGN"}],"label":"operator"},{"children":[{"children":[{"children":[{"label":"i"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"NameExpr"}],"label":"type"}],"label":"target"}],"label":"expression"},{"children":[{"label":"ExpressionStmt"}],"label":"type"}],"label":"0"},{"children":[{"children":[{"children":[{"children":[{"children":[{"label":"prod"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"NameExpr"}],"label":"type"}],"label":"left"},{"children":[{"children":[{"children":[{"label":"IntegerLiteralExpr"}],"label":"type"},{"children":[{"label":"100"}],"label":"value"}],"label":"left"},{"children":[{"children":[{"children":[{"label":"i"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"NameExpr"}],"label":"type"}],"label":"right"},{"children":[{"label":"BinaryExpr"}],"label":"type"},{"children":[{"label":"DIVIDE"}],"label":"operator"}],"label":"right"},{"children":[{"label":"BinaryExpr"}],"label":"type"},{"children":[{"label":"GREATER"}],"label":"operator"}],"label":"condition"},{"children":[{"children":[{"children":[{"children":[{"children":[{"children":[{"label":"AssignExpr"}],"label":"type"},{"children":[{"children":[{"children":[{"children":[{"label":"i"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"NameExpr"}],"label":"type"}],"label":"left"},{"children":[{"children":[{"label":"IntegerLiteralExpr"}],"label":"type"},{"children":[{"label":"1"}],"label":"value"}],"label":"right"},{"children":[{"label":"BinaryExpr"}],"label":"type"},{"children":[{"label":"MINUS"}],"label":"operator"}],"label":"value"},{"children":[{"label":"ASSIGN"}],"label":"operator"},{"children":[{"children":[{"children":[{"label":"i"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"NameExpr"}],"label":"type"}],"label":"target"}],"label":"expression"},{"children":[{"label":"ExpressionStmt"}],"label":"type"}],"label":"0"},{"children":[{"children":[{"label":"BreakStmt"}],"label":"type"}],"label":"1"}],"label":"statement"}],"label":"statements"},{"children":[{"label":"BlockStmt"}],"label":"type"}],"label":"thenStmt"},{"children":[{"label":"IfStmt"}],"label":"type"}],"label":"1"},{"children":[{"children":[{"children":[{"label":"AssignExpr"}],"label":"type"},{"children":[{"children":[{"children":[{"children":[{"label":"sum"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"NameExpr"}],"label":"type"}],"label":"left"},{"children":[{"children":[{"children":[{"label":"i"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"NameExpr"}],"label":"type"}],"label":"right"},{"children":[{"label":"BinaryExpr"}],"label":"type"},{"children":[{"label":"PLUS"}],"label":"operator"}],"label":"value"},{"children":[{"label":"ASSIGN"}],"label":"operator"},{"children":[{"children":[{"children":[{"label":"sum"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"NameExpr"}],"label":"type"}],"label":"target"}],"label":"expression"},{"children":[{"label":"ExpressionStmt"}],"label":"type"}],"label":"2"},{"children":[{"children":[{"children":[{"label":"AssignExpr"}],"label":"type"},{"children":[{"children":[{"children":[{"label":"prod"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"NameExpr"}],"label":"type"}],"label":"value"},{"children":[{"label":"ASSIGN"}],"label":"operator"},{"children":[{"children":[{"children":[{"label":"sum"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"NameExpr"}],"label":"type"}],"label":"target"}],"label":"expression"},{"children":[{"label":"ExpressionStmt"}],"label":"type"}],"label":"3"},{"children":[{"children":[{"children":[{"label":"AssignExpr"}],"label":"type"},{"children":[{"children":[{"children":[{"children":[{"label":"prod"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"NameExpr"}],"label":"type"}],"label":"left"},{"children":[{"children":[{"children":[{"label":"i"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"NameExpr"}],"label":"type"}],"label":"right"},{"children":[{"label":"BinaryExpr"}],"label":"type"},{"children":[{"label":"MULTIPLY"}],"label":"operator"}],"label":"value"},{"children":[{"label":"ASSIGN"}],"label":"operator"},{"children":[{"children":[{"children":[{"label":"prod"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"NameExpr"}],"label":"type"}],"label":"target"}],"label":"expression"},{"children":[{"label":"ExpressionStmt"}],"label":"type"}],"label":"4"}],"label":"statement"}],"label":"statements"},{"children":[{"label":"BlockStmt"}],"label":"type"}],"label":"body"}],"label":"3"},{"children":[{"children":[{"children":[{"children":[{"children":[{"children":[{"label":"System"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"NameExpr"}],"label":"type"}],"label":"scope"},{"children":[{"children":[{"label":"out"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"FieldAccessExpr"}],"label":"type"}],"label":"scope"},{"children":[{"children":[{"label":"println"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"children":[{"children":[{"children":[{"label":"i"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"NameExpr"}],"label":"type"}],"label":"argument"}],"label":"arguments"},{"children":[{"label":"MethodCallExpr"}],"label":"type"}],"label":"expression"},{"children":[{"label":"ExpressionStmt"}],"label":"type"}],"label":"4"},{"children":[{"children":[{"children":[{"children":[{"children":[{"children":[{"label":"System"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"NameExpr"}],"label":"type"}],"label":"scope"},{"children":[{"children":[{"label":"out"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"FieldAccessExpr"}],"label":"type"}],"label":"scope"},{"children":[{"children":[{"label":"println"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"children":[{"children":[{"children":[{"label":"sum"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"NameExpr"}],"label":"type"}],"label":"argument"}],"label":"arguments"},{"children":[{"label":"MethodCallExpr"}],"label":"type"}],"label":"expression"},{"children":[{"label":"ExpressionStmt"}],"label":"type"}],"label":"5"},{"children":[{"children":[{"children":[{"children":[{"children":[{"children":[{"label":"System"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"NameExpr"}],"label":"type"}],"label":"scope"},{"children":[{"children":[{"label":"out"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"FieldAccessExpr"}],"label":"type"}],"label":"scope"},{"children":[{"children":[{"label":"println"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"children":[{"children":[{"children":[{"label":"prod"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"NameExpr"}],"label":"type"}],"label":"argument"}],"label":"arguments"},{"children":[{"label":"MethodCallExpr"}],"label":"type"}],"label":"expression"},{"children":[{"label":"ExpressionStmt"}],"label":"type"}],"label":"6"}],"label":"statement"}],"label":"statements"},{"children":[{"label":"BlockStmt"}],"label":"type"}],"label":"body"},{"children":[{"children":[{"children":[{"children":[{"label":"Modifier"}],"label":"type"},{"children":[{"label":"PUBLIC"}],"label":"keyword"}],"label":"0"},{"children":[{"children":[{"label":"Modifier"}],"label":"type"},{"children":[{"label":"STATIC"}],"label":"keyword"}],"label":"1"}],"label":"modifier"}],"label":"modifiers"},{"children":[{"children":[{"children":[{"label":"false"}],"label":"isVarArgs"},{"children":[{"children":[{"label":"args"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"children":[{"label":"Parameter"}],"label":"0"},{"children":[{"children":[{"children":[{"children":[{"label":"String"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"ClassOrInterfaceType"}],"label":"type"}],"label":"componentType"},{"children":[{"label":"TYPE"}],"label":"origin"},{"children":[{"label":"ArrayType"}],"label":"type"}],"label":"1"}],"label":"type"}],"label":"parameter"}],"label":"parameters"}],"label":"2"}],"label":"member"}],"label":"members"},{"children":[{"children":[{"label":"WhileIf"}],"label":"identifier"},{"children":[{"label":"SimpleName"}],"label":"type"}],"label":"name"},{"children":[{"label":"ClassOrInterfaceDeclaration"}],"label":"type"},{"children":[{"children":[{"children":[{"label":"Modifier"}],"label":"type"},{"children":[{"label":"PUBLIC"}],"label":"keyword"}],"label":"modifier"}],"label":"modifiers"},{"children":[{"label":"false"}],"label":"isInterface"}],"label":"type"}],"label":"types"},{"children":[{"label":"CompilationUnit"}],"label":"type"}],"label":"root"}
      }
    },
    methods: {
      collapse(list) {   
        var _this = this;    
        list.forEach(
          function(child) {        
            if (child.expand) {          
              child.expand = false;        
            }        
            child.children && _this.collapse(child.children);	
          });},
      onExpand(e,data) {    
        if ("expand" in data) {       
          data.expand = !data.expand;    	
          if (!data.expand && data.children) {       		
            this.collapse(data.children);    	
          }    
        } 
        else {        
          this.$set(data, "expand", true);    
        }},
      renderLabelClass (node) {
        return 'label-class-blue'
      },
      renderCurrentClass (node) {
        return 'label-bg-blue'
      },
      onMouseout(e, data) {    
        this.BasicSwich = false
      },
      onMouseover(e, data) {    
        this.BasicInfo = data;    
        this.BasicSwich = true;    
        var floating = document.getElementsByClassName('floating')[0];    
        floating.style.left = e.clientX +'px';    
        floating.style.top = e.clientY+'px';
      }
    }
  }
</script>
<style>
.label-class-blue{
  color: #1989fa;
}
.label-bg-blue{
  background: #1989fa;
  color: #fff;
}
/* 盒子css */
.floating{    
  background: rgba(0, 0, 0, 0.7);    
  width: 160px;    
  height: 100px;    
  position: absolute;    
  color: #fff;    
  padding-top: 15px;    
  border-radius: 15px;    
  padding-left: 15px;    
  box-sizing: border-box;    
  left:0;    
  top: 0;    
  transition: all 0.3s;    
  z-index: 999;    
  text-align: left;    
  font-size: 12px;
}
</style>


