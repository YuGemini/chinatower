import Vue from 'vue';
import Router from 'vue-router';
import store from '../store/index.js'
import Login from '@/components/common/Login'
import Home from '@/components/common/Home';
import DashBoard from '@/components/page/DashBoard';
import AmCharts from '@/components/page/BasicCharts';
import FormInput from '@/components/page/FormInput';
import FormLayouts from '@/components/page/FormLayouts';
import BasicTables from '@/components/page/BasicTables';
import EditorPage from '@/components/page/EditorPage';
import MarkdownPage from '@/components/page/MarkdownPage';
import TodoList from '@/components/page/TodoListPage';

Vue.use(Router)

const router = new Router({
  mode: 'history',
  base: __dirname,
  routes: [
    {
      path: '/',
      component: Login,
      name: 'login'
    },
    {
      path: '/',
      component: Home,
      meta: {
        requiresAuth: true
      },
      redirect: '/workDest',
      children: [
        {
          path: '',
          component: DashBoard,
          meta: {
            requiresAuth: true
          }
        }, {
          path: '/DashBoard',
          component: DashBoard,
          meta: {
            requiresAuth: true
          }
        }, {
          path: '/EditorPage',
          component: EditorPage
        }, {
          path: '/MarkdownPage',
          component: MarkdownPage
        }, {
          path: '/BasicCharts',
          component: AmCharts
        }, {
          path: '/FormInput',
          component: FormInput
        }, {
          path: '/FormLayouts',
          component: FormLayouts
        }, {
          path: '/BasicTables',
          component: BasicTables
        }, {
          path: '/TodoList',
          component: TodoList
        }


      ]
    }
  ]
})
router.beforeEach((to, from, next) => {
  //获取store里面的token
  let token = store.state.token;
  //判断要去的路由有没有requiresAuth
  if (to.meta.requiresAuth) {
    if (token) {
      next();
    } else {
      next({
        path: '/login',
        query: {
          redirect: to.fullPath
        } // 将刚刚要去的路由path（却无权限）作为参数，方便登录成功后直接跳转到该路由
      });
    }

  } else {
    next(); //如果无需token,那么随它去吧
  }
});
router.afterEach(route => {
  //iView.LoadingBar.finish();
});
export default router;
