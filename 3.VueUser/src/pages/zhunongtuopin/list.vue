<template>
<div>
  <!-- 面包屑导航 -->
  <div :style='{"width":"100%","padding":"30px 10%","margin":"0 auto","borderRadius":"8px","background":"linear-gradient(180deg, rgba(80, 160, 80, 0.9), rgba(60, 140, 60, 1))","boxShadow":"0 4px 12px rgba(0, 0, 0, 0.15)","border":"1px solid rgba(255, 255, 255, 0.2)"}' class="breadcrumb-preview">
    <el-breadcrumb :separator="'>'" :style='{"fontSize":"16px","lineHeight":"1","color":"#fff"}'>
      <el-breadcrumb-item class="item1" to="/"><a style="color: #fff;">首页</a></el-breadcrumb-item>
      <el-breadcrumb-item class="item2" v-for="(item, index) in breadcrumbItem" :key="index"><span style="color: #fff;">{{item.name}}</span></el-breadcrumb-item>
    </el-breadcrumb>
  </div>
  
  <div v-if="centerType" :style='{"width":"80%","padding":"30px 10%","margin":"30px auto","borderRadius":"8px","background":"linear-gradient(180deg, rgba(80, 160, 80, 0.9), rgba(60, 140, 60, 1))","boxShadow":"0 4px 12px rgba(0, 0, 0, 0.15)","border":"1px solid rgba(255, 255, 255, 0.2)"}'>
    <el-button size="mini" @click="backClick">返回</el-button>
  </div>
  
  <div class="list-preview" :style='{"width":"80%","margin":"40px auto","position":"relative","background":"transparent"}'>
    
    <!-- 搜索区域 -->
    <div class="search-section" :style='{"padding":"0 0 30px 0"}'>
      <el-form :inline="true" :model="formSearch" class="list-form-pv" :style='{"display":"flex","alignItems":"center","justifyContent":"center","flexWrap":"wrap","background":"#fff","padding":"25px 40px","borderRadius":"16px","border":"none","boxShadow":"0 4px 20px rgba(46, 125, 50, 0.08)"}'>
        <el-form-item :style='{"margin":"0 15px 0 0"}'>
          <div class="lable" v-if="true" :style='{"width":"auto","padding":"0 10px 0 0","lineHeight":"46px","display":"inline-block","fontSize":"14px","fontWeight":"500","color":"#555"}'>文章标题：</div>
          <el-input v-model="formSearch.wenzhangbiaoti" placeholder="搜索您感兴趣的文章..." @keydown.enter.native="getList(1, curFenlei)" clearable :style='{"width":"280px"}' prefix-icon="el-icon-search"></el-input>
        </el-form-item>
        <el-button v-if=" true " :style='{"border":"0","cursor":"pointer","padding":"0 30px","margin":"0","outline":"none","color":"#fff","borderRadius":"10px","background":"linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%)","width":"auto","fontSize":"15px","fontWeight":"600","lineHeight":"46px","height":"46px","boxShadow":"0 6px 20px rgba(46, 125, 50, 0.35)"}' type="primary" @click="getList(1, curFenlei)">
          <i v-if="true" :style='{"color":"#fff","margin":"0 8px 0 0","fontSize":"14px"}' class="el-icon-search"></i>查询
        </el-button>
        <el-button v-if="btnAuth('zhunongtuopin','新增')" :style='{"border":"1px solid #2E7D32","cursor":"pointer","padding":"0 30px","margin":"0 0 0 15px","outline":"none","color":"#2E7D32","borderRadius":"10px","background":"#fff","width":"auto","fontSize":"15px","fontWeight":"600","lineHeight":"46px","height":"46px"}' type="primary" @click="add('/index/zhunongtuopinAdd')">
          <i v-if="true" :style='{"color":"#2E7D32","margin":"0 8px 0 0","fontSize":"14px"}' class="el-icon-circle-plus-outline"></i>添加
        </el-button>
      </el-form>
    </div>
    
    <!-- 分类侧边栏 + 内容区域 -->
    <div :style='{"display":"flex","gap":"30px"}'>
      <!-- 左侧分类 -->
      <div class="category-3" :style='{"padding":"25px","boxShadow":"0 4px 15px rgba(0,0,0,0.06)","margin":"0","background":"#fff","borderRadius":"16px","display":"flex","flexDirection":"column","width":"220px","height":"auto","flexShrink":"0"}'>
        <div :style='{"fontSize":"16px","fontWeight":"bold","color":"#1a1a1a","marginBottom":"20px","paddingBottom":"15px","borderBottom":"2px solid rgba(46, 125, 50, 0.1)"}'>文章分类</div>
        <div class="item" :class="swiperIndex == '-1' ? 'active' : ''" @click="getList(1, '全部')" :plain="isPlain" :style='{"cursor":"pointer","borderRadius":"12px","padding":"15px","margin":"0 0 10px","color":"#555","background":"#f8faf8","display":"flex","width":"100%","alignItems":"center","transition":"all 0.3s ease","border":"1px solid transparent"}'>
          <i class="el-icon-menu" :style='{"marginRight":"10px","fontSize":"16px"}'></i>
          <div :style='{"color":"inherit","fontSize":"14px","fontWeight":"500"}'>全部</div>
        </div>
        <div class="item" :class="swiperIndex == index ? 'active' : ''" v-for="(item, index) in fenlei" :key="index" @click="getList(1, item[feileiColumn], 'btn' + index)" :ref="'btn' + index" plain :style='{"cursor":"pointer","borderRadius":"12px","padding":"15px","margin":"0 0 10px","color":"#555","background":"#f8faf8","display":"flex","width":"100%","alignItems":"center","transition":"all 0.3s ease","border":"1px solid transparent"}'>
          <img v-if="item.image" :style='{"width":"40px","margin":"0 10px 0 0","objectFit":"cover","display":"block","height":"40px","borderRadius":"8px"}' :src="baseUrl + (item.image?item.image.split(',')[0]:'')">
          <i v-else class="el-icon-document" :style='{"marginRight":"10px","fontSize":"16px"}'></i>
          <div :style='{"color":"inherit","fontSize":"14px","fontWeight":"500"}'>{{item[feileiColumn]}}</div>
        </div>
      </div>
      
      <!-- 右侧内容 -->
      <div :style='{"flex":"1"}'>
        <!-- 文章列表 -->
        <div class="list" :style='{"width":"100%","margin":"0 auto 30px","background":"transparent"}'>
          <!-- 样式二 -->
          <div class="list2 index-pv1" :style='{"padding":"0","flexWrap":"wrap","background":"none","display":"grid","gridTemplateColumns":"repeat(2, 1fr)","gap":"25px","width":"100%","height":"auto"}'>
            <div :style='{"padding":"0","boxShadow":"0 4px 15px rgba(0,0,0,0.06)","margin":"0","background":"#fff","borderRadius":"16px","overflow":"hidden","display":"flex","width":"100%","fontSize":"0","position":"relative","height":"auto","transition":"all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275)","cursor":"pointer","border":"none"}' v-for="(item, index) in dataList" :key="index" @click.stop="toDetail(item)" class="list-item animation-box" @mouseover="e => {e.currentTarget.style.transform='translateY(-8px)';e.currentTarget.style.boxShadow='0 12px 30px rgba(46, 125, 50, 0.18)'}" @mouseout="e => {e.currentTarget.style.transform='translateY(0)';e.currentTarget.style.boxShadow='0 4px 15px rgba(0,0,0,0.06)'}">
              <div :style='{"border":"0","width":"200px","padding":"0","height":"160px","flexShrink":"0","overflow":"hidden"}'>
                <img @click.stop="imgPreView(item.wenzhangzhaopian)" :style='{"border":"none","width":"100%","objectFit":"cover","background":"#f5f5f5","display":"block","height":"100%","transition":"transform 0.6s ease"}' v-if="item.wenzhangzhaopian && item.wenzhangzhaopian.substr(0,4)=='http'" :src="item.wenzhangzhaopian.split(',')[0]" class="image" @mouseover="e => e.target.style.transform='scale(1.12)'" @mouseout="e => e.target.style.transform='scale(1)'" />
                <img @click.stop="imgPreView(baseUrl + (item.wenzhangzhaopian?item.wenzhangzhaopian.split(',')[0]:''))" :style='{"border":"none","width":"100%","objectFit":"cover","background":"#f5f5f5","display":"block","height":"100%","transition":"transform 0.6s ease"}' v-else :src="baseUrl + (item.wenzhangzhaopian?item.wenzhangzhaopian.split(',')[0]:'')" class="image" @mouseover="e => e.target.style.transform='scale(1.12)'" @mouseout="e => e.target.style.transform='scale(1)'" />
              </div>
              <div class="item-info" :style='{"padding":"22px","overflow":"hidden","alignItems":"flex-start","flexWrap":"wrap","flex":"1","display":"flex","flexDirection":"column","height":"auto","background":"linear-gradient(180deg, rgba(46, 125, 50, 0.02) 0%, rgba(46, 125, 50, 0.04) 100%)"}'>
                <div :style='{"padding":"0 0 10px 0","whiteSpace":"nowrap","overflow":"hidden","color":"#1a1a1a","width":"100%","lineHeight":"1.4","fontSize":"18px","textOverflow":"ellipsis","fontWeight":"bold","order":"1"}' class="name">{{item.wenzhangbiaoti}}</div>
                <div :style='{"padding":"0 0 12px 0","whiteSpace":"nowrap","overflow":"hidden","color":"#2E7D32","width":"100%","lineHeight":"1","fontSize":"13px","textOverflow":"ellipsis","fontWeight":"600","order":"1","display":"flex","alignItems":"center"}' class="name">
                  <i class="el-icon-folder-opened" style="margin-right: 6px;"></i>
                  {{item.wenzhangfenlei}}
                </div>
                <div :style='{"display":"flex","width":"100%","marginTop":"auto","paddingTop":"12px","borderTop":"1px solid #e8e8e8"}'>
                  <div :style='{"width":"auto","padding":"0 15px 0 0","order":"7","display":"flex","alignItems":"center"}'>
                    <i class="el-icon-time" :style='{"margin":"0 5px 0 0","lineHeight":"1","fontSize":"13px","color":"#bbb"}'></i>
                    <span :style='{"color":"#bbb","lineHeight":"1","fontSize":"13px"}'>{{item.addtime}}</span>
                  </div>
                  <div :style='{"width":"auto","padding":"0","order":"4","display":"flex","alignItems":"center"}'>
                    <i class="el-icon-star-off" :style='{"margin":"0 5px 0 0","lineHeight":"1","fontSize":"13px","color":"#bbb"}'></i>
                    <span :style='{"color":"#bbb","lineHeight":"1","fontSize":"13px"}'>{{item.storeupnum}}人收藏</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        
        <!-- 分页 -->
        <el-pagination
          background
          id="pagination"
          class="pagination"
          :pager-count="7"
          :page-size="pageSize"
          :page-sizes="pageSizes"
          prev-text="上一页"
          next-text="下一页"
          :hide-on-single-page="false"
          :layout='["prev","pager","next"].join()'
          :total="total"
          :style='{"padding":"0","margin":"40px auto 20px","whiteSpace":"nowrap","color":"#333","textAlign":"center","width":"100%","fontWeight":"500"}'
          @current-change="curChange"
          @size-change="sizeChange"
          @prev-click="prevClick"
          @next-click="nextClick"
        ></el-pagination>
      </div>
    </div>

  </div>
  <el-dialog title="预览图" :visible.sync="previewVisible" width="50%">
    <img :src="previewImg" alt="" style="width: 100%;">
  </el-dialog>
</div>
</template>

<script>
  export default {
    //数据集合
    data() {
      return {
        selectIndex2: 0,
        selectOptionsList: [],
        layouts: '',
        swiperIndex: -1,
        baseUrl: '',
        breadcrumbItem: [
          {
            name: '助农案例'
          }
        ],
        formSearch: {
          wenzhangbiaoti: '',
        },
        fenlei: [],
        feileiColumn: '',
        dataList: [],
        total: 1,
        pageSize: 10,
        pageSizes: [10,20,30,50],
        totalPage: 1,
        curFenlei: '全部',
        isPlain: false,
        indexQueryCondition: '',
        timeRange: [],
        centerType:false,
        previewImg: '',
        previewVisible: false,
        sortType: 'id',
        sortOrder: 'desc',
      }
    },
    created() {
      if(this.$route.query.centerType){
        this.centerType = true
      }
      this.baseUrl = this.$config.baseUrl;
      this.getFenlei();
      this.getList(1, '全部');
    },
    //方法集合
    methods: {
      selectClick2(row,index) {
        row.check = index
        if(index == -1){
          this.formSearch[row.tableName] = ''
        }else {
          this.formSearch[row.tableName] = row.list[index]
        }
        this.getList()
      },
      add(path) {
        let query = {}
        if(this.centerType){
          query.centerType = 1
        }
        this.$router.push({path: path,query:query});
      },
      getFenlei() {
        this.$http.get('wenzhangfenlei/list',{}).then(res => {
          if (res.data.code == 0) {
            this.fenlei = res.data.data.list
          }
        });
        this.feileiColumn = 'wenzhangfenlei'
      },
      getList(page, fenlei, ref = '') {
        if(fenlei == '全部') this.swiperIndex = -1;
        for(let i=0;i<this.fenlei.length;i++) {
          if(fenlei == this.fenlei[i][this.feileiColumn]) {
            this.swiperIndex = i;
            break;
          }
        }
        if(fenlei){
          this.curFenlei = fenlei;
        }
        let params = {page, limit: this.pageSize};
        let searchWhere = {};
        if (this.formSearch.wenzhangbiaoti != '') searchWhere.wenzhangbiaoti = '%' + this.formSearch.wenzhangbiaoti + '%';
        if (this.curFenlei != '全部') searchWhere.wenzhangfenlei = this.curFenlei;
        if (this.sortType) searchWhere.sort = this.sortType
        if (this.sortOrder) searchWhere.order = this.sortOrder
        this.$http.get(`zhunongtuopin/${this.centerType?'page':'list'}`, {params: Object.assign(params, searchWhere)}).then(res => {
          if (res.data.code == 0) {
            this.dataList = res.data.data.list;
            this.total = Number(res.data.data.total);
            this.pageSize = Number(res.data.data.pageSize);
            this.totalPage = res.data.data.totalPage;
            
            this.pageSizes = [this.pageSize, this.pageSize*2, this.pageSize*3, this.pageSize*5];
          }
        });
      },
      curChange(page) {
        this.getList(page,this.curFenlei);
      },
      prevClick(page) {
        this.getList(page,this.curFenlei);
      },
      sizeChange(size){
        this.pageSize = size
        this.getList(1,this.curFenlei);
      },
      nextClick(page) {
        this.getList(page,this.curFenlei);
      },
      imgPreView(url){
        this.previewImg = url
        this.previewVisible = true
      },
      toDetail(item) {
        let params = {
          id: item.id
        }
        if(this.centerType){
          params.centerType = 1
        }
        this.$router.push({path: '/index/zhunongtuopinDetail', query: params});
      },
      btnAuth(tableName,key){
        if(this.centerType){
          return this.isBackAuth(tableName,key)
        }else{
          return this.isAuth(tableName,key)
        }
      },
      backClick() {
        this.$router.push({path: '/index/center'});
      },
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .list-preview .list-form-pv .el-input {
    width: auto;
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
  
  // 分类标签激活状态
  .category-3 .item.active {
    color: #fff !important;
    background: linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%) !important;
    border-color: transparent !important;
    box-shadow: 0 6px 20px rgba(46, 125, 50, 0.35);
    transform: translateY(-2px);
  }
  
  .category-3 .item:hover {
    color: #2E7D32 !important;
    background: rgba(46, 125, 50, 0.06) !important;
    border-color: #2E7D32 !important;
    transform: translateY(-2px);
  }
  
  // 搜索框样式优化
  .list-form-pv ::v-deep .el-input__inner {
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
  
  .list-form-pv ::v-deep .el-input__prefix {
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
