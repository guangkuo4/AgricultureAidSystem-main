<template>
<div>
  <!-- 面包屑导航 -->
  <div :style='{"width":"100%","padding":"30px 10%","margin":"0 auto","borderRadius":"8px","background":"linear-gradient(180deg, rgba(80, 160, 80, 0.9), rgba(60, 140, 60, 1))","boxShadow":"0 4px 12px rgba(0, 0, 0, 0.15)","border":"1px solid rgba(255, 255, 255, 0.2)"}' class="breadcrumb-preview">
    <el-breadcrumb :separator="'>'" :style='{"fontSize":"16px","lineHeight":"1","color":"#fff"}'>
      <el-breadcrumb-item class="item1" to="/"><a style="color: #fff;">首页</a></el-breadcrumb-item>
      <el-breadcrumb-item class="item2"><span style="color: #fff;">个人中心</span></el-breadcrumb-item>
    </el-breadcrumb>
  </div>

  <!-- 主容器 -->
  <div class="center-preview" :style='{"width":"80%","margin":"30px auto","position":"relative","flexWrap":"wrap","background":"#f8faf8","display":"flex","borderRadius":"16px","boxShadow":"0 8px 32px rgba(46, 125, 50, 0.1)","overflow":"hidden"}'>
    
    <!-- 左侧个人信息卡片 -->
    <div class="info" :style='{"padding":"40px 30px","boxShadow":"none","margin":"0","borderRadius":"0","background":"linear-gradient(180deg, rgba(46, 125, 50, 0.05) 0%, rgba(46, 125, 50, 0.02) 100%)","width":"320px","position":"relative","height":"auto","borderRight":"1px solid rgba(46, 125, 50, 0.1)"}'>
      
      <!-- 头像区域 -->
      <div :style='{"width":"120px","height":"120px","margin":"0 auto 20px","position":"relative"}'>
        <div :style='{"width":"100%","height":"100%","borderRadius":"50%","overflow":"hidden","border":"4px solid #fff","boxShadow":"0 4px 16px rgba(46, 125, 50, 0.2)"}'>
          <el-image :style='{"width":"100%","height":"100%","objectFit":"cover"}' :src="sessionForm.touxiang?baseUrl + sessionForm.touxiang:require('@/assets/avator.png')" fit="cover"></el-image>
        </div>
        <div :style='{"position":"absolute","bottom":"5px","right":"5px","width":"24px","height":"24px","background":"#2E7D32","borderRadius":"50%","border":"3px solid #fff","display":"flex","alignItems":"center","justifyContent":"center"}'>
          <i class="el-icon-check" :style='{"color":"#fff","fontSize":"12px"}'></i>
        </div>
      </div>

      <!-- 用户名称 -->
      <div :style='{"textAlign":"center","marginBottom":"30px"}'>
        <div :style='{"fontSize":"22px","fontWeight":"bold","color":"#1a1a1a","marginBottom":"8px"}' v-if="userTableName=='nonghu'">
          {{sessionForm.nonghuxingming}}
        </div>
        <div :style='{"fontSize":"22px","fontWeight":"bold","color":"#1a1a1a","marginBottom":"8px"}' v-if="userTableName=='yonghu'">
          {{sessionForm.yonghuxingming}}
        </div>
        <div :style='{"fontSize":"14px","color":"#2E7D32","background":"rgba(46, 125, 50, 0.1)","padding":"4px 12px","borderRadius":"20px","display":"inline-block"}'>
          <span v-if="userTableName=='nonghu'">农户用户</span>
          <span v-if="userTableName=='yonghu'">普通用户</span>
        </div>
      </div>

      <!-- 信息列表 -->
      <div :style='{"background":"#fff","borderRadius":"12px","padding":"20px","boxShadow":"0 2px 8px rgba(0,0,0,0.04)"}'>
        <div :style='{"display":"flex","alignItems":"center","padding":"12px 0","borderBottom":"1px solid #f0f0f0"}' v-if="userTableName=='nonghu'">
          <i class="el-icon-user" :style='{"color":"#2E7D32","fontSize":"16px","marginRight":"10px"}'></i>
          <div>
            <div :style='{"fontSize":"12px","color":"#999"}'>账号</div>
            <div :style='{"fontSize":"14px","color":"#333","fontWeight":"500"}'>{{sessionForm.nonghuzhanghao}}</div>
          </div>
        </div>
        <div :style='{"display":"flex","alignItems":"center","padding":"12px 0","borderBottom":"1px solid #f0f0f0"}' v-if="userTableName=='yonghu'">
          <i class="el-icon-user" :style='{"color":"#2E7D32","fontSize":"16px","marginRight":"10px"}'></i>
          <div>
            <div :style='{"fontSize":"12px","color":"#999"}'>账号</div>
            <div :style='{"fontSize":"14px","color":"#333","fontWeight":"500"}'>{{sessionForm.yonghuzhanghao}}</div>
          </div>
        </div>

        <div :style='{"display":"flex","alignItems":"center","padding":"12px 0","borderBottom":"1px solid #f0f0f0"}'>
          <i class="el-icon-male" :style='{"color":"#2E7D32","fontSize":"16px","marginRight":"10px"}'></i>
          <div>
            <div :style='{"fontSize":"12px","color":"#999"}'>性别</div>
            <div :style='{"fontSize":"14px","color":"#333","fontWeight":"500"}'>{{sessionForm.xingbie || '未设置'}}</div>
          </div>
        </div>

        <div :style='{"display":"flex","alignItems":"center","padding":"12px 0","borderBottom":"1px solid #f0f0f0"}' v-if="userTableName=='nonghu'">
          <i class="el-icon-phone" :style='{"color":"#2E7D32","fontSize":"16px","marginRight":"10px"}'></i>
          <div>
            <div :style='{"fontSize":"12px","color":"#999"}'>联系电话</div>
            <div :style='{"fontSize":"14px","color":"#333","fontWeight":"500"}'>{{sessionForm.nonghudianhua || '未设置'}}</div>
          </div>
        </div>
        <div :style='{"display":"flex","alignItems":"center","padding":"12px 0","borderBottom":"1px solid #f0f0f0"}' v-if="userTableName=='yonghu'">
          <i class="el-icon-phone" :style='{"color":"#2E7D32","fontSize":"16px","marginRight":"10px"}'></i>
          <div>
            <div :style='{"fontSize":"12px","color":"#999"}'>联系电话</div>
            <div :style='{"fontSize":"14px","color":"#333","fontWeight":"500"}'>{{sessionForm.yonghudianhua || '未设置'}}</div>
          </div>
        </div>

        <div :style='{"display":"flex","alignItems":"center","padding":"12px 0"}'>
          <i class="el-icon-wallet" :style='{"color":"#2E7D32","fontSize":"16px","marginRight":"10px"}'></i>
          <div>
            <div :style='{"fontSize":"12px","color":"#999"}'>账户余额</div>
            <div :style='{"fontSize":"18px","color":"#2E7D32","fontWeight":"bold"}'>¥{{sessionForm.money || '0.00'}}</div>
          </div>
        </div>
      </div>

      <!-- 会员标识 -->
      <div v-if="userTableName=='yonghu' && sessionForm.vip=='是'" :style='{"marginTop":"20px","background":"linear-gradient(135deg, #FFD700 0%, #FFA500 100%)","borderRadius":"12px","padding":"15px","display":"flex","alignItems":"center","justifyContent":"center","boxShadow":"0 4px 12px rgba(255, 215, 0, 0.3)"}'>
        <i class="el-icon-medal" :style='{"color":"#fff","fontSize":"24px","marginRight":"10px"}'></i>
        <div>
          <div :style='{"fontSize":"16px","fontWeight":"bold","color":"#fff"}'>VIP会员</div>
          <div :style='{"fontSize":"12px","color":"rgba(255,255,255,0.8)"}'>尊享专属特权</div>
        </div>
      </div>
    </div>
    
    <!-- 右侧内容区域 -->
    <div :style='{"flex":"1","padding":"30px","background":"#fff"}'>
      <el-tabs tab-position="left" :style='{"width":"100%"}' @tab-click="handleClick">
        
        <!-- 个人中心 -->
        <el-tab-pane label="个人中心">
          <div :style='{"fontSize":"20px","fontWeight":"bold","color":"#1a1a1a","marginBottom":"25px","paddingBottom":"15px","borderBottom":"2px solid rgba(46, 125, 50, 0.1)"}'>编辑资料</div>
          <el-form class="center-preview-pv" ref="sessionForm" :model="sessionForm" :rules="rules" label-width="100px">
            <el-form-item :style='{"marginBottom":"20px"}' v-if="userTableName=='nonghu'" label="农户账号" prop="nonghuzhanghao">
              <el-input v-model="sessionForm.nonghuzhanghao" placeholder="农户账号" readonly :style='{"background":"#f5f5f5"}'></el-input>
            </el-form-item>
            <el-form-item :style='{"marginBottom":"20px"}' v-if="userTableName=='nonghu'" label="农户姓名" prop="nonghuxingming">
              <el-input v-model="sessionForm.nonghuxingming" placeholder="请输入农户姓名"></el-input>
            </el-form-item>
            <el-form-item :style='{"marginBottom":"20px"}' v-if="userTableName=='nonghu'" label="性别">
              <el-select v-model="sessionForm.xingbie" placeholder="请选择性别" :style='{"width":"100%"}'>
                <el-option v-for="(item, index) in dynamicProp.xingbie" :key="index" :label="item" :value="item"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item :style='{"marginBottom":"20px"}' v-if="userTableName=='nonghu'" label="农户电话" prop="nonghudianhua">
              <el-input v-model="sessionForm.nonghudianhua" placeholder="请输入农户电话"></el-input>
            </el-form-item>
            <el-form-item :style='{"marginBottom":"20px"}' v-if="userTableName=='nonghu'" label="头像">
              <file-upload
                tip="点击上传头像"
                action="file/upload"
                :limit="1"
                :multiple="true"
                :fileUrls="sessionForm.touxiang?sessionForm.touxiang:''"
                @change="nonghutouxiangHandleAvatarSuccess"
              ></file-upload>
            </el-form-item>

            <el-form-item class="balance" :style='{"marginBottom":"20px"}' v-if="userTableName=='nonghu'" label="余额">
              <div :style='{"flexWrap":"wrap","display":"flex","alignItems":"center"}'>
                <el-input v-model="sessionForm.money" placeholder="余额" readonly :style='{"width":"300px","marginRight":"15px"}'></el-input>
                <el-button @click="dialogFormVisibleMoney = true" :style='{"border":"0","cursor":"pointer","padding":"0 25px","margin":"0","outline":"none","color":"rgba(255, 255, 255, 1)","borderRadius":"8px","background":"linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%)","width":"auto","lineHeight":"40px","fontSize":"14px","height":"40px","boxShadow":"0 4px 12px rgba(46, 125, 50, 0.3)"}'>
                  <i class="el-icon-credit-pay" style="margin-right: 5px;"></i>点我充值
                </el-button>
              </div>
            </el-form-item>

            <el-form-item :style='{"marginBottom":"20px"}' v-if="userTableName=='yonghu'" label="用户账号" prop="yonghuzhanghao">
              <el-input v-model="sessionForm.yonghuzhanghao" placeholder="用户账号" readonly :style='{"background":"#f5f5f5"}'></el-input>
            </el-form-item>
            <el-form-item :style='{"marginBottom":"20px"}' v-if="userTableName=='yonghu'" label="用户姓名" prop="yonghuxingming">
              <el-input v-model="sessionForm.yonghuxingming" placeholder="请输入用户姓名"></el-input>
            </el-form-item>
            <el-form-item :style='{"marginBottom":"20px"}' v-if="userTableName=='yonghu'" label="性别">
              <el-select v-model="sessionForm.xingbie" placeholder="请选择性别" :style='{"width":"100%"}'>
                <el-option v-for="(item, index) in dynamicProp.xingbie" :key="index" :label="item" :value="item"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item :style='{"marginBottom":"20px"}' v-if="userTableName=='yonghu'" label="用户电话" prop="yonghudianhua">
              <el-input v-model="sessionForm.yonghudianhua" placeholder="请输入用户电话"></el-input>
            </el-form-item>
            <el-form-item :style='{"marginBottom":"20px"}' v-if="userTableName=='yonghu'" label="头像">
              <file-upload
                tip="点击上传头像"
                action="file/upload"
                :limit="1"
                :multiple="true"
                :fileUrls="sessionForm.touxiang?sessionForm.touxiang:''"
                @change="yonghutouxiangHandleAvatarSuccess"
              ></file-upload>
            </el-form-item>

            <el-form-item class="balance" :style='{"marginBottom":"20px"}' v-if="userTableName=='yonghu'" label="余额">
              <div :style='{"flexWrap":"wrap","display":"flex","alignItems":"center"}'>
                <el-input v-model="sessionForm.money" placeholder="余额" readonly :style='{"width":"300px","marginRight":"15px"}'></el-input>
                <el-button @click="dialogFormVisibleMoney = true" :style='{"border":"0","cursor":"pointer","padding":"0 25px","margin":"0","outline":"none","color":"rgba(255, 255, 255, 1)","borderRadius":"8px","background":"linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%)","width":"auto","lineHeight":"40px","fontSize":"14px","height":"40px","boxShadow":"0 4px 12px rgba(46, 125, 50, 0.3)"}'>
                  <i class="el-icon-credit-pay" style="margin-right: 5px;"></i>点我充值
                </el-button>
              </div>
            </el-form-item>

            <el-form-item class="balance" :style='{"marginBottom":"20px"}' v-if="userTableName=='yonghu'" label="会员">
              <div :style='{"flexWrap":"wrap","display":"flex","alignItems":"center"}'>
                <el-input v-model="sessionForm.vip" placeholder="会员" readonly :style='{"width":"300px","marginRight":"15px"}'></el-input>
                <el-button @click="dialogFormVisibleVip = true" :style='{"border":"0","cursor":"pointer","padding":"0 25px","margin":"0","outline":"none","color":"rgba(255, 255, 255, 1)","borderRadius":"8px","background":"linear-gradient(135deg, #FFD700 0%, #FFA500 100%)","width":"auto","lineHeight":"40px","fontSize":"14px","height":"40px","boxShadow":"0 4px 12px rgba(255, 215, 0, 0.3)"}'>
                  <i class="el-icon-medal" style="margin-right: 5px;"></i>会员购买
                </el-button>
              </div>
            </el-form-item>

            <el-form-item :style='{"marginTop":"30px"}'>
              <el-button :style='{"border":"0","cursor":"pointer","padding":"0 30px","margin":"0 20px 0 0","outline":"none","color":"rgba(255, 255, 255, 1)","borderRadius":"8px","background":"linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%)","width":"auto","lineHeight":"44px","fontSize":"16px","height":"44px","boxShadow":"0 4px 12px rgba(46, 125, 50, 0.3)"}' type="primary" @click="onSubmit('sessionForm')">
                <i class="el-icon-check" style="margin-right: 5px;"></i>保存修改
              </el-button>
              <el-button :style='{"border":"1px solid #dc3545","cursor":"pointer","padding":"0 30px","margin":"0","outline":"none","color":"#dc3545","borderRadius":"8px","background":"transparent","width":"auto","lineHeight":"44px","fontSize":"16px","height":"44px"}' type="danger" @click="logout">
                <i class="el-icon-switch-button" style="margin-right: 5px;"></i>退出登录
              </el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>

        <!-- 修改密码 -->
        <el-tab-pane label="修改密码">
          <div :style='{"fontSize":"20px","fontWeight":"bold","color":"#1a1a1a","marginBottom":"25px","paddingBottom":"15px","borderBottom":"2px solid rgba(46, 125, 50, 0.1)"}'>修改密码</div>
          <el-form class="center-preview-pv" ref="passwordForm" :model="passwordForm" :rules="passwordRules" label-width="100px">
            <el-form-item :style='{"marginBottom":"20px"}' label="原密码" prop="password">
              <el-input type="password" v-model="passwordForm.password" placeholder="请输入原密码" show-password></el-input>
            </el-form-item>
            <el-form-item :style='{"marginBottom":"20px"}' label="新密码" prop="newpassword">
              <el-input type="password" v-model="passwordForm.newpassword" placeholder="请输入新密码（至少6位）" show-password></el-input>
            </el-form-item>
            <el-form-item :style='{"marginBottom":"20px"}' label="确认密码" prop="repassword">
              <el-input type="password" v-model="passwordForm.repassword" placeholder="请再次输入新密码" show-password></el-input>
            </el-form-item>
            <el-form-item :style='{"marginTop":"30px"}'>
              <el-button :style='{"border":"0","cursor":"pointer","padding":"0 30px","margin":"0 20px 0 0","outline":"none","color":"rgba(255, 255, 255, 1)","borderRadius":"8px","background":"linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%)","width":"auto","lineHeight":"44px","fontSize":"16px","height":"44px","boxShadow":"0 4px 12px rgba(46, 125, 50, 0.3)"}' type="primary" @click="updatePassword">
                <i class="el-icon-lock" style="margin-right: 5px;"></i>确认修改
              </el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>

        <!-- 我的订单 -->
        <el-tab-pane label="我的订单">
          <div :style='{"fontSize":"20px","fontWeight":"bold","color":"#1a1a1a","marginBottom":"25px","paddingBottom":"15px","borderBottom":"2px solid rgba(46, 125, 50, 0.1)"}'>我的订单</div>
          <router-view></router-view>
        </el-tab-pane>

        <!-- 我的地址 -->
        <el-tab-pane label="我的地址" name="MyAddress">
          <div :style='{"fontSize":"20px","fontWeight":"bold","color":"#1a1a1a","marginBottom":"25px","paddingBottom":"15px","borderBottom":"2px solid rgba(46, 125, 50, 0.1)"}'>我的地址</div>
          <router-view></router-view>
        </el-tab-pane>

        <!-- 我的收藏 -->
        <el-tab-pane label="我的收藏">
          <div :style='{"fontSize":"20px","fontWeight":"bold","color":"#1a1a1a","marginBottom":"25px","paddingBottom":"15px","borderBottom":"2px solid rgba(46, 125, 50, 0.1)"}'>我的收藏</div>
          <router-view></router-view>
        </el-tab-pane>

        <!-- 动态菜单 -->
        <el-tab-pane v-for="(item,index) in menuList" :key="index" v-if="hasBack(item.menu)" :label="item.child[0].menu" :name="item.child[0].tableName"></el-tab-pane>
      </el-tabs>
    </div>
  </div>

  <!-- 充值弹窗 -->
  <el-dialog title="账户充值" :visible.sync="dialogFormVisibleMoney" width="500px" center :close-on-click-modal="false">
    <div :style='{"padding":"20px"}'>
      <el-form :model="chongzhiForm" label-width="100px">
        <el-form-item label="充值金额">
          <el-input type="number" v-model="chongzhiForm.money" autocomplete="off" placeholder="请输入充值金额" :style='{"width":"100%"}'>
            <template slot="prepend">¥</template>
          </el-input>
        </el-form-item>
        <el-form-item label="支付方式">
          <el-radio-group v-model="chongzhiForm.radio" :style='{"width":"100%"}'>
            <el-radio label="微信支付" :style='{"display":"block","marginBottom":"15px","padding":"15px","border":"1px solid #e0e0e0","borderRadius":"8px","width":"100%"}'>
              <div :style='{"display":"flex","alignItems":"center"}'>
                <el-image style="width: 30px; height: 30px; margin-right: 10px;" :src="require('@/assets/weixin.png')" fit="fill"></el-image>
                <span>微信支付</span>
              </div>
            </el-radio>
            <el-radio label="支付宝支付" :style='{"display":"block","marginBottom":"15px","padding":"15px","border":"1px solid #e0e0e0","borderRadius":"8px","width":"100%"}'>
              <div :style='{"display":"flex","alignItems":"center"}'>
                <el-image style="width: 30px; height: 30px; margin-right: 10px;" :src="require('@/assets/zhifubao.png')" fit="fill"></el-image>
                <span>支付宝支付</span>
              </div>
            </el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
    </div>
    <div slot="footer" class="dialog-footer" :style='{"padding":"20px","borderTop":"1px solid #eee"}'>
      <el-button @click="dialogFormVisibleMoney = false" :style='{"padding":"10px 25px"}'>取 消</el-button>
      <el-button type="primary" @click="chongzhi" :style='{"padding":"10px 25px","background":"#2E7D32","borderColor":"#2E7D32"}'>确认充值</el-button>
    </div>
  </el-dialog>

  <!-- 会员购买弹窗 -->
  <el-dialog title="开通VIP会员" :visible.sync="dialogFormVisibleVip" width="500px" center :close-on-click-modal="false">
    <div :style='{"padding":"20px","textAlign":"center"}'>
      <div :style='{"width":"80px","height":"80px","margin":"0 auto 20px","background":"linear-gradient(135deg, #FFD700 0%, #FFA500 100%)","borderRadius":"50%","display":"flex","alignItems":"center","justifyContent":"center","boxShadow":"0 4px 16px rgba(255, 215, 0, 0.4)"}'>
        <i class="el-icon-medal" :style='{"fontSize":"40px","color":"#fff"}'></i>
      </div>
      <div :style='{"fontSize":"24px","fontWeight":"bold","color":"#1a1a1a","marginBottom":"10px"}'>VIP会员</div>
      <div :style='{"fontSize":"14px","color":"#666","marginBottom":"30px"}'>尊享专属特权，畅享更多服务</div>
      
      <div :style='{"background":"#f8f8f8","borderRadius":"12px","padding":"20px","marginBottom":"20px"}'>
        <div :style='{"fontSize":"32px","fontWeight":"bold","color":"#2E7D32"}'>¥199<span :style='{"fontSize":"14px","color":"#999","fontWeight":"normal"}'>/年</span></div>
        <div :style='{"fontSize":"12px","color":"#999","marginTop":"5px"}'>限时优惠价</div>
      </div>

      <el-form :model="chongzhiForm">
        <el-form-item label="支付方式" label-width="80px">
          <el-radio-group v-model="chongzhiForm.radio">
            <el-radio label="微信支付" :style='{"marginRight":"20px"}'>微信支付</el-radio>
            <el-radio label="支付宝支付">支付宝支付</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
    </div>
    <div slot="footer" class="dialog-footer" :style='{"padding":"20px","borderTop":"1px solid #eee"}'>
      <el-button @click="dialogFormVisibleVip = false" :style='{"padding":"10px 25px"}'>取 消</el-button>
      <el-button type="primary" @click="chongzhivip" :style='{"padding":"10px 25px","background":"linear-gradient(135deg, #FFD700 0%, #FFA500 100%)","border":"none"}'>立即开通</el-button>
    </div>
  </el-dialog>
</div>
</template>

<script>
  import config from '@/config/config'
  import menu from '@/config/menu'
  import Vue from 'vue'
  export default {
    data() {
      return {
        title: '个人中心',
        baseUrl: config.baseUrl,
        sessionForm: {},
        passwordForm: {},
        passwordRules: {
          password: [{ required: true, message: "请输入原密码", trigger: "blur" }],
          newpassword: [{ required: true, message: "请输入新密码", trigger: "blur" }, { min: 6, message: "密码长度不能少于6位", trigger: "blur" }],
          repassword: [{ required: true, message: "请确认新密码", trigger: "blur" }]
        },
        rules: {},
        chongzhiForm: { money: '', radio: '' },
        menuList: [],
        disabled: false,
        dialogFormVisibleMoney: false,
        dialogFormVisibleVip: false,
        uploadUrl: config.baseUrl + 'file/upload',
        imageUrl: '',
        headers: {Token: localStorage.getItem('frontToken')},
        userTableName: localStorage.getItem('UserTableName'),
        dynamicProp: {},
      }
    },
    created() {
      let menus = menu.list()
      for(let x in menus){
        if(menus[x].tableName == this.userTableName){
          this.menuList = menus[x].backMenu
        }
      }
      if ('nonghu' == this.userTableName) {
        this.$set(this.sessionForm, 'nonghuzhanghao', null);
        this.$set(this.sessionForm, 'mima', null);
        this.$set(this.sessionForm, 'nonghuxingming', null);
        this.$set(this.sessionForm, 'xingbie', null);
        this.$set(this.sessionForm, 'nonghudianhua', null);
        this.$set(this.sessionForm, 'touxiang', null);
        this.$set(this.sessionForm, 'money', null);
      }
      if ('yonghu' == this.userTableName) {
        this.$set(this.sessionForm, 'yonghuzhanghao', null);
        this.$set(this.sessionForm, 'mima', null);
        this.$set(this.sessionForm, 'yonghuxingming', null);
        this.$set(this.sessionForm, 'xingbie', null);
        this.$set(this.sessionForm, 'yonghudianhua', null);
        this.$set(this.sessionForm, 'touxiang', null);
        this.$set(this.sessionForm, 'money', null);
        this.$set(this.sessionForm, 'vip', null);
      }

      if ('nonghu' == this.userTableName) {
        this.$set(this.rules, 'nonghuzhanghao', [{ required: true, message: '请输入农户账号', trigger: 'blur' }]);
        this.$set(this.rules, 'mima', [{ required: true, message: '请输入密码', trigger: 'blur' }]);
        this.$set(this.rules, 'nonghuxingming', [{ required: true, message: '请输入农户姓名', trigger: 'blur' }]);
        this.$set(this.rules, 'nonghudianhua', [{ required: false, validator: this.$validate.isMobile, trigger: 'blur' }]);
        this.$set(this.rules, 'money', [{ required: false, validator: this.$validate.isNumber, trigger: 'blur' }]);
      }
      if ('yonghu' == this.userTableName) {
        this.$set(this.rules, 'yonghuzhanghao', [{ required: true, message: '请输入用户账号', trigger: 'blur' }]);
        this.$set(this.rules, 'mima', [{ required: true, message: '请输入密码', trigger: 'blur' }]);
        this.$set(this.rules, 'yonghuxingming', [{ required: true, message: '请输入用户姓名', trigger: 'blur' }]);
        this.$set(this.rules, 'yonghudianhua', [{ required: false, validator: this.$validate.isMobile, trigger: 'blur' }]);
        this.$set(this.rules, 'money', [{ required: false, validator: this.$validate.isNumber, trigger: 'blur' }]);
      }

      this.init();
      this.sessionForm = JSON.parse(localStorage.getItem('sessionForm'))
    },
    methods: {
      init() {
        if ('nonghu' == this.userTableName) {
          this.dynamicProp.xingbie = '男,女'.split(',');
        }
        if ('yonghu' == this.userTableName) {
          this.dynamicProp.xingbie = '男,女'.split(',');
          this.dynamicProp.vip = '是,否'.split(',');
        }
      },
      setSession(){
        localStorage.setItem('sessionForm',JSON.stringify(this.sessionForm))
      },
      onSubmit(formName) {
        if(`nonghu` == this.userTableName && this.sessionForm.touxiang!=null){
          this.sessionForm.touxiang = this.sessionForm.touxiang.replace(new RegExp(this.$config.baseUrl,"g"),"");
        }
        if(`yonghu` == this.userTableName && this.sessionForm.touxiang!=null){
          this.sessionForm.touxiang = this.sessionForm.touxiang.replace(new RegExp(this.$config.baseUrl,"g"),"");
        }
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$http.post(this.userTableName + '/update', this.sessionForm).then(res => {
              if (res.data.code == 0) {
                this.setSession()
                this.$message({ message: '更新成功', type: 'success', duration: 1500 });
              }
            });
          } else {
            return false;
          }
        });
      },
      nonghutouxiangHandleAvatarSuccess(fileUrls) {
        this.sessionForm.touxiang = fileUrls;
      },
      yonghutouxiangHandleAvatarSuccess(fileUrls) {
        this.sessionForm.touxiang = fileUrls;
      },
      chongzhi() {
        if (this.chongzhiForm.money == '') {
          this.$message({ message: '请输入充值金额', type: 'error', duration: 1500 });
          return;
        }
        if (this.chongzhiForm.money <= 0) {
          this.$message({ message: '请输入正确的充值金额', type: 'error', duration: 1500 });
          return;
        }
        if (this.chongzhiForm.radio == '') {
          this.$message({ message: '请选择充值方式', type: 'error', duration: 1500 });
          return;
        }
        if(!this.sessionForm.money) {
          this.sessionForm.money = parseFloat(this.chongzhiForm.money)
        }else{
          this.sessionForm.money = parseFloat(this.sessionForm.money) + parseFloat(this.chongzhiForm.money);
        }
        
        this.$http.post(this.userTableName + '/update', this.sessionForm).then(res => {
          if (res.data.code == 0) {
            this.setSession()
            this.$message({ message: '充值成功', type: 'success', duration: 1500, onClose: () => { this.dialogFormVisibleMoney = false; }});
          }
        });
      },
      chongzhivip() {
        if (this.chongzhiForm.radio == '') {
          this.$message({ message: '请选择支付方式', type: 'error', duration: 1500 });
          return;
        }
        if(this.sessionForm.vip == '是') {
          this.$message({ message: '您已是我们的尊贵会员。', type: 'success', duration: 1500 });
          return;
        }
        
        this.sessionForm.vip = "是"
        this.$http.post(this.userTableName + '/update', this.sessionForm).then(res => {
          if (res.data.code == 0) {
            this.setSession()
            this.$message({ message: '会员购买成功', type: 'success', duration: 1500, onClose: () => {
              localStorage.setItem('vip', this.sessionForm.vip);
              this.dialogFormVisibleVip = false;
            }});
          }
        });
      },
      handleClick(tab, event) {
        switch(event.target.outerText) {
          case '个人中心':
            tab.$router.push('/index/center');
            break;
          case '修改密码':
            this.passwordForm = { password: '', newpassword: '', repassword: '' }
            this.$forceUpdate()
            break;
          case '我的订单':
            tab.$router.push('/index/shop-order/order');
            break;
          case '我的地址':
            tab.$router.push('/index/shop-address/list');
            break;
          case '我的收藏':
            localStorage.setItem('storeupType', 1);
            tab.$router.push('/index/storeup');
            break;
          default:
            tab.$router.push(`/index/${tab.name}?centerType=1`);
        }
        this.title = event.target.outerText;
      },
      async updatePassword(){
        this.$refs["passwordForm"].validate(async valid => {
          if (valid) {
            var password = "";
            if (this.sessionForm.mima) {
              password = this.sessionForm.mima;
            } else if (this.sessionForm.password) {
              password = this.sessionForm.password;
            }
            if (this.passwordForm.password != password) {
              this.$message.error("原密码错误");
              return;
            }
            if (this.passwordForm.newpassword != this.passwordForm.repassword) {
              this.$message.error("两次密码输入不一致");
              return;
            }
            this.sessionForm.password = this.passwordForm.newpassword;
            this.sessionForm.mima = this.passwordForm.newpassword;
            this.$http.post(`${this.userTableName}/update`,this.sessionForm).then(({data})=>{
              if (data && data.code === 0) {
                this.$message({ message: "修改密码成功,下次登录系统生效", type: "success", duration: 1500 });
                this.setSession()
              } else {
                this.$message.error(data.msg);
              }
            });
          }
        })
      },
      logout() {
        localStorage.clear();
        Vue.http.headers.common['Token'] = "";
        this.$router.push('/index/home');
        this.activeIndex = '0'
        localStorage.setItem('keyPath', this.activeIndex)
        this.$forceUpdate()
        this.$message({ message: '登出成功', type: 'success', duration: 1500 });
      },
      hasBack(name){
        switch(name){
          case '订单管理': return false;
          case '我的收藏管理': return false;
          default: return true;
        }
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.center-preview {
  .el-tabs {
    ::v-deep .el-tabs__header {
      margin-right: 30px;
      
      .el-tabs__nav {
        border-radius: 12px;
        overflow: hidden;
      }
      
      .el-tabs__item {
        padding: 0 25px;
        height: 50px;
        line-height: 50px;
        font-size: 15px;
        color: #666;
        transition: all 0.3s ease;
        border-left: 3px solid transparent;
        
        &:hover {
          color: #2E7D32;
          background: rgba(46, 125, 50, 0.05);
        }
        
        &.is-active {
          color: #2E7D32;
          background: rgba(46, 125, 50, 0.1);
          border-left-color: #2E7D32;
          font-weight: 600;
        }
      }
    }
    
    ::v-deep .el-tabs__content {
      padding: 0;
    }
  }
  
  .center-preview-pv {
    max-width: 500px;
    
    .el-form-item {
      ::v-deep .el-form-item__label {
        color: #555;
        font-weight: 500;
        font-size: 14px;
      }
      
      .el-input {
        ::v-deep .el-input__inner {
          border: 1px solid #e0e0e0;
          border-radius: 8px;
          padding: 0 15px;
          height: 44px;
          font-size: 14px;
          transition: all 0.3s ease;
          
          &:focus {
            border-color: #2E7D32;
            box-shadow: 0 0 0 2px rgba(46, 125, 50, 0.1);
          }
          
          &[readonly] {
            background: #f5f5f5;
            color: #999;
          }
        }
      }
      
      .el-select {
        width: 100%;
        
        ::v-deep .el-input__inner {
          border: 1px solid #e0e0e0;
          border-radius: 8px;
          height: 44px;
        }
      }
    }
  }
}

// 按钮悬停效果
.el-button {
  transition: all 0.3s ease;
  
  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(46, 125, 50, 0.4) !important;
  }
  
  &:active {
    transform: translateY(0);
  }
}

// 弹窗样式优化
::v-deep .el-dialog {
  border-radius: 16px;
  overflow: hidden;
  
  .el-dialog__header {
    background: linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%);
    padding: 20px;
    
    .el-dialog__title {
      color: #fff;
      font-weight: 600;
    }
    
    .el-dialog__headerbtn .el-dialog__close {
      color: #fff;
    }
  }
  
  .el-dialog__body {
    padding: 30px 20px;
  }
}

// 单选框样式
::v-deep .el-radio {
  &.is-checked {
    .el-radio__label {
      color: #2E7D32;
    }
    .el-radio__inner {
      border-color: #2E7D32;
      background: #2E7D32;
    }
  }
}

// 文件上传组件样式优化
::v-deep .upload-container {
  .el-upload--picture-card {
    border: 2px dashed #d0d0d0;
    border-radius: 12px;
    width: 120px;
    height: 120px;
    line-height: 120px;
    transition: all 0.3s ease;
    
    &:hover {
      border-color: #2E7D32;
      color: #2E7D32;
    }
  }
}
</style>
