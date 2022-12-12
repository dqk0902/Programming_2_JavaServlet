/**
 * 
 */
/**
* 
*/
function getDataFromServer(url, printStudents) {
	fetch(url)
		.then(response => { // 1.
			if (response.ok) { // 2.
				return response.json(); // 3.
			} else {
				throw "HTTP status code is " + response.status;
			}

		})
		.then(studentList => printStudents(studentList)) // 5.
		.catch(errorText => alert("getDataFromServer failed: " + errorText)); // 6.
}
function printStudents(studentList) {
	var table = "";

	for (var i in studentList) {
		table += "<tr>";
		table +=
			"<td>" + studentList[i].id + "</td>" +
			"<td>" + studentList[i].lastname + "</td>"
			+ "<td>" + studentList[i].firstname + "</td>" +
			"<td>" + studentList[i].streetaddress + "</td>" +
			"<td>" + studentList[i].postcode + "</td>" +
			"<td>" + studentList[i].postoffice + "</td>";
		table += "</tr>";
	}

	document.getElementById("id").innerHTML = table;

}

getDataFromServer("http://localhost:8080/WebAppExercises/students", printStudents);
