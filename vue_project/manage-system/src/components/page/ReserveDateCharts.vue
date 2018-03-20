<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-date"></i> 图表</el-breadcrumb-item>
                <el-breadcrumb-item>基础图表</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="plugins-tips">
            vue-schart：vue.js封装sChart.js的图表组件。
            访问地址：<a href="https://github.com/lin-xin/vue-schart" target="_blank">vue-schart</a>
        </div>
        <div class="schart">
            <div class="content-title">柱状图</div>
            <schart canvasId="bar" width="500" height="400" :data="data2" type="bar" :options="options1"></schart>
        </div>
        <div class="schart">
        <div class="content-title">饼状图</div>
        <schart canvasId="pie" width="500" height="400" :data="data1" type="pie" :options="options1"></schart>
        </div>
    </div>
</template>

<script>
    import Schart from 'vue-schart';
    export default {

        components: {
            Schart
        },
        methods: {
          getData(url){
            this.$http({
              url: url,
              method: 'post',
            }).then((res) => {
              console.log(res.data);
              this.data1 = res.data;
              
            })
          },
        },
        mounted() {
          this.getData(this.data1Url);
          this.$http({
              url: this.data2Url,
              method: 'post',
            }).then((res) => {
              console.log(res.data);
              this.data2 = res.data;
              
            })
          //alert(this.data1.length);
          //this.data2 = this.getData(this.data2Url);
        },
        data: () => ({
            data1: [],
            data2: [],
            data1Url: 'http://127.0.0.1:8081/admin/countCollege',
            data2Url: 'http://127.0.0.1:8081/admin/countDegree',
            options1: {
                title: '座位预约情况',
                bgColor: '#009688',
                titleColor: '#ffffff',
                fillColor: '#e0f2f1',
                axisColor: '#ffffff',
                contentColor: '#999'
            },
        })
    }
</script>

<style scoped>
    .schart{
        width: 600px;
        display: inline-block;
    }
    .content-title{
        clear: both;
        font-weight: 400;
        line-height: 50px;
        margin: 10px 0;
        font-size: 22px;
        color: #1f2f3d;
    }
    
</style>