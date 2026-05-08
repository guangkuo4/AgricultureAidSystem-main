<template>
  <div class="carousel-container">
    <el-carousel :style='{"width":"100%","margin":"0 auto"}' trigger="click" indicator-position="bottom" arrow="hover" type="default" height="400px" :autoplay="true" :interval="5000" :loop="true">
      <el-carousel-item :style='{"width":"100%","height":"100%"}' v-for="(item, index) in carouselData" :key="index">
        <div class="carousel-item-wrapper">
          <img :src="carouselImgSrc(item)" alt="轮播图" class="carousel-image" @error="handleImageError(index)">
          <div class="carousel-overlay">
            <div class="carousel-content">
              <div class="carousel-tag">
                <i class="el-icon-s-marketing"></i>
                助农平台
              </div>
              <div class="carousel-title">{{ item.title || '让每一份农产品都能被看见' }}</div>
              <div class="carousel-desc">{{ item.description || '连接乡村与城市，传递绿色与希望' }}</div>
            </div>
          </div>
        </div>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
export default {
  name: 'Carousel',
  data() {
    return {
      carouselData: [],
      defaultImages: [
        'https://neeko-copilot.bytedance.net/api/text_to_image?prompt=beautiful%20agricultural%20landscape%20with%20green%20fields%20golden%20wheat%20and%20blue%20sky%20with%20sunlight&image_size=landscape_16_9',
        'https://neeko-copilot.bytedance.net/api/text_to_image?prompt=fresh%20organic%20farm%20products%20vegetables%20fruits%20on%20wooden%20table%20with%20natural%20light&image_size=landscape_16_9',
        'https://neeko-copilot.bytedance.net/api/text_to_image?prompt=modern%20greenhouse%20agriculture%20technology%20with%20plants%20growing%20under%20LED%20lights&image_size=landscape_16_9'
      ]
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.getCarouselData();
    });
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
      if (v.indexOf('upload/') !== -1) {
        return this.$base.url + v.replace(/^\//, "");
      }
      return this.$base.url + 'upload/' + v.replace(/^\//, "");
    },
    handleImageError(index) {
      if (this.carouselData[index]) {
        this.carouselData[index].value = this.defaultImages[index % this.defaultImages.length];
      }
    },
    getCarouselData() {
      this.$http({
        url: "config/list",
        method: "get",
        params: {
          limit: 10
        }
      }).then(({ data }) => {
        if (data && data.code === 0 && data.data && data.data.list && data.data.list.length > 0) {
          this.carouselData = data.data.list.filter(item => item.value && item.value.trim() !== '');
        } else {
          this.setDefaultCarousel();
        }
      }).catch(err => {
        console.error('轮播图数据获取失败:', err);
        this.setDefaultCarousel();
      });
    },
    setDefaultCarousel() {
      this.carouselData = this.defaultImages.map((img, index) => ({
        value: img,
        id: index
      }));
    }
  }
}
</script>

<style scoped>
.carousel-container {
  width: 100%;
  margin-bottom: 20px;
  border-radius: 16px;
  overflow: hidden;
}

.carousel-item-wrapper {
  position: relative;
  width: 100%;
  height: 100%;
  border-radius: 16px;
  overflow: hidden;
}

.carousel-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.6s ease;
}

.carousel-item-wrapper:hover .carousel-image {
  transform: scale(1.05);
}

.carousel-overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background: linear-gradient(transparent, rgba(0, 0, 0, 0.6));
  padding: 50px 40px 30px;
}

.carousel-content {
  max-width: 800px;
}

.carousel-tag {
  display: inline-flex;
  align-items: center;
  background: rgba(102, 187, 106, 0.9);
  color: #fff;
  padding: 6px 16px;
  border-radius: 20px;
  font-size: 12px;
  margin-bottom: 16px;
  font-weight: 500;
}

.carousel-tag i {
  margin-right: 6px;
}

.carousel-title {
  color: #fff;
  font-size: 28px;
  font-weight: 600;
  margin-bottom: 12px;
  text-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
}

.carousel-desc {
  color: rgba(255, 255, 255, 0.85);
  font-size: 15px;
  line-height: 1.6;
}

:deep(.el-carousel__indicator) {
  background: rgba(255, 255, 255, 0.4);
  width: 12px;
  height: 12px;
  border-radius: 50%;
}

:deep(.el-carousel__indicator.is-active) {
  background: #66bb6a;
  width: 32px;
  border-radius: 6px;
}

:deep(.el-carousel__arrow) {
  width: 44px;
  height: 44px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 50%;
  margin: 0 20px;
}

:deep(.el-carousel__arrow:hover) {
  background: rgba(102, 187, 106, 0.8);
}

:deep(.el-carousel__arrow) i {
  font-size: 20px;
}
</style>
