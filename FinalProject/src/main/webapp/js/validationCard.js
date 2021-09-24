var regexCardNumber = /^[0-9]{12,16}$/;
var regexCvv = /^[0-9]{3}$/;
var regexExpiredDate = /^[0-9]{4}\-(0[1-9]|1[012])\-(0[1-9]|[12][0-9]|3[01])$/;
var regexAmount = /^[0-9]{1,6}$/;

var cardNumberMessage = "Please enter valid card number";
var cvvMessage = "Please enter valid cvv";
var expiredDateMessage = "Please enter valid expired date(yyyy-mm-dd)";
var amountMessage = "Please enter valid amount(less than 1 million)";
var cardNumberMessageRu = "\u041f\u043e\u0436\u0430\u043b\u0443\u0439\u0441\u0442\u0430\u002c\u0020\u0432\u0432\u0435\u0434\u0438\u0442\u0435\u0020\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u044b\u0439\u0020\u043d\u043e\u043c\u0435\u0440\u0020\u043a\u0430\u0440\u0442\u044b\u0020";
var cvvMessageRu = "\u041f\u043e\u0436\u0430\u043b\u0443\u0439\u0441\u0442\u0430\u002c\u0020\u0432\u0432\u0435\u0434\u0438\u0442\u0435\u0020\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u044b\u0439\u0020\u0063\u0076\u0076";
var expiredDateMessageRu = "\u041f\u043e\u0436\u0430\u043b\u0443\u0439\u0441\u0442\u0430\u002c\u0020\u0432\u0432\u0435\u0434\u0438\u0442\u0435\u0020\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u0443\u044e\u0020\u0434\u0430\u0442\u0443(yyyy-mm-dd)";
var amountMessageRu = "\u041f\u043e\u0436\u0430\u043b\u0443\u0439\u0441\u0442\u0430\u002c\u0020\u0432\u0432\u0435\u0434\u0438\u0442\u0435\u0020\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u0443\u044e\u0020\u0441\u0443\u043c\u043c\u0443\u0028\u0434\u043e\u0020\u0031\u0020\u043c\u0438\u043b\u043b\u0438\u043e\u043d\u0430\u0029";

const validation = ()=> {
    let cardNumber = $("input[name='cardNumber']").val();
    let cvv = $("input[name='cvv']").val();
    let expiredDate = $("input[name='expiredDate']").val();
    let amount = $("input[name='amount']").val();
    return checker(cardNumber, cvv, expiredDate, amount);
}

const checker = (cardNumber, cvv, expiredDate, amount) => {
    var flag = true;
    const array = new Array(document.getElementById('cardNumber'), document.getElementById('cvv'),
    document.getElementById('expiredDate'), document.getElementById('amount'));
    var count = 0;
    const fieldToRegexMap = new Map();
    fieldToRegexMap.set(regexCardNumber, cardNumber);
    fieldToRegexMap.set(regexCvv, cvv);
    fieldToRegexMap.set(regexExpiredDate, expiredDate);
    fieldToRegexMap.set(regexAmount, amount);

    const fieldToMessageMap = new Map();
    var lang = $("body").attr("data-lang");
    if(lang === 'ru'){
    fieldToMessageMap.set(regexCardNumber, cardNumberMessageRu);
    fieldToMessageMap.set(regexCvv, cvvMessageRu);
    fieldToMessageMap.set(regexExpiredDate, expiredDateMessageRu);
    fieldToMessageMap.set(regexAmount, amountMessageRu);
    }else{
    fieldToMessageMap.set(regexCardNumber, cardNumberMessage);
    fieldToMessageMap.set(regexCvv, cvvMessage);
    fieldToMessageMap.set(regexExpiredDate, expiredDateMessage);
    fieldToMessageMap.set(regexAmount, amountMessage);
    }
    var date = new Date();
    var userDate = new Date(expiredDate);

    fieldToRegexMap.forEach((key, value) => {
        var lang = $("body").attr("data-lang");
        if (!new RegExp(value).test(key)) {
            array[count].innerHTML = fieldToMessageMap.get(value);
            flag = false;
        } else if(date > userDate){
        if(lang === 'ru'){
        array[2].innerHTML = "\u041d\u0435\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u0430\u044f\u0020\u0434\u0430\u0442\u0430";
        }else if(lang === 'en'){
        array[2].innerHTML = "Incorrect date";
        }
        } else {
            array[count].innerHTML = "";
        }
        count++;
    });
    return flag;

}
