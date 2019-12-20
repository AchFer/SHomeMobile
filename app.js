const express = require('express');
const mysql = require('mysql');
const ejs = require('ejs');
const path = require('path');
var dateTime = require('node-datetime');
var bodyParser = require('body-parser');
var connect = require('connect');
var connectmul = require('connect-multiparty');
var cookiparser = require('cookie-parser');
const app = express();


//Public Folder
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));



//create db
const db = mysql.createConnection({
    host     : 'localhost',
    user     : 'root',
    password : '',
    database : 'malvoyants'
});

db.connect((err) => {
    if(err){
        console.log("saraaaaaaa mefhemtech nnnn");
        console.log(err);
        throw err;
    }
    console.log('Mysql connected....');
});


app.post('/login/:Email/:Password',(req,res) => {

    let Testname = req.params.Email
    console.log(Testname);
    
    db.query('SELECT * FROM utilisateur where Email =?',[req.params.Email],function(err,result,fields){
        console.log("2");
    
        db.on('error',function(err){
              console.log('[MySQL ERROR]',err);
        });
        console.log("3");
    
    if(result && result.length)
    {console.log("4");
    console.log(result);
    console.log(result[0]);
    if(result[0].password == req.params.Password)
    {
        res.json('User connected !!!!');

    }
    else{
        res.json('User Password is wrong !!!!');


    }

}
    else{

        res.json('User email does not exist !!!!');

    }

});

});




app.get('/iduser/:email',(req,res) => {

   
    db.query('SELECT * FROM utilisateur where Email =?',[req.params.email],function(err,result,fields){

        db.on('error',function(err){
            console.log('[MySQL ERROR]',err);
      });

      console.log(result)
      res.json(result)
    });
});




app.get('/idmaison/:utilisateurid',(req,res) => {

   
    db.query('SELECT * FROM maison where utilisateur_id =?',[req.params.utilisateurid],function(err,result,fields){

        db.on('error',function(err){
            console.log('[MySQL ERROR]',err);
      });

      console.log(result)
      res.json(result)
    });
});


app.get('/idnoeud/:idmaison',(req,res) => {

    db.query('SELECT * FROM noeud where maison_id =?',[req.params.idmaison],function(err,result,fields){

        db.on('error',function(err){
            console.log('[MySQL ERROR]',err);
      });

      console.log(result)
      res.json(result)
    });
});





app.post('/adreclamation/:Id_user/:nom/:desc',(req,res)=>{
    let rating = {description:req.params.desc , nom_rec:req.params.nom, utilisateur_id:req.params.Id_user};
    let sql = 'INSERT INTO reclamation SET ?'
    let query = db.query(sql, rating, (err,result) => {
        if(err) throw err;
        console.log(result);
        res.send('Reclamation')
    })
});



app.listen('3000',() => {
    console.log('Server started on port 3000....');
});