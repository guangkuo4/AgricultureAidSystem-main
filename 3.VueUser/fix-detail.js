const fs = require('fs');
const path = 'src/pages/zhunongtuopin/detail.vue';
let s = fs.readFileSync(path, 'utf8');

// Find the swiper3 div start
const old1 = '<div class="swiper3" v-if="detailBanner.length"';
const idx1 = s.indexOf(old1);
console.log('swiper3 start:', idx1);

// Find el-tabs start (after swiper3)
const old2 = '<el-tabs class="detail';
const idx2 = s.indexOf(old2, idx1);
console.log('el-tabs start:', idx2);

if (idx1 < 0 || idx2 < 0) {
  console.error('Markers not found!');
  process.exit(1);
}

// Hero section template
const hero = `<section class="case-detail__hero">
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
		</section>`;

// Replace swiper3 div through the closing </div> of attr with hero section
// We need to find where the attr div closes (the </div> right before el-tabs)
const before = s.slice(0, idx1);
const after = s.slice(idx2);
const result = before + hero + '\n\t\t' + after;

fs.writeFileSync(path, result);
console.log('Done! Replaced', idx2 - idx1, 'chars');
