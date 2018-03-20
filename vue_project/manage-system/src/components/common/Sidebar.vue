<template>
    <div class="sidebar">
        <el-menu :default-active="onRoutes" class="el-menu-vertical-demo" theme="dark" unique-opened router>
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-submenu :index="item.index" :key="item.index">
                        <template slot="title"><i :class="item.icon"></i>{{ item.title }}</template>
                        <el-menu-item v-for="(subItem,i) in item.subs" :key="i" :index="subItem.index">{{ subItem.title }}
                        </el-menu-item>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i>{{ item.title }}
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                items: [
                    {
                        icon: 'el-icon-setting',
                        index: 'readme',
                        title: '系统简介'
                    },
                    {
                        icon: 'el-icon-menu',
                        index: '2',
                        title: '数据查询',
                        subs: [
                            {
                                index: 'reserveRecord',
                                title: '预约记录'
                            },
                            {
                                index: 'signInRecord',
                                title: '签到记录'
                            },
                            {
                                index: 'signOutRecord',
                                title: '签离记录'
                            },
                            {
                                index: 'inobservanceRecord',
                                title: '违规记录'
                            },
                            {
                                index: 'blacklist',
                                title: '黑名单'
                            }
                        ]
                    },
                    {
                        icon: 'el-icon-date',
                        index: 'readingRoomInfo',
                        title: '阅览室管理',
                    },
                    {
                        icon: 'el-icon-date',
                        index: '3',
                        title: '座位管理',
                        subs: [
                            {
                                index: 'showSeat',
                                title: '座位使用情况'
                            },
                            {
                                index: 'seatInfo',
                                title: '座位信息'
                            },
                            /*
                            {
                                index: 'baseform',
                                title: '基本表单'
                            },
                            {
                                index: 'vueeditor',
                                title: '编辑器'
                            },
                            {
                                index: 'markdown',
                                title: 'markdown'
                            },
                            {
                                index: 'upload',
                                title: '文件上传'
                            }
                            */
                        ]
                    },
                    {
                        icon: 'el-icon-star-on',
                        index: 'myEcharts',
                        title: '数据统计',
                        subs: [
                            {
                                index: 'collegeCharts',
                                title: '按学院统计'
                            },
                            {
                                index: 'degreeCharts',
                                title: '按学位统计'
                            },
                            {
                                index: 'readingRoomCharts',
                                title: '按阅览室统计'
                            },
                            {
                                index: 'weekCharts',
                                title: '按周统计'
                            },
                            {
                                index: 'timeCharts',
                                title: '按时间段统计'
                            }
                        ]
                    }
                ]
            }
        },
        computed:{
            onRoutes(){
                return this.$route.path.replace('/','');
            }
        }
    }
</script>

<style scoped>
    .sidebar{
        display: block;
        position: absolute;
        width: 200px;
        left: 0;
        top: 70px;
        bottom:0;
        background: #2E363F;
    }
    .sidebar > ul {
        height:100%;
    }
</style>
