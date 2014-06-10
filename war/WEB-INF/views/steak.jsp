<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	int pipelineCount = 7;
	int stageCount = 10;
	int columns = 16;
	int rows = 100;
	int notiCount = 10;
	int columnWidth = 0;
%>
<html>
<head>
<meta charset="UTF-8">
<link href="./css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="./css/steak.css" rel="stylesheet">
<title>Steak</title>
<script>
	window.onload = function() {
		var navbarWidth = document.getElementById('nav-container').offsetWidth;
		var navItemWidth = document.getElementById('pipeline-container').offsetWidth;
		
		boxTable.style.maxWidth = document.getElementById('stageContainer').offsetWidth + 'px';
		boxTable.style.minWidth = <%=columns%>*document.getElementById('cols').offsetWidth + 'px';
		
		mainContainer.style.height = (document.getElementById('mainContainer').offsetHeight - 60) + 'px';
		boxContainer.style.height = (document.getElementById('boxContainer').offsetHeight - document.getElementById('stageContainer').offsetHeight) + 'px';
	}

	function changePipeline(lineNum) {
		var anch = document.getElementById('pipeline');
		anch.innerHTML = document.getElementById('pipeline_' + lineNum).innerHTML;
	}
	
	function popupAlert(cell) {
		var inner = cell.innerText;
		alert(inner);
	}
	
	function showDropdown() {
		alert('dropdown!')
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
          <input type="submit" class="btn btn-info" value="Share Pipeline" />
        </form>
      </div>
    </div>
  </div>

  <div class="main-container" id="mainContainer">
    <div class="stage-container" id="stageContainer">
      <ul style="padding: 0;">
        <%
        	for ( int i = 0; i < stageCount; i++ ) {
        %>
        <li class="stage dragging" id="stage_<%=i%>" style="background-color: #aaaaaa; width: <%=95 / stageCount%>%;">
          <h2>0</h2> Stage <%=i%>
        </li>
        <%
        	}
        %>
      </ul>
    </div>

    <div class="box-container" id="boxContainer">
      <div class="box-table" id="boxTable">
        <!-- Column header -->
        <div class="col-header-container">
          <%
          	for ( int i = 0; i < columns; i++ ) {
          %>
          <div class="cols" id="cols">
            <div class="cols-name">Column Header</div>
            <div class="cols-settings"></div>
            <div class="cols-resize"></div>
          </div>
          <%
          	}
          %>
        </div>
        <%
        	for ( int i = 0; i < stageCount; i++ ) {
        %>
        <!-- Stage name -->
        <div class="stage-name">
          <h2>
            Stage
            <%=i%></h2>
        </div>
        <%
        	for ( int j = 0; j < columns; j++ ) {
        %>
        <!-- Cells -->
        <div class="cols" id="cols">
          <div class="cols-name">Cell</div>
        </div>
        <%
        	}
        %>
        <%
        	}
        %>
      </div>
    </div>
  </div>

  <div class="noti-containter">
    <ul style="padding: 0px;">
      <%
      	for ( int i = 0; i < notiCount; i++ ) {
      %>
      <li class="notify">
        <div>
          <!-- Author -->
          <div style="display: inline-block;">
            <h4>Author</h4>
          </div>
          <!-- Date -->
          <div style="float: right; display: inline-block;">
            <h6>Jun 10</h6>
          </div>
          <!-- Action -->
          <h5>Added a comment</h5>
          <!-- Detail action -->
          <div class="comment">Comment!!!</div>
          <!-- Box -->
          <a href="#"><h5>Box name</h5></a>
        </div>
      </li>
      <%
      	}
      %>
    </ul>
  </div>
  <script src="./js/jquery.js"></script>
  <script src="./js/bootstrap.min.js"></script>
</body>
</html>