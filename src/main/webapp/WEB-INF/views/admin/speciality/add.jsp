<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nouvelle Spécialité - VitaCare</title>
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
            padding: 2rem 0;
        }

        .form-container {
            max-width: 700px;
            margin: 0 auto;
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

        .form-label {
            font-weight: 600;
            color: #495057;
            margin-bottom: 0.5rem;
        }

        .form-control, .form-select {
            border-radius: 8px;
            border: 2px solid #e9ecef;
            padding: 0.6rem 1rem;
            transition: all 0.3s;
        }

        .form-control:focus, .form-select:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 0.2rem rgba(99, 102, 241, 0.25);
        }

        .btn-primary {
            background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
            border: none;
            padding: 0.7rem 2rem;
            border-radius: 8px;
            font-weight: 600;
            transition: transform 0.2s;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(99, 102, 241, 0.4);
        }

        .btn-secondary {
            border-radius: 8px;
            padding: 0.7rem 2rem;
            font-weight: 600;
        }

        .required {
            color: #dc3545;
        }
    </style>
</head>
<body>
<div class="container form-container">
    <div class="card">
        <div class="card-header">
            <h3 class="mb-1"><i class="bi bi-star"></i> Nouvelle Spécialité</h3>
            <p class="mb-0 opacity-75">Ajoutez une nouvelle spécialité médicale</p>
        </div>
        <div class="card-body p-4">
            <form method="post" action="${pageContext.request.contextPath}/speciality?action=create" onsubmit="return validateForm()">
                <div class="mb-3">
                    <label for="name" class="form-label">
                        Nom de la spécialité <span class="required">*</span>
                    </label>
                    <input type="text"
                           class="form-control"
                           id="name"
                           name="name"
                           placeholder="Ex: Cardiologie"
                           required>
                    <div class="invalid-feedback">
                        Veuillez saisir le nom de la spécialité.
                    </div>
                </div>

                <div class="mb-3">
                    <label for="code" class="form-label">
                        Code <span class="required">*</span>
                    </label>
                    <input type="text"
                           class="form-control"
                           id="code"
                           name="code"
                           placeholder="Ex: CARD"
                           required>
                    <div class="invalid-feedback">
                        Veuillez saisir le code de la spécialité.
                    </div>
                </div>

                <div class="mb-3">
                    <label for="departmentName" class="form-label">
                        Département <span class="required">*</span>
                    </label>
                    <select class="form-select"
                            id="departmentName"
                            name="departmentName"
                            required>
                        <option value="">-- Sélectionnez un département --</option>
                        <c:forEach var="dept" items="${departments}">
                            <option value="${dept.name}">${dept.name}</option>
                        </c:forEach>
                    </select>
                    <div class="invalid-feedback">
                        Veuillez sélectionner un département.
                    </div>
                </div>

                <div class="mb-4">
                    <label for="description" class="form-label">Description</label>
                    <textarea class="form-control"
                              id="description"
                              name="description"
                              rows="4"
                              placeholder="Description de la spécialité..."></textarea>
                </div>

                <div class="d-flex justify-content-between">
                    <a href="${pageContext.request.contextPath}/speciality?action=list" class="btn btn-secondary">
                        <i class="bi bi-x-circle"></i> Annuler
                    </a>
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-check-circle"></i> Créer la spécialité
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function validateForm() {
        const form = document.querySelector('form');
        if (!form.checkValidity()) {
            form.classList.add('was-validated');
            return false;
        }
        return true;
    }
</script>
</body>
</html>
