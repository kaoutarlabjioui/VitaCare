<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<c:set var="pageTitle" value="Tableau de bord - Docteur" scope="request"/>
<jsp:include page="../common/header.jsp"/>

<div class="container mt-4">
    <h2 class="mb-4"><i class="bi bi-stethoscope"></i> Tableau de bord du docteur</h2>

    <!-- Statistiques -->
    <div class="row g-4 mb-4">
        <div class="col-md-3">
            <div class="card text-white bg-primary shadow">
                <div class="card-body text-center">
                    <h6>Rendez-vous du jour</h6>
                    <h2>${stats.todayAppointments}</h2>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card text-white bg-success shadow">
                <div class="card-body text-center">
                    <h6>Patients suivis</h6>
                    <h2>${stats.totalPatients}</h2>
                </div>
            </div>
        </div>
        <c:set var="availableCount" value="0" />

        <c:forEach var="a" items="${availabilities}">
            <c:if test="${a.status.name() == 'AVAILABLE'}">
                <c:set var="availableCount" value="${availableCount + 1}" />
            </c:if>
        </c:forEach>

        <div class="col-md-3">
            <div class="card text-white bg-info shadow">
                <div class="card-body text-center">
                    <h6>Créneaux disponibles</h6>
                    <h2>${availableCount}</h2>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card text-white bg-secondary shadow">
                <div class="card-body text-center">
                    <h6>Prochains rendez-vous</h6>
                    <h2>${appointments.size()}</h2>
                </div>
            </div>
        </div>
    </div>

    <!-- 2 colonnes principales -->
    <div class="row g-4">
        <!-- 🕒 Disponibilités -->
        <div class="col-md-6">
            <div class="card shadow mb-4">
                <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                    <h5 class="mb-0"><i class="bi bi-clock"></i> Mes disponibilités</h5>
                </div>
                <div class="card-body">
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                ${error}
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/availabilities/create" method="post" class="row g-3 mb-3">
                        <div class="col-md-4">
                            <label class="form-label">Jour de la semaine</label>
                            <select name="day" class="form-select" required>
                                <option value="">-- Sélectionner --</option>
                                <option value="MONDAY">Lundi</option>
                                <option value="TUESDAY">Mardi</option>
                                <option value="WEDNESDAY">Mercredi</option>
                                <option value="THURSDAY">Jeudi</option>
                                <option value="FRIDAY">Vendredi</option>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">Début</label>
                            <input type="time" name="startTime" class="form-control" required>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">Fin</label>
                            <input type="time" name="endTime" class="form-control" required>
                        </div>
                        <div class="col-md-2 d-flex align-items-end">
                            <button type="submit" class="btn btn-success w-100"><i class="bi bi-plus-circle"></i></button>
                        </div>
                    </form>

                    <c:if test="${empty availabilities}">
                        <p class="text-muted text-center mb-0">Aucune disponibilité ajoutée.</p>
                    </c:if>

                    <c:if test="${not empty availabilities}">
                        <table class="table table-sm table-hover align-middle">
                            <thead>
                            <tr>
                                <th>Jour</th><th>Début</th><th>Fin</th><th>Statut</th><th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="a" items="${availabilities}">
                                <tr>
                                    <td>${a.day}</td>
                                    <td>${a.startTime}</td>
                                    <td>${a.endTime}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${a.status.name() == 'AVAILABLE'}">
                                                <span class="badge bg-success">${a.status.name()}</span>
                                            </c:when>
                                            <c:when test="${a.status.name() == 'BOOKED'}">
                                                <span class="badge bg-info">${a.status.name()}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">${a.status.name()}</span>
                                            </c:otherwise>
                                        </c:choose>

                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/availabilities/delete?id=${a.id}"
                                           class="btn btn-sm btn-outline-danger"
                                           onclick="return confirm('Supprimer ce créneau ?')">
                                            <i class="bi bi-trash"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                </div>
            </div>
        </div>

        <!-- 📅 Rendez-vous à venir -->
        <div class="col-md-6">
            <div class="card shadow">
                <div class="card-header bg-info text-white">
                    <h5 class="mb-0"><i class="bi bi-calendar-week"></i> Mes rendez-vous à venir</h5>
                </div>
                <div class="card-body">
                    <c:if test="${empty appointments}">
                        <p class="text-muted text-center mb-0">Aucun rendez-vous prévu.</p>
                    </c:if>

                    <c:if test="${not empty appointments}">
                        <table class="table table-hover align-middle">
                            <thead>
                            <tr>
                                <th>Date</th><th>Heure</th><th>Patient</th><th>Statut</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="appt" items="${appointments}">
                                <tr>
                                    <td><fmt:formatDate value="${appt.appointmentDate}" pattern="dd/MM/yyyy"/></td>
                                    <td><fmt:formatDate value="${appt.appointmentDate}" pattern="HH:mm"/></td>
                                    <td>${appt.patient.firstName} ${appt.patient.lastName}</td>
                                    <td>
                                        <span class="badge bg-${appt.status == 'SCHEDULED' ? 'primary' :
                                                               appt.status == 'CANCELLED' ? 'danger' :
                                                               appt.status == 'DONE' ? 'success' : 'secondary'}">
                                                ${appt.status}
                                        </span>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
