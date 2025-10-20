<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<c:set var="pageTitle" value="Détails du rendez-vous" scope="request"/>
<jsp:include page="../common/header.jsp"/>

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2><i class="bi bi-info-circle"></i> Détails du rendez-vous</h2>
        <a href="${pageContext.request.contextPath}/appointments" class="btn btn-outline-secondary">
            <i class="bi bi-arrow-left"></i> Retour
        </a>
    </div>

    <div class="row g-4">
        <!-- Informations principales -->
        <div class="col-md-8">
            <div class="card shadow mb-4">
                <div class="card-header bg-primary text-white">
                    <h5 class="mb-0">
                        <i class="bi bi-calendar-check"></i> Informations du rendez-vous
                    </h5>
                </div>
                <div class="card-body">
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <p class="mb-2">
                                <i class="bi bi-calendar text-primary"></i>
                                <strong>Date:</strong>
                            </p>
                            <h5>
                                <fmt:formatDate value="${appointment.startAt}" pattern="EEEE dd MMMM yyyy" type="date"/>
                            </h5>
                        </div>
                        <div class="col-md-6">
                            <p class="mb-2">
                                <i class="bi bi-clock text-success"></i>
                                <strong>Heure:</strong>
                            </p>
                            <h5>
                                <fmt:formatDate value="${appointment.startAt}" pattern="HH:mm" type="time"/> -
                                <fmt:formatDate value="${appointment.endAt}" pattern="HH:mm" type="time"/>
                            </h5>
                            <small class="text-muted">Durée: ${appointment.duration} minutes</small>
                        </div>
                    </div>

                    <hr>

                    <div class="mb-3">
                        <p class="mb-2">
                            <i class="bi bi-check-circle text-info"></i>
                            <strong>Statut:</strong>
                        </p>
                        <h5>
                            <c:choose>
                                <c:when test="${appointment.status == 'PLANNED'}">
                                    <span class="badge bg-primary fs-6">${appointment.status.displayName}</span>
                                </c:when>
                                <c:when test="${appointment.status == 'DONE'}">
                                    <span class="badge bg-success fs-6">${appointment.status.displayName}</span>
                                </c:when>
                                <c:when test="${appointment.status == 'CANCELLED'}">
                                    <span class="badge bg-danger fs-6">${appointment.status.displayName}</span>
                                </c:when>
                            </c:choose>
                        </h5>
                    </div>

                    <div class="mb-3">
                        <p class="mb-2">
                            <i class="bi bi-geo-alt text-warning"></i>
                            <strong>Type de consultation:</strong>
                        </p>
                        <h5>
                            <span class="badge bg-${appointment.type == 'LOCAL' ? 'info' : 'warning'} fs-6">
                                ${appointment.type == 'LOCAL' ? 'Sur place' : 'En ligne'}
                            </span>
                        </h5>
                    </div>

                    <div class="mb-0">
                        <p class="mb-2">
                            <i class="bi bi-hash text-secondary"></i>
                            <strong>Numéro de rendez-vous:</strong>
                        </p>
                        <h5>#${appointment.id}</h5>
                    </div>
                </div>
            </div>

            <!-- Informations du médecin -->
            <div class="card shadow">
                <div class="card-header bg-success text-white">
                    <h5 class="mb-0">
                        <i class="bi bi-person-badge"></i> Médecin
                    </h5>
                </div>
                <div class="card-body">
                    <h4 class="mb-3">Dr. ${appointment.doctorFirstName} ${appointment.doctorLastName}</h4>

                    <p class="mb-2">
                        <i class="bi bi-hospital text-info"></i>
                        <strong>Spécialité:</strong> ${appointment.doctorSpeciality}
                    </p>
                    <p class="mb-0">
                        <i class="bi bi-building text-warning"></i>
                        <strong>Département:</strong> ${appointment.doctorDepartment}
                    </p>
                </div>
            </div>
        </div>

        <!-- Actions et informations complémentaires -->
        <div class="col-md-4">
            <!-- Actions -->
            <div class="card shadow mb-4">
                <div class="card-header bg-info text-white">
                    <h5 class="mb-0"><i class="bi bi-gear"></i> Actions</h5>
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${canCancel}">
                            <div class="alert alert-success mb-3">
                                <i class="bi bi-check-circle"></i>
                                <small>Vous pouvez annuler ce rendez-vous</small>
                            </div>
                            <div class="d-grid">
                                <a href="${pageContext.request.contextPath}/appointments/cancel?id=${appointment.id}"
                                   class="btn btn-danger"
                                   onclick="return confirm('Êtes-vous sûr de vouloir annuler ce rendez-vous ?')">
                                    <i class="bi bi-x-circle"></i> Annuler le rendez-vous
                                </a>
                            </div>
                            <small class="text-muted d-block text-center mt-2">
                                <i class="bi bi-clock"></i>
                                    ${hoursUntilAppointment} heure(s) avant le RDV
                            </small>
                        </c:when>
                        <c:otherwise>
                            <div class="alert alert-warning mb-0">
                                <i class="bi bi-exclamation-triangle"></i>
                                <c:choose>
                                    <c:when test="${appointment.status == 'CANCELLED'}">
                                        <small>Ce rendez-vous a été annulé</small>
                                    </c:when>
                                    <c:when test="${appointment.status == 'DONE'}">
                                        <small>Ce rendez-vous est terminé</small>
                                    </c:when>
                                    <c:otherwise>
                                        <small>
                                            Annulation impossible<br>
                                            (moins de 12h avant le RDV)
                                        </small>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <!-- Rappels importants -->
            <div class="card shadow border-warning">
                <div class="card-header bg-warning text-dark">
                    <h5 class="mb-0"><i class="bi bi-bell"></i> Rappels</h5>
                </div>
                <div class="card-body">
                    <ul class="mb-0 small">
                        <li class="mb-2">
                            <strong>Arrivée:</strong> Présentez-vous 10 minutes avant l'heure du rendez-vous
                        </li>
                        <li class="mb-2">
                            <strong>Documents:</strong> Apportez votre carte d'identité et carte vitale
                        </li>
                        <li class="mb-2">
                            <strong>Annulation:</strong> Possible jusqu'à 12 heures avant
                        </li>
                        <li class="mb-0">
                            <strong>Contact:</strong> En cas d'urgence, contactez le secrétariat
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <!-- Boutons de navigation -->
    <div class="row mt-4">
        <div class="col-12">
            <div class="d-flex justify-content-between">
                <a href="${pageContext.request.contextPath}/appointments" class="btn btn-outline-secondary">
                    <i class="bi bi-arrow-left"></i> Mes rendez-vous
                </a>
                <a href="${pageContext.request.contextPath}/appointments/history" class="btn btn-outline-info">
                    <i class="bi bi-clock-history"></i> Historique
                </a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>