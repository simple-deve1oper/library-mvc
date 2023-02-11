const form = document.getElementById('form');

form.addEventListener('submit', (event) => {
    let result = validateInputs();
    
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

const isValidName = name => {
	const regularExpression = new RegExp("^[А-Я]{1}[а-я]+$");
	return regularExpression.test(String(name));
};

const isValidEmail = email => {
    const re = /[a-z0-9]+\@[a-z]+\.[a-z]+/;
    return re.test(String(email).toLowerCase());
};

const isValidPhoneNumber = phoneNumber => {
	const regularExpression = new RegExp("^\\+7\\([0-9]{1,3}\\)[0-9]{3}-[0-9]{2}-[0-9]{2}$");
	return regularExpression.test(String(phoneNumber));
};

const dateComparison = date => {
	let inputDate = new Date(date);
	let todayDate = new Date();
	let comparisonResult = inputDate > todayDate;
	
	return comparisonResult;
};

const validateInputs = () => {
    const lastNameValue = lastName.value.trim();
    const firstNameValue = firstName.value.trim();
    const middleNameValue = middleName.value.trim();
    const birthDateValue = birthDate.value.trim();
    const emailValue = email.value.trim();
    const phoneNumberValue = phoneNumber.value.trim();

    let lastNameVerificationResult = false;
    if (lastNameValue === '') {
        setError(lastName, 'Поле \'Фамилия\' не может быть пустым');
    } else if (!isValidName(lastNameValue)) {
		setError(lastName, 'Поле \'Фамилия\' может содержать только русские буквы и первая буква должна быть заглавной');
	} else {
        setSuccess(lastName);
        lastNameVerificationResult = true;
    }
    
    let firstNameVerificationResult = false;
    if (firstNameValue === '') {
        setError(firstName, 'Поле \'Имя\' не может быть пустым');
    } else if (!isValidName(firstNameValue)) {
		setError(firstName, 'Поле \'Имя\' может содержать только русские буквы и первая буква должна быть заглавной');
	} else {
        setSuccess(firstName);
        firstNameVerificationResult = true;
    }
    
    let middleNameVerificationResult = true;
    if (middleNameValue !== '') {
		if (!isValidName(middleNameValue)) {
			setError(middleName, 'Поле \'Отчество\' может содержать только русские буквы и первая буква должна быть заглавной');
			middleNameVerificationResult = false;
		} else {
			setSuccess(middleName);
		}
	}
	
	let birthDateVerificationResult = false;
	if (birthDateValue === '') {
		setError(birthDate, 'Поле \'Дата рождения\' не может быть пустым');
	} else if (dateComparison(birthDateValue)) {
		setError(birthDate, 'Поле \'Дата рождения\' не может быть больше сегодняшней даты');
	} else {
		setSuccess(birthDate);
		birthDateVerificationResult = true;
	}
	
	let emailVerificationResult = true;
	if (emailValue !== '') {
		if (!isValidEmail(emailValue)) {
			setError(email, 'Поле \'Email\' должно соответствовать следующему паттерну: test@localhost.org');
			emailVerificationResult = false;
		} else {
			setSuccess(email);
		}
	}
	
	let phoneNumberVerificationResult = false;
	if (phoneNumberValue === '') {
        setError(phoneNumber, 'Поле \'Номер телефона\' не может быть пустым');
    } else if (!isValidPhoneNumber(phoneNumberValue)) {
		setError(phoneNumber, 'Поле \'Номер телефона\' должно соответствовать следующему паттерну: +7(999)999-99-99');
	} else {
        setSuccess(phoneNumber);
        phoneNumberVerificationResult = true;
    }
    
    var generalResult = lastNameVerificationResult && firstNameVerificationResult && middleNameVerificationResult && 
    	birthDateVerificationResult && emailVerificationResult && phoneNumberVerificationResult;
    return generalResult;
}
		
