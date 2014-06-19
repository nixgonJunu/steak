<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Ajax 예제</title>
</head>
<script>
	var sendAjax = function( url ) {

		var postString = "";
		$.ajax( {

			type : "POST",
			url : "test.test",
			data : postString, //post 형식 전송형태 data: {인자명 : 데이터, num:num},
			success : function( msg ) {
				//body 태그 안에서 div로 innerHTML을 적용한 영역을 지정하고 
				//버튼 클릭시 지정한 영역에 HTML을 삽입한다.
				alert(msg);
			}
		} );
	};
</script>
<body>
  <input type="button" value="화면1" onclick="sendAjax('ajaxView1')">
  <br>
  <input type="button" value="화면2" onclick="sendAjax('ajaxView2')">
  <br>

  <hr>
  <!-- 새로운 View를 표시하기 위한위치. -->
  <div id="changeView"></div>
  <div id="changeView2"></div>

  <script src="./js/jquery-1.11.1.min.js"></script>
</body>
</html>