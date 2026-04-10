<template>
	<div class="news-detail-page">
		<div class="news-detail__toolbar">
			<el-button type="default" size="small" class="news-detail__back" icon="el-icon-back" @click="backClick">返回</el-button>
		</div>

		<div class="news-detail__shell">
			<header class="news-detail__header">
				<p class="news-detail__kicker">公告资讯</p>
				<h1 class="news-detail__title">{{ detail.title }}</h1>

				<div class="news-detail__meta-row">
					<div class="news-detail__meta">
						<span class="news-detail__meta-item">
							<i class="el-icon-time"></i>
							发布时间：{{ detail.addtime }}
						</span>
						<span class="news-detail__meta-item">
							<i class="icon iconfont icon-zan10"></i>
							{{ detail.thumbsupnum }}
						</span>
						<span class="news-detail__meta-item">
							<i class="icon iconfont icon-shoucang10"></i>
							{{ detail.storeupnum }}
						</span>
						<span class="news-detail__meta-item">
							<i class="icon iconfont icon-chakan9"></i>
							{{ detail.clicknum }}
						</span>
					</div>
					<div class="news-detail__actions">
						<el-button size="small" :type="zanType ? 'success' : 'default'" :plain="!zanType" @click="zanClick">
							<i class="icon iconfont icon-guanzhu-zhihui"></i>
							{{ zanType ? '已赞' : '点赞' }}
						</el-button>
						<el-button size="small" :type="collectType ? 'success' : 'default'" :plain="!collectType" @click="collectClick">
							<i class="icon iconfont icon-shoucang10"></i>
							{{ collectType ? '已收藏' : '收藏' }}
						</el-button>
					</div>
				</div>
			</header>

			<nav class="news-detail__nav" v-if="allList.length">
				<el-button size="small" icon="el-icon-arrow-left" :disabled="!prevArticle" @click="prepDetailClick">上一篇</el-button>
				<span class="news-detail__nav-hint" v-if="prevArticle" :title="prevArticle.title">{{ prevArticle.title }}</span>
				<span class="news-detail__nav-gap"></span>
				<span class="news-detail__nav-hint news-detail__nav-hint--next" v-if="nextArticle" :title="nextArticle.title">{{ nextArticle.title }}</span>
				<el-button size="small" icon="el-icon-arrow-right" :disabled="!nextArticle" @click="nextDetailClick">下一篇</el-button>
			</nav>

			<article class="news-detail__body content-detail" v-html="detail.content"></article>
		</div>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				id: 0,
				detail: {},
				zanType: false,
				zanForm: {},
				collectType: false,
				collectForm: {},
				baseUrl: '',
				categoryList: [],
				currentIndex: 0,
				allList: []
			}
		},
		computed: {
			prevArticle() {
				if (!this.allList.length || this.currentIndex <= 0) return null
				return this.allList[this.currentIndex - 1]
			},
			nextArticle() {
				if (!this.allList.length || this.currentIndex >= this.allList.length - 1) return null
				return this.allList[this.currentIndex + 1]
			}
		},
		created() {
			this.id = this.$route.query.id
			this.baseUrl = this.$config.baseUrl
			this.getCategoryList()
			this.getDetail()
			this.getNewsList()
		},
		watch: {
			$route() {
				this.id = this.$route.query.id
				this.getDetail()
			}
		},
		methods: {
			backClick() { history.back() },
			getNewsList() {
				this.$http.get('news/list', { params: { page: 1, limit: 100, sort: 'addtime', order: 'desc' } }).then(res => {
					if (res.data.code == 0) {
						for (let x in res.data.data.list) {
							if (res.data.data.list[x].id == this.id) { this.currentIndex = Number(x); break }
						}
						this.allList = res.data.data.list
					}
				})
			},
			prepDetailClick() {
				if (this.currentIndex == 0) { this.$message.error('已经是第一篇了'); return false }
				this.currentIndex--
				this.$router.push({ path: '/index/newsDetail', query: { id: this.allList[this.currentIndex].id } })
			},
			nextDetailClick() {
				if (this.currentIndex == this.allList.length - 1) { this.$message.error('已经是最后一篇了'); return false }
				this.currentIndex++
				this.$router.push({ path: '/index/newsDetail', query: { id: this.allList[this.currentIndex].id } })
			},
			getCategoryList() {
				this.$http.get('newstype/list', {}).then(res => { if (res.data.code == 0) this.categoryList = res.data.data })
			},
			toDetail(id) { this.$router.push({ path: '/index/newsDetail', query: { id } }) },
			getDetail() {
				this.$http.get(`news/detail/${this.id}`, {}).then(res => {
					if (res.data && res.data.code == 0) {
						this.detail = res.data.data
						window.scrollTo(0, 0)
						this.getZan()
						this.getCollect()
					}
				})
			},
			getZan() {
				this.$http.get('storeup/list', { params: { page: 1, limit: 1, type: 21, userid: localStorage.getItem('frontUserid'), tablename: 'news', refid: this.id } }).then(res => {
					if (res.data && res.data.code == 0) {
						if (res.data.data.list.length) { this.zanType = true; this.zanForm = res.data.data.list[0] }
						else { this.zanType = false }
					}
				})
			},
			getCollect() {
				this.$http.get('storeup/list', { params: { page: 1, limit: 1, type: 1, userid: localStorage.getItem('frontUserid'), tablename: 'news', refid: this.id } }).then(res => {
					if (res.data && res.data.code == 0) {
						if (res.data.data.list.length) { this.collectType = true; this.collectForm = res.data.data.list[0] }
						else { this.collectType = false }
					}
				})
			},
			zanClick() {
				if (this.zanType) {
					this.$http.post('storeup/delete', [this.zanForm.id]).then(res => {
						if (res.data && res.data.code == 0) {
							this.$message.success('取消成功')
							this.detail.thumbsupnum--
							this.$http.post('news/update', this.detail).then(() => {})
							this.getZan()
						}
					})
				} else {
					this.$http.post('storeup/add', { name: this.detail.title, picture: this.detail.picture, refid: this.detail.id, type: 21, tablename: 'news', userid: localStorage.getItem('frontUserid') }).then(res => {
						if (res.data && res.data.code == 0) {
							this.$message.success('点赞成功')
							this.detail.thumbsupnum++
							this.$http.post('news/update', this.detail).then(() => {})
							this.getZan()
						}
					})
				}
			},
			collectClick() {
				if (this.collectType) {
					this.$http.post('storeup/delete', [this.collectForm.id]).then(res => {
						if (res.data && res.data.code == 0) {
							this.$message.success('取消成功')
							this.detail.storeupnum--
							this.$http.post('news/update', this.detail).then(() => {})
							this.getCollect()
						}
					})
				} else {
					this.$http.post('storeup/add', { name: this.detail.title, picture: this.detail.picture, refid: this.detail.id, type: 1, tablename: 'news', userid: localStorage.getItem('frontUserid') }).then(res => {
						if (res.data && res.data.code == 0) {
							this.detail.storeupnum++
							this.$http.post('news/update', this.detail).then(() => {})
							this.$message.success('收藏成功')
							this.getCollect()
						}
					})
				}
			}
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
$ag-green-800: #2e7d32;
$ag-green-700: #388e3c;
$ag-green-500: #4caf50;
$ag-border: rgba(46, 125, 50, 0.14);

.news-detail-page {
	width: 100%;
	max-width: none;
	margin: 0;
	padding: 8px 28px 52px;
	box-sizing: border-box;
}

.news-detail__toolbar { margin-bottom: 14px; }

.news-detail__back {
	border: none !important;
	background: linear-gradient(135deg, #4caf50, #2e7d32) !important;
	color: #fff !important;
	border-radius: 10px !important;
	padding: 10px 20px !important;
	font-weight: 600 !important;
	box-shadow: 0 4px 14px rgba(46, 125, 50, 0.28);
	&:hover { color: #fff !important; filter: brightness(1.03); }
}

.news-detail__shell {
	background: #fff;
	border: 1px solid $ag-border;
	border-radius: 20px;
	box-shadow: 0 8px 32px rgba(46, 125, 50, 0.08);
	overflow: hidden;
}

.news-detail__header {
	padding: 40px clamp(28px, 5vw, 72px) 28px;
	background: linear-gradient(180deg, rgba(241, 248, 233, 0.55), #fff);
	border-bottom: 1px solid rgba(46, 125, 50, 0.1);
}

.news-detail__kicker {
	margin: 0 0 12px;
	font-size: 13px;
	font-weight: 700;
	color: $ag-green-700;
	letter-spacing: 0.06em;
}

.news-detail__title {
	margin: 0;
	font-size: clamp(24px, 3.5vw, 38px);
	font-weight: 800;
	color: #1a2e1a;
	line-height: 1.35;
	text-align: center;
}

.news-detail__meta-row {
	display: flex;
	flex-direction: column;
	align-items: stretch;
	gap: 16px;
	margin-top: 26px;
}

.news-detail__meta {
	display: flex;
	flex-wrap: wrap;
	align-items: center;
	justify-content: center;
	gap: 14px 36px;
	font-size: 14px;
	color: #607060;
}

.news-detail__meta-item {
	display: inline-flex;
	align-items: center;
	gap: 6px;
	i { color: rgba($ag-green-500, 0.85); font-size: 14px; }
}

.news-detail__actions {
	display: flex;
	flex-wrap: wrap;
	gap: 12px;
	justify-content: center;
	padding-top: 4px;
}

.news-detail__nav {
	display: flex;
	flex-wrap: wrap;
	align-items: center;
	gap: 10px 12px;
	padding: 18px clamp(20px, 4vw, 56px);
	background: rgba(241, 248, 233, 0.35);
	border-bottom: 1px solid rgba(46, 125, 50, 0.08);

	::v-deep .el-button { border-radius: 8px; font-weight: 600; }
	::v-deep .el-button--default:not(.is-disabled) {
		border-color: rgba(46, 125, 50, 0.35);
		color: $ag-green-800;
		&:hover { border-color: $ag-green-500; color: $ag-green-700; background: rgba(76, 175, 80, 0.08); }
	}
}

.news-detail__nav-hint {
	flex: 1;
	min-width: 120px;
	font-size: 13px;
	color: #607060;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
	&--next { text-align: right; }
}

.news-detail__nav-gap { flex: 1; min-width: 8px; }

.news-detail__body {
	padding: 44px clamp(28px, 5vw, 80px) 52px;
	max-width: none;
	width: 100%;
	margin: 0;
	font-size: 18px;
	line-height: 2;
	color: #2f3a2f;
	word-break: break-word;

	::v-deep img { max-width: 100%; height: auto; border-radius: 10px; }
	::v-deep p { margin: 0 0 1em; }
	::v-deep h1, ::v-deep h2, ::v-deep h3 { color: #1a2e1a; margin: 1.2em 0 0.6em; }
}

@media (max-width: 768px) {
	.news-detail-page { padding: 8px 16px 32px; }
	.news-detail__header { padding: 24px 18px 18px; }
	.news-detail__title { font-size: 22px; }
	.news-detail__body { padding: 24px 18px 32px; font-size: 16px; line-height: 1.9; }
	.news-detail__nav-hint { display: none; }
	.news-detail__nav { padding: 14px 16px; }
}
</style>
