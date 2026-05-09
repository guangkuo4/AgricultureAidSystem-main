<template>
  <div class="main-content" :style='{"color":"#666","padding":"0px 30px 30px","fontSize":"14px"}'>
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

    <!-- 帮扶项目管理 -->
    <div v-if="activeTab === 'projects'" class="tab-panel">
      <!-- 搜索栏 -->
      <el-form class="center-form-pv" :inline="true" :model="searchForm"
               :style='{"padding":"0px 0px 0","margin":"0px","overflow":"hidden","flexWrap":"wrap","background":"none","display":"flex","fontSize":"inherit"}'>
        <el-row :style='{"padding":"0px","margin":"0 0 20px","borderRadius":"0px","textAlign":"left","background":"none","display":"block","width":"100%","fontSize":"inherit","order":"2"}'>
          <div :style='{"margin":"0 10px 0 0","fontSize":"inherit","display":"inline-block"}'>
            <label class="item-label" :style='{"margin":"0 10px 0 0","color":"inherit","display":"inline-block","lineHeight":"40px","fontSize":"inherit","fontWeight":"500","height":"40px"}'>项目名称</label>
            <el-input v-model="searchForm.xiangmumingcheng" placeholder="项目名称" @keydown.enter.native="search()" clearable></el-input>
          </div>
          <div :style='{"margin":"0 10px 0 0","fontSize":"inherit","display":"inline-block"}'>
            <label class="item-label" :style='{"margin":"0 10px 0 0","color":"inherit","display":"inline-block","lineHeight":"40px","fontSize":"inherit","fontWeight":"500","height":"40px"}'>项目类型</label>
            <el-select clearable v-model="searchForm.xiangmuleixing" placeholder="请选择" style="width:150px;">
              <el-option label="全部" value=""></el-option>
              <el-option label="技术" value="技术"></el-option>
              <el-option label="采购" value="采购"></el-option>
              <el-option label="资金" value="资金"></el-option>
              <el-option label="农机" value="农机"></el-option>
              <el-option label="其他" value="其他"></el-option>
            </el-select>
          </div>
          <el-button class="search" type="success" icon="el-icon-search" @click="search()">查询</el-button>
          <el-button type="primary" icon="el-icon-refresh" @click="loadData()">刷新</el-button>
        </el-row>
      </el-form>

      <!-- 表格 -->
      <div :style='{"border":"0px solid #eee","width":"100%","padding":"0","margin":"0px 0 0","borderRadius":"12px","background":"rgba(255,255,255,.9)"}'>
        <el-table class="tables"
                  :stripe='false'
                  :border='true'
                  :data="dataList"
                  v-loading="dataListLoading"
                  @selection-change="selectionChangeHandler"
                  :style='{"padding":"0","borderColor":"#edf7ff","color":"inherit","borderRadius":"12px","borderWidth":"0px 0px 0 0px","background":"none","width":"100%","fontSize":"inherit"}'>
          <el-table-column type="selection" align="center" width="50"></el-table-column>
          <el-table-column label="序号" type="index" width="50"></el-table-column>
          <el-table-column prop="xiangmubianhao" label="项目编号" width="150"></el-table-column>
          <el-table-column prop="xiangmumingcheng" label="项目名称" min-width="180"></el-table-column>
          <el-table-column prop="xiangmuleixing" label="项目类型" width="100"></el-table-column>
          <el-table-column prop="faburiqi" label="发布日期" width="120"></el-table-column>
          <el-table-column prop="lianxiren" label="联系人" width="100"></el-table-column>
          <el-table-column prop="lianxidianhua" label="联系电话" width="130"></el-table-column>
          <el-table-column prop="clicknum" label="浏览次数" width="100"></el-table-column>
          <el-table-column label="操作" width="150" fixed="right">
            <template slot-scope="scope">
              <div class="btn-group">
                <el-button type="info" size="mini" icon="el-icon-view" @click="viewDetail(scope.row)">查看</el-button>
                <el-button type="danger" size="mini" icon="el-icon-delete" @click="deleteHandle(scope.row)">删除</el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <!-- 分页 -->
      <el-pagination
        @size-change="sizeChangeHandle"
        @current-change="currentChangeHandle"
        :current-page="pageIndex"
        background
        :page-sizes="[10, 20, 30, 50]"
        :page-size="pageSize"
        :layout="layouts.join()"
        :total="totalPage"
        prev-text="上一页"
        next-text="下一页"
        :hide-on-single-page="false"
        :style='{"padding":"0","margin":"20px 0 0","whiteSpace":"nowrap","color":"inherit","textAlign":"right","width":"100%","fontSize":"inherit","fontWeight":"500"}'
      />
    </div>

    <!-- 待审核资源 -->
    <div v-if="activeTab === 'pendingResources'" class="tab-panel">
      <div :style='{"border":"0px solid #eee","width":"100%","padding":"0","margin":"0px 0 0","borderRadius":"12px","background":"rgba(255,255,255,.9)"}'>
        <el-table class="tables"
                  :stripe='false'
                  :border='true'
                  :data="pendingResources"
                  v-loading="resourcesLoading"
                  :style='{"padding":"0","borderColor":"#edf7ff","color":"inherit","borderRadius":"12px","borderWidth":"0px 0px 0 0px","background":"none","width":"100%","fontSize":"inherit"}'>
          <el-table-column prop="ziyuanbianhao" label="资源编号" width="150"></el-table-column>
          <el-table-column prop="bangfufangming" label="帮扶方名称" min-width="180"></el-table-column>
          <el-table-column prop="bangfuleixing" label="帮扶类型" width="120"></el-table-column>
          <el-table-column prop="suozaidiqu" label="所在地区" width="120"></el-table-column>
          <el-table-column prop="shanchanglingyu" label="擅长领域" width="150"></el-table-column>
          <el-table-column prop="lianxiren" label="联系人" width="100"></el-table-column>
          <el-table-column prop="lianxidianhua" label="联系电话" width="130"></el-table-column>
          <el-table-column label="操作" width="220" fixed="right">
            <template slot-scope="scope">
              <div class="btn-group">
                <el-button type="info" size="mini" icon="el-icon-view" @click="viewResourceDetail(scope.row)">查看</el-button>
                <el-button type="success" size="mini" icon="el-icon-check" @click="approveResource(scope.row)">通过</el-button>
                <el-button type="danger" size="mini" icon="el-icon-close" @click="rejectResource(scope.row)">拒绝</el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-if="pendingResources.length === 0 && !resourcesLoading" class="empty">暂无待审核资源</div>
    </div>

    <!-- 帮扶资源池 -->
    <div v-if="activeTab === 'resourcePool'" class="tab-panel">
      <div :style='{"border":"0px solid #eee","width":"100%","padding":"0","margin":"0px 0 0","borderRadius":"12px","background":"rgba(255,255,255,.9)"}'>
        <el-table class="tables"
                  :stripe='false'
                  :border='true'
                  :data="resourcePool"
                  v-loading="resourcePoolLoading"
                  :style='{"padding":"0","borderColor":"#edf7ff","color":"inherit","borderRadius":"12px","borderWidth":"0px 0px 0 0px","background":"none","width":"100%","fontSize":"inherit"}'>
          <el-table-column prop="ziyuanbianhao" label="资源编号" width="150"></el-table-column>
          <el-table-column prop="bangfufangming" label="帮扶方名称" min-width="180"></el-table-column>
          <el-table-column prop="bangfuleixing" label="帮扶类型" width="120"></el-table-column>
          <el-table-column prop="suozaidiqu" label="所在地区" width="120"></el-table-column>
          <el-table-column prop="shanchanglingyu" label="擅长领域" width="150"></el-table-column>
          <el-table-column prop="lianxiren" label="联系人" width="100"></el-table-column>
          <el-table-column prop="lianxidianhua" label="联系电话" width="130"></el-table-column>
          <el-table-column prop="faburiqi" label="发布日期" width="120"></el-table-column>
          <el-table-column label="操作" width="150" fixed="right">
            <template slot-scope="scope">
              <div class="btn-group">
                <el-button type="info" size="mini" icon="el-icon-view" @click="viewResourceDetail(scope.row)">查看</el-button>
                <el-button type="danger" size="mini" icon="el-icon-delete" @click="deleteResource(scope.row)">删除</el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-if="resourcePool.length === 0 && !resourcePoolLoading" class="empty">暂无帮扶资源</div>
    </div>

    <!-- 待审核需求 -->
    <div v-if="activeTab === 'pendingNeeds'" class="tab-panel">
      <div :style='{"border":"0px solid #eee","width":"100%","padding":"0","margin":"0px 0 0","borderRadius":"12px","background":"rgba(255,255,255,.9)"}'>
        <el-table class="tables"
                  :stripe='false'
                  :border='true'
                  :data="pendingNeeds"
                  v-loading="needsLoading"
                  :style='{"padding":"0","borderColor":"#edf7ff","color":"inherit","borderRadius":"12px","borderWidth":"0px 0px 0 0px","background":"none","width":"100%","fontSize":"inherit"}'>
          <el-table-column prop="xuqiubianhao" label="需求编号" width="150"></el-table-column>
          <el-table-column prop="xuqiumingcheng" label="需求名称" min-width="180"></el-table-column>
          <el-table-column prop="shenqingrenxingming" label="申请人" width="120"></el-table-column>
          <el-table-column prop="xuqiuleixing" label="需求类型" width="120"></el-table-column>
          <el-table-column prop="xuqiujinji" label="紧急程度" width="100"></el-table-column>
          <el-table-column prop="shenqingridi" label="申请日期" width="120"></el-table-column>
          <el-table-column prop="xuqiumiaoshu" label="需求描述" min-width="200"></el-table-column>
          <el-table-column label="操作" width="220" fixed="right">
            <template slot-scope="scope">
              <div class="btn-group">
                <el-button type="info" size="mini" icon="el-icon-view" @click="viewNeedDetail(scope.row)">查看</el-button>
                <el-button type="success" size="mini" icon="el-icon-check" @click="approveNeed(scope.row)">通过</el-button>
                <el-button type="danger" size="mini" icon="el-icon-close" @click="rejectNeed(scope.row)">拒绝</el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-if="pendingNeeds.length === 0 && !needsLoading" class="empty">暂无待审核需求</div>
    </div>

    <!-- 对接实施管理 -->
    <div v-if="activeTab === 'implementations'" class="tab-panel">
      <div :style='{"border":"0px solid #eee","width":"100%","padding":"0","margin":"0px 0 0","borderRadius":"12px","background":"rgba(255,255,255,.9)"}'>
        <el-table class="tables"
                  :stripe='false'
                  :border='true'
                  :data="implementations"
                  v-loading="implementationsLoading"
                  :style='{"padding":"0","borderColor":"#edf7ff","color":"inherit","borderRadius":"12px","borderWidth":"0px 0px 0 0px","background":"none","width":"100%","fontSize":"inherit"}'>
          <el-table-column prop="shenqingbianhao" label="申请编号" width="150"></el-table-column>
          <el-table-column prop="xiangmumingcheng" label="项目名称" min-width="180"></el-table-column>
          <el-table-column prop="xiangmuleixing" label="项目类型" width="120"></el-table-column>
          <el-table-column prop="bangfuzhuangtai" label="帮扶状态" width="120">
            <template slot-scope="scope">
              <el-tag :type="getStatusType(scope.row.bangfuzhuangtai)">{{ scope.row.bangfuzhuangtai || '待分配' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="bangfujindu" label="帮扶进度" min-width="200"></el-table-column>
          <el-table-column prop="shenqingriqi" label="申请日期" width="120"></el-table-column>
          <el-table-column label="操作" width="120" fixed="right">
            <template slot-scope="scope">
              <div class="btn-group">
                <el-button type="info" size="mini" icon="el-icon-view" @click="viewImplementation(scope.row)">查看</el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-if="implementations.length === 0 && !implementationsLoading" class="empty">暂无对接实施记录</div>
    </div>

    <!-- 新增/编辑弹窗 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>

    <!-- 详情弹窗 -->
    <el-dialog title="帮扶项目详情" :visible.sync="viewVisible" width="720px" :close-on-click-modal="false">
      <el-descriptions :column="2" border size="small">
        <el-descriptions-item label="项目编号">{{ detailRow.xiangmubianhao }}</el-descriptions-item>
        <el-descriptions-item label="项目名称">{{ detailRow.xiangmumingcheng }}</el-descriptions-item>
        <el-descriptions-item label="项目类型">{{ detailRow.xiangmuleixing }}</el-descriptions-item>
        <el-descriptions-item label="发布日期">{{ detailRow.faburiqi }}</el-descriptions-item>
        <el-descriptions-item label="联系人">{{ detailRow.lianxiren }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ detailRow.lianxidianhua }}</el-descriptions-item>
        <el-descriptions-item label="项目详情" :span="2">
          <div v-html="detailRow.xiangmuxiangqing" style="max-height:300px;overflow-y:auto;line-height:1.8;"></div>
        </el-descriptions-item>
        <el-descriptions-item label="项目图片" :span="2" v-if="detailRow.tupian">
          <el-image
            v-if="detailRow.tupian && detailRow.tupian.indexOf('http')===0"
            :src="detailRow.tupian" fit="cover" style="width:120px;height:80px;"
            :preview-src-list="[detailRow.tupian]"></el-image>
          <el-image
            v-else-if="detailRow.tupian"
            :src="$base.url+detailRow.tupian" fit="cover" style="width:120px;height:80px;"
            :preview-src-list="[$base.url+detailRow.tupian]"></el-image>
          <span v-else>无</span>
        </el-descriptions-item>
        <el-descriptions-item label="浏览次数">{{ detailRow.clicknum || 0 }} 次</el-descriptions-item>
      </el-descriptions>
      <span slot="footer"><el-button type="primary" @click="viewVisible=false">关闭</el-button></span>
    </el-dialog>

    <!-- 资源详情弹窗 -->
    <el-dialog title="帮扶资源详情" :visible.sync="resourceDetailVisible" width="720px" :close-on-click-modal="false">
      <el-descriptions :column="2" border size="small" v-if="selectedResource">
        <el-descriptions-item label="资源编号">{{ selectedResource.ziyuanbianhao }}</el-descriptions-item>
        <el-descriptions-item label="帮扶方名称">{{ selectedResource.bangfufangming }}</el-descriptions-item>
        <el-descriptions-item label="帮扶类型">{{ selectedResource.bangfuleixing }}</el-descriptions-item>
        <el-descriptions-item label="帮扶方类型">{{ selectedResource.bangfufangleixing }}</el-descriptions-item>
        <el-descriptions-item label="所在地区">{{ selectedResource.suozaidiqu }}</el-descriptions-item>
        <el-descriptions-item label="擅长领域">{{ selectedResource.shanchanglingyu }}</el-descriptions-item>
        <el-descriptions-item label="响应时效">{{ selectedResource.xiangyingshixiao }}</el-descriptions-item>
        <el-descriptions-item label="联系人">{{ selectedResource.lianxiren }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ selectedResource.lianxidianhua }}</el-descriptions-item>
        <el-descriptions-item label="帮扶内容" :span="2">{{ selectedResource.bangfuneirong }}</el-descriptions-item>
      </el-descriptions>
      <span slot="footer"><el-button type="primary" @click="resourceDetailVisible=false">关闭</el-button></span>
    </el-dialog>

    <!-- 需求详情弹窗 -->
    <el-dialog title="帮扶需求详情" :visible.sync="needDetailVisible" width="720px" :close-on-click-modal="false">
      <el-descriptions :column="2" border size="small" v-if="selectedNeed">
        <el-descriptions-item label="需求编号">{{ selectedNeed.xuqiubianhao }}</el-descriptions-item>
        <el-descriptions-item label="需求名称">{{ selectedNeed.xuqiumingcheng }}</el-descriptions-item>
        <el-descriptions-item label="需求类型">{{ selectedNeed.xuqiuleixing }}</el-descriptions-item>
        <el-descriptions-item label="申请人">{{ selectedNeed.shenqingrenxingming }}</el-descriptions-item>
        <el-descriptions-item label="联系方式">{{ selectedNeed.lianxidianhua }}</el-descriptions-item>
        <el-descriptions-item label="紧急程度">{{ selectedNeed.xuqiujinji }}</el-descriptions-item>
        <el-descriptions-item label="申请日期">{{ selectedNeed.shenqingridi }}</el-descriptions-item>
        <el-descriptions-item label="期望时间">{{ selectedNeed.qiwangshijian }}</el-descriptions-item>
        <el-descriptions-item label="所在省份">{{ selectedNeed.suozaidiqu }}</el-descriptions-item>
        <el-descriptions-item label="具体地址">{{ selectedNeed.jutidizhi }}</el-descriptions-item>
        <el-descriptions-item label="需求描述" :span="2">{{ selectedNeed.xuqiumiaoshu }}</el-descriptions-item>
        <el-descriptions-item label="审核状态">
          <el-tag :type="selectedNeed.sfsh === '已通过' ? 'success' : selectedNeed.sfsh === '已拒绝' ? 'danger' : 'warning'">
            {{ selectedNeed.sfsh }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="审核回复" :span="2">{{ selectedNeed.shhf || '暂无' }}</el-descriptions-item>
      </el-descriptions>
      <span slot="footer"><el-button type="primary" @click="needDetailVisible=false">关闭</el-button></span>
    </el-dialog>

    <!-- 对接实施详情弹窗 -->
    <el-dialog title="对接实施详情" :visible.sync="implementationDetailVisible" width="720px" :close-on-click-modal="false">
      <el-descriptions :column="2" border size="small" v-if="selectedImplementation">
        <el-descriptions-item label="申请编号">{{ selectedImplementation.shenqingbianhao }}</el-descriptions-item>
        <el-descriptions-item label="项目名称">{{ selectedImplementation.xiangmumingcheng }}</el-descriptions-item>
        <el-descriptions-item label="项目类型">{{ selectedImplementation.xiangmuleixing }}</el-descriptions-item>
        <el-descriptions-item label="申请人账号">{{ selectedImplementation.shenqingrenzhanghao }}</el-descriptions-item>
        <el-descriptions-item label="申请人姓名">{{ selectedImplementation.shenqingrenxingming }}</el-descriptions-item>
        <el-descriptions-item label="申请日期">{{ selectedImplementation.shenqingriqi }}</el-descriptions-item>
        <el-descriptions-item label="审核状态">
          <el-tag :type="selectedImplementation.sfsh === '通过' ? 'success' : 'warning'">{{ selectedImplementation.sfsh }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="机构/个人名称">{{ selectedImplementation.jigoumingcheng }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ selectedImplementation.lianxidianhua || '无' }}</el-descriptions-item>
        <el-descriptions-item label="帮扶状态">
          <el-tag :type="getStatusType(selectedImplementation.bangfuzhuangtai)">{{ selectedImplementation.bangfuzhuangtai || '待分配' }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="帮扶进度" :span="2">
          <div v-if="selectedImplementation.bangfujindu" class="progress-content">{{ selectedImplementation.bangfujindu }}</div>
          <span v-else>暂无进度</span>
        </el-descriptions-item>
        <el-descriptions-item label="对接说明" :span="2">{{ selectedImplementation.shenqingshuoming }}</el-descriptions-item>
        <el-descriptions-item label="审核回复" :span="2">{{ selectedImplementation.shhf || '暂无' }}</el-descriptions-item>
      </el-descriptions>
      <span slot="footer"><el-button type="primary" @click="implementationDetailVisible=false">关闭</el-button></span>
    </el-dialog>
  </div>
</template>

<script>
import AddOrUpdate from './add-or-update'
export default {
  components: { AddOrUpdate },
  data() {
    return {
      activeTab: 'projects',
      tabs: [
        { key: 'projects', label: '帮扶项目', icon: 'el-icon-suitcase' },
        { key: 'pendingNeeds', label: '待审核需求', icon: 'el-icon-document' },
        { key: 'implementations', label: '对接实施', icon: 'el-icon-s-management' },
        { key: 'resourcePool', label: '帮扶资源池', icon: 'el-icon-bank-card' },
        { key: 'pendingResources', label: '待审核资源', icon: 'el-icon-collection' }
      ],
      searchForm: { xiangmumingcheng: '', xiangmuleixing: '' },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      layouts: ['prev', 'pager', 'next', 'jumper'],
      addOrUpdateVisible: false,
      viewVisible: false,
      detailRow: {},

      // 待审核资源
      pendingResources: [],
      resourcesLoading: false,

      // 帮扶资源池
      resourcePool: [],
      resourcePoolLoading: false,

      // 待审核需求
      pendingNeeds: [],
      needsLoading: false,

      // 对接实施
      implementations: [],
      implementationsLoading: false,

      // 详情弹窗
      resourceDetailVisible: false,
      selectedResource: {},
      needDetailVisible: false,
      selectedNeed: {},

      // 对接实施详情
      implementationDetailVisible: false,
      selectedImplementation: {}
    }
  },
  created() {
    this.getDataList()
  },
  watch: {
    activeTab(val) {
      if (val === 'projects') this.getDataList()
      if (val === 'pendingResources') this.loadPendingResources()
      if (val === 'resourcePool') this.loadResourcePool()
      if (val === 'pendingNeeds') this.loadPendingNeeds()
      if (val === 'implementations') this.loadImplementations()
    }
  },
  methods: {
    // 项目管理方法
    search() {
      this.pageIndex = 1
      this.getDataList()
    },
    loadData() {
      this.pageIndex = 1
      this.getDataList()
    },
    getDataList() {
      this.dataListLoading = true
      const params = {
        page: this.pageIndex,
        limit: this.pageSize,
        sort: 'faburiqi',
        order: 'desc'
      }
      if (this.searchForm.xiangmumingcheng) params.xiangmumingcheng = '%' + this.searchForm.xiangmumingcheng + '%'
      if (this.searchForm.xiangmuleixing) params.xiangmuleixing = this.searchForm.xiangmuleixing
      
      Promise.all([
        this.$http({ url: 'bangfuxiangmu/page', method: 'get', params }),
        this.$http({ url: 'bangfuxuqiu/list', method: 'get', params: { sfsh: '已通过', page: 1, limit: 100 } })
      ]).then(([projectRes, needRes]) => {
        let dataList = []
        let totalPage = 0
        
        if (projectRes.data && projectRes.data.code === 0) {
          dataList = projectRes.data.data.list || []
          totalPage = projectRes.data.data.total || 0
        }
        
        if (needRes.data && needRes.data.code === 0) {
          const needs = needRes.data.data.list || []
          const convertedNeeds = needs.map(need => ({
            id: need.id,
            xiangmubianhao: need.xuqiubianhao,
            xiangmumingcheng: need.xuqiuleixing + '需求',
            xiangmuleixing: need.xuqiuleixing,
            faburiqi: need.shenqingridi,
            lianxiren: need.shenqingrenxingming,
            lianxidianhua: '',
            clicknum: 0
          }))
          dataList = [...dataList, ...convertedNeeds]
          totalPage += needs.length
        }
        
        dataList = this.filterData(dataList)
        
        this.dataList = dataList
        this.totalPage = dataList.length
        this.dataListLoading = false
      }).catch(() => {
        this.dataList = []
        this.totalPage = 0
        this.dataListLoading = false
      })
    },
    filterData(data) {
      let result = data
      
      if (this.searchForm.xiangmumingcheng) {
        const keyword = this.searchForm.xiangmumingcheng.toLowerCase()
        result = result.filter(item => 
          item.xiangmumingcheng && item.xiangmumingcheng.toLowerCase().includes(keyword)
        )
      }
      
      if (this.searchForm.xiangmuleixing) {
        const type = this.searchForm.xiangmuleixing
        result = result.filter(item => {
          const itemType = item.xiangmuleixing || ''
          return itemType === type || itemType.includes(type)
        })
      }
      
      return result
    },
    sizeChangeHandle(val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    currentChangeHandle(val) {
      this.pageIndex = val
      this.getDataList()
    },
    selectionChangeHandler(val) {
      this.dataListSelections = val
    },
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id)
      })
    },
    viewDetail(row) {
      this.detailRow = row || {}
      this.viewVisible = true
    },
    deleteHandle(row) {
      this.$confirm('确定删除该项目吗？删除后不可恢复！', '删除确认', { type: 'warning' }).then(() => {
        const isNeedData = row.xiangmubianhao && row.xiangmubianhao.startsWith('XQ')
        const url = isNeedData ? 'bangfuxuqiu/delete' : 'bangfuxiangmu/delete'
        
        this.$http({ url: url, method: 'post', data: [row.id] }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message.success('删除成功')
            this.getDataList()
          } else {
            this.$message.error((data && data.msg) || '删除失败')
          }
        }).catch(() => {
          this.$message.error('删除失败')
        })
      }).catch(() => {})
    },

    // 待审核资源方法
    loadPendingResources() {
      this.resourcesLoading = true
      this.$http.get('/bangfuziyuan/list', { params: { sfsh: '待审核', page: 1, limit: 200 } }).then(res => {
        if (res.data.code === 0) {
          this.pendingResources = (res.data.data && res.data.data.list) || []
        }
        this.resourcesLoading = false
      }).catch(() => {
        this.resourcesLoading = false
      })
    },

    // 帮扶资源池方法
    loadResourcePool() {
      this.resourcePoolLoading = true
      this.$http.get('/bangfuziyuan/list', { params: { sfsh: '已通过', page: 1, limit: 200 } }).then(res => {
        if (res.data.code === 0) {
          this.resourcePool = (res.data.data && res.data.data.list) || []
        }
        this.resourcePoolLoading = false
      }).catch(() => {
        this.resourcePoolLoading = false
      })
    },

    deleteResource(row) {
      this.$confirm('确定删除该资源吗？删除后不可恢复！', '删除确认', { type: 'warning' }).then(() => {
        this.$http({ url: 'bangfuziyuan/delete', method: 'post', data: [row.id] }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message.success('删除成功')
            this.loadResourcePool()
          } else {
            this.$message.error((data && data.msg) || '删除失败')
          }
        }).catch(() => {
          this.$message.error('删除失败')
        })
      }).catch(() => {})
    },
    viewResourceDetail(row) {
      this.selectedResource = row
      this.resourceDetailVisible = true
    },
    approveResource(row) {
      const updateData = { ...row, sfsh: '已通过' }
      this.$http.post('/bangfuziyuan/update', updateData).then(res => {
        if (res.data.code === 0) {
          this.$message.success('已通过审核')
          this.loadPendingResources()
        } else {
          this.$message.error(res.data.msg || '操作失败')
        }
      }).catch(() => {
        this.$message.error('操作失败')
      })
    },
    rejectResource(row) {
      const updateData = { ...row, sfsh: '已拒绝' }
      this.$http.post('/bangfuziyuan/update', updateData).then(res => {
        if (res.data.code === 0) {
          this.$message.success('已拒绝')
          this.loadPendingResources()
        } else {
          this.$message.error(res.data.msg || '操作失败')
        }
      }).catch(() => {
        this.$message.error('操作失败')
      })
    },

    // 待审核需求方法
    loadPendingNeeds() {
      this.needsLoading = true
      this.$http.get('/bangfuxuqiu/list', { params: { sfsh: '待审核', page: 1, limit: 200 } }).then(res => {
        if (res.data.code === 0) {
          this.pendingNeeds = (res.data.data && res.data.data.list) || []
        }
        this.needsLoading = false
      }).catch(() => {
        this.needsLoading = false
      })
    },
    viewNeedDetail(row) {
      this.selectedNeed = row
      this.needDetailVisible = true
    },
    approveNeed(row) {
      const updateData = { ...row, sfsh: '已通过' }
      this.$http.post('/bangfuxuqiu/update', updateData).then(res => {
        if (res.data.code === 0) {
          this.$message.success('已通过审核')
          this.loadPendingNeeds()
        } else {
          this.$message.error(res.data.msg || '操作失败')
        }
      }).catch(() => {
        this.$message.error('操作失败')
      })
    },
    rejectNeed(row) {
      const updateData = { ...row, sfsh: '已拒绝' }
      this.$http.post('/bangfuxuqiu/update', updateData).then(res => {
        if (res.data.code === 0) {
          this.$message.success('已拒绝')
          this.loadPendingNeeds()
        } else {
          this.$message.error(res.data.msg || '操作失败')
        }
      }).catch(() => {
        this.$message.error('操作失败')
      })
    },

    // 对接实施方法
    loadImplementations() {
      this.implementationsLoading = true
      this.$http.get('/duijieshenqing/list', { params: { sfsh: '通过', page: 1, limit: 200 } }).then(res => {
        if (res.data.code === 0) {
          this.implementations = (res.data.data && res.data.data.list) || []
        }
        this.implementationsLoading = false
      }).catch(() => {
        this.implementationsLoading = false
      })
    },
    viewImplementation(row) {
      this.selectedImplementation = row
      this.implementationDetailVisible = true
    },
    getStatusType(status) {
      const typeMap = {
        '待分配': 'warning',
        '进行中': 'primary',
        '已完成': 'success'
      }
      return typeMap[status] || 'info'
    }
  }
}
</script>

<style scoped>
.tab-nav {
  display: flex;
  gap: 10px;
  padding: 15px 0;
  margin-bottom: 20px;
  border-bottom: 1px solid #e8e8e8;
}

.tab-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 24px;
  border-radius: 25px;
  background: #f8f9fa;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 14px;
  color: #666;
}

.tab-item:hover {
  background: rgba(46, 125, 50, 0.1);
  color: #2E7D32;
}

.tab-item.active {
  background: linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%);
  color: #fff;
}

.tab-panel {
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}

.btn-group {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  gap: 6px;
  flex-wrap: nowrap;
}

.btn-group .el-button--mini {
  margin: 0;
  padding: 4px 12px;
  font-size: 12px;
  height: auto;
  line-height: 1.4;
}

.empty {
  text-align: center;
  padding: 60px;
  color: #999;
  background: #fff;
  border-radius: 12px;
  margin-top: 20px;
}
</style>