<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inscription - VitaCare</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            padding: 20px 0;
        }
        .register-container {
            background: white;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            overflow: hidden;
            max-width: 600px;
            width: 100%;
            margin: 20px;
        }
        .register-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 40px 30px;
            text-align: center;
        }
        .register-header h1 {
            font-size: 2rem;
            font-weight: 700;
            margin-bottom: 10px;
        }
        .register-header p {
            opacity: 0.9;
            margin: 0;
        }
        .register-body {
            padding: 40px 30px;
        }
        .form-control {
            border-radius: 10px;
            padding: 12px 15px;
            border: 2px solid #e0e0e0;
            transition: all 0.3s;
        }
        .form-control:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
        }
        .btn-register {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            border-radius: 10px;
            padding: 12px;
            font-weight: 600;
            color: white;
            width: 100%;
            transition: transform 0.2s;
        }
        .btn-register:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }
        .alert {
            border-radius: 10px;
            border: none;
        }
        .form-label {
            font-weight: 600;
            color: #333;
            margin-bottom: 8px;
        }
        .login-link {
            text-align: center;
            margin-top: 20px;
            color: #666;
        }
        .login-link a {
            color: #667eea;
            text-decoration: none;
            font-weight: 600;
        }
        .login-link a:hover {
            text-decoration: underline;
        }
        .home-link {
            text-align: center;
            margin-top: 15px;
        }
        .home-link a {
            color: #999;
            text-decoration: none;
            font-size: 0.9rem;
        }
        .home-link a:hover {
            color: #667eea;
        }
        .info-badge {
            background: #f0f4ff;
            border-left: 4px solid #667eea;
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 0.9rem;
            color: #555;
        }
    </style>
</head>
<body>
<div class="register-container">
    <div class="register-header">
        <i class="bi bi-person-plus-fill" style="font-size: 3rem;"></i>
        <h1>VitaCare</h1>
        <p>Créez votre compte patient</p>
    </div>

    <div class="register-body">
        <c:if test="${not empty error}">
            <div class="alert alert-danger d-flex align-items-center" role="alert">
                <i class="bi bi-exclamation-triangle-fill me-2"></i>
                <div>${error}</div>
            </div>
        </c:if>

        <div class="info-badge">
            <i class="bi bi-info-circle me-2"></i>
            Vous vous inscrivez en tant que <strong>Patient</strong>. Remplissez tous les champs pour créer votre compte.
        </div>

        <form method="post" action="${pageContext.request.contextPath}/auth?action=register">
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="firstName" class="form-label">
                        <i class="bi bi-person me-1"></i>Prénom
                    </label>
                    <input type="text"
                           class="form-control"
                           id="firstName"
                           name="firstName"
                           placeholder="Jean"
                           required>
                </div>

                <div class="col-md-6 mb-3">
                    <label for="lastName" class="form-label">
                        <i class="bi bi-person me-1"></i>Nom
                    </label>
                    <input type="text"
                           class="form-control"
                           id="lastName"
                           name="lastName"
                           placeholder="Dupont"
                           required>
                </div>
            </div>

            <div class="mb-3">
                <label for="email" class="form-label">
                    <i class="bi bi-envelope me-1"></i>Email
                </label>
                <input type="email"
                       class="form-control"
                       id="email"
                       name="email"
                       placeholder="jean.dupont@exemple.com"
                       required>
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">
                    <i class="bi bi-lock me-1"></i>Mot de passe
                </label>
                <input type="password"
                       class="form-control"
                       id="password"
                       name="password"
                       placeholder="••••••••"
                       minlength="6"
                       required>
                <small class="text-muted">Minimum 6 caractères</small>
            </div>

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="cin" class="form-label">
                        <i class="bi bi-card-text me-1"></i>CIN
                    </label>
                    <input type="text"
                           class="form-control"
                           id="cin"
                           name="cin"
                           placeholder="AB123456"
                           required>
                </div>

                <div class="col-md-6 mb-3">
                    <label for="phone" class="form-label">
                        <i class="bi bi-telephone me-1"></i>Téléphone
                    </label>
                    <input type="tel"
                           class="form-control"
                           id="phone"
                           name="phone"
                           placeholder="0612345678"
                           required>
                </div>
            </div>

            <div class="mb-4">
                <label for="address" class="form-label">
                    <i class="bi bi-geo-alt me-1"></i>Adresse
                </label>
                <input type="text"
                       class="form-control"
                       id="address"
                       name="address"
                       placeholder="123 Rue de la Santé, Paris"
                       required>
            </div>

            <button type="submit" class="btn btn-register">
                <i class="bi bi-person-check me-2"></i>Créer mon compte
            </button>
        </form>

        <div class="login-link">
            Vous avez déjà un compte ?
            <a href="${pageContext.request.contextPath}/auth?action=login">
                Se connecter
            </a>
        </div>

        <div class="home-link">
            <a href="${pageContext.request.contextPath}/">
                <i class="bi bi-arrow-left me-1"></i>Retour à l'accueil
            </a>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
