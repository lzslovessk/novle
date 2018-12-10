;(function($, window, document,undefined) {
    //在插件中使用myModal对象
    $.fn.loading = function(options) {
        //创建myModal的实体
        var mymodal = new myModal(this, options);
        //调用其方法
        return mymodal;
    }
    //定义myModal的构造函数
    var myModal = function(ele, opt) {
        this.$element = ele,
            this.defaults = {
                'image' : ''
            },
            this.options = $.extend({}, this.defaults, opt)
    }
    //定义myModal的方法
    myModal.prototype = {
        //开启loading
        openLoading:function(id){
            var that = this,
                element = this.$element,
                options = this.options,
                loading_bg = $('<div id="'+id+'" class="popup_loading_bg"></div>'),
                loading = $('<div class="popup_loading">' +
                    '<img class="loader" src="static/BJUI/plugins/loading/images/loading.png">' +
                    '<!--<div class="loading_txt">运行中</div>-->' +
                    '</div>');
            loading_bg.css({
                'width' : '100%',
                'height' : '100vh',
                'background-color' : 'rgba(0,0,0,0.6)',
                'position' : 'fixed',
                'top' : 0,
                'left' : 0,
                'z-index' : 9999
            });
            loading_bg.append(loading);
            element.append(loading_bg);
        },
        //关闭loading
        closeLoading:function(id){
            $("#"+id).remove();
        }
    }
})(jQuery, window, document);