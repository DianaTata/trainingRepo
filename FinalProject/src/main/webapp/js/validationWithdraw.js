var regexAmount = /^[0-9]{1,6}$/;

var amountMessage = "Please enter valid amount(less than 1 million)";
var amountMessageRu = "\u041f\u043e\u0436\u0430\u043b\u0443\u0439\u0441\u0442\u0430\u002c\u0020\u0432\u0432\u0435\u0434\u0438\u0442\u0435\u0020\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u0443\u044e\u0020\u0441\u0443\u043c\u043c\u0443\u0028\u0434\u043e\u0020\u0031\u0020\u043c\u0438\u043b\u043b\u0438\u043e\u043d\u0430\u0029";

const validation = ()=> {
    let amount = $("input[name='amount']").val();
    return checker(amount);
}

const checker = (amount) => {
    var flag = true;
    const array = new Array(document.getElementById('amount'));
    var count = 0;
    const fieldToRegexMap = new Map();
    fieldToRegexMap.set(regexAmount, amount);

    const fieldToMessageMap = new Map();
    var lang = $("body").attr("data-lang");
        if(lang === 'ru'){
        fieldToMessageMap.set(regexAmount, amountMessageRu);
        }else{
    fieldToMessageMap.set(regexAmount, amountMessage);
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
