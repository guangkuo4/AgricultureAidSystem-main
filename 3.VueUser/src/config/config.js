export default {
    // 开发环境走 webpack devServer 的 /api 代理到后端，避免跨域与直连 8080 失败；打包后请按部署环境改为真实后端地址
    baseUrl: process.env.NODE_ENV === 'development' ? '/api/' : 'http://localhost:8080/',
	name: '',
    indexNav: [
        {
            name: '助农商品',
            url: '/index/zhunongshangpin'
        },
        {
            name: '助农案例',
            url: '/index/zhunongtuopin'
        },
        {
            name: '助农乐学',
            url: '/index/nongjixuetang'
        },
        {
            name: '帮扶对接',
            url: '/index/bangfuxiangmu'
        },
        {
            name: '公告资讯',
            url: '/index/news'
        },
        {
            name: '农商企业入驻',
            url: '/index/qiyeruzhuApply'
        },
    ]
}
