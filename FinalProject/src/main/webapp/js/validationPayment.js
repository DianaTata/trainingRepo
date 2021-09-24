var regexToCount = /^[0-9]{4}$/;
var regexAmount = /^[0-9]{1,6}$/;

var toCountMessage = "Please enter valid count number";
var amountMessage = "Please enter valid amount(less than 1 million)";
var toCountMessageRu = "\u041f\u043e\u0436\u0430\u043b\u0443\u0439\u0441\u0442\u0430\u002c\u0020\u0432\u0432\u0435\u0434\u0438\u0442\u0435\u0020\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u044b\u0439\u0020\u043d\u043e\u043c\u0435\u0440\u0020\u0441\u0447\u0435\u0442\u0430\u0020";
var amountMessageRu = "\u041f\u043e\u0436\u0430\u043b\u0443\u0439\u0441\u0442\u0430\u002c\u0020\u0432\u0432\u0435\u0434\u0438\u0442\u0435\u0020\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u0443\u044e\u0020\u0441\u0443\u043c\u043c\u0443\u0028\u0434\u043e\u0020\u0031\u0020\u043c\u0438\u043b\u043b\u0438\u043e\u043d\u0430\u0029";

const validation = ()=> {
    let toCount = $("input[name='toCount']").val();
    let amount = $("input[name='amount']").val();
    return checker(toCount, amount);
}

const checker = (toCount, amount) => {
    var flag = true;
    const array = new Array(document.getElementById('toCount'), document.getElementById('amount'));
    var count = 0;
    const fieldToRegexMap = new Map();
    fieldToRegexMap.set(regexToCount, toCount);
    fieldToRegexMap.set(regexAmount, amount);

    const fieldToMessageMap = new Map();
    var lang = $("body").attr("data-lang");
        if(lang === 'ru'){
        fieldToMessageMap.set(regexToCount, toCountMessageRu);
            fieldToMessageMap.set(regexAmount, amountMessageRu);
        }else{
    fieldToMessageMap.set(regexToCount, toCountMessage);
    fieldToMessageMap.set(regexAmount, amountMessage);
    }
    var fromCount = $("select[name='fromCount']").val();
    fieldToRegexMap.forEach((key, value) => {
        if (!new RegExp(value).test(key)) {
            array[count].innerHTML = fieldToMessageMap.get(value);
            flag = false;
        } else if(fromCount == toCount){
        array[0].innerHTML = "~~~";
        }else {
            array[count].innerHTML = "";
        }
        count++;
    });
    return flag;

}
