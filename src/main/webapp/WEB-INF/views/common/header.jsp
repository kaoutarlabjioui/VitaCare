<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${pageTitle != null ? pageTitle : 'Vita Care'}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">
            <i class="bi bi-hospital"></i> Vita Care
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <c:if test="${sessionScope.user != null}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/dashboard">
                            <i class="bi bi-speedometer2"></i> Tableau de bord
                        </a>
                    </li>

                    <c:if test="${sessionScope.user.role == 'ADMIN'}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                                <i class="bi bi-gear"></i> Administration
                            </a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/patients">Patients</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/doctors">Docteurs</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/departments">Départements</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/specialties">Spécialités</a></li>
                            </ul>
                        </li>
                    </c:if>

                    <c:if test="${sessionScope.user.role == 'DOCTOR' || sessionScope.user.role == 'STAFF' || sessionScope.user.role == 'ADMIN'}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/appointments">
                                <i class="bi bi-calendar-check"></i> Rendez-vous
                            </a>
                        </li>
                    </c:if>

                    <c:if test="${sessionScope.user.role == 'DOCTOR'}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/availability">
                                <i class="bi bi-clock"></i> Disponibilités
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/notes">
                                <i class="bi bi-file-medical"></i> Notes médicales
                            </a>
                        </li>
                    </c:if>
                </c:if>
            </ul>

            <ul class="navbar-nav">
                <c:if test="${sessionScope.user != null}">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                            <i class="bi bi-person-circle"></i> ${sessionScope.user.firstName} ${sessionScope.user.lastName}
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile">Mon profil</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/change-password">Changer mot de passe</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/auth/logout">Déconnexion</a></li>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${sessionScope.user == null}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/auth/login">Connexion</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>

<main class="container-fluid py-4">
    <c:if test="${not empty successMessage}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <i class="bi bi-check-circle"></i> ${successMessage}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
    </c:if>

    <c:if test="${not empty errorMessage}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <i class="bi bi-exclamation-triangle"></i> ${errorMessage}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
    </c:if>
