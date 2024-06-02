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
      <el-table-column label="账号" prop="no" width="180">
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
        <el-button size="small"
                   type="success"
                   @click="handleEdit(scope.$index, scope.row)">编辑
        </el-button>
        <el-button size="small"
                   type="danger"
                   @click="handleDelete(scope.$index, scope.row)">删除
        </el-button>
      </el-table-column>
    </el-table>
    <el-pagination
        :current-page="pageNum"
        :page-size="pageSize"
        :page-sizes="[5, 10, 20, 30]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange">
    </el-pagination>
    <el-dialog
        :before-close="handleClose"
        :visible.sync="centerDialogVisible"
        center
        title="提示"
        width="30%"
    >
      <span>
        <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="账号">
          <el-col :span="20">
        <el-input v-model="form.no"></el-input>
          </el-col>
        </el-form-item>
          <el-form-item label="姓名">
          <el-col :span="20">
        <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
          <el-form-item label="密码">
          <el-col :span="20">
        <el-input v-model="form.password"></el-input>
          </el-col>
        </el-form-item>
          <el-form-item label="年龄">
          <el-col :span="20">
        <el-input v-model="form.age"></el-input>
          </el-col>
        </el-form-item>
          <el-form-item label="性别">
         <el-radio-group v-model="form.sex">
          <el-radio label="1">男</el-radio>
       <el-radio label="0">女</el-radio>
     </el-radio-group>
  </el-form-item>
          <el-form-item label="电话">
          <el-col :span="20">
        <el-input v-model="form.phone"></el-input>
          </el-col>
        </el-form-item>
        </el-form>
      </span>
      <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="centerDialogVisible = false">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Main",
  data() {
    return {
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
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
        no: '',
        name: '',
        password: '',
        age: '',
        phone: '',
        sex: '1'
      }

    }
  },
  methods: {
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {
          });
    },
    add() {
      this.centerDialogVisible = true
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
      }).then(res => res.data).then(res => {
        console.log(res);
        if (res.code === 200) {
          this.total = res.total
          this.tableData = res.data
        } else {
          alert('获取数据失败')
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