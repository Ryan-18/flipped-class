<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <link rel="apple-touch-icon" sizes="76x76" href="/static/imgs/apple-icon.png">
    <link rel="icon" type="image/png" href="/static/imgs/favicon.png">
    <link rel="stylesheet" href="/static/css/material-kit.css?v=2.0.4">
    <link rel="stylesheet" href="/static/css/user.css">
    <link rel="stylesheet" href="/static/css/icon.css">
    <link rel="stylesheet" href="/static/css/countup.css">
    <script src="https://cdn.bootcss.com/sockjs-client/1.3.0/sockjs.js"></script>
    <script src="/static/lib/stomp.js"></script>
    <script src="/static/lib/jquery-3.3.1.js"></script>
    <script src="/static/lib/countup.js"></script>
    <script src="/static/js/util.js"></script>
    <script src="/static/js/student/course/seminar/progressing.js"></script>
    <title>讨论课报名</title>
    <style>
        input::-ms-input-placeholder {
            text-align: center;
        }

        input::-webkit-input-placeholder {
            text-align: center;
        }

        #score {
            text-align: center;
            width: 100px;
        }
    </style>
</head>
<body class="card-page sidebar-collapse" data-ksId="${ksId}">
<nav class="navbar navbar-color-on-scroll navbar-expand-lg bg-dark" id="sectionsNav">
    <div class="container">
        <div class="navbar-translate">
            <a class="btn btn-link btn-fab btn-round" id="backBtn">
                <i class="material-icons">arrow_back_ios</i>
            </a>
            <div class="navbar-brand brand-title">讨论课</div>
            <button class="navbar-toggler" type="button" data-toggle="collapse" aria-expanded="false"
                    aria-label="Toggle navigation">
                <!--All are needed here. Please do not remove anything.-->
                <span class="sr-only">Toggle navigation</span>
                <span class="navbar-toggler-icon"></span>
                <span class="navbar-toggler-icon"></span>
                <span class="navbar-toggler-icon"></span>
            </button>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link">
                        <i class="material-icons">person</i>个人首页
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="left-side side-raised">
    <#list monitor.enrollList as enroll>
        <#if enroll??>
            <button class="btn btn-fab btn-round btn-team <#if (enroll?index < monitor.onPreAttendanceIndex)>passed-team<#elseif (enroll?index = monitor.onPreAttendanceIndex)>active-team<#else>preparatory-team</#if>">
                ${enroll.team.serial}
            </button>
        </#if>
    </#list>
</div>
<div class="right-upper-side side-raised">
    <button id="questionCount" class="btn btn-fab btn-round btn-team static-question" disabled>
        ${monitor.raisedQuestionsCount}
    </button>
</div>
<div class="right-downer-side side-raised">

</div>
<div class="flex-center main-area">
    <div class="container">
        <div class="row">
            <div id="timer"></div>
        </div>
        <div class="row flex-center">
            <div>3-5</div>
            <div>劳斯莱斯幻影小组</div>
        </div>
    </div>
</div>
<div class="container foot-operation">
    <div class="row  flex-center" style="flex-direction: column;">
        <div id="operation" class="flex-space-around" style="width: 100%;">
            <button id="raiseQuestion" class="btn bg-dark btn-round btn-lg">
                请求提问
            </button>
        </div>
    </div>
</div>

<form hidden id="returnForm" action="/student/course/seminarList" method="post">
    <input id="courseIdInput" name="courseId" placeholder="">
</form>
<!--   Core JS Files   -->
<script src="/static/lib/core/popper.min.js" type="text/javascript"></script>
<script src="/static/lib/core/bootstrap-material-design.min.js" type="text/javascript"></script>
<script src="/static/lib/plugins/moment.min.js"></script>
<!--	Plugin for the Datepicker, full documentation here: https://github.com/Eonasdan/bootstrap-datetimepicker -->
<script src="/static/lib/plugins/bootstrap-datetimepicker.js" type="text/javascript"></script>
<!--  Plugin for the Sliders, full documentation here: http://refreshless.com/nouislider/ -->
<script src="/static/lib/plugins/nouislider.min.js" type="text/javascript"></script>
<!-- Place this tag in your head or just before your close body tag. -->
<script async defer src="https://buttons.github.io/buttons.js"></script>
<!-- Control Center for Material Kit: parallax effects, scripts for the example pages etc -->
<script src="/static/lib/material-kit.js?v=2.0.4" type="text/javascript"></script>
</body>
</html>