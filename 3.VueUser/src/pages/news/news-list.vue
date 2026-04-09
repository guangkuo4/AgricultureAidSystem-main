<template>
<div>
  <!-- 面包屑导航 -->
  <div :style='{"width":"100%","padding":"30px 10%","margin":"0 auto","borderRadius":"8px","background":"linear-gradient(180deg, rgba(80, 160, 80, 0.9), rgba(60, 140, 60, 1))","boxShadow":"0 4px 12px rgba(0, 0, 0, 0.15)","border":"1px solid rgba(255, 255, 255, 0.2)"}' class="breadcrumb-preview">
    <el-breadcrumb :separator="'>'" :style='{"fontSize":"16px","lineHeight":"1","color":"#fff"}'>
      <el-breadcrumb-item class="item1" to="/"><a style="color: #fff;">首页</a></el-breadcrumb-item>
      <el-breadcrumb-item class="item2" v-for="(item, index) in breadcrumbItem" :key="index"><span style="color: #fff;">{{item.name}}</span></el-breadcrumb-item>
    </el-breadcrumb>
  </div>
  
  <div class="news-preview-pv" :style='{"width":"80%","margin":"40px auto","position":"relative","background":"transparent"}'>
    
    <!-- 搜索区域 -->
    <div class="search-section" :style='{"padding":"0 0 30px 0"}'>
      <el-form :inline="true" :model="formSearch" class="list-form-pv" :style='{"display":"flex","alignItems":"center","justifyContent":"center","flexWrap":"wrap","background":"#fff","padding":"25px 40px","borderRadius":"16px","border":"none","boxShadow":"0 4px 20px rgba(46, 125, 50, 0.08)"}'>
        <el-form-item :style='{"margin":"0 15px 0 0"}'>
          <el-input v-model="title" placeholder="搜索您感兴趣的资讯..." :style='{"width":"320px"}' prefix-icon="el-icon-search"></el-input>
        </el-form-item>
        <el-button type="primary" @click="getNewsList(1)" :style='{"border":"0","cursor":"pointer","padding":"0 35px","margin":"0","outline":"none","color":"#fff","borderRadius":"10px","background":"linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%)","width":"auto","fontSize":"15px","fontWeight":"600","lineHeight":"46px","height":"46px","boxShadow":"0 6px 20px rgba(46, 125, 50, 0.35)"}'>
          <i class="el-icon-search" style="margin-right: 8px;"></i>
          搜索
        </el-button>
      </el-form>
    </div>
    
    <!-- 分类标签 -->
    <div class="category" :style='{"width":"100%","padding":"0 0 35px 0","background":"none","display":"flex","justifyContent":"center","flexWrap":"wrap","height":"auto","gap":"10px"}'>
      <div class="item" @click="categoryClick(0)" :class="categoryIndex == 0 ? 'active' : ''" :style='{"cursor":"pointer","borderRadius":"24px","padding":"10px 28px","margin":"0","color":"#555","background":"#fff","border":"1px solid #e8e8e8","fontSize":"14px","fontWeight":"500","transition":"all 0.3s ease"}'>全部资讯</div>
      <div v-for="(item,index) in categoryList" @click="categoryClick(index+1)" :key="index" class="item" :class="categoryIndex == index+1 ? 'active' : ''" :style='{"cursor":"pointer","borderRadius":"24px","padding":"10px 28px","margin":"0","color":"#555","background":"#fff","border":"1px solid #e8e8e8","fontSize":"14px","fontWeight":"500","transition":"all 0.3s ease"}'>{{item.typename}}</div>
    </div>
    
    <!-- 主要新闻列表 -->
    <div v-if="newsList.length" :style='{"width":"100%","padding":"0","background":"none","height":"auto"}' class="list8 index-pv1">
      <!-- 头条新闻 -->
      <div v-if="newsList[0]" :style='{"width":"100%","marginBottom":"35px"}'>
        <div :style='{"width":"100%","display":"flex","height":"auto","background":"#fff","borderRadius":"20px","overflow":"hidden","boxShadow":"0 8px 30px rgba(46, 125, 50, 0.1)","border":"none"}' @click="toNewsDetail(newsList[0])">
          <div :style='{"width":"52%","position":"relative","height":"460px","overflow":"hidden"}'>
            <img :style='{"width":"100%","objectFit":"cover","display":"block","height":"100%","transition":"transform 0.6s ease"}' :src="baseUrl + newsList[0].picture" @mouseover="e => e.target.style.transform='scale(1.08)'" @mouseout="e => e.target.style.transform='scale(1)'">
            <div :style='{"position":"absolute","top":"25px","left":"25px","background":"linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%)","color":"#fff","padding":"10px 24px","borderRadius":"24px","fontSize":"14px","fontWeight":"600","boxShadow":"0 6px 20px rgba(46, 125, 50, 0.35)"}'>
              <i class="el-icon-star-on" style="margin-right: 6px;"></i>头条推荐
            </div>
            <div :style='{"position":"absolute","bottom":"0","left":"0","right":"0","height":"50%","background":"linear-gradient(180deg, transparent 0%, rgba(0,0,0,0.6) 100%)"}'></div>
          </div>
          <div :style='{"width":"48%","padding":"50px 45px","display":"flex","flexDirection":"column","justifyContent":"center","background":"linear-gradient(180deg, rgba(46, 125, 50, 0.02) 0%, rgba(46, 125, 50, 0.06) 100%)"}'>
            <div :style='{"color":"#2E7D32","fontSize":"14px","fontWeight":"600","marginBottom":"20px","display":"flex","alignItems":"center"}'>
              <i class="el-icon-date" style="margin-right: 10px;"></i>
              {{newsList[0].addtime}}
            </div>
            <div :style='{"color":"#1a1a1a","fontSize":"30px","fontWeight":"bold","marginBottom":"24px","lineHeight":"1.35"}'>{{newsList[0].title}}</div>
            <div :style='{"color":"#666","fontSize":"15px","lineHeight":"1.8","marginBottom":"30px","display":"-webkit-box","-webkit-box-orient":"vertical","-webkit-line-clamp":"3","overflow":"hidden"}'>{{newsList[0].introduction}}</div>
            <div :style='{"marginTop":"auto"}'>
              <div :style='{"display":"inline-flex","alignItems":"center","color":"#2E7D32","fontSize":"16px","fontWeight":"600","padding":"12px 28px","background":"rgba(46, 125, 50, 0.08)","borderRadius":"30px","transition":"all 0.3s ease"}' @mouseover="e => {e.currentTarget.style.background='rgba(46, 125, 50, 0.15)';e.currentTarget.style.transform='translateX(5px)'}" @mouseout="e => {e.currentTarget.style.background='rgba(46, 125, 50, 0.08)';e.currentTarget.style.transform='translateX(0)'}">
                阅读全文
                <i class="el-icon-arrow-right" style="margin-left: 10px;"></i>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 新闻卡片列表 -->
      <div v-if="newsList.length > 1" :style='{"display":"grid","gridTemplateColumns":"repeat(3, 1fr)","gap":"25px","marginBottom":"40px"}'>
        <template v-for="(item,index) in newsList">
          <div v-if="index > 0 && index < 7" :style='{"background":"#fff","borderRadius":"16px","overflow":"hidden","boxShadow":"0 4px 15px rgba(0,0,0,0.06)","border":"none","transition":"all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275)","cursor":"pointer"}' @click="toNewsDetail(item)" @mouseover="e => {e.currentTarget.style.transform='translateY(-8px)';e.currentTarget.style.boxShadow='0 12px 30px rgba(46, 125, 50, 0.18)'}" @mouseout="e => {e.currentTarget.style.transform='translateY(0)';e.currentTarget.style.boxShadow='0 4px 15px rgba(0,0,0,0.06)'}">
            <div :style='{"width":"100%","height":"200px","overflow":"hidden","position":"relative"}'>
              <img :style='{"width":"100%","objectFit":"cover","display":"block","height":"100%","transition":"transform 0.6s ease"}' :src="baseUrl + item.picture" @mouseover="e => e.target.style.transform='scale(1.12)'" @mouseout="e => e.target.style.transform='scale(1)'">
              <div :style='{"position":"absolute","top":"15px","left":"15px","background":"linear-gradient(135deg, rgba(46, 125, 50, 0.9) 0%, rgba(27, 94, 32, 0.9) 100%)","color":"#fff","padding":"6px 16px","borderRadius":"20px","fontSize":"12px","fontWeight":"600","backdropFilter":"blur(10px)"}'>{{item.typename || '最新资讯'}}</div>
            </div>
            <div :style='{"padding":"24px"}'>
              <div :style='{"fontSize":"17px","fontWeight":"600","color":"#1a1a1a","lineHeight":"1.45","marginBottom":"12px","display":"-webkit-box","-webkit-box-orient":"vertical","-webkit-line-clamp":"2","overflow":"hidden"}'>{{item.title}}</div>
              <div :style='{"color":"#999","fontSize":"14px","lineHeight":"1.7","display":"-webkit-box","-webkit-box-orient":"vertical","-webkit-line-clamp":"2","overflow":"hidden","marginBottom":"18px"}'>{{item.introduction}}</div>
              <div :style='{"display":"flex","alignItems":"center","justifyContent":"space-between","paddingTop":"18px","borderTop":"1px solid #f0f0f0"}'>
                <span :style='{"color":"#bbb","fontSize":"13px","display":"flex","alignItems":"center"}'>
                  <i class="el-icon-time" style="margin-right: 5px;"></i>
                  {{item.addtime.split(" ")[0]}}
                </span>
                <span :style='{"color":"#2E7D32","fontSize":"14px","fontWeight":"500","display":"flex","alignItems":"center"}'>
                  查看详情
                  <i class="el-icon-arrow-right" style="margin-left: 5px;"></i>
                </span>
              </div>
            </div>
          </div>
        </template>
      </div>
      
      <!-- 更多新闻列表 -->
      <div v-if="newsList.length > 6" :style='{"width":"100%","marginTop":"20px","background":"#fff","borderRadius":"20px","padding":"30px","boxShadow":"0 4px 20px rgba(46, 125, 50, 0.06)"}'>
        <div :style='{"fontSize":"22px","fontWeight":"bold","color":"#1a1a1a","marginBottom":"25px","display":"flex","alignItems":"center"}'>
          <span :style='{"width":"5px","height":"24px","background":"linear-gradient(180deg, #2E7D32 0%, #1B5E20 100%)","borderRadius":"3px","marginRight":"15px"}'></span>
          更多资讯
        </div>
        <template v-for="(item,index) in newsList">
          <div v-if="index > 5" @click="toNewsDetail(item)" :style='{"padding":"22px 25px","background":"#f8faf8","borderRadius":"12px","marginBottom":"12px","display":"flex","alignItems":"center","justifyContent":"space-between","border":"none","transition":"all 0.3s ease","cursor":"pointer"}' @mouseover="e => {e.currentTarget.style.background='rgba(46, 125, 50, 0.06)';e.currentTarget.style.transform='translateX(8px)'}" @mouseout="e => {e.currentTarget.style.background='#f8faf8';e.currentTarget.style.transform='translateX(0)'}">
            <div :style='{"display":"flex","alignItems":"center","flex":"1"}'>
              <span :style='{"width":"46px","height":"46px","background":"linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%)","borderRadius":"12px","display":"flex","alignItems":"center","justifyContent":"center","color":"#fff","fontWeight":"bold","fontSize":"18px","marginRight":"20px","boxShadow":"0 4px 12px rgba(46, 125, 50, 0.25)"}'>
                {{index + 1}}
              </span>
              <div>
                <div :style='{"fontSize":"16px","fontWeight":"600","color":"#1a1a1a","marginBottom":"6px"}'>{{item.title}}</div>
                <div :style='{"fontSize":"13px","color":"#999"}'>{{item.typename || '资讯'}}</div>
              </div>
            </div>
            <div :style='{"display":"flex","alignItems":"center"}'>
              <span :style='{"color":"#bbb","fontSize":"14px","marginRight":"20px"}'>{{item.addtime.split(" ")[0]}}</span>
              <i class="el-icon-arrow-right" :style='{"color":"#2E7D32","fontSize":"18px"}'></i>
            </div>
          </div>
        </template>
      </div>
    </div>
    
    <!-- 空状态 -->
    <div v-else :style='{"textAlign":"center","padding":"100px 30px","background":"#fff","borderRadius":"20px","boxShadow":"0 4px 20px rgba(46, 125, 50, 0.06)"}'>
      <i class="el-icon-document" :style='{"fontSize":"120px","color":"#e0e0e0","marginBottom":"25px"}'></i>
      <div :style='{"fontSize":"18px","color":"#999","marginBottom":"25px"}'>暂无资讯内容</div>
      <el-button @click="getNewsList(1)" :style='{"border":"0","padding":"0 35px","borderRadius":"10px","background":"linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%)","color":"#fff","fontSize":"15px","fontWeight":"600","lineHeight":"46px","height":"46px","boxShadow":"0 6px 20px rgba(46, 125, 50, 0.35)"}'>
        <i class="el-icon-refresh" style="margin-right: 8px;"></i>刷新
      </el-button>
    </div>
            
    <!-- 分页 -->
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
      :style='{"padding":"0","margin":"50px auto 30px","whiteSpace":"nowrap","color":"#333","textAlign":"center","width":"100%","fontWeight":"500"}'
      @current-change="curChange"
      @prev-click="prevClick"
      @next-click="nextClick"
    ></el-pagination>

    <!-- 热门信息 -->
    <div class="hot" :style='{"width":"100%","boxShadow":"none","padding":"40px 0","background":"transparent","marginTop":"20px"}'>
      <div :style='{"width":"100%","lineHeight":"44px","fontSize":"24px","fontWeight":"bold","color":"#1a1a1a","textAlign":"left","background":"none","marginBottom":"28px","display":"flex","alignItems":"center"}'>
        <i class="el-icon-hot-water" :style='{"color":"#f56c6c","fontSize":"26px","marginRight":"12px"}'></i>
        热门资讯
      </div>
      <div :style='{"padding":"0","flexWrap":"wrap","background":"none","display":"flex","width":"100%","justifyContent":"space-between","height":"auto","gap":"25px"}'>
        <div v-for="item in hotList" :key="item.id" :style='{"padding":"0","boxShadow":"0 4px 15px rgba(0,0,0,0.06)","borderRadius":"16px","flexWrap":"nowrap","background":"#fff","flexDirection":"row","display":"flex","width":"calc(25% - 19px)","alignItems":"center","height":"120px","overflow":"hidden","cursor":"pointer","transition":"all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275)","border":"none"}' @click="toNewsDetail(item)" @mouseover="e => {e.currentTarget.style.transform='translateY(-6px)';e.currentTarget.style.boxShadow='0 10px 25px rgba(46, 125, 50, 0.18)'}" @mouseout="e => {e.currentTarget.style.transform='translateY(0)';e.currentTarget.style.boxShadow='0 4px 15px rgba(0,0,0,0.06)'}">
          <img :style='{"width":"130px","objectFit":"cover","display":"block","height":"100%"}' :src="baseUrl + item.picture" alt="">
          <div :style='{"padding":"20px","display":"flex","flexDirection":"column","justifyContent":"center","flex":"1","height":"100%"}'>
            <div :style='{"fontSize":"15px","fontWeight":"600","color":"#1a1a1a","lineHeight":"1.45","marginBottom":"10px","display":"-webkit-box","-webkit-box-orient":"vertical","-webkit-line-clamp":"2","overflow":"hidden"}'>{{ item.title }}</div>
            <div :style='{"fontSize":"13px","color":"#bbb","display":"flex","alignItems":"center"}'>
              <i class="el-icon-view" style="margin-right: 5px;"></i>
              {{item.addtime.split(" ")[0]}}
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 最新动态 -->
    <div class="news" :style='{"width":"100%","padding":"40px 0","background":"transparent","height":"auto"}'>
      <div :style='{"width":"100%","padding":"0 0 28px 0","lineHeight":"44px","fontSize":"24px","fontWeight":"bold","color":"#1a1a1a","background":"none","display":"flex","alignItems":"center"}'>
        <i class="el-icon-lightbulb" :style='{"color":"#2E7D32","fontSize":"26px","marginRight":"12px"}'></i>
        最新动态
      </div>
      <div :style='{"width":"100%","padding":"0","background":"none","justifyContent":"space-between","display":"flex","height":"auto","gap":"25px"}'>
        <div v-for="item in recommendList" :key="item.id" :style='{"width":"25%","margin":"0","background":"#fff","flex":"1","height":"auto","borderRadius":"16px","overflow":"hidden","boxShadow":"0 4px 15px rgba(0,0,0,0.06)","border":"none","cursor":"pointer","transition":"all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275)"}' @click="toNewsDetail(item)" @mouseover="e => {e.currentTarget.style.transform='translateY(-8px)';e.currentTarget.style.boxShadow='0 12px 30px rgba(46, 125, 50, 0.18)'}" @mouseout="e => {e.currentTarget.style.transform='translateY(0)';e.currentTarget.style.boxShadow='0 4px 15px rgba(0,0,0,0.06)'}">
          <img :style='{"width":"100%","objectFit":"cover","display":"block","height":"180px","transition":"transform 0.6s ease"}' :src="baseUrl + item.picture" alt="" @mouseover="e => e.target.style.transform='scale(1.12)'" @mouseout="e => e.target.style.transform='scale(1)'">
          <div :style='{"padding":"22px"}'>
            <div :style='{"lineHeight":"1.45","fontSize":"16px","fontWeight":"600","color":"#1a1a1a","textAlign":"left","marginBottom":"10px","display":"-webkit-box","-webkit-box-orient":"vertical","-webkit-line-clamp":"2","overflow":"hidden"}'>{{ item.title }}</div>
            <div :style='{"padding":"0","lineHeight":"1.5","fontSize":"13px","color":"#bbb","textAlign":"left","display":"flex","alignItems":"center"}'>
              <i class="el-icon-time" style="margin-right: 5px;"></i>
              {{item.addtime.split(" ")[0]}}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</template>

<script>
  export default {
    //数据集合
    data() {
      return {
        baseUrl: this.$config.baseUrl,
        breadcrumbItem: [
          {
            name: '公告资讯'
          }
        ],
        newsList: [],
        total: 1,
        pageSize: 10,
        pageSizes: [10,20,30,50],
        totalPage: 1,
        layouts: '',
        title: '',
        categoryIndex: 0,
        categoryList: [],
        hotList: [],
        recommendList: [],
      }
    },
    created() {
      this.getCategoryList()
      this.getNewsList(1);
      this.getHotList()
      this.getRecommendList()
    },
    //方法集合
    methods: {
      getCategoryList(){
        this.$http.get('newstype/list', {}).then(res => {
          if (res.data.code == 0) {
            this.categoryList = res.data.data.list
          }
        });
      },
      categoryClick(index) {
        this.categoryIndex = index
        this.getNewsList()
      },
      getNewsList(page) {
        let params = {page, limit: this.pageSize,sort:'addtime',order:'desc'};
        let searchWhere = {};
        if(this.title != '') searchWhere.title = '%' + this.title + '%';
        if(this.categoryIndex!=0){
          searchWhere.typename = this.categoryList[this.categoryIndex - 1].typename
        }
        this.$http.get('news/list', {params: Object.assign(params, searchWhere)}).then(res => {
          if (res.data.code == 0) {
            this.newsList = res.data.data.list;
            this.total = res.data.data.total;
            this.pageSize = Number(res.data.data.pageSize);
            this.pageSizes = [this.pageSize, this.pageSize*2, this.pageSize*3, this.pageSize*5];
            this.totalPage = res.data.data.totalPage;
          }
        });
      },
      getHotList(){
        let params = {page:1, limit: 4,sort:'addtime',order:'desc'};
        this.$http.get('news/autoSort', {params: params}).then(res => {
          if (res.data.code == 0) {
            this.hotList = res.data.data.list;
          }
        });
      },
      getRecommendList(){
        let url = 'news/autoSort'
        if(localStorage.getItem('frontToken')){
          url = 'news/autoSort2'
        }
        let params = {page:1, limit: 4,sort:'addtime',order:'desc'};
        this.$http.get(url, {params: params}).then(res => {
          if (res.data.code == 0) {
            this.recommendList = res.data.data.list;
          }
        });
      },
      curChange(page) {
        this.getNewsList(page);
      },
      prevClick(page) {
        this.getNewsList(page);
      },
      nextClick(page) {
        this.getNewsList(page);
      },
      toNewsDetail(item) {
        this.$router.push({path: '/index/newsDetail', query: {id: item.id}});
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
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
  
  // 分类标签激活状态
  .news-preview-pv .category .item.active {
    color: #fff !important;
    background: linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%) !important;
    border-color: transparent !important;
    box-shadow: 0 6px 20px rgba(46, 125, 50, 0.35);
    transform: translateY(-2px);
  }
  
  .news-preview-pv .category .item:hover {
    color: #2E7D32 !important;
    border-color: #2E7D32 !important;
    background: rgba(46, 125, 50, 0.06) !important;
    transform: translateY(-2px);
  }
  
  // 搜索框样式优化
  .news-preview-pv .list-form-pv ::v-deep .el-input__inner {
    border: 1px solid #e8e8e8 !important;
    border-radius: 10px;
    padding: 0 15px 0 45px !important;
    height: 46px;
    line-height: 46px;
    font-size: 14px;
    transition: all 0.3s ease;
    background: #f8faf8;
    
    &:focus {
      border-color: #2E7D32 !important;
      background: #fff;
      box-shadow: 0 0 0 3px rgba(46, 125, 50, 0.1) !important;
    }
  }
  
  .news-preview-pv .list-form-pv ::v-deep .el-input__prefix {
    left: 15px;
    color: #2E7D32;
  }
  
  // 分页样式优化
  #pagination.el-pagination ::v-deep .btn-prev,
  #pagination.el-pagination ::v-deep .btn-next {
    border: 1px solid #e8e8e8;
    border-radius: 10px;
    padding: 0 24px;
    margin: 0 8px;
    color: #666;
    background: #fff;
    font-size: 14px;
    line-height: 40px;
    min-width: auto;
    height: 40px;
    transition: all 0.3s ease;
    font-weight: 500;
    
    &:hover:not(:disabled) {
      color: #2E7D32;
      border-color: #2E7D32;
      background: rgba(46, 125, 50, 0.06);
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(46, 125, 50, 0.15);
    }
  }
  
  #pagination.el-pagination ::v-deep .el-pager .number {
    cursor: pointer;
    padding: 0 16px;
    margin: 0 6px;
    color: #666;
    font-size: 14px;
    line-height: 40px;
    border-radius: 10px;
    background: #fff;
    border: 1px solid #e8e8e8;
    text-align: center;
    min-width: auto;
    height: 40px;
    transition: all 0.3s ease;
    font-weight: 500;
    
    &:hover {
      color: #2E7D32;
      border-color: #2E7D32;
      background: rgba(46, 125, 50, 0.06);
      transform: translateY(-2px);
    }
    
    &.active {
      color: #fff;
      background: linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%);
      border-color: transparent;
      box-shadow: 0 6px 20px rgba(46, 125, 50, 0.35);
      transform: translateY(-2px);
    }
  }
</style>
