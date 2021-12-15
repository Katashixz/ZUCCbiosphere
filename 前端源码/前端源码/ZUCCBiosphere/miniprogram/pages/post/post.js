// pages/post/post.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
      postID: null,
      resContent:"",
      postDetail:{
          user_ID:'',
          user_icon:'',
          user_openID:'',
          user_content:'',
          post_Date:'',
          post_Image:[],
          post_likeNum:0,
          post_reportNum:0,
          post_commentNum:0,
          post_isLiked:false,
          post_isEssential:true
      },
      commentDetail:[{
        user_ID:'',
        user_icon:'',
        user_Content:'',
        post_Date:'',
        // post_likeNum:3,
        // post_isLiked:false
      },
    ],
    //评论框
    windowHeight: 0,//记录界面高度
    containerHeight: 0,//记录未固定整体滚动界面的高度,内容高度
    containerBottomHeight: 0,//记录未固定整体滚动界面距离底部高度
    keyboardHeight: 0,//键盘距底部高度
    isIphone: false,//是否为苹果手机，因苹果手机效果与Android有冲突，所以需要特殊处理
    commentsContent:null
  },

  onLoad: function(options) {
    console.log("----"+options.postID);
    this.loadPostDetail(options.postID)
    this.loadPostComment(options.postID)
    this.setData({
        postID:options.postID
    })
    var that = this
    //获取屏幕高度以及设备信息（是否为苹果手机）
    wx.getSystemInfo({
      success: function(res) {
      console.log(res.windowHeight)
      that.data.windowHeight = res.windowHeight
      that.data.isIphone = res.model.indexOf("iphone") >= 0 || res.model.indexOf("iPhone") >= 0

      // console.error(res)

      }
    });
  },



     /* 获取回复内容 */
     getResContent: function (e){
        this.setData({
            resContent:e.detail.value
        })
        console.log(e)
        console.log(this.data.resContent)
      },

// 回复内容
      clickComments:function(){
        if (getApp().globalData.userInfo){
            this.pushcomment()
        }
        else{
            this.getUserProfile()
        }
      },


  pushcomment:function(){
    var that = this
    wx.request({
        url: getApp().globalData.urlHome + '/postBar/pushComment',
        method:'POST',
          header:{'content-type': 'application/json;charset=utf-8',
                  'x-auth-token': getApp().globalData.token
        },
        data:{
            post_ID: that.data.postID,
            user_ID: getApp().globalData.openid,
            acceptID: that.data.postDetail.user_openID,
            comment:that.data.resContent
        },
        success(r){
            console.log(r)
            that.loadPostDetail(that.data.postID)
            that.loadPostComment(that.data.postID)
            that.setData({
                resContent:""
            })
        },
    })

  },

    // 点赞功能
    changeLike:function(){
        const post = this.data.postDetail
        if (getApp().globalData.userInfo){
            this.getLikeNum(post)
        }
        else{
            this.getUserProfile()
        }
    },

      // 获取点赞数
      getLikeNum: function(post){
        var that = this
        console.log(post.post_isLiked)
        wx.request({
            url: getApp().globalData.urlHome + '/postBar/modifyLike',
            method:'POST',
              header:{'content-type': 'application/json;charset=utf-8',
                      'x-auth-token': getApp().globalData.token
            },
            data:{
                post_ID: that.data.postID,
                user_ID: getApp().globalData.openid,
                post_isLiked: post.post_isLiked
            },
            complete(r){
                console.log(r)
                that.setData({
                    [`postDetail.post_isLiked`]:!that.data.postDetail.post_isLiked,
                    [`postDetail.post_likeNum`]:r.data.post_likeNum
                })
            },
        })
    },


  //打赏弹窗
  toReward: function(){
    const post = this.data.postDetail
    if (getApp().globalData.userInfo){
        var that = this
        wx.showActionSheet({
            itemList: ['10','20','50'],//显示的列表项
            success: function (tap) {//res.tapIndex点击的列表项
                that.payPoint(post, tap.tapIndex)
            },
         })
    }
    else{
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

//   获取信息
  loadPostDetail(postID){
    var that = this
    wx.request({
      url: getApp().globalData.urlHome + '/postBar/loadPostDetail',
      method:'POST',
        header:{'content-type': 'application/json;charset=utf-8',
                'x-auth-token': getApp().globalData.token
      },
      data:{
        post_ID:postID,
        user_ID:getApp().globalData.openid
      },
      complete(r){
          console.log(r)
          that.setData({
            postDetail:r.data
          })
      },
    })
  },

  loadPostComment(postID){
    var that = this
    wx.request({
      url: getApp().globalData.urlHome + '/postBar/loadPostComment',
      method:'POST',
        header:{'content-type': 'application/json;charset=utf-8',
                'x-auth-token': getApp().globalData.token
      },
      data:{
        post_ID:postID
      },
      success(r){
          console.log(r)
        that.setData({
          commentDetail:r.data
        })

      },
    })  
  },




  //初次渲染
  onReady: function() {
    var that = this
    setTimeout(() => {
    //界面初始化渲染需要初始化获取整体界面的高度以及距离信息   
    that.refreshContainerHeight()   
    }, 800);   
  },

  refreshContainerHeight: function() {
    var that = this   
    var query = wx.createSelectorQuery();   
    query.select('.container').boundingClientRect()   
    query.exec((res) => {
    //container为整体界面的class的样式名称   
    that.data.containerHeight = res[0].height;
    that.data.containerBottomHeight = res[0].bottom
    
    })
    
  },

  onPageScroll: function(e) {
      var that = this    
      // 界面滚动刷新整体界面高度以及间距
      that.refreshContainerHeight()   
  },
      
      /**
      * 评论框焦点获取监听
      */
      
  inputCommentsFocus: function(e) {
    console.log(e)
    var that = this
    if (!that.data.isIphone) {
      var keyboardHeight = e.detail.height
      var windowHeight = that.data.windowHeight
      var containerHeight = that.data.containerHeight
      var containerBottomHeight = that.data.containerBottomHeight
      //整体内容高度大于屏幕高度，才动态计算输入框移动的位置；
      if (containerHeight > windowHeight) {
        if ((containerBottomHeight - windowHeight) > keyboardHeight) {
        //距离底部高度与屏幕高度的差值大于键盘高度，则评论布局上移键盘高度；     
          that.setData({
            keyboardHeight: e.detail.height    
          })
      
        } else {
            //距离底部高度与屏幕高度的差值小于键盘高度，则评论布局上移距离底部高度与屏幕高度的差值；
            var newHeight = containerBottomHeight - windowHeight      
            that.setData({
             keyboardHeight: newHeight     
            })      
        }     
      } else {
        that.setData({
          keyboardHeight: 0
        })
      }
    } else {
      that.setData({
        keyboardHeight: 0    
    })
    }      
  },

      /**     
      * 评论框焦点失去监听     
      */
      
  inputCommentsBlur: function(e) {
      var that = this
      that.setData({
        keyboardHeight: 0
      })
      this.setData({
          resContent:e.detail.value
      })
      },
  
//   查看图片
handleImagePreview(e) {
    const idx = e.target.dataset.idx
    const images = this.data.postDetail.post_Image
    console.log(images)
    let toImg = []
    images.forEach(element => {
        toImg.push(element.url)
    });
    wx.previewImage({
      current: toImg[idx],  //当前预览的图片
      urls: toImg,  //所有要预览的图片
    })
  },
})