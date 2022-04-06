const connection = require("../services/db");

var express = require('express');
var router = express.Router();

/* Retrieve user's response to a particular question. :p */
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

  /* compare response to correct answer and update points acordingly. :p */
    router.post('/updatePoints', function(req, res, next) {

        let qnId = req.body.qnId
        let userId = req.body.userId
        let sessionId = req.body.sessionId

        let sql = `select * from response where qnId = ${qnId} and userId = ${userId} and sessionId = ${sessionId}`
    
        connection.query(sql, (err, data, fields) => {
        if (err) throw err
        
            userAnswer = data[0].choice

            sql = `select * from questions where qnId = ${qnId}`
    
            connection.query(sql, (err, data, fields) => {
            if (err) throw err
            
                correctAnswer = data[0].answer

                let points = 0
                if(userAnswer==correctAnswer){
                    points = 10
                }

                sql = `update response set points = ${points} where qnId = ${qnId} and userId = ${userId} and sessionId = ${sessionId}`
    
                connection.query(sql, (err, data, fields) => {
                    if (err) throw err
                    
                        res.status(200).json({
                        status: "success",
                        length: data?.length,
                        });
                    })

            })
        })
        
    });

   /* updating user response. :p */
   router.post('/updateUserResponse', function(req, res, next) {

    let qnId = req.body.qnId
    let userId = req.body.userId
    let sessionId = req.body.sessionId
    let choice = req.body.choice

    let sql = `insert into response (userId, qnId, sessionId, choice) values (${userId}, ${qnId}, ${sessionId}, '${choice}')`

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