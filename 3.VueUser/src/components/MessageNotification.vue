<template>
  <div class="message-notification">
    <el-dropdown trigger="click" @command="handleCommand">
      <div class="notification-icon" @click="getMessages">
        <i class="el-icon-bell"></i>
        <span v-if="unreadCount > 0" class="unread-badge">{{ unreadCount }}</span>
      </div>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item v-if="messages.length === 0" disabled>
          暂无消息
        </el-dropdown-item>
        <el-dropdown-item
          v-for="message in messages"
          :key="message.id"
          :command="message.id"
          :class="{ 'unread': message.status === '未读' }"
        >
          <div class="message-item">
            <div class="message-title">{{ message.title }}</div>
            <div class="message-content">{{ message.content }}</div>
            <div class="message-time">{{ formatTime(message.addtime) }}</div>
          </div>
        </el-dropdown-item>
        <el-dropdown-item divided @click="markAllRead">
          标记全部已读
        </el-dropdown-item>
        <el-dropdown-item @click="goToMessageCenter">
          查看全部消息
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  data() {
    return {
      messages: [],
      unreadCount: 0,
      pollTimer: null,
      previousToken: localStorage.getItem('frontToken')
    }
  },
  mounted() {
    // 监听 localStorage 变化（退出登录时 Token 被清除）
    window.addEventListener('storage', this.handleStorageChange)
    const currentToken = localStorage.getItem('frontToken')
    if (currentToken && currentToken !== 'null' && currentToken !== 'undefined') {
      this.getMessages()
      this.startPolling()
    }
  },
  beforeDestroy() {
    this.stopPolling()
    window.removeEventListener('storage', this.handleStorageChange)
  },
  methods: {
    handleStorageChange(e) {
      // localStorage.clear() 时所有 key 都会触发，这里只处理 Token 变化
      if (e.key === 'frontToken' || e.key === null) {
        const currentToken = localStorage.getItem('frontToken')
        if (!currentToken || currentToken === 'null' || currentToken === 'undefined') {
          this.stopPolling()
          this.messages = []
          this.unreadCount = 0
        }
      }
    },
    // 启动轮询：每 10 秒刷新一次消息
    startPolling() {
      this.stopPolling()
      const userid = localStorage.getItem('frontUserid')
      if (!userid) return
      this.pollTimer = setInterval(() => {
        const uid = localStorage.getItem('frontUserid')
        if (uid) {
          this.getMessages()
        }
      }, 10000)
    },
    stopPolling() {
      if (this.pollTimer) {
        clearInterval(this.pollTimer)
        this.pollTimer = null
      }
    },
    getMessages() {
      const userid = localStorage.getItem('frontUserid')
      if (!userid) {
        this.messages = []
        this.unreadCount = 0
        this.stopPolling()
        return
      }

      this.$http.get('/message/list', {
        params: { userid: userid }
      }).then(res => {
        if (res.data.code === 0) {
          const newMessages = (res.data.page.list || []).slice(0, 10)
          this.unreadCount = newMessages.filter(m => m.status === '未读').length
          if (JSON.stringify(newMessages) !== JSON.stringify(this.messages)) {
            this.messages = newMessages
          }
        }
      })
    },
    handleCommand(id) {
      this.$http.post('/message/update', {
        id: id,
        status: '已读'
      }).then(res => {
        if (res.data.code === 0) {
          const message = this.messages.find(m => m.id == id)
          if (message) {
            message.status = '已读'
            this.unreadCount = Math.max(0, this.unreadCount - 1)
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
          this.unreadCount = 0
        }
      }).catch(err => {
        console.error('标记全部已读失败:', err)
      })
    },
    goToMessageCenter() {
      this.$router.push('/index/message')
    },
    formatTime(time) {
      if (!time) return ''
      const date = new Date(time)
      return date.toLocaleString()
    }
  }
}
</script>

<style scoped>
.message-notification {
  position: relative;
}

.notification-icon {
  position: relative;
  cursor: pointer;
  font-size: 20px;
  color: #666;
  padding: 0 10px;

  &:hover {
    color: #667eea;
  }
}

.unread-badge {
  position: absolute;
  top: -5px;
  right: 5px;
  background: #ff4d4f;
  color: white;
  font-size: 12px;
  border-radius: 50%;
  width: 18px;
  height: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.message-item {
  max-width: 300px;
  padding: 10px;
  border-bottom: 1px solid #f0f0f0;

  &:last-child {
    border-bottom: none;
  }
}

.message-title {
  font-weight: 600;
  margin-bottom: 5px;
  color: #333;
}

.message-content {
  font-size: 14px;
  color: #666;
  margin-bottom: 5px;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.message-time {
  font-size: 12px;
  color: #999;
}

.unread {
  background: #f5f7fa;
}
</style>
