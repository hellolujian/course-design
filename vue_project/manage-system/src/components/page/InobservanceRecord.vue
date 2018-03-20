<template>
    <div class="table">
        <div class="handle-box">
            <el-select v-model="select_date" clearable filterable placeholder="筛选违规类别" class="handle-select mr10" @change="selectChange"
            :filter-method="selectFilter">
                <el-option v-for="inobservanceType in inobservanceTypeData" 
                :key="inobservanceType.inobservanceTypeId" 
                :value="inobservanceType.inobservanceTypeName"
                :label="inobservanceType.inobservanceTypeName">
                </el-option>
            </el-select>
            <el-input v-model="search" placeholder="筛选关键词" class="handle-input mr10"></el-input>
            
        </div>
        <el-table :data="searchData" 
                   highlight-current-row v-loading="listLoading" border style="width: 100%"  @selection-change="handleSelectionChange">
            <el-table-column prop="userId" label="编号" sortable width="120">
            </el-table-column>
            <el-table-column prop="userName" label="姓名" width="100">
            </el-table-column>
            <el-table-column prop="seatId" label="座位编号" width="180">
                </el-table-column>
            <el-table-column prop="reserveTime" label="预约时间" width="170">
            </el-table-column>
            <el-table-column
              prop="inobservanceTypeName"
              label="违规类别"
              width="120"
              :filters="filters"
              :filter-method="filterTag"
              filter-placement="bottom-end">
              <template slot-scope="scope">
                <el-tag
                  :type="scope.row.inobservanceTypeName === '今天' ? 'primary' : 'success'"
                  close-transition>{{scope.row.inobservanceTypeName}}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="createTime" label="违规时间" width="170">
            </el-table-column>
            
        </el-table>
        <div class="pagination">
            <el-pagination
                    @current-change ="handleCurrentChange"
                    layout="prev, pager, next"
                    :total="total"
                    :page-size="size">
            </el-pagination>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                url: 'http://127.0.0.1:8081/admin/inobservance',
                inobservanceTypeUrl: 'http://127.0.0.1:8081/admin/inobservanceType',
                inobservanceTypeData: [],
                tableData: [],
                tableDataShow: [],
                sels: [], //列表选中列
                listLoading: false,
                page: 1,
                total: 0,
                size: 8,
                filters: [],
                select_date: '',
                search: ''
            }
        },
        mounted(){
            this.getInobservanceType();
            this.getData();
        },
        

        computed: {
            searchData: function() {
                var search = this.search;
                var select_date = this.select_date;
                if(select_date && !search ) {
                //alert("select_date变化search不变");
                    var a = this.tableData.filter(function(tableData) {
                        return Object.keys(tableData).some(function(key) {
                            var flag = String(tableData[key]).toLowerCase().indexOf(select_date) > -1;
                            return flag;

                        })
                        
                    })
                    this.total = a.length;
                    return a.slice((this.page-1)*this.size,this.page*this.size);
                }
                if(search && !select_date) {
                //alert("select_date不变search变化");
                    var a = this.tableData.filter(function(tableData) {
                        return Object.keys(tableData).some(function(key) {
                            var flag = String(tableData[key]).toLowerCase().indexOf(search) > -1;
                            return flag;

                        })
                        
                    })
                    this.total = a.length;
                    return a.slice((this.page-1)*this.size,this.page*this.size);
                }
                if(search && select_date) {
                //alert("select_date变化search变化");
                    var b = this.tableData.filter(function(tableData) {
                        return Object.keys(tableData).some(function(key) {
                            var flag = String(tableData[key]).toLowerCase().indexOf(search) > -1;
                            return flag;

                        })
                        
                    });
                    var a = b.filter(function(tableData) {
                        return Object.keys(tableData).some(function(key) {
                            var flag = String(tableData[key]).toLowerCase().indexOf(select_date) > -1;
                            return flag;

                        })
                        
                    });
                    this.total = a.length;
                    return a.slice((this.page-1)*this.size,this.page*this.size);
                }
                this.total = this.tableData.length;
                return this.tableData.slice((this.page-1)*this.size,this.page*this.size);
            }
        },

        methods: {

            filtersInit() {
                var _this = this;
                //alert(this.inobservanceTypeData.length)
                this.inobservanceTypeData.forEach(function(item){
                    let object = new Object();
                    object.text = item.inobservanceTypeName;
                    object.value = item.inobservanceTypeName;
                    _this.filters.push(object);
                });
            },
            selectChange(){
                
            },
            selectFilter(selectValue){
                //alert(selectValue);
            },
            handleCurrentChange(val){
                this.page = val;
                //this.getData();
            },
            //获取违规类别
            getInobservanceType() {
              this.$http({
                  url: this.inobservanceTypeUrl,
                  method: 'post'
              }).then((res) => {
                 console.log(res.data);
                 this.inobservanceTypeData = res.data;
                 this.filtersInit();
                // alert(this.inobservanceTypeData.length);
              })
            },
            //获取读者列表
            getData(){
              this.listLoading = true;
                this.$http({
                    url: this.url,
                    method: 'post',
                }).then((res) => {
                    console.log(res.data);
                    this.tableData = res.data;
                    this.total = this.tableData.length;
                    //this.tableDataShow = res.data.slice((this.page-1)*this.size,this.page*this.size);
                    this.listLoading = false;
                })
            },
            
            formatter(row, column) {
                return row.address;
            },
            filterTag(value, row) {
                return row.inobservanceTypeName === value;
            },
            handleDelete(index, row) {
                this.$confirm('确认删除该记录吗?', '提示', {
                    type: 'warning'
                }).then(() => {

                    this.listLoading = true;
                    let idList = [row.id];
                    this.deleteBlacklist(idList);
                    this.listLoading = false;
                }).catch(() => {});
            },
           
            
            //选中项改变触发
            handleSelectionChange(sels) {
            //alert('change')
                this.sels = sels
            }
        }
    }
</script>

<style scoped>
.handle-box{
    margin-bottom: 20px;
}
.handle-select{
    width: 130px;
}
.handle-input{
    width: 300px;
    display: inline-block;
}
.pagination{
    text-align: center;
}
</style>