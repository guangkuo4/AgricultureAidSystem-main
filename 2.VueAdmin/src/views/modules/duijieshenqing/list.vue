<template>
  <div class="main-content" :style='{"color":"#666","padding":"0px 30px 30px","fontSize":"14px"}'>
    <!-- 搜索栏 -->
    <el-form class="center-form-pv" :inline="true" :model="searchForm"
             :style='{"padding":"0px 0px 0","margin":"0px","overflow":"hidden","flexWrap":"wrap","background":"none","display":"flex","fontSize":"inherit"}'>
      <el-row :style='{"padding":"0px","margin":"0 0 20px","borderRadius":"0px","textAlign":"left","background":"none","display":"block","width":"100%","fontSize":"inherit","order":"2"}'>
        <div :style='{"margin":"0 10px 0 0","fontSize":"inherit","display":"inline-block"}'>
          <label class="item-label" :style='{"margin":"0 10px 0 0","color":"inherit","display":"inline-block","lineHeight":"40px","fontSize":"inherit","fontWeight":"500","height":"40px"}'>申请人姓名</label>
          <el-input v-model="searchForm.shenqingrenxingming" placeholder="申请人姓名" @keydown.enter.native="search()" clearable></el-input>
        </div>
        <div :style='{"margin":"0 10px 0 0","fontSize":"inherit","display":"inline-block"}'>
          <label class="item-label" :style='{"margin":"0 10px 0 0","color":"inherit","display":"inline-block","lineHeight":"40px","fontSize":"inherit","fontWeight":"500","height":"40px"}'>审核状态</label>
          <el-select clearable v-model="searchForm.sfsh" placeholder="请选择" style="width:140px;">
            <el-option label="待审核" value="待审核"></el-option>
            <el-option label="通过" value="通过"></el-option>
            <el-option label="驳回" value="驳回"></el-option>
          </el-select>
        </div>
        <el-button class="search" type="success" icon="el-icon-search" @click="search()">查询</el-button>
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
        <el-table-column prop="shenqingbianhao" label="申请编号" width="160"></el-table-column>
        <el-table-column prop="xiangmumingcheng" label="帮扶项目" min-width="160"></el-table-column>
        <el-table-column prop="shenqingrenzhanghao" label="申请人账号" width="130"></el-table-column>
        <el-table-column prop="shenqingrenxingming" label="申请人姓名" width="100"></el-table-column>
        <el-table-column prop="shenqingriqi" label="申请日期" width="120"></el-table-column>
        <el-table-column prop="sfsh" label="审核状态" width="100">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.sfsh === '通过'" type="success" size="small">通过</el-tag>
            <el-tag v-else-if="scope.row.sfsh === '驳回'" type="danger" size="small">驳回</el-tag>
            <el-tag v-else type="warning" size="small">待审核</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="240" fixed="right">
          <template slot-scope="scope">
            <el-button type="info" size="mini" icon="el-icon-view" @click="viewHandler(scope.row)">查看</el-button>
            <el-button type="warning" size="mini" icon="el-icon-refresh" @click="auditHandler(scope.row)" v-if="scope.row.sfsh!=='通过' && scope.row.sfsh!=='驳回'">审核</el-button>
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
    <el-dialog title="对接申请详情" :visible.sync="viewVisible" width="720px" :close-on-click-modal="false">
      <el-descriptions :column="2" border size="small">
        <el-descriptions-item label="申请编号">{{ detailRow.shenqingbianhao }}</el-descriptions-item>
        <el-descriptions-item label="帮扶项目">{{ detailRow.xiangmumingcheng }}</el-descriptions-item>
        <el-descriptions-item label="申请人账号">{{ detailRow.shenqingrenzhanghao }}</el-descriptions-item>
        <el-descriptions-item label="申请人姓名">{{ detailRow.shenqingrenxingming }}</el-descriptions-item>
        <el-descriptions-item label="申请日期">{{ detailRow.shenqingriqi }}</el-descriptions-item>
        <el-descriptions-item label="审核状态">
          <el-tag v-if="detailRow.sfsh === '通过'" type="success" size="small">通过</el-tag>
          <el-tag v-else-if="detailRow.sfsh === '驳回'" type="danger" size="small">驳回</el-tag>
          <el-tag v-else type="warning" size="small">待审核</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="申请说明" :span="2">{{ detailRow.shenqingshuoming || '无' }}</el-descriptions-item>
        <el-descriptions-item label="审核回复" :span="2">{{ detailRow.shhf || '无' }}</el-descriptions-item>
      </el-descriptions>
      <span slot="footer"><el-button type="primary" @click="viewVisible=false">关闭</el-button></span>
    </el-dialog>

    <!-- 审核弹窗 -->
    <el-dialog title="审核对接申请" :visible.sync="auditVisible" width="500px" :close-on-click-modal="false">
      <el-form ref="auditFormRef" :model="auditForm" label-width="100px">
        <el-form-item label="申请编号">{{ auditForm.shenqingbianhao }}</el-form-item>
        <el-form-item label="帮扶项目">{{ auditForm.xiangmumingcheng }}</el-form-item>
        <el-form-item label="审核结果" required>
          <el-radio-group v-model="auditForm.sfsh">
            <el-radio label="通过">通过</el-radio>
            <el-radio label="驳回">驳回</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="审核意见">
          <el-input type="textarea" :rows="3" v-model="auditForm.shhf" placeholder="填写审核意见（可选）"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="auditVisible=false">取消</el-button>
        <el-button type="primary" @click="submitAudit">确定审核</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import AddOrUpdate from './add-or-update'
export default {
  components: { AddOrUpdate },
  data() {
    return {
      searchForm: { shenqingrenxingming: '', sfsh: '' },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      layouts: ['prev', 'pager', 'next', 'jumper'],
      addOrUpdateVisible: false,
      viewVisible: false,
      auditVisible: false,
      detailRow: {},
      auditForm: { id: null, shenqingbianhao: '', xiangmumingcheng: '', sfsh: '通过', shhf: '' }
    }
  },
  created() { this.getDataList() },
  methods: {
    search() { this.pageIndex = 1; this.getDataList() },
    getDataList() {
      this.dataListLoading = true
      const params = { page: this.pageIndex, limit: this.pageSize, sort: 'id', order: 'desc' }
      if (this.searchForm.shenqingrenxingming) params.shenqingrenxingming = '%' + this.searchForm.shenqingrenxingming + '%'
      if (this.searchForm.sfsh) params.sfsh = this.searchForm.sfsh
      this.$http({ url: 'duijieshenqing/page', method: 'get', params }).then(({ data }) => {
        if (data && data.code === 0) {
          this.dataList = data.data.list || []
          this.totalPage = data.data.total || 0
        } else { this.dataList = []; this.totalPage = 0 }
        this.dataListLoading = false
      })
    },
    sizeChangeHandle(val) { this.pageSize = val; this.pageIndex = 1; this.getDataList() },
    currentChangeHandle(val) { this.pageIndex = val; this.getDataList() },
    selectionChangeHandler(val) { this.dataListSelections = val },
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => { this.$refs.addOrUpdate.init(id) })
    },
    viewHandler(row) { this.detailRow = row || {}; this.viewVisible = true },
    auditHandler(row) {
      this.auditForm = { id: row.id, shenqingbianhao: row.shenqingbianhao, xiangmumingcheng: row.xiangmumingcheng, sfsh: '通过', shhf: '' }
      this.auditVisible = true
    },
    submitAudit() {
      this.$http({ url: 'duijieshenqing/update', method: 'post', data: this.auditForm }).then(({ data }) => {
        if (data && data.code === 0) {
          this.$message.success('审核成功')
          this.auditVisible = false
          this.getDataList()
        } else { this.$message.error((data && data.msg) || '操作失败') }
      })
    },
    deleteHandle(id) {
      this.$confirm('确定删除该申请记录吗？', '删除确认', { type: 'warning' }).then(() => {
        this.$http({ url: 'duijieshenqing/delete', method: 'post', data: [id] }).then(({ data }) => {
          if (data && data.code === 0) { this.$message.success('删除成功'); this.getDataList() }
          else { this.$message.error((data && data.msg) || '删除失败') }
        })
      }).catch(() => {})
    }
  }
}
</script>
