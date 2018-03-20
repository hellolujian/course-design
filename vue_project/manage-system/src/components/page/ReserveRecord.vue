<template>
    <div class="table">
        <div class="handle-box">
            <el-button type="danger" icon="delete" class="handle-del mr10" :disabled="this.sels.length===0" @click="delAll">批量删除</el-button>
            <el-select v-model="select_date" clearable filterable placeholder="筛选日期" class="handle-select mr10" @change="selectChange"
            :filter-method="selectFilter">
                <el-option key="1" label="今天" value="今天"></el-option>
                <el-option key="2" label="明天" value="明天"></el-option>
                <el-option key="3" label="后天" value="后天"></el-option>
                <el-option key="4" label="今天前" value="今天前"></el-option>
            </el-select>
            <el-input v-model="search" placeholder="筛选关键词" class="handle-input mr10"></el-input>
        </div>
        <el-table :data="searchData" 
            highlight-current-row 
            v-loading="listLoading" 
            border 
            style="width: 100%"  
            @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="40"></el-table-column>
            <el-table-column prop="id" label="索引" sortable width="90">
            </el-table-column>
            <el-table-column prop="userId" label="编号" sortable width="120">
            </el-table-column>
            <el-table-column prop="userName" label="姓名" width="100">
            </el-table-column>
            <el-table-column prop="seatId" sortable label="座位号" width="140">
            </el-table-column>
            <el-table-column prop="reserveTime" sortable label="预约时间" width="170">
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="170">
            </el-table-column>
            <el-table-column
              prop="tag"
              label="标签"
              width="90"
              :filters="filters"
              :filter-method="filterTag"
              filter-placement="bottom-end">
              <template slot-scope="scope">
                <el-tag
                  :type="scope.row.tag === '今天' ? 'primary' : 'success'"
                  close-transition>{{scope.row.tag}}</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="100">
                <template scope="scope">
                    <el-button size="small" type="danger"
                            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
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
                url: 'http://127.0.0.1:8081/admin/reserveRecord',
                tableData: [],
                tableDataShow: [],
                sels: [], //列表选中列
                listLoading: false,
                page: 1,
                total: 0,
                size: 8,
                filters: [ { text: '今天', value: '今天' }, { text: '明天', value: '明天' }, { text: '后天', value: '后天' }, { text: '今天前', value: '今天前' }],
                select_date: '',
                search: ''
            }
        },
        mounted(){
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
            selectChange(){
                
            },
            selectFilter(selectValue){
                //alert(selectValue);
            },
            handleCurrentChange(val){
                this.page = val;
                //this.getData();
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
            //删除预约记录
            deleteReserve(idList) {
            //alert(idList);
            console.log(idList);
              this.$http({
                url: 'http://127.0.0.1:8081/admin/deleteReserve',
                method: 'get',
                params: {
                           idList: JSON.stringify(idList),
                           
                        }
              }).then((res) => {
                if(res.data >= 1){
                    this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.getData();
                }
                else{
                this.$message({
                            message: '删除失败',
                            type: 'error'
                        });
                }

                console.log(res.data);
              })
            },
            formatter(row, column) {
                return row.address;
            },
            filterTag(value, row) {
                alert(value);
                return row.tag === value;
            },
            handleDelete(index, row) {
                this.$confirm('确认删除该记录吗?', '提示', {
                    type: 'warning'
                }).then(() => {

                    this.listLoading = true;
                    let idList = [row.id];
                    this.deleteReserve(idList);
                    this.listLoading = false;
                }).catch(() => {});
            },
            //批量删除
            delAll(){
                var ids = this.sels.map(item => item.id);
                this.$confirm('确认删除选中记录吗？', '提示', {
                    type: 'warning'
                }).then(() => {
                    this.listLoading = true;
                    this.deleteReserve(ids);
                    this.listLoading = false;
                }).catch(() => {});
            },
            //选中项改变触发
            handleSelectionChange(sels) {
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
    width: 120px;
}
.handle-input{
    width: 300px;
    display: inline-block;
}
.pagination{
    text-align: center;
}
</style>