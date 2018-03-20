<template>
  <div class="hello">
    <h1>{{ this.$route.params.userId }}</h1>
    <!--
    <p>
      <router-link to="/reserve">预约</router-link>
      <router-link to="/login">登录</router-link>
      <router-link to="/signOut">签离</router-link>
      <router-link to="/signIn">签到</router-link>
      <router-link to="/signOut/temporay">临时签离</router-link>
    </p>
  -->
    
    <el-button type="primary" @click="fastReserve">快速预约</el-button>
    <el-button type="primary" @click="signIn">签到</el-button>
    <el-button type="primary" @click="temporarySignOut">临时签离</el-button>
    <el-button type="primary" @click="signOut">签离</el-button>
    <el-button type="primary" @click="getReserveRecord">预约记录</el-button>
    <el-button type="primary" @click="getSignInRecord">签到记录</el-button>
    <el-button type="primary" @click="getSignOutRecord">签离记录</el-button>
    <el-button type="primary" @click="getInobservanceRecord">违规记录</el-button>
    <DatePicker ref="getDate" v-on:changeDate="showChangeDate"></DatePicker>
    <!--
    <Seat ref="child"></Seat>
    -->

    <el-dialog 
      title="座位分布情况" 
      :visible.sync="dialogSeatVisible" 
      fullscreen
      width="80%" 
      center 
      top="10vh">        
            <el-button 
                :type="(item.seatStatus == 0)?'success':'danger'"
                style="margin:2px"
                v-for="(item,index) in seatDialogData"
                :key="index"
                :disabled=Boolean(item.seatStatus)
                @click="selectSeat(item)">{{item.seatId}}
            </el-button>
        
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
import DatePicker from './DatePicker'
import Seat from './Seat'
export default {
  name: 'Main',
  components:{
    DatePicker,
    Seat
  },
  data () {
    return {
      msg: '',
      userId: '',
      reserveTime: '',
      apiUrl: 'http://127.0.0.1:8081/seat/query',
      reserveUrl: 'http://127.0.0.1:8081/reserve',
        data: [],
     //   reserveTime: '',
        dialogVisible: false,
        cardTitle: '',
        position: '',
        usableCount: '',
        count: '',
        percent: 0,
        dialogSeatVisible: false,
        seatDialogData: [],
    }
  },
  mounted () {
    this.userId = this.$route.query.userId;
    this.reserveTime = this.$refs.getDate.date;
    //this.$refs.child.reserveTime = this.reserveTime;
    this.getSeat();
    //alert(this.reserveTime);
  },
  methods: {
    //点击按钮预约座位
    reserveSeat(seatId) {
      //alert(this.$route.query.userId),
      //alert(seatId);
      //alert(this.reserveTime)
        this.$http({
          url: this.reserveUrl,
          method: 'post',
          params: {
          			userId: this.$route.query.userId,
          			seatId: seatId,
          			reserveTime: this.reserveTime
                  }
        }).then((res) => {
          //this.data = res.data.data;
          //alert(res.data.msg);
          console.log(res.data.data);
          this.dialogSeatVisible = false;
          if(res.data.data != null) {
            //alert(res.data.msg);
            this.$message.success(res.data.msg);
            this.getSeat();           
            //window.location.reload();
          }
          else {
            this.$message.error(res.data.msg);
          }
        })
  	  },

    //点击座位按钮选择座位
    selectSeat: function(object) {
        //alert(this.$route.query.userId);
        //alert(this.reserveTime);
        //alert(object.seatId);
        
        this.reserveSeat(object.seatId);
        
      },

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

    showChangeDate:function(data) {
      this.reserveTime = this.$refs.getDate.date;
      //alert('showChangeDate:'+this.reserveTime);
      this.getSeat();
      //this.$refs.child.callMethod(this.reserveTime);
    },
    formatDate(date) {
        var y = date.getFullYear();  
        var m = date.getMonth() + 1;  
        m = m < 10 ? '0' + m : m;  
        var d = date.getDate();  
        d = d < 10 ? ('0' + d) : d;  
        return y + '-' + m + '-' + d; 
      },
    fastReserve() {
      var reserveTime = this.formatDate(new Date());
      //alert(reserveTime);
      this.$http({
        url: 'http://127.0.0.1:8081/reserve/fast',
        method: 'post',
        params: {
                    userId: this.userId,
                    reserveTime: reserveTime,
                }
      }).then((res) => {
        if(res.data.data != null) {
            //alert(res.data.msg);
            this.$message.success(res.data.msg);
            this.getSeat();           
            //window.location.reload();
          }
          else {
            this.$message.error(res.data.msg);
          }
      })
    },
    signOut() {
      //alert('signOut');
      this.$http({
        url: 'http://127.0.0.1:8081/sign_out/final',
        method: 'post',
        params: {
                    userId: this.userId,
                }
      }).then((res) => {
        if(res.data.data != null) {
            //alert(res.data.msg);
            this.$message.success(res.data.msg);
            this.getSeat();           
            //window.location.reload();
          }
          else {
            this.$message.error(res.data.msg);
          }
      })
    },
    signIn() {
      //alert('signIn');
      this.$http({
        url: 'http://127.0.0.1:8081/sign_in',
        method: 'post',
        params: {
                    userId: this.userId,
                }
      }).then((res) => {
        if(res.data.data != null) {
            //alert(res.data.msg);
            this.$message.success(res.data.msg);
            this.getSeat();           
            //window.location.reload();
          }
          else {
            this.$message.error(res.data.msg);
          }
      })
    },
    temporarySignOut() {
      //alert('temporarySignOut');
      this.$http({
        url: 'http://127.0.0.1:8081/sign_out/temporary',
        method: 'post',
        params: {
                    userId: this.userId
                }
      }).then((res) => {
        if(res.data.data != null) {
            //alert(res.data.msg);
            this.$message.success(res.data.msg);
            this.getSeat();           
            //window.location.reload();
          }
          else {
            this.$message.error(res.data.msg);
          }
      })
    },
    getReserveRecord() {
      //this.$router.push({name:'ReserveRecord',params:{userId:this.userId}});
      this.$router.push({path:'/reserveRecord',query:{userId:this.userId}});
    },
    getSignInRecord() {
      //this.$router.push({name:'SignInRecord',params:{userId:this.userId}});
      this.$router.push({path:'/signInRecord',query:{userId:this.userId}});
    },
    getSignOutRecord() {
      //this.$router.push({name:'SignInRecord',params:{userId:this.userId}});
      this.$router.push({path:'/signOutRecord',query:{userId:this.userId}});
    },
    getInobservanceRecord() {
      //this.$router.push({name:'SignInRecord',params:{userId:this.userId}});
      this.$router.push({path:'/inobservanceRecord',query:{userId:this.userId}});
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
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

h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
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
