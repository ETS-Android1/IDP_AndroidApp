const connection = require("../services/db");

var express = require('express');
var router = express.Router();

/* Retrieve option. :p */
router.post('/retrieveOption', function(req, res, next) {

    let qnId = req.body.qnId
    let optionLetter = req.body.optionLetter

    let sql = `select * from options where qnId = ${qnId} and optionLetter = '${optionLetter}'`
  
    connection.query(sql, (err, data, fields) => {
      if (err) throw err
      
        let optionData = null
        optionData = data[0]

        res.status(200).json({
        status: "success",
        length: data?.length,
        optionData: optionData,
        });
    })
    
  });

  module.exports = router;