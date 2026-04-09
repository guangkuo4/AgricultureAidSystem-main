<template>
	<div>
		<div class="container" :style='{"minHeight":"100vh","alignItems":"center","background":"url(https://picsum.photos/id/1039/1920/1080)","display":"flex","width":"100%","backgroundSize":"cover","backgroundPosition":"center bottom","backgroundRepeat":"no-repeat","justifyContent":"center"}'>
			<el-form v-if="pageFlag=='register'" :style='{"border":"0","padding":"40px 5% 40px 45%","margin":"50px auto","borderRadius":"16px","textAlign":"center","background":"rgba(255,255,255,0.95) url(https://picsum.photos/id/1039/800/620) no-repeat left center / 50% 100%","width":"75%","height":"auto","boxShadow":"0 8px 32px rgba(0,0,0,0.1)","backdropFilter":"blur(8px)"}' ref="rgsForm" class="rgs-form" :model="rgsForm" :rules="rules">
				<div v-if="true" :style='{"padding":"0","margin":"0 auto 30px","color":"#2c3e50","textAlign":"center","background":"none","width":"100%","lineHeight":"48px","fontSize":"26px","fontWeight":"700","letterSpacing":"1px"}' class="title">助农扶贫服务平台注册</div>
				
				<el-form-item :style='{"padding":"0","margin":"0 auto 18px","textAlign":"left","display":"block","width":"100%","position":"relative"}' class="list-item" v-if="tableName=='nonghu'">
					<div v-if="true" :style='{"padding":"0 10px","color":"#333","textAlign":"right","left":"-150px","width":"150px","lineHeight":"44px","fontSize":"14px","position":"absolute","fontWeight":"500"}' class="lable" :class="changeRules('nonghuzhanghao')?'required':''">农户账号：</div>
					<el-input v-model="ruleForm.nonghuzhanghao" autocomplete="off" placeholder="请输入农户账号" type="text" style="--input-border-color:#4facfe;--input-focus-border-color:#00f2fe" />
				</el-form-item>
				
				<el-form-item :style='{"padding":"0","margin":"0 auto 18px","textAlign":"left","display":"block","width":"100%","position":"relative"}' class="list-item" v-if="tableName=='nonghu'">
					<div v-if="true" :style='{"padding":"0 10px","color":"#333","textAlign":"right","left":"-150px","width":"150px","lineHeight":"44px","fontSize":"14px","position":"absolute","fontWeight":"500"}' class="lable" :class="changeRules('mima')?'required':''">密码：</div>
					<el-input v-model="ruleForm.mima" autocomplete="off" placeholder="请输入密码" type="password" style="--input-border-color:#4facfe;--input-focus-border-color:#00f2fe" />
				</el-form-item>
				
				<el-form-item :style='{"padding":"0","margin":"0 auto 18px","textAlign":"left","display":"block","width":"100%","position":"relative"}' class="list-item" v-if="tableName=='nonghu'">
					<div v-if="true" :style='{"padding":"0 10px","color":"#333","textAlign":"right","left":"-150px","width":"150px","lineHeight":"44px","fontSize":"14px","position":"absolute","fontWeight":"500"}' class="lable">确认密码：</div>
					<el-input v-model="ruleForm.mima2" autocomplete="off" placeholder="请再次输入密码" type="password" style="--input-border-color:#4facfe;--input-focus-border-color:#00f2fe" />
				</el-form-item>
				
				<el-form-item :style='{"padding":"0","margin":"0 auto 18px","textAlign":"left","display":"block","width":"100%","position":"relative"}' class="list-item" v-if="tableName=='nonghu'">
					<div v-if="true" :style='{"padding":"0 10px","color":"#333","textAlign":"right","left":"-150px","width":"150px","lineHeight":"44px","fontSize":"14px","position":"absolute","fontWeight":"500"}' class="lable" :class="changeRules('nonghuxingming')?'required':''">农户姓名：</div>
					<el-input v-model="ruleForm.nonghuxingming" autocomplete="off" placeholder="请输入农户姓名" type="text" style="--input-border-color:#4facfe;--input-focus-border-color:#00f2fe" />
				</el-form-item>
				
				<el-form-item :style='{"padding":"0","margin":"0 auto 18px","textAlign":"left","display":"block","width":"100%","position":"relative"}' class="list-item" v-if="tableName=='nonghu'">
					<div v-if="true" :style='{"padding":"0 10px","color":"#333","textAlign":"right","left":"-150px","width":"150px","lineHeight":"44px","fontSize":"14px","position":"absolute","fontWeight":"500"}' class="lable" :class="changeRules('xingbie')?'required':''">性别：</div>
                    <el-select v-model="ruleForm.xingbie" placeholder="请选择性别" style="width:100%;--input-border-color:#4facfe;--input-focus-border-color:#00f2fe">
                        <el-option v-for="(item,index) in nonghuxingbieOptions" :key="index" :label="item" :value="item"></el-option>
                    </el-select>
				</el-form-item>
				
				<el-form-item :style='{"padding":"0","margin":"0 auto 18px","textAlign":"left","display":"block","width":"100%","position":"relative"}' class="list-item" v-if="tableName=='nonghu'">
					<div v-if="true" :style='{"padding":"0 10px","color":"#333","textAlign":"right","left":"-150px","width":"150px","lineHeight":"44px","fontSize":"14px","position":"absolute","fontWeight":"500"}' class="lable" :class="changeRules('nonghudianhua')?'required':''">农户电话：</div>
					<el-input v-model="ruleForm.nonghudianhua" autocomplete="off" placeholder="请输入农户电话" type="text" style="--input-border-color:#4facfe;--input-focus-border-color:#00f2fe" />
				</el-form-item>
				
				<el-form-item :style='{"padding":"0","margin":"0 auto 18px","textAlign":"left","display":"block","width":"100%","position":"relative"}' class="list-item" v-if="tableName=='nonghu'">
					<div v-if="true" :style='{"padding":"0 10px","color":"#333","textAlign":"right","left":"-150px","width":"150px","lineHeight":"44px","fontSize":"14px","position":"absolute","fontWeight":"500"}' class="lable" :class="changeRules('touxiang')?'required':''">头像：</div>
                    <file-upload tip="点击上传头像" action="file/upload" :limit="3" :multiple="true" :fileUrls="ruleForm.touxiang?ruleForm.touxiang:''" @change="nonghutouxiangUploadChange"></file-upload>
				</el-form-item>

				<!-- 用户端表单 -->
				<el-form-item :style='{"padding":"0","margin":"0 auto 18px","textAlign":"left","display":"block","width":"100%","position":"relative"}' class="list-item" v-if="tableName=='yonghu'">
					<div v-if="true" :style='{"padding":"0 10px","color":"#333","textAlign":"right","left":"-150px","width":"150px","lineHeight":"44px","fontSize":"14px","position":"absolute","fontWeight":"500"}' class="lable" :class="changeRules('yonghuzhanghao')?'required':''">用户账号：</div>
					<el-input v-model="ruleForm.yonghuzhanghao" autocomplete="off" placeholder="请输入用户账号" type="text" style="--input-border-color:#4facfe;--input-focus-border-color:#00f2fe" />
				</el-form-item>
				
				<el-form-item :style='{"padding":"0","margin":"0 auto 18px","textAlign":"left","display":"block","width":"100%","position":"relative"}' class="list-item" v-if="tableName=='yonghu'">
					<div v-if="true" :style='{"padding":"0 10px","color":"#333","textAlign":"right","left":"-150px","width":"150px","lineHeight":"44px","fontSize":"14px","position":"absolute","fontWeight":"500"}' class="lable" :class="changeRules('mima')?'required':''">密码：</div>
					<el-input v-model="ruleForm.mima" autocomplete="off" placeholder="请输入密码" type="password" style="--input-border-color:#4facfe;--input-focus-border-color:#00f2fe" />
				</el-form-item>
				
				<el-form-item :style='{"padding":"0","margin":"0 auto 18px","textAlign":"left","display":"block","width":"100%","position":"relative"}' class="list-item" v-if="tableName=='yonghu'">
					<div v-if="true" :style='{"padding":"0 10px","color":"#333","textAlign":"right","left":"-150px","width":"150px","lineHeight":"44px","fontSize":"14px","position":"absolute","fontWeight":"500"}' class="lable">确认密码：</div>
					<el-input v-model="ruleForm.mima2" autocomplete="off" placeholder="请再次输入密码" type="password" style="--input-border-color:#4facfe;--input-focus-border-color:#00f2fe" />
				</el-form-item>
				
				<el-form-item :style='{"padding":"0","margin":"0 auto 18px","textAlign":"left","display":"block","width":"100%","position":"relative"}' class="list-item" v-if="tableName=='yonghu'">
					<div v-if="true" :style='{"padding":"0 10px","color":"#333","textAlign":"right","left":"-150px","width":"150px","lineHeight":"44px","fontSize":"14px","position":"absolute","fontWeight":"500"}' class="lable" :class="changeRules('yonghuxingming')?'required':''">用户姓名：</div>
					<el-input v-model="ruleForm.yonghuxingming" autocomplete="off" placeholder="请输入用户姓名" type="text" style="--input-border-color:#4facfe;--input-focus-border-color:#00f2fe" />
				</el-form-item>
				
				<el-form-item :style='{"padding":"0","margin":"0 auto 18px","textAlign":"left","display":"block","width":"100%","position":"relative"}' class="list-item" v-if="tableName=='yonghu'">
					<div v-if="true" :style='{"padding":"0 10px","color":"#333","textAlign":"right","left":"-150px","width":"150px","lineHeight":"44px","fontSize":"14px","position":"absolute","fontWeight":"500"}' class="lable" :class="changeRules('xingbie')?'required':''">性别：</div>
                    <el-select v-model="ruleForm.xingbie" placeholder="请选择性别" style="width:100%;--input-border-color:#4facfe;--input-focus-border-color:#00f2fe">
                        <el-option v-for="(item,index) in yonghuxingbieOptions" :key="index" :label="item" :value="item"></el-option>
                    </el-select>
				</el-form-item>
				
				<el-form-item :style='{"padding":"0","margin":"0 auto 18px","textAlign":"left","display":"block","width":"100%","position":"relative"}' class="list-item" v-if="tableName=='yonghu'">
					<div v-if="true" :style='{"padding":"0 10px","color":"#333","textAlign":"right","left":"-150px","width":"150px","lineHeight":"44px","fontSize":"14px","position":"absolute","fontWeight":"500"}' class="lable" :class="changeRules('yonghudianhua')?'required':''">用户电话：</div>
					<el-input v-model="ruleForm.yonghudianhua" autocomplete="off" placeholder="请输入用户电话" type="text" style="--input-border-color:#4facfe;--input-focus-border-color:#00f2fe" />
				</el-form-item>
				
				<el-form-item :style='{"padding":"0","margin":"0 auto 18px","textAlign":"left","display":"block","width":"100%","position":"relative"}' class="list-item" v-if="tableName=='yonghu'">
					<div v-if="true" :style='{"padding":"0 10px","color":"#333","textAlign":"right","left":"-150px","width":"150px","lineHeight":"44px","fontSize":"14px","position":"absolute","fontWeight":"500"}' class="lable" :class="changeRules('touxiang')?'required':''">头像：</div>
                    <file-upload tip="点击上传头像" action="file/upload" :limit="3" :multiple="true" :fileUrls="ruleForm.touxiang?ruleForm.touxiang:''" @change="yonghutouxiangUploadChange"></file-upload>
				</el-form-item>

				<div style="display:flex;justify-content:space-between;align-items:center;margin-top:20px;width:100%;padding-left:150px">
					<button :style='{"border":"0","cursor":"pointer","padding":"0 20px","margin":"0","color":"#fff","letterSpacing":"2px","outline":"none","borderRadius":"8px","background":"linear-gradient(135deg,#4facfe,#00f2fe)","width":"68%","fontSize":"16px","fontWeight":"600","height":"48px","boxShadow":"0 4px 12px rgba(79,172,254,0.3)","transition":"all 0.3s"}' type="button" class="r-btn" @click="login()">注册</button>
					<div :style='{"cursor":"pointer","color":"#4facfe","textAlign":"center","width":"30%","fontSize":"14px","fontWeight":"500"}' class="r-login" @click="close()">已有账号，去登录</div>
				</div>
			</el-form>
		</div>
	</div>
</template>

<script>
export default {
	data() {
		return {
			ruleForm: {},
			forgetForm: {},
            pageFlag : '',
			tableName:"",
			rules: {},
            nonghuxingbieOptions: [],
            yonghuxingbieOptions: [],
		};
	},
	mounted(){
		this.pageFlag = this.$route.query.pageFlag
		if(this.$route.query.pageFlag=='register'){
			
			let table = this.$storage.get("loginTable");
			this.tableName = table;
			if(this.tableName=='nonghu'){
				this.ruleForm = {
					nonghuzhanghao: '',
					mima: '',
					nonghuxingming: '',
					xingbie: '',
					nonghudianhua: '',
					touxiang: '',
					money: '',
				}
			}
			if(this.tableName=='yonghu'){
				this.ruleForm = {
					yonghuzhanghao: '',
					mima: '',
					yonghuxingming: '',
					xingbie: '',
					yonghudianhua: '',
					touxiang: '',
					money: '',
					vip: '',
				}
			}
			if ('nonghu' == this.tableName) {
				this.rules.nonghuzhanghao = [{ required: true, message: '请输入农户账号', trigger: 'blur' }]
			}
			if ('nonghu' == this.tableName) {
				this.rules.mima = [{ required: true, message: '请输入密码', trigger: 'blur' }]
			}
			if ('nonghu' == this.tableName) {
				this.rules.nonghuxingming = [{ required: true, message: '请输入农户姓名', trigger: 'blur' }]
			}
			if ('yonghu' == this.tableName) {
				this.rules.yonghuzhanghao = [{ required: true, message: '请输入用户账号', trigger: 'blur' }]
			}
			if ('yonghu' == this.tableName) {
				this.rules.mima = [{ required: true, message: '请输入密码', trigger: 'blur' }]
			}
			if ('yonghu' == this.tableName) {
				this.rules.yonghuxingming = [{ required: true, message: '请输入用户姓名', trigger: 'blur' }]
			}
			this.nonghuxingbieOptions = "男,女".split(',')
			this.yonghuxingbieOptions = "男,女".split(',')
		}
	},
	methods: {
		changeRules(name){
			if(this.rules[name]){
				return true
			}
			return false
		},
		close(){
			this.$router.push({ path: "/login" });
		},
        nonghutouxiangUploadChange(fileUrls) {
            this.ruleForm.touxiang = fileUrls;
        },
        yonghutouxiangUploadChange(fileUrls) {
            this.ruleForm.touxiang = fileUrls;
        },
		login() {
			var url=this.tableName+"/register";
					if((!this.ruleForm.nonghuzhanghao) && `nonghu` == this.tableName){
						this.$message.error(`农户账号不能为空`);
						return
					}
					if((!this.ruleForm.mima) && `nonghu` == this.tableName){
						this.$message.error(`密码不能为空`);
						return
					}
					if((this.ruleForm.mima!=this.ruleForm.mima2) && `nonghu` == this.tableName){
						this.$message.error(`两次密码输入不一致`);
						return
					}
					if((!this.ruleForm.nonghuxingming) && `nonghu` == this.tableName){
						this.$message.error(`农户姓名不能为空`);
						return
					}
					if(`nonghu` == this.tableName && this.ruleForm.nonghudianhua &&(!this.$validate.isMobile(this.ruleForm.nonghudianhua))){
						this.$message.error(`农户电话应输入手机格式`);
						return
					}
            if(this.ruleForm.touxiang!=null) {
                this.ruleForm.touxiang = this.ruleForm.touxiang.replace(new RegExp(this.$base.url,"g"),"");
            }
					if((!this.ruleForm.yonghuzhanghao) && `yonghu` == this.tableName){
						this.$message.error(`用户账号不能为空`);
						return
					}
					if((!this.ruleForm.mima) && `yonghu` == this.tableName){
						this.$message.error(`密码不能为空`);
						return
					}
					if((this.ruleForm.mima!=this.ruleForm.mima2) && `yonghu` == this.tableName){
						this.$message.error(`两次密码输入不一致`);
						return
					}
					if((!this.ruleForm.yonghuxingming) && `yonghu` == this.tableName){
						this.$message.error(`用户姓名不能为空`);
						return
					}
					if(`yonghu` == this.tableName && this.ruleForm.yonghudianhua &&(!this.$validate.isMobile(this.ruleForm.yonghudianhua))){
						this.$message.error(`用户电话应输入手机格式`);
						return
					}
            if(this.ruleForm.touxiang!=null) {
                this.ruleForm.touxiang = this.ruleForm.touxiang.replace(new RegExp(this.$base.url,"g"),"");
            }
			this.$http({
				url: url,
				method: "post",
				data:this.ruleForm
			}).then(({ data }) => {
				if (data && data.code === 0) {
					this.$message({
						message: "注册成功",
						type: "success",
						duration: 1500,
						onClose: () => {
							this.$router.replace({ path: "/login" });
						}
					});
				} else {
					this.$message.error(data.msg);
				}
			});
		}
	}
};
</script>

<style lang="scss" scoped>
	.container {
	  position: relative;
	  background-size: cover !important;
	  background-position: center !important;
	  background-repeat: no-repeat !important;
	  min-height: 100vh;
	}

	.rgs-form {
		position: relative;
		z-index: 10;
	}

	.el-input__inner,
	.el-select .el-input__inner {
		border-radius: 8px !important;
		height: 44px !important;
		padding: 0 15px !important;
		font-size: 14px !important;
		border: 1px solid #4facfe !important;
		transition: all 0.3s ease !important;
	}

	.el-input__inner:focus,
	.el-select .el-input__inner:focus {
		border-color: #00f2fe !important;
		box-shadow: 0 0 8px rgba(79, 172, 254, 0.2) !important;
	}

	.r-btn:hover {
		transform: translateY(-2px);
		box-shadow: 0 6px 16px rgba(79, 172, 254, 0.4) !important;
	}

	.r-login:hover {
		text-decoration: underline;
	}

	.required {
		position: relative;
	}
	.required::after{
				color: red;
				position: absolute;
				right: 6px;
				content: "*";
				order: 1;
			}
</style>