<template>
  <div class="block" ref="date">
    <span class="demonstration">预约日期：</span>
    <el-date-picker
      v-model="date"
      value-format="yyyy-MM-dd"
      type="date"
      placeholder="选择日期"
      :picker-options="pickerOptions1"
      @change="changeDate">
    </el-date-picker>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        editable: false,
        pickerOptions1: {
          disabledDate(time) {

            return (time.getTime() < Date.now() - 24*1000*3600)||(time.getTime() > Date.now() + 24*1000*3600*2);
          },
          shortcuts: [{
            text: '今天',
            onClick(picker) {
              picker.$emit('pick',new Date());
            }
          }, {
            text: '明天',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() +3600*1000*24);
              picker.$emit('pick',date);
            }
          }, {
            text: '后天',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() +3600*1000*24*2);
              picker.$emit('pick',date);
            }
          }]
        },
        date: ''
      };
    },
    mounted () {
      this.date = this.formatDate(new Date());
      //alert(this.date);
      //this.date = this.defaultValue;
    },
    methods: {
      formatDate(date) {
        var y = date.getFullYear();  
        var m = date.getMonth() + 1;  
        m = m < 10 ? '0' + m : m;  
        var d = date.getDate();  
        d = d < 10 ? ('0' + d) : d;  
        return y + '-' + m + '-' + d; 
      },
      changeDate() {
        //alert('changeDate');
        //alert(this.date);
        this.$emit("changeDate",this.date);
      }
    }
  };
</script>