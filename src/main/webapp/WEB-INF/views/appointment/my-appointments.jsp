<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<c:set var="pageTitle" value="Mes rendez-vous" scope="request"/>
<jsp:include page="../common/header.jsp"/>

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2><i class="bi bi-calendar-check"></i> Mes rendez-vous à venir</h2>
        <div>
            <a href="${pageContext.request.contextPath}/appointments/history" class="btn btn-outline-secondary me-2">
                <i class="bi bi-clock-history"></i> Historique
            </a>
            <a href="${pageContext.request.contextPath}/search-doctors" class="btn btn-primary">
                <i class="bi bi-plus-circle"></i> Nouveau rendez-vous
            </a>
        </div>
    </div>

    <c:if test="${not empty param.success}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="bi bi-check-circle"></i> ${param.success}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <c:if test="${not empty param.error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="bi bi-exclamation-triangle"></i> ${param.error}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <c:if test="${empty upcomingAppointments}">
        <div class="card shadow">
            <div class="card-body text-center py-5">
                <i class="bi bi-calendar-x text-muted" style="font-size: 4rem;"></i>
                <h4 class="mt-3 text-muted">Aucun rendez-vous à venir</h4>
                <p class="text-muted">Vous n'avez pas encore de rendez-vous planifié.</p>
                <a href="${pageContext.request.contextPath}/search-doctors" class="btn btn-primary mt-2">
                    <i class="bi bi-search"></i> Rechercher un médecin
                </a>
            </div>
        </div>
    </c:if>

    <c:if test="${not empty upcomingAppointments}">
        <div class="row g-4">
            <c:forEach var="appt" items="${upcomingAppointments}">
                <div class="col-md-6">
                    <div class="card shadow h-100">
                        <div class="card-header bg-primary text-white">
                            <div class="d-flex justify-content-between align-items-center">
                                <h5 class="mb-0">
                                    <i class="bi bi-person-badge"></i>
                                    Dr. ${appt.doctorFirstName} ${appt.doctorLastName}
                                </h5>
                                <span class="badge bg-${appt.status == 'PLANNED' ? 'success' : 'secondary'}">
                                        ${appt.status.displayName}
                                </span>
                            </div>
                        </div>
                        <div class="card-body">
                            <div class="mb-3">
                                <p class="mb-2">
                                    <i class="bi bi-hospital text-info"></i>
                                    <strong>Spécialité:</strong> ${appt.doctorSpeciality}
                                </p>
                                <p class="mb-2">
                                    <i class="bi bi-building text-warning"></i>
                                    <strong>Département:</strong> ${appt.doctorDepartment}
                                </p>
                            </div>

                            <hr>

                            <div class="mb-3">
                                <p class="mb-2">
                                    <i class="bi bi-calendar text-primary"></i>
                                    <strong>Date:</strong>
                                    <fmt:formatDate value="${appt.startAt}" pattern="dd/MM/yyyy" type="date"/>
                                </p>
                                <p class="mb-2">
                                    <i class="bi bi-clock text-success"></i>
                                    <strong>Heure:</strong>
                                    <fmt:formatDate value="${appt.startAt}" pattern="HH:mm" type="time"/> -
                                    <fmt:formatDate value="${appt.endAt}" pattern="HH:mm" type="time"/>
                                    (${appt.duration} min)
                                </p>
                                <p class="mb-0">
                                    <i class="bi bi-geo-alt text-danger"></i>
                                    <strong>Type:</strong>
                                    <span class="badge bg-${appt.type == 'LOCAL' ? 'info' : 'warning'}">
                                            ${appt.type == 'LOCAL' ? 'Sur place' : 'En ligne'}
                                    </span>
                                </p>
                            </div>

                            <hr>

                            <div class="d-grid gap-2">
                                <a href="${pageContext.request.contextPath}/appointments/details?id=${appt.id}"
                                   class="btn btn-outline-info">
                                    <i class="bi bi-eye"></i> Voir les détails
                                </a>

                                <c:if test="${appt.status.canBeCancelled()}">
                                    <a href="${pageContext.request.contextPath}/appointments/cancel?id=${appt.id}"
                                       class="btn btn-outline-danger"
                                       onclick="return confirm('Êtes-vous sûr de vouloir annuler ce rendez-vous ?')">
                                        <i class="bi bi-x-circle"></i> Annuler le rendez-vous
                                    </a>
                                    <small class="text-muted text-center">
                                        <i class="bi bi-info-circle"></i>
                                        Annulation possible jusqu'à ${maxCancellationHours}h avant
                                    </small>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
</div>

<jsp:include page="../common/footer.jsp"/>