<template>
  <div class="detail-page">
    <!-- 面包屑导航 -->
    <div class="breadcrumb-wrapper">
      <el-breadcrumb :separator="'>'" class="breadcrumb">
        <el-breadcrumb-item to="/"><a>首页</a></el-breadcrumb-item>
        <el-breadcrumb-item to="/index/bangfuxiangmu"><a>帮扶对接</a></el-breadcrumb-item>
        <el-breadcrumb-item>{{ projectDetail.xiangmumingcheng || '项目详情' }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <!-- 加载中 -->
    <div v-if="loading" class="loading">
      <el-spinner type="ring" size="large"></el-spinner>
      <p>加载中...</p>
    </div>

    <!-- 项目详情 -->
    <div v-else class="content">
      <!-- 项目图片 -->
      <div class="project-images">
        <div v-if="projectDetail.tupian" class="main-image">
          <img :src="baseUrl + 'upload/' + projectDetail.tupian" alt="项目图片" />
        </div>
        <div v-else class="image-placeholder" :class="getPlaceholderClass(projectDetail.xiangmuleixing)">
          <span>{{ getPlaceholderText(projectDetail.xiangmuleixing) }}</span>
        </div>
      </div>

      <!-- 项目信息 -->
      <div class="project-info">
        <div class="project-header">
          <h1 class="project-title">{{ projectDetail.xiangmumingcheng }}</h1>
          <span class="project-type">{{ projectDetail.xiangmuleixing }}</span>
        </div>

        <div class="meta-info">
          <span class="meta-item">
            <i class="el-icon-user"></i>
            {{ projectDetail.lianxiren || projectDetail.shenqingrenxingming || '未填写' }}
          </span>
          <span class="meta-item">
            <i class="el-icon-location"></i>
            {{ projectDetail.suozaidiqu || '未填写' }}
          </span>
          <span v-if="projectDetail.jutidizhi" class="meta-item">
            <i class="el-icon-map-marker"></i>
            {{ projectDetail.jutidizhi }}
          </span>
          <span class="meta-item">
            <i class="el-icon-calendar"></i>
            {{ projectDetail.faburiqi || projectDetail.shenqingriqi }}
          </span>
          <span class="meta-item">
            <i class="el-icon-view"></i>
            {{ projectDetail.clicknum || 0 }} 次浏览
          </span>
        </div>

        <div class="project-desc">
          <h3>项目描述</h3>
          <p>{{ projectDetail.xiangmuxiangqing || projectDetail.xuqiumiaoshu || projectDetail.shenqingshuoming || '暂无描述' }}</p>
        </div>

        <!-- 申请对接按钮 -->
        <div class="action-area">
          <button class="apply-btn" @click="applyProject">申请对接</button>
        </div>
      </div>
    </div>

    <!-- 申请对接弹窗 -->
    <el-dialog title="申请对接" :visible.sync="applyDialogVisible" width="600px" :close-on-click-modal="false">
      <el-form :model="applyForm" label-width="130px">
        <el-form-item label="机构 / 个人名称" required>
          <el-input v-model="applyForm.jigoumingcheng" placeholder="企业填公司全称、个人/专家填姓名"></el-input>
        </el-form-item>
        <el-form-item label="联系电话" required>
          <el-input v-model="applyForm.lianxidianhua" placeholder="请输入需求方可直接联系的手机号"></el-input>
        </el-form-item>
        <el-form-item label="对接说明" required>
          <el-input type="textarea" v-model="applyForm.duijieshuoming" :rows="5" placeholder="至少10字，说明自身优势、为什么能做好这个帮扶"></el-input>
          <span class="word-count">已输入 {{ applyForm.duijieshuoming ? applyForm.duijieshuoming.length : 0 }} 字</span>
        </el-form-item>
        <el-form-item label="附件上传">
          <el-upload
            :action="uploadUrl"
            :headers="uploadHeaders"
            name="file"
            list-type="picture-card"
            :on-success="handleApplyImageSuccess"
            :file-list="applyImageList"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
          <div class="upload-tip">可选上传：资质证书、过往案例证明、营业执照等，增强可信度</div>
        </el-form-item>
        <el-form-item label="服务承诺" required>
          <el-checkbox v-model="applyForm.fuwuchengnuo">
            承诺提供信息真实有效，遵守平台帮扶规则
          </el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitApply" :disabled="!applyForm.fuwuchengnuo">提交申请</el-button>
          <el-button @click="applyDialogVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import config from '@/config/config'

export default {
  name: 'BangfuDetail',
  data() {
    return {
      baseUrl: config.baseUrl,
      loading: true,
      projectDetail: {},
      applyDialogVisible: false,
      applyForm: {
        xiangmuid: '',
        xiangmumingcheng: '',
        lianxidianhua: '',
        jigoumingcheng: '',
        duijieshuoming: '',
        fujian: '',
        fuwuchengnuo: false
      },
      applyImageList: [],
      uploadUrl: config.baseUrl + 'file/upload',
      uploadHeaders: {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }
    }
  },
  created() {
    this.loadProjectDetail()
  },
  methods: {
    loadProjectDetail() {
      const id = this.$route.params.id
      if (!id) {
        this.$message.error('项目ID不存在')
        this.loading = false
        return
      }
      this.loading = true
      this.projectDetail = {}
      this.$http.get('bangfuxiangmu/detail/' + id).then(res => {
        if (res.data.code === 0 && res.data.data) {
          this.projectDetail = res.data.data
        } else {
          return this.$http.get('bangfuxuqiu/detail/' + id)
        }
      }).then(res => {
        if (res && res.data && res.data.code === 0 && res.data.data) {
          this.projectDetail = res.data.data
        }
      }).catch(err => {
        console.error('加载项目详情失败:', err)
      }).finally(() => {
        this.loading = false
      })
    },
    getPlaceholderClass(type) {
      if (!type) return 'other'
      if (type.includes('技术')) return 'tech'
      if (type.includes('资金')) return 'fund'
      if (type.includes('采购')) return 'procurement'
      if (type.includes('农机')) return 'machine'
      return 'other'
    },
    getPlaceholderText(type) {
      if (!type) return '帮扶项目'
      if (type.includes('技术')) return '技术帮扶'
      if (type.includes('资金')) return '资金帮扶'
      if (type.includes('采购')) return '产品采购'
      if (type.includes('农机')) return '农机帮扶'
      return type
    },
    applyProject() {
      const uid = localStorage.getItem('frontUserid') || localStorage.getItem('sessionUserId')
      if (!uid) {
        this.$message.warning('请先登录')
        return
      }
      this.applyForm = {
        xiangmuid: this.projectDetail.id,
        xiangmumingcheng: this.projectDetail.xiangmumingcheng,
        lianxidianhua: '',
        jigoumingcheng: '',
        duijieshuoming: '',
        fujian: '',
        fuwuchengnuo: false
      }
      this.applyImageList = []
      this.applyDialogVisible = true
    },
    handleApplyImageSuccess(res) {
      if (res.code === 0) {
        this.applyForm.fujian = res.data ? res.data.url : ''
        this.$message.success('上传成功')
      } else {
        this.$message.error('上传失败')
      }
    },
    submitApply() {
      if (!this.applyForm.jigoumingcheng) {
        this.$message.warning('请输入机构/个人名称')
        return
      }
      if (!this.applyForm.lianxidianhua) {
        this.$message.warning('请输入联系电话')
        return
      }
      if (!this.applyForm.duijieshuoming) {
        this.$message.warning('请输入对接说明')
        return
      }
      if (this.applyForm.duijieshuoming.length < 10) {
        this.$message.warning('对接说明需要至少10字')
        return
      }
      if (!this.applyForm.fuwuchengnuo) {
        this.$message.warning('请勾选服务承诺')
        return
      }

      const userInfo = JSON.parse(localStorage.getItem('sessionForm') || '{}')
      const submitData = {
        shenqingbianhao: 'DJ' + Date.now(),
        xiangmuid: this.applyForm.xiangmuid,
        xiangmumingcheng: this.applyForm.xiangmumingcheng,
        shenqingrenzhanghao: userInfo.yonghuzhanghao || userInfo.nonghuzhanghao || localStorage.getItem('username') || '游客',
        shenqingrenxingming: userInfo.yonghuxingming || userInfo.nonghuxingming || '游客',
        shenqingriqi: new Date().toISOString().split('T')[0],
        shenqingshuoming: this.applyForm.duijieshuoming,
        jigoumingcheng: this.applyForm.jigoumingcheng,
        lianxidianhua: this.applyForm.lianxidianhua,
        fujian: this.applyForm.fujian,
        fuwuchengnuo: this.applyForm.fuwuchengnuo,
        sfsh: '待审核',
        userid: userInfo.id
      }

      this.$http.post('/duijieshenqing/add', submitData).then(res => {
        if (res.data.code === 0) {
          this.$message.success('申请提交成功，等待审核')
          this.applyDialogVisible = false
        } else {
          this.$message.error(res.data.msg || '申请失败')
        }
      }).catch(err => {
        console.error('申请失败:', err)
        this.$message.error('申请失败')
      })
    }
  }
}
</script>

<style scoped>
.detail-page {
  min-height: 100vh;
  background: #f5f7fa;
  padding-bottom: 60px;
}

.breadcrumb-wrapper {
  padding: 20px 30px;
  background: linear-gradient(135deg, #2E7D32, #1B5E20);
  color: #fff;
}

.breadcrumb {
  font-size: 14px;
}

.breadcrumb a {
  color: rgba(255, 255, 255, 0.8);
}

.breadcrumb a:hover {
  color: #fff;
}

.loading {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 100px 0;
}

.loading p {
  margin-top: 20px;
  color: #999;
}

.content {
  width: 90%;
  max-width: 900px;
  margin: 30px auto;
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  overflow: hidden;
}

.project-images {
  width: 100%;
  height: 400px;
  position: relative;
}

.main-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.image-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 24px;
  font-weight: 600;
}

.image-placeholder.tech {
  background: linear-gradient(135deg, #11998e, #38ef7d);
}

.image-placeholder.fund {
  background: linear-gradient(135deg, #fc4a1a, #f7b733);
}

.image-placeholder.procurement {
  background: linear-gradient(135deg, #4facfe, #00f2fe);
}

.image-placeholder.machine {
  background: linear-gradient(135deg, #a18cd1, #fbc2eb);
}

.image-placeholder.other {
  background: linear-gradient(135deg, #667eea, #764ba2);
}

.project-info {
  padding: 30px;
}

.project-header {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 20px;
}

.project-title {
  font-size: 28px;
  font-weight: 700;
  color: #1a1a1a;
  margin: 0;
  flex: 1;
  min-width: 200px;
}

.project-type {
  background: linear-gradient(135deg, #2E7D32, #1B5E20);
  color: #fff;
  padding: 8px 16px;
  border-radius: 20px;
  font-size: 14px;
  font-weight: 500;
  flex-shrink: 0;
}

.meta-info {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 1px solid #eee;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 5px;
  font-size: 14px;
  color: #666;
}

.meta-item i {
  color: #2E7D32;
}

.project-desc h3 {
  font-size: 18px;
  font-weight: 600;
  color: #333;
  margin-bottom: 15px;
}

.project-desc p {
  font-size: 15px;
  line-height: 1.8;
  color: #555;
  margin: 0;
  white-space: pre-wrap;
}

.action-area {
  margin-top: 30px;
  text-align: center;
}

.apply-btn {
  background: linear-gradient(135deg, #2E7D32, #1B5E20);
  color: #fff;
  border: none;
  padding: 15px 50px;
  border-radius: 10px;
  font-size: 18px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 16px rgba(46, 125, 50, 0.35);
}

.apply-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 24px rgba(46, 125, 50, 0.45);
}

.word-count {
  display: block;
  text-align: right;
  font-size: 12px;
  color: #999;
  margin-top: 5px;
}

.upload-tip {
  font-size: 12px;
  color: #999;
  margin-top: 5px;
}
</style>