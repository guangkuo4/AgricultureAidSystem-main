const base = {
    get() {
        return {
            // 静态资源、轮播图拼接用后端真实地址（与 devServer 端口区分）
            url : "http://localhost:8080/",
            // 与 vue.config.js 中 /api 代理一致，上传组件路径为 /api/file/upload
            name: "api",
            // 退出到首页链接
            indexUrl: 'http://localhost:8082/index.html'
        };
    },
    getProjectName(){
        return {
            projectName: "助农扶贫服务平台"
        } 
    }
}
export default base
