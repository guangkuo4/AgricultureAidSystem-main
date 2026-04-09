<template>
  <div class="main-content" :style='{"color":"#666","padding":"0px 30px 30px","fontSize":"14px"}'>
    <el-form class="center-form-pv" :inline="true" :model="searchForm"
             :style='{"padding":"0px 0px 0","margin":"0px","overflow":"hidden","flexWrap":"wrap","background":"none","display":"flex","fontSize":"inherit"}'>
      <el-row :style='{"padding":"0px","margin":"0 0 20px","borderRadius":"0px","textAlign":"left","background":"none","display":"block","width":"100%","fontSize":"inherit","order":"2"}'>
        <div :style='{"margin":"0 0px 0 0","fontSize":"inherit","display":"inline-block"}'>
          <label class="item-label"
                 :style='{"margin":"0 10px 0 0","color":"inherit","display":"inline-block","lineHeight":"40px","fontSize":"inherit","fontWeight":"500","height":"40px"}'>企业名称</label>
          <el-input v-model="searchForm.qiyemingcheng" placeholder="企业名称" @keydown.enter.native="search()" clearable></el-input>
        </div>
        <div :style='{"margin":"0 0px 0 0","fontSize":"inherit","display":"inline-block"}' class="select">
          <label class="item-label"
                 :style='{"margin":"0 10px 0 0","color":"inherit","display":"inline-block","lineHeight":"40px","fontSize":"inherit","fontWeight":"500","height":"40px"}'>审核状态</label>
          <el-select clearable v-model="searchForm.sfsh" placeholder="请选择审核状态">
            <el-option v-for="(item,index) in sfshOptions" :key="index" :label="item" :value="item"></el-option>
          </el-select>
        </div>
        <el-button class="search" type="success" @click="search()">查询</el-button>
      </el-row>
    </el-form>

    <div :style='{"border":"0px solid #eee","width":"100%","padding":"0","margin":"0px 0 0","borderRadius":"12px","background":"rgba(255,255,255,.9)"}'>
      <el-table class="tables"
                :stripe='false'
                :border='true'
                :data="dataList"
                v-loading="dataListLoading"
                @selection-change="selectionChangeHandler"
                :style='{"padding":"0","borderColor":"#edf7ff","color":"inherit","borderRadius":"12px","borderWidth":"0px 0px 0 0px","background":"none","width":"100%","fontSize":"inherit","borderStyle":"solid"}'>
        <el-table-column type="selection" align="center" width="50"></el-table-column>
        <el-table-column label="序号" type="index" width="50" />
        <el-table-column prop="qiyemingcheng" label="企业名称" />
        <el-table-column prop="lianxiren" label="联系人" width="120" />
        <el-table-column prop="lianxidianhua" label="联系电话" width="130" />
        <el-table-column prop="shenqingzhanghao" label="申请账号" width="140" />
        <el-table-column prop="sfsh" label="审核状态" width="100" />
        <el-table-column prop="shenhetime" label="审核时间" width="170" />
        <el-table-column label="操作" width="260">
          <template slot-scope="scope">
            <el-button type="success" size="mini" @click="viewHandler(scope.row)">查看</el-button>
            <el-button type="warning" size="mini" @click="auditHandler(scope.row)" :disabled="scope.row.sfsh==='通过'">审核</el-button>
            <el-button type="primary" size="mini" @click="deleteHandler(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      background
      :page-sizes="[10, 20, 30, 50]"
      :page-size="pageSize"
      :layout="layouts.join()"
      :total="totalPage"
      prev-text="上一页 "
      next-text="下一页 "
      :hide-on-single-page="false"
      :style='{"padding":"0","margin":"20px 0 0","whiteSpace":"nowrap","color":"inherit","textAlign":"right","width":"100%","fontSize":"inherit","fontWeight":"500"}'
    />

    <el-dialog title="申请详情" :visible.sync="viewVisible" width="720px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="企业名称">{{currentRow.qiyemingcheng}}</el-descriptions-item>
        <el-descriptions-item label="联系人">{{currentRow.lianxiren}}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{currentRow.lianxidianhua}}</el-descriptions-item>
        <el-descriptions-item label="申请账号">{{currentRow.shenqingzhanghao}}</el-descriptions-item>
        <el-descriptions-item label="审核状态">{{currentRow.sfsh}}</el-descriptions-item>
        <el-descriptions-item label="审核时间">{{currentRow.shenhetime}}</el-descriptions-item>
        <el-descriptions-item label="审核回复" :span="2">{{currentRow.shhf}}</el-descriptions-item>
        <el-descriptions-item label="营业执照" :span="2">
          <div v-if="currentRow.yingyezhizhao">
            <a v-if="currentRow.yingyezhizhao.substring(0,4)==='http'" :href="currentRow.yingyezhizhao" target="_blank">查看文件</a>
            <a v-else :href="$base.url+currentRow.yingyezhizhao" target="_blank">查看文件</a>
          </div>
          <div v-else>无</div>
        </el-descriptions-item>
      </el-descriptions>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="viewVisible=false">关闭</el-button>
      </span>
    </el-dialog>

    <el-dialog title="审核入驻申请" :visible.sync="auditVisible" width="560px">
      <el-form ref="auditFormRef" :model="auditForm" label-width="90px">
        <el-form-item label="审核结果">
          <el-radio-group v-model="auditForm.sfsh">
            <el-radio label="通过">通过</el-radio>
            <el-radio label="驳回">驳回</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="审核意见">
          <el-input type="textarea" :rows="4" v-model="auditForm.shhf" placeholder="填写审核意见（可选）"></el-input>
        </el-form-item>
        <div style="color:#999;line-height:20px;margin-left:10px;">
          审核通过后会自动开通农商账号：账号=申请账号，密码=申请密码。
        </div>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="auditVisible=false">取消</el-button>
        <el-button type="primary" @click="submitAudit">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchForm: { qiyemingcheng: '', sfsh: '' },
      sfshOptions: ['待审核', '通过', '驳回'],
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      layouts: ['prev', 'pager', 'next', 'jumper'],
      viewVisible: false,
      auditVisible: false,
      currentRow: {},
      auditForm: { id: null, sfsh: '通过', shhf: '' }
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
        sort: 'id',
        order: 'desc'
      }
      if (this.searchForm.qiyemingcheng) params.qiyemingcheng = '%' + this.searchForm.qiyemingcheng + '%'
      if (this.searchForm.sfsh) params.sfsh = this.searchForm.sfsh
      this.$http({ url: 'qiyeruzhu/page', method: 'get', params }).then(({ data }) => {
        if (data && data.code === 0) {
          this.dataList = data.data.list
          this.totalPage = data.data.total
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
    viewHandler(row) {
      this.currentRow = row || {}
      this.viewVisible = true
    },
    auditHandler(row) {
      this.currentRow = row || {}
      this.auditForm = { id: row.id, sfsh: '通过', shhf: '' }
      this.auditVisible = true
    },
    submitAudit() {
      this.$http({ url: 'qiyeruzhu/audit', method: 'post', data: this.auditForm }).then(({ data }) => {
        if (data && data.code === 0) {
          this.$message.success('操作成功')
          this.auditVisible = false
          this.getDataList()
        } else {
          this.$message.error((data && data.msg) || '操作失败')
        }
      })
    },
    deleteHandler(id) {
      this.$confirm('确定删除该记录吗?', '提示', { type: 'warning' }).then(() => {
        this.$http({ url: 'qiyeruzhu/delete', method: 'post', data: [id] }).then(({ data }) => {
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

