<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nouveau Département - VitaCare</title>
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
            display: flex;
            align-items: center;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .form-container {
            max-width: 600px;
            margin: 2rem auto;
        }

        .card {
            border: none;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            overflow: hidden;
        }

        .card-header {
            background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
            color: white;
            padding: 2rem;
            text-align: center;
        }

        .card-header h3 {
            margin: 0;
            font-weight: 600;
        }

        .card-body {
            padding: 2.5rem;
        }

        .form-label {
            font-weight: 600;
            color: #495057;
            margin-bottom: 0.5rem;
        }

        .form-control, .form-select {
            border-radius: 10px;
            border: 2px solid #e9ecef;
            padding: 0.75rem 1rem;
            transition: all 0.3s;
        }

        .form-control:focus, .form-select:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 0.2rem rgba(99, 102, 241, 0.25);
        }

        .btn-primary {
            background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
            border: none;
            padding: 0.75rem 2rem;
            border-radius: 10px;
            font-weight: 600;
            transition: transform 0.2s;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(99, 102, 241, 0.4);
        }

        .btn-secondary {
            border-radius: 10px;
            padding: 0.75rem 2rem;
            font-weight: 600;
        }

        .required {
            color: #dc3545;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="form-container">
        <div class="card">
            <div class="card-header">
                <h3><i class="bi bi-building"></i> Nouveau Département</h3>
                <p class="mb-0 mt-2 opacity-75">Créer un nouveau département médical</p>
            </div>
            <div class="card-body">
                <form method="post" action="${pageContext.request.contextPath}/departments/create">
                    <div class="mb-4">
                        <label for="code" class="form-label">
                            Code du Département <span class="required">*</span>
                        </label>
                        <input type="text"
                               class="form-control"
                               id="code"
                               name="code"
                               placeholder="Ex: CARD, NEUR, PEDI"
                               required
                               maxlength="10">
                        <small class="text-muted">Code unique pour identifier le département</small>
                    </div>

                    <div class="mb-4">
                        <label for="name" class="form-label">
                            Nom du Département <span class="required">*</span>
                        </label>
                        <input type="text"
                               class="form-control"
                               id="name"
                               name="name"
                               placeholder="Ex: Cardiologie, Neurologie"
                               required
                               maxlength="100">
                    </div>

                    <div class="mb-4">
                        <label for="description" class="form-label">
                            Description
                        </label>
                        <textarea class="form-control"
                                  id="description"
                                  name="description"
                                  rows="4"
                                  placeholder="Description détaillée du département et de ses services..."></textarea>
                    </div>

                    <div class="d-grid gap-2">
                        <button type="submit" class="btn btn-primary">
                            <i class="bi bi-check-circle"></i> Créer le Département
                        </button>
                        <a href="${pageContext.request.contextPath}/departments" class="btn btn-secondary">
                            <i class="bi bi-x-circle"></i> Annuler
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
