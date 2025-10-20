<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<c:set var="pageTitle" value="Créneaux disponibles" scope="request"/>
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
                        <i class="bi bi-calendar-check"></i>
                        <strong>Date:</strong> <fmt:formatDate value="${selectedDate}" pattern="dd/MM/yyyy" type="date"/>
                    </p>
                    <p class="text-muted mb-0">
                        <i class="bi bi-clock"></i>
                        <strong>Durée:</strong> ${duration} minutes
                    </p>
                </div>
                <a href="${pageContext.request.contextPath}/appointments/book?doctorId=${doctor.id}"
                   class="btn btn-outline-secondary">
                    <i class="bi bi-arrow-left"></i> Changer la date
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

    <!-- Créneaux disponibles -->
    <div class="card shadow">
        <div class="card-header bg-success text-white">
            <h5 class="mb-0">
                <i class="bi bi-clock-history"></i>
                Créneaux disponibles
                <span class="badge bg-light text-dark">${availableSlots.size()} créneau(x)</span>
            </h5>
        </div>
        <div class="card-body">
            <c:if test="${empty availableSlots}">
                <div class="alert alert-warning text-center" role="alert">
                    <i class="bi bi-exclamation-triangle fs-1"></i>
                    <h5 class="mt-3">Aucun créneau disponible</h5>
                    <p class="mb-0">
                        Le médecin n'a pas de créneaux disponibles pour cette date.<br>
                        Veuillez choisir une autre date ou un autre médecin.
                    </p>
                </div>
            </c:if>

            <c:if test="${not empty availableSlots}">
                <div class="row g-3">
                    <c:forEach var="slot" items="${availableSlots}">
                        <div class="col-md-3 col-sm-6">
                            <div class="card h-100 border-success hover-shadow">
                                <div class="card-body text-center">
                                    <i class="bi bi-clock text-success fs-1"></i>
                                    <h4 class="mt-2 mb-3">
                                        <fmt:formatDate value="${slot}" pattern="HH:mm" type="time"/>
                                    </h4>
                                    <form method="post" action="${pageContext.request.contextPath}/appointments/book">
                                        <input type="hidden" name="doctorId" value="${doctor.id}">
                                        <input type="hidden" name="appointmentDateTime"
                                               value="<fmt:formatDate value='${slot}' pattern='yyyy-MM-dd\\'T\\'HH:mm:ss'/>">
                                        <input type="hidden" name="duration" value="${duration}">
                                        <button type="submit" class="btn btn-success w-100">
                                            <i class="bi bi-check-circle"></i> Réserver
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <!-- Information sur les créneaux -->
                <div class="alert alert-info mt-4" role="alert">
                    <i class="bi bi-info-circle"></i>
                    <strong>Important:</strong>
                    <ul class="mb-0 mt-2">
                        <li>Les créneaux affichés respectent les disponibilités du médecin</li>
                        <li>Les rendez-vous pendant la pause déjeuner (12h-13h) ne sont pas disponibles</li>
                        <li>Un buffer de 5 minutes est automatiquement ajouté entre chaque rendez-vous</li>
                        <li>Les créneaux déjà réservés ne sont pas affichés</li>
                    </ul>
                </div>
            </c:if>
        </div>
    </div>
</div>

<style>
    .hover-shadow:hover {
        box-shadow: 0 4px 8px rgba(0,0,0,0.2);
        transform: translateY(-2px);
        transition: all 0.3s ease;
    }
</style>

<jsp:include page="../common/footer.jsp"/>