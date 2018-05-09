const Koa = require("koa");
const Router = require("koa-router");
const bodyParser = require("koa-bodyparser");
const cors = require("@koa/cors");
const serve = require("koa-static");
const mount = require("koa-mount");
const assert = require("assert");
const path = require("path");
const proxy = require("koa-proxy");

const app = new Koa();
const login = new Koa();
const main = new Koa();

const controller = new Router();
const router = new Router();
const master = new Router();

var PATH_1 = path.join(__dirname, "/login");
var PATH_2 = path.join(__dirname, "..", "/Mirrormere/dist");
var $HOST = "http://localhost:5002/";

var USER = {};

/* ROUTES USING MOUNT */
/*
controller.all("/submit", async (ctx, next) => {
  // TODO: call real API to check if user info is valid
  USER = {
    user: "nick",
    password: "pass",
  };
  if (USER.user) {
    await app.use(mount("/", main));

    await next();
    ctx.redirect("/");
  } else {
    ctx.redirect("/login");
  }
});

main.use(master.routes());
login.use(router.routes());

login.use(serve(PATH_1));

main.use(serve(PATH_2));
// main.listen(5001, "127.0.0.1");

// controller.all("/submit", async (ctx, next) => {
//   // TODO: call real API to check if user info is valid
//   USER = {
//     user: "nick",
//     password: "pass",
//   };
//   if (USER.user) {
//     await app.use(proxy({ host: $HOST }));
//
//     await next();
//     ctx.redirect("/");
//   } else {
//     ctx.redirect("/login");
//   }
// });

controller.all("/", async (ctx, next) => {
  if (USER.user === "nick") await app.use(mount("/", main));
  else ctx.redirect("/login");
  await next();
});

controller.all("/login", async (ctx, next) => {
  // await login.use(router.routes());
  await app.use(mount("/login", login));
  await next();
});

app.use(controller.routes());

app.listen(8008, "127.0.0.1");
console.log("app is running at http://localhost:8008");
 */

/* ------- -------- ------- */
/* ROUTES USING PROXY */

main.use(master.routes());
// login.use(master.routes());
app.use(master.routes());

login.use(router.routes());

login.use(mount("/login", serve(PATH_1)));
login.listen(5002, "127.0.0.1");

main.use(mount("/", serve(PATH_2)));
main.listen(5001, "127.0.0.1");

controller.all("/submit", async (ctx, next) => {
  console.log("Running login submit function to handle making things awsome");
  // TODO: call real API to check if user info is valid
  USER = {
    user: "nick",
    password: "pass",
  };
  $HOST = "http://localhost:5001/";
  if (USER.user) {
    ctx.redirect("/");
  } else {
    ctx.redirect("/login");
  }
});

controller.all("/", async (ctx, next) => {
  $HOST = "http://localhost:5001/";
  if (USER.user) {
    console.log("Starting main app");
    await main.use(mount("/", serve(PATH_2)));
    await login.use(proxy({ host: "http://localhost:5001/" }));
    await next();
  } else {
    ctx.redirect("/login");
  }
});

controller.all("/login", async (ctx, next) => {
  $HOST = "http://localhost:5002/";
  await login.use(mount("/login", serve(PATH_1)));
  await app.use(proxy({ host: $HOST }));
  await next();
});

app.use(controller.routes());

app.listen(8008, "127.0.0.1");
console.log("app is running at http://localhost:8008");
