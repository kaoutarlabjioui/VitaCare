<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="pageTitle" value="Tableau de bord Patient" scope="request"/>
<jsp:include page="../common/header.jsp"/>

<h2 class="mb-4"><i class="bi bi-person-heart"></i> Mon espace patient</h2>

<div class="row g-4 mb-4">
    <div class="col-md-4">
        <div class="card text-white bg-primary shadow">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="card-title">Prochain rendez-vous</h6>
                        <h5 class="mb-0">
                            <c:choose>
                                <c:when test="${nextAppointment != null}">
                                    <fmt:formatDate value="${nextAppointment.appointmentDate}" pattern="dd/MM/yyyy"/>
                                </c:when>
                                <c:otherwise>
                                    Aucun
                                </c:otherwise>
                            </c:choose>
                        </h5>
                    </div>
                    <i class="bi bi-calendar-check" style="font-size: 3rem; opacity: 0.5;"></i>
                </div>
            </div>
        </div>
    </div>

    <div class="col-md-4">
        <div class="card text-white bg-success shadow">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="card-title">Consultations totales</h6>
                        <h2 class="mb-0">${stats.totalAppointments}</h2>
                    </div>
                    <i class="bi bi-clipboard-check" style="font-size: 3rem; opacity: 0.5;"></i>
                </div>
            </div>
        </div>
    </div>

    <div class="col-md-4">
        <div class="card text-white bg-info shadow">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="card-title">Notes médicales</h6>
                        <h2 class="mb-0">${stats.totalMedicalNotes}</h2>
                    </div>
                    <i class="bi bi-file-medical" style="font-size: 3rem; opacity: 0.5;"></i>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row g-4">
    <div class="col-md-8">
        <c:if test="${nextAppointment != null}">
            <div class="card shadow mb-4 border-primary">
                <div class="card-header bg-primary text-white">
                    <h5 class="mb-0"><i class="bi bi-calendar-event"></i> Prochain rendez-vous</h5>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-6">
                            <h6 class="text-muted">Date et heure</h6>
                            <p class="h5">
                                <i class="bi bi-calendar3"></i>
                                <fmt:formatDate value="${nextAppointment.appointmentDate}" pattern="dd MMMM yyyy"/>
                                <br>
                                <i class="bi bi-clock"></i>
                                <fmt:formatDate value="${nextAppointment.appointmentDate}" pattern="HH:mm"/>
                            </p>
                        </div>
                        <div class="col-md-6">
                            <h6 class="text-muted">Docteur</h6>
                            <p class="h5">
                                <i class="bi bi-person-badge"></i>
                                Dr. ${nextAppointment.doctor.firstName} ${nextAppointment.doctor.lastName}
                            </p>
                            <p class="text-muted">${nextAppointment.doctor.specialty.name}</p>
                        </div>
                    </div>
                    <hr>
                    <div>
                        <h6 class="text-muted">Motif</h6>
                        <p>${nextAppointment.reason}</p>
                    </div>
                    <div class="d-flex gap-2">
                        <a href="${pageContext.request.contextPath}/appointments/${nextAppointment.id}"
                           class="btn btn-primary">
                            <i class="bi bi-eye"></i> Voir détails
                        </a>
                        <button class="btn btn-outline-danger" data-bs-toggle="modal"
                                data-bs-target="#cancelModal">
                            <i class="bi bi-x-circle"></i> Annuler
                        </button>
                    </div>
                </div>
            </div>
        </c:if>

        <div class="card shadow">
            <div class="card-header bg-success text-white d-flex justify-content-between align-items-center">
                <h5 class="mb-0"><i class="bi bi-clock-history"></i> Historique des rendez-vous</h5>
                <a href="${pageContext.request.contextPath}/appointments/new" class="btn btn-sm btn-light">
                    <i class="bi bi-plus-circle"></i> Nouveau rendez-vous
                </a>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>Date</th>
                            <th>Docteur</th>
                            <th>Spécialité</th>
                            <th>Statut</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${appointments}" var="appointment">
                            <tr>
                                <td><fmt:formatDate value="${appointment.appointmentDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                                <td>Dr. ${appointment.doctor.firstName} ${appointment.doctor.lastName}</td>
                                <td>${appointment.doctor.specialty.name}</td>
                                <td>
                                        <span class="badge bg-${appointment.status == 'COMPLETED' ? 'success' :
                                                                 appointment.status == 'SCHEDULED' ? 'primary' :
                                                                 appointment.status == 'CANCELLED' ? 'danger' : 'warning'}">
                                                ${appointment.status}
                                        </span>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/appointments/${appointment.id}"
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
    </div>

    <div class="col-md-4">
        <div class="card shadow mb-4">
            <div class="card-header bg-info text-white">
                <h5 class="mb-0"><i class="bi bi-search"></i> Trouver un docteur</h5>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/doctors/search" method="get">
                    <div class="mb-3">
                        <label for="specialty" class="form-label">Spécialité</label>
                        <select class="form-select" id="specialty" name="specialtyId">
                            <option value="">Toutes les spécialités</option>
                            <c:forEach items="${specialties}" var="specialty">
                                <option value="${specialty.id}">${specialty.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">
                        <i class="bi bi-search"></i> Rechercher
                    </button>
                </form>
            </div>
        </div>

        <div class="card shadow mb-4">
            <div class="card-header bg-warning text-white">
                <h5 class="mb-0"><i class="bi bi-info-circle"></i> Mes informations</h5>
            </div>
            <div class="card-body">
                <table class="table table-sm table-borderless">
                    <tbody>
                    <tr>
                        <th>CIN:</th>
                        <td>${patient.cin}</td>
                    </tr>
                    <tr>
                        <th>Groupe sanguin:</th>
                        <td><span class="badge bg-danger">${patient.bloodType}</span></td>
                    </tr>
                    <tr>
                        <th>Téléphone:</th>
                        <td>${patient.phone}</td>
                    </tr>
                    </tbody>
                </table>
                <a href="${pageContext.request.contextPath}/profile" class="btn btn-outline-primary w-100">
                    <i class="bi bi-pencil"></i> Modifier mon profil
                </a>
            </div>
        </div>

        <div class="card shadow">
            <div class="card-header bg-secondary text-white">
                <h5 class="mb-0"><i class="bi bi-question-circle"></i> Besoin d'aide ?</h5>
            </div>
            <div class="card-body">
                <p class="mb-2">Contactez-nous pour toute question</p>
                <ul class="list-unstyled">
                    <li><i class="bi bi-telephone"></i> +212 5XX-XXXXXX</li>
                    <li><i class="bi bi-envelope"></i> contact@clinique.ma</li>
                    <li><i class="bi bi-clock"></i> Lun-Ven: 8h-18h</li>
                </ul>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
