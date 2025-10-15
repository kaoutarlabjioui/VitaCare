<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Admin - VitaCare</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        :root {
            --primary-color: #6366f1;
            --secondary-color: #8b5cf6;
            --success-color: #10b981;
            --warning-color: #f59e0b;
            --danger-color: #ef4444;
            --info-color: #3b82f6;
        }

        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .sidebar {
            background: white;
            min-height: 100vh;
            box-shadow: 2px 0 10px rgba(0,0,0,0.1);
            position: fixed;
            width: 250px;
            left: 0;
            top: 0;
            z-index: 1000;
        }

        .sidebar .logo {
            padding: 1.5rem;
            border-bottom: 1px solid #e5e7eb;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }

        .sidebar .nav-link {
            color: #4b5563;
            padding: 0.75rem 1.5rem;
            border-left: 3px solid transparent;
            transition: all 0.3s;
        }

        .sidebar .nav-link:hover,
        .sidebar .nav-link.active {
            background: #f3f4f6;
            border-left-color: var(--primary-color);
            color: var(--primary-color);
        }

        .main-content {
            margin-left: 250px;
            padding: 2rem;
        }

        .stat-card {
            background: white;
            border-radius: 12px;
            padding: 1.5rem;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            transition: transform 0.3s, box-shadow 0.3s;
            border-left: 4px solid;
        }

        .stat-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 15px rgba(0,0,0,0.2);
        }

        .stat-card.primary { border-left-color: var(--primary-color); }
        .stat-card.success { border-left-color: var(--success-color); }
        .stat-card.warning { border-left-color: var(--warning-color); }
        .stat-card.info { border-left-color: var(--info-color); }

        .stat-icon {
            width: 60px;
            height: 60px;
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.5rem;
        }

        .stat-icon.primary { background: rgba(99, 102, 241, 0.1); color: var(--primary-color); }
        .stat-icon.success { background: rgba(16, 185, 129, 0.1); color: var(--success-color); }
        .stat-icon.warning { background: rgba(245, 158, 11, 0.1); color: var(--warning-color); }
        .stat-icon.info { background: rgba(59, 130, 246, 0.1); color: var(--info-color); }

        .quick-action-card {
            background: white;
            border-radius: 12px;
            padding: 1.5rem;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            text-align: center;
            transition: all 0.3s;
            cursor: pointer;
            text-decoration: none;
            color: inherit;
            display: block;
        }

        .quick-action-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 15px rgba(0,0,0,0.2);
            color: inherit;
        }

        .quick-action-icon {
            width: 80px;
            height: 80px;
            border-radius: 50%;
            margin: 0 auto 1rem;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 2rem;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }

        .page-header {
            background: white;
            border-radius: 12px;
            padding: 1.5rem;
            margin-bottom: 2rem;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }

        .user-info {
            padding: 1rem 1.5rem;
            border-top: 1px solid #e5e7eb;
            margin-top: auto;
        }

        .user-avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="sidebar d-flex flex-column">
    <div class="logo">
        <h4 class="mb-0"><i class="bi bi-heart-pulse-fill"></i> VitaCare</h4>
        <small>Administration</small>
    </div>

    <nav class="flex-grow-1">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link active" href="${pageContext.request.contextPath}/admin/dashboard">
                    <i class="bi bi-speedometer2 me-2"></i> Dashboard
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/departments/">
                    <i class="bi bi-building me-2"></i> Départements
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/doctors/">
                    <i class="bi bi-person-badge me-2"></i> Médecins
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/patients/">
                    <i class="bi bi-people me-2"></i> Patients
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/appointments/">
                    <i class="bi bi-calendar-check me-2"></i> Rendez-vous
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/speciality?action=list">
                    <i class="bi bi-star me-2"></i> Spécialités
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/users/">
                    <i class="bi bi-person-gear me-2"></i> Utilisateurs
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/users">
                    <i class="bi bi-person-gear me-2"></i> Créer Utilisateur
                </a>
            </li>
        </ul>
    </nav>

    <div class="user-info d-flex align-items-center">
        <div class="user-avatar me-3">
            <c:choose>
                <c:when test="${not empty sessionScope.user.firstName}">
                    ${sessionScope.user.firstName.substring(0,1).toUpperCase()}
                </c:when>
                <c:otherwise>A</c:otherwise>
            </c:choose>
        </div>
        <div class="flex-grow-1">
            <div class="fw-bold">
                <c:choose>
                    <c:when test="${not empty sessionScope.user.firstName}">
                        ${sessionScope.user.firstName}
                    </c:when>
                    <c:otherwise>Admin</c:otherwise>
                </c:choose>
            </div>
            <small class="text-muted">Administrateur</small>
        </div>
        <a href="${pageContext.request.contextPath}/auth?action=logout" class="btn btn-sm btn-outline-danger">
            <i class="bi bi-box-arrow-right"></i>
        </a>
    </div>
</div>

<div class="main-content">

    <div class="page-header">
        <div class="d-flex justify-content-between align-items-center">
            <div>
                <h2 class="mb-1">Tableau de Bord</h2>
                <p class="text-muted mb-0">Bienvenue sur votre espace d'administration VitaCare</p>
            </div>
            <div class="text-muted">
                <i class="bi bi-calendar3"></i>
                <span id="currentDate"></span>
            </div>
        </div>
    </div>

    <div class="row g-4 mb-4">
        <div class="col-md-3">
            <div class="stat-card primary">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <p class="text-muted mb-1">Total Patients</p>
                        <h3 class="mb-0">1,234</h3>
                        <small class="text-success"><i class="bi bi-arrow-up"></i> +12% ce mois</small>
                    </div>
                    <div class="stat-icon primary">
                        <i class="bi bi-people-fill"></i>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="stat-card success">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <p class="text-muted mb-1">Médecins</p>
                        <h3 class="mb-0">87</h3>
                        <small class="text-success"><i class="bi bi-arrow-up"></i> +5 nouveaux</small>
                    </div>
                    <div class="stat-icon success">
                        <i class="bi bi-person-badge-fill"></i>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="stat-card warning">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <p class="text-muted mb-1">Rendez-vous</p>
                        <h3 class="mb-0">342</h3>
                        <small class="text-warning"><i class="bi bi-clock"></i> 28 aujourd'hui</small>
                    </div>
                    <div class="stat-icon warning">
                        <i class="bi bi-calendar-check-fill"></i>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="stat-card info">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <p class="text-muted mb-1">Départements</p>
                        <h3 class="mb-0">12</h3>
                        <small class="text-info"><i class="bi bi-building"></i> Actifs</small>
                    </div>
                    <div class="stat-icon info">
                        <i class="bi bi-building-fill"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row g-4 mb-4">
        <div class="col-12">
            <h4 class="mb-3">Actions Rapides</h4>
        </div>

        <div class="col-md-3">
            <a href="${pageContext.request.contextPath}/departments/create" class="quick-action-card">
                <div class="quick-action-icon">
                    <i class="bi bi-plus-circle"></i>
                </div>
                <h5>Nouveau Département</h5>
                <p class="text-muted mb-0">Créer un département</p>
            </a>
        </div>

        <div class="col-md-3">
            <a href="${pageContext.request.contextPath}/doctors/create" class="quick-action-card">
                <div class="quick-action-icon">
                    <i class="bi bi-person-plus"></i>
                </div>
                <h5>Ajouter Médecin</h5>
                <p class="text-muted mb-0">Enregistrer un médecin</p>
            </a>
        </div>

        <div class="col-md-3">
            <a href="${pageContext.request.contextPath}/patients/create" class="quick-action-card">
                <div class="quick-action-icon">
                    <i class="bi bi-person-plus-fill"></i>
                </div>
                <h5>Nouveau Patient</h5>
                <p class="text-muted mb-0">Enregistrer un patient</p>
            </a>
        </div>

        <div class="col-md-3">
            <a href="${pageContext.request.contextPath}/appointments/create" class="quick-action-card">
                <div class="quick-action-icon">
                    <i class="bi bi-calendar-plus"></i>
                </div>
                <h5>Rendez-vous</h5>
                <p class="text-muted mb-0">Planifier un RDV</p>
            </a>
        </div>

        <div class="col-md-3">
            <a href="${pageContext.request.contextPath}/admin/users" class="quick-action-card">
                <div class="quick-action-icon">
                    <i class="bi bi-person-plus-fill"></i>
                </div>
                <h5>Créer Utilisateur</h5>
                <p class="text-muted mb-0">Admin ou Personnel</p>
            </a>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <div class="stat-card">
                <h5 class="mb-3"><i class="bi bi-clock-history me-2"></i>Activité Récente</h5>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>Date</th>
                            <th>Action</th>
                            <th>Utilisateur</th>
                            <th>Statut</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td><small class="text-muted">Il y a 5 min</small></td>
                            <td>Nouveau patient enregistré</td>
                            <td>Dr. Martin</td>
                            <td><span class="badge bg-success">Complété</span></td>
                        </tr>
                        <tr>
                            <td><small class="text-muted">Il y a 12 min</small></td>
                            <td>Rendez-vous confirmé</td>
                            <td>Réceptionniste</td>
                            <td><span class="badge bg-info">Confirmé</span></td>
                        </tr>
                        <tr>
                            <td><small class="text-muted">Il y a 25 min</small></td>
                            <td>Département mis à jour</td>
                            <td>Admin</td>
                            <td><span class="badge bg-warning">Modifié</span></td>
                        </tr>
                        <tr>
                            <td><small class="text-muted">Il y a 1 heure</small></td>
                            <td>Nouveau médecin ajouté</td>
                            <td>Admin</td>
                            <td><span class="badge bg-success">Complété</span></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>

    const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
    document.getElementById('currentDate').textContent = new Date().toLocaleDateString('fr-FR', options);
</script>
</body>
</html>
