<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Ajax ����</title>
</head>
<script>
	var sendAjax = function( url ) {

		var postString = "";
		$.ajax( {

			type : "POST",
			url : "test.test",
			data : postString, //post ���� �������� data: {���ڸ� : ������, num:num},
			success : function( msg ) {
				//body �±� �ȿ��� div�� innerHTML�� ������ ������ �����ϰ� 
				//��ư Ŭ���� ������ ������ HTML�� �����Ѵ�.
				alert(msg);
			}
		} );
	};
</script>
<body>
  <input type="button" value="ȭ��1" onclick="sendAjax('ajaxView1')">
  <br>
  <input type="button" value="ȭ��2" onclick="sendAjax('ajaxView2')">
  <br>

  <hr>
  <!-- ���ο� View�� ǥ���ϱ� ������ġ. -->
  <div id="changeView"></div>
  <div id="changeView2"></div>

  <script src="./js/jquery-1.11.1.min.js"></script>
</body>
</html>