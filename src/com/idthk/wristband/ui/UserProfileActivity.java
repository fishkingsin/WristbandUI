package com.idthk.wristband.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import com.idthk.wristband.ui.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class UserProfileActivity extends Activity {
	static final String TAG = "UserProfileActivity";
	private Context mContext;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_profile);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		mContext = this;
		((Button) findViewById(R.id.btn_settings_done))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View m) {
						Log.v(TAG, "onClick");
						finish();
					}
				});
		;

		((RelativeLayout) findViewById(R.id.gender_layout))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View m) {
						Log.v(TAG, "onClick");

					}
				});
		((RelativeLayout) findViewById(R.id.birthday_layout))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View m) {
						Log.v(TAG, "onClick");

					}
				});
		((RelativeLayout) findViewById(R.id.unit_layout))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View m) {
						Log.v(TAG, "onClick");

					}
				});
		((RelativeLayout) findViewById(R.id.height_layout))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View m) {
						Log.v(TAG, "onClick");

					}
				});
		((RelativeLayout) findViewById(R.id.weight_layout))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View m) {
						Log.v(TAG, "onClick");

					}
				});
		ImageView img = ((ImageView) findViewById(R.id.user_profile_pic));
		
		SharedPreferences prefs = getSharedPreferences(getString(R.string.pref_name), 0);
        String path = prefs.getString("profile_pic", "");
        Log.v(TAG,"profile path : "+path);
        if(path!="")
        {
        	Bitmap myBitmap = Utilities.decodeFile(new File(path),this);
        	img.setImageBitmap(myBitmap);
        }
		
		img.setOnClickListener(new OnClickListener() {
			public void onClick(View m) {
				// Log.v(TAG,"onClick");
				// Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				// photoPickerIntent.setType("image/*");
				// startActivityForResult(photoPickerIntent,
				// Main.IMAGE_GALLERY_REQUEST);
				showDialog();
			}
		});
	}

	private void showDialog() {
		final String[] list = {
				(String) getResources().getText(R.string.take_picture),
				(String) getResources().getText(R.string.choose_from_library) };// ,
		// (String) getResources().getText(R.string.restore_profile_picture)};

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		DialogInterface.OnClickListener listItemOnClick = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int position) {
				Intent intent;
				switch (position) {
				case 0:
					intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(MediaStore.EXTRA_OUTPUT,
							Uri.fromFile(getTempFile(mContext)));
					startActivityForResult(intent, Main.TAKE_PHOTO_CODE);
					break;
				case 1:
					intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
					startActivityForResult(intent, Main.SELECT_IMAGE_CODE);
					break;
				case 2:
					// SharedPreferences settings =
					// getSharedPreferences("Preferences", 0);
					// SharedPreferences.Editor editor = settings.edit();
					// if (mBackgroundProfile == 0){
					// editor.putString("DEFAULT_BACKGROUND", "");
					// mChangeBackgroundImageView.setImageDrawable(getResources().getDrawable(R.drawable.default_background));
					// } else {
					// editor.putString("USER_AVATAR", "null");
					// mChangeProfileImageView.setImageDrawable(getResources().getDrawable(R.drawable.community_default_icon));
					// mDefaultAvatar = "1";
					// }
					// editor.commit();

					break;
				}

			}
		};

		DialogInterface.OnClickListener cancelBtnOnClick = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int position) {

			}
		};

		alertDialog.setItems(list, listItemOnClick);
		alertDialog.setNeutralButton("Cancel", cancelBtnOnClick);
		alertDialog.show();
	}

//	protected void onActivityResult(int requestCode, int resultcode,
//			Intent intent) {
//		super.onActivityResult(requestCode, resultcode, intent);
//		if (resultcode == RESULT_OK) {
//			if (requestCode == Main.SELECT_IMAGE_CODE) {
//				if (intent != null) {
//					Log.d(TAG,
//							"idButSelPic Photopicker: "
//									+ intent.getDataString());
//					Cursor cursor = getContentResolver().query(
//							intent.getData(), null, null, null, null);
//					cursor.moveToFirst(); // if not doing this, 01-22
//											// 19:17:04.564:
//											// ERROR/AndroidRuntime(26264):
//											// Caused by:
//											// android.database.CursorIndexOutOfBoundsException:
//											// Index -1 requested, with a size
//											// of 1
//					int idx = cursor.getColumnIndex(ImageColumns.DATA);
//					String fileSrc = cursor.getString(idx);
//					Log.d(TAG, "Picture:" + fileSrc);
//
//					Bitmap myBitmap = BitmapFactory.decodeFile(fileSrc);
//
//					((ImageView) findViewById(R.id.user_profile_pic))
//							.setImageBitmap(myBitmap);
//
//				} else {
//					Log.d(TAG, "idButSelPic Photopicker canceled");
//
//				}
//			}
//		}
//	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Utilities.getLog("resultCode", resultCode+"");
		
		if (resultCode == RESULT_OK) {
			SharedPreferences prefs = getSharedPreferences(getString(R.string.pref_name), 0);
//			SharedPreferences settings = getSharedPreferences("Preferences", 0);
    		SharedPreferences.Editor editor = prefs.edit();
//    		if (mBackgroundProfile == 0){
//    			editor.putString("DEFAULT_BACKGROUND", "1");
//    		} else {
//    			editor.putString("DEFAULT_PROFILE", "1");
//    		}
//    		editor.commit();
			
			switch (requestCode) {
			case Main.TAKE_PHOTO_CODE:
				final File file = getTempFile(this);
				try {
					Bitmap captureBmp = Media.getBitmap(getContentResolver(),
							Uri.fromFile(file));
					
					captureBmp = Utilities.decodeFile(file, mContext);
					
//					if (mBackgroundProfile == 0){
//						captureBmp = getResizedBitmap(captureBmp, 960, 640);
//					} else {
//						captureBmp = getResizedBitmap(captureBmp, 300, 300);
//					}
					
					FileOutputStream fos;
//					if (mBackgroundProfile == 0){
//						fos = openFileOutput("DefaultBackground", Context.MODE_PRIVATE);
//					} else {
						fos = openFileOutput("DefaultProfile", Context.MODE_PRIVATE);
//						mDefaultAvatar = "0";
//					}
				    captureBmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
				    ((ImageView) findViewById(R.id.user_profile_pic)).setImageBitmap(captureBmp);
				    editor.putString("profile_pic",file.getAbsolutePath());
				    
		    		editor.commit();
				    fos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
//				loadDefaultBackground();
				
				break;
				
			case Main.SELECT_IMAGE_CODE:
				Uri selectedImageUri = data.getData();
	            String[] filePathColumn = {MediaStore.Images.Media.DATA};

	            Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
	            cursor.moveToFirst();

	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            String filePath = cursor.getString(columnIndex);
	            cursor.close();


	            //Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
	            Bitmap selectedImage = Utilities.decodeFile(new File(filePath), mContext);
	            
	            FileOutputStream fos;
			    try {
//			    	if (mBackgroundProfile == 0){
//			    		fos = openFileOutput("DefaultBackground", Context.MODE_PRIVATE);
//			    		selectedImage = getResizedBitmap(selectedImage, 960, 640);
//			    	} else {
			    		fos = openFileOutput("DefaultProfile", Context.MODE_PRIVATE);
			    		selectedImage = getResizedBitmap(selectedImage, 300, 300);
//			    		mDefaultAvatar = "0";
//			    	}
					selectedImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
					((ImageView) findViewById(R.id.user_profile_pic)).setImageBitmap(selectedImage);
					
					 editor.putString("profile_pic",filePath);
			    		editor.commit();
					
				    fos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			    
//			    loadDefaultBackground();
				break;
			}
		}
	}
	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);


        // RECREATE THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }
	

	private File getTempFile(Context context) {
		// it will return /sdcard/image.tmp
		final File path = new File(Environment.getExternalStorageDirectory(),
				context.getPackageName());
		if (!path.exists()) {
			path.mkdir();
		}
		Calendar c = Calendar.getInstance();
		int seconds = c.get(Calendar.SECOND);

		return new File(path, "image.png");
	}

}
