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
			<el-form-item :style='{"width":"48%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' class="input" v-if="type!='info'" label="申请编号" prop="shenqingbianhao">
				<el-input v-model="ruleForm.shenqingbianhao" placeholder="申请编号" readonly></el-input>
			</el-form-item>
			<el-form-item :style='{"width":"48%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' v-else-if="ruleForm.shenqingbianhao" class="input" label="申请编号" prop="shenqingbianhao">
				<el-input v-model="ruleForm.shenqingbianhao" placeholder="申请编号" readonly></el-input>
			</el-form-item>

			<el-form-item :style='{"width":"48%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' class="input" v-if="type!='info'" label="项目编号" prop="xiangmubianhao">
				<el-input v-model="ruleForm.xiangmubianhao" placeholder="请输入项目编号" clearable></el-input>
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

			<el-form-item :style='{"width":"48%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' class="input" v-if="type!='info'" label="申请人账号" prop="shenqingrenzhanghao">
				<el-input v-model="ruleForm.shenqingrenzhanghao" placeholder="请输入申请人账号" clearable></el-input>
			</el-form-item>
			<el-form-item :style='{"width":"48%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' v-else-if="ruleForm.shenqingrenzhanghao" class="input" label="申请人账号" prop="shenqingrenzhanghao">
				<el-input v-model="ruleForm.shenqingrenzhanghao" placeholder="申请人账号" readonly></el-input>
			</el-form-item>

			<el-form-item :style='{"width":"48%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' class="input" v-if="type!='info'" label="申请人姓名" prop="shenqingrenxingming">
				<el-input v-model="ruleForm.shenqingrenxingming" placeholder="请输入申请人姓名" clearable></el-input>
			</el-form-item>
			<el-form-item :style='{"width":"48%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' v-else-if="ruleForm.shenqingrenxingming" class="input" label="申请人姓名" prop="shenqingrenxingming">
				<el-input v-model="ruleForm.shenqingrenxingming" placeholder="申请人姓名" readonly></el-input>
			</el-form-item>

			<el-form-item :style='{"width":"48%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' class="date" v-if="type!='info'" label="申请日期" prop="shenqingriqi">
				<el-date-picker format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd" v-model="ruleForm.shenqingriqi" type="date" placeholder="选择申请日期"></el-date-picker>
			</el-form-item>
			<el-form-item :style='{"width":"48%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' v-else-if="ruleForm.shenqingriqi" class="input" label="申请日期" prop="shenqingriqi">
				<el-input v-model="ruleForm.shenqingriqi" placeholder="申请日期" readonly></el-input>
			</el-form-item>

			<el-form-item :style='{"width":"96%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' class="textarea" v-if="type!='info'" label="申请说明" prop="shenqingshuoming">
				<el-input type="textarea" :rows="5" placeholder="请输入申请说明" v-model="ruleForm.shenqingshuoming"></el-input>
			</el-form-item>
			<el-form-item :style='{"width":"96%","margin":"0 0 30px 0","fontSize":"inherit","color":"inherit"}' v-else-if="ruleForm.shenqingshuoming" label="申请说明" prop="shenqingshuoming">
				<span :style='{"padding":"0 20px","color":"inherit","background":"#fff","display":"inline-block","width":"80%","fontSize":"14px","lineHeight":"40px"}'>{{ruleForm.shenqingshuoming}}</span>
			</el-form-item>

			<el-form-item :style='{"padding":"0 10px","margin":"30px 0","alignItems":"center","textAlign":"center","display":"flex","width":"100%","justifyContent":"flex-end"}' class="btn">
				<el-button class="btn3" v-if="type!='info'" type="success" @click="onSubmit">提交</el-button>
				<el-button class="btn4" v-if="type!='info'" type="info" @click="back()">取消</el-button>
				<el-button class="btn5" v-if="type=='info'" type="success" @click="back()">返回</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>
<script>
export default {
	data() {
		return {
			id: '', type: '',
			ro: { shenqingbianhao: true, xiangmubianhao: false, xiangmumingcheng: false, shenqingrenzhanghao: false, shenqingrenxingming: false, shenqingshuoming: false, shenqingriqi: false, sfsh: false, shhf: false, userid: false },
			ruleForm: { shenqingbianhao: '', xiangmubianhao: '', xiangmumingcheng: '', shenqingrenzhanghao: '', shenqingrenxingming: '', shenqingshuoming: '', shenqingriqi: '', sfsh: '待审核', shhf: '' },
			rules: {
				shenqingbianhao: [{ required: true, message: '请输入申请编号', trigger: 'blur' }],
				shenqingrenzhanghao: [{ required: true, message: '请输入申请人账号', trigger: 'blur' }],
				shenqingrenxingming: [{ required: true, message: '请输入申请人姓名', trigger: 'blur' }],
			}
		};
	},
	props: ["parent"],
	components: {},
	created() {
		if (!this.ruleForm.shenqingbianhao) this.ruleForm.shenqingbianhao = 'DJ' + new Date().getTime();
		if (!this.ruleForm.shenqingriqi) this.ruleForm.shenqingriqi = this.getCurDate();
	},
	methods: {
		init(id, type) {
			if (id) { this.id = id; this.type = type; }
			if (this.type == 'info' || this.type == 'else') { this.info(id); }
		},
		info(id) {
			this.$http({ url: 'duijieshenqing/info/' + id, method: 'get' }).then(({ data }) => {
				if (data && data.code === 0) { this.ruleForm = data.data; }
				else { this.$message.error(data.msg); }
			});
		},
		onSubmit() {
			this.$refs.ruleForm.validate(valid => {
				if (!valid) return;
				this.$http({ url: !this.id ? 'duijieshenqing/save' : 'duijieshenqing/update', method: 'post', data: this.ruleForm }).then(({ data }) => {
					if (data && data.code === 0) {
						this.$message({ message: '操作成功', type: 'success', duration: 1500 });
						this.$emit('refreshDataList');
						if (this.parent) this.parent.closeDialog();
					} else { this.$message.error(data.msg); }
				});
			});
		},
		back() { if (this.parent) this.parent.closeDialog(); },
		getCurDate() {
			var d = new Date();
			return d.getFullYear() + '-' + String(d.getMonth() + 1).padStart(2, '0') + '-' + String(d.getDate()).padStart(2, '0');
		}
	}
}
</script>
