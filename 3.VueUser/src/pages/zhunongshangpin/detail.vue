<template>
<div>
	<!-- 面包屑导航 -->
	<div class="breadcrumb-preview" :style='{"width":"100%","padding":"30px 10%","margin":"0 auto","background":"linear-gradient(135deg, #2e7d32 0%, #1b5e20 100%)","boxShadow":"0 8px 32px rgba(46, 125, 50, 0.3)","border":"none"}'>
		<div class="breadcrumb-decoration"></div>
		<el-breadcrumb :separator="'/'">
			<el-breadcrumb-item class="item1" to="/">
				<a style="color: rgba(255,255,255,0.9);">
					<i class="el-icon-house"></i> 首页
				</a>
			</el-breadcrumb-item>
			<el-breadcrumb-item v-for="(item, index) in breadcrumbItem" :key="index" :to="'/index/zhunongshangpin'">
				<a style="color: rgba(255,255,255,0.9);">{{item.name}}</a>
			</el-breadcrumb-item>
			<el-breadcrumb-item>
				<span style="color: #fff; font-weight: 600;">商品详情</span>
			</el-breadcrumb-item>
		</el-breadcrumb>
	</div>

	<!-- 返回按钮区域 -->
	<div class="back-bar" :style='{"width":"100%","padding":"16px 10%","background":"linear-gradient(135deg, #2e7d32 0%, #1b5e20 100%)"}'>
		<el-button size="mini" class="back-btn" @click="backClick">
			<i class="el-icon-arrow-left"></i>
			返回列表
		</el-button>
	</div>
	<!-- 详情内容区域 -->
	<div class="detail-preview" :style='{"width":"80%","margin":"30px auto","position":"relative","alignItems":"flex-start","flexWrap":"wrap","display":"flex"}'>
		<!-- 商品图片展示 -->
		<div class="swiper3" v-if="detailBanner.length" :style='{"padding":"24px","boxShadow":"0 12px 40px rgba(46, 125, 50, 0.15)","margin":"0 0 0","background":"#fff","width":"48%","height":"auto","order":"1","borderRadius":"20px","border":"1px solid rgba(46, 125, 50, 0.1)"}'>
			  <div class="big" :style='{"border":"0","width":"100%","margin":"0 0 20px","position":"relative","background":"#fff","height":"480px","borderRadius":"16px","overflow":"hidden"}'>
				<img id="big" :style='{"border":"none","boxShadow":"none","objectFit":"cover","display":"block","width":"100%","height":"100%","zIndex":"1","transition":"transform 0.4s ease"}' :src="swiperBigUrl" class="image">
			  </div>
			  <div class="samll" :style='{"width":"100%","padding":"0 0","background":"#fff","display":"flex","height":"90px","gap":"12px"}'>
			    <div :style='{"border":"0","flex":"1","margin":"0","position":"relative","background":"#fff","height":"100%","borderRadius":"12px","overflow":"hidden","cursor":"pointer","transition":"all 0.25s ease","border":"2px solid transparent"}' v-for="item in detailBanner" :key="item.id">
				  <img :style='{"width":"100%","boxShadow":"none","objectFit":"cover","display":"block","height":"100%","zIndex":"1","transition":"transform 0.3s ease"}' v-if="item.substr(0,4)=='http'" :src="item" @click="swiperClick3(item)" class="image">
				  <img :style='{"width":"100%","boxShadow":"none","objectFit":"cover","display":"block","height":"100%","zIndex":"1","transition":"transform 0.3s ease"}' v-else :src="baseUrl + item" @click="swiperClick3(baseUrl + item)" class="image">
			    </div>
			  </div>
		</div>

		<!-- 商品信息区域 -->
		<div class="attr" :style='{"padding":"24px","margin":"0 0 0 20px","background":"#fff","flex":"1","display":"flex","width":"calc(52% - 20px)","position":"relative","order":"2","borderRadius":"20px","border":"1px solid rgba(46, 125, 50, 0.1)","boxShadow":"0 12px 40px rgba(46, 125, 50, 0.1)"}'>
			<div class="info" :style='{"padding":"0","margin":"0","background":"none","flex":"1"}'>
				<!-- 商品名称和收藏 -->
				<div class="item detail-title" :style='{"padding":"0 0 20px 0","margin":"0 0 20px 0","alignItems":"flex-start","background":"none","justifyContent":"space-between","display":"flex","borderBottom":"1px dashed rgba(46, 125, 50, 0.15)"}'>
					<div :style='{"flex":"1","margin":"0 16px 0 0","fontSize":"22px","color":"#1a2e1a","fontWeight":"800","lineHeight":"1.4"}'>
                    {{detail.chanpinmingcheng}}
                    </div>
					<div @click="storeup(1)" v-show="!isStoreup" class="collect-btn" :style='{"padding":"10px 18px","borderRadius":"999px","textAlign":"center","background":"linear-gradient(135deg, #4caf50, #2e7d32)","cursor":"pointer","transition":"all 0.25s ease","boxShadow":"0 6px 20px rgba(46, 125, 50, 0.25)"}'>
						<i class="el-icon-star-off" :style='{"color":"#fff","fontSize":"14px","marginRight":"6px"}'></i>
						<span :style='{"color":"#fff","fontSize":"13px","fontWeight":"600"}'>收藏 ({{detail.storeupnum}})</span>
					</div>
					<div @click="storeup(-1)" v-show="isStoreup" class="collect-btn" :style='{"padding":"10px 18px","borderRadius":"999px","textAlign":"center","background":"linear-gradient(135deg, #ff9800, #f57c00)","cursor":"pointer","transition":"all 0.25s ease","boxShadow":"0 6px 20px rgba(255, 152, 0, 0.25)"}'>
						<i class="el-icon-star-on" :style='{"color":"#fff","fontSize":"14px","marginRight":"6px"}'></i>
						<span :style='{"color":"#fff","fontSize":"13px","fontWeight":"600"}'>已收藏 ({{detail.storeupnum}})</span>
					</div>
				</div>

				<!-- 价格信息 -->
				<div v-if="detail.price" class="item" :style='{"padding":"16px 20px","margin":"0 0 12px 0","background":"linear-gradient(135deg, rgba(46, 125, 50, 0.05), rgba(46, 125, 50, 0.02))","borderRadius":"14px","display":"flex","justifyContent":"space-between","alignItems":"center"}'>
					<div class="lable" :style='{"color":"#607060","fontSize":"14px","fontWeight":"600"}'>价格</div>
					<div style="font-weight: 800;" :style='{"fontSize":"26px","lineHeight":"1","color":"#e53935","fontWeight":"800"}'>
						<span :style='{"fontSize":"16px"}'>¥</span>{{detail.price}}
					</div>
				</div>
				<div v-if="detail.vipprice" class="item" :style='{"padding":"16px 20px","margin":"0 0 12px 0","background":"linear-gradient(135deg, rgba(255, 152, 0, 0.08), rgba(255, 152, 0, 0.03))","borderRadius":"14px","display":"flex","justifyContent":"space-between","alignItems":"center"}'>
					<div class="lable" :style='{"color":"#607060","fontSize":"14px","fontWeight":"600"}'>会员价</div>
					<div style="font-weight: 800;" :style='{"fontSize":"20px","lineHeight":"1","color":"#ff9800","fontWeight":"800"}'>
						<span :style='{"fontSize":"14px"}'>¥</span>{{detail.vipprice}} <span :style='{"fontSize":"12px","color":"#ff9800","fontWeight":"600"}'>VIP</span>
					</div>
				</div>

				<!-- 其他信息 -->
				<div class="item" :style='{"padding":"12px 20px","margin":"0 0 8px 0","background":"#f8faf8","borderRadius":"12px","display":"flex","justifyContent":"spaceBetween","alignItems":"center"}'>
					<div class="lable" :style='{"color":"#607060","fontSize":"13px","width":"100px","fontWeight":"600"}'>单限</div>
					<div :style='{"fontSize":"13px","color":"#455445","fontWeight":"500"}'>{{detail.onelimittimes}}</div>
				</div>
				<div class="item" :style='{"padding":"12px 20px","margin":"0 0 8px 0","background":"#f8faf8","borderRadius":"12px","display":"flex","justifyContent":"spaceBetween","alignItems":"center"}'>
					<div class="lable" :style='{"color":"#607060","fontSize":"13px","width":"100px","fontWeight":"600"}'>库存</div>
					<div :style='{"fontSize":"13px","color":"#455445","fontWeight":"500"}'>{{detail.alllimittimes}}</div>
				</div>
				<div class="item" :style='{"padding":"12px 20px","margin":"0 0 8px 0","background":"#f8faf8","borderRadius":"12px","display":"flex","justifyContent":"spaceBetween","alignItems":"center"}'>
					<div class="lable" :style='{"color":"#607060","fontSize":"13px","width":"100px","fontWeight":"600"}'>产品编号</div>
					<div :style='{"fontSize":"13px","color":"#455445","fontWeight":"500"}'>{{detail.chanpinbianhao}}</div>
				</div>
				<div class="item" :style='{"padding":"12px 20px","margin":"0 0 8px 0","background":"#f8faf8","borderRadius":"12px","display":"flex","justifyContent":"spaceBetween","alignItems":"center"}'>
					<div class="lable" :style='{"color":"#607060","fontSize":"13px","width":"100px","fontWeight":"600"}'>产品分类</div>
					<div :style='{"fontSize":"13px","color":"#388e3c","fontWeight":"600"}'>{{detail.chanpinfenlei}}</div>
				</div>
				<div class="item" :style='{"padding":"12px 20px","margin":"0 0 8px 0","background":"#f8faf8","borderRadius":"12px","display":"flex","justifyContent":"spaceBetween","alignItems":"center"}'>
					<div class="lable" :style='{"color":"#607060","fontSize":"13px","width":"100px","fontWeight":"600"}'>发布日期</div>
					<div :style='{"fontSize":"13px","color":"#455445","fontWeight":"500"}'>{{detail.faburiqi}}</div>
				</div>
				<div class="item" :style='{"padding":"12px 20px","margin":"0 0 8px 0","background":"#f8faf8","borderRadius":"12px","display":"flex","justifyContent":"spaceBetween","alignItems":"center"}'>
					<div class="lable" :style='{"color":"#607060","fontSize":"13px","width":"100px","fontWeight":"600"}'>保质期</div>
					<div :style='{"fontSize":"13px","color":"#455445","fontWeight":"500"}'>{{detail.baozhiqi}}</div>
				</div>
				<div class="item" :style='{"padding":"12px 20px","margin":"0 0 8px 0","background":"#f8faf8","borderRadius":"12px","display":"flex","justifyContent":"spaceBetween","alignItems":"center"}'>
					<div class="lable" :style='{"color":"#607060","fontSize":"13px","width":"100px","fontWeight":"600"}'>生产地</div>
					<div :style='{"fontSize":"13px","color":"#455445","fontWeight":"500"}'>{{detail.shengchandi}}</div>
				</div>
				<div class="item" :style='{"padding":"12px 20px","margin":"0 0 8px 0","background":"#f8faf8","borderRadius":"12px","display":"flex","justifyContent":"spaceBetween","alignItems":"center"}'>
					<div class="lable" :style='{"color":"#607060","fontSize":"13px","width":"100px","fontWeight":"600"}'>规格</div>
					<div :style='{"fontSize":"13px","color":"#455445","fontWeight":"500"}'>{{detail.guige}}</div>
				</div>
				<div class="item" :style='{"padding":"12px 20px","margin":"0 0 8px 0","background":"#f8faf8","borderRadius":"12px","display":"flex","justifyContent":"spaceBetween","alignItems":"center"}'>
					<div class="lable" :style='{"color":"#607060","fontSize":"13px","width":"100px","fontWeight":"600"}'>农户账号</div>
					<div :style='{"fontSize":"13px","color":"#455445","fontWeight":"500"}'>{{detail.nonghuzhanghao}}</div>
				</div>
				<div class="item" :style='{"padding":"12px 20px","margin":"0 0 8px 0","background":"#f8faf8","borderRadius":"12px","display":"flex","justifyContent":"spaceBetween","alignItems":"center"}'>
					<div class="lable" :style='{"color":"#607060","fontSize":"13px","width":"100px","fontWeight":"600"}'>农户姓名</div>
					<div :style='{"fontSize":"13px","color":"#388e3c","fontWeight":"600"}'>{{detail.nonghuxingming}}</div>
				</div>
				<div class="item" :style='{"padding":"12px 20px","margin":"0 0 8px 0","background":"#f8faf8","borderRadius":"12px","display":"flex","justifyContent":"spaceBetween","alignItems":"center"}'>
					<div class="lable" :style='{"color":"#607060","fontSize":"13px","width":"100px","fontWeight":"600"}'>点击次数</div>
					<div :style='{"fontSize":"13px","color":"#455445","fontWeight":"500"}'>
						<i class="el-icon-view" :style='{"marginRight":"4px","color":"#607060"}'></i>
						{{detail.clicknum}}
					</div>
				</div>

				<!-- 操作按钮 -->
				<div class="btn" :style='{"padding":"20px 0 0 0","flexWrap":"wrap","display":"flex","gap":"12px","borderTop":"1px solid rgba(46, 125, 50, 0.1)","marginTop":"12px"}'>
					<el-input-number v-if="detail.alllimittimes" :style='{"width":"140px"}' :min=1 v-model="buynumber"></el-input-number>
					<el-button v-if="detail.alllimittimes" class="btn-cart" type="warning" size="small" @click="addCart">
						<i class="el-icon-shopping-cart-2"></i>
						添加到购物车
					</el-button>
					<el-button v-if="detail.alllimittimes" class="btn-buy" type="warning" size="small" @click="buyNow">
						<i class="el-icon-credit-pay"></i>
						立即购买
					</el-button>
				</div>
				<div class="btn" :style='{"padding":"12px 0","flexWrap":"wrap","display":"flex","gap":"12px"}'>
					<el-button v-if="btnAuth('zhunongshangpin','修改')" class="btn-edit" @click="editClick">
						<i class="el-icon-edit"></i>
						修改
					</el-button>
					<el-button v-if="btnAuth('zhunongshangpin','删除')" class="btn-delete" @click="delClick">
						<i class="el-icon-delete"></i>
						删除
					</el-button>
				</div>
			</div>
		</div>


		

		<!-- 商品详情Tab -->
		<el-tabs class="detail" :style='{"border":"none","width":"100%","boxShadow":"none","margin":"30px 0 0","background":"#fff","order":"5","borderRadius":"20px","overflow":"hidden","border":"1px solid rgba(46, 125, 50, 0.1)"}' v-model="activeName" type="border-card">
			<el-tab-pane label="产品详情" name="first">
				<div class="detail-content" v-html="detail.chanpinxiangqing"></div>
			</el-tab-pane>
			<el-tab-pane label="用户评论" name="second">
				<!-- 评论表单 -->
				<el-form class="add comment" :style='{"boxShadow":"none","padding":"20px","margin":"0 0 20px","borderRadius":"16px","background":"linear-gradient(135deg, rgba(241, 248, 233, 0.5), rgba(255, 255, 255, 0.5))","border":"1px solid rgba(46, 125, 50, 0.1)"}' :model="form" :rules="rules" ref="form">
					<el-form-item class="item" :style='{"width":"100%","display":"flex","height":"auto"}' label="发表评价" prop="content">
						<editor
						    :style='{"border":"0","boxShadow":"none","outline":"none","color":"#333","borderRadius":"12px","background":"#fff","width":"100%","lineHeight":"32px","fontSize":"14px"}'
						    v-model="form.content"
						    class="editor"
						    action="file/upload">
						</editor>
					</el-form-item>
					<el-form-item class="btn" :style='{"width":"100%","padding":"0","margin":"12px 0 0","height":"auto"}'>
						<el-button class="btn-submit" type="primary" @click="submitForm('form')">
							<i class="el-icon-s-promotion"></i>
							立即提交
						</el-button>
						<el-button class="btn-reset" @click="resetForm('form')">
							<i class="el-icon-refresh"></i>
							重置
						</el-button>
					</el-form-item>
				</el-form>

				<!-- 评论列表 -->
				<div v-if="infoList.length" :style='{"boxShadow":"none","padding":"0"}' class="comment">
					<div :style='{"padding":"24px","margin":"0 0 16px","borderColor":"rgba(46, 125, 50, 0.1)","alignItems":"flex-start","borderWidth":"0 0 1px 0","background":"#fff","borderStyle":"solid","height":"auto","borderRadius":"16px","border":"1px solid rgba(46, 125, 50, 0.1)"}' v-for="item in infoList" :key="item.id" @mouseenter="discussEnter(item.id)"
						@mouseleave="discussLeave">
						<div class="user" :style='{"width":"100%","alignItems":"center","display":"flex","height":"auto","gap":"12px"}'>
							<el-image v-if="item.avatarurl" :style='{"width":"44px","margin":"0","borderRadius":"50%","objectFit":"cover","height":"44px","border":"2px solid rgba(46, 125, 50, 0.2)"}' :size="50" :src="baseUrl + item.avatarurl"></el-image>
							<el-image v-else :style='{"width":"44px","margin":"0","borderRadius":"50%","objectFit":"cover","height":"44px","border":"2px solid rgba(46, 125, 50, 0.2)"}' :size="50" :src="require('@/assets/touxiang.png')"></el-image>
							<div>
								<div :style='{"color":"#1a2e1a","fontSize":"15px","fontWeight":"600"}' class="name">{{item.nickname}}</div>
								<div :style='{"color":"#607060","fontSize":"12px","marginTop":"2px"}'>{{item.addtime}}</div>
							</div>
						</div>
						<div :style='{"padding":"16px 0 0 56px","boxShadow":"none","margin":"0","color":"#455445","borderRadius":"0","background":"none","wordWrap":"break-word","lineHeight":"1.8","fontSize":"14px"}' class="content-block-ask">
							<div v-html="item.content"></div>
							<div class="btn" :style='{"width":"100%","margin":"12px 0 0 0","alignItems":"center","justifyContent":"flex-start","display":"flex","height":"auto"}'>
							  <el-button v-if="showIndex==item.id&&userid==item.userid" @click="discussDel(item.id)" :style='{"border":"0","cursor":"pointer","padding":"8px 16px","margin":"0","outline":"none","color":"#fff","borderRadius":"20px","background":"#e53935","width":"auto","fontSize":"12px","fontWeight":"600","lineHeight":"1","height":"auto"}'>
								<i class="el-icon-delete"></i> 删除
							  </el-button>
							</div>
						</div>
						<div v-if="item.reply" :style='{"padding":"12px 16px","margin":"12px 0 0 56px","boxShadow":"none","background":"#f8faf8","borderRadius":"12px","color":"#607060","fontSize":"13px","lineHeight":"1.7","borderLeft":"3px solid #4caf50"}' class="content-block-reply">
							<strong style="color: #388e3c;">管理员回复：</strong><span v-html="item.reply"></span>
						</div>
					</div>
				</div>

				<!-- 评论分页 -->
				<el-pagination
				  background
				  id="pagination" class="pagination"
				  :pager-count="7"
				  :page-size="pageSize"
				  :page-sizes="pageSizes"
				  prev-text="上一页"
				  next-text="下一页"
				  :hide-on-single-page="false"
				  :layout='["prev","pager","next"].join()'
				  :total="total"
				  :style='{"padding":"20px 0","margin":"20px auto","whiteSpace":"nowrap","color":"#333","textAlign":"center","width":"100%","fontWeight":"500"}'
				  @current-change="curChange"
				  @prev-click="prevClick"
				  @next-click="nextClick"
				></el-pagination>
			</el-tab-pane>
		</el-tabs>
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
        tablename: 'zhunongshangpin',
        baseUrl: '',
        breadcrumbItem: [
          {
            name: '助农商品'
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
          tablename: 'zhunongshangpin',
          userid: localStorage.getItem('frontUserid')
        },
        isStoreup: false,
        storeupInfo: {},
        buynumber: 1,
        cart: {
          buynumber: 0,
          discountprice: 0,
          goodid: 0,
          goodname: '',
          picture: '',
          price: 0,
          userid: localStorage.getItem('frontUserid')
        },
        isInCart: false,
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
              this.title = this.detail.chanpinmingcheng;
              this.detailBanner = this.detail.tupian ? this.detail.tupian.split(",") : [];
              this.$forceUpdate();

			  this.getDiscussList(1);
				if(localStorage.getItem('frontToken')){
					this.getStoreupStatus();
					this.getCartList();
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
			  this.$http.post('zhunongshangpin/update', this.detail).then(res => {});
              this.$message({
                type: 'success',
                message: '收藏成功!',
                duration: 1500,
              });
            }
          });
        }
        if (type == -1 && this.isStoreup) {
          this.$http.get('storeup/list', {params: {page: 1, limit: 1, type: 1, refid: this.detail.id, tablename: 'zhunongshangpin', userid: localStorage.getItem('frontUserid')}}).then(res => {
            if (res.data.code == 0 && res.data.data.list.length > 0) {
              this.isStoreup = true;
              this.storeupInfo = res.data.data.list[0];
              let delIds = new Array();
              delIds.push(this.storeupInfo.id);
              this.$http.post('storeup/delete', delIds).then(res => {
                if (res.data.code == 0) {
                  this.isStoreup = false;
				  this.detail.storeupnum--
				  this.$http.post('zhunongshangpin/update', this.detail).then(res => {});
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
            this.$http.get('storeup/list', {params: {page: 1, limit: 1, type: 1, refid: this.detail.id, tablename: 'zhunongshangpin', userid: localStorage.getItem('frontUserid')}}).then(res => {
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
        this.$http.get('discusszhunongshangpin/list', {params: {page, limit: this.pageSize, refid: this.detail.id}}).then(res => {
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
		      this.$http.post('discusszhunongshangpin/delete',[id]).then(res=>{
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
            this.$http.get('orders/list', {params: {page: 1, limit: 1, status: '已完成', goodid: this.detail.id, userid: localStorage.getItem('frontUserid')}}).then(res => {
                if (res.data.code == 0 && res.data.data.list.length == 0) {
                  this.$message({
                    type: 'success',
                    message: '请完成订单后再评论!',
                    duration: 1500
                  });
                  return false
                } else {
                    this.form.refid = this.detail.id;
                    this.form.avatarurl = localStorage.getItem('frontHeadportrait')?localStorage.getItem('frontHeadportrait'):'';
                    this.$http.post('discusszhunongshangpin/add', this.form).then(res => {
						if (res.data.code == 0) {
							this.addDiscussNum(2)
							this.form.content = '';
							this.getDiscussList(1);
							this.$message({
								type: 'success',
								message: '评论成功!',
								duration: 1500,
							});
						}
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
		  this.$http.post('zhunongshangpin/update',this.detail).then(res=>{
			  
		  })
	  },
      getCartList() {
        this.$http.get('cart/list', {params: {userid: localStorage.getItem('frontUserid'), tablename: 'zhunongshangpin', goodid: this.detail.id}}).then(res => {
          if (res.data.code == 0) {
            if (res.data.data.list.length > 0) {
              this.isInCart = true;
            } else {
              this.isInCart = false;
            }
          }
        });
      },
      addCart() {
            // 单次购买限制
            if (this.detail.onelimittimes > 0 && this.detail.onelimittimes < this.buynumber) {
                this.$message.error(`每人单次只能购买${this.detail.onelimittimes}件`);
                return
            }
            // 库存不够
            if (this.detail.alllimittimes <= 0 ) {
                this.$message.error(`商品已售罄`);
                return
            }
            // 库存限制
            if (this.detail.alllimittimes > 0 && this.detail.alllimittimes < this.buynumber) {
                this.$message.error(`库存不足`);
                return
            }
        if (this.isInCart) {
          this.$message.error('该商品已经添加到购物车');
          return;
        }

        this.cart.buynumber = this.buynumber;
        this.cart.goodid = this.detail.id;
        this.cart.goodname = this.title;
        this.cart.tablename = this.tablename;
        this.cart.nonghuzhanghao = this.detail.nonghuzhanghao;
        this.cart.picture = this.detailBanner[0];
        this.cart.price = (localStorage.getItem('vip')=='是'&&this.detail.vipprice>0)?this.detail.vipprice:this.detail.price;
        this.$http.post('cart/save', this.cart).then(res => {
          if (res.data.code === 0) {
            this.getCartList();
            this.$message({
              message: '添加成功',
              type: 'success',
              duration: 1500,
            });
          } else {
            this.$message.error(res.data.msg);
          }
        });
      },
        //立即购买
        buyNow() {
            // 单次购买限制
            if (this.detail.onelimittimes > 0 && this.detail.onelimittimes < this.buynumber) {
                this.$message.error(`每人单次只能购买${this.detail.onelimittimes}件`);
                return
            }
            // 库存不够
            if (this.detail.alllimittimes <= 0 ) {
                this.$message.error(`商品已售罄`);
                return
            }
            // 库存限制
            if (this.detail.alllimittimes > 0 && this.detail.alllimittimes < this.buynumber) {
                this.$message.error(`库存不足`);
                return
            }
            // 保存到storage中，在确认下单页面中获取要购买的商品
            localStorage.setItem('orderGoods', JSON.stringify([{
                tablename: this.tablename,
                goodid: this.detail.id,
                goodname: this.title,
                nonghuzhanghao: this.detail.nonghuzhanghao,

                picture:this.detailBanner[0],
                buynumber: this.buynumber,
                userid: localStorage.getItem('frontUserid'),
                price: (localStorage.getItem('vip')=='是'&&this.detail.vipprice>0)?this.detail.vipprice:this.detail.price,
                discountprice: this.detail.vipprice ? this.detail.vipprice : 0
            }]));
            // 跳转到确认下单页面
            let query = {
                type: 1,
            }
            this.$router.push({path: '/index/shop-order/orderConfirm', query: query});
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
			this.$router.push(`/index/zhunongshangpinAdd?type=edit&&id=${this.detail.id}`);
		},
		// 删除
		delClick(){
			this.$confirm('是否删除此助农商品？')
			  .then(_ => {
			    this.$http.post('zhunongshangpin/delete', [this.detail.id]).then(res => {
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
  /* ========== 商品详情页样式 ========== */

  /* 面包屑导航 */
  .breadcrumb-preview {
    position: relative;
    overflow: hidden;

    &::before {
      content: '';
      position: absolute;
      top: -50%;
      right: -10%;
      width: 400px;
      height: 400px;
      background: radial-gradient(circle, rgba(255,255,255,0.1), transparent 70%);
      pointer-events: none;
    }

    ::v-deep .el-breadcrumb {
      position: relative;
      z-index: 1;
    }
    ::v-deep .el-breadcrumb__separator {
      color: rgba(255,255,255,0.7) !important;
    }
    ::v-deep .el-breadcrumb__inner a,
    ::v-deep .el-breadcrumb__inner.is-link {
      color: rgba(255,255,255,0.9) !important;
      font-weight: 500;
      transition: all 0.2s ease;
    }
  }

  /* 返回按钮 */
  .back-btn {
    background: rgba(255,255,255,0.15) !important;
    border: 1px solid rgba(255,255,255,0.3) !important;
    color: #fff !important;
    border-radius: 8px !important;
    backdrop-filter: blur(10px);
    transition: all 0.25s ease;
  }

  /* 详情预览容器 */
  .detail-preview {
    position: relative;

    &::before {
      content: '';
      position: absolute;
      top: -50px;
      left: -100px;
      width: 300px;
      height: 300px;
      background: radial-gradient(circle, rgba(102, 187, 106, 0.08), transparent 70%);
      pointer-events: none;
      border-radius: 50%;
    }
  }

  /* 图片展示区域 */
  .swiper3 ::v-deep .big img {
    border-radius: 16px;
    transition: transform 0.4s ease;
  }

  .swiper3 ::v-deep .samll > div:hover {
    border-color: #4caf50 !important;
    transform: scale(0.95);
  }

  .swiper3 ::v-deep .samll > div:hover img {
    transform: scale(1.05);
  }

  /* 操作按钮样式 */
  .btn-cart {
    background: linear-gradient(135deg, #ff9800, #f57c00) !important;
    border-color: #f57c00 !important;
    border-radius: 10px !important;
    color: #fff !important;
    font-weight: 600 !important;
    padding: 10px 24px !important;
    box-shadow: 0 6px 20px rgba(255, 152, 0, 0.3);
    transition: all 0.25s ease;
  }

  .btn-buy {
    background: linear-gradient(135deg, #4caf50, #2e7d32) !important;
    border-color: #2e7d32 !important;
    border-radius: 10px !important;
    color: #fff !important;
    font-weight: 600 !important;
    padding: 10px 24px !important;
    box-shadow: 0 6px 20px rgba(46, 125, 50, 0.3);
    transition: all 0.25s ease;
  }

  .btn-edit {
    background: linear-gradient(135deg, #4caf50, #2e7d32) !important;
    border: none !important;
    border-radius: 10px !important;
    color: #fff !important;
    font-weight: 600 !important;
    padding: 10px 24px !important;
    transition: all 0.25s ease;
  }

  .btn-delete {
    background: linear-gradient(135deg, #e53935, #c62828) !important;
    border: none !important;
    border-radius: 10px !important;
    color: #fff !important;
    font-weight: 600 !important;
    padding: 10px 24px !important;
    transition: all 0.25s ease;
  }

  .btn-submit {
    background: linear-gradient(135deg, #4caf50, #2e7d32) !important;
    border: none !important;
    border-radius: 10px !important;
    color: #fff !important;
    font-weight: 600 !important;
    padding: 12px 28px !important;
    box-shadow: 0 6px 20px rgba(46, 125, 50, 0.3);
    transition: all 0.25s ease;
  }

  .btn-reset {
    background: #f8faf8 !important;
    border: 1px solid rgba(46, 125, 50, 0.2) !important;
    border-radius: 10px !important;
    color: #607060 !important;
    font-weight: 600 !important;
    padding: 12px 24px !important;
    transition: all 0.25s ease;
  }

  /* Tab样式 */
  .detail-preview .detail.el-tabs ::v-deep .el-tabs__header {
    margin: 0;
    background: linear-gradient(135deg, rgba(241, 248, 233, 0.8), rgba(255, 255, 255, 0.95));
    border-color: rgba(46, 125, 50, 0.1);
    border-width: 0 0 1px 0;
    border-style: solid;
  }

  .detail-preview .detail.el-tabs ::v-deep .el-tabs__header .el-tabs__item {
    border: 0;
    padding: 0 30px;
    margin: 0;
    color: #607060;
    background: transparent;
    font-weight: 600;
    display: inline-block;
    font-size: 15px;
    line-height: 60px;
    position: relative;
    list-style: none;
    height: 60px;
    transition: all 0.25s ease;
  }

  .detail-preview .detail.el-tabs ::v-deep .el-tabs__header .el-tabs__item:hover {
    color: #388e3c;
  }

  .detail-preview .detail.el-tabs ::v-deep .el-tabs__header .el-tabs__item.is-active {
    border: 0;
    color: #388e3c !important;
    background: transparent;
    font-weight: 700;
  }

  /* 详情内容 */
  .detail-content {
    padding: 24px;

    ::v-deep p {
      line-height: 1.8;
      color: #455445;
      margin-bottom: 16px;
    }

    ::v-deep img {
      max-width: 100%;
      border-radius: 12px;
      margin: 16px 0;
    }
  }

  /* 分页样式 */
  #pagination.el-pagination ::v-deep {
    .btn-prev,
    .btn-next {
      border: 1px solid rgba(46, 125, 50, 0.12) !important;
      border-radius: 12px !important;
      padding: 0 20px !important;
      margin: 0 6px;
      color: #607060;
      background: #fff;
      font-size: 14px;
      font-weight: 600;
      height: 42px;
      line-height: 42px;
      transition: all 0.25s ease;
    }

    .el-pager .number {
      border: 1px solid rgba(46, 125, 50, 0.12) !important;
      border-radius: 12px !important;
      padding: 0 16px !important;
      margin: 0 4px;
      color: #607060;
      font-size: 14px;
      font-weight: 600;
      background: #fff;
      height: 42px;
      line-height: 42px;
      transition: all 0.25s ease;
    }

    .el-pager .number:hover {
      color: #388e3c !important;
      border-color: #4caf50 !important;
      background: rgba(46, 125, 50, 0.06) !important;
      transform: translateY(-2px);
    }

    .el-pager .number.active {
      color: #fff !important;
      background: linear-gradient(135deg, #4caf50, #2e7d32) !important;
      border-color: transparent !important;
      box-shadow: 0 6px 20px rgba(46, 125, 50, 0.35);
      transform: translateY(-2px);
    }
  }

  /* 响应式 */
  @media (max-width: 992px) {
    .detail-preview {
      flex-direction: column;
    }

    .swiper3,
    .attr {
      width: 100% !important;
      margin: 0 0 20px 0 !important;
    }
  }
</style>
	
	.detail-preview {
	
	  .attr {
	    .el-carousel ::v-deep .el-carousel__indicator button {
	      width: 0;
	      height: 0;
	      display: none;
	    }
	
	    .el-input-number__decrease:hover:not(.is-disabled)~.el-input .el-input__inner:not(.is-disabled), .el-input-number__increase:hover:not(.is-disabled)~.el-input .el-input__inner:not(.is-disabled) {
	      border-color: none;
	    }
	  }
	
	  .detail {
	    & ::v-deep .el-tabs__header .el-tabs__nav-wrap {
	      margin-bottom: 0;
	    }
	
	    & .add .el-textarea {
	      width: auto;
	    }
	  }
	}
	
	.attr .el-carousel ::v-deep .el-carousel__container .el-carousel__arrow--left {
		width: 36px;
		font-size: 12px;
		height: 36px;
	}
	
	.attr .el-carousel ::v-deep .el-carousel__container .el-carousel__arrow--left:hover {
		background: red;
	}
	
	.attr .el-carousel ::v-deep .el-carousel__container .el-carousel__arrow--right {
		width: 36px;
		font-size: 12px;
		height: 36px;
	}
	
	.attr .el-carousel ::v-deep .el-carousel__container .el-carousel__arrow--right:hover {
		background: red;
	}

	.attr .el-carousel ::v-deep .el-carousel__indicators {
		padding: 0;
		margin: 0;
		z-index: 2;
		position: absolute;
		list-style: none;
	}
	
	.attr .el-carousel ::v-deep .el-carousel__indicators li {
		padding: 0;
		margin: 0 4px;
		background: #fff;
		display: inline-block;
		width: 12px;
		opacity: 0.4;
		transition: 0.3s;
		height: 12px;
	}
	
	.attr .el-carousel ::v-deep .el-carousel__indicators li:hover {
		padding: 0;
		margin: 0 4px;
		background: #fff;
		display: inline-block;
		width: 24px;
		opacity: 0.7;
		height: 12px;
	}
	
	.attr .el-carousel ::v-deep .el-carousel__indicators li.is-active {
		padding: 0;
		margin: 0 4px;
		background: #fff;
		display: inline-block;
		width: 24px;
		opacity: 1;
		height: 12px;
	}
	
	.attr .el-input-number ::v-deep .el-input-number__decrease {
		cursor: pointer;
		z-index: 1;
		display: flex;
		border-color: #DCDFE6;
		border-radius: 4px 0 0 4px;
		top: 1px;
		left: 1px;
		background: #8A8A8A;
		width: 40px;
		justify-content: center;
		border-width: 0 1px 0 0;
		align-items: center;
		position: absolute;
		border-style: solid;
		text-align: center;
		height: 38px;
	}
	
	.attr .el-input-number ::v-deep .el-input-number__decrease i {
		color: #fff;
		font-size: 14px;
	}

	.attr .el-input-number ::v-deep .el-input-number__increase {
		cursor: pointer;
		z-index: 1;
		display: flex;
		border-color: #DCDFE6;
		right: 1px;
		border-radius: 0 4px 4px 0;
		top: 1px;
		background: #8A8A8A;
		width: 40px;
		justify-content: center;
		border-width: 0 0 0 1px;
		align-items: center;
		position: absolute;
		border-style: solid;
		text-align: center;
		height: 38px;
	}
	
	.attr .el-input-number ::v-deep .el-input-number__increase i {
		color: #fff;
		font-size: 14px;
	}
	
	.attr .el-input-number ::v-deep .el-input .el-input__inner {
		border: 1px solid #DCDFE6;
		border-radius: 4px;
		padding: 0 40px;
		outline: none;
		color: #666;
		background: #FFF;
		display: inline-block;
		width: 100%;
		font-size: 14px;
		line-height: 40px;
		text-align: center;
		height: 40px;
	}
	
	.detail-preview .detail.el-tabs ::v-deep .el-tabs__header {
		margin: 0;
		background: #F7F9FA;
		border-color: #E4E7ED;
		border-width: 0;
		border-style: solid;
	}
	
	.detail-preview .detail.el-tabs ::v-deep .el-tabs__header .el-tabs__item {
		border: 0;
		padding: 0 0;
		margin: 0 40px;
		color: #000;
		background: transparent;
		font-weight: bold;
		display: inline-block;
		font-size: 18px;
		line-height: 60px;
		position: relative;
		list-style: none;
		height: 60px;
	}
	
	.detail-preview .detail.el-tabs ::v-deep .el-tabs__header .el-tabs__item:hover {
		border: 1px solid #3B2E7E;
		color: #3B2E7E;
		background: none;
		border-width: 0 0 2px;
	}
	
	.detail-preview .detail.el-tabs ::v-deep .el-tabs__header .el-tabs__item.is-active {
		border: 1px solid #3B2E7E;
		padding: 0 0;
		margin: 0 40px;
		color: #3B2E7E;
		background: none;
		font-weight: bold;
		font-size: 18px;
		border-width: 0 0 2px;
		line-height: 60px;
		height: 60px;
	}
	
	.detail-preview .detail.el-tabs ::v-deep .el-tabs__content {
		padding: 15px;
	}
	
	.detail-preview .detail.el-tabs .add ::v-deep .el-form-item__label {
		padding: 0 10px 0 0;
		color: #666;
		width: 80px;
		font-size: 14px;
		line-height: 40px;
		text-align: right;
	}
	
	.detail-preview .detail.el-tabs .add ::v-deep .el-textarea__inner {
	}
	
	.breadcrumb-preview .el-breadcrumb ::v-deep .el-breadcrumb__separator {
		margin: 0 20px;
		color: #fff;
		font-weight: 500;
	}
	
	.breadcrumb-preview .el-breadcrumb .item1 ::v-deep .el-breadcrumb__inner a {
		color: #fff;
		display: inline-block;
	}
	
	.breadcrumb-preview .el-breadcrumb .item2 ::v-deep .el-breadcrumb__inner a {
		color: #fff;
		display: inline-block;
	}
		
	.breadcrumb-preview .el-breadcrumb .item3 ::v-deep .el-breadcrumb__inner a {
		color: #fff;
		display: inline-block;
	}
	
	#pagination.el-pagination ::v-deep .el-pagination__total {
		margin: 0 10px 0 0;
		color: #666;
		font-weight: 400;
		display: inline-block;
		vertical-align: top;
		font-size: 13px;
		line-height: 28px;
		height: 28px;
	}
	
	#pagination.el-pagination ::v-deep .btn-prev {
		border: none;
		border-radius: 2px;
		padding: 0 10px;
		margin: 0 5px;
		color: #000;
		background: none;
		display: inline-block;
		vertical-align: top;
		font-size: 13px;
		line-height: 30px;
		min-width: 35px;
		height: 30px;
	}
	
	#pagination.el-pagination ::v-deep .btn-next {
		border: none;
		border-radius: 2px;
		padding: 0 10px;
		margin: 0 5px;
		color: #000;
		background: none;
		display: inline-block;
		vertical-align: top;
		font-size: 13px;
		line-height: 30px;
		min-width: 35px;
		height: 30px;
	}
	
	#pagination.el-pagination ::v-deep .btn-prev:disabled {
		border: none;
		cursor: not-allowed;
		border-radius: 2px;
		padding: 0 0;
		margin: 0 5px;
		color: #666;
		background: none;
		display: inline-block;
		vertical-align: top;
		font-size: 13px;
		line-height: 30px;
		height: 30px;
	}
	
	#pagination.el-pagination ::v-deep .btn-next:disabled {
		border: none;
		cursor: not-allowed;
		border-radius: 2px;
		padding: 0 0;
		margin: 0 5px;
		color: #666;
		background: none;
		display: inline-block;
		vertical-align: top;
		font-size: 13px;
		line-height: 30px;
		height: 30px;
	}
	
	#pagination.el-pagination ::v-deep .el-pager {
		padding: 0;
		margin: 0;
		display: inline-block;
		vertical-align: top;
	}
	
	#pagination.el-pagination ::v-deep .el-pager .number {
		cursor: pointer;
		padding: 0 4px;
		margin: 0 5px;
		color: #000;
		display: inline-block;
		vertical-align: top;
		font-size: 13px;
		line-height: 30px;
		border-radius: 50%;
		background: #9E9E9E;
		text-align: center;
		min-width: 30px;
		height: 30px;
	}
	
	#pagination.el-pagination ::v-deep .el-pager .number:hover {
		cursor: pointer;
		padding: 0 4px;
		margin: 0 5px;
		color: #000;
		display: inline-block;
		vertical-align: top;
		font-size: 13px;
		line-height: 30px;
		border-radius: 50%;
		background: #A293B6;
		text-align: center;
		min-width: 30px;
		height: 30px;
}

#pagination.el-pagination ::v-deep .el-pager .number.active {
		cursor: default;
		padding: 0 4px;
		margin: 0 5px;
		color: #000;
		display: inline-block;
		vertical-align: top;
		font-size: 13px;
		line-height: 30px;
		border-radius: 50%;
		background: #A293B6;
		text-align: center;
		min-width: 30px;
		height: 30px;
	}
	
	#pagination.el-pagination ::v-deep .el-pagination__sizes {
		display: inline-block;
		vertical-align: top;
		font-size: 13px;
		line-height: 28px;
		height: 28px;
	}
	
	#pagination.el-pagination ::v-deep .el-pagination__sizes .el-input {
		margin: 0 5px;
		width: 100px;
		position: relative;
	}
	
	#pagination.el-pagination ::v-deep .el-pagination__sizes .el-input .el-input__inner {
		border: 1px solid #DCDFE6;
		cursor: pointer;
		padding: 0 25px 0 8px;
		color: #606266;
		display: inline-block;
		font-size: 13px;
		line-height: 28px;
		border-radius: 3px;
		outline: 0;
		background: #FFF;
		width: 100%;
		text-align: center;
		height: 28px;
	}
	
	#pagination.el-pagination ::v-deep .el-pagination__sizes .el-input span.el-input__suffix {
		top: 0;
		position: absolute;
		right: 0;
		height: 100%;
	}
	
	#pagination.el-pagination ::v-deep .el-pagination__sizes .el-input .el-input__suffix .el-select__caret {
		cursor: pointer;
		color: #C0C4CC;
		width: 25px;
		font-size: 14px;
		line-height: 28px;
		text-align: center;
	}

	#pagination.el-pagination ::v-deep .el-pagination__jump {
		margin: 0 0 0 24px;
		color: #606266;
		display: inline-block;
		vertical-align: top;
		font-size: 13px;
		line-height: 28px;
		height: 28px;
	}
	
	#pagination.el-pagination ::v-deep .el-pagination__jump .el-input {
		border-radius: 3px;
		padding: 0 2px;
		margin: 0 2px;
		display: inline-block;
		width: 50px;
		font-size: 14px;
		line-height: 18px;
		position: relative;
		text-align: center;
		height: 28px;
	}
	
	#pagination.el-pagination ::v-deep .el-pagination__jump .el-input .el-input__inner {
		border: 1px solid #DCDFE6;
		cursor: pointer;
		padding: 0 3px;
		color: #606266;
		display: inline-block;
		font-size: 14px;
		line-height: 28px;
		border-radius: 3px;
		outline: 0;
		background: #FFF;
		width: 100%;
		text-align: center;
		height: 28px;
	}
	.share_view{
		position: fixed;
		right:0;
		bottom: 20%;
		background: #fff;
		box-shadow: 0 4px 6px rgba(0,0,0,.1);
		.share{
			width: 40px;
			height: 40px;
			display: flex;
			align-items: center;
			justify-content: center;
			border-bottom: 1px solid #eee;
			cursor: pointer;
		}
		.share:last-of-type{
			border:none;
		}
	}


	.detail-preview .el-rate ::v-deep .el-rate__item {
				cursor: pointer;
				display: inline-block;
				vertical-align: middle;
				font-size: 0;
				position: relative;
			}
	
	.detail-preview .el-rate ::v-deep .el-rate__item .el-rate__icon {
				margin: 0 3px;
				display: block;
				font-size: 18px;
				position: relative;
				transition: .3s;
			}
</style>
