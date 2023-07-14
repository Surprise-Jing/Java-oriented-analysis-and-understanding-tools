<template>
  <div>
      DFG,待完善
      


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

<script>
export default {  
  data(){
    return{
      num:'0',
      fileData:[],//要选择的文件
              selectFile:{
                  id:''
              },
    }
  },
  methods:{
    getfileid(val){
      console.log(val)
    },


  
    

  }
}
</script>

<style>

</style>