<template>
  <div>
      CFG,待完善
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
    return {
      fileData:[
        {
          fileId:'111',
          fileName:'test1',
          file_time:'2022.0.1'
        },
        {
          fileId:'222',
          fileName:'test2',
          file_time:'2022.0.1'
        },
        {
          fileId:'333',
          fileName:'test3',
          file_time:'2022.0.1'
        },
      ],
      selectFile:{
        id:''
      }
    }
  },
  methods:{
    getfileid(val){
      console.log(val)
    }
  }

}
</script>

<style>

</style>