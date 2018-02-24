

//        var form = document.querySelector('.registration');
//        var fields = form.querySelectorAll('.field');
//
//        form.addEventListener('submit', function (event) {
//            event.preventDefault();
//
//            var errors = form.querySelectorAll('.error');
//
//            for (var i = 0; i < errors.length; i++) {
//                errors[i].remove();
//            }
//
//            for (var i = 0; i < fields.length; i++) {
//                if (!fields[i].value) {
//                    console.log('field is blank', fields[i]);
//                    var error = document.createElement('div');
//                    error.className = 'error';
//                    error.style.color = 'red';
//                    error.innerHTML = 'Cannot be blank';
//                    form[i].parentElement.insertBefore(error, fields[i]);
//                }
//            }
//        })

function checkedForm(form) {
    var valid = true;
    if(form.email.length < 4){
        alert('В email должно быть не менее 4-х символов!');
        form.email.focus();
        valid = false;
    };
    if(form.email.value == ""){
        alert('Не указан email!');
        form.email.focus();
        valid = false;
    };

    if(!(/^[a-zA-Z0-9_\-\.]+@[a-zA-Z0-9\-]+\.[a-zA-Z0-9\-\.]+$/.test(form.email.value))){
        alert("Проверьте e-mail!");
        form.email.focus();
        valid = false;
    };

    if(!(/^[a-zA-Z0-9]+$/.test(form.login.value))){
        alert('Login должен состоять из комбинации латинских букв и арабских цифр!');
        form.login.focus();
        valid = false;
    };

    return valid;
}


