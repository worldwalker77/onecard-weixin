﻿<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="description" content="" />
    <meta name="keywords" content="" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <!-- Set render engine for 360 browser -->
    <meta name="renderer" content="webkit" />
    <!-- No Baidu Siteapp-->
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <meta name="format-detection" content="telephone = no" />
    <!-- Add to homescreen for Chrome on Android -->
    <meta name="mobile-web-app-capable" content="yes" />
    <!-- Add to homescreen for Safari on iOS -->
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <title>我的投诉建议</title>
    <link href="/resources/css/wechat/css_style.css" rel="stylesheet" />
    <script src="/resources/js/jquery/jquery.min.js"></script>
</head>
<body>
    <div class="feed_box">
        <h2>建议内容：${model.content}<b class="f_green"><#if model.status==0> 未处理<#else> 已处理</#if></b></h2>
        <p>${model.replyContent}</p>
        <h3><span>提交时间：${model.createTime}</span><label class="fr">回复时间：<#if model.status==0> 无 <#else> ${model.updateTime}</#if></label></h3>
    </div>
</body>
</html>
