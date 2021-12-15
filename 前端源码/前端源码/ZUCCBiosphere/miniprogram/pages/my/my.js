// pages/my/my.js
// import{ request } from "../../request/index.js";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {},
    hasUserInfo: false,
    canIUseGetUserProfile: false,

    myrp: 0,
    myPush: 0,
    myComment: 0,
    myRewardRecord: 0,
    
    isAdmin: false,
    // commitList:[],
    
  
  },
  onLoad() {

    if(!getApp().globalData.hasUserInfo){
      if (wx.getUserProfile) {
        this.setData({
          canIUseGetUserProfile: true
        })
      }
    }else{
      this.setData({
        hasUserInfo:getApp().globalData.hasUserInfo,
        userInfo:getApp().globalData.userInfo
      })
      
      this.onRefreshUserInfo();
    }
    
  },

  onShow(){
    this.onRefreshUserInfo();
  },

  onPullDownRefresh: function(){
    this.onRefresh();
  },

  onPullDownRefresh: function(){
    this.onRefreshUserInfo();
  },

  getUserProfile(e) {
    // 推荐使用wx.getUserProfile获取用户信息，开发者每次通过该接口获取用户个人信息均需用户确认
    var that = this;
    // 开发者妥善保管用户快速填写的头像昵称，避免重复弹窗

    wx.getUserProfile({
      desc: '用于完善会员资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
      success: (res) => {
        getApp().globalData.userInfo = res.userInfo, //将用户信息存入全局
        console.log(getApp().globalData.userInfo);
        console.log("getUserProfile ok");
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      },
       complete: () => {
        console.log("sendUserInfo Begin");
        this.sendUserInfo()
        this.onRefreshUserInfo();
      } 

    })
    // 登录
    wx.login({
      success: function (res) {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        wx.request({
          'url': getApp().globalData.urlHome + '/login/getUserInfo',
          
          data: {
            code: res.code,
            grant_type: 'authorization_code'
          },

          method: 'GET',
          header:{'content-type': 'application/json;charset=utf-8',
               'x-auth-token': getApp().globalData.token
       },
          success: function (r) {
            var token = r.header['x-auth-token'];
            var openid = r.header['openid'];
            getApp().globalData.token = token;
            getApp().globalData.openid = openid;
            console.log("---get wx token success---");
            console.log(getApp().globalData.openid);
            
          }

        })
      }
    })
  },
 
  sendUserInfo(){
 
    //发送用户信息
   /*  request({
      url:"/login/saveUserInfo",
      data:{
        userInfo:getApp().globalData.userInfo.nickName,
        openId:getApp().globalData.openid,
      },
      method:'POST'
    }) */

    wx.request({
      url: getApp().globalData.urlHome + '/login/saveUserInfo',
      method:'POST',
      header:{'content-type': 'application/json;charset=utf-8',
               'x-auth-token': getApp().globalData.token
    },
      data:{
        userAvatarUrl:getApp().globalData.userInfo.avatarUrl,
        userName:getApp().globalData.userInfo.nickName,
        openId:getApp().globalData.openid,
      },
      success(r){
        console.log("sendUserInfo ok");
      }
    })
  },

  onRefreshUserInfo(){
    var that = this
    wx.request({
        url: getApp().globalData.urlHome + '/user/loadUserInfo',
        method:'POST',
          header:{'content-type': 'application/json;charset=utf-8',
                  'x-auth-token': getApp().globalData.token
        },
        data:{
            openid:getApp().globalData.openid
        },
        complete(r){
            console.log("here are the deatail")
            console.log(r)
            that.setData({
              myrp:r.data.myrp ,
              myPush: r.data.myPush,
              myComment: r.data.myComment,
              myRewardRecord: r.data.myRewardRecord,
            })
        },
    })

  },

//   跳转到手册界面
  toRulesPage:function(){
      
    wx.navigateTo({
        url:'/pages/userRule/userRule'
    })

  },

// 跳转到关于界面
  toAboutPage:function(){
      
    wx.navigateTo({
        url:'/pages/about/about'
    })

  },
  //人品值详细记录
  toMyRPRecord:function(){
      
    wx.navigateTo({
        url:'/pages/myRPRecord/myRPRecord'
    })

  },
  //跳转我的发帖界面
  toMyPost:function(){
      
    wx.navigateTo({
        url:'/pages/myPost/myPost'
    })

  },
  //我的评论
  toMyComment:function(){
      
    wx.navigateTo({
        url:'/pages/myComment/myComment'
    })

  },
  //我的打赏记录
  toRewardRecord:function(){
      
    wx.navigateTo({
        url:'/pages/myRewardRecord/myRewardRecord'
    })

  },

// 扫一扫功能
  scan() {

    wx.scanCode({
      success: (res) => {
        console.log("扫码结果");
        console.log(res);
      },

      fail: (res) => {
        console.log(res);
      }

    })
  }

})