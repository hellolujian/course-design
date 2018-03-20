<template>
	<div id="app1">
		<el-collapse v-model="activeName" accordion @change="getSeat">
		  <el-collapse-item :title="item.name+':'+item.usableCount+'/'+item.count+'(可用座位数/座位总数)'" :name="index" :key="index" v-for="(item,index) in data">
		    <div v-for="(subItem,subIndex) in item.readingRooms" :key="subIndex">
		    	{{subItem.name}}:{{subItem.usableCount}}/{{subItem.count}}(可用座位数/座位总数)
		    	<el-button :disabled="Boolean(subSubItem.seatStatus)" @click="selectSeat(subSubItem)" type="primary" plain v-for="(subSubItem,subSubIndex) in subItem.seats" :key="subSubIndex">
		    		{{subSubItem.seatId}}
		    	</el-button>
		    </div>

		  </el-collapse-item>
		</el-collapse>
	</div>
</template>
<script>
  export default {
    data() {
      return {
        activeName: '0',
        apiUrl: 'http://127.0.0.1:8081/seat/query',
        reserveUrl: 'http://127.0.0.1:8081/reserve',
        data: [],
        reserveTime: '',
      };
    },
    //props: ['reserveTime'],
    methods: {
      reserveSeat(seatId) {

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
          alert(res.data.msg);
          console.log(res.data.data);
          if(res.data.data != null) {
            window.location.reload();
          }
        })
  	  },
      selectSeat: function(object) {
        //alert(this.$route.query.userId);
        //alert(this.reserveTime);
        //alert(object.seatId);
        this.reserveSeat(object.seatId);
        
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
        var y = date.getFullYear();  
        var m = date.getMonth() + 1;  
        m = m < 10 ? '0' + m : m;  
        var d = date.getDate();  
        d = d < 10 ? ('0' + d) : d;  
        return y + '-' + m + '-' + d; 
      },
    },
    
    mounted () {
      this.reserveTime = this.formatDate(new Date());
      //alert('seat:mounted:'+this.reserveTime)
      this.getSeat();
    }
  }
</script>