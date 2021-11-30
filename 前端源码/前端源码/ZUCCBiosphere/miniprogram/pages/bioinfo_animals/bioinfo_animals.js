// pages/bioinfo/bioinfo.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
      hs_name1:"白手套",
      hs_name2:"小花",
      
    },
  
    tobioinfo_plants:function(){
      wx.navigateTo({
        url: '/pages/bioinfo_plants/bioinfo_plants',
      })
    },

    toupdateInterface:function(){
        wx.navigateTo({
          url: '/pages/bioupdate/bioupdate',
        })
    }
  })

