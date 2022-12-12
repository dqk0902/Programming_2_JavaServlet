/**
 * 
 */
function main() {
	let url = "http://localhost:8080/WebAppExercises/addStudent";
	let form = document.forms["formStudent"];
	let requestParameters =
		"id=" + form["id"].value +
		"&firstname=" + form["firstname"].value +
		"&lastname=" + form["lastname"].value +
		"&street=" + form["street"].value +
		"&postcode=" + form["postcode"].value +
		"&postoffice=" + form["postoffice"].value;
	let requestOptions = {
		method: "POST",
		headers: { "Content-Type": "application/x-www-form-urlencoded" },
		body: requestParameters
	};
	fetch(url, requestOptions)
		.then(response => {
			if (response.ok) {
				return response.json();
			} else {
				throw "HTTP status code is " + response.status;
			}
		})
		.then(status => processResponseStatus(status))
		.catch(errorText => console.error("Fetch failed: " + errorText));
}
function processResponseStatus(status) {
	if (status.errorCode === 0) {
		console.log("Student data added.");
	}
	else if (status.errorCode === 1) {
		console.log("Nothing added. The student id is already in use");
	} else {
		console.log("Nothing added. An unknown error occurred.");
	}
}
main();
