import axios from 'axios';
import store from './store';
import router from './router';

//设置全局axios默认值

//axios.defaults.timeout = 150000; //15000的超时验证
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';

//创建一个axios实例
const instance = axios.create();
//const _baseUrl = '/gray/';
const _baseUrl = 'http://172.16.60.202/gray/'
instance.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
axios.interceptors.request.use = instance.interceptors.request.use;

//request拦截器
instance.interceptors.request.use(
  config => {
    //每次发送请求之前检测都vuex存有token,那么都要放在请求头发送给服务器
    if (store.state.token) {
      config.headers.Authorization = `${store.state.token}`;
    }
    return config;
  },
  err => {
    return Promise.reject(err);
  }
);
//response拦截器
instance.interceptors.response.use(
  response => {
    if(response.data.code === 403) {
      store.dispatch('UserLogout'); //可能是token过期，清除它
      router.replace({
        //跳转到登录页面
        path: 'login',
        query: { redirect: router.currentRoute.fullPath } // 将跳转的路由path作为参数，登录成功后跳转到该路由
      });
    }

    return response;
  },
  error => {
    //默认除了2XX之外的都是错误的，就会走这里
    if (error.response) {
      switch (error.response.status) {
        case 401:
          store.dispatch('UserLogout'); //可能是token过期，清除它
          router.replace({
            //跳转到登录页面
            path: 'login',
            query: { redirect: router.currentRoute.fullPath } // 将跳转的路由path作为参数，登录成功后跳转到该路由
          });
      }
    }
    return Promise.reject(error.response);
  }
);

export default {
  getBaseUrl() {
    return _baseUrl;
  },
  //用户登录
  userLogin(data) {
    return instance.post(_baseUrl + 'login', data);
  },
  //退出登录
  userLogOut() {
    return instance.get(_baseUrl + 'logout'); //线上没有问题  本地404
  },
  //获取表格数据
  getTableData(data) {
    return instance.post(_baseUrl + 'api/perp/getPerpByIds', data);
  },
  getExtendedRelations() {
    return instance.get('/static/extendedRelations.json');
  },
  //获取筛选条件目录
  getFilterItems() {
    //return instance.get('/static/filters.json');
    return instance.get(_baseUrl + 'api/schemas');
  },
  //获取筛选条件详情
  getFilterItemsDetail(params) {
    //return instance.get('/static/filters.json');
    return instance.get(_baseUrl + 'api/system/dict', params);
    //return instance.get(_baseUrl + 'api/system/multiDict', params);
  },
  //获取字典
  getDictsDetail(params) {
    //return instance.get('/static/filters.json');
    return instance.get(_baseUrl + 'api/system/dict', params);
  },
  //保存筛选方案
  addFilteringScheme(data) {
    return instance.post(_baseUrl + 'api/filter/add', data);
  },
  //更新筛选方案
  updateFilteringScheme(data) {
    return instance.post(_baseUrl + 'api/filter/update', data);
  },
  //获取筛选方案
  getFilteringScheme() {
    return instance.get(_baseUrl + 'api/filter/query');
  },
  //获取常用标签
  getCommonTags() {
    return instance.get(_baseUrl + 'api/docs/getLabels');
  },
  //获取首页关系图数据
  getHomeDiagramData(params) {
    // return instance.get('/static/graynetData.json', data);//测试数据
    return instance.get(_baseUrl + 'api/graphInfo', { params });
  },
  //根据人员的身份证号和关系查询相同关系的点
  searchSameRelationshipData(params) {
    return instance.get(_baseUrl + 'api/singleExtension', params);
  },
  //点击搜索按钮通过身份证号搜索(搜索或查找的人员不在数据库时提醒添加人员)
  searchThroughTheIdNumber(params) {
    return instance.get(_baseUrl + 'api/multiQuery', params );
  },
  //根据人员的身份证号查询相关统计数据信息
  searchStatisticalData(data) {
    return instance.post(_baseUrl + 'api/perp/statistic/allByIds', data);
  },
  //点击保存报告
  clickSaveReport(data) {
    return instance.post(_baseUrl + 'api/docs/saveDoc', data);
  },
  //点击保存图片
  clickSavePic(data) {
    return instance.post(_baseUrl + 'api/savePic', data);
  },
  //获取个人信息
  getPersonalInformation(params) {
    return instance.get(_baseUrl + 'api/nodeInfo', { params });
  },
  //获取关系信息
  getRelationInformation(params) {
    return instance.get(_baseUrl + 'api/edgeInfo', { params });
  },
  /*--------------------------------报告页面--------------------------------------*/
  //获取报告详情
  getObtainReportDetails(params) {
    return instance.get(_baseUrl + 'api/docs/getDocById', params);
  },
  //调用请求所有报告列表数据
  getAllReportLists() {
    return instance.get(_baseUrl + 'api/docs/getAllDoc');
  },
  //调用请求部门报告列表数据
  getDepartmentReportLists() {
    return instance.get(_baseUrl + 'api/docs/getDocByDept');
  },
  //收藏报告
  collectReport(data) {
    return instance.post(_baseUrl + 'api/docs/addCollect', data);
  },
  //取消收藏报告
  unfavoriteReport(params) {
    return instance.get(_baseUrl + 'api/docs/unfavorite', params);
  },
  //查找需要导入的报告
  searchImportReport(params) {
    return instance.get(_baseUrl + 'api/docs/getDocs', params);
  },
  //导入报告功能
  importReportingSearch() {
    //return instance.get(_baseUrl+'api/docs/unfavorite');
  },
  //添加评论
  addComment(data) {
    return instance.post(_baseUrl + 'api/docs/addComment', data);
  },
  //获取评论树
  getCommentTree(params) {
    return instance.get(_baseUrl + 'api/docs/commentTree', params);
  },
  /*----------------------------------------我的相关页面--------------------------------------------*/
  //请求系统消息
  requestSystemMessage() {
    //return instance.get(_baseUrl+'api/docs/addComment');
  },
  //请求回复我的
  requestReplyMessage() {
    return instance.get(_baseUrl + 'api/docs/getComment');
  },
  //获取我的关系图列表
  getListDiagrams() {
    // return instance.get('/static/pic.json');
    return instance.get(_baseUrl + 'api/docs/pics');
  },
  //获取我的收藏列表
  getListCollects() {
    return instance.get(_baseUrl + 'api/docs/getCollectInfo');
  },
  //调用请求我的报告列表数据
  getMyReportLists() {
    return instance.get(_baseUrl + 'api/docs/getDocByUser');
  },
  //获取个人信息
  getMyPersonMes() {
    return instance.get(_baseUrl + 'api/system/getUserInfo');
  },
  //分享报告
  sharingReport(params) {
    return instance.get(_baseUrl + 'api/docs/shareDoc', params);
  }
};
