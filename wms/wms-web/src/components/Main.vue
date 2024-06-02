<template>
  <div>
    <div style="margin-bottom: 5px;">
      <el-input v-model="name" placeholder="请输入姓名" style="width: 200px;" suffix-icon="el-icon-search"
                @keyup.enter.native="loadPost"></el-input>
      <el-select v-model="sex" filterable placeholder="请选择性别" style="margin-left: 5px;">
        <el-option
            v-for="item in sexs"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-button style="margin-left: 5px;" type="primary" @click="loadPost">查询</el-button>
      <el-button type="success" @click="resetParam">重置</el-button>

      <el-button style="margin-left: 5px;" type="primary" @click="add">新增</el-button>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{background:'#f3f6fd',color:'#555'}"
              border
    >
      <el-table-column label="ID" prop="id" width="60">
      </el-table-column>
      <el-table-column label="账号" prop="no" width="180"  >
      </el-table-column>
      <el-table-column label="姓名" prop="name" width="180">
      </el-table-column>
      <el-table-column label="年龄" prop="age" width="80">
      </el-table-column>
      <el-table-column label="性别" prop="sex" width="80">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.sex === 1 ? 'primary' : 'success'"
              disable-transitions>{{ scope.row.sex === 1 ? '男' : '女' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="角色" prop="roleId" width="200">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.roleId === 0 ? 'danger' : (scope.row.sex === 1 ? 'primary' : 'success')"
              disable-transitions>
            {{ scope.row.roleId === 0 ? '超级管理员' : (scope.row.sex === 1 ? '管理员' : '普通用户') }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="联系方式" prop="phone" width="180">
      </el-table-column>
      <el-table-column label="操作" prop="operate">
        <template slot-scope="scope">
        <el-button size="small"
                   type="success"
                   @click="Edit(scope.row)">编辑
        </el-button>
          <el-popconfirm
              title="你确定删除吗？"
              @confirm="Del(scope.row.id)"
          >
            <el-button slot="reference"
                       size="small"
                       type="danger"
            style="margin-left: 5px">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 10]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        >
    </el-pagination>
    <el-dialog
        :before-close="handleClose"
        :visible.sync="centerDialogVisible"
        center
        title="编辑"
        width="30%"
    >
      <span>
        <el-form   :rules="rules" ref="form" :model="form" label-width="80px">
        <el-form-item label="账号" prop="no" >
          <el-col :span="20">
        <el-input v-model="form.no"></el-input>
          </el-col>
        </el-form-item>
          <el-form-item label="姓名" prop="name">
          <el-col :span="20">
        <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
          <el-form-item label="密码"  prop="password">
          <el-col :span="20">
        <el-input v-model="form.password" show-password prefix-icon="el-icon-lock" size="medium"></el-input>
          </el-col>
        </el-form-item>
          <el-form-item label="年龄" prop="age">
          <el-col :span="20">
        <el-input v-model="form.age"></el-input>
          </el-col>
        </el-form-item>
          <el-form-item label="性别" >
         <el-radio-group v-model="form.sex">
          <el-radio label="1">男</el-radio>
       <el-radio label="0">女</el-radio>
     </el-radio-group>
  </el-form-item>
          <el-form-item label="电话" prop="phone">
          <el-col :span="20">
        <el-input v-model="form.phone"></el-input>
          </el-col>
        </el-form-item>
        </el-form>
      </span>
      <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Main",
  data() {
    const checkAge = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('年龄不能为空'));
      }
      if (!/^[0-9]+$/.test(value)) {
        callback(new Error('请输入数字值'));
      }
      if (parseInt(value) > 120 || parseInt(value) <= 0){
        callback(new Error("请输入合理的年龄"))
      }
      callback()
    };
    const checkPhone = (rule, value, callback) => {
      if (!/^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(value)) {
        callback(new Error('请输入合法的手机号'));
      }
      callback();
    };
    let  checkDuplicate = (rule,value,callback) =>{
      if (this.form.id){
        return callback()
      }
      this.$axios.get(this.$httpUrl+"/user/findByNo?no="+ this.form.no).then(res=>res.data).then(res =>{
        if (res.code !== 200){
          callback()
        }else {
         callback(new Error('账号已存在！'))
        }
      })
    }
    return {
      tableData:[],
      pageSize:10,
      pageNum:1,
      total:0,
      name: '',
      sex: '',
      sexs: [
        {
          value: '1',
          label: '男'
        }, {
          value: '2',
          label: '女'
        },
      ],
      centerDialogVisible: false,
      form: {
        id:'',
        no: '',
        name: '',
        password: '',
        age: '',
        phone: '',
        sex: '1',
        roleId:'1'
      },
      rules: {
        no: [
          {required: true, message: '请输入账号', trigger: 'blur'},
          {min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur'},
          {validator:checkDuplicate,trigger: 'blur'}
        ],
          name: [
            {required: true, message: '请输入姓名', trigger: 'blur'},
          ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur'}
        ],
        age: [
          { validator: checkAge, trigger: 'blur' }
        ],
        phone:[
          { validator: checkPhone, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    resetForm(){
      this.$refs.form.resetFields();
    },
    Edit(row){
      console.log(row)
      //把数据赋值到表单
      this.centerDialogVisible = true
      this.$nextTick(()=>{
        this.form.id = row.id
        this.form.no = row.no
        this.form.name = row.name
        this.form.password = ''
        this.form.age = row.age + ''
        this.form.sex = row.sex + ''
        this.form.phone = row.phone
        this.form.roleId = row.roleId
      })
    },
    Del(id){
      console.log(id)
      this.$axios.get(this.$httpUrl+'/user/delete?id='+id).then(res=>res.data).then(res=>{
        console.log(res)
        if (res.code === 200){
          this.$notify({
            message:'删除成功!',
            type:'success',
            duration:1000
          });
          this.loadPost()
        }else {
          this.$notify({
            message:'删除失败',
            type:'error',
            duration:1000
          });
        }
      })

    },
    add() {
      this.centerDialogVisible = true
      this.$nextTick(()=>{
        this.resetForm();
      })
    },
    doSave(){
      this.$axios.post(this.$httpUrl+'/user/save',this.form).then(res=>res.data).then(res=>{
        console.log(res)
        if (res.code === 200){
          this.$notify({
            message:'新增成功!',
            type:'success',
            duration: 1000
          });
          this.centerDialogVisible = false
          this.loadPost()
          this.resetForm()
        }else {
          this.$notify({
            message:'新增失败',
            type:'error',
            duration: 1000
          });
        }
      })
    },
    doMod(){
      this.$axios.post(this.$httpUrl+'/user/update',this.form).then(res=>res.data).then(res=>{
        console.log(res)
        if (res.code === 200){
          this.$notify({
            message:'新增成功!',
            type:'success',
            duration:1000
          });
          this.centerDialogVisible = false
          this.loadPost()
          this.resetForm()
        }else {
          this.$notify({
            message:'新增失败',
            type:'error',
            duration:1000
          });
        }
      })
    },
    save(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.id){
            this.doMod();
          }else {
            this.doSave();
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {
          });
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageNum = 1
      this.pageSize = val
      this.loadPost()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum = val
      this.loadPost()
    },
    loadGet() {
      this.$axios.get(this.$httpUrl + '/user/list').then(res => res.data).then(res => {
        console.log(res)
      })
    },
    resetParam() {
      this.name = ''
      this.sex = ''
      this.loadPost()
    },
    loadPost() {
      this.$axios.post(this.$httpUrl + '/user/listPageC1', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          name: this.name,
          sex: this.sex
        }
      }).then(res => res.data).then(res =>{
        console.log(res);
        if (res.code === 200) {
          this.tableData = res.data
          this.total = res.total
        } else {
          this.$notify({
            message:'获取数据失败!',
            type:'warning',
            duration: 1000
          })
        }
      })
    },
  },
  beforeMount() {
    //this.loadGet();
    this.loadPost();
  }
}
</script>
<style scoped>

</style>