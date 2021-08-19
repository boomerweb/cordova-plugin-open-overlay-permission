var exec = require('cordova/exec');

function OpenOverlayPermissionPlugin() {}

OpenOverlayPermissionPlugin.openOverlayPermission = function (options, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "OpenOverlayPermissionPlugin", "openOverlayPermission", options);
  };

  OpenOverlayPermissionPlugin.canDrawOverlays = function (options, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "OpenOverlayPermissionPlugin", "canDrawOverlays", options);
  };

  OpenOverlayPermissionPlugin.isMIUI = function (options, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "OpenOverlayPermissionPlugin", "isMIUI", options);
  };
  
  OpenOverlayPermissionPlugin.install = function () {
    if (!window.plugins) {
      window.plugins = {};
    }
  
    window.plugins.OpenOverlayPermissionPlugin = new OpenOverlayPermissionPlugin();
    return window.plugins.OpenOverlayPermissionPlugin;
  };
  
  cordova.addConstructor(OpenOverlayPermissionPlugin.install);

module.exports = OpenOverlayPermissionPlugin;
