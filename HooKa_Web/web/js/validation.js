/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function validateForm() {

    var m = "^[8-9]{1}[0-9]{7}$";
    var mobile = document.forms["customer"]["mobile"].value;
    var match = mobile.match(m);
    if(match === null) {
        alert("Please enter a valid mobile number (8 digits, starts with 8/9)");
        return false;
    }
    
    var p = "^[0-9]{6}$";
    var postal = document.forms["customer"]["postalCode"].value;
    var match = postal.match(p);
    if(match === null) {
        alert("Please enter a valid postal code (6 digits)");
        return false;
    }
    
    var p1 = document.forms["customer"]["password"].value;
    var p2 = document.forms["customer"]["confirmPassword"].value;
    if(p1 !== p2) {
        alert("Confirm password is not the same as password");
        return false;
    }
}

