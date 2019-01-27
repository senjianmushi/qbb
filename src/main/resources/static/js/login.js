$(document).ready(function ()  {
    $('#login #password').focus(function() {
        $('#owl-login').addClass('password');
    }).blur(function() {
        $('#owl-login').removeClass('password');
    });
});

$('#btnLogin').click(function() {
    // window.location.href="adminIndex";
    $.ajax({
        url: "/dologin",
        type: 'post',
        data: {
            "username": $("#name").val(),
            "password": $("#password").val()
        },
        success: function (data) {
            console.log("login.data");
            console.log(data);
            if (data != null) {
               if(data.code == 0){
                   //alert("登录成功");
                   window.location.href="/view/adminIndex";
               }else{
                   alert(data.data);
                   //window.location.href="/adminIndex";
               }
            }
        }
    })
})

$('#btnRegister').click(function() {
    window.location.href="/view/register";
})

