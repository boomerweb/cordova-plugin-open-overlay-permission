var exec = require('cordova/exec');

var OpenOverlayPermissionPlugin = function () { };

OpenOverlayPermissionPlugin.openOverlayPermission = function (options, successCallback, errorCallback) {
  exec(successCallback, errorCallback, "OpenOverlayPermissionPlugin", "openOverlayPermission", options);
};

OpenOverlayPermissionPlugin.canDrawOverlays = function (options, successCallback, errorCallback) {
  exec(successCallback, errorCallback, "OpenOverlayPermissionPlugin", "canDrawOverlays", options);
};

OpenOverlayPermissionPlugin.isMIUI = function (options, successCallback, errorCallback) {
  exec(successCallback, errorCallback, "OpenOverlayPermissionPlugin", "isMIUI", options);
};

module.exports = OpenOverlayPermissionPlugin;
