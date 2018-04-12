'use strict';

var utils = require('../utils/writer.js');
var Apps = require('../service/AppsService');

module.exports.aa = function aa (req, res, next) {
  Apps.aa()
    .then(function (response) {
      utils.writeJson(res, response);
    })
    .catch(function (response) {
      utils.writeJson(res, response);
    });
};

module.exports.getPetById = function getPetById (req, res, next) {
  Apps.getPetById()
    .then(function (response) {
      utils.writeJson(res, response);
    })
    .catch(function (response) {
      utils.writeJson(res, response);
    });
};
