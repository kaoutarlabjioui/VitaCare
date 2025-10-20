<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<c:set var="pageTitle" value="Historique des rendez-vous" scope="request"/>
<jsp:include page="../common/header.jsp"/>

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2><i class="bi bi-clock-history"></i> Historique des rendez-vous</h2>
        <a href="${pageContext.request.contextPath}/appointments" class="btn btn-outline-secondary">
            <i class="bi bi-arrow-left"></i> Retour
        </a>
    </div>

    <!-- Filtres -->
    <div class="card shadow mb-4">
        <div class="card-header bg-primary text-white">
            <h5 class="mb-0"><i class="bi bi-funnel"></i> Filtres</h5>
        </div>
        <div class="card-body">
            <form method="post" action="${pageContext.request.contextPath}/appointments/filter-history">
                <div class="row g-3">
                    <div class="col-md-4">
                        <label for="status" class="form-label">Statut</label>
                        <select class="form-select" id="status" name="status">
                            <option value="">-- Tous les statuts --</option>
                            <option value="PLANNED" ${selectedStatus == 'PLANNED' ? 'selected' : ''}>Planifié</option>
                            <option value="DONE" ${selectedStatus == 'DONE' ? 'selected' : ''}>Terminé</option>
                            <option value="CANCELLED" ${selectedStatus == 'CANCELLED' ? 'selected' : ''}>Annulé</option>
                        </select>
                    </div>

                    <div class="col-md-3">
                        <label for="startDate" class="form-label">Date de début</label>
                        <input type="date"
                               class="form-control"
                               id="startDate"
                               name="startDate"
                               value="${startDate}">
                    </div>

                    <div class="col-md-3">
                        <label for="endDate" class="form-label">Date de fin</label>
                        <input type="date"
                               class="form-control"
                               id="endDate"
                               name="endDate"
                               value="${endDate}">
                    </div>

                    <div class="col-md-2 d-flex align-items-end">
                        <button type="submit" class="btn btn-primary w-100">
                            <i class="bi bi-search"></i> Filtrer
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <!-- Résultats -->
    <div class="card shadow">
        <div class="card-header bg-secondary text-white">
            <h5 class="mb-0">
                <i class="bi bi-list-ul"></i>
                Rendez-vous
                <span class="badge bg-light text-dark">${appointments.size()}</span>
            </h5>
        </div>
        <div class="card-body">
            <c:if test="${empty appointments}">
                <div class="alert alert-info text-center" role="alert">
                    <i class="bi bi-info-circle"></i>
                    Aucun rendez-vous trouvé avec ces critères.
                </div>
            </c:if>

            <c:if test="${not empty appointments}">
                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead class="table-light">
                        <tr>
                            <th><i class="bi bi-calendar"></i> Date</th>
                            <th><i class="bi bi-clock"></i> Heure</th>
                            <th><i class="bi bi-person"></i> Médecin</th>
                            <th><i class="bi bi-hospital"></i> Spécialité</th>
                            <th><i class="bi bi-hourglass"></i> Durée</th>
                            <th><i class="bi bi-check-circle"></i> Statut</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="appt" items="${appointments}">
                            <tr>
                                <td>
                                    <fmt:formatDate value="${appt.startAt}" pattern="dd/MM/yyyy" type="date"/>
                                </td>
                                <td>
                                    <fmt:formatDate value="${appt.startAt}" pattern="HH:mm" type="time"/>
                                </td>
                                <td>
                                    Dr. ${appt.doctorFirstName} ${appt.doctorLastName}
                                </td>
                                <td>${appt.doctorSpeciality}</td>
                                <td>${appt.duration} min</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${appt.status == 'PLANNED'}">
                                            <span class="badge bg-primary">${appt.status.displayName}</span>
                                        </c:when>
                                        <c:when test="${appt.status == 'DONE'}">
                                            <span class="badge bg-success">${appt.status.displayName}</span>
                                        </c:when>
                                        <c:when test="${appt.status == 'CANCELLED'}">
                                            <span class="badge bg-danger">${appt.status.displayName}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-secondary">${appt.status}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/appointments/details?id=${appt.id}"
                                       class="btn btn-sm btn-outline-info">
                                        <i class="bi bi-eye"></i> Détails
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>