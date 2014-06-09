<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	int pipelineCount = 7;
	int stageCount = 10;
	int columns = 16;
	int rows = 16;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="./css/steak.css" rel="stylesheet">
<title>Steak</title>
<script>
	window.onload = function() {
		var navbarWidth = document.getElementById('nav-container').offsetWidth;
		var navItemWidth = document.getElementById('pipeline-container').offsetWidth;

		if (navbarWidth - 30 <= navItemWidth) {
			alert('Overflow!!!');
		}
	}

	function changePipeline(lineNum) {
		var anch = document.getElementById('pipeline');
		anch.innerHTML = document.getElementById('pipeline_' + lineNum).innerHTML;
	}
</script>
</head>
<body>
  <div class="navbar navbar-inverse navbar-fixed-top navbar-">
    <div class="container">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
          <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#">Recipes - Steak</a>
      </div>
      <div class="navbar-collapse collapse" id="nav-container">
        <ul class="nav navbar-nav" id="pipeline-container">
          <li class="active"><a href="#" id="pipeline">Pipeline 0</a></li>
          <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Pipelines <b class="caret"></b></a>
            <ul class="dropdown-menu">
              <%
              	for ( int i = 0; i < pipelineCount; i++ ) {
              %>
              <li><a href="#" onclick="changePipeline(<%=i%>)" id="pipeline_<%=i%>">Pipeline <%=i%></a></li>
              <%
              	}
              %>
            </ul></li>
        </ul>
        <form class="navbar-form navbar-right">
          <input type="submit" class="btn btn-success" value="New Box" /> <input type="submit" class="btn btn-danger" value="Delete Box" />
          <input type="submit" class="btn btn-info" value="Share Box" />
        </form>
      </div>
    </div>
  </div>

  <div class="container" style="text-align: center;">
    <%
    	for ( int i = 0; i < stageCount; i++ ) {
    %>
    <div class="stage dragging" id="stage_<%=i%>" style="background-color: #aaaaaa; width: <%=95 / stageCount%>%;">
      <h2>0</h2>
      Stage
      <%=i%>
    </div>
    <%
    	}
    %>
  </div>

  <div class="container" style="text-align: center;">
    <div class="navbar-collapse collapse" style="height: 500px;">
      <table class="cells">
        <%
        	for ( int i = 0; i < rows; i++ ) {
        %>
        <tr>
          <%
          	for ( int j = 0; j < columns; j++ ) {
          %>
          <td class="columns" style="background-color: #<%=i%><%=j%><%=i%><%=j%><%=i%><%=j%>"><%=i%>, <%=j%></td>
          <%
          	}
          %>
        </tr>
        <%
        	}
        %>
      </table>
    </div>
  </div>

  <script src="./js/jquery.js"></script>
  <script src="./js/bootstrap.min.js"></script>
</body>
</html>