<template>
<div>
    <section class="chart-container">
        <el-row>
            <el-col :span="12">
                <div id="chartLine" style="width:100%; height:400px;"></div>
            </el-col>
        </el-row>
    </section>
    <div>
        <DatePicker ref="getDate" v-on:changeDate="showChangeDate"></DatePicker>
    </div>
</div>
</template>

<script>
    import echarts from 'echarts';
    import DatePicker from './DatePicker';
    
    export default {
        components: {
            DatePicker,
        },
        data() {
            return {
                chartLine: null,
                url: 'http://127.0.0.1:8081/admin/countByTime',
                data: [],
                xAxisData: [],
                seriesData: [],
                morningTop: '',//早高峰
                morningTop2: '',
                morningTopStartIndex: 0,
                morningTopEndIndex: 0,
                afternoonTop: '',//午高峰
                afternoonTop2: '',
                afternoonTopStartIndex: 0,
                afternoonTopEndIndex: 0,
                nightTop: '',//晚高峰
                nightTop2: '',
                nightTopStartIndex: 0,
                nightTopEndIndex: 0,
                reserveTime: ''
            }
        },
        watch: {
            reserveTime:{
                handler:function(val,oldval){
                    //alert(val);
                }
            }
        },
        methods: {
            formatDate(date) {
                if(date instanceof Date)
                {
                    var y = date.getFullYear();  
                        var m = date.getMonth() + 1;  
                        m = m < 10 ? '0' + m : m;  
                        var d = date.getDate();  
                        d = d < 10 ? ('0' + d) : d;  
                        return y + '-' + m + '-' + d;
                }
                return date;
            },
            clearLineCharts() {

            },
            showChangeDate:function(data) {
                //alert('before:'+this.reserveTime);
                this.reserveTime = this.formatDate(this.$refs.getDate.date);
                //alert('after'+this.reserveTime);
                //alert('showChangeDate:'+this.formatDate(this.$refs.getDate.date));
                this.getLineData(this.url);
            },
            getLineData(url){
                this.data = [];
                this.xAxisData=[];
                this.seriesData=[];
                //alert('开始获取数据'+this.xAxisData.length);
                //alert(this.reserveTime);
            this.$http({
              url: url,
              method: 'post',
              params: {
                  reserveTime: this.reserveTime
              }
            }).then((res) => {
                //需先清空原数据
                this.data = [];
                this.xAxisData=[];
                this.seriesData=[];
              console.log(res.data);
              this.data = res.data;
              var _this = this;
              var morningMax = -1;
              var afternoonMax = -1;
              var nightMax = -1;
              for(var index = 0; index<this.data.length; index++){
                  var item = this.data[index];
                  if(index <= 3) {
                      if(item.value > morningMax) {
                          morningMax = item.value;
                          //alert('morningmax:'+morningMax);
                          this.morningTop = item.name;
                          this.morningTop2 = this.data[index+1].name;
                          this.morningTopStartIndex = index;
                          this.morningTopEndIndex = index + 1;
                          //alert(this.morningTopStartIndex+"-"+this.morningTopEndIndex);
                      }
                  }
                  else if(index > 3 && index <= 9) {
                      if(item.value > afternoonMax) {
                          afternoonMax = item.value;
                          //alert('afternoonMax:'+afternoonMax);
                          this.afternoonTop = item.name;
                          this.afternoonTop2 = this.data[index+1].name;
                          this.afternoonTopStartIndex = index;
                          this.afternoonTopEndIndex = index + 1;
                          //alert(this.afternoonTopStartIndex+"-"+this.afternoonTopEndIndex);
                      }
                  }
                  else {
                      if(item.value > nightMax) {
                          nightMax = item.value;
                          //alert('nightMax'+nightMax);
                          this.nightTop = item.name;
                          this.nightTop2 = this.data[index+1].name;
                          this.nightTopStartIndex = index;
                          this.nightTopEndIndex = index + 1;
                          //alert(this.nightTopStartIndex+"-"+this.nightTopEndIndex);
                      }
                  }
                  
                    this.xAxisData.push(this.data[index].name);
                    this.seriesData.push(this.data[index].value);
              }
              console.log('新数据'+this.xAxisData);
              this.drawCharts();
            })
          },
            drawLineChart() {
                console.log('开始画图'+this.xAxisData);
                console.log('开始画图'+this.seriesData);
                this.chartLine = echarts.init(document.getElementById('chartLine'));
                this.chartLine.setOption({
                    title: {
                        text: '一天预约情况',
                        x: 'center',
                        subtext: '江苏大学图书馆'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'cross'
                        }
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            saveAsImage: {}
                        }
                    },
                    xAxis:  {
                        type: 'category',
                        boundaryGap: false,
                        data: this.xAxisData,
                    },
                    yAxis: {
                        type: 'value',
                        axisLabel: {
                            formatter: '{value} 次'
                        },
                        axisPointer: {
                            snap: true
                        }
                    },
                    visualMap: {
                        show: false,
                        dimension: 0,
                        pieces: [{
                            lte: this.morningTopStartIndex,
                            color: 'green'
                        }, {
                            gt: this.morningTopStartIndex,
                            lte: this.morningTopEndIndex,
                            color: 'red'
                        }, {
                            gt: this.morningTopEndIndex,
                            lte: this.afternoonTopStartIndex,
                            color: 'green'
                        }, {
                            gt: this.afternoonTopStartIndex,
                            lte: this.afternoonTopEndIndex,
                            color: 'red'
                        }, {
                            gt: this.afternoonTopEndIndex,
                            lte: this.nightTopStartIndex,
                            color: 'green'
                        }, {
                            gt: this.nightTopStartIndex,
                            lte: this.nightTopEndIndex,
                            color: 'red'
                        }, {
                            gt: this.nightTopEndIndex,
                            color: 'green'
                        }]
                    },
                    series: [
                        {
                            name:'预约人数',
                            type:'line',
                            smooth: true,
                            data: this.seriesData,
                            markArea: {
                                data: [ 
                                    [{
                                    name: '早高峰',
                                    xAxis: this.morningTop,
                                }, {
                                    xAxis: this.morningTop2,
                                }], [{
                                    name: '午高峰',
                                    xAxis: this.afternoonTop
                                }, {
                                    xAxis: this.afternoonTop2
                                }],
                                [{
                                    name: '晚高峰',
                                    xAxis: this.nightTop
                                }, {
                                    xAxis: this.nightTop2
                                }] ]
                            }
                        }
                    ]
                });
            },
            
            drawCharts() {
                this.drawLineChart()
            },
        },
        mounted: function () {
            this.reserveTime = this.$refs.getDate.date;
            this.getLineData(this.url);
        },
        updated: function () {
            //this.drawCharts()
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