package com.example.mynanohttpd;

import java.util.UUID;

import org.apache.http.Header;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class PlayActivity extends Activity implements SurfaceHolder.Callback {

	private static final String TAG = "com.ineoquest.Sample";
	private static final String PREFS = "com.ineoquest.Sample.prefs";
	private static final String USERUUID_PREF = "com.ineoquest.Sample.prefs.useruuid";

	private SurfaceView _mediaPlayerSurface;
	private SurfaceHolder _mediaPlayerSurfaceHolder;
	private MediaPlayer _mediaPlayer;
//	private AndroidMediaPlayerAdapter _androidMediaPlayerAdapter;
	// private ControlsFragment _controlsFragment;
	// private MediaPlayerFragment _mediaPlayerFragment;
	private UUID _userUUID;
	private String _userName;
	private boolean isSurfaveCreated;
	private Button playButton;
	private Button testNat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.mediaplayer_fragment);
		//
		_mediaPlayerSurface = (SurfaceView) findViewById(R.id.mediaPlayerSurface);
		_mediaPlayerSurfaceHolder = _mediaPlayerSurface.getHolder();
		_mediaPlayerSurfaceHolder.addCallback(PlayActivity.this);

//		_androidMediaPlayerAdapter = new AndroidMediaPlayerAdapter();

		//
		// retrieve our user uuid
		SharedPreferences settings = this.getSharedPreferences(PREFS, 0);
		String uuidValue = settings.getString(USERUUID_PREF, null);

		if (uuidValue != null) {
			String[] values = uuidValue.split("\\|");

			_userUUID = UUID.fromString(values[0]);
			_userName = values[1];
		} else {
//			_userUUID = UUIDHelper.UInt64ToUUID(0);
			_userName = "Default";

			// persist default values
			SharedPreferences.Editor settingsEditor = settings.edit();
			settingsEditor.putString(USERUUID_PREF,
					String.format("%s|%s", _userUUID.toString(), _userName));
			settingsEditor.commit();
		}

		// /
		// initialize the SDK
//		IQPegasus
//				.Configure()
//				.withAMPASM("ampasm-demo.ineoquest.ca", 9997)
//				.withAMPASM("iqdamp.ineoquest.ca", 9997)
//				// .withAMPASM("nowtv.ineoquest.ca", 9998)
//				.withSSLCertificateCheckingDisabled()
//				.withMediaPlayerAdapter(_androidMediaPlayerAdapter)
//				.withContext(this)
//				.withGeoLocation()
//				.withNetworkDetection()
//				.withAPIKey(
//						UUID.fromString("abcdefff-aaaa-bbaa-ffff-329bf39fa1e4"))
//				// .withVeriStreaListener(_veriStreamListener)
//				// .withHttpErrorListener(_httpErrorListener)
//				// .withAMPConnectionListener(_ampConnectionListener)
//				.withUserId(_userUUID).start();

		// /
		playButton = (Button) findViewById(R.id.paly);
		playButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isSurfaveCreated) {
					startPlaying(null);
					isSurfaveCreated = false;
				}
			}
		});

		testNat = (Button) findViewById(R.id.testNAT);
		testNat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String url = "http://172.20.171.2:8080/index3.html";

				try {
					// String content =
					// CustomHttpClient.PostFromWebByHttpClient(getActivity(),
					// url);
					// LogUtils.e("the content ="+content);

					HttpGetJsonUtil.get(url, new AsyncHttpResponseHandler() {

						@Override
						public void onSuccess(int statueCode, Header[] arg1,
								byte[] result) {
							String content = new String(result);
							LogUtils.e("___________result________________= "
									+ new String(result));
						}

						@Override
						public void onFailure(int arg0, Header[] arg1,
								byte[] arg2, Throwable arg3) {
//							LogUtils.e("___________error_________________="+new String(arg2));
							LogUtils.e("error message ="+arg3.getMessage());
						}
					});

				} catch (Exception e) {
					LogUtils.e("error " + e.getMessage());
					e.printStackTrace();
				}

			}
		});

		// LogUtils.e( "start play...");
		// try {
		// Thread.sleep(5000);
		// startPlaying(null);
		//
		// } catch (Exception e) {
		// // TODO: handle exception
		// }

	}

	// //////////////////////////////////////////////////////////////////////////
	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {
		LogUtils.e("Surface created.");
	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2,
			int i3) {
		LogUtils.e("Surface changed.");
		isSurfaveCreated = true;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
		LogUtils.e("Surface destroyed.");
	}

	// ///////////////////////////////////////////

	private void startPlaying(final Uri uri) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// Sample.this.runOnUiThread(new Runnable() {
					// @Override
					// public void run() {
					// _mediaPlayerFragment.setMediaPlaying();
					// _controlsFragment.setMediaPlaying(selectedMedia.toString());
					// _controlsFragment.setStopMediaButtonText(R.string.please_wait_text);
					// _controlsFragment.setStopMediaButtonEnabled(false);
					//
					// }
					// });

					_mediaPlayer = new MediaPlayer();
//					_androidMediaPlayerAdapter.setMediaPlayer(_mediaPlayer);

					// add a listener to stop playing when the video is complete
					_mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
								@Override
								public void onCompletion(MediaPlayer mediaPlayer) {
									stopPlaying();
								}
							});

					// add a listener so we know when the media is prepared
					_mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
								@Override
								public void onPrepared(MediaPlayer mediaPlayer) {
									// Sample.this.runOnUiThread(new Runnable()
									// {
									// @Override
									// public void run() {
									// _controlsFragment.setStopMediaButtonText(R.string.stopPlaybackText);
									// _controlsFragment.setStopMediaButtonEnabled(true);
									// }
									// });

								}
							});

					// set our display
					_mediaPlayer.setDisplay(
							_mediaPlayerSurfaceHolder);

					// lock the screen on
					_mediaPlayer.setScreenOnWhilePlaying(true);

					// get our target url
					Uri testUri = Uri
							.parse("http://asm-origin.ineoquest.ca/hls/origin-asm.m3u8");
					// Uri sdcardtestUri =
					// Uri.parse("/sdcard/TestLibary2/testm3u8/0/prog_index.m3u8");
					// Uri sdcardtestUri =
					// Uri.parse("/sdcard/TestLibary2/test.mp4");
					String url = "http://172.20.171.1/hls/test.m3u8";
					Uri sdcardtestUri = Uri.parse(url);
//					Uri targetUri = IQPegasus.getInstance()
//							.getMediaUri(testUri);

					LogUtils.d("sdcardtestUri----" + sdcardtestUri.toString());
					// set our datasource
					_mediaPlayer.setDataSource(
							PlayActivity.this, sdcardtestUri);

					// prepare
					_mediaPlayer.prepare();

					// start
					_mediaPlayer.start();
					LogUtils.d("palying.......");
				} catch (final Exception ex) {
					LogUtils.e("error==" + ex.getMessage());

					// report our playback error
//					_mediaPlayer.onMediaPlayerPlaybackError(ex);

					// force a stop
					stopPlaying();

					// simply toast the user that an error happened
					PlayActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(
									PlayActivity.this.getApplicationContext(),
									"Playback error: " + ex.getMessage(),
									Toast.LENGTH_LONG).show();
						}
					});
				}
			}
		}).start();
	}

	private void stopPlaying() {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					_mediaPlayer.stop();
				} catch (Exception e) {
					LogUtils.e("Media Player not set.");
					return;
				}

				_mediaPlayer.release();
				_mediaPlayer = null;

				// _mediaPlayerFragment.setMediaStopped();
				// _controlsFragment.setMediaStopped();

				// clear the surface
				_mediaPlayerSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
				_mediaPlayerSurfaceHolder.setFormat(PixelFormat.OPAQUE);

			}
		});
	}

}
