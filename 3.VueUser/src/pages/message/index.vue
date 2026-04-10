<template>
  <div class="message-center">
    <div class="message-header">
      <h1>消息中心</h1>
      <el-button type="primary" @click="markAllRead">标记全部已读</el-button>
    </div>
    
    <div class="message-filter">
      <el-radio-group v-model="filterType">
        <el-radio-button label="all">全部</el-radio-button>
        <el-radio-button label="unread">未读</el-radio-button>
        <el-radio-button label="system">系统通知</el-radio-button>
        <el-radio-button label="review">审核通知</el-radio-button>
        <el-radio-button label="docking">对接通知</el-radio-button>
      </el-radio-group>
    </div>
    
    <div class="message-list">
      <el-empty v-if="messages.length === 0" description="暂无消息" />
      <el-card
        v-for="message in messages"
        :key="message.id"
        :class="{ 'unread': message.status === '未读' }"
      >
        <div class="message-card">
          <div class="message-card-header">
            <h3>{{ message.title }}</h3>
            <span class="message-type">{{ getTypeText(message.type) }}</span>
          </div>
          <div class="message-card-content">
            {{ message.content }}
          </div>
          <div class="message-card-footer">
            <span class="message-time">{{ formatTime(message.addtime) }}</span>
            <el-button
              v-if="message.status === '未读'"
              type="text"
              @click="markAsRead(message.id)"
            >
              标记已读
            </el-button>
          </div>
        </div>
      </el-card>
    </div>
    
    <div class="message-pagination">
      <el-pagination
        v-if="total > 0"
        layout="prev, pager, next"
        :total="total"
        :page-size="pageSize"
        :current-page="currentPage"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      messages: [],
      total: 0,
      currentPage: 1,
      pageSize: 10,
      filterType: 'all'
    }
  },
  mounted() {
    this.getMessages()
  },
  methods: {
    getMessages() {
      const userid = localStorage.getItem('frontUserid')
      if (!userid) return

      const params = {
        userid: userid,
        page: this.currentPage,
        limit: this.pageSize
      }

      if (this.filterType === 'unread') {
        params.status = '未读'
      } else if (this.filterType !== 'all') {
        params.type = this.filterType
      }

      this.$http.get('/message/list', { params }).then(res => {
        if (res.data.code === 0) {
          this.messages = res.data.page.list || []
          this.total = res.data.page.totalCount || 0
        }
      }).catch(err => {
        console.error('获取消息失败:', err)
      })
    },
    markAsRead(id) {
      this.$http.post('/message/update', {
        id: id,
        status: '已读'
      }).then(res => {
        if (res.data.code === 0) {
          const message = this.messages.find(m => m.id === id)
          if (message) {
            message.status = '已读'
          }
        }
      }).catch(err => {
        console.error('标记消息已读失败:', err)
      })
    },
    markAllRead() {
      const unreadMessages = this.messages.filter(m => m.status === '未读')
      const ids = unreadMessages.map(m => m.id)
      if (ids.length === 0) return

      this.$http.post('/message/update', {
        ids: ids,
        status: '已读'
      }).then(res => {
        if (res.data.code === 0) {
          unreadMessages.forEach(m => {
            m.status = '已读'
          })
        }
      }).catch(err => {
        console.error('标记全部已读失败:', err)
      })
    },
    handleCurrentChange(page) {
      this.currentPage = page
      this.getMessages()
    },
    getTypeText(type) {
      const typeMap = {
        '审核通知': '审核通知',
        'system': '系统通知',
        'docking': '对接通知'
      }
      return typeMap[type] || type
    },
    formatTime(time) {
      if (!time) return ''
      const date = new Date(time)
      return date.toLocaleString()
    }
  },
  watch: {
    filterType() {
      this.currentPage = 1
      this.getMessages()
    }
  }
}
</script>

<style scoped>
.message-center {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.message-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #e8e8e8;
}

.message-header h1 {
  font-size: 24px;
  font-weight: 600;
  color: #333;
  margin: 0;
}

.message-filter {
  margin-bottom: 20px;
}

.message-list {
  margin-bottom: 20px;
}

.message-card {
  padding: 15px;
}

.message-card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.message-card-header h3 {
  font-size: 18px;
  font-weight: 600;
  color: #333;
  margin: 0;
}

.message-type {
  font-size: 12px;
  padding: 2px 8px;
  border-radius: 10px;
  background: #f0f0f0;
  color: #666;
}

.message-card-content {
  font-size: 16px;
  color: #666;
  line-height: 1.5;
  margin-bottom: 15px;
  white-space: pre-wrap;
}

.message-card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 14px;
  color: #999;
}

.message-time {
  font-size: 12px;
}

.unread {
  border-left: 4px solid #667eea;
  box-shadow: 0 2px 8px rgba(102, 126, 234, 0.1);
}

.message-pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>
