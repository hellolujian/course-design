import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/login'
        },
        {
            path: '/readme',
            component: resolve => require(['../components/common/Home.vue'], resolve),
            children:[
                {
                    path: '/',
                    component: resolve => require(['../components/page/Readme.vue'], resolve)
                },
                {
                    path: '/basetable',
                    component: resolve => require(['../components/page/BaseTable.vue'], resolve)
                },
                {
                    path: '/vuetable',
                    component: resolve => require(['../components/page/VueTable.vue'], resolve)     // vue-datasource组件
                },
                {
                    path: '/baseform',
                    component: resolve => require(['../components/page/BaseForm.vue'], resolve)
                },
                {
                    path: '/vueeditor',
                    component: resolve => require(['../components/page/VueEditor.vue'], resolve)    // Vue-Quill-Editor组件
                },
                {
                    path: '/markdown',
                    component: resolve => require(['../components/page/Markdown.vue'], resolve)     // Vue-Quill-Editor组件
                },
                {
                    path: '/upload',
                    component: resolve => require(['../components/page/Upload.vue'], resolve)       // Vue-Core-Image-Upload组件
                },
                {
                    path: '/basecharts',
                    component: resolve => require(['../components/page/BaseCharts.vue'], resolve)   // vue-schart组件
                },
                {
                    path: '/reserveDateCharts',
                    component: resolve => require(['../components/page/ReserveDateCharts'], resolve)//
                },
                {
                    path: '/drag',
                    component: resolve => require(['../components/page/DragList.vue'], resolve)    // 拖拽列表组件
                },
                {
                    path: '/seat',
                    component: resolve => require(['../components/page/Seat.vue'], resolve)        // 座位组件
                },
                {
                    path: '/myEcharts',
                    component: resolve => require(['../components/page/MyEcharts.vue'], resolve)        // 座位组件
                },
                {
                    path: '/collegeCharts',
                    component: resolve => require(['../components/page/CollegeCharts.vue'], resolve)        // 座位组件
                },
                {
                    path: '/degreeCharts',
                    component: resolve => require(['../components/page/DegreeCharts.vue'], resolve)        // 座位组件
                },
                {
                    path: '/readingRoomCharts',
                    component: resolve => require(['../components/page/ReadingRoomCharts.vue'], resolve)        // 座位组件
                },
                {
                    path: '/timeCharts',
                    component: resolve => require(['../components/page/TimeCharts.vue'], resolve)        // 座位组件
                },
                {
                    path: '/weekCharts',
                    component: resolve => require(['../components/page/WeekCharts.vue'], resolve)        // 座位组件
                },
                {
                    path: '/reserveRecord',
                    component: resolve => require(['../components/page/ReserveRecord.vue'], resolve)        // 座位组件
                },
                {
                    path: '/signInRecord',
                    component: resolve => require(['../components/page/SignInRecord.vue'], resolve)        // 座位组件
                },
                {
                    path: '/signOutRecord',
                    component: resolve => require(['../components/page/SignOutRecord.vue'], resolve)        // 座位组件
                },
                {
                    path: '/blacklist',
                    component: resolve => require(['../components/page/Blacklist.vue'], resolve)        // 座位组件
                },
                {
                    path: '/inobservanceRecord',
                    component: resolve => require(['../components/page/InobservanceRecord.vue'], resolve)        // 座位组件
                },
                {
                    path: '/showSeat',
                    component: resolve => require(['../components/page/ShowSeat.vue'], resolve)        // 座位组件
                },
                {
                    path: '/seatInfo',
                    component: resolve => require(['../components/page/SeatInfo.vue'], resolve)        // 座位组件
                },
                {
                    path: '/readingRoomInfo',
                    component: resolve => require(['../components/page/ReadingRoomInfo.vue'], resolve)        // 座位组件
                },
                {
                    path: '/test',
                    component: resolve => require(['../components/page/test.vue'], resolve)        // 座位组件
                }
            ]
        },
        {
            path: '/login',
            component: resolve => require(['../components/page/Login.vue'], resolve)
        },
    ]
})
