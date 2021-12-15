// pages/home/home.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isLogin: false,
    postList:[
      {
        post_ID:"",
        user_ID:"",
        user_openID:"",
        user_head:"",
        post_Content:"",
        post_Date: "",
        post_Image:[],
        post_commentNum:0,
        post_likeNum:0,
        post_isTop:false,
        post_isEssential:true,
        post_isLiked:false,
        post_isStared:false
    }
    ],
    tenHotPosts:[
        {
            post_ID:"",
            post_Content:""
        },
    ]
  },

  onLoad: function(){
    this.onRefresh();
  },


  //下拉刷新
  onPullDownRefresh: function(){
    this.onRefresh();
  },


  // 切换时获取数据

  onShow: function(){
      if (getApp().globalData.openid!= '' && !this.data.isLogin){
          this.setData({
              isLogin:!this.data.isLogin
          })
      }
      this.onRefresh();
  },

 
  //跳转到城友会界面
  tobiofri: function(){
    wx.navigateTo({
        url:'/pages/biofri/biofri'
    })
  },

    //跳转到十大热帖界面
    toTenHotPosts:function(){
        wx.navigateTo({
            url:'/pages/tenHotPost/tenHotPost'
        })
    },

    //跳转到动植物科普界面
    tobioinfo:function(){
        wx.navigateTo({
            url:'/pages/bioinfo/bioinfo'
        })
        },

    // 点赞功能
    changeLike:function(e){
        const post = this.data.postList[e.currentTarget.dataset.index]
        if (getApp().globalData.userInfo){
            this.getLikeNum(post, e.currentTarget.dataset.index)
        }
        else{
            this.getUserProfile()
        }
    },

    
  //打赏弹窗
  toReward: function(e){
    const post = this.data.postList[e.currentTarget.dataset.index]
    if (getApp().globalData.userInfo){
        if(getApp().globalData.openid == post.user_openID){
          wx.showToast({
            title: '不能给自己打赏',
            icon:'error',
          })
        }else{
          var that = this
          wx.showActionSheet({
              itemList: ['10','20','50'],//显示的列表项
              success: function (tap) {//res.tapIndex点击的列表项
                  that.payPoint(post, tap.tapIndex)
              },
           })
        }
        
    }else{
        this.getUserProfile()
    }
  },

  payPoint:function(post, point){
    wx.request({
        url: getApp().globalData.urlHome + '/user/rpSend',
        method:'POST',
          header:{'content-type': 'application/json;charset=utf-8',
                  'x-auth-token': getApp().globalData.token
        },
        data:{
            posterID: post.user_openID,
            openid: getApp().globalData.openid,
            point: point
        },
        success: res => {
            wx.showToast({
                title: res.data.result,
                icon: 'success',
                duration: 2000
               })
        },
    })
    
  },

    // 获取点赞数
    getLikeNum: function(post, index){
        var that = this
        console.log(post.post_isLiked)
        wx.request({
            url: getApp().globalData.urlHome + '/postBar/modifyLike',
            method:'POST',
              header:{'content-type': 'application/json;charset=utf-8',
                      'x-auth-token': getApp().globalData.token
            },
            data:{
                post_ID: post.post_ID,
                user_ID: getApp().globalData.openid,
                post_isLiked: post.post_isLiked
            },
            complete(r){
                console.log(r)
                that.setData({
                    [`postList[${index}].post_isLiked`]:!that.data.postList[index].post_isLiked,
                    [`postList[${index}].post_likeNum`]:r.data.post_likeNum
                })
            },
        })
    },

    //跳转到帖子详情页面
    toPost:function(e){
        console.log(e);
        wx.navigateTo({
            url:'/pages/post/post?postID='+this.data.postList[e.currentTarget.dataset.index].post_ID
        })
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
            getApp().globalData.hasUserInfo = true
            this.setData({
              userInfo: res.userInfo
              
            })
            
    
          },
    
           complete: () => {
            console.log("sendUserInfo Begin");
            this.sendUserInfo()
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
                that.onRefresh()
                
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
 //刷新发送请求到后端获取帖子数据
 onRefresh(){
    var that = this
    wx.request({
        url: getApp().globalData.urlHome + '/postBar/loadPost',
        method:'POST',
          header:{'content-type': 'application/json;charset=utf-8',
                  'x-auth-token': getApp().globalData.token
        },
        data:{
            user_ID:getApp().globalData.openid
        },
        complete(r){
            console.log(r)
            that.setData({
                postList:r.data
            })
        },
    })
      
    wx.request({
        url: getApp().globalData.urlHome + '/postBar/loadHotPost',
        method:'POST',
          header:{'content-type': 'application/json;charset=utf-8',
                  'x-auth-token': getApp().globalData.token
        },
  
        complete(r){
            console.log(r)
            that.setData({
                tenHotPosts:r.data
            })
        },
      })

  },

//   查看图片
  handleImagePreview(e) {
    console.log(e)
    const idx = e.target.dataset.idx
    const idx2 = e.target.dataset.idx2
    const images = this.data.postList[idx].post_Image
    console.log(images)
    let toImg = []
    images.forEach(element => {
        toImg.push(element.imageurl)
    });
    wx.previewImage({
      current: toImg[idx2],  //当前预览的图片
      urls: toImg,  //所有要预览的图片
    })
  },
    
})