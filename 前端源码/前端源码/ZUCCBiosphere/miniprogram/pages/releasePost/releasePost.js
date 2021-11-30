
Page({

  /**
   * 页面的初始数据
   */
  data: {
    postTheme:{
      hidden:true,
      default:"选择主题",
      txt:["吐槽","晒动物","晒植物","求助"]
    }
    
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 展示或隐藏选项
   */
  showSelect: function(e){
    //获取当前数据
    var data = this.data.postTheme;
    // console.log("打开区域下拉框")
    //变更hidden属性
    data["hidden"] = !data.hidden
    //执行变更
    this.setData({
      postTheme:data
    })
  },

  /**
   * 设置当前选项的值
   */

  SelectVal: function(e){
    // 获取到点击的列表下标。
    var index = e.target.dataset.index;
    var data = this.data.postTheme
    //获取选中的选项的值
    var test_name = data.txt[index];
    //设置区域默认和隐藏
    data["default"] = test_name;
    data["hidden"] = !data.hidden;
    this.setData({
      postTheme:data,
      //获取活动类型
      theme:test_name
      
    })
  },
  



  /* 获取帖子内容 */
  getPostContent(e){
    this.setData({
      postContent:e.detail.value
    })
  },

  /* 发布帖子 */
  release(){
    console.log(this.data.theme+" "+getApp().globalData.openid)
    if(!this.data.postContent || !this.data.postTheme){
      wx.showModal({
        title:'请输入完整！'
      })
    }else{
      wx.request({
        url: getApp().globalData.urlHome + '/postBar/releasePost',
        method:'POST',
        header:{'content-type': 'application/json;charset=utf-8',
                'x-auth-token': getApp().globalData.token
      },
        data:{
          postTheme:this.data.theme,
          postContent:this.data.postContent,
          userID:getApp().globalData.openid,
        },
  
        success(r){
          wx.showToast({
            title: '操作成功！', // 标题
            icon: 'success',  // 图标类型，默认success
            duration: 1500  // 提示窗停留时间，默认1500ms
          })
          
          setTimeout(function(){
            wx.switchTab({
              url: '../home/home',
            })
          },1500)
        }
      })
    }

    
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