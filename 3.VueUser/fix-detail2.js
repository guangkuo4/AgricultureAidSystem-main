const fs = require('fs');
const path = 'src/pages/zhunongtuopin/detail.vue';
let s = fs.readFileSync(path, 'utf8');

// Find <template> start and </template> end
const tplStart = s.indexOf('<template>');
const tplEnd = s.indexOf('</template>');
if (tplStart < 0 || tplEnd < 0) { console.error('no template'); process.exit(1); }

const newTemplate = `<template>
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
		<section class="case-detail__hero">
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

			<aside class="case-detail__meta">
				<p class="case-detail__kicker">助农案例</p>
				<h1 class="case-detail__title">{{ detail.wenzhangbiaoti }}</h1>
				<div class="case-detail__actions">
					<el-button v-show="!isStoreup" type="primary" size="small" round @click="storeup(1)">
						<i class="el-icon-star-off"></i> 收藏 ({{ detail.storeupnum }})
					</el-button>
					<el-button v-show="isStoreup" type="success" size="small" round plain @click="storeup(-1)">
						<i class="el-icon-star-on"></i> 已收藏 ({{ detail.storeupnum }})
					</el-button>
				</div>
				<dl class="case-detail__dl">
					<div class="case-detail__row"><dt>文章编号</dt><dd>{{ detail.wenzhangbianhao }}</dd></div>
					<div class="case-detail__row"><dt>文章分类</dt><dd>{{ detail.wenzhangfenlei }}</dd></div>
					<div class="case-detail__row"><dt>发布时间</dt><dd>{{ detail.fabushijian }}</dd></div>
					<div class="case-detail__row case-detail__row--block"><dt>文章简介</dt><dd>{{ detail.wenzhangjianjie }}</dd></div>
				</dl>
				<div class="case-detail__admin" v-if="btnAuth('zhunongtuopin','修改') || btnAuth('zhunongtuopin','删除')">
					<el-button v-if="btnAuth('zhunongtuopin','修改')" type="primary" size="small" round @click="editClick">修改</el-button>
					<el-button v-if="btnAuth('zhunongtuopin','删除')" type="danger" size="small" round plain @click="delClick">删除</el-button>
				</div>
			</aside>
		</section>

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
</template>`;

const before = s.slice(0, tplStart + '<template>'.length);
const after = s.slice(tplEnd);
const result = before + '\n' + newTemplate.trim() + '\n' + after;

fs.writeFileSync(path, result);
console.log('Done! Template replaced.');
