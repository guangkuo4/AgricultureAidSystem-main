<template>
<div>
  <!-- 面包屑导航 -->
  <div :style='{"width":"100%","padding":"30px 10%","margin":"0 auto","borderRadius":"8px","background":"linear-gradient(180deg, rgba(80, 160, 80, 0.9), rgba(60, 140, 60, 1))","boxShadow":"0 4px 12px rgba(0, 0, 0, 0.15)","border":"1px solid rgba(255, 255, 255, 0.2)"}' class="breadcrumb-preview">
    <el-breadcrumb :separator="'>'" :style='{"fontSize":"16px","lineHeight":"1","color":"#fff"}'>
      <el-breadcrumb-item class="item1" to="/"><a style="color: #fff;">首页</a></el-breadcrumb-item>
      <el-breadcrumb-item class="item2"><span style="color: #fff;">购物车</span></el-breadcrumb-item>
    </el-breadcrumb>
  </div>

  <!-- 主容器 -->
  <div :style='{"width":"80%","padding":"30px 0","margin":"30px auto","position":"relative","background":"#f8faf8","borderRadius":"16px","boxShadow":"0 8px 32px rgba(46, 125, 50, 0.1)"}'>
    
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
    <div v-if="tableData.length == 0" :style='{"textAlign":"center","padding":"80px 30px"}'>
      <i class="el-icon-shopping-cart-empty" :style='{"fontSize":"120px","color":"#d0d0d0","marginBottom":"20px"}'></i>
      <div :style='{"fontSize":"18px","color":"#999","marginBottom":"30px"}'>购物车空空如也~</div>
      <el-button @click="$router.push('/index/zhunongshangpin')" :style='{"border":"0","padding":"12px 40px","borderRadius":"8px","background":"linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%)","color":"#fff","fontSize":"16px","boxShadow":"0 4px 12px rgba(46, 125, 50, 0.3)"}'>
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
.shangpin {
  display: flex;
}

.buy {
  text-align: right;
  padding-right: 82px;
}

// 按钮悬停效果
.el-button {
  transition: all 0.3s ease;
  
  &:hover:not(:disabled) {
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(46, 125, 50, 0.4) !important;
  }
  
  &:active:not(:disabled) {
    transform: translateY(0);
  }
}

// 数字输入框样式优化
::v-deep .el-input-number {
  .el-input__inner {
    border-radius: 8px;
    border-color: #e0e0e0;
    height: 36px;
    line-height: 36px;
    text-align: center;
    font-weight: 600;
    color: #333;
    
    &:focus {
      border-color: #2E7D32;
      box-shadow: 0 0 0 2px rgba(46, 125, 50, 0.1);
    }
  }
  
  .el-input-number__decrease,
  .el-input-number__increase {
    border-color: #e0e0e0;
    background: #f5f5f5;
    color: #666;
    transition: all 0.3s ease;
    
    &:hover {
      background: #2E7D32;
      color: #fff;
      border-color: #2E7D32;
    }
  }
}

// 复选框样式优化
::v-deep .el-checkbox {
  .el-checkbox__input.is-checked .el-checkbox__inner {
    background-color: #2E7D32;
    border-color: #2E7D32;
  }
  
  .el-checkbox__input.is-checked + .el-checkbox__label {
    color: #2E7D32;
  }
  
  .el-checkbox__inner {
    border-radius: 4px;
    border-color: #d0d0d0;
    
    &:hover {
      border-color: #2E7D32;
    }
  }
}

// 商品卡片悬停效果
div[style*="border"] {
  &:hover {
    box-shadow: 0 4px 16px rgba(46, 125, 50, 0.15) !important;
    transform: translateY(-2px) !important;
  }
}

// 空状态优化
.el-icon-shopping-cart-empty {
  animation: float 3s ease-in-out infinite;
}

@keyframes float {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-10px);
  }
}
</style>
