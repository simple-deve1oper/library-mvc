document.addEventListener('DOMContentLoaded', () => {
    var date = new Date();
    var day = date.getDate();
    if (day < 10) {
		day = "0" + day;
	}
    var month = date.getMonth() + 1;
    var year = date.getFullYear();
    var maxDay = document.getElementById('birthDate');
    maxDay.max = year + "-" + month + "-" + day;
});