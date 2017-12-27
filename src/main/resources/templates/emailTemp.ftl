<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>welcome to spring boot</title>
</head>
<body>
<h4>亲爱的${username}，您好！</h4>
    <p style="color:blue;">&emsp;${message} ,验证码为:${code}, 有效时间为5分钟 , 请不要告诉其他人，切记！</p>
祝：开心！
</br>
${from}
</br>
${time?date}
</body>
</html>