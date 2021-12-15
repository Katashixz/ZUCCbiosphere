// pages/activityDetail/activityDetail.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        activityId:"1",
        activityPersonId:"86",
        activityType:"拍照",
        activityParticipantsNum:"5",
        activityTotolParticipantsNum:"10",
        activityTitle:"白手套脚瘸了，我们去看看他",
        activityContent:"猫，属于猫科动物，分家猫、野猫，是全世界家庭中较为广泛的宠物。家猫的祖先据推测是古埃及的沙漠猫，波斯的波斯猫，已经被人类驯化了3500年（但未像狗一样完全地被驯化一般的猫：头圆、颜面部短，前肢五指，后肢四趾，趾端具锐利而弯曲的爪，爪能伸缩。夜行性。以伏击的方式猎捕其它动物，大多能攀援上树。猫的趾底有脂肪质肉垫，以免在行走时发出声响，捕猎时也不会惊跑鼠。行进时爪子处于收缩状态，防止爪被磨钝，在捕鼠和攀岩时会伸出来。",
        activityLocation:"北校情人坡",
        activityStartDate:"2021/12/8 13:30",
        activityDate:"2小时前",
        activityImage:""
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options){
        // console.log(options)
        this.setData({
            activityId:options.activityId
        })
        this.onRefresh();
    },

  //下拉刷新
  onPullDownRefresh: function(){
    this.onRefresh();
  },
    
  //刷新发送请求到后端获取帖子数据
  onRefresh(){
    var that = this
    wx.request({
        url: getApp().globalData.urlHome + '/acfc/loadActivityDetail',
        method:'POST',
          header:{'content-type': 'application/json;charset=utf-8',
                  'x-auth-token': getApp().globalData.token
        },
        data:{
            activityId:this.data.activityId
        },
        complete(r){
            console.log(r)
            that.setData({
                activityPersonId:r.data.activityPersonId,
                activityType:r.data.activityType,
                activityParticipantsNum:r.data.activityParticipantsNum,
                activityTotolParticipantsNum:r.data.activityTotolParticipantsNum,
                activityTitle:r.data.activityTitle,
                activityContent:r.data.activityContent,
                activityLocation:r.data.activityLocation,
                activityStartDate:r.data.activityStartDate,
                activityDate:r.data.activityDate
            })
        },
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