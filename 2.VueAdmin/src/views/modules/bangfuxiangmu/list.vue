<template>
  <div class="main-content" :style='{"color":"#666","padding":"0px 30px 30px","fontSize":"14px"}'>
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
            <el-option label="技术" value="技术"></el-option>
            <el-option label="采购" value="采购"></el-option>
            <el-option label="资金" value="资金"></el-option>
            <el-option label="农机" value="农机"></el-option>
            <el-option label="其他" value="其他"></el-option>
          </el-select>
        </div>
        <el-button class="search" type="success" icon="el-icon-search" @click="search()">查询</el-button>
        <el-button type="primary" icon="el-icon-plus" @click="addOrUpdateHandle()">新增</el-button>
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
        <el-table-column label="操作" width="220" fixed="right">
          <template slot-scope="scope">
            <el-button type="info" size="mini" icon="el-icon-view" @click="viewDetail(scope.row)">查看</el-button>
            <el-button type="primary" size="mini" icon="el-icon-edit" @click="addOrUpdateHandle(scope.row.id)">编辑</el-button>
            <el-button type="danger" size="mini" icon="el-icon-delete" @click="deleteHandle(scope.row.id)">删除</el-button>
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
  </div>
</template>

<script>
import AddOrUpdate from './add-or-update'
export default {
  components: { AddOrUpdate },
  data() {
    return {
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
      detailRow: {}
    }
  },
  created() {
    this.getDataList()
  },
  methods: {
    search() {
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
      this.$http({ url: 'bangfuxiangmu/page', method: 'get', params }).then(({ data }) => {
        if (data && data.code === 0) {
          this.dataList = data.data.list || []
          this.totalPage = data.data.total || 0
        } else {
          this.dataList = []
          this.totalPage = 0
        }
        this.dataListLoading = false
      })
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
    deleteHandle(id) {
      this.$confirm('确定删除该项目吗？删除后不可恢复！', '删除确认', { type: 'warning' }).then(() => {
        this.$http({ url: 'bangfuxiangmu/delete', method: 'post', data: [id] }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message.success('删除成功')
            this.getDataList()
          } else {
            this.$message.error((data && data.msg) || '删除失败')
          }
        })
      }).catch(() => {})
    }
  }
}
</script>
