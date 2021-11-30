// pages/bioupdate/bioupdate.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        parameter: [{ id: 1, name: '动物' }, { id: 2, name: '植物' }],
        name: '',
        sex: '',
        situation: '',
        sterilization: '',
        character: '',
        appearance: '',
        others: '',
    },

    parameterTap: function (e) {
        var that = this
        var this_checked = e.currentTarget.dataset.id
        var parameterList = this.data.parameter//获取Json数组
        for (var i = 0; i < parameterList.length; i++) {
            if (parameterList[i].id == this_checked) {
                parameterList[i].checked = true;//当前点击的位置为true即选中
            }else {
                parameterList[i].checked = false;//其他的位置为false
            }
        }
        that.setData({
        parameter: parameterList
        })
    },
    nameInput:function(e){
        this.setData({
          name:e.detail.value
        })
      },
    sexInput:function(e){
    this.setData({
        sex:e.detail.value
    })
    },
    situationInput:function(e){
    this.setData({
        situation:e.detail.value
    })
    },
    sterilizationInput:function(e){
    this.setData({
        sterilization:e.detail.value
    })
    },
    characterInput:function(e){
    this.setData({
        character:e.detail.value
    })
    },
    appearanceInput:function(e){
    this.setData({
        appearance:e.detail.value
    })
    },
    othersInput:function(e){
    this.setData({
        others:e.detail.value
    })
    },
  
})