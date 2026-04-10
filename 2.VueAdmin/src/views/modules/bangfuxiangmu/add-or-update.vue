<template>
	<div class="addEdit-block" :style='{"padding":"0px 0px 30px","fontSize":"14px","color":"#000","background":"none"}'>
		<el-form
			:style='{"border":"0px solid rgba(255,255,255,1)","padding":"30px 0 10px","borderRadius":"0 0 8px 8px","alignItems":"flex-start","flexWrap":"wrap","background":"rgba(255, 255, 255, 0)","display":"flex","fontSize":"inherit"}'
			class="add-update-preview"
			ref="ruleForm"
			:model="ruleForm"
			:rules="rules"
			label-width="150px"
		>
			<el-form-item :style='{"width":"48%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' class="input" v-if="type!='info'" label="项目编号" prop="xiangmubianhao">
				<el-input v-model="ruleForm.xiangmubianhao" placeholder="项目编号" readonly></el-input>
			</el-form-item>
			<el-form-item :style='{"width":"48%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' v-else-if="ruleForm.xiangmubianhao" class="input" label="项目编号" prop="xiangmubianhao">
				<el-input v-model="ruleForm.xiangmubianhao" placeholder="项目编号" readonly></el-input>
			</el-form-item>

			<el-form-item :style='{"width":"48%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' class="input" v-if="type!='info'" label="项目名称" prop="xiangmumingcheng">
				<el-input v-model="ruleForm.xiangmumingcheng" placeholder="请输入项目名称" clearable></el-input>
			</el-form-item>
			<el-form-item :style='{"width":"48%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' v-else-if="ruleForm.xiangmumingcheng" class="input" label="项目名称" prop="xiangmumingcheng">
				<el-input v-model="ruleForm.xiangmumingcheng" placeholder="项目名称" readonly></el-input>
			</el-form-item>

			<el-form-item :style='{"width":"48%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' class="select" v-if="type!='info'" label="项目类型" prop="xiangmuleixing">
				<el-select v-model="ruleForm.xiangmuleixing" placeholder="请选择项目类型">
					<el-option label="技术" value="技术"></el-option>
					<el-option label="采购" value="采购"></el-option>
					<el-option label="资金" value="资金"></el-option>
					<el-option label="农机" value="农机"></el-option>
					<el-option label="其他" value="其他"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item :style='{"width":"48%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' v-else-if="ruleForm.xiangmuleixing" class="input" label="项目类型" prop="xiangmuleixing">
				<el-input v-model="ruleForm.xiangmuleixing" placeholder="项目类型" readonly></el-input>
			</el-form-item>

			<el-form-item :style='{"width":"48%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' class="date" v-if="type!='info'" label="发布日期" prop="faburiqi">
				<el-date-picker
					format="yyyy 年 MM 月 dd 日"
					value-format="yyyy-MM-dd"
					v-model="ruleForm.faburiqi"
					type="date"
					placeholder="选择发布日期"
				></el-date-picker>
			</el-form-item>
			<el-form-item :style='{"width":"48%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' v-else-if="ruleForm.faburiqi" class="input" label="发布日期" prop="faburiqi">
				<el-input v-model="ruleForm.faburiqi" placeholder="发布日期" readonly></el-input>
			</el-form-item>

			<el-form-item :style='{"width":"48%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' class="input" v-if="type!='info'" label="联系人" prop="lianxiren">
				<el-input v-model="ruleForm.lianxiren" placeholder="请输入联系人姓名" clearable></el-input>
			</el-form-item>
			<el-form-item :style='{"width":"48%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' v-else-if="ruleForm.lianxiren" class="input" label="联系人" prop="lianxiren">
				<el-input v-model="ruleForm.lianxiren" placeholder="联系人" readonly></el-input>
			</el-form-item>

			<el-form-item :style='{"width":"48%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' class="input" v-if="type!='info'" label="联系电话" prop="lianxidianhua">
				<el-input v-model="ruleForm.lianxidianhua" placeholder="请输入联系电话" clearable></el-input>
			</el-form-item>
			<el-form-item :style='{"width":"48%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' v-else-if="ruleForm.lianxidianhua" class="input" label="联系电话" prop="lianxidianhua">
				<el-input v-model="ruleForm.lianxidianhua" placeholder="联系电话" readonly></el-input>
			</el-form-item>

			<el-form-item :style='{"width":"96%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' class="upload" v-if="type!='info'" label="项目图片" prop="tupian">
				<file-upload
					tip="点击上传项目图片（建议尺寸 800x600）"
					action="file/upload"
					:limit="1"
					:multiple="false"
					:fileUrls="ruleForm.tupian ? ruleForm.tupian : ''"
					@change="tupianUploadChange"
				></file-upload>
			</el-form-item>
			<el-form-item :style='{"width":"48%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' v-else-if="ruleForm.tupian" class="upload" label="项目图片" prop="tupian">
				<img v-if="ruleForm.tupian.substring(0,4)=='http'" class="upload-img" style="max-width:200px;" :src="ruleForm.tupian">
				<img v-else class="upload-img" style="max-width:200px;" :src="$base.url+ruleForm.tupian">
			</el-form-item>

			<el-form-item :style='{"width":"96%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' v-if="type!='info'" label="项目详情" prop="xiangmuxiangqing">
				<editor
					style="min-width: 200px; max-width: 100%;"
					v-model="ruleForm.xiangmuxiangqing"
					class="editor"
					action="file/upload">
				</editor>
			</el-form-item>
			<el-form-item :style='{"width":"96%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' v-else-if="ruleForm.xiangmuxiangqing" label="项目详情" prop="xiangmuxiangqing">
				<div v-html="ruleForm.xiangmuxiangqing" style="background:#fff;padding:15px;border-radius:4px;min-height:100px;"></div>
			</el-form-item>

			<el-form-item :style='{"padding":"0 10px","margin":"30px 0","alignItems":"center","textAlign":"center","display":"flex","width":"100%","justifyContent":"flex-end"}' class="btn">
				<el-button class="btn3" v-if="type!='info'" type="success" @click="onSubmit">
					<span class="icon iconfont icon-tijiao16" :style='{"margin":"0 2px","fontSize":"18px","color":"inherit","display":"none"}'></span>
					提交
				</el-button>
				<el-button class="btn4" v-if="type!='info'" type="info" @click="back()">
					<span class="icon iconfont icon-quxiao09" :style='{"margin":"0 2px","fontSize":"18px","color":"inherit","display":"none"}'></span>
					取消
				</el-button>
				<el-button class="btn5" v-if="type=='info'" type="success" @click="back()">
					<span class="icon iconfont icon-fanhui01" :style='{"margin":"0 2px","fontSize":"18px","color":"inherit","display":"none"}'></span>
					返回
				</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>
<script>
import { isNumber, isIntNumer, isEmail, isPhone, isMobile, isURL, checkIdCard } from "@/utils/validate";
export default {
	data() {
		return {
			id: '',
			type: '',
			ro: {
				xiangmubianhao: true,
				xiangmumingcheng: false,
				xiangmuleixing: false,
				tupian: false,
				faburiqi: false,
				xiangmuxiangqing: false,
				lianxiren: false,
				lianxidianhua: false,
				clicktime: false,
				clicknum: false,
			},
			ruleForm: {
				xiangmubianhao: '',
				xiangmumingcheng: '',
				xiangmuleixing: '',
				tupian: '',
				faburiqi: '',
				xiangmuxiangqing: '',
				lianxiren: '',
				lianxidianhua: '',
			},
			rules: {
				xiangmubianhao: [],
				xiangmumingcheng: [{ required: true, message: '请输入项目名称', trigger: 'blur' }],
				xiangmuleixing: [{ required: true, message: '请选择项目类型', trigger: 'change' }],
				faburiqi: [],
				lianxiren: [{ required: true, message: '请输入联系人', trigger: 'blur' }],
				lianxidianhua: [
					{ required: true, message: '请输入联系电话', trigger: 'blur' },
					{ validator: isMobile, trigger: 'blur' }
				],
			}
		};
	},
	props: ["parent"],
	components: {},
	created() {
		// 初始生成编号
		if (!this.ruleForm.xiangmubianhao) {
			this.ruleForm.xiangmubianhao = 'XM' + new Date().getTime();
		}
		if (!this.ruleForm.faburiqi) {
			this.ruleForm.faburiqi = this.getCurDate();
		}
	},
	methods: {
		download(file) { window.open(`${file}`) },
		init(id, type) {
			if (id) {
				this.id = id;
				this.type = type;
			}
			if (this.type == 'info' || this.type == 'else') {
				this.info(id);
			} else if (this.type == 'cross') {
				var obj = this.$storage.getObj('crossObj');
				for (var o in obj) {
					if (o == 'xiangmubianhao') { this.ruleForm.xiangmubianhao = obj[o]; this.ro.xiangmubianhao = true; continue; }
					if (o == 'xiangmumingcheng') { this.ruleForm.xiangmumingcheng = obj[o]; continue; }
					if (o == 'xiangmuleixing') { this.ruleForm.xiangmuleixing = obj[o]; continue; }
					if (o == 'tupian') { this.ruleForm.tupian = obj[o]; continue; }
					if (o == 'faburiqi') { this.ruleForm.faburiqi = obj[o]; continue; }
					if (o == 'xiangmuxiangqing') { this.ruleForm.xiangmuxiangqing = obj[o]; continue; }
					if (o == 'lianxiren') { this.ruleForm.lianxiren = obj[o]; continue; }
					if (o == 'lianxidianhua') { this.ruleForm.lianxidianhua = obj[o]; continue; }
					if (o == 'clicknum') { this.ruleForm.clicknum = obj[o]; continue; }
				}
			}
		},
		info(id) {
			this.$http({ url: 'bangfuxiangmu/info/' + id, method: 'get' }).then(({ data }) => {
				if (data && data.code === 0) {
					this.ruleForm = data.data;
				} else {
					this.$message.error(data.msg);
				}
			});
		},
		tupianUploadChange(file, files) {
			this.ruleForm.tupian = file;
		},
		onSubmit() {
			this.$refs.ruleForm.validate(valid => {
				if (!valid) return;
				this.$http({
					url: !this.id ? 'bangfuxiangmu/save' : 'bangfuxiangmu/update',
					method: 'post',
					data: this.ruleForm
				}).then(({ data }) => {
					if (data && data.code === 0) {
						this.$message({ message: '操作成功', type: 'success', duration: 1500 });
						this.$emit('refreshDataList');
						if (this.parent) {
							this.parent.closeDialog();
						}
					} else {
						this.$message.error(data.msg);
					}
				});
			});
		},
		back() {
			if (this.parent) this.parent.closeDialog();
		},
		getUUID() { return 'XM' + Date.now() + Math.floor(Math.random() * 1000); },
		getCurDate() {
			var d = new Date();
			return d.getFullYear() + '-' + String(d.getMonth() + 1).padStart(2, '0') + '-' + String(d.getDate()).padStart(2, '0');
		}
	}
}
</script>
