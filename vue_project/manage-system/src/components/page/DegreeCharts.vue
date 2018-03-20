<template>
    <section class="chart-container">
        <el-row>
            <el-col :span="12">
                <div id="chartColumn" style="width:100%; height:400px;"></div>
            </el-col>
        </el-row>
    </section>
</template>

<script>
    import echarts from 'echarts'
    export default {
        data() {
            return {
                chartColumn: null,
                url: 'http://127.0.0.1:8081/admin/countDegree',
                xData: [],
                yData: [],
                data: [],
            }
        },
        methods: {
            getColumnData(url){
                this.$http({
                url: url,
                method: 'post',
                }).then((res) => {
                console.log(res.data);
                    this.data = res.data;
                    var _this = this;
                    this.data.forEach(function(item){
                            _this.xData.push(item.name);
                            _this.yData.push(item.value);
                        });
                    this.drawColumnChart();
                })
            },
            drawColumnChart() {
                this.chartColumn = echarts.init(document.getElementById('chartColumn'));
                this.chartColumn.setOption({
                  title: {
                        text: '学位人数统计情况',
                        subtext: '',
                        x: 'center'
                  },
                  tooltip: {},
                  xAxis: {
                      data: this.xData
                  },
                  yAxis: {},
                  series: [{
                      name: '人数',
                      type: 'bar',
                      data: this.yData
                    }]
                });
            },
        },
        mounted: function () {
            this.getColumnData(this.url);
        },
        updated: function () {
            this.drawColumnChart()
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
        width: 50%;
        padding: 30px 20px;
    }
</style>