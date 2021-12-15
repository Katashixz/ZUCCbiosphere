// pages/myCommit/myCommit.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        userInfo:{},
        user_ID:null,
        // openid:1234,//用户开放id
        // commenter_Name:'tester',//用户名字
        // commenter_icon:"http://photocq.photo.store.qq.com/psc?/V11L9yfC1eZGvm/gWShryPLAbNNMut8n7qrsMO31B4dkTc*OXzwkM*C4XIBe6dkNb6Sr94CO*v.NiDtVKdea1.Waf..TD3gD.eONAnBX3eK*UHkmGNzfbSJzrQ!/b&bo=8ACgAPAAoAAFFzQ!&rf=viewer_4",//头像
        myCommitList:[
            {  
                //关于帖子
                post_ID:1,
                post_content:'这是帖子内容',
                post_imageurl:'',//if（exit（image））=>优先图片
                commented_Name:'被评论者',
                //关于回复
                comment_content:'评论的内容',
                comment_date:"时间",
                
            },
            {  
                
                post_ID:1,
                post_content:'这是帖子内容',
                post_imageurl:'',
                commented_Name:'被评论者',
                comment_content:'评论的内容',
                comment_date:"时间",
                
            },
        ],
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad() {
        this.setData({
            userInfo:getApp().globalData.userInfo
        })
        this.loadMyComment()
    },

    loadMyComment(){
        var that = this
        wx.request({
            url: getApp().globalData.urlHome + '/postBar/loadMyComment',
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
                myCommitList:r.data
            })
        },
        })
    },

    toPost:function(e){
        console.log(e);
        console.log(e.currentTarget.dataset.index);
        wx.navigateTo({
            url:'/pages/post/post?postID='+this.data.myCommitList[e.currentTarget.dataset.index].post_ID
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