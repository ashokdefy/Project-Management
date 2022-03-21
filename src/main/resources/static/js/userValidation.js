if(errorMessageJS) {
	let niceErrorMessage = decodeHtml(errorMessageJS);
	document.getElementById('validationError').innerHTML = niceErrorMessage;
}

function decodeHtml(html) {
	let txt = document.createElement("textarea");
	txt.innerHTML = html
	return txt.value;
}