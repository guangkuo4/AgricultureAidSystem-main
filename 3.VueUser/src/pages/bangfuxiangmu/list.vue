<template>
  <div class="page">
    <!-- 面包屑导航 -->
    <div :style='{"width":"100%","padding":"30px 10%","margin":"0 auto","borderRadius":"8px","background":"linear-gradient(180deg, rgba(80, 160, 80, 0.9), rgba(60, 140, 60, 1))","boxShadow":"0 4px 12px rgba(0, 0, 0, 0.15)","border":"1px solid rgba(255, 255, 255, 0.2)"}' class="breadcrumb-preview">
      <el-breadcrumb :separator="'>'" :style='{"fontSize":"16px","lineHeight":"1","color":"#fff"}'>
        <el-breadcrumb-item class="item1" to="/"><a style="color: #fff;">首页</a></el-breadcrumb-item>
        <el-breadcrumb-item class="item2"><span style="color: #fff;">帮扶对接</span></el-breadcrumb-item>
      </el-breadcrumb>
    </div>

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

    <!-- 标签页内容 -->
    <div class="tab-content">
      <!-- 帮扶项目 -->
      <div v-if="activeTab === 'projects'" class="tab-panel">
        <div class="filter-bar">
          <div class="search-bar">
            <input type="text" v-model="searchValue" placeholder="搜索项目名称" @input="searchProjects" />
            <button class="search-btn" @click="searchProjects">搜索</button>
          </div>
          <div class="filter-options">
            <select v-model="selectedType" @change="searchProjects">
              <option value="">全部类型</option>
              <option value="技术">技术</option>
              <option value="采购">采购</option>
              <option value="资金">资金</option>
              <option value="其他">其他</option>
            </select>
            <select v-model="sortBy" @change="searchProjects">
              <option value="newest">最新发布</option>
              <option value="oldest">最早发布</option>
            </select>
          </div>
        </div>
        
        <div class="project-list">
          <div 
            v-for="project in projects" 
            :key="project.id"
            class="project-item"
            @click="goToDetail(project.id)"
          >
            <div class="project-image">
              <div v-if="project.tupian" class="image-wrapper">
                <img :src="imgUrl(project.tupian)" alt="项目图片" @error="handleImageError($event, project)" />
              </div>
              <div v-else class="image-placeholder" :class="getPlaceholderClass(project.xiangmuleixing)">
                <span>{{ getPlaceholderText(project.xiangmuleixing) }}</span>
              </div>
            </div>
            <div class="project-info">
              <h3 class="project-title">{{ project.xiangmumingcheng }}</h3>
              <div class="project-meta">
                <span class="project-type">{{ project.xiangmuleixing }}</span>
                <span class="project-date">{{ formatDate(project.faburiqi) }}</span>
              </div>
              <p class="project-desc">{{ truncate(project.xiangmuxiangqing, 50) }}</p>
              <div class="project-contact">
                <span class="contact-person"><i class="el-icon-user"></i> {{ project.lianxiren }}</span>
                <span class="contact-location"><i class="el-icon-location"></i> {{ project.suozaidiqu || '未填写' }}</span>
              </div>
              <div class="project-footer">
                <span class="view-count"><i class="el-icon-view"></i> {{ project.clicknum || 0 }} 次浏览</span>
                <button class="apply-btn" @click.stop="applyProject(project)">申请对接</button>
              </div>
            </div>
          </div>
        </div>
        
        <div v-if="projects.length === 0" class="empty">
          <i class="el-icon-folder-opened" style="font-size: 48px; color: #ccc;"></i>
          <p>暂无项目</p>
          <button class="refresh-btn" @click="loadProjects">刷新</button>
        </div>
        
        <div class="loading" v-if="loading">
          <i class="el-icon-loading" style="font-size: 24px;"></i>
          <p>加载中...</p>
        </div>
        
        <!-- 分页 -->
        <div class="pagination" v-if="total > 0">
          <button class="page-btn" @click="prevPage" :disabled="page === 1">上一页</button>
          <span class="page-info">第 {{ page }} / {{ totalPages }} 页</span>
          <button class="page-btn" @click="nextPage" :disabled="page === totalPages">下一页</button>
        </div>
      </div>

      <!-- 帮扶资源池 -->
      <div v-if="activeTab === 'resources'" class="tab-panel">
        <div class="filter-bar">
          <div class="search-bar">
            <input type="text" v-model="resourceSearch" placeholder="搜索资源名称" @input="searchResources" />
            <button class="search-btn" @click="searchResources">搜索</button>
          </div>
          <div class="filter-options">
            <select v-model="resourceType" @change="searchResources">
              <option value="">全部类型</option>
              <option value="技术帮扶">技术帮扶</option>
              <option value="资金帮扶">资金帮扶</option>
              <option value="产品采购">产品采购</option>
              <option value="农机帮扶">农机帮扶</option>
            </select>
            <select v-model="resourceArea" @change="searchResources">
              <option value="">全部地区</option>
              <option value="河南">河南</option>
              <option value="山东">山东</option>
              <option value="河北">河北</option>
            </select>
          </div>
        </div>

        <div class="resource-list">
          <div v-for="resource in resources" :key="resource.id" class="resource-card">
            <div class="resource-header">
              <h3>{{ resource.bangfufangming }}</h3>
              <span class="resource-type">{{ resource.bangfuleixing }}</span>
            </div>
            <div class="resource-body">
              <p><strong>擅长领域：</strong>{{ resource.shanchanglingyu }}</p>
              <p><strong>所在地区：</strong>{{ resource.suozaidiqu }}</p>
              <p><strong>响应时效：</strong>{{ resource.xiangyingshixiao }}</p>
              <p class="resource-content">{{ truncate(resource.bangfuneirong, 80) }}</p>
            </div>
            <div class="resource-footer">
              <span><i class="el-icon-user"></i> {{ resource.lianxiren }}</span>
              <span><i class="el-icon-phone"></i> {{ resource.lianxidianhua }}</span>
              <button class="contact-btn" @click="contactResource(resource)">联系帮扶方</button>
            </div>
          </div>
        </div>

        <div v-if="resources.length === 0" class="empty">
          <i class="el-icon-folder-opened" style="font-size: 48px; color: #ccc;"></i>
          <p>暂无资源</p>
        </div>
      </div>

      <!-- 发布资源 -->
      <div v-if="activeTab === 'publishResource'" class="tab-panel">
        <div class="form-container">
          <h2 class="form-title">发布帮扶资源</h2>
          <el-form :model="resourceForm" :rules="resourceRules" ref="resourceForm" label-width="120px">
            <el-form-item label="帮扶类型" prop="bangfuleixing">
              <el-select v-model="resourceForm.bangfuleixing" placeholder="请选择帮扶类型">
                <el-option label="技术帮扶" value="技术帮扶"></el-option>
                <el-option label="资金帮扶" value="资金帮扶"></el-option>
                <el-option label="产品采购" value="产品采购"></el-option>
                <el-option label="农机帮扶" value="农机帮扶"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="帮扶方名称" prop="bangfufangming">
              <el-input v-model="resourceForm.bangfufangming" placeholder="请输入帮扶方名称"></el-input>
            </el-form-item>
            <el-form-item label="帮扶方类型" prop="bangfufangleixing">
              <el-select v-model="resourceForm.bangfufangleixing" placeholder="请选择帮扶方类型">
                <el-option label="帮扶单位" value="帮扶单位"></el-option>
                <el-option label="农技专家" value="农技专家"></el-option>
                <el-option label="农业企业" value="农业企业"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="擅长领域" prop="shanchanglingyu">
              <el-input v-model="resourceForm.shanchanglingyu" placeholder="请输入擅长领域"></el-input>
            </el-form-item>
            <el-form-item label="所在地区" prop="suozaidiqu">
              <el-input v-model="resourceForm.suozaidiqu" placeholder="请输入所在地区"></el-input>
            </el-form-item>
            <el-form-item label="响应时效" prop="xiangyingshixiao">
              <el-select v-model="resourceForm.xiangyingshixiao" placeholder="请选择响应时效">
                <el-option label="24小时内" value="24小时内"></el-option>
                <el-option label="48小时内" value="48小时内"></el-option>
                <el-option label="72小时内" value="72小时内"></el-option>
                <el-option label="一周内" value="一周内"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="帮扶内容" prop="bangfuneirong">
              <el-input type="textarea" v-model="resourceForm.bangfuneirong" :rows="4" placeholder="请详细描述可提供的帮扶内容"></el-input>
            </el-form-item>
            <el-form-item label="资质照片">
              <el-upload
                :action="uploadUrl"
                :headers="uploadHeaders"
                name="file"
                list-type="picture-card"
                :on-success="handleResourceImageSuccess"
                :file-list="resourceImageList"
              >
                <i class="el-icon-plus"></i>
              </el-upload>
            </el-form-item>
            <el-form-item label="联系人" prop="lianxiren">
              <el-input v-model="resourceForm.lianxiren" placeholder="请输入联系人姓名"></el-input>
            </el-form-item>
            <el-form-item label="联系电话" prop="lianxidianhua">
              <el-input v-model="resourceForm.lianxidianhua" placeholder="请输入联系电话"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitResource">提交审核</el-button>
              <el-button @click="resetResourceForm">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>

      <!-- 我的需求 -->
      <div v-if="activeTab === 'myNeeds'" class="tab-panel">
        <!-- 批量操作栏 -->
        <div class="batch-actions" v-if="myNeeds.length > 0">
          <el-checkbox v-model="selectAllNeeds" @change="handleSelectAll">全选</el-checkbox>
          <el-button v-if="selectedNeedIds.length > 0" type="danger" icon="el-icon-delete" @click="batchDeleteNeed">
            批量删除 ({{ selectedNeedIds.length }})
          </el-button>
        </div>
        
        <div class="needs-list">
          <div v-for="need in myNeeds" :key="need.id" class="need-card">
            <div style="display: flex; align-items: center; gap: 8px; margin-bottom: 10px;">
              <el-checkbox v-model="selectedNeedIds" :label="need.id"></el-checkbox>
              <h3 style="margin: 0; flex: 1;">{{ need.xuqiubianhao }}</h3>
              <span :class="['need-status', getStatusClass(need.sfsh)]">{{ need.sfsh }}</span>
            </div>
            <div class="need-body" style="margin-bottom: 15px;">
              <p><strong>需求名称：</strong>{{ need.xuqiumingcheng || '-' }}</p>
              <p><strong>需求类型：</strong>{{ need.xuqiuleixing }}</p>
              <p><strong>需求描述：</strong>{{ need.xuqiumiaoshu }}</p>
              <p><strong>紧急程度：</strong>{{ need.xuqiujinji }}</p>
              <p><strong>期望时间：</strong>{{ need.qiwangshijian || '-' }}</p>
              <p><strong>申请日期：</strong>{{ need.shenqingridi }}</p>
            </div>
            <div v-if="need.shhf" style="margin-bottom: 15px; padding: 10px; background: #f5f5f5; border-radius: 4px;">
              <p><strong>审核回复：</strong>{{ need.shhf }}</p>
            </div>
            <div style="text-align: right;">
              <el-button type="danger" size="mini" icon="el-icon-delete" @click="deleteNeed(need.id)">删除</el-button>
            </div>
          </div>
        </div>

        <div v-if="myNeeds.length === 0" class="empty">
          <i class="el-icon-document" style="font-size: 48px; color: #ccc;"></i>
          <p>暂无需求记录</p>
          <button class="publish-btn" @click="activeTab = 'publishNeed'">发布需求</button>
        </div>
      </div>

      <!-- 发布需求 -->
      <div v-if="activeTab === 'publishNeed'" class="tab-panel">
        <div class="form-container">
          <h2 class="form-title">发布帮扶需求</h2>
          <el-form :model="needForm" :rules="needRules" ref="needForm" label-width="120px">
            <el-form-item label="需求名称" prop="xuqiumingcheng" required>
              <el-input v-model="needForm.xuqiumingcheng" placeholder="请输入需求名称" style="width: 400px;"></el-input>
            </el-form-item>
            <el-form-item label="联系方式" prop="lianxidianhua" required>
              <el-input v-model="needForm.lianxidianhua" placeholder="请输入联系电话" style="width: 400px;"></el-input>
            </el-form-item>
            <el-form-item label="需求类型" prop="xuqiuleixing">
              <el-select v-model="needForm.xuqiuleixing" placeholder="请选择需求类型">
                <el-option label="技术帮扶" value="技术帮扶"></el-option>
                <el-option label="资金帮扶" value="资金帮扶"></el-option>
                <el-option label="产品采购帮扶" value="产品采购帮扶"></el-option>
                <el-option label="农机帮扶" value="农机帮扶"></el-option>
                <el-option label="其他帮扶" value="其他帮扶"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="需求描述" prop="xuqiumiaoshu">
              <el-input type="textarea" v-model="needForm.xuqiumiaoshu" :rows="4" placeholder="请详细描述您的需求"></el-input>
            </el-form-item>
            <el-form-item label="需求图片">
              <el-upload
                :action="uploadUrl"
                :headers="uploadHeaders"
                name="file"
                list-type="picture-card"
                :on-success="handleNeedImageSuccess"
                :file-list="needImageList"
              >
                <i class="el-icon-plus"></i>
              </el-upload>
            </el-form-item>
            <el-form-item label="紧急程度">
              <el-radio-group v-model="needForm.xuqiujinji">
                <el-radio label="普通">普通</el-radio>
                <el-radio label="紧急">紧急</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="期望帮扶时间">
              <el-date-picker v-model="needForm.qiwangshijian" type="date" placeholder="选择日期"></el-date-picker>
            </el-form-item>
            <el-form-item label="所在省份" prop="suozaidiqu">
              <el-select v-model="needForm.suozaidiqu" placeholder="请选择所在省份">
                <el-option label="北京市" value="北京市"></el-option>
                <el-option label="天津市" value="天津市"></el-option>
                <el-option label="河北省" value="河北省"></el-option>
                <el-option label="山西省" value="山西省"></el-option>
                <el-option label="内蒙古自治区" value="内蒙古自治区"></el-option>
                <el-option label="辽宁省" value="辽宁省"></el-option>
                <el-option label="吉林省" value="吉林省"></el-option>
                <el-option label="黑龙江省" value="黑龙江省"></el-option>
                <el-option label="上海市" value="上海市"></el-option>
                <el-option label="江苏省" value="江苏省"></el-option>
                <el-option label="浙江省" value="浙江省"></el-option>
                <el-option label="安徽省" value="安徽省"></el-option>
                <el-option label="福建省" value="福建省"></el-option>
                <el-option label="江西省" value="江西省"></el-option>
                <el-option label="山东省" value="山东省"></el-option>
                <el-option label="河南省" value="河南省"></el-option>
                <el-option label="湖北省" value="湖北省"></el-option>
                <el-option label="湖南省" value="湖南省"></el-option>
                <el-option label="广东省" value="广东省"></el-option>
                <el-option label="广西壮族自治区" value="广西壮族自治区"></el-option>
                <el-option label="海南省" value="海南省"></el-option>
                <el-option label="重庆市" value="重庆市"></el-option>
                <el-option label="四川省" value="四川省"></el-option>
                <el-option label="贵州省" value="贵州省"></el-option>
                <el-option label="云南省" value="云南省"></el-option>
                <el-option label="西藏自治区" value="西藏自治区"></el-option>
                <el-option label="陕西省" value="陕西省"></el-option>
                <el-option label="甘肃省" value="甘肃省"></el-option>
                <el-option label="青海省" value="青海省"></el-option>
                <el-option label="宁夏回族自治区" value="宁夏回族自治区"></el-option>
                <el-option label="新疆维吾尔自治区" value="新疆维吾尔自治区"></el-option>
                <el-option label="香港特别行政区" value="香港特别行政区"></el-option>
                <el-option label="澳门特别行政区" value="澳门特别行政区"></el-option>
                <el-option label="台湾省" value="台湾省"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="具体地址">
              <el-input v-model="needForm.jutidizhi" placeholder="请输入具体地址（精确到村/组，选填）"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitNeed">提交需求</el-button>
              <el-button @click="resetNeedForm">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>

      <!-- 我的对接 -->
      <div v-if="activeTab === 'myDocking'" class="tab-panel">
        <div class="form-container">
          <h2 class="form-title">我的对接</h2>
          
          <div v-if="dockingLoading" class="loading">
            <i class="el-icon-loading" style="font-size: 24px;"></i>
            <p>加载中...</p>
          </div>

          <div v-else-if="myDockings.length === 0" class="empty">
            <i class="el-icon-link" style="font-size: 48px; color: #ccc;"></i>
            <p>暂无对接项目</p>
            <button class="publish-btn" @click="activeTab = 'projects'">去申请对接</button>
          </div>

          <div v-else class="docking-list">
            <div v-for="docking in myDockings" :key="docking.id" class="docking-card">
              <div class="docking-header">
                <h3>{{ docking.xiangmumingcheng }}</h3>
                <span :class="['docking-status', getDockingStatusClass(docking.sfsh)]">{{ docking.sfsh }}</span>
              </div>
              <div class="docking-body">
                <p><strong>项目类型：</strong>{{ docking.xiangmuleixing }}</p>
                <p><strong>项目编号：</strong>{{ docking.xiangmubianhao }}</p>
                <p><strong>申请日期：</strong>{{ docking.shenqingriqi }}</p>
                <p><strong>对接说明：</strong>{{ docking.shenqingshuoming }}</p>
                <div v-if="docking.sfsh === '已通过'" class="contact-info">
                  <h4>对接联系方式</h4>
                  <p><strong>需求方名称：</strong>{{ docking.xuqiumingcheng }}</p>
                  <p><strong>联系人：</strong>{{ docking.shenqingrenxingming }}</p>
                  <p><strong>联系电话：</strong><a :href="'tel:' + docking.lianxidianhua" class="phone-link">{{ docking.lianxidianhua }}</a></p>
                  <p><strong>所在省份：</strong>{{ docking.suozaidiqu }}</p>
                  <p><strong>具体地址：</strong>{{ docking.jutidizhi || '未填写' }}</p>
                </div>
                <div v-if="docking.bangfujindu" class="progress-info">
                  <p><strong>当前进度：</strong>{{ docking.bangfujindu }}</p>
                </div>
              </div>
              <div v-if="docking.shhf" class="docking-feedback">
                <p><strong>审核回复：</strong>{{ docking.shhf }}</p>
              </div>
              <div class="docking-actions">
                <el-button v-if="docking.sfsh === '待审核'" type="danger" size="mini" icon="el-icon-delete" @click="deleteDocking(docking.id)">删除申请</el-button>
                <el-button v-if="docking.sfsh === '已通过'" type="primary" size="mini" icon="el-icon-edit" @click="showUpdateProgress(docking)">更新进度</el-button>
                <el-button v-if="docking.sfsh === '已通过'" type="success" size="mini" icon="el-icon-check" @click="confirmComplete(docking)">确认完成</el-button>
              </div>
            </div>
          </div>
        </div>
      </div>

      </div>

      <!-- 删除确认弹窗 -->
      <el-dialog title="提示" :visible.sync="deleteConfirmVisible" width="400px" :close-on-click-modal="false">
        <div style="text-align: center; padding: 20px;">
          <i class="el-icon-warning" style="font-size: 48px; color: #E6A23C; margin-bottom: 15px;"></i>
          <p style="font-size: 14px; color: #666;">{{ deleteMessage }}</p>
        </div>
        <div slot="footer" style="text-align: center;">
          <el-button @click="deleteConfirmVisible = false">取消</el-button>
          <el-button type="danger" @click="confirmDelete">确定</el-button>
        </div>
      </el-dialog>

      <!-- 申请对接弹窗 -->
      <el-dialog title="申请对接" :visible.sync="applyDialogVisible" width="600px" :close-on-click-modal="false">
        <el-form :model="applyForm" label-width="130px">
          <el-form-item label="机构 / 个人名称" required>
            <el-input v-model="applyForm.jigoumingcheng" placeholder="企业填公司全称、个人/专家填姓名"></el-input>
          </el-form-item>
          <el-form-item label="联系电话" required>
            <el-input v-model="applyForm.lianxidianhua" placeholder="请输入需求方可直接联系的手机号"></el-input>
          </el-form-item>
          <el-form-item label="对接说明" required>
            <el-input type="textarea" v-model="applyForm.duijieshuoming" :rows="5" placeholder="至少10字，说明自身优势、为什么能做好这个帮扶"></el-input>
            <span class="word-count">已输入 {{ applyForm.duijieshuoming ? applyForm.duijieshuoming.length : 0 }} 字</span>
          </el-form-item>
          <el-form-item label="附件上传">
            <el-upload
              :action="uploadUrl"
              :headers="uploadHeaders"
              name="file"
              list-type="picture-card"
              :on-success="handleApplyImageSuccess"
              :file-list="applyImageList"
            >
              <i class="el-icon-plus"></i>
            </el-upload>
            <div class="upload-tip">可选上传：资质证书、过往案例证明、营业执照等，增强可信度</div>
          </el-form-item>
          <el-form-item label="服务承诺" required>
            <el-checkbox v-model="applyForm.fuwuchengnuo">
              承诺提供信息真实有效，遵守平台帮扶规则
            </el-checkbox>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitApply" :disabled="!applyForm.fuwuchengnuo">提交申请</el-button>
            <el-button @click="applyDialogVisible = false">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>

      <!-- 更新进度弹窗 -->
      <el-dialog title="更新对接进度" :visible.sync="updateProgressVisible" width="500px" :close-on-click-modal="false">
        <el-form :model="progressForm" label-width="100px">
          <el-form-item label="帮扶状态">
            <el-select v-model="progressForm.bangfuzhuangtai" placeholder="请选择状态">
              <el-option label="待分配" value="待分配"></el-option>
              <el-option label="进行中" value="进行中"></el-option>
              <el-option label="已完成" value="已完成"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="进度说明">
            <el-input type="textarea" :rows="4" v-model="progressForm.bangfujindu" placeholder="请输入最新进度说明"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer">
          <el-button @click="updateProgressVisible = false">取消</el-button>
          <el-button type="primary" @click="submitProgress">保存</el-button>
        </span>
      </el-dialog>

  </div>
</template>

<script>
export default {
  data() {
    return {
      // 标签页
      activeTab: 'projects',
      tabs: [
        { key: 'projects', label: '帮扶项目', icon: 'el-icon-suitcase' },
        { key: 'resources', label: '帮扶资源池', icon: 'el-icon-collection' },
        { key: 'publishNeed', label: '发布需求', icon: 'el-icon-edit' },
        { key: 'publishResource', label: '发布资源', icon: 'el-icon-plus' },
        { key: 'myNeeds', label: '我的需求', icon: 'el-icon-document' },
        { key: 'myDocking', label: '我的对接', icon: 'el-icon-link' }
      ],

      // 项目列表数据
      searchValue: '',
      selectedType: '',
      sortBy: 'newest',
      projects: [],
      loading: false,
      page: 1,
      limit: 10,
      total: 0,
      defaultImage: 'https://via.placeholder.com/300x200?text=项目图片',

      // 资源池数据
      resourceSearch: '',
      resourceType: '',
      resourceArea: '',
      resources: [],

      // 发布资源表单
      resourceForm: {
        bangfuleixing: '',
        bangfufangming: '',
        bangfufangleixing: '',
        shanchanglingyu: '',
        suozaidiqu: '',
        xiangyingshixiao: '',
        bangfuneirong: '',
        zizhengzhaopian: '',
        lianxiren: '',
        lianxidianhua: ''
      },
      resourceRules: {
        bangfuleixing: [{ required: true, message: '请选择帮扶类型', trigger: 'change' }],
        bangfufangming: [{ required: true, message: '请输入帮扶方名称', trigger: 'blur' }],
        bangfufangleixing: [{ required: true, message: '请选择帮扶方类型', trigger: 'change' }],
        shanchanglingyu: [{ required: true, message: '请输入擅长领域', trigger: 'blur' }],
        suozaidiqu: [{ required: true, message: '请输入所在地区', trigger: 'blur' }],
        xiangyingshixiao: [{ required: true, message: '请选择响应时效', trigger: 'change' }],
        bangfuneirong: [{ required: true, message: '请输入帮扶内容', trigger: 'blur' }],
        lianxiren: [{ required: true, message: '请输入联系人', trigger: 'blur' }],
        lianxidianhua: [{ required: true, message: '请输入联系电话', trigger: 'blur' }]
      },
      resourceImageList: [],

      // 我的需求
      myNeeds: [],
      selectedNeedIds: [],
      selectAllNeeds: false,
      deleteConfirmVisible: false,
      deleteId: null,
      deleteType: 'single',
      deleteMessage: '',

      // 发布需求表单
      needForm: {
        xuqiumingcheng: '',
        xuqiuleixing: '',
        xuqiumiaoshu: '',
        xuqiutupian: '',
        xuqiujinji: '普通',
        qiwangshijian: '',
        suozaidiqu: '',
        jutidizhi: '',
        lianxidianhua: ''
      },
      needRules: {
        xuqiumingcheng: [{ required: true, message: '请输入需求名称', trigger: 'blur' }],
        xuqiuleixing: [{ required: true, message: '请选择需求类型', trigger: 'change' }],
        xuqiumiaoshu: [{ required: true, message: '请输入需求描述', trigger: 'blur' }],
        suozaidiqu: [{ required: true, message: '请选择所在省份', trigger: 'change' }],
        lianxidianhua: [{ required: true, message: '请输入联系方式', trigger: 'blur' }]
      },
      needImageList: [],

      // 我的对接
      myDockings: [],
      dockingLoading: false,

      // 申请对接弹窗
      applyDialogVisible: false,
      applyForm: {
        xiangmuid: '',
        xiangmumingcheng: '',
        lianxidianhua: '',
        jigoumingcheng: '',
        duijieshuoming: '',
        fujian: '',
        fuwuchengnuo: false
      },
      applyImageList: [],

      // 更新进度弹窗
      updateProgressVisible: false,
      progressForm: { id: null, bangfuzhuangtai: '', bangfujindu: '' }
    }
  },
  computed: {
    totalPages() {
      return Math.ceil(this.total / this.limit)
    },
    /** 与 vue.config 代理一致：开发环境为 /api/file/upload */
    uploadUrl() {
      const b = (this.$config && this.$config.baseUrl) || '/api/'
      return String(b).replace(/\/?$/, '/') + 'file/upload'
    },
    uploadHeaders() {
      const t = typeof localStorage !== 'undefined' ? localStorage.getItem('frontToken') : ''
      return t ? { Token: t } : {}
    }
  },
  created() {
    this.loadProjects()
    this.loadResources()
    this.loadMyNeeds()
    this.loadMyDockings()
  },
  watch: {
    activeTab(val) {
      if (val === 'projects') this.loadProjects()
      if (val === 'resources') this.loadResources()
      if (val === 'myNeeds') this.loadMyNeeds()
      if (val === 'myDocking') this.loadMyDockings()
    }
  },
  methods: {
    // 项目列表方法
    loadProjects() {
      console.log('正在加载帮扶项目...')
      this.loading = true
      
      // 同时加载帮扶项目和审核通过的需求
      Promise.all([
        this.$http.get('/bangfuxiangmu/list', {
          params: {
            page: this.page,
            limit: this.limit,
            xiangmumingcheng: this.searchValue,
            xiangmuleixing: this.selectedType,
            sort: this.sortBy === 'newest' ? 'faburiqi' : 'faburiqi',
            order: this.sortBy === 'newest' ? 'desc' : 'asc'
          }
        }),
        this.$http.get('/bangfuxuqiu/list', {
          params: {
            sfsh: '已通过',
            page: 1,
            limit: 100
          }
        })
      ]).then(([projectRes, needRes]) => {
        console.log('项目列表响应:', projectRes)
        console.log('需求列表响应:', needRes)
        
        let projects = []
        let total = 0
        
        // 处理帮扶项目数据
        if (projectRes.data && projectRes.data.code === 0) {
          projects = projectRes.data.data.list || []
          total = projectRes.data.data.total || 0
        }
        
        // 处理审核通过的需求数据，转换为项目格式
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
            xiangmuxiangqing: need.xuqiumiaoshu,
            tupian: need.xuqiutupian,
            clicknum: 0,
            isNeed: true // 标记为需求转换的项目
          }))
          projects = [...projects, ...convertedNeeds]
          total += needs.length
        }
        
        // 按发布日期排序
        projects.sort((a, b) => {
          const dateA = new Date(a.faburiqi || a.shenqingridi || '1970-01-01')
          const dateB = new Date(b.faburiqi || b.shenqingridi || '1970-01-01')
          return this.sortBy === 'newest' ? dateB - dateA : dateA - dateB
        })
        
        this.projects = projects
        this.total = total
        
        console.log('合并后的项目列表:', this.projects)
        if (this.projects.length === 0) {
          console.log('项目列表为空，请检查数据库是否有数据')
        }
        
        this.loading = false
      }).catch(err => {
        console.error('加载项目失败:', err)
        this.loading = false
      })
    },
    searchProjects() {
      this.page = 1
      this.loadProjects()
    },
    prevPage() {
      if (this.page > 1) {
        this.page--
        this.loadProjects()
      }
    },
    nextPage() {
      if (this.page < this.totalPages) {
        this.page++
        this.loadProjects()
      }
    },
    goToDetail(id) {
      this.$router.push(`/index/bangfuxiangmuDetail/${id}`)
    },
    applyProject(project) {
      const uid = this.sessionUserId()
      if (!uid) {
        this.$message.warning('请先登录')
        return
      }
      this.applyForm = {
        xiangmuid: project.id,
        xiangmumingcheng: project.xiangmumingcheng,
        lianxiren: '',
        lianxidianhua: '',
        jigoumingcheng: '',
        duijieshuoming: '',
        fujian: '',
        fuwuchengnuo: false
      }
      this.applyImageList = []
      this.applyDialogVisible = true
    },
    handleApplyImageSuccess(res) {
      if (res.code === 0) {
        this.applyForm.fujian = res.data ? res.data.url : ''
        this.$message.success('上传成功')
      } else {
        this.$message.error('上传失败')
      }
    },
    submitApply() {
      if (!this.applyForm.jigoumingcheng) {
        this.$message.warning('请输入机构/个人名称')
        return
      }
      if (!this.applyForm.lianxidianhua) {
        this.$message.warning('请输入联系电话')
        return
      }
      if (!this.applyForm.duijieshuoming) {
        this.$message.warning('请输入对接说明')
        return
      }
      if (this.applyForm.duijieshuoming.length < 10) {
        this.$message.warning('对接说明需要至少10字')
        return
      }
      if (!this.applyForm.fuwuchengnuo) {
        this.$message.warning('请勾选服务承诺')
        return
      }
      
      const userInfo = JSON.parse(localStorage.getItem('sessionForm') || '{}')
      const submitData = {
        shenqingbianhao: 'DJ' + Date.now(),
        xiangmuid: this.applyForm.xiangmuid,
        xiangmumingcheng: this.applyForm.xiangmumingcheng,
        shenqingrenzhanghao: userInfo.yonghuzhanghao || userInfo.nonghuzhanghao || localStorage.getItem('username') || '游客',
        shenqingrenxingming: userInfo.yonghuxingming || userInfo.nonghuxingming || '游客',
        shenqingriqi: new Date().toISOString().split('T')[0],
        shenqingshuoming: this.applyForm.duijieshuoming,
        jigoumingcheng: this.applyForm.jigoumingcheng,
        lianxidianhua: this.applyForm.lianxidianhua,
        fujian: this.applyForm.fujian,
        fuwuchengnuo: this.applyForm.fuwuchengnuo,
        sfsh: '待审核',
        userid: userInfo.id,
        duijieshuoming: this.applyForm.duijieshuoming
      }
      console.log('提交的数据:', submitData)
      this.$http.post('/duijieshenqing/add', submitData).then(res => {
        if (res.data.code === 0) {
          this.$message.success('申请提交成功，等待审核')
          this.applyDialogVisible = false
        } else {
          this.$message.error(res.data.msg || '申请失败')
        }
      }).catch(err => {
        console.error('申请失败:', err)
        this.$message.error('申请失败')
      })
    },

    // 资源池方法
    loadResources() {
      console.log('正在加载资源...')
      const params = {
        page: 1,
        limit: 100,
        sfsh: '已通过'
      }
      if (this.resourceType) params.bangfuleixing = this.resourceType
      if (this.resourceArea) params.suozaidiqu = this.resourceArea
      if (this.resourceSearch) params.bangfufangming = this.resourceSearch

      console.log('请求参数:', params)

      this.$http.get('/bangfuziyuan/list', {
        params: params
      }).then(res => {
        console.log('资源加载响应:', res)
        if (res.data.code === 0) {
          this.resources = res.data.data.list || []
          console.log('资源列表:', this.resources)
          if (this.resources.length === 0) {
            console.log('资源列表为空，请先添加资源数据')
          }
        } else {
          console.warn('API 返回错误:', res.data.msg)
          this.$message.warning(res.data.msg || '获取资源失败')
        }
      }).catch(err => {
        console.error('加载资源失败:', err)
        // 不弹 Message：避免与「提交成功」同时出现误导性网络报错；切换 Tab 时会再拉取
      })
    },
    searchResources() {
      this.loadResources()
    },
    contactResource(resource) {
      const phone = (resource.lianxidianhua || '').replace(/\s/g, '')
      if (phone) {
        window.location.href = 'tel:' + phone
      }
      this.$message.success(`请联系：${resource.lianxiren || ''} ${phone || '（未留电话）'}`)
    },

    // 发布资源方法
    parseUploadPayload(res) {
      if (!res) return null
      const body = typeof res === 'string' ? (function() { try { return JSON.parse(res) } catch (e) { return {} } })() : res
      return (body && body.file) ? body.file : null
    },
    handleResourceImageSuccess(response) {
      const name = this.parseUploadPayload(response)
      if (name) this.resourceForm.zizhengzhaopian = name
    },
    submitResource() {
      this.$refs.resourceForm.validate(valid => {
        if (valid) {
          const submitData = { ...this.resourceForm }
          submitData.ziyuanbianhao = 'ZY' + Date.now()
          submitData.sfsh = '待审核'
          
          this.$http.post('/bangfuziyuan/add', submitData).then(res => {
            if (res.data.code === 0) {
              this.$message.success('资源提交成功，等待审核')
              this.resetResourceForm()
              this.activeTab = 'resources'
              // 由 watch(activeTab) 触发 loadResources，避免与手动调用重复请求、重复报错
            } else {
              this.$message.error(res.data.msg || '提交失败')
            }
          }).catch(err => {
            console.error('提交资源失败:', err)
            const msg = (err.body && err.body.msg) || err.message || '网络异常'
            this.$message.error('提交失败：' + msg)
          })
        }
      })
    },
    resetResourceForm() {
      this.$refs.resourceForm.resetFields()
      this.resourceImageList = []
    },

    // 我的需求方法
    sessionAccount() {
      try {
        const userInfo = JSON.parse(localStorage.getItem('sessionForm') || '{}')
        return userInfo.yonghuzhanghao || userInfo.nonghuzhanghao || localStorage.getItem('username') || ''
      } catch (e) {
        return localStorage.getItem('username') || ''
      }
    },
    sessionUserId() {
      try {
        const userInfo = JSON.parse(localStorage.getItem('sessionForm') || '{}')
        return userInfo.id
      } catch (e) {
        return null
      }
    },
    loadMyNeeds() {
      const account = this.sessionAccount()
      if (!account) {
        console.log('用户未登录，跳过加载需求')
        return
      }
      this.$http.get('/bangfuxuqiu/list', {
        params: {
          shenqingrenzhanghao: account
        }
      }).then(res => {
        if (res.data.code === 0) {
          this.myNeeds = (res.data.data && res.data.data.list) || []
          this.selectAllNeeds = false
          this.selectedNeedIds = []
        }
      }).catch(err => {
        console.error('加载需求失败:', err)
      })
    },
    getStatusClass(status) {
      const statusMap = {
        '待审核': 'status-pending',
        '已通过': 'status-approved',
        '已拒绝': 'status-rejected'
      }
      return statusMap[status] || 'status-pending'
    },
    // 全选需求
    handleSelectAll(val) {
      if (val) {
        this.selectedNeedIds = this.myNeeds.map(item => item.id)
      } else {
        this.selectedNeedIds = []
      }
    },
    // 删除单个需求
    deleteNeed(id) {
      this.deleteConfirmVisible = true
      this.deleteId = id
      this.deleteType = 'single'
      this.deleteMessage = '确定要删除这个需求吗？'
    },
    // 确认删除
    confirmDelete() {
      const ids = this.deleteType === 'single' ? [this.deleteId] : [...this.selectedNeedIds]
      this.$http.post('/bangfuxuqiu/delete', ids).then(res => {
        if (res.data.code === 0) {
          this.$message.success('删除成功')
          this.loadMyNeeds()
        } else {
          this.$message.error(res.data.msg || '删除失败')
        }
      }).catch(() => {
        this.$message.error('删除失败')
      })
      this.deleteConfirmVisible = false
    },
    // 批量删除需求
    batchDeleteNeed() {
      if (this.selectedNeedIds.length === 0) {
        this.$message.warning('请选择要删除的需求')
        return
      }
      this.deleteConfirmVisible = true
      this.deleteId = null
      this.deleteType = 'batch'
      this.deleteMessage = `确定要删除选中的 ${this.selectedNeedIds.length} 个需求吗？`
    },

    // 发布需求方法
    handleNeedImageSuccess(response) {
      const name = this.parseUploadPayload(response)
      if (name) this.needForm.xuqiutupian = name
    },
    submitNeed() {
      this.$refs.needForm.validate(valid => {
        if (valid) {
          const userInfo = JSON.parse(localStorage.getItem('sessionForm') || '{}')
          const submitData = { 
            ...this.needForm,
            xuqiubianhao: 'XQ' + Date.now(),
            shenqingrenzhanghao: userInfo.yonghuzhanghao || userInfo.nonghuzhanghao || localStorage.getItem('username') || '游客',
            shenqingrenxingming: userInfo.yonghuxingming || userInfo.nonghuxingming || '游客',
            shenqingridi: new Date().toISOString().split('T')[0],
            sfsh: '待审核',
            userid: userInfo.id,
            lianxidianhua: this.needForm.lianxidianhua
          }
          
          // 处理日期格式
          if (submitData.qiwangshijian) {
            submitData.qiwangshijian = new Date(submitData.qiwangshijian).toISOString().split('T')[0]
          }
          
          this.$http.post('/bangfuxuqiu/add', submitData).then(res => {
            if (res.data.code === 0) {
              this.$message.success('需求提交成功，等待审核')
              this.resetNeedForm()
              this.activeTab = 'myNeeds'
              this.loadMyNeeds()
            } else {
              this.$message.error(res.data.msg || '提交失败')
            }
          }).catch(err => {
            console.error('提交需求失败:', err)
            const msg = (err.body && err.body.msg) || err.message || '网络异常'
            this.$message.error('提交失败：' + msg)
          })
        }
      })
    },
    resetNeedForm() {
      this.$refs.needForm.resetFields()
      this.needImageList = []
    },

    // 通用方法
    formatDate(date) {
      if (!date) return ''
      return new Date(date).toLocaleDateString('zh-CN')
    },
    truncate(text, length) {
      if (!text) return ''
      return text.length > length ? text.substring(0, length) + '...' : text
    },
    imgUrl(path) {
      if (!path) return this.defaultImage
      const p = String(path).split(',')[0].trim()
      if (p.indexOf('http') === 0) return p
      const base = (this.$config && this.$config.baseUrl) ? String(this.$config.baseUrl).replace(/\/?$/, '/') : '/api/'
      return base + p.replace(/^\//, '')
    },
    handleImageError(event, project) {
      project.tupian = null
    },
    getPlaceholderClass(type) {
      const classMap = {
        '技术帮扶': 'tech',
        '资金帮扶': 'fund',
        '产品采购帮扶': 'product',
        '产品采购帮扶需求': 'product',
        '农机帮扶': 'machine',
        '农机帮扶需求': 'machine',
        '技术': 'tech',
        '资金': 'fund',
        '产品采购': 'product'
      }
      return classMap[type] || 'default'
    },
    getPlaceholderIcon(type) {
      const iconMap = {
        '技术帮扶': 'el-icon-science',
        '资金帮扶': 'el-icon-wallet',
        '产品采购帮扶': 'el-icon-shopping-bag',
        '产品采购帮扶需求': 'el-icon-shopping-bag',
        '农机帮扶': 'el-icon-truck',
        '农机帮扶需求': 'el-icon-truck',
        '技术': 'el-icon-science',
        '资金': 'el-icon-wallet',
        '产品采购': 'el-icon-shopping-bag'
      }
      return iconMap[type] || 'el-icon-heart'
    },
    getPlaceholderText(type) {
      const textMap = {
        '技术帮扶': '技术帮扶',
        '资金帮扶': '资金帮扶',
        '产品采购帮扶': '产品采购',
        '产品采购帮扶需求': '产品采购',
        '农机帮扶': '农机帮扶',
        '农机帮扶需求': '农机帮扶',
        '技术': '技术帮扶',
        '资金': '资金帮扶',
        '产品采购': '产品采购'
      }
      return textMap[type] || '帮扶项目'
    },

    // 我的对接方法
    loadMyDockings() {
      const account = this.sessionAccount()
      if (!account) {
        console.log('用户未登录，跳过加载对接记录')
        return
      }
      this.dockingLoading = true
      this.$http.get('/duijieshenqing/list', {
        params: {
          shenqingrenzhanghao: account
        }
      }).then(res => {
        if (res.data.code === 0) {
          this.myDockings = (res.data.data && res.data.data.list) || []
        }
        this.dockingLoading = false
      }).catch(err => {
        console.error('加载对接记录失败:', err)
        this.dockingLoading = false
      })
    },
    getDockingStatusClass(status) {
      const statusMap = {
        '待审核': 'status-pending',
        '已通过': 'status-approved',
        '已拒绝': 'status-rejected'
      }
      return statusMap[status] || 'status-pending'
    },
    // 删除对接申请
    deleteDocking(id) {
      this.$confirm('确定要删除这个对接申请吗？', '删除确认', { type: 'warning' }).then(() => {
        this.$http.post('/duijieshenqing/delete', [id]).then(res => {
          if (res.data.code === 0) {
            this.$message.success('删除成功')
            this.loadMyDockings()
          } else {
            this.$message.error(res.data.msg || '删除失败')
          }
        }).catch(() => {
          this.$message.error('删除失败')
        })
      }).catch(() => {})
    },
    // 显示更新进度弹窗
    showUpdateProgress(docking) {
      this.progressForm = {
        id: docking.id,
        bangfuzhuangtai: docking.bangfuzhuangtai || '进行中',
        bangfujindu: docking.bangfujindu || ''
      }
      this.updateProgressVisible = true
    },
    // 提交进度更新
    submitProgress() {
      if (!this.progressForm.bangfujindu) {
        this.$message.warning('请输入进度说明')
        return
      }
      this.$http.post('/duijieshenqing/update', this.progressForm).then(res => {
        if (res.data.code === 0) {
          this.$message.success('进度已更新')
          this.updateProgressVisible = false
          this.loadMyDockings()
        } else {
          this.$message.error(res.data.msg || '保存失败')
        }
      }).catch(() => {
        this.$message.error('保存失败')
      })
    },
    // 确认完成对接
    confirmComplete(docking) {
      this.$confirm('确定已完成本次对接吗？此操作将标记对接为已完成状态。', '确认完成', { type: 'info' }).then(() => {
        const updateData = {
          id: docking.id,
          bangfuzhuangtai: '已完成',
          bangfujindu: docking.bangfujindu ? docking.bangfujindu + '（已完成）' : '对接已完成'
        }
        this.$http.post('/duijieshenqing/update', updateData).then(res => {
          if (res.data.code === 0) {
            this.$message.success('已确认完成对接')
            this.loadMyDockings()
          } else {
            this.$message.error(res.data.msg || '操作失败')
          }
        }).catch(() => {
          this.$message.error('操作失败')
        })
      }).catch(() => {})
    }
  }
}
</script>

<style scoped>
.page {
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #e4e8ec 100%);
}

/* 标签页导航 */
.tab-nav {
  display: flex;
  justify-content: center;
  gap: 10px;
  padding: 20px 10%;
  background: #fff;
  border-bottom: 1px solid #e8e8e8;
  flex-wrap: wrap;
}

.tab-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px 24px;
  border-radius: 25px;
  background: #f8faf8;
  border: 1px solid #e8e8e8;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 14px;
  color: #555;
}

.tab-item:hover {
  background: rgba(46, 125, 50, 0.1);
  border-color: #2E7D32;
  color: #2E7D32;
}

.tab-item.active {
  background: linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%);
  color: #fff;
  border-color: transparent;
  box-shadow: 0 4px 15px rgba(46, 125, 50, 0.3);
}

/* 标签页内容 */
.tab-content {
  padding: 30px 10%;
}

.tab-panel {
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}

/* 搜索和筛选 */
.filter-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  flex-wrap: wrap;
  gap: 15px;
}

.search-bar {
  display: flex;
  gap: 10px;
}

.search-bar input {
  padding: 12px 20px;
  border: 1px solid #e8e8e8;
  border-radius: 25px;
  width: 300px;
  font-size: 14px;
  transition: all 0.3s ease;
}

.search-bar input:focus {
  outline: none;
  border-color: #2E7D32;
  box-shadow: 0 0 0 3px rgba(46, 125, 50, 0.1);
}

.search-btn {
  padding: 12px 30px;
  background: linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%);
  color: #fff;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s ease;
}

.search-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 15px rgba(46, 125, 50, 0.3);
}

.filter-options {
  display: flex;
  gap: 10px;
}

.filter-options select {
  padding: 10px 20px;
  border: 1px solid #e8e8e8;
  border-radius: 20px;
  background: #fff;
  font-size: 14px;
  cursor: pointer;
}

/* 项目列表 */
.project-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 25px;
}

.project-item {
  background: #fff;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
  cursor: pointer;
  transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
}

.project-item:hover {
  transform: translateY(-8px);
  box-shadow: 0 12px 30px rgba(46, 125, 50, 0.15);
}

.project-image {
  width: 100%;
  height: 200px;
  overflow: hidden;
}

.project-image .image-wrapper {
  width: 100%;
  height: 100%;
}

.project-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.6s ease;
}

.project-item:hover .project-image img {
  transform: scale(1.1);
}

.image-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;
  font-size: 16px;
  font-weight: 500;
  transition: transform 0.3s ease;
}

.image-placeholder span {
  opacity: 0.9;
}

.project-item:hover .image-placeholder {
  transform: scale(1.02);
}

.image-placeholder.default {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.image-placeholder.tech {
  background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
}

.image-placeholder.fund {
  background: linear-gradient(135deg, #fc4a1a 0%, #f7b733 100%);
}

.image-placeholder.product {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.image-placeholder.machine {
  background: linear-gradient(135deg, #a18cd1 0%, #fbc2eb 100%);
}

.project-info {
  padding: 20px;
}

.project-title {
  font-size: 18px;
  font-weight: bold;
  color: #1a1a1a;
  margin-bottom: 10px;
  cursor: pointer;
  transition: color 0.3s ease;
}

.project-title:hover {
  color: #2E7D32;
  text-decoration: underline;
}

.project-meta {
  display: flex;
  gap: 15px;
  margin-bottom: 10px;
}

.project-type {
  background: linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%);
  color: #fff;
  padding: 4px 12px;
  border-radius: 15px;
  font-size: 12px;
}

.project-date {
  color: #999;
  font-size: 13px;
}

.project-desc {
  color: #666;
  font-size: 14px;
  line-height: 1.6;
  margin-bottom: 15px;
}

.project-contact {
  display: flex;
  gap: 15px;
  margin-bottom: 10px;
  font-size: 13px;
  color: #555;
}

.contact-location {
  display: flex;
  align-items: center;
  gap: 4px;
}

.contact-person {
  display: flex;
  align-items: center;
  gap: 4px;
}

.project-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 13px;
  color: #999;
  margin-top: 10px;
}

.apply-btn {
  background: linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%);
  color: white;
  border: none;
  padding: 6px 16px;
  border-radius: 20px;
  font-size: 13px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.apply-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(46, 125, 50, 0.3);
}

.apply-btn:active {
  transform: translateY(0);
}

.word-count {
  display: block;
  text-align: right;
  font-size: 12px;
  color: #999;
  margin-top: 5px;
}

.upload-tip {
  font-size: 12px;
  color: #999;
  margin-top: 5px;
}

/* 资源卡片 */
.resource-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(400px, 1fr));
  gap: 25px;
}

.resource-card {
  background: #fff;
  border-radius: 16px;
  padding: 25px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
}

.resource-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 25px rgba(46, 125, 50, 0.15);
}

.resource-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.resource-header h3 {
  font-size: 18px;
  color: #1a1a1a;
}

.resource-type {
  background: linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%);
  color: #fff;
  padding: 4px 12px;
  border-radius: 15px;
  font-size: 12px;
}

.resource-body {
  margin-bottom: 15px;
}

.resource-body p {
  margin-bottom: 8px;
  font-size: 14px;
  color: #555;
}

.resource-content {
  color: #666;
  line-height: 1.6;
}

.resource-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 15px;
  border-top: 1px solid #e8e8e8;
  font-size: 13px;
  color: #666;
}

.contact-btn {
  padding: 8px 20px;
  background: linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%);
  color: #fff;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  font-size: 13px;
  transition: all 0.3s ease;
}

.contact-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(46, 125, 50, 0.3);
}

/* 表单容器 */
.form-container {
  max-width: 800px;
  margin: 0 auto;
  background: #fff;
  padding: 40px;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
}

.form-title {
  text-align: center;
  font-size: 24px;
  color: #1a1a1a;
  margin-bottom: 30px;
}

/* 需求卡片 */
.needs-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(400px, 1fr));
  gap: 25px;
}

.need-card {
  background: #fff;
  border-radius: 16px;
  padding: 25px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
}

.need-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.need-header h3 {
  font-size: 16px;
  color: #1a1a1a;
}

.need-status {
  padding: 4px 12px;
  border-radius: 15px;
  font-size: 12px;
}

.status-pending {
  background: #fff3cd;
  color: #856404;
}

.status-approved {
  background: #d4edda;
  color: #155724;
}

.status-rejected {
  background: #f8d7da;
  color: #721c24;
}

.need-body {
  margin-bottom: 15px;
}

.need-body p {
  margin-bottom: 8px;
  font-size: 14px;
  color: #555;
}

.need-footer {
  padding-top: 15px;
  border-top: 1px solid #e8e8e8;
  font-size: 13px;
  color: #666;
}

/* 空状态 */
.empty {
  text-align: center;
  padding: 60px 20px;
  color: #999;
}

.empty p {
  margin: 20px 0;
  font-size: 16px;
}

.refresh-btn, .publish-btn {
  padding: 12px 30px;
  background: linear-gradient(135deg, #2E7D32 0%, #1B5E20 100%);
  color: #fff;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s ease;
}

.refresh-btn:hover, .publish-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 15px rgba(46, 125, 50, 0.3);
}

/* 加载状态 */
.loading {
  text-align: center;
  padding: 40px;
  color: #999;
}

/* 分页 */
.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 20px;
  margin-top: 40px;
}

.page-btn {
  padding: 10px 25px;
  background: #fff;
  border: 1px solid #e8e8e8;
  border-radius: 20px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s ease;
}

.page-btn:hover:not(:disabled) {
  border-color: #2E7D32;
  color: #2E7D32;
}

.page-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.page-info {
  font-size: 14px;
  color: #666;
}

/* 我的对接样式 */
.docking-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(400px, 1fr));
  gap: 25px;
}

.docking-card {
  background: #fff;
  border-radius: 16px;
  padding: 25px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
}

.docking-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid #e8e8e8;
}

.docking-header h3 {
  font-size: 18px;
  color: #1a1a1a;
  margin: 0;
}

.docking-status {
  padding: 4px 12px;
  border-radius: 15px;
  font-size: 12px;
}

.docking-body {
  margin-bottom: 15px;
}

.docking-body p {
  margin-bottom: 10px;
  font-size: 14px;
  color: #555;
}

.contact-info {
  margin-top: 20px;
  padding: 15px;
  background: linear-gradient(135deg, rgba(46, 125, 50, 0.05) 0%, rgba(46, 125, 50, 0.1) 100%);
  border-radius: 10px;
  border-left: 3px solid #2E7D32;
}

.contact-info h4 {
  font-size: 16px;
  color: #2E7D32;
  margin: 0 0 10px 0;
}

.phone-link {
  color: #2E7D32;
  text-decoration: none;
}

.phone-link:hover {
  text-decoration: underline;
}

.docking-feedback {
  padding: 15px;
  background: #f5f5f5;
  border-radius: 8px;
}

.docking-feedback p {
  margin: 0;
  font-size: 14px;
  color: #666;
}

.docking-actions {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
  margin-top: 15px;
  padding-top: 15px;
  border-top: 1px solid #e8e8e8;
}

.progress-info {
  margin-top: 15px;
  padding: 12px;
  background: #fff3cd;
  border-radius: 8px;
  border-left: 3px solid #ffc107;
}

.progress-info p {
  margin: 0;
  font-size: 14px;
  color: #856404;
}
</style>
