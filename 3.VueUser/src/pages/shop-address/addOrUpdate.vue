<template>
<div :style='{"width":"80%","padding":"20px 0","margin":"0 auto","position":"relative","background":"#FCFAFF"}'>
    <el-form class="add-update-preview" ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item :style='{"padding":"10px","margin":"0 0 10px","background":"none"}' label="联系人" prop="name">
        <el-input v-model="form.name" placeholder="联系人"></el-input>
      </el-form-item>
      <el-form-item :style='{"padding":"10px","margin":"0 0 10px","background":"none"}' label="手机号码" prop="phone">
        <el-input v-model="form.phone" placeholder="手机号码"></el-input>
      </el-form-item>
      <el-form-item :style='{"padding":"10px","margin":"0 0 10px","background":"none"}' label="默认地址">
        <el-radio-group v-model="form.isdefault">
          <el-radio label="是"></el-radio>
          <el-radio label="否"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item :style='{"padding":"10px","margin":"0 0 10px","background":"none"}' label="地址" prop="address">
        <el-input v-model="form.address" placeholder="请输入地址"></el-input>
      </el-form-item>
      <el-form-item :style='{"padding":"0","margin":"0"}'>
        <el-button :style='{"border":"0","cursor":"pointer","padding":"0","margin":"0 20px 0 0","outline":"none","color":"rgba(255, 255, 255, 1)","borderRadius":"5px","background":"#A293B6","width":"128px","lineHeight":"40px","fontSize":"14px","height":"40px"}' type="primary" @click="onSubmit('form')">添加</el-button>
        <el-button v-if="!isBuy" :style='{"border":"none","cursor":"pointer","padding":"0","margin":"0","outline":"none","color":"#fff","borderRadius":"5px","background":"#9e9e9e","width":"128px","lineHeight":"40px","fontSize":"14px","height":"40px"}' @click="goBack">取消</el-button>
      </el-form-item>
    </el-form>

    <el-dialog id="myMapDialog" title="请选择收货地址" width="500px" :visible.sync="dialogVisible" top="5vh">
      <baidu-map class="map" :center="center" :zoom="zoom" :scroll-wheel-zoom="true" @ready="onBaiduMapReady" @click="getClickInfo">
        <bm-view style="width: 100%; height: 100%;" />
        <bm-marker :position="{lng: center.lng, lat: center.lat}" :dragging="true" animation="BMAP_ANIMATION_BOUNCE" />
        <bm-geolocation
          anchor="BMAP_ANCHOR_BOTTOM_RIGHT"
          :showAddressBar="true"
          :autoLocation="true"
        ></bm-geolocation>
      </baidu-map>
      <div slot="footer">
        <el-input v-model="address" style="margin-bottom: 15px;" placeholder="请选择或填写收货地址"></el-input>
        <el-button type="primary" @click="getAddr">确定</el-button>
        <el-button @click="dialogVisible = false">取消</el-button>
      </div>
    </el-dialog>
</div>
</template>

<script>
  import { Loading } from 'element-ui';
  export default {
    //数据集合
    data() {
      return {
        form: {
          userid: localStorage.getItem('frontUserid'),
          name: localStorage.getItem('username'),
          phone: '',
          isdefault: '是',
          address: ''
        },
        rules: {
          name: [{ required: true, message: '请输入联系人', trigger: 'blur' }],
          phone: [
            { required: true, message: '请输入手机号码', trigger: 'blur' },
            { required: true, validator: this.$validate.isMobile, trigger: 'blur' }
          ],
          address: [{ required: true, message: '请选择收货地址', trigger: 'blur' }]
        },
        dialogVisible: false,
        BMap: null, // 地图组件是否就绪
        // center: { lat: 24.30457061, lng: 116.12640310 },
        center: { lng: 0, lat: 0 },
        address: '',
        zoom: 15,
        loadingInstance: null,
        isEdit: false,
		isBuy: false
      }
    },
    created() {
      if (this.$route.query.id != undefined) {
        this.isEdit = true;
        this.form = Object.assign({}, JSON.parse(this.$route.query.editObj));
      }
    },
    //方法集合
    methods: {
		buyAdd(){
			this.isBuy = true
		},
      onSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$http.post(`address/${this.isEdit ? 'update' : 'add'}`, this.form).then(res => {
              if (res.data.code == 0) {
                this.$message({
                  message: `${this.isEdit ? '更新' : '添加'}成功`,
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
					  if(this.isBuy){
						this.$emit('change')
					  }else{
						this.$router.go(-1);
					  }
                  }
                });
              }
            });
          } else {
            return false;
          }
        });
      },
      getAddr() {
        if (this.address == '') {
          this.$message({
            message: '地址不能为空',
            type: 'error',
            duration: 1500
          });
          return;
        }
        this.form.address = this.address;
        this.dialogVisible = false;
      },
      goBack() {
        this.$router.go(-1);
      },
      onBaiduMapReady(e) {
        let that = this
        this.BMap = e.BMap;
        if (this.BMap) {
          const geolocation = new this.BMap.Geolocation();
          geolocation.getCurrentPosition(res => {
            that.$nextTick(() => {
              that.loadingInstance.close();
            });
            that.center.lng = res.point.lng
            that.center.lat = res.point.lat
            const { province, city, district, street, street_number } = res.address;
            that.address = province + city + district + street + street_number;
          })
        }
      },
      getClickInfo(e) {
        this.center.lng = e.point.lng
        this.center.lat = e.point.lat

        if (this.BMap) {
          let that = this
          const geoCoder = new this.BMap.Geocoder()
          geoCoder.getLocation(e.point, function(res) {
            const addrComponent = res.addressComponents
            const surroundingPois = res.surroundingPois
            const province = addrComponent.province
            const city = addrComponent.city
            const district = addrComponent.district
            let addr = addrComponent.street
            if (surroundingPois.length > 0 && surroundingPois[0].title) {
              if (addr) {
                addr += `-${surroundingPois[0].title}`
              } else {
                addr += `${surroundingPois[0].title}`
              }
            } else {
              addr += addrComponent.streetNumber
            }
            that.address = province + city + district + addr;
          })
        }
      },
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
/* ========== 地址添加/编辑页样式 ========== */
.add-update-preview {
  width: 90%;
  max-width: 700px;
  margin: 30px auto;
  padding: 30px;
  background: linear-gradient(135deg, rgba(241, 248, 233, 0.5), rgba(255, 255, 255, 0.95));
  border-radius: 20px;
  border: 1px solid rgba(46, 125, 50, 0.1);
  box-shadow: 0 10px 40px rgba(46, 125, 50, 0.08);
  position: relative;

  &::before {
    content: '';
    position: absolute;
    top: -60px;
    right: -60px;
    width: 200px;
    height: 200px;
    background: radial-gradient(circle, rgba(76, 175, 80, 0.06), transparent 70%);
    pointer-events: none;
    border-radius: 50%;
  }
}

/* 表单项 */
.add-update-preview .el-form-item {
  padding: 12px;
  margin: 0 0 16px;
  background: transparent;
}

.add-update-preview .el-form-item ::v-deep .el-form-item__label {
  padding: 0 12px 0 0;
  color: #1a2e1a;
  font-weight: 700;
  width: 100px;
  font-size: 14px;
  line-height: 40px;
  text-align: right;
}

.add-update-preview .el-form-item ::v-deep .el-form-item__content {
  margin-left: 100px;
}

/* 输入框样式 */
.add-update-preview .el-input ::v-deep .el-input__inner,
.add-update-preview .el-select ::v-deep .el-input__inner {
  border: 1px solid rgba(46, 125, 50, 0.15) !important;
  border-radius: 12px !important;
  padding: 0 16px !important;
  box-shadow: 0 2px 8px rgba(46, 125, 50, 0.06) !important;
  outline: none;
  color: #333;
  width: 100%;
  font-size: 14px;
  height: 44px;
  line-height: 44px;
  transition: all 0.3s ease;

  &:focus {
    border-color: #4caf50 !important;
    box-shadow: 0 0 0 3px rgba(76, 175, 80, 0.15) !important;
  }

  &::placeholder {
    color: #aaa;
  }
}

/* 日期选择器 */
.add-update-preview .el-date-editor ::v-deep .el-input__inner {
  border: 1px solid rgba(46, 125, 50, 0.15) !important;
  border-radius: 12px !important;
  padding: 0 16px 0 40px !important;
  box-shadow: 0 2px 8px rgba(46, 125, 50, 0.06) !important;
  outline: none;
  color: #333;
  width: 100%;
  font-size: 14px;
  height: 44px;
  line-height: 44px;
  transition: all 0.3s ease;

  &:focus {
    border-color: #4caf50 !important;
    box-shadow: 0 0 0 3px rgba(76, 175, 80, 0.15) !important;
  }
}

/* 单选按钮组 */
.add-update-preview ::v-deep .el-radio {
  margin-right: 20px;

  .el-radio__inner {
    border: 1px solid rgba(46, 125, 50, 0.2);
    border-radius: 50%;
    transition: all 0.25s ease;

    &:after {
      background: #4caf50;
    }
  }

  .el-radio__input.is-checked .el-radio__inner {
    background-color: #4caf50;
    border-color: #4caf50;
  }

  .el-radio__label {
    font-weight: 600;
    color: #333;
  }

  .el-radio__input.is-checked + .el-radio__label {
    color: #388e3c;
  }
}

/* 文本域 */
.add-update-preview .el-textarea ::v-deep .el-textarea__inner {
  border: 1px solid rgba(46, 125, 50, 0.15) !important;
  border-radius: 12px !important;
  padding: 14px 16px;
  box-shadow: 0 2px 8px rgba(46, 125, 50, 0.06) !important;
  outline: none;
  color: #333;
  width: 100%;
  font-size: 14px;
  min-height: 120px !important;
  resize: vertical;
  transition: all 0.3s ease;

  &:focus {
    border-color: #4caf50 !important;
    box-shadow: 0 0 0 3px rgba(76, 175, 80, 0.15) !important;
  }
}

/* 提交按钮 */
.add-update-preview .el-button--primary {
  border: none !important;
  border-radius: 12px !important;
  background: linear-gradient(135deg, #4caf50, #2e7d32) !important;
  color: #fff;
  font-weight: 700;
  font-size: 15px;
  padding: 12px 36px !important;
  margin: 0 24px 0 0;
  box-shadow: 0 6px 20px rgba(46, 125, 50, 0.35);
  transition: all 0.3s ease;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 10px 28px rgba(46, 125, 50, 0.45);
  }
}

/* 取消按钮 */
.add-update-preview .el-button:not(.el-button--primary) {
  border: none !important;
  border-radius: 12px !important;
  background: linear-gradient(135deg, #9e9e9e, #757575) !important;
  color: #fff;
  font-weight: 600;
  font-size: 15px;
  padding: 12px 28px !important;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  transition: all 0.3s ease;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 18px rgba(0, 0, 0, 0.2);
  }
}

/* 地图弹窗 */
#myMapDialog ::v-deep .el-dialog {
  border-radius: 20px;
  overflow: hidden;
}

#myMapDialog ::v-deep .el-dialog__header {
  background: linear-gradient(135deg, #4caf50, #2e7d32);
  padding: 16px 20px;

  .el-dialog__title {
    color: #fff;
    font-weight: 700;
  }
}

#myMapDialog ::v-deep .el-dialog__body {
  padding: 20px;
}

#myMapDialog ::v-deep .el-dialog__footer {
  padding: 16px 20px;
  border-top: 1px solid rgba(46, 125, 50, 0.1);
}

.map {
  height: 50vh;
  border-radius: 12px;
  overflow: hidden;
  border: 1px solid rgba(46, 125, 50, 0.1);
}

/* 上传组件 */
.add-update-preview ::v-deep .el-upload--picture-card {
  background: transparent;
  border: 0;
  border-radius: 0;
  width: auto;
  height: auto;
  line-height: initial;
  vertical-align: middle;
}

.add-update-preview ::v-deep .upload .upload-img {
  border: 1px solid rgba(46, 125, 50, 0.15);
  cursor: pointer;
  border-radius: 12px;
  color: #4caf50;
  width: 100px;
  font-size: 28px;
  line-height: 100px;
  text-align: center;
  height: 100px;
  background: rgba(76, 175, 80, 0.05);
  transition: all 0.3s ease;

  &:hover {
    border-color: #4caf50;
    background: rgba(76, 175, 80, 0.1);
  }
}

.add-update-preview ::v-deep .el-upload-list .el-upload-list__item {
  border: 1px solid rgba(46, 125, 50, 0.15);
  cursor: pointer;
  border-radius: 12px;
  width: 100px;
  height: 100px;
  line-height: 100px;
  text-align: center;
  margin-right: 10px;
  transition: all 0.3s ease;

  &:hover {
    border-color: #4caf50;
  }
}

.add-update-preview ::v-deep .el-upload .el-icon-plus {
  border: 1px solid rgba(46, 125, 50, 0.15);
  cursor: pointer;
  border-radius: 12px;
  color: #4caf50;
  width: 100px;
  font-size: 28px;
  line-height: 100px;
  text-align: center;
  height: 100px;
  background: rgba(76, 175, 80, 0.05);
  transition: all 0.3s ease;

  &:hover {
    border-color: #4caf50;
    background: rgba(76, 175, 80, 0.1);
  }
}
</style>
