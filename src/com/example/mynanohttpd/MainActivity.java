package com.example.mynanohttpd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;

import org.apache.http.Header;
import org.apache.http.conn.util.InetAddressUtils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class MainActivity extends Activity  implements SurfaceHolder.Callback {
	private static final int MENU_EXIT = 0xCC882201;
  
//    StreamingLoop cameraLoop;
//    StreamingLoop httpLoop;

//    NativeAgent nativeAgt;
//    CameraView myCamView;
    StreamingServer strServer;
    
    TextView myMessage;
    Button btnStart;
    Button playButon;
    Button testnat;
    RadioGroup resRadios;

    boolean inServer = false;
    boolean inStreaming = false;
    int targetWid = 320;
    int targetHei = 240;

	private static final String TAG = "com.ineoquest.Sample";
	private static final String PREFS = "com.ineoquest.Sample.prefs";
	private static final String USERUUID_PREF = "com.ineoquest.Sample.prefs.useruuid";

	private SurfaceView _mediaPlayerSurface;
	private SurfaceHolder _mediaPlayerSurfaceHolder;
	private MediaPlayer _mediaPlayer;
	// private ControlsFragment _controlsFragment;
	// private MediaPlayerFragment _mediaPlayerFragment;
	private UUID _userUUID;
	private String _userName;
	private boolean isSurfaveCreated;
    
//    final String checkingFile = "/sdcard/ipcam/myvideo.mp4";
//    final String resourceDirectory = "/sdcard/ipcam";
    final String checkingFile = "/sdcard/TestLibary/test.mp4";
    final String resourceDirectory = "/sdcard/TestLibary";
    final String HTTP_HEAD ="http://";

    // memory object for encoder
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window win = getWindow();
		win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);		
        win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); 
        
        setContentView(R.layout.main);
        
        //
		_mediaPlayerSurface = (SurfaceView) findViewById(R.id.mediaPlayerSurface);
		_mediaPlayerSurfaceHolder = _mediaPlayerSurface.getHolder();
		_mediaPlayerSurfaceHolder.addCallback(MainActivity.this);


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
        
		Constant.IP = getLocalIpAddress();
		Constant.FULL_HTTP_IP = HTTP_HEAD+Constant.IP;
		LogUtils.i("FULL_HTTP_IP=="+Constant.FULL_HTTP_IP);
		
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu m){
    	m.add(0, MENU_EXIT, 0, "Exit");
    	return true;
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem i){
    	switch(i.getItemId()){
		    case MENU_EXIT:
                finish();
                System.exit(0);
		    	return true;	    	
		    default:
		    	return false;
		}
    }

    @Override
    public void onDestroy(){
    	super.onDestroy();
    }

    @Override
    public void onStart(){
    	super.onStart();
        setup();
    }

    @Override
    public void onResume(){
    	super.onResume();
    }

    @Override
    public void onPause(){    	
    	super.onPause();
        finish();
        System.exit(0);
    }
    
    private void clearResource() {
        String[] str ={"rm", "-r", resourceDirectory};

        try { 
            Process ps = Runtime.getRuntime().exec(str);
            try {
                ps.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildResource() {
        String[] str ={"mkdir", resourceDirectory};

        try { 
            Process ps = Runtime.getRuntime().exec(str);
            try {
                ps.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } 
        
            copyResourceFile(R.raw.index, resourceDirectory + "/index.html"  );
            copyResourceFile(R.raw.style, resourceDirectory + "/style.css"  );
            copyResourceFile(R.raw.player, resourceDirectory + "/player.js"  );
            copyResourceFile(R.raw.player_object, resourceDirectory + "/player_object.swf"  );
            copyResourceFile(R.raw.player_controler, resourceDirectory + "/player_controler.swf"  );
            copyResourceFile(R.raw.index2, resourceDirectory + "/index2.html"  ); 
//            copySdcardFile("/sdcard/TestLibary2/test2.mkv", resourceDirectory + "/test2.mkv");
            copySdcardFile("/sdcard/TestLibary2/index3.html", resourceDirectory + "/index3.html");
            copyFolder("/sdcard/TestLibary2/testm3u8/0",resourceDirectory);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setup() {
//        clearResource();
    	//TODO
//        buildResource(); 

//        NativeAgent.LoadLibraries();
//        nativeAgt = new NativeAgent();
//        cameraLoop = new StreamingLoop("teaonly.projects");
//        httpLoop = new StreamingLoop("teaonly.http");

//    	myCamView = (CameraView)findViewById(R.id.surface_overlay);
//        SurfaceView sv = (SurfaceView)findViewById(R.id.surface_camera);
//    	myCamView.SetupCamera(sv);
       
        myMessage = (TextView)findViewById(R.id.label_msg);

        btnStart = (Button)findViewById(R.id.btn_start);
        btnStart.setOnClickListener(startAction);
        btnStart.setEnabled(true);
        
        playButon= (Button)findViewById(R.id.playButton);
        playButon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(getBaseContext(), MainActivity.class);
//				startActivity(intent);
				
				startPlaying(null);
				
			}
		});
		
        
        testnat= (Button)findViewById(R.id.TestNat);
        testnat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String url = Constant.FULL_HTTP_IP+":8080/index3.html";

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
							LogUtils.e("___________error_________________="+new String(arg2));
							LogUtils.e("error message ="+arg3.getMessage());
						}
					});

				} catch (Exception e) {
					LogUtils.e("error " + e.getMessage());
					e.printStackTrace();
				}
			}
		});
        
		/*
        Button btnTest = (Button)findViewById(R.id.btn_test);
        btnTest.setOnClickListener(testAction);
        btnTest.setVisibility(View.INVISIBLE);
		*/

        RadioButton rb = (RadioButton)findViewById(R.id.res_low);
        rb.setOnClickListener(low_res_listener);
        rb = (RadioButton)findViewById(R.id.res_medium);
        rb.setOnClickListener(medium_res_listener);
        rb = (RadioButton)findViewById(R.id.res_high);
        rb.setOnClickListener(high_res_listener);

        resRadios = (RadioGroup)findViewById(R.id.resolution);

        View  v = (View)findViewById(R.id.layout_setup);
        v.setVisibility(View.VISIBLE);
    }
    
    private void startServer() {
        inServer = true;
        btnStart.setText( getString(R.string.action_stop) );
        btnStart.setEnabled(true);    
//        NetInfoAdapter.Update(this);
        
        myMessage.setText( getString(R.string.msg_prepare_ok) + " http://" + getLocalIpAddress()+ ":8080" );

        try {
            strServer = new StreamingServer(8080, resourceDirectory); 
            strServer.setOnRequestListen(streamingRequest);
        } catch( IOException e ) {
            e.printStackTrace();
            showToast(this, "Can't start http server..");
        }
    }

    private void stopServer() {
       inServer = false;
       btnStart.setText( getString(R.string.action_start) );
       btnStart.setEnabled(true);    
       myMessage.setText( getString(R.string.msg_idle));
       if ( strServer != null) {
            strServer.stop();
            strServer = null;
       }
    }

    private boolean startStreaming() {
    	LogUtils.i("************startStreaming");
        if ( inStreaming == true)
            return false;
        
//        cameraLoop.InitLoop();
//        httpLoop.InitLoop();
//        nativeAgt.NativeStartStreamingMedia(cameraLoop.getReceiverFileDescriptor() , httpLoop.getSenderFileDescriptor());

//        myCamView.PrepareMedia(targetWid, targetHei);
//        boolean ret = myCamView.StartStreaming(cameraLoop.getSenderFileDescriptor());
//        if ( ret == false) {
//            return false;
//        } 
        
        new Handler(Looper.getMainLooper()).post(new Runnable() { 
            public void run() { 
                showToast(MainActivity.this, getString(R.string.msg_streaming));
                btnStart.setEnabled(false);
            } 
        });

        inStreaming = true;
        return true;
    }

    private void stopStreaming() {
        if ( inStreaming == false)
            return;
        inStreaming = false;

//        myCamView.StopMedia(); 
//        httpLoop.ReleaseLoop();
//        cameraLoop.ReleaseLoop();
//        
//        nativeAgt.NativeStopStreamingMedia();
        new Handler(Looper.getMainLooper()).post(new Runnable() { 
            public void run() { 
                btnStart.setEnabled(true);
            } 
        });
    }

    private void doAction() {
         if ( inServer == false) {
//            myCamView.PrepareMedia(targetWid, targetHei);
//            boolean ret = myCamView.StartRecording(checkingFile);
           if ( true ) {
               btnStart.setEnabled(false);
               resRadios.setEnabled(false);
               myMessage.setText( getString(R.string.msg_prepare_waiting));
               new Handler().postDelayed(new Runnable() { 
                    public void run() { 
//                        myCamView.StopMedia();
                        LogUtils.i("targetWid=="+targetWid);
                        LogUtils.d("targetHei=="+targetHei);
                        LogUtils.i("checkingFile=="+checkingFile);
                        if ( true/*NativeAgent.NativeCheckMedia(targetWid, targetHei, checkingFile)*/ ) {
                            startServer();    
                        } else {
                            btnStart.setEnabled(true);
                            resRadios.setEnabled(false);
                            showToast(MainActivity.this, getString(R.string.msg_prepare_error2));
                        }
                    } 
                }, 2000); // 2 seconds to release 
            } else {
                showToast(this, getString(R.string.msg_prepare_error1));
            }
        } else {
            stopServer();
        }
    
    }

    /**
     * 复制项目的文件到服务器
     * @param rid
     * @param targetFile
     * @throws IOException
     */
    private void copyResourceFile(int rid, String targetFile) throws IOException {
        InputStream fin = ((Context)this).getResources().openRawResource(rid);
        FileOutputStream fos = new FileOutputStream(targetFile);  

        int     length;
        byte[] buffer = new byte[1024*32]; 
        while( (length = fin.read(buffer)) != -1){
            fos.write(buffer,0,length);
        }
        fin.close();
        fos.close();
    }

    /**
     * 复制sdcard卡的文件到服务器
     * @param rid
     * @param targetFile
     * @throws IOException
     */
    private void copySdcardFile(String sdcardPath, String targetFile) throws IOException {
//        InputStream fin = new inpu
//    	File file = new File("/sdcard/TestLibary2/test2.mkv");
    	File file = new File(sdcardPath); 
//    	LogUtils.e("is folder  --"+file.isDirectory());
    	FileInputStream fin = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(targetFile);  

        int     length;
        byte[] buffer = new byte[1024*32]; 
        while( (length = fin.read(buffer)) != -1){
            fos.write(buffer,0,length);
        }
        fin.close();
        fos.close();
    }
    
    /**
     * 复制文件夹的文件到另一个文件夹
     * @param originDirectory
     * @param targetDirectory
     */
    public void copyFolder(String originDirectory,String targetDirectory){
        File origindirectory = new File(originDirectory);   //源路径File实例
        File targetdirectory = new File(targetDirectory);  //目标路径File实例
//        if(!origindirectory.isDirectory() || !targetdirectory.isDirectory()){    //判断是不是正确的路径
//                    System.out.println("不是正确的目录！");
//                    LogUtils.e("不是正确的目录！");
//                    return;
//        }
        File[] fileList = origindirectory.listFiles();  //目录中的所有文件
        for(File file : fileList){
                  if(!file.isFile())   //判断是不是文件
                  continue;
//                  System.out.println(file.getName());
                  LogUtils.e("fileName "+file.getName());
                  LogUtils.e("file "+file.toString());
                  String fileName = file.getName();
                  try{
//                           FileInputStream fin = new FileInputStream(file);
//                           BufferedInputStream bin = new BufferedInputStream(fin);
//                           PrintStream pout = new PrintStream(targetdirectory.getAbsolutePath()+"/"+file.getName());
//                           BufferedOutputStream bout = new BufferedOutputStream(pout);
//                           int total =bin.available();  //文件的总大小
//                           int percent = total/100;    //文件总量的百分之一
//                           int count;
//                           while((count = bin.available())!= 0){
//                                      int c = bin.read();  //从输入流中读一个字节
//                                      bout.write((char)c);  //将字节（字符）写到输出流中     
//
//                                      if(((total-count) % percent) == 0){
//                                               double d = (double)(total-count) / total; //必须强制转换成double
////                                               System.out.println(Math.round(d*100)+"%"); //输出百分比进度
//                                               LogUtils.e(Math.round(d*100)+"%");
//                                       }
//                           }
//                           bout.close();
//                           pout.close();
//                           bin.close();
//                           fin.close();
                	  
                	 	FileInputStream fin = new FileInputStream(file);
//                	 	File fileOut = new File(targetDirectory+"/hls");
//                	 	if(!fileOut.exists()){
//                	 		LogUtils.e("file create....");
//                	 		if(!fileOut.mkdir()){
//                	 			LogUtils.e("file mkdir error");
//                	 			return ;
//                	 		}
//                	 		
//                	 	}
                	 	File file2 = new File(targetDirectory+"/hls/"+fileName);
                        FileOutputStream fos = new FileOutputStream(file2);  

                        int     length;
                        byte[] buffer = new byte[1024*32]; 
                        while( (length = fin.read(buffer)) != -1){
                            fos.write(buffer,0,length);
                            LogUtils.i("________====");
                        }
                        fin.close();
                        fos.close();
                	  
                  }catch(Exception e){
                	  LogUtils.e("error=="+e.getMessage());
                           e.printStackTrace();
                  }
       }
//      System.out.println("End");
      LogUtils.e("end---------------------");
      
}
    
    
    private void showToast(Context context, String message) { 
        // create the view
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.message_toast, null);

        // set the text in the view
        TextView tv = (TextView)view.findViewById(R.id.message);
        tv.setText(message);

        // show the toast
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }   

    StreamingServer.OnRequestListen streamingRequest = new StreamingServer.OnRequestListen() {
        @Override
        public InputStream onRequest() {
            LogUtils.d("Request live streaming...");
//            if ( startStreaming() == false){
//                return null;
//            }
            try {
            	
            	
                InputStream ins = null;//httpLoop.getInputStream(); 
                return ins;
                
//                InputStream inputStream = 
                
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.d("call httpLoop.getInputStream() error");
                stopStreaming();              
            } 
            LogUtils.d("Return a null response to request");
            return null;
        }
        
        @Override 
        public void requestDone() {
            LogUtils.d("Request live streaming is Done!");
            stopStreaming();     
        }
    };

    private OnClickListener startAction = new OnClickListener() {
        @Override
        public void onClick(View v) {
            doAction();
        }
    };

    private OnClickListener testAction = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // do some debug testing here.
        }
    };
    
    private OnClickListener low_res_listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            targetWid = 320;
            targetHei = 240;
        }
    };
    private OnClickListener medium_res_listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            targetWid = 640;
            targetHei = 480;
        }
    };
    private OnClickListener high_res_listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            targetWid = 1280;
            targetHei = 720;
        }
    };

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

					// add a listener to stop playing when the video is complete
					_mediaPlayer
							.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
								@Override
								public void onCompletion(MediaPlayer mediaPlayer) {
									stopPlaying();
								}
							});

					// add a listener so we know when the media is prepared
					_mediaPlayer
							.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
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
					_mediaPlayer
							.setScreenOnWhilePlaying(true);

					// get our target url
					Uri testUri = Uri
							.parse("http://asm-origin.ineoquest.ca/hls/origin-asm.m3u8");
					// Uri sdcardtestUri =
					// Uri.parse("/sdcard/TestLibary2/testm3u8/0/prog_index.m3u8");
					// Uri sdcardtestUri =
					// Uri.parse("/sdcard/TestLibary2/test.mp4");
//					String url = "http://172.20.171.1/hls/test.m3u8";
//					String url = Constant.FULL_HTTP_IP+":8080/test2.mkv";
//					Constant.FULL_HTTP_IP+":8080/hls2/test.m3u8";
//					Constant.FULL_HTTP_IP+":8080/hls/Master_bipbopall.m3u8"
					String url = Constant.FULL_HTTP_IP+":8080/hls2/test.m3u8";
					Uri sdcardtestUri = Uri.parse(url);
					LogUtils.d("sdcardtestUri----" + sdcardtestUri.toString());
					// set our datasource
					_mediaPlayer.setDataSource(
							MainActivity.this, sdcardtestUri);

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
					MainActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(
									MainActivity.this.getApplicationContext(),
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
	
	
	/**
	 * 获取本机IP
	 */
	public String getLocalIpAddress() {
        try {
            // 遍历网络接口
            Enumeration<NetworkInterface> infos = NetworkInterface
                    .getNetworkInterfaces();
            while (infos.hasMoreElements()) {
                // 获取网络接口
                NetworkInterface niFace = infos.nextElement();
                Enumeration<InetAddress> enumIpAddr = niFace.getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress mInetAddress = enumIpAddr.nextElement();
                    // 所获取的网络地址不是127.0.0.1时返回得得到的IP
                    if (!mInetAddress.isLoopbackAddress()
                            && InetAddressUtils.isIPv4Address(mInetAddress
                                    .getHostAddress())) {
                        return mInetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
 
        }
        return null;
    }
	
}
