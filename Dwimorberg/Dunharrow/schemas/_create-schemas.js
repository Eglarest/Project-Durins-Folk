"use strict";

const definition = require("sequelize-json-schema");
const db = require("../index")();
const path = require("path");
const fs = require("fs");

function replaceAll(str, find, replace) {
  return str.replace(new RegExp(escapeRegExp(find), "g"), replace);
}

function escapeRegExp(str) {
  return str.replace(/([.*+?^=!:${}()|\[\]\/\\])/g, "\\$1");
}

function writeSchema(filename, obj) {
  const fs = require("fs");
  let data = JSON.stringify(obj);

  // Hacking the sequelize-json-schema:
  // * Replacing type any to Object
  // * removing format for the integers

  data = replaceAll(data, '"any"', '"object"');
  data = replaceAll(data, ',"format":"int32"', "");
  data = replaceAll(data, ',"format":"int64"', "");
  fs.writeFile(`../schemas/${filename}`, data, function(err) {
    if (err) return console.log(err);

    console.log("create schema ", filename);
  });
}

db
  .then(res => {
    fs
      .readdirSync("../models/")
      .filter(
        file =>
          // ignore files without extension and this index.js file
          file.indexOf(".") !== 0 && file !== "index.js"
      )
      .forEach(file => {
        // import the files into sequelize and add the model to the exported db
        const model = res.sequelize.import(path.join("../models", file));
        writeSchema(`${model.name}.json`, definition(res[model.name]));
      });
  })
  .catch(err => console.error(`Error creating schemas : ${JSON.stringify(err, null, 2)}`));
