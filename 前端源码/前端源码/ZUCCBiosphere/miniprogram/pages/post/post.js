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
        post_likeNum:3,
        post_isLiked:false
      },
      {
        user_ID:'AE86',
        user_icon:'http://photocq.photo.store.qq.com/psc?/V11L9yfC1eZGvm/gWShryPLAbNNMut8n7qrsMO31B4dkTc*OXzwkM*C4XIBe6dkNb6Sr94CO*v.NiDtVKdea1.Waf..TD3gD.eONAnBX3eK*UHkmGNzfbSJzrQ!/b&bo=8ACgAPAAoAAFFzQ!&rf=viewer_4',
        user_Content:'hhh',
        post_Date:'1.1',
        post_likeNum:3,
        post_isLiked:false
      }
    ]

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
        postID: options.postID
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})