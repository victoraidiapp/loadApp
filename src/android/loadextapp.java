package com.aidiapp.loadapp;

import java.util.List;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;

public class LoadExtApp extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("cargarTests")) {
            
            this.cargarApp( callbackContext,args.getString(0));
            return true;
        }
        return false;
    }

    private void cargarApp(CallbackContext callbackContext,String paquete) {
    	// Verify it resolves
    	
    	Intent intencion=this.cordova.getActivity().getPackageManager().getLaunchIntentForPackage(paquete);
    	
    	
    		if(this.isAppInstalled(paquete)){
    			
    			this.cordova.getActivity().startActivity(intencion);
    		}else{
    			try {
    				this.cordova.getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+paquete)));
    			} catch (android.content.ActivityNotFoundException anfe) {
    				this.cordova.getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+paquete)));
    			}
    		}
    }
    
    private boolean isAppInstalled(String packageName) {
        PackageManager pm = this.cordova.getActivity().getPackageManager();
        boolean installed = false;
        try {
           pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
           installed = true;
        } catch (PackageManager.NameNotFoundException e) {
           installed = false;
        }
        return installed;
    }

}
