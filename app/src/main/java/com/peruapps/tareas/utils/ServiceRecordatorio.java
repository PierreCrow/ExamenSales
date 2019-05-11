package com.peruapps.tareas.utils;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;


import com.peruapps.tareas.R;
import com.peruapps.tareas.activities.TareasActivity;
import com.peruapps.tareas.adapter.ListaTareas;
import com.peruapps.tareas.datasource.AppDataBase;
import com.peruapps.tareas.entities.Tarea;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ServiceRecordatorio extends IntentService {
    private final LocalBinder mBinder = new LocalBinder();
    protected Handler handler;
    protected Toast mToast;

    AppDataBase BBDD;
    ArrayList<Tarea> misTareas;
    static Integer idNotificacion=1;


    private class cargaTareas extends AsyncTask<Void, Void, ArrayList<Tarea>> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected ArrayList<Tarea> doInBackground(Void... params) {

            List<Tarea> tareass;
            ArrayList<Tarea> listatareas=null;
            try {

                tareass=BBDD.tareasDAO().GetAll();

                if(tareass==null || tareass.size()==0)
                {
                }
                else
                {
                    listatareas=new ArrayList<>();

                    for(Integer i=0;i< tareass.size();i++)
                    {
                        listatareas.add(tareass.get(i));
                    }
                }






            }
            catch (Exception e) {
            }

            return listatareas;
        }

        @Override
        protected void onPostExecute(ArrayList<Tarea> result) {
            super.onPostExecute(result);




            if(result==null || result.size()==0)
            {

            }
            else
            {
                misTareas =result;


                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
                String fechaActual = mdformat.format(calendar.getTime());

                for(Integer i=0;i<misTareas.size();i++)
                {
                    if(fechaActual.equals(misTareas.get(i).getFecha()))
                    {
                        addNotification();
                    }
                }


            }

        }
    }




    private void addNotification() {

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.iconnnnn)
                        .setContentTitle("Tareas")
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentText("Tienes tareas para hoy");






        Intent notificationIntent = new Intent(this, TareasActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);



        // Agregar la notificacion
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(idNotificacion++, builder.build());

    }



    public class LocalBinder extends Binder {
        public ServiceRecordatorio getService() {
            return ServiceRecordatorio.this;
        }
    }

    public ServiceRecordatorio() {
        super("ServiceRecordatorio");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        BBDD = Utiles.Inicia_BBDD_LOCAL(getApplicationContext());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    //detecta si el app esta en foreground
    private boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onHandleIntent(Intent workIntent) {


        Timer_Envia_A_Servidor();

        //    startTime = System.currentTimeMillis();
        //    timerHandler.postDelayed(timerRunnable, 0);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        onHandleIntent(intent);


        return Service.START_STICKY;
    }


    void Timer_Envia_A_Servidor() {


        Integer minutos = 360;
        Integer CADA_CUANTO_TIEMPO = 60000 * minutos;

        CountDownTimer newtimer = new CountDownTimer(1000000000, CADA_CUANTO_TIEMPO) {

            Boolean primeravez = true;

            public void onTick(long millisUntilFinished) {

                if (primeravez == true) {
                    primeravez = false;
                } else {
                    primeravez = false;


                    //aca hacer codigo

                    cargaTareas task= new cargaTareas();
                    task.execute();




                }


            }

            public void onFinish() {


            }
        };
        newtimer.start();


    }






}