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
    <div class="search-section">
      <el-form :inline="true" :model="formSearch" class="search-form">
        <div class="search-input-wrapper">
          <i class="el-icon-search search-icon"></i>
          <el-input 
            v-model="formSearch.wenzhangbiaoti" 
            placeholder="搜索您感兴趣的文章..." 
            @keydown.enter.native="getList(1, curFenlei)" 
            clearable 
            class="search-input"
          ></el-input>
        </div>
        <el-button class="search-btn" type="primary" @click="getList(1, curFenlei)">
          <i class="el-icon-search"></i>
          查询
        </el-button>
        <el-button v-if="btnAuth('zhunongtuopin','新增')" class="add-btn" @click="add('/index/zhunongtuopinAdd')">
          <i class="el-icon-circle-plus-outline"></i>
          添加案例
        </el-button>
      </el-form>
    </div>
    
    <!-- 分类侧边栏 + 内容区域 -->
    <div class="main-content">
      <!-- 左侧分类 -->
      <aside class="category-sidebar">
        <div class="category-header">
          <i class="el-icon-folder-open"></i>
          <span>文章分类</span>
        </div>
        <nav class="category-nav">
          <div 
            class="category-item" 
            :class="{ active: swiperIndex === '-1' }" 
            @click="getList(1, '全部')"
          >
            <i class="el-icon-menu"></i>
            <span>全部</span>
          </div>
          <div 
            class="category-item" 
            v-for="(item, index) in fenlei" 
            :key="index"
            :class="{ active: swiperIndex === index }"
            @click="getList(1, item[feileiColumn], 'btn' + index)"
          >
            <img v-if="item.image" :src="baseUrl + (item.image ? item.image.split(',')[0] : '')" class="category-icon" />
            <i v-else class="el-icon-document"></i>
            <span>{{item[feileiColumn]}}</span>
          </div>
        </nav>
        
        <!-- 统计信息 -->
        <div class="category-stats">
          <div class="stat-item">
            <div class="stat-num">{{dataList.length}}</div>
            <div class="stat-label">当前展示</div>
          </div>
          <div class="stat-item">
            <div class="stat-num">{{total}}</div>
            <div class="stat-label">总案例数</div>
          </div>
        </div>
      </aside>
      
      <!-- 右侧内容 -->
      <main class="content-area">
        <!-- 文章列表 -->
        <div class="article-grid">
          <article 
            class="article-card" 
            v-for="(item, index) in dataList" 
            :key="index" 
            @click.stop="toDetail(item)"
          >
            <div class="article-card__image-wrapper">
              <img 
                @click.stop="imgPreView(item.wenzhangzhaopian)" 
                v-if="item.wenzhangzhaopian && item.wenzhangzhaopian.substr(0,4)=='http'" 
                :src="item.wenzhangzhaopian.split(',')[0]" 
                class="article-card__image"
                @error="imgError"
              />
              <img 
                @click.stop="imgPreView(baseUrl + (item.wenzhangzhaopian ? item.wenzhangzhaopian.split(',')[0] : ''))" 
                v-else 
                :src="baseUrl + (item.wenzhangzhaopian ? item.wenzhangzhaopian.split(',')[0] : '')" 
                class="article-card__image"
                @error="imgError"
              />
              <div class="article-card__placeholder" v-if="!item.wenzhangzhaopian">
                <i class="el-icon-image"></i>
              </div>
              <div class="article-card__category-tag">{{item.wenzhangfenlei}}</div>
            </div>
            <div class="article-card__content">
              <h3 class="article-card__title">{{item.wenzhangbiaoti}}</h3>
              <p class="article-card__desc">{{item.wenzhangjianjie || '暂无简介'}}</p>
              <div class="article-card__meta">
                <span class="meta-item">
                  <i class="el-icon-time"></i>
                  {{item.addtime}}
                </span>
                <span class="meta-item">
                  <i class="el-icon-star-off"></i>
                  {{item.storeupnum}}人收藏
                </span>
                <span class="meta-item">
                  <i class="el-icon-eye"></i>
                  {{item.clicknum || 0}}阅读
                </span>
              </div>
            </div>
          </article>
        </div>
        
        <!-- 空状态 -->
        <div v-if="dataList.length === 0" class="empty-state">
          <div class="empty-icon">
            <i class="el-icon-folder-opened"></i>
          </div>
          <h3>暂无案例</h3>
          <p>暂无符合条件的助农案例，换个关键词试试吧</p>
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
          :layout='["prev","pager","next"]'
          :total="total"
          @current-change="curChange"
          @size-change="sizeChange"
          @prev-click="prevClick"
          @next-click="nextClick"
        ></el-pagination>
      </main>
    </div>
  </div>
  
  <!-- 图片预览弹窗 -->
  <el-dialog title="预览图" :visible.sync="previewVisible" width="60%" class="image-preview-dialog">
    <img :src="previewImg" alt="" class="preview-image">
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
      this.baseUrl = this.$config.resourceUrl;
      this.getFenlei();
      this.getList(1, '全部');
    },
    //方法集合
    methods: {
      imgError(e) {
        e.target.style.display = 'none';
        const placeholder = e.target.parentElement.querySelector('.article-card__placeholder');
        if (placeholder) {
          placeholder.style.display = 'flex';
        }
      },
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
  
  /* ========== 搜索区域 ========== */
  .search-section {
    margin-bottom: 32px;
  }
  
  .search-form {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 16px;
    padding: 24px 32px;
    background: #fff;
    border-radius: 20px;
    box-shadow: 0 8px 32px rgba(46, 125, 50, 0.08);
  }
  
  .search-input-wrapper {
    position: relative;
    width: 360px;
    max-width: 100%;
  }
  
  .search-icon {
    position: absolute;
    left: 16px;
    top: 50%;
    transform: translateY(-50%);
    color: #81C784;
    font-size: 16px;
    pointer-events: none;
  }
  
  .search-input ::v-deep .el-input__inner {
    width: 100%;
    height: 48px;
    padding: 0 16px 0 48px;
    border: 2px solid #e8f5e9;
    border-radius: 12px;
    font-size: 15px;
    background: #fafffa;
    transition: all 0.3s ease;
    
    &:focus {
      border-color: #4CAF50;
      background: #fff;
      box-shadow: 0 0 0 4px rgba(76, 175, 80, 0.12);
    }
  }
  
  .search-btn {
    display: inline-flex;
    align-items: center;
    gap: 8px;
    padding: 0 28px;
    height: 48px;
    background: linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%);
    border: none;
    border-radius: 12px;
    color: #fff;
    font-size: 15px;
    font-weight: 600;
    box-shadow: 0 6px 20px rgba(46, 125, 50, 0.35);
    transition: all 0.3s ease;
    
    &:hover {
      transform: translateY(-3px);
      box-shadow: 0 10px 30px rgba(46, 125, 50, 0.4);
    }
  }
  
  .add-btn {
    display: inline-flex;
    align-items: center;
    gap: 8px;
    padding: 0 28px;
    height: 48px;
    background: #fff;
    border: 2px solid #2E7D32;
    border-radius: 12px;
    color: #2E7D32;
    font-size: 15px;
    font-weight: 600;
    transition: all 0.3s ease;
    
    &:hover {
      background: rgba(46, 125, 50, 0.08);
      transform: translateY(-2px);
      box-shadow: 0 4px 16px rgba(46, 125, 50, 0.15);
    }
  }
  
  /* ========== 主内容布局 ========== */
  .main-content {
    display: flex;
    gap: 32px;
  }
  
  /* ========== 左侧分类侧边栏 ========== */
  .category-sidebar {
    width: 240px;
    flex-shrink: 0;
    background: #fff;
    border-radius: 20px;
    padding: 24px;
    box-shadow: 0 4px 20px rgba(46, 125, 50, 0.06);
    position: sticky;
    top: 24px;
  }
  
  .category-header {
    display: flex;
    align-items: center;
    gap: 10px;
    padding-bottom: 20px;
    border-bottom: 2px solid #f0f5f0;
    font-size: 16px;
    font-weight: 700;
    color: #1B5E20;
  }
  
  .category-nav {
    margin-top: 20px;
    display: flex;
    flex-direction: column;
    gap: 8px;
  }
  
  .category-item {
    display: flex;
    align-items: center;
    gap: 12px;
    padding: 14px 16px;
    border-radius: 12px;
    cursor: pointer;
    transition: all 0.3s ease;
    color: #555;
    background: #f8fcf8;
    border: 1px solid transparent;
    
    &:hover {
      background: rgba(46, 125, 50, 0.08);
      color: #2E7D32;
      transform: translateX(4px);
    }
    
    &.active {
      background: linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%);
      color: #fff;
      box-shadow: 0 6px 20px rgba(46, 125, 50, 0.3);
      transform: translateX(4px);
    }
  }
  
  .category-icon {
    width: 24px;
    height: 24px;
    border-radius: 8px;
    object-fit: cover;
    flex-shrink: 0;
  }
  
  /* 分类统计 */
  .category-stats {
    margin-top: 24px;
    padding-top: 20px;
    border-top: 2px solid #f0f5f0;
    display: flex;
    justify-content: space-around;
  }
  
  .stat-item {
    text-align: center;
  }
  
  .stat-num {
    font-size: 28px;
    font-weight: 800;
    color: #2E7D32;
  }
  
  .stat-label {
    font-size: 12px;
    color: #999;
    margin-top: 4px;
  }
  
  /* ========== 右侧内容区域 ========== */
  .content-area {
    flex: 1;
    min-width: 0;
  }
  
  /* ========== 文章网格列表 ========== */
  .article-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(360px, 1fr));
    gap: 24px;
  }
  
  .article-card {
    background: #fff;
    border-radius: 20px;
    overflow: hidden;
    box-shadow: 0 4px 20px rgba(46, 125, 50, 0.06);
    transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
    cursor: pointer;
    
    &:hover {
      transform: translateY(-8px);
      box-shadow: 0 16px 40px rgba(46, 125, 50, 0.15);
    }
  }
  
  .article-card__image-wrapper {
    position: relative;
    width: 100%;
    height: 200px;
    overflow: hidden;
  }
  
  .article-card__image {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.6s ease;
    
    &:hover {
      transform: scale(1.08);
    }
  }
  
  .article-card__placeholder {
    position: absolute;
    inset: 0;
    display: flex;
    align-items: center;
    justify-content: center;
    background: linear-gradient(135deg, #f0f5f0 0%, #e8f5e9 100%);
    
    i {
      font-size: 48px;
      color: #81C784;
    }
  }
  
  .article-card__category-tag {
    position: absolute;
    top: 12px;
    left: 12px;
    padding: 6px 14px;
    background: rgba(46, 125, 50, 0.9);
    border-radius: 999px;
    font-size: 12px;
    font-weight: 600;
    color: #fff;
  }
  
  .article-card__content {
    padding: 20px;
  }
  
  .article-card__title {
    font-size: 18px;
    font-weight: 700;
    color: #1a1a1a;
    line-height: 1.4;
    margin-bottom: 10px;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
  }
  
  .article-card__desc {
    font-size: 14px;
    color: #666;
    line-height: 1.6;
    margin-bottom: 16px;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
    color: #888;
  }
  
  .article-card__meta {
    display: flex;
    gap: 16px;
    padding-top: 14px;
    border-top: 1px solid #f0f5f0;
  }
  
  .meta-item {
    display: flex;
    align-items: center;
    gap: 6px;
    font-size: 13px;
    color: #999;
    
    i {
      font-size: 13px;
    }
  }
  
  /* ========== 空状态 ========== */
  .empty-state {
    text-align: center;
    padding: 80px 24px;
    background: #fff;
    border-radius: 20px;
    box-shadow: 0 4px 20px rgba(46, 125, 50, 0.06);
  }
  
  .empty-icon {
    width: 80px;
    height: 80px;
    margin: 0 auto 24px;
    display: flex;
    align-items: center;
    justify-content: center;
    background: linear-gradient(135deg, #f0f5f0 0%, #e8f5e9 100%);
    border-radius: 50%;
    
    i {
      font-size: 40px;
      color: #81C784;
    }
  }
  
  .empty-state h3 {
    font-size: 20px;
    font-weight: 600;
    color: #333;
    margin-bottom: 8px;
  }
  
  .empty-state p {
    font-size: 14px;
    color: #999;
  }
  
  /* ========== 分页样式 ========== */
  #pagination {
    margin-top: 40px;
    text-align: center;
  }
  
  #pagination ::v-deep .btn-prev,
  #pagination ::v-deep .btn-next {
    border: 1px solid #e8e8e8;
    border-radius: 12px;
    padding: 0 24px;
    margin: 0 8px;
    color: #666;
    background: #fff;
    font-size: 14px;
    line-height: 44px;
    min-width: auto;
    height: 44px;
    transition: all 0.3s ease;
    font-weight: 500;
    
    &:hover:not(:disabled) {
      color: #2E7D32;
      border-color: #2E7D32;
      background: rgba(46, 125, 50, 0.06);
      transform: translateY(-2px);
      box-shadow: 0 6px 16px rgba(46, 125, 50, 0.15);
    }
  }
  
  #pagination ::v-deep .el-pager .number {
    cursor: pointer;
    padding: 0 18px;
    margin: 0 6px;
    color: #666;
    font-size: 14px;
    line-height: 44px;
    border-radius: 12px;
    background: #fff;
    border: 1px solid #e8e8e8;
    text-align: center;
    min-width: auto;
    height: 44px;
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
  
  /* ========== 图片预览弹窗 ========== */
  .image-preview-dialog ::v-deep .el-dialog__body {
    padding: 0;
    background: #1a1a1a;
  }
  
  .preview-image {
    display: block;
    max-width: 100%;
    max-height: 80vh;
    margin: 0 auto;
    object-fit: contain;
  }
  
  /* ========== 响应式适配 ========== */
  @media (max-width: 900px) {
    .main-content {
      flex-direction: column;
    }
    
    .category-sidebar {
      width: 100%;
      position: static;
    }
    
    .article-grid {
      grid-template-columns: 1fr;
    }
    
    .search-form {
      flex-wrap: wrap;
    }
    
    .search-input-wrapper {
      width: 100%;
    }
    
    .page-title__main {
      font-size: 32px;
    }
  }
</style>
