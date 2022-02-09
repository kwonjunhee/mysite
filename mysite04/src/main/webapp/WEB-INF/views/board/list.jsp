<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath}/board" method="get">
					<input type="text" id="kwd" name="kwd" value="${param.kwd}">
					<input type="hidden" id="page" name="page" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>				
					<c:set var="count" value="${fn:length(list)}" />
					
						<c:forEach items="${list}" var="boardvo" varStatus="status">
						<tr>
							<td>[${page.boardcnt-status.index}]</td>
							
							<td style="text-align:left, padding-left:${(boardvo.depth-1)*20}px">
										<c:if test = "${boardvo.depth>1}">
											<img src="${pageContext.servletContext.contextPath}/assets/images/reply.png"/>
										</c:if>
							<c:choose>
								<c:when test='${empty authUser}'>
									
									${boardvo.title}
								</c:when>
								<c:otherwise>
									
									<a href="${pageContext.servletContext.contextPath}/board/view/${boardvo.no}?page=${param.page}&kwd=${param.kwd}">${boardvo.title}</a>
								</c:otherwise>
							</c:choose>

							<td>${boardvo.userName}</td>
							<td>${boardvo.hit}</td>
							<td>${boardvo.regDate}</td>
						<c:if test='${boardvo.userNo eq authUser.no}'>
						
							<td><a href="${pageContext.servletContext.contextPath}/board/delete/${boardvo.no}" class="del" 
							style='background-image:url("${pageContext.servletContext.contextPath}/assets/images/recycle.png")'>삭제</a></td>
						</c:if>
						</tr>
						</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						
						<c:choose>
							<c:when test='${page.currentpage==1 }'>
								<c:forEach var="pagenum" begin="1" end="${page.listcnt}">
									<li><a href="${pageContext.servletContext.contextPath}/board?page=${pagenum}&kwd=${param.kwd}">${pagenum}</a></li>														
								</c:forEach>
								<li><a href="${pageContext.servletContext.contextPath}/board?page=${page.currentpage+page.nextpage}&kwd=${param.kwd}">▶</a></li>
								
							</c:when>
							
							<c:when test='${page.currentpage==page.listcnt}'>
							<li><a href="${pageContext.servletContext.contextPath}/board?page=${page.currentpage+page.prepage}&kwd=${param.kwd}">◀</a></li>
							
								<c:forEach var="pagenum" begin="1" end="${page.listcnt}">
									<li><a href="${pageContext.servletContext.contextPath}/board?page=${pagenum}&kwd=${param.kwd}">${pagenum}</a></li>														
								</c:forEach>
							</c:when>
							
							<c:otherwise>
								<li><a href="${pageContext.servletContext.contextPath}/board?page=${page.currentpage+page.prepage}&kwd=${param.kwd}">◀</a></li>
								<c:forEach var="pagenum" begin="1" end="${page.listcnt}">
									<li><a href="${pageContext.servletContext.contextPath}/board?page=${pagenum}&kwd=${param.kwd}">${pagenum}</a></li>					
								</c:forEach>
								<li><a href="${pageContext.servletContext.contextPath}/board?page=${page.currentpage+page.nextpage}&kwd=${param.kwd}">▶</a></li>
								
							</c:otherwise>
						</c:choose>
						
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				
				<c:if test='${!empty authUser}'>
					<div class="bottom">
						<a href="${pageContext.servletContext.contextPath}/board/write" id="new-book">글쓰기</a>
					</div>
				</c:if>
								
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>