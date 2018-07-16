<template>
  <div>
    <v-pageTitle vtitle="系统操作"></v-pageTitle>


    <el-card class="box-card">
      <el-row :gutter="20" type="flex" align="middle">
        <el-col :xs="10" :sm="10" :md="10" :lg="10">
          <span class="customFont">台账信息上传:</span>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12">

          <el-upload
            class="upload-demo"
            drag
            action="http://203.195.152.87/api/standbook/import"
            :limit="1"
            :file-list="fileList"
            :on-success="handleResponse">
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <div class="el-upload__tip" slot="tip" style="margin-left: 50px">只能上传xls/xlsx文件，且不超过100Mb</div>
          </el-upload>
        </el-col>

      </el-row>
      <el-row :gutter="20" style="margin-top: 70px" type="flex" align="middle">
        <el-col :xs="12" :sm="12" :md="12" :lg="12">
          <span class="customFont">后台任务执行(生成待付款、待续约等合同信息):</span>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12">
          <el-button type="primary" style="width: 100px" round @click="executeTask">执行</el-button>
        </el-col>
      </el-row>
    </el-card>


  </div>


</template>

<script>
  import vPageTitle from '../common/pageTitle.vue';
  import api from '../../axios';

  export default {
    data() {
      return {
        fileList: []
      }
    },
    components: {
      vPageTitle
    },
    methods: {
      handleResponse(response, file, fileList) {
        console.log(response);
        this.fileList = [];
        if (response.code === 400) {
          this.$message.warning(response.message);
        }
        if (response.code === 200) {
          this.$message.info("上传成功！");
        }
      },
      executeTask() {
        api.executeTaskStandBook().then(res => {
          if (res.data.code === 200) {
            this.$message.info("执行任务成功！");
          } else {
            this.$message.warning("执行任务失败！");
          }
        })
      }
    }
  }
</script>

<style>
  .el-col {
    margin-bottom: 16px;
  }

  .customFont {
    font-family: "Helvetica Neue";
    font-size: large;
  }
</style>
