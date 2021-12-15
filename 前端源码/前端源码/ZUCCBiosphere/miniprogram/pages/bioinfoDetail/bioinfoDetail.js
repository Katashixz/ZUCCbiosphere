// pages/bioinfoDetail/bioinfoDetail.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        bioImage:"https://c-ssl.duitang.com/uploads/item/201912/31/20191231121259_dckjf.thumb.1000_0.jpg",
        nickName:"哈士奇",
        scientificName:"中华田园猫",
        sex:"雄性",
        situation:"健康",
        sterillization:"已绝育",
        character:"乖巧",
        appearance:"漂亮",
        other:"无"
    },
    
    toupdateInterface:function(){
        wx.navigateTo({
          url: '/pages/bioupdate/bioupdate',
        })
    },

    onLoad:function(options) {
        console.log(options)
        this.setData({
            bioImage:options.bioImage,
            nickName:options.nickName,
            scientificName:options.scientificName,
            situation:options.situation,
            character:options.character,
            appearance:options.appearance
        })
    },
   
})