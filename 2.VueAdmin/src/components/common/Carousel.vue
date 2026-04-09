<template>
  <div class="carousel-container">
    <el-carousel :interval="5000" type="card" height="400px">
      <el-carousel-item v-for="(item, index) in carouselData" :key="index">
        <img :src="carouselImgSrc(item)" alt="轮播图" style="width: 100%; height: 100%; object-fit: cover;">
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
    carouselImgSrc(item) {
      const v = (item && item.value ? String(item.value) : "").trim();
      if (!v) {
        return "";
      }
      if (v.startsWith("http://") || v.startsWith("https://")) {
        return v;
      }
      const base = (this.$base.url || "").replace(/\/?$/, "/");
      return base + v.replace(/^\//, "");
    },
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
        this.carouselData = [];
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