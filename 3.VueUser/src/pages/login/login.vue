<template>
<div class="login-page-wrapper">
	<div class="container">
		<div class="login-card">
			<div class="login-header">
				<div class="logo-icon">🌾</div>
				<h1 class="title">助农扶贫服务平台</h1>
				<p class="subtitle">欢迎登录</p>
			</div>

			<el-form ref="loginForm" :model="loginForm" @submit.native.prevent="submitForm('loginForm')" :rules="rules" class="login-form">
				<el-form-item v-if="loginType==1" class="form-item" prop="username">
					<div class="input-wrapper">
						<span class="input-icon">👤</span>
						<input 
							class="custom-input" 
							v-model="loginForm.username" 
							placeholder="请输入账号" 
							@keyup.enter="submitForm('loginForm')"
						>
					</div>
				</el-form-item>

				<el-form-item v-if="loginType==1" class="form-item" prop="password">
					<div class="input-wrapper">
						<span class="input-icon">🔒</span>
						<input 
							class="custom-input" 
							v-model="loginForm.password" 
							placeholder="请输入密码" 
							type="password"
							@keyup.enter="submitForm('loginForm')"
						>
					</div>
				</el-form-item>

				<el-form-item class="form-item select-item" v-if="roles.length>1">
					<div class="input-wrapper">
						<span class="input-icon">👥</span>
						<el-select v-model="loginForm.tableName" placeholder="请选择角色" @change="selectChange" class="custom-select">
							<el-option v-for="item,index in roles" :key="index" :label="item.roleName" :value="item.tableName" />
						</el-select>
					</div>
				</el-form-item>

				<el-form-item class="form-item btn-item">
					<el-button 
						v-if="loginType==1" 
						type="submit" 
						class="login-btn"
						@click="submitForm('loginForm')"
					>
						<span class="btn-text">登 录</span>
					</el-button>
				</el-form-item>
			</el-form>

			<div class="footer-links">
				<div class="link-item back-link" @click="goBack">
					← 返回首页
				</div>
				<router-link 
					class="link-item" 
					:to="{path: '/register', query: {role: item.tableName,pageFlag:'register'}}" 
					v-if="item.hasFrontRegister=='是'" 
					v-for="(item, index) in roles" 
					:key="index"
				>
					注册{{item.roleName.replace('注册','')}}
				</router-link>
				<div class="link-item admin-link" @click="goToAdminLogin">
					农商企业登录
				</div>
			</div>
		</div>
	</div>
</div>
</template>

<script>
import menu from '@/config/menu'
import loginBg from '@/assets/login-bg.png'
export default {
	data() {
		return {
            baseUrl: this.$config.baseUrl,
			loginBg,
            loginType: 1,
			roleMenus: [],
			loginForm: {
				username: '',
				password: '',
				tableName: '',
				code: '',
			},
			role: '',
            roles: [],
			rules: {
				username: [
					{ required: true, message: '请输入账户', trigger: 'blur' }
				],
				password: [
					{ required: true, message: '请输入密码', trigger: 'blur' }
				]
			},
			codes: [{
				num: 1,
				color: '#000',
				rotate: '10deg',
				size: '16px'
			}, {
				num: 2,
				color: '#000',
				rotate: '10deg',
				size: '16px'
			}, {
				num: 3,
				color: '#000',
				rotate: '10deg',
				size: '16px'
			}, {
				num: 4,
				color: '#000',
				rotate: '10deg',
				size: '16px'
			}],
			flag: false,
			verifyCheck2: false,
		}
	},
	created() {
		this.roleMenus = menu.list()
		for(let item in this.roleMenus) {
		    if(this.roleMenus[item].hasFrontLogin=='是') {
		        this.roles.push(this.roleMenus[item]);
		    }
		}
	},
    methods: {
		randomString() {
			var len = 4;
			var chars = [
			  'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
			  'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
			  'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			  'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
			  'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2',
			  '3', '4', '5', '6', '7', '8', '9'
			]
			var colors = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f']
			var sizes = ['14', '15', '16', '17', '18']
			
			var output = []
			for (var i = 0; i < len; i++) {
			  var key = Math.floor(Math.random() * chars.length)
			  this.codes[i].num = chars[key]
			  var code = '#'
			  for (var j = 0; j < 6; j++) {
			    var key = Math.floor(Math.random() * colors.length)
			    code += colors[key]
			  }
			  this.codes[i].color = code
			  var rotate = Math.floor(Math.random() * 45)
			  var plus = Math.floor(Math.random() * 2)
			  if (plus == 1) rotate = '-' + rotate
			  this.codes[i].rotate = 'rotate(' + rotate + 'deg)'
			  var size = Math.floor(Math.random() * sizes.length)
			  this.codes[i].size = sizes[size] + 'px'
			}
		},
	  selectChange(e){
		  this.role = e
	  },
      submitForm(formName) {
        if (this.roles.length!=1) {
            if (!this.role) {
                this.$message.error("请选择登录用户类型");
                return false;
            }
        } else {
            this.role = this.roles[0].roleName;
            this.loginForm.tableName = this.roles[0].tableName;
        }
		this.loginPost(formName)
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
	  loginPost(formName) {
		this.$refs[formName].validate((valid) => {
		  if (valid) {
		    this.$http.get(`${this.loginForm.tableName}/login`, {params: this.loginForm}).then(res => {
		      if (res.data.code === 0) {
		        localStorage.setItem('frontToken', res.data.token);
		        localStorage.setItem('UserTableName', this.loginForm.tableName);
		        localStorage.setItem('username', this.loginForm.username);
		        localStorage.setItem('adminName', this.loginForm.username);
		        localStorage.setItem('frontSessionTable', this.loginForm.tableName);
		        localStorage.setItem('frontRole', this.role);
		        localStorage.setItem('keyPath', 0);
		        this.$router.push('/');
		        this.$message({
		          message: '登录成功',
		          type: 'success',
		          duration: 1500,
		        });
		      } else {
		        this.$message.error(res.data.msg);
		      }
		    }).catch((err) => {
		      let msg = "网络异常，请确认后端已启动在 http://localhost:8080";
		      if (err.body && typeof err.body === "object" && err.body.msg) {
		        msg = err.body.msg;
		      } else if (err.statusText) {
		        msg = err.statusText;
		      }
		      this.$message.error(msg);
		    });
		  } else {
		    return false;
		  }
		});
	  },
	  goBack() {
		this.$router.push('/');
	  },
	  goToAdminLogin() {
		window.open('http://localhost:8081/#/login', '_blank');
	  },
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.login-page-wrapper {
	width: 100%;
	min-height: 100vh;
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
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
		animation: float 20s ease-in-out infinite;
	}

	@keyframes float {
		0%, 100% { transform: translate(0, 0); }
		50% { transform: translate(30px, -30px); }
	}
}

.container {
	min-height: 100vh;
	display: flex;
	align-items: center;
	justify-content: center;
	position: relative;
	z-index: 1;
}

.login-card {
	width: 560px;
	background: rgba(255, 255, 255, 0.95);
	backdrop-filter: blur(20px);
	border-radius: 24px;
	padding: 60px 55px;
	box-shadow: 
		0 20px 60px rgba(0, 0, 0, 0.3),
		0 0 0 1px rgba(255, 255, 255, 0.2) inset;
	position: relative;
	overflow: hidden;

	&::before {
		content: "";
		position: absolute;
		top: 0;
		left: 0;
		right: 0;
		height: 4px;
		background: linear-gradient(90deg, #667eea, #764ba2, #667eea);
		background-size: 200% 100%;
		animation: shimmer 3s linear infinite;
	}

	@keyframes shimmer {
		0% { background-position: -200% 0; }
		100% { background-position: 200% 0; }
	}
}

.login-header {
	text-align: center;
	margin-bottom: 40px;
}

.logo-icon {
	font-size: 56px;
	margin-bottom: 20px;
	animation: bounce 2s ease-in-out infinite;
}

@keyframes bounce {
	0%, 100% { transform: translateY(0); }
	50% { transform: translateY(-8px); }
}

.title {
	font-size: 32px;
	font-weight: 700;
	color: #1a1a2e;
	margin: 0 0 10px 0;
	letter-spacing: 1px;
}

.subtitle {
	font-size: 17px;
	color: #888;
	margin: 0;
	font-weight: 400;
}

.login-form {
	.el-form-item {
		margin-bottom: 24px;
	}
}

.form-item {
	margin-bottom: 24px;
}

.input-wrapper {
	position: relative;
	display: flex;
	align-items: center;
	background: #f8f9fa;
	border: 2px solid #e9ecef;
	border-radius: 14px;
	transition: all 0.3s ease;

	&:focus-within {
		border-color: #667eea;
		background: #fff;
		box-shadow: 0 0 0 4px rgba(102, 126, 234, 0.1);
	}
}

.input-icon {
	font-size: 24px;
	padding: 0 18px 0 20px;
	opacity: 0.6;
	transition: all 0.3s ease;
}

.input-wrapper:focus-within .input-icon {
	opacity: 1;
	transform: scale(1.1);
}

.custom-input {
	flex: 1;
	border: none;
	background: transparent;
	padding: 18px 18px 18px 0;
	font-size: 17px;
	color: #333;
	outline: none;
	height: 62px;
	box-sizing: border-box;

	&::placeholder {
		color: #aaa;
	}
}

.custom-select {
	flex: 1;

	::v-deep .el-input__inner {
		border: none !important;
		background: transparent !important;
		padding: 16px 16px 16px 0 !important;
		font-size: 15px !important;
		color: #333 !important;
		height: 54px !important;
		line-height: 54px !important;
	}
}

.btn-item {
	margin-top: 32px;
	margin-bottom: 20px;
}

.login-btn {
	width: 100%;
	height: 64px;
	border: none;
	border-radius: 14px;
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	color: #fff;
	font-size: 19px;
	font-weight: 600;
	letter-spacing: 2px;
	cursor: pointer;
	transition: all 0.3s ease;
	box-shadow: 0 10px 30px rgba(102, 126, 234, 0.4);
	position: relative;
	overflow: hidden;

	&:hover {
		transform: translateY(-2px);
		box-shadow: 0 15px 40px rgba(102, 126, 234, 0.5);
	}

	&:active {
		transform: translateY(0);
	}

	&::before {
		content: "";
		position: absolute;
		top: 0;
		left: -100%;
		width: 100%;
		height: 100%;
		background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent);
		transition: left 0.5s ease;
	}

	&:hover::before {
		left: 100%;
	}
}

.btn-text {
	position: relative;
	z-index: 1;
}

.footer-links {
	display: flex;
	justify-content: space-around;
	margin-top: 24px;
	padding-top: 24px;
	border-top: 1px solid #eee;
}

.link-item {
	padding: 12px 28px;
	color: #667eea;
	font-size: 16px;
	font-weight: 500;
	cursor: pointer;
	border-radius: 20px;
	transition: all 0.3s ease;
	text-decoration: none;

	&:hover {
		background: rgba(102, 126, 234, 0.1);
		transform: translateY(-2px);
	}
}

.back-link {
	color: #666;

	&:hover {
		background: rgba(102, 102, 102, 0.1);
	}
}

.admin-link {
	color: #764ba2;

	&:hover {
		background: rgba(118, 75, 162, 0.1);
	}
}
</style>
