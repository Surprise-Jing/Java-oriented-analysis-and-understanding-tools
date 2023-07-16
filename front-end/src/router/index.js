import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessedlogin
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/registerpage',
  
    component: () => import('@/views/register/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '主页', icon: 'dashboard' }
    }]
  },


  {
    path: '/upload',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Upload',
        component: () => import('@/views/upload/upload'),
        meta: { title: '源码上传', icon: 'form' }
      }
    ]
  },

  {
    path: '/analyse',
    component: Layout,
    redirect: '/analyse/ast',
    name: 'Analyse',
    meta: { title: '代码分析', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'Ast',
        name: 'Ast',
        component: () => import('@/views/analyse/ast'),
        meta: { title: 'AST', icon: 'tree' }
      },
      {
        path: 'Cfg',
        name: 'Cfg',
        component: () => import('@/views/analyse/cfg'),
        meta: { title: 'CFG', icon: 'tree' }
      },
      {
        path: 'Cg',
        name: 'Cg',
        component: () => import('@/views/analyse/cg'),
        meta: { title: 'CG', icon: 'tree' }
      },
      {
        path: 'Pdg',
        name: 'Pdg',
        component: () => import('@/views/analyse/pdg'),
        meta: { title: 'PDG', icon: 'tree' }
      },
      // {
      //   path: '/program_slice',
      //   component: () => import('@/views/analyse/slice'),
      //   name: 'program_slice',
      //   meta: { title: '程序切片', icon: 'tree' },
      //   children:[
      //     {
      //       path: 'static_slicing',
      //       name: 'Static_slicing',
      //       component: () => import('@/views/analyse/static_slicing'),
      //       meta: { title: '静态切片', icon: 'table' }
      //     },
      //     {
      //       path: 'dynamic_slicing',
      //       name: 'Dynamic_slicing',
      //       component: () => import('@/views/analyse/dynamic_slicing'),
      //       meta: { title: '动态切片', icon: 'table' }
      //     }
      //   ]

      // }
    ]
  },
  //todo
  {
    path: '/program_slice',
    component:Layout,
    name: 'program_slice',
    meta: { title: '程序切片', icon: 'tree' },
    children:[
      {
        path: 'static_slicing',
        name: 'Static_slicing',
        component: () => import('@/views/analyse/static_slicing'),
        meta: { title: '静态切片', icon: 'table' }
      },
      {
        path: 'dynamic_slicing',
        name: 'Dynamic_slicing',
        component: () => import('@/views/analyse/dynamic_slicing'),
        meta: { title: '动态切片', icon: 'table' }
      }
    ]

  },
  //endtodo
  {
    path: '/codemessage',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Code_message',
        component: () => import('@/views/code_message/code_message'),
        meta: { title: '代码度量', icon: 'nested' }
      }
    ]
  },
  {
    path: '/report',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Create_report',
        component: () => import('@/views/create_report/create_report'),
        meta: { title: '报告生成', icon: 'link' }
      }
    ]
  },
  
 

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
