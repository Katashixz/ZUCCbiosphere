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
          image:"https://pic3.zhimg.com/f217948947c44ecda6c8d7b0a49e11d0_r.jpg?source=1940ef5c",
          ID:"1",
          type:"植物",
          name:"多肉1",
          scientificName:"tbd",
          situation:"可人",
          appearance:"好看",
          other:"无",
        },
        {
          image:"https://pic3.zhimg.com/f217948947c44ecda6c8d7b0a49e11d0_r.jpg?source=1940ef5c",
          ID:"2",
          type:"植物",
          name:"多肉2",
          scientificName:"tbd",
          situation:"可人",
          appearance:"好看",
          other:"无",
        },
        {
          image:"https://pic3.zhimg.com/f217948947c44ecda6c8d7b0a49e11d0_r.jpg?source=1940ef5c",
          ID:"3",
          type:"植物",
          name:"多肉3",
          scientificName:"tbd",
          situation:"可人",
          appearance:"好看",
          other:"无",
        },
        

      ]
    },
  
    tobioinfo_animals:function(){
      wx.redirectTo({
        url: '/pages/bioinfo_animals/bioinfo_animals',
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

