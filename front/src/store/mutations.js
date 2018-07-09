import * as types from './types.js';
//关于token的存放位置
//1.只用vuex存储：刷新页面vuex重新初始化，token消失导致需要重新登录
//2.window.sessionStorage: 初始化的时候用sessionStorage来赋值，刷新页面重新初始化，但因为sessionStorage里面存有token有值，
//那么state.token就有值了,vue-router就不会拦截，则无需重新登录。（这个demo用的是这种）
//3.window.localStorage: 初始化的时候用localStorage来赋值，这种情况跟sessionStorage差不多。后台配合给长时间有效的token比较好,
//如果token的有效期比较短，直接有sessionStorage比较好。
const mutations = {
  [types.LOGIN]: (state, data) => {
    //更改token的值
    state.token = data;
    window.sessionStorage.setItem('token', data);
  },
  [types.LOGOUT]: state => {
    //登出的时候要清除token
    state.token = null;
    window.sessionStorage.removeItem('token');
  },
  [types.GETUSERINFO]: (state, data) => {
    //获取用户信息
    state.userInfo = data;
  },
  [types.SETGRAPHDATA]: (state, element) => {
    state.graphData = element;
  },
  [types.SELECTEDELEMENTS]: (state, element) => {
    state[`selected${element.group}`].push(element);
  },
  [types.UNSELECTELEMENTS]: (state, { type, id: elementId }) => {
    let index = state[`selected${type}`].findIndex(
      ({ data: { id } }) => id === elementId
    );
    state[`selected${type}`].splice(index, 1);
  },
  [types.CLEARELEMENTS]: state => {
    state.selectednodes = [];
    state.selectededges = [];
  },
  [types.ADDREMARK]: (state, data) => {
    state.remarks.push(data);
  },
  [types.EDITREMARK]: (state, { data, index }) => {
    state.remarks.splice(index, 1, data);
  },
  [types.DELETEREMARK]: (state, index) => {
    state.remarks.splice(index, 1);
  },
  [types.SETSCHEMA]: (state, data) => {
    //获取过滤
    state.schema = data;
  },
	[types.SETAllDICTLIST]: (state, data) => {
    //获取过滤
    state.dicts = data;
  },
  config(state, config) {
    state.skin = config.skin;
  }
};

export default mutations;
