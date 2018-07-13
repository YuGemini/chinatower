<template>
  <div>
    <div>
      <el-row type="flex" align="middle">
        <el-col :xs="3" :sm="3" :md="3" :lg="3">
          <span class="customFont">所属区域 :</span>
        </el-col>
        <el-col :xs="7" :sm="7" :md="7" :lg="7">
          <Input v-model="filter.region" style="width: 200px">
          </Input>
        </el-col>
        <el-col :xs="3" :sm="3" :md="3" :lg="3">
          <span class="customFont">站点编码 :</span>
        </el-col>
        <el-col :xs="7" :sm="7" :md="7" :lg="7">
          <Input v-model="filter.code" style="width: 200px">
          </Input>
        </el-col>
      </el-row>
      <el-row type="flex" align="middle">
        <el-col :xs="3" :sm="3" :md="3" :lg="3">
          <span class="customFont">站点名称 :</span>
        </el-col>
        <el-col :xs="7" :sm="7" :md="7" :lg="7">
          <Input v-model="filter.siteName" style="width: 200px">
          </Input>
        </el-col>
        <el-col :xs="3" :sm="3" :md="3" :lg="3">
          <span class="customFont">付款时间 :</span>
        </el-col>
        <el-col :xs="7" :sm="7" :md="7" :lg="7">
          <DatePicker type="daterange"
                      :value="time"
                      :split-panels=false
                      @on-change="handleChange"
                      placement="bottom-end"
                      placeholder="选择时间范围"
                      style="width: 200px"></DatePicker>
          </Input>
        </el-col>
        <el-col :xs="2" :sm="2" :md="2" :lg="2">
          <Button type="primary" @click="search">查询</Button>
        </el-col>
        <el-col :xs="2" :sm="2" :md="2" :lg="2">
          <Button type="primary" @click="reset">重置</Button>
        </el-col>
      </el-row>
    </div>
    <div>
      <el-table
        :data="standBookList"
        height="490"
        stripe
        style="width: 100%">
        <el-table-column type="expand">

          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="所属区域">
                <span>{{ props.row.region }}</span>
              </el-form-item>
              <el-form-item label="站点编码">
                <span>{{ props.row.code }}</span>
              </el-form-item>
              <el-form-item label="站点名称">
                <span>{{ props.row.siteName }}</span>
              </el-form-item>
              <el-form-item label="合同属性">
                <span>{{ props.row.attribute }}</span>
              </el-form-item>
              <el-form-item label="合同名称">
                <span>{{ props.row.contractName }}</span>
              </el-form-item>
              <el-form-item label="业主">
                <span>{{ props.row.ownerName }}</span>
              </el-form-item>
              <el-form-item label="联系方式">
                <span>{{ props.row.phoneNumber }}</span>
              </el-form-item>
              <el-form-item label="合同开始时间">
                <span>{{ props.row.contractStart|formatDate}}</span>
              </el-form-item>
              <el-form-item label="合同结束时间">
                <span>{{ props.row.contractEnd|formatDate}}</span>
              </el-form-item>
              <el-form-item label="付款时间">
                <span>{{ props.row.payTime|formatDate }}</span>
              </el-form-item>
              <el-form-item label="开始时间">
                <span>{{ props.row.start|formatDate }}</span>
              </el-form-item>
              <el-form-item label="结束时间">
                <span>{{ props.row.end|formatDate }}</span>
              </el-form-item>
              <el-form-item label="房租">
                <span>{{ props.row.rent }} 元</span>
              </el-form-item>
              <el-form-item label="税金">
                <span>{{ props.row.tax }} 元</span>
              </el-form-item>
              <el-form-item label="ID" style="display:none">
                <span>{{ props.row.id }}</span>
              </el-form-item>

            </el-form>
          </template>
        </el-table-column>
        <el-table-column
          label="站点编码"
          prop="code">
        </el-table-column>
        <el-table-column
          label="站点名称"
          prop="siteName">
        </el-table-column>
        <el-table-column
          label="合同名称"
          prop="contractName">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row)" type="text" size="small">编辑</el-button>
            <el-button type="text" size="small">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="block">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="filter.curPage"
        :page-sizes="[10, 20, 40, 80]"
        :page-size="filter.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<style>
  .demo-table-expand {
    font-size: 0;
  }

  .demo-table-expand label {
    width: 120px;
    color: #99a9bf;
  }

  .demo-table-expand .el-form-item {
    margin-right: 0 !important;
    margin-bottom: 0;
    width: 50%;
  }

  .el-pagination {
    float: right;
    margin-top: 15px;
  }
</style>

<script>
  import api from '../../axios';
  import moment from 'moment';

  export default {
    methods: {
      handleClick(row) {
        console.log(row);
      },
      getStandBookList(param) {
        var _getAll = this;
        api.getStandBook(param).then(res => {
          _getAll.total = res.data.total;
          _getAll.standBookList = res.data.results;
        })
      },

      handleSizeChange(val) {
        this.filter.pageSize = val;
        this.getStandBookList({params: this.filter});
      },
      handleCurrentChange(val) {
        this.filter.curPage = val;
        this.getStandBookList({params: this.filter});
      },
      handleChange(date1){
        this.filter.startTime=date1[0];
        this.filter.endTime=date1[1];
      },
      search() {
        if(this.filter.curPage===1){
          this.getStandBookList({params: this.filter});
        }else{
          this.filter.curPage = 1;
        }
      },
      reset(){
        this.time=[];
        this.filter.region='';
        this.filter.siteName='';
        this.filter.code='';
        this.filter.startTime='';
        this.filter.endTime='';

      }
    },
    data: function () {
      return {
        standBookList: [],
        total: 0,
        time: [],
        filter: {
          curPage: 1,
          pageSize: 10,
          siteName: '',
          region: '',
          code: '',
          startTime: '',
          endTime: ''
        }
      }
    },
    filters: {
      //时间格式化
      formatDate(allupdatetime) {
        var date = new Date(allupdatetime);
        return moment(date).format('YYYY-MM-DD');
      }
    },
    mounted() {
      this.getStandBookList({params: this.filter});
    }

  }
</script>
