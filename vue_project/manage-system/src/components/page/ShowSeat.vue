<template>
	<div id="app1">
        <!--
		<el-collapse v-model="activeName" accordion @change="getSeat">
		  <el-collapse-item 
            :title="item.name+':'+item.usableCount+'/'+item.count+'(可用座位数/座位总数)'" 
            :name="index" 
            :key="index" 
            v-for="(item,index) in data">
		    <div v-for="(subItem,subIndex) in item.readingRooms" :key="subIndex">
		    	{{subItem.name}}:{{subItem.usableCount}}/{{subItem.count}}(可用座位数/座位总数)
		    	<el-button 
                    :disabled="Boolean(subSubItem.seatStatus)" 
                    @click="selectSeat(subSubItem)" 
                    type="primary" 
                    plain 
                    v-for="(subSubItem,subSubIndex) in subItem.seats" 
                    :key="subSubIndex">
		    		{{subSubItem.seatId}}
		    	</el-button>
		    </div>

		  </el-collapse-item>
		</el-collapse>
        -->

        <el-dialog title="座位分布情况" :visible.sync="dialogSeatVisible" fullscreen="true" width="80%" center top="10vh">
            <el-button-group>
                <el-button 
                    :type="(item.seatStatus == 0)?'success':'danger'"
                    icon="el-icon-arrow-left" 
                    style="margin:2px"
                    v-for="(item,index) in seatDialogData"
                    :key="index"
                    >{{item.seatId}}
                </el-button>
            </el-button-group>
        </el-dialog>
        
        <el-card class="box-card"
            v-for="(item,index) in data"
            :key="index">
            
            <div slot="header" class="clearfix">
                <el-dialog
                    :title="cardTitle"
                    :visible.sync="dialogVisible"
                    width="30%"
                    :before-close="handleClose">
                    <p>座位总数：{{count}}</p>
                    <p>可用座位数：{{usableCount}}</p>
                    <p>座位使用率：{{percent}}%</p>
                    <span slot="footer" class="dialog-footer">
                        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
                    </span>
                </el-dialog>
                <span>{{item.name}}</span>
                <el-button style="float: right; padding: 3px 0" type="text" @click="cardClick(item.name,item.count,item.usableCount)">查看详情</el-button>
            </div>
            <Circle
                class="circle"
                v-for="(subItem,subIndex) in item.readingRooms" :key="subIndex"
                :size="200"
                :trail-width="4"
                :stroke-width="5"
                :percent="(subItem.count-subItem.usableCount)/subItem.count*100"
                stroke-linecap="square"
                stroke-color="red">
                <div class="demo-Circle-custom" @click="circleClick(subItem.seats)">
                    <h2>{{subItem.name}}</h2>
                    <p>{{subItem.usableCount}}/{{subItem.count}}(可用座位数/总数)</p>
                    <span>
                        座位使用率:
                        <i>{{((subItem.count-subItem.usableCount)/subItem.count).toFixed(4)*100}}%</i>
                    </span>
                </div>
                
            </Circle>
        </el-card>


        
	</div>
</template>
<script>
import {circleClick2} from '../../js/myJS.js'
  export default {
    data() {
      return {
        apiUrl: 'http://127.0.0.1:8081/seat/query',
        data: [],
        reserveTime: '',
        dialogVisible: false,
        cardTitle: '',
        position: '',
        usableCount: '',
        count: '',
        percent: 0,


        dialogSeatVisible: false,
        seatDialogData: [],
      };
    },
    methods: { 
        circleClick:function(seatDialogData){  

            this.seatDialogData = seatDialogData;
            this.dialogSeatVisible = true;
            //alert(name);
            //circleClick2(name);
        },
        cardClick(title,count,usableCount) {
            this.dialogVisible = true;
            this.cardTitle = title;
            this.count = count;
            this.usableCount = usableCount;
            this.percent = ((this.count-this.usableCount)/this.count).toFixed(4)*100;
        },
        handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
      getSeat() {
        //alert('执行getSeat');
        //alert(this.reserveTime);
        this.$http({
          url: this.apiUrl,
          method: 'post',
          params: {
                    reserveTime: this.reserveTime
                  }
        }).then((res) => {
          this.data = res.data.data;
          console.log(res.data.data);
        })
      },
      callMethod(reserveTime) {
        
        this.reserveTime = reserveTime;
        //alert('callMethod'+this.reserveTime);
        this.getSeat();
        //alert('调用成功');
      },
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
    },
    
    mounted () {
      this.reserveTime = this.formatDate(new Date());
      //alert('seat:mounted:'+this.reserveTime)
      this.getSeat();
    }
  }
</script>
<style lang="less">

.text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 100%;
  }
  
  .circle {
     // border: 1px solid black;
      margin-right: 10px;
  }

  .circle:hover{
      cursor: pointer;
  }

    .demo-Circle-custom{
        & h2{
            color: #0b0c0c;
            font-weight: normal;
            //border: 1px solid black;
            padding-bottom: 5px;
            padding-top: 5px;
        }
        & p{
            color: #657180;
            font-size: 16px;
            margin: 5px 5px;
            padding: 4px 4px;
            //border: 1px solid black;
            font-weight: bolder;
        }
        & span{
            //border: 1px solid black;
            display: block;
            //padding-top: 15px;
            margin: 5px 5px;
            padding-bottom: 4px;
            padding-top: 4px;
            color: #657180;
            font-size: 16px;
            &:before{
                content: '';
                display: block;
                width: 50px;
                height: 1px;
                margin: 0 auto;
                background: #e0e3e6;
                position: relative;
                top: -15px;
            };
        }
        & span i{
           // border: 1px solid black;
            font-size: 16px;
            font-style: normal;
            color: #3f414d;
        }
    }
</style>