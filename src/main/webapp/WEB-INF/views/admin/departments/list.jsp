<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Départements - VitaCare</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        :root {
            --primary-color: #6366f1;
            --primary-dark: #4f46e5;
            --secondary-color: #8b5cf6;
        }

        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .admin-container {
            padding: 2rem 0;
        }

        .card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
        }

        .card-header {
            background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
            color: white;
            border-radius: 15px 15px 0 0 !important;
            padding: 1.5rem;
        }

        .btn-primary {
            background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
            border: none;
            padding: 0.6rem 1.5rem;
            border-radius: 8px;
            transition: transform 0.2s;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(99, 102, 241, 0.4);
        }

        .table {
            margin-bottom: 0;
        }

        .table thead th {
            background-color: #f8f9fa;
            border-bottom: 2px solid var(--primary-color);
            color: #495057;
            font-weight: 600;
            text-transform: uppercase;
            font-size: 0.85rem;
            letter-spacing: 0.5px;
        }

        .table tbody tr {
            transition: all 0.3s ease;
        }

        .table tbody tr:hover {
            background-color: #f8f9ff;
            transform: scale(1.01);
        }

        .badge {
            padding: 0.5rem 1rem;
            border-radius: 20px;
            font-weight: 500;
        }

        .btn-action {
            padding: 0.4rem 0.8rem;
            border-radius: 6px;
            font-size: 0.875rem;
            transition: all 0.2s;
        }

        .btn-action:hover {
            transform: translateY(-2px);
        }

        .search-box {
            border-radius: 10px;
            border: 2px solid #e9ecef;
            padding: 0.6rem 1rem;
            transition: all 0.3s;
        }

        .search-box:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 0.2rem rgba(99, 102, 241, 0.25);
        }

        .empty-state {
            padding: 3rem;
            text-align: center;
            color: #6c757d;
        }

        .empty-state i {
            font-size: 4rem;
            color: #dee2e6;
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>
<div class="container admin-container">
    <div class="card">
        <div class="card-header">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h3 class="mb-1"><i class="bi bi-building"></i> Gestion des Départements</h3>
                    <p class="mb-0 opacity-75">Gérez tous les départements de la clinique</p>
                </div>
                <a href="${pageContext.request.contextPath}/departments/create" class="btn btn-light">
                    <i class="bi bi-plus-circle"></i> Nouveau Département
                </a>
            </div>
        </div>
        <div class="card-body p-0">
            <div class="p-4 border-bottom">
                <form method="get" action="${pageContext.request.contextPath}/departments" class="row g-3">
                    <div class="col-md-10">
                        <input type="text"
                               name="search"
                               class="form-control search-box"
                               placeholder="Rechercher un département par nom..."
                               value="${param.search}">
                    </div>
                    <div class="col-md-2">
                        <button type="submit" class="btn btn-primary w-100">
                            <i class="bi bi-search"></i> Rechercher
                        </button>
                    </div>
                </form>
            </div>
            <c:choose>
                <c:when test="${not empty departments}">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>Code</th>
                                <th>Nom</th>
                                <th>Description</th>
                                <th class="text-center">Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="dept" items="${departments}">
                                <tr>
                                    <td>
                                        <span class="badge bg-primary">${dept.code}</span>
                                    </td>
                                    <td>
                                        <strong>${dept.name}</strong>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty dept.description}">
                                                ${dept.description}
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted fst-italic">Aucune description</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-center">
                                        <a href="${pageContext.request.contextPath}/departments/edit?id=${dept.id}"
                                           class="btn btn-sm btn-outline-primary btn-action me-2">
                                            <i class="bi bi-pencil"></i> Modifier
                                        </a>
                                        <a href="${pageContext.request.contextPath}/departments/delete?id=${dept.id}"
                                           class="btn btn-sm btn-outline-danger btn-action"
                                           onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce département ?')">
                                            <i class="bi bi-trash"></i> Supprimer
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="p-3 border-top bg-light">
                        <small class="text-muted">
                            <i class="bi bi-info-circle"></i>
                            Total: <strong>${departments.size()}</strong> département(s)
                        </small>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="empty-state">
                        <i class="bi bi-inbox"></i>
                        <h5>Aucun département trouvé</h5>
                        <p class="text-muted">
                            <c:choose>
                                <c:when test="${not empty param.search}">
                                    Aucun résultat pour "${param.search}"
                                </c:when>
                                <c:otherwise>
                                    Commencez par créer votre premier département
                                </c:otherwise>
                            </c:choose>
                        </p>
                        <a href="${pageContext.request.contextPath}/departments/create" class="btn btn-primary mt-3">
                            <i class="bi bi-plus-circle"></i> Créer un département
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="text-center mt-4">
        <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-light">
            <i class="bi bi-arrow-left"></i> Retour au tableau de bord
        </a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
