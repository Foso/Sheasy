'use strict';


/**
 * Get device info
 * Returns a list of the installed apps
 *
 * returns DeviceResponse
 **/
exports.aa = function() {
  return new Promise(function(resolve, reject) {
    var examples = {};
    examples['application/json'] = {
  "name" : "YouTube",
  "packageName" : "com.google.android.youtube",
  "instalTime" : "YouTube"
};
    if (Object.keys(examples).length > 0) {
      resolve(examples[Object.keys(examples)[0]]);
    } else {
      resolve();
    }
  });
}


/**
 * Get installed apps
 * Returns a list of the installed apps
 *
 * returns AppsResponse
 **/
exports.getPetById = function() {
  return new Promise(function(resolve, reject) {
    var examples = {};
    examples['application/json'] = {
  "name" : "YouTube",
  "packageName" : "com.google.android.youtube",
  "instalTime" : "YouTube"
};
    if (Object.keys(examples).length > 0) {
      resolve(examples[Object.keys(examples)[0]]);
    } else {
      resolve();
    }
  });
}

