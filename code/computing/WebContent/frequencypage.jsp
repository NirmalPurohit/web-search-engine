<!--
--  @author Nirmal Purohit
--  Help for GUI was taken form Yash Mistry email: yashmistry1995@gmail.com
-->



<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="limiter">
	<div class="wrap-table100">
		<div class="table100 ver3 m-b-110">
			<div class="table100-head">
				<table>
					<thead>
						<tr class="row100 head">
                                                    <c:set var = "count" value="${counter}"/>
                                                    <c:set var = "time" value="${time}"/>
							<th class="cell100 column1">Number  of  Result  found: ${counter} in    ${time} seconds</th>
						</tr>
					</thead>
				</table>
			</div>

			<div class="table100-body js-pscroll">
				<table>
					<tbody>
						<c:choose>
							<c:when test="${not empty listOfResult}">
								<c:forEach items="${listOfResult}" var="entry">
									<c:choose>
										<c:when test="${entry.value != 0}">
											<tr class="row100 body">
												<td class="cell100 column1">${entry.key}</td>
											</tr>
										</c:when>
									</c:choose>
								</c:forEach>
							</c:when>
							<c:when test="${empty listOfResult}">
								<tr class="row100 body">
									<td class="cell100 column1" colspan="2">No Rows Found</td>
								</tr>
							</c:when>
						</c:choose>
					</tbody>
				</table>
			</div>

		</div>
	</div>
</div>

