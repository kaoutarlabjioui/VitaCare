// Auto-dismiss alerts after 5 seconds
document.addEventListener("DOMContentLoaded", () => {
    const alerts = document.querySelectorAll(".alert")
    const bootstrap = window.bootstrap // Declare the bootstrap variable
    alerts.forEach((alert) => {
        setTimeout(() => {
            const bsAlert = new bootstrap.Alert(alert)
            bsAlert.close()
        }, 5000)
    })
})

// Confirm appointment
/*function confirmAppointment(appointmentId) {
    if (confirm("Confirmer ce rendez-vous ?")) {
        fetch(`/appointments/${appointmentId}/confirm`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
        })
            .then((response) => response.json())
            .then((data) => {
                if (data.success) {
                    location.reload()
                } else {
                    alert("Erreur lors de la confirmation")
                }
            })
    }
}*/

// Assign doctor
function assignDoctor(requestId) {
    // Implementation for assigning doctor
    window.location.href = `/appointments/new?requestId=${requestId}`
}

// Contact patient
function contactPatient(requestId) {
    // Implementation for contacting patient
    alert("Fonctionnalité de contact en cours de développement")
}
// Form validation
;(() => {
    const forms = document.querySelectorAll(".needs-validation")
    Array.from(forms).forEach((form) => {
        form.addEventListener(
            "submit",
            (event) => {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }
                form.classList.add("was-validated")
            },
            false,
        )
    })
})()

// Password confirmation validation
const passwordField = document.getElementById("password")
const confirmPasswordField = document.getElementById("confirmPassword")

if (passwordField && confirmPasswordField) {
    confirmPasswordField.addEventListener("input", () => {
        if (passwordField.value !== confirmPasswordField.value) {
            confirmPasswordField.setCustomValidity("Les mots de passe ne correspondent pas")
        } else {
            confirmPasswordField.setCustomValidity("")
        }
    })
}
