import Vue from 'vue'
import VueRouter from 'vue-router'
import VueResource from 'vue-resource'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import router from './router/router'
import BaiduMap from 'vue-baidu-map'
import VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
import '@/assets/css/iconfont.css'
import '@/assets/css/global-agriculture.css'
import config from './config/config'
import validate from './common/validate'

// 修复 AMap 错误：ReferenceError: AMap is not defined
// 延迟加载 vue-amap，确保 AMap API 正确加载
setTimeout(() => {
  try {
    import('vue-amap').then((VueAMap) => {
      Vue.use(VueAMap.default)
      VueAMap.default.initAMapApiLoader({
        //key: 'ca04cee7ac952691aa67a131e6f0cee0',
        key: '001d42eaa139dc53fd655e7c23c0187e',
        plugin: ['AMap.Autocomplete', 'AMap.PlaceSearch', 'AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType', 'AMap.PolyEditor', 'AMap.CircleEditor', 'AMap.Geocoder','AMap.CitySearch'],
        // 默认高德 sdk 版本为 1.4.4
        v: '1.4.4'
      })
    })
  } catch (error) {
    console.error('Failed to load vue-amap:', error)
  }
}, 100)
import {
	isAuth,
	getCurDateTime,
	getCurDate,
	isBackAuth
} from './common/system'
import App from './App.vue'
import Breadcrumb from '@/components/Breadcrumb'
import FileUpload from '@/components/FileUpload'
import Editor from "@/components/Editor";
import aplayer from 'vue-aplayer';
import store from './store'
import { encryptDes,decryptDes,encryptAes,decryptAes } from '@/common/des.js'
import VueLuckyCanvas from '@lucky-canvas/vue'
Vue.use(VueLuckyCanvas)
Vue.config.productionTip = false;

Vue.prototype.$config = config;
Vue.prototype.$validate = validate;
Vue.prototype.isAuth = isAuth;
Vue.prototype.isBackAuth = isBackAuth;
Vue.prototype.getCurDateTime = getCurDateTime;
Vue.prototype.getCurDate = getCurDate;
Vue.prototype.encryptDes = encryptDes
Vue.prototype.decryptDes = decryptDes
Vue.prototype.encryptAes = encryptAes
Vue.prototype.decryptAes = decryptAes

Vue.use(VueRouter);
Vue.use(VueResource);
Vue.use(ElementUI);
Vue.use(BaiduMap, {});
Vue.use(VueQuillEditor);

Vue.component('Breadcrumb', Breadcrumb);
Vue.component('file-upload', FileUpload);
Vue.component('editor', Editor);
Vue.component('aplayer', aplayer);

Vue.http.options.root = config.baseUrl;
Vue.http.headers.common['Token'] = localStorage.getItem('frontToken') || '';

/**
 * vue-resource：url 以 / 开头时不再拼接 options.root（见 url/root.js），会打到当前页面所在端口。
 * 开发环境会错误请求 8082/bangfuziyuan/add；生产环境也会忽略 http://后端/ 的 root。
 * 对非绝对地址统一去掉前导 /，使请求始终为 root + 接口路径。
 */
function apiUrl(path) {
	if (typeof path !== 'string' || !path) return path
	if (path.indexOf('http') === 0) return path
	if (path.charAt(0) === '/') return path.slice(1)
	return path
}
;['get', 'delete', 'head', 'jsonp'].forEach(function (method) {
	var orig = Vue.http[method]
	Vue.http[method] = function (url, options) {
		return orig.call(this, apiUrl(url), options)
	}
})
;['post', 'put', 'patch'].forEach(function (method) {
	var orig = Vue.http[method]
	Vue.http[method] = function (url, body, options) {
		return orig.call(this, apiUrl(url), body, options)
	}
})

// 每次请求携带最新 Token（登录后无需整页刷新）；Element Upload 等也会走同源 /api 代理
Vue.http.interceptors.push(function(request, next) {
	const token = localStorage.getItem('frontToken');
	if (token) {
		request.headers.set('Token', token);
	}
	next(function(response) {
		const body = response.body || response.data;
		const code = body && body.code;
		if (code === 401 || code === 403) {
			router.replace('/login').catch(function() {});
			try {
				localStorage.clear();
			} catch (e) {}
		}
		return response;
	});
});

router.afterEach(function() {
	Vue.http.headers.common['Token'] = localStorage.getItem('frontToken') || '';
});

const vueInstance = new Vue({
	render: h => h(App),
	router,
	store,
});
vueInstance.$mount('#app');

// 将 Vue 实例保存到全局变量，供 ErrorHandler 等工具类使用
window.__vueApp__ = vueInstance;