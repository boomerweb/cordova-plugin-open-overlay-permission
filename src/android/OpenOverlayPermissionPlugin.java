package cordova.plugin.openoverlaypermission;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import leumit.mobile.BuildConfig;
import leumit.mobile.MainActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import android.content.Context;
import android.app.Activity;
import leumit.mobile.MainActivity;
import android.util.Log;
/**
 * This class echoes a string called from JavaScript.
 */
public class OpenOverlayPermissionPlugin extends CordovaPlugin {
final Integer RequestCode = 9999;
public static CallbackContext mCallbackContext;
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.i("LeumitMobile",  "**** Build.VERSION.SDK_INT: " + Build.VERSION.SDK_INT + "****");
        Log.i("LeumitMobile",  "**** Build.MANUFACTURER: " + Build.MANUFACTURER + "****");

        mCallbackContext = callbackContext;

        if (action.equals("openOverlayPermission")) {
            boolean openOverlayPermissionResult = true;

            try{
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    boolean canDrawOverlays = Settings.canDrawOverlays(cordova.getActivity().getApplicationContext());

                    if (!canDrawOverlays){
                        this.openOverlayPermission();
                    }
                }

                return true;
            }
            catch(Exception ex){
                ex.printStackTrace();
                callbackContext.error(ex.getMessage());
                return false;
            }
        }
        else if (action.equals("canDrawOverlays"))
        {
            boolean canDrawOverlays = true;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                canDrawOverlays = Settings.canDrawOverlays(cordova.getActivity().getApplicationContext());
            }

            callbackContext.success(String.valueOf(canDrawOverlays));

            return true;
        }
        else if (action.equals("isMIUI")){
            boolean isMiui = isMiUi();

            callbackContext.success(String.valueOf(isMiui));

            return true;
        }
        return false;
    }

    private void openOverlayPermission() {
        if (isMiuiWithApi27OrMore()) {
            goToXiaomiPermissions();

            return;
        }
        else {
            // Show alert dialog to the user saying a separate permission is needed
            // Launch the settings activity if the user prefers
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + BuildConfig.APPLICATION_ID));
            cordova.setActivityResultCallback(this);
            cordova.getActivity().startActivityForResult(intent, RequestCode);
            Log.i("LeumitMobile",  "****after cordova.startActivityForResult****");
        }
    }

    private void goToXiaomiPermissions() {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
        intent.putExtra("extra_pkgname", cordova.getActivity().getPackageName());
        cordova.setActivityResultCallback(this);
        cordova.getActivity().startActivityForResult(intent, RequestCode);
    }

    private String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.i("LeumitMobile",  "getSystemProperty -> line: " + line);
        return line;
    }

    private boolean isMiUi() {
        return getSystemProperty("ro.miui.ui.version.name").isEmpty() != true;
    }

    private boolean isMiuiWithApi27OrMore() {
        return isMiUi() && Build.VERSION.SDK_INT >= 27;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCode) {
                boolean openOverlayPermissionResult = true;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    openOverlayPermissionResult = Settings.canDrawOverlays(cordova.getActivity().getApplicationContext());
                }

                mCallbackContext.success(String.valueOf(openOverlayPermissionResult));
        }
    }
}
