// app.js
App({
  onLaunch() {
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
    var that = this;
    // 登录
    wx.login({
      success: function (res) {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        /* wx.request({
          'url': that.globalData.urlHome + '/login/getUserInfo',

          data: {
            code: res.code,
            grant_type: 'authorization_code'
          },

          method: 'GET',
          success: function (r) {
            var token = r.header['x-auth-token'];
            var openid = r.header['openid'];
            that.globalData.token = token;
            that.globalData.openid = openid;
            console.log("---get wx token success---");
            console.log(token);
          }

        }) */



      }
    })
  },
  globalData: {
    userInfo: null,
    urlHome: 'http://121.40.227.132:8080/api',
    token: '',
    openid:'',
    plans: [],
    hasUserInfo: false,
  }
})
