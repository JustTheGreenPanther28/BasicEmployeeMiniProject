let submitBtn = document.getElementById("submit-btn");
submitBtn.addEventListener("click", submit);

async function submit() {
    submitBtn.disabled = true;
    submitBtn.textContent = "Submitting...";
    submitBtn.style.backgroundColor = "darkgray";
    submitBtn.style.color = "white";
    submitBtn.style.cursor = "not-allowed";
    submitBtn.style.opacity = "0.6";
    let employeeName = document.getElementById("employeeName").value;
    let employeeAge = document.getElementById("employeeAge").value;
    let position = document.getElementById("position").value;
    let salary = document.getElementById("salary").value;
    let joinDate = document.getElementById("joinDate").value;
    let reportTo = document.getElementById("reportTo").value;

    if (joinDate == undefined || joinDate == "") {
        joinDate = new Date().toISOString();
    }
    if (employeeAge == undefined || employeeAge == "" || isNaN(employeeAge) || employeeAge < 18 || employeeAge >= 100) {
        alert("Please enter a valid age (must be 18 or older).");
        submitBtn.disabled = false;
        return;
    }
    let payload = {
        name: employeeName,
        age: parseInt(employeeAge),
        position: position,
        salary: parseFloat(salary),
        joinDate: joinDate,
        reportTo: reportTo
    }

    const response = await fetch("http://localhost:8080/employee",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

    if (response.ok) {
        submitBtn.disabled = false;
        alert("Employee added!");
    } else {
        const error = await response.json();
        alert("Failed: " + error.message);
        submitBtn.disabled = false;
        submitBtn.textContent = "Submit";
        submitBtn.style.backgroundColor = "";
        submitBtn.style.color = "";
        submitBtn.style.cursor = "";
        submitBtn.style.opacity = "";
    }
}