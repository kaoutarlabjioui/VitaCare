<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inscription - VitaCare</title>

    <!-- Bootstrap & Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">

    <style>
        /* 🌈 Arrière-plan général */
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            padding: 20px 0;
        }

        /* 💎 Conteneur principal */
        .register-container {
            background: white;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            overflow: hidden;
            max-width: 600px;
            width: 100%;
            margin: 20px;
            animation: fadeInUp 0.6s ease;
        }

        /* 🎬 Animation d'apparition */
        @keyframes fadeInUp {
            from {
                transform: translateY(30px);
                opacity: 0;
            }
            to {
                transform: translateY(0);
                opacity: 1;
            }
        }

        /* 🧩 En-tête */
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

        /* 📄 Corps */
        .register-body {
            padding: 40px 30px;
        }

        /* 🧠 Champs du formulaire */
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

        /* 🎯 Bouton principal */
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

        /* ⚠️ Messages d'erreur */
        .alert {
            border-radius: 10px;
            border: none;
        }

        /* 🏷️ Labels */
        .form-label {
            font-weight: 600;
            color: #333;
            margin-bottom: 8px;
        }

        /* 🔗 Lien vers la connexion */
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

        /* 📚 Section du formulaire */
        .form-section {
            margin-bottom: 25px;
        }
        .form-section-title {
            font-size: 1.1rem;
            font-weight: 700;
            color: #667eea;
            margin-bottom: 15px;
            padding-bottom: 10px;
            border-bottom: 2px solid #f0f0f0;
        }

        /* 📱 Responsive */
        @media (max-width: 576px) {
            .register-body {
                padding: 25px 20px;
            }
        }
    </style>
</head>

<body>
<div class="register-container">
    <div class="register-header">
        <img src="${pageContext.request.contextPath}/assets/img/logo.png" alt="VitaCare" width="70" class="mb-3">
        <h1><i class="bi bi-person-plus-fill me-2"></i>Créer un compte</h1>
        <p>Rejoignez VitaCare en tant que patient</p>
    </div>

    <div class="register-body">
        <!-- 🔔 Message d'erreur -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger d-flex align-items-center" role="alert">
                <i class="bi bi-exclamation-triangle-fill me-2"></i>
                <div>${error}</div>
            </div>
        </c:if>

        <!-- 📝 Formulaire d'inscription -->
        <form method="post" action="${pageContext.request.contextPath}/auth?action=register">

            <!-- Section Connexion -->
            <div class="form-section">
                <div class="form-section-title">
                    <i class="bi bi-shield-lock me-2"></i>Informations de connexion
                </div>

                <div class="mb-3">
                    <label for="email" class="form-label">
                        <i class="bi bi-envelope me-1"></i>Email
                    </label>
                    <input type="email" class="form-control" id="email" name="email"
                           placeholder="votre.email@exemple.com" required>
                </div>

                <div class="mb-3">
                    <label for="password" class="form-label">
                        <i class="bi bi-lock me-1"></i>Mot de passe
                    </label>
                    <input type="password" class="form-control" id="password" name="password"
                           placeholder="••••••••" minlength="6" required>
                    <small class="text-muted">Minimum 6 caractères</small>
                </div>
            </div>

            <!-- Section Informations personnelles -->
            <div class="form-section">
                <div class="form-section-title">
                    <i class="bi bi-person me-2"></i>Informations personnelles
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="firstName" class="form-label">Prénom</label>
                        <input type="text" class="form-control" id="firstName" name="firstName"
                               placeholder="Jean" required>
                    </div>

                    <div class="col-md-6 mb-3">
                        <label for="lastName" class="form-label">Nom</label>
                        <input type="text" class="form-control" id="lastName" name="lastName"
                               placeholder="Dupont" required>
                    </div>
                </div>

                <div class="mb-3">
                    <label for="cin" class="form-label">
                        <i class="bi bi-card-text me-1"></i>CIN
                    </label>
                    <input type="text" class="form-control" id="cin" name="cin"
                           placeholder="AB123456" required>
                </div>
            </div>

            <!-- Section Coordonnées -->
            <div class="form-section">
                <div class="form-section-title">
                    <i class="bi bi-geo-alt me-2"></i>Coordonnées
                </div>

                <div class="mb-3">
                    <label for="phone" class="form-label">
                        <i class="bi bi-telephone me-1"></i>Téléphone
                    </label>
                    <input type="tel" class="form-control" id="phone" name="phone"
                           placeholder="0612345678" required>
                </div>

                <div class="mb-4">
                    <label for="address" class="form-label">
                        <i class="bi bi-house me-1"></i>Adresse
                    </label>
                    <textarea class="form-control" id="address" name="address" rows="2"
                              placeholder="123 Rue de la Santé, 75000 Paris" required></textarea>
                </div>
            </div>

            <!-- Bouton -->
            <button type="submit" class="btn btn-register">
                <i class="bi bi-check-circle me-2"></i>Créer mon compte
            </button>
        </form>

        <!-- Lien connexion -->
        <div class="login-link">
            Vous avez déjà un compte ?
            <a href="${pageContext.request.contextPath}/auth?action=login">
                Se connecter
            </a>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
