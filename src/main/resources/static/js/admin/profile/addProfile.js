$(function () {
    $("#btnAdd").click(function(e){
        var name = $("#name").val();
        var identityId = $("#identityId").val();
        var describes = $("#describes").val();
        var comment = $("#comment").val();
        var picUrls = $("#picUrls").val();

        var employee = {
            name: name,
            identityId: identityId,
            describes: describes,
            comment: comment,
            picUrls: picUrls
        }
        console.log("employee")
        console.log(employee)
        var url = "/employee/insertEmployee";
        $.ajax({
            url: url,
            type:"POST",
            data: JSON.stringify(employee),//必要
            dataType:"json",
            contentType:"application/json",
            async: false,
            cache:false,
            success: function (data) {
                if (data != null) {
                    if(data.code == 0){
                        layer.msg('操作成功', {icon: 1});
                        // setTimeout(function(){
                        //     var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                        //     parent.getJobList(1)
                        //     parent.layer.close(index);
                        // }, 1000);
                    }else{
                        layer.msg('操作失败', {icon: 0});
                    }
                }
            }
        })

    })
})