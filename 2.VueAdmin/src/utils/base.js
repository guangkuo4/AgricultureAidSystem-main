const base = {
    get() {
        return {
            url : "http://localhost:8084/springboot2855f2n2/",
            name: "springboot2855f2n2",
            // 退出到首页链接
            indexUrl: 'http://localhost:8084/springboot2855f2n2/front/dist/index.html'
        };
    },
    getProjectName(){
        return {
            projectName: "助农扶贫服务平台"
        } 
    }
}
export default base
