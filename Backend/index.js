/**
 * Import function triggers from their respective submodules:
 *
 * const {onCall} = require("firebase-functions/v2/https");
 * const {onDocumentWritten} = require("firebase-functions/v2/firestore");
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

const {onRequest} = require("firebase-functions/v2/https");
const logger = require("firebase-functions/logger");
const functions = require("firebase-functions")
const admin = require("firebase-admin");
const serviceAccount = require("./permission.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://truong-ac24d-default-rtdb.asia-southeast1.firebasedatabase.app"
});
const express = require("express")
const app = express()
const cors = require("cors")
// upload 
const formidable = require('formidable');
const bodyParser = require("body-parser");
const { object } = require("firebase-functions/v1/storage");
// var database = firebase.database();

const db = admin.database()
dbRef = admin.database().ref()
// const storageRef = admin.storage().ref();
app.use(cors({origin: true} ) )
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
// routes
app.get("/", (req, res, next) =>
  res.json({message: "Firebase function service is working kaka"})
);
// create
const roomUrl = "https://firebasestorage.googleapis.com/v0/b/truong-ac24d.appspot.com/o/bed%20(1).png?alt=media&token=5fb0e82b-e064-45da-93ce-e1ab8c045a0c"
const user2Url = "https://firebasestorage.googleapis.com/v0/b/truong-ac24d.appspot.com/o/user%20(1).png?alt=media&token=6b24dd65-f75b-43d1-99f3-bb74427b7f76"
const user1Url = "https://firebasestorage.googleapis.com/v0/b/truong-ac24d.appspot.com/o/support.png?alt=media&token=75fa17e7-83b7-4b80-a29f-bfd57e3e9650"
// add room
let cost;
app.post('/room/add', (req, res)=>{
  var roomObj={
    reserveID: "",
    receptionID: "",
    number: "",
    type: "",
    name: "",
    status: 0,
    cost: 0,
    description: "",
    image: ""
  }
  roomObj.reserveID = req.body.reserveID;
  roomObj.number = req.body.number;
  roomObj.type = req.body.type;
  roomObj.name = req.body.name;
  roomObj.status = req.body.status;
  roomObj.description = req.body.description;
  roomObj.cost = req.body.cost;
  roomObj.image = roomUrl;
  console.log(req.body)
  var postListRef = admin.database().ref('room');
  var newPostRef = postListRef.push();
  newPostRef.set(roomObj);
  res.status(200).send("OK")
})
// add user
app.post('/user/add', (req,res)=>{
  var userObj={
    name: "",
    numberphone: "",
    email: "",
    username: "",
    password: "",
    role: 0,
    img: "",
    address: "",
    salary: 0,
    birthday:"",
    hiredate: "",
    session: ""
  }
  if(req.body.role==2){
    userObj.name=req.body.name
    userObj.email=req.body.email
    userObj.numberphone=req.body.numberphone
    userObj.username=req.body.username
    userObj.password=req.body.password
    userObj.role=req.body.role
    userObj.img=user2Url
  }
  else if(req.body.role==1){
    userObj.name=req.body.name
    userObj.email=req.body.email
    userObj.numberphone=req.body.numberphone
    userObj.username=req.body.username
    userObj.password=req.body.password
    userObj.role=req.body.role
    userObj.address=req.body.address
    userObj.salary=req.body.salary
    userObj.birthday=req.body.birthday
    userObj.hiredate=req.body.hiredate
    userObj.img=user1Url
  }
  var postListRef = admin.database().ref('user');
  var newPostRef = postListRef.push();
  newPostRef.set(userObj);
  res.status(200).send("OK")
});

// upload user images
app.post('/user/images', (req, res)=>{
  const form = formidable.formidable({multiple: true});
  form.parse(req, (err, fields, files)=>{
    if(err){
      res.status(500).json({
        success: false,
        error: err
      });
    }
    else {
      console.log(files.file.name)
      res.status(300).send("Cc")
    }
  });
});
// add reservation
app.post('/reservation/add',(req,res)=>{
  reserveObj={
    userID: "",
    roomType: "",
    roomNumber: "",
    checkIn: "",
    checkOut: "",
    status: 0,
    moreRequire: "",
    totalPrice: 0
  }
  reserveObj.userID = req.body.userID
  reserveObj.roomType = req.body.roomType
  reserveObj.checkIn = req.body.checkIn
  reserveObj.checkOut = req.body.checkOut
  reserveObj.status = req.body.status
  reserveObj.moreRequire = req.body.moreRequire
  reserveObj.totalPrice = req.body.totalPrice
  reserveObj.roomNumber = req.body.roomNumber

  var postListRef = admin.database().ref('reservation');
  var newPostRef = postListRef.push();
  newPostRef.set(reserveObj);
  res.status(200).json({
    key: newPostRef.key
  });
});
// checkout
app.post('/checkout', (req, res)=>{
  var invoiceObj={
    userID: "",
    checkIn: "",
    checkOut: "",
    roomNumber: "",
    date: "",
    total: "",
    review: ""
  }
  var reserveID = req.body.reserveID;
  var userID, checkIn, checkOut, roomNumber;
  dbRef.child('reservation').get().then((snapshot)=>{
    if(snapshot.exists()){
      snapshot.forEach((child)=>{
        if(child.key==reserveID){
          userID = child.val().userID;
          checkIn = child.val().checkIn;
          checkOut = child.val().checkOut;
          roomNumber = child.val().roomNumber;
          invoiceObj.userID = userID;
          invoiceObj.checkIn = checkIn;
          invoiceObj.checkOut = new Date().toISOString();
          invoiceObj.roomNumber = roomNumber;
          invoiceObj.date = new Date().toISOString(); 
          invoiceObj.review = req.body.review;
          // calculate total
          var startDate = new Date(invoiceObj.checkIn).getTime();
          var endDate = new Date(invoiceObj.checkOut).getTime();
          var millisecondsPerDay = 1000 * 60 * 60 * 24;
          var dayDifference = Math.floor((endDate - startDate) / millisecondsPerDay);
          // set status room to empty
          dbRef.child('room').get().then((snapshot)=>{
            if(snapshot.exists()){
              snapshot.forEach((child)=>{
                if(child.val().number==roomNumber){
                  var obj = child.val();
                  obj.status=1;
                  cost = child.val().cost;
                  invoiceObj.total = cost*dayDifference
                  
                // Delete reservation after check out
                  var reserveRef = db.ref('/reservation/'+reserveID);
                  reserveRef.remove();
                // add invoice
                  var postListRef = admin.database().ref('invoice');
                  var newPostRef = postListRef.push();
                  newPostRef.set(invoiceObj);
                  res.status(200).json({
                    key: newPostRef.key
                  });
                  console.log(invoiceObj)
                  admin.database().ref('/room/'+child.key).set(obj, (err)=>{
                    if(err){
                      console.log('err'+err)
                    }
                  });
                }
              });
            }
          });
        }
      });
    }
    else {
      res.status(300).send("NULL RESERVATION")
    }
  });
});
// checkin
app.post('/checkin', (req, res)=>{
  var reserveID = req.body.reserveID;
  dbRef.child("reservation").get().then((snapshot)=>{
    snapshot.forEach((child)=>{
      if(child.key==reserveID){
        var obj = child.val();
        obj.status = "1";
        dbRef.child("room").get().then((snapshot1)=>{
          snapshot1.forEach((child1)=>{
            if(obj.roomType==child1.val().type&&child1.val().status=="1"){
              obj.roomNumber = child1.val().number;
              obj.checkIn = new Date().toISOString();
              var objRoom = child1.val();
              objRoom.status = "0";
              objRoom.reserveID = reserveID;
              res.status(200).json({
                roomNumber: child1.val().number
              });
              // console.log(child.val())
              // console.log(child1.val())
              admin.database().ref("/room/"+child1.key).set(objRoom, (err)=>{
                if(err){
                  console.log(err);
                }
              });
              admin.database().ref("/reservation/"+reserveID).set(obj, (err)=>{
                if(err){
                  console.log(err);
                }
              });
            }
          });
          // res.status(300).send("No room valid!");
        });
      }
    });
  })
});
// read

// get all users
app.get("/user", (req, res, next) =>
  {
    dbRef.child("user").get().then((snapshot) => {
      if (snapshot.exists()) {
        var listUser = [];
        snapshot.forEach((child)=>{
          var obj = child.val();
          obj = Object.assign({
            key: child.key
          }, obj)
          listUser.push(obj);
        });
        res.status(200).send(listUser)
      } else {
        console.log("No data available");
      }
    }).catch((error) => {
      console.error(error);
    });
  }
);

// get all rooms
app.get("/room", (req, res)=>{
  dbRef.child("room").get().then((snapshot) => {
    var listRoom = []
    if (snapshot.exists()) {
      snapshot.forEach((child)=>{
        listRoom.push({
          key: child.key,
          reserveID: child.val().reserveID,
          number: child.val().number,
          type: child.val().type,
          name: child.val().name,
          status: child.val().status,
          description: child.val().description,
          cost: child.val().cost,
          image: child.val().image
        })
      });
      console.log(listRoom)
      res.status(200).send(listRoom)
    } else {
      console.log("No data available");
    }
  }).catch((error) => {
    console.error(error);
  });
});

// get all reservation
app.get("/reservation",(req, res)=>{
  dbRef.child("reservation").get().then((snapshot) => {
    if (snapshot.exists()) {
      var listReserve = [];
      snapshot.forEach((child)=>{
        listReserve.push({
          key: child.key,
          userID: child.val().userID,
          roomNumber: child.val().roomNumber,
          roomType: child.val().roomType,
          checkIn: child.val().checkIn,
          checkOut: child.val().checkOut,
          status: child.val().status,
          moreRequire: child.val().moreRequire,
          totalPrice: child.val().totalPrice
        });
      });
      res.status(200).send(listReserve)
    } else {
      console.log("No data available");
    }
  }).catch((error) => {
    console.error(error);
  });
}
);
// get all invoices
app.get('/invoice', (req, res)=>{
  dbRef.child('invoice').get().then((snapshot)=>{
    var listInvoice = [];
    snapshot.forEach((child)=>{
      var obj = child.val();
      obj = Object.assign({
        key: child.key
      }, obj)
      listInvoice.push(obj);
    });
    res.status(200).send(listInvoice);
  });
});
// get login
app.post("/user/login",(req, res)=>{
  var usr = req.body.username
  var pass = req.body.password
  dbRef.child("user").get().then((snapshot) => {
    if (snapshot.exists()) {
      snapshot.forEach((child)=>{
        var usrObj
        if(child.val().username==usr&&child.val().password==pass){
          usrObj = child.val()
          usrObj = Object.assign({
            key: child.key
          }, usrObj);
          res.status(200).send(usrObj)
        }
      });
      res.status(300).send("NOT FOUND")
    } else {
      console.log("No data available");
    }
  }).catch((error) => {
    console.error(error);
  });
})
// get reservation by id user
app.get("/reservation/:id",(req, res)=>{
  
  dbRef.child("reservation").get().then((snapshot) => {
    var usrId = req.params.id
    if (snapshot.exists()) {
      snapshot.forEach((child)=>{
        if(child.val().userID==usrId){
          var reserveObj = child.val();
          reserveObj = Object.assign({
            key: child.key
          }, reserveObj);
          res.status(200).send(reserveObj);
        }
      });
      res.status(300).send("No valid user ID")
    } else {
      console.log("No data available");
    }
  }).catch((error) => {
    console.error(error);
  });
});
// get userid by numberphone
app.get("/user/findnumberphone/:id",(req, res)=>{
  dbRef.child("/user").get().then((snapshot)=>{
    if(snapshot.exists()){
      var SDT = req.params.id;
      snapshot.forEach((child)=>{
        if(child.val().numberphone==SDT){
          res.status(200).json({
            key: child.key  
          });
        }
      });
      res.status(300).json({
        error: "No number valid"
      })
    }
    else {
      res.status(300).send("No user")
    }
  })
});
// update
// update room by id
app.put("/room/update/:id",(req, res)=>{
  var roomObj={
    reserveID: "",
    number: "",
    type: "",
    name: "",
    status: 0,
    cost: 0,
    description: "",
    image: ""
  }
  roomObj.reserveID = req.body.reserveID;
  roomObj.number = req.body.number;
  roomObj.type = req.body.type;
  roomObj.name = req.body.name;
  roomObj.status = req.body.status;
  roomObj.description = req.body.description;
  roomObj.cost = req.body.cost;
  roomObj.image = req.body.image;
  console.log(req.params.id)
  admin.database().ref('/room/'+req.params.id).set(roomObj, (error)=>{
    if(error){
      console.log(error)
    } else{
      res.status(200).send('OK')
    }
  })
});

// update user by id role 2
app.put("/user/update/2/:id", (req, res)=>{
  var userObj={
    name: "",
    numberphone: "",
    email: "",
    username: "",
    password: "",
    role: 0,
    img: "",
    address: "",
    salary: 0,
    birthday:"",
    hiredate: "",
    session: ""
  }
  userObj.name=req.body.name
  userObj.email=req.body.email
  userObj.numberphone=req.body.numberphone
  userObj.username=req.body.username
  userObj.password=req.body.password
  userObj.img=req.body.img
  userObj.role=req.body.role
  admin.database().ref('/user/'+req.params.id).set(userObj, (error)=>{
    if(error){
      console.log(error)
    } else{
      res.status(200).send('OK')
    }
  })
});

// update user role 1
app.put("/user/update/:id",(req, res)=>{
  var userObj={
    name: "",
    numberphone: "",
    email: "",
    username: "",
    password: "",
    role: 0,
    img: "",
    address: "",
    salary: 0,
    birthday:"",
    hiredate: "",
    session: ""
  }
  userObj.name=req.body.name
  userObj.email=req.body.email
  userObj.numberphone=req.body.numberphone
  userObj.username=req.body.username
  userObj.password=req.body.password
  userObj.img=req.body.img
  userObj.role=req.body.role
  userObj.address=req.body.address
  userObj.salary=req.body.salary
  userObj.birthday=req.body.birthday
  userObj.hiredate=req.body.hiredate
  admin.database().ref('/user/'+req.params.id).set(userObj, (error)=>{
    if(error){
      console.log(error)
    } else{
      res.status(200).send('OK')
    }
  })
});
// update reservation by user id
app.put('/reservation/update/:id', (req, res)=>{
  reserveObj={
    userID: "",
    roomType: "",
    checkIn: "",
    checkOut: "",
    status: 0,
    moreRequire: "",
    totalPrice: 0
  };
  reserveObj.userID = req.body.userID
  reserveObj.roomType = req.body.roomType
  reserveObj.checkIn = req.body.checkIn
  reserveObj.checkOut = req.body.checkOut
  reserveObj.status = req.body.status
  reserveObj.moreRequire = req.body.moreRequire  
  reserveObj.totalPrice = req.body.totalPrice  
  admin.database().ref('/reservation/'+req.params.id).set(reserveObj, (err)=>{
    if(err){
      res.status(300).json({
        error: err
      })
    }else{
      res.status(200).send("OK")
    }
  })
})
// delete
// delete user
app.delete("/user/delete/:id",(req, res)=>{
  var id = req.params.id;
  var ref = db.ref('/user/'+id)
  ref.remove()
  .then(function(){
    res.status(200).send("OK")
  })
  .catch(function(error){
    res.status(300).send("Delete failed: "+error)
  })
})

// delete reservation
app.delete("/reservation/delete/:id",(req, res)=>{
  var id = req.params.id;
  var ref = db.ref('/reservation/'+id)
  ref.remove()
  .then(function(){
    res.status(200).send("OK")
  })
  .catch(function(error){
    res.status(300).send("Delete failed: "+error)
  })
})
app.get('/demo',(req, res)=>{
  dbRef.child('user').get().then((snapshot)=>{
    snapshot.forEach((child)=>{
      if(child.val().username=="truongvip"){
        console.log(child.val())
        var obj = child.val();
        obj.numberphone='01234567';
        admin.database().ref('/user/-NxHQgy65mgn-gJrDTdH').set(obj, (err)=>{
          if(err){
            console.log(err)
          }
          else {
            res.status(200).send("Modify ok")
          }
        })
      }
    });
    res.status(200).send("OK")
  });
})
exports.api = functions.https.onRequest(app);
