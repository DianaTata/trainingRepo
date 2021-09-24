var regexPassword = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,12}$/;
var regexEmail = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;

var emailMessage = "Please enter valid email";
var passwordMessage = "Please enter valid password(Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters)";
var emailMessageRu = "\u041f\u043e\u0436\u0430\u043b\u0443\u0439\u0441\u0442\u0430\u002c\u0020\u0432\u0432\u0435\u0434\u0438\u0442\u0435\u0020\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u0443\u044e\u0020\u043f\u043e\u0447\u0442\u0443";
var passwordMessageRu = "\u041f\u043e\u0436\u0430\u043b\u0443\u0439\u0441\u0442\u0430\u002c\u0020\u0432\u0432\u0435\u0434\u0438\u0442\u0435\u0020\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u044b\u0439\u0020\u043f\u0430\u0440\u043e\u043b\u044c\u0020\u0028\u043e\u0442\u0020\u0038\u0020\u0441\u0438\u043c\u0432\u043e\u043b\u044b\u002c\u0020\u0441\u043e\u0434\u0435\u0440\u0436\u0438\u0442\u0020\u0446\u0438\u0444\u0440\u044b\u0020\u0438\u0020\u0431\u0443\u043a\u0432\u044b\u0020\u0432\u0435\u0440\u0445\u043d\u0435\u0433\u043e\u0020\u0438\u0020\u043d\u0438\u0436\u043d\u0435\u0433\u043e\u0020\u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0430\u0029";
const validation = ()=> {
    let email = $("input[name='email']").val();
    let password = $("input[name='password']").val();
    return checker(email, password);
}

const checker = (email, password) => {
    var flag = true;
    const array = new Array(document.getElementById('email'), document.getElementById('password'));
    var count = 0;
    const fieldToRegexMap = new Map();
    fieldToRegexMap.set(regexEmail, email);
    fieldToRegexMap.set(regexPassword, password);

    const fieldToMessageMap = new Map();
    var lang = $("body").attr("data-lang");
        if(lang === 'ru'){
        fieldToMessageMap.set(regexEmail, emailMessageRu);
        fieldToMessageMap.set(regexPassword, passwordMessageRu);
        }else{
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
