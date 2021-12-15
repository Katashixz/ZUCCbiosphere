// pages/biofri/biofri.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    
    activitiesList:[
    {
        activityId:"1",
        activityPersonId:"86",
        activityType:'救助',
        activityParticipantsNum:"100",
        activityContent:"青青园中葵，朝露待日晞。阳春布德泽，万物生光辉。常恐秋节至，焜黄华叶衰。百川东到海，何时复西归？少壮不努力，老大徒伤悲。",
        activityDate:"2小时前",
        activityImage:"",
        activityStarter:"大仙"
    },{
      activityId:"2",
      activityPersonId:"86",
      activityType:'拍照',
      activityParticipantsNum:"100",
      activityContent:"情人坡拍照了",
      activityDate:"3小时前",
      activityImage:"",
      activityStarter:"和尚"
    },{
      activityId:"3",
      activityPersonId:"87",
      activityType:'投喂',
      activityParticipantsNum:"100",
      activityContent:"投喂去！",
      activityDate:"4小时前",
      activityImage:"",
      activityStarter:"屠夫"
    },
    {
      activityId:"4",
      activityPersonId:"87",
      activityType:'养护',
      activityParticipantsNum:"100",
      activityContent:"科普",
      activityDate:"5小时前",
      activityImage:"",
      activityStarter:"衙役"
    },
      

    ]
  },

  //下拉刷新
  onPullDownRefresh: function(){
    this.onRefresh();
  },

  //刷新发送请求到后端获取活动数据
  onRefresh(){
      var that = this
    wx.request({
        url: getApp().globalData.urlHome + '/acfc/loadActivities',
        method:'POST',
          header:{'content-type': 'application/json;charset=utf-8',
                  'x-auth-token': getApp().globalData.token
        },
  
        complete(r){
            console.log(r)
            that.setData({
              activitiesList:r.data
            })
        },
      })
      console.log("-----datashow2-----")
      console.log(that.data)

  },

  toStartActivity: function(){
      wx.navigateTo({
        url:'/pages/startActivity/startActivity'
      })
  },

  toActivityDetail: function(e){
      console.log(e)
    wx.navigateTo({
      url:'/pages/activityDetail/activityDetail?activityId=' 
      + this.data.activitiesList[e.currentTarget.dataset.index].activityId
    })
  },

  toMyActivity: function(){
      if (getApp().globalData.userInfo != null){
        wx.navigateTo({
          url:'/pages/myActivitiyRecords/myActivitiyRecords'
        })

      }
      else{
          login()
      }
  }
})