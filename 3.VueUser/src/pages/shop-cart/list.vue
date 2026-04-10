<template>
<div>
  <!-- 面包屑导航 -->
  <div :style='{"width":"100%","padding":"30px 10%","margin":"0 auto","borderRadius":"8px","background":"linear-gradient(180deg, rgba(80, 160, 80, 0.9), rgba(60, 140, 60, 1))","boxShadow":"0 4px 12px rgba(0, 0, 0, 0.15)","border":"1px solid rgba(255, 255, 255, 0.2)"}' class="breadcrumb-preview">
    <el-breadcrumb :separator="'>'" :style='{"fontSize":"16px","lineHeight":"1","color":"#fff"}'>
      <el-breadcrumb-item class="item1" to="/"><a style="color: #fff;">首页</a></el-breadcrumb-item>
      <el-breadcrumb-item class="item2"><span style="color: #fff;">购物车</span></el-breadcrumb-item>
    </el-breadcrumb>
  </div>

  <!-- 主容器（空车时拉高白框，内容区更易读） -->
  <div
    class="cart-page-main"
    :class="{ 'cart-page-main--empty': tableData.length === 0 }"
    :style='{"width":"80%","padding":"30px 0","margin":"30px auto","position":"relative","background":"#f8faf8","borderRadius":"16px","boxShadow":"0 8px 32px rgba(46, 125, 50, 0.1)"}'
  >
    
    <!-- 标题区域 -->
    <div :style='{"padding":"0 30px 20px","marginBottom":"20px","borderBottom":"2px solid rgba(46, 125, 50, 0.1)"}'>
      <div :style='{"display":"flex","alignItems":"center","justifyContent":"space-between"}'>
        <div :style='{"display":"flex","alignItems":"center"}'>
          <i class="el-icon-shopping-cart-full" :style='{"fontSize":"28px","color":"#2E7D32","marginRight":"15px"}'></i>
          <div>
            <div :style='{"fontSize":"24px","fontWeight":"bold","color":"#1a1a1a"}'>我的购物车</div>
            <div :style='{"fontSize":"14px","color":"#999","marginTop":"5px"}'>共 {{ tableData.length }} 件商品</div>
          </div>
        </div>
        <el-button v-if="tableData.length > 0" @click="clearCart" :style='{"border":"1px solid #dc3545","color":"#dc3545","background":"transparent","padding":"10px 20px","borderRadius":"8px","fontSize":"14px"}'>
          <i class="el-icon-delete" style="margin-right: 5px;"></i>清空购物车
        </el-button>
      </div>
    </div>

    <!-- 空购物车提示 -->
    <div v-if="tableData.length == 0" class="cart-empty-block">
      <i class="el-icon-shopping-cart-empty" :style='{"fontSize":"120px","color":"#d0d0d0","marginBottom":"20px"}'></i>
      <div :style='{"fontSize":"18px","color":"#999","marginBottom":"30px"}'>购物车空空如也~</div>
      <el-button type="primary" class="btn-cart-empty-go" @click="$router.push('/index/zhunongshangpin')">
        去逛逛
      </el-button>
    </div>

    <!-- 购物车商品列表 -->
    <div v-else :style='{"padding":"0 30px"}'>
      <!-- 表头 -->
      <div :style='{"display":"flex","alignItems":"center","padding":"15px 20px","background":"rgba(46, 125, 50, 0.05)","borderRadius":"12px","marginBottom":"20px"}'>
        <el-checkbox v-model="allSelected" @change="toggleAllSelection" :style='{"marginRight":"20px"}'>
          <span :style='{"fontSize":"14px","fontWeight":"600","color":"#333"}'>全选</span>
        </el-checkbox>
        <div :style='{"flex":"1","fontSize":"14px","fontWeight":"600","color":"#333"}'>商品信息</div>
        <div :style='{"width":"120px","textAlign":"center","fontSize":"14px","fontWeight":"600","color":"#333"}'>单价</div>
        <div :style='{"width":"140px","textAlign":"center","fontSize":"14px","fontWeight":"600","color":"#333"}'>数量</div>
        <div :style='{"width":"120px","textAlign":"center","fontSize":"14px","fontWeight":"600","color":"#333"}'>小计</div>
        <div :style='{"width":"80px","textAlign":"center","fontSize":"14px","fontWeight":"600","color":"#333"}'>操作</div>
      </div>

      <!-- 商品列表 -->
      <div v-for="(item, index) in tableData" :key="item.id" :style='{"display":"flex","alignItems":"center","padding":"20px","background":"#fff","borderRadius":"12px","marginBottom":"15px","border":"1px solid rgba(46, 125, 50, 0.1)","transition":"all 0.3s ease","&:hover":{"boxShadow":"0 4px 16px rgba(46, 125, 50, 0.15)","transform":"translateY(-2px)"}}'>
        <el-checkbox v-model="item.selected" @change="updateTotalPrice" :style='{"marginRight":"20px"}'></el-checkbox>
        
        <!-- 商品图片和名称 -->
        <div :style='{"flex":"1","display":"flex","alignItems":"center"}'>
          <div :style='{"width":"100px","height":"100px","borderRadius":"12px","overflow":"hidden","marginRight":"20px","border":"1px solid #e8e8e8","flexShrink":"0"}'>
            <el-image :src="baseUrl + item.picture" fit="cover" :style='{"width":"100%","height":"100%"}'></el-image>
          </div>
          <div>
            <div :style='{"fontSize":"16px","fontWeight":"600","color":"#1a1a1a","marginBottom":"8px","lineHeight":"1.4"}'>{{ item.goodname }}</div>
            <div :style='{"fontSize":"13px","color":"#999"}'>{{ item.tablename }}</div>
          </div>
        </div>

        <!-- 单价 -->
        <div :style='{"width":"120px","textAlign":"center"}'>
          <div :style='{"fontSize":"16px","fontWeight":"bold","color":"#2E7D32"}'>
            <span :style='{"fontSize":"12px","marginRight":"2px"}'>¥</span>{{ item.price }}
          </div>
        </div>

        <!-- 数量 -->
        <div :style='{"width":"140px","textAlign":"center"}'>
          <el-input-number 
            :min="1" 
            v-model="item.buynumber" 
            @change="(value) => handleChange(value, index, item)" 
            size="small"
            :style='{"display":"inline-block"}'
            controls-position="right"
          ></el-input-number>
        </div>

        <!-- 小计 -->
        <div :style='{"width":"120px","textAlign":"center"}'>
          <div :style='{"fontSize":"18px","fontWeight":"bold","color":"#2E7D32"}'>
            <span :style='{"fontSize":"12px","marginRight":"2px"}'>¥</span>{{ (item.price * item.buynumber).toFixed(2) }}
          </div>
        </div>

        <!-- 操作 -->
        <div :style='{"width":"80px","textAlign":"center"}'>
          <el-button 
            type="text" 
            @click="handleDelete(index, item)"
            :style='{"color":"#dc3545","fontSize":"14px","padding":"5px 10px"}'
          >
            <i class="el-icon-delete" style="margin-right: 3px;"></i>删除
          </el-button>
        </div>
      </div>

      <!-- 底部结算区域 -->
      <div :style='{"marginTop":"30px","padding":"25px 30px","background":"linear-gradient(135deg, rgba(46, 125, 50, 0.05) 0%, rgba(46, 125, 50, 0.1) 100%)","borderRadius":"16px","display":"flex","alignItems":"center","justifyContent":"space-between"}'>
        <div :style='{"display":"flex","alignItems":"center"}'>
          <el-checkbox v-model="allSelected" @change="toggleAllSelection" :style='{"marginRight":"30px"}'>
            <span :style='{"fontSize":"14px","color":"#333"}'>全选</span>
          </el-checkbox>
          <el-button type="text" @click="deleteSelected" :style='{"color":"#666","fontSize":"14px","marginRight":"20px"}'>
            删除选中
          </el-button>
        </div>
        
        <div :style='{"display":"flex","alignItems":"center"}'>
          <div :style='{"marginRight":"30px"}'>
            <span :style='{"fontSize":"14px","color":"#666"}'>已选</span>
            <span :style='{"fontSize":"24px","fontWeight":"bold","color":"#2E7D32","margin":"0 8px"}'>{{ selRows.length }}</span>
            <span :style='{"fontSize":"14px","color":"#666"}'>件</span>
          </div>
          
          <div :style='{"marginRight":"30px","display":"flex","alignItems":"baseline"}'>
            <span :style='{"fontSize":"14px","color":"#666"}'>合计：</span>
            <span :style='{"fontSize":"32px","fontWeight":"bold","color":"#2E7D32"}'>
              <span :style='{"fontSize":"16px","marginRight":"2px"}'>¥</span>{{ totalPrice }}
            </span>
          </div>
          
          <el-button 
            @click="buyClick"
            :disabled="selRows.length == 0"
            :style='{"border":"0","padding":"15px 50px","borderRadius":"8px","background":"linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%)","color":"#fff","fontSize":"18px","fontWeight":"600","boxShadow":"0 4px 16px rgba(46, 125, 50, 0.4)","transition":"all 0.3s ease","&:hover":{"transform":"translateY(-2px)","boxShadow":"0 6px 20px rgba(46, 125, 50, 0.5)"},"&:disabled":{"background":"#ccc","boxShadow":"none","cursor":"not-allowed"}}'
          >
            <i class="el-icon-shopping-cart-2" style="margin-right: 8px;"></i>去结算
          </el-button>
        </div>
      </div>
    </div>
  </div>
</div>
</template>

<script>
  import config from '@/config/config'
  export default {
    data() {
      return {
        baseUrl: config.baseUrl,
        tableData: [],
        selRows: [],
        allSelected: false
      }
    },
    created() {
      this.getCartList();
    },
    methods: {
      // 全选/取消全选
      toggleAllSelection() {
        this.tableData.forEach(item => {
          item.selected = this.allSelected;
        });
        this.updateTotalPrice();
      },

      // 更新选中列表和总价
      updateTotalPrice() {
        this.selRows = this.tableData.filter(item => item.selected);
        this.allSelected = this.tableData.length > 0 && this.selRows.length === this.tableData.length;
      },

      // 处理选中变化
      handleSelectionChange(e) {
        this.selRows = e;
      },

      // 获取购物车列表
      getCartList() {
        this.$http.get('cart/list', {params: {page: 1, limit: 1000, userid: localStorage.getItem('frontUserid')}}).then(res => {
          if (res.data.code == 0) {
            this.tableData = res.data.data.list.map(item => ({
              ...item,
              selected: false
            }));
          }
        });
      },

      // 修改数量
      handleChange(value, index, row) {
        this.$http.get(`${row.tablename}/detail/${row.goodid}`).then(res => {
          if (res.data.code == 0) {
            if (res.data.data.onelimittimes&&res.data.data.onelimittimes > 0) {
                if (value > res.data.data.onelimittimes) {
                    this.tableData[index].buynumber = --value;
                    this.$message.error(`每人单次只能购买${res.data.data.onelimittimes}件`);
                } else {
                    row.buynumber = value;
                    this.$http.post('cart/update', row).then(res => {});
                }
            } else {
                row.buynumber = value;
                this.$http.post('cart/update', row).then(res => {});
            }
          }
        });
      },

      // 删除单个商品
      handleDelete(index, row) {
        this.$confirm('确定要删除这件商品吗？', '温馨提示', {
          confirmButtonText: '确定删除',
          cancelButtonText: '取消',
          type: 'warning',
          confirmButtonClass: 'el-button--danger'
        }).then(() => {
          let delIds = new Array();
          delIds.push(row.id);
          this.$http.post('cart/delete', delIds).then(res => {
            if (res.data.code == 0) {
              this.$message({
                type: 'success',
                message: '删除成功!',
                duration: 1500,
                onClose: () => {
                  this.getCartList();
                }
              });
            }
          });
        });
      },

      // 清空购物车
      clearCart() {
        this.$confirm('确定要清空购物车吗？', '温馨提示', {
          confirmButtonText: '确定清空',
          cancelButtonText: '取消',
          type: 'warning',
          confirmButtonClass: 'el-button--danger'
        }).then(() => {
          let delIds = this.tableData.map(item => item.id);
          this.$http.post('cart/delete', delIds).then(res => {
            if (res.data.code == 0) {
              this.$message({
                type: 'success',
                message: '购物车已清空!',
                duration: 1500,
                onClose: () => {
                  this.getCartList();
                }
              });
            }
          });
        });
      },

      // 删除选中商品
      deleteSelected() {
        if (this.selRows.length == 0) {
          this.$message.warning('请先选择要删除的商品');
          return;
        }
        this.$confirm(`确定要删除选中的 ${this.selRows.length} 件商品吗？`, '温馨提示', {
          confirmButtonText: '确定删除',
          cancelButtonText: '取消',
          type: 'warning',
          confirmButtonClass: 'el-button--danger'
        }).then(() => {
          let delIds = this.selRows.map(item => item.id);
          this.$http.post('cart/delete', delIds).then(res => {
            if (res.data.code == 0) {
              this.$message({
                type: 'success',
                message: '删除成功!',
                duration: 1500,
                onClose: () => {
                  this.getCartList();
                }
              });
            }
          });
        });
      },

      // 购买
      buyClick() {
        if(this.selRows.length==0) {
            this.$message({
                type: 'warning',
                message: '请选择商品',
                duration: 1500,
            });
            return
        }
        var gn = "";
        for(let x in this.selRows){
            var g = this.selRows[x];
            console.log(g.goodname)
            let that = this;
            this.$http.get(`${g.tablename}/detail/${g.goodid}`).then(res => {
              if (res.data.code == 0 && g.buynumber > res.data.data.alllimittimes) {
                gn = g.goodname;
              }
            });
        }
        
        setTimeout(() => {
            if(gn=="") {
                localStorage.setItem('orderGoods', JSON.stringify(this.selRows));
                this.$router.push('/index/shop-order/orderConfirm');
            } else {
                this.$message.error(gn+` 库存不足`);
                return;
            }
        }, 200)
      }
    },
    computed: {
      totalPrice() {
            let totalPrice = 0;
            for(let x in this.selRows){
                  totalPrice += Number(this.selRows[x].price) * Number(this.selRows[x].buynumber)
            }
            return totalPrice.toFixed(2);
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
/* ========== 购物车页面样式 ========== */

/* 面包屑导航 */
.breadcrumb-preview {
  &::before {
    content: '';
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 200px;
    height: 200px;
    background: radial-gradient(circle, rgba(255, 255, 255, 0.15), transparent 70%);
    pointer-events: none;
    border-radius: 50%;
  }

  .el-breadcrumb {
    position: relative;
    z-index: 1;
  }
}

.cart-page-main--empty {
  min-height: min(68vh, 700px);
}

.cart-empty-block {
  text-align: center;
  padding: 120px 32px 140px;
  min-height: 500px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  box-sizing: border-box;
}

/* 主容器 */
.cart-container {
  width: 90%;
  max-width: 1200px;
  margin: 30px auto;
  padding: 30px;
  background: linear-gradient(135deg, rgba(241, 248, 233, 0.5), rgba(255, 255, 255, 0.95));
  border-radius: 20px;
  border: 1px solid rgba(46, 125, 50, 0.1);
  box-shadow: 0 10px 40px rgba(46, 125, 50, 0.08);
  position: relative;
  overflow: hidden;

  &::before {
    content: '';
    position: absolute;
    top: -80px;
    right: -80px;
    width: 250px;
    height: 250px;
    background: radial-gradient(circle, rgba(76, 175, 80, 0.08), transparent 70%);
    pointer-events: none;
    border-radius: 50%;
  }
}

/* 标题区域 */
.cart-header {
  padding: 0 30px 20px;
  margin-bottom: 20px;
  border-bottom: 2px solid rgba(46, 125, 50, 0.1);

  .header-content {
    display: flex;
    align-items: center;
    justify-content: space-between;

    .title-area {
      display: flex;
      align-items: center;

      .cart-icon {
        font-size: 32px;
        color: #388e3c;
        margin-right: 16px;
      }

      .title-text {
        .main-title {
          font-size: 26px;
          font-weight: 800;
          color: #1a2e1a;
          margin: 0;
        }

        .sub-title {
          font-size: 14px;
          color: #888;
          margin-top: 4px;
        }
      }
    }
  }
}

/* 清除购物车按钮 */
.btn-clear {
  border: 2px solid #e53935 !important;
  color: #e53935 !important;
  background: transparent !important;
  padding: 10px 20px !important;
  border-radius: 10px !important;
  font-size: 14px;
  font-weight: 600;
  transition: all 0.3s ease;

  i {
    margin-right: 6px;
  }

  &:hover {
    background: #e53935 !important;
    color: #fff !important;
    transform: translateY(-2px);
    box-shadow: 0 8px 24px rgba(229, 57, 53, 0.35);
  }
}

/* 空购物车提示 */
.empty-cart {
  text-align: center;
  padding: 100px 30px;

  .empty-icon {
    font-size: 120px;
    color: #d0d0d0;
    margin-bottom: 24px;
    animation: float 3s ease-in-out infinite;
  }

  .empty-text {
    font-size: 20px;
    color: #999;
    margin-bottom: 32px;
  }

  .btn-go-shopping {
    border: none;
    padding: 14px 48px;
    border-radius: 12px;
    background: linear-gradient(135deg, #4caf50, #2e7d32) !important;
    color: #fff;
    font-size: 16px;
    font-weight: 600;
    box-shadow: 0 6px 24px rgba(46, 125, 50, 0.35);
    transition: all 0.3s ease;

    &:hover {
      transform: translateY(-3px);
      box-shadow: 0 10px 32px rgba(46, 125, 50, 0.45);
    }
  }
}

@keyframes float {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-15px); }
}

/* 表头 */
.table-header {
  display: flex;
  align-items: center;
  padding: 16px 24px;
  background: linear-gradient(135deg, rgba(241, 248, 233, 0.8), rgba(255, 255, 255, 0.95));
  border-radius: 14px;
  margin-bottom: 20px;
  border: 1px solid rgba(46, 125, 50, 0.08);

  .col-check { width: 60px; }
  .col-info { flex: 1; }
  .col-price { width: 120px; text-align: center; }
  .col-num { width: 140px; text-align: center; }
  .col-subtotal { width: 120px; text-align: center; }
  .col-action { width: 80px; text-align: center; }

  .header-label {
    font-size: 14px;
    font-weight: 700;
    color: #1a2e1a;
  }
}

/* 商品卡片 */
.cart-item {
  display: flex;
  align-items: center;
  padding: 24px;
  background: #fff;
  border-radius: 16px;
  margin-bottom: 16px;
  border: 1px solid rgba(46, 125, 50, 0.08);
  box-shadow: 0 4px 16px rgba(46, 125, 50, 0.04);
  transition: all 0.3s ease;

  &:hover {
    box-shadow: 0 8px 32px rgba(46, 125, 50, 0.12);
    transform: translateY(-3px);
    border-color: rgba(46, 125, 50, 0.2);
  }

  .item-check { width: 60px; }
  .item-info { flex: 1; display: flex; align-items: center; }
  .item-price { width: 120px; text-align: center; }
  .item-num { width: 140px; text-align: center; }
  .item-subtotal { width: 120px; text-align: center; }
  .item-action { width: 80px; text-align: center; }
}

/* 商品图片 */
.goods-pic {
  width: 100px;
  height: 100px;
  border-radius: 14px;
  overflow: hidden;
  margin-right: 20px;
  border: 1px solid rgba(46, 125, 50, 0.1);
  flex-shrink: 0;

  .el-image {
    width: 100%;
    height: 100%;
  }
}

/* 商品名称 */
.goods-info {
  .goods-name {
    font-size: 16px;
    font-weight: 700;
    color: #1a2e1a;
    margin-bottom: 8px;
    line-height: 1.4;
  }

  .goods-type {
    font-size: 13px;
    color: #888;
  }
}

/* 价格显示 */
.price-tag {
  font-size: 18px;
  font-weight: 800;
  color: #388e3c;

  .currency {
    font-size: 13px;
    font-weight: 600;
  }
}

/* 小计显示 */
.subtotal-tag {
  font-size: 20px;
  font-weight: 800;
  color: #388e3c;

  .currency {
    font-size: 14px;
    font-weight: 600;
  }
}

/* 删除按钮 */
.btn-delete {
  color: #e53935 !important;
  font-size: 14px;
  font-weight: 600;
  padding: 8px 12px !important;
  border-radius: 8px !important;
  transition: all 0.3s ease;

  i { margin-right: 4px; }

  &:hover {
    background: rgba(229, 57, 53, 0.1) !important;
  }
}

/* 底部结算区域 */
.cart-footer {
  margin-top: 30px;
  padding: 28px 32px;
  background: linear-gradient(135deg, rgba(241, 248, 233, 0.7), rgba(255, 255, 255, 0.95));
  border-radius: 18px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border: 1px solid rgba(46, 125, 50, 0.1);
  box-shadow: 0 8px 32px rgba(46, 125, 50, 0.08);

  .footer-left {
    display: flex;
    align-items: center;

    .delete-selected {
      color: #666;
      font-size: 14px;
      margin-left: 30px;
    }
  }

  .footer-right {
    display: flex;
    align-items: center;

    .stat-item {
      margin-right: 32px;

      .stat-label {
        font-size: 14px;
        color: #666;
      }

      .stat-value {
        font-size: 28px;
        font-weight: 800;
        color: #388e3c;
        margin: 0 6px;
      }
    }

    .total-price {
      margin-right: 32px;

      .total-label {
        font-size: 14px;
        color: #666;
      }

      .total-value {
        display: flex;
        align-items: baseline;

        .price-num {
          font-size: 36px;
          font-weight: 800;
          color: #388e3c;
        }

        .price-currency {
          font-size: 16px;
          color: #388e3c;
          margin-right: 2px;
        }
      }
    }
  }
}

/* 去结算按钮 */
.btn-checkout {
  border: none;
  padding: 16px 56px;
  border-radius: 12px;
  background: linear-gradient(135deg, #4caf50, #2e7d32) !important;
  color: #fff;
  font-size: 18px;
  font-weight: 700;
  box-shadow: 0 8px 28px rgba(46, 125, 50, 0.4);
  transition: all 0.3s ease;

  i { margin-right: 8px; }

  &:hover:not(:disabled) {
    transform: translateY(-3px);
    box-shadow: 0 12px 36px rgba(46, 125, 50, 0.5);
  }

  &:disabled {
    background: #ccc !important;
    box-shadow: none;
    cursor: not-allowed;
  }
}

/* 数字输入框样式 */
::v-deep .el-input-number {
  .el-input__inner {
    border-radius: 10px;
    border: 1px solid rgba(46, 125, 50, 0.15);
    height: 40px;
    line-height: 40px;
    text-align: center;
    font-weight: 700;
    color: #333;

    &:focus {
      border-color: #4caf50;
      box-shadow: 0 0 0 3px rgba(76, 175, 80, 0.15);
    }
  }

  .el-input-number__decrease,
  .el-input-number__increase {
    border: 1px solid rgba(46, 125, 50, 0.15);
    background: linear-gradient(135deg, #f5f9f5, #fff);
    color: #388e3c;
    border-radius: 10px;
    transition: all 0.25s ease;

    &:hover {
      background: linear-gradient(135deg, #4caf50, #2e7d32);
      color: #fff;
      border-color: #4caf50;
    }
  }
}

/* 复选框样式 */
::v-deep .el-checkbox {
  .el-checkbox__input.is-checked .el-checkbox__inner {
    background-color: #4caf50;
    border-color: #4caf50;
    border-radius: 6px;
  }

  .el-checkbox__input.is-checked + .el-checkbox__label {
    color: #388e3c;
  }

  .el-checkbox__inner {
    border-radius: 6px;
    border-color: #ccc;

    &:hover {
      border-color: #4caf50;
    }
  }

  .el-checkbox__label {
    font-weight: 600;
  }
}

/* 确认对话框样式 */
::v-deep .el-message-box__wrapper .el-message-box {
  border-radius: 16px;
  overflow: hidden;
}

::v-deep .el-button--primary {
  background: linear-gradient(135deg, #4caf50, #2e7d32) !important;
  border: none !important;
  border-radius: 10px !important;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 24px rgba(46, 125, 50, 0.35);
  }
}

/* 空购物车「去逛逛」：避免与全局 default 的 !important 冲突导致浅底浅字 */
::v-deep .btn-cart-empty-go.el-button--primary {
  padding: 12px 40px !important;
  font-size: 16px !important;
  font-weight: 600 !important;
  border-radius: 8px !important;
  color: #fff !important;
  background: linear-gradient(135deg, #2e7d32, #1b5e20) !important;
  box-shadow: 0 4px 14px rgba(27, 94, 32, 0.45) !important;

  span {
    color: #fff !important;
  }

  &:hover,
  &:focus {
    color: #fff !important;
    background: linear-gradient(135deg, #388e3c, #2e7d32) !important;
    filter: brightness(1.02);
  }
}

::v-deep .el-button--danger {
  background: linear-gradient(135deg, #e53935, #c62828) !important;
  border: none !important;
  border-radius: 10px !important;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 24px rgba(229, 57, 53, 0.35);
  }
}
</style>
