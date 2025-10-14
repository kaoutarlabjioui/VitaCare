<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>VitaCare - Gestion de Clinique Moderne</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        :root {
            --primary-color: #0066cc;
            --secondary-color: #00a8e8;
            --accent-color: #00c9ff;
            --dark-color: #1a1a2e;
            --light-color: #f8f9fa;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .navbar {
            background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
            padding: 1rem 0;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .navbar-brand {
            font-size: 1.8rem;
            font-weight: 700;
            color: white !important;
        }

        .navbar-brand i {
            color: var(--accent-color);
        }

        .nav-link {
            color: rgba(255,255,255,0.9) !important;
            font-weight: 500;
            margin: 0 0.5rem;
            transition: all 0.3s;
        }

        .nav-link:hover {
            color: white !important;
            transform: translateY(-2px);
        }

        .btn-outline-light:hover {
            background-color: white;
            color: var(--primary-color);
        }

        .hero-section {
            background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
            color: white;
            padding: 100px 0;
            position: relative;
            overflow: hidden;
        }

        .hero-section::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320"><path fill="rgba(255,255,255,0.1)" d="M0,96L48,112C96,128,192,160,288,160C384,160,480,128,576,122.7C672,117,768,139,864,138.7C960,139,1056,117,1152,106.7C1248,96,1344,96,1392,96L1440,96L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z"></path></svg>') no-repeat bottom;
            background-size: cover;
        }

        .hero-content {
            position: relative;
            z-index: 1;
        }

        .hero-title {
            font-size: 3.5rem;
            font-weight: 700;
            margin-bottom: 1.5rem;
            line-height: 1.2;
        }

        .hero-subtitle {
            font-size: 1.3rem;
            margin-bottom: 2rem;
            opacity: 0.95;
        }

        .btn-hero {
            padding: 15px 40px;
            font-size: 1.1rem;
            font-weight: 600;
            border-radius: 50px;
            margin: 0.5rem;
            transition: all 0.3s;
        }

        .btn-hero:hover {
            transform: translateY(-3px);
            box-shadow: 0 10px 25px rgba(0,0,0,0.2);
        }

        .section-title {
            font-size: 2.5rem;
            font-weight: 700;
            color: var(--dark-color);
            margin-bottom: 1rem;
            position: relative;
            display: inline-block;
        }

        .section-title::after {
            content: '';
            position: absolute;
            bottom: -10px;
            left: 50%;
            transform: translateX(-50%);
            width: 60px;
            height: 4px;
            background: linear-gradient(90deg, var(--primary-color), var(--accent-color));
            border-radius: 2px;
        }

        .feature-card {
            border: none;
            border-radius: 15px;
            padding: 2rem;
            height: 100%;
            transition: all 0.3s;
            background: white;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
        }

        .feature-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 15px 35px rgba(0,0,0,0.15);
        }

        .feature-icon {
            width: 80px;
            height: 80px;
            border-radius: 50%;
            background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 1.5rem;
            font-size: 2rem;
            color: white;
        }

        .stats-section {
            background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
            color: white;
            padding: 60px 0;
        }

        .stat-item {
            text-align: center;
        }

        .stat-number {
            font-size: 3rem;
            font-weight: 700;
            margin-bottom: 0.5rem;
        }

        .stat-label {
            font-size: 1.1rem;
            opacity: 0.9;
        }

        .about-section {
            padding: 80px 0;
            background-color: var(--light-color);
        }

        .contact-section {
            padding: 80px 0;
        }

        .contact-info {
            background: white;
            padding: 2rem;
            border-radius: 15px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
            height: 100%;
        }

        .contact-info i {
            font-size: 2rem;
            color: var(--primary-color);
            margin-bottom: 1rem;
        }

        footer {
            background: var(--dark-color);
            color: white;
            padding: 40px 0 20px;
        }

        footer a {
            color: rgba(255,255,255,0.7);
            text-decoration: none;
            transition: color 0.3s;
        }

        footer a:hover {
            color: white;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark sticky-top">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">
            <i class="bi bi-heart-pulse-fill"></i> VitaCare
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto align-items-center">
                <li class="nav-item">
                    <a class="nav-link" href="#accueil">Accueil</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#services">Services</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#apropos">À Propos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#contact">Contact</a>
                </li>
                <li class="nav-item ms-3">
                    <a href="${pageContext.request.contextPath}/auth?action=login" class="btn btn-outline-light">
                        <i class="bi bi-box-arrow-in-right"></i> Connexion
                    </a>
                </li>
                <li class="nav-item ms-2">
                    <a href="${pageContext.request.contextPath}/auth?action=register" class="btn btn-light">
                        <i class="bi bi-person-plus"></i> S'inscrire
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>


<section id="accueil" class="hero-section">
    <div class="container">
        <div class="row align-items-center">
            <div class="col-lg-6 hero-content">
                <h1 class="hero-title">Votre Santé, Notre Priorité</h1>
                <p class="hero-subtitle">
                    Système de gestion de clinique moderne pour une prise en charge médicale optimale.
                    Prenez rendez-vous en ligne et accédez à vos dossiers médicaux en toute sécurité.
                </p>
                <div>
                    <a href="${pageContext.request.contextPath}/action=register" class="btn btn-light btn-hero">
                        <i class="bi bi-person-plus"></i> Créer un Compte
                    </a>
                    <a href="#services" class="btn btn-outline-light btn-hero">
                        <i class="bi bi-info-circle"></i> En Savoir Plus
                    </a>
                </div>
            </div>
            <div class="col-lg-6 text-center d-none d-lg-block">
                <img src="/placeholder.svg?height=400&width=500"
                     alt="VitaCare Clinic" class="img-fluid" style="filter: drop-shadow(0 20px 40px rgba(0,0,0,0.3));">
            </div>
        </div>
    </div>
</section>

<section class="stats-section">
    <div class="container">
        <div class="row">
            <div class="col-md-3 col-6 mb-4 mb-md-0">
                <div class="stat-item">
                    <div class="stat-number">500+</div>
                    <div class="stat-label">Patients Satisfaits</div>
                </div>
            </div>
            <div class="col-md-3 col-6 mb-4 mb-md-0">
                <div class="stat-item">
                    <div class="stat-number">50+</div>
                    <div class="stat-label">Médecins Experts</div>
                </div>
            </div>
            <div class="col-md-3 col-6">
                <div class="stat-item">
                    <div class="stat-number">15+</div>
                    <div class="stat-label">Spécialités</div>
                </div>
            </div>
            <div class="col-md-3 col-6">
                <div class="stat-item">
                    <div class="stat-number">24/7</div>
                    <div class="stat-label">Service Disponible</div>
                </div>
            </div>
        </div>
    </div>
</section>


<section id="services" class="py-5">
    <div class="container">
        <div class="text-center mb-5">
            <h2 class="section-title">Nos Services</h2>
            <p class="text-muted mt-4">Des solutions complètes pour votre santé</p>
        </div>
        <div class="row g-4">
            <div class="col-md-4">
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="bi bi-calendar-check"></i>
                    </div>
                    <h4 class="text-center mb-3">Prise de Rendez-vous</h4>
                    <p class="text-muted text-center">
                        Réservez vos consultations en ligne facilement. Choisissez votre médecin,
                        la date et l'heure qui vous conviennent.
                    </p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="bi bi-file-medical"></i>
                    </div>
                    <h4 class="text-center mb-3">Dossier Médical</h4>
                    <p class="text-muted text-center">
                        Accédez à votre historique médical complet en toute sécurité.
                        Consultations, prescriptions et résultats d'examens.
                    </p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="bi bi-people"></i>
                    </div>
                    <h4 class="text-center mb-3">Équipe Médicale</h4>
                    <p class="text-muted text-center">
                        Une équipe de médecins qualifiés et expérimentés dans diverses spécialités
                        pour répondre à tous vos besoins.
                    </p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="bi bi-clock-history"></i>
                    </div>
                    <h4 class="text-center mb-3">Disponibilité</h4>
                    <p class="text-muted text-center">
                        Consultez les disponibilités en temps réel de nos médecins et
                        choisissez le créneau qui vous convient le mieux.
                    </p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="bi bi-shield-check"></i>
                    </div>
                    <h4 class="text-center mb-3">Sécurité</h4>
                    <p class="text-muted text-center">
                        Vos données médicales sont protégées avec les plus hauts standards
                        de sécurité et de confidentialité.
                    </p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="bi bi-bell"></i>
                    </div>
                    <h4 class="text-center mb-3">Notifications</h4>
                    <p class="text-muted text-center">
                        Recevez des rappels pour vos rendez-vous et restez informé
                        de l'évolution de votre suivi médical.
                    </p>
                </div>
            </div>
        </div>
    </div>
</section>

<section id="apropos" class="about-section">
    <div class="container">
        <div class="row align-items-center">
            <div class="col-lg-6 mb-4 mb-lg-0">
                <img src="/placeholder.svg?height=400&width=500"
                     alt="VitaCare Building" class="img-fluid rounded shadow">
            </div>
            <div class="col-lg-6">
                <h2 class="section-title mb-4">À Propos de VitaCare</h2>
                <p class="lead mb-4">
                    VitaCare est une plateforme moderne de gestion de clinique qui révolutionne
                    la façon dont les patients et les professionnels de santé interagissent.
                </p>
                <p class="text-muted mb-4">
                    Notre mission est de rendre les soins de santé plus accessibles, plus efficaces
                    et plus personnalisés. Grâce à notre système de gestion intégré, nous facilitons
                    la prise de rendez-vous, le suivi médical et la communication entre patients et médecins.
                </p>
                <div class="row">
                    <div class="col-6 mb-3">
                        <div class="d-flex align-items-center">
                            <i class="bi bi-check-circle-fill text-primary fs-4 me-2"></i>
                            <span>Plateforme Sécurisée</span>
                        </div>
                    </div>
                    <div class="col-6 mb-3">
                        <div class="d-flex align-items-center">
                            <i class="bi bi-check-circle-fill text-primary fs-4 me-2"></i>
                            <span>Interface Intuitive</span>
                        </div>
                    </div>
                    <div class="col-6 mb-3">
                        <div class="d-flex align-items-center">
                            <i class="bi bi-check-circle-fill text-primary fs-4 me-2"></i>
                            <span>Support 24/7</span>
                        </div>
                    </div>
                    <div class="col-6 mb-3">
                        <div class="d-flex align-items-center">
                            <i class="bi bi-check-circle-fill text-primary fs-4 me-2"></i>
                            <span>Équipe Qualifiée</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

Contact Section
<section id="contact" class="contact-section">
    <div class="container">
        <div class="text-center mb-5">
            <h2 class="section-title">Contactez-Nous</h2>
            <p class="text-muted mt-4">Nous sommes là pour répondre à vos questions</p>
        </div>
        <div class="row g-4">
            <div class="col-md-4">
                <div class="contact-info text-center">
                    <i class="bi bi-geo-alt-fill"></i>
                    <h5 class="mb-3">Adresse</h5>
                    <p class="text-muted">123 Avenue de la Santé<br>Casablanca, Maroc</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="contact-info text-center">
                    <i class="bi bi-telephone-fill"></i>
                    <h5 class="mb-3">Téléphone</h5>
                    <p class="text-muted">+212 5XX-XXXXXX<br>+212 6XX-XXXXXX</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="contact-info text-center">
                    <i class="bi bi-envelope-fill"></i>
                    <h5 class="mb-3">Email</h5>
                    <p class="text-muted">contact@vitacare.ma<br>support@vitacare.ma</p>
                </div>
            </div>
        </div>
    </div>
</section>

Footer
<footer>
    <div class="container">
        <div class="row">
            <div class="col-md-4 mb-4 mb-md-0">
                <h5 class="mb-3">
                    <i class="bi bi-heart-pulse-fill text-primary"></i> VitaCare
                </h5>
                <p class="text-muted">
                    Votre partenaire santé de confiance. Une plateforme moderne pour
                    une gestion optimale de vos soins médicaux.
                </p>
            </div>
            <div class="col-md-4 mb-4 mb-md-0">
                <h5 class="mb-3">Liens Rapides</h5>
                <ul class="list-unstyled">
                    <li class="mb-2"><a href="#accueil">Accueil</a></li>
                    <li class="mb-2"><a href="#services">Services</a></li>
                    <li class="mb-2"><a href="#apropos">À Propos</a></li>
                    <li class="mb-2"><a href="#contact">Contact</a></li>
                </ul>
            </div>
            <div class="col-md-4">
                <h5 class="mb-3">Suivez-Nous</h5>
                <div class="d-flex gap-3">
                    <a href="#" class="fs-4"><i class="bi bi-facebook"></i></a>
                    <a href="#" class="fs-4"><i class="bi bi-twitter"></i></a>
                    <a href="#" class="fs-4"><i class="bi bi-instagram"></i></a>
                    <a href="#" class="fs-4"><i class="bi bi-linkedin"></i></a>
                </div>
            </div>
        </div>
        <hr class="my-4" style="border-color: rgba(255,255,255,0.1);">
        <div class="text-center">
            <p class="mb-0 text-muted">&copy; 2025 VitaCare. Tous droits réservés.</p>
        </div>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Smooth scrolling for anchor links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
        });
    });
</script>
</body>
</html>
