const connection = require("../services/db");

var express = require('express');
var router = express.Router();

/* Retrieve question. :p */
router.post('/available', function(req, res, next) {

    let sessionId = req.body.sessionId
    let qnNumber = req.body.qnNumber

    let sql = `select * from questions where sessionId = ${sessionId} and qnNumber = ${qnNumber}`
  
    connection.query(sql, (err, data, fields) => {
      if (err) throw err
      
        let questionData = null
        questionData = data[0]

        res.status(200).json({
        status: "success",
        length: data?.length,
        questionData: questionData,
        });
    })
    
  });

  module.exports = router;