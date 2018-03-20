<template>
    <div class="table">
        <div class="handle-box">
            <el-select
                default-first-option
                v-model="select_date" 
                clearable filterable placeholder="筛选楼层" 
                class="handle-select mr10" 
                @change="selectChange"
                :filter-method="selectFilter">
                <el-option 
                    v-for="item in floorData"
                    :key="item.floorId" 
                    :label="item.floorName" 
                    :value="item.floorName">
                </el-option>
            </el-select>

            <el-input v-model="search" placeholder="输入关键词进行筛选" class="handle-input mr10"></el-input>
            <el-button type="primary" icon="circle-plus" @click="handleAdd">新增阅览室</el-button>
        </div>
        <el-table 
            :data="searchData" 
            highlight-current-row 
            v-loading="listLoading" 
            border 
            class="eltable"  
            @selection-change="handleSelectionChange">
            
            <el-table-column prop="readingRoomId" label="阅览室编号" sortable>
            </el-table-column>
            <el-table-column prop="readingRoomName" label="阅览室名称">
            </el-table-column>
            <el-table-column prop="floorName" sortable label="所在楼层">
            </el-table-column>
            <el-table-column prop="seatCount" sortable label="座位总数">
            </el-table-column>
            <el-table-column label="操作">
                <template scope="scope">
                    <el-button size="small"
                            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
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

        <el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
            <el-form :model="editForm" :rules="editFormRules" ref="editForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="阅览室编号" prop="readingRoomId">
                <el-input 
                    readonly="true" 
                    disabled="true"
                    v-model="editForm.readingRoomId">
                </el-input>
            </el-form-item>
            <el-form-item label="阅览室名称" prop="readingRoomName">
                <el-input v-model="editForm.readingRoomName"></el-input>
            </el-form-item>
            <el-form-item label="楼层名称" prop="readingRoomName">
                <el-select disabled="true" v-model="editForm.floorName" placeholder="请选择楼层" @change="floorChange">
                <el-option 
                    v-for="item in floorData"
                    :key="item.floorName" 
                    :label="item.floorName" 
                    :value="item.floorId"
                    >
                </el-option>
                </el-select>
            </el-form-item>
            
            <el-form-item label="座位数量" prop="seatCount">
                <el-input-number disabled="true" v-model="editForm.seatCount" :min="0" :max="1000" label="座位数量"></el-input-number>
            </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="submitEditForm('editForm')" :loading="editLoading">提交</el-button>
			    <el-button @click="resetEditForm('editForm')">重置</el-button>
            </div>
        </el-dialog>

        <el-dialog title="新增" v-model="addFormVisible" :close-on-click-modal="false">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="阅览室编号" prop="readingRoomId">
                <el-input 
                    readonly="true" 
                    v-model="ruleForm.readingRoomId"
                    placeholder="填写下面信息即可自动生成编号">
                </el-input>
            </el-form-item>
            <el-form-item label="阅览室名称" prop="readingRoomName">
                <el-input v-model="ruleForm.readingRoomName"></el-input>
            </el-form-item>
            <el-form-item label="楼层名称" prop="readingRoomName">
                <el-select v-model="ruleForm.floorName" placeholder="请选择楼层" @change="floorChange">
                <el-option 
                    v-for="item in floorData"
                    :key="item.floorName" 
                    :label="item.floorName" 
                    :value="item.floorId"
                    >
                </el-option>
                </el-select>
            </el-form-item>
            
            <el-form-item label="座位数量" prop="seatCount">
                <el-input-number v-model="ruleForm.seatCount" :min="0" :max="1000" label="座位数量"></el-input-number>
            </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
				<el-button @click.native="addFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="submitForm('ruleForm')" :loading="addLoading">提交</el-button>
			    <el-button @click="resetForm('ruleForm')">重置</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                url: 'http://127.0.0.1:8081/admin/readingRoomInfo',
                floorUrl: 'http://127.0.0.1:8081/admin/floorInfo',
                addOneReadingRoomUrl: 'http://127.0.0.1:8081/admin/addOneReadingRoom',
                deleteOneReadingRoomUrl: 'http://127.0.0.1:8081/admin/deleteOneReadingRoom',
                updateReadingRoomUrl: 'http://127.0.0.1:8081/admin/updateReadingRoom',
                readingRoom: null,
                selectedFloor: '', //新增阅览室的楼层编号
                editFloorId: '', //编辑页面的楼层编号
                floorData: [], //楼层信息
                tableData: [],
                tableDataShow: [],
                listLoading: false,
                page: 1,
                total: 0,
                size: 8,
                filters: [ { text: '今天', value: '今天' }, { text: '明天', value: '明天' }, { text: '后天', value: '后天' }, { text: '今天前', value: '今天前' }],
                select_date: '',
                search: '',
                //编辑界面数据
				editForm: {
					readingRoomId: '',
                    readingRoomName: '',
                    floorName: '',
                    seatCount: 0
				},
                addFormVisible: false,//新增界面是否显示
                addLoading: false,
                editFormVisible: false,//编辑界面是否显示
                editLoading: false,
                editFormRules: {
					readingRoomName: [
						{ required: true, message: '请填写阅览室名称', trigger: 'blur' }
					]
				},
                ruleForm: {
                    readingRoomId: '',
                    readingRoomName: '',
                    floorName: '',
                    seatCount: 0
                },
                rules: {
                    readingRoomId: [
                    ],
                    readingRoomName: [
                        { required: true, message: '请填写阅览室名称', trigger: 'blur' }
                    ],
                    floorName: [
                        { required: true, message: '请选择楼层', trigger: 'change' }
                    ],
                    seatCount: [
                        //{ type: 'number', message: '数量不能为空', trigger: 'change' },
                        //{ required: true, message: '请填写数量', trigger: 'blur' }
                    ],
                
                }
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
            floorChange(val) {
                //alert(val);
                this.selectedFloor = val;
            },
            submitForm(formName) {
                var _this = this;
                this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.$confirm('确认提交吗？', '提示', {}).then(() => {
                        this.addLoading = true;
                        this.addOneReadingRoom();
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
            submitEditForm(formName) {
                //获取选中项的楼层编号
                var defaultFloorId = this.floorData.find(item => item.floorName === this.editForm.floorName)['floorId'];
                var _this = this;
                this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.$confirm('确认提交吗？', '提示', {}).then(() => {
                        this.editLoading = true;
                        this.updateReadingRoom();
                        this.$message({
                            message: '修改成功',
                            type: 'success'
                        });
                        this.$refs[formName].resetFields();
                        this.editFormVisible = false;
                        this.editLoading = false;
                        
                    });
                    
                } else {
                    console.log('error submit!!');
                    return false;
                }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },

            resetEditForm(formName) {
                this.editForm.readingRoomName = '';
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
            //获取阅览室列表
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
            

            //删除阅览室
            deleteOneReadingRoom(readingRoomId) {
                this.$http({
                url: this.deleteOneReadingRoomUrl,
                method: 'post',
                params: {
                           readingRoomId: readingRoomId
                           
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
				this.ruleForm = {
                    readingRoomId: '',
                    readingRoomName: '',
                    floorName: '',
                    seatCount: 0
                };
			},

            //编辑
            handleEdit(index, row) {
                this.editFormVisible = true;
				this.editForm= {
                    readingRoomId: row.readingRoomId,
                    readingRoomName: row.readingRoomName,
                    floorName: row.floorName,
                    seatCount: row.seatCount
                };
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
			//新增
			addSubmit: function () {
				this.$refs.addForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.addLoading = true;
							//NProgress.start();
							let para = Object.assign({}, this.addForm);
							para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
							addUser(para).then((res) => {
								this.addLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['addForm'].resetFields();
								this.addFormVisible = false;
								this.getUsers();
							});
						});
					}
				});
			},
            handleDelete(index, row) {
                this.$confirm('确认删除该记录吗?', '提示', {
                    type: 'warning'
                }).then(() => {

                    this.listLoading = true;
                    this.deleteOneReadingRoom(row.readingRoomId);
                    this.listLoading = false;
                }).catch(() => {});
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

            //添加阅览室
            addOneReadingRoom() {
                this.$http({
                url: this.addOneReadingRoomUrl,
                method: 'post',
                params: {
                    readingRoomName: this.ruleForm.readingRoomName,
                    floorId: this.selectedFloor,
                    seatCount: this.ruleForm.seatCount,
                }
              }).then((res) => {
                this.$message({
                            message: '添加成功',
                            type: 'success'
                        });
                this.getData();
                console.log(res.data);
                this.readingRoom = data;
              })
            },

            //更新阅览室名称
            updateReadingRoom() {
                this.$http({
                    url: this.updateReadingRoomUrl,
                    method: 'post',
                    params: {
                        readingRoomId: this.editForm.readingRoomId,
                        readingRoomName: this.editForm.readingRoomName
                    }
                }).then((res) => {
                    this.$message({
                        message: '修改成功',
                        type: 'success'
                    });
                    this.getData();
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
    width: 160px;
}
.handle-input{
    width: 300px;
    display: inline-block;
}

.pagination{
    text-align: center;
}

</style>