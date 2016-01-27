package com.luttinger.selectortakeaphoto;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.soundcloud.android.crop.Crop;

import java.io.File;

/**
 * Created by Eduardo Luttinger on 27/01/2016.
 */
public class SelectAPhotoActivity extends Activity implements View.OnClickListener {

    private static final int REQUEST_A_PICTURE = 100;
    ImageView selectAPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_an_image_and_display);
        selectAPhoto = (ImageView) findViewById(R.id.selectAPicture);
        selectAPhoto.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode){
                case  REQUEST_A_PICTURE:
                    beginCrop(data,resultCode);
                    break;
                case Crop.REQUEST_CROP:
                    handleCrop(resultCode, data);
                    break;
            }

        }
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            selectAPhoto.setImageURI(Crop.getOutput(result));
        } else if (resultCode == Crop.RESULT_ERROR) {
            Log.e("TEST","Error haciendo el crop de la imagen");
        }
    }

    private void beginCrop(Intent data,int resultCode) {
        Uri source = TakeImagesUtil.getImageUriFromResult(this,resultCode,data);
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.selectAPicture:
                selectAPhoto.setImageDrawable(null);
                startActivityForResult(TakeImagesUtil.getPickImageIntent(this), REQUEST_A_PICTURE);
                break;
            default:
                break;
        }
    }

}
