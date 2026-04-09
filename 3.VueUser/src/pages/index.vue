<template>
	<div class="main-containers">
		<div class="body-containers" :style='{"minHeight":"100vh"}'>
		<div class="top-container">
			<!-- info -->
			<div class="brand">
			  <span class="brand-title">助农扶贫服务平台</span>
			</div>
			
			<div v-if="false" :style='{"color":"#666","margin":"0 10px","fontSize":"14px"}'>0753-1234567</div>

			<el-button class="btn-icon btn-hamburger" @click="mobileNavOpen = true" icon="el-icon-menu"></el-button>
			<el-button v-if="Token" class="btn-shop" @click.native="goMenu('/index/cart')">
				<span class="icon iconfont icon-wuliu8" :style='{"color":"inherit","margin":"0 4px 0 0","fontSize":"inherit"}'></span>
				购物车
			</el-button>
			
			<img v-if="headportrait&&Token" :style='{"width":"40px","margin":"0 0 0 10px","borderRadius":"50%","display":"block","height":"40px","order":"6"}' :src="headportrait?baseUrl + headportrait:require('@/assets/avator.png')">
			<div v-if="Token" :style='{"padding":"0 ","margin":"0 10px","color":"#333","width":"auto","fontSize":"16px","lineHeight":"70px","height":"70px","order":"5"}'>{{username}}</div>
			<div v-if="Token && notAdmin" :style='{"padding":"0 0","margin":"0 10px","color":"#333","textAlign":"center","width":"auto","fontSize":"16px","lineHeight":"70px","height":"70px","order":"7"}' @click="goMenu('/index/center')">个人中心</div>
			<el-button v-if="!Token" class="btn-auth" @click="toLogin()">登录/注册</el-button>
			<el-button v-if="Token" class="btn-auth" @click="logout">退出</el-button>
		</div>

		<el-drawer
			:visible.sync="mobileNavOpen"
			direction="ltr"
			size="78%"
			custom-class="mobile-drawer"
			:with-header="false"
		>
			<div class="mobile-drawer__header">
				<div class="mobile-drawer__brand">
					<div class="mobile-drawer__title">助农扶贫服务平台</div>
					<div class="mobile-drawer__sub">清新 · 简约 · 可信赖</div>
				</div>
				<el-button class="btn-icon" icon="el-icon-close" @click="mobileNavOpen = false"></el-button>
			</div>
			<div class="mobile-drawer__menu">
				<el-menu
					:default-active="activeMenu"
					:router="true"
					@select="handleMobileSelect"
				>
					<el-menu-item index="/index/home">
						<i class="el-icon-house"></i>
						<span slot="title">系统首页</span>
					</el-menu-item>
					<el-menu-item v-for="(menu, index) in menuList" :key="index" :index="menu.url">
						<i :class="iconArr[index]"></i>
						<span slot="title">{{menu.name}}</span>
					</el-menu-item>
					<el-menu-item index="/index/cart" v-if="Token">
						<i class="el-icon-shopping-cart-2"></i>
						<span slot="title">购物车</span>
					</el-menu-item>
					<el-menu-item index="/index/center" v-if="Token && notAdmin">
						<i class="el-icon-user"></i>
						<span slot="title">个人中心</span>
					</el-menu-item>
				</el-menu>
			</div>
		</el-drawer>

			<div class="menu-preview">
			<el-scrollbar wrap-class="scrollbar-wrapper-horizontal">
				<el-menu class="el-menu-horizontal-demo" :style='{"border":0,"padding":"0","listStyle":"none","margin":"0","background":"none","display":"flex","position":"relative"}' :default-active="activeMenu" :unique-opened="true" mode="horizontal" :router="true" @select="handleSelect">
					<div class="userinfo" :style='{"width":"84px","padding":"6px 10px 0","display":"none","height":"auto"}'>
					  <el-image :style='{"width":"100%","objectFit":"cover","borderRadius":"20px","display":"block","height":"32px"}' src="http://codegen.caihongy.cn/20201114/7856ba26477849ea828f481fa2773a95.jpg" fit="cover"></el-image>
					  <div :style='{"fontSize":"18px","lineHeight":"1.5","color":"#333","textAlign":"center"}'>nickname</div>
					</div>
					<el-menu-item class="home" index="/index/home" @click.native="goMenu('/index/home')">
						<span :style='{"padding":"0 10px","margin":"0 2px 0 0","color":"inherit","width":"18px","lineHeight":"70px","fontSize":"inherit","height":"70px"}' class="icon iconfont icon-shouye-zhihui"></span>
						<span :style='{"padding":"0 10px","lineHeight":"70px","fontSize":"inherit","color":"inherit","height":"70px"}'>系统首页</span>
					</el-menu-item>
					<el-menu-item class="item" v-for="(menu, index) in menuList" :index="menu.url" :key="index" @click.native="goMenu(menu.url)">
						<i :style='{"padding":"0 10px","margin":"0 2px 0 0","color":"inherit","width":"14px","lineHeight":"70px","fontSize":"inherit","height":"70px"}' :class="iconArr[index]"></i>
						<span :style='{"padding":"0 10px","lineHeight":"70px","fontSize":"inherit","color":"inherit","height":"70px"}'>{{menu.name}}</span>
					</el-menu-item>
					<el-menu-item class="shop" index="/index/cart" @click.native="goMenu('/index/cart')">
						<span :style='{"padding":"0 10px","margin":"0","color":"inherit","width":"14px","lineHeight":"56px","fontSize":"14px","height":"56px"}' class="icon iconfont icon-wuliu8"></span>
						<span :style='{"padding":"0 10px","lineHeight":"56px","fontSize":"14px","color":"inherit","height":"56px"}'>购物车</span>
					</el-menu-item>
					<el-menu-item class="user" index="/index/center" v-if="Token && notAdmin" @click.native="goMenu('/index/center')">
						<span :style='{"padding":"0 10px","margin":"0","color":"inherit","width":"14px","lineHeight":"56px","fontSize":"14px","height":"56px"}' class="icon iconfont icon-shouye-zhihui"></span>
						<span :style='{"padding":"0 10px","lineHeight":"56px","fontSize":"14px","color":"inherit","height":"56px"}'>个人中心</span>
					</el-menu-item>
				</el-menu>
			</el-scrollbar>
			</div>


			<div v-if="$route.path === '/index/home'" class="banner-preview">
				<el-carousel :style='{"width":"100%","margin":"0 auto"}' trigger="click" indicator-position="inside" arrow="always" type="default" direction="vertical" height="600px" :autoplay="true" :interval="3000" :loop="true">
					<el-carousel-item :style='{"borderRadius":"8px","width":"100%","height":"100%"}' v-for="item in carouselList" :key="item.id">
						<el-image @click="carouselClick(item.url)" :style='{"objectFit":"cover","width":"100%","height":"100%","borderRadius":"8px"}' :src="carouselImgSrc(item)" fit="cover">
							<div slot="error" class="carousel-img-error">图片加载失败</div>
						</el-image>
					</el-carousel-item>
				</el-carousel>
				<div class="hero-overlay">
					<div v-if="showHeroIntro" class="hero-card">
						<button class="hero-close" type="button" aria-label="关闭介绍" @click="dismissHeroIntro"></button>
						<div class="hero-kicker">农业绿色 · 清新简约</div>
						<div class="hero-title">让优质农产品更快触达需要的人</div>
						<div class="hero-desc">助农商品、助农乐学、资讯公告一站式服务，体验更轻盈，信息更清晰。</div>
						<div class="hero-actions">
							<el-button class="btn-hero-primary" @click="goMenu('/index/home')">立即进入</el-button>
							<el-button class="btn-hero-ghost" plain @click="Token && notAdmin ? goMenu('/index/center') : toLogin()">
								{{ Token ? '查看个人中心' : '登录/注册' }}
							</el-button>
						</div>
					</div>
					<button v-else class="hero-mini" type="button" @click="reopenHeroIntro">平台介绍</button>
				</div>
			</div>

			<section v-if="isHome" class="home-sections">
				<div class="mission">
					<div class="mission__left">
						<div class="mission__tag">助农扶贫 · 主题表达</div>
						<div class="mission__title">连接农户与用户，让帮扶看得见、可追溯、可持续</div>
						<div class="mission__desc">
							以“助农商品 + 扶贫项目 + 助农乐学”为核心，打造信息透明、体验清爽的服务平台，让公益与效率并行。
						</div>
						<div class="mission__chips">
							<span class="chip">公开透明</span>
							<span class="chip">便捷触达</span>
							<span class="chip">价值可见</span>
						</div>
					</div>
					<div class="mission__right">
						<div class="stats">
							<div class="stat">
								<div class="stat__num">{{ carouselList.length }}</div>
								<div class="stat__label">轮播展示</div>
							</div>
							<div class="stat">
								<div class="stat__num">{{ Token ? '已登录' : '访客' }}</div>
								<div class="stat__label">当前状态</div>
							</div>
						</div>
						<div class="trust">
							<div class="trust__title">公益可信背书</div>
							<div class="trust__list">
								<div class="trust__item">
									<div class="trust__dot"></div>
									<div class="trust__text">信息发布与管理流程可控，减少误导与冗余</div>
								</div>
								<div class="trust__item">
									<div class="trust__dot"></div>
									<div class="trust__text">关键入口清晰直达，降低使用门槛</div>
								</div>
								<div class="trust__item">
									<div class="trust__dot"></div>
									<div class="trust__text">页面统一配色与交互动效，提升专业感</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="quick-entries">
					<div class="section-hd">
						<div class="section-hd__title">核心模块</div>
						<div class="section-hd__sub">围绕“卖得出、学得到、帮得上”设计的六大入口</div>
					</div>
					<div class="entry-grid">
						<div class="entry-card" @click="goMenu('/index/zhunongshangpin')">
							<div class="entry-card__head">
								<div class="entry-card__icon entry-card__icon--goods">
									<i class="el-icon-shopping-bag-1"></i>
								</div>
								<div class="entry-card__title">助农商品</div>
							</div>
							<div class="entry-card__desc">优质农产品直达需求方，交易链路更清晰</div>
							<div class="entry-card__cta">进入模块</div>
						</div>
						<div class="entry-card" @click="goMenu('/index/zhunongtuopin')">
							<div class="entry-card__head">
								<div class="entry-card__icon entry-card__icon--project">
									<i class="el-icon-collection-tag"></i>
								</div>
								<div class="entry-card__title">助农案例</div>
							</div>
							<div class="entry-card__desc">助农项目与成功案例，让帮扶更可见更可学</div>
							<div class="entry-card__cta">进入模块</div>
						</div>
						<div class="entry-card" @click="goMenu('/index/nongjixuetang')">
							<div class="entry-card__head">
								<div class="entry-card__icon entry-card__icon--learn">
									<i class="el-icon-reading"></i>
								</div>
								<div class="entry-card__title">助农乐学</div>
							</div>
							<div class="entry-card__desc">标准化农技知识与课程，提升生产效率</div>
							<div class="entry-card__cta">进入模块</div>
						</div>
						<div class="entry-card" @click="goMenu('/index/bangfuxiangmu')">
							<div class="entry-card__head">
								<div class="entry-card__icon entry-card__icon--support">
									<i class="el-icon-link"></i>
								</div>
								<div class="entry-card__title">帮扶对接</div>
							</div>
							<div class="entry-card__desc">精准匹配帮扶资源，搭建供需对接桥梁</div>
							<div class="entry-card__cta">进入模块</div>
						</div>
						<div class="entry-card" @click="goMenu('/index/news')">
							<div class="entry-card__head">
								<div class="entry-card__icon entry-card__icon--policy">
									<i class="el-icon-document"></i>
								</div>
								<div class="entry-card__title">公告资讯</div>
							</div>
							<div class="entry-card__desc">及时了解农业政策，获取专业咨询服务</div>
							<div class="entry-card__cta">进入模块</div>
						</div>
						<div class="entry-card" @click="goMenu('/index/qiyeruzhuApply')">
							<div class="entry-card__head">
								<div class="entry-card__icon entry-card__icon--enterprise">
									<i class="el-icon-office-building"></i>
								</div>
								<div class="entry-card__title">农商企业入驻</div>
							</div>
							<div class="entry-card__desc">为农商企业提供便捷的入驻通道，拓展销售渠道</div>
							<div class="entry-card__cta">进入模块</div>
						</div>
					</div>
				</div>
			</section>

			<router-view id="scrollView"></router-view>
			
			<div class="bottom-preview">
				<div class="footer">
					<div class="footer-grid">
						<div class="footer-col">
							<div class="footer-title">平台介绍</div>
							<div class="footer-text">以低饱和自然绿为主的清新体验，帮助你更高效地获取农产品与农技信息。</div>
						</div>
						<div class="footer-col">
							<div class="footer-title">快捷入口</div>
							<div class="footer-links" style="display: grid; grid-template-columns: 1fr 1fr; gap: 10px;">
								<a @click.prevent="goMenu('/index/home')" href="#">系统首页</a>
								<a @click.prevent="goMenu('/index/zhunongshangpin')" href="#">助农商品</a>
								<a @click.prevent="goMenu('/index/zhunongtuopin')" href="#">助农案例</a>
								<a @click.prevent="goMenu('/index/nongjixuetang')" href="#">助农乐学</a>
								<a @click.prevent="goMenu('/index/bangfuxiangmu')" href="#">帮扶对接</a>
								<a @click.prevent="goMenu('/index/news')" href="#">公告资讯</a>
								<a @click.prevent="goMenu('/index/qiyeruzhuApply')" href="#">农商企业入驻</a>
								<a v-if="Token" @click.prevent="goMenu('/index/cart')" href="#">购物车</a>
								<a v-if="Token && notAdmin" @click.prevent="goMenu('/index/center')" href="#">个人中心</a>
							</div>
						</div>
						<div class="footer-col">
							<div class="footer-title">服务承诺</div>
							<div class="footer-badges">
								<span class="badge">信息透明</span>
								<span class="badge">服务便捷</span>
								<span class="badge">体验清爽</span>
							</div>
						</div>
					</div>
					<div class="footer-rich" v-if="bottomContent">
						<div class="footer-rich__card">
							<div v-html="bottomContent"></div>
						</div>
					</div>
					<div class="footer-bottom">© {{ new Date().getFullYear() }} 助农扶贫服务平台</div>
				</div>
			</div>
		</div>
		
	</div>
</template>

<script>
import Vue from 'vue'
import Swiper from "swiper";
import axios from 'axios'

export default {
    data() {
		return {
            activeIndex: '0',
			mobileNavOpen: false,
			showHeroIntro: true,
			roleMenus: [{"backMenu":[{"child":[{"allButtons":["新增","查看","修改","删除"],"appFrontIcon":"cuIcon-paint","buttons":["新增","查看","修改","删除"],"menu":"农商","menuJump":"列表","tableName":"nonghu"}],"menu":"农商管理"},{"child":[{"allButtons":["新增","查看","修改","删除"],"appFrontIcon":"cuIcon-wenzi","buttons":["新增","查看","修改","删除"],"menu":"用户","menuJump":"列表","tableName":"yonghu"}],"menu":"用户管理"},{"child":[{"allButtons":["新增","查看","修改","删除"],"appFrontIcon":"cuIcon-link","buttons":["新增","查看","修改","删除"],"menu":"产品分类","menuJump":"列表","tableName":"chanpinfenlei"}],"menu":"产品分类管理"},{"child":[{"allButtons":["新增","查看","修改","删除"],"appFrontIcon":"cuIcon-cardboard","buttons":["新增","查看","修改","删除"],"menu":"文章分类","menuJump":"列表","tableName":"wenzhangfenlei"}],"menu":"文章分类管理"},{"child":[{"allButtons":["新增","查看","修改","删除","产品库存统计","产品分类","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-similar","buttons":["查看","修改","删除","查看评论","产品库存统计","产品分类","首页总数","首页统计"],"menu":"助农商品","menuJump":"列表","tableName":"zhunongshangpin"}],"menu":"助农商品管理"},{"child":[{"allButtons":["新增","查看","修改","删除","文章分类","文章数量","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-send","buttons":["新增","查看","修改","删除","查看评论","文章分类","文章数量","首页总数","首页统计"],"menu":"助农脱贫","menuJump":"列表","tableName":"zhunongtuopin"}],"menu":"助农脱贫管理"},{"child":[{"allButtons":["新增","查看","修改","删除","课程占比","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-time","buttons":["新增","查看","修改","删除","查看评论","课程占比","首页总数","首页统计"],"menu":"农技学堂","menuJump":"列表","tableName":"nongjixuetang"}],"menu":"农技学堂管理"},{"child":[{"allButtons":["新增","查看","修改","删除"],"appFrontIcon":"cuIcon-news","buttons":["新增","查看","修改","删除"],"menu":"公告资讯","tableName":"news"},{"allButtons":["新增","查看","修改","删除"],"appFrontIcon":"cuIcon-news","buttons":["新增","查看","修改","删除"],"menu":"公告资讯分类","tableName":"newstype"},{"allButtons":["查看","修改"],"appFrontIcon":"cuIcon-skin","buttons":["查看","修改"],"menu":"关于我们","tableName":"aboutus"},{"allButtons":["查看","修改"],"appFrontIcon":"cuIcon-circle","buttons":["查看","修改"],"menu":"系统简介","tableName":"systemintro"},{"allButtons":["新增","查看","修改","删除"],"appFrontIcon":"cuIcon-attentionfavor","buttons":["查看","修改","删除"],"menu":"轮播图管理","tableName":"config"}],"menu":"系统管理"},{"child":[{"allButtons":["新增","查看","修改","删除","导出","日销量","月销量","年销量","品销量","类销量","日销额","月销额","年销额","品销额","类销额"],"appFrontIcon":"cuIcon-vip","buttons":["查看","删除"],"menu":"已取消订单","tableName":"orders/已取消"},{"allButtons":["新增","查看","修改","删除","导出","日销量","月销量","年销量","品销量","类销量","日销额","月销额","年销额","品销额","类销额","物流"],"appFrontIcon":"cuIcon-pay","buttons":["查看","删除"],"menu":"已退款订单","tableName":"orders/已退款"},{"allButtons":["新增","查看","修改","删除","导出","日销量","月销量","年销量","品销量","类销量","日销额","月销额","年销额","品销额","类销额","确认收货","物流"],"appFrontIcon":"cuIcon-phone","buttons":["查看","删除"],"menu":"已发货订单","tableName":"orders/已发货"},{"allButtons":["新增","查看","修改","删除","导出","日销量","月销量","年销量","品销量","类销量","日销额","月销额","年销额","品销额","类销额"],"appFrontIcon":"cuIcon-vip","buttons":["查看","删除"],"menu":"未支付订单","tableName":"orders/未支付"},{"allButtons":["新增","查看","修改","删除","导出","日销量","月销量","年销量","品销量","类销量","日销额","月销额","年销额","品销额","类销额","发货","物流","核销"],"appFrontIcon":"cuIcon-album","buttons":["查看","删除"],"menu":"已支付订单","tableName":"orders/已支付"},{"allButtons":["新增","查看","修改","删除","导出","日销量","月销量","年销量","品销量","类销量","日销额","月销额","年销额","品销额","类销额","物流","退货审核"],"appFrontIcon":"cuIcon-vipcard","buttons":["查看","删除","日销量","月销量","日销额","月销额"],"menu":"已完成订单","tableName":"orders/已完成"}],"menu":"订单管理"}],"frontMenu":[{"child":[{"allButtons":["新增","查看","修改","删除","产品库存统计","产品分类","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-attentionfavor","buttons":["查看","查看评论"],"menu":"助农商品列表","menuJump":"列表","tableName":"zhunongshangpin"}],"menu":"助农商品模块"},{"child":[{"allButtons":["新增","查看","修改","删除","文章分类","文章数量","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-cardboard","buttons":["查看","查看评论"],"menu":"助农脱贫列表","menuJump":"列表","tableName":"zhunongtuopin"}],"menu":"助农脱贫模块"},{"child":[{"allButtons":["新增","查看","修改","删除","课程占比","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-goodsnew","buttons":["查看","查看评论"],"menu":"农技学堂列表","menuJump":"列表","tableName":"nongjixuetang"}],"menu":"农技学堂模块"}],"hasBackLogin":"是","hasBackRegister":"否","hasFrontLogin":"否","hasFrontRegister":"否","roleName":"管理员","tableName":"users"},{"backMenu":[{"child":[{"allButtons":["新增","查看","修改","删除"],"appFrontIcon":"cuIcon-link","buttons":["新增","查看","修改","删除"],"menu":"产品分类","menuJump":"列表","tableName":"chanpinfenlei"}],"menu":"产品分类管理"},{"child":[{"allButtons":["新增","查看","修改","删除","产品库存统计","产品分类","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-similar","buttons":["新增","查看","修改","删除","查看评论"],"menu":"助农商品","menuJump":"列表","tableName":"zhunongshangpin"}],"menu":"助农商品管理"},{"child":[{"allButtons":["新增","查看","修改","删除","导出","日销量","月销量","年销量","品销量","类销量","日销额","月销额","年销额","品销额","类销额","发货","物流","核销"],"appFrontIcon":"cuIcon-album","buttons":["查看","删除","发货"],"menu":"已支付订单","tableName":"orders/已支付"},{"allButtons":["新增","查看","修改","删除","导出","日销量","月销量","年销量","品销量","类销量","日销额","月销额","年销额","品销额","类销额","物流","退货审核"],"appFrontIcon":"cuIcon-vipcard","buttons":["查看","删除","日销量","月销量","日销额","月销额"],"menu":"已完成订单","tableName":"orders/已完成"},{"allButtons":["新增","查看","修改","删除","导出","日销量","月销量","年销量","品销量","类销量","日销额","月销额","年销额","品销额","类销额"],"appFrontIcon":"cuIcon-vip","buttons":["查看","删除"],"menu":"已取消订单","tableName":"orders/已取消"},{"allButtons":["新增","查看","修改","删除","导出","日销量","月销量","年销量","品销量","类销量","日销额","月销额","年销额","品销额","类销额","物流"],"appFrontIcon":"cuIcon-pay","buttons":["查看","删除"],"menu":"已退款订单","tableName":"orders/已退款"},{"allButtons":["新增","查看","修改","删除","导出","日销量","月销量","年销量","品销量","类销量","日销额","月销额","年销额","品销额","类销额","确认收货","物流"],"appFrontIcon":"cuIcon-phone","buttons":["查看","删除"],"menu":"已发货订单","tableName":"orders/已发货"},{"allButtons":["新增","查看","修改","删除","导出","日销量","月销量","年销量","品销量","类销量","日销额","月销额","年销额","品销额","类销额"],"appFrontIcon":"cuIcon-vip","buttons":["查看","删除"],"menu":"未支付订单","tableName":"orders/未支付"}],"menu":"订单管理"}],"frontMenu":[{"child":[{"allButtons":["新增","查看","修改","删除","产品库存统计","产品分类","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-attentionfavor","buttons":["查看","查看评论"],"menu":"助农商品列表","menuJump":"列表","tableName":"zhunongshangpin"}],"menu":"助农商品模块"},{"child":[{"allButtons":["新增","查看","修改","删除","文章分类","文章数量","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-cardboard","buttons":["查看","查看评论"],"menu":"助农脱贫列表","menuJump":"列表","tableName":"zhunongtuopin"}],"menu":"助农脱贫模块"},{"child":[{"allButtons":["新增","查看","修改","删除","课程占比","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-goodsnew","buttons":["查看","查看评论"],"menu":"农技学堂列表","menuJump":"列表","tableName":"nongjixuetang"}],"menu":"农技学堂模块"}],"hasBackLogin":"是","hasBackRegister":"是","hasFrontLogin":"否","hasFrontRegister":"否","roleName":"农商","tableName":"nonghu"},{"backMenu":[],"frontMenu":[{"child":[{"allButtons":["新增","查看","修改","删除","产品库存统计","产品分类","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-attentionfavor","buttons":["查看","查看评论"],"menu":"助农商品列表","menuJump":"列表","tableName":"zhunongshangpin"}],"menu":"助农商品模块"},{"child":[{"allButtons":["新增","查看","修改","删除","文章分类","文章数量","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-cardboard","buttons":["查看","查看评论"],"menu":"助农脱贫列表","menuJump":"列表","tableName":"zhunongtuopin"}],"menu":"助农脱贫模块"},{"child":[{"allButtons":["新增","查看","修改","删除","课程占比","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-goodsnew","buttons":["查看","查看评论"],"menu":"农技学堂列表","menuJump":"列表","tableName":"nongjixuetang"}],"menu":"农技学堂模块"}],"hasBackLogin":"否","hasBackRegister":"否","hasFrontLogin":"是","hasFrontRegister":"是","roleName":"用户","tableName":"yonghu"}],
			baseUrl: '',
			carouselList: [],
			menuList: [],
			form: {
				ask: '',
				userid: localStorage.getItem('frontUserid')
			},
			headportrait: localStorage.getItem('frontHeadportrait')?localStorage.getItem('frontHeadportrait'):'',
			Token: localStorage.getItem('frontToken'),
            username: localStorage.getItem('username'),
            notAdmin: localStorage.getItem('frontSessionTable')!='"users"',
			timer: '',
			iconArr: [
				'el-icon-star-off',
				'el-icon-goods',
				'el-icon-warning',
				'el-icon-question',
				'el-icon-info',
				'el-icon-help',
				'el-icon-picture-outline-round',
				'el-icon-camera-solid',
				'el-icon-video-camera-solid',
				'el-icon-video-camera',
				'el-icon-bell',
				'el-icon-s-cooperation',
				'el-icon-s-order',
				'el-icon-s-platform',
				'el-icon-s-operation',
				'el-icon-s-promotion',
				'el-icon-s-release',
				'el-icon-s-ticket',
				'el-icon-s-management',
				'el-icon-s-open',
				'el-icon-s-shop',
				'el-icon-s-marketing',
				'el-icon-s-flag',
				'el-icon-s-comment',
				'el-icon-s-finance',
				'el-icon-s-claim',
				'el-icon-s-opportunity',
				'el-icon-s-data',
				'el-icon-s-check'
			],
			bottomContent: '',
		}
    },
    created() {
		this.baseUrl = this.$config.baseUrl;
		this.menuList = this.$config.indexNav;
		this.getCarousel();
		this.showHeroIntro = localStorage.getItem('heroIntroDismissed') !== '1';
        if(localStorage.getItem('frontToken') && localStorage.getItem('frontToken')!=null) {
			this.getSession()
        }
    },
    mounted() {
        this.activeIndex = localStorage.getItem('keyPath') || '0';



    },
    computed: {
		activeMenu() {
			const route = this.$route
			const {
				meta,
				path
			} = route
			// if st path, the sidebar will highlight the path you sete
			if (meta.activeMenu) {
				return meta.activeMenu
			}
			return path
		},
		isHome() {
			return this.$route && this.$route.path === '/index/home'
		},
    },
    watch: {
        $route(newValue) {
            let that = this
            let url = window.location.href
            let arr = url.split('#')
            for (let x in this.menuList) {
                if (newValue.path == this.menuList[x].url) {
                    this.activeIndex = x
                }
            }
            this.Token = localStorage.getItem('frontToken')
            // 始终滚动到页面顶部
            window.scrollTo( 0, 0 )
        },
		headportrait(){
			this.$forceUpdate()
		},
    },
    methods: {

		dismissHeroIntro() {
			this.showHeroIntro = false;
			localStorage.setItem('heroIntroDismissed', '1');
		},
		reopenHeroIntro() {
			this.showHeroIntro = true;
			localStorage.removeItem('heroIntroDismissed');
		},

		async getSession() {
			await this.$http.get(`${localStorage.getItem('UserTableName')}/session`, {emulateJSON: true}).then(async res => {
				if (res.data.code == 0) {
					localStorage.setItem('sessionForm',JSON.stringify(res.data.data))
					localStorage.setItem('frontUserid', res.data.data.id);
					if(res.data.data.vip) {
						localStorage.setItem('vip', res.data.data.vip);
					}
					if(res.data.data.touxiang) {
						this.headportrait = res.data.data.touxiang
						localStorage.setItem('frontHeadportrait', res.data.data.touxiang);
					} else if(res.data.data.headportrait) {
						this.headportrait = res.data.data.headportrait
						localStorage.setItem('frontHeadportrait', res.data.data.headportrait);
					}
				}
			});
		},
        handleSelect(keyPath) {
            if (keyPath) {
                localStorage.setItem('keyPath', keyPath)
            }
        },
		handleMobileSelect(keyPath) {
			this.handleSelect(keyPath)
			this.mobileNavOpen = false
		},
		toLogin() {
		  this.$router.push('/login');
		},
        logout() {
            localStorage.clear();
            Vue.http.headers.common['Token'] = "";
            this.$router.push('/index/home');
            this.activeIndex = '0'
            localStorage.setItem('keyPath', this.activeIndex)
            this.Token = ''
            this.$forceUpdate()
            this.$message({
                message: '登出成功',
                type: 'success',
                duration: 1000,
            });
        },
		carouselImgSrc(item) {
			const v = (item && item.value ? String(item.value) : "").trim();
			if (!v) {
				return "";
			}
			if (v.startsWith("http://") || v.startsWith("https://")) {
				return v;
			}
			const base = (this.baseUrl || "").replace(/\/?$/, "/");
			return base + v.replace(/^\//, "");
		},
		getCarousel() {
			// 获取轮播图数据，按名称排序
			this.$http.get('config/list', {params: { page: 1, limit: 10, sort: 'id', order: 'asc' }}).then(res => {
				if (res.data.code == 0 && res.data.data && res.data.data.list) {
					// 只保留有图片的轮播图
					this.carouselList = res.data.data.list.filter(item => item.value && item.value.trim() !== '');
				} else {
					this.carouselList = [];
				}
			}).catch(() => {
				this.carouselList = [];
			});
		},
		// 轮播图跳转
		carouselClick(url) {
			if (url) {
				if (url.indexOf('https') != -1) {
					window.open(url)
				} else {
					this.$router.push(url)
				}
			}
		},
		goBackend() {
			localStorage.setItem('Token', localStorage.getItem('frontToken'));
			localStorage.setItem('role', localStorage.getItem('frontRole'));
			localStorage.setItem('sessionTable', localStorage.getItem('frontSessionTable'));
			localStorage.setItem('headportrait', localStorage.getItem('frontHeadportrait'));
			localStorage.setItem('userid', localStorage.getItem('frontUserid'));
			window.open(`${this.$config.baseUrl}admin/dist/index.html`, "_blank");
		},
		goMenu(path) {
            this.$router.push(path);
		},
    }
}
</script>

<style lang="scss" scoped>
	.main-containers {
		--ag-green-900: #1b5e20;
		--ag-green-800: #2e7d32;
		--ag-green-700: #388e3c;
		--ag-green-500: #4caf50;
		--ag-green-400: #66bb6a;
		--ag-green-100: #e8f5e9;
		--ag-green-050: #f1f8e9;
		--ag-text-900: #1b2b1b;
		--ag-text-700: #2f3a2f;
		--ag-text-500: #607060;
		--ag-surface: rgba(255, 255, 255, 0.84);
		--ag-border: rgba(46, 125, 50, 0.14);
		--ag-shadow: 0 10px 30px rgba(16, 24, 16, 0.10);
		--ag-shadow-hover: 0 16px 42px rgba(16, 24, 16, 0.16);
		--ag-radius: 12px;
		--ag-transition: 220ms cubic-bezier(0.2, 0.8, 0.2, 1);
	}

	.body-containers {
		position: relative;
		min-height: 100vh;
		padding-top: 70px !important;
		margin: 0;
		background:
			radial-gradient(900px 260px at 20% 0%, rgba(102, 187, 106, 0.22), rgba(102, 187, 106, 0) 60%),
			radial-gradient(760px 260px at 75% 10%, rgba(76, 175, 80, 0.16), rgba(76, 175, 80, 0) 60%),
			linear-gradient(180deg, #f7fbf6 0%, #ffffff 65%);
	}

	/* 去掉首页菜单横向滚动区域的“多余纵向滚动条” */
	::v-deep .scrollbar-wrapper-horizontal {
		overflow-x: auto !important;
		overflow-y: hidden !important;
		padding: 6px 0 !important; /* 让这一行更“高”一点 */
	}
	::v-deep .el-scrollbar__bar.is-vertical {
		display: none !important;
	}

	.top-container {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		z-index: 1002;
		height: 70px;
		display: flex;
		align-items: center;
		justify-content: flex-end;
		gap: 10px;
		padding: 0 24px;
		background: rgba(255, 255, 255, 0.86);
		backdrop-filter: saturate(1.15) blur(10px);
		border-bottom: 1px solid var(--ag-border);
		box-shadow: 0 6px 24px rgba(16, 24, 16, 0.08);
	}

	.brand {
		margin-right: auto;
		display: flex;
		align-items: center;
		min-width: 0;
	}

	.brand-title {
		font-weight: 800;
		letter-spacing: 0.5px;
		color: var(--ag-green-800);
		font-size: 24px;
		line-height: 1;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}

	.btn-auth {
		border: none !important;
		background: linear-gradient(135deg, var(--ag-green-400), var(--ag-green-700)) !important;
		color: #fff !important;
		border-radius: 999px !important;
		padding: 0 18px !important;
		font-size: 14px !important;
		height: 40px !important;
		line-height: 1 !important;
		display: inline-flex !important;
		align-items: center !important;
		justify-content: center !important;
		box-shadow: 0 10px 26px rgba(56, 142, 60, 0.20);
		transition: transform var(--ag-transition), box-shadow var(--ag-transition), filter var(--ag-transition);
	}
	.btn-auth:hover {
		filter: brightness(1.02);
		transform: translateY(-1px);
		box-shadow: 0 14px 34px rgba(56, 142, 60, 0.26);
	}

	.btn-icon {
		border: 1px solid rgba(46, 125, 50, 0.14) !important;
		background: rgba(255, 255, 255, 0.8) !important;
		color: var(--ag-text-700) !important;
		border-radius: 999px !important;
		height: 40px !important;
		width: 40px !important;
		padding: 0 !important;
		display: inline-flex !important;
		align-items: center !important;
		justify-content: center !important;
		transition: transform var(--ag-transition), background var(--ag-transition), box-shadow var(--ag-transition);
	}
	.btn-icon:hover {
		transform: translateY(-1px);
		background: rgba(102, 187, 106, 0.10) !important;
		box-shadow: 0 10px 24px rgba(56, 142, 60, 0.12);
	}

	.btn-hamburger {
		display: none !important;
	}

	.menu-preview {
		position: sticky;
		top: 70px;
		z-index: 1001;
		padding: 8px 24px;
		background: rgba(255, 255, 255, 0.72);
		backdrop-filter: saturate(1.1) blur(10px);
		border-bottom: 1px solid var(--ag-border);
	}

	/* 修复导航栏菜单项对齐 */
	::v-deep .el-menu-horizontal-demo .el-menu-item {
		display: flex !important;
		align-items: center !important;
		justify-content: center !important;
		height: 70px !important;
		line-height: 1 !important;
		padding: 0 12px !important;
	}

	::v-deep .el-menu-horizontal-demo .el-menu-item span,
	::v-deep .el-menu-horizontal-demo .el-menu-item i {
		display: inline-flex !important;
		align-items: center !important;
		justify-content: center !important;
		line-height: 1 !important;
		height: auto !important;
	}

	.banner-preview {
		padding: 14px 24px 0;
	}

	.carousel-img-error {
		display: flex;
		align-items: center;
		justify-content: center;
		width: 100%;
		height: 100%;
		min-height: 200px;
		color: #999;
		font-size: 14px;
		background: #f2f3f5;
	}

	.banner-preview ::v-deep .el-carousel {
		border-radius: 18px;
		overflow: hidden;
		box-shadow: var(--ag-shadow);
	}

	.banner-preview ::v-deep .el-carousel__container {
		height: clamp(260px, 42vw, 560px) !important;
	}

	.banner-preview ::v-deep .el-carousel__arrow {
		background: rgba(255, 255, 255, 0.86);
		color: var(--ag-text-700);
		transition: transform var(--ag-transition), background var(--ag-transition), color var(--ag-transition);
	}
	.banner-preview ::v-deep .el-carousel__arrow:hover {
		background: var(--ag-green-400);
		color: #fff;
		transform: translateY(-1px);
	}

	.banner-preview ::v-deep .el-carousel__indicators li button {
		display: none;
	}
	.banner-preview ::v-deep .el-carousel__indicators li {
		width: 10px;
		height: 10px;
		border-radius: 999px;
		background: rgba(255, 255, 255, 0.6);
		margin: 0 4px !important;
		transition: var(--ag-transition);
	}
	.banner-preview ::v-deep .el-carousel__indicators li.is-active {
		width: 26px;
		background: rgba(46, 125, 50, 0.92);
	}

	.hero-overlay {
		position: absolute;
		inset: 0;
		pointer-events: none;
		display: flex;
		align-items: flex-end;
		justify-content: flex-start;
		padding: 18px;
	}
	.hero-card {
		pointer-events: auto;
		max-width: 560px;
		border-radius: 18px;
		padding: 18px 18px 16px;
		background: linear-gradient(180deg, rgba(255, 255, 255, 0.82), rgba(255, 255, 255, 0.62));
		backdrop-filter: saturate(1.15) blur(12px);
		border: 1px solid rgba(46, 125, 50, 0.16);
		box-shadow: 0 18px 46px rgba(16, 24, 16, 0.14);
		position: relative;
	}
	.hero-close {
		position: absolute;
		top: 10px;
		right: 10px;
		width: 30px;
		height: 30px;
		border-radius: 999px;
		border: 1px solid rgba(46, 125, 50, 0.18);
		background: rgba(255, 255, 255, 0.78);
		cursor: pointer;
		display: inline-flex;
		align-items: center;
		justify-content: center;
		transition: transform var(--ag-transition), background var(--ag-transition), box-shadow var(--ag-transition);
	}
	.hero-close::before,
	.hero-close::after {
		content: "";
		position: absolute;
		width: 14px;
		height: 2px;
		background: rgba(27, 43, 27, 0.70);
		border-radius: 2px;
	}
	.hero-close::before { transform: rotate(45deg); }
	.hero-close::after { transform: rotate(-45deg); }
	.hero-close:hover {
		transform: translateY(-1px);
		background: rgba(102, 187, 106, 0.12);
		box-shadow: 0 10px 24px rgba(56, 142, 60, 0.14);
	}

	/* 关闭后的小入口：不遮挡，只占一小块 */
	.hero-mini {
		pointer-events: auto;
		border: 1px solid rgba(255, 255, 255, 0.40);
		background: rgba(16, 24, 16, 0.40);
		backdrop-filter: blur(10px) saturate(1.1);
		color: rgba(255, 255, 255, 0.92);
		border-radius: 999px;
		padding: 8px 12px;
		font-size: 12px;
		font-weight: 700;
		letter-spacing: 0.4px;
		cursor: pointer;
		box-shadow: 0 12px 30px rgba(0, 0, 0, 0.22);
		transition: transform var(--ag-transition), box-shadow var(--ag-transition), background var(--ag-transition);
	}
	.hero-mini:hover {
		transform: translateY(-1px);
		background: rgba(16, 24, 16, 0.52);
		box-shadow: 0 16px 40px rgba(0, 0, 0, 0.26);
	}
	.hero-kicker {
		display: inline-flex;
		align-items: center;
		gap: 8px;
		color: var(--ag-green-800);
		font-weight: 700;
		font-size: 13px;
		letter-spacing: 0.6px;
	}
	.hero-kicker::before {
		content: "";
		width: 10px;
		height: 10px;
		border-radius: 999px;
		background: radial-gradient(circle at 30% 30%, #a5d6a7, var(--ag-green-400));
		box-shadow: 0 0 0 6px rgba(102, 187, 106, 0.14);
	}
	.hero-title {
		margin-top: 10px;
		font-size: 26px;
		line-height: 1.25;
		color: var(--ag-text-900);
		font-weight: 900;
	}
	.hero-desc {
		margin-top: 10px;
		color: rgba(27, 43, 27, 0.78);
		font-size: 14px;
		line-height: 1.7;
	}
	.hero-actions {
		margin-top: 14px;
		display: flex;
		gap: 10px;
		flex-wrap: wrap;
	}
	.btn-hero-primary {
		border: none !important;
		background: linear-gradient(135deg, var(--ag-green-400), var(--ag-green-800)) !important;
		color: #fff !important;
		border-radius: 999px !important;
		padding: 12px 18px !important;
		box-shadow: 0 14px 34px rgba(56, 142, 60, 0.22);
		transition: transform var(--ag-transition), box-shadow var(--ag-transition), filter var(--ag-transition);
	}
	.btn-hero-primary:hover {
		transform: translateY(-2px);
		filter: brightness(1.02);
		box-shadow: 0 18px 44px rgba(56, 142, 60, 0.28);
	}
	.btn-hero-ghost {
		border-radius: 999px !important;
		padding: 12px 18px !important;
		border: 1px solid rgba(46, 125, 50, 0.22) !important;
		color: var(--ag-green-800) !important;
		background: rgba(255, 255, 255, 0.72) !important;
		transition: transform var(--ag-transition), background var(--ag-transition), border-color var(--ag-transition);
	}
	.btn-hero-ghost:hover {
		transform: translateY(-2px);
		background: rgba(102, 187, 106, 0.10) !important;
		border-color: rgba(46, 125, 50, 0.28) !important;
	}

	/* Home sections (only /index/home) */
	.home-sections {
		padding: 18px 24px 6px;
		width: 100%;
		max-width: 100%;
		box-sizing: border-box;
	}
	.section-hd {
		display: flex;
		align-items: flex-end;
		justify-content: space-between;
		gap: 14px;
		margin: 8px 0 12px;
		padding: 0 20px;
	}
	.section-hd__title {
		font-size: 16px;
		font-weight: 900;
		color: var(--ag-text-900);
		letter-spacing: 0.4px;
	}
	.section-hd__sub {
		font-size: 13px;
		color: rgba(47, 58, 47, 0.70);
	}

	.mission {
		display: grid;
		grid-template-columns: 1.2fr 0.9fr;
		gap: 14px;
		margin-top: 10px;
	}
	.mission__left,
	.mission__right {
		border-radius: 18px;
		border: 1px solid rgba(46, 125, 50, 0.12);
		background: linear-gradient(180deg, rgba(255, 255, 255, 0.86), rgba(255, 255, 255, 0.68));
		backdrop-filter: saturate(1.1) blur(10px);
		box-shadow: 0 16px 40px rgba(16, 24, 16, 0.10);
		padding: 16px 16px 14px;
	}
	.mission__tag {
		display: inline-flex;
		align-items: center;
		gap: 8px;
		padding: 6px 10px;
		border-radius: 999px;
		background: rgba(102, 187, 106, 0.14);
		border: 1px solid rgba(46, 125, 50, 0.14);
		color: var(--ag-green-800);
		font-weight: 800;
		font-size: 12px;
	}
	.mission__title {
		margin-top: 12px;
		font-size: 20px;
		line-height: 1.35;
		font-weight: 950;
		color: var(--ag-text-900);
	}
	.mission__desc {
		margin-top: 10px;
		color: rgba(27, 43, 27, 0.78);
		line-height: 1.8;
		font-size: 13px;
	}
	.mission__chips {
		margin-top: 12px;
		display: flex;
		gap: 8px;
		flex-wrap: wrap;
	}
	.chip {
		display: inline-flex;
		align-items: center;
		padding: 6px 10px;
		border-radius: 999px;
		background: rgba(241, 248, 233, 0.92);
		border: 1px solid rgba(46, 125, 50, 0.12);
		color: rgba(27, 43, 27, 0.80);
		font-size: 12px;
		font-weight: 700;
	}

	.stats {
		display: grid;
		grid-template-columns: 1fr 1fr;
		gap: 10px;
	}
	.stat {
		border-radius: 14px;
		background: rgba(241, 248, 233, 0.70);
		border: 1px solid rgba(46, 125, 50, 0.10);
		padding: 12px 12px 10px;
	}
	.stat__num {
		font-weight: 950;
		color: var(--ag-green-800);
		font-size: 18px;
		letter-spacing: 0.2px;
	}
	.stat__label {
		margin-top: 6px;
		font-size: 12px;
		color: rgba(47, 58, 47, 0.68);
	}

	.trust {
		margin-top: 10px;
		border-radius: 14px;
		background: rgba(255, 255, 255, 0.70);
		border: 1px solid rgba(46, 125, 50, 0.10);
		padding: 12px 12px 10px;
	}
	.trust__title {
		font-weight: 900;
		color: var(--ag-text-900);
		font-size: 13px;
	}
	.trust__list {
		margin-top: 10px;
		display: grid;
		gap: 8px;
	}
	.trust__item {
		display: flex;
		align-items: flex-start;
		gap: 10px;
	}
	.trust__dot {
		width: 10px;
		height: 10px;
		border-radius: 999px;
		margin-top: 4px;
		background: radial-gradient(circle at 30% 30%, #a5d6a7, var(--ag-green-400));
		box-shadow: 0 0 0 6px rgba(102, 187, 106, 0.12);
	}
	.trust__text {
		color: rgba(27, 43, 27, 0.76);
		font-size: 12px;
		line-height: 1.6;
	}

	.quick-entries {
		margin-top: 14px;
		width: 100%;
		padding: 0 20px;
		box-sizing: border-box;
	}
	.entry-grid {
		display: grid;
		grid-template-columns: repeat(3, 1fr);
		gap: 20px;
		width: 100%;
	}
	.entry-card {
		position: relative;
		border-radius: 18px;
		border: 1px solid rgba(46, 125, 50, 0.12);
		background: linear-gradient(180deg, rgba(255, 255, 255, 0.92), rgba(255, 255, 255, 0.72));
		box-shadow: 0 14px 34px rgba(16, 24, 16, 0.10);
		padding: 20px;
		cursor: pointer;
		min-height: 140px;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
		transition: transform var(--ag-transition), box-shadow var(--ag-transition), border-color var(--ag-transition);
		overflow: hidden;
	}
	.entry-card::after {
		content: "";
		position: absolute;
		inset: -60px -40px auto auto;
		width: 180px;
		height: 180px;
		border-radius: 999px;
		background: radial-gradient(circle at 30% 30%, rgba(165, 214, 167, 0.55), rgba(102, 187, 106, 0));
		transform: rotate(12deg);
	}
	.entry-card:hover {
		transform: translateY(-8px);
		box-shadow: 0 22px 54px rgba(16, 24, 16, 0.16);
		border-color: rgba(46, 125, 50, 0.20);
	}
	.entry-card__icon {
		width: 44px;
		height: 44px;
		border-radius: 14px;
		display: inline-flex;
		align-items: center;
		justify-content: center;
		color: #fff;
		position: relative;
		z-index: 1;
		box-shadow: 0 14px 30px rgba(56, 142, 60, 0.18);
	}
	.entry-card__icon--goods { background: linear-gradient(135deg, #66bb6a, #2e7d32); }
	.entry-card__icon--project { background: linear-gradient(135deg, #8bc34a, #2e7d32); }
	.entry-card__icon--learn { background: linear-gradient(135deg, #4caf50, #1b5e20); }
	.entry-card__icon--support { background: linear-gradient(135deg, #a5d6a7, #388e3c); }
	.entry-card__icon--policy { background: linear-gradient(135deg, #c8e6c9, #4caf50); }
	.entry-card__icon--enterprise { background: linear-gradient(135deg, #81c784, #2e7d32); }
	.entry-card__head {
		display: flex;
		align-items: center;
		gap: 12px;
		position: relative;
		z-index: 1;
	}
	.entry-card__title {
		margin-top: 0;
		font-weight: 950;
		color: var(--ag-text-900);
		font-size: 18px;
		position: relative;
		z-index: 1;
	}
	.entry-card__desc {
		margin-top: 10px;
		color: rgba(27, 43, 27, 0.74);
		line-height: 1.7;
		font-size: 13px;
		position: relative;
		z-index: 1;
		flex: 1;
	}
	.entry-card__cta {
		margin-top: 12px;
		color: var(--ag-green-800);
		font-weight: 800;
		font-size: 13px;
		position: relative;
		z-index: 1;
	}

	@media (max-width: 1200px) {
		.mission { grid-template-columns: 1fr; }
	}
	@media (max-width: 992px) {
		.entry-grid { grid-template-columns: repeat(2, 1fr); }
	}
	@media (max-width: 576px) {
		.entry-grid { grid-template-columns: 1fr; }
	}
	@media (max-width: 768px) {
		.home-sections { padding: 14px 12px 2px; }
		.section-hd { flex-direction: column; align-items: flex-start; }
	}

	/* mobile drawer */
	::v-deep .mobile-drawer {
		background: linear-gradient(180deg, rgba(247, 251, 246, 0.98), rgba(255, 255, 255, 0.96));
	}
	.mobile-drawer__header {
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 16px 14px 10px;
		border-bottom: 1px solid var(--ag-border);
	}
	.mobile-drawer__title {
		font-weight: 900;
		color: var(--ag-green-800);
		font-size: 16px;
	}
	.mobile-drawer__sub {
		margin-top: 2px;
		font-size: 12px;
		color: rgba(47, 58, 47, 0.7);
	}
	.mobile-drawer__menu {
		padding: 10px 8px 14px;
	}
	.mobile-drawer__menu ::v-deep .el-menu {
		border-right: 0 !important;
		background: transparent !important;
	}
	.mobile-drawer__menu ::v-deep .el-menu-item {
		border-radius: 12px;
		margin: 4px 6px;
		height: 46px;
		line-height: 46px;
	}
	.mobile-drawer__menu ::v-deep .el-menu-item.is-active {
		background: rgba(102, 187, 106, 0.14) !important;
		color: var(--ag-green-800) !important;
		font-weight: 800;
	}

	/* 通用卡片 hover 上浮 */
	::v-deep .el-card,
	::v-deep .card,
	::v-deep .list-card,
	::v-deep .detail-card {
		border-radius: var(--ag-radius) !important;
		border: 1px solid rgba(46, 125, 50, 0.12) !important;
		box-shadow: 0 10px 28px rgba(16, 24, 16, 0.08);
		transition: transform var(--ag-transition), box-shadow var(--ag-transition), border-color var(--ag-transition);
	}
	::v-deep .el-card:hover,
	::v-deep .card:hover,
	::v-deep .list-card:hover,
	::v-deep .detail-card:hover {
		transform: translateY(-6px);
		box-shadow: var(--ag-shadow-hover);
		border-color: rgba(46, 125, 50, 0.20) !important;
	}

	/* 细腻的“高光描边”质感（hover 时更像新卡片） */
	::v-deep .el-card::before,
	::v-deep .card::before,
	::v-deep .list-card::before,
	::v-deep .detail-card::before {
		content: "";
		position: absolute;
		inset: 0;
		border-radius: inherit;
		pointer-events: none;
		opacity: 0;
		transition: opacity var(--ag-transition);
		box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.7);
	}
	::v-deep .el-card:hover::before,
	::v-deep .card:hover::before,
	::v-deep .list-card:hover::before,
	::v-deep .detail-card:hover::before {
		opacity: 1;
	}

	/* Element UI 主题轻统一（首页范围） */
	::v-deep .el-button--primary {
		border-color: var(--ag-green-700) !important;
		background: var(--ag-green-700) !important;
	}
	::v-deep .el-button--primary:hover {
		border-color: var(--ag-green-800) !important;
		background: var(--ag-green-800) !important;
	}
	::v-deep .el-input__inner:focus {
		border-color: rgba(46, 125, 50, 0.42) !important;
		box-shadow: 0 0 0 4px rgba(102, 187, 106, 0.18);
	}

	@media (max-width: 768px) {
		.brand-title { font-size: 16px; }
		.btn-auth { padding: 10px 14px !important; }
		.btn-hamburger { display: inline-flex !important; }
		.menu-preview { display: none; }
		.banner-preview { padding: 10px 12px 0; }
		.hero-overlay { padding: 12px; }
		.hero-title { font-size: 20px; }
	}
	@media (max-width: 480px) {
		.hero-card { padding: 14px 14px 12px; }
		.hero-desc { display: none; }
	}

	/* Footer */
	.bottom-preview {
		padding: 22px 24px 26px;
	}
	.footer {
		border-radius: 18px;
		border: 1px solid rgba(46, 125, 50, 0.12);
		background: linear-gradient(180deg, rgba(241, 248, 233, 0.92), rgba(255, 255, 255, 0.92));
		box-shadow: 0 16px 40px rgba(16, 24, 16, 0.10);
		padding: 18px 18px 14px;
	}
	.footer-grid {
		display: grid;
		grid-template-columns: 1.2fr 1fr 1fr;
		gap: 14px;
	}
	.footer-title {
		font-size: 14px;
		font-weight: 900;
		color: var(--ag-green-800);
		letter-spacing: 0.4px;
	}
	.footer-text {
		margin-top: 10px;
		color: rgba(27, 43, 27, 0.74);
		line-height: 1.8;
		font-size: 13px;
	}
	.footer-links {
		margin-top: 10px;
		display: flex;
		flex-direction: column;
		gap: 8px;
	}
	.footer-links a {
		color: rgba(27, 43, 27, 0.78);
		text-decoration: none;
		cursor: pointer;
		transition: color var(--ag-transition), transform var(--ag-transition);
	}
	.footer-links a:hover {
		color: var(--ag-green-800);
		transform: translateX(2px);
	}
	.footer-badges {
		margin-top: 10px;
		display: flex;
		flex-wrap: wrap;
		gap: 8px;
	}
	.badge {
		display: inline-flex;
		align-items: center;
		padding: 6px 10px;
		border-radius: 999px;
		background: rgba(102, 187, 106, 0.14);
		border: 1px solid rgba(46, 125, 50, 0.14);
		color: var(--ag-green-800);
		font-size: 12px;
		font-weight: 700;
	}
	.footer-rich {
		margin-top: 14px;
	}
	.footer-rich__card {
		border-radius: 14px;
		background: rgba(255, 255, 255, 0.76);
		border: 1px solid rgba(46, 125, 50, 0.10);
		padding: 12px 12px;
		color: rgba(27, 43, 27, 0.78);
		overflow: hidden;
	}
	.footer-bottom {
		margin-top: 12px;
		padding-top: 12px;
		border-top: 1px dashed rgba(46, 125, 50, 0.18);
		color: rgba(47, 58, 47, 0.60);
		font-size: 12px;
		text-align: center;
	}

	@media (max-width: 992px) {
		.footer-grid { grid-template-columns: 1fr 1fr; }
	}
	@media (max-width: 640px) {
		.bottom-preview { padding: 16px 12px 22px; }
		.footer { padding: 16px 14px 12px; }
		.footer-grid { grid-template-columns: 1fr; }
	}

	/* Reduced motion */
	@media (prefers-reduced-motion: reduce) {
		* { transition: none !important; }
	}
</style>