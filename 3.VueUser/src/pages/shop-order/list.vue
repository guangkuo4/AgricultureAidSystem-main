<template>
<div :style='{"width":"80%","padding":"20px 0","margin":"0 auto","position":"relative","background":"#FCFAFF"}'>
    <el-button :style='{"border":"0","cursor":"pointer","padding":"0 10px","margin":"0 auto 10px","color":"#fff","outline":"none","borderRadius":"5px","background":"#C61C14","width":"32%","lineHeight":"40px","fontSize":"16px","height":"40px","order":"3"}' type="warning" size="mini" @click="backClick" class="el-icon-back">返回</el-button>
    <div class="section-title" :style='{"margin":"10px 0","color":"#000","textAlign":"center","background":"linear-gradient(180deg, #A293B6 0%, rgba(241,231,255,0) 100%)","clipPath":"polygon(30px 00%, 0% 100%, 100% 100%, calc(100% - 30px) 0%)","width":"100%","fontSize":"20px","lineHeight":"54px","fontWeight":"bold"}'>我的订单</div>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="未支付" name="未支付"></el-tab-pane>
      <el-tab-pane label="已支付" name="已支付"></el-tab-pane>
      <el-tab-pane label="已发货" name="已发货"></el-tab-pane>
      <el-tab-pane label="已完成" name="已完成"></el-tab-pane>
      <el-tab-pane label="已退款" name="已退款"></el-tab-pane>
      <el-tab-pane label="已取消" name="已取消"></el-tab-pane>
    </el-tabs>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column label="订单编号" prop="orderid"></el-table-column>
      <el-table-column label="商品" align="center" width="200px">
        <template slot-scope="scope">
          <div class="shangpin">
            <el-image style="width: 100px; height: 100px" :src="baseUrl + scope.row.picture" fit="fill"></el-image>
            <span style="margin-left: 10px" >{{ scope.row.goodname }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="价格">
        <template slot-scope="scope">
          <span v-if="(scope.row.type == 1 || scope.row.type == 3)&&scope.row.price>0"><span :style='{"fontSize":"12px"}'>￥</span>{{ scope.row.price }}</span>
          <span v-if="scope.row.type == 2&&scope.row.price>0">{{ scope.row.price }}积分</span>
        </template>
      </el-table-column>
      <el-table-column label="数量" prop="buynumber"></el-table-column>
      <el-table-column label="总价">
        <template slot-scope="scope">
          <span v-if="(scope.row.type == 1 || scope.row.type == 3)&&scope.row.total>0"><span :style='{"fontSize":"12px"}'>￥</span>{{ scope.row.total }}</span>
          <span v-if="scope.row.type == 2&&scope.row.total>0">{{ scope.row.total }}积分</span>
        </template>
      </el-table-column>
      <el-table-column label="地址" prop="address"></el-table-column>
      <el-table-column label="电话" prop="tel"></el-table-column>
      <el-table-column label="收货人" prop="consignee"></el-table-column>
      <el-table-column label="下单时间" prop="addtime"></el-table-column>
      <el-table-column label="备注" prop="remark"></el-table-column>
      <el-table-column label="退货审核" prop="sfsh">
		<template slot-scope="scope">
		  <el-tag type="success" v-if="scope.row.sfsh=='是'">已通过</el-tag>
		  <el-tag type="warning" v-if="scope.row.sfsh=='待审核'">待审核</el-tag>
		  <el-tag type="danger" v-if="scope.row.sfsh=='否'">未通过</el-tag>
		</template>
	  </el-table-column>
      <el-table-column label="审核回复" prop="shhf"></el-table-column>
      <el-table-column label="操作" width="150">
        <template slot-scope="scope">
          <el-button v-show="activeName == '未支付'" type="success" :style='{"margin":"2px auto",}' size="mini" @click="pay(scope.row)">支付</el-button>
          <el-button v-show="activeName == '未支付'" type="danger" :style='{"margin":"2px auto",}' size="mini" @click="cancel(scope.row)">取消</el-button>
          <el-button v-show="activeName == '已支付'" type="danger" :style='{"margin":"2px auto",}' size="mini" @click="refund(scope.row)">{{scope.row.total>0?'退款':'退订'}}</el-button>
          <el-button v-if="scope.row.logistics" type="warning" :style='{"margin":"2px auto",}' size="mini" @click="logistics(scope.row)">物流</el-button>
          <el-button v-if="activeName == '已完成'&&scope.row.sfsh==''" type="danger" :style='{"margin":"2px auto",}' size="mini" @click="returnGood(scope.row)">退货</el-button>
          <el-button v-show="activeName == '已完成'" type="primary" :style='{"margin":"2px auto",}' size="mini" @click="toDetail(scope.row)">评价</el-button>
          <el-button v-show="activeName == '已发货'" type="success" :style='{"margin":"2px auto",}' size="mini" @click="confirm(scope.row)">确认收货</el-button>
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

    <el-dialog title="物流信息" :visible.sync="logisticsVisible" width="30%">
        <div v-html="logisticsText"></div>
    </el-dialog>
	
</div>
</template>

<script>
  import config from '@/config/config'
  export default {
    data() {
      return {
		layouts: '',
        baseUrl: config.baseUrl,
        activeName: '未支付',
        tableData: [],
        total: 1,
        pageSize: 10,pageSizes: [10,20,30,50],
        totalPage: 1,
        session: {},
        userTableName: localStorage.getItem('UserTableName'),
        logisticsVisible: false,
        logisticsText: ''
      }
    },
    created() {
      this.getSession();
      this.getMyOrderList(1);
    },
    methods: {
      backClick() {
          this.$router.push('/index/center')
      },
      getSession() {
        this.$http.get(this.userTableName+'/session', {emulateJSON: true}).then(res => {
          if (res.data.code == 0) {
            this.session = res.data.data;
          }
        });
      },
      handleClick(tab, event) {
        tab.activeName = event.target.outerText;
        this.getMyOrderList(1);
      },
      toDetail(row) {
          this.$router.push({path: '/index/'+row.tablename+'Detail', query: {id: row.goodid}});
      },
      handleAction(index, row) {
        if (index == '已支付' && this.session.money < row.total) {
          this.$message.error('余额不足，请先充值');
          return;
        }

        row.status = index;
        this.$http.post('orders/update', row).then(res => {
          if (res.data.code == 0) {
            if (index == '已支付') this.session.money -= row.total;
            if (index == '已退款' && row.type == 1) this.session.money += row.total;
            if (index == '已退款' && row.type == 2) this.session.jifen += row.total;
            if (index == '已支付' || index == '已退款') {
              this.session.money = this.session.money.toFixed(2);
              this.$http.post(this.userTableName+'/update', this.session).then(res => {});
            }
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.getMyOrderList(1);
              }
            });
          }
        });
      },
        // 退款
        refund(item) {
            this.$confirm(`${item.total>0?'确定退款吗？退款金额将返回账户中。':'是否退订？'}`, "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            }).then(() => {
				
                // 获取商品详情信息
                this.$http.get(item.tablename+'/info/'+item.goodid, {}).then(res => {
                    // 商品信息
                    let good = res.data.data;
                    // 用户当前用户信息
                    let table = localStorage.getItem("UserTableName");
                    this.$http.get(table+'/session', {}).then(res => {
                        this.user = res.data.data;
                        if(item.type==1||item.type==3) {
                            // 如果该商品存在积分
                            if (good.jf) {
                                this.user.jf = (Number(this.user.jf) - Number(item.total)).toFixed(2);
                            }
                            this.user.money = (Number(this.user.money) + Number(item.total)).toFixed(2);
                            // 更新用户余额
                            this.$http.post(table+'/update', this.user).then(res => {
                                item.status = '已退款'
								localStorage.setItem('sessionForm',JSON.stringify(this.user))
                                this.$http.post(`orders/update`, item).then(res => {
                                    if(item.tablename == `zhunongshangpin`){
                                        // 加回库存数量
                                        good.alllimittimes = good.alllimittimes + item.buynumber;
                                        this.$http.post(`zhunongshangpin/update`, good).then(res => {
                                        });
                                    }
                                    this.$message({
                                      message: `${item.total>0?'退款成功':'退订成功'}`,
                                      type: 'success',
                                      duration: 1500,
                                      onClose: () => {
                                        this.getMyOrderList(1);
                                      }
                                    });
                                });
                            });
                        } else if(item.type==2) {
                            this.user.jf = Number(this.user.jf) + Number(item.total);
                            // 更新用户余额
                            this.$http.post(table+'/update', this.user).then(res => {
                                item.status = '已退款'
								localStorage.setItem('sessionForm',JSON.stringify(this.user))
                                this.$http.post(`orders/update`, item).then(res => {
                                    if(item.tablename == `zhunongshangpin`){
                                        // 加回库存数量
                                        good.alllimittimes = good.alllimittimes + item.buynumber;
                                        this.$http.post(`zhunongshangpin/update`, good).then(res => {
                                        });
                                    }
                                    this.$message({
                                      message: `${item.total>0?'退款成功':'退订成功'}`,
                                      type: 'success',
                                      duration: 1500,
                                      onClose: () => {
                                        this.getMyOrderList(1);
                                      }
                                    });
                                });
                            });

                        }
                    });
                });
            });
        },
        //物流
        logistics(row) {
            this.logisticsText = row.logistics;
            this.logisticsVisible = true;
        },
        //退货
        returnGood(item) {
            this.$confirm(`确定退货吗？审核通过后退款金额将返回账户中。`, "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            }).then(() => {
				item.sfsh = '待审核'
				this.$http.post(`orders/update`, item).then(res => {
					this.$message({
						message: '申请成功',
						type: 'success',
						duration: 1500,
						onClose: () => {
							this.getMyOrderList(1);
						}
					});
				})
            });
        },

        // 支付
        pay(item) {
            // 获取商品详情信息
            this.$http.get(item.tablename+'/info/'+item.goodid, {}).then(res => {
                // 商品信息
                let data = res.data.data;
                // 用户当前用户信息
                let table = localStorage.getItem("UserTableName");
                this.$http.get(table+'/session', {}).then(res => {
                    this.user = res.data.data;
                    // 判断余额是否充足
                    if (Number(this.user.money) < Number(item.total)) {
                        this.$message({
                            message: '余额不足，请先充值',
                            type: 'error',
                            duration: 1500
                        });
                        return
                    }
                    // 如果该商品存在积分
                    if (data.jf) {
                        this.user.jf = Number((Number(this.user.jf) + Number(item.total)).toFixed(2));
                    }
                    this.user.money = Number((Number(this.user.money) - Number(item.total)).toFixed(2));
                    // 更新用户余额
                    this.$http.post(table+'/update', this.user).then(res => {
                        item.status = '已支付'
						localStorage.setItem('sessionForm',JSON.stringify(this.user))
                        this.$http.post(`orders/update`, item).then(res => {
                            this.$message({
                              message: '支付成功',
                              type: 'success',
                              duration: 1500,
                              onClose: () => {
                                this.getMyOrderList(1);
                              }
                            });
                        });
                    });
                });
            });
        },
      confirm(item){
          this.$confirm(`是否确定收货？`, "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning"
          }).then(() => {
              item.status = '已完成'
              this.$http.post(`orders/update`, item).then(res => {
                  this.$message({
                    message: '确认收货成功',
                    type: 'success',
                    duration: 1500,
                    onClose: () => {
                      this.getMyOrderList(1);
                    }
                  });
              });
          });
      },
		cancel(item){
			this.$confirm(`是否取消订单？`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning"
			}).then(() => {
				item.status = '已取消'
				this.$http.post(`orders/update`, item).then(res => {
                    // 获取商品详情信息
                    this.$http.get(item.tablename+'/info/'+item.goodid, {}).then(res => {
                        // 商品信息
                        let good = res.data.data;
                        if(item.tablename == `zhunongshangpin`){
                            // 加回库存数量
                            good.alllimittimes = good.alllimittimes + item.buynumber;
                            this.$http.post(`zhunongshangpin/update`, good).then(res => {
                            });
                        }
						this.$message({
							message: '订单取消成功',
							type: 'success',
							duration: 1500,
							onClose: () => {
								this.getMyOrderList(1);
							}
						});
					});
				});
			});
		},
		getMyOrderList(page) {
			this.$http.get('orders/list', {params: {page, limit: this.pageSize,sort:'addtime',order:'desc', userid: localStorage.getItem('frontUserid'), status: this.activeName}}).then(res => {
				if (res.data.code == 0) {
					this.tableData = res.data.data.list;
					this.total = res.data.data.total;
					this.pageSize = res.data.data.pageSize;this.pageSizes = [this.pageSize, this.pageSize*2, this.pageSize*3, this.pageSize*5];
					this.totalPage = res.data.data.totalPage;
				}
			});
		},
		curChange(page) {
			this.getMyOrderList(page);
		},
		prevClick(page) {
			this.getMyOrderList(page);
		},
		nextClick(page) {
			this.getMyOrderList(page);
		}
	}
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  /* ========== 订单列表页样式 ========== */
  .section {
    width: 1000px;
    margin: 0 auto;
    padding: 30px;
    background: linear-gradient(135deg, rgba(241, 248, 233, 0.6), rgba(255, 255, 255, 0.95));
    border-radius: 20px;
    border: 1px solid rgba(46, 125, 50, 0.1);
    box-shadow: 0 10px 40px rgba(46, 125, 50, 0.08);
    position: relative;
    overflow: hidden;

    &::before {
      content: '';
      position: absolute;
      top: -100px;
      right: -100px;
      width: 250px;
      height: 250px;
      background: radial-gradient(circle, rgba(76, 175, 80, 0.08), transparent 70%);
      pointer-events: none;
      border-radius: 50%;
    }
  }

  /* 标题区域 */
  .title {
    text-align: center;
    font-size: 22px;
    font-weight: 700;
    color: #1a2e1a;
    margin: 0 0 24px;
    padding: 16px 24px;
    background: linear-gradient(135deg, rgba(241, 248, 233, 0.8), rgba(255, 255, 255, 0.9));
    border-radius: 14px;
    border: 1px solid rgba(46, 125, 50, 0.1);
    box-shadow: 0 4px 16px rgba(46, 125, 50, 0.06);
  }

  /* 表格容器 */
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

  /* 商品缩略图 */
  .shangpin {
    display: flex;
    align-items: center;
    gap: 12px;

    img {
      width: 56px;
      height: 56px;
      object-fit: cover;
      border-radius: 10px;
      border: 1px solid rgba(46, 125, 50, 0.1);
      transition: transform 0.3s ease;
    }

    img:hover {
      transform: scale(1.1);
    }
  }

  /* 按钮样式 */
  ::v-deep .el-button--success {
    background: linear-gradient(135deg, #4caf50, #2e7d32) !important;
    border: none !important;
    border-radius: 10px !important;
    font-weight: 600;
    transition: all 0.3s ease;

    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 8px 24px rgba(46, 125, 50, 0.35);
    }
  }

  ::v-deep .el-button--warning {
    background: linear-gradient(135deg, #ff9800, #f57c00) !important;
    border: none !important;
    border-radius: 10px !important;
    font-weight: 600;
    transition: all 0.3s ease;

    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 8px 24px rgba(255, 152, 0, 0.35);
    }
  }

  ::v-deep .el-button--danger {
    background: linear-gradient(135deg, #e53935, #c62828) !important;
    border: none !important;
    border-radius: 10px !important;
    font-weight: 600;
    transition: all 0.3s ease;

    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 8px 24px rgba(229, 57, 53, 0.35);
    }
  }

  /* 标签样式 */
  ::v-deep .el-tag--success {
    background: rgba(76, 175, 80, 0.1) !important;
    color: #388e3c !important;
    border: none !important;
    border-radius: 20px !important;
    padding: 0 12px;
    font-weight: 600;
  }

  ::v-deep .el-tag--warning {
    background: rgba(255, 152, 0, 0.1) !important;
    color: #f57c00 !important;
    border: none !important;
    border-radius: 20px !important;
    padding: 0 12px;
    font-weight: 600;
  }

  ::v-deep .el-tag--danger {
    background: rgba(229, 57, 53, 0.1) !important;
    color: #e53935 !important;
    border: none !important;
    border-radius: 20px !important;
    padding: 0 12px;
    font-weight: 600;
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

    .el-pagination__sizes .el-input .el-input__inner {
      border: 1px solid rgba(46, 125, 50, 0.12) !important;
      border-radius: 10px !important;
      height: 36px;
      line-height: 36px;
    }

    .el-pagination__jump .el-input .el-input__inner {
      border: 1px solid rgba(46, 125, 50, 0.12) !important;
      border-radius: 10px !important;
      height: 36px;
      line-height: 36px;
    }

    .el-pagination__total {
      color: #607060;
      font-weight: 500;
    }

    .el-pagination__jump {
      color: #607060;
      font-weight: 500;
    }
  }
</style>
