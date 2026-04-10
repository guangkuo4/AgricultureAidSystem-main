const fs = require('fs');
const path = 'src/pages/zhunongtuopin/detail.vue';
let s = fs.readFileSync(path, 'utf8');

// Find the start of <style> tag
const styleStart = s.indexOf('<style');
// Find </style> end
const styleEnd = s.lastIndexOf('</style>');
if (styleStart < 0 || styleEnd < 0) { console.error('no style'); process.exit(1); }

const newStyle = `<style rel="stylesheet/scss" lang="scss" scoped>
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
		width: 88%;
		max-width: 1280px;
		margin: 40px auto;
		padding: 0;
	}

	/* === Hero 区 === */
	.case-detail__hero {
		display: grid;
		grid-template-columns: 1fr 420px;
		gap: 28px;
		margin-bottom: 28px;
		align-items: start;
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
		aspect-ratio: 16 / 9;
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
</style>`;

const before = s.slice(0, styleStart);
const after = s.slice(styleEnd + '</style>'.length);
const result = before + newStyle + '\n' + after;

fs.writeFileSync(path, result);
console.log('Style replaced! File size:', result.length);
