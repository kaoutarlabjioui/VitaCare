<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Créer Utilisateur - VitaCare</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 2rem 0;
        }
        .form-container {
            max-width: 800px;
            margin: 0 auto;
        }
        .card {
            border-radius: 20px;
            overflow: hidden;
            box-shadow: 0 15px 50px rgba(0,0,0,0.25);
        }
        .card-header {
            background: linear-gradient(135deg, #6366f1, #8b5cf6);
            color: white;
            text-align: center;
            padding: 2rem;
        }
        .conditional-fields { display: none; }
        .conditional-fields.active { display: block; }
    </style>
</head>
<body>
<div class="container">
    <div class="form-container">
        <div class="card">
            <div class="card-header">
                <h3><i class="bi bi-person-plus-fill"></i> Créer un Utilisateur</h3>
            </div>

            <div class="card-body p-4">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">
                        <i class="bi bi-exclamation-triangle-fill"></i> ${error}
                    </div>
                </c:if>

                <form method="post" action="${pageContext.request.contextPath}/admin/users" id="userForm">

                    <!-- Sélecteur du type d'utilisateur -->
                    <div class="mb-4 text-center">
                        <div class="btn-group" role="group">
<%--                            <input type="radio" class="btn-check" name="userType" id="typeAdmin" value="ADMIN" required>--%>
<%--                            <label class="btn btn-outline-primary" for="typeAdmin"><i class="bi bi-shield-lock"></i> Admin</label>--%>

                            <input type="radio" class="btn-check" name="userType" id="typeDoctor" value="DOCTOR">
                            <label class="btn btn-outline-success" for="typeDoctor"><i class="bi bi-person-badge"></i> Doctor</label>

                            <input type="radio" class="btn-check" name="userType" id="typeStaff" value="STAFF">
                            <label class="btn btn-outline-info" for="typeStaff"><i class="bi bi-person-workspace"></i> Staff</label>
                        </div>
                    </div>

                    <!-- Champs communs -->
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="firstName" class="form-label">Prénom *</label>
                            <input type="text" class="form-control" name="firstName" id="firstName" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="lastName" class="form-label">Nom *</label>
                            <input type="text" class="form-control" name="lastName" id="lastName" required>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="email" class="form-label">Email *</label>
                            <input type="email" class="form-control" name="email" id="email" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="password" class="form-label">Mot de passe *</label>
                            <input type="password" class="form-control" name="password" id="password" required minlength="6">
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="cin" class="form-label">CIN *</label>
                            <input type="text" class="form-control" name="cin" id="cin" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="phone" class="form-label">Téléphone *</label>
                            <input type="tel" class="form-control" name="phone" id="phone" required>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="address" class="form-label">Adresse *</label>
                        <textarea class="form-control" name="address" id="address" rows="2" required></textarea>
                    </div>

                    <!-- Champs DOCTOR -->
                    <div id="doctorFields" class="conditional-fields">
                        <hr>
                        <h5 class="mb-3"><i class="bi bi-stethoscope"></i> Informations Médecin</h5>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="specialityId" class="form-label">Spécialité</label>
                                <select class="form-select" name="specialityId" id="specialityId">
                                    <option value="">Sélectionner...</option>
                                    <c:forEach var="spec" items="${specialities}">
                                        <option value="${spec.id}">${spec.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="title" class="form-label">Titre</label>
                                <input type="text" class="form-control" name="title" id="title" placeholder="Ex: Cardiologue">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="registration" class="form-label">N° d'enregistrement</label>
                                <input type="text" class="form-control" name="registration" id="registration">
                            </div>
                        </div>
                    </div>

                    <!-- Champs STAFF -->
                    <div id="staffFields" class="conditional-fields">
                        <hr>
                        <h5 class="mb-3"><i class="bi bi-briefcase"></i> Informations du Personnel</h5>
                        <div class="mb-3">
                            <label for="position" class="form-label">Poste</label>
                            <input type="text" class="form-control" name="position" id="position" placeholder="Ex: Infirmier, Réceptionniste">
                        </div>
                    </div>

                    <!-- Boutons -->
                    <div class="d-grid gap-2 mt-4">
                        <button type="submit" class="btn btn-primary">
                            <i class="bi bi-check-circle"></i> Créer l'utilisateur
                        </button>
                        <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-secondary">
                            <i class="bi bi-x-circle"></i> Annuler
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    const radios = document.querySelectorAll('input[name="userType"]');
    const doctorFields = document.getElementById('doctorFields');
    const staffFields = document.getElementById('staffFields');

    radios.forEach(radio => {
        radio.addEventListener('change', function() {
            doctorFields.classList.remove('active');
            staffFields.classList.remove('active');

            if (this.value === 'DOCTOR') {
                doctorFields.classList.add('active');
            } else if (this.value === 'STAFF') {
                staffFields.classList.add('active');
            }
        });
    });
</script>
</body>
</html>
