// pages/bioinfo/bioinfo.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
      isAnimals: true,
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

      contentAnimalList:[
        {
          image:"https://c-ssl.duitang.com/uploads/item/201912/31/20191231121259_dckjf.thumb.1000_0.jpg",
          ID:"1",
          type:"动物",
          name:"小黑",
          scientificName:"tbd",
          situation:"无主",
          appearance:"大黑鼻",
          other:"无",
        },
        {
          image:"https://c-ssl.duitang.com/uploads/item/201912/31/20191231121259_dckjf.thumb.1000_0.jpg",
          ID:"2",
          type:"动物",
          name:"小白",
          scientificName:"tbd",
          situation:"无主",
          appearance:"大黑鼻",
          other:"无",
        },
        {
          image:"https://c-ssl.duitang.com/uploads/item/201912/31/20191231121259_dckjf.thumb.1000_0.jpg",
          ID:"3",
          type:"动物",
          name:"小红",
          scientificName:"tbd",
          situation:"无主",
          appearance:"大黑鼻",
          other:"无",
        },
        {
          image:"https://c-ssl.duitang.com/uploads/item/201912/31/20191231121259_dckjf.thumb.1000_0.jpg",
          ID:"4",
          type:"动物",
          name:"小橙",
          scientificName:"tbd",
          situation:"无主",
          appearance:"大黑鼻",
          other:"无",
        },
        {
          image:"https://c-ssl.duitang.com/uploads/item/201912/31/20191231121259_dckjf.thumb.1000_0.jpg",
          ID:"4",
          type:"动物",
          name:"小黑",
          scientificName:"tbd",
          situation:"无主",
          appearance:"大黑鼻",
          other:"无",
        },

      ],
      contentPlantList:[   
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
  
    // 设置查看植物
    tobioinfo_plants:function(){
        this.setData({
            isAnimals: false
        })
    },

    // 设置查看动物
    tobioinfo_animals:function(){
        this.setData({
            isAnimals: true
        })
      },

    // 进入我要科普界面
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

    // 进入动物详情界面
    toBioinfoDetail:function(e){

        var toURL = this.data.contentAnimalList[e.currentTarget.dataset.index];
        if (!this.data.isAnimals){
            toURL = this.data.contentPlantList[e.currentTarget.dataset.index]
        }
        console.log(toURL);
      wx.navigateTo({
          url:'/pages/bioinfoDetail/bioinfoDetail?bioImage='
          +toURL.image + '&nickName=' + toURL.name 
          + '&scientificName=' + toURL.scientificName 
          + '&situation=' + toURL.situation
          + '&character=' + toURL.character
          + '&appearance=' + toURL.appearance
      })
  },


  
    // 载入
  onLoad: function(){
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
        url: getApp().globalData.urlHome + '/intro/loadAllIntroduce',
        method:'POST',
          header:{'content-type': 'application/json;charset=utf-8',
                  'x-auth-token': getApp().globalData.token
        },
  
        complete(r){
            console.log(r)
            that.setData({
                contentAnimalList:r.data[0],
                contentPlantList:r.data[1]
            })
        },
      })


  },
    
})

