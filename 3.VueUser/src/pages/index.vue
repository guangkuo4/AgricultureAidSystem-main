<template>
	<div class="main-containers">
		<div class="body-containers">
		<div class="site-header-shell">
		<div class="top-container">
			<!-- 品牌标识区域 -->
			<div class="brand" @click="goMenu('/index/home')">
				<div class="brand-logo">
					<svg class="brand-logo__icon" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
						<circle cx="24" cy="24" r="22" fill="url(#logoGradient)" opacity="0.15"/>
						<path d="M24 8C24 8 12 16 12 26C12 31.5228 16.4772 36 22 36H26C31.5228 36 36 31.5228 36 26C36 16 24 8 24 8Z" fill="url(#logoGradient)"/>
						<path d="M24 14C24 14 18 19 18 25C18 28.3137 20.6863 31 24 31V14Z" fill="white" opacity="0.6"/>
						<circle cx="24" cy="24" r="4" fill="white"/>
						<defs>
							<linearGradient id="logoGradient" x1="12" y1="8" x2="36" y2="36" gradientUnits="userSpaceOnUse">
								<stop stop-color="#66bb6a"/>
								<stop offset="1" stop-color="#2e7d32"/>
							</linearGradient>
						</defs>
					</svg>
				</div>
				<div class="brand-text">
					<span class="brand-title">助农扶贫服务平台</span>
					<span class="brand-subtitle">Agricultural Aid Platform</span>
				</div>
			</div>

			<!-- 购物车按钮 -->
			<el-button v-if="Token" class="btn-shop" @click.native="goMenu('/index/cart')">
				<span class="icon iconfont icon-wuliu8"></span>
				<span class="btn-shop__text">购物车</span>
			</el-button>

			<!-- 消息通知 -->
			<MessageNotification v-if="Token" />


			<!-- 用户信息 -->
			<template v-if="Token">
				<div class="user-info" @click="goMenu('/index/center')">
					<div class="user-avatar">
						<img v-if="headportrait" :src="getAvatarUrl(headportrait)" alt="头像">
						<img v-else src="@/assets/avator.png" alt="默认头像">
					</div>
					<span class="user-name">{{username}}</span>
				</div>
			</template>

			<!-- 认证按钮 -->
			<template v-if="!Token">
				<el-button class="btn-auth btn-login" @click="toLogin()">
					<i class="el-icon-user"></i>
					登录
				</el-button>
				<el-button class="btn-auth btn-register" @click="toRegister()">
					<i class="el-icon-edit-outline"></i>
					注册
				</el-button>
			</template>
			<el-button v-if="Token" class="btn-auth btn-logout" @click="logout">
				<i class="el-icon-switch-button"></i>
				退出
			</el-button>

			<!-- 移动端菜单按钮 -->
			<el-button class="btn-icon btn-hamburger" @click="mobileNavOpen = true" icon="el-icon-menu"></el-button>
		</div>

		<!-- 主导航菜单（与顶栏同一固定区域，避免中间空白带） -->
		<div class="menu-preview">
			<el-scrollbar wrap-class="scrollbar-wrapper-horizontal">
				<el-menu class="el-menu-horizontal-demo" :style='{"border":0,"padding":"0","listStyle":"none","margin":"0","background":"none","display":"flex","position":"relative"}' :default-active="activeMenu" :unique-opened="true" mode="horizontal" :router="true" @select="handleSelect">
					<el-menu-item class="home" index="/index/home" @click.native="goMenu('/index/home')">
						<span class="nav-icon icon iconfont icon-shouye-zhihui"></span>
						<span class="nav-text">系统首页</span>
					</el-menu-item>
					<el-menu-item class="item" v-for="(menu, index) in menuList" :index="menu.url" :key="index" @click.native="goMenu(menu.url)">
						<span class="nav-icon" :class="iconArr[index]"></span>
						<span class="nav-text">{{menu.name}}</span>
					</el-menu-item>
					<el-menu-item class="shop" index="/index/cart" @click.native="goMenu('/index/cart')">
						<span class="nav-icon icon iconfont icon-wuliu8"></span>
						<span class="nav-text">购物车</span>
					</el-menu-item>
					<el-menu-item class="user" index="/index/center" v-if="Token && notAdmin" @click.native="goMenu('/index/center')">
						<span class="nav-icon icon iconfont icon-shouye-zhihui"></span>
						<span class="nav-text">个人中心</span>
					</el-menu-item>
				</el-menu>
			</el-scrollbar>
		</div>
		</div>

		<!-- 移动端抽屉导航 -->
		<el-drawer
			:visible.sync="mobileNavOpen"
			direction="ltr"
			size="80%"
			custom-class="mobile-drawer"
			:with-header="false"
		>
			<div class="mobile-drawer__header">
				<div class="mobile-drawer__brand">
					<svg class="brand-logo__icon" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
						<circle cx="24" cy="24" r="22" fill="url(#logoGradient2)" opacity="0.15"/>
						<path d="M24 8C24 8 12 16 12 26C12 31.5228 16.4772 36 22 36H26C31.5228 36 36 31.5228 36 26C36 16 24 8 24 8Z" fill="url(#logoGradient2)"/>
						<path d="M24 14C24 14 18 19 18 25C18 28.3137 20.6863 31 24 31V14Z" fill="white" opacity="0.6"/>
						<circle cx="24" cy="24" r="4" fill="white"/>
						<defs>
							<linearGradient id="logoGradient2" x1="12" y1="8" x2="36" y2="36" gradientUnits="userSpaceOnUse">
								<stop stop-color="#66bb6a"/>
								<stop offset="1" stop-color="#2e7d32"/>
							</linearGradient>
						</defs>
					</svg>
					<div class="mobile-drawer__title">助农扶贫服务平台</div>
					<div class="mobile-drawer__sub">绿色 · 科技 · 助农</div>
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

		<!-- 首页轮播横幅 -->
		<div v-if="$route.path === '/index/home'" class="banner-preview">
			<el-carousel :style='{"width":"100%","margin":"0 auto"}' trigger="click" indicator-position="inside" arrow="always" type="default" direction="vertical" height="580px" :autoplay="true" :interval="4000" :loop="true">
				<el-carousel-item :style='{"borderRadius":"20px","width":"100%","height":"100%"}' v-for="item in carouselList" :key="item.id">
					<div class="carousel-item-wrapper" @click="carouselClick(item.url)">
						<img v-if="carouselImgSrc(item)" :src="carouselImgSrc(item)" alt="轮播图" class="carousel-image">
						<div v-else class="carousel-placeholder">
							<i class="el-icon-picture-outline"></i>
							<span>图片加载中...</span>
						</div>
						<div class="carousel-overlay">
							<div class="carousel-content">
								<div class="carousel-tag">
									<i class="el-icon-s-marketing"></i>
									助农平台
								</div>
								<div class="carousel-title">让每一份农产品都能被看见</div>
								<div class="carousel-desc">连接乡村与城市，传递绿色与希望</div>
							</div>
						</div>
					</div>
				</el-carousel-item>
			</el-carousel>

			<!-- Hero 引导卡片 -->
			<div class="hero-overlay">
				<div v-if="showHeroIntro" class="hero-card">
					<button class="hero-close" type="button" aria-label="关闭介绍" @click="dismissHeroIntro"></button>
					<div class="hero-kicker">
						<span class="hero-kicker__dot"></span>
						农业绿色 · 清新简约
					</div>
					<div class="hero-title">让优质农产品更快触达需要的人</div>
					<div class="hero-desc">助农商品、助农乐学、资讯公告一站式服务，体验更轻盈，信息更清晰。</div>
					<div class="hero-actions">
						<el-button class="btn-hero-primary" @click="goMenu('/index/home')">
							<i class="el-icon-house"></i>
							立即进入
						</el-button>
						<el-button class="btn-hero-ghost" plain @click="Token && notAdmin ? goMenu('/index/center') : toLogin()">
							<i class="el-icon-user"></i>
							{{ Token ? '个人中心' : '登录/注册' }}
						</el-button>
					</div>
					<div class="hero-stats">
						<div class="hero-stat">
							<span class="hero-stat__num">{{ carouselList.length }}+</span>
							<span class="hero-stat__label">精选内容</span>
						</div>
						<div class="hero-stat">
							<span class="hero-stat__num">6</span>
							<span class="hero-stat__label">核心模块</span>
						</div>
						<div class="hero-stat">
							<span class="hero-stat__num">24h</span>
							<span class="hero-stat__label">在线服务</span>
						</div>
					</div>
				</div>
				<button v-else class="hero-mini" type="button" @click="reopenHeroIntro">
					<i class="el-icon-info"></i>
					平台介绍
				</button>
			</div>
		</div>

		<!-- 首页核心内容区域 -->
		<section v-if="isHome" class="home-sections">
			<!-- 使命宣言 -->
			<div class="mission">
				<div class="mission__left">
					<div class="mission__left-inner">
						<div class="mission__intro">
							<div class="mission__tag">
								<svg class="mission__tag-icon" viewBox="0 0 24 24" fill="none">
									<path d="M12 2L15.09 8.26L22 9.27L17 14.14L18.18 21.02L12 17.77L5.82 21.02L7 14.14L2 9.27L8.91 8.26L12 2Z" fill="currentColor"/>
								</svg>
								助农扶贫 · 主题表达
							</div>
							<h2 class="mission__title">连接农户与用户，让帮扶看得见、可追溯、可持续</h2>
							<p class="mission__desc">
								以<span class="mission__hl">助农商品</span><span class="mission__sep"> + </span><span class="mission__hl">扶贫项目</span><span class="mission__sep"> + </span><span class="mission__hl">助农乐学</span>为核心，打造信息透明、体验清爽的服务平台，让公益与效率并行。
							</p>
							<ul class="mission__points">
								<li><i class="el-icon-circle-check"></i><span>统一展示与检索，减少信息碎片化与重复</span></li>
								<li><i class="el-icon-circle-check"></i><span>关键流程可回溯，增强协作与监督的可预期性</span></li>
								<li><i class="el-icon-circle-check"></i><span>清爽界面与一致交互，降低农户与访客使用门槛</span></li>
							</ul>
						</div>
						<div class="mission__pillars" aria-label="平台体验原则">
							<div class="mission__pillar">
								<div class="mission__pillar-icon mission__pillar-icon--goods">
									<i class="el-icon-view"></i>
								</div>
								<div class="mission__pillar-text">
									<div class="mission__pillar-name">全程可视</div>
									<p class="mission__pillar-note">展示、对接与跟进留痕可查，帮扶路径更清晰可核对</p>
								</div>
							</div>
							<div class="mission__pillar">
								<div class="mission__pillar-icon mission__pillar-icon--proj">
									<i class="el-icon-s-grid"></i>
								</div>
								<div class="mission__pillar-text">
									<div class="mission__pillar-name">一站汇聚</div>
									<p class="mission__pillar-note">资讯、服务与常用能力集中呈现，少跳转、少迷路</p>
								</div>
							</div>
							<div class="mission__pillar">
								<div class="mission__pillar-icon mission__pillar-icon--learn">
									<i class="el-icon-guide"></i>
								</div>
								<div class="mission__pillar-text">
									<div class="mission__pillar-name">轻量上手</div>
									<p class="mission__pillar-note">统一版式与提示语，首次使用也能按步骤完成操作</p>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="mission__right">
					<div class="stats">
						<div class="stat">
							<div class="stat__icon">
								<i class="el-icon-data-line"></i>
							</div>
							<div class="stat__content">
								<div class="stat__num">{{ carouselList.length }}</div>
								<div class="stat__label">轮播展示</div>
							</div>
						</div>
						<div class="stat">
							<div class="stat__icon stat__icon--blue">
								<i class="el-icon-user"></i>
							</div>
							<div class="stat__content">
								<div class="stat__num">{{ Token ? '已登录' : '访客' }}</div>
								<div class="stat__label">当前状态</div>
							</div>
						</div>
					</div>
					<div class="trust">
						<div class="trust__title">
							<i class="el-icon-shield"></i>
							公益可信背书
						</div>
						<div class="trust__list">
							<div class="trust__item">
								<span class="trust__dot"></span>
								<span class="trust__text">信息发布与管理流程可控，减少误导与冗余</span>
							</div>
							<div class="trust__item">
								<span class="trust__dot"></span>
								<span class="trust__text">关键入口清晰直达，降低使用门槛</span>
							</div>
							<div class="trust__item">
								<span class="trust__dot"></span>
								<span class="trust__text">页面统一配色与交互动效，提升专业感</span>
							</div>
						</div>
					</div>
				</div>
			</div>

			<!-- 助农数据统计条 -->
			<div class="stats-bar">
				<div class="stats-bar__inner">
					<div class="stats-bar__item">
						<div class="stats-bar__icon">
							<i class="el-icon-s-goods"></i>
						</div>
						<div class="stats-bar__content">
							<div class="stats-bar__num" ref="countGoods">0</div>
							<div class="stats-bar__label">精选商品</div>
						</div>
					</div>
					<div class="stats-bar__divider"></div>
					<div class="stats-bar__item">
						<div class="stats-bar__icon stats-bar__icon--proj">
							<i class="el-icon-s-flag"></i>
						</div>
						<div class="stats-bar__content">
							<div class="stats-bar__num" ref="countProjects">0</div>
							<div class="stats-bar__label">帮扶项目</div>
						</div>
					</div>
					<div class="stats-bar__divider"></div>
					<div class="stats-bar__item">
						<div class="stats-bar__icon stats-bar__icon--learn">
							<i class="el-icon-notebook-2"></i>
						</div>
						<div class="stats-bar__content">
							<div class="stats-bar__num" ref="countCourses">0</div>
							<div class="stats-bar__label">农技课程</div>
						</div>
					</div>
					<div class="stats-bar__divider"></div>
					<div class="stats-bar__item">
						<div class="stats-bar__icon stats-bar__icon--news">
							<i class="el-icon-document-copy"></i>
						</div>
						<div class="stats-bar__content">
							<div class="stats-bar__num" ref="countNews">0</div>
							<div class="stats-bar__label">资讯文章</div>
						</div>
					</div>
				</div>
			</div>

			<!-- 核心模块入口 -->
			<div class="quick-entries">
				<div class="section-hd">
					<div class="section-hd__text">
						<div class="section-hd__title">核心模块</div>
						<div class="section-hd__sub">围绕"卖得出、学得到、帮得上"设计的六大入口</div>
					</div>
				</div>
				<div class="entry-grid">
					<div class="entry-card" @click="goMenu('/index/zhunongshangpin')">
						<div class="entry-card__decoration"></div>
						<div class="entry-card__head">
							<div class="entry-card__icon entry-card__icon--goods">
								<i class="el-icon-shopping-bag-1"></i>
							</div>
							<div class="entry-card__title">助农商品</div>
						</div>
						<div class="entry-card__desc">优质农产品直达需求方，交易链路更清晰</div>
						<div class="entry-card__footer">
							<span class="entry-card__cta">进入模块</span>
							<i class="el-icon-arrow-right"></i>
						</div>
					</div>
					<div class="entry-card" @click="goMenu('/index/zhunongtuopin')">
						<div class="entry-card__decoration entry-card__decoration--orange"></div>
						<div class="entry-card__head">
							<div class="entry-card__icon entry-card__icon--project">
								<i class="el-icon-collection-tag"></i>
							</div>
							<div class="entry-card__title">助农案例</div>
						</div>
						<div class="entry-card__desc">助农项目与成功案例，让帮扶更可见更可学</div>
						<div class="entry-card__footer">
							<span class="entry-card__cta">进入模块</span>
							<i class="el-icon-arrow-right"></i>
						</div>
					</div>
					<div class="entry-card" @click="goMenu('/index/nongjixuetang')">
						<div class="entry-card__decoration entry-card__decoration--teal"></div>
						<div class="entry-card__head">
							<div class="entry-card__icon entry-card__icon--learn">
								<i class="el-icon-reading"></i>
							</div>
							<div class="entry-card__title">助农乐学</div>
						</div>
						<div class="entry-card__desc">标准化农技知识与课程，提升生产效率</div>
						<div class="entry-card__footer">
							<span class="entry-card__cta">进入模块</span>
							<i class="el-icon-arrow-right"></i>
						</div>
					</div>
					<div class="entry-card" @click="goMenu('/index/bangfuxiangmu')">
						<div class="entry-card__decoration entry-card__decoration--purple"></div>
						<div class="entry-card__head">
							<div class="entry-card__icon entry-card__icon--support">
								<i class="el-icon-link"></i>
							</div>
							<div class="entry-card__title">帮扶对接</div>
						</div>
						<div class="entry-card__desc">精准匹配帮扶资源，搭建供需对接桥梁</div>
						<div class="entry-card__footer">
							<span class="entry-card__cta">进入模块</span>
							<i class="el-icon-arrow-right"></i>
						</div>
					</div>
					<div class="entry-card" @click="goMenu('/index/news')">
						<div class="entry-card__decoration entry-card__decoration--red"></div>
						<div class="entry-card__head">
							<div class="entry-card__icon entry-card__icon--policy">
								<i class="el-icon-document"></i>
							</div>
							<div class="entry-card__title">公告资讯</div>
						</div>
						<div class="entry-card__desc">及时了解农业政策，获取专业咨询服务</div>
						<div class="entry-card__footer">
							<span class="entry-card__cta">进入模块</span>
							<i class="el-icon-arrow-right"></i>
						</div>
					</div>
					<div class="entry-card" @click="goMenu('/index/qiyeruzhuApply')">
						<div class="entry-card__decoration entry-card__decoration--gold"></div>
						<div class="entry-card__head">
							<div class="entry-card__icon entry-card__icon--enterprise">
								<i class="el-icon-office-building"></i>
							</div>
							<div class="entry-card__title">农商入驻</div>
						</div>
						<div class="entry-card__desc">为农商企业提供便捷的入驻通道，拓展销售渠道</div>
						<div class="entry-card__footer">
							<span class="entry-card__cta">入驻申请</span>
							<i class="el-icon-arrow-right"></i>
						</div>
					</div>
				</div>
			</div>
		</section>

		<!-- 路由页面内容 -->
		<router-view id="scrollView"></router-view>

		<!-- 时令精选 + 信任条 + 页脚：整体贴底，避免与导航栏之间被 flex 撑出大块空白 -->
		<div class="index-page-tail">
		<!-- 时令精选推荐 -->
		<div class="featured-section" v-if="featuredList.length">
			<div class="featured-section__hd">
				<div class="featured-section__label">
					<i class="el-icon-sunrise-1"></i>
					时令精选
				</div>
				<div class="featured-section__meta">当季热卖 · 产地直发</div>
			</div>
			<div class="featured-grid">
				<div
					class="featured-card"
					v-for="item in featuredList"
					:key="item.id"
					@click="goMenu('/index/zhunongshangpin')"
				>
					<div class="featured-card__img">
						<img v-if="item.tupian" :src="baseUrl + item.tupian" :alt="item.mingcheng">
						<div v-else class="featured-card__placeholder">
							<i class="el-icon-picture-outline"></i>
						</div>
						<div class="featured-card__tag">
							<i class="el-icon-s-marketing"></i>
							精选
						</div>
					</div>
					<div class="featured-card__body">
						<div class="featured-card__name">{{ item.mingcheng || item.title || '助农好物' }}</div>
						<div class="featured-card__desc">{{ item.jianjie || item.content || '源自优质产地，绿色健康直达' }}</div>
						<div class="featured-card__footer">
							<span class="featured-card__price" v-if="item.price">¥{{ item.price }}</span>
							<span class="featured-card__btn">
								去看看 <i class="el-icon-arrow-right"></i>
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- 平台优势信任条 -->
		<div class="trust-bar">
			<div class="trust-bar__inner">
				<div class="trust-bar__item">
					<div class="trust-bar__icon">
						<i class="el-icon-circle-check"></i>
					</div>
					<div class="trust-bar__text">
						<div class="trust-bar__title">真实信息</div>
						<div class="trust-bar__sub">所有内容经审核发布</div>
					</div>
				</div>
				<div class="trust-bar__item">
					<div class="trust-bar__icon trust-bar__icon--2">
						<i class="el-icon-s-order"></i>
					</div>
					<div class="trust-bar__text">
						<div class="trust-bar__title">流程透明</div>
						<div class="trust-bar__sub">帮扶进度实时可查</div>
					</div>
				</div>
				<div class="trust-bar__item">
					<div class="trust-bar__icon trust-bar__icon--3">
						<i class="el-icon-shield"></i>
					</div>
					<div class="trust-bar__text">
						<div class="trust-bar__title">信息安全</div>
						<div class="trust-bar__sub">数据加密传输存储</div>
					</div>
				</div>
				<div class="trust-bar__item">
					<div class="trust-bar__icon trust-bar__icon--4">
						<i class="el-icon-service"></i>
					</div>
					<div class="trust-bar__text">
						<div class="trust-bar__title">全程服务</div>
						<div class="trust-bar__sub">7×24 小时在线支持</div>
					</div>
				</div>
			</div>
		</div>

		<!-- 底部区域 -->
		<div class="bottom-preview">
			<div class="footer">
				<!-- 装饰元素 -->
				<div class="footer-decoration">
					<div class="footer-decoration__circle footer-decoration__circle--1"></div>
					<div class="footer-decoration__circle footer-decoration__circle--2"></div>
					<div class="footer-decoration__circle footer-decoration__circle--3"></div>
					<div class="footer-decoration__dot footer-decoration__dot--1"></div>
					<div class="footer-decoration__dot footer-decoration__dot--2"></div>
					<div class="footer-decoration__dot footer-decoration__dot--3"></div>
					<div class="footer-decoration__dot footer-decoration__dot--4"></div>
					<div class="footer-decoration__dot footer-decoration__dot--5"></div>
				</div>

				<div class="footer-grid">
					<div class="footer-col footer-col--brand">
						<div class="footer-brand">
							<svg class="footer-brand__logo" viewBox="0 0 48 48" fill="none">
								<circle cx="24" cy="24" r="22" fill="url(#footerLogoGradient)" opacity="0.2"/>
								<path d="M24 8C24 8 12 16 12 26C12 31.5228 16.4772 36 22 36H26C31.5228 36 36 31.5228 36 26C36 16 24 8 24 8Z" fill="url(#footerLogoGradient)"/>
								<path d="M24 14C24 14 18 19 18 25C18 28.3137 20.6863 31 24 31V14Z" fill="white" opacity="0.6"/>
								<circle cx="24" cy="24" r="4" fill="white"/>
								<defs>
									<linearGradient id="footerLogoGradient" x1="12" y1="8" x2="36" y2="36" gradientUnits="userSpaceOnUse">
										<stop stop-color="#66bb6a"/>
										<stop offset="1" stop-color="#2e7d32"/>
									</linearGradient>
								</defs>
							</svg>
							<div class="footer-brand__text">助农扶贫服务平台</div>
						</div>
						<div class="footer-text">
							以低饱和自然绿为主的清新体验，帮助你更高效地获取农产品与农技信息。
						</div>
						<div class="footer-social">
							<div class="footer-social__item" title="微信公众号">
								<i class="el-icon-chat-line-round"></i>
							</div>
							<div class="footer-social__item" title="微博">
								<i class="el-icon-share"></i>
							</div>
							<div class="footer-social__item" title="联系我们">
								<i class="el-icon-phone-outline"></i>
							</div>
						</div>
					</div>
					<div class="footer-col">
						<div class="footer-title">
							<i class="el-icon-link"></i>
							快捷入口
						</div>
						<div class="footer-links">
							<a @click.prevent="goMenu('/index/home')" href="#">
								<i class="el-icon-d-arrow-right"></i>
								系统首页
							</a>
							<a @click.prevent="goMenu('/index/zhunongshangpin')" href="#">
								<i class="el-icon-d-arrow-right"></i>
								助农商品
							</a>
							<a @click.prevent="goMenu('/index/zhunongtuopin')" href="#">
								<i class="el-icon-d-arrow-right"></i>
								助农案例
							</a>
							<a @click.prevent="goMenu('/index/nongjixuetang')" href="#">
								<i class="el-icon-d-arrow-right"></i>
								助农乐学
							</a>
						</div>
					</div>
					<div class="footer-col">
						<div class="footer-title">
							<i class="el-icon-s-management"></i>
							更多服务
						</div>
						<div class="footer-links">
							<a @click.prevent="goMenu('/index/bangfuxiangmu')" href="#">
								<i class="el-icon-d-arrow-right"></i>
								帮扶对接
							</a>
							<a @click.prevent="goMenu('/index/news')" href="#">
								<i class="el-icon-d-arrow-right"></i>
								公告资讯
							</a>
							<a @click.prevent="goMenu('/index/qiyeruzhuApply')" href="#">
								<i class="el-icon-d-arrow-right"></i>
								农商入驻
							</a>
							<a v-if="Token" @click.prevent="goMenu('/index/cart')" href="#">
								<i class="el-icon-d-arrow-right"></i>
								购物车
							</a>
						</div>
					</div>
					<div class="footer-col">
						<div class="footer-title">
							<i class="el-icon-medal"></i>
							服务承诺
						</div>
						<div class="footer-badges">
							<span class="badge">
								<i class="el-icon-circle-check"></i>
								信息透明
							</span>
							<span class="badge">
								<i class="el-icon-bangzhu"></i>
								服务便捷
							</span>
							<span class="badge">
								<i class="el-icon-sunny"></i>
								体验清爽
							</span>
						</div>
						<div class="footer-contact">
							<div class="footer-contact__item">
								<i class="el-icon-phone"></i>
								<span>服务热线：400-888-8888</span>
							</div>
							<div class="footer-contact__item">
								<i class="el-icon-message"></i>
								<span>邮箱：service@agricultural.com</span>
							</div>
						</div>
					</div>
				</div>

				<!-- 富文本内容 -->
				<div class="footer-rich" v-if="bottomContent">
					<div class="footer-rich__card">
						<div v-html="bottomContent"></div>
					</div>
				</div>

				<!-- 底部版权 -->
				<div class="footer-bottom">
					<div class="footer-bottom__left">
						<span class="footer-bottom__logo">
							<svg viewBox="0 0 24 24" fill="currentColor">
								<path d="M12 2L15.09 8.26L22 9.27L17 14.14L18.18 21.02L12 17.77L5.82 21.02L7 14.14L2 9.27L8.91 8.26L12 2Z"/>
							</svg>
						</span>
						{{ new Date().getFullYear() }} 助农扶贫服务平台 · 让农业更美好
					</div>
					<div class="footer-bottom__right">
						<a href="#">隐私政策</a>
						<span class="footer-bottom__sep">|</span>
						<a href="#">使用条款</a>
						<span class="footer-bottom__sep">|</span>
						<a href="#">网站地图</a>
					</div>
				</div>
			</div>
		</div>
		</div>
	</div>
</div>
</template>

<script>
import Vue from 'vue'
import Swiper from "swiper";
import axios from 'axios'
import MessageNotification from '@/components/MessageNotification.vue'

export default {
    components: {
        MessageNotification
    },
    data() {
		return {
            activeIndex: '0',
			mobileNavOpen: false,
			showHeroIntro: true,
			roleMenus: [{"backMenu":[{"child":[{"allButtons":["新增","查看","修改","删除"],"appFrontIcon":"cuIcon-paint","buttons":["新增","查看","修改","删除"],"menu":"农商","menuJump":"列表","tableName":"nonghu"}],"menu":"农商管理"},{"child":[{"allButtons":["新增","查看","修改","删除"],"appFrontIcon":"cuIcon-wenzi","buttons":["新增","查看","修改","删除"],"menu":"用户","menuJump":"列表","tableName":"yonghu"}],"menu":"用户管理"},{"child":[{"allButtons":["新增","查看","修改","删除"],"appFrontIcon":"cuIcon-link","buttons":["新增","查看","修改","删除"],"menu":"产品分类","menuJump":"列表","tableName":"chanpinfenlei"}],"menu":"产品分类管理"},{"child":[{"allButtons":["新增","查看","修改","删除"],"appFrontIcon":"cuIcon-cardboard","buttons":["新增","查看","修改","删除"],"menu":"文章分类","menuJump":"列表","tableName":"wenzhangfenlei"}],"menu":"文章分类管理"},{"child":[{"allButtons":["新增","查看","修改","删除","产品库存统计","产品分类","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-similar","buttons":["查看","修改","删除","查看评论","产品库存统计","产品分类","首页总数","首页统计"],"menu":"助农商品","menuJump":"列表","tableName":"zhunongshangpin"}],"menu":"助农商品管理"},{"child":[{"allButtons":["新增","查看","修改","删除","文章分类","文章数量","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-send","buttons":["新增","查看","修改","删除","查看评论","文章分类","文章数量","首页总数","首页统计"],"menu":"助农脱贫","menuJump":"列表","tableName":"zhunongtuopin"}],"menu":"助农脱贫管理"},{"child":[{"allButtons":["新增","查看","修改","删除","课程占比","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-time","buttons":["新增","查看","修改","删除","查看评论","课程占比","首页总数","首页统计"],"menu":"农技学堂","menuJump":"列表","tableName":"nongjixuetang"}],"menu":"农技学堂管理"},{"child":[{"allButtons":["新增","查看","修改","删除"],"appFrontIcon":"cuIcon-news","buttons":["新增","查看","修改","删除"],"menu":"公告资讯","tableName":"news"},{"allButtons":["新增","查看","修改","删除"],"appFrontIcon":"cuIcon-news","buttons":["新增","查看","修改","删除"],"menu":"公告资讯分类","tableName":"newstype"},{"allButtons":["查看","修改"],"appFrontIcon":"cuIcon-skin","buttons":["查看","修改"],"menu":"关于我们","tableName":"aboutus"},{"allButtons":["查看","修改"],"appFrontIcon":"cuIcon-circle","buttons":["查看","修改"],"menu":"系统简介","tableName":"systemintro"},{"allButtons":["新增","查看","修改","删除"],"appFrontIcon":"cuIcon-attentionfavor","buttons":["查看","修改","删除"],"menu":"轮播图管理","tableName":"config"}],"menu":"系统管理"},{"child":[{"allButtons":["新增","查看","修改","删除","导出","日销量","月销量","年销量","品销量","类销量","日销额","月销额","年销额","品销额","类销额"],"appFrontIcon":"cuIcon-vip","buttons":["查看","删除"],"menu":"已取消订单","tableName":"orders/已取消"},{"allButtons":["新增","查看","修改","删除","导出","日销量","月销量","年销量","品销量","类销量","日销额","月销额","年销额","品销额","类销额","物流"],"appFrontIcon":"cuIcon-pay","buttons":["查看","删除"],"menu":"已退款订单","tableName":"orders/已退款"},{"allButtons":["新增","查看","修改","删除","导出","日销量","月销量","年销量","品销量","类销量","日销额","月销额","年销额","品销额","类销额","确认收货","物流"],"appFrontIcon":"cuIcon-phone","buttons":["查看","删除"],"menu":"已发货订单","tableName":"orders/已发货"},{"allButtons":["新增","查看","修改","删除","导出","日销量","月销量","年销量","品销量","类销量","日销额","月销额","年销额","品销额","类销额"],"appFrontIcon":"cuIcon-vip","buttons":["查看","删除"],"menu":"未支付订单","tableName":"orders/未支付"},{"allButtons":["新增","查看","修改","删除","导出","日销量","月销量","年销量","品销量","类销量","日销额","月销额","年销额","品销额","类销额","发货","物流","核销"],"appFrontIcon":"cuIcon-album","buttons":["查看","删除"],"menu":"已支付订单","tableName":"orders/已支付"},{"allButtons":["新增","查看","修改","删除","导出","日销量","月销量","年销量","品销量","类销量","日销额","月销额","年销额","品销额","类销额","物流","退货审核"],"appFrontIcon":"cuIcon-vipcard","buttons":["查看","删除","日销量","月销量","日销额","月销额"],"menu":"已完成订单","tableName":"orders/已完成"}],"menu":"订单管理"}],"frontMenu":[{"child":[{"allButtons":["新增","查看","修改","删除","产品库存统计","产品分类","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-attentionfavor","buttons":["查看","查看评论"],"menu":"助农商品列表","menuJump":"列表","tableName":"zhunongshangpin"}],"menu":"助农商品模块"},{"child":[{"allButtons":["新增","查看","修改","删除","文章分类","文章数量","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-cardboard","buttons":["查看","查看评论"],"menu":"助农脱贫列表","menuJump":"列表","tableName":"zhunongtuopin"}],"menu":"助农脱贫模块"},{"child":[{"allButtons":["新增","查看","修改","删除","课程占比","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-goodsnew","buttons":["查看","查看评论"],"menu":"农技学堂列表","menuJump":"列表","tableName":"nongjixuetang"}],"menu":"农技学堂模块"}],"hasBackLogin":"是","hasBackRegister":"否","hasFrontLogin":"否","hasFrontRegister":"否","roleName":"管理员","tableName":"users"},{"backMenu":[{"child":[{"allButtons":["新增","查看","修改","删除"],"appFrontIcon":"cuIcon-link","buttons":["新增","查看","修改","删除"],"menu":"产品分类","menuJump":"列表","tableName":"chanpinfenlei"}],"menu":"产品分类管理"},{"child":[{"allButtons":["新增","查看","修改","删除","产品库存统计","产品分类","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-similar","buttons":["新增","查看","修改","删除","查看评论"],"menu":"助农商品","menuJump":"列表","tableName":"zhunongshangpin"}],"menu":"助农商品管理"},{"child":[{"allButtons":["新增","查看","修改","删除","导出","日销量","月销量","年销量","品销量","类销量","日销额","月销额","年销额","品销额","类销额","发货","物流","核销"],"appFrontIcon":"cuIcon-album","buttons":["查看","删除","发货"],"menu":"已支付订单","tableName":"orders/已支付"},{"allButtons":["新增","查看","修改","删除","导出","日销量","月销量","年销量","品销量","类销量","日销额","月销额","年销额","品销额","类销额","物流","退货审核"],"appFrontIcon":"cuIcon-vipcard","buttons":["查看","删除","日销量","月销量","日销额","月销额"],"menu":"已完成订单","tableName":"orders/已完成"},{"allButtons":["新增","查看","修改","删除","导出","日销量","月销量","年销量","品销量","类销量","日销额","月销额","年销额","品销额","类销额"],"appFrontIcon":"cuIcon-vip","buttons":["查看","删除"],"menu":"已取消订单","tableName":"orders/已取消"},{"allButtons":["新增","查看","修改","删除","导出","日销量","月销量","年销量","品销量","类销量","日销额","月销额","年销额","品销额","类销额","物流"],"appFrontIcon":"cuIcon-pay","buttons":["查看","删除"],"menu":"已退款订单","tableName":"orders/已退款"},{"allButtons":["新增","查看","修改","删除","导出","日销量","月销量","年销量","品销量","类销量","日销额","月销额","年销额","品销额","类销额","确认收货","物流"],"appFrontIcon":"cuIcon-phone","buttons":["查看","删除"],"menu":"已发货订单","tableName":"orders/已发货"},{"allButtons":["新增","查看","修改","删除","导出","日销量","月销量","年销量","品销量","类销量","日销额","月销额","年销额","品销额","类销额"],"appFrontIcon":"cuIcon-vip","buttons":["查看","删除"],"menu":"未支付订单","tableName":"orders/未支付"}],"menu":"订单管理"}],"frontMenu":[{"child":[{"allButtons":["新增","查看","修改","删除","产品库存统计","产品分类","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-attentionfavor","buttons":["查看","查看评论"],"menu":"助农商品列表","menuJump":"列表","tableName":"zhunongshangpin"}],"menu":"助农商品模块"},{"child":[{"allButtons":["新增","查看","修改","删除","文章分类","文章数量","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-cardboard","buttons":["查看","查看评论"],"menu":"助农脱贫列表","menuJump":"列表","tableName":"zhunongtuopin"}],"menu":"助农脱贫模块"},{"child":[{"allButtons":["新增","查看","修改","删除","课程占比","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-goodsnew","buttons":["查看","查看评论"],"menu":"农技学堂列表","menuJump":"列表","tableName":"nongjixuetang"}],"menu":"农技学堂模块"}],"hasBackLogin":"是","hasBackRegister":"是","hasFrontLogin":"否","hasFrontRegister":"否","roleName":"农商","tableName":"nonghu"},{"backMenu":[],"frontMenu":[{"child":[{"allButtons":["新增","查看","修改","删除","产品库存统计","产品分类","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-attentionfavor","buttons":["查看","查看评论"],"menu":"助农商品列表","menuJump":"列表","tableName":"zhunongshangpin"}],"menu":"助农商品模块"},{"child":[{"allButtons":["新增","查看","修改","删除","文章分类","文章数量","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-cardboard","buttons":["查看","查看评论"],"menu":"助农脱贫列表","menuJump":"列表","tableName":"zhunongtuopin"}],"menu":"助农脱贫模块"},{"child":[{"allButtons":["新增","查看","修改","删除","课程占比","查看评论","首页总数","首页统计"],"appFrontIcon":"cuIcon-goodsnew","buttons":["查看","查看评论"],"menu":"农技学堂列表","menuJump":"列表","tableName":"nongjixuetang"}],"menu":"农技学堂模块"}],"hasBackLogin":"否","hasBackRegister":"否","hasFrontLogin":"是","hasFrontRegister":"是","roleName":"用户","tableName":"yonghu"}],
			baseUrl: '',
			carouselList: [],
			menuList: [],
			statsData: {
				goods: 0,
				projects: 0,
				courses: 0,
				news: 0,
			},
			featuredList: [],
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
		this.getStatsData();
		this.getFeaturedData();
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
		toRegister() {
			/* 与 login.vue 中注册入口一致：必须带 pageFlag、role，register.vue 才会渲染表单 */
			this.$router.push({
				path: '/register',
				query: { role: 'yonghu', pageFlag: 'register' },
			});
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
			// resourceUrl 指向上传文件目录（开发环境 /upload/，生产环境 http://localhost:8080/upload/）
			const base = (this.$config.resourceUrl || this.$config.baseUrl || "").replace(/\/?$/, "/");
			return base + v.replace(/^\//, "");
		},
		getCarousel() {
			this.$http.get('config/list', {params: { page: 1, limit: 10, sort: 'id', order: 'asc' }}).then(res => {
				if (res.data.code == 0 && res.data.data && res.data.data.list) {
					this.carouselList = res.data.data.list.filter(item => item.value && item.value.trim() !== '');
				} else {
					this.carouselList = [];
				}
			}).catch(() => {
				this.carouselList = [];
			});
		},
		getStatsData() {
			const targets = [
				{ ref: 'countGoods',    key: 'goods'    },
				{ ref: 'countProjects', key: 'projects' },
				{ ref: 'countCourses',  key: 'courses'  },
				{ ref: 'countNews',     key: 'news'     },
			];
			const apis = [
				'zhunongshangpin/list?page=1&limit=1',
				'zhunongtuopin/list?page=1&limit=1',
				'nongjixuetang/list?page=1&limit=1',
				'news/list?page=1&limit=1',
			];
			apis.forEach((api, i) => {
				this.$http.get(api).then(res => {
					if (res.data.code === 0 && res.data.data) {
						const total = res.data.data.total || 0;
						this.statsData[targets[i].key] = total;
						this.$nextTick(() => {
							this.animateCount(targets[i].ref, total);
						});
					}
				}).catch(() => {});
			});
		},
		animateCount(refName, target) {
			const el = this.$refs[refName];
			if (!el) return;
			const duration = 1800;
			const start = performance.now();
			const step = (now) => {
				const elapsed = now - start;
				const progress = Math.min(elapsed / duration, 1);
				const eased = 1 - Math.pow(1 - progress, 3);
				el.textContent = Math.round(eased * target);
				if (progress < 1) requestAnimationFrame(step);
			};
			requestAnimationFrame(step);
		},
		getFeaturedData() {
			this.$http.get('zhunongshangpin/list', {params: { page: 1, limit: 4, sort: 'id', order: 'desc' }}).then(res => {
				if (res.data.code === 0 && res.data.data && res.data.data.list) {
					this.featuredList = res.data.data.list.slice(0, 4);
				}
			}).catch(() => {});
		},
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
		getAvatarUrl(url) {
			if (!url) return '';
			if (url.startsWith('http://') || url.startsWith('https://')) {
				return url;
			}
			if (url.startsWith('upload/')) {
				return this.baseUrl + url;
			}
			return this.baseUrl + 'upload/' + url;
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
		--ag-green-300: #81c784;
		--ag-green-200: #a5d6a7;
		--ag-green-100: #e8f5e9;
		--ag-green-050: #f1f8e9;
		--ag-text-900: #1a2e1a;
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
		/* 页脚等：左右 10%；轮播与首页使命/核心模块区：与轮播同宽的固定左右留白 */
		--home-inline-pad: 10%;
		--banner-inline-pad: 24px;
		/* 顶栏 70px + 主导航一行约 66px，与 .site-header-shell 实际高度一致 */
		--site-header-offset: 136px;
		position: relative;
		min-height: 100vh;
		min-height: 100dvh;
		display: flex;
		flex-direction: column;
		padding-top: var(--site-header-offset) !important;
		margin: 0;
		overflow-x: hidden;
		background:
			radial-gradient(ellipse 1200px 300px at 15% -5%, rgba(102, 187, 106, 0.22), transparent 55%),
			radial-gradient(ellipse 1000px 280px at 85% 5%, rgba(76, 175, 80, 0.16), transparent 55%),
			radial-gradient(ellipse 600px 200px at 50% 50%, rgba(129, 199, 132, 0.10), transparent 70%),
			linear-gradient(180deg, #f7fbf6 0%, #ffffff 65%);
	}

	/* ========== 自然装饰叶片 ========== */
	.body-containers::before {
		content: "";
		position: fixed;
		top: var(--site-header-offset);
		right: -10px;
		width: 220px;
		height: 280px;
		background:
			radial-gradient(ellipse 80% 40% at 50% 30%, rgba(102, 187, 106, 0.10), transparent 70%),
			radial-gradient(ellipse 60% 30% at 50% 70%, rgba(56, 142, 60, 0.08), transparent 70%);
		border-radius: 50% 0 0 50%;
		pointer-events: none;
		z-index: 0;
		animation: leafDrift 18s ease-in-out infinite;
	}
	.body-containers::after {
		content: "";
		position: fixed;
		bottom: 0;
		left: -8px;
		width: 180px;
		height: 240px;
		background:
			radial-gradient(ellipse 80% 40% at 50% 30%, rgba(165, 214, 167, 0.12), transparent 70%),
			radial-gradient(ellipse 60% 30% at 50% 70%, rgba(102, 187, 106, 0.08), transparent 70%);
		border-radius: 0 60% 0 0;
		pointer-events: none;
		z-index: 0;
		animation: leafDrift2 22s ease-in-out infinite;
	}
	@keyframes leafDrift {
		0%, 100% { transform: translateY(0) rotate(0deg); }
		50% { transform: translateY(-18px) rotate(2deg); }
	}
	@keyframes leafDrift2 {
		0%, 100% { transform: translateY(0) rotate(0deg); }
		50% { transform: translateY(14px) rotate(-1.5deg); }
	}

	/* 横向滚动条 */
	::v-deep .scrollbar-wrapper-horizontal {
		overflow-x: auto !important;
		overflow-y: hidden !important;
		padding: 2px 0 4px !important;
	}
	::v-deep .el-scrollbar__bar.is-vertical {
		display: none !important;
	}

	/* ========== 顶部固定区：品牌行 + 主导航（一体，无中间空白带） ========== */
	.site-header-shell {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		z-index: 1002;
		display: flex;
		flex-direction: column;
		background: rgba(255, 255, 255, 0.96);
		backdrop-filter: saturate(1.15) blur(12px);
		border-bottom: 1px solid var(--ag-border);
		box-shadow: 0 4px 20px rgba(16, 24, 16, 0.08);
	}
	.top-container {
		position: relative;
		flex-shrink: 0;
		height: 70px;
		display: flex;
		align-items: center;
		justify-content: flex-end;
		gap: 16px;
		padding: 0 28px;
		border-bottom: 1px solid rgba(46, 125, 50, 0.1);
	}

	/* 品牌标识 */
	.brand {
		margin-right: auto;
		display: flex;
		align-items: center;
		gap: 12px;
		cursor: pointer;
		transition: transform var(--ag-transition);
	}
	.brand:hover {
		transform: translateX(4px);
	}
	.brand-logo {
		width: 42px;
		height: 42px;
		flex-shrink: 0;
	}
	.brand-logo__icon {
		width: 100%;
		height: 100%;
	}
	.brand-text {
		display: flex;
		flex-direction: column;
	}
	.brand-title {
		font-weight: 800;
		letter-spacing: 0.5px;
		color: var(--ag-green-800);
		font-size: 20px;
		line-height: 1.2;
		white-space: nowrap;
	}
	.brand-subtitle {
		font-size: 10px;
		color: var(--ag-text-500);
		letter-spacing: 1px;
		text-transform: uppercase;
		opacity: 0.8;
	}

	/* 用户信息 */
	.user-info {
		display: flex;
		align-items: center;
		gap: 10px;
		padding: 6px 14px;
		background: var(--ag-green-050);
		border-radius: 999px;
		cursor: pointer;
		transition: all var(--ag-transition);
		border: 1px solid transparent;
	}
	.user-info:hover {
		background: var(--ag-green-100);
		border-color: var(--ag-border);
	}
	.user-avatar {
		width: 32px;
		height: 32px;
		border-radius: 50%;
		overflow: hidden;
		border: 2px solid #fff;
		box-shadow: 0 2px 8px rgba(0,0,0,0.1);
	}
	.user-avatar img {
		width: 100%;
		height: 100%;
		object-fit: cover;
	}
	.user-name {
		font-size: 14px;
		font-weight: 600;
		color: var(--ag-text-700);
	}

	/* 认证按钮 */
	.btn-auth {
		border: none !important;
		display: inline-flex;
		align-items: center;
		justify-content: center;
		gap: 6px;
		font-size: 14px !important;
		height: 38px !important;
		padding: 0 18px !important;
		transition: all var(--ag-transition);
	}
	.btn-login {
		background: transparent !important;
		color: var(--ag-green-700) !important;
		border: 1px solid var(--ag-green-400) !important;
	}
	.btn-login:hover {
		background: var(--ag-green-050) !important;
		transform: translateY(-1px);
	}
	.btn-register {
		background: linear-gradient(135deg, var(--ag-green-400), var(--ag-green-700)) !important;
		color: #fff !important;
		box-shadow: 0 6px 20px rgba(56, 142, 60, 0.25);
	}
	.btn-register:hover {
		transform: translateY(-2px);
		box-shadow: 0 10px 28px rgba(56, 142, 60, 0.35);
	}
	.btn-logout {
		background: rgba(255,255,255,0.8) !important;
		color: var(--ag-text-500) !important;
		border: 1px solid var(--ag-border) !important;
	}
	.btn-logout:hover {
		background: var(--ag-danger-light) !important;
		color: var(--ag-danger) !important;
		border-color: var(--ag-danger) !important;
	}

	/* 购物车按钮 */
	.btn-shop {
		display: flex;
		align-items: center;
		gap: 6px;
		padding: 0 16px !important;
		height: 38px !important;
		border-radius: 999px !important;
		background: linear-gradient(135deg, var(--ag-green-400), var(--ag-green-700)) !important;
		color: #fff !important;
		border: none !important;
		font-size: 14px !important;
		font-weight: 600;
		box-shadow: 0 6px 20px rgba(56, 142, 60, 0.25);
		transition: all var(--ag-transition);
	}
	.btn-shop:hover {
		transform: translateY(-2px);
		box-shadow: 0 10px 28px rgba(56, 142, 60, 0.35);
	}
	.btn-shop .icon {
		font-size: 16px;
	}

	/* 汉堡菜单 */
	.btn-hamburger {
		display: none !important;
	}
	.btn-icon {
		border: 1px solid var(--ag-border) !important;
		background: rgba(255, 255, 255, 0.9) !important;
		color: var(--ag-text-700) !important;
		border-radius: 999px !important;
		height: 40px !important;
		width: 40px !important;
		padding: 0 !important;
		display: inline-flex !important;
		align-items: center !important;
		justify-content: center !important;
		transition: all var(--ag-transition);
	}
	.btn-icon:hover {
		transform: translateY(-1px);
		background: var(--ag-green-050) !important;
		box-shadow: 0 8px 20px rgba(56, 142, 60, 0.12);
	}

	/* ========== 主导航菜单 ========== */
	.menu-preview {
		position: relative;
		z-index: 1;
		flex-shrink: 0;
		padding: 4px 20px 6px;
		background: rgba(248, 253, 248, 0.92);
		border-bottom: none;
	}
	::v-deep .el-menu-horizontal-demo .el-menu-item {
		display: flex !important;
		align-items: center !important;
		justify-content: center !important;
		height: 56px !important;
		line-height: 1 !important;
		padding: 0 16px !important;
		margin: 0 4px;
		border-radius: 999px;
		transition: all var(--ag-transition);
	}
	::v-deep .el-menu-horizontal-demo .el-menu-item:hover,
	::v-deep .el-menu-horizontal-demo .el-menu-item.is-active {
		background: linear-gradient(135deg, var(--ag-green-400), var(--ag-green-700)) !important;
		color: #fff !important;
	}
	::v-deep .el-menu-horizontal-demo .el-menu-item span,
	::v-deep .el-menu-horizontal-demo .el-menu-item i {
		display: inline-flex !important;
		align-items: center !important;
		justify-content: center !important;
		line-height: 1 !important;
		height: auto !important;
	}
	.nav-icon {
		margin-right: 6px;
		font-size: 16px;
	}
	.nav-text {
		font-size: 14px;
		font-weight: 600;
	}

	/* ========== 轮播横幅（全宽，仅少量边距，不与下方 10% 内容区对齐） ========== */
	.banner-preview {
		padding: 16px var(--banner-inline-pad) 0;
		position: relative;
		width: 100%;
		max-width: none;
		margin: 0;
		box-sizing: border-box;
	}
	.carousel-item-wrapper {
		width: 100%;
		height: 100%;
		position: relative;
		overflow: hidden;
		border-radius: 20px;
		cursor: pointer;
	}
	.carousel-image {
		width: 100%;
		height: 100%;
		object-fit: cover;
		transition: transform 0.6s ease;
	}
	.carousel-item-wrapper:hover .carousel-image {
		transform: scale(1.03);
	}
	.carousel-placeholder {
		width: 100%;
		height: 100%;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		background: linear-gradient(135deg, var(--ag-green-100), var(--ag-green-050));
		color: var(--ag-text-500);
		font-size: 16px;
		gap: 12px;
	}
	.carousel-placeholder i {
		font-size: 48px;
		opacity: 0.5;
	}
	/* 仅左侧轻微中性压暗，便于白字阅读；不对图片使用 color 滤镜或整图染色 */
	.carousel-overlay {
		position: absolute;
		inset: 0;
		background: linear-gradient(
			105deg,
			rgba(0, 0, 0, 0.38) 0%,
			rgba(0, 0, 0, 0.12) 44%,
			transparent 72%
		);
		display: flex;
		align-items: center;
		padding: 0 60px;
		pointer-events: none;
	}
	.carousel-content {
		max-width: 500px;
		pointer-events: auto;
	}
	.carousel-tag {
		display: inline-flex;
		align-items: center;
		gap: 8px;
		padding: 8px 16px;
		background: rgba(0, 0, 0, 0.28);
		border: 1px solid rgba(255, 255, 255, 0.22);
		border-radius: 999px;
		color: #fff;
		font-size: 13px;
		font-weight: 600;
		margin-bottom: 16px;
	}
	.carousel-title {
		font-size: 32px;
		font-weight: 800;
		color: #fff;
		line-height: 1.3;
		margin-bottom: 12px;
		text-shadow: 0 2px 14px rgba(0, 0, 0, 0.45), 0 1px 3px rgba(0, 0, 0, 0.35);
	}
	.carousel-desc {
		font-size: 16px;
		color: rgba(255, 255, 255, 0.95);
		line-height: 1.6;
		text-shadow: 0 1px 8px rgba(0, 0, 0, 0.4);
	}

	::v-deep .banner-preview .el-carousel {
		border-radius: 20px;
		overflow: hidden;
		box-shadow: 0 16px 48px rgba(0, 0, 0, 0.1);
	}
	::v-deep .banner-preview .el-carousel__container {
		height: clamp(280px, 40vw, 560px) !important;
	}
	::v-deep .banner-preview .el-carousel__arrow {
		background: rgba(255, 255, 255, 0.9);
		color: var(--ag-green-800);
		width: 48px;
		height: 48px;
		font-size: 20px;
		transition: all var(--ag-transition);
	}
	::v-deep .banner-preview .el-carousel__arrow:hover {
		background: var(--ag-green-500);
		color: #fff;
		transform: scale(1.1);
	}
	::v-deep .banner-preview .el-carousel__indicators li button {
		display: none;
	}
	::v-deep .banner-preview .el-carousel__indicators li {
		width: 12px;
		height: 12px;
		border-radius: 999px;
		background: rgba(255, 255, 255, 0.5);
		margin: 0 5px !important;
		transition: all var(--ag-transition);
	}
	::v-deep .banner-preview .el-carousel__indicators li.is-active {
		width: 32px;
		background: #fff;
	}

	/* ========== Hero 引导卡片 ========== */
	.hero-overlay {
		position: absolute;
		bottom: 24px;
		left: 50px;
		pointer-events: none;
		display: flex;
		align-items: flex-end;
	}
	.hero-card {
		pointer-events: auto;
		max-width: 420px;
		border-radius: 20px;
		padding: 24px;
		background: rgba(255, 255, 255, 0.95);
		backdrop-filter: saturate(1.2) blur(16px);
		border: 1px solid rgba(255,255,255,0.8);
		box-shadow: 0 20px 60px rgba(16, 24, 16, 0.18);
		position: relative;
	}
	.hero-close {
		position: absolute;
		top: 12px;
		right: 12px;
		width: 28px;
		height: 28px;
		border-radius: 999px;
		border: 1px solid var(--ag-border);
		background: rgba(255,255,255,0.9);
		cursor: pointer;
		display: inline-flex;
		align-items: center;
		justify-content: center;
		transition: all var(--ag-transition);
	}
	.hero-close::before,
	.hero-close::after {
		content: "";
		position: absolute;
		width: 12px;
		height: 2px;
		background: var(--ag-text-500);
		border-radius: 2px;
	}
	.hero-close::before { transform: rotate(45deg); }
	.hero-close::after { transform: rotate(-45deg); }
	.hero-close:hover {
		background: var(--ag-green-050);
		transform: translateY(-1px);
	}
	.hero-kicker {
		display: inline-flex;
		align-items: center;
		gap: 8px;
		color: var(--ag-green-700);
		font-weight: 700;
		font-size: 12px;
		letter-spacing: 0.5px;
		text-transform: uppercase;
	}
	.hero-kicker__dot {
		width: 8px;
		height: 8px;
		border-radius: 999px;
		background: var(--ag-green-500);
		animation: pulse 2s infinite;
	}
	@keyframes pulse {
		0%, 100% { transform: scale(1); opacity: 1; }
		50% { transform: scale(1.3); opacity: 0.7; }
	}
	.hero-title {
		margin-top: 12px;
		font-size: 22px;
		line-height: 1.3;
		color: var(--ag-text-900);
		font-weight: 800;
	}
	.hero-desc {
		margin-top: 10px;
		color: var(--ag-text-500);
		font-size: 13px;
		line-height: 1.7;
	}
	.hero-actions {
		margin-top: 16px;
		display: flex;
		gap: 10px;
		flex-wrap: wrap;
	}
	.hero-stats {
		display: flex;
		gap: 20px;
		margin-top: 16px;
		padding-top: 16px;
		border-top: 1px dashed var(--ag-border);
	}
	.hero-stat {
		display: flex;
		flex-direction: column;
	}
	.hero-stat__num {
		font-size: 20px;
		font-weight: 800;
		color: var(--ag-green-700);
	}
	.hero-stat__label {
		font-size: 11px;
		color: var(--ag-text-400);
		margin-top: 2px;
	}
	.btn-hero-primary {
		border: none !important;
		background: linear-gradient(135deg, var(--ag-green-400), var(--ag-green-700)) !important;
		color: #fff !important;
		border-radius: 999px !important;
		padding: 10px 20px !important;
		font-size: 14px !important;
		font-weight: 600;
		box-shadow: 0 8px 24px rgba(56, 142, 60, 0.3);
		display: inline-flex !important;
		align-items: center !important;
		gap: 6px;
		transition: all var(--ag-transition);
	}
	.btn-hero-primary:hover {
		transform: translateY(-2px);
		box-shadow: 0 12px 32px rgba(56, 142, 60, 0.4);
	}
	.btn-hero-ghost {
		border-radius: 999px !important;
		padding: 10px 20px !important;
		border: 1px solid var(--ag-border) !important;
		color: var(--ag-green-700) !important;
		background: rgba(255,255,255,0.9) !important;
		font-size: 14px !important;
		font-weight: 600;
		display: inline-flex !important;
		align-items: center !important;
		gap: 6px;
		transition: all var(--ag-transition);
	}
	.btn-hero-ghost:hover {
		transform: translateY(-2px);
		background: var(--ag-green-050) !important;
		border-color: var(--ag-green-400) !important;
	}
	.hero-mini {
		pointer-events: auto;
		padding: 10px 16px;
		background: rgba(255,255,255,0.9);
		backdrop-filter: blur(10px);
		border-radius: 999px;
		color: var(--ag-green-700);
		font-size: 13px;
		font-weight: 700;
		cursor: pointer;
		box-shadow: 0 8px 24px rgba(0,0,0,0.12);
		display: inline-flex;
		align-items: center;
		gap: 6px;
		transition: all var(--ag-transition);
	}
	.hero-mini:hover {
		transform: translateY(-2px);
		background: #fff;
		box-shadow: 0 12px 32px rgba(0,0,0,0.16);
	}

	/* ========== 首页核心内容（与轮播横幅同宽） ========== */
	.home-sections {
		padding: 28px var(--banner-inline-pad) 16px;
		width: 100%;
		max-width: none;
		margin: 0;
		box-sizing: border-box;
	}
	.section-hd {
		display: flex;
		justify-content: flex-end;
		align-items: flex-end;
		margin-bottom: 16px;
	}
	.section-hd__text {
		text-align: right;
		max-width: 100%;
	}
	.section-hd__title {
		font-size: 15px;
		font-weight: 700;
		color: var(--ag-text-900);
		letter-spacing: 0.02em;
		line-height: 1.35;
	}
	.section-hd__sub {
		font-size: 11px;
		color: var(--ag-text-500);
		margin-top: 4px;
		line-height: 1.45;
	}

	/* ========== 使命宣言 ========== */
	.mission {
		display: grid;
		grid-template-columns: minmax(0, 1.08fr) minmax(280px, 0.92fr);
		gap: 24px;
		align-items: stretch;
		margin-bottom: 8px;
	}
	.mission__left,
	.mission__right {
		border-radius: 20px;
		border: 1px solid var(--ag-border);
		background: linear-gradient(180deg, rgba(255, 255, 255, 0.95), rgba(255, 255, 255, 0.85));
		backdrop-filter: saturate(1.1) blur(12px);
		box-shadow: 0 12px 36px rgba(16, 24, 16, 0.08);
		padding: 28px 26px;
		position: relative;
		overflow: hidden;
		min-height: 0;
	}
	.mission__left {
		display: flex;
		flex-direction: column;
		justify-content: center;
	}
	.mission__left::after {
		content: "";
		position: absolute;
		right: -20%;
		bottom: -25%;
		width: 55%;
		height: 70%;
		background: radial-gradient(ellipse at 70% 80%, rgba(102, 187, 106, 0.14), transparent 62%);
		pointer-events: none;
	}
	.mission__left-inner {
		position: relative;
		z-index: 1;
		width: 100%;
		display: grid;
		grid-template-columns: minmax(0, 1.05fr) minmax(240px, 0.95fr);
		gap: 28px 36px;
		align-items: start;
	}
	.mission__intro {
		min-width: 0;
	}
	.mission__points {
		margin: 18px 0 0;
		padding: 0;
		list-style: none;
		display: grid;
		gap: 10px;
	}
	.mission__points li {
		display: flex;
		align-items: flex-start;
		gap: 10px;
		font-size: 13px;
		line-height: 1.55;
		color: var(--ag-text-600);
	}
	.mission__points li i {
		margin-top: 2px;
		color: var(--ag-green-600);
		font-size: 15px;
		flex-shrink: 0;
	}
	.mission__pillars {
		display: flex;
		flex-direction: column;
		gap: 12px;
		min-width: 0;
	}
	.mission__pillar {
		display: flex;
		gap: 14px;
		align-items: flex-start;
		padding: 14px 16px;
		border-radius: 14px;
		background: linear-gradient(135deg, rgba(255, 255, 255, 0.95), rgba(246, 250, 246, 0.92));
		border: 1px solid rgba(129, 199, 132, 0.22);
		box-shadow: 0 4px 16px rgba(27, 94, 32, 0.05);
		transition: border-color var(--ag-transition), box-shadow var(--ag-transition);
	}
	.mission__pillar:hover {
		border-color: rgba(129, 199, 132, 0.45);
		box-shadow: 0 8px 22px rgba(27, 94, 32, 0.08);
	}
	.mission__pillar-icon {
		width: 44px;
		height: 44px;
		border-radius: 12px;
		display: flex;
		align-items: center;
		justify-content: center;
		color: #fff;
		font-size: 20px;
		flex-shrink: 0;
	}
	.mission__pillar-icon--goods {
		background: linear-gradient(145deg, var(--ag-green-500), var(--ag-green-700));
	}
	.mission__pillar-icon--proj {
		background: linear-gradient(145deg, #7e57c2, #5e35b1);
	}
	.mission__pillar-icon--learn {
		background: linear-gradient(145deg, #26a69a, #00897b);
	}
	.mission__pillar-text {
		min-width: 0;
		flex: 1;
	}
	.mission__pillar-name {
		font-size: 15px;
		font-weight: 800;
		color: var(--ag-text-900);
		letter-spacing: 0.04em;
	}
	.mission__pillar-note {
		margin: 6px 0 0;
		font-size: 12px;
		line-height: 1.55;
		color: var(--ag-text-500);
	}
	.mission__right {
		display: flex;
		flex-direction: column;
		gap: 16px;
		justify-content: space-between;
	}
	.mission__tag {
		display: inline-flex;
		align-items: center;
		gap: 8px;
		padding: 8px 14px;
		border-radius: 999px;
		background: linear-gradient(135deg, var(--ag-green-100), var(--ag-green-050));
		border: 1px solid var(--ag-border);
		color: var(--ag-green-800);
		font-weight: 800;
		font-size: 12px;
	}
	.mission__tag-icon {
		width: 14px;
		height: 14px;
		color: var(--ag-green-600);
	}
	.mission__title {
		margin: 16px 0 0;
		padding: 0;
		font-size: clamp(18px, 2.1vw, 24px);
		line-height: 1.45;
		font-weight: 800;
		color: var(--ag-text-900);
		letter-spacing: 0.02em;
	}
	.mission__desc {
		margin: 14px 0 0;
		padding: 0;
		color: var(--ag-text-500);
		line-height: 1.8;
		font-size: 14px;
	}
	.mission__hl {
		color: var(--ag-green-800);
		font-weight: 700;
	}
	.mission__sep {
		color: var(--ag-text-400);
		font-weight: 500;
	}

	/* 统计数据 */
	.stats {
		display: grid;
		grid-template-columns: 1fr 1fr;
		gap: 12px;
		flex-shrink: 0;
	}
	.stat {
		display: flex;
		align-items: center;
		gap: 12px;
		border-radius: 14px;
		background: var(--ag-green-050);
		border: 1px solid var(--ag-border);
		padding: 16px;
		transition: all var(--ag-transition);
	}
	.stat:hover {
		transform: translateY(-2px);
		box-shadow: 0 8px 24px rgba(56, 142, 60, 0.12);
	}
	.stat__icon {
		width: 44px;
		height: 44px;
		border-radius: 12px;
		background: linear-gradient(135deg, var(--ag-green-400), var(--ag-green-600));
		display: flex;
		align-items: center;
		justify-content: center;
		color: #fff;
		font-size: 20px;
		flex-shrink: 0;
	}
	.stat__icon--blue {
		background: linear-gradient(135deg, #4fc3f7, #0288d1);
	}
	.stat__content {
		flex: 1;
	}
	.stat__num {
		font-weight: 800;
		color: var(--ag-green-800);
		font-size: 20px;
	}
	.stat__label {
		font-size: 12px;
		color: var(--ag-text-400);
		margin-top: 2px;
	}

	/* 可信背书 */
	.trust {
		flex: 1;
		min-height: 0;
		margin-top: 0;
		border-radius: 14px;
		background: rgba(255,255,255,0.8);
		border: 1px solid var(--ag-border);
		padding: 18px 16px;
		display: flex;
		flex-direction: column;
	}
	.trust__title {
		display: flex;
		align-items: center;
		gap: 8px;
		font-weight: 800;
		color: var(--ag-text-700);
		font-size: 14px;
		margin-bottom: 12px;
		color: var(--ag-green-700);
	}
	.trust__list {
		display: grid;
		gap: 10px;
		flex: 1;
	}
	.trust__item {
		display: flex;
		align-items: flex-start;
		gap: 10px;
	}
	.trust__dot {
		width: 8px;
		height: 8px;
		border-radius: 999px;
		margin-top: 6px;
		background: var(--ag-green-400);
		flex-shrink: 0;
	}
	.trust__text {
		color: var(--ag-text-500);
		font-size: 12px;
		line-height: 1.6;
	}

	/* ========== 助农数据统计条 ========== */
	.stats-bar {
		margin: 24px 0 0;
		position: relative;
		z-index: 1;
	}
	.stats-bar__inner {
		display: flex;
		align-items: center;
		justify-content: center;
		background: linear-gradient(135deg, rgba(232, 245, 233, 0.96), rgba(255, 255, 255, 0.94));
		border: 1px solid var(--ag-border);
		border-radius: 20px;
		box-shadow: 0 8px 28px rgba(27, 94, 32, 0.08);
		padding: 20px 40px;
		gap: 0;
	}
	.stats-bar__item {
		display: flex;
		align-items: center;
		gap: 14px;
		flex: 1;
		justify-content: center;
		padding: 4px 0;
	}
	.stats-bar__divider {
		width: 1px;
		height: 48px;
		background: var(--ag-border);
		flex-shrink: 0;
	}
	.stats-bar__icon {
		width: 48px;
		height: 48px;
		border-radius: 14px;
		background: linear-gradient(145deg, var(--ag-green-400), var(--ag-green-600));
		display: flex;
		align-items: center;
		justify-content: center;
		color: #fff;
		font-size: 22px;
		flex-shrink: 0;
		box-shadow: 0 4px 12px rgba(56, 142, 60, 0.25);
	}
	.stats-bar__icon--proj {
		background: linear-gradient(145deg, #7e57c2, #5e35b1);
		box-shadow: 0 4px 12px rgba(94, 53, 177, 0.25);
	}
	.stats-bar__icon--learn {
		background: linear-gradient(145deg, #26a69a, #00897b);
		box-shadow: 0 4px 12px rgba(0, 137, 123, 0.25);
	}
	.stats-bar__icon--news {
		background: linear-gradient(145deg, #f57c00, #e65100);
		box-shadow: 0 4px 12px rgba(230, 81, 0, 0.25);
	}
	.stats-bar__content {
		min-width: 0;
	}
	.stats-bar__num {
		font-size: 26px;
		font-weight: 800;
		color: var(--ag-text-900);
		line-height: 1.2;
		letter-spacing: 0.04em;
	}
	.stats-bar__label {
		font-size: 12px;
		color: var(--ag-text-400);
		margin-top: 2px;
		font-weight: 500;
	}

	/* ========== 核心模块入口 ========== */
	.quick-entries {
		margin-top: 28px;
		padding-top: 28px;
		border-top: 1px solid var(--ag-border-subtle, rgba(46, 125, 50, 0.08));
		width: 100%;
	}
	.entry-grid {
		display: grid;
		grid-template-columns: repeat(3, 1fr);
		gap: 20px;
		width: 100%;
	}
	.entry-card {
		position: relative;
		border-radius: 20px;
		border: 1px solid var(--ag-border);
		background: linear-gradient(180deg, rgba(255, 255, 255, 0.95), rgba(255, 255, 255, 0.85));
		box-shadow: 0 10px 30px rgba(16, 24, 16, 0.06);
		padding: 24px;
		cursor: pointer;
		min-height: 180px;
		display: flex;
		flex-direction: column;
		transition: all var(--ag-transition);
		overflow: hidden;
	}
	.entry-card__decoration {
		position: absolute;
		top: -40px;
		right: -40px;
		width: 160px;
		height: 160px;
		border-radius: 999px;
		background: radial-gradient(circle, rgba(102, 187, 106, 0.3), transparent 70%);
		pointer-events: none;
		transition: all 0.4s ease;
	}
	.entry-card__decoration--orange {
		background: radial-gradient(circle, rgba(139, 195, 74, 0.3), transparent 70%);
	}
	.entry-card__decoration--teal {
		background: radial-gradient(circle, rgba(76, 175, 80, 0.3), transparent 70%);
	}
	.entry-card__decoration--purple {
		background: radial-gradient(circle, rgba(129, 199, 132, 0.3), transparent 70%);
	}
	.entry-card__decoration--red {
		background: radial-gradient(circle, rgba(165, 214, 167, 0.3), transparent 70%);
	}
	.entry-card__decoration--gold {
		background: radial-gradient(circle, rgba(129, 199, 132, 0.25), transparent 70%);
	}
	.entry-card:hover {
		transform: translateY(-8px);
		box-shadow: 0 20px 50px rgba(16, 24, 16, 0.14);
		border-color: var(--ag-green-300);
	}
	.entry-card:hover .entry-card__decoration {
		transform: scale(1.3) rotate(15deg);
	}
	.entry-card__head {
		display: flex;
		align-items: center;
		gap: 14px;
		position: relative;
		z-index: 1;
	}
	.entry-card__icon {
		width: 50px;
		height: 50px;
		border-radius: 14px;
		display: inline-flex;
		align-items: center;
		justify-content: center;
		color: #fff;
		font-size: 22px;
		box-shadow: 0 8px 20px rgba(56, 142, 60, 0.2);
	}
	.entry-card__icon--goods { background: linear-gradient(135deg, #66bb6a, #2e7d32); }
	.entry-card__icon--project { background: linear-gradient(135deg, #8bc34a, #558b2f); }
	.entry-card__icon--learn { background: linear-gradient(135deg, #4caf50, #1b5e20); }
	.entry-card__icon--support { background: linear-gradient(135deg, #81c784, #388e3c); }
	.entry-card__icon--policy { background: linear-gradient(135deg, #c8e6c9, #4caf50); color: #2e7d32; }
	.entry-card__icon--enterprise { background: linear-gradient(135deg, #a5d6a7, #2e7d32); }
	.entry-card__title {
		font-weight: 800;
		color: var(--ag-text-900);
		font-size: 18px;
		position: relative;
		z-index: 1;
	}
	.entry-card__desc {
		margin-top: 14px;
		color: var(--ag-text-500);
		line-height: 1.7;
		font-size: 13px;
		flex: 1;
		position: relative;
		z-index: 1;
	}
	.entry-card__footer {
		margin-top: 16px;
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding-top: 14px;
		border-top: 1px solid var(--ag-border);
		position: relative;
		z-index: 1;
	}
	.entry-card__cta {
		color: var(--ag-green-700);
		font-weight: 700;
		font-size: 13px;
	}
	.entry-card__footer i {
		color: var(--ag-green-400);
		transition: transform var(--ag-transition);
	}
	.entry-card:hover .entry-card__footer i {
		transform: translateX(4px);
	}

	/* ========== 移动端抽屉 ========== */
	::v-deep .mobile-drawer {
		background: linear-gradient(180deg, rgba(247, 251, 246, 0.98), rgba(255, 255, 255, 0.98));
	}
	.mobile-drawer__header {
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 20px 18px 16px;
		border-bottom: 1px solid var(--ag-border);
		background: linear-gradient(135deg, var(--ag-green-050), #fff);
	}
	.mobile-drawer__brand {
		display: flex;
		flex-direction: column;
		gap: 4px;
	}
	.mobile-drawer__title {
		font-weight: 800;
		color: var(--ag-green-800);
		font-size: 18px;
	}
	.mobile-drawer__sub {
		font-size: 12px;
		color: var(--ag-text-500);
	}
	.mobile-drawer__menu {
		padding: 12px 10px 16px;
	}
	::v-deep .mobile-drawer__menu .el-menu {
		border-right: 0 !important;
		background: transparent !important;
	}
	::v-deep .mobile-drawer__menu .el-menu-item {
		border-radius: 12px;
		margin: 4px 6px;
		height: 48px;
		line-height: 48px;
	}
	::v-deep .mobile-drawer__menu .el-menu-item.is-active {
		background: var(--ag-green-050) !important;
		color: var(--ag-green-800) !important;
		font-weight: 800;
	}

	/* ========== 通用卡片 hover ========== */
	::v-deep .el-card,
	::v-deep .card,
	::v-deep .list-card,
	::v-deep .detail-card {
		border-radius: var(--ag-radius) !important;
		border: 1px solid var(--ag-border) !important;
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

	/* ========== 时令精选推荐 ========== */
	.featured-section {
		margin: 24px var(--banner-inline-pad) 0;
		position: relative;
		z-index: 1;
	}
	.featured-section__hd {
		display: flex;
		align-items: center;
		justify-content: space-between;
		margin-bottom: 18px;
	}
	.featured-section__label {
		display: flex;
		align-items: center;
		gap: 10px;
		font-size: 20px;
		font-weight: 800;
		color: var(--ag-text-900);
	}
	.featured-section__label i {
		color: #f57c00;
		font-size: 24px;
	}
	.featured-section__meta {
		font-size: 13px;
		color: var(--ag-text-400);
		background: var(--ag-green-050);
		border: 1px solid var(--ag-border);
		padding: 6px 14px;
		border-radius: 999px;
	}
	.featured-grid {
		display: grid;
		grid-template-columns: repeat(4, 1fr);
		gap: 18px;
	}
	.featured-card {
		border-radius: 18px;
		border: 1px solid var(--ag-border);
		background: linear-gradient(180deg, rgba(255, 255, 255, 0.97), rgba(255, 255, 255, 0.9));
		box-shadow: 0 6px 22px rgba(27, 94, 32, 0.07);
		overflow: hidden;
		cursor: pointer;
		transition: all var(--ag-transition);
	}
	.featured-card:hover {
		transform: translateY(-5px);
		box-shadow: 0 14px 36px rgba(27, 94, 32, 0.13);
	}
	.featured-card__img {
		position: relative;
		height: 160px;
		overflow: hidden;
		background: linear-gradient(135deg, var(--ag-green-100), var(--ag-green-050));
	}
	.featured-card__img img {
		width: 100%;
		height: 100%;
		object-fit: cover;
		transition: transform 0.5s ease;
	}
	.featured-card:hover .featured-card__img img {
		transform: scale(1.06);
	}
	.featured-card__placeholder {
		width: 100%;
		height: 100%;
		display: flex;
		align-items: center;
		justify-content: center;
		color: var(--ag-green-300);
		font-size: 36px;
	}
	.featured-card__tag {
		position: absolute;
		top: 10px;
		left: 10px;
		display: flex;
		align-items: center;
		gap: 5px;
		padding: 5px 10px;
		background: rgba(245, 124, 0, 0.88);
		border-radius: 999px;
		color: #fff;
		font-size: 11px;
		font-weight: 700;
		backdrop-filter: blur(4px);
	}
	.featured-card__body {
		padding: 14px 16px 16px;
	}
	.featured-card__name {
		font-size: 14px;
		font-weight: 700;
		color: var(--ag-text-900);
		line-height: 1.4;
		margin-bottom: 6px;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
	.featured-card__desc {
		font-size: 12px;
		color: var(--ag-text-400);
		line-height: 1.5;
		overflow: hidden;
		display: -webkit-box;
		-webkit-line-clamp: 2;
		-webkit-box-orient: vertical;
		margin-bottom: 10px;
	}
	.featured-card__footer {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}
	.featured-card__price {
		font-size: 18px;
		font-weight: 800;
		color: #e53935;
	}
	.featured-card__btn {
		font-size: 12px;
		color: var(--ag-green-700);
		font-weight: 600;
		display: flex;
		align-items: center;
		gap: 3px;
	}

	/* ========== 平台优势信任条 ========== */
	.trust-bar {
		margin: 20px 0 0;
		position: relative;
		z-index: 1;
	}
	.trust-bar__inner {
		display: grid;
		grid-template-columns: repeat(4, 1fr);
		gap: 16px;
		padding: 0 var(--banner-inline-pad);
		box-sizing: border-box;
	}
	.trust-bar__item {
		display: flex;
		align-items: center;
		gap: 14px;
		padding: 18px 20px;
		background: rgba(255, 255, 255, 0.75);
		border: 1px solid rgba(129, 199, 132, 0.2);
		border-radius: 16px;
		backdrop-filter: blur(8px);
		box-shadow: 0 4px 16px rgba(27, 94, 32, 0.05);
		transition: all var(--ag-transition);
	}
	.trust-bar__item:hover {
		transform: translateY(-3px);
		box-shadow: 0 10px 28px rgba(27, 94, 32, 0.1);
		border-color: rgba(129, 199, 132, 0.4);
	}
	.trust-bar__icon {
		width: 44px;
		height: 44px;
		border-radius: 12px;
		background: linear-gradient(145deg, var(--ag-green-400), var(--ag-green-600));
		display: flex;
		align-items: center;
		justify-content: center;
		color: #fff;
		font-size: 20px;
		flex-shrink: 0;
		box-shadow: 0 4px 12px rgba(56, 142, 60, 0.2);
	}
	.trust-bar__icon--2 {
		background: linear-gradient(145deg, #7e57c2, #5e35b1);
		box-shadow: 0 4px 12px rgba(94, 53, 177, 0.2);
	}
	.trust-bar__icon--3 {
		background: linear-gradient(145deg, #26a69a, #00897b);
		box-shadow: 0 4px 12px rgba(0, 137, 123, 0.2);
	}
	.trust-bar__icon--4 {
		background: linear-gradient(145deg, #f57c00, #e65100);
		box-shadow: 0 4px 12px rgba(230, 81, 0, 0.2);
	}
	.trust-bar__text {
		min-width: 0;
	}
	.trust-bar__title {
		font-size: 15px;
		font-weight: 800;
		color: var(--ag-text-900);
		line-height: 1.3;
	}
	.trust-bar__sub {
		font-size: 12px;
		color: var(--ag-text-400);
		margin-top: 3px;
	}

	/* 时令精选 / 信任条 / 页脚：作为一整块贴住视口底部（空白留在购物车等内容上方） */
	.index-page-tail {
		margin-top: auto;
		flex-shrink: 0;
		width: 100%;
	}
	.index-page-tail > .trust-bar:first-child {
		margin-top: 0;
	}

	/* ========== Footer 底部（通栏铺满视口，仅内容区保留与轮播一致的左右内边距） ========== */
	.bottom-preview {
		padding: 12px 0 0;
		width: 100%;
		max-width: none;
		margin-bottom: 0;
		box-sizing: border-box;
		position: relative;
		z-index: 1;
	}
	.footer {
		border-radius: 0;
		border-left: none;
		border-right: none;
		border-top: 1px solid var(--ag-border);
		border-bottom: none;
		/* 底部保持浅绿，避免渐变到纯白导致版权条像「悬在空白上」 */
		background: linear-gradient(180deg, rgba(241, 248, 233, 0.98), rgba(228, 245, 233, 0.99));
		box-shadow: none;
		padding: 36px var(--banner-inline-pad) 0;
		position: relative;
		overflow: hidden;
	}
	/* 页脚顶部装饰渐变条 */
	.footer::before {
		content: "";
		position: absolute;
		top: 0;
		left: 0;
		right: 0;
		height: 3px;
		background: linear-gradient(90deg,
			var(--ag-green-300) 0%,
			var(--ag-green-500) 25%,
			#7e57c2 50%,
			var(--ag-green-500) 75%,
			var(--ag-green-300) 100%
		);
		border-radius: 0;
	}
	/* 右上角 SVG 植物装饰 */
	.footer::after {
		content: "";
		position: absolute;
		top: 0;
		right: 60px;
		width: 160px;
		height: 160px;
		background:
			radial-gradient(ellipse 70% 35% at 50% 50%, rgba(102, 187, 106, 0.09), transparent 70%),
			radial-gradient(ellipse 50% 25% at 50% 80%, rgba(56, 142, 60, 0.07), transparent 70%);
		border-radius: 50% 0 0 0;
		pointer-events: none;
	}
	.footer-decoration {
		position: absolute;
		inset: 0;
		pointer-events: none;
		overflow: hidden;
	}
	.footer-decoration__circle {
		position: absolute;
		border-radius: 999px;
		background: radial-gradient(circle, rgba(102, 187, 106, 0.1), transparent 70%);
	}
	.footer-decoration__circle--1 {
		width: 300px;
		height: 300px;
		top: -150px;
		right: -100px;
	}
	.footer-decoration__circle--2 {
		width: 200px;
		height: 200px;
		bottom: -100px;
		left: -50px;
	}
	.footer-decoration__circle--3 {
		width: 150px;
		height: 150px;
		top: 50%;
		left: 30%;
	}
	/* 叶子散点装饰 */
	.footer-decoration__dot {
		position: absolute;
		width: 8px;
		height: 8px;
		border-radius: 50%;
		background: rgba(102, 187, 106, 0.25);
	}
	.footer-decoration__dot--1 { top: 40px; left: 15%; animation: leafFloat 8s ease-in-out infinite; }
	.footer-decoration__dot--2 { top: 80px; left: 40%; width: 6px; height: 6px; background: rgba(165, 214, 167, 0.3); animation: leafFloat 10s ease-in-out infinite 2s; }
	.footer-decoration__dot--3 { top: 30px; right: 25%; width: 10px; height: 10px; background: rgba(102, 187, 106, 0.2); animation: leafFloat 12s ease-in-out infinite 1s; }
	.footer-decoration__dot--4 { bottom: 40px; right: 20%; width: 7px; height: 7px; background: rgba(129, 199, 132, 0.28); animation: leafFloat 9s ease-in-out infinite 3s; }
	.footer-decoration__dot--5 { bottom: 60px; left: 60%; width: 5px; height: 5px; background: rgba(165, 214, 167, 0.25); animation: leafFloat 11s ease-in-out infinite 1.5s; }
	@keyframes leafFloat {
		0%, 100% { transform: translateY(0) scale(1); opacity: 0.8; }
		50% { transform: translateY(-8px) scale(1.15); opacity: 1; }
	}

	.footer-grid {
		display: grid;
		grid-template-columns: 1.5fr 1fr 1fr 1.2fr;
		gap: 30px;
		position: relative;
		z-index: 1;
	}
	.footer-col--brand {
		padding-right: 20px;
	}
	.footer-brand {
		display: flex;
		align-items: center;
		gap: 12px;
		margin-bottom: 16px;
	}
	.footer-brand__logo {
		width: 40px;
		height: 40px;
	}
	.footer-brand__text {
		font-size: 16px;
		font-weight: 800;
		color: var(--ag-green-800);
	}
	.footer-text {
		color: var(--ag-text-500);
		line-height: 1.8;
		font-size: 13px;
		margin-bottom: 16px;
	}
	.footer-social {
		display: flex;
		gap: 10px;
	}
	.footer-social__item {
		width: 36px;
		height: 36px;
		border-radius: 10px;
		background: var(--ag-green-050);
		border: 1px solid var(--ag-border);
		display: flex;
		align-items: center;
		justify-content: center;
		color: var(--ag-green-700);
		font-size: 16px;
		cursor: pointer;
		transition: all var(--ag-transition);
	}
	.footer-social__item:hover {
		background: var(--ag-green-500);
		color: #fff;
		border-color: var(--ag-green-500);
		transform: translateY(-2px);
	}

	.footer-title {
		display: flex;
		align-items: center;
		gap: 8px;
		font-size: 15px;
		font-weight: 800;
		color: var(--ag-green-800);
		margin-bottom: 16px;
	}
	.footer-title i {
		font-size: 18px;
	}

	.footer-links {
		display: flex;
		flex-direction: column;
		gap: 10px;
	}
	.footer-links a {
		color: var(--ag-text-500);
		text-decoration: none;
		cursor: pointer;
		font-size: 13px;
		display: flex;
		align-items: center;
		gap: 8px;
		transition: all var(--ag-transition);
	}
	.footer-links a:hover {
		color: var(--ag-green-700);
		transform: translateX(4px);
	}
	.footer-links a i {
		color: var(--ag-green-400);
		font-size: 12px;
	}

	.footer-badges {
		display: flex;
		flex-wrap: wrap;
		gap: 10px;
		margin-bottom: 16px;
	}
	.badge {
		display: inline-flex;
		align-items: center;
		gap: 6px;
		padding: 8px 14px;
		border-radius: 999px;
		background: linear-gradient(135deg, var(--ag-green-100), var(--ag-green-050));
		border: 1px solid var(--ag-border);
		color: var(--ag-green-800);
		font-size: 12px;
		font-weight: 700;
	}
	.badge i {
		font-size: 14px;
	}

	.footer-contact {
		display: flex;
		flex-direction: column;
		gap: 8px;
	}
	.footer-contact__item {
		display: flex;
		align-items: center;
		gap: 8px;
		font-size: 12px;
		color: var(--ag-text-500);
	}
	.footer-contact__item i {
		color: var(--ag-green-600);
	}

	.footer-rich {
		margin-top: 24px;
	}
	.footer-rich__card {
		border-radius: 16px;
		background: rgba(255, 255, 255, 0.8);
		border: 1px solid var(--ag-border);
		padding: 16px 20px;
		color: var(--ag-text-500);
	}

	.footer-bottom {
		margin-top: 24px;
		padding: 20px 0 16px;
		border-top: 1px dashed var(--ag-border);
		display: flex;
		align-items: center;
		justify-content: space-between;
		flex-wrap: wrap;
		gap: 12px;
	}
	.footer-bottom__left {
		display: flex;
		align-items: center;
		gap: 8px;
		color: var(--ag-text-400);
		font-size: 12px;
	}
	.footer-bottom__logo {
		display: flex;
		color: var(--ag-green-500);
	}
	.footer-bottom__logo svg {
		width: 14px;
		height: 14px;
	}
	.footer-bottom__right {
		display: flex;
		align-items: center;
		gap: 8px;
		font-size: 12px;
	}
	.footer-bottom__right a {
		color: var(--ag-text-400);
		text-decoration: none;
		transition: color var(--ag-transition);
	}
	.footer-bottom__right a:hover {
		color: var(--ag-green-700);
	}
	.footer-bottom__sep {
		color: var(--ag-border);
	}

	/* ========== 响应式 ========== */
	@media (max-width: 1200px) {
		.mission {
			grid-template-columns: 1fr;
			gap: 20px;
		}
		.mission__left {
			justify-content: flex-start;
		}
		.footer-grid { grid-template-columns: 1fr 1fr; }
		.entry-grid { grid-template-columns: repeat(2, 1fr); }
	}
	@media (max-width: 992px) {
		.entry-grid { grid-template-columns: repeat(2, 1fr); }
		.carousel-title { font-size: 26px; }
		.hero-overlay { left: 24px; }
		.trust-bar__inner {
			grid-template-columns: repeat(2, 1fr);
		}
		.featured-grid {
			grid-template-columns: repeat(2, 1fr);
		}
	}
	@media (max-width: 900px) {
		.mission__left-inner {
			grid-template-columns: 1fr;
			gap: 22px;
		}
		.mission__pillars {
			flex-direction: column;
			gap: 10px;
		}
	}
	@media (max-width: 768px) {
		.body-containers {
			--home-inline-pad: 16px;
			--banner-inline-pad: 16px;
			--site-header-offset: 70px;
		}
		.brand-title { font-size: 16px; }
		.brand-subtitle { display: none; }
		.btn-login, .btn-register { display: none !important; }
		.btn-hamburger { display: inline-flex !important; }
		.menu-preview { display: none; }
		.banner-preview { padding: 12px var(--banner-inline-pad) 0; }
		.hero-overlay { left: 16px; bottom: 16px; }
		.hero-card { max-width: calc(100vw - 60px); }
		.hero-title { font-size: 18px; }
		.footer-grid { grid-template-columns: 1fr; }
		.bottom-preview { padding: 8px 0 0; }
		.footer { padding: 24px var(--banner-inline-pad) 0; }
		.section-hd { justify-content: flex-end; }
		.section-hd__text { text-align: right; }
		.stats-bar__inner { padding: 16px 20px; gap: 0; }
		.stats-bar__item { padding: 8px 0; }
		.stats-bar__num { font-size: 22px; }
		.stats-bar__icon { width: 40px; height: 40px; font-size: 18px; }
		.trust-bar__inner { grid-template-columns: 1fr; }
		.trust-bar__item { padding: 14px 16px; }
		.featured-grid { grid-template-columns: repeat(2, 1fr); }
		.featured-card__img { height: 140px; }
	}
	@media (max-width: 576px) {
		.entry-grid { grid-template-columns: 1fr; }
		.stats { grid-template-columns: 1fr; }
		.mission__points li {
			font-size: 12px;
		}
	}
	@media (prefers-reduced-motion: reduce) {
		* { transition: none !important; }
	}
</style>
