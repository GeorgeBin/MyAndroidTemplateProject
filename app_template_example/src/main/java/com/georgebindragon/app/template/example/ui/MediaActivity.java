package com.georgebindragon.app.template.example.ui;

import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.georgebindragon.app.template.example.R;
import com.georgebindragon.app.template.example.media.RawPlayer;
import com.georgebindragon.base.function.log.LogProxy;

import androidx.appcompat.app.AppCompatActivity;

public class MediaActivity extends AppCompatActivity implements View.OnClickListener
{
	private static final String TAG = "MediaActivity-->";

	Button play_btn;
	Button stop_btn;

	Button typeCall_btn;
	Button typeSystem_btn;
	Button typeMusic_btn;
	Button typeRing_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_media);
		play_btn = findViewById(R.id.ui_main_play_btn);
		play_btn.setOnClickListener(this);
		stop_btn = findViewById(R.id.ui_main_stop_btn);
		stop_btn.setOnClickListener(this);

		typeCall_btn = findViewById(R.id.ui_main_typeCall_btn);
		typeCall_btn.setOnClickListener(this);
		typeSystem_btn = findViewById(R.id.ui_main_typeSystem_btn);
		typeSystem_btn.setOnClickListener(this);
		typeMusic_btn = findViewById(R.id.ui_main_typeMusic_btn);
		typeMusic_btn.setOnClickListener(this);
		typeRing_btn = findViewById(R.id.ui_main_typeRing_btn);
		typeRing_btn.setOnClickListener(this);

		findViewById(R.id.ui_main_typeAlarm_btn).setOnClickListener(this);
		findViewById(R.id.ui_main_typeNotification_btn).setOnClickListener(this);
		findViewById(R.id.ui_main_typeDTMF_btn).setOnClickListener(this);

		initPlayer(AudioManager.STREAM_MUSIC);
	}

	RawPlayer rawPlayer;

	private void initPlayer(int type)
	{
		LogProxy.i(TAG, "initPlayer-->");
		rawPlayer = new RawPlayer(this, R.raw.adele_hello, true, type);
	}

	private void playMusic() { rawPlayer.play(); }

	private void stopMusic() { rawPlayer.stop(); }

	@Override
	public void onClick(View v)
	{
		int id = v.getId();
		switch (id)
		{
			default:
				LogProxy.i(TAG, "onClick-->v=" + v);
				break;
			case R.id.ui_main_play_btn:
				playMusic();
				break;
			case R.id.ui_main_stop_btn:
				stopMusic();
				break;
			case R.id.ui_main_typeCall_btn:
				initPlayer(AudioManager.STREAM_VOICE_CALL);
				break;
			case R.id.ui_main_typeSystem_btn:
				initPlayer(AudioManager.STREAM_SYSTEM);
				break;
			case R.id.ui_main_typeMusic_btn:
				initPlayer(AudioManager.STREAM_MUSIC);
				break;
			case R.id.ui_main_typeRing_btn:
				initPlayer(AudioManager.STREAM_RING);
				break;
			case R.id.ui_main_typeAlarm_btn:
				initPlayer(AudioManager.STREAM_ALARM);
				break;
			case R.id.ui_main_typeNotification_btn:
				initPlayer(AudioManager.STREAM_NOTIFICATION);
				break;
			case R.id.ui_main_typeDTMF_btn:
				initPlayer(AudioManager.STREAM_DTMF);
				break;
		}
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		rawPlayer.stop();
		rawPlayer.release();
	}
}
