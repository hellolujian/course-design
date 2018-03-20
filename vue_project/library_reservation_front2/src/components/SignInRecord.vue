<template>
  <div>
    <h1>{{this.$route.params.userId}}</h1>
  <el-table
    :data="tableData.slice((page-1)*size,page*size)"
    border
    style="width: 100%">
    <el-table-column
      prop="userId"
      label="读者编号"
      width="180">
    </el-table-column>
    <el-table-column
      prop="seatId"
      label="座位编号">
    </el-table-column>
    <el-table-column
      prop="reserveTime"
      label="预约时间">
    </el-table-column>
    <el-table-column
      prop="signInTime"
      label="签到时间">
    </el-table-column>
    <el-table-column
      prop="signInType"
      label="签到类别">
    </el-table-column>
  </el-table>

  <el-pagination
  background
  layout="total,sizes,prev, pager, next,jumper"
  :total="tableData.length"
  :page-sizes="[1,2,5,10]"
  :page-size="size"
  @current-change="changePage"
  @size-change="changeSize">
</el-pagination>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        tableData: [],
        page: 1,
        size: 8,
        apiUrl: 'http://127.0.0.1:8081/sign_in/record'
      }
    },
    mounted () {
      this.getOnePage();
    },
    methods: {
      getOnePage: function() {
        this.$http({
          url: this.apiUrl,
          method: 'post',
          params: {
                    userId: this.$route.query.userId
                   //page: this.page-1,
                   //size: this.size
                  }
        }).then((res) => {
          console.log(res.data);
          this.tableData = res.data;
          //alert(this.tableData.length)
        })
      },
      changePage: function (currentPage) {
        this.page = currentPage;
        this.getOnePage();
      },
      changeSize: function (pageSize) {
      this.size = pageSize;
      }
    }
  }
</script>