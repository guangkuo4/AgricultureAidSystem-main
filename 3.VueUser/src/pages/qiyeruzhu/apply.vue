<template>
  <div>
    <!-- 面包屑导航 -->
    <div :style='{"width":"100%","padding":"30px 10%","margin":"0 auto","borderRadius":"8px","background":"linear-gradient(180deg, rgba(80, 160, 80, 0.9), rgba(60, 140, 60, 1))","boxShadow":"0 4px 12px rgba(0, 0, 0, 0.15)","border":"1px solid rgba(255, 255, 255, 0.2)"}' class="breadcrumb-preview">
      <el-breadcrumb :separator="'>'" :style='{"fontSize":"16px","lineHeight":"1","color":"#fff"}'>
        <el-breadcrumb-item class="item1" to="/"><a style="color: #fff;">首页</a></el-breadcrumb-item>
        <el-breadcrumb-item class="item2"><span style="color: #fff;">农商企业入驻</span></el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <!-- 主容器 -->
    <div :style='{"width":"80%","padding":"40px 0","margin":"30px auto","position":"relative","background":"#f8faf8","borderRadius":"16px","boxShadow":"0 8px 32px rgba(46, 125, 50, 0.1)"}'>
      
      <!-- 标题区域 -->
      <div :style='{"textAlign":"center","padding":"0 30px 30px","borderBottom":"2px solid rgba(46, 125, 50, 0.1)","marginBottom":"40px"}'>
        <i class="el-icon-office-building" :style='{"fontSize":"48px","color":"#2E7D32","marginBottom":"15px"}'></i>
        <div :style='{"fontSize":"28px","fontWeight":"bold","color":"#1a1a1a","marginBottom":"8px"}'>农商企业入驻申请</div>
        <div :style='{"fontSize":"14px","color":"#999"}'>填写以下信息，开启您的合作之旅</div>
      </div>

      <!-- 表单区域 -->
      <div :style='{"maxWidth":"800px","margin":"0 auto","padding":"0 30px"}'>
        <el-form
          ref="ruleForm"
          :model="ruleForm"
          :rules="rules"
          label-width="140px"
          class="add-update-preview"
          :style='{"width":"100%"}'
        >
          <!-- 企业信息区域 -->
          <div :style='{"background":"#fff","padding":"30px","borderRadius":"12px","marginBottom":"25px","border":"1px solid rgba(46, 125, 50, 0.1)"}'>
            <div :style='{"display":"flex","alignItems":"center","marginBottom":"25px"}'>
              <i class="el-icon-office-building" :style='{"fontSize":"20px","color":"#2E7D32","marginRight":"10px"}'></i>
              <div :style='{"fontSize":"18px","fontWeight":"600","color":"#1a1a1a"}'>农商企业基本信息</div>
            </div>

            <el-form-item :style='{"marginBottom":"20px"}' label="农商/企业名称" prop="qiyemingcheng">
              <el-input 
                v-model="ruleForm.qiyemingcheng" 
                placeholder="请输入农商/企业名称" 
                clearable
                prefix-icon="el-icon-s-shop"
              ></el-input>
            </el-form-item>

            <el-form-item :style='{"marginBottom":"20px"}' label="联系人" prop="lianxiren">
              <el-input 
                v-model="ruleForm.lianxiren" 
                placeholder="请输入联系人姓名" 
                clearable
                prefix-icon="el-icon-user"
              ></el-input>
            </el-form-item>

            <el-form-item :style='{"marginBottom":"20px"}' label="联系电话" prop="lianxidianhua">
              <el-input 
                v-model="ruleForm.lianxidianhua" 
                placeholder="请输入联系电话" 
                clearable
                prefix-icon="el-icon-phone"
              ></el-input>
            </el-form-item>

            <el-form-item :style='{"marginBottom":"20px"}' label="入驻理由" prop="ruzhuliyou">
              <el-input 
                v-model="ruleForm.ruzhuliyou" 
                placeholder="请详细描述您的入驻理由" 
                clearable
                type="textarea"
                :rows="4"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </div>

          <!-- 营业执照区域 -->
          <div :style='{"background":"#fff","padding":"30px","borderRadius":"12px","marginBottom":"25px","border":"1px solid rgba(46, 125, 50, 0.1)"}'>
            <div :style='{"display":"flex","alignItems":"center","marginBottom":"25px"}'>
              <i class="el-icon-document" :style='{"fontSize":"20px","color":"#2E7D32","marginRight":"10px"}'></i>
              <div :style='{"fontSize":"18px","fontWeight":"600","color":"#1a1a1a"}'>营业执照</div>
            </div>

            <el-form-item :style='{"marginBottom":"10px"}' label="上传执照" prop="yingyezhizhao">
              <div :style='{"display":"flex","alignItems":"center","width":"100%"}'>
                <div :style='{"flex":"1"}'>
                  <file-upload
                    tip="点击上传营业执照"
                    action="file/upload"
                    :limit="1"
                    :multiple="false"
                    :fileUrls="ruleForm.yingyezhizhao?ruleForm.yingyezhizhao:''"
                    @change="yingyezhizhaoUploadChange"
                  ></file-upload>
                </div>
              </div>
            </el-form-item>
            <div :style='{"marginLeft":"140px","fontSize":"13px","color":"#f56c6c","display":"flex","alignItems":"center"}'>
              <i class="el-icon-warning" :style='{"marginRight":"5px"}'></i>
              农商企业申请必须上传营业执照
            </div>
          </div>

          <!-- 账号信息区域 -->
          <div :style='{"background":"#fff","padding":"30px","borderRadius":"12px","marginBottom":"30px","border":"1px solid rgba(46, 125, 50, 0.1)"}'>
            <div :style='{"display":"flex","alignItems":"center","marginBottom":"25px"}'>
              <i class="el-icon-lock" :style='{"fontSize":"20px","color":"#2E7D32","marginRight":"10px"}'></i>
              <div :style='{"fontSize":"18px","fontWeight":"600","color":"#1a1a1a"}'>登录账号设置</div>
            </div>

            <el-form-item :style='{"marginBottom":"20px"}' label="申请账号" prop="shenqingzhanghao">
              <el-input 
                v-model="ruleForm.shenqingzhanghao" 
                placeholder="审核通过后用于农商后台登录" 
                clearable
                prefix-icon="el-icon-s-custom"
              ></el-input>
            </el-form-item>

            <el-form-item :style='{"marginBottom":"20px"}' label="申请密码" prop="shenqingmima">
              <el-input 
                v-model="ruleForm.shenqingmima" 
                placeholder="请输入密码（至少6位）" 
                clearable 
                show-password
                prefix-icon="el-icon-key"
              ></el-input>
            </el-form-item>
          </div>

          <!-- 操作按钮 -->
          <el-form-item :style='{"textAlign":"center","paddingTop":"10px"}'>
            <el-button
              :style='{"border":"0","cursor":"pointer","padding":"0 50px","margin":"0 15px 0 0","outline":"none","color":"rgba(255, 255, 255, 1)","borderRadius":"8px","background":"linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%)","width":"auto","lineHeight":"48px","fontSize":"16px","fontWeight":"600","height":"48px","boxShadow":"0 4px 16px rgba(46, 125, 50, 0.4)","transition":"all 0.3s ease"}'
              type="primary"
              @click="onSubmit"
            >
              <i class="el-icon-check" style="margin-right: 8px;"></i>提交申请
            </el-button>
            <el-button
              :style='{"border":"1px solid #d0d0d0","cursor":"pointer","padding":"0 50px","margin":"0","outline":"none","color":"#666","borderRadius":"8px","background":"#fff","width":"auto","lineHeight":"48px","fontSize":"16px","height":"48px","transition":"all 0.3s ease"}'
              @click="back"
            >
              <i class="el-icon-back" style="margin-right: 8px;"></i>返回
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      baseUrl: this.$config.baseUrl,
      ruleForm: {
        qiyemingcheng: '',
        lianxiren: '',
        lianxidianhua: '',
        ruzhuliyou: '',
        yingyezhizhao: '',
        shenqingzhanghao: '',
        shenqingmima: ''
      },
      rules: {
        qiyemingcheng: [{ required: true, message: '农商/企业名称不能为空', trigger: 'blur' }],
        lianxiren: [{ required: true, message: '联系人不能为空', trigger: 'blur' }],
        lianxidianhua: [{ required: true, message: '联系电话不能为空', trigger: 'blur' }, { validator: this.$validate.isMobile, trigger: 'blur' }],
        ruzhuliyou: [{ required: true, message: '入驻理由不能为空', trigger: 'blur' }, { min: 10, message: '入驻理由至少10个字符', trigger: 'blur' }],
        shenqingzhanghao: [{ required: true, message: '申请账号不能为空', trigger: 'blur' }],
        shenqingmima: [{ required: true, message: '申请密码不能为空', trigger: 'blur' }, { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }]
      }
    }
  },
  created() {
    // 检查用户是否已登录
    if (!localStorage.getItem('frontToken')) {
      this.$message.error('请先登录');
      this.$router.push('/login');
    }
  },
  methods: {
    yingyezhizhaoUploadChange(fileUrls) {
      this.ruleForm.yingyezhizhao = fileUrls
    },
    onSubmit() {
      // 再次检查用户是否已登录
      if (!localStorage.getItem('frontToken')) {
        this.$message.error('请先登录');
        this.$router.push('/login');
        return;
      }
      this.$refs['ruleForm'].validate((valid) => {
        if (!valid) return
        // 确保 userid 有效后再提交
        const userid = localStorage.getItem('frontUserid')
        if (!userid) {
          this.$message.error('请先登录后再提交申请')
          this.$router.push('/login')
          return
        }
        this.ruleForm.userid = Number(userid)
        this.$http.post('qiyeruzhu/apply', this.ruleForm).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message.success(data.msg || '提交成功')
            this.$router.push({ path: '/index/home' })
          } else {
            this.$message.error((data && data.msg) || '提交失败')
          }
        })
      })
    },
    back() {
      this.$router.back()
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
// 按钮悬停效果
.el-button {
  transition: all 0.3s ease;
  
  &:hover:not(:disabled) {
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(46, 125, 50, 0.5) !important;
  }
  
  &:active:not(:disabled) {
    transform: translateY(0);
  }
}

// 输入框样式优化
::v-deep .el-input {
  .el-input__inner {
    border: 1px solid #e0e0e0;
    border-radius: 8px;
    height: 44px;
    line-height: 44px;
    padding: 0 15px;
    font-size: 14px;
    transition: all 0.3s ease;
    
    &:focus {
      border-color: #2E7D32;
      box-shadow: 0 0 0 2px rgba(46, 125, 50, 0.1);
    }
    
    &:hover {
      border-color: #a0a0a0;
    }
  }
  
  &.el-input--prefix {
    .el-input__inner {
      padding-left: 40px;
    }
  }
  
  .el-input__prefix {
    color: #2E7D32;
    left: 10px;
  }
}

// 表单标签样式
::v-deep .el-form-item__label {
  color: #555;
  font-weight: 600;
  font-size: 14px;
}

// 表单验证提示
::v-deep .el-form-item.is-error {
  .el-input__inner {
    border-color: #f56c6c !important;
  }
}

// 卡片悬停效果
div[style*="border"] {
  transition: all 0.3s ease;
  
  &:hover {
    box-shadow: 0 4px 16px rgba(46, 125, 50, 0.1) !important;
    transform: translateY(-2px) !important;
  }
}

// 文件上传组件样式优化
::v-deep .upload-container {
  .el-upload--picture-card {
    border: 2px dashed #d0d0d0;
    border-radius: 12px;
    width: 140px;
    height: 140px;
    line-height: 140px;
    transition: all 0.3s ease;
    
    &:hover {
      border-color: #2E7D32;
      color: #2E7D32;
      background: rgba(46, 125, 50, 0.05);
    }
  }
}
</style>
