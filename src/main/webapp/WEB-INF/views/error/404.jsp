<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="pageTitle" value="Page non trouvée" scope="request"/>
<jsp:include page="../common/header.jsp"/>

<div class="row justify-content-center">
    <div class="col-md-6 text-center">
        <div class="card shadow">
            <div class="card-body p-5">
                <i class="bi bi-exclamation-triangle text-warning" style="font-size: 5rem;"></i>
                <h1 class="display-1 mt-3">404</h1>
                <h3 class="mb-3">Page non trouvée</h3>
                <p class="text-muted mb-4">
                    Désolé, la page que vous recherchez n'existe pas ou a été déplacée.
                </p>
                <a href="${pageContext.request.contextPath}/" class="btn btn-primary">
                    <i class="bi bi-house"></i> Retour à l'accueil
                </a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
