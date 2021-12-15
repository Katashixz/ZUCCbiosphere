// pages/myActivitiyRecords/myActivitiyRecords.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        activitiesList:[
            {
                activityId:"1",
                activityPersonId:"86",
                activityType:1,
                activityParticipantsNum:"100",
                activityLocation:"北校情人坡",
                activityStartDate:"2021/12/8 13:30",
                activityContent:"白手套脚瘸了，我们去看看他",
                activityDate:"2小时前",
                activityImage:""
            },{
              activityId:"2",
              activityPersonId:"86",
              activityType:2,
              activityParticipantsNum:"100",
              activityLocation:"北校情人坡",
            activityStartDate:"2021/12/8 13:30",
              activityContent:"情人坡拍照了",
              activityDate:"3小时前",
              activityImage:""
            },{
              activityId:"3",
              activityPersonId:"87",
              activityType:3,
              activityLocation:"北校情人坡",
              activityStartDate:"2021/12/8 13:30",
              activityParticipantsNum:"100",
              activityContent:"投喂去！",
              activityDate:"4小时前",
              activityImage:""
            },
            {
              activityId:"4",
              activityPersonId:"87",
              activityLocation:"北校情人坡",
              activityStartDate:"2021/12/8 13:30",
              activityType:4,
              activityParticipantsNum:"100",
              activityContent:"科普",
              activityDate:"5小时前",
              activityImage:""
            },
        ]
    },

    
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
    console.log(getApp().globalData.openid)
    wx.request({
        url: getApp().globalData.urlHome + '/acfc/loadMyActivities',
        method:'POST',
          header:{'content-type': 'application/json;charset=utf-8',
                  'x-auth-token': getApp().globalData.token
        },
        data:{
            activityId:getApp().globalData.openid
        },
        complete(r){
            console.log(r)
            that.setData({
                activitiesList:r.data
            })
        },
      })

  },
})