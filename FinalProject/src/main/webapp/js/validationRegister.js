var regexName =/^[\u0430-\u044f\u0410-\u042f\u0401\u0451\u042c\u044c\u042a\u044a\u042b\u044ba-zA-Z]{2,25}$/;
var regexSurname =/^[\u0430-\u044f\u0410-\u042f\u0401\u0451\u042c\u044c\u042a\u044a\u042b\u044ba-zA-Z]{2,25}$/;
var regexPassword = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,12}$/;
var regexEmail = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;

var nameMessage = "Please enter valid name";
var surnameMessage = "Please enter valid surname";
var emailMessage = "Please enter valid email";
var passwordMessage = "Please enter valid password(Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters)";
var nameMessageRu = "\u041f\u043e\u0436\u0430\u043b\u0443\u0439\u0441\u0442\u0430\u002c\u0020\u0432\u0432\u0435\u0434\u0438\u0442\u0435\u0020\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u043e\u0435\u0020\u0438\u043c\u044f\u0020";
var surnameMessageRu = "\u041f\u043e\u0436\u0430\u043b\u0443\u0439\u0441\u0442\u0430\u002c\u0020\u0432\u0432\u0435\u0434\u0438\u0442\u0435\u0020\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u043e\u0435\u0020\u0444\u0430\u043c\u0438\u043b\u0438\u044e";
var emailMessageRu = "\u041f\u043e\u0436\u0430\u043b\u0443\u0439\u0441\u0442\u0430\u002c\u0020\u0432\u0432\u0435\u0434\u0438\u0442\u0435\u0020\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u0443\u044e\u0020\u043f\u043e\u0447\u0442\u0443";
var passwordMessageRu = "\u041f\u043e\u0436\u0430\u043b\u0443\u0439\u0441\u0442\u0430\u002c\u0020\u0432\u0432\u0435\u0434\u0438\u0442\u0435\u0020\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u044b\u0439\u0020\u043f\u0430\u0440\u043e\u043b\u044c\u0020\u0028\u043e\u0442\u0020\u0038\u0020\u0441\u0438\u043c\u0432\u043e\u043b\u044b\u002c\u0020\u0441\u043e\u0434\u0435\u0440\u0436\u0438\u0442\u0020\u0446\u0438\u0444\u0440\u044b\u0020\u0438\u0020\u0431\u0443\u043a\u0432\u044b\u0020\u0432\u0435\u0440\u0445\u043d\u0435\u0433\u043e\u0020\u0438\u0020\u043d\u0438\u0436\u043d\u0435\u0433\u043e\u0020\u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0430\u0029";
const validation = ()=> {
    let name = $("input[name='name']").val();
    let surname = $("input[name='surname']").val();
    let email = $("input[name='email']").val();
    let password = $("input[name='password']").val();
    return checker(name, surname, email, password);
}

const checker = (name, surname, email, password) => {
    var flag = true;
    const array = new Array(document.getElementById('name'), document.getElementById('surname'),
    document.getElementById('email'), document.getElementById('password'));
    var count = 0;
    const fieldToRegexMap = new Map();
    fieldToRegexMap.set(regexName, name);
    fieldToRegexMap.set(regexSurname, surname);
    fieldToRegexMap.set(regexEmail, email);
    fieldToRegexMap.set(regexPassword, password);

    const fieldToMessageMap = new Map();
    var lang = $("body").attr("data-lang");
        if(lang === 'ru'){
        fieldToMessageMap.set(regexName, nameMessageRu);
            fieldToMessageMap.set(regexSurname, surnameMessageRu);
            fieldToMessageMap.set(regexEmail, emailMessageRu);
            fieldToMessageMap.set(regexPassword, passwordMessageRu);
        }else{
    fieldToMessageMap.set(regexName, nameMessage);
    fieldToMessageMap.set(regexSurname, surnameMessage);
    fieldToMessageMap.set(regexEmail, emailMessage);
    fieldToMessageMap.set(regexPassword, passwordMessage);
    }
    fieldToRegexMap.forEach((key, value) => {
        if (!new RegExp(value).test(key)) {
            array[count].innerHTML = fieldToMessageMap.get(value);
            flag = false;
        } else {
            array[count].innerHTML = "";
        }
        count++;
    });
    return flag;

}
