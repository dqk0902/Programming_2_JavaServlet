/**
 * 
 */
function getDataFromServer(url, printStudents) {
	fetch(url)
		.then(response => { // 1.
			if (response.ok) { // 2.
				return response.json() // 3.
			}
		})
		.then(studentList => printStudents(studentList)) // 5.
		.catch(errorText => console.error("Fetch failed: " + errorText)); // 6.
}