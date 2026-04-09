<template>
  <div class="page">
    <!-- 顶部导航栏 -->
    <div class="nav-bar">
      <button class="back-btn" @click="goBack">&larr; 返回</button>
      <h2>项目详情</h2>
      <div class="nav-actions">
        <button class="share-btn" @click="share">分享</button>
      </div>
    </div>
    
    <div class="project-detail">
      <!-- 项目图片 -->
      <div class="project-image">
        <img :src="project.tupian || 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=agriculture%20project%20detail&image_size=landscape_16_9'" alt="项目图片" />
      </div>
      
      <!-- 项目基本信息 -->
      <div class="project-header">
        <h1 class="project-title">{{ project.xiangmumingcheng }}</h1>
        <div class="project-meta">
          <span class="project-type">{{ project.xiangmuleixing }}</span>
          <span class="project-date">{{ formatDate(project.faburiqi) }}</span>
          <span class="view-count">👁 {{ project.clicknum || 0 }} 次浏览</span>
        </div>
      </div>
      
      <!-- 项目详情内容 -->
      <div class="project-content">
        <section class="content-section">
          <h3 class="section-title">项目详情</h3>
          <div class="content-text">{{ project.xiangmuxiangqing || '暂无详细信息' }}</div>
        </section>
        
        <section class="content-section">
          <h3 class="section-title">联系方式</h3>
          <div class="contact-info">
            <div class="contact-item">
              <span class="contact-label">联系人：</span>
              <span class="contact-value">{{ project.lianxiren || '暂无' }}</span>
            </div>
            <div class="contact-item">
              <span class="contact-label">联系电话：</span>
              <span class="contact-value">{{ project.lianxidianhua || '暂无' }}</span>
            </div>
          </div>
        </section>
      </div>
      
      <!-- 操作按钮 -->
      <div class="action-buttons">
        <button class="contact-btn" @click="contact" :disabled="!project.lianxidianhua">
          📞 联系我们
        </button>
        <button class="apply-btn" @click="showApplyDialog">
          📋 发起对接
        </button>
      </div>
    </div>
    
    <!-- 对接申请弹窗 -->
    <div class="dialog" v-if="showApply">
      <div class="dialog-content">
        <div class="dialog-header">
          <h3>发起对接申请</h3>
          <button class="close-btn" @click="showApply = false">&times;</button>
        </div>
        <div class="form-group">
          <label>项目名称</label>
          <input type="text" v-model="project.xiangmumingcheng" disabled />
        </div>
        <div class="form-group">
          <label>申请说明 <span class="required">*</span></label>
          <textarea 
            v-model="applyForm.shenqingshuoming" 
            placeholder="请详细描述您的对接需求和合作意向..." 
            rows="4"
            :class="{ 'error': !applyForm.shenqingshuoming && submitAttempted }"
          ></textarea>
          <span v-if="!applyForm.shenqingshuoming && submitAttempted" class="error-message">请输入申请说明</span>
        </div>
        <div class="form-group">
          <label>联系方式</label>
          <input 
            type="text" 
            v-model="applyForm.contactPhone" 
            placeholder="请留下您的联系电话"
          />
        </div>
        <div class="dialog-buttons">
          <button class="cancel-btn" @click="showApply = false">取消</button>
          <button class="submit-btn" @click="submitApply" :disabled="submitting">
            {{ submitting ? '提交中...' : '提交申请' }}
          </button>
        </div>
      </div>
    </div>
    
    <!-- 加载状态 -->
    <div class="loading" v-if="loading">
      <div class="loading-spinner"></div>
      <p>加载中...</p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      project: {},
      loading: false,
      showApply: false,
      submitting: false,
      submitAttempted: false,
      applyForm: {
        shenqingshuoming: '',
        contactPhone: ''
      }
    }
  },
  mounted() {
    this.loadProjectDetail()
  },
  methods: {
    loadProjectDetail() {
      const id = this.$route.query.id || this.$route.params.id
      if (!id) {
        alert('项目ID不存在')
        this.goBack()
        return
      }
      
      this.loading = true
      this.$http.get(`/bangfuxiangmu/detail/${id}`).then(res => {
        if (res.data.code === 0) {
          this.project = res.data.data
          // 增加点击次数
          this.incrementViewCount(id)
        } else {
          alert('项目不存在')
          this.goBack()
        }
        this.loading = false
      }).catch(() => {
        alert('网络错误，请稍后重试')
        this.loading = false
      })
    },
    incrementViewCount(id) {
      this.$http.get(`/bangfuxiangmu/updateClick/${id}`).catch(() => {
        // 忽略错误
      })
    },
    showApplyDialog() {
      // 检查用户是否登录
      const userInfo = localStorage.getItem('userInfo')
      if (!userInfo) {
        this.$router.push('/login')
        return
      }
      
      // 初始化表单
      this.applyForm = {
        shenqingshuoming: '',
        contactPhone: ''
      }
      this.submitAttempted = false
      this.showApply = true
    },
    submitApply() {
      this.submitAttempted = true
      
      if (!this.applyForm.shenqingshuoming) {
        return
      }
      
      this.submitting = true
      const userInfo = JSON.parse(localStorage.getItem('userInfo'))
      
      this.$http.post('/duijieshenqing/add', {
        xiangmubianhao: this.project.xiangmubianhao || '项目编号',
        xiangmumingcheng: this.project.xiangmumingcheng,
        shenqingrenzhanghao: userInfo.username,
        shenqingrenxingming: userInfo.nickname,
        shenqingshuoming: this.applyForm.shenqingshuoming,
        lianxidianhua: this.applyForm.contactPhone || userInfo.phone || '',
        shenqingriqi: new Date(),
        userid: userInfo.id
      }).then(res => {
        if (res.data.code === 0) {
          alert('申请提交成功！我们会尽快与您联系。')
          this.showApply = false
          // 跳转到我的对接申请页面
          setTimeout(() => {
            this.$router.push('/index/duijieshenqing')
          }, 1000)
        } else {
          alert('申请提交失败：' + res.data.msg)
        }
        this.submitting = false
      }).catch(() => {
        alert('网络错误，请稍后重试')
        this.submitting = false
      })
    },
    contact() {
      if (this.project.lianxidianhua) {
        if (confirm(`是否拨打 ${this.project.lianxidianhua}？`)) {
          window.location.href = `tel:${this.project.lianxidianhua}`
        }
      }
    },
    goBack() {
      if (window.history.length > 1) {
        window.history.back()
      } else {
        this.$router.push('/index/bangfuxiangmu')
      }
    },
    share() {
      if (navigator.share) {
        navigator.share({
          title: this.project.xiangmumingcheng,
          text: '查看这个帮扶项目',
          url: window.location.href
        }).catch(() => {
          // 分享失败
        })
      } else {
        // 复制链接
        const url = window.location.href
        navigator.clipboard.writeText(url).then(() => {
          alert('链接已复制到剪贴板')
        }).catch(() => {
          alert('复制失败，请手动复制链接')
        })
      }
    },
    formatDate(date) {
      if (!date) return ''
      return new Date(date).toLocaleDateString()
    }
  }
}
</script>

<style scoped>
.page {
  background-color: #f5f5f5;
  min-height: 100vh;
  padding-top: 60px;
}

/* 导航栏 */
.nav-bar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  background-color: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 15px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  z-index: 100;
}

.nav-bar h2 {
  font-size: 16px;
  font-weight: bold;
  margin: 0;
  color: #333;
}

.back-btn {
  background: none;
  border: none;
  font-size: 18px;
  cursor: pointer;
  padding: 5px 10px;
  color: #333;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.back-btn:hover {
  background-color: #f0f0f0;
}

.share-btn {
  background: none;
  border: none;
  font-size: 14px;
  cursor: pointer;
  padding: 5px 10px;
  color: #4CAF50;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.share-btn:hover {
  background-color: #e8f5e8;
}

/* 项目详情 */
.project-detail {
  background-color: #fff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  margin: 15px;
}

.project-image {
  width: 100%;
  height: 250px;
  position: relative;
}

.project-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.project-header {
  padding: 20px;
  border-bottom: 1px solid #eee;
}

.project-title {
  font-size: 20px;
  font-weight: bold;
  margin: 0 0 12px 0;
  color: #333;
  line-height: 1.3;
}

.project-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 0;
  font-size: 12px;
  color: #666;
}

.project-type {
  background-color: #e8f5e8;
  color: #2e7d32;
  padding: 2px 8px;
  border-radius: 10px;
  font-weight: 500;
}

.view-count {
  display: flex;
  align-items: center;
  gap: 4px;
}

.project-content {
  padding: 20px;
}

.content-section {
  margin-bottom: 25px;
}

.section-title {
  font-size: 16px;
  font-weight: bold;
  margin: 0 0 12px 0;
  color: #333;
  padding-bottom: 8px;
  border-bottom: 1px solid #f0f0f0;
}

.content-text {
  font-size: 14px;
  line-height: 1.6;
  color: #666;
  white-space: pre-wrap;
}

.contact-info {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.contact-item {
  display: flex;
  align-items: flex-start;
  gap: 10px;
}

.contact-label {
  font-size: 14px;
  font-weight: 500;
  color: #333;
  min-width: 80px;
}

.contact-value {
  font-size: 14px;
  color: #666;
  flex: 1;
}

/* 操作按钮 */
.action-buttons {
  display: flex;
  padding: 20px;
  border-top: 1px solid #eee;
  gap: 10px;
  background-color: #fafafa;
}

.contact-btn,
.apply-btn {
  flex: 1;
  padding: 14px;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.contact-btn {
  background-color: #f0f0f0;
  color: #333;
  border: 1px solid #ddd;
}

.contact-btn:hover:not(:disabled) {
  background-color: #e0e0e0;
}

.contact-btn:disabled {
  background-color: #f5f5f5;
  color: #ccc;
  cursor: not-allowed;
  border-color: #eee;
}

.apply-btn {
  background-color: #4CAF50;
  color: white;
}

.apply-btn:hover {
  background-color: #45a049;
  transform: translateY(-1px);
  box-shadow: 0 2px 4px rgba(76, 175, 80, 0.3);
}

/* 弹窗样式 */
.dialog {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0,0,0,0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.dialog-content {
  background-color: #fff;
  border-radius: 8px;
  width: 90%;
  max-width: 400px;
  max-height: 80vh;
  overflow-y: auto;
  box-shadow: 0 4px 12px rgba(0,0,0,0.15);
}

.dialog-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  border-bottom: 1px solid #eee;
}

.dialog-header h3 {
  margin: 0;
  color: #333;
  font-size: 16px;
  font-weight: bold;
}

.close-btn {
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
  color: #999;
  padding: 0;
  width: 24px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.close-btn:hover {
  background-color: #f0f0f0;
  color: #666;
}

.form-group {
  padding: 0 20px 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-size: 14px;
  font-weight: 500;
  color: #333;
}

.required {
  color: #f44336;
  font-size: 12px;
}

.form-group input,
.form-group textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
  box-sizing: border-box;
  transition: border-color 0.3s;
}

.form-group input:focus,
.form-group textarea:focus {
  outline: none;
  border-color: #4CAF50;
  box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.1);
}

.form-group textarea {
  resize: vertical;
  min-height: 100px;
}

.form-group input:disabled {
  background-color: #f5f5f5;
  color: #999;
  cursor: not-allowed;
}

.form-group input.error,
.form-group textarea.error {
  border-color: #f44336;
}

.error-message {
  color: #f44336;
  font-size: 12px;
  margin-top: 4px;
  display: block;
}

.dialog-buttons {
  display: flex;
  gap: 10px;
  padding: 0 20px 20px;
}

.cancel-btn,
.submit-btn {
  flex: 1;
  padding: 12px;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s;
}

.cancel-btn {
  background-color: #f0f0f0;
  color: #333;
  border: 1px solid #ddd;
}

.cancel-btn:hover {
  background-color: #e0e0e0;
}

.submit-btn {
  background-color: #4CAF50;
  color: white;
}

.submit-btn:hover:not(:disabled) {
  background-color: #45a049;
  transform: translateY(-1px);
}

.submit-btn:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

/* 加载状态 */
.loading {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(255,255,255,0.9);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  z-index: 2000;
}

.loading-spinner {
  width: 40px;
  height: 40px;
  border: 3px solid #f3f3f3;
  border-top: 3px solid #4CAF50;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 15px;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.loading p {
  color: #666;
  margin: 0;
  font-size: 14px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .project-image {
    height: 200px;
  }
  
  .project-header,
  .project-content {
    padding: 15px;
  }
  
  .action-buttons {
    padding: 15px;
    flex-direction: column;
  }
  
  .contact-btn,
  .apply-btn {
    width: 100%;
  }
  
  .project-meta {
    flex-direction: column;
    gap: 5px;
  }
}
</style>
