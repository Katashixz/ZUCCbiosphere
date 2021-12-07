// pages/notice/notice.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
      isNotice:false,

      noticeList:[
          {
            Id:"1",
            profilePicture:"https://c-ssl.duitang.com/uploads/item/201912/31/20191231121259_dckjf.thumb.1000_0.jpg",
            name:"城院生态圈",
            text:"非常欢迎我们会员的加入",
            time:"一个小时前"
          }
      ],

      chatList:[
          {
            Id:"1",
            profilePicture:"https://c-ssl.duitang.com/uploads/item/201912/31/20191231121259_dckjf.thumb.1000_0.jpg",
            name:"Diza",
            text:"你好啊",
            time:"一个小时前"
          }
      ]

  },

  toChatLine:function(){
      this.setData({
          isNotice:false
      })
  },

  
  toNotice:function(){
    this.setData({
        isNotice:true
    })
}

  
})