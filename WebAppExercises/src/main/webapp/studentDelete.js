/**
 * 
 */
 function main() {
	let url = "http://localhost:8080/WebAppExercises/deleteStudent";
	let form = document.forms["formStudent"];
	let requestParameters =
		"id=" + form["id"].value ;
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
		alert("Student data deleted.");
	}
	else if (status.errorCode === 1) {
		alert("Student data not deleted. Unknown student id!");
	} else {
		alert("The database is temporarily unavailable. Please try again later.");
	}
}
main();
