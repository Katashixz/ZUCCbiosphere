// pages/post/post.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
      postID: null,
      postDetail:{
          user_ID:'AE86',
          user_icon:'http://photocq.photo.store.qq.com/psc?/V11L9yfC1eZGvm/gWShryPLAbNNMut8n7qrsMO31B4dkTc*OXzwkM*C4XIBe6dkNb6Sr94CO*v.NiDtVKdea1.Waf..TD3gD.eONAnBX3eK*UHkmGNzfbSJzrQ!/b&bo=8ACgAPAAoAAFFzQ!&rf=viewer_4',
          user_content:'23333',
          post_Date:'10.31',
          post_Image:[],
          post_likeNum:7,
          post_reportNum:1,
          post_isLiked:false,
          post_isEssential:true
      },
      commitDetail:[{
        user_ID:'Hello',
        user_icon:'http://photocq.photo.store.qq.com/psc?/V11L9yfC1eZGvm/gWShryPLAbNNMut8n7qrsMO31B4dkTc*OXzwkM*C4XIBe6dkNb6Sr94CO*v.NiDtVKdea1.Waf..TD3gD.eONAnBX3eK*UHkmGNzfbSJzrQ!/b&bo=8ACgAPAAoAAFFzQ!&rf=viewer_4',
        user_Content:'666',
        post_Date:'1.1',
        // post_likeNum:3,
        // post_isLiked:false
      },
      {
        user_ID:'AE86',
        user_icon:'http://photocq.photo.store.qq.com/psc?/V11L9yfC1eZGvm/gWShryPLAbNNMut8n7qrsMO31B4dkTc*OXzwkM*C4XIBe6dkNb6Sr94CO*v.NiDtVKdea1.Waf..TD3gD.eONAnBX3eK*UHkmGNzfbSJzrQ!/b&bo=8ACgAPAAoAAFFzQ!&rf=viewer_4',
        user_Content:'hhh',
        post_Date:'1.1',
        // post_likeNum:3,
        // post_isLiked:false
      }
    ]

  },

  onLoad(options){
    console.log("----"+options.postID);
    this.loadPostDetail(options.postID)
    this.loadPostComment(options.postID)
  },

  loadPostDetail(postID){
    var that = this
    wx.request({
      url: getApp().globalData.urlHome + '/postBar/loadPostDetail',
      method:'POST',
        header:{'content-type': 'application/json;charset=utf-8',
                'x-auth-token': getApp().globalData.token
      },
      data:{
        post_ID:postID
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
      complete(r){
        console.log(r)
        that.setData({
          commitDetail:r.data
        })
    },
      
    })
    
  }
  
})