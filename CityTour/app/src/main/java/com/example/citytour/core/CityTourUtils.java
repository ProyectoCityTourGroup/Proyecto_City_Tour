package com.example.citytour.core;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.citytour.fragments.FragmentHandler;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by alvaro on 14/09/15.
 */
public class CityTourUtils {

    private static File tempPhoto;
    private static Uri imageUri;
    private static final int TAKE_PICTURE = 1;
    private static Activity activity;

    public CityTourUtils(Activity activity){
        CityTourUtils.activity = activity;
    }

    public static void cameraClick(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            tempPhoto = createTemporaryFile();
            if (tempPhoto != null) {
                tempPhoto.delete();
            }
        } catch (Exception e){
            Toast toast = Toast.makeText(CityTour.getContext(), "Unable to create temporary file.", Toast.LENGTH_SHORT);
            toast.show();
        }
        if (tempPhoto != null) {
            imageUri = Uri.fromFile(tempPhoto);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

            if (takePictureIntent.resolveActivity(CityTour.getContext().getPackageManager()) != null) {
                activity.startActivityForResult(takePictureIntent, TAKE_PICTURE);
            }
        } else {
            Toast toast = Toast.makeText(CityTour.getContext(), "Unable to create temporary file.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private static File createTemporaryFile() throws Exception {
        if (isExternalStorageWritable()) {
            File tempDir = Environment.getExternalStorageDirectory();
            tempDir = new File(tempDir.getAbsolutePath()+"/.temp/");
            if(!tempDir.exists()) {
                tempDir.mkdir();
            }
            return File.createTempFile("picture", ".jpg", tempDir);
        } else {
            return null;
        }
    }

    public static void grabImage() {
        CityTour.getContext().getContentResolver().notifyChange(imageUri, null);
        ContentResolver cr = CityTour.getContext().getContentResolver();
        Bitmap bitmap;
        try {
            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, imageUri);
            createImageFile(bitmap);
        } catch (Exception e) {
            Toast toast = Toast.makeText(CityTour.getContext(), "Unable to create file.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private static void createImageFile(Bitmap bitmap) throws IOException {

        // Convert bitmap to JPEG and store it in a byte array
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
        byte[] bitmapdata = bytes.toByteArray();

        // Generate file name and directory paths
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        if (isExternalStorageWritable()){
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "City Tour";
            // Generate storage directory
            File storageDir = new File(path);
            if (!storageDir.exists()) {
                storageDir.mkdir();
            }

            // Create file to store the image
            File imageFile = new File(path + File.separator + imageFileName);
            imageFile.createNewFile();

            // Write image data to image file
            FileOutputStream output = new FileOutputStream(imageFile);
            output.write(bitmapdata);

            // Make image file visible from gallery
            String mCurrentPhotoPath = "file:" + imageFile.getAbsolutePath();
            CityTour.getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(mCurrentPhotoPath)));
            output.close();
        } else {
            Toast toast = Toast.makeText(CityTour.getContext(), "Unable to access external storage.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static ArrayList<LatLng> getCoordinates(String[] coordinates){
        ArrayList<LatLng> coord = new ArrayList<>();
        for (String coordinate : coordinates) {
            String[] aux = coordinate.split(",");
            LatLng latLng = new LatLng(Double.parseDouble(aux[0]), Double.parseDouble(aux[1]));
            coord.add(latLng);
        }
        return coord;
    }

    public static LatLng getCoordinates(String coordinates){
        String[] aux = coordinates.split(",");
        return new LatLng(Double.parseDouble(aux[0]), Double.parseDouble(aux[1]));
    }

    public static void quizzAndInfo(Context context, String name, String url){
        Intent intent = new Intent(context, FragmentHandler.class);
        Bundle extras = new Bundle();
        extras.putString("checkpoint", name);
        extras.putString("url", url);
        intent.putExtras(extras);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
