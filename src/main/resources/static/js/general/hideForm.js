const switchingBtn = document.querySelector('#switching');
const divForm = document.querySelector('.divForm');

switchingBtn.addEventListener('click', () => {
    if (divForm.style.display === 'none') {
	    divForm.style.display = 'block';
	    switchingBtn.innerHTML = 'Скрыть форму';
	} else {
	    divForm.style.display = 'none';
	    switchingBtn.innerHTML = 'Добавить авторов';
	}
});