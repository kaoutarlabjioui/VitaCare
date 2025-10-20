<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<c:set var="pageTitle" value="Prendre rendez-vous" scope="request"/>
<jsp:include page="../common/header.jsp"/>

<div class="container mt-4">
    <!-- En-tête avec infos du docteur -->
    <div class="card shadow mb-4">
        <div class="card-body">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h3 class="mb-2">
                        <i class="bi bi-person-badge text-primary"></i>
                        Dr. ${doctor.firstName} ${doctor.lastName}
                    </h3>
                    <p class="text-muted mb-1">
                        <i class="bi bi-hospital"></i> <strong>Spécialité:</strong> ${doctor.speciality}
                    </p>
                    <p class="text-muted mb-0">
                        <i class="bi bi-building"></i> <strong>Département:</strong> ${doctor.department}
                    </p>
                </div>
                <a href="${pageContext.request.contextPath}/search-doctors" class="btn btn-outline-secondary">
                    <i class="bi bi-arrow-left"></i> Retour
                </a>
            </div>
        </div>
    </div>

    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="bi bi-exclamation-triangle"></i> ${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <!-- Formulaire de sélection de date et durée -->
    <div class="card shadow mb-4">
        <div class="card-header bg-primary text-white">
            <h5 class="mb-0"><i class="bi bi-calendar-check"></i> Sélectionner une date</h5>
        </div>
        <div class="card-body">
            <form id="dateForm" method="get" action="${pageContext.request.contextPath}/appointments/available-slots">
                <input type="hidden" name="doctorId" value="${doctor.id}">

                <div class="row g-3">
                    <div class="col-md-6">
                        <label for="date" class="form-label">Date du rendez-vous</label>
                        <input type="date"
                               class="form-control"
                               id="date"
                               name="date"
                               required
                               min="<fmt:formatDate value='${java.time.LocalDate.now().plusDays(1)}' pattern='yyyy-MM-dd'/>">
                        <small class="text-muted">Les rendez-vous doivent être pris au moins 2 heures à l'avance</small>
                    </div>

                    <div class="col-md-4">
                        <label for="duration" class="form-label">Durée du rendez-vous</label>
                        <select class="form-select" id="duration" name="duration" required>
                            <option value="">-- Sélectionner --</option>
                            <option value="15">15 minutes</option>
                            <option value="30" selected>30 minutes</option>
                        </select>
                    </div>

                    <div class="col-md-2 d-flex align-items-end">
                        <button type="submit" class="btn btn-primary w-100">
                            <i class="bi bi-search"></i> Rechercher
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <!-- Informations importantes -->
    <div class="card shadow border-info">
        <div class="card-header bg-info text-white">
            <h5 class="mb-0"><i class="bi bi-info-circle"></i> Informations importantes</h5>
        </div>
        <div class="card-body">
            <ul class="mb-0">
                <li><strong>Lead time:</strong> Les rendez-vous doivent être pris au moins <strong>2 heures à l'avance</strong></li>
                <li><strong>Durée:</strong> Vous pouvez choisir entre <strong>15 ou 30 minutes</strong></li>
                <li><strong>Pause déjeuner:</strong> Aucun rendez-vous entre <strong>12h00 et 13h00</strong></li>
                <li><strong>Buffer:</strong> Un délai de <strong>5 minutes</strong> est automatiquement ajouté entre chaque rendez-vous</li>
                <li><strong>Annulation:</strong> Vous pouvez annuler jusqu'à <strong>12 heures avant</strong> le rendez-vous</li>
            </ul>
        </div>
    </div>
</div>

<script>
    // Définir la date minimale (aujourd'hui + 2 heures minimum)
    document.addEventListener('DOMContentLoaded', function() {
        const dateInput = document.getElementById('date');
        const today = new Date();
        today.setDate(today.getDate() + 1); // Minimum demain
        const minDate = today.toISOString().split('T')[0];
        dateInput.setAttribute('min', minDate);
    });
</script>

<jsp:include page="../common/footer.jsp"/>