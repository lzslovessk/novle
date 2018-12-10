var $image = $(".cropper");
var img_index = '';
var img_num = '';
$(function() {
    $('.eg-wrapper').css({
        'width':'940',
        'height':'470'
    });
    $("#showEdit").fadeOut();
  $image.cropper({
    aspectRatio: 16 / 9,
    // autoCropArea: 1,
    data: {
      x: 420,
      y: 50,
      width: 640,
      height: 360
    },
    preview: ".preview",
    checkImageOrigin: true,
    dragCrop: false,
    movable: false,
    resizable: false,
    autoCrop: false
    // multiple: true,
    // dragCrop: false,
    // dashed: false,
    // modal: false,
    // movable: false,
    // resizable: false,
    // zoomable: false,
    // rotatable: false,
    // checkImageOrigin: false,

    // maxWidth: 480,
    // maxHeight: 270,
    // minWidth: 160,
    // minHeight: 90,
  });
//    $("#clear").click(function() {
//        $image.cropper("clear");
//    });
    $("#replace").click(function() {
        $image.cropper("replace", $(".cropper").attr('c'));
    });
    $("#rotateLeft").click(function() {
        $image.cropper("rotate", -90);
    });
    $("#rotateRight").click(function() {
        $image.cropper("rotate", 90);
    });
    $("#zoomIn").click(function() {
        $image.cropper("zoom", 0.4);
    });
    $("#zoomOut").click(function() {
        $image.cropper("zoom", -0.4);
    });
});
function img_refresh(){
    $image.cropper("replace", $(".cropper").attr('c'));
    img_index = $('.eg-button').attr('img_index');
    img_num = $('.eg-button').attr('img_num');
}
function replace_btn(type){
    if(type == 'prev'){
        if(img_index > 0){
            img_index--;
            $('#replace_next').removeAttr('disabled');
            $('.cropper').attr('c',parent.get_img(img_index));
            $image.cropper("replace", $(".cropper").attr('c'));
        }
    }
    else if(type == 'next'){
        if(img_index < img_num-1){
            img_index++;
            $('#replace_prev').removeAttr('disabled');
            $('.cropper').attr('c',parent.get_img(img_index));
            $image.cropper("replace", $(".cropper").attr('c'));
        }
    }
    else{
        alert('参数"'+type+'"错误');
    }
    if(img_index <= 0){
        $('#replace_prev').attr('disabled','disabled');
    }
    if(img_index >= img_num-1){
        $('#replace_next').attr('disabled','disabled');
    }
}