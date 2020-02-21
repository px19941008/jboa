<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link href="/jboa/css/style.css" rel="stylesheet" type="text/css">
<body>
<div class="action  divaction">
		<div class="t">月度统计列表</div>
		<div class="pages">
			<form id="claimVoucherStatistics_getDeptStatisticsByMonth_action"
				name="queryForm"
				action="/jboa/c/Tbstatus/monthList?pageNum=${PAGE.pageNum }&pageSize=${PAGE.pageSize }"
				method="get">
				<label for="time">年份</label> <select name="year"
					id="claimVoucherStatistics_getDeptStatisticsByMonth_action_year">
					<option value="0">无</option>
					<c:forEach begin="2013" end="2020" var="i">
						<c:choose>
							<c:when test="${year == i }">
								<option selected value="${i }">${i }年</option>
							</c:when>
							<c:otherwise>
								<option value="${i }">${i }年</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> <label for="time">开始月份</label> <select name="startMonth"
					id="claimVoucherStatistics_getDeptStatisticsByMonth_action_startMonth">
					<option value="0">无</option>
					<c:forEach begin="1" end="12" var="i">
						<c:choose>
							<c:when test="${startMonth == i }">
								<option selected value="${i }">${i }月份</option>
							</c:when>
							<c:otherwise>
								<option value="${i }">${i }月份</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> <label for="end-time">结束月份</label> <select name="endMonth"
					id="claimVoucherStatistics_getDeptStatisticsByMonth_action_endMonth">
					<option value="0">无</option>
					<c:forEach begin="1" end="12" var="i">
						<c:choose>
							<c:when test="${endMonth == i }">
								<option selected value="${i }">${i }月份</option>
							</c:when>
							<c:otherwise>
								<option value="${i }">${i }月份</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> 
				<!-- <input type="hidden" name="year" value="2013"/> -->
				<input type="submit"
					id="claimVoucherStatistics_getDeptStatisticsByMonth_action_0"
					value="查询" class="submit_01">
			</form>
			<table width="90%" border="0" cellspacing="0" cellpadding="0"
				class="list items">
				<thead>
					<tr class="even">
						<td>编号</td>
						<td>总计</td>
						<td>年份</td>
						<td>月份</td>
						<c:if test="${emp.positionid == 1 }">
							<td>部门</td>
						</c:if>
						<td>操作</td>
					</tr>
				</thead>
				<tbody>
					 <c:forEach items="${PAGE.list }" var="temp" varStatus="status">
						<tr>
						    <td>${status.count }</td> 
						    <td>￥${temp.money }</td> 
							<td>${temp.year }年</td>
							<td>${temp.month }月</td>
							  <c:if test="${emp.positionid == 1 }">
								<td>${temp.departmentName }</td>
							</c:if>   
							<td><a target="rightFrame" href=""> 
									<img src="images/search.gif" width="16" height="15">
							</a></td>
						</tr>
					</c:forEach>  
				</tbody>
				<tr>
					<td colspan="7" align="center">
						<div class="page-bar">

							<a href="/jboa/statistics/monthList?year=${year }&startMonth=${startMonth}&endMonth=${endMonth}&pageNum=1&pageSize=${PAGE.pageSize}">首页</a>
							&nbsp;&nbsp;
							<c:if test="${PAGE.isHasPreviousPage()}">
								<a href="/jboa/statistics/monthList?year=${year }&startMonth=${startMonth}&endMonth=${endMonth}&pageNum=${PAGE.prePage}&pageSize=${PAGE.pageSize}">上一页</a>
							</c:if>
							&nbsp;&nbsp;
							<c:if test="${PAGE.isHasNextPage()}">
								<a href="/jboa/statistics/monthList?year=${year }&startMonth=${startMonth}&endMonth=${endMonth}&pageNum=${PAGE.nextPage}&pageSize=${PAGE.pageSize}">下一页</a>
							</c:if>
							&nbsp;&nbsp; <a href="/jboa/statistics/monthList?year=${year }&startMonth=${startMonth}&endMonth=${endMonth}&pageNum=${PAGE.lastPage}&pageSize=${PAGE.pageSize}">末页</a>
							

							&nbsp;&nbsp; &nbsp;&nbsp;第 ${PAGE.pageNum}页/共${PAGE.lastPage}页&nbsp;&nbsp;共${PAGE.total}条记录
						
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
<script type="text/javascript" src="/jboa/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript">
</script>
</html>