<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.servletContext.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" method="post"
					action="${ pageContext.request.contextPath }/board?page=${ page }">
					<input type="text" id="kwd" name="keyword" value=""> 
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
				
					<c:forEach items="${ list }" step="1" var="BoardVo" varStatus="status">
						<tr>
							<td>${ status.count + startIndex }</td>
							<td
								style="text-align:left; padding-left:${ BoardVo.depth * 20}px">
								<c:if test="${ BoardVo.depth >= 1 }">
									<img src='/mysite03/assets/images/reply.png'>
								</c:if> <c:choose>
									<c:when test="${ BoardVo.regDate eq '0002-11-30 12:00:00.0' }">
										<a>(삭제된 게시물입니다.)</a>
										<td></td>
										<td></td>
										<td></td>
									</c:when>
									<c:otherwise>
										<a
											href="${ pageContext.request.contextPath }/board/view/${ BoardVo.no }">${ BoardVo.title }</a>
										<td>${ BoardVo.name }</td>
										<td>${ BoardVo.hit }</td>
										<td>${ BoardVo.regDate }</td>
										<c:if
											test='${ (BoardVo.userNo eq authUser.no) and (not empty authUser)}'>
											<td><a
												href="${ pageContext.request.contextPath }/board/delete/${ BoardVo.no }"
												class="del">삭제</a></td>
										</c:if>
									</c:otherwise>
								</c:choose>
							</td>
							<!-- 제목 </td> -->
						</tr>
					</c:forEach>

				</table>

				<div class="pager">
					<ul>

						<c:choose>
							<c:when test="${ page eq 1 }">
								<li><a href="" style="color: lightgrey">◀</a></li>
							</c:when>
							<c:otherwise>
								<li><a		
									href="${ pageContext.request.contextPath }/board?page=${ page - 1 }&keyword=${ keyword }">◀</a></li>
							</c:otherwise>
						</c:choose>

						<c:forEach var="no" begin="${ firstPageNo }" end="${ lastPageNo }" step="1">
							<c:choose>
								<c:when test="${ page eq no }">
									<li class="selected">${ no }</li>
								</c:when>

								<c:otherwise>
									<li><a
										href="${ pageContext.request.contextPath }/board?page=${ no }&keyword=${ keyword }">${ no }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>

						<c:choose>
							<c:when test="${ page eq pageLen }">
								<li><a href="" style="color: lightgrey">▶</a></li>
							</c:when>
							<c:otherwise>
								<li><a
									href="${ pageContext.request.contextPath }/board?page=${ page + 1 }&keyword=${ keyword }">▶</a></li>
							</c:otherwise>
						</c:choose>

					</ul>
				</div>



				<c:if test="${ not empty authUser }">
					<div class="bottom">
						<a href="${ pageContext.request.contextPath }/board/write"
							id="new-book">글쓰기</a>
					</div>
				</c:if>

			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>