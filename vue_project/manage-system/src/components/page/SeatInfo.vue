<template>
    <div class="table">
        <div class="handle-box">
            <el-button type="danger" 
                icon="delete" 
                class="handle-del mr10" 
                :disabled="this.sels.length===0" 
                @click="delAll">批量删除
            </el-button>
            <el-select
                default-first-option
                v-model="select_date" 
                clearable 
                filterable 
                placeholder="选择楼层" 
                class="handle-select mr10" 
                @change="floorSelectChange"
                @clear="floorClear"
                :filter-method="selectFilter">
                <el-option 
                    v-for="item in floorData"
                    :key="item.floorName" 
                    :label="item.floorName" 
                    :value="item.floorId">
                </el-option>
            </el-select>
            <el-select
                default-first-option
                v-model="selectReadingRoom" 
                clearable 
                filterable 
                placeholder="选择阅览室" 
                class="handle-select mr10" 
                @change="selectReadingRoomChange"
                :filter-method="selectFilter">
                <el-option 
                    v-for="item in readingRoomData"
                    :key="item.readingRoomId" 
                    :label="item.readingRoomName" 
                    :value="item.readingRoomId">
                </el-option>
            </el-select>

            <el-input 
                v-model="search" 
                placeholder="输入关键词搜索" 
                class="handle-input mr10">
            </el-input>
            <el-button 
                type="primary" 
                icon="circle-plus" 
                @click="handleAdd">
                新增
            </el-button>
        </div>
        <el-table 
            :data="searchData" 
            highlight-current-row 
            v-loading="listLoading" 
            border 
            @selection-change="handleSelectionChange">
            <el-table-column type="selection"></el-table-column>
            <el-table-column prop="seatId" label="座位号" sortable>
            </el-table-column>
            <el-table-column prop="seatStatusName" label="状态">
            </el-table-column>
            <el-table-column prop="readingRoomName" label="所在阅览室">
            </el-table-column>
            <el-table-column prop="floorName" label="所在楼层">
            </el-table-column>
            
            <el-table-column label="操作">
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

        <!--新增界面-->
		<el-dialog title="新增" v-model="addFormVisible" :close-on-click-modal="false">
            <el-form :model="addSeatForm" :rules="addSeatRules" ref="addSeatForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="楼层名称" prop="floorName">
                <el-select v-model="addSeatForm.floorName" placeholder="请选择楼层" @change="addFormFloorChange">
                <el-option 
                    v-for="item in floorData"
                    :key="item.floorName" 
                    :label="item.floorName" 
                    :value="item.floorId"
                    >
                </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="阅览室名称" prop="readingRoomName">
                <el-select v-model="addSeatForm.readingRoomName" >
                    <el-option 
                        v-for="item in readingRoomData"
                        :key="item.readingRoomId" 
                        :label="item.readingRoomName" 
                        :value="item.readingRoomName">
                    </el-option>
                </el-select>
            </el-form-item>
            
            <el-form-item label="座位数量" prop="seatCount">
                <el-input-number v-model="addSeatForm.seatCount" :min="0" :max="1000" label="座位数量"></el-input-number>
            </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
				<el-button @click.native="addFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="submitForm('addSeatForm')" :loading="addLoading">提交</el-button>
		    <!--
        	    <el-button @click="resetForm('addSeatForm')">重置</el-button>
            -->
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                url: 'http://127.0.0.1:8081/admin/seatInfo',
                floorUrl: 'http://127.0.0.1:8081/admin/floorInfo',
                floorData: [],
                addSeatUrl: 'http://127.0.0.1:8081/admin/addSeat',
                deleteOneSeatUrl: 'http://127.0.0.1:8081/admin/deleteOneSeat',
                deleteManySeatsUrl: 'http://127.0.0.1:8081/admin/deleteManySeats',
                readingRoomUrl: 'http://127.0.0.1:8081/admin/getReadingRoomByFloor',
                readingRoomData: [],
                tableData: [],
                tableDataShow: [],
                sels: [], //列表选中列
                listLoading: false,
                page: 1,
                total: 0,
                size: 8,
                filters: [ { text: '今天', value: '今天' }, { text: '明天', value: '明天' }, { text: '后天', value: '后天' }, { text: '今天前', value: '今天前' }],
                select_date: '', //选中的楼层
                selectReadingRoom: '',//选中的阅览室
                search: '',
                addFormVisible: false,//新增界面是否显示
                addLoading: false,
                //新增界面数据
				addSeatForm: {
					floorName: '',
                    readingRoomName: '',
                    seatCount: 1,
				},
                //新增界面校验规则
                addSeatRules: {
					//floorName: [
						//{ required: true, message: '请输入姓名', trigger: 'blur' }
					//]
                    seatCount: [
                        //{ required: true, message: '数量不能为空', trigger: 'blur'},
                        //{ min: 1, max: 1000, message: '范围在1-1000', trigger: 'blur'}
                    ]
				},
            }
        },
        mounted(){
            this.getData();
            this.getFloorInfo();
        },

        computed: {
            searchData: function() {
                var search = this.search;
                var select_date = this.select_date;
                var selectReadingRoom = this.selectReadingRoom;
                if(selectReadingRoom) {
                    if(search) {
                        var b = this.tableData.filter(function(tableData) {
                            return Object.keys(tableData).some(function(key) {
                                var flag = String(tableData[key]).toLowerCase().indexOf(search) > -1;
                                return flag;
                            })
                        });
                        var a = b.filter(function(tableData) {
                            return Object.keys(tableData).some(function(key) {
                                var flag = String(tableData[key]).toLowerCase().indexOf(selectReadingRoom) > -1;
                                return flag;
                            })
                        });
                        this.total = a.length;
                        return a.slice((this.page-1)*this.size,this.page*this.size);
                    }
                    else {
                        selectReadingRoom = this.readingRoomData.find(
                            item => item.readingRoomId === selectReadingRoom)['readingRoomName'];
                        var a = this.tableData.filter(function(tableData) {
                            return Object.keys(tableData).some(function(key) {
                                var flag = String(tableData[key]).toLowerCase().indexOf(selectReadingRoom) > -1;
                                return flag;
                            })
                        })
                        this.total = a.length;
                        return a.slice((this.page-1)*this.size,this.page*this.size);
                    }
                    
                }
                else {
                    if(select_date && !search ) {
                    //alert("select_date变化search不变");
                    //选择器绑定的值是value,而显示的值是label,所以需要对应floorId的floorName值
                    select_date = this.floorData.find(item => item.floorId === select_date)['floorName'];
                    //alert(select_date);
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
                }
                
                this.total = this.tableData.length;
                return this.tableData.slice((this.page-1)*this.size,this.page*this.size);
            }
        },

        methods: {
            //重置表单数据
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            //添加页面楼层改变触发事件
            addFormFloorChange(val) {
                //alert(val)
                this.getReadingRoomByFloor(val);
                
            },

            floorClear() {
                this.selectReadingRoom = '';
            },
            //楼层改变触发
            floorSelectChange(val){
                //alert(val)
                
                this.getReadingRoomByFloor(val);
                this.selectReadingRoom = '';
            },
            //阅览室改变触发
            selectReadingRoomChange(val) {

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
            //删除座位
            deleteSeat(seatIds) {
            //alert(seatIds);
            //alert(JSON.stringify(seatIds));
            console.log(seatIds);
              this.$http({
                url: this.deleteManySeatsUrl,
                method: 'post',
                params: {
                           seatIds: JSON.stringify(seatIds),
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

            //显示新增界面
			handleAdd: function () {
				this.addFormVisible = true;
                var defaultFloorName = this.floorData[0].floorName;
                this.addSeatForm.floorName = defaultFloorName;
                var defaultFloorId = this.floorData.find(item => item.floorName === defaultFloorName)['floorId'];
                
                this.getReadingRoomByFloor(defaultFloorId);
                this.addSeatForm.seatCount = 1;
                //alert(this.addSeatForm.readingRoomName);
			},

            //编辑
            handleEdit(index, row) {
                this.editFormVisible = true;
				this.editForm = Object.assign({}, row);
            },
            //编辑
			editSubmit: function () {
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.editLoading = true;
							//NProgress.start();
							let para = Object.assign({}, this.editForm);
							para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
							editUser(para).then((res) => {
								this.editLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['editForm'].resetFields();
								this.editFormVisible = false;
								this.getUsers();
							});
						});
					}
				});
			},
			//新增座位
			submitForm(formName) {
                var _this = this;
                var selectReadingRoomName = this.addSeatForm.readingRoomName;
                var selectReadingRoomId = this.readingRoomData.find(item => item.readingRoomName === selectReadingRoomName)['readingRoomId'];
                var count = this.addSeatForm.seatCount;
                this.$refs[formName].validate((valid) => {
                if (valid) {
                    //alert(selectReadingRoomId + count);
                    this.$confirm('确认提交吗？', '提示', {}).then(() => {
                        //alert(selectReadingRoomId + ':' + count);
                        this.addLoading = true;
                        this.addSeat(selectReadingRoomId,count);
                        this.$message({
                            message: '添加成功',
                            type: 'success'
                        });
                        this.$refs[formName].resetFields();
                        this.addFormVisible = false;
                        this.addLoading = false;
                        this.resetForm(formName);
                    });
                    
                } else {
                    console.log('error submit!!');
                    return false;
                }
                });
            },
            handleDelete(index, row) {
                this.$confirm('确认删除该记录吗?', '提示', {
                    type: 'warning'
                }).then(() => {

                    this.listLoading = true;
                    let seatIds = [row.seatId];
                    //alert(seatIds);
                    this.deleteSeat(seatIds);
                    this.listLoading = false;
                }).catch(() => {});
            },
            //批量删除
            delAll(){
                var seatIds = this.sels.map(item => item.seatId);
                this.$confirm('确认删除选中座位吗？', '提示', {
                    type: 'warning'
                }).then(() => {
                    this.listLoading = true;
                    this.deleteSeat(seatIds);
                    this.listLoading = false;
                }).catch(() => {});
            },
            //选中项改变触发
            handleSelectionChange(sels) {
                this.sels = sels
            },

            //获取楼层信息
            getFloorInfo() {
                this.$http({
                url: this.floorUrl,
                method: 'post',
              }).then((res) => {
                console.log(res.data);
                this.floorData = res.data;
              })
            },

            //获取阅览室信息
            getReadingRoomByFloor(floorId) {
                this.$http({
                    url: this.readingRoomUrl,
                    method: 'post',
                    params: {
                        floorId: floorId
                    }
                }).then((res) => {
                    console.log(res.data);
                    this.readingRoomData = res.data;
                    this.addSeatForm.readingRoomName = this.readingRoomData[0].readingRoomName;
                })
            },
            //添加座位
            addSeat(readingRoomId,count) {
                //alert('readingRoomId:'+readingRoomId+'count:'+count);
                this.$http({
                    url: this.addSeatUrl,
                    method: 'post',
                    params: {
                        readingRoomId: readingRoomId,
                        count: count,
                    }
                }).then((res) => {
                    this.getData();
                    console.log(res.data);
                })
            }
        }
    }
</script>

<style scoped>
.handle-box{
    margin-bottom: 20px;
}
.handle-select{
    width: 140px;
}
.handle-input{
    width: 300px;
    display: inline-block;
}
.pagination{
    text-align: center;
}
</style>