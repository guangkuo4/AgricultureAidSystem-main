<template>
	<div class="login-page">
		<div class="login-card">
			<!-- 左侧品牌区 -->
			<div class="left-side">
				<div class="brand-content">
					<div class="big-logo">🌾</div>
					<h2 class="brand-title">助农扶贫</h2>
					<p class="brand-sub">服务平台</p>
				</div>
				<div class="deco-lines">
					<div class="line line1"></div>
					<div class="line line2"></div>
					<div class="line line3"></div>
				</div>
			</div>

			<!-- 右侧注册区 -->
			<div class="right-side">
				<div class="login-header">
					<h1 class="title">创建账户</h1>
					<p class="subtitle">注册账号 · Register</p>
				</div>

				<el-form class='register-form' v-if="pageFlag=='register'" ref="registerForm" :model="registerForm" :rules="rules">
					<el-form-item v-if="tableName=='nonghu'" prop="nonghuzhanghao">
						<div class="input-wrapper">
							<div class="icon-box">
								<span class="input-icon">👤</span>
							</div>
							<el-input class="custom-input" v-model="registerForm.nonghuzhanghao" placeholder="请输入农户账号" />
						</div>
					</el-form-item>
					<el-form-item v-if="tableName=='nonghu'" prop="mima">
						<div class="input-wrapper">
							<div class="icon-box">
								<span class="input-icon">🔒</span>
							</div>
							<el-input class="custom-input" v-model="registerForm.mima" type="password" placeholder="请输入密码" />
						</div>
					</el-form-item>
					<el-form-item v-if="tableName=='nonghu'" prop="mima2">
						<div class="input-wrapper">
							<div class="icon-box">
								<span class="input-icon">🔐</span>
							</div>
							<el-input class="custom-input" v-model="registerForm.mima2" type="password" placeholder="请再次输入密码" />
						</div>
					</el-form-item>
					<el-form-item v-if="tableName=='nonghu'" prop="nonghuxingming">
						<div class="input-wrapper">
							<div class="icon-box">
								<span class="input-icon">📝</span>
							</div>
							<el-input class="custom-input" v-model="registerForm.nonghuxingming" placeholder="请输入农户姓名" />
						</div>
					</el-form-item>
					<el-form-item v-if="tableName=='nonghu'" prop="xingbie">
						<div class="input-wrapper">
							<div class="icon-box">
								<span class="input-icon">⚧</span>
							</div>
							<el-select class="custom-select" v-model="registerForm.xingbie" placeholder="请选择性别">
								<el-option v-for="(item,index) in nonghuxingbieOptions" :key="index" :label="item" :value="item"></el-option>
							</el-select>
						</div>
					</el-form-item>
					<el-form-item v-if="tableName=='nonghu'" prop="nonghudianhua">
						<div class="input-wrapper">
							<div class="icon-box">
								<span class="input-icon">📱</span>
							</div>
							<el-input class="custom-input" v-model="registerForm.nonghudianhua" placeholder="请输入农户电话" />
						</div>
					</el-form-item>
					<el-form-item v-if="tableName=='nonghu'" prop="touxiang">
						<div class="upload-wrapper">
							<div class="icon-box">
								<span class="input-icon">🖼️</span>
							</div>
							<file-upload
								tip="点击上传头像"
								action="file/upload"
								:limit="1"
								:multiple="true"
								:fileUrls="registerForm.touxiang?registerForm.touxiang:''"
								@change="nonghutouxiangUploadChange"
							></file-upload>
						</div>
					</el-form-item>

					<el-form-item v-if="tableName=='yonghu'" prop="yonghuzhanghao">
						<div class="input-wrapper">
							<div class="icon-box">
								<span class="input-icon">👤</span>
							</div>
							<el-input class="custom-input" v-model="registerForm.yonghuzhanghao" placeholder="请输入用户账号" />
						</div>
					</el-form-item>
					<el-form-item v-if="tableName=='yonghu'" prop="mima">
						<div class="input-wrapper">
							<div class="icon-box">
								<span class="input-icon">🔒</span>
							</div>
							<el-input class="custom-input" v-model="registerForm.mima" type="password" placeholder="请输入密码" />
						</div>
					</el-form-item>
					<el-form-item v-if="tableName=='yonghu'" prop="mima2">
						<div class="input-wrapper">
							<div class="icon-box">
								<span class="input-icon">🔐</span>
							</div>
							<el-input class="custom-input" v-model="registerForm.mima2" type="password" placeholder="请再次输入密码" />
						</div>
					</el-form-item>
					<el-form-item v-if="tableName=='yonghu'" prop="yonghuxingming">
						<div class="input-wrapper">
							<div class="icon-box">
								<span class="input-icon">📝</span>
							</div>
							<el-input class="custom-input" v-model="registerForm.yonghuxingming" placeholder="请输入用户姓名" />
						</div>
					</el-form-item>
					<el-form-item v-if="tableName=='yonghu'" prop="xingbie">
						<div class="input-wrapper">
							<div class="icon-box">
								<span class="input-icon">⚧</span>
							</div>
							<el-select class="custom-select" v-model="registerForm.xingbie" placeholder="请选择性别">
								<el-option v-for="(item,index) in yonghuxingbieOptions" :key="index" :label="item" :value="item"></el-option>
							</el-select>
						</div>
					</el-form-item>
					<el-form-item v-if="tableName=='yonghu'" prop="yonghudianhua">
						<div class="input-wrapper">
							<div class="icon-box">
								<span class="input-icon">📱</span>
							</div>
							<el-input class="custom-input" v-model="registerForm.yonghudianhua" placeholder="请输入用户电话" />
						</div>
					</el-form-item>
					<el-form-item v-if="tableName=='yonghu'" prop="touxiang">
						<div class="upload-wrapper">
							<div class="icon-box">
								<span class="input-icon">🖼️</span>
							</div>
							<file-upload
								tip="点击上传头像"
								action="file/upload"
								:limit="1"
								:multiple="true"
								:fileUrls="registerForm.touxiang?registerForm.touxiang:''"
								@change="yonghutouxiangUploadChange"
							></file-upload>
						</div>
					</el-form-item>

					<el-form-item class="btn-item">
						<el-button class="login-btn" type="primary" @click="submitForm('registerForm')">
							<span class="btn-text">立即注册</span>
							<span class="btn-arrow">→</span>
						</el-button>
					</el-form-item>

					<div class="footer-links">
						<div class="link-item back-link" @click="goBack">
							<span class="link-icon">←</span>
							返回首页
						</div>
						<router-link class="link-item" to="/login">
							<span class="link-icon">✨</span>
							已有账户登录
						</router-link>
					</div>
				</el-form>
			</div>
		</div>
	</div>
</template>

<script>
import registerBg from '@/assets/register-bg.png'
export default {
    data() {
		return {
            pageFlag : '',
			registerBg,
			tableName: '',
			registerForm: {},
			forgetForm: {},
			rules: {},
			requiredRules: {},
            nonghuxingbieOptions: [],
            yonghuxingbieOptions: [],
		}
    },
	mounted() {
		if(this.$route.query.pageFlag=='register'){
			
			this.tableName = this.$route.query.role;
			if(this.tableName=='nonghu'){
				this.registerForm = {
					nonghuzhanghao: '',
					mima: '',
					mima2: '',
					nonghuxingming: '',
					xingbie: '',
					nonghudianhua: '',
					touxiang: '',
					money: '',
				}
			}
			if(this.tableName=='yonghu'){
				this.registerForm = {
					yonghuzhanghao: '',
					mima: '',
					mima2: '',
					yonghuxingming: '',
					xingbie: '',
					yonghudianhua: '',
					touxiang: '',
					money: '',
					vip: '',
				}
			}
			if ('nonghu' == this.tableName) {
				this.requiredRules.nonghuzhanghao = [{ required: true, message: '请输入农户账号', trigger: 'blur' }]
			}
			if ('nonghu' == this.tableName) {
				this.requiredRules.mima = [{ required: true, message: '请输入密码', trigger: 'blur' }, { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }]
			}
			if ('nonghu' == this.tableName) {
				this.requiredRules.nonghuxingming = [{ required: true, message: '请输入农户姓名', trigger: 'blur' }]
			}
			if ('yonghu' == this.tableName) {
				this.requiredRules.yonghuzhanghao = [{ required: true, message: '请输入用户账号', trigger: 'blur' }]
			}
			if ('yonghu' == this.tableName) {
				this.requiredRules.mima = [{ required: true, message: '请输入密码', trigger: 'blur' }, { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }]
			}
			if ('yonghu' == this.tableName) {
				this.requiredRules.yonghuxingming = [{ required: true, message: '请输入用户姓名', trigger: 'blur' }]
			}
		}
    },
    created() {
		this.pageFlag = this.$route.query.pageFlag;
		if(this.$route.query.pageFlag=='register'){
			const tableName = this.$route.query.role;
		  if ('nonghu' == tableName) {
			this.rules.nonghuzhanghao = [{ required: true, message: '请输入农户账号', trigger: 'blur' }];
		  }
		  if ('nonghu' == tableName) {
			this.rules.mima = [{ required: true, message: '请输入密码', trigger: 'blur' }, { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }];
		}
		  if ('nonghu' == tableName) {
			this.rules.nonghuxingming = [{ required: true, message: '请输入农户姓名', trigger: 'blur' }];
		  }
			this.nonghuxingbieOptions = "男,女".split(',');
		  if ('nonghu' == tableName) {
			this.rules.nonghudianhua = [{ required: true, validator: this.$validate.isMobile, trigger: 'blur' }];
		  }
		  if ('nonghu' == tableName) {
			this.rules.money = [{ required: true, validator: this.$validate.isNumber, trigger: 'blur' }];
		  }
		  if ('yonghu' == tableName) {
			this.rules.yonghuzhanghao = [{ required: true, message: '请输入用户账号', trigger: 'blur' }];
		  }
		  if ('yonghu' == tableName) {
			this.rules.mima = [{ required: true, message: '请输入密码', trigger: 'blur' }, { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }];
		}
		  if ('yonghu' == tableName) {
			this.rules.yonghuxingming = [{ required: true, message: '请输入用户姓名', trigger: 'blur' }];
		  }
			this.yonghuxingbieOptions = "男,女".split(',');
		  if ('yonghu' == tableName) {
			this.rules.yonghudianhua = [{ required: true, validator: this.$validate.isMobile, trigger: 'blur' }];
		  }
		  if ('yonghu' == tableName) {
			this.rules.money = [{ required: true, validator: this.$validate.isNumber, trigger: 'blur' }];
		  }
		}
    },
    methods: {
		changeRules(name){
			if(this.requiredRules[name]){
				return true
			}
			return false
		},
      getUUID () {
        return new Date().getTime();
      },
      nonghutouxiangUploadChange(fileUrls) {
          this.registerForm.touxiang = fileUrls.replace(new RegExp(this.$config.baseUrl,"g"),"");
      },
      yonghutouxiangUploadChange(fileUrls) {
          this.registerForm.touxiang = fileUrls.replace(new RegExp(this.$config.baseUrl,"g"),"");
      },
	  goBack() {
		this.$router.push('/');
	  },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            var url=this.tableName+"/register";
				if((!this.registerForm.nonghuzhanghao) && `nonghu` == this.tableName){
					this.$message.error(`农户账号不能为空`);
					return
				}
               if(`nonghu` == this.tableName && this.registerForm.mima!=this.registerForm.mima2) {
                this.$message.error(`两次密码输入不一致`);
                return
               }
				if((!this.registerForm.mima) && `nonghu` == this.tableName){
					this.$message.error(`密码不能为空`);
					return
				}
				if((!this.registerForm.nonghuxingming) && `nonghu` == this.tableName){
					this.$message.error(`农户姓名不能为空`);
					return
				}
					if(`nonghu` == this.tableName && this.registerForm.nonghudianhua &&(!this.$validate.isMobile2(this.registerForm.nonghudianhua))){
						this.$message.error(`农户电话应输入手机格式`);
						return
					}
				if((!this.registerForm.yonghuzhanghao) && `yonghu` == this.tableName){
					this.$message.error(`用户账号不能为空`);
					return
				}
               if(`yonghu` == this.tableName && this.registerForm.mima!=this.registerForm.mima2) {
                this.$message.error(`两次密码输入不一致`);
                return
               }
				if((!this.registerForm.mima) && `yonghu` == this.tableName){
					this.$message.error(`密码不能为空`);
					return
				}
				if((!this.registerForm.yonghuxingming) && `yonghu` == this.tableName){
					this.$message.error(`用户姓名不能为空`);
					return
				}
					if(`yonghu` == this.tableName && this.registerForm.yonghudianhua &&(!this.$validate.isMobile2(this.registerForm.yonghudianhua))){
						this.$message.error(`用户电话应输入手机格式`);
						return
					}
            this.$http.post(url, this.registerForm).then(res => {
              if (res.data.code === 0) {
                this.$message({
                  message: '注册成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.$router.push('/login');
                  }
                });
              } else {
                this.$message.error(res.data.msg);
              }
            });
          } else {
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.login-page {
  width: 100%;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px 20px;
  box-sizing: border-box;
  position: relative;
  overflow: hidden;

  &::before {
    content: "";
    position: absolute;
    top: -50%;
    left: -50%;
    width: 200%;
    height: 200%;
    background: 
      radial-gradient(circle at 20% 30%, rgba(102, 126, 234, 0.3) 0%, transparent 50%),
      radial-gradient(circle at 80% 70%, rgba(118, 75, 162, 0.3) 0%, transparent 50%);
    animation: floatBg 20s ease-in-out infinite;
  }

  @keyframes floatBg {
    0%, 100% { transform: translate(0, 0); }
    50% { transform: translate(30px, -30px); }
  }
}

.login-card {
  width: 1000px;
  background: #fff;
  border-radius: 20px;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.4);
  display: flex;
  overflow: hidden;
  position: relative;
  z-index: 1;
}

.login-card::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 3px;
  background: linear-gradient(90deg, #667eea, #764ba2, #667eea);
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
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
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
  background: radial-gradient(circle, rgba(255,255,255,0.15) 0%, transparent 60%);
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
  color: rgba(255, 255, 255, 0.85);
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
  background: #fff;
}

.login-header {
  text-align: center;
  margin-bottom: 35px;
}

.title {
  font-size: 32px;
  font-weight: 700;
  color: #1a1a2e;
  margin: 0 0 10px 0;
  letter-spacing: 1px;
}

.subtitle {
  font-size: 15px;
  color: #888;
  margin: 0;
  font-weight: 400;
  letter-spacing: 2px;
}

.register-form {
  .el-form-item {
    margin-bottom: 20px;
  }
}

.input-wrapper {
  display: flex;
  align-items: center;
  background: #f8f9fa;
  border: 2px solid #e9ecef;
  border-radius: 12px;
  transition: all 0.3s;
}

.input-wrapper:focus-within {
  border-color: #667eea;
  box-shadow: 0 0 0 4px rgba(102, 126, 234, 0.1), inset 0 0 20px rgba(102, 126, 234, 0.05);
}

.upload-wrapper {
  display: flex;
  align-items: center;
  background: #f8f9fa;
  border: 2px solid #e9ecef;
  border-radius: 12px;
  transition: all 0.3s;
  padding: 20px;
  min-height: 100px;
}

.upload-wrapper:focus-within {
  border-color: #667eea;
  box-shadow: 0 0 0 4px rgba(102, 126, 234, 0.1);
}

.icon-box {
  padding: 0 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-right: 1px solid #e9ecef;
}

.input-icon {
  font-size: 22px;
  opacity: 0.7;
  transition: all 0.3s;
}

.input-wrapper:focus-within .input-icon {
  opacity: 1;
  transform: scale(1.1);
}

.upload-wrapper .icon-box {
  border-right: 1px solid #e9ecef;
}

.upload-wrapper .input-icon {
  font-size: 28px;
}

.custom-input {
  flex: 1;

  ::v-deep .el-input__inner {
    border: none !important;
    background: transparent !important;
    padding: 16px 18px 16px 16px !important;
    font-size: 15px !important;
    color: #333 !important;
    height: 56px !important;
    box-sizing: border-box !important;
  }

  ::v-deep .el-input__inner::placeholder {
    color: #aaa;
  }
}

.custom-select {
  flex: 1;

  ::v-deep .el-input__inner {
    border: none !important;
    background: transparent !important;
    padding: 16px 18px 16px 16px !important;
    font-size: 15px !important;
    color: #333 !important;
    height: 56px !important;
    line-height: 56px !important;
  }
}

.btn-item {
  margin-top: 30px;
  margin-bottom: 20px;
}

.login-btn {
  width: 100%;
  height: 56px;
  border: none;
  border-radius: 12px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  font-size: 17px;
  font-weight: 600;
  letter-spacing: 1px;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 10px 30px rgba(102, 126, 234, 0.3);
  position: relative;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
}

.login-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 15px 40px rgba(102, 126, 234, 0.4);
}

.login-btn:active {
  transform: translateY(-1px);
}

.login-btn::before {
  content: "";
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2), transparent);
  transition: left 0.6s ease;
}

.login-btn:hover::before {
  left: 100%;
}

.btn-text {
  position: relative;
  z-index: 1;
}

.btn-arrow {
  position: relative;
  z-index: 1;
  font-size: 18px;
  transition: transform 0.3s ease;
}

.login-btn:hover .btn-arrow {
  transform: translateX(5px);
}

.footer-links {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
  padding-top: 25px;
  border-top: 1px solid #eee;
}

.link-item {
  padding: 12px 24px;
  color: #667eea;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  border-radius: 10px;
  transition: all 0.3s ease;
  text-decoration: none;
  display: flex;
  align-items: center;
  gap: 8px;
}

.link-item:hover {
  background: rgba(102, 126, 234, 0.1);
  transform: translateY(-2px);
}

.link-icon {
  font-size: 16px;
}

.back-link {
  color: #888;
}

.back-link:hover {
  color: #888;
  background: rgba(136, 136, 136, 0.1);
}
</style>
