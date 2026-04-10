<template>
<div class="case-detail-container">
	<header class="case-detail__top">
		<div class="case-detail__breadcrumb breadcrumb-preview">
			<el-breadcrumb separator=">">
				<el-breadcrumb-item class="item1" to="/"><a>首页</a></el-breadcrumb-item>
				<el-breadcrumb-item class="item2" v-for="(item, index) in breadcrumbItem" :key="index" to="/index/zhunongtuopin"><a>{{ item.name }}</a></el-breadcrumb-item>
				<el-breadcrumb-item class="item3"><span>详情</span></el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<el-button type="default" size="small" class="case-detail__back" icon="el-icon-back" @click="backClick">返回</el-button>
	</header>

	<div class="case-detail__shell detail-preview">
		<div class="case-detail__content">
			<!-- 文章标题和元信息 -->
			<header class="case-detail__header">
				<p class="case-detail__kicker">助农案例</p>
				<h1 class="case-detail__title">{{ detail.wenzhangbiaoti }}</h1>
				<div class="case-detail__meta-info">
					<div class="case-detail__meta-item"><i class="el-icon-document"></i> {{ detail.wenzhangbianhao }}</div>
					<div class="case-detail__meta-item"><i class="el-icon-collection-tag"></i> {{ detail.wenzhangfenlei }}</div>
					<div class="case-detail__meta-item"><i class="el-icon-time"></i> {{ detail.fabushijian }}</div>
					<div class="case-detail__meta-item"><i class="el-icon-star-off"></i> {{ detail.storeupnum }} 收藏</div>
				</div>
				<div class="case-detail__actions">
					<el-button v-show="!isStoreup" type="primary" size="small" round @click="storeup(1)">
						<i class="el-icon-star-off"></i> 收藏
					</el-button>
					<el-button v-show="isStoreup" type="success" size="small" round plain @click="storeup(-1)">
						<i class="el-icon-star-on"></i> 已收藏
					</el-button>
					<div class="case-detail__admin" v-if="btnAuth('zhunongtuopin','修改') || btnAuth('zhunongtuopin','删除')">
						<el-button v-if="btnAuth('zhunongtuopin','修改')" type="primary" size="small" round @click="editClick">修改</el-button>
						<el-button v-if="btnAuth('zhunongtuopin','删除')" type="danger" size="small" round plain @click="delClick">删除</el-button>
					</div>
				</div>
			</header>

			<!-- 文章配图 -->
			<div class="case-detail__gallery" v-if="detailBanner.length">
				<div class="case-detail__gallery-main">
					<img v-if="swiperBigUrl" :src="swiperBigUrl" alt="" class="case-detail__gallery-img" />
				</div>
				<div class="case-detail__gallery-thumbs">
					<button
						type="button"
						class="case-detail__thumb"
						v-for="(item, idx) in detailBanner"
						:key="'banner-' + idx"
						:class="{ 'is-active': thumbActiveIndex === idx }"
						@click="onThumbClick(item, idx)"
					>
						<img v-if="item.substr(0,4)=='http'" :src="item" alt="" />
						<img v-else :src="baseUrl + item" alt="" />
					</button>
				</div>
			</div>
			<div v-else class="case-detail__gallery case-detail__gallery--empty">
				<div class="case-detail__placeholder">
					<i class="el-icon-picture-outline"></i>
					<span>暂无配图</span>
				</div>
			</div>

			<!-- 文章简介 -->
			<div class="case-detail__intro" v-if="detail.wenzhangjianjie">
				<h2 class="case-detail__section-title">文章简介</h2>
				<p class="case-detail__intro-text">{{ detail.wenzhangjianjie }}</p>
			</div>

			<!-- 文章内容和评论 -->
			<el-tabs class="detail case-detail__tabs" v-model="activeName" type="border-card">
				<el-tab-pane label="文章详情" name="first">
					<div class="case-detail__article" v-html="detail.wenzhangxiangqing"></div>
				</el-tab-pane>
				<el-tab-pane label="评论" name="second">
				<el-form class="case-detail__comment-form add comment" :model="form" :rules="rules" ref="form" label-position="top">
					<el-form-item label="发表评论" prop="content">
						<editor v-model="form.content" class="editor" action="file/upload"></editor>
					</el-form-item>
					<el-form-item class="case-detail__form-actions">
						<el-button type="primary" round @click="submitForm('form')">立即提交</el-button>
						<el-button round @click="resetForm('form')">重置</el-button>
					</el-form-item>
				</el-form>

				<div v-if="infoList.length" class="case-detail__comments">
					<div
						class="case-detail__comment-card comment"
						v-for="item in infoList"
						:key="item.id"
						@mouseenter="discussEnter(item.id)"
						@mouseleave="discussLeave"
					>
						<div class="case-detail__comment-user user">
							<el-image v-if="item.avatarurl" class="case-detail__avatar" :src="baseUrl + item.avatarurl" fit="cover"></el-image>
							<el-image v-if="!item.avatarurl" class="case-detail__avatar" :src="require('@/assets/touxiang.png')" fit="cover"></el-image>
							<span class="name">{{ item.nickname }}</span>
						</div>
						<div class="case-detail__comment-body content-block-ask">
							<div v-html="item.content"></div>
							<div class="case-detail__comment-tools btn" v-if="showIndex==item.id && userid==item.userid">
								<el-button type="danger" size="mini" round @click="discussDel(item.id)">删除</el-button>
							</div>
						</div>
						<div class="case-detail__comment-reply content-block-reply" v-if="item.reply">
							回复：<span v-html="item.reply"></span>
						</div>
					</div>
				</div>

				<el-pagination
					background
					id="pagination"
					class="pagination case-detail__pagination"
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

		<div class="share_view case-detail__fab">
			<div class="share"><i class="el-icon-share"></i></div>
			<div class="share"><i class="el-icon-chat-line-round"></i></div>
			<div class="share" @click="scrollToTop"><i class="el-icon-top"></i></div>
		</div>
	</div>
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
        tablename: 'zhunongtuopin',
        baseUrl: '',
        breadcrumbItem: [
          {
            name: '助农案例'
          }
        ],
        title: '',
        detailBanner: [],
		id: 0,
        detail: {},
        activeName: 'second',
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
          tablename: 'zhunongtuopin',
          userid: localStorage.getItem('frontUserid')
        },
        isStoreup: false,
        storeupInfo: {},
        buynumber: 1,
		centerType: false,
		shareUrl: location.href,
		swiperBigUrl: null,
		thumbActiveIndex: 0,
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
		onThumbClick(item, idx) {
			this.thumbActiveIndex = idx;
			this.swiperBigUrl = item.substr(0,4) == 'http' ? item : this.baseUrl + item;
		},
		scrollToTop() {
			window.scrollTo({ top: 0, behavior: 'smooth' });
		},
        init() {
		  this.id = this.$route.query.id
          this.baseUrl = this.$config.baseUrl;
          this.$http.get(this.tablename + '/detail/'  + this.id, {}).then(res => {
            if (res.data.code == 0) {
              this.detail = res.data.data;
              this.title = this.detail.wenzhangbiaoti;
              this.detailBanner = this.detail.wenzhangzhaopian ? this.detail.wenzhangzhaopian.split(",") : [];
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
			  this.$http.post('zhunongtuopin/update', this.detail).then(res => {});
              this.$message({
                type: 'success',
                message: '收藏成功!',
                duration: 1500,
              });
            }
          });
        }
        if (type == -1 && this.isStoreup) {
          this.$http.get('storeup/list', {params: {page: 1, limit: 1, type: 1, refid: this.detail.id, tablename: 'zhunongtuopin', userid: localStorage.getItem('frontUserid')}}).then(res => {
            if (res.data.code == 0 && res.data.data.list.length > 0) {
              this.isStoreup = true;
              this.storeupInfo = res.data.data.list[0];
              let delIds = new Array();
              delIds.push(this.storeupInfo.id);
              this.$http.post('storeup/delete', delIds).then(res => {
                if (res.data.code == 0) {
                  this.isStoreup = false;
				  this.detail.storeupnum--
				  this.$http.post('zhunongtuopin/update', this.detail).then(res => {});
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
            this.$http.get('storeup/list', {params: {page: 1, limit: 1, type: 1, refid: this.detail.id, tablename: 'zhunongtuopin', userid: localStorage.getItem('frontUserid')}}).then(res => {
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
        this.$http.get('discusszhunongtuopin/list', {params: {page, limit: this.pageSize, refid: this.detail.id}}).then(res => {
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
		      this.$http.post('discusszhunongtuopin/delete',[id]).then(res=>{
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
            this.$http.post('discusszhunongtuopin/add', this.form).then(res => {
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
		  this.$http.post('zhunongtuopin/update',this.detail).then(res=>{
			  
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
			this.$router.push(`/index/zhunongtuopinAdd?type=edit&&id=${this.detail.id}`);
		},
		// 删除
		delClick(){
			this.$confirm('是否删除此助农脱贫？')
			  .then(_ => {
			    this.$http.post('zhunongtuopin/delete', [this.detail.id]).then(res => {
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
	.case-detail-container {
		position: relative;
		min-height: 100vh;
		display: flex;
		flex-direction: column;
		background:
			radial-gradient(ellipse 1200px 300px at 15% -5%, rgba(102, 187, 106, 0.22), transparent 55%),
			radial-gradient(ellipse 1000px 280px at 85% 5%, rgba(76, 175, 80, 0.16), transparent 55%),
			linear-gradient(180deg, #f7fbf6 0%, #ffffff 65%);
	}

	/* === 顶部导航 === */
	.case-detail__top {
		background: linear-gradient(135deg, #2e7d32 0%, #43a047 50%, #66bb6a 100%);
		padding: 0;
		display: flex;
		align-items: center;
		justify-content: space-between;
		box-shadow: 0 4px 20px rgba(46, 125, 50, 0.25);
		position: sticky;
		top: 0;
		z-index: 100;
	}
	.case-detail__breadcrumb {
		flex: 1;
		padding: 16px 32px;
	}
	.case-detail__breadcrumb .el-breadcrumb__inner a,
	.case-detail__breadcrumb .el-breadcrumb__inner span {
		color: rgba(255,255,255,0.9) !important;
		font-size: 14px;
	}
	.case-detail__breadcrumb .el-breadcrumb__separator {
		color: rgba(255,255,255,0.6) !important;
	}
	.case-detail__back {
		margin-right: 32px;
		background: rgba(255,255,255,0.15) !important;
		border: 1px solid rgba(255,255,255,0.3) !important;
		color: #fff !important;
		border-radius: 20px !important;
		backdrop-filter: blur(4px);
		transition: all 0.3s ease;
	}
	.case-detail__back:hover {
		background: rgba(255,255,255,0.3) !important;
		transform: translateX(-4px);
	}

	/* === 内容容器 === */
	.case-detail__shell {
		width: 100%;
		max-width: none;
		margin: 0;
		padding: 0;
		background: #fff;
		border-radius: 0;
		box-shadow: none;
		overflow: hidden;
	}

	.case-detail__content {
		padding: 60px 20px;
		max-width: 1600px;
		margin: 0 auto;
	}

	/* === 文章内容 === */
	.case-detail__article {
		padding: 40px 0;
		font-size: 18px;
		line-height: 1.8;
		color: #2f3a2f;
		background: transparent;
		border-radius: 0;
		box-shadow: none;
		border: none;
		margin-top: 0;
		max-width: 1400px;
		margin-left: auto;
		margin-right: auto;
	}

	/* === 文章标题 === */
	.case-detail__title {
		font-size: 36px;
		font-weight: 800;
		color: #1b5e20;
		line-height: 1.3;
		margin: 0 0 32px;
		text-align: center;
	}

	/* === 文章内容 === */
	.case-detail__article {
		padding: 40px 0;
		font-size: 18px;
		line-height: 1.8;
		color: #2f3a2f;
		background: transparent;
		border-radius: 0;
		box-shadow: none;
		border: none;
		margin-top: 0;
	}

	.case-detail__article p {
		margin-bottom: 24px;
		text-align: justify;
	}

	.case-detail__article h1,
	.case-detail__article h2,
	.case-detail__article h3,
	.case-detail__article h4,
	.case-detail__article h5,
	.case-detail__article h6 {
		color: #1b5e20;
		margin-top: 40px;
		margin-bottom: 20px;
		font-weight: 700;
	}

	.case-detail__article h2 {
		font-size: 24px;
	}

	.case-detail__article h3 {
		font-size: 20px;
	}

	.case-detail__article img {
		max-width: 100%;
		height: auto;
		border-radius: 8px;
		margin: 32px 0;
		display: block;
		margin-left: auto;
		margin-right: auto;
	}

	.case-detail__article a {
		color: #4caf50;
		text-decoration: none;
		transition: color 0.3s ease;
	}

	.case-detail__article a:hover {
		color: #2e7d32;
		text-decoration: underline;
	}

	/* === 标签页 === */
	.case-detail__tabs {
		border: none !important;
		box-shadow: none !important;
	}

	.case-detail__tabs .el-tabs__header {
		margin-bottom: 0 !important;
		padding-bottom: 16px;
		border-bottom: 1px solid rgba(46, 125, 50, 0.1);
	}

	.case-detail__tabs .el-tabs__content {
		padding-top: 32px;
	}

	/* === 响应式设计 === */
	@media (max-width: 1200px) {
		.case-detail__content {
			padding: 40px 60px;
			max-width: 800px;
		}
		.case-detail__title {
			font-size: 32px;
		}
	}

	@media (max-width: 992px) {
		.case-detail__content {
			padding: 32px 40px;
			max-width: 700px;
		}
		.case-detail__title {
			font-size: 28px;
		}
		.case-detail__article {
			font-size: 16px;
		}
	}

	@media (max-width: 768px) {
		.case-detail__content {
			padding: 24px 24px;
			max-width: 100%;
		}
		.case-detail__title {
			font-size: 24px;
		}
		.case-detail__meta-info {
			flex-direction: column;
			align-items: center;
			gap: 8px;
		}
	}

	/* === 文章头部 === */
	.case-detail__header {
		margin-bottom: 40px;
		padding-bottom: 32px;
		border-bottom: 1px solid rgba(46, 125, 50, 0.1);
	}
	.case-detail__header-content {
		max-width: 800px;
		margin: 0 auto;
		text-align: center;
	}
	.case-detail__kicker {
		display: inline-block;
		font-size: 12px;
		font-weight: 600;
		color: #4caf50;
		text-transform: uppercase;
		letter-spacing: 1px;
		background: rgba(76, 175, 80, 0.1);
		padding: 6px 16px;
		border-radius: 20px;
		margin-bottom: 16px;
	}
	.case-detail__title {
		font-size: 32px;
		font-weight: 800;
		color: #1b5e20;
		line-height: 1.3;
		margin: 0 0 24px;
	}
	.case-detail__meta-info {
		display: flex;
		flex-wrap: wrap;
		justify-content: center;
		gap: 24px;
		margin-bottom: 24px;
		font-size: 14px;
		color: #607060;
	}
	.case-detail__meta-item {
		display: flex;
		align-items: center;
		gap: 6px;
	}
	.case-detail__meta-item i {
		color: #4caf50;
	}
	.case-detail__actions {
		display: flex;
		flex-wrap: wrap;
		justify-content: center;
		gap: 12px;
	}
	.case-detail__admin {
		display: flex;
		gap: 8px;
		margin-left: 16px;
	}

	/* === 文章配图 === */
	.case-detail__gallery {
		margin-bottom: 40px;
		border-radius: 16px;
		overflow: hidden;
		box-shadow: 0 8px 32px rgba(46, 125, 50, 0.12);
		border: 1px solid rgba(46, 125, 50, 0.08);
	}
	.case-detail__gallery-main {
		width: 100%;
		height: 300px;
		overflow: hidden;
		position: relative;
	}
	.case-detail__gallery-img {
		width: 100%;
		height: 100%;
		object-fit: cover;
		display: block;
		transition: transform 0.6s ease;
	}
	.case-detail__gallery-main:hover .case-detail__gallery-img {
		transform: scale(1.02);
	}
	.case-detail__gallery-thumbs {
		display: flex;
		gap: 12px;
		padding: 16px;
		background: #f8faf8;
		border-top: 1px solid rgba(46, 125, 50, 0.08);
	}
	.case-detail__thumb {
		flex: 1;
		aspect-ratio: 4 / 3;
		border: 2px solid transparent;
		border-radius: 8px;
		overflow: hidden;
		cursor: pointer;
		transition: all 0.3s ease;
		background: #fff;
	}
	.case-detail__thumb:hover {
		border-color: #4caf50;
		transform: translateY(-2px);
		box-shadow: 0 4px 16px rgba(46, 125, 50, 0.15);
	}
	.case-detail__thumb.is-active {
		border-color: #4caf50;
		box-shadow: 0 4px 16px rgba(46, 125, 50, 0.2);
	}
	.case-detail__thumb img {
		width: 100%;
		height: 100%;
		object-fit: cover;
		display: block;
	}
	.case-detail__gallery--empty {
		aspect-ratio: 16 / 9;
		display: flex;
		align-items: center;
		justify-content: center;
		background: #f8faf8;
	}
	.case-detail__placeholder {
		display: flex;
		flex-direction: column;
		align-items: center;
		gap: 12px;
		color: #607060;
	}
	.case-detail__placeholder i {
		font-size: 48px;
		color: #a5d6a7;
	}

	/* === 文章简介 === */
	.case-detail__intro {
		margin-bottom: 40px;
		padding: 32px;
		background: rgba(241, 248, 233, 0.3);
		border-radius: 16px;
		border-left: 4px solid #4caf50;
	}
	.case-detail__section-title {
		font-size: 20px;
		font-weight: 700;
		color: #2e7d32;
		margin: 0 0 16px;
	}
	.case-detail__intro-text {
		font-size: 16px;
		line-height: 1.6;
		color: #2f3a2f;
		margin: 0;
	}

	/* === 文章内容 === */
	.case-detail__article {
		padding: 0;
		font-size: 18px;
		line-height: 1.8;
		color: #2f3a2f;
		background: transparent;
		border-radius: 0;
		box-shadow: none;
		border: none;
		margin-top: 0;
	}
	.case-detail__article h1,
	.case-detail__article h2,
	.case-detail__article h3,
	.case-detail__article h4,
	.case-detail__article h5,
	.case-detail__article h6 {
		color: #1b5e20;
		margin-top: 32px;
		margin-bottom: 16px;
	}
	.case-detail__article p {
		margin-bottom: 20px;
	}
	.case-detail__article img {
		max-width: 100%;
		height: auto;
		border-radius: 8px;
		margin: 24px 0;
	}
	.case-detail__article a {
		color: #4caf50;
		text-decoration: none;
		transition: color 0.3s ease;
	}
	.case-detail__article a:hover {
		color: #2e7d32;
		text-decoration: underline;
	}

	/* === 评论区 === */
	.case-detail__comment-form {
		margin-bottom: 32px;
		padding: 24px;
		background: rgba(241, 248, 233, 0.3);
		border-radius: 12px;
		border: 1px solid #dcdfe6;
	}

	.case-detail__comment-form:focus-within {
		border-color: #4caf50;
		box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.2);
	}

	/* === 编辑器样式 === */
	.editor {
		max-height: 300px;
		min-height: 150px;
	}

	/* 为编辑器添加完整边框 */
	.editor :deep(.w-e-container) {
		border: 1px solid #dcdfe6 !important;
		border-radius: 4px !important;
	}

	.editor :deep(.w-e-container:focus-within) {
		border-color: #4caf50 !important;
		box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.2) !important;
	}

	/* 确保文本容器有正确的边框 */
	.editor :deep(.w-e-text-container) {
		max-height: 250px;
		min-height: 120px;
		border: 1px solid #dcdfe6 !important;
		border-top: none !important;
		border-radius: 0 0 4px 4px !important;
	}

	/* 确保工具栏有正确的边框 */
	.editor :deep(.w-e-toolbar) {
		border: 1px solid #dcdfe6 !important;
		border-bottom: none !important;
		border-radius: 4px 4px 0 0 !important;
	}

	/* 为编辑器添加完整边框 */
	.editor {
		border: 1px solid #dcdfe6 !important;
		border-radius: 4px !important;
		overflow: hidden !important;
	}

	.editor:focus-within {
		border-color: #4caf50 !important;
		box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.2) !important;
	}

	/* 确保编辑器内部元素没有边框 */
	.editor :deep(.w-e-container) {
		border: none !important;
	}

	.editor :deep(.w-e-text-container) {
		max-height: 250px;
		min-height: 120px;
		border: none !important;
	}

	.editor :deep(.w-e-toolbar) {
		border: none !important;
	}

	.case-detail__form-actions {
		display: flex;
		justify-content: center;
		gap: 16px;
		margin-top: 24px;
	}
	.case-detail__comments {
		margin-bottom: 32px;
	}
	.case-detail__comment-card {
		padding: 24px;
		background: #fff;
		border-radius: 12px;
		border: 1px solid rgba(46, 125, 50, 0.08);
		box-shadow: 0 4px 16px rgba(46, 125, 50, 0.08);
		margin-bottom: 20px;
		transition: all 0.3s ease;
	}
	.case-detail__comment-card:hover {
		transform: translateY(-2px);
		box-shadow: 0 8px 24px rgba(46, 125, 50, 0.12);
	}
	.case-detail__comment-user {
		display: flex;
		align-items: center;
		gap: 12px;
		margin-bottom: 16px;
	}
	.case-detail__avatar {
		width: 48px;
		height: 48px;
		border-radius: 50%;
		border: 2px solid #4caf50;
	}
	.case-detail__comment-body {
		margin-bottom: 12px;
		line-height: 1.6;
	}
	.case-detail__comment-tools {
		margin-top: 16px;
		display: flex;
		justify-content: flex-end;
	}
	.case-detail__comment-reply {
		padding: 16px;
		background: rgba(76, 175, 80, 0.1);
		border-radius: 8px;
		font-size: 14px;
		line-height: 1.5;
	}
	.case-detail__pagination {
		margin-top: 32px;
		display: flex;
		justify-content: center;
	}

	/* === 响应式设计 === */
	@media (max-width: 1200px) {
		.case-detail__shell {
			width: 95%;
			max-width: 1200px;
		}
		.case-detail__hero {
			grid-template-columns: 1fr 420px;
			gap: 24px;
		}
	}

	@media (max-width: 992px) {
		.case-detail__hero {
			grid-template-columns: 1fr;
		}
		.case-detail__meta {
			order: -1;
		}
	}

	@media (max-width: 768px) {
		.case-detail__shell {
			width: 98%;
			padding: 24px;
		}
		.case-detail__top {
			flex-direction: column;
			align-items: flex-start;
			padding: 16px;
		}
		.case-detail__back {
			margin: 16px 0 0 16px;
		}
	}

	/* === 图库 === */
	.case-detail__gallery {
		background: #fff;
		border-radius: 20px;
		overflow: hidden;
		box-shadow: 0 12px 48px rgba(46, 125, 50, 0.12);
		border: 1px solid rgba(46, 125, 50, 0.08);
	}
	.case-detail__gallery-main {
		width: 100%;
		height: 300px;
		overflow: hidden;
		position: relative;
	}
	.case-detail__gallery-img {
		width: 100%;
		height: 100%;
		object-fit: cover;
		display: block;
		transition: transform 0.6s ease;
	}
	.case-detail__gallery-main:hover .case-detail__gallery-img {
		transform: scale(1.04);
	}
	.case-detail__gallery-thumbs {
		display: flex;
		gap: 8px;
		padding: 16px;
		background: linear-gradient(135deg, #f8faf8, #fff);
		overflow-x: auto;
	}
	.case-detail__gallery-thumbs::-webkit-scrollbar { height: 4px; }
	.case-detail__gallery-thumbs::-webkit-scrollbar-thumb { background: #a5d6a7; border-radius: 2px; }
	.case-detail__thumb {
		flex: 0 0 80px;
		height: 56px;
		border: 2px solid transparent;
		border-radius: 10px;
		overflow: hidden;
		cursor: pointer;
		padding: 0;
		background: #fff;
		transition: all 0.3s ease;
		box-shadow: 0 2px 8px rgba(0,0,0,0.06);
	}
	.case-detail__thumb img {
		width: 100%;
		height: 100%;
		object-fit: cover;
		display: block;
	}
	.case-detail__thumb:hover,
	.case-detail__thumb.is-active {
		border-color: #4caf50;
		transform: translateY(-4px);
		box-shadow: 0 6px 20px rgba(46, 125, 50, 0.2);
	}
	.case-detail__gallery--empty {
		display: flex;
		align-items: center;
		justify-content: center;
		min-height: 320px;
		background: linear-gradient(135deg, #f8faf8, #fff);
	}
	.case-detail__placeholder {
		text-align: center;
		color: #a5d6a7;
	}
	.case-detail__placeholder i {
		font-size: 56px;
		display: block;
		margin-bottom: 12px;
	}
	.case-detail__placeholder span {
		font-size: 14px;
	}

	/* === 右侧信息卡 === */
	.case-detail__meta {
		background: #fff;
		border-radius: 20px;
		padding: 32px;
		box-shadow: 0 12px 48px rgba(46, 125, 50, 0.12);
		border: 1px solid rgba(46, 125, 50, 0.08);
		position: sticky;
		top: 80px;
	}
	.case-detail__kicker {
		font-size: 12px;
		font-weight: 600;
		color: #4caf50;
		text-transform: uppercase;
		letter-spacing: 2px;
		margin: 0 0 8px;
	}
	.case-detail__title {
		font-size: 22px;
		font-weight: 800;
		color: #1b5e20;
		line-height: 1.4;
		margin: 0 0 20px;
	}
	.case-detail__actions {
		margin-bottom: 20px;
	}
	.case-detail__actions .el-button {
		width: 100%;
	}
	.btn-store.el-button {
		background: linear-gradient(135deg, #66bb6a, #2e7d32) !important;
		border: none !important;
		color: #fff !important;
		font-weight: 600;
		box-shadow: 0 6px 20px rgba(46, 125, 50, 0.25);
		transition: all 0.3s ease;
	}
	.btn-store.el-button:hover {
		transform: translateY(-2px);
		box-shadow: 0 10px 28px rgba(46, 125, 50, 0.35);
	}
	.case-detail__dl {
		margin: 0 0 20px;
		padding: 0;
		list-style: none;
	}
	.case-detail__row {
		display: flex;
		align-items: flex-start;
		padding: 10px 14px;
		margin-bottom: 8px;
		background: rgba(241, 248, 233, 0.5);
		border-radius: 10px;
		border-left: 3px solid #4caf50;
		transition: all 0.3s ease;
	}
	.case-detail__row:hover {
		background: rgba(241, 248, 233, 0.8);
		transform: translateX(4px);
	}
	.case-detail__row dt {
		font-size: 13px;
		font-weight: 600;
		color: #2e7d32;
		min-width: 80px;
		flex-shrink: 0;
	}
	.case-detail__row dd {
		font-size: 14px;
		color: #2f3a2f;
		margin: 0;
		flex: 1;
		line-height: 1.5;
		word-break: break-all;
	}
	.case-detail__row--block {
		flex-direction: column;
	}
	.case-detail__row--block dd {
		margin-top: 6px;
		font-size: 13px;
		color: #607060;
	}
	.case-detail__admin {
		display: flex;
		gap: 10px;
		padding-top: 8px;
		border-top: 1px solid rgba(46, 125, 50, 0.1);
	}
	.case-detail__admin .el-button {
		flex: 1;
	}

	/* === Tabs === */
	.case-detail__tabs {
		border-radius: 20px !important;
		overflow: hidden;
		box-shadow: 0 12px 48px rgba(46, 125, 50, 0.12) !important;
		border: 1px solid rgba(46, 125, 50, 0.08) !important;
		margin-top: 0 !important;
	}
	.case-detail__tabs ::v-deep .el-tabs__header {
		background: linear-gradient(135deg, #f8faf8, #fff) !important;
		margin: 0 !important;
		padding: 0 24px !important;
		border-bottom: 1px solid rgba(46, 125, 50, 0.08) !important;
	}
	.case-detail__tabs ::v-deep .el-tabs__nav {
		border: none !important;
	}
	.case-detail__tabs ::v-deep .el-tabs__item {
		font-size: 15px !important;
		font-weight: 600 !important;
		color: #607060 !important;
		border: none !important;
		padding: 0 24px !important;
		height: 56px !important;
		line-height: 56px !important;
		transition: all 0.3s ease !important;
	}
	.case-detail__tabs ::v-deep .el-tabs__item:hover {
		color: #2e7d32 !important;
	}
	.case-detail__tabs ::v-deep .el-tabs__item.is-active {
		color: #1b5e20 !important;
		font-weight: 700 !important;
	}
	.case-detail__tabs ::v-deep .el-tabs__active-bar {
		background: linear-gradient(135deg, #66bb6a, #2e7d32) !important;
		height: 3px !important;
		border-radius: 3px 3px 0 0 !important;
	}
	.case-detail__tabs ::v-deep .el-tabs__content {
		padding: 24px !important;
	}

	/* === 文章详情 === */
	.case-detail__article {
		font-size: 15px;
		line-height: 1.9;
		color: #2f3a2f;
		padding: 8px;
	}
	.case-detail__article p { margin: 0 0 16px; }
	.case-detail__article img {
		max-width: 100%;
		border-radius: 12px;
		margin: 12px 0;
		box-shadow: 0 4px 16px rgba(0,0,0,0.08);
	}

	/* === 评论表单 === */
	.case-detail__comment-form {
		background: rgba(241, 248, 233, 0.4);
		border-radius: 16px;
		padding: 24px;
		margin-bottom: 24px;
		border: 1px solid rgba(46, 125, 50, 0.08);
	}
	.case-detail__comment-form .el-form-item__label {
		color: #2e7d32 !important;
		font-weight: 600 !important;
	}
	.case-detail__comment-form .editor {
		border-radius: 12px !important;
		overflow: hidden;
	}
	.case-detail__form-actions {
		margin-top: 16px;
		margin-bottom: 0 !important;
	}
	.case-detail__form-actions .el-button {
		min-width: 100px;
	}

	/* === 评论卡片 === */
	.case-detail__comments {
		display: flex;
		flex-direction: column;
		gap: 16px;
	}
	.case-detail__comment-card {
		background: #fff;
		border-radius: 16px;
		padding: 24px;
		border: 1px solid rgba(46, 125, 50, 0.08);
		box-shadow: 0 4px 16px rgba(46, 125, 50, 0.06);
		transition: all 0.3s ease;
	}
	.case-detail__comment-card:hover {
		transform: translateY(-2px);
		box-shadow: 0 8px 32px rgba(46, 125, 50, 0.12);
		border-color: rgba(46, 125, 50, 0.15);
	}
	.case-detail__comment-user {
		display: flex;
		align-items: center;
		margin-bottom: 14px;
	}
	.case-detail__avatar {
		width: 44px !important;
		height: 44px !important;
		border-radius: 50% !important;
		border: 2px solid #4caf50 !important;
		flex-shrink: 0;
		margin-right: 12px !important;
		box-shadow: 0 2px 8px rgba(46, 125, 50, 0.15);
	}
	.case-detail__comment-user .name {
		font-size: 15px;
		font-weight: 600;
		color: #2e7d32;
	}
	.case-detail__comment-body {
		padding: 14px 18px !important;
		background: rgba(241, 248, 233, 0.4) !important;
		border-radius: 12px !important;
		margin: 0 !important;
		font-size: 14px !important;
		line-height: 1.7 !important;
		color: #2f3a2f !important;
	}
	.case-detail__comment-tools {
		margin-top: 10px !important;
	}
	.case-detail__comment-reply {
		margin-top: 10px !important;
		padding: 12px 16px !important;
		background: rgba(76, 175, 80, 0.08) !important;
		border-radius: 10px !important;
		color: #2e7d32 !important;
		font-size: 13px !important;
	}

	/* === 分页 === */
	.case-detail__pagination {
		margin-top: 24px !important;
		background: rgba(241, 248, 233, 0.4) !important;
		border-radius: 12px !important;
		padding: 16px 24px !important;
	}

	/* === FAB 悬浮按钮 === */
	.case-detail__fab {
		position: fixed !important;
		right: 24px !important;
		bottom: 20% !important;
		z-index: 999 !important;
		border-radius: 14px !important;
		overflow: hidden;
		border: 1px solid rgba(46, 125, 50, 0.12) !important;
		box-shadow: 0 8px 32px rgba(46, 125, 50, 0.15) !important;
		background: #fff !important;
	}
	.case-detail__fab .share {
		width: 48px !important;
		height: 48px !important;
		display: flex !important;
		align-items: center !important;
		justify-content: center !important;
		cursor: pointer;
		border-bottom: 1px solid rgba(46, 125, 50, 0.08) !important;
		transition: all 0.3s ease !important;
		background: linear-gradient(135deg, #f8faf8, #ffffff) !important;
	}
	.case-detail__fab .share:last-child {
		border-bottom: none !important;
	}
	.case-detail__fab .share:hover {
		background: linear-gradient(135deg, #66bb6a, #2e7d32) !important;
		color: #fff !important;
	}
	.case-detail__fab .share:hover i {
		color: #fff !important;
	}
	.case-detail__fab .share i {
		font-size: 20px !important;
		color: #2e7d32 !important;
		transition: color 0.3s ease !important;
	}

	/* === 响应式 === */
	@media (max-width: 900px) {
		.case-detail__hero {
			grid-template-columns: 1fr !important;
		}
		.case-detail__meta {
			position: static !important;
		}
		.case-detail__shell {
			width: 94% !important;
		}
	}
</style>

