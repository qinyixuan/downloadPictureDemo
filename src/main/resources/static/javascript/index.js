$('#download').click(function () {
    // loading
    var url = $('#url').val();
    $.ajax({
        url: "downloadPicture",
        data: {
            url: url
        },
        success:function (result) {
            if ("success" === result.info) {
                alert("下载成功~");
            } else {
                alert("下载失败~");
            }
        }
   })
});