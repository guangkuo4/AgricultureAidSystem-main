<template>
<div :style='{"width":"80%","padding":"20px 0","margin":"0 auto","position":"relative","background":"#FCFAFF"}'>
    <el-button :style='{"border":"0","cursor":"pointer","padding":"0 10px","margin":"0 auto 10px","color":"#fff","outline":"none","borderRadius":"5px","background":"#C61C14","width":"32%","lineHeight":"40px","fontSize":"16px","height":"40px","order":"3"}' type="warning" size="mini" @click="backClick" class="el-icon-back">返回</el-button>
    <div class="section-title" :style='{"margin":"10px 0","color":"#000","textAlign":"center","background":"linear-gradient(180deg, #A293B6 0%, rgba(241,231,255,0) 100%)","clipPath":"polygon(30px 00%, 0% 100%, 100% 100%, calc(100% - 30px) 0%)","width":"100%","fontSize":"20px","lineHeight":"54px","fontWeight":"bold"}'>我的地址</div>
    <el-button type="primary" icon="el-icon-plus" @click="toAddAddr">添加新地址</el-button>
    <el-table
      :data="tableData"
      style="width: 100%">
      <el-table-column
        label="联系人"
        prop="name"
        width="100">
      </el-table-column>
      <el-table-column
        label="手机号"
        prop="phone"
        width="120">
      </el-table-column>
      <el-table-column
        label="地址"
        prop="address">
      </el-table-column>
      <el-table-column
        label="默认"
        prop="isdefault"
        width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.isdefault === '是' ? 'primary' : 'success'" size="medium">{{ scope.row.isdefault }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
	
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
      :style='{"padding":"0","margin":"20px auto","whiteSpace":"nowrap","color":"#333","textAlign":"center","width":"100%","fontWeight":"500"}'
      @current-change="curChange"
      @prev-click="prevClick"
      @next-click="nextClick"
    ></el-pagination>
	
</div>

</template>

<script>
  export default {
    data() {
      return {
		  layouts: '',
        tableData: [],
        total: 1,
        pageSize: 10,pageSizes: [10,20,30,50],
        totalPage: 1
      }
    },
    created() {
      this.getAddr(1);
    },
    methods: {
      backClick() {
          this.$router.push('/index/center')
      },
      getAddr(page) {
        this.$http.get('address/list', {params: {page, limit: this.pageSize, userid: localStorage.getItem('frontUserid')}}).then(res => {
          if (res.data.code == 0) {
            this.tableData = res.data.data.list;
            this.total = res.data.data.total;
            this.pageSize = res.data.data.pageSize;this.pageSizes = [this.pageSize, this.pageSize*2, this.pageSize*3, this.pageSize*5];
            this.totalPage = res.data.data.totalPage;
          }
        });
      },
      curChange(page) {
        this.getAddr(page);
      },
      prevClick(page) {
        this.getAddr(page);
      },
      nextClick(page) {
        this.getAddr(page);
      },
      handleEdit(index, row) {
        this.$router.push({path: '/index/shop-address/addOrUpdate', query: {id: row.id, editObj: JSON.stringify(row)}})
      },
      handleDelete(index, row) {
        this.$confirm('是否确认删除?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let delIds = new Array();
          delIds.push(row.id);
          this.$http.post('address/delete', delIds).then(res => {
            if (res.data.code == 0) {
              this.$message({
                type: 'success',
                message: '删除成功!',
                duration: 1500,
                onClose: () => {
                  this.getAddr(1);
                }
              });
            }
          });
        });
      },
      toAddAddr() {
        this.$router.push('/index/shop-address/addOrUpdate');
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
/* ========== 收货地址列表页样式 ========== */
.address-container {
  width: 90%;
  max-width: 1000px;
  margin: 0 auto;
  padding: 30px 0;
  position: relative;

  &::before {
    content: '';
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 400px;
    height: 400px;
    background: radial-gradient(circle, rgba(76, 175, 80, 0.06), transparent 70%);
    pointer-events: none;
    border-radius: 50%;
  }
}

/* 页面标题 */
.section-title {
  margin: 16px 0;
  color: #1a2e1a;
  text-align: center;
  background: linear-gradient(135deg, rgba(241, 248, 233, 0.8), rgba(255, 255, 255, 0.95));
  clip-path: polygon(30px 0%, 0% 100%, 100% 100%, calc(100% - 30px) 0%);
  width: 100%;
  font-size: 24px;
  line-height: 60px;
  font-weight: 800;
  border-radius: 16px;
  box-shadow: 0 8px 32px rgba(46, 125, 50, 0.1);
  border: 1px solid rgba(46, 125, 50, 0.08);
}

/* 返回按钮 */
.el-icon-back {
  background: linear-gradient(135deg, #4caf50, #2e7d32) !important;
  border: none !important;
  border-radius: 12px !important;
  color: #fff !important;
  font-weight: 600;
  box-shadow: 0 6px 20px rgba(46, 125, 50, 0.3);
  transition: all 0.25s ease;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 10px 28px rgba(46, 125, 50, 0.4);
  }
}

/* 添加地址按钮 */
.el-button--primary {
  display: block;
  width: fit-content;
  margin: 20px auto;
  background: linear-gradient(135deg, #4caf50, #2e7d32) !important;
  border: none !important;
  border-radius: 12px !important;
  padding: 12px 28px !important;
  font-weight: 600;
  box-shadow: 0 6px 20px rgba(46, 125, 50, 0.3);
  transition: all 0.3s ease;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 10px 28px rgba(46, 125, 50, 0.4);
  }
}

/* 表格样式 */
.el-table {
  border-radius: 16px !important;
  overflow: hidden;
  border: 1px solid rgba(46, 125, 50, 0.08);
  box-shadow: 0 8px 32px rgba(46, 125, 50, 0.06);
}

::v-deep .el-table th {
  background: linear-gradient(135deg, rgba(241, 248, 233, 0.9), rgba(255, 255, 255, 0.95)) !important;
  color: #1a2e1a !important;
  font-weight: 700;
  font-size: 14px;
  padding: 16px 12px;
  border-bottom: 1px solid rgba(46, 125, 50, 0.1) !important;
}

::v-deep .el-table td {
  padding: 14px 12px;
  border-bottom: 1px solid rgba(46, 125, 50, 0.06) !important;
  font-size: 14px;
}

::v-deep .el-table tr:hover > td {
  background: rgba(241, 248, 233, 0.5) !important;
}

/* 标签样式 */
::v-deep .el-tag--primary {
  background: linear-gradient(135deg, #4caf50, #2e7d32) !important;
  border: none !important;
  border-radius: 20px !important;
  padding: 0 12px;
  font-weight: 600;
}

::v-deep .el-tag--success {
  background: rgba(76, 175, 80, 0.1) !important;
  color: #388e3c !important;
  border: none !important;
  border-radius: 20px !important;
  padding: 0 12px;
  font-weight: 600;
}

/* 操作按钮 */
::v-deep .el-button--mini {
  border-radius: 8px !important;
  font-weight: 600;
  transition: all 0.25s ease;

  &:first-child {
    background: linear-gradient(135deg, #4caf50, #2e7d32) !important;
    border: none !important;
    color: #fff !important;

    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 6px 18px rgba(46, 125, 50, 0.35);
    }
  }

  &:last-child {
    background: linear-gradient(135deg, #e53935, #c62828) !important;
    border: none !important;
    color: #fff !important;

    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 6px 18px rgba(229, 57, 53, 0.35);
    }
  }
}

/* 分页样式 */
#pagination.el-pagination ::v-deep {
  .btn-prev,
  .btn-next {
    border: 1px solid rgba(46, 125, 50, 0.12) !important;
    border-radius: 12px !important;
    padding: 0 16px !important;
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

  .btn-prev:disabled,
  .btn-next:disabled {
    color: #c0c4cc !important;
    background: #f5f7fa !important;
    cursor: not-allowed;
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
