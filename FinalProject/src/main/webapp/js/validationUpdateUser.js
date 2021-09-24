var regexName =/^[\u0430-\u044f\u0410-\u042f\u0401\u0451\u042c\u044c\u042a\u044a\u042b\u044ba-zA-Z]{2,25}$/;
var regexSurname =/^[\u0430-\u044f\u0410-\u042f\u0401\u0451\u042c\u044c\u042a\u044a\u042b\u044ba-zA-Z]{2,25}$/;
var regexPassword = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,12}$/;
var regexEmail = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;

var nameMessage = "Please enter valid name";
var surnameMessage = "Please enter valid surname";
var nameMessageRu = "\u041f\u043e\u0436\u0430\u043b\u0443\u0439\u0441\u0442\u0430\u002c\u0020\u0432\u0432\u0435\u0434\u0438\u0442\u0435\u0020\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u043e\u0435\u0020\u0438\u043c\u044f\u0020";
var surnameMessageRu = "\u041f\u043e\u0436\u0430\u043b\u0443\u0439\u0441\u0442\u0430\u002c\u0020\u0432\u0432\u0435\u0434\u0438\u0442\u0435\u0020\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u043e\u0435\u0020\u0444\u0430\u043c\u0438\u043b\u0438\u044e";
const validation = ()=> {
    let name = $("input[name='name']").val();
    let surname = $("input[name='surname']").val();
    return checker(name, surname);
}

const checker = (name, surname) => {
    var flag = true;
    const array = new Array(document.getElementById('name'), document.getElementById('surname'));
    var count = 0;
    const fieldToRegexMap = new Map();
    fieldToRegexMap.set(regexName, name);
    fieldToRegexMap.set(regexSurname, surname);
    const fieldToMessageMap = new Map();
    var lang = $("body").attr("data-lang");
        if(lang === 'ru'){
        fieldToMessageMap.set(regexName, nameMessageRu);
            fieldToMessageMap.set(regexSurname, surnameMessageRu);
        }else{
    fieldToMessageMap.set(regexName, nameMessage);
    fieldToMessageMap.set(regexSurname, surnameMessage);
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
