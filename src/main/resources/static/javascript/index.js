$('#download').click(function () {
    // loading
    showLoading();
    var url = $('#url').val();
    $.ajax({
        url: "downloadPicture",
        data: {
            url: url
        },
        success:function (result) {
            if ("success" === result.info) {
                hideLoading();
            } else {
                alert("下载失败~");
            }
        }
   })
});

showLoading = function (downloadText) {
    if (!downloadText) {
        $("#downloadText").html(downloadText)
    }
    $('#downloadModal').modal({backdrop: 'static', keyboard: false});
};

hideLoading = function () {
    $('#downloadModal').modal('hide');
};
