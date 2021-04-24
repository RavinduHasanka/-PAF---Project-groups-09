<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.item"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="Components/items.js"></script>

<title>Insert title here</title>
</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col-8">

				<h1 class="m-3">Student details</h1>

				<form id="formItem" name="formItem" method="post" action="Index.jsp">

					Item code: <input id="itemCode" name="itemCode" type="text"
						class="form-control form-control-sm"><br> Item name:
					<input id="itemName" name="itemName" type="text"
						class="form-control form-control-sm"> <br> Item
					price: <input id="itemPrice" name="itemPrice" type="text"
						class="form-control form-control-sm"> <br> Item
					description: <input id="itemDesc" name="itemDesc" type="text"
						class="form-control form-control-sm"> <br> <input
						id="btnSave" name="btnSave" type="submit" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidItemIDSave" name="hidItemIDSave" value="">

				</form>

			</div>
		</div>

		<br>

		<div class="row">
			<div class="col-12" id="colStudents"></div>
		</div>
	</div>



</body>
<%
item ob1 = new item();
%>
<%
String stsMsg = "";

//Save---------------------------------
if (request.getParameter("itemCode") != null) {
	item itemObj = new item();

	System.out.println("Function Save First Bound");
	//Insert--------------------------
	if (request.getParameter("hidItemIDSave") == "") {
		System.out.println("Button Save");
		stsMsg = itemObj.insertItem(request.getParameter("itemCode"), request.getParameter("itemName"),
		request.getParameter("itemPrice"), request.getParameter("itemDesc"));
		session.setAttribute("statusMsg", stsMsg);
	}
	else//Update----------------------
	 { 
		System.out.println("Updateb call");
		 stsMsg = itemObj.updateItem(request.getParameter("hidItemIDDelete"), 
		 request.getParameter("itemCode"), 
		 request.getParameter("itemName"), 
		 request.getParameter("itemPrice"), 
		 request.getParameter("itemDesc")); 
	 } 
	 session.setAttribute("statusMsg", stsMsg);
}

//Delete-----------------------------
if (request.getParameter("hidItemIDDelete") != null) {
	System.out.println("Button Delete");
	item itemObj = new item();

	stsMsg = itemObj.deleteItem(request.getParameter("hidItemIDDelete"));

	session.setAttribute("statusMsg", stsMsg);
}

out.print(session.getAttribute("statusMsg"));
stsMsg = "";
%>
<%
out.print(ob1.selectItem());
%>
</html>