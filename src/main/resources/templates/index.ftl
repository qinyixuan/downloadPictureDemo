<!DOCTYPE HTML>
<html>
    <head>
        <title>下载图片</title>
        <link rel="icon" href="../images/icon.png" type="image/x-icon">
        <link rel="stylesheet" href="../css/index.css">
        <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    </head>
    <body>
        <div class="center">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="请输入图片地址..." id="url">
                <span class="input-group-btn">
                    <button class="btn btn-default" type="button" id="download">下载</button>
                </span>
            </div>
        </div>
        <#--loading遮罩层-->
        <div class="modal fade" id="downloadModal" backdrop="static" keyboard="false">
            <div class="loading">
                <div class="progress progress-striped active margin-zero">
                    <div class="progress-bar full-width"></div>
                </div>
                <h5 id="downloadText">正在下载图片，请稍后...</h5>
            </div>
        </div>
        <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
        <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
        <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <script src="../javascript/index.js"></script>
    </body>
</html>