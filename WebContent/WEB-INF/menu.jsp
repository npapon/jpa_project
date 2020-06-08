
        <c:forEach var="entry" items="${menu}">     
    <a href="<c:url value="${entry.item}"/>" title="<c:out value="${entry.description}"/>"><c:out value="${entry.item}"/></a>
       <br/>
        </c:forEach>