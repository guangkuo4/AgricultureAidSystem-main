<template>
<div class="center-sub-page">
    <div class="center-sub-page__toolbar">
      <el-button type="default" size="small" icon="el-icon-back" class="center-sub-page__back" @click="backClick">返回</el-button>
    </div>
    <h2 v-if="storeupType==1" class="section-title">我的收藏</h2>
    <el-form :inline="true" :model="formSearch" class="formSearch center-sub-page__search">
      <el-form-item>
        <el-input v-model="formSearch.name" placeholder="名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getStoreupList(1)">查询</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="20">
      <el-col :span="6" v-for="(item, index) in storeupList" :key="index" @click.native="toDetail(item)">
        <el-card :body-style="{ padding: '0px', cursor: 'pointer' }">
          <el-image v-if="item.picture && item.picture.substr(0,4)=='http'" :src="item.picture" fit="fill" class="image"></el-image>
          <el-image v-else :src="baseUrl + item.picture" fit="fill" class="image"></el-image>
          <div style="padding: 14px;">
            <span v-if="item.remark">{{item.name}}</span>
            <span v-if="!item.remark">{{item.name}}</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
	
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
      @current-change="curChange"
      @prev-click="prevClick"
      @next-click="nextClick"
    ></el-pagination>
	
</div>
</template>

<script>
  import config from '@/config/config'
  export default {
    data() {
      return {
		  layouts: '',
        baseUrl: config.baseUrl,
        formSearch: {
          name: ''
        },
        storeupType: 1,
        storeupList: [],
        total: 1,
        pageSize: 8,
		pageSizes: [10,20,30,50],
        totalPage: 1
      }
    },
    created() {
      this.storeupType = localStorage.getItem('storeupType');
      this.getStoreupList(1);
    },
    methods: {
      backClick() {
          this.$router.push('/index/center')
      },
      getStoreupList(page) {
        let params = {page, limit: this.pageSize, type: this.storeupType, userid: localStorage.getItem('frontUserid'),sort:"addtime",order:"desc"};
        let searchWhere = {
		};
        if (this.formSearch.name != '') searchWhere.name = '%' + this.formSearch.name + '%';
        this.$http.get('storeup/list', {params: Object.assign(params, searchWhere)}).then(res => {
          if (res.data.code == 0) {
            this.storeupList = res.data.data.list;
            this.total = res.data.data.total;
            this.pageSize = res.data.data.pageSize;this.pageSizes = [this.pageSize, this.pageSize*2, this.pageSize*3, this.pageSize*5];
            this.totalPage = res.data.data.totalPage;
          }
        });
      },
      curChange(page) {
        this.getStoreupList(page);
      },
      prevClick(page) {
        this.getStoreupList(page);
      },
      nextClick(page) {
        this.getStoreupList(page);
      },
      toDetail(item) {
		this.$router.push({path: `/index/${item.tablename}Detail`, query: {id:item.refid}});
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  /* ========== 个人中心子页：与全站农业绿统一 ========== */
  .center-sub-page {
    width: 100%;
    margin: 0 auto;
    padding: 4px 20px 28px;
    box-sizing: border-box;
    position: relative;
    background: transparent;
  }

  .center-sub-page__toolbar {
    margin-bottom: 8px;
  }

  .center-sub-page__back {
    border: none !important;
    background: linear-gradient(135deg, #4caf50, #2e7d32) !important;
    color: #fff !important;
    border-radius: 10px !important;
    padding: 10px 18px !important;
    font-weight: 600 !important;
    width: auto !important;
    box-shadow: 0 4px 14px rgba(46, 125, 50, 0.28);
    transition: transform 0.2s ease, box-shadow 0.2s ease;

    &:hover {
      color: #fff !important;
      transform: translateY(-1px);
      box-shadow: 0 6px 18px rgba(46, 125, 50, 0.38);
    }
  }

  .center-sub-page__search {
    margin: 8px 0 20px;
    display: flex;
    justify-content: flex-end;
    flex-wrap: wrap;
    gap: 8px;
  }

  /* ========== 收藏列表页样式 ========== */
  .list-preview {
    padding: 30px 0;
  }

  /* 页面标题 */
  .section-title {
    text-align: center;
    font-size: 26px;
    font-weight: 800;
    color: #1a2e1a;
    margin: 20px 0 30px;
    padding: 20px;
    background: linear-gradient(135deg, rgba(241, 248, 233, 0.8), rgba(255, 255, 255, 0.9));
    border-radius: 20px;
    border: 1px solid rgba(46, 125, 50, 0.1);
    box-shadow: 0 8px 32px rgba(46, 125, 50, 0.08);
  }

  /* 搜索表单 */
  .formSearch {
    margin: 8px 0 20px;
    display: flex;
    justify-content: flex-end;
    flex-wrap: wrap;
    gap: 8px;

    ::v-deep .el-form-item {
      margin-bottom: 0;
    }

    ::v-deep .el-input {
      width: 220px;
    }

    ::v-deep .el-input__inner {
      border: 1px solid rgba(46, 125, 50, 0.15) !important;
      border-radius: 12px !important;
      padding: 0 16px;
      height: 40px;
      line-height: 40px;
      background: #fafcf9;
      transition: all 0.25s ease;

      &:focus {
        border-color: #4caf50 !important;
        background: #fff;
        box-shadow: 0 0 0 4px rgba(102, 187, 106, 0.12) !important;
      }
    }
  }

  /* 卡片区域：弹性铺满 */
  ::v-deep .el-row {
    display: flex;
    flex-wrap: wrap;
  }

  ::v-deep .el-col {
    max-width: 320px;
    flex: 1 1 260px;
  }

  /* 图片 */
  .image {
    height: 180px;
    width: 100%;
    display: block;
    object-fit: cover;
    transition: transform 0.5s ease;
  }

  /* 卡片 */
  ::v-deep .el-card {
    border-radius: 20px !important;
    border: 1px solid rgba(46, 125, 50, 0.08) !important;
    background: linear-gradient(180deg, #fff, rgba(255, 255, 255, 0.95)) !important;
    box-shadow: 0 8px 32px rgba(46, 125, 50, 0.08) !important;
    overflow: hidden;
    cursor: pointer;
    transition: all 0.35s cubic-bezier(0.175, 0.885, 0.32, 1.275) !important;

    &:hover {
      transform: translateY(-10px) !important;
      box-shadow: 0 16px 48px rgba(46, 125, 50, 0.16) !important;
      border-color: rgba(46, 125, 50, 0.2) !important;
    }

    .el-card__body > div:not(.image) {
      font-size: 15px;
      font-weight: 600;
      color: #1a2e1a;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      text-align: center;
      padding: 8px 0;
    }
  }

  /* 分页样式 */
  #pagination.pagination {
    margin: 24px auto 0;
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
  }

  #pagination.el-pagination ::v-deep {
    .btn-prev,
    .btn-next {
      border: 1px solid rgba(46, 125, 50, 0.12) !important;
      border-radius: 12px !important;
      padding: 0 18px !important;
      margin: 0 6px;
      color: #607060;
      background: #fff;
      font-size: 14px;
      font-weight: 600;
      height: 40px;
      line-height: 40px;
      transition: all 0.25s ease;

      &:hover:not(:disabled) {
        color: #388e3c !important;
        border-color: #4caf50 !important;
        background: rgba(46, 125, 50, 0.06) !important;
        transform: translateY(-2px);
        box-shadow: 0 6px 20px rgba(46, 125, 50, 0.15);
      }
    }

    .el-pager .number {
      border: 1px solid rgba(46, 125, 50, 0.12) !important;
      border-radius: 12px !important;
      padding: 0 14px !important;
      margin: 0 4px;
      color: #607060;
      font-size: 14px;
      font-weight: 600;
      background: #fff;
      height: 40px;
      line-height: 40px;
      transition: all 0.25s ease;

      &:hover {
        color: #388e3c !important;
        border-color: #4caf50 !important;
        background: rgba(46, 125, 50, 0.06) !important;
        transform: translateY(-2px);
      }

      &.active {
        color: #fff !important;
        background: linear-gradient(135deg, #4caf50, #2e7d32) !important;
        border-color: transparent !important;
        box-shadow: 0 6px 20px rgba(46, 125, 50, 0.35);
        transform: translateY(-2px);
      }
    }
  }
</style>
