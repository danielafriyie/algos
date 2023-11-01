"use strict";

/**
 * @param {Integer} n 
 * @returns {Integer}
 */
function factorial(n) {
    if (n < 0)
        throw new Error("Expects value greater than zero!");
    else if (n <= 1)
        return 1;
    else if (n === 2)
        return 2;
    return n * factorial(n - 1);
}


console.log(factorial(1));
console.log(factorial(2));
console.log(factorial(5));
console.log(factorial(10));
