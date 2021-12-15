// pages/myRewardRecord/myRewardRecord.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        userInfo:{},
        user_ID:null,
        myRewardList:[
            {  
                RPCount_type:0,//0,1对应登陆奖励和打赏
                rewarded_Name:'被赞赏用户',
                rewarded_count:'赞赏数',
                rewarded_date:"时间",
                
            },
        ],
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.setData({
            userInfo:getApp().globalData.userInfo
        })
        this.loadMyRewardRecord()
    },

    loadMyRewardRecord(){
        var that = this
        wx.request({
            url: getApp().globalData.urlHome + '/postBar/loadMyRewardRecord',
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
                myRewardList:r.data
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