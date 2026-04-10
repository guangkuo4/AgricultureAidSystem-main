<template>
<div class="news-page-align">
  <!-- 面包屑导航 -->
  <div class="breadcrumb-preview">
    <el-breadcrumb :separator="'>'">
      <el-breadcrumb-item class="item1" to="/"><a style="color: #fff;">首页</a></el-breadcrumb-item>
      <el-breadcrumb-item class="item2" v-for="(item, index) in breadcrumbItem" :key="index"><span style="color: #fff;">{{item.name}}</span></el-breadcrumb-item>
    </el-breadcrumb>
  </div>
  
  <div class="news-preview-pv">
    
    <!-- 搜索区域 -->
    <div class="search-section">
      <el-form :inline="true" :model="formSearch" class="list-form-pv">
        <el-form-item>
          <el-input v-model="title" placeholder="搜索您感兴趣的资讯..." prefix-icon="el-icon-search"></el-input>
        </el-form-item>
        <el-button type="primary" @click="getNewsList(1)">
          <i class="el-icon-search"></i>
          搜索
        </el-button>
      </el-form>
    </div>
    
    <!-- 分类标签 -->
    <div class="category">
      <div class="item" @click="categoryClick(0)" :class="categoryIndex == 0 ? 'active' : ''">全部资讯</div>
      <div v-for="(item,index) in categoryList" @click="categoryClick(index+1)" :key="index" class="item" :class="categoryIndex == index+1 ? 'active' : ''">{{item.typename}}</div>
    </div>
    
    <!-- 主要新闻列表 -->
    <div v-if="newsList.length" class="list8 index-pv1">
      <!-- 头条新闻 -->
      <div v-if="newsList[0]">
        <div @click="toNewsDetail(newsList[0])">
          <div>
            <img :src="baseUrl + newsList[0].picture" alt="">
            <div>头条推荐</div>
          </div>
          <div>
            <div>{{newsList[0].addtime}}</div>
            <div>{{newsList[0].title}}</div>
            <div>{{newsList[0].introduction}}</div>
            <div>阅读全文</div>
          </div>
        </div>
      </div>
      
      <!-- 新闻卡片列表 -->
      <div v-if="newsList.length > 1">
        <template v-for="(item,index) in newsList">
          <div v-if="index > 0 && index < 7" @click="toNewsDetail(item)">
            <div>
              <img :src="baseUrl + item.picture" alt="">
              <div>{{item.typename || '最新资讯'}}</div>
            </div>
            <div>
              <div>{{item.title}}</div>
              <div>{{item.introduction}}</div>
              <div>
                <span>{{item.addtime.split(" ")[0]}}</span>
                <span>查看详情</span>
              </div>
            </div>
          </div>
        </template>
      </div>
      
      <!-- 更多新闻列表 -->
      <div v-if="newsList.length > 6">
        <div>更多资讯</div>
        <template v-for="(item,index) in newsList">
          <div v-if="index > 5" @click="toNewsDetail(item)">
            <div>
              <span>{{index + 1}}</span>
              <div>
                <div>{{item.title}}</div>
                <div>{{item.typename || '资讯'}}</div>
              </div>
            </div>
            <div>
              <span>{{item.addtime.split(" ")[0]}}</span>
              <i class="el-icon-arrow-right"></i>
            </div>
          </div>
        </template>
      </div>
    </div>
    
    <!-- 空状态 -->
    <div v-else>
      <i class="el-icon-document"></i>
      <div>暂无资讯内容</div>
      <el-button @click="getNewsList(1)">
        <i class="el-icon-refresh"></i>刷新
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
      @current-change="curChange"
      @prev-click="prevClick"
      @next-click="nextClick"
    ></el-pagination>

    <!-- 热门信息 -->
    <div class="hot">
      <div>热门资讯</div>
      <div>
        <div v-for="item in hotList" :key="item.id" @click="toNewsDetail(item)">
          <img :src="baseUrl + item.picture" alt="">
          <div>
            <div>{{ item.title }}</div>
            <div>{{item.addtime.split(" ")[0]}}</div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 最新动态 -->
    <div class="news">
      <div>最新动态</div>
      <div>
        <div v-for="item in recommendList" :key="item.id" @click="toNewsDetail(item)">
          <img :src="baseUrl + item.picture" alt="">
          <div>
            <div>{{ item.title }}</div>
            <div>{{item.addtime.split(" ")[0]}}</div>
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
/* ========== 新闻列表页样式 ========== */
.news-page-align {
	width: 100%;
	box-sizing: border-box;
	padding-left: 28px;
	padding-right: 28px;
}

@media (max-width: 768px) {
	.news-page-align {
		padding-left: 16px;
		padding-right: 16px;
	}
}

/* 面包屑导航 */
.breadcrumb-preview {
  width: 100%;
  padding: 26px 28px;
  margin: 0;
  border-radius: 8px;
  background: linear-gradient(180deg, rgba(80, 160, 80, 0.9), rgba(60, 140, 60, 1));
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  border: 1px solid rgba(255, 255, 255, 0.2);

  .el-breadcrumb {
    font-size: 16px;
    line-height: 1;
    color: #fff;

    .el-breadcrumb__separator {
      margin: 0 20px;
      color: rgba(255, 255, 255, 0.8);
      font-weight: 500;
    }

    .item1 .el-breadcrumb__inner a,
    .item2 .el-breadcrumb__inner a {
      color: #fff;
      display: inline-block;
    }
  }
}

/* 主要内容区域 */
.news-preview-pv {
  width: 100%;
  margin: 60px 0 0;
  position: relative;
  background: transparent;
}

/* 搜索区域 */
.search-section {
  padding: 0 0 40px 0;

  .list-form-pv {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-wrap: wrap;
    background: #fff;
    padding: 30px 40px;
    border-radius: 20px;
    border: none;
    box-shadow: 0 6px 24px rgba(46, 125, 50, 0.08);

    .el-form-item {
      margin: 0 20px 0 0;
    }

    .el-input {
      width: 360px;
    }

    .el-button {
      border: 0;
      cursor: pointer;
      padding: 0 40px;
      margin: 0;
      outline: none;
      color: #fff;
      border-radius: 12px;
      background: linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%);
      width: auto;
      font-size: 16px;
      font-weight: 600;
      line-height: 48px;
      height: 48px;
      box-shadow: 0 8px 24px rgba(46, 125, 50, 0.35);
    }
  }
}

/* 分类标签 */
.category {
  width: 100%;
  padding: 0 0 45px 0;
  background: none;
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  height: auto;
  gap: 15px;

  .item {
    cursor: pointer;
    border-radius: 28px;
    padding: 12px 32px;
    margin: 0;
    color: #555;
    background: #fff;
    border: 1px solid #e8e8e8;
    font-size: 15px;
    font-weight: 500;
    transition: all 0.3s ease;

    &:hover {
      color: #388e3c !important;
      border-color: #4caf50 !important;
      background: rgba(76, 175, 80, 0.06) !important;
      transform: translateY(-2px);
    }

    &.active {
      color: #fff !important;
      background: linear-gradient(135deg, #4caf50, #2e7d32) !important;
      border-color: transparent !important;
      box-shadow: 0 8px 24px rgba(46, 125, 50, 0.35);
      transform: translateY(-3px);
    }
  }
}

/* 新闻列表 */
.list8 {
  width: 100%;
  padding: 0;
  background: none;
  height: auto;

  /* 头条新闻 */
  > div:first-child {
    width: 100%;
    margin-bottom: 35px;

    > div {
      width: 100%;
      display: flex;
      height: auto;
      background: #fff;
      border-radius: 20px;
      overflow: hidden;
      box-shadow: 0 8px 30px rgba(46, 125, 50, 0.1);
      border: none;
      cursor: pointer;

      > div:first-child {
        width: 52%;
        position: relative;
        height: 460px;
        overflow: hidden;

        img {
          width: 100%;
          object-fit: cover;
          display: block;
          height: 100%;
          transition: transform 0.6s ease;

          &:hover {
            transform: scale(1.08);
          }
        }

        > div:first-child {
          position: absolute;
          top: 25px;
          left: 25px;
          background: linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%);
          color: #fff;
          padding: 10px 24px;
          border-radius: 24px;
          font-size: 14px;
          font-weight: 600;
          box-shadow: 0 6px 20px rgba(46, 125, 50, 0.35);
        }

        > div:last-child {
          position: absolute;
          bottom: 0;
          left: 0;
          right: 0;
          height: 50%;
          background: linear-gradient(180deg, transparent 0%, rgba(0,0,0,0.6) 100%);
        }
      }

      > div:last-child {
        width: 48%;
        padding: 50px 45px;
        display: flex;
        flex-direction: column;
        justify-content: center;
        background: linear-gradient(180deg, rgba(46, 125, 50, 0.02) 0%, rgba(46, 125, 50, 0.06) 100%);

        > div:first-child {
          color: #2E7D32;
          font-size: 14px;
          font-weight: 600;
          margin-bottom: 20px;
          display: flex;
          align-items: center;
        }

        > div:nth-child(2) {
          color: #1a1a1a;
          font-size: 30px;
          font-weight: bold;
          margin-bottom: 24px;
          line-height: 1.35;
        }

        > div:nth-child(3) {
          color: #666;
          font-size: 15px;
          line-height: 1.8;
          margin-bottom: 30px;
          display: -webkit-box;
          -webkit-box-orient: vertical;
          -webkit-line-clamp: 3;
          overflow: hidden;
        }

        > div:last-child {
          margin-top: auto;

          > div {
            display: inline-flex;
            align-items: center;
            color: #2E7D32;
            font-size: 16px;
            font-weight: 600;
            padding: 12px 28px;
            background: rgba(46, 125, 50, 0.08);
            border-radius: 30px;
            transition: all 0.3s ease;

            &:hover {
              background: rgba(46, 125, 50, 0.15);
              transform: translateX(5px);
            }
          }
        }
      }
    }
  }

  /* 新闻卡片列表 */
  > div:nth-child(2) {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 30px;
    margin-bottom: 50px;

    > div {
      background: #fff;
      border-radius: 20px;
      overflow: hidden;
      box-shadow: 0 6px 20px rgba(0,0,0,0.08);
      border: none;
      transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
      cursor: pointer;

      &:hover {
        transform: translateY(-10px);
        box-shadow: 0 15px 35px rgba(46, 125, 50, 0.2);
      }

      > div:first-child {
        width: 100%;
        height: 200px;
        overflow: hidden;
        position: relative;

        img {
          width: 100%;
          object-fit: cover;
          display: block;
          height: 100%;
          transition: transform 0.6s ease;

          &:hover {
            transform: scale(1.12);
          }
        }

        > div {
          position: absolute;
          top: 15px;
          left: 15px;
          background: linear-gradient(135deg, rgba(46, 125, 50, 0.9) 0%, rgba(27, 94, 32, 0.9) 100%);
          color: #fff;
          padding: 6px 16px;
          border-radius: 20px;
          font-size: 12px;
          font-weight: 600;
          backdrop-filter: blur(10px);
        }
      }

      > div:last-child {
        padding: 30px;

        > div:first-child {
          font-size: 18px;
          font-weight: 600;
          color: #1a1a1a;
          line-height: 1.5;
          margin-bottom: 15px;
          display: -webkit-box;
          -webkit-box-orient: vertical;
          -webkit-line-clamp: 2;
          overflow: hidden;
        }

        > div:nth-child(2) {
          color: #999;
          font-size: 15px;
          line-height: 1.8;
          display: -webkit-box;
          -webkit-box-orient: vertical;
          -webkit-line-clamp: 2;
          overflow: hidden;
          margin-bottom: 22px;
        }

        > div:last-child {
          display: flex;
          align-items: center;
          justify-content: space-between;
          padding-top: 20px;
          border-top: 1px solid #f0f0f0;

          > span:first-child {
            color: #bbb;
            font-size: 14px;
            display: flex;
            align-items: center;
          }

          > span:last-child {
            color: #2E7D32;
            font-size: 15px;
            font-weight: 500;
            display: flex;
            align-items: center;
          }
        }
      }
    }
  }

  /* 更多新闻列表 */
  > div:last-child {
    width: 100%;
    margin-top: 30px;
    background: #fff;
    border-radius: 24px;
    padding: 40px;
    box-shadow: 0 6px 24px rgba(46, 125, 50, 0.08);

    > div:first-child {
      font-size: 24px;
      font-weight: bold;
      color: #1a1a1a;
      margin-bottom: 30px;
      display: flex;
      align-items: center;

      > span:first-child {
        width: 6px;
        height: 28px;
        background: linear-gradient(180deg, #2E7D32 0%, #1B5E20 100%);
        border-radius: 4px;
        margin-right: 18px;
      }
    }

    > div:not(:first-child) {
      padding: 25px 30px;
      background: #f8faf8;
      border-radius: 16px;
      margin-bottom: 15px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      border: none;
      transition: all 0.3s ease;
      cursor: pointer;

      &:hover {
        background: rgba(46, 125, 50, 0.06);
        transform: translateX(10px);
      }

      > div:first-child {
        display: flex;
        align-items: center;
        flex: 1;

        > span:first-child {
          width: 50px;
          height: 50px;
          background: linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%);
          border-radius: 16px;
          display: flex;
          align-items: center;
          justify-content: center;
          color: #fff;
          font-weight: bold;
          font-size: 20px;
          margin-right: 24px;
          box-shadow: 0 6px 16px rgba(46, 125, 50, 0.3);
        }

        > div {
          > div:first-child {
            font-size: 17px;
            font-weight: 600;
            color: #1a1a1a;
            margin-bottom: 8px;
          }

          > div:last-child {
            font-size: 14px;
            color: #999;
          }
        }
      }

      > div:last-child {
        display: flex;
        align-items: center;

        > span {
          color: #bbb;
          font-size: 15px;
          margin-right: 24px;
        }

        > i {
          color: #2E7D32;
          font-size: 20px;
        }
      }
    }
  }
}

/* 空状态 */
.news-preview-pv > div:nth-child(4) {
  text-align: center;
  padding: 100px 30px;
  background: #fff;
  border-radius: 20px;
  box-shadow: 0 4px 20px rgba(46, 125, 50, 0.06);

  > i {
    font-size: 120px;
    color: #e0e0e0;
    margin-bottom: 25px;
  }

  > div {
    font-size: 18px;
    color: #999;
    margin-bottom: 25px;
  }

  > button {
    border: 0;
    padding: 0 35px;
    border-radius: 10px;
    background: linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%);
    color: #fff;
    font-size: 15px;
    font-weight: 600;
    line-height: 46px;
    height: 46px;
    box-shadow: 0 6px 20px rgba(46, 125, 50, 0.35);
  }
}

/* 分页 */
#pagination.el-pagination {
  padding: 0;
  margin: 50px auto 30px;
  white-space: nowrap;
  color: #333;
  text-align: center;
  width: 100%;
  font-weight: 500;

  .btn-prev,
  .btn-next {
    border: 1px solid rgba(46, 125, 50, 0.12);
    border-radius: 12px;
    padding: 0 20px;
    margin: 0 8px;
    color: #607060;
    background: #fff;
    font-size: 14px;
    line-height: 42px;
    min-width: auto;
    height: 42px;
    transition: all 0.3s ease;
    font-weight: 600;

    &:hover:not(:disabled) {
      color: #388e3c;
      border-color: #4caf50;
      background: rgba(76, 175, 80, 0.06);
      transform: translateY(-2px);
      box-shadow: 0 6px 20px rgba(46, 125, 50, 0.15);
    }
  }

  .el-pager .number {
    cursor: pointer;
    padding: 0 16px;
    margin: 0 6px;
    color: #607060;
    font-size: 14px;
    line-height: 42px;
    border-radius: 12px;
    background: #fff;
    border: 1px solid rgba(46, 125, 50, 0.12);
    text-align: center;
    min-width: auto;
    height: 42px;
    transition: all 0.3s ease;
    font-weight: 600;

    &:hover {
      color: #388e3c;
      border-color: #4caf50;
      background: rgba(76, 175, 80, 0.06);
      transform: translateY(-2px);
    }

    &.active {
      color: #fff;
      background: linear-gradient(135deg, #4caf50, #2e7d32);
      border-color: transparent;
      box-shadow: 0 6px 20px rgba(46, 125, 50, 0.35);
      transform: translateY(-2px);
    }
  }
}

/* 热门资讯 */
.hot {
  width: 100%;
  box-shadow: none;
  padding: 60px 0;
  background: transparent;
  margin-top: 40px;

  > div:first-child {
    width: 100%;
    line-height: 48px;
    font-size: 28px;
    font-weight: bold;
    color: #1a1a1a;
    text-align: left;
    background: none;
    margin-bottom: 36px;
    display: flex;
    align-items: center;

    > i {
      color: #f56c6c;
      font-size: 30px;
      margin-right: 18px;
    }
  }

  > div:last-child {
    padding: 0;
    flex-wrap: wrap;
    background: none;
    display: flex;
    width: 100%;
    justify-content: center;
    height: auto;
    gap: 24px;

    > div {
      padding: 0;
      box-shadow: 0 8px 24px rgba(0,0,0,0.1);
      border-radius: 16px;
      flex-wrap: nowrap;
      background: #fff;
      flex-direction: column;
      display: flex;
      width: calc(25% - 18px);
      align-items: center;
      height: auto;
      overflow: hidden;
      cursor: pointer;
      transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
      border: none;

      &:hover {
        transform: translateY(-10px);
        box-shadow: 0 16px 32px rgba(46, 125, 50, 0.2);
      }

      > img {
        width: 100%;
        object-fit: cover;
        display: block;
        height: 160px;
      }

      > div {
        padding: 20px;
        display: flex;
        flex-direction: column;
        justify-content: center;
        flex: 1;
        width: 100%;

        > div:first-child {
          font-size: 15px;
          font-weight: 600;
          color: #1a1a1a;
          line-height: 1.5;
          margin-bottom: 10px;
          display: -webkit-box;
          -webkit-box-orient: vertical;
          -webkit-line-clamp: 2;
          overflow: hidden;
        }

        > div:last-child {
          font-size: 13px;
          color: #bbb;
          display: flex;
          align-items: center;
          margin-top: auto;
        }
      }
    }
  }
}

/* 最新动态 */
.news {
  width: 100%;
  padding: 50px 0;
  background: transparent;
  height: auto;

  > div:first-child {
    width: 100%;
    padding: 0 0 32px 0;
    line-height: 48px;
    font-size: 26px;
    font-weight: bold;
    color: #1a1a1a;
    background: none;
    display: flex;
    align-items: center;

    > i {
      color: #2E7D32;
      font-size: 28px;
      margin-right: 15px;
    }
  }

  > div:last-child {
    width: 100%;
    padding: 0;
    background: none;
    justify-content: space-between;
    display: flex;
    height: auto;
    gap: 30px;

    > div {
      width: 25%;
      margin: 0;
      background: #fff;
      flex: 1;
      height: auto;
      border-radius: 20px;
      overflow: hidden;
      box-shadow: 0 6px 20px rgba(0,0,0,0.08);
      border: none;
      cursor: pointer;
      transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);

      &:hover {
        transform: translateY(-10px);
        box-shadow: 0 15px 35px rgba(46, 125, 50, 0.2);
      }

      > img {
        width: 100%;
        object-fit: cover;
        display: block;
        height: 200px;
        transition: transform 0.6s ease;

        &:hover {
          transform: scale(1.15);
        }
      }

      > div {
        padding: 26px;

        > div:first-child {
          line-height: 1.5;
          font-size: 17px;
          font-weight: 600;
          color: #1a1a1a;
          text-align: left;
          margin-bottom: 12px;
          display: -webkit-box;
          -webkit-box-orient: vertical;
          -webkit-line-clamp: 2;
          overflow: hidden;
        }

        > div:last-child {
          padding: 0;
          line-height: 1.5;
          font-size: 14px;
          color: #bbb;
          text-align: left;
          display: flex;
          align-items: center;
        }
      }
    }
  }
}
</style>