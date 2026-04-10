<template>
  <div class="login-page">
    <div class="login-card">
      <!-- 左侧品牌区 -->
      <div class="left-side">
        <div class="brand-content">
          <div class="big-logo">🏢</div>
          <h2 class="brand-title">助农扶贫</h2>
          <p class="brand-sub">管理系统</p>
        </div>
        <div class="deco-lines">
          <div class="line line1"></div>
          <div class="line line2"></div>
          <div class="line line3"></div>
        </div>
      </div>

      <!-- 右侧登录区 -->
      <div class="right-side">
        <div class="login-header">
          <h1 class="title">欢迎登录</h1>
          <p class="sub-title">管理端 · Admin Panel</p>
        </div>

        <el-form class="form" @submit.native.prevent>
          <div class="form-item">
            <div class="input-box">
              <div class="icon-box">
                <span class="icon">👤</span>
              </div>
              <input class="input" 
                placeholder="请输入用户名" 
                v-model="rulesForm.username" 
                @keyup.enter="handleKeyPress">
            </div>
          </div>

          <div class="form-item">
            <div class="input-box">
              <div class="icon-box">
                <span class="icon">🔐</span>
              </div>
              <input class="input" 
                placeholder="请输入密码" 
                type="password"
                v-model="rulesForm.password" 
                @keyup.enter="handleKeyPress">
            </div>
          </div>

          <div class="form-item" v-if="roles.length>1">
            <div class="radio-box">
              <el-radio v-for="item in roles" v-bind:key="item.roleName" v-model="rulesForm.role" :label="item.roleName">
                {{item.roleName}}
              </el-radio>
            </div>
          </div>

          <div class="btn-box">
            <button class="btn" @click="login()">
              <span class="btn-text">立即登录</span>
            </button>
          </div>

          <div class="links">
            <span class="link back" @click="goBack">
              <span class="link-icon">←</span>
              返回用户端
            </span>
            <span class="link" @click="goToApply">
              <span class="link-icon">📝</span>
              企业入驻申请
            </span>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import menu from "@/utils/menu";
export default {
  data() {
    return {
      verifyCheck2: false,
      flag: false,
      baseUrl:this.$base.url,
      loginType: 1,
      rulesForm: {
        username: "",
        password: "",
        role: "",
      },
      menus: [],
      roles: [],
      tableName: "",
    };
  },
  mounted() {
    let menus = menu.list();
    this.menus = menus;

    for (let i = 0; i < this.menus.length; i++) {
      if (this.menus[i].hasBackLogin=='是') {
        this.roles.push(this.menus[i])
      }
    }

    if (this.roles.length > 1) {
      let adminIndex = -1;
      let nonghuIndex = -1;
      
      for (let i = 0; i < this.roles.length; i++) {
        if (this.roles[i].roleName.includes('管理员')) {
          adminIndex = i;
        }
        if (this.roles[i].roleName.includes('农商企业')) {
          nonghuIndex = i;
        }
      }
      
      if (adminIndex !== -1 && nonghuIndex !== -1 && adminIndex < nonghuIndex) {
        let temp = this.roles[adminIndex];
        this.roles[adminIndex] = this.roles[nonghuIndex];
        this.roles[nonghuIndex] = temp;
      }
      
      for (let i = 0; i < this.roles.length; i++) {
        if (this.roles[i].roleName.includes('农商企业')) {
          this.rulesForm.role = this.roles[i].roleName;
          break;
        }
      }
    }
  },
  methods: {
    handleKeyPress(event) {
      if (event.keyCode === 13 || event.key === 'Enter') {
        this.login();
      }
    },

    goBack() {
      window.location.href = 'http://localhost:8082';
    },

    goToApply() {
      window.open('http://localhost:8082/#/index/qiyeruzhuApply', '_blank');
    },

    login() {
      if (this.flag) return
      this.flag = true

      if (!this.rulesForm.username) {
        this.$message.error("请输入用户名");
        this.flag = false
        return;
      }
      if (!this.rulesForm.password) {
        this.$message.error("请输入密码");
        this.flag = false
        return;
      }
      if(this.roles.length>1) {
        if (!this.rulesForm.role) {
          this.$message.error("请选择角色");
          this.flag = false
          return;
        }

        let menus = this.menus;
        for (let i = 0; i < menus.length; i++) {
          if (menus[i].roleName == this.rulesForm.role) {
            this.tableName = menus[i].tableName;
          }
        }
      } else {
        this.tableName = this.roles[0].tableName;
        this.rulesForm.role = this.roles[0].roleName;
      }

      this.loginPost()
    },
    loginPost() {
      this.$http({
        url: `${this.tableName}/login?username=${this.rulesForm.username}&password=${this.rulesForm.password}`,
        method: "post"
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.$storage.set("Token", data.token);
          this.$storage.set("role", this.rulesForm.role);
          this.$storage.set("sessionTable", this.tableName);
          this.$storage.set("adminName", this.rulesForm.username);
          this.$router.replace({ path: "/" });
        } else {
          this.$message.error(data.msg);
          this.flag = false
        }
      }).catch((err) => {
        this.flag = false
        const msg = (err.response && err.response.data && err.response.data.msg) || err.message || "网络异常，请确认后端已启动在 http://localhost:8080";
        this.$message.error(msg);
      });
    },
  }
}
</script>

<style lang="scss" scoped>
.login-page {
  width: 100%;
  min-height: 100vh;
  background: linear-gradient(135deg, #0f172a 0%, #1e293b 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px 20px;
  box-sizing: border-box;
}

.login-card {
  width: 920px;
  background: #0f172a;
  border-radius: 20px;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5);
  display: flex;
  overflow: hidden;
  position: relative;
}

.login-card::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 3px;
  background: linear-gradient(90deg, #3b82f6, #0ea5e9, #3b82f6);
  background-size: 200% 100%;
  animation: shimmer 3s linear infinite;
  z-index: 10;
}

@keyframes shimmer {
  0% { background-position: -200% 0; }
  100% { background-position: 200% 0; }
}

/* 左侧 */
.left-side {
  width: 40%;
  background: linear-gradient(135deg, #1e3a8a 0%, #3b82f6 100%);
  padding: 50px 40px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: relative;
  overflow: hidden;
}

.left-side::before {
  content: "";
  position: absolute;
  top: -50%;
  left: -50%;
  width: 200%;
  height: 200%;
  background: radial-gradient(circle, rgba(255,255,255,0.1) 0%, transparent 60%);
  animation: pulse 4s ease-in-out infinite;
}

@keyframes pulse {
  0%, 100% { transform: scale(1); opacity: 0.5; }
  50% { transform: scale(1.1); opacity: 0.8; }
}

.brand-content {
  text-align: center;
  position: relative;
  z-index: 1;
}

.big-logo {
  font-size: 80px;
  margin-bottom: 20px;
  animation: float 3s ease-in-out infinite;
}

@keyframes float {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-10px); }
}

.brand-title {
  font-size: 36px;
  font-weight: 700;
  color: #fff;
  margin: 0 0 8px 0;
  letter-spacing: 2px;
  text-shadow: 0 4px 15px rgba(0, 0, 0, 0.3);
}

.brand-sub {
  font-size: 18px;
  color: rgba(255, 255, 255, 0.8);
  margin: 0;
  font-weight: 400;
  letter-spacing: 4px;
}

.deco-lines {
  position: absolute;
  bottom: 40px;
  display: flex;
  gap: 12px;
  z-index: 1;
}

.line {
  width: 40px;
  height: 4px;
  background: rgba(255, 255, 255, 0.5);
  border-radius: 2px;
}

.line1 { animation: linePulse 2s ease-in-out 0s infinite; }
.line2 { animation: linePulse 2s ease-in-out 0.3s infinite; }
.line3 { animation: linePulse 2s ease-in-out 0.6s infinite; }

@keyframes linePulse {
  0%, 100% { width: 40px; opacity: 0.5; }
  50% { width: 60px; opacity: 1; }
}

/* 右侧 */
.right-side {
  width: 60%;
  padding: 55px 55px 50px;
  background: #0f172a;
}

.login-header {
  text-align: center;
  margin-bottom: 40px;
}

.title {
  font-size: 32px;
  font-weight: 700;
  color: #e2e8f0;
  margin: 0 0 10px 0;
  letter-spacing: 1px;
}

.sub-title {
  font-size: 15px;
  color: #64748b;
  margin: 0;
  font-weight: 400;
  letter-spacing: 2px;
}

.form {
  width: 100%;
}

.form-item {
  margin-bottom: 22px;
}

.input-box {
  display: flex;
  align-items: center;
  background: #1e293b;
  border: 2px solid #334155;
  border-radius: 12px;
  transition: all 0.3s;
}

.input-box:focus-within {
  border-color: #3b82f6;
  box-shadow: 0 0 0 4px rgba(59, 130, 246, 0.1), inset 0 0 20px rgba(59, 130, 246, 0.05);
}

.icon-box {
  padding: 0 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-right: 1px solid #334155;
}

.icon {
  font-size: 22px;
  opacity: 0.7;
  transition: all 0.3s;
}

.input-box:focus-within .icon {
  opacity: 1;
  transform: scale(1.1);
}

.input {
  flex: 1;
  border: none;
  background: transparent;
  padding: 18px 20px;
  font-size: 16px;
  color: #e2e8f0;
  height: 58px;
  box-sizing: border-box;
  outline: none;
}

.input::placeholder {
  color: #64748b;
}

.radio-box {
  display: flex;
  justify-content: center;
  gap: 50px;
}

.radio-box ::v-deep .el-radio__input .el-radio__inner {
  background: #1e293b;
  border-color: #475569;
  width: 20px;
  height: 20px;
  transition: all 0.3s;
}

.radio-box ::v-deep .el-radio__input.is-checked .el-radio__inner {
  background: #3b82f6;
  border-color: #3b82f6;
}

.radio-box ::v-deep .el-radio__label {
  color: #94a3b8;
  font-size: 15px;
  font-weight: 500;
}

.radio-box ::v-deep .el-radio__input.is-checked+.el-radio__label {
  color: #3b82f6;
  font-size: 15px;
  font-weight: 600;
}

.btn-box {
  margin-top: 35px;
  margin-bottom: 25px;
}

.btn {
  width: 100%;
  height: 56px;
  border: none;
  border-radius: 12px;
  background: linear-gradient(135deg, #3b82f6 0%, #0ea5e9 100%);
  color: #fff;
  font-size: 17px;
  font-weight: 600;
  letter-spacing: 1px;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 10px 30px rgba(59, 130, 246, 0.3);
  position: relative;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
}

.btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 15px 40px rgba(59, 130, 246, 0.4);
}

.btn:active {
  transform: translateY(-1px);
}

.btn::before {
  content: "";
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2), transparent);
  transition: left 0.6s ease;
}

.btn:hover::before {
  left: 100%;
}

.btn-text {
  position: relative;
  z-index: 1;
}

.links {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
  padding-top: 25px;
  border-top: 1px solid #1e293b;
}

.link {
  padding: 12px 24px;
  color: #94a3b8;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  border-radius: 10px;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 8px;
}

.link:hover {
  background: #1e293b;
  color: #3b82f6;
  transform: translateY(-2px);
}

.link-icon {
  font-size: 16px;
}

.link.back {
  color: #64748b;
}

.link.back:hover {
  color: #64748b;
}
</style>
