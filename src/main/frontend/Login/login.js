function validate () {
    const x = document.getElementById("fname").value;
    if (x.includes("emp")) {
        window.open("EmployeeDashboard.html");
    } else if (x.includes("cust")) {
        window.open("CustomerDashboard.html");
    }
}