<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<c:set var="pageTitle" value="Tableau de bord - Agent administratif" scope="request"/>
<jsp:include page="../common/header.jsp"/>

<h2 class="mb-4"><i class="bi bi-briefcase"></i> Espace agent administratif</h2>

<!-- Statistiques globales -->
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
                <h6>Patients enregistrés</h6>
                <h2>${stats.totalPatients}</h2>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card text-white bg-info shadow">
            <div class="card-body text-center">
                <h6>Docteurs actifs</h6>
                <h2>${stats.activeDoctors}</h2>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card text-white bg-warning shadow">
            <div class="card-body text-center">
                <h6>En attente</h6>
                <h2>${stats.waitingList}</h2>
            </div>
        </div>
    </div>
</div>

<div class="row g-4">
    <!-- Gestion des rendez-vous -->
    <div class="col-md-8">
        <div class="card shadow mb-4">
            <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                <h5 class="mb-0"><i class="bi bi-calendar2-week"></i> Planning des rendez-vous</h5>
                <a href="${pageContext.request.contextPath}/appointments/new?manual=true" class="btn btn-light btn-sm">
                    <i class="bi bi-plus-circle"></i> Nouveau rendez-vous manuel
                </a>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead>
                        <tr>
                            <th>Date</th>
                            <th>Heure</th>
                            <th>Patient</th>
                            <th>Docteur</th>
                            <th>Statut</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${appointments}" var="appt">
                            <tr>
                                <td><fmt:formatDate value="${appt.appointmentDate}" pattern="dd/MM/yyyy"/></td>
                                <td><fmt:formatDate value="${appt.appointmentDate}" pattern="HH:mm"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${appt.patient != null}">
                                            ${appt.patient.firstName} ${appt.patient.lastName}
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-muted fst-italic">Non enregistré</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>Dr. ${appt.doctor.firstName} ${appt.doctor.lastName}</td>
                                <td>
                                    <span class="badge bg-${appt.status == 'SCHEDULED' ? 'primary' :
                                                           appt.status == 'WAITING' ? 'warning' :
                                                           appt.status == 'CANCELLED' ? 'danger' : 'success'}">
                                            ${appt.status}
                                    </span>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/appointments/${appt.id}"
                                       class="btn btn-sm btn-outline-primary">
                                        <i class="bi bi-eye"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- Liste d’attente -->
        <div class="card shadow">
            <div class="card-header bg-warning text-white d-flex justify-content-between align-items-center">
                <h5 class="mb-0"><i class="bi bi-hourglass-split"></i> Liste d’attente</h5>
                <a href="${pageContext.request.contextPath}/waiting-list/manage" class="btn btn-sm btn-light">
                    <i class="bi bi-sliders"></i> Gérer
                </a>
            </div>
            <div class="card-body">
                <c:if test="${empty waitingList}">
                    <p class="text-muted text-center mb-0">Aucun patient en attente actuellement.</p>
                </c:if>
                <c:if test="${not empty waitingList}">
                    <ul class="list-group">
                        <c:forEach items="${waitingList}" var="w">
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                    ${w.patient.firstName} ${w.patient.lastName}
                                <span class="badge bg-secondary">${w.reason}</span>
                            </li>
                        </c:forEach>
                    </ul>
                </c:if>
            </div>
        </div>
    </div>

    <!-- Section d’actions rapides -->
    <div class="col-md-4">
        <div class="card shadow mb-4">
            <div class="card-header bg-success text-white">
                <h5 class="mb-0"><i class="bi bi-person-plus"></i> Enregistrer un patient</h5>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/patients/register" method="post">
                    <div class="mb-3">
                        <label for="firstName" class="form-label">Prénom</label>
                        <input type="text" class="form-control" id="firstName" name="firstName" required>
                    </div>
                    <div class="mb-3">
                        <label for="lastName" class="form-label">Nom</label>
                        <input type="text" class="form-control" id="lastName" name="lastName" required>
                    </div>
                    <div class="mb-3">
                        <label for="phone" class="form-label">Téléphone</label>
                        <input type="text" class="form-control" id="phone" name="phone" required>
                    </div>
                    <button type="submit" class="btn btn-success w-100">
                        <i class="bi bi-save"></i> Enregistrer
                    </button>
                </form>
            </div>
        </div>

        <!-- Infos staff -->
        <div class="card shadow">
            <div class="card-header bg-secondary text-white">
                <h5 class="mb-0"><i class="bi bi-person-circle"></i> Mon profil</h5>
            </div>
            <div class="card-body">
                <p><strong>Nom :</strong> ${user.firstName} ${user.lastName}</p>
                <p><strong>Rôle :</strong> Agent administratif</p>
                <p><strong>Email :</strong> ${user.email}</p>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
