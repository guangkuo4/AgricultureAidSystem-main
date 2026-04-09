<template>
  <div class="carousel-container">
    <el-carousel :interval="5000" type="card" height="400px">
      <el-carousel-item v-for="(item, index) in carouselData" :key="index">
        <img :src="item.value.substring(0,4)=='http' ? item.value : $base.url+item.value" alt="轮播图" style="width: 100%; height: 100%; object-fit: cover;">
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
export default {
  name: 'Carousel',
  data() {
    return {
      carouselData: []
    }
  },
  mounted() {
    this.getCarouselData();
  },
  methods: {
    getCarouselData() {
      this.$http({
        url: "config/list",
        method: "get",
        params: {
          limit: 10
        }
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.carouselData = data.data.list;
        }
      }).catch(err => {
        console.error('轮播图数据获取失败:', err);
      });
    }
  }
}
</script>

<style scoped>
.carousel-container {
  width: 100%;
  margin-bottom: 20px;
}
</style>