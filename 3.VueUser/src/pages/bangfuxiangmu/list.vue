<template>
  <div class="page">
    <!-- 面包屑导航 -->
    <div :style='{"width":"100%","padding":"30px 10%","margin":"0 auto","borderRadius":"8px","background":"linear-gradient(180deg, rgba(80, 160, 80, 0.9), rgba(60, 140, 60, 1))","boxShadow":"0 4px 12px rgba(0, 0, 0, 0.15)","border":"1px solid rgba(255, 255, 255, 0.2)"}' class="breadcrumb-preview">
      <el-breadcrumb :separator="'>'" :style='{"fontSize":"16px","lineHeight":"1","color":"#fff"}'>
        <el-breadcrumb-item class="item1" to="/"><a style="color: #fff;">首页</a></el-breadcrumb-item>
        <el-breadcrumb-item class="item2"><span style="color: #fff;">帮扶对接</span></el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <!-- 标签页导航 -->
    <div class="tab-nav">
      <div 
        v-for="tab in tabs" 
        :key="tab.key"
        :class="['tab-item', { active: activeTab === tab.key }]"
        @click="activeTab = tab.key"
      >
        <i :class="tab.icon"></i>
        <span>{{ tab.label }}</span>
      </div>
    </div>

    <!-- 标签页内容 -->
    <div class="tab-content">
      <!-- 帮扶项目 -->
      <div v-if="activeTab === 'projects'" class="tab-panel">
        <div class="filter-bar">
          <div class="search-bar">
            <input type="text" v-model="searchValue" placeholder="搜索项目名称" @input="searchProjects" />
            <button class="search-btn" @click="searchProjects">搜索</button>
          </div>
          <div class="filter-options">
            <select v-model="selectedType" @change="searchProjects">
              <option value="">全部类型</option>
              <option value="技术">技术</option>
              <option value="采购">采购</option>
              <option value="资金">资金</option>
              <option value="其他">其他</option>
            </select>
            <select v-model="sortBy" @change="searchProjects">
              <option value="newest">最新发布</option>
              <option value="oldest">最早发布</option>
            </select>
          </div>
        </div>
        
        <div class="project-list">
          <div 
            v-for="project in projects" 
            :key="project.id"
            class="project-item"
            @click="goToDetail(project.id)"
          >
            <div class="project-image">
              <img :src="project.tupian || defaultImage" alt="项目图片" />
            </div>
            <div class="project-info">
              <h3 class="project-title">{{ project.xiangmumingcheng }}</h3>
              <div class="project-meta">
                <span class="project-type">{{ project.xiangmuleixing }}</span>
                <span class="project-date">{{ formatDate(project.faburiqi) }}</span>
              </div>
              <p class="project-desc">{{ truncate(project.xiangmuxiangqing, 50) }}</p>
              <div class="project-contact">
                <span class="contact-person"><i class="el-icon-user"></i> {{ project.lianxiren }}</span>
                <span class="contact-phone"><i class="el-icon-phone"></i> {{ project.lianxidianhua }}</span>
              </div>
              <div class="project-stats">
                <span class="view-count"><i class="el-icon-view"></i> {{ project.clicknum || 0 }} 次浏览</span>
              </div>
            </div>
          </div>
        </div>
        
        <div v-if="projects.length === 0" class="empty">
          <i class="el-icon-folder-opened" style="font-size: 48px; color: #ccc;"></i>
          <p>暂无项目</p>
          <button class="refresh-btn" @click="loadProjects">刷新</button>
        </div>
        
        <div class="loading" v-if="loading">
          <i class="el-icon-loading" style="font-size: 24px;"></i>
          <p>加载中...</p>
        </div>
        
        <!-- 分页 -->
        <div class="pagination" v-if="total > 0">
          <button class="page-btn" @click="prevPage" :disabled="page === 1">上一页</button>
          <span class="page-info">第 {{ page }} / {{ totalPages }} 页</span>
          <button class="page-btn" @click="nextPage" :disabled="page === totalPages">下一页</button>
        </div>
      </div>

      <!-- 帮扶资源池 -->
      <div v-if="activeTab === 'resources'" class="tab-panel">
        <div class="filter-bar">
          <div class="search-bar">
            <input type="text" v-model="resourceSearch" placeholder="搜索资源名称" @input="searchResources" />
            <button class="search-btn" @click="searchResources">搜索</button>
          </div>
          <div class="filter-options">
            <select v-model="resourceType" @change="searchResources">
              <option value="">全部类型</option>
              <option value="技术帮扶">技术帮扶</option>
              <option value="资金帮扶">资金帮扶</option>
              <option value="产品采购">产品采购</option>
              <option value="农机帮扶">农机帮扶</option>
            </select>
            <select v-model="resourceArea" @change="searchResources">
              <option value="">全部地区</option>
              <option value="河南">河南</option>
              <option value="山东">山东</option>
              <option value="河北">河北</option>
            </select>
          </div>
        </div>

        <div class="resource-list">
          <div v-for="resource in resources" :key="resource.id" class="resource-card">
            <div class="resource-header">
              <h3>{{ resource.bangfufangming }}</h3>
              <span class="resource-type">{{ resource.bangfuleixing }}</span>
            </div>
            <div class="resource-body">
              <p><strong>擅长领域：</strong>{{ resource.shanchanglingyu }}</p>
              <p><strong>所在地区：</strong>{{ resource.suozaidiqu }}</p>
              <p><strong>响应时效：</strong>{{ resource.xiangyingshixiao }}</p>
              <p class="resource-content">{{ truncate(resource.bangfuneirong, 80) }}</p>
            </div>
            <div class="resource-footer">
              <span><i class="el-icon-user"></i> {{ resource.lianxiren }}</span>
              <span><i class="el-icon-phone"></i> {{ resource.lianxidianhua }}</span>
              <button class="contact-btn" @click="contactResource(resource)">联系帮扶方</button>
            </div>
          </div>
        </div>

        <div v-if="resources.length === 0" class="empty">
          <i class="el-icon-folder-opened" style="font-size: 48px; color: #ccc;"></i>
          <p>暂无资源</p>
        </div>
      </div>

      <!-- 发布资源 -->
      <div v-if="activeTab === 'publishResource'" class="tab-panel">
        <div class="form-container">
          <h2 class="form-title">发布帮扶资源</h2>
          <el-form :model="resourceForm" :rules="resourceRules" ref="resourceForm" label-width="120px">
            <el-form-item label="帮扶类型" prop="bangfuleixing">
              <el-select v-model="resourceForm.bangfuleixing" placeholder="请选择帮扶类型">
                <el-option label="技术帮扶" value="技术帮扶"></el-option>
                <el-option label="资金帮扶" value="资金帮扶"></el-option>
                <el-option label="产品采购" value="产品采购"></el-option>
                <el-option label="农机帮扶" value="农机帮扶"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="帮扶方名称" prop="bangfufangming">
              <el-input v-model="resourceForm.bangfufangming" placeholder="请输入帮扶方名称"></el-input>
            </el-form-item>
            <el-form-item label="帮扶方类型" prop="bangfufangleixing">
              <el-select v-model="resourceForm.bangfufangleixing" placeholder="请选择帮扶方类型">
                <el-option label="帮扶单位" value="帮扶单位"></el-option>
                <el-option label="农技专家" value="农技专家"></el-option>
                <el-option label="农业企业" value="农业企业"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="擅长领域" prop="shanchanglingyu">
              <el-input v-model="resourceForm.shanchanglingyu" placeholder="请输入擅长领域"></el-input>
            </el-form-item>
            <el-form-item label="所在地区" prop="suozaidiqu">
              <el-input v-model="resourceForm.suozaidiqu" placeholder="请输入所在地区"></el-input>
            </el-form-item>
            <el-form-item label="响应时效" prop="xiangyingshixiao">
              <el-select v-model="resourceForm.xiangyingshixiao" placeholder="请选择响应时效">
                <el-option label="24小时内" value="24小时内"></el-option>
                <el-option label="48小时内" value="48小时内"></el-option>
                <el-option label="72小时内" value="72小时内"></el-option>
                <el-option label="一周内" value="一周内"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="帮扶内容" prop="bangfuneirong">
              <el-input type="textarea" v-model="resourceForm.bangfuneirong" :rows="4" placeholder="请详细描述可提供的帮扶内容"></el-input>
            </el-form-item>
            <el-form-item label="资质照片">
              <el-upload
                action="/file/upload"
                list-type="picture-card"
                :on-success="handleResourceImageSuccess"
                :file-list="resourceImageList"
              >
                <i class="el-icon-plus"></i>
              </el-upload>
            </el-form-item>
            <el-form-item label="联系人" prop="lianxiren">
              <el-input v-model="resourceForm.lianxiren" placeholder="请输入联系人姓名"></el-input>
            </el-form-item>
            <el-form-item label="联系电话" prop="lianxidianhua">
              <el-input v-model="resourceForm.lianxidianhua" placeholder="请输入联系电话"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitResource">提交审核</el-button>
              <el-button @click="resetResourceForm">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>

      <!-- 我的需求 -->
      <div v-if="activeTab === 'myNeeds'" class="tab-panel">
        <div class="needs-list">
          <div v-for="need in myNeeds" :key="need.id" class="need-card">
            <div class="need-header">
              <h3>{{ need.xuqiubianhao }}</h3>
              <span :class="['need-status', getStatusClass(need.sfsh)]">{{ need.sfsh }}</span>
            </div>
            <div class="need-body">
              <p><strong>需求类型：</strong>{{ need.xuqiuleixing }}</p>
              <p><strong>需求描述：</strong>{{ need.xuqiumiaoshu }}</p>
              <p><strong>紧急程度：</strong>{{ need.xuqiujinji }}</p>
              <p><strong>期望时间：</strong>{{ need.qiwangshijian }}</p>
              <p><strong>申请日期：</strong>{{ need.shenqingridi }}</p>
            </div>
            <div class="need-footer" v-if="need.shhf">
              <p><strong>审核回复：</strong>{{ need.shhf }}</p>
            </div>
          </div>
        </div>

        <div v-if="myNeeds.length === 0" class="empty">
          <i class="el-icon-document" style="font-size: 48px; color: #ccc;"></i>
          <p>暂无需求记录</p>
          <button class="publish-btn" @click="activeTab = 'publishNeed'">发布需求</button>
        </div>
      </div>

      <!-- 发布需求 -->
      <div v-if="activeTab === 'publishNeed'" class="tab-panel">
        <div class="form-container">
          <h2 class="form-title">发布帮扶需求</h2>
          <el-form :model="needForm" :rules="needRules" ref="needForm" label-width="120px">
            <el-form-item label="需求类型" prop="xuqiuleixing">
              <el-select v-model="needForm.xuqiuleixing" placeholder="请选择需求类型">
                <el-option label="技术帮扶" value="技术帮扶"></el-option>
                <el-option label="资金帮扶" value="资金帮扶"></el-option>
                <el-option label="产品采购帮扶" value="产品采购帮扶"></el-option>
                <el-option label="农机帮扶" value="农机帮扶"></el-option>
                <el-option label="其他帮扶" value="其他帮扶"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="需求描述" prop="xuqiumiaoshu">
              <el-input type="textarea" v-model="needForm.xuqiumiaoshu" :rows="4" placeholder="请详细描述您的需求"></el-input>
            </el-form-item>
            <el-form-item label="需求图片">
              <el-upload
                action="/file/upload"
                list-type="picture-card"
                :on-success="handleNeedImageSuccess"
                :file-list="needImageList"
              >
                <i class="el-icon-plus"></i>
              </el-upload>
            </el-form-item>
            <el-form-item label="紧急程度">
              <el-radio-group v-model="needForm.xuqiujinji">
                <el-radio label="普通">普通</el-radio>
                <el-radio label="紧急">紧急</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="期望帮扶时间">
              <el-date-picker v-model="needForm.qiwangshijian" type="date" placeholder="选择日期"></el-date-picker>
            </el-form-item>
            <el-form-item label="具体地址">
              <el-input v-model="needForm.jutidizhi" placeholder="请输入具体地址（精确到村/组）"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitNeed">提交需求</el-button>
              <el-button @click="resetNeedForm">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>

      <!-- 对接管理 -->
      <div v-if="activeTab === 'management'" class="tab-panel">
        <div class="management-tabs">
          <el-tabs type="border-card">
            <el-tab-pane label="待审核资源">
              <el-table :data="pendingResources" style="width: 100%">
                <el-table-column prop="ziyuanbianhao" label="资源编号"></el-table-column>
                <el-table-column prop="bangfufangming" label="帮扶方名称"></el-table-column>
                <el-table-column prop="bangfuleixing" label="帮扶类型"></el-table-column>
                <el-table-column prop="suozaidiqu" label="所在地区"></el-table-column>
                <el-table-column label="操作" width="200">
                  <template slot-scope="scope">
                    <el-button size="mini" @click="viewResourceDetail(scope.row)">查看</el-button>
                    <el-button size="mini" type="success" @click="approveResource(scope.row)">通过</el-button>
                    <el-button size="mini" type="danger" @click="rejectResource(scope.row)">拒绝</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-tab-pane>
            <el-tab-pane label="待审核需求">
              <el-table :data="pendingNeeds" style="width: 100%">
                <el-table-column prop="xuqiubianhao" label="需求编号"></el-table-column>
                <el-table-column prop="shenqingrenxingming" label="申请人"></el-table-column>
                <el-table-column prop="xuqiuleixing" label="需求类型"></el-table-column>
                <el-table-column prop="xuqiujinji" label="紧急程度"></el-table-column>
                <el-table-column label="操作" width="200">
                  <template slot-scope="scope">
                    <el-button size="mini" @click="viewNeedDetail(scope.row)">查看</el-button>
                    <el-button size="mini" type="success" @click="approveNeed(scope.row)">通过</el-button>
                    <el-button size="mini" type="danger" @click="rejectNeed(scope.row)">拒绝</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-tab-pane>
            <el-tab-pane label="对接实施">
              <el-table :data="implementations" style="width: 100%">
                <el-table-column prop="shishibianhao" label="实施编号"></el-table-column>
                <el-table-column prop="xuqiubianhao" label="需求编号"></el-table-column>
                <el-table-column prop="bangfuleixing" label="帮扶类型"></el-table-column>
                <el-table-column prop="bangfuzhuangtai" label="帮扶状态"></el-table-column>
                <el-table-column label="操作" width="150">
                  <template slot-scope="scope">
                    <el-button size="mini" @click="viewImplementation(scope.row)">查看</el-button>
                    <el-button size="mini" type="primary" @click="updateProgress(scope.row)">更新进度</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-tab-pane>
          </el-tabs>
        </div>
      </div>

      <!-- 我的评价 -->
      <div v-if="activeTab === 'myReviews'" class="tab-panel">
        <div class="review-list">
          <div v-for="review in myReviews" :key="review.id" class="review-card">
            <div class="review-header">
              <h3>评价编号：{{ review.pingjiabianhao }}</h3>
              <span class="review-score">综合评分：{{ review.zonghepingfen }}分</span>
            </div>
            <div class="review-body">
              <div class="score-detail">
                <span>服务态度：{{ review.fuwutaidu }}星</span>
                <span>帮扶效果：{{ review.bangfuxiaoguo }}星</span>
                <span>响应速度：{{ review.xiangyingsudu }}星</span>
              </div>
              <p class="review-content">{{ review.pingjias }}</p>
            </div>
            <div class="review-footer">
              <span>评价时间：{{ review.addtime }}</span>
              <span :class="['archive-status', review.shifouguidang === '是' ? 'archived' : '']">
                {{ review.shifouguidang === '是' ? '已归档' : '未归档' }}
              </span>
            </div>
          </div>
        </div>

        <div v-if="myReviews.length === 0" class="empty">
          <i class="el-icon-star-off" style="font-size: 48px; color: #ccc;"></i>
          <p>暂无评价记录</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 标签页
      activeTab: 'projects',
      tabs: [
        { key: 'projects', label: '帮扶项目', icon: 'el-icon-suitcase' },
        { key: 'resources', label: '帮扶资源池', icon: 'el-icon-collection' },
        { key: 'publishResource', label: '发布资源', icon: 'el-icon-plus' },
        { key: 'myNeeds', label: '我的需求', icon: 'el-icon-document' },
        { key: 'publishNeed', label: '发布需求', icon: 'el-icon-edit' },
        { key: 'management', label: '对接管理', icon: 'el-icon-s-management' },
        { key: 'myReviews', label: '我的评价', icon: 'el-icon-star-on' }
      ],

      // 项目列表数据
      searchValue: '',
      selectedType: '',
      sortBy: 'newest',
      projects: [],
      loading: false,
      page: 1,
      limit: 10,
      total: 0,
      defaultImage: 'https://via.placeholder.com/300x200?text=项目图片',

      // 资源池数据
      resourceSearch: '',
      resourceType: '',
      resourceArea: '',
      resources: [],

      // 发布资源表单
      resourceForm: {
        bangfuleixing: '',
        bangfufangming: '',
        bangfufangleixing: '',
        shanchanglingyu: '',
        suozaidiqu: '',
        xiangyingshixiao: '',
        bangfuneirong: '',
        zizhengzhaopian: '',
        lianxiren: '',
        lianxidianhua: ''
      },
      resourceRules: {
        bangfuleixing: [{ required: true, message: '请选择帮扶类型', trigger: 'change' }],
        bangfufangming: [{ required: true, message: '请输入帮扶方名称', trigger: 'blur' }],
        bangfufangleixing: [{ required: true, message: '请选择帮扶方类型', trigger: 'change' }],
        shanchanglingyu: [{ required: true, message: '请输入擅长领域', trigger: 'blur' }],
        suozaidiqu: [{ required: true, message: '请输入所在地区', trigger: 'blur' }],
        xiangyingshixiao: [{ required: true, message: '请选择响应时效', trigger: 'change' }],
        bangfuneirong: [{ required: true, message: '请输入帮扶内容', trigger: 'blur' }],
        lianxiren: [{ required: true, message: '请输入联系人', trigger: 'blur' }],
        lianxidianhua: [{ required: true, message: '请输入联系电话', trigger: 'blur' }]
      },
      resourceImageList: [],

      // 我的需求
      myNeeds: [],

      // 发布需求表单
      needForm: {
        xuqiuleixing: '',
        xuqiumiaoshu: '',
        xuqiutupian: '',
        xuqiujinji: '普通',
        qiwangshijian: '',
        jutidizhi: ''
      },
      needRules: {
        xuqiuleixing: [{ required: true, message: '请选择需求类型', trigger: 'change' }],
        xuqiumiaoshu: [{ required: true, message: '请输入需求描述', trigger: 'blur' }]
      },
      needImageList: [],

      // 对接管理
      pendingResources: [],
      pendingNeeds: [],
      implementations: [],

      // 我的评价
      myReviews: []
    }
  },
  computed: {
    totalPages() {
      return Math.ceil(this.total / this.limit)
    }
  },
  created() {
    this.loadProjects()
    this.loadResources()
    this.loadMyNeeds()
    this.loadReviews()
    this.loadManagementData()
  },
  methods: {
    // 项目列表方法
    loadProjects() {
      console.log('正在加载帮扶项目...')
      this.loading = true
      this.$http.get('/bangfuxiangmu/list', {
        params: {
          page: this.page,
          limit: this.limit,
          xiangmumingcheng: this.searchValue,
          xiangmuleixing: this.selectedType,
          sort: this.sortBy === 'newest' ? 'faburiqi' : 'faburiqi',
          order: this.sortBy === 'newest' ? 'desc' : 'asc'
        }
      }).then(res => {
        console.log('项目列表响应:', res)
        console.log('响应数据:', res.data)
        if (res.data.code === 0) {
          this.projects = res.data.data.list || []
          this.total = res.data.data.total || 0
          console.log('项目列表:', this.projects)
          if (this.projects.length === 0) {
            console.log('项目列表为空，请检查数据库是否有数据')
          }
        } else {
          console.warn('API 返回错误:', res.data.msg)
          this.$message.warning(res.data.msg || '获取项目失败')
        }
        this.loading = false
      }).catch(err => {
        console.error('加载项目失败:', err)
        console.error('错误详情:', err.message)
        console.error('后端地址:', this.$config.baseUrl)
        this.loading = false
        this.$message.error('加载项目失败: ' + (err.message || '网络错误'))
      })
    },
    searchProjects() {
      this.page = 1
      this.loadProjects()
    },
    prevPage() {
      if (this.page > 1) {
        this.page--
        this.loadProjects()
      }
    },
    nextPage() {
      if (this.page < this.totalPages) {
        this.page++
        this.loadProjects()
      }
    },
    goToDetail(id) {
      this.$router.push(`/index/bangfuxiangmuDetail?id=${id}`)
    },

    // 资源池方法
    loadResources() {
      console.log('正在加载资源...')
      const params = {}
      if (this.resourceType) params.bangfuleixing = this.resourceType
      if (this.resourceArea) params.suozaidiqu = this.resourceArea
      
      console.log('请求参数:', params)
      
      this.$http.get('/bangfuziyuan/list', {
        params: params
      }).then(res => {
        console.log('资源加载响应:', res)
        if (res.data.code === 0) {
          this.resources = res.data.data.list || []
          console.log('资源列表:', this.resources)
          if (this.resources.length === 0) {
            console.log('资源列表为空，请先添加资源数据')
          }
        } else {
          console.warn('API 返回错误:', res.data.msg)
          this.$message.warning(res.data.msg || '获取资源失败')
        }
      }).catch(err => {
        console.error('加载资源失败:', err)
        console.error('错误详情:', err.message)
        console.error('后端地址:', this.$config.baseUrl)
        this.$message.error('加载资源失败: ' + (err.message || '网络错误'))
      })
    },
    searchResources() {
      this.loadResources()
    },
    contactResource(resource) {
      this.$message.success(`已发送联系请求给 ${resource.bangfufangming}`)
    },

    // 发布资源方法
    handleResourceImageSuccess(response) {
      this.resourceForm.zizhengzhaopian = response.file
    },
    submitResource() {
      this.$refs.resourceForm.validate(valid => {
        if (valid) {
          const submitData = { ...this.resourceForm }
          submitData.ziyuanbianhao = 'ZY' + Date.now()
          submitData.sfsh = '待审核'
          
          this.$http.post('/bangfuziyuan/add', submitData).then(res => {
            if (res.data.code === 0) {
              this.$message.success('资源提交成功，等待审核')
              this.resetResourceForm()
              this.activeTab = 'resources'
              this.loadResources()
            } else {
              this.$message.error(res.data.msg || '提交失败')
            }
          }).catch(err => {
            console.error('提交资源失败:', err)
            this.$message.error('提交失败，请稍后重试')
          })
        }
      })
    },
    resetResourceForm() {
      this.$refs.resourceForm.resetFields()
      this.resourceImageList = []
    },

    // 我的需求方法
    loadMyNeeds() {
      const userInfo = JSON.parse(localStorage.getItem('sessionForm') || '{}')
      const account = userInfo.yonghuzhanghao || userInfo.nonghuzhanghao
      if (!account) {
        console.log('用户未登录，跳过加载需求')
        return
      }
      this.$http.get('/bangfuxuqiu/list', {
        params: {
          shenqingrenzhanghao: account
        }
      }).then(res => {
        if (res.data.code === 0) {
          this.myNeeds = res.data.data.list
        }
      }).catch(err => {
        console.error('加载需求失败:', err)
      })
    },
    getStatusClass(status) {
      const statusMap = {
        '待审核': 'status-pending',
        '已通过': 'status-approved',
        '已拒绝': 'status-rejected'
      }
      return statusMap[status] || ''
    },

    // 发布需求方法
    handleNeedImageSuccess(response) {
      this.needForm.xuqiutupian = response.file
    },
    submitNeed() {
      this.$refs.needForm.validate(valid => {
        if (valid) {
          const userInfo = JSON.parse(localStorage.getItem('sessionForm') || '{}')
          if (!userInfo.yonghuzhanghao && !userInfo.nonghuzhanghao) {
            this.$message.warning('请先登录')
            return
          }
          
          const submitData = { ...this.needForm }
          submitData.xuqiubianhao = 'XQ' + Date.now()
          submitData.shenqingrenzhanghao = userInfo.yonghuzhanghao || userInfo.nonghuzhanghao
          submitData.shenqingrenxingming = userInfo.yonghuxingming || userInfo.nonghuxingming
          submitData.shenqingridi = new Date().toISOString().split('T')[0]
          submitData.sfsh = '待审核'
          submitData.userid = userInfo.id
          
          // 处理日期格式
          if (submitData.qiwangshijian) {
            submitData.qiwangshijian = new Date(submitData.qiwangshijian).toISOString().split('T')[0]
          }
          
          this.$http.post('/bangfuxuqiu/add', submitData).then(res => {
            if (res.data.code === 0) {
              this.$message.success('需求提交成功，等待审核')
              this.resetNeedForm()
              this.activeTab = 'myNeeds'
              this.loadMyNeeds()
            } else {
              this.$message.error(res.data.msg || '提交失败')
            }
          }).catch(err => {
            console.error('提交需求失败:', err)
            this.$message.error('提交失败，请稍后重试')
          })
        }
      })
    },
    resetNeedForm() {
      this.$refs.needForm.resetFields()
      this.needImageList = []
    },

    // 对接管理方法
    loadManagementData() {
      // 加载待审核资源
      this.$http.get('/bangfuziyuan/list', { params: { sfsh: '待审核' } }).then(res => {
        if (res.data.code === 0) {
          this.pendingResources = res.data.data.list
        }
      }).catch(err => {
        console.error('加载待审核资源失败:', err)
      })
      // 加载待审核需求
      this.$http.get('/bangfuxuqiu/list', { params: { sfsh: '待审核' } }).then(res => {
        if (res.data.code === 0) {
          this.pendingNeeds = res.data.data.list
        }
      }).catch(err => {
        console.error('加载待审核需求失败:', err)
      })
      // 加载对接实施
      this.$http.get('/bangfushishi/list').then(res => {
        if (res.data.code === 0) {
          this.implementations = res.data.data.list
        }
      }).catch(err => {
        console.error('加载对接实施失败:', err)
      })
    },
    viewResourceDetail(row) {
      this.$message.info('查看资源详情：' + row.ziyuanbianhao)
    },
    approveResource(row) {
      const updateData = { ...row, sfsh: '已通过' }
      this.$http.post('/bangfuziyuan/update', updateData).then(res => {
        if (res.data.code === 0) {
          this.$message.success('已通过审核')
          this.loadManagementData()
        } else {
          this.$message.error(res.data.msg || '操作失败')
        }
      }).catch(err => {
        console.error('审核资源失败:', err)
        this.$message.error('操作失败')
      })
    },
    rejectResource(row) {
      const updateData = { ...row, sfsh: '已拒绝' }
      this.$http.post('/bangfuziyuan/update', updateData).then(res => {
        if (res.data.code === 0) {
          this.$message.success('已拒绝')
          this.loadManagementData()
        } else {
          this.$message.error(res.data.msg || '操作失败')
        }
      }).catch(err => {
        console.error('拒绝资源失败:', err)
        this.$message.error('操作失败')
      })
    },
    viewNeedDetail(row) {
      this.$message.info('查看需求详情：' + row.xuqiubianhao)
    },
    approveNeed(row) {
      const updateData = { ...row, sfsh: '已通过' }
      this.$http.post('/bangfuxuqiu/update', updateData).then(res => {
        if (res.data.code === 0) {
          this.$message.success('已通过审核')
          this.loadManagementData()
        } else {
          this.$message.error(res.data.msg || '操作失败')
        }
      }).catch(err => {
        console.error('审核需求失败:', err)
        this.$message.error('操作失败')
      })
    },
    rejectNeed(row) {
      const updateData = { ...row, sfsh: '已拒绝' }
      this.$http.post('/bangfuxuqiu/update', updateData).then(res => {
        if (res.data.code === 0) {
          this.$message.success('已拒绝')
          this.loadManagementData()
        } else {
          this.$message.error(res.data.msg || '操作失败')
        }
      }).catch(err => {
        console.error('拒绝需求失败:', err)
        this.$message.error('操作失败')
      })
    },
    viewImplementation(row) {
      this.$message.info('查看实施详情：' + row.shishibianhao)
    },
    updateProgress(row) {
      this.$message.info('更新进度：' + row.shishibianhao)
    },

    // 我的评价方法
    loadReviews() {
      const userInfo = JSON.parse(localStorage.getItem('sessionForm') || '{}')
      if (!userInfo.id) {
        console.log('用户未登录，跳过加载评价')
        return
      }
      this.$http.get('/bangfupingjia/list', {
        params: { userid: userInfo.id }
      }).then(res => {
        if (res.data.code === 0) {
          this.myReviews = res.data.data.list
        }
      }).catch(err => {
        console.error('加载评价失败:', err)
      })
    },

    // 通用方法
    formatDate(date) {
      if (!date) return ''
      return new Date(date).toLocaleDateString('zh-CN')
    },
    truncate(text, length) {
      if (!text) return ''
      return text.length > length ? text.substring(0, length) + '...' : text
    }
  }
}
</script>

<style scoped>
.page {
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #e4e8ec 100%);
}

/* 标签页导航 */
.tab-nav {
  display: flex;
  justify-content: center;
  gap: 10px;
  padding: 20px 10%;
  background: #fff;
  border-bottom: 1px solid #e8e8e8;
  flex-wrap: wrap;
}

.tab-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px 24px;
  border-radius: 25px;
  background: #f8faf8;
  border: 1px solid #e8e8e8;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 14px;
  color: #555;
}

.tab-item:hover {
  background: rgba(46, 125, 50, 0.1);
  border-color: #2E7D32;
  color: #2E7D32;
}

.tab-item.active {
  background: linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%);
  color: #fff;
  border-color: transparent;
  box-shadow: 0 4px 15px rgba(46, 125, 50, 0.3);
}

/* 标签页内容 */
.tab-content {
  padding: 30px 10%;
}

.tab-panel {
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}

/* 搜索和筛选 */
.filter-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  flex-wrap: wrap;
  gap: 15px;
}

.search-bar {
  display: flex;
  gap: 10px;
}

.search-bar input {
  padding: 12px 20px;
  border: 1px solid #e8e8e8;
  border-radius: 25px;
  width: 300px;
  font-size: 14px;
  transition: all 0.3s ease;
}

.search-bar input:focus {
  outline: none;
  border-color: #2E7D32;
  box-shadow: 0 0 0 3px rgba(46, 125, 50, 0.1);
}

.search-btn {
  padding: 12px 30px;
  background: linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%);
  color: #fff;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s ease;
}

.search-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 15px rgba(46, 125, 50, 0.3);
}

.filter-options {
  display: flex;
  gap: 10px;
}

.filter-options select {
  padding: 10px 20px;
  border: 1px solid #e8e8e8;
  border-radius: 20px;
  background: #fff;
  font-size: 14px;
  cursor: pointer;
}

/* 项目列表 */
.project-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 25px;
}

.project-item {
  background: #fff;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
  cursor: pointer;
  transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
}

.project-item:hover {
  transform: translateY(-8px);
  box-shadow: 0 12px 30px rgba(46, 125, 50, 0.15);
}

.project-image {
  width: 100%;
  height: 200px;
  overflow: hidden;
}

.project-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.6s ease;
}

.project-item:hover .project-image img {
  transform: scale(1.1);
}

.project-info {
  padding: 20px;
}

.project-title {
  font-size: 18px;
  font-weight: bold;
  color: #1a1a1a;
  margin-bottom: 10px;
}

.project-meta {
  display: flex;
  gap: 15px;
  margin-bottom: 10px;
}

.project-type {
  background: linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%);
  color: #fff;
  padding: 4px 12px;
  border-radius: 15px;
  font-size: 12px;
}

.project-date {
  color: #999;
  font-size: 13px;
}

.project-desc {
  color: #666;
  font-size: 14px;
  line-height: 1.6;
  margin-bottom: 15px;
}

.project-contact {
  display: flex;
  gap: 15px;
  margin-bottom: 10px;
  font-size: 13px;
  color: #555;
}

.project-stats {
  font-size: 13px;
  color: #999;
}

/* 资源卡片 */
.resource-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(400px, 1fr));
  gap: 25px;
}

.resource-card {
  background: #fff;
  border-radius: 16px;
  padding: 25px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
}

.resource-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 25px rgba(46, 125, 50, 0.15);
}

.resource-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.resource-header h3 {
  font-size: 18px;
  color: #1a1a1a;
}

.resource-type {
  background: linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%);
  color: #fff;
  padding: 4px 12px;
  border-radius: 15px;
  font-size: 12px;
}

.resource-body {
  margin-bottom: 15px;
}

.resource-body p {
  margin-bottom: 8px;
  font-size: 14px;
  color: #555;
}

.resource-content {
  color: #666;
  line-height: 1.6;
}

.resource-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 15px;
  border-top: 1px solid #e8e8e8;
  font-size: 13px;
  color: #666;
}

.contact-btn {
  padding: 8px 20px;
  background: linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%);
  color: #fff;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  font-size: 13px;
  transition: all 0.3s ease;
}

.contact-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(46, 125, 50, 0.3);
}

/* 表单容器 */
.form-container {
  max-width: 800px;
  margin: 0 auto;
  background: #fff;
  padding: 40px;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
}

.form-title {
  text-align: center;
  font-size: 24px;
  color: #1a1a1a;
  margin-bottom: 30px;
}

/* 需求卡片 */
.needs-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(400px, 1fr));
  gap: 25px;
}

.need-card {
  background: #fff;
  border-radius: 16px;
  padding: 25px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
}

.need-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.need-header h3 {
  font-size: 16px;
  color: #1a1a1a;
}

.need-status {
  padding: 4px 12px;
  border-radius: 15px;
  font-size: 12px;
}

.status-pending {
  background: #fff3cd;
  color: #856404;
}

.status-approved {
  background: #d4edda;
  color: #155724;
}

.status-rejected {
  background: #f8d7da;
  color: #721c24;
}

.need-body {
  margin-bottom: 15px;
}

.need-body p {
  margin-bottom: 8px;
  font-size: 14px;
  color: #555;
}

.need-footer {
  padding-top: 15px;
  border-top: 1px solid #e8e8e8;
  font-size: 13px;
  color: #666;
}

/* 评价卡片 */
.review-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(400px, 1fr));
  gap: 25px;
}

.review-card {
  background: #fff;
  border-radius: 16px;
  padding: 25px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
}

.review-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.review-header h3 {
  font-size: 16px;
  color: #1a1a1a;
}

.review-score {
  background: linear-gradient(135deg, #FFD700 0%, #FFA500 100%);
  color: #fff;
  padding: 6px 15px;
  border-radius: 20px;
  font-size: 14px;
  font-weight: bold;
}

.score-detail {
  display: flex;
  gap: 20px;
  margin-bottom: 15px;
  font-size: 13px;
  color: #666;
}

.review-content {
  color: #555;
  line-height: 1.6;
  margin-bottom: 15px;
}

.review-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 15px;
  border-top: 1px solid #e8e8e8;
  font-size: 13px;
  color: #999;
}

.archive-status {
  padding: 4px 12px;
  border-radius: 15px;
  font-size: 12px;
  background: #f0f0f0;
  color: #666;
}

.archive-status.archived {
  background: linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%);
  color: #fff;
}

/* 空状态 */
.empty {
  text-align: center;
  padding: 60px 20px;
  color: #999;
}

.empty p {
  margin: 20px 0;
  font-size: 16px;
}

.refresh-btn, .publish-btn {
  padding: 12px 30px;
  background: linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%);
  color: #fff;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s ease;
}

.refresh-btn:hover, .publish-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 15px rgba(46, 125, 50, 0.3);
}

/* 加载状态 */
.loading {
  text-align: center;
  padding: 40px;
  color: #999;
}

/* 分页 */
.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 20px;
  margin-top: 40px;
}

.page-btn {
  padding: 10px 25px;
  background: #fff;
  border: 1px solid #e8e8e8;
  border-radius: 20px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s ease;
}

.page-btn:hover:not(:disabled) {
  border-color: #2E7D32;
  color: #2E7D32;
}

.page-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.page-info {
  font-size: 14px;
  color: #666;
}

/* 管理标签页 */
.management-tabs {
  background: #fff;
  border-radius: 16px;
  padding: 20px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
}
</style>
