<template>
  <div class="page">
    <!-- 顶部导航栏 -->
    <div class="nav-bar">
      <button class="back-btn" @click="goBack">&larr; 返回</button>
      <h2>我的对接申请</h2>
      <div class="nav-actions">
        <button class="refresh-btn" @click="loadApplications">刷新</button>
      </div>
    </div>
    
    <!-- 状态筛选 -->
    <div class="filter-bar">
      <div class="filter-options">
        <button 
          v-for="filter in filters" 
          :key="filter.value"
          :class="['filter-btn', { active: selectedFilter === filter.value }]"
          @click="selectFilter(filter.value)"
        >
          {{ filter.label }}
        </button>
      </div>
    </div>
    
    <!-- 申请列表 -->
    <div class="application-list">
      <div 
        v-for="app in filteredApplications" 
        :key="app.id"
        class="application-item"
        @click="viewDetails(app)"
      >
        <div class="application-header">
          <h3 class="project-name">{{ app.xiangmumingcheng }}</h3>
          <span :class="['status', getStatusClass(app.sfsh)]">{{ getStatusText(app.sfsh) }}</span>
        </div>
        <div class="application-info">
          <div class="info-row">
            <span class="info-label">申请时间：</span>
            <span class="info-value">{{ formatDate(app.shenqingriqi) }}</span>
          </div>
          <div class="info-row">
            <span class="info-label">申请说明：</span>
            <span class="info-value">{{ truncate(app.shenqingshuoming, 50) }}</span>
          </div>
          <div v-if="app.lianxidianhua" class="info-row">
            <span class="info-label">联系方式：</span>
            <span class="info-value">{{ app.lianxidianhua }}</span>
          </div>
          <div v-if="app.shhf" class="review-comment">
            <div class="comment-header">
              <span class="comment-title">审核回复</span>
              <span class="comment-date">{{ formatDate(app.shhfTime) }}</span>
            </div>
            <div class="comment-content">{{ app.shhf }}</div>
          </div>
        </div>
        <div class="application-actions">
          <button class="view-btn" @click.stop="viewProject(app.xiangmubianhao)">
            查看项目
          </button>
          <button 
            class="reapply-btn" 
            v-if="app.sfsh === '已拒绝'"
            @click.stop="reapply(app)"
          >
            重新申请
          </button>
        </div>
      </div>
    </div>
    
    <!-- 空状态 -->
    <div v-if="filteredApplications.length === 0" class="empty">
      <div class="empty-icon">📋</div>
      <p>{{ selectedFilter === 'all' ? '暂无对接申请' : '暂无' + getStatusText(selectedFilter) + '的申请' }}</p>
      <button class="add-btn" @click="goToProjects">去浏览项目</button>
    </div>
    
    <!-- 加载状态 -->
    <div class="loading" v-if="loading">
      <div class="loading-spinner"></div>
      <p>加载中...</p>
    </div>
    
    <!-- 详情弹窗 -->
    <div class="dialog" v-if="showDetail">
      <div class="dialog-content">
        <div class="dialog-header">
          <h3>申请详情</h3>
          <button class="close-btn" @click="showDetail = false">&times;</button>
        </div>
        <div class="detail-info" v-if="selectedApplication">
          <div class="detail-item">
            <span class="detail-label">项目名称：</span>
            <span class="detail-value">{{ selectedApplication.xiangmumingcheng }}</span>
          </div>
          <div class="detail-item">
            <span class="detail-label">申请时间：</span>
            <span class="detail-value">{{ formatDate(selectedApplication.shenqingriqi) }}</span>
          </div>
          <div class="detail-item">
            <span class="detail-label">申请说明：</span>
            <span class="detail-value">{{ selectedApplication.shenqingshuoming }}</span>
          </div>
          <div class="detail-item" v-if="selectedApplication.lianxidianhua">
            <span class="detail-label">联系方式：</span>
            <span class="detail-value">{{ selectedApplication.lianxidianhua }}</span>
          </div>
          <div class="detail-item">
            <span class="detail-label">申请状态：</span>
            <span :class="['status', getStatusClass(selectedApplication.sfsh)]">
              {{ getStatusText(selectedApplication.sfsh) }}
            </span>
          </div>
          <div class="detail-item" v-if="selectedApplication.shhf">
            <span class="detail-label">审核回复：</span>
            <div class="detail-value comment-box">{{ selectedApplication.shhf }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      applications: [],
      loading: false,
      selectedFilter: 'all',
      showDetail: false,
      selectedApplication: null,
      filters: [
        { label: '全部', value: 'all' },
        { label: '待审核', value: '待审核' },
        { label: '已通过', value: '已审核' },
        { label: '已拒绝', value: '已拒绝' }
      ]
    }
  },
  computed: {
    filteredApplications() {
      if (this.selectedFilter === 'all') {
        return this.applications
      }
      return this.applications.filter(app => app.sfsh === this.selectedFilter)
    }
  },
  mounted() {
    this.loadApplications()
  },
  methods: {
    loadApplications() {
      // 检查用户是否登录
      const userInfo = localStorage.getItem('userInfo')
      if (!userInfo) {
        this.$router.push('/login')
        return
      }
      
      const user = JSON.parse(userInfo)
      this.loading = true
      this.$http.get('/duijieshenqing/list', {
        params: {
          userid: user.id
        }
      }).then(res => {
        if (res.data.code === 0) {
          this.applications = res.data.data.list
        }
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    selectFilter(filter) {
      this.selectedFilter = filter
    },
    getStatusText(status) {
      switch (status) {
        case '待审核':
          return '待审核'
        case '已审核':
          return '已通过'
        case '已拒绝':
          return '已拒绝'
        default:
          return status
      }
    },
    getStatusClass(status) {
      switch (status) {
        case '待审核':
          return 'pending'
        case '已审核':
          return 'approved'
        case '已拒绝':
          return 'rejected'
        default:
          return ''
      }
    },
    formatDate(date) {
      if (!date) return ''
      return new Date(date).toLocaleString()
    },
    truncate(str, length) {
      if (!str) return ''
      return str.length > length ? str.substring(0, length) + '...' : str
    },
    goToProjects() {
      this.$router.push('/index/bangfuxiangmu')
    },
    goBack() {
      if (window.history.length > 1) {
        window.history.back()
      } else {
        this.$router.push('/index')
      }
    },
    viewDetails(app) {
      this.selectedApplication = app
      this.showDetail = true
    },
    viewProject(projectId) {
      // 这里可以跳转到项目详情页
      // 暂时跳转到项目列表
      this.$router.push('/index/bangfuxiangmu')
    },
    reapply(app) {
      // 跳转到项目详情页重新申请
      this.$router.push(`/index/bangfuxiangmuDetail?id=${app.xiangmubianhao}`)
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

.refresh-btn {
  background: none;
  border: none;
  font-size: 14px;
  cursor: pointer;
  padding: 5px 10px;
  color: #4CAF50;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.refresh-btn:hover {
  background-color: #e8f5e8;
}

/* 筛选栏 */
.filter-bar {
  background-color: #fff;
  padding: 15px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  margin-bottom: 15px;
}

.filter-options {
  display: flex;
  gap: 10px;
  overflow-x: auto;
  padding-bottom: 5px;
}

.filter-btn {
  padding: 8px 16px;
  border: 1px solid #ddd;
  background-color: #fff;
  border-radius: 20px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.3s;
  white-space: nowrap;
}

.filter-btn:hover {
  border-color: #4CAF50;
  color: #4CAF50;
}

.filter-btn.active {
  background-color: #4CAF50;
  color: white;
  border-color: #4CAF50;
}

/* 申请列表 */
.application-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
  padding: 0 15px;
}

.application-item {
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  overflow: hidden;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
}

.application-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0,0,0,0.15);
}

.application-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  border-bottom: 1px solid #eee;
  background-color: #fafafa;
}

.project-name {
  font-size: 16px;
  font-weight: bold;
  margin: 0;
  color: #333;
  flex: 1;
  line-height: 1.3;
}

.status {
  padding: 4px 12px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: bold;
  white-space: nowrap;
}

.status.pending {
  background-color: #fff3cd;
  color: #856404;
}

.status.approved {
  background-color: #d4edda;
  color: #155724;
}

.status.rejected {
  background-color: #f8d7da;
  color: #721c24;
}

.application-info {
  padding: 15px;
}

.info-row {
  display: flex;
  margin-bottom: 10px;
  align-items: flex-start;
}

.info-label {
  font-size: 14px;
  font-weight: 500;
  color: #333;
  min-width: 90px;
  flex-shrink: 0;
}

.info-value {
  font-size: 14px;
  color: #666;
  flex: 1;
  line-height: 1.4;
}

.review-comment {
  margin-top: 12px;
  padding: 12px;
  background-color: #f8f9fa;
  border-radius: 6px;
  border-left: 3px solid #4CAF50;
}

.comment-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
}

.comment-title {
  font-size: 14px;
  font-weight: bold;
  color: #333;
}

.comment-date {
  font-size: 12px;
  color: #999;
}

.comment-content {
  font-size: 14px;
  color: #666;
  line-height: 1.4;
}

.application-actions {
  display: flex;
  gap: 10px;
  padding: 15px;
  border-top: 1px solid #eee;
  background-color: #fafafa;
}

.view-btn,
.reapply-btn {
  flex: 1;
  padding: 8px 16px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.3s;
  text-align: center;
}

.view-btn {
  background-color: #f0f0f0;
  color: #333;
}

.view-btn:hover {
  background-color: #e0e0e0;
  border-color: #ccc;
}

.reapply-btn {
  background-color: #4CAF50;
  color: white;
  border-color: #4CAF50;
}

.reapply-btn:hover {
  background-color: #45a049;
  transform: translateY(-1px);
}

/* 空状态 */
.empty {
  text-align: center;
  padding: 60px 15px;
  color: #999;
  background-color: #fff;
  border-radius: 8px;
  margin: 15px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.empty-icon {
  font-size: 48px;
  margin-bottom: 15px;
}

.empty p {
  margin: 0 0 20px 0;
  font-size: 16px;
}

.add-btn {
  background-color: #4CAF50;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  font-size: 14px;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s;
}

.add-btn:hover {
  background-color: #45a049;
  transform: translateY(-1px);
  box-shadow: 0 2px 4px rgba(76, 175, 80, 0.3);
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

.detail-info {
  padding: 20px;
}

.detail-item {
  margin-bottom: 15px;
  display: flex;
  align-items: flex-start;
}

.detail-label {
  font-size: 14px;
  font-weight: 500;
  color: #333;
  min-width: 90px;
  flex-shrink: 0;
}

.detail-value {
  font-size: 14px;
  color: #666;
  flex: 1;
  line-height: 1.4;
}

.comment-box {
  padding: 10px;
  background-color: #f8f9fa;
  border-radius: 4px;
  border-left: 3px solid #4CAF50;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .application-actions {
    flex-direction: column;
  }
  
  .info-row {
    flex-direction: column;
    align-items: flex-start;
  }
  
  .info-label {
    margin-bottom: 4px;
  }
  
  .filter-options {
    padding-bottom: 10px;
  }
}
</style>
