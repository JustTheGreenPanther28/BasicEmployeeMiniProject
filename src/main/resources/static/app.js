let employees = [];

async function fetchEmployees(page, size){
    try{
        let response = await fetch(`http://localhost:8080/employee?page=${page}&size=${size}`);
        if(response.ok){
            let data  = await response.json();
            employees = data.content;
        } else {
            alert("Error fetching employees");
        }
    }
    catch(error){
        alert("Error fetching employees: " + error.message);
    }
    return employees;
}

async function init() {
    let employeeValues = await fetchEmployees(0, 10);
    let tb = document.getElementById("table-body");
    for (let employee of employeeValues) {
        let newRow = `
<tr>
    <td><input type="checkbox" name="employeeSelect" value="${employee.employeeId}"></td>
    <td>${employee.employeeId}</td>
    <td>${employee.employeeName}</td>
    <td>${employee.employeeAge}</td>
    <td>${employee.position}</td>
    <td>${employee.salary}</td>
    <td>${new Date(employee.joinDate).toLocaleDateString()}</td>
    <td><button class="reportToBtn" data-id="${employee.employeeId}">Report To</button></td>
</tr>
`;
        tb.insertAdjacentHTML("beforeend", newRow);
    }

    document.querySelectorAll(".reportToBtn").forEach(btn => {
        btn.addEventListener("click", async () => {
            let id = btn.dataset.id;
            const response = await fetch(`http://localhost:8080/employee/report/${id}`);
            if (response.ok) {
                let data = await response.json();
                console.log("Reports:", data);
                alert(JSON.stringify(data, null, 2)); // replace with proper UI later
            } else {
                alert("Failed to fetch report");
            }
        });
    });
}

document.addEventListener("DOMContentLoaded", init);

let addBtn = document.getElementById("add");
addBtn.addEventListener("click", ()=>{
    // window.location.href = "add.html";
    window.open("add.html", "iframe", "width=600,height=400");
});

let deleteBtn = document.getElementById("delete");
deleteBtn.addEventListener("click", async ()=>{
    alert("Delete button clicked!");
    let checkboxes = document.querySelectorAll('input[name="employeeSelect"]:checked');
    let ids = [];
    for(let checkbox of checkboxes){
        ids.push(checkbox.value);
    }
    for(let id of ids){
        alert(`Deleting employee with ID: ${id}`);
        const response = await fetch(`http://localhost:8080/employee/${id}`,{
            method: "DELETE",
        })
        if(response.ok){
            alert(`Employee with ID ${id} deleted!`);
        }else{
            alert(`Failed to delete employee with ID ${id}: ${response.statusText}`);
        }
    }
    location.reload();
});