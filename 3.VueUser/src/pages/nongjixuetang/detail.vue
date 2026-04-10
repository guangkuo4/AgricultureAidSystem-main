<template>
<div class="nongji-detail-page">
	<div class="nongji-detail__breadcrumb breadcrumb-preview">
		<div class="nongji-detail__breadcrumb-inner">
			<el-breadcrumb separator=">">
				<el-breadcrumb-item class="item1" to="/"><a>首页</a></el-breadcrumb-item>
				<el-breadcrumb-item class="item2" v-for="(item, index) in breadcrumbItem" :key="index" to="/index/nongjixuetang"><a>{{item.name}}</a></el-breadcrumb-item>
				<el-breadcrumb-item class="item3"><a href="javascript:void(0);">详情</a></el-breadcrumb-item>
			</el-breadcrumb>
			<el-button type="text" class="nongji-detail__back" icon="el-icon-back" @click="backClick">返回</el-button>
		</div>
	</div>

	<div class="detail-preview nongji-detail__wrap">
		<div class="nongji-detail__card">
			<div class="nongji-detail__grid">
				<div class="nongji-detail__media" v-if="detailBanner.length">
					<div class="nongji-detail__media-main">
						<img id="big" class="nongji-detail__media-img" :src="swiperBigUrl" alt="课程图片">
					</div>
					<div class="nongji-detail__thumbs">
						<button
							type="button"
							class="nongji-detail__thumb"
							v-for="(item, idx) in detailBanner"
							:key="idx"
							@click="item.substr(0,4)=='http' ? swiperClick3(item) : swiperClick3(baseUrl + item)"
						>
							<img
								class="nongji-detail__thumb-img"
								:src="item.substr(0,4)=='http' ? item : (baseUrl + item)"
								alt=""
							>
						</button>
					</div>
				</div>
				<div class="nongji-detail__media nongji-detail__media--empty" v-else>
					<div class="nongji-detail__media-placeholder">
						<i class="el-icon-picture-outline"></i>
						<span>暂无课程配图</span>
					</div>
				</div>

				<div class="nongji-detail__meta attr">
					<div class="nongji-detail__title-row">
						<h1 class="nongji-detail__title">{{ detail.kechengbiaoti }}</h1>
						<div
							class="nongji-detail__fav"
							v-show="!isStoreup"
							@click="storeup(1)"
						>
							<i class="el-icon-star-off"></i>
							<span>收藏 ({{ detail.storeupnum }})</span>
						</div>
						<div
							class="nongji-detail__fav nongji-detail__fav--on"
							v-show="isStoreup"
							@click="storeup(-1)"
						>
							<i class="el-icon-star-on"></i>
							<span>已收藏 ({{ detail.storeupnum }})</span>
						</div>
					</div>

					<dl class="nongji-detail__dl">
						<div class="nongji-detail__row">
							<dt>课程分类</dt>
							<dd>{{ detail.kechengfenlei }}</dd>
						</div>
						<div class="nongji-detail__row">
							<dt>课程简介</dt>
							<dd>{{ detail.kechengjianjie }}</dd>
						</div>
						<div class="nongji-detail__row nongji-detail__row--actions">
							<dt>文档资料</dt>
							<dd>
								<el-button type="primary" size="small" class="nongji-detail__download" @click="download(detail.wendangziliao)">下载资料</el-button>
							</dd>
						</div>
					</dl>

					<div class="nongji-detail__admin" v-if="btnAuth('nongjixuetang','修改') || btnAuth('nongjixuetang','删除')">
						<el-button type="primary" size="small" v-if="btnAuth('nongjixuetang','修改')" @click="editClick">修改</el-button>
						<el-button type="danger" plain size="small" v-if="btnAuth('nongjixuetang','删除')" @click="delClick">删除</el-button>
					</div>
				</div>
			</div>

			<div class="nongji-detail__video" v-if="detail.kechengshipin">
				<div class="nongji-detail__video-label"><i class="el-icon-video-camera"></i> 课程视频</div>
				<div class="nongji-detail__video-box">
					<video class="nongji-detail__video-el" :src="baseUrl + detail.kechengshipin" controls playsinline>
						您的浏览器不支持视频播放
					</video>
				</div>
			</div>
		</div>

		<el-tabs class="detail nongji-detail__tabs" v-model="activeName">
			<el-tab-pane label="课程详情" name="first">
				<div class="nongji-detail__rich" v-html="detail.kechengxiangqing"></div>
			</el-tab-pane>
			<el-tab-pane label="评论" name="second">
				<el-form class="add comment nongji-detail__comment-form" :model="form" :rules="rules" ref="form">
					<el-form-item class="item" label="评论" prop="content">
						<editor
							v-model="form.content"
							class="editor"
							action="file/upload">
						</editor>
					</el-form-item>
					<el-form-item class="btn">
						<el-button type="primary" @click="submitForm('form')">立即提交</el-button>
						<el-button @click="resetForm('form')">重置</el-button>
					</el-form-item>
				</el-form>

				<div v-if="infoList.length" class="comment">
					<div
						class="nongji-detail__comment-card"
						v-for="item in infoList"
						:key="item.id"
						@mouseenter="discussEnter(item.id)"
						@mouseleave="discussLeave"
					>
						<div class="user">
							<el-image v-if="item.avatarurl" :size="50" :src="baseUrl + item.avatarurl"></el-image>
							<el-image v-if="!item.avatarurl" :size="50" :src="require('@/assets/touxiang.png')"></el-image>
							<div class="name">{{ item.nickname }}</div>
						</div>
						<div class="content-block-ask">
							<div v-html="item.content"></div>
							<div class="btn">
								<el-button size="small" v-if="showIndex==item.id&&userid==item.userid" @click="discussDel(item.id)">删除</el-button>
							</div>
						</div>
						<div class="content-block-reply" v-if="item.reply">
							回复：<span v-html="item.reply"></span>
						</div>
					</div>
				</div>

				<el-pagination
					background
					id="pagination"
					class="pagination"
					:pager-count="7"
					:page-size="pageSize"
					:page-sizes="pageSizes"
					prev-text="上一页"
					next-text="下一页"
					:hide-on-single-page="false"
					:layout='["prev","pager","next"].join()'
					:total="total"
					@current-change="curChange"
					@prev-click="prevClick"
					@next-click="nextClick"
				></el-pagination>
			</el-tab-pane>
		</el-tabs>
	</div>
	<div class="share_view"></div>
</div>
</template>

<script>
  import CountDown from '@/components/CountDown';
  import axios from 'axios'
  import Swiper from "swiper";
  
  export default {
    //数据集合
    data() {
      return {
        tablename: 'nongjixuetang',
        baseUrl: '',
        breadcrumbItem: [
          {
            name: '助农乐学'
          }
        ],
        title: '',
        detailBanner: [],
		id: 0,
        detail: {},
        activeName: 'first',
		userid: localStorage.getItem('frontUserid'),
        form: {
          content: '',
          userid: localStorage.getItem('frontUserid'),
          nickname: localStorage.getItem('username'),
          avatarurl: '',
        },
		showIndex: -1,
        infoList: [],
        rules: {
          content: [
            { required: true, message: '请输入内容', trigger: 'blur' }
          ],
        },
        total: 1,
        pageSize: 5,
		pageSizes: [10,20,30,50],
        totalPage: 1,
        storeupParams: {
          name: '',
          picture: '',
          refid: 0,
          tablename: 'nongjixuetang',
          userid: localStorage.getItem('frontUserid')
        },
        isStoreup: false,
        storeupInfo: {},
        buynumber: 1,
		centerType: false,
		shareUrl: location.href,
		swiperBigUrl: null,
      }
    },
    created() {
		if(this.$route.query.centerType) {
			this.centerType = true
		}
		
        this.init();
    },
	mounted() {
	},
    //方法集合
    methods: {
		swiperClick3(src) {
			this.swiperBigUrl = src
		},
        init() {
		  this.id = this.$route.query.id
          this.baseUrl = this.$config.baseUrl;
          this.$http.get(this.tablename + '/detail/'  + this.id, {}).then(res => {
            if (res.data.code == 0) {
              this.detail = res.data.data;
              this.title = this.detail.kechengbiaoti;
              this.detailBanner = this.detail.kechengzhaopian ? this.detail.kechengzhaopian.split(",") : [];
              this.$forceUpdate();

			  this.getDiscussList(1);
				if(localStorage.getItem('frontToken')){
					this.getStoreupStatus();
				}

            }
			if (this.detailBanner.length) {
				if (this.detailBanner[0].substr(0,4)=='http') {
					this.swiperBigUrl = this.detailBanner[0]
				} else {
					this.swiperBigUrl = this.baseUrl + this.detailBanner[0]
				}
			}
          });
        },
      storeup(type) {
        if (type == 1 && !this.isStoreup) {
          this.storeupParams.name = this.title;
          this.storeupParams.picture = this.detailBanner[0];
          this.storeupParams.refid = this.detail.id;
          this.storeupParams.type = type;
          this.$http.post('storeup/add', this.storeupParams).then(res => {
            if (res.data.code == 0) {
              this.isStoreup = true;
			  this.detail.storeupnum++
			  this.$http.post('nongjixuetang/update', this.detail).then(res => {});
              this.$message({
                type: 'success',
                message: '收藏成功!',
                duration: 1500,
              });
            }
          });
        }
        if (type == -1 && this.isStoreup) {
          this.$http.get('storeup/list', {params: {page: 1, limit: 1, type: 1, refid: this.detail.id, tablename: 'nongjixuetang', userid: localStorage.getItem('frontUserid')}}).then(res => {
            if (res.data.code == 0 && res.data.data.list.length > 0) {
              this.isStoreup = true;
              this.storeupInfo = res.data.data.list[0];
              let delIds = new Array();
              delIds.push(this.storeupInfo.id);
              this.$http.post('storeup/delete', delIds).then(res => {
                if (res.data.code == 0) {
                  this.isStoreup = false;
				  this.detail.storeupnum--
				  this.$http.post('nongjixuetang/update', this.detail).then(res => {});
                  this.$message({
                    type: 'success',
                    message: '取消成功!',
                    duration: 1500,
                  });
                }
              });
            }
          });
        }
      },
      getStoreupStatus(){
        if(localStorage.getItem("frontToken")) {
            this.$http.get('storeup/list', {params: {page: 1, limit: 1, type: 1, refid: this.detail.id, tablename: 'nongjixuetang', userid: localStorage.getItem('frontUserid')}}).then(res => {
              if (res.data.code == 0 && res.data.data.list.length > 0) {
                this.isStoreup = true;
                this.storeupInfo = res.data.data.list[0];
              }
            });
        }
      },
      curChange(page) {
        this.getDiscussList(page);
      },
      prevClick(page) {
        this.getDiscussList(page);
      },
      nextClick(page) {
        this.getDiscussList(page);
      },
		// 返回按钮
		backClick(){
			history.back()
		},
		// 下载
		download(file){
			if(!file) {
				this.$message({
				  type: 'error',
				  message: '文件不存在',
				  duration: 1500,
				});
				return;
			}
		  let arr = file.replace(new RegExp('upload/', "g"), "")
		  axios.get(this.baseUrl + '/file/download?fileName=' + arr, {
		  	headers: {
		  		token: localStorage.getItem("frontToken")
		  	},
		  	responseType: "blob"
		  }).then(({
		  	data
		  }) => {
		  	const binaryData = [];
		  	binaryData.push(data);
		  	const objectUrl = window.URL.createObjectURL(new Blob(binaryData, {
		  		type: 'application/pdf;chartset=UTF-8'
		  	}))
		  	const a = document.createElement('a')
		  	a.href = objectUrl
		  	a.download = arr
		  	// a.click()
		  	// 下面这个写法兼容火狐
		  	a.dispatchEvent(new MouseEvent('click', {
		  		bubbles: true,
		  		cancelable: true,
		  		view: window
		  	}))
		  	window.URL.revokeObjectURL(data)
		  },err=>{
			  axios.get((location.href.split(this.$config.name).length>1 ? location.href.split(this.$config.name)[0] :'') + this.$config.name + '/file/download?fileName=' + arr, {
			  	headers: {
			  		token: localStorage.getItem("frontToken")
			  	},
			  	responseType: "blob"
			  }).then(({
			  	data
			  }) => {
			  	const binaryData = [];
			  	binaryData.push(data);
			  	const objectUrl = window.URL.createObjectURL(new Blob(binaryData, {
			  		type: 'application/pdf;chartset=UTF-8'
			  	}))
			  	const a = document.createElement('a')
			  	a.href = objectUrl
			  	a.download = arr
			  	// a.click()
			  	// 下面这个写法兼容火狐
			  	a.dispatchEvent(new MouseEvent('click', {
			  		bubbles: true,
			  		cancelable: true,
			  		view: window
			  	}))
			  	window.URL.revokeObjectURL(data)
			  })
		  })
      },
      getDiscussList(page) {
        this.$http.get('discussnongjixuetang/list', {params: {page, limit: this.pageSize, refid: this.detail.id}}).then(res => {
          if (res.data.code == 0) {
            this.infoList = res.data.data.list;
            this.total = res.data.data.total;
            this.pageSize = res.data.data.pageSize;this.pageSizes = [this.pageSize, this.pageSize*2, this.pageSize*3, this.pageSize*5];
            this.totalPage = res.data.data.totalPage;
          }
        });
      },
	  discussEnter(index){
		  this.showIndex = index
	  },
	  discussLeave(){
		  this.showIndex = -1
	  },
	  discussDel(id){
		  this.$confirm('是否删除此评论？')
		    .then(_ => {
		      this.$http.post('discussnongjixuetang/delete',[id]).then(res=>{
				  if(res.data&&res.data.code==0){
					  this.addDiscussNum(1)
					  this.$message({
					    type: 'success',
					    message: '删除成功!',
					    duration: 1500,
						onClose: () => {
							this.getDiscussList(1);
						}
					  });
				  }
			  })
		    }).catch(_ => {});
	  },
      submitForm(formName) {
        let sensitiveWords = "";
        let sensitiveWordsArr = [];
        if(sensitiveWords) {
            sensitiveWordsArr = sensitiveWords.split(",");
        }
        for(var i=0; i<sensitiveWordsArr.length; i++){
            //全局替换
            var reg = new RegExp(sensitiveWordsArr[i],"g");
            //判断内容中是否包括敏感词
            if (this.form.content.indexOf(sensitiveWordsArr[i]) > -1) {
                // 将敏感词替换为 **
                this.form.content = this.form.content.replace(reg,"**");
            }
        }
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.form.refid = this.detail.id;
            this.form.avatarurl = localStorage.getItem('frontHeadportrait')?localStorage.getItem('frontHeadportrait'):'';
            this.$http.post('discussnongjixuetang/add', this.form).then(res => {
              if (res.data.code == 0) {
                this.form.content = '';
				this.addDiscussNum(2)
                this.getDiscussList(1);
                this.$message({
                  type: 'success',
                  message: '评论成功!',
                  duration: 1500,
                });
              }
            });
          } else {
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
	  addDiscussNum(type){
		  if(type==2){
			  this.detail.discussnum++
		  }else if(type==1){
			  if(this.detail.discussnum!=0){
				  this.detail.discussnum--
			  }else{
				  this.detail.discussnum = 0
			  }
		  }
		  this.$http.post('nongjixuetang/update',this.detail).then(res=>{
			  
		  })
	  },


		// 权限判断
		btnAuth(tableName,key){
			if(this.centerType){
				return this.isBackAuth(tableName,key)
			}else{
				return this.isAuth(tableName,key)
			}
		},
		// 修改
		editClick(){
			this.$router.push(`/index/nongjixuetangAdd?type=edit&&id=${this.detail.id}`);
		},
		// 删除
		delClick(){
			this.$confirm('是否删除此助农乐学？')
			  .then(_ => {
			    this.$http.post('nongjixuetang/delete', [this.detail.id]).then(res => {
			      if (res.data.code == 0) {
			        this.$message({
			          type: 'success',
			          message: '删除成功!',
			          duration: 1500,
					  onClose: () => {
						  history.back()
					  }
			        });
			      }
			    });
			  }).catch(_ => {});
		},
    },
    components: {
      CountDown
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
$ag-green-800: #2e7d32;
$ag-green-700: #388e3c;
$ag-green-500: #4caf50;
$ag-green-100: #e8f5e9;
$ag-green-050: #f1f8e9;
$ag-border: rgba(46, 125, 50, 0.14);
$ag-text: #1a2e1a;
$ag-muted: #607060;

.nongji-detail-page {
	min-height: 40vh;
	padding-bottom: 24px;
}

.nongji-detail__breadcrumb {
	width: 100%;
	padding: 22px 10%;
	margin: 0 auto;
	background: linear-gradient(180deg, rgba(80, 160, 80, 0.92), rgba(46, 125, 46, 1));
	box-shadow: 0 4px 12px rgba(27, 94, 32, 0.2);
	border-bottom: 1px solid rgba(255, 255, 255, 0.2);
}

.nongji-detail__breadcrumb-inner {
	display: flex;
	align-items: center;
	justify-content: space-between;
	flex-wrap: wrap;
	gap: 12px;
	max-width: 1400px;
	margin: 0 auto;
}

.nongji-detail__back {
	color: #fff !important;
	font-weight: 600;
	padding: 6px 12px !important;
	border-radius: 8px;
	background: rgba(255, 255, 255, 0.12) !important;

	&:hover {
		background: rgba(255, 255, 255, 0.22) !important;
		color: #fff !important;
	}
}

.breadcrumb-preview ::v-deep .el-breadcrumb__separator {
	margin: 0 10px;
	color: rgba(255, 255, 255, 0.85);
	font-weight: 500;
}

.breadcrumb-preview ::v-deep .el-breadcrumb__inner a,
.breadcrumb-preview ::v-deep .el-breadcrumb__inner.is-link {
	color: #fff !important;
	font-weight: 500;
}

.detail-preview.nongji-detail__wrap {
	width: 100%;
	max-width: 1600px;
	margin: 32px auto 0;
	padding: 0 32px;
	box-sizing: border-box;
}

.nongji-detail__card {
	background: linear-gradient(135deg, #ffffff 0%, #f8faf8 100%);
	border-radius: 20px;
	border: 1px solid $ag-border;
	box-shadow: 0 12px 40px rgba(46, 125, 50, 0.12);
	padding: 40px;
	position: relative;
	overflow: hidden;

	&::before {
		content: '';
		position: absolute;
		top: 0;
		left: 0;
		right: 0;
		height: 4px;
		background: linear-gradient(90deg, $ag-green-500, $ag-green-700);
		border-radius: 20px 20px 0 0;
	}
}

.nongji-detail__grid {
	display: grid;
	grid-template-columns: minmax(0, 1.2fr) minmax(0, 1fr);
	gap: 32px;
	align-items: start;
}

.nongji-detail__media {
	min-width: 0;
}

.nongji-detail__media-main {
	aspect-ratio: 16 / 9;
	max-height: 500px;
	border-radius: 16px;
	overflow: hidden;
	background: $ag-green-050;
	border: 1px solid $ag-border;
	box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
	transition: all 0.3s ease;

	&:hover {
		transform: translateY(-4px);
		box-shadow: 0 12px 32px rgba(46, 125, 50, 0.15);
	}
}

.nongji-detail__media-img {
	width: 100%;
	height: 100%;
	object-fit: cover;
	display: block;
	transition: transform 0.6s ease;

	&:hover {
		transform: scale(1.05);
	}
}

.nongji-detail__thumbs {
	display: flex;
	flex-wrap: wrap;
	gap: 12px;
	margin-top: 16px;
	padding: 16px;
	background: rgba(241, 248, 233, 0.5);
	border-radius: 12px;
	border: 1px solid $ag-border;
}

.nongji-detail__thumb {
	padding: 0;
	border: 3px solid transparent;
	border-radius: 10px;
	overflow: hidden;
	width: 80px;
	height: 80px;
	cursor: pointer;
	background: #fff;
	transition: all 0.3s ease;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);

	&:hover {
		border-color: $ag-green-500;
		transform: translateY(-2px);
		box-shadow: 0 6px 16px rgba(46, 125, 50, 0.15);
	}
}

.nongji-detail__thumb-img {
	width: 100%;
	height: 100%;
	object-fit: cover;
	display: block;
	transition: transform 0.3s ease;

	&:hover {
		transform: scale(1.1);
	}
}

.nongji-detail__media--empty .nongji-detail__media-placeholder {
	aspect-ratio: 16 / 9;
	max-height: 500px;
	border-radius: 12px;
	border: 1px dashed $ag-border;
	background: linear-gradient(145deg, $ag-green-050, #fff);
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	gap: 10px;
	color: $ag-muted;
	font-size: 14px;

	i {
		font-size: 48px;
		color: rgba($ag-green-500, 0.45);
	}
}

.nongji-detail__meta {
	background: linear-gradient(135deg, #ffffff 0%, #f8faf8 100%);
	border: 1px solid $ag-border;
	border-radius: 16px;
	padding: 32px 36px;
	box-sizing: border-box;
	min-height: 100%;
	box-shadow: 0 6px 20px rgba(46, 125, 50, 0.06);
	position: relative;

	&::before {
		content: '';
		position: absolute;
		top: 24px;
		left: 24px;
		width: 4px;
		height: 32px;
		background: linear-gradient(180deg, $ag-green-500, $ag-green-700);
		border-radius: 4px;
	}
}

.nongji-detail__title-row {
	display: flex;
	flex-wrap: wrap;
	align-items: flex-start;
	justify-content: space-between;
	gap: 12px;
	margin-bottom: 18px;
}

.nongji-detail__title {
	margin: 0 0 0 20px;
	font-size: 28px;
	font-weight: 800;
	color: $ag-text;
	line-height: 1.35;
	flex: 1;
	min-width: 200px;
	text-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.nongji-detail__fav {
	display: inline-flex;
	align-items: center;
	gap: 8px;
	padding: 10px 20px;
	border-radius: 999px;
	font-size: 14px;
	font-weight: 600;
	cursor: pointer;
	user-select: none;
	background: linear-gradient(135deg, $ag-green-500, $ag-green-800);
	color: #fff;
	white-space: nowrap;
	transition: all 0.3s ease;
	box-shadow: 0 6px 16px rgba(46, 125, 50, 0.25);

	&:hover {
		filter: brightness(1.1);
		transform: translateY(-2px);
		box-shadow: 0 8px 20px rgba(46, 125, 50, 0.35);
	}

	&--on {
		background: linear-gradient(135deg, $ag-green-700, #1b5e20);
		box-shadow: 0 6px 16px rgba(27, 94, 32, 0.35);
	}
}

.nongji-detail__dl {
	margin: 0;
}

.nongji-detail__row {
	display: grid;
	grid-template-columns: 100px 1fr;
	gap: 16px 20px;
	padding: 16px 0 16px 20px;
	border-bottom: 1px solid rgba(46, 125, 50, 0.08);
	font-size: 15px;
	line-height: 1.6;

	&:last-child {
		border-bottom: none;
	}

	dt {
		margin: 0;
		color: $ag-muted;
		font-weight: 700;
		font-size: 14px;
		text-transform: uppercase;
		letter-spacing: 0.5px;
	}

	dd {
		margin: 0;
		color: #2f3a2f;
		word-break: break-word;
		font-size: 16px;
	}
}

.nongji-detail__row--actions dd {
	display: flex;
	align-items: center;

	.el-button {
		background: linear-gradient(135deg, $ag-green-500, $ag-green-700) !important;
		border: none !important;
		color: #fff !important;
		font-weight: 600 !important;
		border-radius: 8px !important;
		padding: 8px 20px !important;
		box-shadow: 0 6px 16px rgba(46, 125, 50, 0.25) !important;
		transition: all 0.3s ease !important;

		&:hover {
			filter: brightness(1.1) !important;
			transform: translateY(-2px) !important;
			box-shadow: 0 8px 20px rgba(46, 125, 50, 0.35) !important;
		}
	}
}

.nongji-detail__admin {
	display: flex;
	flex-wrap: wrap;
	gap: 10px;
	margin-top: 16px;
	padding-top: 16px;
	border-top: 1px solid rgba(46, 125, 50, 0.1);
}

.nongji-detail__video {
	margin-top: 40px;
	padding: 32px;
	border: 1px solid $ag-border;
	border-radius: 16px;
	background: linear-gradient(135deg, #ffffff 0%, #f8faf8 100%);
	box-shadow: 0 8px 24px rgba(46, 125, 50, 0.08);
}

.nongji-detail__video-label {
	display: flex;
	align-items: center;
	gap: 10px;
	font-size: 18px;
	font-weight: 700;
	color: $ag-green-800;
	margin-bottom: 20px;
	padding-bottom: 16px;
	border-bottom: 1px solid rgba(46, 125, 50, 0.1);

	i {
		font-size: 22px;
		color: $ag-green-500;
		padding: 8px;
		background: rgba(76, 175, 80, 0.1);
		border-radius: 8px;
	}
}

.nongji-detail__video-box {
	border-radius: 16px;
	overflow: hidden;
	border: 1px solid $ag-border;
	background: #0d0d0d;
	box-shadow: 0 12px 32px rgba(0, 0, 0, 0.15);
	transition: all 0.3s ease;

	&:hover {
		transform: translateY(-4px);
		box-shadow: 0 16px 40px rgba(0, 0, 0, 0.2);
	}
}

.nongji-detail__video-el {
	display: block;
	width: 100%;
	aspect-ratio: 16 / 9;
	min-height: 450px;
	background: #000;
	border-radius: 16px;
}

.nongji-detail__tabs {
	margin-top: 40px;
	border: 1px solid $ag-border !important;
	border-radius: 16px !important;
	overflow: hidden;
	box-shadow: 0 8px 24px rgba(46, 125, 50, 0.08);
	background: linear-gradient(135deg, #ffffff 0%, #f8faf8 100%);
}

.nongji-detail__tabs ::v-deep .el-tabs__header {
	margin: 0;
	background: linear-gradient(180deg, $ag-green-050, #fff);
	border-bottom: 1px solid $ag-border;
	padding: 0 24px;
}

.nongji-detail__tabs ::v-deep .el-tabs__item {
	font-size: 16px;
	font-weight: 700;
	color: $ag-muted;
	height: 60px;
	line-height: 60px;
	padding: 0 32px;
	transition: all 0.3s ease;

	&:hover {
		color: $ag-green-700;
		transform: translateY(-2px);
	}

	&.is-active {
		color: $ag-green-800;
		font-weight: 800;
	}
}

.nongji-detail__tabs ::v-deep .el-tabs__active-bar {
	background-color: $ag-green-500;
	height: 4px;
	border-radius: 4px;
}

.nongji-detail__tabs ::v-deep .el-tabs__content {
	padding: 32px;
	background: #fff;
	border-radius: 0 0 16px 16px;
}

.nongji-detail__rich {
	font-size: 16px;
	line-height: 1.9;
	color: #2f3a2f;
	word-break: break-word;
	padding: 24px;
	background: #f8faf8;
	border-radius: 12px;
	border: 1px solid $ag-border;

	p {
		margin-bottom: 16px;
	}

	h1, h2, h3, h4, h5, h6 {
		color: $ag-green-800;
		margin: 24px 0 16px;
		font-weight: 700;
	}

	img {
		max-width: 100%;
		border-radius: 8px;
		margin: 16px 0;
	}
}

.nongji-detail__comment-form {
	padding: 24px;
	margin-bottom: 24px;
	background: #f8faf8;
	border-radius: 12px;
	border: 1px solid $ag-border;
}

.nongji-detail__comment-card {
	padding: 24px;
	margin-bottom: 20px;
	border-radius: 16px;
	background: linear-gradient(135deg, #ffffff 0%, #f8faf8 100%);
	border: 1px solid $ag-border;
	box-shadow: 0 4px 16px rgba(46, 125, 50, 0.06);
	transition: all 0.3s ease;

	&:hover {
		transform: translateY(-2px);
		box-shadow: 0 8px 24px rgba(46, 125, 50, 0.12);
	}

	.user {
		display: flex;
		align-items: center;
		gap: 12px;
		margin-bottom: 16px;
		padding-bottom: 12px;
		border-bottom: 1px solid rgba(46, 125, 50, 0.08);

		::v-deep .el-image {
			width: 50px;
			height: 50px;
			border-radius: 50%;
			overflow: hidden;
			flex-shrink: 0;
			box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
		}

		.name {
			font-weight: 700;
			color: $ag-text;
			font-size: 16px;
		}
	}

	.content-block-ask {
		color: #555;
		font-size: 15px;
		line-height: 1.7;
		padding: 16px;
		background: rgba(241, 248, 233, 0.5);
		border-radius: 8px;
		border-left: 4px solid $ag-green-500;
	}

	.content-block-reply {
		margin-top: 16px;
		padding: 12px 16px;
		background: rgba(46, 125, 50, 0.05);
		border-radius: 8px;
		border-left: 4px solid $ag-green-700;
		color: $ag-green-800;
		font-size: 14px;
		line-height: 1.6;
	}
}

#pagination {
	margin-top: 32px;
	text-align: center;
	padding: 24px;
	background: #f8faf8;
	border-radius: 12px;
	border: 1px solid $ag-border;
}

#pagination.el-pagination.is-background {
	::v-deep {
		.btn-prev,
		.btn-next {
			border: 1px solid $ag-border;
			border-radius: 8px;
			padding: 6px 16px;
			background: #fff;
			color: $ag-muted;
			transition: all 0.3s ease;

			&:hover:not(:disabled) {
				background: $ag-green-500;
				color: #fff;
				border-color: $ag-green-500;
				transform: translateY(-2px);
				box-shadow: 0 4px 12px rgba(46, 125, 50, 0.25);
			}
		}

		.el-pager li {
			margin: 0 4px;
			border-radius: 8px;
			transition: all 0.3s ease;

			&:hover:not(.disabled) {
				color: $ag-green-500;
				transform: translateY(-2px);
			}

			&.active {
				background-color: $ag-green-500;
				color: #fff;
				transform: translateY(-2px);
				box-shadow: 0 4px 12px rgba(46, 125, 50, 0.25);
			}
		}
	}
}

.editor ::v-deep .avatar-uploader {
	height: 0;
	line-height: 0;
}

.detail-preview .detail ::v-deep .el-tabs__header .el-tabs__nav-wrap {
	margin-bottom: 0;
}

.share_view {
	position: fixed;
	right: 0;
	bottom: 20%;
	z-index: 11;
	box-shadow: 0 1px 6px rgba(0, 0, 0, 0.12);
	background: #fff;
}

@media (max-width: 900px) {
	.nongji-detail__grid {
		grid-template-columns: 1fr;
	}

	.nongji-detail__breadcrumb {
		padding: 16px 5%;
	}

	.detail-preview.nongji-detail__wrap {
		padding: 0 16px;
	}

	.nongji-detail__card {
		padding: 16px;
	}

	.nongji-detail__row {
		grid-template-columns: 1fr;
		gap: 4px;

		dt {
			font-size: 13px;
		}
	}
}
</style>
