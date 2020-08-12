var exec = require('cordova/exec');

function OpenOverlayPermissionPlugin() {}

OpenOverlayPermissionPlugin.prototype.openOverlayPermission = function (options, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "OpenOverlayPermissionPlugin", "openOverlayPermission", options);
  };

  OpenOverlayPermissionPlugin.prototype.canDrawOverlays = function (options, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "OpenOverlayPermissionPlugin", "canDrawOverlays", options);
  };

  OpenOverlayPermissionPlugin.prototype.isMIUI = function (options, successCallback, errorCallback) {
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