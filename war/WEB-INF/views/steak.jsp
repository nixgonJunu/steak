<%@page import="java.util.Date"%>
<%@page import="com.nixgon.steak.PMF"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.nixgon.steak.model.SteakModel"%>
<%@ page import="com.nixgon.steak.model.SteakDishModel"%>
<%@ page import="com.nixgon.steak.model.SteakTableModel"%>
<%@ page import="com.nixgon.steak.model.SteakShapeModel"%>
<%@ page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
<%@ page import="com.google.appengine.api.datastore.Key"%>
<%@ page import="javax.jdo.PersistenceManager"%>
<!DOCTYPE html>
<html>
<%
	SteakTableModel steakTable = (SteakTableModel) request.getAttribute( "steakTable" );
	List< SteakTableModel > steakTables = (List< SteakTableModel >) request.getAttribute( "steakTables" );
	ArrayList< SteakDishModel > steakDish = (ArrayList< SteakDishModel >) request.getAttribute( "steakDish" );
	ArrayList< SteakModel > steaks = (ArrayList< SteakModel >) request.getAttribute( "steaks" );
	SteakShapeModel shape = (SteakShapeModel) request.getAttribute( "shape" );
	String owner = (String) request.getAttribute( "owner" );
  
  int steakIndex = 0;

	int tableWidth = 0;
	for ( int width : shape.getWidth() ) {
		tableWidth += width;
	}
	tableWidth += 100;

	System.out.println( "jsp table size : " + steakTables.size() );
	System.out.println( "jsp dish size : " + steakDish.size() );
	System.out.println( "jsp steak size : " + steaks.size() );
	System.out.println( "jsp table width : " + tableWidth );
%>
<head>
<meta charset="UTF-8">
<link href="./css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="./css/steak.css" rel="stylesheet">
<title>Steak</title>
</head>
<script>
function insertNewDish() {
	var msg = "Please enter dish name";
	while(true) {
	  var dishName = prompt(msg, "Dish");
	  
	  if (dishName == null)
	  		return;
	  
	  if (dishName != '') {
	  		<%for(SteakDishModel dish : steakDish) {%>
	  		if (dishName == "<%=dish.getDish()%>") {
	  			msg = "'" + dishName + "' is exist.\nPlease enter another dish name";
	  			dishName = null;
	  		}
	    <%}%>
	    
	    if (dishName != null)
		    break;
	  }
	}
	
	var url = "insertDish";
	var params = "dishName=" + dishName;

	$.ajax( {
		type : "POST",
		url : url,
		data : params,
		success : function( msg ) {
			var html = '<div class="dish-header" id="'+dishName.replace( " ", "_" )+'" onclick="popupClick()">';
			html += '<div class="cols-dish cols-divine-size">';
			html += '<img class="arrow folding-arrow" id="foldToggleAll" src=""';
			html += 'onclick="foldDish('+dishName.replace( " ", "_" )+')" />';
			html += '</div>';
			html += '<div class="cols-dish cols-divine-size">';
			html += '<input type="checkbox" class="chkbox" id="chk_'+dishName.replace( " ", "_" )+'"';
			html += 'onclick="checkDish('+dishName.replace( " ", "_" )+')" />';
			html += '</div>';
			html += '<div class="cols-dish dish-name">';
			html += '<h4>'+dishName+'</h4>';
			html += '</div>';
			html += '</div>';
			$('#boxTable').append(html);
		}
	} );
}

function popupClick() {
	alert('clicked!');
}
</script>
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
        <ul class="nav navbar-nav" id="table-container">
          <li class="active"><a href="#" id="table"><%=steakTable.getTable()%></a></li>
          <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Tables<b
              class="caret"></b></a>
            <ul class="dropdown-menu">
              <%
              	for ( int i = 0; i < steakTables.size(); i++ ) {
              %>
              <li><a href="#" onclick="changeTable(<%=i%>)" id="table_<%=i%>"><%=steakTables.get( i ).getTable()%></a></li>
              <%
              	}
              %>
            </ul></li>
        </ul>
        <form class="navbar-form navbar-right">
          <input type="button" class="btn btn-success" value="New Box" onclick="insertNewBox()" /> <input type="button"
            class="btn btn-danger" value="Delete Box" onclick="deleteBox()" /> <input type="button"
            class="btn btn-primary" value="New Dish" onclick="insertNewDish()" /> <input type="button"
            class="btn btn-info" value="Share Pipeline" onclick="sharePipeline()" />
        </form>
      </div>
    </div>
  </div>

  <div class="main-container">
    <div class="steak-container">
      <div class="dish-container" id="dishContainer">
        <!-- Dish information -->
        <div class="dish-list">
          <%
          	for ( int i = 0; i < steakDish.size(); i++ ) {
          %>
          <div class="dish dragging" id="dish_<%=steakDish.get( i ).getDish().replaceAll( " ", "_" )%>"
            style="width: <%=100 / steakDish.size()%>%;">
            <font size="7"><%=steakDish.get( i ).getRows().size()%></font><br> <a
              href="#<%=steakDish.get( i ).getDish().replaceAll( " ", "_" )%>"><%=steakDish.get( i ).getDish()%></a>
          </div>
          <%
          	}
          %>
        </div>
      </div>
      <div class="cell-container" id="cellContainer" style="min-height: 100%;">
        <!-- Column header -->
        <div class="cols-header-container" id="colHeaderContainer" style="max-width:<%=tableWidth%>px;">
          <div class="cols cols-header cols-divine-size">
            <img class="arrow folding-arrow" id="foldToggleAll" src="" onclick="foldToggleAll()" />
          </div>
          <div class="cols cols-header cols-divine-size">
            <input type="checkbox" class="chkbox" id="chkToogleAll" onclick="chkToogleAll()" />
          </div>
          <%
          	for ( int i = 0; i < steakTable.getColumns().size(); i++ ) {
          %>
          <div class="cols" style="width:<%=shape.getWidth().get( i )%>px;">
            <div class="cols-inside cols-resize"></div>
            <div class="cols-inside cols-name"><%=steakTable.getColumns().get( i )%></div>
            <div class="cols-inside cols-settings"></div>
          </div>
          <%
          	}
          %>
        </div>
        <!-- Boxs -->
        <div class="box-table" id="boxTable" style="max-width:<%=tableWidth%>px; min-height: 100%;">
          <%
          	steakIndex = 0;
          	for ( int i = 0; i < steakDish.size(); i++ ) {
          %>
          <!-- Dish name -->
          <div class="dish-header" id="<%=steakDish.get( i ).getDish().replaceAll( " ", "_" )%>" onclick="popupClick()">
            <div class="cols-dish cols-divine-size">
              <img class="arrow folding-arrow" id="foldToggleAll" src=""
                onclick="foldDish'<%=steakDish.get( i ).getDish().replaceAll( " ", "_" )%>')" />
            </div>
            <div class="cols-dish cols-divine-size">
              <input type="checkbox" class="chkbox" id="chk_<%=steakDish.get( i ).getDish().replaceAll( " ", "_" )%>"
                onclick="checkDish'<%=steakDish.get( i ).getDish().replaceAll( " ", "_" )%>')" />
            </div>
            <div class="cols-dish dish-name">
              <h4><%=steakDish.get( i ).getDish()%></h4>
            </div>
          </div>
          <!-- Steaks -->
          <%
          	for ( int j = 0; j < steakDish.get( i ).getRows().size(); j++ ) {
          %>
          <div class="rows-container rows-<%=steakDish.get( i ).getDish()%>" style="max-width:<%=tableWidth%>px;">
            <div class="cols cols-divine-size">
              <img src="./favicon.ico" />
            </div>
            <div class="cols cols-divine-size">
              <input type="checkbox" class="checkbox chk-<%=steakDish.get( i ).getDish().replaceAll( " ", "_" )%>"
                onclick="checkToggle('chk-<%=steakDish.get( i ).getDish().replaceAll( " ", "_" )%>')" />
            </div>
            <%
            	for ( int k = 0; k < steakTable.getColumns().size(); k++ ) {
            %>
            <div class="cols" id="cols_cont" onclick="popupClick()">
              <div class="cols-name"><%=steaks.get( steakIndex ).getValues().get( steakIndex )%></div>
            </div>
            <%
            	steakIndex++;
            			}
            %>
            <%
            	}
            %>
            <%
            	}
            %>
          </div>
        </div>
      </div>
      <div class="noti-container">
        <ul style="padding: 0px;">
          <%
          	for ( int i = 0; i < 10; i++ ) {
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
    </div>
  </div>

  <%--
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
            <h4 class="stage-header"><%=steakStage.get( i ).getStage()%></h4>
          </div>
        </div>
        <%
        	for ( int j = 0; j < steakStage.get( i ).getRows().size(); j++ ) {
                			PersistenceManager pm = PMF.get().getPersistenceManager();
                			SteakModel steak = pm.getObjectById( SteakModel.class, steakStage.get( i ).getRows().get( j ) );
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
--%>
  <script src="./js/steak.js"></script>
  <script src="./js/jquery-1.11.1.min.js"></script>
  <script src="./js/bootstrap.min.js"></script>
</body>
</html>