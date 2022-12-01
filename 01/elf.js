const fs = require('fs');

const allFileContents = fs.readFileSync("input.txt", 'utf8');

let elves = []

var temp_acc = 0;
var index = 1;

allFileContents.split(/\r?\n/).forEach(line =>  {
    
    if(line == "" || line == null || line == undefined){
        elves.push({"index": index, "calories": temp_acc})
        index++
        temp_acc = 0
    }
    else {
        temp_acc += parseInt(line);
    }

});

let sortedElves = elves.sort((e1, e2) => (e1.calories < e2.calories) ? 1 : (e1.calories > e2.calories) ? -1 : 0);

console.log(sortedElves[0].calories + sortedElves[1].calories + sortedElves[2].calories)

