<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="assets/vendor/jquery/jquery.min.js"></script>
<script type="text/javascript" src="assets/vendor/jquery/jquery.qrcode.min.js"></script>
<title>Insert title here</title>
</head>
<body>
<h1>hello</h1>
<div style="text-align:center;">

<div  class="frend-link" style="width:100px;margin:0 auto;">
<!-- <span><button onclick="tests()">测试</button></span> -->

</div>
</div>
<script type="text/javascript">
function tests(){
	 $.ajax({
			type : 'post',
			url : './postdata?data={"appid": "t2e24g","appkey": "12345678","hid": "12345678","areacode": "320281","uid": "1234","zcdm": "232020008517182021","datetime": "2018-04-17","btype": "0"}',
			cache : false,
			dataType : 'json',
			success : function(data) {
				alert(data.message);
			}
		});
}
</script>
</body>
</html>