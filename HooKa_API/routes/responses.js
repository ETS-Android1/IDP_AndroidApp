const connection = require("../services/db");

var express = require('express');
var router = express.Router();

/* Retrieve user's respond to a particular question. :p */
router.post('/userQuestionResponse', function(req, res, next) {

    let qnId = req.body.qnId
    let userId = req.body.userId
    let sessionId = req.body.sessionId

    let sql = `select * from response where qnId = ${qnId} and userId = ${userId} and sessionId = ${sessionId}`
  
    connection.query(sql, (err, data, fields) => {
      if (err) throw err
      
        let responseData = null
        responseData = data[0]

        res.status(200).json({
        status: "success",
        length: data?.length,
        responseData: responseData,
        });
    })
    
  });

  module.exports = router;