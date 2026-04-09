<template>
  <div class="breadcrumb">
    <el-breadcrumb :separator="separator">
      <el-breadcrumb-item>首页</el-breadcrumb-item>
      <el-breadcrumb-item v-for="(item, index) in breadcrumbItem" :key="index">{{item.name}}</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script>
  export default {
    name: 'Breadcrumb',
    props: {
      separator: {
        type: String,
        default: '/'
      },
      breadcrumbItem: {
        type: Array,
        default: () => []
      }
    }
  }
</script>

<style lang='css' scoped>
  .breadcrumb {
    width: 1000px;
    height: 60px;
    line-height: 60px;
    margin: 15px auto 50px;
    border-radius: 5px;
    background-color: #f90;
    padding-left: 10px;
    color: #fff;
  }
  .el-breadcrumb {
    color: #fff;
  }
  .el-breadcrumb__item a {
    color: #fff !important;
  }
  .el-breadcrumb__item:last-child {
    color: #fff !important;
  }
</style>