<template>
    <section class="chart-container">
        <el-row>
            <el-col :span="12">
                <div id="chartLine" style="width:100%; height:400px;"></div>
            </el-col>
            <el-col :span="12">
                <div id="chartPie" style="width:100%; height:400px;"></div>
            </el-col>
            <el-col :span="24">
                <a href="http://echarts.baidu.com/examples.html" target="_blank" style="float: right;">more>></a>
            </el-col>
        </el-row>
    </section>
</template>

<script>
    import echarts from 'echarts'
    export default {
        data() {
            return {
                chartLine: null,
                url: 'http://127.0.0.1:8081/admin/countByWeek',
                data: [],
                xAxisData: [],
                seriesData: [],
            }
        },
        methods: {
            getLineData(url){
            this.$http({
              url: url,
              method: 'post',
            }).then((res) => {
              console.log(res.data);
              this.data = res.data;
              var _this = this;
              this.data.forEach(function(item){
                    _this.xAxisData.push(item.name);
                    _this.seriesData.push(item.value);
                });
              this.drawCharts();
            })
          },
            drawLineChart() {
                this.chartLine = echarts.init(document.getElementById('chartLine'));
                this.chartLine.setOption({
                    title: {
                        text: '每周统计情况',
                        x: 'center'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        //data: ['邮件营销', '联盟广告', '搜索引擎']
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        data: this.xAxisData,
                    },
                    yAxis: {
                        type: 'value',
                    },
                    series: [
                        {
                            name: '预约次数',
                            type: 'line',
                            stack: '总量',
                            data: this.seriesData
                        }
                    ]
                });
            },
            
            drawCharts() {
                this.drawLineChart()
            },
        },
        mounted: function () {
            this.getLineData(this.url);
        },
        updated: function () {
            this.drawCharts()
        }
    }
</script>

<style scoped>
    .chart-container {
        width: 100%;
        float: left;
    }
    /*.chart div {
        height: 400px;
        float: left;
    }*/
    .el-col {
        padding: 30px 20px;
    }
</style>