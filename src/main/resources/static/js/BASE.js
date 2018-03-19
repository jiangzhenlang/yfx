function _l(){
    console.log.apply(this,arguments)
}
var vueapp="";
var $window=$(window);
var G_height=$window.height();
var G_width=$window.width();
_l(G_height,G_width);

/**
 * 重置全局高度和宽度
 */
function resetWin(){
    G_height=$window.height();
    G_width=$window.width();

    $("body").css({
        height:G_height*0.95+"px"
    })
    _l(G_height,G_width);
}

/**
 * 复制跳转
 * @param id
 */
function goMain(id) {
    $("#"+id).slideDown(500).siblings().slideUp(500);
    clear();
}

/**
 * 复制
 */
function  copy() {
    var ikey=document.getElementById("mmkey");//对象是copy-num1
    try{
        ikey.select(); //选择对象
        var flag=document.execCommand("Copy");
        if(!flag){
            throw  "复制失败！请手动复制！";
        }
        goMain("main");

    }catch(e){
        var span="<span class=\"input-group-addon font2\" id=\"basic-addon1\">"
        span+="<span class='glyphicon glyphicon-remove'></span>"
        span+="复制失败,请手动复制！"
        span+="</span>";

        $(ikey).next().html(span);
    }
}


/**
 * 清理
 */
function clear(){
    $("input,textarea").val("");
    $("#keyQ").show().siblings().hide();
}

/**
 * 弹出框
 * @param $pop
 * @param op
 */
var gop={
    pwidth:"",
    ptitle:"",
    ptitle:"",
    pbtn:""
}
function pop($pop,op){
    var ptitle=op.title;
    var pbody=op.body;
    var pbtn=op.btn;
    var pwidth=op.width;
    ptitle?$pop.find(".modal-title").html(ptitle):"";
    pbody?$pop.find(".modal-body").html(pbody):"";
    pbtn?$pop.find(".modal-footer").html(pbtn):"";
    pwidth?$pop.find(".modal-content").css({
        width:pwidth,
        margin:"auto"
    }):"";
    $pop.modal("show");
}


var $Mpop=""
var $Malert="";
var  $i=  $("input,textarea");


$(function () {
    resetWin();//重置高和宽
    $window.resize(resetWin)

     $Mpop=$('#Mpop');
     $Malert=$("#Malert");

    /**
     * 写btn
     */
    $("#writeBtn").click(function () {
        goMain("writePanel")
       // $("#writePanel").slideDown(500).siblings().slideUp(500);
    })

    /**
     * 读btn
     */
    $("#readBtn").click(function () {
        goMain("readPanel")
    })

    /**
     * 返回
     */
    $("span[fn=goback]").click(function () {
        goMain("main");
    })





    /**
     * 获取信内容
     */
    $("#getXinBtn").click(function () {
    	getXin();
    })
    //输入框按回车触发事件
    $("#keyIp").keypress(function(e){
        var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
        if (eCode == 13){
        	getXin();
        }
    })


    

    /**
     * 模块弹框 复制
     */
    $("div.modal").on('shown.bs.modal',function(){
        var dHeight=$(this).children("div.modal-dialog").height();
        var cH=(G_height-dHeight)/2-30+"px";
        $(this).animate({
            paddingTop:cH
        },300)
    })



    $("#jmscbtn").click(function () {
        var saveUrl="/xin/save?r="+new Date().getTime();
        var data={
            param:JSON.stringify({content:encodeURIComponent($("#content").val())})
        }
        var callsuccess=function (data) {
        	console.log(data)
            pop($Mpop,{
                title:"加密生成Key",
                body:"<div><input value='"+data.key+"'  class='form-control' type='text' id=\"mmkey\" class='font1'><span></span></div>",
                btn:"  <button type=\"button\" class=\"btn btn-primary\"  onclick='copy()'>复制并关闭</button>"
            })
        }
        baseAjax(saveUrl,data,callsuccess);

    })

})


/**
 * 获取信内容
 * @returns
 */
function getXin(){
    var key=""
    key=$("#keyIp").val();
    if(key) {
        var getXinURL="/xin/get?r="+new Date().getTime();
        var data={
                    key:key
                }
        var callsuccess=function (data) {
            $("#keyC").val(decodeURIComponent(data.xnr));
            $("#keyQ").hide().siblings().show();

            //倒计时.删除



        }
        baseAjax(getXinURL,data,callsuccess);
    }
}
function  baseAjax(url,data,callSuccess) {
    $("#Mload").modal("show");
    $.ajax({
        url:url ,
        data:data,
        method: "post",
        dataType: "json",
        success: function (r) {
            var code = r.code;
            var data = r.data;
            var msg = r.msg;
            if (code == 0) {
                $("#Mload").modal("hide");
                callSuccess(data);
            } else {
                $("#Mload").modal("hide");
                pop($Malert, {
                    width: "60%",
                    title: "提示信息",
                    body: "<span class=\"font2\">" + msg + "</span>",
                    btn: " <div style=\"text-align: center;\"><button type=\"button\" class=\"btn btn-info\" data-dismiss=\"modal\">确定</button></div>"
                })
            }
        },
        error: function () {
            $("#Mload").modal("hide");
            pop($Malert, {
                body: "<span class=\"font2\">服务器异常！</span>",
                btn: " <div style=\"text-align: center;\"><button type=\"button\" class=\"btn btn-info\" data-dismiss=\"modal\">确定</button></div>"
            })
        }
    })


}