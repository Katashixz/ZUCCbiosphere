// pages/bioinfo/bioinfo.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
      inputValue: null,
      hotSearchList:[
        {
          hs_number:1,
          hs_name:"白手套"
        },
        {
          hs_number:2,
          hs_name:"小花"
        }
      ],

      contentList:[
        {
          image:"http://www.people.com.cn/mediafile/pic/20161116/98/6613685902270301218.jpg",
          ID:"1",
          type:"动物",
          name:"小黑",
          scientificName:"tbd",
          situation:"无主",
          appearance:"大黑鼻",
          other:"无",
        },
        {
          image:"http://www.people.com.cn/mediafile/pic/20161116/98/6613685902270301218.jpg",
          ID:"2",
          type:"动物",
          name:"小白",
          scientificName:"tbd",
          situation:"无主",
          appearance:"大黑鼻",
          other:"无",
        },
        {
          image:"http://www.people.com.cn/mediafile/pic/20161116/98/6613685902270301218.jpg",
          ID:"3",
          type:"动物",
          name:"小红",
          scientificName:"tbd",
          situation:"无主",
          appearance:"大黑鼻",
          other:"无",
        },
        {
          image:"http://www.people.com.cn/mediafile/pic/20161116/98/6613685902270301218.jpg",
          ID:"4",
          type:"动物",
          name:"小橙",
          scientificName:"tbd",
          situation:"无主",
          appearance:"大黑鼻",
          other:"无",
        },
        {
          image:"http://www.people.com.cn/mediafile/pic/20161116/98/6613685902270301218.jpg",
          ID:"4",
          type:"动物",
          name:"小黑",
          scientificName:"tbd",
          situation:"无主",
          appearance:"大黑鼻",
          other:"无",
        },

      ]
    },
  
    tobioinfo_plants:function(){
      wx.redirectTo({
        url: '/pages/bioinfo_plants/bioinfo_plants',
      })
    },

    toupdateInterface:function(){
        wx.navigateTo({
          url: '/pages/bioupdate/bioupdate',
        })
    },

    clearInputEvent: function(res) {
      this.setData({
        'inputValue': ''
      })
    },

    fillInpute:function(e){
      // console.log(e.target.dataset.text)
      this.setData({
        'inputValue': e.target.dataset.text
      })
    },
    toBioinfoDetail:function(e){
      wx.navigateTo({
          url:'/pages/bioinfoDetail/bioinfoDetail?bioinfoDetailID='+this.data.contentList[e.currentTarget.dataset.index].ID
      })
  }

    
  })

