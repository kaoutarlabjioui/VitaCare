<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<c:set var="pageTitle" value="Tableau de bord - Patient" scope="request"/>
<jsp:include page="../common/header.jsp"/>

<div class="container mt-4">
    <h2 class="mb-4"><i class="bi bi-person-heart"></i> Bienvenue ${user.firstName} ${user.lastName}</h2>

    <!-- Statistiques Patient -->
    <div class="row g-4 mb-4">
        <div class="col-md-3">
            <div class="card text-white bg-primary shadow">
                <div class="card-body text-center">
                    <h6>Rendez-vous à venir</h6>
                    <h2>0</h2>
                    <small>Prochainement disponible</small>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card text-white bg-success shadow">
                <div class="card-body text-center">
                    <h6>Rendez-vous passés</h6>
                    <h2>0</h2>
                    <small>Prochainement disponible</small>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card text-white bg-info shadow">
                <div class="card-body text-center">
                    <h6>Médecins consultés</h6>
                    <h2>0</h2>
                    <small>Prochainement disponible</small>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card text-white bg-warning shadow">
                <div class="card-body text-center">
                    <h6>Prochain RDV</h6>
                    <h2>-</h2>
                    <small>Aucun rendez-vous</small>
                </div>
            </div>
        </div>
    </div>

    <!-- 2 colonnes principales -->
    <div class="row g-4">
        <!-- 📅 Mes prochains rendez-vous -->
        <div class="col-md-6">
            <div class="card shadow mb-4">
                <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                    <h5 class="mb-0"><i class="bi bi-calendar-check"></i> Mes prochains rendez-vous</h5>
                    <a href="${pageContext.request.contextPath}/appointments/book" class="btn btn-sm btn-light">
                        <i class="bi bi-plus-circle"></i> Nouveau RDV
                    </a>
                </div>
                <div class="card-body">
                    <!-- À activer plus tard avec appointments -->
                    <%--
                    <c:if test="${empty upcomingAppointments}">
                        <p class="text-muted text-center mb-0">Aucun rendez-vous prévu.</p>
                    </c:if>

                    <c:if test="${not empty upcomingAppointments}">
                        <table class="table table-hover align-middle">
                            <thead>
                            <tr>
                                <th>Date</th><th>Heure</th><th>Médecin</th><th>Statut</th><th>Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="appt" items="${upcomingAppointments}">
                                <tr>
                                    <td><fmt:formatDate value="${appt.appointmentDate}" pattern="dd/MM/yyyy"/></td>
                                    <td><fmt:formatDate value="${appt.appointmentDate}" pattern="HH:mm"/></td>
                                    <td>Dr. ${appt.doctor.firstName} ${appt.doctor.lastName}</td>
                                    <td>
                                        <span class="badge bg-${appt.status == 'SCHEDULED' ? 'primary' :
                                                               appt.status == 'CANCELLED' ? 'danger' :
                                                               appt.status == 'DONE' ? 'success' : 'secondary'}">
                                                ${appt.status}
                                        </span>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/appointments/details?id=${appt.id}"
                                           class="btn btn-sm btn-outline-info">
                                            <i class="bi bi-eye"></i>
                                        </a>
                                        <c:if test="${appt.status == 'SCHEDULED'}">
                                            <a href="${pageContext.request.contextPath}/appointments/cancel?id=${appt.id}"
                                               class="btn btn-sm btn-outline-danger"
                                               onclick="return confirm('Annuler ce rendez-vous ?')">
                                                <i class="bi bi-x-circle"></i>
                                            </a>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                    --%>

                    <!-- Message temporaire -->
                    <div class="alert alert-info text-center" role="alert">
                        <i class="bi bi-info-circle"></i> Fonctionnalité de rendez-vous en cours de développement
                    </div>
                </div>
            </div>
        </div>

        <!-- 🩺 Médecins disponibles -->
        <div class="col-md-6">
            <div class="card shadow">
                <div class="card-header bg-success text-white">
                    <h5 class="mb-0"><i class="bi bi-stethoscope"></i> Médecins disponibles</h5>
                </div>
                <div class="card-body">
                    <!-- À activer plus tard avec la liste des doctors -->
                    <%--
                    <c:if test="${empty availableDoctors}">
                        <p class="text-muted text-center mb-0">Aucun médecin disponible pour le moment.</p>
                    </c:if>

                    <c:if test="${not empty availableDoctors}">
                        <div class="list-group">
                            <c:forEach var="doctor" items="${availableDoctors}">
                                <a href="${pageContext.request.contextPath}/appointments/book?doctorId=${doctor.id}"
                                   class="list-group-item list-group-item-action">
                                    <div class="d-flex w-100 justify-content-between">
                                        <h6 class="mb-1">Dr. ${doctor.firstName} ${doctor.lastName}</h6>
                                        <small class="text-success"><i class="bi bi-circle-fill"></i> Disponible</small>
                                    </div>
                                    <p class="mb-1 text-muted">${doctor.speciality}</p>
                                    <small>${doctor.department}</small>
                                </a>
                            </c:forEach>
                        </div>
                    </c:if>
                    --%>

                    <!-- Message temporaire -->
                    <div class="alert alert-info text-center" role="alert">
                        <i class="bi bi-info-circle"></i> Liste des médecins en cours de développement
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Mes informations personnelles -->
    <div class="row g-4 mt-2">
        <div class="col-md-12">
            <div class="card shadow">
                <div class="card-header bg-info text-white d-flex justify-content-between align-items-center">
                    <h5 class="mb-0"><i class="bi bi-person-vcard"></i> Mes informations</h5>
                    <a href="${pageContext.request.contextPath}/patients/edit?id=${user.id}" class="btn btn-sm btn-light">
                        <i class="bi bi-pencil"></i> Modifier
                    </a>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-6">
                            <p><strong>Nom :</strong> ${user.firstName} ${user.lastName}</p>
                          
                            <c:if test="${not empty patient}">
                                <p><strong>CIN :</strong> ${patient.cin}</p>
                                <p><strong>Téléphone :</strong> ${patient.phone}</p>
                            </c:if>
                        </div>
                        <div class="col-md-6">
                            <c:if test="${not empty patient}">
                                <p><strong>Date de naissance :</strong>
                                    <fmt:formatDate value="${patient.birthDate}" pattern="dd/MM/yyyy"/>
                                </p>
                                <p><strong>Sexe :</strong> ${patient.sexe}</p>
                                <p><strong>Groupe sanguin :</strong> ${patient.blood}</p>
                                <p><strong>Adresse :</strong> ${patient.address}</p>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Historique des rendez-vous -->
    <div class="row g-4 mt-2">
        <div class="col-md-12">
            <div class="card shadow">
                <div class="card-header bg-secondary text-white">
                    <h5 class="mb-0"><i class="bi bi-clock-history"></i> Historique des rendez-vous</h5>
                </div>
                <div class="card-body">
                    <!-- À activer plus tard avec appointments -->
                    <%--
                    <c:if test="${empty pastAppointments}">
                        <p class="text-muted text-center mb-0">Aucun rendez-vous passé.</p>
                    </c:if>

                    <c:if test="${not empty pastAppointments}">
                        <table class="table table-hover align-middle">
                            <thead>
                            <tr>
                                <th>Date</th><th>Heure</th><th>Médecin</th><th>Spécialité</th><th>Statut</th><th>Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="appt" items="${pastAppointments}">
                                <tr>
                                    <td><fmt:formatDate value="${appt.appointmentDate}" pattern="dd/MM/yyyy"/></td>
                                    <td><fmt:formatDate value="${appt.appointmentDate}" pattern="HH:mm"/></td>
                                    <td>Dr. ${appt.doctor.firstName} ${appt.doctor.lastName}</td>
                                    <td>${appt.doctor.speciality}</td>
                                    <td>
                                        <span class="badge bg-${appt.status == 'DONE' ? 'success' : 'danger'}">
                                                ${appt.status}
                                        </span>
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
                    </c:if>
                    --%>

                    <!-- Message temporaire -->
                    <div class="alert alert-info text-center" role="alert">
                        <i class="bi bi-info-circle"></i> Historique des rendez-vous en cours de développement
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>