<template>
	<div class="menu-preview">
		<!-- 竖向-2 -->
		<el-scrollbar :wrap-class="isCollapse ? 'scrollbar-wrapper scrollbar-wrapper-close' : 'scrollbar-wrapper scrollbar-wrapper-open'">
		  <el-button :style="verticalStyle2[isCollapse?'close':'open'].btn.default" type="primary" @click="collapse">
		    <span class="icon iconfont"
		      :style="verticalStyle2[isCollapse?'close':'open'].btn.icon.default"
		      :class="verticalStyle2[isCollapse?'close':'open'].btn.icon.text"></span>{{verticalStyle2[isCollapse?'close':'open'].btn.text}}
		  </el-button>
		  <div class="userinfo"
		    :style="verticalStyle2[isCollapse?'close':'open'].userinfo.box.default">
		    <el-image v-if="avatar" :style="verticalStyle2[isCollapse?'close':'open'].userinfo.img.default" :src="avatar?this.$base.url + avatar:require('@/assets/img/avator.svg')" fit="cover"></el-image>
		    <div :style="verticalStyle2[isCollapse?'close':'open'].userinfo.nickname.default">
		      {{this.$storage.get('adminName')}}</div>
		  </div>
		  <el-menu :default-active="activeMenu" :unique-opened="true" :style="verticalStyle2[isCollapse?'close':'open'].menu.box.default"
		    class="el-menu-vertical-2" :collapse-transition="false" :collapse="isCollapse">
		    <el-menu-item class="home" :popper-append-to-body="false" popper-class="home" @click.native="menuHandler('')" :style="verticalStyle2[isCollapse?'close':'open'].home.one.box.default" index="/">
		      <div class="el-tooltip">
		        <i :style="verticalStyle2[isCollapse?'close':'open'].home.one.icon.default"
		          class="icon iconfont icon-shouye-zhihui"></i>
		        <span :style="verticalStyle2[isCollapse?'close':'open'].home.one.title.default"
		          slot="title">{{verticalStyle2.open.home.one.title.text}}</span>
		      </div>
		    </el-menu-item>
		    <el-submenu class="user" popper-class="user" :popper-append-to-body="false"
		      :style="verticalStyle2[isCollapse?'close':'open'].user.one.box.default" index="1">
		      <template slot="title">
		        <i :style="verticalStyle2[isCollapse?'close':'open'].user.one.icon.default"
		          class="icon iconfont icon-kuaijiezhifu"></i>
		        <span :style="verticalStyle2[isCollapse?'close':'open'].user.one.title.default"
		          slot="title">{{verticalStyle2.open.user.one.title.text}}</span>
		      </template>
		      <el-menu-item index="/updatePassword" @click="menuHandler('updatePassword')">修改密码</el-menu-item>
		      <el-menu-item index="/center" @click="menuHandler('center')">个人信息</el-menu-item>
		    </el-submenu>
			<template v-for="(menu,index) in menuList.backMenu">
				<el-submenu v-if="menu.child.length > 1" class="other" popper-class="other" :popper-append-to-body="false" :style="verticalStyle2[isCollapse?'close':'open'].menu.one.box.default" :index="index+2+''">
					<template slot="title">
						<i :style="verticalStyle2[isCollapse?'close':'open'].menu.one.icon.default" class="el-icon-menu" :class="icons[index]"></i>
						<span :style="verticalStyle2[isCollapse?'close':'open'].menu.one.title.default" slot="title">{{menu.menu + (verticalFlag ? '管理' : '')}}</span>
					</template>
					<el-menu-item v-for="(child,sort) in menu.child" :key="sort" :index="'/'+child.tableName" @click="menuHandler(child.tableName)">{{ child.menu }}</el-menu-item>
				</el-submenu>
				<el-menu-item v-if="menu.child.length <= 1" class="other" popper-class="other" :style="verticalStyle2[isCollapse?'close':'open'].menu.one.box.default" @click="menuHandler(menu.child.length === 1 ? menu.child[0].tableName : menu.tableName)" :index="'/'+(menu.child.length === 1 ? menu.child[0].tableName : menu.tableName)">
				  <div class="el-tooltip">
				    <i :style="verticalStyle2[isCollapse?'close':'open'].menu.one.icon.default" class="el-icon-menu" :class="icons[index]"></i>
				    <span :style="verticalStyle2[isCollapse?'close':'open'].menu.one.title.default" slot="title">{{(menu.child.length === 1 ? menu.child[0].menu : menu.menu) + (verticalFlag ? '管理' : '')}}</span>
				  </div>
				</el-menu-item>
			</template>
		  </el-menu>
		</el-scrollbar>


	</div>
</template>

<script>
import menu from '@/utils/menu'
import verticalStyle2 from '@/assets/styles/verticalStyle2'
export default {
	data() {
		return {
			menuList: [],
			dynamicMenuRoutes: [],
			role: '',
			user: null,
			avatar:'',
			icons: [
				'el-icon-s-cooperation',
				'el-icon-s-order',
				'el-icon-s-platform',
				'el-icon-s-fold',
				'el-icon-s-unfold',
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
				'el-icon-s-custom',
				'el-icon-s-opportunity',
				'el-icon-s-data',
				'el-icon-s-check',
				'el-icon-s-grid',
				'el-icon-menu',
				'el-icon-chat-dot-square',
				'el-icon-message',
				'el-icon-postcard',
				'el-icon-position',
				'el-icon-microphone',
				'el-icon-close-notification',
				'el-icon-bangzhu',
				'el-icon-time',
				'el-icon-odometer',
				'el-icon-crop',
				'el-icon-aim',
				'el-icon-switch-button',
				'el-icon-full-screen',
				'el-icon-copy-document',
				'el-icon-mic',
				'el-icon-stopwatch',
			],
			menulistBorderBottom: {},
			verticalFlag: false,
			isCollapse: false,
			verticalStyle2: verticalStyle2,
			verticalIsMultiple: false,
		}
	},
	computed: {
		activeMenu() {
			const route = this.$route
			console.log(route)
			const {
				meta,
				path
			} = route
			// if st path, the sidebar will highlight the path you sete
			if (meta.activeMenu) {
				return meta.activeMenu
			}
			return path
		}
	},
	watch:{
		avatar(){
			this.$forceUpdate()
		},
	},
	mounted() {
		const menus = menu.list()
		if(menus) {
			this.menuList = menus
		} else {
			let params = {
				page: 1,
				limit: 1,
				sort: 'id',
			}
			
			this.$http({
				url: "menu/list",
				method: "get",
				params: params
			}).then(({
				data
			}) => {
				if (data && data.code === 0) {
					this.menuList = JSON.parse(data.data.list[0].menujson);
					this.$storage.set("menus", this.menuList);
				}
			})
		}
		this.role = this.$storage.get('role')
		
		for(let i=0;i<this.menuList.length;i++) {
			if(this.menuList[i].roleName == this.role) {
				this.menuList = this.menuList[i];
				break;
			}
		}
		this.styleChange()
		
		let sessionTable = this.$storage.get("sessionTable")
		this.$http({
			url: sessionTable + '/session',
			method: "get"
		}).then(({
			data
		}) => {
			if (data && data.code === 0) {
				if(sessionTable == 'nonghu') {
					this.avatar = data.data.touxiang
				}
				if(sessionTable == 'yonghu') {
					this.avatar = data.data.touxiang
				}
				if(sessionTable=='users') {
					this.avatar = data.data.image
				}
				this.user = data.data;
			} else {
				let message = this.$message
				message.error(data.msg);
			}
		});
	},
	created(){
		this.icons.sort(()=>{
			return (0.5-Math.random())
		})
	},
	methods: {
		collapse() {
		  this.isCollapse = !this.isCollapse
		  this.$emit('oncollapsechange', this.isCollapse)
		},
		styleChange() {
			this.$nextTick(() => {
								document.querySelectorAll('.el-menu-vertical-demo .el-submenu .el-menu').forEach(el => {
				  el.removeAttribute('style')
				  const icon = {"border":"none","display":"none"}
				  Object.keys(icon).forEach((key) => {
					el.style[key] = icon[key]
				  })
				})
											})
		},
		menuHandler(name) {
			let router = this.$router
			name = '/'+name
			router.push(name)
		},
	}
}
</script>
<style lang="scss" scoped>
	.menu-preview {
	  .el-scrollbar {
	    height: 100%;
	
	    & ::v-deep .scrollbar-wrapper {
	      overflow-x: hidden;
	    }
		
				// 竖向
		.el-menu-vertical-demo {
		  .el-submenu:first-of-type ::v-deep .el-submenu__title .el-submenu__icon-arrow {
		    display: none;
		  }
		}
		
		.el-menu-vertical-demo>.el-menu-item {
				  				  cursor: pointer;
				  				  padding: 0 20px;
				  				  color: #333;
				  				  white-space: nowrap;
				  				  background: #fff;
				  				  position: relative;
				  		}
		
		.el-menu-vertical-demo>.el-menu-item:hover {
						color: #fff;
						background: blue;
					}
		
		.el-menu-vertical-demo .el-submenu ::v-deep .el-submenu__title {
						cursor: pointer;
						padding: 0 20px;
						color: #333;
						white-space: nowrap;
						background: #fff;
						position: relative;
					}
		
		.el-menu-vertical-demo .el-submenu ::v-deep .el-submenu__title:hover {
						color: #fff;
						background: blue;
					}
		
		.el-menu-vertical-demo .el-submenu ::v-deep .el-submenu__title .el-submenu__icon-arrow {
						margin: -7px 0 0 0;
						top: 50%;
						color: inherit;
						vertical-align: middle;
						font-size: 12px;
						position: absolute;
						right: 20px;
					}
		
		.el-menu-vertical-demo .el-submenu {
						padding: 0;
						margin: 0;
						list-style: none;
					}
		
		// .el-menu-vertical-demo .el-submenu ::v-deep .el-menu {
		// 				// 		border: none;
		// 				// 		display: none;
		// 				// }
		
		.el-menu-vertical-demo .el-submenu ::v-deep .el-menu .el-menu-item {
						padding: 0 40px;
						color: #666;
						background: #fff;
						line-height: 50px;
						height: 50px;
					}
		
		.el-menu-vertical-demo .el-submenu ::v-deep .el-menu .el-menu-item:hover {
						padding: 0 40px;
						color: #fff;
						background: red;
						line-height: 50px;
						height: 50px;
					}
		
		.el-menu-vertical-demo .el-submenu ::v-deep .el-menu .el-menu-item.is-active {
						padding: 0 40px;
						color: #fff;
						background: blue;
						line-height: 50px;
						height: 50px;
					}
		// 竖向
			  }
	  	}
	// 竖向 样式二-open
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other {
		font-size: inherit;
		background: none;
	}
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home {
		font-size: inherit;
		background: none;
	}
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other>.el-tooltip {
				cursor: pointer;
				border: 0px solid rgba(255,215,0,.15);
				border-radius: 0px;
				padding: 14px 20px 14px;
				color: rgba(255,255,255,.88);
				white-space: nowrap;
				background: rgba(255,255,255,.03);
				font-size: 13px;
				line-height: 1.2;
				position: relative;
				height: auto;
			}

	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other>.el-tooltip:hover {
				color: #ffd700 !important;
				background: rgba(255,215,0,.08) !important;
			}


	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title {
				cursor: pointer !important;
				border: 0px solid rgba(255,215,0,.15) !important;
				border-radius: 0px !important;
				padding: 14px 20px 14px !important;
				color: rgba(255,255,255,.88) !important;
				white-space: nowrap !important;
				background: rgba(255,255,255,.03) !important;
				font-size: 13px !important;
				line-height: 1.2 !important;
				position: relative !important;
				height: auto !important;
			}
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other.is-active>.el-tooltip {
				color: #ffd700 !important;
				background: rgba(255,215,0,.12) !important;
			}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title:hover {
				color: #ffd700 !important;
				background: rgba(255,215,0,.08) !important;
			}
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other.is-active ::v-deep .el-submenu__title {
				color: #ffd700 !important;
				background: rgba(255,215,0,.12) !important;
			}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title .iconfont {
				margin: 0 8px 0 0;
				color: #ffd700;
				display: inline-block;
				vertical-align: middle;
				width: auto;
				font-size: 18px;
				text-align: left;
			}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title .el-submenu__icon-arrow {
				margin: -7px 0 0 0;
				top: 50%;
				color: rgba(255,255,255,.5);
				vertical-align: middle;
				font-size: 14px;
				position: absolute;
				right: 20px;
			}

	.scrollbar-wrapper-open .el-menu-vertical-2 ::v-deep .el-submenu.other .el-menu {
				border: 0px solid rgba(255,215,0,.15);
				border-radius: 0px;
				margin: 6px auto 0;
				background: none;
				width: 100%;
				font-size: inherit;
			}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu .el-menu-item {
				padding: 0 14px !important;
				margin: 0px auto !important;
				color: rgba(255,255,255,.65) !important;
				font-size: 13px !important;
				border-color: rgba(255,215,0,.15) !important;
				line-height: 42px !important;
				border-radius: 0px !important;
				background: rgba(255,255,255,.04) !important;
				width: 88% !important;
				border-width: 0 0 0px !important;
				border-style: dashed !important;
				text-align: left !important;
				height: 42px !important;
			}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu .el-menu-item:hover {
				padding: 0 14px !important;
				color: #ffd700 !important;
				background: rgba(255,215,0,.08) !important;
				width: 88% !important;
				line-height: 42px !important;
				height: 42px !important;
			}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu .el-menu-item.is-active {
				padding: 0 14px !important;
				color: #ffd700 !important;
				background: rgba(255,215,0,.12) !important;
				width: 88% !important;
				line-height: 42px !important;
				height: 42px !important;
			}

	// 竖向 样式二-close
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.other>.el-tooltip {
				cursor: pointer;
				padding: 0 8px;
				color: rgba(255,255,255,.8);
				white-space: nowrap;
				background: none;
				font-size: inherit;
				position: relative;
			}

	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.other>.el-tooltip:hover {
				color: #ffd700;
				background: rgba(255,215,0,.08);
			}

	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.other.is-active>.el-tooltip {
				color: #ffd700;
				background: rgba(255,215,0,.12);
			}

	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title {
				cursor: pointer !important;
				padding: 0 8px !important;
				color: rgba(255,255,255,.8) !important;
				white-space: nowrap !important;
				background: none !important;
				font-size: inherit !important;
				position: relative !important;
			}

	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title:hover {
				color: #ffd700 !important;
				background: rgba(255,215,0,.08) !important;
			}

	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title .iconfont {
				margin: 0;
				color: #ffd700;
				display: inline-block;
				vertical-align: middle;
				width: 42px;
				font-size: 24px;
				text-align: center;
			}

	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title .el-submenu__icon-arrow {
				margin: -7px 0 0 0;
				top: 50%;
				color: inherit;
				display: none;
				vertical-align: middle;
				font-size: 12px;
				position: absolute;
				right: 20px;
			}

	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other .el-menu {
				border: none;
				border-radius: 0px;
				padding: 0px 0;
				margin: 0 0 0 3px;
				font-size: inherit;
			}

	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other .el-menu--vertical.other .el-menu-item {
				border: 0px solid rgba(255,215,0,.15);
				background-color: rgba(255,255,255,.04);
				padding: 0 0px;
				color: rgba(255,255,255,.65);
				font-size: 13px;
				line-height: 40px;
				text-align: left;
				height: 40px;
			}

	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other .el-menu--vertical.other .el-menu-item:hover {
				border: 0px solid rgba(255,215,0,.3);
				padding: 0 0px;
				color: #ffd700;
				background: rgba(255,215,0,.08) !important;
				line-height: 40px;
				text-align: left;
				height: 40px;
			}

	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other .el-menu--vertical.other .el-menu-item.is-active {
				border: 0px solid rgba(255,215,0,.3);
				padding: 0 0px;
				color: #ffd700;
				background: rgba(255,215,0,.12) !important;
				line-height: 40px;
				text-align: left;
				height: 40px;
			}
	
	// 竖向 样式二-open-首页
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home>.el-tooltip {
				cursor: pointer;
				border: 0px solid rgba(255,215,0,.15);
				border-radius: 0px;
				padding: 14px 20px 14px;
				color: rgba(255,255,255,.88);
				white-space: nowrap;
				background: rgba(255,255,255,.03);
				font-size: 13px;
				line-height: 1.2;
				position: relative;
				height: auto;
			}

	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home>.el-tooltip:hover {
				color: #ffd700;
				background: rgba(255,215,0,.08);
			}

	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home.is-active>.el-tooltip {
				color: #ffd700;
				background: rgba(255,215,0,.12);
			}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title {
				cursor: pointer !important;
				border: 0px solid rgba(255,215,0,.15) !important;
				border-radius: 0px !important;
				padding: 14px 20px 14px !important;
				color: rgba(255,255,255,.88) !important;
				white-space: nowrap !important;
				background: rgba(255,255,255,.03) !important;
				font-size: 13px !important;
				line-height: 1.2 !important;
				position: relative !important;
				height: auto !important;
			}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title:hover {
				color: #ffd700 !important;
				background: rgba(255,215,0,.08) !important;
			}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title .iconfont {
				margin: 0 8px 0 0;
				color: #ffd700;
				display: inline-block;
				vertical-align: middle;
				width: auto;
				font-size: 18px;
				text-align: left;
			}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title .el-submenu__icon-arrow {
				margin: -7px 0 0 0;
				top: 50%;
				color: rgba(255,255,255,.5);
				vertical-align: middle;
				font-size: inherit;
				position: absolute;
				right: 20px;
			}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu {
				border: 0px solid rgba(255,215,0,.15);
				border-radius: 0px;
				margin: 6px auto 0;
				background: none;
				width: 100%;
				font-size: inherit;
			}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu .el-menu-item {
				padding: 0 14px;
				margin: 0px auto;
				color: rgba(255,255,255,.65);
				font-size: 13px;
				border-color: rgba(255,215,0,.15);
				line-height: 42px;
				border-radius: 0px;
				background: rgba(255,255,255,.04);
				width: 88%;
				border-width: 0 0 0px;
				border-style: dashed;
				text-align: left;
				height: 42px;
			}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu .el-menu-item:hover {
				padding: 0 14px;
				color: #ffd700;
				background: rgba(255,215,0,.08);
				width: 88%;
				line-height: 42px;
				height: 42px;
			}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu .el-menu-item.is-active {
				padding: 0 14px;
				color: #ffd700;
				background: rgba(255,215,0,.12);
				width: 88%;
				line-height: 42px;
				height: 42px;
			}

	// 竖向 样式二-close-首页
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.home>.el-tooltip {
				cursor: pointer;
				padding: 0 8px;
				color: rgba(255,255,255,.8);
				white-space: nowrap;
				background: none;
				font-size: inherit;
				position: relative;
			}

	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.home>.el-tooltip:hover {
				color: #ffd700;
				background: rgba(255,215,0,.08);
			}

	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.home.is-active>.el-tooltip {
				color: #ffd700;
				background: rgba(255,215,0,.12);
			}

	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title {
				cursor: pointer;
				padding: 0 8px;
				color: rgba(255,255,255,.8);
				white-space: nowrap;
				background: none;
				font-size: inherit;
				position: relative;
			}

	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title:hover {
				color: #ffd700;
				background: rgba(255,215,0,.08);
			}

	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title .iconfont {
				margin: 0;
				color: #ffd700;
				display: inline-block;
				vertical-align: middle;
				width: 42px;
				font-size: 24px;
				text-align: center;
			}

	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title .el-submenu__icon-arrow {
				margin: -7px 0 0 0;
				top: 50%;
				color: inherit;
				display: none;
				vertical-align: middle;
				font-size: 12px;
				position: absolute;
				right: 20px;
			}

	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home .el-menu {
				border: none;
				border-radius: 0px;
				padding: 0px 0;
				margin: 0 0 0 3px;
				font-size: inherit;
			}

	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home .el-menu--vertical.home .el-menu-item {
				border: 0px solid rgba(255,215,0,.15);
				background-color: rgba(255,255,255,.04);
				padding: 0 0px;
				color: rgba(255,255,255,.65);
				font-size: 13px;
				line-height: 40px;
				text-align: left;
				height: 40px;
			}

	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home .el-menu--vertical.home .el-menu-item:hover {
				border: 0px solid rgba(255,215,0,.3);
				padding: 0 0px;
				color: #ffd700;
				background: rgba(255,215,0,.08) !important;
				line-height: 40px;
				text-align: left;
				height: 40px;
			}

	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home .el-menu--vertical.home .el-menu-item.is-active {
				border: 0px solid rgba(255,215,0,.3);
				padding: 0 0px;
				color: #ffd700;
				background: rgba(255,215,0,.12) !important;
				line-height: 40px;
				text-align: left;
				height: 40px;
			}
	
	// 竖向 样式二-open-个人中心
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.user>.el-tooltip {
				cursor: pointer;
				border: 0px solid rgba(255,215,0,.15);
				border-radius: 0px;
				padding: 14px 20px 14px;
				color: rgba(255,255,255,.88);
				white-space: nowrap;
				background: rgba(255,255,255,.03);
				font-size: 13px;
				line-height: 1.2;
				position: relative;
				height: auto;
			}

	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.user>.el-tooltip:hover {
				color: #ffd700;
				background: rgba(255,215,0,.08);
			}

	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.user.is-active>.el-tooltip {
				color: #ffd700;
				background: rgba(255,215,0,.12);
			}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title {
				cursor: pointer !important;
				border: 0px solid rgba(255,215,0,.15) !important;
				border-radius: 0px !important;
				padding: 14px 20px 14px !important;
				color: rgba(255,255,255,.88) !important;
				white-space: nowrap !important;
				background: rgba(255,255,255,.03) !important;
				font-size: 13px !important;
				line-height: 1.2 !important;
				position: relative !important;
				height: auto !important;
			}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title:hover {
				color: #ffd700 !important;
				background: rgba(255,215,0,.08) !important;
			}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title .iconfont {
				margin: 0 8px 0 0;
				color: #ffd700;
				display: inline-block;
				vertical-align: middle;
				width: auto;
				font-size: 18px;
				text-align: left;
			}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title .el-submenu__icon-arrow {
				margin: -7px 0 0 0;
				top: 50%;
				color: rgba(255,255,255,.5);
				vertical-align: middle;
				font-size: inherit;
				position: absolute;
				right: 20px;
			}

	.scrollbar-wrapper-open .el-menu-vertical-2 ::v-deep .el-submenu.user .el-menu {
				border: 0px solid rgba(255,215,0,.15);
				border-radius: 0px;
				margin: 6px auto 0;
				background: none;
				width: 100%;
				font-size: inherit;
			}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu .el-menu-item {
				padding: 0 14px !important;
				margin: 0px auto !important;
				color: rgba(255,255,255,.65) !important;
				font-size: 13px !important;
				border-color: rgba(255,215,0,.15) !important;
				line-height: 42px !important;
				border-radius: 0px !important;
				background: rgba(255,255,255,.04) !important;
				width: 88% !important;
				border-width: 0 0 0px !important;
				border-style: dashed !important;
				text-align: left !important;
				height: 42px !important;
			}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu .el-menu-item:hover {
				padding: 0 14px !important;
				color: #ffd700 !important;
				background: rgba(255,215,0,.08) !important;
				width: 88% !important;
				line-height: 42px !important;
				height: 42px !important;
			}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu .el-menu-item.is-active {
				padding: 0 14px !important;
				color: #ffd700 !important;
				background: rgba(255,215,0,.12) !important;
				width: 88% !important;
				line-height: 42px !important;
				height: 42px !important;
			}

	// 竖向 样式二-close-个人中心
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.user>.el-tooltip {
				cursor: pointer;
				padding: 0 8px;
				color: rgba(255,255,255,.8);
				white-space: nowrap;
				background: none;
				font-size: inherit;
				position: relative;
			}

	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.user>.el-tooltip:hover {
				color: #ffd700;
				background: rgba(255,215,0,.08);
			}

	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.user.is-active>.el-tooltip {
				color: #ffd700;
				background: rgba(255,215,0,.12);
			}

	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title {
				cursor: pointer !important;
				padding: 0 8px !important;
				color: rgba(255,255,255,.8) !important;
				white-space: nowrap !important;
				background: none !important;
				font-size: inherit !important;
				position: relative !important;
			}

	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title:hover {
				color: #ffd700 !important;
				background: rgba(255,215,0,.08) !important;
			}

	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title .iconfont {
				margin: 0;
				color: #ffd700;
				display: inline-block;
				vertical-align: middle;
				width: 42px;
				font-size: 24px;
				text-align: center;
			}

	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title .el-submenu__icon-arrow {
				margin: -7px 0 0 0;
				top: 50%;
				color: inherit;
				display: none;
				vertical-align: middle;
				font-size: 12px;
				position: absolute;
				right: 20px;
			}

	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user .el-menu {
				border: none;
				border-radius: 0px;
				padding: 0px 0;
				margin: 0 0 0 3px;
				font-size: inherit;
			}

	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user .el-menu--vertical.user .el-menu-item {
				border: 0px solid rgba(255,215,0,.15);
				background-color: rgba(255,255,255,.04);
				padding: 0 0px;
				color: rgba(255,255,255,.65);
				font-size: 13px;
				line-height: 40px;
				text-align: left;
				height: 40px;
			}

	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user .el-menu--vertical.user .el-menu-item:hover {
				border: 0px solid rgba(255,215,0,.3);
				padding: 0 0px;
				color: #ffd700;
				background: rgba(255,215,0,.08) !important;
				line-height: 40px;
				text-align: left;
				height: 40px;
			}

	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user .el-menu--vertical.user .el-menu-item.is-active {
				border: 0px solid rgba(255,215,0,.3);
				padding: 0 0px;
				color: #ffd700;
				background: rgba(255,215,0,.12) !important;
				line-height: 40px;
				text-align: left;
				height: 40px;
			}
</style>
