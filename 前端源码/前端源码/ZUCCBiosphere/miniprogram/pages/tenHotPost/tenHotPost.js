// pages/tenHotPost/tenHotPost.js
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
                post_Image:[],
                post_reportNum:33,
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
                post_Content:"lulalulalu",
                post_Date: "11月31日",
                post_Image:[],
                post_reportNum:33,
                post_likeNum:2,
                post_isTop:true,
                post_isEssential:false,
                post_isLiked:true,
                post_isStared:false
            },
            {
                post_ID:"88",
                user_ID:"AE86",
                user_head:"http://photocq.photo.store.qq.com/psc?/V11L9yfC1eZGvm/gWShryPLAbNNMut8n7qrsMO31B4dkTc*OXzwkM*C4XIBe6dkNb6Sr94CO*v.NiDtVKdea1.Waf..TD3gD.eONAnBX3eK*UHkmGNzfbSJzrQ!/b&bo=8ACgAPAAoAAFFzQ!&rf=viewer_4",
                post_Content:"lulalulalu",
                post_Date: "11月31日",
                post_Image:[],
                post_reportNum:33,
                post_likeNum:2,
                post_isTop:true,
                post_isEssential:false,
                post_isLiked:true,
                post_isStared:false
            },
            {
                post_ID:"89",
                user_ID:"AE86",
                user_head:"http://photocq.photo.store.qq.com/psc?/V11L9yfC1eZGvm/gWShryPLAbNNMut8n7qrsMO31B4dkTc*OXzwkM*C4XIBe6dkNb6Sr94CO*v.NiDtVKdea1.Waf..TD3gD.eONAnBX3eK*UHkmGNzfbSJzrQ!/b&bo=8ACgAPAAoAAFFzQ!&rf=viewer_4",
                post_Content:"lulalulalu",
                post_Date: "11月31日",
                post_Image:[],
                post_reportNum:33,
                post_likeNum:2,
                post_isTop:true,
                post_isEssential:false,
                post_isLiked:true,
                post_isStared:false
            },
            {
                post_ID:"90",
                user_ID:"AE86",
                user_head:"http://photocq.photo.store.qq.com/psc?/V11L9yfC1eZGvm/gWShryPLAbNNMut8n7qrsMO31B4dkTc*OXzwkM*C4XIBe6dkNb6Sr94CO*v.NiDtVKdea1.Waf..TD3gD.eONAnBX3eK*UHkmGNzfbSJzrQ!/b&bo=8ACgAPAAoAAFFzQ!&rf=viewer_4",
                post_Content:"lulalulalu",
                post_Date: "11月31日",
                post_Image:[],
                post_reportNum:33,
                post_likeNum:2,
                post_isTop:true,
                post_isEssential:false,
                post_isLiked:true,
                post_isStared:false
            },
            
            {
              post_ID:"91",
              user_ID:"AE86",
              user_head:"http://photocq.photo.store.qq.com/psc?/V11L9yfC1eZGvm/gWShryPLAbNNMut8n7qrsMO31B4dkTc*OXzwkM*C4XIBe6dkNb6Sr94CO*v.NiDtVKdea1.Waf..TD3gD.eONAnBX3eK*UHkmGNzfbSJzrQ!/b&bo=8ACgAPAAoAAFFzQ!&rf=viewer_4",
              post_Content:"ccccccccssssssssssbbbbbbbbb",
              post_Date: "11月31日",
              post_Image:[],
              post_reportNum:33,
              post_likeNum:2,
              post_isTop:false,
              post_isEssential:true,
              post_isLiked:false,
              post_isStared:false
          }
        ]
    },
    toPost:function(e){
        console.log(e);
        wx.navigateTo({
            url:'/pages/post/post?postID='+this.data.postList[e.currentTarget.dataset.index].post_ID
        })
    }
})