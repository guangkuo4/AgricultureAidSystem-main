import axios from 'axios'
import router from '@/router/router-static'
import storage from '@/utils/storage'
import Vue from 'vue'

const http = axios.create({
    timeout: 1000 * 86400,
    withCredentials: true,
    baseURL: '/api',
    headers: {
        'Content-Type': 'application/json; charset=utf-8'
    }
})
// 请求拦截
http.interceptors.request.use(config => {
    config.headers['Token'] = storage.get('Token') // 请求头带上token
    return config
}, error => {
    return Promise.reject(error)
})
// 响应拦截
http.interceptors.response.use(response => {
    if (response.data && response.data.code === 401) { // 401, token失效
        router.push({ name: 'login' })
    }
    return response
}, error => {
    // 处理 HTTP 错误，给出友好提示
    if (error && error.response) {
        const data = error.response.data
        if (data && data.msg) {
            Vue.prototype.$message.error(data.msg)
        } else if (error.response.status === 404) {
            Vue.prototype.$message.error('请求接口不存在')
        } else if (error.response.status === 500) {
            Vue.prototype.$message.error('服务器内部错误')
        } else if (error.response.status === 403) {
            Vue.prototype.$message.error('无权限访问')
        } else {
            Vue.prototype.$message.error('请求失败，请稍后重试')
        }
    } else {
        Vue.prototype.$message.error('网络错误，请检查后端服务是否启动')
    }
    return Promise.reject(error)
})
export default http
