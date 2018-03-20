

<template>
    <section class="chart-container">
        <el-row>
            
            <el-col :span="12">
                <div id="chartPie" style="width:100%; height:400px;"></div>
            </el-col>
        </el-row>
    </section>
</template>

<script>
    import echarts from 'echarts'
    export default {
        data() {
            return {
                chartPie: null,
                url: 'http://127.0.0.1:8081/admin/countCollege',
                data: [],
                legend: []
            }
        },
        methods: {
          getPieData(url){
            this.$http({
              url: url,
              method: 'post',
            }).then((res) => {
              console.log(res.data);
              this.data = res.data;
              var _this = this;
              this.data.forEach(function(item){
                    _this.legend.push(item.name);
                });
              this.drawCharts();
            })
          },
            
            drawPieChart() {
                this.chartPie = echarts.init(document.getElementById('chartPie'));
                this.chartPie.setOption({
                    title: {
                        text: '学院人数统计情况',
                        subtext: '',
                        x: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: this.legend
                    },
                    series: [
                        {
                            name: '预约情况',
                            type: 'pie',
                            radius: '55%',
                            center: ['50%', '60%'],
                            data: this.data,
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                });
            },
            drawCharts() {
                this.drawPieChart()
            },
        },
        mounted: function () {
            this.getPieData(this.url);
            
        },
        updated: function () {
            this.drawCharts()
        }
    }
</script>

<style scoped>
    .chart-container {
        width: 100%;
        float: center;
    }
    /*.chart div {
        height: 400px;
        float: left;
    }*/
    .el-col {
        width: 100%;
        padding: 30px 20px;
    }
</style>