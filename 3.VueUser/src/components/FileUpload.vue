<template>
  <div>
    <!-- 上传文件组件 -->
    <el-upload
      ref="upload"
      :action="getActionUrl"
      list-type="picture-card"
      :multiple="multiple"
      :limit="limit"
      :headers="myHeaders"
      :file-list="fileList"
      :on-exceed="handleExceed"
      :on-preview="handleUploadPreview"
      :on-remove="handleRemove"
      :on-success="handleUploadSuccess"
      :on-error="handleUploadErr"
      :before-upload="handleBeforeUpload"
    >
      <i class="el-icon-plus"></i>
      <div slot="tip" class="el-upload__tip" style="color:#838fa1;">{{tip}}</div>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible" size="tiny" append-to-body width="60%">
      <img width="100%" :src="dialogImageUrl" alt>
    </el-dialog>
  </div>
</template>
<script>
import storage from "@/common/storage";
import config from "@/config/config";
export default {
  data() {
    return {
      baseUrl: config.baseUrl,
      // 查看大图
      dialogVisible: false,
      // 查看大图
      dialogImageUrl: "",
      // 组件渲染图片的数组字段，有特殊格式要求
      fileList: [],
      fileUrlList: [],
      myHeaders:{}
    };
  },
  props: ["tip", "action", "limit", "multiple", "fileUrls"],
  mounted() {
    this.init();
    this.myHeaders= {
      'Token':storage.get("frontToken")
    }
  },
  watch: {
    fileUrls: function(val, oldVal) {
      //   console.log("new: %s, old: %s", val, oldVal);
      this.init();
    }
  },
  computed: {
    // 计算属性的 getter
    getActionUrl: function() {
      return this.baseUrl + this.action;
    },
    // 静态资源（上传文件）的访问基础路径，指向后端 upload 目录
    getResourceUrl: function() {
      return this.baseUrl.replace('/api/', '/upload/');
    }
  },
  methods: {
    // 初始化：fileUrls 由父组件传入（来自数据库），格式为纯文件名（xxx.jpg）或带 upload/ 前缀（upload/xxx.jpg）
    // 为避免 setFileList 重复添加 upload/ 前缀，统一截取纯文件名后再处理
    init() {
      if (this.fileUrls) {
        this.fileUrlList = this.fileUrls.split(",");
        let fileArray = [];
        this.fileUrlList.forEach((item, index) => {
          // 去掉已有的 upload/ 前缀，只保留纯文件名
          var url = item.trim();
          if (url.startsWith("upload/")) {
            url = url.substring(7); // 去掉 "upload/" 这7个字符
          }
          var name = index;
          var file = {
            name: name,
            url: url  // fileList 中存纯文件名，setFileList 再加完整前缀
          };
          fileArray.push(file);
        });
        this.setFileList(fileArray);
      }
    },
    handleBeforeUpload(file) {
	
    },
    // 上传文件成功后执行
    handleUploadSuccess(res, file, fileList) {
      if (res && res.code === 0) {
        // res.file 已是文件名（如 1744357200000.jpg），无需再加 upload/ 前缀
        // setFileList 中会自动拼接 baseUrl + "upload/" + 文件名组成完整访问路径
        fileList[fileList.length - 1]["url"] = res.file;
        this.setFileList(fileList);
        this.$emit("change", this.fileUrlList.join(","));
      } else {
        this.$message.error(res.msg);
      }
    },
    // 图片上传失败
    handleUploadErr(err, file, fileList) {
      this.$message.error("文件上传失败");
    },
    // 移除图片
    handleRemove(file, fileList) {
      // 确保fileList不为空时再处理
      if (fileList.length > 0) {
        this.setFileList(fileList);
        this.$emit("change", this.fileUrlList.join(","));
      } else {
        // 当所有图片都被删除时，清空fileList和fileUrlList
        this.fileList = [];
        this.fileUrlList = [];
        this.$emit("change", "");
      }
    },
    // 查看大图
    handleUploadPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    // 限制图片数量
    handleExceed(files, fileList) {
      this.$message.warning(`最多上传${this.limit}张图片`);
    },
    // 重新对fileList进行赋值
    // fileList 中的 url 为纯文件名，拼接成完整访问路径用于显示
    // fileUrlList 中存纯文件名，供父组件保存到数据库
    setFileList(fileList) {
      var fileArray = [];
      var fileUrlArray = [];
      var token = storage.get("frontToken");
      let _this = this;
      fileList.forEach(function(item, index) {
        var url = item.url.split("?")[0];
        if(!url.startsWith("http")) {
          // 完整访问路径：baseUrl + "upload/" + 文件名
          url = _this.baseUrl + "upload/" + url;
        }
        var name = item.name;
        var file = {
          name: name,
          url: url
        };
        fileArray.push(file);
        // fileUrlList 存纯文件名，供数据库存储
        var pureName = item.url.split("?")[0];
        if(pureName.startsWith("upload/")) {
          pureName = pureName.substring(7);
        }
        fileUrlArray.push(pureName);
      });
      this.fileList = fileArray;
      this.fileUrlList = fileUrlArray;
    }
  }
};
</script>
<style lang="scss" scoped>
</style>
