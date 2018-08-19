package com.jskgmail.indiaskills;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidhiddencamera.CameraConfig;
import com.androidhiddencamera.HiddenCameraActivity;
import com.androidhiddencamera.config.CameraFacing;
import com.androidhiddencamera.config.CameraImageFormat;
import com.androidhiddencamera.config.CameraResolution;
import com.androidhiddencamera.config.CameraRotation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CapturePictureActivity extends HiddenCameraActivity{
    //Upload_candidate_photo_videos
    //"userId
    //api_key
    //batch_id
    //type
    //testID
    //uniqueID"
    int no_pics=0;
    protected static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
    Uri imageUri;
    File photoFile = null;
    File image = null;
    String mCurrentPhotoPath;
    static Camera camera = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_picture);
        CameraConfig   mCameraConfig = new CameraConfig()
                .getBuilder(this)
                .setCameraFacing(CameraFacing.FRONT_FACING_CAMERA)
                .setCameraResolution(CameraResolution.MEDIUM_RESOLUTION)
                .setImageFormat(CameraImageFormat.FORMAT_JPEG)
                .setImageRotation(CameraRotation.ROTATION_270)
                .build();
       // HiddenCameraUtils.openDrawOverPermissionSetting(this);
        Button b=findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

            //Start camera preview
            {            Toast.makeText(getApplicationContext(),"y",Toast.LENGTH_SHORT).show();

                startCamera(mCameraConfig);
          //  takePicture();
            }
        } else {
            Toast.makeText(getApplicationContext(),"nnnnn",Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 101);
        }

   //     takeSnapShots();
    }
    private void takeSnapShots()
    {
        Toast.makeText(getApplicationContext(), "Image snapshot   Started",Toast.LENGTH_SHORT).show();
        // here below "this" is activity context.
        SurfaceView surface = new SurfaceView(this);
//surface=findViewById(R.id.surfaceView);
         camera = Camera.open();
        try {
            camera.setPreviewDisplay(surface.getHolder());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        camera.startPreview();
        camera.takePicture(null,null,jpegCallback);
    }


    /** picture call back */
    Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera)
        {
            FileOutputStream outStream = null;
            try {
                String dir_path = "";// set your directory path here
                outStream = new FileOutputStream(dir_path+File.separator+"TAGScore"+no_pics+".jpg");
                outStream.write(data);
                outStream.close();
                Log.d("TTTTT", "onPictureTaken - wrote bytes: " + data.length);
                no_pics++;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally
            {
                camera.stopPreview();
                camera.release();
                camera = null;
                Toast.makeText(getApplicationContext(), "Image snapshot Done",Toast.LENGTH_LONG).show();


            }
            Log.d("TTTTT", "onPictureTaken - jpeg");
        }
    };




    @Override
    public void onImageCapture(@NonNull File imageFile) {
        Toast.makeText(getApplicationContext(),"gfdgdf",Toast.LENGTH_LONG).show();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

        //Display the image to the image view
        ((ImageView) findViewById(R.id.cam_prev)).setImageBitmap(bitmap);
    }

    @Override
    public void onCameraError(int errorCode) {
        Toast.makeText(getApplicationContext(),"ohhhh",Toast.LENGTH_LONG).show();

    }



    /*
        private File createImageFile(){
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        try {
            image = File.createTempFile(
                    imageFileName,
                    ".jpg",
    storageDir
            );
} catch (IOException e) {
        e.printStackTrace();
        }

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();

        return image;

        }

private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
        // Create the File where the photo should go

        photoFile = createImageFile();
        // Continue only if the File was successfully created
        if (photoFile != null) {
        Uri photoURI = FileProvider.getUriForFile(this,
        "com.jskgmail1.android.fileprovider",
        photoFile);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(takePictureIntent, 1);

        }

        }}
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {


        case 111:
        if (resultCode == RESULT_OK) {

        startActivity(new Intent(CapturePictureActivity.this,QuestionsActivity.class));
        }


        }
        }
      */













}
