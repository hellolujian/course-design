import Vue from 'vue'
import Router from 'vue-router'
import Main from '@/components/Main'
import Login from '@/components/Login'
import Reserve from '@/components/Reserve'
import SignIn from '@/components/SignIn'
import SignOut from '@/components/SignOut'
import TemporarySignOut from '@/components/TemporarySignOut'
import ReserveRecord from '@/components/ReserveRecord'
import SignInRecord from '@/components/SignInRecord'
import SignOutRecord from '@/components/SignOutRecord'
import InobservanceRecord from '@/components/InobservanceRecord'
import AdminLogin from '@/components/AdminLogin'
import Admin from '@/components/Admin'

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/main',
      name: 'Main',
      component: Main
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/reserve',
      name: 'Reserve',
      component: Reserve
    },
    {
      path: '/signIn',
      name: 'SignIn',
      component: SignIn
    },
    {
      path: '/signOut',
      name: 'SignOut',
      component: SignOut
    },
    {
      path: '/signOut/Temporary',
      name: 'TemporaySignOut',
      component: TemporarySignOut
    },
    {
      path: '/reserveRecord',
      name: 'ReserveRecord',
      component: ReserveRecord
    },
    {
      path: '/signInRecord',
      name: 'SignInRecord',
      component: SignInRecord
    },
    {
      path: '/signOutRecord',
      name: 'SignOutRecord',
      component: SignOutRecord
    },
    {
      path: '/inobservanceRecord',
      name: 'InobservanceRecord',
      component: InobservanceRecord
    },
    {
      path: '/admin',
      name: 'Admin',
      component: Admin
    },
    {
      path: '/adminLogin',
      name: 'AdminLogin',
      component: AdminLogin
    }
  ]
})
