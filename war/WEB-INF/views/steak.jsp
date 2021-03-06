<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.nixgon.steak.model.SteakDataModel"%>
<%@ page import="com.nixgon.steak.model.SteakStageModel"%>
<%@ page import="com.nixgon.steak.model.SteakPipelineModel"%>
<%@ page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
<%@ page import="javax.jdo.PersistenceManager"%>
<%@ page import="com.nixgon.steak.PMF"%>
<!DOCTYPE html>
<%
	ArrayList< String > pipelines = (ArrayList< String >) request.getAttribute( "pipelines" );
	ArrayList< String > stages = (ArrayList< String >) request.getAttribute( "stages" );
	ArrayList< String > columns = (ArrayList< String >) request.getAttribute( "columns" );

	List< SteakDataModel > steakData = (List< SteakDataModel >) request.getAttribute( "steakData" );
	List< SteakStageModel > steakStage = (List< SteakStageModel >) request.getAttribute( "steakStage" );
	List< SteakPipelineModel > steakPipeline = (List< SteakPipelineModel >) request.getAttribute( "steakPipeline" );

	int pipelineCount = 0;
	int stageCount = 0;
	int colCount = 0;
	int notiCount = 10;

	SteakPipelineModel pipeline = null;

	if ( pipelines != null ) {
		pipelineCount = pipelines.size();
		pipeline = steakPipeline.get( 0 );
		System.out.println( "pipe " + steakPipeline.size() );
	} else {
		pipeline = new SteakPipelineModel();
		ArrayList< String > cols = new ArrayList< String >();
		ArrayList< String > stgs = new ArrayList< String >();
		cols.add( "" );
		stgs.add( "" );
		pipeline.setColumns( cols );
		pipeline.setPipeline( "Empty Pipeline" );
		pipeline.setStages( stgs );
	}

	if ( stages != null ) {
		stageCount = stages.size();
		System.out.println( "stages " + steakStage.size() );
	}

	if ( columns != null ) {
		colCount = columns.size();
		System.out.println( "columns " + steakData.size() );
	}
%>
<html>
<head>
<meta charset="UTF-8">
<link href="./css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="./css/steak.css" rel="stylesheet">
<title>Steak</title>
<script>
var colCount = <%=colCount%>;
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
          <li class="active"><a href="#" id="pipeline"><%=pipeline.getPipeline()%></a></li>
          <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Pipelines<b class="caret"></b></a>
            <ul class="dropdown-menu">
              <%
              	for ( int i = 0; i < pipelineCount; i++ ) {
              %>
              <li><a href="#" onclick="changePipeline(<%=i%>)" id="pipeline_<%=i%>"><%=steakPipeline.get( i ).getPipeline()%></a></li>
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
          <h2><%=steakStage.get( i ).getRows().size()%></h2> <a href="#<%=steakStage.get( i ).getStage()%>"><%=steakStage.get( i ).getStage()%></a>
        </li>
        <%
        	}
        %>
      </ul>
    </div>

    <div class="box-container" id="boxContainer">
      <!-- Column header -->
      <div class="col-header-container" id="colHeaderContainer">
        <div class="stage-cols" id="cols"
          style="border: 1px rgb(160, 160, 160) solid; padding: 8px; width: 50px; height: 40px; text-align: center;">
          <a href="#" onclick="foldToggleAll()"> <img class="arrow-down stage-down" id="downAll" src="" />
          </a>
        </div>
        <div class="cols" id="cols" style="width: 50px; text-align: center;">
          <div class="cols-name">
            <input type="checkbox" class="chkbox" id="chk_all" onclick="checkAll()" />
          </div>
        </div>
        <%
        	for ( int i = 0; i < colCount; i++ ) {
        %>
        <div class="cols" id="cols">
          <div class="cols-resize"></div>
          <div class="cols-name"><%=pipeline.getColumns().get( i )%></div>
          <div class="cols-settings"></div>
        </div>
        <%
        	}
        %>
      </div>
      <div class="box-table" id="boxTable">
        <%
        	for ( int i = 0; i < stageCount; i++ ) {
        %>
        <!-- Stage name -->
        <div class="stage-name" id="<%=steakStage.get( i ).getStage()%>">
          <div class="stage-cols" id="cols" style="width: 50px; text-align: center;">
            <div class="stage-cols">
              <a href="#" onclick="foldStage('<%=steakStage.get( i ).getStage().replaceAll( " ", "_" )%>')"> <img
                class="arrow-down stage-down" id="downStage_<%=steakStage.get( i ).getStage().replaceAll( " ", "_" )%>" src="" />
              </a>
            </div>
          </div>
          <div class="stage-cols" id="cols" style="width: 50px; text-align: center;">
            <div class="cols-name">
              <input type="checkbox" class="chkbox" id="chk_<%=steakStage.get( i ).getStage().replaceAll( " ", "_" )%>"
                onclick="checkStage('<%=steakStage.get( i ).getStage().replaceAll( " ", "_" )%>')" />
            </div>
          </div>
          <div class="stage-cols">
            <h4 class="stage-header"><%=steakStage.get( i ).getStage().replaceAll( " ", "_" )%></h4>
          </div>
        </div>
        <%
        	for ( int j = 0; j < steakStage.get( i ).getRows().size(); j++ ) {
        			PersistenceManager pm = PMF.get().getPersistenceManager();
        			SteakDataModel steak = pm.getObjectById( SteakDataModel.class, steakStage.get( i ).getRows().get( j ) );
        %>
        <div class="rows rows_<%=steakStage.get( i ).getStage().replaceAll( " ", "_" )%>">
          <div class="cols" id="cols_<%=steakStage.get( i ).getRows().get( j )%>_ico" style="width: 50px; text-align: center;">
            <div class="cols-name">
              <img src="./favicon.ico" />
            </div>
          </div>
          <div class="cols" id="cols_<%=steakStage.get( i ).getRows().get( j )%>_chk" style="width: 50px; text-align: center;">
            <div class="cols-name">
              <input type="checkbox" class="chkbox"
                id="chk_<%=steakStage.get( i ).getStage().replaceAll( " ", "_" )%>_<%=steakStage.get( i ).getRows().get( j )%>" />
            </div>
          </div>
          <%
          	for ( int k = 0; k < colCount; k++ ) {
          %>
          <!-- Cells -->
          <div class="cols" id="cols_<%=steakStage.get( i ).getRows().get( j )%>_cont">
            <div class="cols-name"><%=steak.getName()%></div>
          </div>
          <%
          	}
          %>
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
          <h5>
            <a href="#">Box name</a>
          </h5>
        </div>
      </li>
      <%
      	}
      %>
    </ul>
  </div>
  <script src="./js/steak.js"></script>
  <script src="./js/jquery.js"></script>
  <script src="./js/bootstrap.min.js"></script>
</body>
</html>