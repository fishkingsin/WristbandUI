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
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;

public class UserPreferencesActivity extends Activity {
	static final String TAG = "UserProfileActivity";
	private Activity mContext;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_profile);
		mContext = this;
		int targetPreferenceResource = R.xml.userprofile_preferences;
		UserPrefsFragment fragment = UserPrefsFragment
				.create(targetPreferenceResource);
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, fragment).commit();

	}

	@Override
	public void onBackPressed() {
	}

	public static class UserPrefsFragment extends PreferenceFragment implements
			SharedPreferences.OnSharedPreferenceChangeListener {
		static final String ARG_XML = "xml";
		private int targetPreferenceFile;
		private Context mContext;

		public static UserPrefsFragment create(int targetPreferenceFile) {
			UserPrefsFragment fragment = new UserPrefsFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_XML, targetPreferenceFile);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public void onAttach(Activity activity) {
			mContext = activity;
			super.onAttach(activity);
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			targetPreferenceFile = getArguments().getInt(ARG_XML);
			// Load the preferences from an XML resource
			addPreferencesFromResource(targetPreferenceFile);

			SharedPreferences sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(this.getActivity());
			sharedPreferences.registerOnSharedPreferenceChangeListener(this);

			Preference pref = findPreference(getString(R.string.pref_user_name));
			pref.setSummary(sharedPreferences.getString(
					getString(R.string.pref_user_name),
					getString(R.string.default_user_name)));
			pref = findPreference("prefUserGender");
			pref.setSummary(sharedPreferences.getString("prefUserGender",
					getString(R.string.default_user_gender)));

			pref = findPreference("prefUnit");
			pref.setSummary(sharedPreferences.getString("prefUnit", "Metric"));
			pref = findPreference(getString(R.string.pref_profile_pic));
			pref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference) {
					Log.v(TAG, "preference" + preference.getKey()
							+ " onPreferenceClick");

					showDialog();

					return true;
				}
			});

		}

		@Override
		public void onDestroyView() {

			SharedPreferences sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(this.getActivity());
			sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
			super.onDestroyView();
		}

		@Override
		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
			// handle the preference change here'

			Log.v(TAG, "key : " + sharedPreferences.toString() + " " + key
					+ " " + sharedPreferences.getAll().toString());
			Preference pref = findPreference(key);
			if (key.equals("prefUserGender")) {

				pref.setSummary(sharedPreferences.getString(key,
						getString(R.string.default_user_gender)));
			} else if (key.equals("prefUnit")) {
				pref.setSummary(sharedPreferences.getString(key, "Metric"));
			} else if (key.equals(getString(R.string.pref_user_name))) {
				pref.setSummary(sharedPreferences.getString(key,
						getString(R.string.default_user_name)));
			} else if (key.equals("prefDateOfBirth")) {
				pref.setSummary(sharedPreferences.getString(key, "1991.01.01"));
			} else if (key.equals("prefHeight")) {
				pref.setSummary(sharedPreferences.getInt(key, 178));
			} else if (key.equals("prefWeight")) {
				pref.setSummary(sharedPreferences.getInt(key, 50));
			}

		}

		private void showDialog() {
			final String[] list = {
					(String) getResources().getText(R.string.take_picture),
					(String) getResources().getText(
							R.string.choose_from_library) };// ,
			// (String)
			// getResources().getText(R.string.restore_profile_picture)};

			AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

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
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
//			Utilities.getLog("resultCode", resultCode + "");
			Log.v(TAG,"resultCode " + resultCode);

			if (resultCode == RESULT_OK) {

				switch (requestCode) {
				case Main.TAKE_PHOTO_CODE:
					final File file = getTempFile(mContext);
					try {
						Bitmap captureBmp = Media.getBitmap(mContext.getContentResolver(),
								Uri.fromFile(file));

						captureBmp = Utilities.decodeFile(file, mContext);

						captureBmp = getResizedBitmap(captureBmp, 300, 300);

						FileOutputStream fos;
						fos = mContext.openFileOutput("DefaultProfile", Context.MODE_PRIVATE);

						captureBmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
						Log.v(TAG, "Take new photo path: " + file.getPath());
						

						SharedPreferences prefs = PreferenceManager
								.getDefaultSharedPreferences(mContext);

						SharedPreferences.Editor editor = prefs.edit();
						editor.putString(getString(R.string.pref_profile_pic),  file.getPath());
						// Commit the edits!
						editor.commit();
						
						
						fos.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					break;

				case Main.SELECT_IMAGE_CODE:
					Uri selectedImageUri = data.getData();
					String[] filePathColumn = { MediaStore.Images.Media.DATA };

					Cursor cursor = mContext.getContentResolver().query(selectedImageUri,
							filePathColumn, null, null, null);
					cursor.moveToFirst();

					int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
					String filePath = cursor.getString(columnIndex);
					cursor.close();

					// Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
					Bitmap selectedImage = Utilities.decodeFile(new File(filePath),
							mContext);

					FileOutputStream fos;
					try {
						fos = mContext.openFileOutput("DefaultProfile", Context.MODE_PRIVATE);
						selectedImage = getResizedBitmap(selectedImage, 300, 300);

						selectedImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
						Log.v(TAG, "Select photo path: " + filePath);
						
						SharedPreferences prefs = PreferenceManager
								.getDefaultSharedPreferences(mContext);

						SharedPreferences.Editor editor = prefs.edit();
						editor.putString(getString(R.string.pref_profile_pic), filePath);
						// Commit the edits!
						editor.commit();
						fos.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					break;
				}
			}
			super.onActivityResult(requestCode, resultCode, data);
		}
		static public File getTempFile(Context mContext) {
			// TODO Auto-generated method stub
			final File path = new File(Environment.getExternalStorageDirectory(),
					mContext.getPackageName());
			if (!path.exists()) {
				path.mkdir();
			}
			Calendar c = Calendar.getInstance();
			int seconds = c.get(Calendar.SECOND);

			return new File(path, "image.png");
		}

		static public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
			int width = bm.getWidth();
			int height = bm.getHeight();
			float scaleWidth = ((float) newWidth) / width;
			float scaleHeight = ((float) newHeight) / height;
			// CREATE A MATRIX FOR THE MANIPULATION
			Matrix matrix = new Matrix();
			// RESIZE THE BIT MAP
			matrix.postScale(scaleWidth, scaleHeight);

			// RECREATE THE NEW BITMAP
			Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
					matrix, false);
			return resizedBitmap;
		}
	}

	

	

}
