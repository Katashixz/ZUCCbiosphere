// pages/myPost/myPost.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        user_ID:null,
        myPostList:[
            {  
                post_ID:1,
                post_Theme:'吐槽',
                post_Content:'test',
                post_Image:'',
            },
            {  
                post_ID:2,
                post_theme:'晒动物',
                post_content:'test',
                post_Image:"https://c-ssl.duitang.com/uploads/item/201912/31/20191231121259_dckjf.thumb.1000_0.jpg",
            }
        ],
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(){
        console.log(getApp().globalData.openid);
        this.loadMyPost()
    },


    loadMyPost(){
        var that = this
        wx.request({
            url: getApp().globalData.urlHome + '/postBar/loadMyPost',
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
                myPostList:r.data
            })
        },
        })
    },

    toPost:function(e){
        console.log(e);
        console.log(e.currentTarget.dataset.index);
        wx.navigateTo({
            url:'/pages/post/post?postID='+this.data.myPostList[e.currentTarget.dataset.index].post_ID
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