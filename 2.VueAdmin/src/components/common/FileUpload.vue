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
    <el-dialog :visible.sync="dialogVisible" size="tiny" append-to-body>
      <img width="100%" :src="dialogImageUrl" alt>
    </el-dialog>
  </div>
</template>
<script>
import storage from "@/utils/storage";
import base from "@/utils/base";
export default {
  data() {
    return {
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
      'Token':storage.get("Token")
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
      // return base.url + this.action + "?token=" + storage.get("token");
      return `/${this.$base.name}/` + this.action;
    }
  },
  methods: {
    // 初始化：fileUrls 由父组件传入（来自数据库），可能为纯文件名（xxx.jpg）或带 upload/ 前缀（upload/xxx.jpg）
    // 统一去掉已有的 upload/ 前缀，只保留纯文件名，避免 setFileList 重复添加
    init() {
      if (this.fileUrls) {
        this.fileUrlList = this.fileUrls.split(",");
        let fileArray = [];
        this.fileUrlList.forEach(function(item, index) {
          var url = item.trim();
          if (url.startsWith("upload/")) {
            url = url.substring(7); // 去掉 "upload/" 这7个字符
          }
          var name = index;
          var file = {
            name: name,
            url: url  // 存纯文件名，setFileList 再加完整前缀
          };
          fileArray.push(file);
        });
        this.setFileList(fileArray);
      }
    },
    handleBeforeUpload(file) {
	
    },
    // 上传文件成功后执行（el-upload 在部分环境下会把响应解析成字符串）
    handleUploadSuccess(res, file, fileList) {
      let body = res;
      if (typeof body === "string") {
        try {
          body = JSON.parse(body);
        } catch (e) {
          this.$message.error("上传响应无效，请检查接口是否返回 JSON");
          return;
        }
      }
      if (body && body.code === 0 && body.file) {
        fileList[fileList.length - 1]["url"] = "upload/" + body.file;
        this.setFileList(fileList);
        this.$emit("change", this.fileUrlList.join(","));
      } else {
        this.$message.error((body && body.msg) || "上传失败");
      }
    },
    // 图片上传失败
    handleUploadErr(err, file, fileList) {
      let msg = "文件上传失败";
      if (err && err.message) {
        msg += "：" + err.message;
      }
      this.$message.error(msg);
    },
    // 移除图片
    handleRemove(file, fileList) {
      this.setFileList(fileList);
      this.$emit("change", this.fileUrlList.join(","));
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
      var token = storage.get("Token");
      let _this = this;
      fileList.forEach(function(item, index) {
        var url = item.url.split("?")[0];
	if(!url.startsWith("http")) {
	  url = _this.$base.url + "upload/" + url
	}
        var name = item.name;
        var file = {
          name: name,
          url: url + "?token=" + token
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
