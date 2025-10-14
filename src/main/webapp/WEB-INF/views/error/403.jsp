<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="pageTitle" value="Accès refusé" scope="request"/>
<jsp:include page="../common/header.jsp"/>

<div class="row justify-content-center">
    <div class="col-md-6 text-center">
        <div class="card shadow">
            <div class="card-body p-5">
                <i class="bi bi-shield-x text-danger" style="font-size: 5rem;"></i>
                <h1 class="display-1 mt-3">403</h1>
                <h3 class="mb-3">Accès refusé</h3>
                <p class="text-muted mb-4">
                    Vous n'avez pas les permissions nécessaires pour accéder à cette page.
                </p>
                <a href="${pageContext.request.contextPath}/" class="btn btn-primary">
                    <i class="bi bi-house"></i> Retour à l'accueil
                </a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
