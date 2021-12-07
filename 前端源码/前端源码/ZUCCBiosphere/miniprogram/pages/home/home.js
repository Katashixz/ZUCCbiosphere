// pages/home/home.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    postList:[
        {
            post_ID:"86",
            user_ID:"AE86",
            user_head:"http://photocq.photo.store.qq.com/psc?/V11L9yfC1eZGvm/gWShryPLAbNNMut8n7qrsMO31B4dkTc*OXzwkM*C4XIBe6dkNb6Sr94CO*v.NiDtVKdea1.Waf..TD3gD.eONAnBX3eK*UHkmGNzfbSJzrQ!/b&bo=8ACgAPAAoAAFFzQ!&rf=viewer_4",
            post_Content:"lulalulalu",
            post_Date: "11月31日",
            post_Image:[
                {
                    imageurl:"https://c-ssl.duitang.com/uploads/item/201912/31/20191231121259_dckjf.thumb.1000_0.jpg"
                },
                {
                    imageurl:"https://c-ssl.duitang.com/uploads/item/201809/16/20180916175034_Gr2hk.jpeg"
                },
                {
                    imageurl:"http://img.alicdn.com/tfscom/i1/O1CN01dztWUe296kWaC7hRg_!!510788019.jpg_600x600.jpg"
                }
            ],
            
            post_commentNum:33,
            post_likeNum:2,
            post_isTop:true,
            post_isEssential:false,
            post_isLiked:true,
            post_isStared:false
        },
        
        {
          post_ID:"87",
          user_ID:"AE86",
          user_head:"http://photocq.photo.store.qq.com/psc?/V11L9yfC1eZGvm/gWShryPLAbNNMut8n7qrsMO31B4dkTc*OXzwkM*C4XIBe6dkNb6Sr94CO*v.NiDtVKdea1.Waf..TD3gD.eONAnBX3eK*UHkmGNzfbSJzrQ!/b&bo=8ACgAPAAoAAFFzQ!&rf=viewer_4",
          post_Content:"离骚：战国时楚人屈原的作品。关于篇名，司马迁在《史记·屈原列传》中解释为“离忧”；王逸在《楚辞章句》中解释为“别愁”；近人或解释为“牢骚”，或解释为“楚国曲名‘劳商’的异写”。关于写作年代，有人认为写于楚怀王当朝，诗人被疏远以后；也有人认为作于顷襄王当朝，诗人再放江南时。迄无定论。《离骚》是中国古代诗歌史上最长的一首浪漫主义的政治抒情诗。诗人从自叙身世、品德、理想写起，抒发了自己遭谗被害的苦闷与矛盾，斥责了楚王昏庸、群小猖獗与朝政日非，表现了诗人坚持“美政”理想，抨击黑暗现实，不与邪恶势力同流合污的斗争精神和至死不渝的爱国热情。诗中大量运用了古代神话传说，以想象和联想的方式构成了瑰丽奇特的幻想世界，又以神游幻想世界的方式表现了诗人对理想的热烈追求。诗中大量地运用了“香草美人”的比兴手法，将深刻的内容借助具体生动的艺术形象表现出来，极富艺术魅力。《离骚》具有深刻现实性的积极浪漫主义精神，对后世产生了深远的影响",
          post_Date: "11月31日",
          post_Image:[],
          post_commentNum:33,
          post_likeNum:2,
          post_isTop:false,
          post_isEssential:true,
          post_isLiked:false,
          post_isStared:false
      },
      {
        post_ID:"88",
        user_ID:"AE86",
        user_head:"http://photocq.photo.store.qq.com/psc?/V11L9yfC1eZGvm/gWShryPLAbNNMut8n7qrsMO31B4dkTc*OXzwkM*C4XIBe6dkNb6Sr94CO*v.NiDtVKdea1.Waf..TD3gD.eONAnBX3eK*UHkmGNzfbSJzrQ!/b&bo=8ACgAPAAoAAFFzQ!&rf=viewer_4",
        post_Content:"春城无处不飞花，寒食东风御柳斜。日暮汉宫传蜡烛，轻烟散入五侯家。",
        post_Date: "11月31日",
        post_Image:[],
        post_commentNum:33,
        post_likeNum:2,
        post_isTop:false,
        post_isEssential:true,
        post_isLiked:false,
        post_isStared:false
    }
    ],
    tenHotPosts:[
        {
            post_ID:"88",
            post_Content:"这只小猫可爱爆了"
        },
        {
            post_ID:"89",
            post_Content:"见天台司马子微"
        },
        {
            post_ID:"99",
            post_Content:"谓余有仙风道骨"
        },
        {
            post_ID:"12",
            post_Content:"可与神游八极之表"
        },
        {
            post_ID:"13",
            post_Content:"因著大鹏遇希有鸟赋以自广"
        },
    ]
  },


  //下拉刷新
  onPullDownRefresh: function(){
    this.onRefresh();
  },

  //刷新发送请求到后端获取帖子数据
  onRefresh(){
      var that = this
    wx.request({
        url: getApp().globalData.urlHome + '/postBar/loadPost',
        method:'POST',
          header:{'content-type': 'application/json;charset=utf-8',
                  'x-auth-token': getApp().globalData.token
        },
  
        complete(r){
            console.log(r)
            that.setData({
                postList:r.data
            })
        },
      })
      console.log("-----datashow2-----")
      console.log(that.data)

  },

  //跳转到城友会界面
  tobiofri: function(){
    wx.navigateTo({
        url:'/pages/biofri/biofri'
    })
    },

    //跳转到十大热帖界面
    toTenHotPosts:function(){
        wx.navigateTo({
            url:'/pages/tenHotPost/tenHotPost'
        })
    },

    //跳转到动植物科普界面
    tobioinfo:function(){
        wx.navigateTo({
            url:'/pages/bioinfo/bioinfo'
        })
        },
    changeLike:function(e){
        console.log(e);
        console.log(this.data.postList[e.currentTarget.dataset.index]);
        this.setData({
            [`postList[${e.currentTarget.dataset.index}].post_isLiked`]:!this.data.postList[e.currentTarget.dataset.index].post_isLiked
        })
    },
    toReward: function(){
        
    },

    //跳转到帖子详情页面
    toPost:function(e){
        console.log(e);
        wx.navigateTo({
            url:'/pages/post/post?postID='+this.data.postList[e.currentTarget.dataset.index].post_ID
        })
    }
    

    
})