"use strict";

const fs = require("fs");
const path = require("path");

const Sequelize = require("sequelize");

function init(dbConfig, options) {
  if (!options) options = {};
  const db = {};
  let config = require("./config/config.json").development;

  if (dbConfig) config = dbConfig;
  else console.error("no db creds passed! using default local env creds for DB");

  const projectName = config.project || "dwimorberg";

  const sequelize = configureSequelize(config, options);


  const modelSets = {
    dwimorberg: "models",
  };
  const modelFolder = modelSets[projectName] || "models";
  const modelPath = `${__dirname}/${modelFolder}/`;

  const models = {};

  // Load each model file
  fs
    .readdirSync(modelPath)
    .filter(
      file => file.indexOf(".") !== 0 && file.slice(-3) === ".js" && file !== "base-model.js"
    )
    .forEach(file => {
      const model = require(path.join(modelPath, file));
      models[model.name] = model.init(sequelize);
    });

  // Load model associations
  Object.keys(models).forEach(modelName => {
    typeof models[modelName].associate === "function" && models[modelName].associate(models);
  });

  db.models = models;
  db.sequelize = sequelize;
  db.closeConnections = sequelize.close.bind(sequelize);

  // db contains all models by file name, e.g. "db.user"
  return Promise.resolve(db);
};

function configureSequelize(config, options) {
  const sequelize = new Sequelize(
    config.database,
    config.username || config.user,
    config.password,
    Object.assign({}, config, {
      host: config.host,
      port: config.port || 5432,
      dialect: "postgres",
      logging: !!process.env.DB_DEBUG,
      define: {
        paranoid: true,
        timestamps: true,
      },
      pool: options.pool || {
        max: 1,
        min: 0,
        idle: 5000,
      },
    })
  );
  return sequelize;
};

module.exports = init;
