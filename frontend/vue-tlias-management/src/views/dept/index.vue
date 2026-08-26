<script setup>
import { ref, onMounted } from 'vue';
import { queryAllApi, addApi, queryByIdApi, updateApi, deleteByIdApi} from '@/api/dept';
import { ElMessage, ElMessageBox } from 'element-plus';

//声明列表展示数据
const deptList = ref([])

//动态加载数据-查询部门
const search = async () => {
  // const result = await axios.get('https://apifoxmock.com/m1/3128855-1224313-default/depts')
  // deptList.value = result.data.data

  const result = await queryAllApi();
  if(result.code){
    deptList.value = result.data;//不要把数据data写成日期date
  }
}

//钩子函数
onMounted(() => {
  search();
})

//Dialog对话框相关的数据模型以及函数
const dialogFormVisible = ref(false);//提示框是否可见
const dept = ref({name: ''});
const formTitle = ref('');//动态展示提示框名字
const deptFormRef = ref();//检查表单校验是否通过

//表单校验规则
const rules = ref({
  name: [
    // required: 必填项验证
    // trigger: 'blur' 表示在失去焦点时触发验证
    { required: true, message: '部门名称是必填项', trigger: 'blur' },
    { min: 2, max: 10, message: '部门名称的长度应该在2-10位', trigger: 'blur' },
  ]
})

//点击按钮打开新增部门对话框
const addDept = () => {
  dialogFormVisible.value = true;//显示对话框
  formTitle.value = '新增部门';//设置标题为新增部门
  dept.value = {name: ''};//重置表单数据(即部门名称需要重新写)
  //清除之前的表单校验结果(如果之前有表单校验结果的话)
  if(deptFormRef.value){
    deptFormRef.value.resetFields();
  }
}

//新增和修改部门
const save = async () => {
  //表单校验
  if(!deptFormRef.value) return;
  deptFormRef.value.validate(async (valid)=>{//valid表示表单校验是否通过：true 通过 / false 不通过
    if(valid){//校验通过后提交数据到后端
      let result;

      //不要忘记添加async关键字, await只能在async函数中使用
      if(dept.value.id){//修改
        result = await updateApi(dept.value);
      }else{//新增
        result = await addApi(dept.value);
      }

      //异步操作 = async：只要函数内涉及异步（如 HTTP 请求、定时器、文件读写），通常需要 async
      if(result.code){//成功
        //提示信息
        ElMessage.success('操作成功');
        //关闭对话框
        dialogFormVisible.value = false;
        //查询
        search();
      }else{//失败
        ElMessage.error(result.msg);
      }      
    }else{
      ElMessage.error('表单校验不通过');
    }
  })
}

//查询回显部门名称
const edit = async(id)=>{
  //修改对话框名字
  formTitle.value = '修改部门';
  //清除之前的表单校验结果(如果之前有表单校验结果的话)
  if(deptFormRef.value){
    deptFormRef.value.resetFields();
  }
  //查询回显
  const result = await queryByIdApi(id);
  if(result.code){
    dialogFormVisible.value = true;
    dept.value = result.data;
  }
}

//删除部门
const delById = async (id) =>{
  //弹出确认框
  ElMessageBox.confirm('您确认删除该部门吗?','提示',
    {confirmButtonText: '确认',cancelButtonText: '取消',type: 'warning',}
  ).then(async() => {//确认
      const result = await deleteByIdApi(id);
      if(result.code){
        ElMessage.success('删除成功');
        search();
      }else{
        ElMessage.error(result.msg);
      }
  }).catch(() => {//取消
      ElMessage.info('您已取消删除');
  })
}
</script>

<template>
  <h1>部门管理</h1>
  <div class="container">
    <el-button type="primary" @click="addDept"> + 新增部门</el-button>
  </div>

  <!-- 表格 -->
   <div class="container">
      <el-table :data="deptList" border style="width: 100%">
        <el-table-column type="index" label="序号" width="100" align="center" />
        <el-table-column prop="name" label="部门名称" width="260" align="center" />
        <el-table-column prop="updateTime" label="最后修改时间" width="300" align="center" />
        <el-table-column prop="address" label="操作" align="center">
          <template #default="scope">
            <el-button type="primary" size="small" @click="edit(scope.row.id)"><el-icon><Edit /></el-icon>编辑</el-button>
            <el-button type="danger" size="small" @click="delById(scope.row.id)"><el-icon><Delete /></el-icon>删除</el-button>
          </template>
        </el-table-column>
      </el-table>
   </div>

  <!-- Dialog对话框 -->
   <!-- :title中的“:”的作用：动态的绑定一个或多个 attribute，也可以是组件的 prop -->
  <el-dialog v-model="dialogFormVisible" :title="formTitle" width="500">
    <el-form :model="dept" :rules="rules" ref="deptFormRef">
      <el-form-item label="部门名称" label-width="80px" prop="name">
        <el-input v-model="dept.name" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save">确定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped>
  .container{
    margin: 10px 0px;
  }
</style>
