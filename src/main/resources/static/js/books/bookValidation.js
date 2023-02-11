const form = document.getElementById('form');

form.addEventListener('submit', (event) => {
    let result = validateInputs();
    console.log(result);

    if (result) {
		form.submit();
	} else {
		event.preventDefault();
	}
});

const setError = (element, message) => {
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.error');

    errorDisplay.innerText = message;
    inputControl.classList.add('error');
    inputControl.classList.remove('success');
};

const setSuccess = element => {
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.error');

    errorDisplay.innerText = '';
    inputControl.classList.add('success');
    inputControl.classList.remove('error');
};

const isValidName = nameBook => {
	const regularExpression = new RegExp("^.{1,255}$");
	return regularExpression.test(String(nameBook));
};

const isValidPublicationYearNumbers = publicationYear => {
    const regularExpression = new RegExp("^\\d+$");
    return regularExpression.test(String(publicationYear));
};

const isValidPublicationYearComparison = publicationYear => {
    publicationYear = Number(publicationYear);
    let todayDate = new Date();
    let year = todayDate.getFullYear();
	let comparisonResult = publicationYear > year;

	return comparisonResult;
};

const validateInputs = () => {
    const nameValue = nameBook.value.trim();
    const authorIdValue = authorId.value.trim();
    const publisherIdValue = publisherId.value.trim();
    const publicationYearValue = publicationYear.value.trim();

    console.log(authorIdValue);
    let nameVerificationResult = false;
    if (nameValue === '') {
        setError(nameBook, 'Поле \'Наименование\' не может быть пустым');
    } else if (!isValidName(nameValue)) {
		setError(nameBook, 'Поле \'Наименование\' может содержать от 1 до 255 символов');
	} else {
        setSuccess(nameBook);
        nameVerificationResult = true;
    }

    let authorIdVerificationResult = false;
    if (authorIdValue === "") {
        setError(authorId, 'Поле \'Автор\' не может быть пустым');
    } else {
        setSuccess(authorId);
        authorIdVerificationResult = true;
    }

    let publisherIdVerificationResult = false;
    if (publisherIdValue === "") {
        setError(publisherId, 'Поле \'Издательство\' не может быть пустым');
    } else {
        setSuccess(publisherId);
        publisherIdVerificationResult = true;
    }

    let publicationYearVerificationResult = false;
    if (publicationYearValue === '') {
        setError(publicationYear, 'Поле \'Год издания\' не может быть пустым');
    } else if (!isValidPublicationYearNumbers(publicationYearValue)) {
		setError(publicationYear, 'Поле \'Год издания\' может содержать только числа');
	} else if (isValidPublicationYearComparison(publicationYearValue)) {
        setError(publicationYear, 'Поле \'Год издания\' не может быть больше нынешнего года');
    } else {
        setSuccess(publicationYear);
        publicationYearVerificationResult = true;
    }

    var generalResult = nameVerificationResult && authorIdVerificationResult && publisherIdVerificationResult && publicationYearVerificationResult;
    return generalResult;
}